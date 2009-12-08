package org.semanticweb.owlapi.debugging;

import org.semanticweb.owlapi.model.*;

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
 * Date: 24-Nov-2006<br><br>
 * <p/>
 * An abstract debugger which provides common infrastructure for finding
 * multiple justification.  This functionality relies on a concrete implementation
 * of a debugger that can compute a minimal set of axioms that cause the unsatisfiability.
 */
public abstract class AbstractOWLDebugger implements OWLDebugger {

    private OWLOntologyManager owlOntologyManager;

    private OWLOntology ontology;


    protected AbstractOWLDebugger(OWLOntologyManager owlOntologyManager, OWLOntology ontology) {
        this.owlOntologyManager = owlOntologyManager;
        this.ontology = ontology;
        mergeImportsClosure();
    }


    private void mergeImportsClosure() {
        OWLOntology originalOntology = ontology;
        try {
            ontology = owlOntologyManager.createOntology(IRI.create("http://debugger.semanticweb.org/ontolog" + System.nanoTime()));
        }
        catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(originalOntology)) {
            for (OWLAxiom ax : ont.getLogicalAxioms()) {
                changes.add(new AddAxiom(ontology, ax));
            }
        }
            owlOntologyManager.applyChanges(changes);
    }


    protected abstract OWLClassExpression getCurrentClass() throws OWLException;


    public OWLOntology getOWLOntology() throws OWLException {
        return ontology;
    }


    public OWLOntologyManager getOWLOntologyManager() throws OWLException {
        return owlOntologyManager;
    }


    public Set<Set<OWLAxiom>> getAllSOSForIncosistentClass(OWLClassExpression cls) throws OWLException {
        Set<OWLAxiom> firstMups = getSOSForIncosistentClass(cls);
        if (firstMups.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Set<OWLAxiom>> allMups = new HashSet<Set<OWLAxiom>>();
        allMups.add(firstMups);
        Set<Set<OWLAxiom>> satPaths = new HashSet<Set<OWLAxiom>>();
        Set<OWLAxiom> currentPathContents = new HashSet<OWLAxiom>();
        constructHittingSetTree(firstMups, allMups, satPaths, currentPathContents);
        return allMups;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Hitting Set Stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////


    /**
     * This is a recursive method that builds a hitting set tree to obtain all justifications
     * for an unsatisfiable class.
     * @param mups                The current justification for the current class.  This corresponds to a node
     *                            in the hitting set tree.
     * @param allMups             All of the MUPS that have been found - this set gets populated over the course
     *                            of the tree building process.  Initially this should just contain the first justification
     * @param satPaths            Paths that have been completed.
     * @param currentPathContents The contents of the current path.  Initially this should be an
     *                            empty set.
     */
    public void constructHittingSetTree(Set<OWLAxiom> mups, Set<Set<OWLAxiom>> allMups, Set<Set<OWLAxiom>> satPaths,
                                        Set<OWLAxiom> currentPathContents) throws OWLException {

        // We go through the current mups, axiom by axiom, and extend the tree
        // with edges for each axiom
        for (OWLAxiom axiom : mups) {
            // Remove the current axiom from the ontology
            owlOntologyManager.applyChanges(Arrays.asList(new RemoveAxiom(ontology, axiom)));
            currentPathContents.add(axiom);

            boolean earlyTermination = false;
            // Early path termination.  If our path contents are the superset of
            // the contents of a path then we can terminate here.
            for (Set<OWLAxiom> satPath : satPaths) {
                if (satPath.containsAll(currentPathContents)) {
                    earlyTermination = true;
                    break;
                }
            }

            if (!earlyTermination) {
                // Generate a new node - i.e. a new justification set
//                generateSOSAxioms();
                Set<OWLAxiom> newMUPS = getSOSForIncosistentClass(getCurrentClass());

                if (!newMUPS.isEmpty()) {
                    // We have a new justification set, and a new node
                    if (!allMups.contains(newMUPS)) {
                        // Entirely new justification set
                        allMups.add(newMUPS);
                        constructHittingSetTree(newMUPS, allMups, satPaths, currentPathContents);
                    }
                }
                else {
                    // End of current path - add it to the list of paths
                    satPaths.add(new HashSet<OWLAxiom>(currentPathContents));
                }
            }
            // Back track - go one level up the tree and run for the next axiom
            currentPathContents.remove(axiom);
            // Done with the axiom that was removed. Add it back in
            owlOntologyManager.applyChanges(Arrays.asList(new AddAxiom(ontology, axiom)));
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Stats stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

}
