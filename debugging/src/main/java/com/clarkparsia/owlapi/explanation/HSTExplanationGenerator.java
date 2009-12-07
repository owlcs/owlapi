package com.clarkparsia.owlapi.explanation;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLEntityCollector;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.clarkparsia.owlapi.explanation.util.ExplanationProgressMonitor;
import com.clarkparsia.owlapi.explanation.util.SilentExplanationProgressMonitor;
import com.clarkparsia.owlapi.explanation.util.OntologyUtils;

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
 * <p/>
 * Description: An implementation of MultipleExplanationGenerator interface
 * using Reiter's Hitting Set Tree (HST) algorithm as described in Aditya
 * Kalyanpur's thesis. This class relies on a SingleExplanationGenerator that
 * can compute a minimal set of axioms that cause the unsatisfiability. The
 * core of the functionality is based on Matthew Horridge's implementation.
 * </p>
 * <p/>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com>
 * </p>
 *
 * @author Evren Sirin
 */
public class HSTExplanationGenerator implements MultipleExplanationGenerator {

    public static final Logger log = Logger.getLogger(HSTExplanationGenerator.class.getName());

    private TransactionAwareSingleExpGen singleExplanationGenerator;

    private ExplanationProgressMonitor progressMonitor = new SilentExplanationProgressMonitor();

    private static OWLEntityCollector collector;

    public HSTExplanationGenerator(TransactionAwareSingleExpGen singleExplanationGenerator) {
        this.singleExplanationGenerator = singleExplanationGenerator;
        collector = new OWLEntityCollector();
        collector.setCollectDatatypes(false);
    }


    public void setProgressMonitor(ExplanationProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
    }


    public OWLOntologyManager getOntologyManager() {
        return singleExplanationGenerator.getOntologyManager();
    }

    public OWLOntology getOntology() {
        return singleExplanationGenerator.getOntology();
    }

//    public Set<OWLOntology> getOntologies() {
//        return singleExplanationGenerator.getOntologies();
//    }

//    public void setOntology(OWLOntology ontology) {
//        singleExplanationGenerator.setOntology(ontology);
//    }

//    public void setOntologies(Set<OWLOntology> ontologies) {
//        singleExplanationGenerator.setOntologies(ontologies);
//    }

//
    public OWLReasoner getReasoner() {
        return singleExplanationGenerator.getReasoner();
    }
//
//
//    public void setReasoner(OWLReasoner reasoner) {
//        singleExplanationGenerator.setReasoner(reasoner);
//    }
//
//
    public OWLReasonerFactory getReasonerFactory() {
        return singleExplanationGenerator.getReasonerFactory();
    }
//
//
//    public void setReasonerFactory(OWLReasonerFactory reasonerFactory) {
//        singleExplanationGenerator.setReasonerFactory(reasonerFactory);
//    }


