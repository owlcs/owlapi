package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.PriorityCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class OWLOntologyFactoryImpl implements OWLOntologyFactory {

    private static final long serialVersionUID = 40000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(OWLOntologyFactoryImpl.class);
    private final Set<String> parsableSchemes = new HashSet<>(Arrays.asList("http", "https", "file", "ftp"));
    private final OWLOntologyBuilder ontologyBuilder;

    @Inject
    public OWLOntologyFactoryImpl(OWLOntologyBuilder ontologyBuilder) {
        this.ontologyBuilder = verifyNotNull(ontologyBuilder);
    }

    @Override
    public boolean canCreateFromDocumentIRI(@Nonnull IRI documentIRI) {
        return true;
    }

    @Nonnull
    @Override
    public OWLOntology createOWLOntology(@Nonnull OWLOntologyManager manager, @Nonnull OWLOntologyID ontologyID,
        @Nonnull IRI documentIRI, @Nonnull OWLOntologyCreationHandler handler) throws OWLOntologyCreationException {
        OWLOntology ont = ontologyBuilder.createOWLOntology(manager, ontologyID);
        handler.ontologyCreated(ont);
        handler.setOntologyFormat(ont, new RDFXMLDocumentFormat());
        return ont;
    }

    /**
     * Select parsers by MIME type and format of the input source, if known. If
     * format and MIME type are not known or not matched by any parser, return
     * all known parsers.
     * 
     * @param documentSource
     *        document source
     * @param parsers
     *        parsers
     * @return selected parsers
     */
    private static PriorityCollection<OWLParserFactory> getParsers(OWLOntologyDocumentSource documentSource,
        PriorityCollection<OWLParserFactory> parsers) {
        if (parsers.isEmpty()) {
            return parsers;
        }
        if (!documentSource.isFormatKnown() && !documentSource.isMIMETypeKnown()) {
            return parsers;
        }
        PriorityCollection<OWLParserFactory> candidateParsers = parsers;
        if (documentSource.isFormatKnown()) {
            OWLDocumentFormat format = documentSource.getFormat();
            assert format != null;
            candidateParsers = getParsersByFormat(format, parsers);
        }
        if (candidateParsers.isEmpty() && documentSource.isMIMETypeKnown()) {
            String mimeType = documentSource.getMIMEType();
            assert mimeType != null;
            candidateParsers = getParserCandidatesByMIME(mimeType, parsers);
        }
        if (candidateParsers.isEmpty()) {
            return parsers;
        }
        return candidateParsers;
    }

    /**
     * Use the format to select a sublist of parsers.
     * 
     * @param format
     *        document format
     * @param parsers
     *        parsers
     * @return candidate parsers
     */
    private static PriorityCollection<OWLParserFactory> getParsersByFormat(@Nonnull OWLDocumentFormat format,
        PriorityCollection<OWLParserFactory> parsers) {
        PriorityCollection<OWLParserFactory> candidateParsers = new PriorityCollection<>(
            PriorityCollectionSorting.NEVER);
        for (OWLParserFactory parser : parsers) {
            if (parser.getSupportedFormat().getKey().equals(format.getKey())) {
                candidateParsers.add(parser);
            }
        }
        return candidateParsers;
    }

    /**
     * Use the MIME type it to select a sublist of parsers.
     * 
     * @param mimeType
     *        MIME type
     * @param parsers
     *        parsers
     * @return candidate parsers
     */
    private static PriorityCollection<OWLParserFactory> getParserCandidatesByMIME(@Nonnull String mimeType,
        PriorityCollection<OWLParserFactory> parsers) {
        return parsers.getByMIMEType(mimeType);
    }

    @Override
    public boolean canLoad(@Nonnull OWLOntologyDocumentSource documentSource) {
        if (documentSource.isReaderAvailable()) {
            return true;
        }
        if (documentSource.isInputStreamAvailable()) {
            return true;
        }
        if (parsableSchemes.contains(documentSource.getDocumentIRI().getScheme())) {
            return true;
        }
        // If we can open an input stream then we can attempt to parse the
        // ontology
        // TODO: Take into consideration the request type!
        try {
            documentSource.getDocumentIRI().toURI().toURL().openConnection();
            return true;
        } catch (IllegalArgumentException e) {
            LOGGER.info("Illegal argument: {}", documentSource.getDocumentIRI(), e);
        } catch (UnknownHostException e) {
            LOGGER.info("Unknown host: {}", e.getMessage(), e);
        } catch (MalformedURLException e) {
            LOGGER.info("Malformed URL: {}", e.getMessage(), e);
        } catch (FileNotFoundException e) {
            LOGGER.info("File not found: {}", e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.info("IO Exception: {}", e.getMessage(), e);
        }
        return false;
    }

    @Nonnull
    @Override
    public OWLOntology loadOWLOntology(@Nonnull OWLOntologyManager manager,
        @Nonnull OWLOntologyDocumentSource documentSource, @Nonnull OWLOntologyCreationHandler handler,
        @Nonnull OWLOntologyLoaderConfiguration configuration) throws OWLOntologyCreationException {
        // Attempt to parse the ontology by looping through the parsers. If the
        // ontology is parsed successfully then we break out and return the
        // ontology.
        // I think that this is more reliable than selecting a parser based on a
        // file extension
        // for example (perhaps the parser list could be ordered based on most
        // likely parser, which
        // could be determined by file extension).
        Map<OWLParser, OWLParserException> exceptions = new LinkedHashMap<>();
        // Call the super method to create the ontology - this is needed,
        // because
        // we throw an exception if someone tries to create an ontology directly
        OWLOntology existingOntology = null;
        IRI iri = documentSource.getDocumentIRI();
        if (manager.contains(iri)) {
            existingOntology = manager.getOntology(iri);
        }
        OWLOntologyID ontologyID = new OWLOntologyID();
        OWLOntology ont = createOWLOntology(manager, ontologyID, documentSource.getDocumentIRI(), handler);
        // Now parse the input into the empty ontology that we created
        // select a parser if the input source has format information and MIME
        // information
        Set<String> bannedParsers = Sets.newHashSet(configuration.getBannedParsers().split(" "));
        PriorityCollection<OWLParserFactory> parsers = getParsers(documentSource, manager.getOntologyParsers());
        for (OWLParserFactory parserFactory : parsers) {
            if (!bannedParsers.contains(parserFactory.getClass().getName())) {
                OWLParser parser = parserFactory.createParser();
                try {
                    if (existingOntology == null && !ont.isEmpty()) {
                        // Junk from a previous parse. We should clear the ont
                        manager.removeOntology(ont);
                        ont = createOWLOntology(manager, ontologyID, documentSource.getDocumentIRI(), handler);
                    }
                    OWLDocumentFormat format = parser.parse(documentSource, ont, configuration);
                    handler.setOntologyFormat(ont, format);
                    return ont;
                } catch (IOException e) {
                    // For input/output exceptions, we assume that it means the
                    // source cannot be read regardless of the parsers, so we
                    // stop
                    // early
                    // First clean up
                    manager.removeOntology(ont);
                    throw new OWLOntologyCreationIOException(e);
                } catch (UnloadableImportException e) {
                    // If an import cannot be located, all parsers will fail.
                    // Again,
                    // terminate early
                    // First clean up
                    manager.removeOntology(ont);
                    throw e;
                } catch (OWLParserException e) {
                    // Record this attempts and continue trying to parse.
                    exceptions.put(parser, e);
                } catch (RuntimeException e) {
                    // Clean up and rethrow
                    exceptions.put(parser, new OWLParserException(e));
                    manager.removeOntology(ont);
                    throw e;
                }
            }
        }
        if (existingOntology == null) {
            manager.removeOntology(ont);
        }
        // We haven't found a parser that could parse the ontology properly.
        // Throw an
        // exception whose message contains the stack traces from all of the
        // parsers
        // that we have tried.
        throw new UnparsableOntologyException(documentSource.getDocumentIRI(), exceptions, configuration);
    }
}
