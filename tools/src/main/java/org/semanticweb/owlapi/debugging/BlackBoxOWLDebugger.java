/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.debugging;

import static org.semanticweb.owlapi.model.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is an implementation of a blackbox debugger. The implementation is based
 * on the description of a black box debugger as described in Aditya Kalyanpur's
 * PhD Thesis : "Debugging and Repair of OWL Ontologies".
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class BlackBoxOWLDebugger extends AbstractOWLDebugger {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BlackBoxOWLDebugger.class);
    private OWLClass currentClass;
    private OWLOntology debuggingOntology;
    private final Set<OWLAxiom> debuggingAxioms = new LinkedHashSet<OWLAxiom>();
    private final Set<OWLEntity> objectsExpandedWithDefiningAxioms = new HashSet<OWLEntity>();
    private final Set<OWLEntity> objectsExpandedWithReferencingAxioms = new HashSet<OWLEntity>();
    private final Set<OWLAxiom> expandedWithDefiningAxioms = new HashSet<OWLAxiom>();
    private final Set<OWLAxiom> expandedWithReferencingAxioms = new HashSet<OWLAxiom>();
    private final OWLReasonerFactory reasonerFactory;
    private final Set<OWLAxiom> temporaryAxioms = new HashSet<OWLAxiom>();
    private final Map<OWLAxiom, OWLAxiom> expandedAxiomMap = new HashMap<OWLAxiom, OWLAxiom>();
    private static final int DEFAULT_INITIAL_EXPANSION_LIMIT = 50;
    private int initialExpansionLimit = DEFAULT_INITIAL_EXPANSION_LIMIT;
    private int expansionLimit = initialExpansionLimit;
    private double expansionFactor = 1.25;
    private static final int DEFAULT_FAST_PRUNING_WINDOW_SIZE = 10;
    private int fastPruningWindowSize = 0;
    private boolean performRepeatedFastPruning = false;

    /**
     * Instantiates a new black box owl debugger.
     * 
     * @param owlOntologyManager
     *        manager to use
     * @param ontology
     *        ontology to debug
     * @param reasonerFactory
     *        factory to use
     */
    public BlackBoxOWLDebugger(@Nonnull OWLOntologyManager owlOntologyManager,
            @Nonnull OWLOntology ontology,
            @Nonnull OWLReasonerFactory reasonerFactory) {
        super(owlOntologyManager, ontology);
        this.reasonerFactory = checkNotNull(reasonerFactory,
                "reasonerFactory cannot be null");
    }

    @Override
    public void dispose() {
        reset();
    }

    private void reset() {
        currentClass = null;
        debuggingOntology = null;
        debuggingAxioms.clear();
        objectsExpandedWithDefiningAxioms.clear();
        objectsExpandedWithReferencingAxioms.clear();
        expandedWithDefiningAxioms.clear();
        expandedWithReferencingAxioms.clear();
        temporaryAxioms.clear();
        expandedAxiomMap.clear();
        expansionLimit = initialExpansionLimit;
    }

    @Override
    protected OWLClassExpression getCurrentClass() throws OWLException {
        return currentClass;
    }

    /**
     * Setup debugging class.
     * 
     * @param cls
     *        the cls
     * @return the oWL class
     * @throws OWLException
     *         the oWL exception
     */
    @Nonnull
    private OWLClass setupDebuggingClass(@Nonnull OWLClassExpression cls)
            throws OWLException {
        if (!cls.isAnonymous()) {
            return (OWLClass) cls;
        } else {
            // The class is anonymous, so we need to assign it a name
            OWLClass curCls = owlOntologyManager.getOWLDataFactory()
                    .getOWLClass(createIRI());
            Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
            operands.add(curCls);
            operands.add(cls);
            temporaryAxioms.add(owlOntologyManager.getOWLDataFactory()
                    .getOWLEquivalentClassesAxiom(operands));
            for (OWLAxiom ax : temporaryAxioms) {
                owlOntologyManager.applyChanges(Arrays.asList(new AddAxiom(
                        getOWLOntology(), ax)));
            }
            return curCls;
        }
    }

    @Override
    public Set<OWLAxiom> getSOSForIncosistentClass(OWLClassExpression cls)
            throws OWLException {
        reset();
        currentClass = setupDebuggingClass(cls);
        generateSOSAxioms();
        for (OWLAxiom ax : temporaryAxioms) {
            owlOntologyManager.applyChanges(Arrays.asList(new RemoveAxiom(
                    getOWLOntology(), ax)));
        }
        debuggingAxioms.removeAll(temporaryAxioms);
        return new HashSet<OWLAxiom>(debuggingAxioms);
    }

    // /////////////////////////////////////////////////////////////////////////////////////////
    //
    // Expansion
    //
    // /////////////////////////////////////////////////////////////////////////////////////////
    private int expandAxioms() throws OWLException {
        // We expand the axiom set using axioms that define entities that are
        // already
        // referenced in the existing set of axioms. If this fails to expand the
        // axiom
        // set we expand using axioms that reference the entities in the axioms
        // that have
        // already been expanded.
        // Keep track of the number of axioms that have been added
        int axiomsAdded = 0;
        int remainingSpace = expansionLimit;
        for (OWLAxiom ax : new ArrayList<OWLAxiom>(debuggingAxioms)) {
            if (expandedWithDefiningAxioms.contains(ax)) {
                // Skip if already done
                continue;
            }
            // Collect the entities that have been used in the axiom
            for (OWLEntity curObj : ax.getSignature()) {
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
        for (OWLAxiom ax : new ArrayList<OWLAxiom>(debuggingAxioms)) {
            if (expandedWithReferencingAxioms.contains(ax)) {
                // Skip - already done this one
                continue;
            }
            // Keep track of the number of axioms that have been added
            for (OWLEntity curObj : ax.getSignature()) {
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
     * @throws OWLException
     *         the oWL exception
     */
    private int expandWithDefiningAxioms(@Nonnull OWLEntity obj, int limit)
            throws OWLException {
        Set<OWLAxiom> expansionAxioms = new HashSet<OWLAxiom>();
        if (obj instanceof OWLClass) {
            expansionAxioms.addAll(getOWLOntology().getAxioms((OWLClass) obj,
                    INCLUDED));
        } else if (obj instanceof OWLObjectProperty) {
            expansionAxioms.addAll(getOWLOntology().getAxioms(
                    (OWLObjectProperty) obj, INCLUDED));
        } else if (obj instanceof OWLDataProperty) {
            expansionAxioms.addAll(getOWLOntology().getAxioms(
                    (OWLDataProperty) obj, INCLUDED));
        } else if (obj instanceof OWLIndividual) {
            expansionAxioms.addAll(getOWLOntology().getAxioms(
                    (OWLIndividual) obj, INCLUDED));
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
     * @throws OWLException
     *         the oWL exception
     */
    private int expandWithReferencingAxioms(@Nonnull OWLEntity obj, int limit)
            throws OWLException {
        Set<OWLAxiom> expansionAxioms = new HashSet<OWLAxiom>();
        // First expand by getting the defining axioms - if this doesn't
        // return any axioms, then get the axioms that reference the entity
        expansionAxioms.addAll(getOWLOntology().getReferencingAxioms(obj,
                INCLUDED));
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
     * @return The number of objects that were actuall added.
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
    private void performFastPruning() throws OWLException {
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
            if (isSatisfiable()) {
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
            if (isSatisfiable()) {
                debuggingAxioms.addAll(axiomWindow);
            }
        }
        LOGGER.info("    - End of fast pruning");
    }

    private void performSlowPruning() throws OWLException {
        // Simply remove axioms one at a time. If the class
        // being debugged turns satisfiable then we know we have
        // an SOS axoiom.
        List<OWLAxiom> axiomsCopy = new ArrayList<OWLAxiom>(debuggingAxioms);
        for (OWLAxiom ax : axiomsCopy) {
            debuggingAxioms.remove(ax);
            if (isSatisfiable()) {
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
     * @return true, if is satisfiable
     * @throws OWLException
     *         the oWL exception
     */
    private boolean isSatisfiable() throws OWLException {
        createDebuggingOntology();
        OWLReasoner reasoner = reasonerFactory
                .createNonBufferingReasoner(debuggingOntology);
        satTestCount++;
        boolean sat = reasoner.isSatisfiable(currentClass);
        reasoner.dispose();
        return sat;
    }

    private void createDebuggingOntology() throws OWLException {
        if (debuggingOntology != null) {
            owlOntologyManager.removeOntology(debuggingOntology);
        }
        IRI iri = createIRI();
        SimpleIRIMapper mapper = new SimpleIRIMapper(iri, iri);
        owlOntologyManager.getIRIMappers().add(mapper);
        debuggingOntology = owlOntologyManager.createOntology(iri);
        owlOntologyManager.getIRIMappers().remove(mapper);
        List<AddAxiom> changes = new ArrayList<AddAxiom>();
        for (OWLAxiom ax : debuggingAxioms) {
            changes.add(new AddAxiom(debuggingOntology, ax));
        }
        for (OWLAxiom ax : temporaryAxioms) {
            changes.add(new AddAxiom(debuggingOntology, ax));
        }
        // Ensure the ontology contains the signature of the class which is
        // being debugged
        OWLDataFactory factory = owlOntologyManager.getOWLDataFactory();
        OWLAxiom ax = factory.getOWLSubClassOfAxiom(currentClass,
                factory.getOWLThing());
        changes.add(new AddAxiom(debuggingOntology, ax));
        owlOntologyManager.applyChanges(changes);
    }

    private void resetSatisfiabilityTestCounter() {
        satTestCount = 0;
    }

    private void generateSOSAxioms() throws OWLException {
        // Perform the initial expansion - this will cause
        // the debugging axioms set to be expanded to the
        // defining axioms for the class being debugged
        resetSatisfiabilityTestCounter();
        expandWithDefiningAxioms(currentClass, expansionLimit);
        LOGGER.info("Initial axiom count: {}", debuggingAxioms.size());
        int totalAdded = 0;
        int expansionCount = 0;
        while (isSatisfiable()) {
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
        LOGGER.info("FOUND CLASH! Pruning {} axioms...", debuggingAxioms.size());
        resetSatisfiabilityTestCounter();
        LOGGER.info("Fast pruning...");
        if (performRepeatedFastPruning) {
            // Base the initial fast pruning window size on the number of axioms
            fastPruningWindowSize = debuggingAxioms.size() / 10;
            if (fastPruningWindowSize < DEFAULT_FAST_PRUNING_WINDOW_SIZE) {
                fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
            }
            LOGGER.info("    Initial fast prunung window size: {}",
                    fastPruningWindowSize);
            int fastPruningCounter = 0;
            while (fastPruningWindowSize != 1) {
                LOGGER.info("    Round: {} (axioms to prune: {})",
                        fastPruningCounter, debuggingAxioms.size());
                fastPruningCounter++;
                performFastPruning();
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
            performFastPruning();
            LOGGER.info("... end of fast pruning. Axioms remaining: {}",
                    debuggingAxioms.size());
            LOGGER.info(
                    "Performed {} satisfiability tests during fast pruning",
                    satTestCount);
        }
        int totalSatTests = satTestCount;
        resetSatisfiabilityTestCounter();
        LOGGER.info("Slow pruning...");
        performSlowPruning();
        LOGGER.info("... end of slow pruning");
        LOGGER.info("Performed {} satisfiability tests during slow pruning",
                satTestCount);
        totalSatTests += satTestCount;
        LOGGER.info("Total number of satisfiability tests performed: {}",
                totalSatTests);
    }

    private static final AtomicLong counter = new AtomicLong(System.nanoTime());

    private static IRI createIRI() {
        return IRI.create("http://debugging.blackbox#",
                "A" + counter.incrementAndGet());
    }
}
