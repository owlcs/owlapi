package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.net.URI;
import java.util.*;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 11-Dec-2006<br><br>
 * <p/>
 * Renames entities that have a particular URI.  Entities with the specified
 * URI are renamed regardless of whether they are classes, object properties,
 * data properties, individuals or data types.
 */
public class OWLEntityRenamer {

    private OWLOntologyManager owlOntologyManager;

    private Set<OWLOntology> ontologies;


    public OWLEntityRenamer(OWLOntologyManager owlOntologyManager, Set<OWLOntology> ontologies) {
        this.owlOntologyManager = owlOntologyManager;
        this.ontologies = ontologies;
    }


    /**
     * Changes a URI for another URI.  This creates the appropriate changes to be
     * applied in order to change a URI.
     * @param uri    The URI to be changed
     * @param newURI The URI that the URI should be changed to.
     * @return A list of ontology changes that should be applied to change the
     * specified URI.
     */
    public List<OWLOntologyChange> changeURI(URI uri, URI newURI) {
        Map<URI, URI> uriMap = new HashMap<URI, URI>();
        uriMap.put(uri, newURI);
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : ontologies) {
            OWLObjectDuplicator dup = new OWLObjectDuplicator(owlOntologyManager.getOWLDataFactory(), uriMap);
            fillListWithTransformChanges(changes, getAxioms(ont, uri), ont, dup);
        }
        return changes;
    }


    /**
     * Changes the URI of an entity for another URI.
     * @param entity The entity whose URI is to be changed.
     * @param newURI The new URI
     * @return A list of ontology changes that should be applied to change the
     * specified entity URI.
     */
    public List<OWLOntologyChange> changeURI(OWLEntity entity, URI newURI) {
        Map<OWLEntity, URI> uriMap = new HashMap<OWLEntity, URI>();
        uriMap.put(entity, newURI);
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        for(OWLOntology ont : ontologies) {
            OWLObjectDuplicator duplicator = new OWLObjectDuplicator(uriMap, owlOntologyManager.getOWLDataFactory());
            fillListWithTransformChanges(changes, getAxioms(ont, entity), ont, duplicator);
        }
        return changes;
    }

    public List<OWLOntologyChange> changeURI(Map<OWLEntity, URI> entity2URIMap) {
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        for(OWLOntology ont : ontologies) {
            for(OWLEntity ent : entity2URIMap.keySet()) {
                OWLObjectDuplicator duplicator = new OWLObjectDuplicator(entity2URIMap, owlOntologyManager.getOWLDataFactory());
                fillListWithTransformChanges(changes, getAxioms(ont, ent), ont, duplicator);
            }
        }
        return changes;
    }

    private static Set<OWLAxiom> getAxioms(OWLOntology ont, OWLEntity entity) {
        return ont.getReferencingAxioms(entity);
    }

    private Set<OWLAxiom> getAxioms(OWLOntology ont, URI uri) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(ont.getReferencingAxioms(owlOntologyManager.getOWLDataFactory().getOWLClass(uri)));
        axioms.addAll(ont.getReferencingAxioms(owlOntologyManager.getOWLDataFactory().getOWLObjectProperty(uri)));
        axioms.addAll(ont.getReferencingAxioms(owlOntologyManager.getOWLDataFactory().getOWLDataProperty(uri)));
        axioms.addAll(ont.getReferencingAxioms(owlOntologyManager.getOWLDataFactory().getOWLIndividual(uri)));
        axioms.addAll(ont.getReferencingAxioms(owlOntologyManager.getOWLDataFactory().getOWLDataType(uri)));
        return axioms;
    }


    /**
     * Fills a list with ontology changes which will replace a set of axioms with
     * duplicated/transformed axioms.
     * @param changes A list that will be filled with ontology changes which will remove the
     * specified axioms from the specified ontology, and add the duplicated/transformed version
     * @param axioms The axioms to be duplicated/transformed
     * @param ont The ontology to which the changed should be applied
     * @param duplicator The duplicator that will do the duplicating
     */
    private static void fillListWithTransformChanges(List<OWLOntologyChange> changes, Set<OWLAxiom> axioms, OWLOntology ont,
                                                     OWLObjectDuplicator duplicator) {
        for(OWLAxiom ax : axioms) {
            changes.add(new RemoveAxiom(ont, ax));
            OWLAxiom dupAx = duplicator.duplicateObject(ax);
            changes.add(new AddAxiom(ont, dupAx));
        }
    }

    
}
