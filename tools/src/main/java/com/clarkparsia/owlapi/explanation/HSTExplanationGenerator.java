/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package com.clarkparsia.owlapi.explanation;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAPIPreconditions;
import org.semanticweb.owlapi.util.OWLEntityCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clarkparsia.owlapi.explanation.util.ExplanationProgressMonitor;
import com.clarkparsia.owlapi.explanation.util.OntologyUtils;
import com.clarkparsia.owlapi.explanation.util.SilentExplanationProgressMonitor;

/** HST explanation generator. */
public class HSTExplanationGenerator implements MultipleExplanationGenerator {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HSTExplanationGenerator.class);
    @Nonnull
    private final TransactionAwareSingleExpGen singleExplanationGenerator;
    @Nonnull
    private ExplanationProgressMonitor progressMonitor = new SilentExplanationProgressMonitor();

    /**
     * Instantiates a new hST explanation generator.
     * 
     * @param singleExplanationGenerator
     *        explanation generator to use
     */
    public HSTExplanationGenerator(
            @Nonnull TransactionAwareSingleExpGen singleExplanationGenerator) {
        this.singleExplanationGenerator = checkNotNull(
                singleExplanationGenerator,
                "singleExplanationGenerator cannot be null");
    }

    @Override
    public void setProgressMonitor(ExplanationProgressMonitor progressMonitor) {
        this.progressMonitor = checkNotNull(progressMonitor,
                "progressMonitor cannot be null");
    }

    @Override
    public OWLOntologyManager getOntologyManager() {
        return singleExplanationGenerator.getOntologyManager();
    }

    @Override
    public OWLOntology getOntology() {
        return singleExplanationGenerator.getOntology();
    }

    @Override
    public OWLReasoner getReasoner() {
        return singleExplanationGenerator.getReasoner();
    }

    @Override
    public OWLReasonerFactory getReasonerFactory() {
        return singleExplanationGenerator.getReasonerFactory();
    }

    /**
     * Gets the single explanation generator.
     * 
     * @return the explanation generator
     */
    @Nonnull
    public TransactionAwareSingleExpGen getSingleExplanationGenerator() {
        return singleExplanationGenerator;
    }

    @Override
    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass) {
        return singleExplanationGenerator.getExplanation(unsatClass);
    }

    @Override
    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass) {
        return getExplanations(unsatClass, 0);
    }

    @Override
    public void dispose() {
        singleExplanationGenerator.dispose();
    }

    @Override
    public Set<Set<OWLAxiom>> getExplanations(OWLClassExpression unsatClass,
            @Nonnegative int maxExplanations) {
        OWLAPIPreconditions.checkNotNegative(maxExplanations,
                "max explanations cannot be negative");
        Object max = maxExplanations == 0 ? "all" : maxExplanations;
        LOGGER.info("Get {} explanation(s) for: {}", max, unsatClass);
        try {
            Set<OWLAxiom> firstMups = getExplanation(unsatClass);
            if (firstMups.isEmpty()) {
                return CollectionFactory.emptySet();
            }
            Set<Set<OWLAxiom>> allMups = new LinkedHashSet<>();
            progressMonitor.foundExplanation(firstMups);
            allMups.add(firstMups);
            Set<Set<OWLAxiom>> satPaths = new HashSet<>();
            Set<OWLAxiom> currentPathContents = new HashSet<>();
            singleExplanationGenerator.beginTransaction();
            try {
                constructHittingSetTree(unsatClass, firstMups, allMups,
                        satPaths, currentPathContents, maxExplanations);
            } finally {
                singleExplanationGenerator.endTransaction();
            }
            progressMonitor.foundAllExplanations();
            return allMups;
        } catch (OWLException e) {
            throw new OWLRuntimeException(e);
        }
    }

    // Hitting Set Stuff
    /**
     * Orders the axioms in a single MUPS by the frequency of which they appear
     * in all MUPS.
     * 
     * @param mups
     *        The MUPS containing the axioms to be ordered
     * @param allMups
     *        The set of all MUPS which is used to calculate the ordering
     * @return the ordered mups
     */
    @Nonnull
    private static List<OWLAxiom> getOrderedMUPS(@Nonnull List<OWLAxiom> mups,
            @Nonnull final Set<Set<OWLAxiom>> allMups) {
        Comparator<OWLAxiom> mupsComparator = new Comparator<OWLAxiom>() {

            @Override
            public int compare(OWLAxiom o1, OWLAxiom o2) {
                // The axiom that appears in most MUPS has the lowest index
                // in the list
                assert o1 != null;
                assert o2 != null;
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
     * @param ax
     *        The axiom that will be counted.
     * @param axiomSets
     *        The sets to count from
     * @return the occurrences
     */
    protected static int getOccurrences(@Nonnull OWLAxiom ax,
            @Nonnull Set<Set<OWLAxiom>> axiomSets) {
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
     * @param axiom
     *        axiom whose signature is being computed
     * @return the entities referenced in the axiom
     */
    @Nonnull
    private static Set<OWLEntity> getSignature(@Nonnull OWLAxiom axiom) {
        Set<OWLEntity> toReturn = new HashSet<>();
        OWLEntityCollector collector = new OWLEntityCollector(toReturn);
        axiom.accept(collector);
        return toReturn;
    }

    /**
     * This is a recursive method that builds a hitting set tree to obtain all
     * justifications for an unsatisfiable class.
     * 
     * @param unsatClass
     *        the unsat class
     * @param mups
     *        The current justification for the current class. This corresponds
     *        to a node in the hitting set tree.
     * @param allMups
     *        All of the MUPS that have been found - this set gets populated
     *        over the course of the tree building process. Initially this
     *        should just contain the first justification
     * @param satPaths
     *        Paths that have been completed.
     * @param currentPathContents
     *        The contents of the current path. Initially this should be an
     *        empty set.
     * @param maxExplanations
     *        the max explanations
     * @throws OWLException
     *         the oWL exception
     */
    private void constructHittingSetTree(
            @Nonnull OWLClassExpression unsatClass,
            @Nonnull Set<OWLAxiom> mups, @Nonnull Set<Set<OWLAxiom>> allMups,
            @Nonnull Set<Set<OWLAxiom>> satPaths,
            @Nonnull Set<OWLAxiom> currentPathContents, int maxExplanations)
            throws OWLException {
        LOGGER.info("MUPS {}: {}", allMups.size(), mups);
        if (progressMonitor.isCancelled()) {
            return;
        }
        // We go through the current mups, axiom by axiom, and extend the tree
        // with edges for each axiom
        List<OWLAxiom> orderedMups = getOrderedMUPS(new ArrayList<>(mups),
                allMups);
        while (!orderedMups.isEmpty()) {
            if (progressMonitor.isCancelled()) {
                return;
            }
            OWLAxiom axiom = orderedMups.get(0);
            assert axiom != null;
            orderedMups.remove(0);
            if (allMups.size() == maxExplanations) {
                LOGGER.info("Computed {} explanations", maxExplanations);
                return;
            }
            LOGGER.info("Removing axiom: {} {} more removed: {}", axiom,
                    currentPathContents.size(), currentPathContents);
            // Removal may have dereferenced some entities, if so declarations
            // are added
            List<OWLDeclarationAxiom> temporaryDeclarations = new ArrayList<>();
            Set<OWLOntology> ontologies = removeAxiomAndAddDeclarations(axiom,
                    temporaryDeclarations);
            currentPathContents.add(axiom);
            boolean earlyTermination = checkEarlyTermination(satPaths,
                    currentPathContents);
            if (!earlyTermination) {
                orderedMups = recurse(unsatClass, allMups, satPaths,
                        currentPathContents, maxExplanations, orderedMups,
                        axiom);
            }
            backtrack(currentPathContents, axiom, temporaryDeclarations,
                    ontologies);
        }
    }

    /**
     * Check early termination.
     * 
     * @param satPaths
     *        the sat paths
     * @param currentPathContents
     *        the current path contents
     * @return true, if successful
     */
    private static boolean checkEarlyTermination(
            @Nonnull Set<Set<OWLAxiom>> satPaths,
            @Nonnull Set<OWLAxiom> currentPathContents) {
        boolean earlyTermination = false;
        // Early path termination. If our path contents are the superset of
        // the contents of a path then we can terminate here.
        for (Set<OWLAxiom> satPath : satPaths) {
            if (currentPathContents.containsAll(satPath)) {
                earlyTermination = true;
                LOGGER.info("Stop - satisfiable (early termination)");
                break;
            }
        }
        return earlyTermination;
    }

    /**
     * Recurse.
     * 
     * @param unsatClass
     *        the unsat class
     * @param allMups
     *        the all mups
     * @param satPaths
     *        the sat paths
     * @param currentPathContents
     *        the current path contents
     * @param maxExplanations
     *        the max explanations
     * @param orderedMups
     *        the ordered mups
     * @param axiom
     *        the axiom
     * @return the list
     * @throws OWLException
     *         the oWL exception
     */
    @Nonnull
    private List<OWLAxiom> recurse(@Nonnull OWLClassExpression unsatClass,
            @Nonnull Set<Set<OWLAxiom>> allMups,
            @Nonnull Set<Set<OWLAxiom>> satPaths,
            @Nonnull Set<OWLAxiom> currentPathContents, int maxExplanations,
            @Nonnull List<OWLAxiom> orderedMups, @Nonnull OWLAxiom axiom)
            throws OWLException {
        Set<OWLAxiom> newMUPS = getNewMUPS(unsatClass, allMups,
                currentPathContents);
        // Generate a new node - i.e. a new justification set
        if (newMUPS.contains(axiom)) {
            // How can this be the case???
            throw new OWLRuntimeException(
                    "Explanation contains removed axiom: " + axiom);
        }
        if (!newMUPS.isEmpty()) {
            // Note that getting a previous justification does not mean
            // we can stop. stopping here causes some justifications to
            // be missed
            allMups.add(newMUPS);
            progressMonitor.foundExplanation(newMUPS);
            // Recompute priority here?
            constructHittingSetTree(unsatClass, newMUPS, allMups, satPaths,
                    currentPathContents, maxExplanations);
            // We have found a new MUPS, so recalculate the ordering
            // axioms in the MUPS at the current level
            return getOrderedMUPS(orderedMups, allMups);
        } else {
            LOGGER.info("Stop - satisfiable");
            // End of current path - add it to the list of paths
            satPaths.add(new HashSet<>(currentPathContents));
        }
        return orderedMups;
    }

    private void backtrack(@Nonnull Set<OWLAxiom> currentPathContents,
            @Nonnull OWLAxiom axiom,
            @Nonnull List<OWLDeclarationAxiom> temporaryDeclarations,
            @Nonnull Set<OWLOntology> ontologies) {
        // Back track - go one level up the tree and run for the next axiom
        currentPathContents.remove(axiom);
        LOGGER.info("Restoring axiom: {}", axiom);
        // Remove any temporary declarations
        for (OWLDeclarationAxiom decl : temporaryDeclarations) {
            assert decl != null;
            OntologyUtils.removeAxiom(decl, getReasoner().getRootOntology()
                    .getImportsClosure(), getOntologyManager());
        }
        // Done with the axiom that was removed. Add it back in
        OntologyUtils.addAxiom(axiom, ontologies, getOntologyManager());
    }

    /**
     * Gets the new mups.
     * 
     * @param unsatClass
     *        the unsat class
     * @param allMups
     *        the all mups
     * @param currentPathContents
     *        the current path contents
     * @return the new mups
     */
    @Nonnull
    private Set<OWLAxiom> getNewMUPS(@Nonnull OWLClassExpression unsatClass,
            @Nonnull Set<Set<OWLAxiom>> allMups,
            @Nonnull Set<OWLAxiom> currentPathContents) {
        Set<OWLAxiom> newMUPS = null;
        for (Set<OWLAxiom> foundMUPS : allMups) {
            Set<OWLAxiom> foundMUPSCopy = new HashSet<>(foundMUPS);
            foundMUPSCopy.retainAll(currentPathContents);
            if (foundMUPSCopy.isEmpty()) {
                newMUPS = foundMUPS;
                break;
            }
        }
        if (newMUPS == null) {
            newMUPS = getExplanation(unsatClass);
        }
        return newMUPS;
    }

    /**
     * Removes the axiom and add declarations.
     * 
     * @param axiom
     *        the axiom
     * @param temporaryDeclarations
     *        the temporary declarations
     * @return the sets the
     */
    @Nonnull
    private Set<OWLOntology> removeAxiomAndAddDeclarations(
            @Nonnull OWLAxiom axiom,
            @Nonnull List<OWLDeclarationAxiom> temporaryDeclarations) {
        // Remove the current axiom from all the ontologies it is included
        // in
        Set<OWLOntology> ontologies = OntologyUtils.removeAxiom(axiom,
                getReasoner().getRootOntology().getImportsClosure(),
                getOntologyManager());
        collectTemporaryDeclarations(axiom, temporaryDeclarations);
        for (OWLDeclarationAxiom decl : temporaryDeclarations) {
            assert decl != null;
            OntologyUtils.addAxiom(decl, getReasoner().getRootOntology()
                    .getImportsClosure(), getOntologyManager());
        }
        return ontologies;
    }

    private void collectTemporaryDeclarations(@Nonnull OWLAxiom axiom,
            @Nonnull List<OWLDeclarationAxiom> temporaryDeclarations) {
        for (OWLEntity e : getSignature(axiom)) {
            assert e != null;
            boolean referenced = getReasoner().getRootOntology().isDeclared(e,
                    INCLUDED);
            if (!referenced) {
                temporaryDeclarations.add(getDeclaration(e));
            }
        }
    }

    /**
     * Gets the declaration.
     * 
     * @param e
     *        the e
     * @return the declaration
     */
    @Nonnull
    private OWLDeclarationAxiom getDeclaration(@Nonnull OWLEntity e) {
        return getOntologyManager().getOWLDataFactory().getOWLDeclarationAxiom(
                e);
    }
}
