package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tukaani.xz.XZInputStream;

import com.google.common.base.Charsets;

/**
 * Static methods from AbstractOWLParser. Mostly used by
 * OWLOntologyDocumentSource implementations.
 */
public class DocumentSources {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentSources.class);
    private static final String ZIP_FILE_EXTENSION = ".zip";
    private static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    private static final Pattern CONTENT_DISPOSITION_FILE_NAME_PATTERN = Pattern.compile(".*filename=\"([^\\s;]*)\".*");
    private static final int CONTENT_DISPOSITION_FILE_NAME_PATTERN_GROUP = 1;
    private static final Pattern ZIP_ENTRY_ONTOLOGY_NAME_PATTERN = Pattern.compile(".*owl|rdf|xml|mos");
    private static final String acceptableContentEncoding = "xz,gzip,deflate";
    private static final @Nonnull String REQUESTTYPES = "application/rdf+xml, application/xml; q=0.5, text/xml; q=0.3, */*; q=0.2";

    /**
     * Select the available input source and, if it is not already a Reader,
     * wrap it in a Reader. This method removes the duplication of code required
     * for each caller to figure out if a reader or an inputstream is available.
     * The returned Reader will be buffered.
     * 
     * @param source
     *        ontology source
     * @param configuration
     *        loader configuration to use of the reader must be built form the
     *        input IRI
     * @param encoding
     *        character encoding if a new Reader needs to be created.
     * @return A Reader for the input; if no Reader can be obtained, an
     *         OWLOntologyInputSourceException is thrown.
     * @throws OWLOntologyInputSourceException
     *         if an IO related exception is thrown.
     */
    public static Reader wrapInputAsReader(OWLOntologyDocumentSource source,
        OWLOntologyLoaderConfiguration configuration, Charset encoding) throws OWLOntologyInputSourceException {
        Optional<Reader> reader = source.getReader();
        if (reader.isPresent()) {
            return new BufferedReader(reader.get());
        }
        return new BufferedReader(new InputStreamReader(wrapInput(source, configuration), encoding));
    }

    /**
     * Call #wrapwrapInputAsReader(OWLOntologyLoaderConfiguration, String) with
     * UTF-* as default encoding.
     * 
     * @param source
     *        ontology source
     * @param configuration
     *        loader configuration to use of the reader must be built form the
     *        input IRI
     * @return A Reader wrapped in an Optional; if no Reader can be obtained,
     *         the result is Optional.empty. @throws
     *         OWLOntologyInputSourceException if an IO related exception is
     *         thrown.
     * @throws OWLOntologyInputSourceException
     *         if an IO related exception is thrown.
     */
    public static Reader wrapInputAsReader(OWLOntologyDocumentSource source,
        OWLOntologyLoaderConfiguration configuration) throws OWLOntologyInputSourceException {
        return wrapInputAsReader(source, configuration, Charsets.UTF_8);
    }

    /**
     * Select the available input source as an input stream. The input stream
     * will be buffered.
     * 
     * @param source
     *        ontology source
     * @param configuration
     *        loader configuration to use of the reader must be built form the
     *        input IRI
     * @return A Reader for the input; if no Reader can be obtained, an
     *         OWLOntologyInputSourceException is thrown.
     * @throws OWLOntologyInputSourceException
     *         if an IO related exception is thrown.
     */
    public static InputStream wrapInput(OWLOntologyDocumentSource source, OWLOntologyLoaderConfiguration configuration)
        throws OWLOntologyInputSourceException {
        Optional<InputStream> input = source.getInputStream();
        if (!input.isPresent()) {
            if (!source.hasAlredyFailedOnIRIResolution()) {
                input = getInputStream(source.getDocumentIRI(), configuration);
            }
        }
        if (input.isPresent()) {
            return new BufferedInputStream(input.get());
        }
        throw new OWLOntologyInputSourceException("No input reader can be found");
    }

    /**
     * A convenience method that obtains an input stream from a URI. This method
     * sets up the correct request type and wraps the input stream within a
     * buffered input stream.
     * 
     * @param documentIRI
     *        The URI from which the input stream should be returned
     * @param config
     *        the load configuration
     * @return The input stream obtained from the URI
     * @throws OWLOntologyInputSourceException
     *         if there was an {@code IOException} in obtaining the input stream
     *         from the URI.
     */
    @SuppressWarnings("resource")
    public static Optional<InputStream> getInputStream(IRI documentIRI, OWLOntologyLoaderConfiguration config)
        throws OWLOntologyInputSourceException {
        try {
            URL originalURL = documentIRI.toURI().toURL();
            String originalProtocol = originalURL.getProtocol();
            URLConnection conn = originalURL.openConnection();
            conn.addRequestProperty("Accept", REQUESTTYPES);
            if (config.isAcceptingHTTPCompression()) {
                conn.setRequestProperty("Accept-Encoding", acceptableContentEncoding);
            }
            int connectionTimeout = config.getConnectionTimeout();
            conn.setConnectTimeout(connectionTimeout);
            if (conn instanceof HttpURLConnection && config.isFollowRedirects()) {
                // follow redirects to HTTPS
                HttpURLConnection con = (HttpURLConnection) conn;
                con.connect();
                int responseCode = con.getResponseCode();
                // redirect
                if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                    || responseCode == HttpURLConnection.HTTP_MOVED_PERM
                    || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                    String location = con.getHeaderField("Location");
                    URL newURL = new URL(location);
                    String newProtocol = newURL.getProtocol();
                    if (!originalProtocol.equals(newProtocol)) {
                        // then different protocols: redirect won't follow
                        // automatically
                        conn = newURL.openConnection();
                        conn.addRequestProperty("Accept", REQUESTTYPES);
                        if (config.isAcceptingHTTPCompression()) {
                            conn.setRequestProperty("Accept-Encoding", acceptableContentEncoding);
                        }
                        conn.setConnectTimeout(connectionTimeout);
                    }
                }
            }
            String contentEncoding = conn.getContentEncoding();
            InputStream is = null;
            int count = 0;
            while (count < config.getRetriesToAttempt() && is == null) {
                try {
                    is = getInputStreamFromContentEncoding(conn, contentEncoding);
                } catch (SocketTimeoutException e) {
                    count++;
                    if (count == 5) {
                        throw new OWLOntologyInputSourceException("cannot connect to " + documentIRI
                            + "; retry limit exhausted", e);
                    }
                    conn.setConnectTimeout(connectionTimeout + connectionTimeout * count);
                }
            }
            if (is == null) {
                return emptyOptional();
            }
            if (isZipName(documentIRI, conn)) {
                ZipInputStream zis = new ZipInputStream(is);
                ZipEntry entry = null;
                ZipEntry nextEntry = zis.getNextEntry();
                while (entry != null && nextEntry != null) {
                    if (couldBeOntology(nextEntry)) {
                        entry = nextEntry;
                    }
                    nextEntry = zis.getNextEntry();
                }
                is = wrap(zis);
            } else {
                is = wrap(is);
            }
            return optional(is);
        } catch (IOException e) {
            throw new OWLOntologyInputSourceException(e);
        }
    }

    /**
     * Wrap an input stream to strip BOMs.
     * 
     * @param delegate
     *        delegate to wrap
     * @return wrapped input stream
     */
    public static InputStream wrap(InputStream delegate) {
        checkNotNull(delegate, "delegate cannot be null");
        return new BOMInputStream(delegate, ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE,
            ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE);
    }

    private static boolean couldBeOntology(@Nullable ZipEntry zipEntry) {
        if (zipEntry == null) {
            return false;
        }
        return ZIP_ENTRY_ONTOLOGY_NAME_PATTERN.matcher(zipEntry.getName()).matches();
    }

    @SuppressWarnings("resource")
    private static InputStream getInputStreamFromContentEncoding(URLConnection conn, @Nullable String contentEncoding)
        throws IOException {
        InputStream in = conn.getInputStream();
        if (contentEncoding != null) {
            if ("xz".equals(contentEncoding)) {
                LOGGER.info("URL connection input stream is compressed using xz");
                return new BufferedInputStream(new XZInputStream(in));
            }
            if ("gzip".equals(contentEncoding)) {
                LOGGER.info("URL connection input stream is compressed using gzip");
                return new BufferedInputStream(new GZIPInputStream(in));
            }
            if ("deflate".equals(contentEncoding)) {
                LOGGER.info("URL connection input stream is compressed using deflate");
                return wrap(new InflaterInputStream(in, new Inflater(true)));
            }
        }
        String fileName = getFileNameFromContentDisposition(conn);
        if (fileName == null && conn.getURL() != null) {
            fileName = conn.getURL().toString();
        }
        if (fileName != null) {
            if (fileName.endsWith(".gz")) {
                LOGGER.info("URL connection has no content encoding but name ends with .gz");
                return new BufferedInputStream(new GZIPInputStream(in));
            }
            if (fileName.endsWith(".xz")) {
                LOGGER.info("URL connection has no content encoding but name ends with .xz");
                return new BufferedInputStream(new XZInputStream(in));
            }
        }
        return wrap(in);
    }

    private static boolean isZipName(IRI documentIRI, URLConnection connection) {
        if (isZipFileName(documentIRI.toString())) {
            return true;
        } else {
            String fileName = getFileNameFromContentDisposition(connection);
            return fileName != null && isZipFileName(fileName);
        }
    }

    private static @Nullable String getFileNameFromContentDisposition(URLConnection connection) {
        String contentDispositionHeaderValue = connection.getHeaderField(CONTENT_DISPOSITION_HEADER);
        if (contentDispositionHeaderValue != null) {
            Matcher matcher = CONTENT_DISPOSITION_FILE_NAME_PATTERN.matcher(contentDispositionHeaderValue);
            if (matcher.matches()) {
                return matcher.group(CONTENT_DISPOSITION_FILE_NAME_PATTERN_GROUP);
            }
        }
        return null;
    }

    private static boolean isZipFileName(String fileName) {
        return fileName.toLowerCase(Locale.getDefault()).endsWith(ZIP_FILE_EXTENSION);
    }
}
