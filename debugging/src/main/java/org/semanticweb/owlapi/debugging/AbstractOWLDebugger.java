package org.semanticweb.owlapi.debugging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.RemoveAxiom;


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
     * @param mups The current justification for the current class.  This corresponds to a node
     * in the hitting set tree.
     * @param allMups All of the MUPS that have been found - this set gets populated over the course
     * of the tree building process.  Initially this should just contain the first justification
     * @param satPaths Paths that have been completed.
     * @param currentPathContents The contents of the current path.  Initially this should be an
     * empty set.
     */
    public void constructHittingSetTree(Set<OWLAxiom> mups, Set<Set<OWLAxiom>> allMups, Set<Set<OWLAxiom>> satPaths, Set<OWLAxiom> currentPathContents) throws OWLException {

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
