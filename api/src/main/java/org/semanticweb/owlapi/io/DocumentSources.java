package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
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
import org.tukaani.xz.XZInputStream;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.base.Charsets;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * Static methods from AbstractOWLParser. Mostly used by
 * OWLOntologyDocumentSource implementations.
 */
public class DocumentSources {

    private static final String ZIP_FILE_EXTENSION = ".zip";
    private static final Pattern CONTENT_DISPOSITION_FILE_NAME_PATTERN = Pattern.compile(".*filename=\"([^\\s;]*)\".*");
    private static final Pattern ZIP_ENTRY_ONTOLOGY_NAME_PATTERN = Pattern.compile(".*owl|rdf|xml|mos");

    private DocumentSources() {}

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
        return new BufferedReader(new InputStreamReader(wrap(wrapInput(source, configuration)), encoding));
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
        if (!input.isPresent() && !source.hasAlredyFailedOnIRIResolution()) {
            input = getInputStream(source.getDocumentIRI(), configuration);
        }
        if (input.isPresent()) {
            return new BufferedInputStream(input.get());
        }
        throw new OWLOntologyInputSourceException("No input reader can be found");
    }

    private static final LoadingCache<Integer, OkHttpClient> CACHE = Caffeine.newBuilder().maximumSize(16).build(
        DocumentSources::client);

    private static Response getResponse(IRI documentIRI, int timeout) throws IOException {
        Builder builder = new Request.Builder()
            .url(documentIRI.toString())
            .addHeader("Accept","application/rdf+xml, application/xml; q=0.5, text/xml; q=0.3, */*; q=0.2")
            .addHeader("Accept-Encoding", "xz,gzip,deflate");
        Request request = builder.build();
        Call newCall = CACHE.get(Integer.valueOf(timeout)).newCall(request);
        return newCall.execute();
    }

    private static OkHttpClient client(int connectTimeout) {
        return new OkHttpClient.Builder().connectTimeout(connectTimeout, TimeUnit.MILLISECONDS).readTimeout(
            connectTimeout, TimeUnit.MILLISECONDS).followRedirects(true).followSslRedirects(true).build();
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
            int count = 0;
            InputStream is = null;
            if (documentIRI.getNamespace().startsWith("file:")) {
                is = documentIRI.toURI().toURL().openStream();
                if (isZipFileName(documentIRI.toString())) {
                    is = handleZips(is);
                }
            }
            if (is == null) {
                String disposition = null;
                while (count < config.getRetriesToAttempt() && is == null) {
                    try {
                        count++;
                        int timeout = count * config.getConnectionTimeout();
                        Response response = getResponse(documentIRI, timeout);
                        String encoding = response.header("Content-Encoding");
                        disposition = response.header("Content-Disposition");
                        is = getInputStreamFromContentEncoding(documentIRI, response, encoding, disposition);
                        if (isZipFileName(documentIRI.toString()) || isZipFileName(getFileNameFromContentDisposition(
                            disposition))) {
                            is = handleZips(is);
                        }
                    } catch (SocketTimeoutException e) {
                        if (count == 5) {
                            throw new OWLOntologyInputSourceException("cannot connect to " + documentIRI
                                + "; retry limit exhausted", e);
                        }
                    }
                }
            }
            return optional(is);
        } catch (IOException e) {
            throw new OWLOntologyInputSourceException(e);
        }
    }

    protected static InputStream handleZips(InputStream is) throws IOException {
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry selectedEntry = null;
        ZipEntry nextEntry = zis.getNextEntry();
        while (selectedEntry == null && nextEntry != null) {
            if (couldBeOntology(nextEntry)) {
                selectedEntry = nextEntry;
            }
            nextEntry = zis.getNextEntry();
        }
        return zis;
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

    private static boolean couldBeOntology(ZipEntry zipEntry) {
        return ZIP_ENTRY_ONTOLOGY_NAME_PATTERN.matcher(zipEntry.getName()).matches();
    }

    private static InputStream getInputStreamFromContentEncoding(@Nullable IRI url, Response conn,
        @Nullable String contentEncoding, @Nullable String contentDisposition) throws IOException {
        InputStream in = conn.body().byteStream();
        if (contentEncoding != null) {
            switch (contentEncoding) {
                case "xz":
                    return new BufferedInputStream(new XZInputStream(in));
                case "gzip":
                    return new BufferedInputStream(new GZIPInputStream(in));
                case "deflate":
                    return new InflaterInputStream(in, new Inflater(true));
                default:
                    break;
            }
        }
        String fileName = getFileNameFromContentDisposition(contentDisposition);
        if (fileName == null && url != null) {
            fileName = url.toString();
        }
        if (fileName != null) {
            if (fileName.endsWith(".gz")) {
                return new BufferedInputStream(new GZIPInputStream(in));
            }
            if (fileName.endsWith(".xz")) {
                return new BufferedInputStream(new XZInputStream(in));
            }
        }
        return wrap(in);
    }

    @Nullable
    private static String getFileNameFromContentDisposition(@Nullable String contentDispositionHeader) {
        if (contentDispositionHeader != null) {
            Matcher matcher = CONTENT_DISPOSITION_FILE_NAME_PATTERN.matcher(contentDispositionHeader);
            if (matcher.matches()) {
                return matcher.group(1).toLowerCase(Locale.getDefault());
            }
        }
        return null;
    }

    private static boolean isZipFileName(@Nullable String fileName) {
        if (fileName == null) {
            return false;
        }
        return fileName.toLowerCase(Locale.getDefault()).endsWith(ZIP_FILE_EXTENSION);
    }
}
