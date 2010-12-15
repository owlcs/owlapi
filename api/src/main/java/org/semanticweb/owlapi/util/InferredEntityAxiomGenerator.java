package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 * Generates axioms which relate to inferred information for a specific entity.
 */
public abstract class InferredEntityAxiomGenerator<E extends OWLEntity, A extends OWLAxiom> implements InferredAxiomGenerator<A> {


    public Set<A> createAxioms(OWLOntologyManager manager, OWLReasoner reasoner) {
        Set<E> processedEntities = new HashSet<E>();
        Set<A> result = new HashSet<A>();
        for (OWLOntology ont : reasoner.getRootOntology().getImportsClosure()) {
            for (E entity : getEntities(ont)) {
                if (!processedEntities.contains(entity)) {
                    processedEntities.add(entity);
                    addAxioms(entity, reasoner, manager.getOWLDataFactory(), result);
                }
            }
        }
        return result;
    }


    /**
     * Adds inferred axioms to a results set.  The inferred axioms are generated for the specific entity.
     * @param entity The entity
     * @param reasoner The reasoner that has inferred the new axioms
     * @param dataFactory A data factory which should be used to create the new axioms
     * @param result The results set, which the new axioms should be added to.
     */
    protected abstract void addAxioms(E entity, OWLReasoner reasoner, OWLDataFactory dataFactory, Set<A> result);


    /**
     * Gets the entities from the specified ontology that this generator processes
     * @param ont The ontology from which entities are to be retrieved.
     * @return A set of entities.
     */
    protected abstract Set<E> getEntities(OWLOntology ont);

    protected Set<E> getAllEntities(OWLReasoner reasoner) {
        Set<E> results = new HashSet<E>();
        for (OWLOntology ont : reasoner.getRootOntology().getImportsClosure()) {
            results.addAll(getEntities(ont));
        }
        return results;
    }


    @Override
	public String toString() {
        return getLabel();
    }
}