    public TransactionAwareSingleExpGen getSingleExplanationGenerator() {
        return singleExplanationGenerator;
    }


    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass) {
        return singleExplanationGenerator.getExplanation(unsatClass);
    }


    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass) {
        return getExplanations(unsatClass, 0);
    }


    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass, int maxExplanations) {
        if (maxExplanations < 0)
            throw new IllegalArgumentException();

        if (log.isLoggable(Level.CONFIG))
            log.config("Get " + (maxExplanations == 0 ? "all" : maxExplanations) + " explanation(s) for: " + unsatClass);

        try {
            Set<OWLAxiom> firstMups = getExplanation(unsatClass);
            if (firstMups.isEmpty()) {
                return Collections.emptySet();
            }
            Set<Set<OWLAxiom>> allMups = new LinkedHashSet<Set<OWLAxiom>>();
            progressMonitor.foundExplanation(firstMups);
            allMups.add(firstMups);
            Set<Set<OWLAxiom>> satPaths = new HashSet<Set<OWLAxiom>>();
            Set<OWLAxiom> currentPathContents = new HashSet<OWLAxiom>();
            singleExplanationGenerator.beginTransaction();
            try {
                constructHittingSetTree(unsatClass, firstMups, allMups, satPaths, currentPathContents, maxExplanations);
            }
            finally {
                singleExplanationGenerator.endTransaction();
            }
            progressMonitor.foundAllExplanations();
            return allMups;
        }
        catch (OWLException e) {
            throw new OWLRuntimeException(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Hitting Set Stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Orders the axioms in a single MUPS by the frequency of which they appear
     * in all MUPS.
     *
     * @param mups The MUPS containing the axioms to be ordered
     * @param allMups The set of all MUPS which is used to calculate the ordering
     */
    private static List<OWLAxiom> getOrderedMUPS(List<OWLAxiom> mups, final Set<Set<OWLAxiom>> allMups) {
        Comparator<OWLAxiom> mupsComparator = new Comparator<OWLAxiom>() {
            public int compare(OWLAxiom o1, OWLAxiom o2) {
                // The axiom that appears in most MUPS has the lowest index
                // in the list
                int occ1 = getOccurrences(o1, allMups);
                int occ2 = getOccurrences(o2, allMups);
                return -occ1 + occ2;
            }
        };
        Collections.sort(mups, mupsComparator);
        return mups;
    }


    /**
     * Given an axiom and a set of axioms this method determines how many sets
     * contain the axiom.
     *
     * @param ax The axiom that will be counted.
     * @param axiomSets The sets to count from
     */
    private static int getOccurrences(OWLAxiom ax, Set<Set<OWLAxiom>> axiomSets) {
        int count = 0;
        for (Set<OWLAxiom> axioms : axiomSets) {
            if (axioms.contains(ax)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the entities referenced in an axiom.
     *
     * @param axiom axiom whose signature is being computed
     * @return the entities referenced in the axiom
     */
    private Set<OWLEntity> getSignature(OWLAxiom axiom) {
        collector.reset();

        axiom.accept(collector);

        return new HashSet<OWLEntity>(collector.getObjects());
    }


    /**
     * This is a recursive method that builds a hitting set tree to obtain all
     * justifications for an unsatisfiable class.
     *
     * @param mups The current justification for the current class. This
     * corresponds to a node in the hitting set tree.
     * @param allMups All of the MUPS that have been found - this set gets populated
     * over the course of the tree building process. Initially this
     * should just contain the first justification
     * @param satPaths Paths that have been completed.
     * @param currentPathContents The contents of the current path. Initially this should be an
     * empty set.
     */
    private void constructHittingSetTree(OWLClassExpression unsatClass, Set<OWLAxiom> mups, Set<Set<OWLAxiom>> allMups, Set<Set<OWLAxiom>> satPaths, Set<OWLAxiom> currentPathContents, int maxExplanations) throws OWLException {

        if (log.isLoggable(Level.FINE))
            log.fine("MUPS " + allMups.size() + ": " + mups);

        if (progressMonitor.isCancelled()) {
            return;
        }

        // We go through the current mups, axiom by axiom, and extend the tree
        // with edges for each axiom
        List<OWLAxiom> orderedMups = getOrderedMUPS(new ArrayList<OWLAxiom>(mups), allMups);

        while (!orderedMups.isEmpty()) {
            if (progressMonitor.isCancelled()) {
                return;
            }
            OWLAxiom axiom = orderedMups.get(0);
            orderedMups.remove(0);
            if (allMups.size() == maxExplanations) {
                if (log.isLoggable(Level.FINE))
                    log.fine("Computed " + maxExplanations + "explanations");
                return;
            }

            if (log.isLoggable(Level.FINE))
                log.fine("Removing axiom: " + axiom + " " + currentPathContents.size() + " more removed: " + currentPathContents);

            // Remove the current axiom from all the ontologies it is included
            // in

            Set<OWLOntology> ontologies = OntologyUtils.removeAxiom(axiom, getReasoner().getRootOntology().getImportsClosure(), getOntologyManager());

            // Removal may have dereferenced some entities, if so declarations are added
            Set<OWLEntity> sig = getSignature(axiom);
            List<OWLDeclarationAxiom> temporaryDeclarations = new ArrayList<OWLDeclarationAxiom>(sig.size());
            for (OWLEntity e : sig) {
                boolean referenced = false;
                for (Iterator<OWLOntology> i = ontologies.iterator(); !referenced && i.hasNext();) {
                    for (Iterator<OWLAxiom> j = i.next().getReferencingAxioms(e).iterator(); !referenced && j.hasNext();) {
                        OWLAxiom a = j.next();
                        referenced = a.isLogicalAxiom() || (a instanceof OWLDeclarationAxiom);
                    }
                }
                if (!referenced) {
                    OWLDeclarationAxiom declaration = getOntologyManager().getOWLDataFactory().getOWLDeclarationAxiom(e);
                    temporaryDeclarations.add(declaration);
                }
            }
            for (OWLDeclarationAxiom decl : temporaryDeclarations) {
                OntologyUtils.addAxiom(decl, getReasoner().getRootOntology().getImportsClosure(), getOntologyManager());
            }

            currentPathContents.add(axiom);

            boolean earlyTermination = false;
            // Early path termination. If our path contents are the superset of
            // the contents of a path then we can terminate here.
            for (Set<OWLAxiom> satPath : satPaths) {
                if (currentPathContents.containsAll(satPath)) {
                    earlyTermination = true;
                    if (log.isLoggable(Level.FINE))
                        log.fine("Stop - satisfiable (early termination)");
                    break;
                }
            }

            if (!earlyTermination) {
                Set<OWLAxiom> newMUPS = null;
                for (Set<OWLAxiom> foundMUPS : allMups) {
                    Set<OWLAxiom> foundMUPSCopy = new HashSet<OWLAxiom>(foundMUPS);
                    foundMUPSCopy.retainAll(currentPathContents);
                    if (foundMUPSCopy.isEmpty()) {
                        newMUPS = foundMUPS;
                        break;
                    }
                }
                if (newMUPS == null) {
                    newMUPS = getExplanation(unsatClass);
                }
                // Generate a new node - i.e. a new justification set
                if (newMUPS.contains(axiom)) {
                    // How can this be the case???
                    throw new OWLRuntimeException("Explanation contains removed axiom: " + axiom);
                }

                if (!newMUPS.isEmpty()) {
                    // Note that getting a previous justification does not mean
                    // we
                    // can stop. stopping here causes some justifications to be
                    // missed
                    allMups.add(newMUPS);
                    progressMonitor.foundExplanation(newMUPS);
                    // Recompute priority here?
                    constructHittingSetTree(unsatClass, newMUPS, allMups, satPaths, currentPathContents, maxExplanations);
                    // We have found a new MUPS, so recalculate the ordering
                    // axioms in the MUPS at the current level
                    orderedMups = getOrderedMUPS(orderedMups, allMups);
                }
                else {
                    if (log.isLoggable(Level.FINE))
                        log.fine("Stop - satisfiable");

                    // End of current path - add it to the list of paths
                    satPaths.add(new HashSet<OWLAxiom>(currentPathContents));
                }
            }

            // Back track - go one level up the tree and run for the next axiom
            currentPathContents.remove(axiom);

            if (log.isLoggable(Level.FINE))
                log.fine("Restoring axiom: " + axiom);

            // Remove any temporary declarations
            for (OWLDeclarationAxiom decl : temporaryDeclarations) {
                OntologyUtils.removeAxiom(decl, getReasoner().getRootOntology().getImportsClosure(), getOntologyManager());
            }

            // Done with the axiom that was removed. Add it back in
            OntologyUtils.addAxiom(axiom, ontologies, getOntologyManager());
        }
    }
}
