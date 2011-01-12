package org.coode.owl.krssparser;

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
public class KRSSOWLParser extends AbstractOWLParser {


    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException {
        try {
            KRSSOntologyFormat format = new KRSSOntologyFormat();
            KRSSParser parser;
            if(documentSource.isReaderAvailable()) {
                parser = new KRSSParser(documentSource.getReader());
            }
            else if(documentSource.isInputStreamAvailable()) {
                parser = new KRSSParser(documentSource.getInputStream());
            }
            else {
                parser = new KRSSParser(getInputStream(documentSource.getDocumentIRI()));
            }
            parser.setOntology(ontology, getOWLOntologyManager().getOWLDataFactory());
            parser.parse();
            return format;
        }
        catch (ParseException e) {
            throw new KRSSOWLParserException(e);
        }
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
        // We ignore the configuration information
        return parse(documentSource, ontology);
    }
}
