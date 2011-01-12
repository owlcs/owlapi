package uk.ac.manchester.cs.owl.owlapi.turtle.parser;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserIOException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Feb-2008<br><br>
 */
public class TurtleOntologyParser extends AbstractOWLParser {

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, UnloadableImportException, IOException {
        return parse(documentSource, ontology, new OWLOntologyLoaderConfiguration());
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
        try {
            TurtleParser parser;
            if(documentSource.isReaderAvailable()) {
                parser = new TurtleParser(documentSource.getReader(), new ConsoleTripleHandler(), documentSource.getDocumentIRI().toString());
            }
            else if(documentSource.isInputStreamAvailable()) {
                parser = new TurtleParser(documentSource.getInputStream(), new ConsoleTripleHandler(), documentSource.getDocumentIRI().toString());
            }
            else {
                parser = new TurtleParser(new BufferedInputStream(documentSource.getDocumentIRI().toURI().toURL().openStream()), new ConsoleTripleHandler(), documentSource.getDocumentIRI().toString());
            }

            OWLRDFConsumerAdapter consumer = new OWLRDFConsumerAdapter(getOWLOntologyManager(), ontology, parser, configuration);
            parser.setTripleHandler(consumer);
            parser.parseDocument();
            TurtleOntologyFormat format = new TurtleOntologyFormat();
            DefaultPrefixManager prefixManager = parser.getPrefixManager();
            for(String prefixName : prefixManager.getPrefixNames()) {
                format.setPrefix(prefixName, prefixManager.getPrefix(prefixName));
            }
            return format;

        }
        catch(ParseException e) {
            throw new TurtleParserException(e);
        }
        catch (IOException e) {
            throw new OWLParserIOException(e);
        }
    }
}
