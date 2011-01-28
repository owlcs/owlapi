package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.AddAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public class IsATagValueHandler extends AbstractTagValueHandler {

    public IsATagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.IS_A.getName(), consumer);
    }


    public void handle(String id, String value) {
        if (getConsumer().isTerm()) {
            // We simply add a subclass axiom
            applyChange(new AddAxiom(getOntology(), getDataFactory().getOWLSubClassOfAxiom(
                    getClassFromId(id),
                    getClassFromId(value))
            ));
        } else if (getConsumer().isTypedef()) {
            // We simply add a sub property axiom
            applyChange(new AddAxiom(getOntology(), getDataFactory().getOWLSubObjectPropertyOfAxiom(
                    getOWLObjectProperty(id),
                    getOWLObjectProperty(value))
            ));
        }
    }
}
