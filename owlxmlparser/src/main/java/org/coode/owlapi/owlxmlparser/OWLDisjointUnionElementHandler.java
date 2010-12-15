package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Apr-2007<br><br>
 */
public class OWLDisjointUnionElementHandler extends AbstractOWLAxiomElementHandler {

    private OWLClass cls;

    private Set<OWLClassExpression> classExpressions;

    public OWLDisjointUnionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        classExpressions = new HashSet<OWLClassExpression>();
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLDisjointUnionAxiom(cls, classExpressions, getAnnotations());
    }


    @Override
	public void handleChild(AbstractClassExpressionElementHandler handler) throws OWLXMLParserException {
        if (cls == null) {
            OWLClassExpression desc = handler.getOWLObject();
            cls = (OWLClass) desc;
        } else {
            classExpressions.add(handler.getOWLObject());
        }
    }


}
