package org.semanticweb.owlapi.profiles.test;

import static org.junit.Assert.*;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWL2QLProfile;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;

@SuppressWarnings("javadoc")
public class ProfileBase {

    protected String example = "<rdf:RDF xml:base=\"http://example.org/\" xmlns=\"http://example.org/\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">";
    protected String rdf = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" ";
    protected String head = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" ";
    protected String head2 = "<rdf:RDF xml:base=\"urn:test\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\">";
    protected String head3 = "<rdf:RDF xml:base=\"urn:test\" xmlns=\"urn:test#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">";

    private OWLProfileReport EL(OWLOntology in) {
        return new OWL2ELProfile().checkOntology(in);
    }

    private OWLProfileReport QL(OWLOntology in) {
        return new OWL2QLProfile().checkOntology(in);
    }

    private OWLProfileReport RL(OWLOntology in) {
        return new OWL2RLProfile().checkOntology(in);
    }

    private OWLProfileReport DL(OWLOntology in) {
        return new OWL2DLProfile().checkOntology(in);
    }

    boolean in(OWLProfile p, String in) {
        return p.checkOntology(o(in)).isInProfile();
    }

    OWLOntology o(String in) {
        try {
            return OWLManager.createOWLOntologyManager()
                    .loadOntologyFromOntologyDocument(
                            new StringDocumentSource(in));
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
        return null;
    }

    void compareOntologies(String in1, String in2) {
        OWLOntology o1 = o(in1);
        OWLOntology o2 = o(in2);
        assertEquals(o1.getAxioms(), o2.getAxioms());
    }

    protected void test(String in, boolean el, boolean ql, boolean rl,
            boolean dl) {
        OWLOntology o = o(in);
        assertTrue("empty ontology", o.getAxioms().size() > 0);
        OWLProfileReport elReport = EL(o);
        assertEquals(elReport.toString(), el, elReport.isInProfile());
        OWLProfileReport qlReport = QL(o);
        assertEquals(qlReport.toString(), ql, qlReport.isInProfile());
        OWLProfileReport rlReport = RL(o);
        assertEquals(rlReport.toString(), rl, rlReport.isInProfile());
        OWLProfileReport dlReport = DL(o);
        assertEquals(dlReport.toString(), dl, dlReport.isInProfile());
    }
}
