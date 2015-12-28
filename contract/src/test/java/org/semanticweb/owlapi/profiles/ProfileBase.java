package org.semanticweb.owlapi.profiles;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.profiles.Profiles.*;

import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLRuntimeException;

@SuppressWarnings("javadoc")
public class ProfileBase extends TestBase {

    protected void test(String in, boolean el, boolean ql, boolean rl, boolean dl) {
        OWLOntology o;
        try {
            o = loadOntologyFromString(in);
            assertTrue("empty ontology", o.axioms().count() > 0);
            assertTrue(el == OWL2_EL.checkOntology(o).isInProfile());
            assertTrue(ql == OWL2_QL.checkOntology(o).isInProfile());
            assertTrue(rl == OWL2_RL.checkOntology(o).isInProfile());
            assertTrue(dl == OWL2_DL.checkOntology(o).isInProfile());
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
