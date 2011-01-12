package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */  @SuppressWarnings("unused")
public class OWLOntologyHandler extends AbstractOWLElementHandler<OWLOntology> {

    public OWLOntologyHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	public void startElement(String name) throws OWLXMLParserException {
    }


    @Override
	public void attribute(String name, String value) throws OWLParserException {
        if (name.equals("ontologyIRI")) {
            OWLOntologyID newID = new OWLOntologyID(IRI.create(value), getOntology().getOntologyID().getVersionIRI());
            getOWLOntologyManager().applyChange(new SetOntologyID(getOntology(), newID));
        }
        if(name.equals("versionIRI")) {
            OWLOntologyID newID = new OWLOntologyID(getOntology().getOntologyID().getOntologyIRI(), IRI.create(value));
            getOWLOntologyManager().applyChange(new SetOntologyID(getOntology(), newID));
        }
    }


    @Override
	public void handleChild(AbstractOWLAxiomElementHandler handler) throws OWLXMLParserException {
        OWLAxiom axiom = handler.getOWLObject();
        if(!axiom.isAnnotationAxiom() || getConfiguration().isLoadAnnotationAxioms()) {
            getOWLOntologyManager().applyChange(new AddAxiom(getOntology(), axiom));
        }
    }


    @Override
	public void handleChild(AbstractOWLDataRangeHandler handler) throws OWLXMLParserException {
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
    }


    @Override
	public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        getOWLOntologyManager().applyChange(new AddOntologyAnnotation(getOntology(), handler.getOWLObject()));
    }


    public void endElement() throws OWLParserException, UnloadableImportException {
    }


    public OWLOntology getOWLObject() {
        return getOntology();
    }


    @Override
	public void setParentHandler(OWLElementHandler<?> handler) {

    }
}
