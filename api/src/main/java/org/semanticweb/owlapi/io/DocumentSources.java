package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Nullable;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tukaani.xz.XZInputStream;

/**
 * Static methods from AbstractOWLParser. Mostly used by OWLOntologyDocumentSource implementations.
 */
public class DocumentSources {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentSources.class);
    private static final String ZIP_FILE_EXTENSION = ".zip";
    private static final String GZ_FILE_EXTENSION = ".gz";
    private static final String XZ_FILE_EXTENSION = ".xz";
    private static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    private static final Pattern CONTENT_DISPOSITION_FILE_NAME_PATTERN =
        Pattern.compile(".*filename=\"([^\\s;]*)\".*");
    private static final int CONTENT_DISPOSITION_FILE_NAME_PATTERN_GROUP = 1;
    private static final Pattern ZIP_ENTRY_ONTOLOGY_NAME_PATTERN =
        Pattern.compile(".*owl|rdf|xml|mos");
    private static final String ACCEPTABLE_CONTENT_ENCODING = "xz,gzip,deflate";
    private static final String TEXTPLAIN_REQUEST_TYPE = ", text/plain; q=0.1";
    private static final String LAST_REQUEST_TYPE = ", */*; q=0.09";
    private static final String DEFAULT_REQUEST =
        "application/rdf+xml, application/xml; q=0.7, text/xml; q=0.6" + TEXTPLAIN_REQUEST_TYPE
            + LAST_REQUEST_TYPE;

    private DocumentSources() {}

    /**
     * Select the available input source and, if it is not already a Reader, wrap it in a Reader.
     * This method removes the duplication of code required for each caller to figure out if a
     * reader or an inputstream is available. The returned Reader will be buffered.
     *
     * @param source ontology source
     * @param configuration loader configuration to use of the reader must be built form the input
     *        IRI
     * @param encoding character encoding if a new Reader needs to be created.
     * @return A Reader for the input; if no Reader can be obtained, an
     *         OWLOntologyInputSourceException is thrown.
     * @throws OWLOntologyInputSourceException if an IO related exception is thrown.
     */
    public static Reader wrapInputAsReader(OWLOntologyDocumentSource source,
        OWLOntologyLoaderConfiguration configuration, Charset encoding)
        throws OWLOntologyInputSourceException {
        Optional<Reader> reader = source.getReader();
        if (reader.isPresent()) {
            return new BufferedReader(reader.get());
        }
        return new BufferedReader(
            new InputStreamReader(wrap(wrapInput(source, configuration)), encoding));
    }

    /**
     * Call #wrapwrapInputAsReader(OWLOntologyLoaderConfiguration, String) with UTF-* as default
     * encoding.
     *
     * @param source ontology source
     * @param configuration loader configuration to use of the reader must be built form the input
     *        IRI
     * @return A Reader wrapped in an Optional; if no Reader can be obtained, the result is
     *         Optional.empty. @throws OWLOntologyInputSourceException if an IO related exception is
     *         thrown.
     * @throws OWLOntologyInputSourceException if an IO related exception is thrown.
     */
    public static Reader wrapInputAsReader(OWLOntologyDocumentSource source,
        OWLOntologyLoaderConfiguration configuration) throws OWLOntologyInputSourceException {
        return wrapInputAsReader(source, configuration, StandardCharsets.UTF_8);
    }

    /**
     * Select the available input source as an input stream. The input stream will be buffered.
     *
     * @param source ontology source
     * @param configuration loader configuration to use of the reader must be built form the input
     *        IRI
     * @return A Reader for the input; if no Reader can be obtained, an
     *         OWLOntologyInputSourceException is thrown.
     * @throws OWLOntologyInputSourceException if an IO related exception is thrown.
     */
    public static InputStream wrapInput(OWLOntologyDocumentSource source,
        OWLOntologyLoaderConfiguration configuration) throws OWLOntologyInputSourceException {
        Optional<InputStream> input = source.getInputStream();
        if (!input.isPresent() && !source.hasAlredyFailedOnIRIResolution()) {
            if (source.getDocumentIRI().getNamespace().startsWith("jar:")) {
                if (source.getDocumentIRI().getNamespace().startsWith("jar:!")) {
                    String name = source.getDocumentIRI().toString().substring(5);
                    if (!name.startsWith("/")) {
                        name = "/" + name;
                    }
                    return DocumentSources.class.getResourceAsStream(name);
                } else {
                    try {
                        return streamFromJar(source.getDocumentIRI()).getInputStream();
                    } catch (IOException e) {
                        source.setIRIResolutionFailed(true);
                        throw new OWLParserException(e);
                    }
                }
            }

            Optional<String> headers = source.getAcceptHeaders();
            if (headers.isPresent()) {
                input = getInputStream(source.getDocumentIRI(), configuration, headers.get());
            } else {
                input = getInputStream(source.getDocumentIRI(), configuration, DEFAULT_REQUEST);
            }
        }
        if (input.isPresent()) {
            return new BufferedInputStream(input.get());
        }
        throw new OWLOntologyInputSourceException("No input reader can be found");
    }

    protected static JarURLConnection streamFromJar(IRI documentIRI)
        throws IOException, MalformedURLException {
        return (JarURLConnection) new URL(documentIRI.toString()).openConnection();
    }

    /**
     * A convenience method that obtains an input stream from a URI. This method sets up the correct
     * request type and wraps the input stream within a buffered input stream.
     *
     * @param documentIRI The URI from which the input stream should be returned
     * @param config the load configuration
     * @return The input stream obtained from the URI
     * @throws OWLOntologyInputSourceException if there was an {@code IOException} in obtaining the
     *         input stream from the URI.
     * @deprecated use {@link #getInputStream(IRI, OWLOntologyLoaderConfiguration, String)} instead
     */
    @Deprecated
    public static Optional<InputStream> getInputStream(IRI documentIRI,
        OWLOntologyLoaderConfiguration config) throws OWLOntologyInputSourceException {
        return getInputStream(documentIRI, config, DEFAULT_REQUEST);
    }

    /**
     * A convenience method that obtains an input stream from a URI. This method sets up the correct
     * request type and wraps the input stream within a buffered input stream.
     *
     * @param documentIRI The URI from which the input stream should be returned
     * @param config the load configuration
     * @param acceptHeaders accept headers for the connection
     * @return The input stream obtained from the URI
     * @throws OWLOntologyInputSourceException if there was an {@code IOException} in obtaining the
     *         input stream from the URI.
     */
    @SuppressWarnings("resource")
    public static Optional<InputStream> getInputStream(IRI documentIRI,
        OWLOntologyLoaderConfiguration config, String acceptHeaders)
        throws OWLOntologyInputSourceException {
        try {
            URL originalURL = documentIRI.toURI().toURL();
            URLConnection conn = originalURL.openConnection();
            String actualAcceptHeaders = acceptHeaders;
            if (!acceptHeaders.contains("text/plain")) {
                actualAcceptHeaders += TEXTPLAIN_REQUEST_TYPE;
            }
            if (!acceptHeaders.contains("*/*")) {
                actualAcceptHeaders += LAST_REQUEST_TYPE;
            }
            conn.addRequestProperty("Accept", actualAcceptHeaders);
            if (config.getAuthorizationValue() != null
                && !config.getAuthorizationValue().isEmpty()) {
                conn.setRequestProperty("Authorization", config.getAuthorizationValue());
            }
            if (config.isAcceptingHTTPCompression()) {
                conn.setRequestProperty("Accept-Encoding", ACCEPTABLE_CONTENT_ENCODING);
            }
            int connectionTimeout = config.getConnectionTimeout();
            conn.setConnectTimeout(connectionTimeout);
            conn = connect(config, conn, connectionTimeout, actualAcceptHeaders, new HashSet<>());
            String contentEncoding = conn.getContentEncoding();
            InputStream is = connectWithFiveRetries(documentIRI, config, conn, connectionTimeout,
                contentEncoding);
            if (is == null) {
                return emptyOptional();
            }
            return optional(is);
        } catch (IOException e) {
            throw new OWLOntologyInputSourceException(e);
        }
    }

    protected static URLConnection connect(OWLOntologyLoaderConfiguration config,
        URLConnection conn, int connectionTimeout, String acceptHeaders, Set<String> visited)
        throws IOException {
        if (conn instanceof HttpURLConnection && config.isFollowRedirects()) {
            // follow redirects to HTTPS
            HttpURLConnection con = (HttpURLConnection) conn;
            con.connect();
            int responseCode = con.getResponseCode();
            // redirect
            if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                || responseCode == HttpURLConnection.HTTP_MOVED_PERM
                || responseCode == HttpURLConnection.HTTP_SEE_OTHER
                // no constants for temporary and permanent redirect in HttpURLConnection
                || responseCode == 307 || responseCode == 308) {
                String location = con.getHeaderField("Location");
                if (visited.add(location)) {
                    URL newURL = new URL(location);
                    return connect(config,
                        rebuildConnection(config, connectionTimeout, newURL, acceptHeaders),
                        connectionTimeout, acceptHeaders, visited);
                } else {
                    throw new IllegalStateException(
                        "Infinite loop: redirect cycle detected. " + visited);
                }
            }
        }
        return conn;
    }

    protected static URLConnection rebuildConnection(OWLOntologyLoaderConfiguration config,
        int connectionTimeout, URL newURL, String acceptHeaders) throws IOException {
        URLConnection conn;
        conn = newURL.openConnection();
        conn.addRequestProperty("Accept", acceptHeaders);
        if (config.isAcceptingHTTPCompression()) {
            conn.setRequestProperty("Accept-Encoding", ACCEPTABLE_CONTENT_ENCODING);
        }
        conn.setConnectTimeout(connectionTimeout);
        return conn;
    }

    @Nullable
    protected static InputStream connectWithFiveRetries(IRI documentIRI,
        OWLOntologyLoaderConfiguration config, URLConnection conn, int connectionTimeout,
        String contentEncoding) throws IOException, OWLOntologyInputSourceException {
        InputStream is = null;
        int count = 0;
        while (count < config.getRetriesToAttempt() && is == null) {
            try {
                is = getInputStreamFromContentEncoding(documentIRI, conn, contentEncoding);
            } catch (SocketTimeoutException e) {
                count++;
                if (count == 5) {
                    throw new OWLOntologyInputSourceException(
                        "cannot connect to " + documentIRI + "; retry limit exhausted", e);
                }
                conn.setConnectTimeout(connectionTimeout + connectionTimeout * count);
            }
        }
        return is;
    }

    /**
     * Wrap an input stream to strip BOMs.
     *
     * @param delegate delegate to wrap
     * @return wrapped input stream
     */
    public static InputStream wrap(InputStream delegate) {
        checkNotNull(delegate, "delegate cannot be null");
        return new BOMInputStream(delegate, ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE,
            ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE);
    }

    private static boolean couldBeOntology(@Nullable ZipEntry zipEntry) {
        if (zipEntry == null) {
            return false;
        }
        return ZIP_ENTRY_ONTOLOGY_NAME_PATTERN.matcher(zipEntry.getName()).matches();
    }

    private static InputStream getInputStreamFromContentEncoding(@Nullable IRI documentIRI,
        URLConnection conn, @Nullable String contentEncoding) throws IOException {
        String fileName = getFileNameFromContentDisposition(conn);
        if (fileName == null) {
            fileName = documentIRI == null ? "" : documentIRI.toString();
        }
        InputStream in = conn.getInputStream();
        if (contentEncoding != null) {
            InputStream toReturn = handleKnownContentEncodings(contentEncoding, in, fileName);
            if (toReturn != null) {
                return toReturn;
            }
        }
        return wrap(checkFileName(in, fileName));
    }

    private static InputStream checkFileName(InputStream in, String fileName) throws IOException {
        if (isGzFileName(fileName)) {
            LOGGER.info("URL connection has no content encoding but name ends with .gz");
            return new BufferedInputStream(new GZIPInputStream(in));
        }
        if (isXzFileName(fileName)) {
            LOGGER.info("URL connection has no content encoding but name ends with .xz");
            return new BufferedInputStream(new XZInputStream(in));
        }
        if (isZipFileName(fileName)) {
            ZipInputStream zis = new ZipInputStream(in);
            ZipEntry entry = null;
            ZipEntry nextEntry = zis.getNextEntry();
            // XXX is this a bug?
            while (entry != null && nextEntry != null) {
                if (couldBeOntology(nextEntry)) {
                    entry = nextEntry;
                }
                nextEntry = zis.getNextEntry();
            }
            return zis;
        }
        return in;

    }

    @Nullable
    protected static InputStream handleKnownContentEncodings(String contentEncoding, InputStream in,
        String fileName) throws IOException {
        if ("xz".equals(contentEncoding)) {
            LOGGER.info("URL connection input stream is compressed using xz");
            return new BufferedInputStream(checkFileName(new XZInputStream(in), fileName));
        }
        if ("gzip".equals(contentEncoding)) {
            LOGGER.info("URL connection input stream is compressed using gzip");
            return new BufferedInputStream(checkFileName(new GZIPInputStream(in), fileName));
        }
        if ("deflate".equals(contentEncoding)) {
            LOGGER.info("URL connection input stream is compressed using deflate");
            return checkFileName(new InflaterInputStream(in, new Inflater(true)), fileName);
        }
        return null;
    }

    @Nullable
    private static String getFileNameFromContentDisposition(URLConnection connection) {
        String contentDispositionHeaderValue =
            connection.getHeaderField(CONTENT_DISPOSITION_HEADER);
        if (contentDispositionHeaderValue != null) {
            Matcher matcher =
                CONTENT_DISPOSITION_FILE_NAME_PATTERN.matcher(contentDispositionHeaderValue);
            if (matcher.matches()) {
                return matcher.group(CONTENT_DISPOSITION_FILE_NAME_PATTERN_GROUP);
            }
        }
        return null;
    }

    private static boolean isZipFileName(String fileName) {
        return fileName.toLowerCase(Locale.getDefault()).endsWith(ZIP_FILE_EXTENSION);
    }

    private static boolean isGzFileName(String fileName) {
        return fileName.toLowerCase(Locale.getDefault()).endsWith(GZ_FILE_EXTENSION);
    }

    private static boolean isXzFileName(String fileName) {
        return fileName.toLowerCase(Locale.getDefault()).endsWith(XZ_FILE_EXTENSION);
    }
}
