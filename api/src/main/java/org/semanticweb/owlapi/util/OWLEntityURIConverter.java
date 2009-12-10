package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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
    private Set<OWLOntology> ontologies;

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
        this.ontologies = new HashSet<OWLOntology>(ontologies);
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
