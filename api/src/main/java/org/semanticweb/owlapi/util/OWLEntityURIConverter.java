package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
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
 * Date: 25-Nov-2007<br><br>
 * <p/>
 * Performs a bulk conversion/translation of entity URIs.  This utility class
 * can be used to replace entity names with IDs for example.  The
 * entity converter is supplied with a set of ontologies and a conversion
 * strategy.  All of the entities that are referenced in the specified
 * ontologies will have their URIs converted according the specified conversion
 * strategy.
 */
public class OWLEntityURIConverter {

    private OWLOntologyManager manager;

    // The ontologies that reference the
    // entities whose names will be converted
    private Collection<OWLOntology> ontologies;

    private Map<OWLEntity, IRI> replacementMap;

    private List<OWLOntologyChange> changes;

    private Set<OWLEntity> processedEntities;

    private OWLEntityURIConverterStrategy strategy;


    /**
     * Creates a converter that will convert the URIs of entities in the specified ontologies
     * using the specified conversion strategy.
     *
     * @param manager    The manager which managers the specified ontologies.
     * @param ontologies The ontologies whose entity URIs will be converted
     * @param strategy   The conversion strategy to be used.
     */
    public OWLEntityURIConverter(OWLOntologyManager manager, Set<OWLOntology> ontologies, OWLEntityURIConverterStrategy strategy) {
        this.manager = manager;
        this.ontologies = new ArrayList<OWLOntology>(ontologies);
        this.strategy = strategy;
    }


    /**
     * Gets the changes required to perform the conversion.
     *
     * @return A list of ontology changes that should be applied in order
     *         to convert the URI of entities in the specified ontologies.
     */
    public List<OWLOntologyChange> getChanges() {
        replacementMap = new HashMap<OWLEntity, IRI>();
        processedEntities = new HashSet<OWLEntity>();
        changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : ontologies) {
            for (OWLClass cls : ont.getClassesInSignature()) {
                if (!cls.isOWLThing() && !cls.isOWLNothing()) {
                    processEntity(cls);
                }
            }
            for (OWLObjectProperty prop : ont.getObjectPropertiesInSignature()) {
                processEntity(prop);
            }
            for (OWLDataProperty prop : ont.getDataPropertiesInSignature()) {
                processEntity(prop);
            }
            for (OWLNamedIndividual ind : ont.getIndividualsInSignature()) {
                processEntity(ind);
            }
        }
        OWLObjectDuplicator dup = new OWLObjectDuplicator(replacementMap, manager.getOWLDataFactory());
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getAxioms()) {
                OWLAxiom dupAx = dup.duplicateObject(ax);
                if (!dupAx.equals(ax)) {
                    changes.add(new RemoveAxiom(ont, ax));
                    changes.add(new AddAxiom(ont, dupAx));
                }
            }
        }
        return changes;
    }

    private void processEntity(OWLEntity ent) {
        if (processedEntities.contains(ent)) {
            return;
        }
        // Add label?
        IRI rep = getTinyURI(ent);
        replacementMap.put(ent, rep);
        processedEntities.add(ent);
    }

    private IRI getTinyURI(OWLEntity ent) {
        return strategy.getConvertedIRI(ent);
    }

}
