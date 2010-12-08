package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 24-Dec-2009
 */
public class OntologyVersionIRITestCase extends AbstractRoundTrippingTest {
//
//    @Override
//    protected String getFileName() {
//        return "VersionIRITestCase.rdf";
//    }
//
//    public void testID() {
//        OWLOntology ontology = createOntology();
//        OWLOntology ontologyByID = getManager().getOntology(ontology.getOntologyID());
//        assertNotNull(ontologyByID);
//    }


    @Override
    protected OWLOntology createOntology() throws Exception {
        IRI ontIRI = IRI.create("http://www.semanticweb.org/owlapi/ontology");
        IRI versionIRI = IRI.create("http://www.semanticweb.org/owlapi/ontology/version");
        OWLOntologyID ontologyID = new OWLOntologyID(ontIRI, versionIRI);
        return getManager().createOntology(ontologyID);
    }

//    @Override
//    protected Set<? extends OWLAxiom> createAxioms() {
//        return Collections.singleton(getFactory().getOWLDeclarationAxiom(getOWLClass("A")));
//    }
}


