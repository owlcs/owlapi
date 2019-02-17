package org.semanticweb.owlapi6.tutorialowled2011;

import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationObjectVisitorEx;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectVisitorEx;

class LabelExtractor implements OWLObjectVisitorEx<String>, OWLAnnotationObjectVisitorEx<String> {

    @Override
    public String visit(OWLAnnotation node) {
        /*
         * If it's a label, grab it as the result. Note that if there are multiple labels, the last
         * one will be used.
         */
        if (node.getProperty().isLabel()) {
            OWLLiteral c = (OWLLiteral) node.getValue();
            return c.getLiteral();
        }
        return "";
    }

    @Override
    public String doDefault(OWLObject individual) {
        return "";
    }
}
