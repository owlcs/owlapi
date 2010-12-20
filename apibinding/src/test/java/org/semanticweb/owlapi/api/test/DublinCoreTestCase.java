package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 */
public class DublinCoreTestCase extends AbstractFileTestCase {

    @Override
    protected String getFileName() {
        return "dublincore.rdf";
    }

    public void testAnnotationProperties() {
        OWLOntology ontology = createOntology();
        System.out.println("Annotation properties ----------------------------------------------------------");
        for(OWLAnnotationProperty property : ontology.getAnnotationPropertiesInSignature()) {
            System.out.println(property);
        }
        System.out.println("Object properties --------------------------------------------------------------");
        for(OWLObjectProperty property : ontology.getObjectPropertiesInSignature()) {
            System.out.println(property);
            for(OWLAxiom ax : ontology.getReferencingAxioms(property)) {
                System.out.println("\t" + ax);
            }
        }

        for(DublinCoreVocabulary vocabulary : DublinCoreVocabulary.values()) {
            assertTrue(ontology.containsAnnotationPropertyInSignature(vocabulary.getIRI()));
        }
    }
}
