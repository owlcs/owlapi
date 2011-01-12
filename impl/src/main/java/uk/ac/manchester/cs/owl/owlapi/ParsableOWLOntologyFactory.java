package uk.ac.manchester.cs.owl.owlapi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Nov-2006<br><br>
 * <p/>
 * An ontology factory that creates ontologies by parsing documents containing
 * concrete representations of ontologies.  This ontology factory will claim that
 * it is suitable for creating an ontology if the document IRI can be opened for
 * reading.  This factory will not create empty ontologies.  Parsers are instantiated
 * by using a list of <code>OWLParserFactory</code> objects that are obtained from
 * the <code>OWLParserFactoryRegistry</code>.
 */
public class ParsableOWLOntologyFactory extends AbstractInMemOWLOntologyFactory {

    private static final Logger logger = Logger.getLogger(ParsableOWLOntologyFactory.class.getName());

    private Set<String> parsableSchemes;

    /**
     * Creates an ontology factory.
     */
    public ParsableOWLOntologyFactory() {
        parsableSchemes = new HashSet<String>();
        parsableSchemes.add("http");
        parsableSchemes.add("https");
        parsableSchemes.add("file");
        parsableSchemes.add("ftp");
    }


    @Override
	public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        super.setOWLOntologyManager(owlOntologyManager);
    }


    /**
     * Gets a list of parsers that this factory uses when it tries to
     * create an ontology from a concrete representation.
     */
    public List<OWLParser> getParsers() {
        List<OWLParser> parsers = new ArrayList<OWLParser>();
        List<OWLParserFactory> factories = OWLParserFactoryRegistry.getInstance().getParserFactories();
        for (OWLParserFactory factory : factories) {
            OWLParser parser = factory.createParser(getOWLOntologyManager());
            parser.setOWLOntologyManager(getOWLOntologyManager());
            parsers.add(parser);
        }
        return new ArrayList<OWLParser>(parsers);
    }


    /**
     * Overriden - We don't create new empty ontologies - this isn't our responsibility
     * @param documentIRI
     */
    @Override  @SuppressWarnings("unused")
	public boolean canCreateFromDocumentIRI(IRI documentIRI) {
        return false;
    }


    /**
     * Overriden - This method will throw an OWLException which wraps an UnsupportedOperationException.
     */
    @SuppressWarnings("unused")
    public OWLOntology createOWLOntology(URI ontologyURI, URI physicalURI) {
        throw new OWLRuntimeException(new UnsupportedOperationException("Cannot create new empty ontologes!"));
    }


    public boolean canLoad(OWLOntologyDocumentSource documentSource) {
        if (documentSource.isReaderAvailable()) {
            return true;
        }
        if (documentSource.isInputStreamAvailable()) {
            return true;
        }
        if (parsableSchemes.contains(documentSource.getDocumentIRI().getScheme())) {
            return true;
        }
        // If we can open an input stream then we can attempt to parse the ontology
        // TODO: Take into consideration the request type!
        try {
            InputStream is = documentSource.getDocumentIRI().toURI().toURL().openStream();
            is.close();
            return true;
        }
        catch (UnknownHostException e) {
            logger.info("Unknown host: " + e.getMessage());
        }
        catch (MalformedURLException e) {
            logger.info("Malformed URL: " + e.getMessage());
        }
        catch (FileNotFoundException e) {
            logger.info("File not found: " + e.getMessage());
        }
        catch (IOException e) {
            logger.info("IO Exception: " + e.getMessage());
        }
        return false;
    }

    public OWLOntology loadOWLOntology(OWLOntologyDocumentSource documentSource, OWLOntologyCreationHandler mediator, OWLOntologyLoaderConfiguration configuration) throws OWLOntologyCreationException {
        // Attempt to parse the ontology by looping through the parsers.  If the
        // ontology is parsed successfully then we break out and return the ontology.
        // I think that this is more reliable than selecting a parser based on a file extension
        // for example (perhaps the parser list could be ordered based on most likely parser, which
        // could be determined by file extension).
        Map<OWLParser, OWLParserException> exceptions = new LinkedHashMap<OWLParser, OWLParserException>();
        // Call the super method to create the ontology - this is needed, because
        // we throw an exception if someone tries to create an ontology directly

        OWLOntology existingOntology = null;
        IRI iri = documentSource.getDocumentIRI();
        if (getOWLOntologyManager().contains(iri)) {
            existingOntology = getOWLOntologyManager().getOntology(iri);
        }
        OWLOntologyID ontologyID = new OWLOntologyID();
        OWLOntology ont = super.createOWLOntology(ontologyID, documentSource.getDocumentIRI(), mediator);
        // Now parse the input into the empty ontology that we created
        for (final OWLParser parser : getParsers()) {
            try {
                if (existingOntology == null && !ont.isEmpty()) {
                    // Junk from a previous parse.  We should clear the ont
                    getOWLOntologyManager().removeOntology(ont);
                    ont = super.createOWLOntology(ontologyID, documentSource.getDocumentIRI(), mediator);
                }
                OWLOntologyFormat format = parser.parse(documentSource, ont, configuration);
                mediator.setOntologyFormat(ont, format);
                return ont;
            }
            catch (IOException e) {
                // No hope of any parsers working?
                // First clean up
                getOWLOntologyManager().removeOntology(ont);
                throw new OWLOntologyCreationIOException(e);
            }
            catch (UnloadableImportException e) {
                // First clean up
                getOWLOntologyManager().removeOntology(ont);
                throw e;
            }
            catch (OWLParserException e) {
                // Record this attempts and continue trying to parse.
                exceptions.put(parser, e);
            }
            catch (RuntimeException e) {
                // Clean up and rethrow
                getOWLOntologyManager().removeOntology(ont);
                throw e;
            }

        }
        if (existingOntology == null) {
            getOWLOntologyManager().removeOntology(ont);
        }
        // We haven't found a parser that could parse the ontology properly.  Throw an
        // exception whose message contains the stack traces from all of the parsers
        // that we have tried.
        throw new UnparsableOntologyException(documentSource.getDocumentIRI(), exceptions);
    }

    public OWLOntology loadOWLOntology(OWLOntologyDocumentSource documentSource, final OWLOntologyCreationHandler mediator) throws OWLOntologyCreationException {
        return loadOWLOntology(documentSource, mediator, new OWLOntologyLoaderConfiguration());
    }
}
