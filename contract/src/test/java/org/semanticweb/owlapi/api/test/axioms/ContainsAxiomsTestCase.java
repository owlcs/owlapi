package org.semanticweb.owlapi.api.test.axioms;

import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
public class ContainsAxiomsTestCase {

    @Test
    public void shouldFindallIncluded() throws OWLOntologyCreationException {
        OWLOntology o = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        Set<OWLLogicalAxiom> logAx = o.getLogicalAxioms();
        TreeSet<OWLLogicalAxiom> ts = new TreeSet<OWLLogicalAxiom>(logAx);
        for (OWLLogicalAxiom ax : logAx) {
            assertTrue(ax.toString(), ts.contains(ax));
        }
    }

    String input = "<?xml version=\"1.0\"?>\n"
            + "    <rdf:RDF xmlns=\"file:/Users/seanb/Desktop/Cercedilla2005/hands-on/people.owl#\"\n"
            + "         xml:base=\"file:/Users/seanb/Desktop/Cercedilla2005/hands-on/people.owl\"\n"
            + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
            + "         xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "         xmlns:people=\"http://owl.man.ac.uk/2005/07/sssw/people#\">\n"
            + "        <owl:Ontology rdf:about=\"urn:people.owl\"/>\n"
            + "        <owl:ObjectProperty rdf:about=\"http://owl.man.ac.uk/2005/07/sssw/people#drives\"/>\n"
            + "        <owl:NamedIndividual rdf:about=\"http://owl.man.ac.uk/2005/07/sssw/people#Mick\">\n"
            + "            <people:drives rdf:resource=\"http://owl.man.ac.uk/2005/07/sssw/people#Q123_ABC\"/>\n"
            + "        </owl:NamedIndividual>\n"
            + "        <owl:NamedIndividual rdf:about=\"http://owl.man.ac.uk/2005/07/sssw/people#Q123_ABC\"/></rdf:RDF>";
}
