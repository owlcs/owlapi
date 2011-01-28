package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public class NameTagValueHandler extends AbstractTagValueHandler {

    public NameTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.NAME.getName(), consumer);
    }


    public void handle(String id, String value) {
        // This is an annotation - but add as a label
        OWLEntity ent;
        if (getConsumer().isTerm()) {
            ent = getDataFactory().getOWLClass(getIRIFromValue(id));
        } else if (getConsumer().isTypedef()) {
            ent = getDataFactory().getOWLObjectProperty(getIRIFromValue(id));
        } else {
            ent = getDataFactory().getOWLNamedIndividual(getIRIFromValue(id));
        }
        OWLLiteral con = getDataFactory().getOWLLiteral(value);
        OWLAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(getDataFactory().getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()), ent.getIRI(), con);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
