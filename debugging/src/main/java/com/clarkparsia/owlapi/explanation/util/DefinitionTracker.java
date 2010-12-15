package com.clarkparsia.owlapi.explanation.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;

/*
* Copyright (C) 2007, Clark & Parsia
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
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: This class tracks the usage of named entities in the imports
 * closure of a designated ontology. This class listens to the changes
 * broadcasted by the ontology manager to update the usage statistics for
 * entities automatically when a change occurs in one of the ontologies.
 * <p>
 * <bold>WARNING:</bold> This class is not appropriate for general use because
 * it does not distinguish multiple occurrences of the same axiom in separate
 * ontologies. If the exact same axiom exists in two separate ontologies of the
 * imports closure, removing the axiom from one ontology would not remove the
 * axiom from the imports closure. However, when such a change occurs the
 * DefinitionTracker will treat as if the axiom is removed completely from the
 * imports closure. This is not important for generating explanation because
 * when we remove an axiom (during multiple explanation generation or blackbox
 * explanation) we remove it from all the ontologies in the imports closure.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * 
 * @author Evren Sirin
 */
public class DefinitionTracker implements OWLOntologyChangeListener {

    /**
     * Mapping from entities to the number of axioms
     */
    private Map<OWLEntity, Integer> referenceCounts = new HashMap<OWLEntity, Integer>();

    private OWLOntology ontology;

	private Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();

    private OWLOntologyManager manager;

    private Integer ONE = Integer.valueOf(1);


    public DefinitionTracker(OWLOntology ontology) {
        this.manager = ontology.getOWLOntologyManager();
        this.ontology = ontology;
        for (OWLOntology ont : ontology.getImportsClosure()) {
			for (OWLOntology importOnt : manager.getImportsClosure(ont)) {
//	            if (this.ontologies.add(importOnt)) {
					for (OWLAxiom axiom : importOnt.getAxioms()) {
		                addAxiom(axiom);
					}
//	            }
	        }
		}
        manager.addOntologyChangeListener(this);
    }
    
//    private void clear() {
//        axioms.clear();
//        ontologies.clear();
//        referenceCounts.clear();
//    }

	private void addAxiom(OWLAxiom axiom) {
        if (axioms.add(axiom)) {
            for (OWLEntity entity : getEntities(axiom)) {
                Integer count = referenceCounts.get(entity);
                if (count == null)
                    count = ONE;
                else
                    count = count + 1;
                referenceCounts.put(entity, count);
            }
        }
    }


    private Set<OWLEntity> getEntities(OWLObject obj) {
    	return obj.getSignature();
    }

	private void removeAxiom(OWLAxiom axiom) {
        if (axioms.remove(axiom)) {
            for (OWLEntity entity : getEntities(axiom)) {
                Integer count = referenceCounts.get(entity);
                if (count == 1)
                    referenceCounts.remove(entity);
                else
                    referenceCounts.put(entity, count - 1);
            }
        }
    }

//	public void setOntology(OWLOntology ontology) {
//		setOntologies( Collections.singleton( ontology ) );
//	}

//	public void setOntologies(Set<OWLOntology> ontologies) {
//		clear();
//		for (OWLOntology ont : ontologies) {
//			for (OWLOntology importOnt : manager.getImportsClosure(ont)) {
//	            if (this.ontologies.add(importOnt)) {
//					for (OWLAxiom axiom : importOnt.getAxioms()) {
//		                addAxiom(axiom);
//					}
//	            }
//	        }
//		}
//	}

//    public Set<OWLOntology> getOntologies() {
//    	return ontologies;
//    }

    /**
     * Checks if this entity is referred by a logical axiom in the imports
     * closure of the designated ontology.
     * @param entity entity we are searching for
     * @return <code>true</code> if there is at least one logical axiom in the
     *         imports closure of the given ontology that refers the given
     *         entity
     */
    public boolean isDefined(OWLEntity entity) {
        return entity.isBuiltIn() || referenceCounts.containsKey(entity);
    }


    /**
     * Checks if all the entities referred in the given concept are also
     * referred by a logical axiom in the imports closure of the designated
     * ontology.
     * @param classExpression description that contains the entities we are searching for
     * @return <code>true</code> if all the entities in the given description
     *         are referred by at least one logical axiom in the imports closure
     *         of the given ontology
     */
    public boolean isDefined(OWLClassExpression classExpression) {
        for (OWLEntity entity : getEntities(classExpression)) {
            if (!isDefined(entity))
                return false;
        }
        return true;
    }


    public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
        for (OWLOntologyChange change : changes) {
            if (!change.isAxiomChange() || !ontology.getImportsClosure().contains(change.getOntology()))
                continue;

			final OWLAxiom axiom = change.getAxiom();

            if (change instanceof AddAxiom)
				addAxiom( axiom );
            else if (change instanceof RemoveAxiom)
				removeAxiom( axiom );
            else
                throw new UnsupportedOperationException("Unrecognized axiom change: " + change);
        }
    }
}
