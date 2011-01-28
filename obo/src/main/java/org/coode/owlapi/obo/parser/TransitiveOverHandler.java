package org.coode.owlapi.obo.parser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Mar-2007<br><br>
 */
public class TransitiveOverHandler extends AbstractTagValueHandler {

    public TransitiveOverHandler(OBOConsumer consumer) {
        super("is_transitive_over", consumer);
    }


    public void handle(String id, String value) {
        OWLObjectProperty first = getOWLObjectProperty(id);
        OWLObjectProperty second = getOWLObjectProperty(value);
        List<OWLObjectProperty> chain = new ArrayList<OWLObjectProperty>();
        chain.add(first);
        chain.add(second);
        OWLAxiom ax = getDataFactory().getOWLSubPropertyChainOfAxiom(chain, first);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
