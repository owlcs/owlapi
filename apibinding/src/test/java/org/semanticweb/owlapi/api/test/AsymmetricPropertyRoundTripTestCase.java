package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 07-Sep-2008<br><br>
 */
public class AsymmetricPropertyRoundTripTestCase extends AbstractRoundTrippingTest {

    @Override
	protected OWLOntology createOntology() {
        OWLOntology ont = getOWLOntology("Test");
        getManager().addAxiom(ont, getFactory().getOWLAsymmetricObjectPropertyAxiom(getOWLObjectProperty("p")));
        return ont;
    }


    @Override
	public void testFunctionalSyntax() throws Exception {

    }
}
