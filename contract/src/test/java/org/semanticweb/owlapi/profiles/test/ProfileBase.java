package org.semanticweb.owlapi.profiles.test;

import static org.junit.Assert.*;

import javax.annotation.Nonnull;

import org.junit.BeforeClass;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.Profiles;

@SuppressWarnings("javadoc")
public class ProfileBase {

    private static OWLOntologyManager m;

    @BeforeClass
    public static void setupManager() {
        m = OWLManager.createOWLOntologyManager();
    }

    private static OWLProfileReport el(OWLOntology in) {
        return Profiles.OWL2_EL.checkOntology(in);
    }

    private static OWLProfileReport ql(OWLOntology in) {
        return Profiles.OWL2_QL.checkOntology(in);
    }

    private static OWLProfileReport rl(OWLOntology in) {
        return Profiles.OWL2_RL.checkOntology(in);
    }

    private static OWLProfileReport dl(OWLOntology in) {
        return Profiles.OWL2_DL.checkOntology(in);
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
        OWLProfileReport elReport = el(o);
        assertEquals(el, elReport.isInProfile());
        OWLProfileReport qlReport = ql(o);
        assertEquals(ql, qlReport.isInProfile());
        OWLProfileReport rlReport = rl(o);
        assertEquals(rl, rlReport.isInProfile());
        OWLProfileReport dlReport = dl(o);
        assertEquals(dl, dlReport.isInProfile());
        m.removeOntology(o);
    }
}
