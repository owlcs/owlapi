package org.coode.owlapi.owlxmlparser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractOWLAxiomElementHandler extends AbstractOWLElementHandler<OWLAxiom> {

    private OWLAxiom axiom;

    private Set<OWLAnnotation> annotations;

    public AbstractOWLAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public OWLAxiom getOWLObject() {
        return axiom;
    }


    public void setAxiom(OWLAxiom axiom) {
        this.axiom = axiom;
    }


    @Override
	public void startElement(String name) throws OWLXMLParserException {
        if (annotations != null) {
            annotations.clear();
        }
    }


    @Override
	public void handleChild(OWLAnnotationElementHandler handler) throws OWLXMLParserException {
        if(annotations == null) {
            annotations = new HashSet<OWLAnnotation>();
        }
        annotations.add(handler.getOWLObject());
    }


    final public void endElement() throws OWLParserException, UnloadableImportException {
        setAxiom(createAxiom());
        getParentHandler().handleChild(this);
    }

    public Set<OWLAnnotation> getAnnotations() {
        if(annotations == null) {
            return Collections.emptySet();
        }
        else {
            return annotations;
        }
    }



    protected abstract OWLAxiom createAxiom() throws OWLXMLParserException;

}
