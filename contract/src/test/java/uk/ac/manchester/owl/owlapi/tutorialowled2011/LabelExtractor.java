package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter;

class LabelExtractor extends OWLObjectVisitorExAdapter<String> implements
        OWLAnnotationObjectVisitorEx<String> {

    public LabelExtractor() {
        super("");
    }

    @Override
    public String visit(@Nonnull OWLAnnotation node) {
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
}
