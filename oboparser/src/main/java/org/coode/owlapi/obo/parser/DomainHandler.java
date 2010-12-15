package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20-May-2007<br><br>
 */
public class DomainHandler extends AbstractTagValueHandler {

    public DomainHandler(OBOConsumer consumer) {
        super(OBOVocabulary.DOMAIN.getName(), consumer);
    }


    public void handle(String id, String value) {
        OWLObjectProperty prop = getOWLObjectProperty(getConsumer().getCurrentId());
        OWLClass cls = getOWLClass(value);
        applyChange(new AddAxiom(getOntology(), getDataFactory().getOWLObjectPropertyDomainAxiom(prop, cls)));
    }


}
