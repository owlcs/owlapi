package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.util.*;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Date: 30-May-2008<br><br>
 *
 * Sorts objects into sets based on where they appear in the imports closure of an ontology.
 * Consider ontology B that imports ontology A.  A map will
 * be generated that maps each ontology, A, B, to a set of objects that are associated with
 * the ontology.  If an object is associated with ontology A and associated with ontology B
 * then it will only appear in the set of objects that are associated with ontology A since
 * A appears higher up the imports closure.  An example of the use of this class is to
 * obtain a map of ontologies to sets of entities where each set of entities contains entities
 * that are first mentioned in the ontology that maps to them. 
 * 
 * @see org.semanticweb.owlapi.util.ImportsStructureEntitySorter
 */
public class ImportsStructureObjectSorter<O> {

    private OWLOntology ontology;

    private OWLOntologyManager manager;

    private ObjectSelector<O> objectSelector;


    /**
     * Creates a sorter for the specified ontology, whose imports closure is obtained
     * with the specified manager, and for each ontology whose objects are selected
     * using the specified object selector.
     * @param ontology The ontology
     * @param manager The manager that will be used to obtain the imports closure
     * @param objectSelector The selector that will be used to select objects that
     * are associated with each ontology.
     */
    public ImportsStructureObjectSorter(OWLOntology ontology, OWLOntologyManager manager,
                                        ObjectSelector<O> objectSelector) {
        this.ontology = ontology;
        this.manager = manager;
        this.objectSelector = objectSelector;
    }


    /**
     * Gets a map that maps ontologies to sets of associated objects.  The ontologies
     * will be the ontologies that are contained in the imports closure of the original
     * specified ontology.
     * @return The map.
     */
    public Map<OWLOntology, Set<O>> getObjects() {
        List<OWLOntology> imports = manager.getSortedImportsClosure(ontology);
        Map<OWLOntology, Set<O>> ontology2EntityMap = new HashMap<OWLOntology, Set<O>>();
        Set<O> processed = new HashSet<O>();
        for(int i = imports.size() - 1; i > -1; i--) {
            OWLOntology currentOnt = imports.get(i);
            Set<O> objects = new HashSet<O>();
            for(O obj : objectSelector.getObjects(currentOnt)) {
                if(!processed.contains(obj)) {
                    processed.add(obj);
                    objects.add(obj);
                }
            }
            ontology2EntityMap.put(currentOnt, objects);
        }
        return ontology2EntityMap;
    }


    public static interface ObjectSelector<O> {

        public Set<O> getObjects(OWLOntology ontology);
    }


}
