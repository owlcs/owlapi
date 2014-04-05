package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
public class SKOSInvalidIRITestCase {

    @Ignore
    @Test
    public void shouldFindAxioms() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<rdf:RDF\n"
                + "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "xmlns:skos=\"http://www.w3.org/2004/02/skos/core#\"\n"
                + "xmlns:dc=\"http://purl.org/dc/elements/1.1#\"\n"
                + ">\n"
                + "<skos:ConceptScheme rdf:about=\"http://www.thesaurus.gc.ca/#CoreSubjectThesaurus\">\n"
                + "<dc:title xml:lang=\"en\">Government of Canada Core Subject Thesaurus</dc:title>\n"
                + "<dc:creator xml:lang=\"en\">Government of Canada</dc:creator>\n"
                + "</skos:ConceptScheme>\n"
                + "\n"
                + "<skos:Concept rdf:about=\"http://www.thesaurus.gc.ca/concept/#Abbreviations\">\n"
                + "<skos:prefLabel>Abbreviations</skos:prefLabel>\n"
                + "<skos:related rdf:resource=\"http://www.thesaurus.gc.ca/#Terminology\"/>\n"
                + "<skos:inScheme rdf:resource=\"http://www.thesaurus.gc.ca/#CoreSubjectThesaurus\"/>\n"
                + "<skos:prefLabel xml:lang=\"fr\">Abr&#233;viation</skos:prefLabel>\n"
                + "</skos:Concept>\n"
                + "<skos:Concept rdf:about=\"http://www.thesaurus.gc.ca/concept/#Aboriginal%20affairs\">\n"
                + "<skos:prefLabel>Aboriginal affairs</skos:prefLabel>\n"
                + "<skos:altLabel>Aboriginal issues</skos:altLabel>\n"
                + "<skos:related rdf:resource=\"http://www.thesaurus.gc.ca/#Aboriginal%20rights\"/>\n"
                + "<skos:related rdf:resource=\"http://www.thesaurus.gc.ca/#Land claims\"/>\n"
                + "<skos:inScheme rdf:resource=\"http://www.thesaurus.gc.ca/#CoreSubjectThesaurus\"/>\n"
                + "<skos:prefLabel xml:lang=\"fr\">Affaires autochtones</skos:prefLabel>\n"
                + "</skos:Concept>\n" + "\n" + "</rdf:RDF>";
        OWLOntology o = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        assertTrue(o.getAxiomCount() > 0);
    }
}
