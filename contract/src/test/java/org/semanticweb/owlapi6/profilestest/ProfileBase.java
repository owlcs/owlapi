package org.semanticweb.owlapi6.profilestest;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_DL;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_EL;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_QL;
import static org.semanticweb.owlapi6.profiles.Profiles.OWL2_RL;

import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;

public class ProfileBase extends TestBase {

    protected void test(OWLDocumentFormat f, String in, boolean el, boolean ql, boolean rl,
        boolean dl) {
        OWLOntology o = loadOntologyFromString(in, f);
        assertTrue("empty ontology", o.getAxiomCount() > 0);
        assertTrue(el == OWL2_EL.checkOntology(o).isInProfile());
        assertTrue(ql == OWL2_QL.checkOntology(o).isInProfile());
        assertTrue(rl == OWL2_RL.checkOntology(o).isInProfile());
        assertTrue(dl == OWL2_DL.checkOntology(o).isInProfile());
    }
}
