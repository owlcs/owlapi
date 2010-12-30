package com.clarkparsia.owlapi.explanation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.semanticweb.owlapi.apibinding.OWLManager;
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
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

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
 * Title:
 * </p>
 * <p/>
 * Description: Implementation of a {@link SingleExplanationGenerator}
 * interface. This code is based on Matthew Horridge's implementation which was
 * based on the description of a black box debugger described in Aditya
 * Kalyanpur's PhD Thesis : "Debugging and Repair of OWL Ontologies".
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
public class BlackBoxExplanation extends SingleExplanationGeneratorImpl implements SingleExplanationGenerator {

    public static final Logger log = Logger.getLogger(BlackBoxExplanation.class.getName());

    private OWLOntology debuggingOntology;

    private Set<OWLAxiom> debuggingAxioms;

    private Set<OWLEntity> objectsExpandedWithDefiningAxioms;

    private Set<OWLEntity> objectsExpandedWithReferencingAxioms;

    private Set<OWLAxiom> expandedWithDefiningAxioms;

    private Set<OWLAxiom> expandedWithReferencingAxioms;

    private Map<OWLAxiom, OWLAxiom> expandedAxiomMap;

    public static final int DEFAULT_INITIAL_EXPANSION_LIMIT = 50;

    private int initialExpansionLimit = DEFAULT_INITIAL_EXPANSION_LIMIT;

    private int expansionLimit = initialExpansionLimit;

    private double expansionFactor = 1.25;

    private static final int DEFAULT_FAST_PRUNING_WINDOW_SIZE = 10;

    private int fastPruningWindowSize = 0;

    private boolean performRepeatedFastPruning = false;

    private OWLOntologyManager owlOntologyManager;

    public BlackBoxExplanation(OWLOntology ontology, OWLReasonerFactory reasonerFactory, OWLReasoner reasoner) {
        super(ontology, reasonerFactory, reasoner);
        owlOntologyManager = OWLManager.createOWLOntologyManager();
        debuggingAxioms = new LinkedHashSet<OWLAxiom>();
        objectsExpandedWithDefiningAxioms = new HashSet<OWLEntity>();
        objectsExpandedWithReferencingAxioms = new HashSet<OWLEntity>();
        expandedWithDefiningAxioms = new HashSet<OWLAxiom>();
        expandedWithReferencingAxioms = new HashSet<OWLAxiom>();
        expandedAxiomMap = new HashMap<OWLAxiom, OWLAxiom>();
    }


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


