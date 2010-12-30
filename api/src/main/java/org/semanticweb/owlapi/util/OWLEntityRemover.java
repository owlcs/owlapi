package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 * <p/>
 * A convenience object that generates the changes which are necessary to
 * remove an entity from a set of ontologies.  This is accomplished by removing
 * all axioms that refer to the entity.  The entity remover follows the visitor
 * design pattern, entities that need to be removed from an ontology should
 * accept visits from the entity remover.  Changes are accumulated as the entity
 * remover visits various entities.
 */
public class OWLEntityRemover implements OWLEntityVisitor {

    private List<OWLOntologyChange> changes;

    private Collection<OWLOntology> ontologies;


    /**
     * Creates an entity remover, which will remove entities (axioms referring to the entities
     * from the specified ontologies).
     *
     * @param owlOntologyManager The <code>OWLOntologyManager</code> which contains the ontologies
     *                           that contain entities to be removed.
     * @param ontologies         The set of ontologies that contain references to axioms to be removed.
     */  @SuppressWarnings("unused")
    public OWLEntityRemover(OWLOntologyManager owlOntologyManager, Set<OWLOntology> ontologies) {
        changes = new ArrayList<OWLOntologyChange>();
        this.ontologies = new ArrayList<OWLOntology>(ontologies);
    }


    /**
     * Gets the list of ontology changes that are required in order to remove
     * visited entities from the set of ontologies.
     */
    public List<OWLOntologyChange> getChanges() {
        return new ArrayList<OWLOntologyChange>(changes);
    }


    /**
     * Clears any changes which have accumulated over the course of visiting
     * different entities.
     */
    public void reset() {
        changes.clear();
    }

    private void generateChanges(OWLEntity entity) {
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getReferencingAxioms(entity)) {
                changes.add(new RemoveAxiom(ont, ax));
            }
            for(OWLAnnotationAssertionAxiom ax : ont.getAnnotationAssertionAxioms(entity.getIRI())) {
                changes.add(new RemoveAxiom(ont, ax));
            }
        }
    }

    public void visit(OWLClass cls) {
        generateChanges(cls);
    }


    public void visit(OWLDatatype datatype) {
        generateChanges(datatype);
    }


    public void visit(OWLNamedIndividual individual) {
        generateChanges(individual);
    }


    public void visit(OWLDataProperty property) {
        generateChanges(property);
    }


    public void visit(OWLObjectProperty property) {
        generateChanges(property);
    }

    public void visit(OWLAnnotationProperty property) {
        generateChanges(property);
    }
}
