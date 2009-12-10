package com.clarkparsia.owlapi.explanation.util;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLEntityCollector;

import java.util.HashSet;
import java.util.Set;
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
 * Copyright: Copyright (c) 2007
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 * @author Evren Sirin
 */
public class OntologyUtils {

    /**
     * Determines if a class description contains any unreferenced entities with
     * respect to the ontology that contains the entailments which are being
     * explained.
     * @param desc The description to be searched
     * @return <code>true</code> if the description references entities that
     *         the ontology that contains entailments which are being explained,
     *         otherwise <code>false</code>
     */
    public static boolean containsUnreferencedEntity(OWLOntology ontology, OWLClassExpression desc) {
        OWLEntityCollector entityCollector = new OWLEntityCollector();
        desc.accept(entityCollector);
        for (OWLEntity entity : entityCollector.getObjects()) {
            if (!ontology.containsEntityInSignature(entity)) {
                if (entity instanceof OWLClass && (((OWLClass) entity).isOWLThing() || ((OWLClass) entity).isOWLNothing())) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }


    /**
     * Removes an axiom from all the given ontologies that contains the axiom
     * and returns those ontologies.
     * @param axiom      axiom being removed
     * @param ontologies ontologies from which axiom is being removed
     * @param manager    manager to apply the actual change
     * @return set of ontologies that have been affected
     */
    public static Set<OWLOntology> removeAxiom(OWLAxiom axiom, Set<OWLOntology> ontologies,
                                               OWLOntologyManager manager) {
        Set<OWLOntology> modifiedOnts = new HashSet<OWLOntology>();

        for (OWLOntology ont : ontologies) {
            if (ont.getAxioms().contains(axiom)) {
                modifiedOnts.add(ont);

                manager.applyChange(new RemoveAxiom(ont, axiom));
            }
        }

        return modifiedOnts;
    }


    /**
     * Add the axiom to all the given ontologies.
     */
    public static void addAxiom(OWLAxiom axiom, Set<OWLOntology> ontologies, OWLOntologyManager manager) {
        for (OWLOntology ont : ontologies) {
            manager.applyChange(new AddAxiom(ont, axiom));
        }
    }
}
