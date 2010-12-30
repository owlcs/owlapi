package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 * <p/>
 * Generates an ontology based on inferred axioms which are essentially supplied by a reasoner.
 * The generator can be configured with <code>InferredAxiomGenerator</code>s which generate specific
 * kinds of axioms e.g. subclass axioms.
 */
public class InferredOntologyGenerator {


    // The reasoner which is used to compute the inferred axioms
    private OWLReasoner reasoner;

    private List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators;


    public InferredOntologyGenerator(OWLReasoner reasoner, List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators) {
        this.reasoner = reasoner;
        this.axiomGenerators = axiomGenerators;
    }


    public InferredOntologyGenerator(OWLReasoner reasoner) {
        this.reasoner = reasoner;
        axiomGenerators = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
        axiomGenerators.add(new InferredClassAssertionAxiomGenerator());
        axiomGenerators.add(new InferredDataPropertyCharacteristicAxiomGenerator());
        axiomGenerators.add(new InferredEquivalentClassAxiomGenerator());
        axiomGenerators.add(new InferredEquivalentDataPropertiesAxiomGenerator());
        axiomGenerators.add(new InferredEquivalentObjectPropertyAxiomGenerator());
        axiomGenerators.add(new InferredInverseObjectPropertiesAxiomGenerator());
        axiomGenerators.add(new InferredObjectPropertyCharacteristicAxiomGenerator());
        axiomGenerators.add(new InferredPropertyAssertionGenerator());
        axiomGenerators.add(new InferredSubClassAxiomGenerator());
        axiomGenerators.add(new InferredSubDataPropertyAxiomGenerator());
        axiomGenerators.add(new InferredSubObjectPropertyAxiomGenerator());
    }


    public List<InferredAxiomGenerator<?>> getAxiomGenerators() {
        return new ArrayList<InferredAxiomGenerator<?>>(axiomGenerators);
    }


    /**
     * Adds a generator if it isn't already in the list of generators
     * @param generator The generator to be added.
     */
    public void addGenerator(InferredAxiomGenerator<?> generator) {
        if (!axiomGenerators.contains(generator)) {
            axiomGenerators.add(generator);
        }
    }


    /**
     * Removes a generator
     * @param generator the generator to be removed
     */
    public void removeGenerator(InferredAxiomGenerator<?> generator) {
        axiomGenerators.remove(generator);
    }


    /**
     * Adds 'inferred axioms' to an ontology using the generators that have
     * been registered with this <code>InferredAxiomGenerator</code>
     * @param manager  The manager which can be used to obtain a data factory and
     *                 apply changes.
     * @param ontology The ontology which the inferred axioms will be added to
     * @throws OWLOntologyChangeException If there was a problem adding the inferred
     * axioms to the specified ontology.
     */
    public void fillOntology(OWLOntologyManager manager, OWLOntology ontology) throws OWLOntologyChangeException {
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        for (InferredAxiomGenerator<? extends OWLAxiom> axiomGenerator : axiomGenerators) {
                for (OWLAxiom ax : axiomGenerator.createAxioms(manager, reasoner)) {
                    changes.add(new AddAxiom(ontology, ax));
                }
        }
        manager.applyChanges(changes);
    }
}
