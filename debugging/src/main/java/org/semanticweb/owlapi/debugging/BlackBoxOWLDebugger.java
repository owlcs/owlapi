package org.semanticweb.owlapi.debugging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

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
 * This is an implementation of a blackbox debugger.  The implementation
 * is based on the description of a black box debugger as described in
 * Aditya Kalyanpur's PhD Thesis : "Debugging and Repair of OWL Ontologies".
 */
public class BlackBoxOWLDebugger extends AbstractOWLDebugger {


    private static final Logger logger = Logger.getLogger(BlackBoxOWLDebugger.class.getName());


    private OWLOntologyManager owlOntologyManager;

//    private OWLOntology ontology;

    private OWLClass currentClass;

    private OWLOntology debuggingOntology;

    private Set<OWLAxiom> debuggingAxioms;

    private Set<OWLEntity> objectsExpandedWithDefiningAxioms;

    private Set<OWLEntity> objectsExpandedWithReferencingAxioms;

    private Set<OWLAxiom> expandedWithDefiningAxioms;

    private Set<OWLAxiom> expandedWithReferencingAxioms;

    private OWLReasonerFactory reasonerFactory;

//    private OWLReasoner reasoner;

    private Set<OWLAxiom> temporaryAxioms;

    private Map<OWLAxiom, OWLAxiom> expandedAxiomMap;

    public static final int DEFAULT_INITIAL_EXPANSION_LIMIT = 50;

    private int initialExpansionLimit = DEFAULT_INITIAL_EXPANSION_LIMIT;

    private int expansionLimit = initialExpansionLimit;

    private double expansionFactor = 1.25;

    private static final int DEFAULT_FAST_PRUNING_WINDOW_SIZE = 10;

    private int fastPruningWindowSize = 0;

    private boolean performRepeatedFastPruning = false;


    public BlackBoxOWLDebugger(OWLOntologyManager owlOntologyManager, OWLOntology ontology, OWLReasonerFactory reasonerFactory) {
        super(owlOntologyManager, ontology);
        this.reasonerFactory = reasonerFactory;
        this.owlOntologyManager = owlOntologyManager;
//        this.reasoner = reasoner;
        debuggingAxioms = new LinkedHashSet<OWLAxiom>();
        objectsExpandedWithDefiningAxioms = new HashSet<OWLEntity>();
        objectsExpandedWithReferencingAxioms = new HashSet<OWLEntity>();
        expandedWithDefiningAxioms = new HashSet<OWLAxiom>();
        expandedWithReferencingAxioms = new HashSet<OWLAxiom>();
        temporaryAxioms = new HashSet<OWLAxiom>();
        expandedAxiomMap = new HashMap<OWLAxiom, OWLAxiom>();
        logger.setLevel(Level.INFO);
    }


