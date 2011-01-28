package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Dec-2006<br><br>
 */
public class OWLImportsHandler extends AbstractOWLElementHandler<OWLOntology> {

    public OWLImportsHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void endElement() throws OWLParserException, UnloadableImportException {
        IRI ontIRI = getIRI(getText().trim());
        OWLImportsDeclaration decl = getOWLDataFactory().getOWLImportsDeclaration(ontIRI);
        getOWLOntologyManager().applyChange(new AddImport(getOntology(), decl));
        getOWLOntologyManager().makeLoadImportRequest(decl, getConfiguration());
    }


    public OWLOntology getOWLObject() {
        return null;
    }


    @Override
	public boolean isTextContentPossible() {
        return true;
    }
}
