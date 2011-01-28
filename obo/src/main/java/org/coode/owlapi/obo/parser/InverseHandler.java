package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Mar-2007<br><br>
 */
public class InverseHandler extends AbstractTagValueHandler {

    public InverseHandler(OBOConsumer consumer) {
        super(OBOVocabulary.INVERSE.getName(), consumer);
    }


    public void handle(String id, String value) {
        OWLAxiom ax = getDataFactory().getOWLInverseObjectPropertiesAxiom(getOWLObjectProperty(id),
                getOWLObjectProperty(value));
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
