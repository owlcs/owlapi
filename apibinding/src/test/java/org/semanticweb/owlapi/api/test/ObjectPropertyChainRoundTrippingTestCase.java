package org.semanticweb.owlapi.api.test;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-May-2008<br><br>
 */
public class ObjectPropertyChainRoundTrippingTestCase extends AbstractRoundTrippingTest {


    protected OWLOntology createOntology() {
        OWLOntology ont = getOWLOntology("OntA");
        OWLObjectProperty propA = getOWLObjectProperty("propA");
        OWLObjectProperty propB = getOWLObjectProperty("propB");
        OWLObjectProperty propC = getOWLObjectProperty("propC");
        OWLObjectProperty propD = getOWLObjectProperty("propD");
        List<OWLObjectProperty> props = new ArrayList<OWLObjectProperty>();
        props.add(propA);
        props.add(propB);
        props.add(propC);
        OWLAxiom ax = getFactory().getOWLSubPropertyChainOfAxiom(props, propD);
        addAxiom(ont, ax);
        return ont;
    }
}
