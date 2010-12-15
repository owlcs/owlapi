package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Mar-2007<br><br>
 */
public class DisjointFromHandler extends AbstractTagValueHandler {

    public DisjointFromHandler(OBOConsumer consumer) {
        super("disjoint_from", consumer);
    }


    public void handle(String id, String value) {
        OWLAxiom ax = getDataFactory().getOWLDisjointClassesAxiom(CollectionFactory.createSet(getCurrentClass(),
                getOWLClass(value)));
        applyChange(new AddAxiom(getOntology(), ax));
    }
}
