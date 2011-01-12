package org.coode.owlapi.obo.parser;

import java.io.IOException;

import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public class OWLOBOParser extends AbstractOWLParser {

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException, UnloadableImportException {
        return parse(documentSource, ontology, new OWLOntologyLoaderConfiguration());
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
        OBOParser parser;
        if (documentSource.isReaderAvailable()) {
            parser = new OBOParser(documentSource.getReader());
        }
        else if (documentSource.isInputStreamAvailable()) {
            parser = new OBOParser(documentSource.getInputStream());
        }
        else {
            parser = new OBOParser(getInputStream(documentSource.getDocumentIRI()));
        }
        parser.setHandler(new OBOConsumer(getOWLOntologyManager(), ontology, configuration));
        try {
            parser.parse();
        }
        catch (ParseException e) {
            throw new OWLParserException(e, e.currentToken.beginLine, e.currentToken.beginColumn);
        }
        catch(TokenMgrError e) {
            throw new OWLParserException(e);
        }
        return new OBOOntologyFormat();
    }
}