    public void dispose() {
        reset();
//        reasoner.dispose();
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


    private OWLClass setupDebuggingClass(OWLClassExpression cls) throws OWLException {
        if (!cls.isAnonymous()) {
            return (OWLClass) cls;
        }
        else {
            // The class is anonymous, so we need to assign it a name
            OWLClass curCls = owlOntologyManager.getOWLDataFactory().getOWLClass(createIRI());
            Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
            operands.add(curCls);
            operands.add(cls);
            temporaryAxioms.add(owlOntologyManager.getOWLDataFactory().getOWLEquivalentClassesAxiom(operands));
            for (OWLAxiom ax : temporaryAxioms) {
                owlOntologyManager.applyChanges(Arrays.asList(new AddAxiom(getOWLOntology(), ax)));
            }
            return curCls;
        }
    }


    public Set<OWLAxiom> getSOSForIncosistentClass(OWLClassExpression cls) throws OWLException {
        reset();
        currentClass = setupDebuggingClass(cls);
        generateSOSAxioms();
        for (OWLAxiom ax : temporaryAxioms) {
            owlOntologyManager.applyChanges(Arrays.asList(new RemoveAxiom(getOWLOntology(), ax)));
        }
        debuggingAxioms.removeAll(temporaryAxioms);
        ontologyCounter = 0;
        return new HashSet<OWLAxiom>(debuggingAxioms);
    }


//    private static List<OWLAxiom> toList(Set<OWLAxiom> axioms) {
//        return new ArrayList<OWLAxiom>(axioms);
//    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //
    // Expansion
    //
    ///////////////////////////////////////////////////////////////////////////////////////////


    private int expandAxioms() throws OWLException {
        // We expand the axiom set using axioms that define entities that are already
        // referenced in the existing set of axioms.  If this fails to expand the axiom
        // set we expand using axioms that reference the entities in the axioms that have
        // already been expanded.

        // Keep track of the number of axioms that have been added
        int axiomsAdded = 0;

        int remainingSpace = expansionLimit;
//        System.out.println("Expansion limit: " + expansionLimit);
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

        // No axioms added at this point.  Start adding axioms that reference
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
     * Creates a set of axioms to expands the debugging axiom set by adding the defining axioms for the
     * specified entity.
     */
    private int expandWithDefiningAxioms(OWLEntity obj, int limit) throws OWLException {
        Set<OWLAxiom> expansionAxioms = new HashSet<OWLAxiom>();
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(getOWLOntology())) {
            if (obj instanceof OWLClass) {
                expansionAxioms.addAll(ont.getAxioms((OWLClass) obj));
            }
            else if (obj instanceof OWLObjectProperty) {
                expansionAxioms.addAll(ont.getAxioms((OWLObjectProperty) obj));
            }
            else if (obj instanceof OWLDataProperty) {
                expansionAxioms.addAll(ont.getAxioms((OWLDataProperty) obj));
            }
            else if (obj instanceof OWLIndividual) {
                expansionAxioms.addAll(ont.getAxioms((OWLIndividual) obj));
            }
        }
        expansionAxioms.removeAll(debuggingAxioms);
        return addMax(expansionAxioms, debuggingAxioms, limit);
    }


    /**
     * Expands the axiom set by adding the referencing axioms for the
     * specified entity.
     */
    private int expandWithReferencingAxioms(OWLEntity obj, int limit) throws OWLException {
        Set<OWLAxiom> expansionAxioms = new HashSet<OWLAxiom>();
        // First expand by getting the defining axioms - if this doesn't
        // return any axioms, then get the axioms that reference the entity
        for (OWLOntology ont : owlOntologyManager.getImportsClosure(getOWLOntology())) {
            expansionAxioms.addAll(ont.getReferencingAxioms(obj));
        }
        expansionAxioms.removeAll(debuggingAxioms);
        return addMax(expansionAxioms, debuggingAxioms, limit);
    }


    /**
     * A utility method.  Adds axioms from one set to another set upto a specified limit.
     * Annotation axioms are stripped out
     *
     * @param source The source set.  Objects from this set will be added to the
     * destination set
     * @param dest The destination set.  Objects will be added to this set
     * @param limit The maximum number of objects to be added.
     * @return The number of objects that were actuall added.
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

    ///////////////////////////////////////////////////////////////////////////////////////////
    //
    // Contraction/Pruning - Fast pruning is performed and then slow pruning is performed.
    //
    ///////////////////////////////////////////////////////////////////////////////////////////


    private void performFastPruning() throws OWLException {
        logger.setLevel(Level.INFO);
        Set<OWLAxiom> axiomWindow = new HashSet<OWLAxiom>();
        Object[] axioms = debuggingAxioms.toArray();
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Fast pruning: ");
        }
//        if (performRepeatedFastPruning) {
//            int desiredWindowSize = debuggingAxioms.size() / 30;
//
//            if(desiredWindowSize == fastPruningWindowSize) {
//                fastPruningWindowSize = desiredWindowSize / 3;
//
//            }
//            else {
//                fastPruningWindowSize = desiredWindowSize;
//            }
//            if(fastPruningWindowSize < 1) {
//                fastPruningWindowSize = 1;
//            }
//        }
//        else {
//            fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
//        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info("     - Window size: " + fastPruningWindowSize);
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
            if (isSatisfiable()) {
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
            if (isSatisfiable()) {
                debuggingAxioms.addAll(axiomWindow);
            }
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info("    - End of fast pruning");
        }
    }


    private void performSlowPruning() throws OWLException {
        // Simply remove axioms one at a time.  If the class
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

    ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Creation of debugging ontology and satisfiability testing
    //
    ///////////////////////////////////////////////////////////////////////////////////////////

    private int satTestCount = 0;


    /**
     * Tests the satisfiability of the test class.  The ontology
     * is recreated before the test is performed.
     */
    private boolean isSatisfiable() throws OWLException {
        createDebuggingOntology();
//        RDFXMLRenderer ren = new RDFXMLRenderer();
//        ren.setOWLOntologyManager(owlOntologyManager);
//        ren.render(debuggingOntology, IRI.create("file:/Users/matthewhorridge/Desktop/DebuggingOntology" + ontologyCounter + ".owlapi"));
        ontologyCounter++;
        OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(debuggingOntology);
        satTestCount++;
        boolean sat = reasoner.isSatisfiable(currentClass);
        reasoner.dispose();
        return sat;
    }


    int ontologyCounter = 0;


    private void createDebuggingOntology() throws OWLException {
        //System.out.println("Debugging ontology: " + ontologyCounter);
        if (debuggingOntology != null) {
            owlOntologyManager.removeOntology(debuggingOntology);
        }
        IRI iri = createIRI();
        SimpleIRIMapper mapper = new SimpleIRIMapper(iri, iri);
        owlOntologyManager.addIRIMapper(mapper);
        debuggingOntology = owlOntologyManager.createOntology(iri);
        owlOntologyManager.removeIRIMapper(mapper);
        List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        for (OWLAxiom ax : debuggingAxioms) {
            changes.add(new AddAxiom(debuggingOntology, ax));
        }
        for (OWLAxiom ax : temporaryAxioms) {
            changes.add(new AddAxiom(debuggingOntology, ax));
        }

        // Ensure the ontology contains the signature of the class which is being debugged
        OWLDataFactory factory = owlOntologyManager.getOWLDataFactory();
        OWLAxiom ax = factory.getOWLSubClassOfAxiom(currentClass, factory.getOWLThing());
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

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Initial axiom count: " + debuggingAxioms.size());
        }

        int totalAdded = 0;
        int expansionCount = 0;
        while (isSatisfiable()) {

            if (logger.isLoggable(Level.INFO)) {
                logger.info("Expanding axioms (expansion " + expansionCount + ")");
            }

            expansionCount++;
            int numberAdded = expandAxioms();
            totalAdded += numberAdded;

            if (logger.isLoggable(Level.INFO)) {
                logger.info("    ... expanded by " + numberAdded);
            }

            if (numberAdded == 0) {

                if (logger.isLoggable(Level.INFO)) {
                    logger.info("ERROR! Cannot find SOS axioms!");
                }

                debuggingAxioms.clear();
                return;
            }
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Total number of axioms added: " + totalAdded);
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.info("FOUND CLASH! Prunning " + debuggingAxioms.size() + " axioms...");
        }

        resetSatisfiabilityTestCounter();
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Fast pruning...");
        }

//        fastPruningWindowSize = 0;
        if (performRepeatedFastPruning) {
            // Base the initial fast pruning window size on the number of axioms
            fastPruningWindowSize = debuggingAxioms.size() / 10;
            if (fastPruningWindowSize < DEFAULT_FAST_PRUNING_WINDOW_SIZE) {
                fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
            }
            if (logger.isLoggable(Level.INFO)) {
                logger.info("    Initial fast prunung window size: " + fastPruningWindowSize);
            }
            int fastPruningCounter = 0;
            while (fastPruningWindowSize != 1) {
                if (logger.isLoggable(Level.INFO)) {
                    logger.info("    Round: " + fastPruningCounter + " (axioms to prune: " + debuggingAxioms.size() + ")");
                }
                fastPruningCounter++;
                performFastPruning();
                fastPruningWindowSize = fastPruningWindowSize / 3;
                if (fastPruningWindowSize < 1) {
                    fastPruningWindowSize = 1;
                }
            }
            if (logger.isLoggable(Level.INFO)) {
                logger.info("... end of fast pruning. Axioms remaining: " + debuggingAxioms.size());
                logger.info("Performed " + satTestCount + " satisfiability tests during fast pruning");
            }
        }
        else {
            fastPruningWindowSize = DEFAULT_FAST_PRUNING_WINDOW_SIZE;
            performFastPruning();
            if (logger.isLoggable(Level.INFO)) {
                logger.info("... end of fast pruning. Axioms remaining: " + debuggingAxioms.size());
                logger.info("Performed " + satTestCount + " satisfiability tests during fast pruning");
            }
        }


        int totalSatTests = satTestCount;

        resetSatisfiabilityTestCounter();
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Slow pruning...");
        }

        performSlowPruning();

        if (logger.isLoggable(Level.INFO)) {
            logger.info("... end of slow pruning");
            logger.info("Performed " + satTestCount + " satisfiability tests during slow pruning");
        }
        totalSatTests += satTestCount;
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Total number of satisfiability tests performed: " + totalSatTests);
        }
    }


    private static IRI createIRI() {
        return IRI.create("http://debugging.blackbox#" + System.nanoTime());
    }
}
