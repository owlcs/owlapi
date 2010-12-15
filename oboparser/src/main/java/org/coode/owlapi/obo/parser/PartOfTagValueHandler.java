package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public class PartOfTagValueHandler extends AbstractTagValueHandler {

    public PartOfTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.RELATIONSHIP.getName(), consumer);
    }


    public void handle(String id, String value) {
        int index = value.indexOf(' ');
        String propLocalName = value.substring(0, index);
        String val = value.substring(index + 1, value.length());
        OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(getIRIFromValue(propLocalName));
        OWLClassExpression desc = getDataFactory().getOWLObjectSomeValuesFrom(prop, getClassFromId(val));
        OWLAxiom ax = getDataFactory().getOWLSubClassOfAxiom(
                getCurrentClass(),
                desc
        );
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
