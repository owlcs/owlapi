package org.coode.owlapi.obo.parser;

import java.io.IOException;

import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
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
        parser.setHandler(new OBOConsumer(ontology, configuration));
        try {
            parser.parse();
        }
        catch (ParseException e) {
        	if(e.getCause()!=null && e.getCause() instanceof OWLOntologyChangeException) {
        		throw (OWLOntologyChangeException)e.getCause();
        	}
        	if(e.getCause()!=null && e.getCause() instanceof OWLOntologyAlreadyExistsException) {
        		OWLOntologyAlreadyExistsException ex=(OWLOntologyAlreadyExistsException)e.getCause();
        		throw new UnloadableImportException(ex, ontology.getOWLOntologyManager().getOWLDataFactory().getOWLImportsDeclaration(ex.getOntologyID().getOntologyIRI()));
        	}
            Token currentToken = e.currentToken;
            if (currentToken != null) {
                int beginLine = currentToken.beginLine;
                int beginColumn = currentToken.beginColumn;
                throw new OWLParserException(e, beginLine, beginColumn);
            }
            else {
                throw new OWLParserException(e);
            }
        }
        catch(TokenMgrError e) {
            throw new OWLParserException(e);
        }
        return new OBOOntologyFormat();
    }
}
