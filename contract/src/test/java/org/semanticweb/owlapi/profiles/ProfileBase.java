package org.semanticweb.owlapi.profiles;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.profiles.Profiles.*;

import javax.annotation.Nonnull;

import org.junit.BeforeClass;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;

@SuppressWarnings("javadoc")
public class ProfileBase {

    private static OWLOntologyManager m;

    @BeforeClass
    public static void setupManager() {
        m = OWLManager.createOWLOntologyManager();
    }

    @Nonnull
    OWLOntology o(@Nonnull String in) {
        try {
            return m.loadOntologyFromOntologyDocument(new StringDocumentSource(
            in));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected void test(@Nonnull String in, boolean el, boolean ql, boolean rl,
    boolean dl) {
        OWLOntology o = o(in);
        assertTrue("empty ontology", o.axioms().count() > 0);
        assertEquals(el, OWL2_EL.checkOntology(o).isInProfile());
        assertEquals(ql, OWL2_QL.checkOntology(o).isInProfile());
        assertEquals(rl, OWL2_RL.checkOntology(o).isInProfile());
        assertEquals(dl, OWL2_DL.checkOntology(o).isInProfile());
        m.removeOntology(o);
    }
}
