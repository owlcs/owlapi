package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

class LabelExtractor implements OWLObjectVisitorEx<String>, OWLAnnotationObjectVisitorEx<String> {

    @Override
    public String visit(OWLAnnotation node) {
        /*
         * If it's a label, grab it as the result. Note that if there are
         * multiple labels, the last one will be used.
         */
        if (node.getProperty().isLabel()) {
            OWLLiteral c = (OWLLiteral) node.getValue();
            return c.getLiteral();
        }
        return "";
    }

    @Override
    public String doDefault(Object individual) {
        return "";
    }
}
