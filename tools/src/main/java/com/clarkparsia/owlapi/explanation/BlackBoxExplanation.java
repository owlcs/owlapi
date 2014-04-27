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

import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clarkparsia.owlapi.explanation.util.OntologyUtils;

/** A black box explanation. */
public class BlackBoxExplanation extends SingleExplanationGeneratorImpl
        implements SingleExplanationGenerator {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BlackBoxExplanation.class.getName());
    /** The debugging ontology. */
    private OWLOntology debuggingOntology;
    /** The debugging axioms. */
    @Nonnull
    protected final Set<OWLAxiom> debuggingAxioms = new LinkedHashSet<OWLAxiom>();
    /** The objects expanded with defining axioms. */
    @Nonnull
    private final Set<OWLEntity> objectsExpandedWithDefiningAxioms = new HashSet<OWLEntity>();
    /** The objects expanded with referencing axioms. */
    @Nonnull
    private final Set<OWLEntity> objectsExpandedWithReferencingAxioms = new HashSet<OWLEntity>();
    /** The expanded with defining axioms. */
    @Nonnull
    private final Set<OWLAxiom> expandedWithDefiningAxioms = new HashSet<OWLAxiom>();
    /** The expanded with referencing axioms. */
    @Nonnull
    private final Set<OWLAxiom> expandedWithReferencingAxioms = new HashSet<OWLAxiom>();
    /** The expanded axiom map. */
    @Nonnull
    private final Map<OWLAxiom, OWLAxiom> expandedAxiomMap = new HashMap<OWLAxiom, OWLAxiom>();
    /** default expansion limit. */
    public static final int DEFAULT_INITIAL_EXPANSION_LIMIT = 50;
    /** The initial expansion limit. */
    private int initialExpansionLimit = DEFAULT_INITIAL_EXPANSION_LIMIT;
    /** The expansion limit. */
    private int expansionLimit = initialExpansionLimit;
    /** The expansion factor. */
    private double expansionFactor = 1.25;
    /** The Constant DEFAULT_FAST_PRUNING_WINDOW_SIZE. */
    private static final int DEFAULT_FAST_PRUNING_WINDOW_SIZE = 10;
    /** The fast pruning window size. */
    private int fastPruningWindowSize = 0;
    /** The perform repeated fast pruning. */
    private boolean performRepeatedFastPruning = false;
    /** The owl ontology manager. */
    private final OWLOntologyManager owlOntologyManager;

    /**
     * Instantiates a new black box explanation.
     * 
     * @param ontology
     *        the ontology
     * @param reasonerFactory
     *        the reasoner factory
     * @param reasoner
     *        the reasoner
     */
    public BlackBoxExplanation(@Nonnull OWLOntology ontology,
            @Nonnull OWLReasonerFactory reasonerFactory,
            @Nonnull OWLReasoner reasoner) {
        super(ontology, reasonerFactory, reasoner);
        owlOntologyManager = ontology.getOWLOntologyManager();
    }

    @Override
    public void dispose() {
        reset();
        getReasoner().dispose();
    }

    private void reset() {
        if (debuggingOntology != null) {
            owlOntologyManager.removeOntology(debuggingOntology);
            debuggingOntology = null;
        }
        debuggingAxioms.clear();
        objectsExpandedWithDefiningAxioms.clear();
        objectsExpandedWithReferencingAxioms.clear();
        expandedWithDefiningAxioms.clear();
        expandedWithReferencingAxioms.clear();
        expandedAxiomMap.clear();
        expansionLimit = initialExpansionLimit;
    }

    @SuppressWarnings("null")
    @Override
    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass) {
        if (!getDefinitionTracker().isDefined(unsatClass)) {
            return Collections.emptySet();
        }
        try {
            satTestCount++;
            if (isFirstExplanation() && getReasoner().isSatisfiable(unsatClass)) {
                return Collections.emptySet();
            }
            reset();
            expandUntilUnsatisfiable(unsatClass);
            pruneUntilMinimal(unsatClass);
            removeDeclarations();
            return new HashSet<OWLAxiom>(debuggingAxioms);
        } catch (OWLException e) {
            throw new OWLRuntimeException(e);
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////
    //
    // Expansion
    //
    // /////////////////////////////////////////////////////////////////////////////////////////
    private int expandAxioms() {
        /*
         * We expand the axiom set using axioms that define entities that are
         * already referenced in the existing set of axioms. If this fails to
         * expand the axiom set we expand using axioms that reference the
         * entities in the axioms that have already been expanded.
         */
        // Keep track of the number of axioms that have been added
        int axiomsAdded = 0;
        int remainingSpace = expansionLimit;
        for (OWLAxiom ax : debuggingAxioms) {
            if (expandedWithDefiningAxioms.contains(ax)) {
                // Skip if already done
                continue;
            }
            // Collect the entities that have been used in the axiom
            for (OWLEntity curObj : ax.getSignature()) {
                assert curObj != null;
                if (!objectsExpandedWithDefiningAxioms.contains(curObj)) {
                    int added = expandWithDefiningAxioms(curObj, remainingSpace);
                    axiomsAdded += added;
                    remainingSpace = remainingSpace - added;
                    if (remainingSpace == 0) {
                        expansionLimit *= expansionFactor;
                        return axiomsAdded;
                    }
                    // Flag that we have completely expanded all defining axioms
                    // for this particular entity
                    objectsExpandedWithDefiningAxioms.add(curObj);
                }
            }
            // Flag that we've completely expanded this particular axiom
            expandedWithDefiningAxioms.add(ax);
        }
        if (axiomsAdded > 0) {
            return axiomsAdded;
        }
        // No axioms added at this point. Start adding axioms that reference
        // entities contained in the current set of debugging axioms
        for (OWLAxiom ax : debuggingAxioms) {
            if (expandedWithReferencingAxioms.contains(ax)) {
                // Skip - already done this one
                continue;
            }
            // Keep track of the number of axioms that have been added
            for (OWLEntity curObj : ax.getSignature()) {
                assert curObj != null;
                if (!objectsExpandedWithReferencingAxioms.contains(curObj)) {
                    int added = expandWithReferencingAxioms(curObj,
                            expansionLimit);
                    axiomsAdded += added;
                    remainingSpace -= added;
                    if (remainingSpace == 0) {
                        expansionLimit *= expansionFactor;
                        return axiomsAdded;
                    }
                    objectsExpandedWithReferencingAxioms.add(curObj);
                }
            }
            expandedWithReferencingAxioms.add(ax);
        }
        return axiomsAdded;
    }

    /**
     * Creates a set of axioms to expands the debugging axiom set by adding the
     * defining axioms for the specified entity.
     * 
     * @param obj
     *        the obj
     * @param limit
     *        the limit
     * @return the int
     */
    private int expandWithDefiningAxioms(@Nonnull OWLEntity obj, int limit) {
        Set<OWLAxiom> expansionAxioms = new HashSet<OWLAxiom>();
        for (OWLOntology ont : getOntology().getImportsClosure()) {
            boolean referenceFound = false;
            if (obj instanceof OWLClass) {
                referenceFound = expansionAxioms.addAll(ont.getAxioms(
                        (OWLClass) obj, EXCLUDED));
            } else if (obj instanceof OWLObjectProperty) {
                referenceFound = expansionAxioms.addAll(ont.getAxioms(
                        (OWLObjectProperty) obj, EXCLUDED));
            } else if (obj instanceof OWLDataProperty) {
                referenceFound = expansionAxioms.addAll(ont.getAxioms(
                        (OWLDataProperty) obj, EXCLUDED));
            } else if (obj instanceof OWLIndividual) {
                referenceFound = expansionAxioms.addAll(ont.getAxioms(
                        (OWLIndividual) obj, EXCLUDED));
            }
            if (!referenceFound) {
                expansionAxioms.add(owlOntologyManager.getOWLDataFactory()
                        .getOWLDeclarationAxiom(obj));
            }
        }
        expansionAxioms.removeAll(debuggingAxioms);
        return addMax(expansionAxioms, debuggingAxioms, limit);
    }

    /**
     * Expands the axiom set by adding the referencing axioms for the specified
     * entity.
     * 
     * @param obj
     *        the obj
     * @param limit
     *        the limit
     * @return the int
     */
    private int expandWithReferencingAxioms(@Nonnull OWLEntity obj, int limit) {
        Set<OWLAxiom> expansionAxioms = new HashSet<OWLAxiom>();
        // First expand by getting the defining axioms - if this doesn't
        // return any axioms, then get the axioms that reference the entity
        expansionAxioms.addAll(getOntology()
                .getReferencingAxioms(obj, INCLUDED));
        expansionAxioms.removeAll(debuggingAxioms);
        return addMax(expansionAxioms, debuggingAxioms, limit);
    }

    /**
     * A utility method. Adds axioms from one set to another set upto a
     * specified limit. Annotation axioms are stripped out
     * 
     * @param <N>
     *        the number type
     * @param source
     *        The source set. Objects from this set will be added to the
     *        destination set
     * @param dest
     *        The destination set. Objects will be added to this set
     * @param limit
     *        The maximum number of objects to be added.
     * @return The number of objects that were actually added.
     */
    private static <N extends OWLAxiom> int addMax(@Nonnull Set<N> source,
            @Nonnull Set<N> dest, int limit) {
        int count = 0;
        for (N obj : source) {
            if (count == limit) {
                break;
            }
            if (!(obj instanceof OWLAnnotationAxiom) && dest.add(obj)) {
                count++;
            }
        }
        return count;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////
    //
    // Contraction/Pruning - Fast pruning is performed and then slow pruning is
    // performed.
    //
    // /////////////////////////////////////////////////////////////////////////////////////////
    private void performFastPruning(@Nonnull OWLClassExpression unsatClass)
            throws OWLException {
        Set<OWLAxiom> axiomWindow = new HashSet<OWLAxiom>();
        Object[] axioms = debuggingAxioms.toArray();
        LOGGER.info("Fast pruning: ");
        LOGGER.info("     - Window size: {}", fastPruningWindowSize);
        int windowCount = debuggingAxioms.size() / fastPruningWindowSize;
        for (int currentWindow = 0; currentWindow < windowCount; currentWindow++) {
            axiomWindow.clear();
            int startIndex = currentWindow * fastPruningWindowSize;
            int endIndex = startIndex + fastPruningWindowSize;
            for (int axiomIndex = startIndex; axiomIndex < endIndex; axiomIndex++) {
                OWLAxiom currentAxiom = (OWLAxiom) axioms[axiomIndex];
                axiomWindow.add(currentAxiom);
                debuggingAxioms.remove(currentAxiom);
            }
            if (isSatisfiable(unsatClass)) {
                debuggingAxioms.addAll(axiomWindow);
            }
        }
        // Add any left over axioms
        axiomWindow.clear();
        int remainingAxiomsCount = debuggingAxioms.size()
                % fastPruningWindowSize;
        if (remainingAxiomsCount > 0) {
            int fragmentIndex = windowCount * fastPruningWindowSize;
            while (fragmentIndex < axioms.length) {
                OWLAxiom curAxiom = (OWLAxiom) axioms[fragmentIndex];
                axiomWindow.add(curAxiom);
                debuggingAxioms.remove(curAxiom);
                fragmentIndex++;
            }
            if (isSatisfiable(unsatClass)) {
                debuggingAxioms.addAll(axiomWindow);
            }
        }
        LOGGER.info("    - End of fast pruning");
    }

    private void performSlowPruning(@Nonnull OWLClassExpression unsatClass)
            throws OWLException {
        // Simply remove axioms one at a time. If the class
        // being debugged turns satisfiable then we know we have
        // an SOS axiom.
        List<OWLAxiom> axiomsCopy = new ArrayList<OWLAxiom>(debuggingAxioms);
        for (OWLAxiom ax : axiomsCopy) {
            debuggingAxioms.remove(ax);
            if (isSatisfiable(unsatClass)) {
                // Affects satisfiability, so add back in
                debuggingAxioms.add(ax);
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////
    //
    // Creation of debugging ontology and satisfiability testing
    //
    // /////////////////////////////////////////////////////////////////////////////////////////
    private int satTestCount = 0;

    /**
     * Tests the satisfiability of the test class. The ontology is recreated
     * before the test is performed.
     * 
     * @param unsatClass
     *        the unsat class
     * @return true, if is satisfiable
     * @throws OWLException
     *         the oWL exception
     */
    @SuppressWarnings("null")
    private boolean isSatisfiable(@Nonnull OWLClassExpression unsatClass)
            throws OWLException {
        createDebuggingOntology();
        OWLReasoner reasoner = getReasonerFactory().createNonBufferingReasoner(
                debuggingOntology);
        if (OntologyUtils.containsUnreferencedEntity(debuggingOntology,
                unsatClass)) {
            reasoner.dispose();
            return true;
        }
        satTestCount++;
        boolean sat = reasoner.isSatisfiable(unsatClass);
        reasoner.dispose();
        return sat;
    }

    @SuppressWarnings("null")
    private void createDebuggingOntology() throws OWLException {
        if (debuggingOntology != null) {
            owlOntologyManager.removeOntology(debuggingOntology);
        }
        debuggingOntology = owlOntologyManager.createOntology();
        List<AddAxiom> changes = new ArrayList<AddAxiom>();
        for (OWLAxiom ax : debuggingAxioms) {
            changes.add(new AddAxiom(debuggingOntology, ax));
        }
        owlOntologyManager.applyChanges(changes);
    }

    private void resetSatisfiabilityTestCounter() {
        satTestCount = 0;
    }

    private void
            expandUntilUnsatisfiable(@Nonnull OWLClassExpression unsatClass)
                    throws OWLException {
        // Perform the initial expansion - this will cause
        // the debugging axioms set to be expanded to the
        // defining axioms for the class being debugged
        resetSatisfiabilityTestCounter();
        if (!unsatClass.isAnonymous()) {
            expandWithDefiningAxioms((OWLClass) unsatClass, expansionLimit);
        } else {
            OWLClass owlThing = owlOntologyManager.getOWLDataFactory()
                    .getOWLThing();
            OWLSubClassOfAxiom axiom = owlOntologyManager.getOWLDataFactory()
                    .getOWLSubClassOfAxiom(unsatClass, owlThing);
            debuggingAxioms.add(axiom);
            expandAxioms();
            debuggingAxioms.remove(axiom);
        }
        LOGGER.info("Initial axiom count: {}", debuggingAxioms.size());
        int totalAdded = 0;
        int expansionCount = 0;
        while (isSatisfiable(unsatClass)) {
            LOGGER.info("Expanding axioms (expansion {})", expansionCount);
            expansionCount++;
            int numberAdded = expandAxioms();
            totalAdded += numberAdded;
            LOGGER.info("    ... expanded by {}", numberAdded);
            if (numberAdded == 0) {
                LOGGER.info("ERROR! Cannot find SOS axioms!");
                debuggingAxioms.clear();
                return;
            }
        }
        LOGGER.info("Total number of axioms added: {}", totalAdded);
    }

    /**
     * Prune until minimal.
     * 
     * @param unsatClass
     *        the unsat class
     * @throws OWLException
     *         the oWL exception
     */
    protected void pruneUntilMinimal(@Nonnull OWLClassExpression unsatClass)
            throws OWLException {
        LOGGER.info("FOUND CLASH! Pruning {} axioms...", debuggingAxioms.size());
        resetSatisfiabilityTestCounter();
        LOGGER.info("Fast pruning...");
        // fastPruningWindowSize = 0;
        if (performRepeatedFastPruning) {
            // Base the initial fast pruning window size on the number of axioms
            fastPruningWindowSize = debuggingAxioms.size() / 6;
            if (fastPruningWindowSize < DEFAULT_FAST_PRUNING_WINDOW_SIZE) {
                fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
            }
            LOGGER.info("    Initial fast pruning window size: {}",
                    fastPruningWindowSize);
            int fastPruningCounter = 0;
            while (fastPruningWindowSize != 1) {
                LOGGER.info("    Round: %s (axioms to prune: {})",
                        fastPruningCounter, debuggingAxioms.size());
                fastPruningCounter++;
                performFastPruning(unsatClass);
                fastPruningWindowSize = fastPruningWindowSize / 3;
                if (fastPruningWindowSize < 1) {
                    fastPruningWindowSize = 1;
                }
            }
            LOGGER.info("... end of fast pruning. Axioms remaining: {}",
                    debuggingAxioms.size());
            LOGGER.info(
                    "Performed {} satisfiability tests during fast pruning",
                    satTestCount);
        } else {
            fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
            performFastPruning(unsatClass);
            LOGGER.info("... end of fast pruning. Axioms remaining: {}",
                    debuggingAxioms.size());
            LOGGER.info(
                    "Performed {} satisfiability tests during fast pruning",
                    satTestCount);
        }
        int totalSatTests = satTestCount;
        resetSatisfiabilityTestCounter();
        LOGGER.info("Slow pruning...");
        performSlowPruning(unsatClass);
        LOGGER.info("... end of slow pruning");
        LOGGER.info("Performed {} satisfiability tests during slow pruning",
                satTestCount);
        totalSatTests += satTestCount;
        LOGGER.info("Total number of satisfiability tests performed: {}",
                totalSatTests);
    }

    private void removeDeclarations() {
        OWLAxiomVisitor declarationRemover = new OWLAxiomVisitorAdapter() {

            @Override
            public void visit(OWLDeclarationAxiom axiom) {
                checkNotNull(axiom, "axiom cannot be null");
                debuggingAxioms.remove(axiom);
            }
        };
        for (OWLAxiom axiom : debuggingAxioms
                .toArray(new OWLAxiom[debuggingAxioms.size()])) {
            axiom.accept(declarationRemover);
        }
    }

    @Override
    public String toString() {
        return "BlackBox";
    }
}
