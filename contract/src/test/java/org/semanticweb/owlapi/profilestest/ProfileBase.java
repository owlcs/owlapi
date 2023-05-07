package org.semanticweb.owlapi.profilestest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.profiles.Profiles.OWL2_DL;
import static org.semanticweb.owlapi.profiles.Profiles.OWL2_EL;
import static org.semanticweb.owlapi.profiles.Profiles.OWL2_QL;
import static org.semanticweb.owlapi.profiles.Profiles.OWL2_RL;

import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

class ProfileBase extends TestBase {

    protected void test(OWLDocumentFormat format, String in, boolean el, boolean ql, boolean rl,
        boolean dl) {
        OWLOntology o = loadFrom(in, format);
        assertTrue(o.getAxiomCount() > 0);
        assertEquals(el, OWL2_EL.checkOntology(o).isInProfile());
        assertEquals(ql, OWL2_QL.checkOntology(o).isInProfile());
        assertEquals(rl, OWL2_RL.checkOntology(o).isInProfile());
        assertEquals(dl, OWL2_DL.checkOntology(o).isInProfile());
    }
}
