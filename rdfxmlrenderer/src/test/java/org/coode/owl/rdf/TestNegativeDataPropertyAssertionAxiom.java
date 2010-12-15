package org.coode.owl.rdf;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-May-2007<br><br>
 */
public class TestNegativeDataPropertyAssertionAxiom extends AbstractRendererAndParserTestCase {

    @Override
	protected String getClassExpression() {
        return "Negative data property assertion test case";
    }


    @Override
	protected Set<OWLAxiom> getAxioms() {
        OWLIndividual subj = createIndividual();
        OWLDataProperty prop = createDataProperty();
        OWLLiteral obj = getDataFactory().getOWLLiteral("TestConstant");
        OWLAxiom ax = getDataFactory().getOWLNegativeDataPropertyAssertionAxiom(prop, subj, obj);
        return Collections.singleton(ax);
    }
}
