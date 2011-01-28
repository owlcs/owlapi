package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public class TransitiveTagValueHandler extends AbstractTagValueHandler {

    public TransitiveTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.IS_TRANSITIVE.getName(), consumer);
    }


    public void handle(String id, String value) {
        if (Boolean.parseBoolean(value)) {
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(getIRIFromValue(id));
            OWLAxiom ax = getDataFactory().getOWLTransitiveObjectPropertyAxiom(prop);
            applyChange(new AddAxiom(getOntology(), ax));
        }
    }
}
