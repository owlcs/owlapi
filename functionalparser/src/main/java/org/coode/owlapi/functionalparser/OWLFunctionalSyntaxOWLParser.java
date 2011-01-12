package org.coode.owlapi.functionalparser;

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
 * Date: 14-Nov-2006<br><br>
 */
public class OWLFunctionalSyntaxOWLParser extends AbstractOWLParser {

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException, UnloadableImportException {
        return parse(documentSource, ontology, new OWLOntologyLoaderConfiguration());
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
        try {
            OWLFunctionalSyntaxParser parser;
            if(documentSource.isReaderAvailable()) {
                parser = new OWLFunctionalSyntaxParser(documentSource.getReader());
            }
            else if(documentSource.isInputStreamAvailable()) {
                parser = new OWLFunctionalSyntaxParser(documentSource.getInputStream());
            }
            else {
                parser = new OWLFunctionalSyntaxParser(getInputStream(documentSource.getDocumentIRI()));
            }
            parser.setUp(getOWLOntologyManager(), ontology, configuration);
            return parser.parse();
        }
        catch (ParseException e) {
            throw new OWLParserException(e.getMessage(), e.currentToken.beginLine, e.currentToken.beginColumn);
        }
    }
}