    public Set<OWLAxiom> getExplanation(OWLClassExpression unsatClass) {

        if (!getDefinitionTracker().isDefined(unsatClass))
            return Collections.emptySet();

        try {
            satTestCount++;

            if (isFirstExplanation()) {
                if (getReasoner().isSatisfiable(unsatClass))
                	return Collections.emptySet();
            }
            reset();
            expandUntilUnsatisfiable(unsatClass);
            pruneUntilMinimal(unsatClass);
            removeDeclarations();
            ontologyCounter = 0;

            return new HashSet<OWLAxiom>(debuggingAxioms);
        }
        catch (OWLException e) {
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
        // System.out.println("Expansion limit: " + expansionLimit);
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
                    int added = expandWithReferencingAxioms(curObj, expansionLimit);
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
     */
    private int expandWithDefiningAxioms(OWLEntity obj, int limit) {
        Set<OWLAxiom> expansionAxioms = new HashSet<OWLAxiom>();
        for (OWLOntology ont : getOntology().getImportsClosure()) {
            boolean referenceFound = false;
            if (obj instanceof OWLClass) {
                referenceFound = expansionAxioms.addAll(ont.getAxioms((OWLClass) obj));
            }
            else if (obj instanceof OWLObjectProperty) {
                referenceFound = expansionAxioms.addAll(ont.getAxioms((OWLObjectProperty) obj));
            }
            else if (obj instanceof OWLDataProperty) {
                referenceFound = expansionAxioms.addAll(ont.getAxioms((OWLDataProperty) obj));
            }
            else if (obj instanceof OWLIndividual) {
                referenceFound = expansionAxioms.addAll(ont.getAxioms((OWLIndividual) obj));
            }
            if (!referenceFound)
                expansionAxioms.add(owlOntologyManager.getOWLDataFactory().getOWLDeclarationAxiom(obj));
        }
        expansionAxioms.removeAll(debuggingAxioms);
        return addMax(expansionAxioms, debuggingAxioms, limit);
    }


    /**
     * Expands the axiom set by adding the referencing axioms for the specified
     * entity.
     */
    private int expandWithReferencingAxioms(OWLEntity obj, int limit) {
        Set<OWLAxiom> expansionAxioms = new HashSet<OWLAxiom>();
        // First expand by getting the defining axioms - if this doesn't
        // return any axioms, then get the axioms that reference the entity
        for (OWLOntology ont : getOntology().getImportsClosure()) {
            expansionAxioms.addAll(ont.getReferencingAxioms(obj));
        }
        expansionAxioms.removeAll(debuggingAxioms);
        return addMax(expansionAxioms, debuggingAxioms, limit);
    }


    /**
     * A utility method. Adds axioms from one set to another set upto a
     * specified limit. Annotation axioms are stripped out
     *
     * @param source The source set. Objects from this set will be added to the
     * destination set
     * @param dest The destination set. Objects will be added to this set
     * @param limit The maximum number of objects to be added.
     * @return The number of objects that were actually added.
     */
    private static <N extends OWLAxiom> int addMax(Set<N> source, Set<N> dest, int limit) {
        int count = 0;
        for (N obj : source) {
            if (count == limit) {
                break;
            }
            if (!(obj instanceof OWLAnnotationAxiom)) {
                if (dest.add(obj)) {
                    count++;
                }
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


    private void performFastPruning(OWLClassExpression unsatClass) throws OWLException {
        Set<OWLAxiom> axiomWindow = new HashSet<OWLAxiom>();
        Object[] axioms = debuggingAxioms.toArray();
        if (log.isLoggable(Level.FINE)) {
            log.info("Fast pruning: ");
        }
//        if (performRepeatedFastPruning) {
//            int desiredWindowSize = debuggingAxioms.size() / 6;
//
//            if (desiredWindowSize == fastPruningWindowSize) {
//                fastPruningWindowSize = desiredWindowSize / 3;
//            }
//            else {
//                fastPruningWindowSize = desiredWindowSize;
//            }
//            if (fastPruningWindowSize < 1) {
//                fastPruningWindowSize = 1;
//            }
//        }
//        else {
//            fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
//        }
        if (log.isLoggable(Level.FINE)) {
            log.fine("     - Window size: " + fastPruningWindowSize);
        }
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
        int remainingAxiomsCount = debuggingAxioms.size() % fastPruningWindowSize;
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
        if (log.isLoggable(Level.FINE)) {
            log.fine("    - End of fast pruning");
        }
    }


    private void performSlowPruning(OWLClassExpression unsatClass) throws OWLException {
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
     */
    private boolean isSatisfiable(OWLClassExpression unsatClass) throws OWLException {
        createDebuggingOntology();
        ontologyCounter++;

        OWLReasoner reasoner = getReasonerFactory().createNonBufferingReasoner(debuggingOntology);

        if (OntologyUtils.containsUnreferencedEntity(debuggingOntology, unsatClass)) {
        	reasoner.dispose();
            return true;
        }
        satTestCount++;
        boolean sat = reasoner.isSatisfiable(unsatClass);
        reasoner.dispose();
        return sat;
    }


    int ontologyCounter = 0;


    private void createDebuggingOntology() throws OWLException {
        if (debuggingOntology != null) {
            owlOntologyManager.removeOntology(debuggingOntology);
        }
        debuggingOntology = owlOntologyManager.createOntology();
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        for (OWLAxiom ax : debuggingAxioms) {
            changes.add(new AddAxiom(debuggingOntology, ax));
        }

        owlOntologyManager.applyChanges(changes);
    }


    private void resetSatisfiabilityTestCounter() {
        satTestCount = 0;
    }


    private void expandUntilUnsatisfiable(OWLClassExpression unsatClass) throws OWLException {
        // Perform the initial expansion - this will cause
        // the debugging axioms set to be expanded to the
        // defining axioms for the class being debugged
        resetSatisfiabilityTestCounter();

        if (!unsatClass.isAnonymous()) {
            expandWithDefiningAxioms((OWLClass) unsatClass, expansionLimit);
        }
        else {
            OWLClass owlThing = owlOntologyManager.getOWLDataFactory().getOWLThing();
            OWLSubClassOfAxiom axiom = owlOntologyManager.getOWLDataFactory().getOWLSubClassOfAxiom(unsatClass, owlThing);
            debuggingAxioms.add(axiom);
            expandAxioms();
            debuggingAxioms.remove(axiom);
        }

        if (log.isLoggable(Level.FINE)) {
            log.fine("Initial axiom count: " + debuggingAxioms.size());
        }

        int totalAdded = 0;
        int expansionCount = 0;
        while (isSatisfiable(unsatClass)) {

            if (log.isLoggable(Level.FINE)) {
                log.fine("Expanding axioms (expansion " + expansionCount + ")");
            }

            expansionCount++;
            int numberAdded = expandAxioms();
            totalAdded += numberAdded;

            if (log.isLoggable(Level.FINE)) {
                log.fine("    ... expanded by " + numberAdded);
            }

            if (numberAdded == 0) {

                if (log.isLoggable(Level.FINE)) {
                    log.fine("ERROR! Cannot find SOS axioms!");
                }

                debuggingAxioms.clear();
                return;
            }
        }

        if (log.isLoggable(Level.FINE)) {
            log.fine("Total number of axioms added: " + totalAdded);
        }
    }


    protected void pruneUntilMinimal(OWLClassExpression unsatClass) throws OWLException {
        if (log.isLoggable(Level.FINE)) {
            log.fine("FOUND CLASH! Pruning " + debuggingAxioms.size() + " axioms...");
        }

        resetSatisfiabilityTestCounter();
        if (log.isLoggable(Level.FINE)) {
            log.fine("Fast pruning...");
        }

        // fastPruningWindowSize = 0;
        if (performRepeatedFastPruning) {
            // Base the initial fast pruning window size on the number of axioms
            fastPruningWindowSize = debuggingAxioms.size() / 6;
            if (fastPruningWindowSize < DEFAULT_FAST_PRUNING_WINDOW_SIZE) {
                fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
            }
            if (log.isLoggable(Level.FINE)) {
                log.fine("    Initial fast pruning window size: " + fastPruningWindowSize);
            }
            int fastPruningCounter = 0;
            while (fastPruningWindowSize != 1) {
                if (log.isLoggable(Level.FINE)) {
                    log.fine("    Round: " + fastPruningCounter + " (axioms to prune: " + debuggingAxioms.size() + ")");
                }
                fastPruningCounter++;
                performFastPruning(unsatClass);
                fastPruningWindowSize = fastPruningWindowSize / 3;
                if (fastPruningWindowSize < 1) {
                    fastPruningWindowSize = 1;
                }
            }
            if (log.isLoggable(Level.FINE)) {
                log.fine("... end of fast pruning. Axioms remaining: " + debuggingAxioms.size());
                log.fine("Performed " + satTestCount + " satisfiability tests during fast pruning");
            }
        }
        else {
            fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
            performFastPruning(unsatClass);
            if (log.isLoggable(Level.FINE)) {
                log.info("... end of fast pruning. Axioms remaining: " + debuggingAxioms.size());
                log.info("Performed " + satTestCount + " satisfiability tests during fast pruning");
            }
        }

        int totalSatTests = satTestCount;

        resetSatisfiabilityTestCounter();
        if (log.isLoggable(Level.FINE)) {
            log.fine("Slow pruning...");
        }

        performSlowPruning(unsatClass);

        if (log.isLoggable(Level.FINE)) {
            log.fine("... end of slow pruning");
            log.fine("Performed " + satTestCount + " satisfiability tests during slow pruning");
        }
        totalSatTests += satTestCount;
        if (log.isLoggable(Level.FINE)) {
            log.fine("Total number of satisfiability tests performed: " + totalSatTests);
        }
    }

    private void removeDeclarations() {
        OWLAxiomVisitor declarationRemover = new OWLAxiomVisitorAdapter() {
            @Override
			public void visit(OWLDeclarationAxiom axiom) {
                debuggingAxioms.remove(axiom);
            }
        };
        for (OWLAxiom axiom : debuggingAxioms.toArray(new OWLAxiom[0])) {
            axiom.accept(declarationRemover);
        }
    }

//    private static URI createURI() {
//        return URI.create("http://debugging.blackbox#" + System.nanoTime());
//    }


    @Override
	public String toString() {
        return "BlackBox";
    }
}
