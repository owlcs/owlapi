package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class StructuralReasonerRecursionTestCase extends AbstractFileTestCase {

    @Override
    protected String getFileName() {
        return "koala.owl";
    }

    public void testRecusion() {
        try {
            OWLOntology ontology = createOntology();
            String ontName = ontology.getOntologyID().getOntologyIRI().toString();
            OWLDataFactory factory = getFactory();
            StructuralReasoner reasoner = new StructuralReasoner(ontology, new SimpleConfiguration(), BufferingMode.BUFFERING);
            OWLClass cls = factory.getOWLClass(IRI.create(ontName + "#Koala"));
            NodeSet<OWLClass> subClasses = reasoner.getSubClasses((OWLClassExpression) cls, false);
            NodeSet<OWLClass> superClasses = reasoner.getSuperClasses((OWLClassExpression) cls, false);
        }
        catch (RuntimeException e) {
            fail(e.getMessage());
        }
    }
}
