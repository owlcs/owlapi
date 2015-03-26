package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;

@SuppressWarnings("javadoc")
public class RemapperTestCase extends TestBase {

    @Test
    public void shouldRemapImport() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n"
                + "<Ontology  ontologyIRI=\"http://protege.org/ontologies/TestFunnyPizzaImport.owl\">\n"
                + "    <Import>http://test.org/TestPizzaImport.owl</Import>\n"
                + "</Ontology>";
        IRI testImport = IRI.create("http://test.org/TestPizzaImport.owl");
        IRI remap = IRI.create("urn:test:mockImport");
        
        StringDocumentSource source = new StringDocumentSource(input);
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().add(mock);
      
        m.createOntology(remap);
        OWLOntology o = m.loadOntologyFromOntologyDocument(source);
        assertEquals(1, o.getImportsDeclarations().size());
        verify(mock).getDocumentIRI(testImport);
          }

    @Test
    public void shouldRemapImportRdfXML() throws OWLOntologyCreationException {
        String input = "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF xmlns=\"urn:test#\"\n"
                + "     xml:base=\"urn:test\"\n"
                + "     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
                + "    <owl:Ontology rdf:about=\"urn:test\">\n"
                + "        <owl:imports rdf:resource=\"http://test.org/TestPizzaImport.owl\"/>\n"
                + "    </owl:Ontology>\n" + "</rdf:RDF>";
        IRI testImport = IRI.create("http://test.org/TestPizzaImport.owl");
        IRI remap = IRI.create("urn:test:mockImport");
        
        StringDocumentSource source = new StringDocumentSource(input);
        OWLOntologyIRIMapper mock = mock(OWLOntologyIRIMapper.class);
        when(mock.getDocumentIRI(eq(testImport))).thenReturn(remap);
        m.getIRIMappers().add(mock);
      
        m.createOntology(remap);
        OWLOntology o = m.loadOntologyFromOntologyDocument(source);
        assertEquals(1, o.getImportsDeclarations().size());
        verify(mock).getDocumentIRI(testImport);
         }
}
