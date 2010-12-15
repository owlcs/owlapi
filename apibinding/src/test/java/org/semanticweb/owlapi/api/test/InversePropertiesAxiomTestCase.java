package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 22-Jul-2008<br><br>
 */
public class InversePropertiesAxiomTestCase extends AbstractRoundTrippingTest {


    @Override
	protected OWLOntology createOntology() {
            OWLOntology ont = getOWLOntology("ont");
            getManager().addAxiom(ont, getFactory().getOWLInverseObjectPropertiesAxiom(getOWLObjectProperty("p"), getOWLObjectProperty("q")));
            return ont;
    }
}
