package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerException;

import java.util.HashSet;
import java.util.Set;
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
 * Date: 27-Jul-2007<br><br>
 *
 * Generates axioms which relate to inferred information for a specific entity.
 */
public abstract class InferredEntityAxiomGenerator<E extends OWLEntity, A extends OWLAxiom> implements InferredAxiomGenerator<A> {


    public Set<A> createAxioms(OWLOntologyManager manager, OWLReasoner reasoner) {
        Set<E> processedEntities = new HashSet<E>();
        Set<A> result = new HashSet<A>();
        for(OWLOntology ont : reasoner.getRootOntology().getImportsClosure()) {
            for(E entity : getEntities(ont)) {
                if(!processedEntities.contains(entity)) {
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
        for(OWLOntology ont : reasoner.getRootOntology().getImportsClosure()) {
            results.addAll(getEntities(ont));
        }
        return results;
    }


    public String toString() {
        return getLabel();
    }
}
