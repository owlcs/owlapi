package com.clarkparsia.owlapi.explanation.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;


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
        for (OWLEntity entity : desc.getSignature()) {
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
     * @param axiom axiom being removed
     * @param ontologies ontologies from which axiom is being removed
     * @param manager manager to apply the actual change
     * @return set of ontologies that have been affected
     */
    public static Set<OWLOntology> removeAxiom(OWLAxiom axiom, Set<OWLOntology> ontologies, OWLOntologyManager manager) {
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
