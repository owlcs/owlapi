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
package uk.ac.manchester.cs.owlapi.modularity;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.modularity.OntologySegmenter;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.search.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clarkparsia.owlapi.modularity.locality.LocalityClass;
import com.clarkparsia.owlapi.modularity.locality.SyntacticLocalityEvaluator;

/**
 * Implementation of module extraction based on syntactic locality.
 * 
 * @author Thomas Schneider, School of Computer Science, University of
 *         Manchester
 */
public class SyntacticLocalityModuleExtractor implements OntologySegmenter {

    private static final Logger logger = LoggerFactory
            .getLogger(SyntacticLocalityModuleExtractor.class);

    /**
     * Auxiliary inner class for the representation of the associated ontology
     * and all its sub-ontologies as arrays of axioms. Advantages: (1) quicker
     * set manipulation operations; (2) storage of all referenced entities of an
     * axiom once this axiom is dealt with.
     * 
     * @author Thomas Schneider
     * @author School of Computer Science
     * @author University of Manchester
     */
    static class OntologyAxiomSet {

        /** Array representing all axioms of the associated ontology. */
        @Nonnull
        final OWLAxiom[] ax;

        /**
         * Creates a new OntologyAxiomSet from a given set of axioms without
         * looking up the referenced entities.
         * 
         * @param axs
         *        the set of axioms representing the ontology
         */
        public OntologyAxiomSet(@Nonnull Set<OWLAxiom> axs) {
            ax = axs.toArray(new OWLAxiom[axs.size()]);
        }

        /**
         * Returns the number of axioms in this set.
         * 
         * @return the number of axioms in this set
         */
        public int size() {
            return ax.length;
        }

        /**
         * Returns some axiom from this set.
         * 
         * @param i
         *        a number for an axiom
         * @return the i-th axiom in this set
         */
        @Nonnull
        public OWLAxiom getAxiom(int i) {
            return ax[i];
        }

        /**
         * Returns an array containing all axioms in this set.
         * 
         * @return array containing all axioms in this set
         */
        @Nonnull
        public OWLAxiom[] getAllAxioms() {
            OWLAxiom[] toReturn = new OWLAxiom[ax.length];
            System.arraycopy(ax, 0, toReturn, 0, ax.length);
            return toReturn;
        }

        /**
         * Returns the set of axioms that is represented by some array of
         * Booleans.
         * 
         * @param isIn
         *        an array of Booleans
         * @return the set of axioms represented by the specified array of
         *         Booleans
         */
        @Nonnull
        public Set<OWLAxiom> getAxiomSet(boolean[] isIn) {
            HashSet<OWLAxiom> gas = new HashSet<OWLAxiom>();
            for (int i = 0; i < isIn.length; i++) {
                if (isIn[i]) {
                    gas.add(ax[i]);
                }
            }
            return gas;
        }

        /**
         * Constructs an array of Booleans that represents a subset of this set.
         * The subset either equals this set (if init==true) or is the empty set
         * (if init==false).
         * 
         * @param init
         *        determines the initial value of the subset
         * @return array of Booleans representing the specified subset
         */
        @Nonnull
        public boolean[] getSubset(boolean init) {
            boolean[] subset = new boolean[ax.length];
            for (int i = 0; i < ax.length; i++) {
                subset[i] = init;
            }
            return subset;
        }

        /**
         * Clones an array of Booleans that represents a subset of this set.
         * 
         * @param oldSubset
         *        an array representing the original subset
         * @return an array representing the new subset
         */
        @Nonnull
        public boolean[] cloneSubset(boolean[] oldSubset) {
            boolean[] newSubset = new boolean[ax.length];
            System.arraycopy(oldSubset, 0, newSubset, 0, ax.length);
            return newSubset;
        }

        /**
         * Subset cardinality.
         * 
         * @param subset
         *        the subset
         * @return the int
         */
        public int subsetCardinality(boolean[] subset) {
            int card = 0;
            for (int i = 0; i < ax.length; i++) {
                if (subset[i]) {
                    card++;
                }
            }
            return card;
        }

        /**
         * Transforms a subset of this set (represented by an array of Booleans)
         * into a set of axioms.
         * 
         * @param subset
         *        an array representing the subset
         * @return a set of axioms
         */
        @Nonnull
        public Set<OWLAxiom> toSet(boolean[] subset) {
            HashSet<OWLAxiom> axs = new HashSet<OWLAxiom>();
            for (int i = 0; i < ax.length; i++) {
                if (subset[i]) {
                    axs.add(ax[i]);
                }
            }
            return axs;
        }
    }

    /** Type of module. */
    @Nonnull
    private ModuleType moduleType;
    /** Represents the associated ontology. */
    private final OntologyAxiomSet ontologyAxiomSet;
    /** The ontology. */
    @Nonnull
    private final OWLOntology rootOntology;
    @Nonnull
    private final OWLOntology ontology;
    /** Represents the manager for the associated ontology. */
    private final OWLOntologyManager manager;

    /**
     * Creates the ontology.
     * 
     * @param man
     *        the man
     * @param ont
     *        the ont
     * @param axs
     *        the axs
     * @return the oWL ontology
     */
    @Nonnull
    private static OWLOntology createOntology(@Nonnull OWLOntologyManager man,
            @Nonnull OWLOntology ont, @Nonnull Set<OWLAxiom> axs) {
        try {
            return man.createOntology(axs);
        } catch (OWLOntologyCreationException e) {
            return ont;
        }
    }

    /**
     * Creates a new module extractor for a subset of a given ontology, its
     * manager, and a specified type of locality.
     * 
     * @param man
     *        the manager for the associated ontology
     * @param ont
     *        the associated ontology
     * @param axs
     *        the subset of the ontology as a set of axioms
     * @param moduleType
     *        the type of module this extractor will construct
     */
    public SyntacticLocalityModuleExtractor(@Nonnull OWLOntologyManager man,
            @Nonnull OWLOntology ont, @Nonnull Set<OWLAxiom> axs,
            @Nonnull ModuleType moduleType) {
        this.moduleType = checkNotNull(moduleType, "moduleType cannot be null");
        manager = checkNotNull(man, "man cannot be null");
        rootOntology = checkNotNull(ont, "ont cannot be null");
        ontologyAxiomSet = new OntologyAxiomSet(axs);
        ontology = checkNotNull(createOntology(man, ont, axs));
    }

    /**
     * Creates a new module extractor for a given ontology, its manager, and a
     * specified type of locality.
     * 
     * @param man
     *        the manager for the associated ontology
     * @param ont
     *        the associated ontology
     * @param moduleType
     *        the type of module this extractor will construct
     */
    public SyntacticLocalityModuleExtractor(@Nonnull OWLOntologyManager man,
            @Nonnull OWLOntology ont, @Nonnull ModuleType moduleType) {
        this(man, ont, asAxiomSet(ont), moduleType);
    }

    @Nonnull
    private static Set<OWLAxiom> asAxiomSet(OWLOntology ont) {
        Set<OWLAxiom> axs = new HashSet<OWLAxiom>(ont.getAxioms());
        for (OWLOntology importedOnt : ont.getImportsClosure()) {
            axs.addAll(importedOnt.getAxioms());
        }
        return axs;
    }

    /**
     * Changes the module type for this extractor without deleting the stored
     * referenced entities.
     * 
     * @param moduleType
     *        the new type of module
     */
    public void setModuleType(@Nonnull ModuleType moduleType) {
        this.moduleType = checkNotNull(moduleType, "moduleType cannot be null");
    }

    /**
     * Returns the module type for this extractor.
     * 
     * @return module type for this extractor
     */
    @Nonnull
    public ModuleType getModuleType() {
        return moduleType;
    }

    /**
     * This auxiliary method extracts a module from a given sub-ontology of the
     * associated ontology for a given signature and locality type. The module
     * will contain only logical axioms, no annotation or declaration axioms.
     * The sub-ontology and module are represented as arrays of Booleans.
     * <p/>
     * This method is (if necessary, iteratively) called by the public method
     * extract.
     * 
     * @param subOnt
     *        an array of Booleans representing the sub-ontology
     * @param signature
     *        the seed signature (set of entities) for the module; on return of
     *        the method, this will contain the signature of the module
     * @param localityClass
     *        the type of locality
     * @param verbose
     *        a flag for verbose output (test purposes)
     * @return an array of Booleans representing the module
     */
    @Nonnull
    boolean[] extractLogicalAxioms(@Nonnull boolean[] subOnt,
            @Nonnull Set<OWLEntity> signature,
            @Nonnull LocalityClass localityClass, boolean verbose) {
        boolean[] mod = ontologyAxiomSet.getSubset(false);
        boolean[] q2 = ontologyAxiomSet.cloneSubset(subOnt);
        SyntacticLocalityEvaluator sle = new SyntacticLocalityEvaluator(
                localityClass);
        boolean change = true;
        int loopNumber = 0;
        while (change) {
            change = false;
            loopNumber++;
            if (verbose) {
                logger.info("  Loop {}", loopNumber);
            }
            for (int i = 0; i < q2.length; i = i + 1) {
                if (q2[i]) {
                    if (!sle.isLocal(ontologyAxiomSet.getAxiom(i), signature)) {
                        if (verbose) {
                            logger.info(
                                    "      Non-local axiom:   {}",
                                    minusOntologyURI(ontologyAxiomSet.getAxiom(
                                            i).toString()));
                        }
                        mod[i] = true;
                        q2[i] = false;
                        int oldSize = signature.size();
                        signature.addAll(ontologyAxiomSet.getAxiom(i)
                                .getSignature());
                        // only triggering a change when the signature has
                        // changed doesn't improve performance
                        if (signature.size() > oldSize) {
                            change = true;
                            if (verbose) {
                                logger.info("    New signature:   {}",
                                        signature);
                            }
                        }
                    } else {
                        if (verbose) {
                            logger.info(
                                    "      Local axiom:       {}",
                                    minusOntologyURI(ontologyAxiomSet.getAxiom(
                                            i).toString()));
                        }
                    }
                }
            }
        }
        return mod;
    }

    /**
     * This method extracts a module from a given sub-ontology of the associated
     * ontology for a given signature and locality type. The module will only
     * contain logical axioms, no annotation or declaration axioms. The
     * sub-ontology and module are represented as sets of axioms.
     * 
     * @param subOnt
     *        a set of axioms representing the sub-ontology
     * @param signature
     *        the seed signature (set of entities) for the module; on return of
     *        the method, this will contain the signature of the module
     * @param localityClass
     *        the type of locality
     * @param verbose
     *        a flag for verbose output (test purposes)
     * @return a set of axioms representing the module
     */
    @Nonnull
    Set<OWLAxiom> extract(@Nonnull Set<OWLAxiom> subOnt,
            @Nonnull Set<OWLEntity> signature,
            @Nonnull LocalityClass localityClass, boolean verbose) {
        HashSet<OWLAxiom> mod = new HashSet<OWLAxiom>();
        HashSet<OWLAxiom> q2 = new HashSet<OWLAxiom>(subOnt);
        SyntacticLocalityEvaluator sle = new SyntacticLocalityEvaluator(
                localityClass);
        boolean change = true;
        int loopNumber = 0;
        while (change) {
            change = false;
            loopNumber++;
            if (verbose) {
                logger.info("  Loop {}", loopNumber);
            }
            HashSet<OWLAxiom> q2remove = new HashSet<OWLAxiom>();
            for (OWLAxiom ax : q2) {
                if (!sle.isLocal(ax, signature)) {
                    if (verbose) {
                        logger.info("      Non-local axiom:   {}",
                                minusOntologyURI(ax.toString()));
                    }
                    mod.add(ax);
                    q2remove.add(ax);
                    int oldSize = signature.size();
                    signature.addAll(ax.getSignature());
                    // only triggering a change when the signature has changed
                    // doesn't improve performance
                    if (signature.size() > oldSize) {
                        change = true;
                        if (verbose) {
                            logger.info("    New signature:   {}", signature);
                        }
                    }
                } else {
                    if (verbose) {
                        logger.info("      Local axiom:       {}",
                                minusOntologyURI(ax.toString()));
                    }
                }
            }
            q2.removeAll(q2remove);
        }
        return mod;
    }

    /**
     * This method enriches a module that contains only logical axioms with all
     * necessary entity declaration axioms, entity annotation axioms, and axiom
     * annotation axioms. The module and enriched module are represented as sets
     * of axioms.
     * 
     * @param module
     *        a set of axioms representing the original module
     * @param sig
     *        a set of entities representing the signature of the original
     *        module
     * @param verbose
     *        a flag for verbose output (test purposes)
     * @return a set of axioms representing the enriched module
     */
    @Nonnull
    Set<OWLAxiom> enrich(@Nonnull Set<OWLAxiom> module,
            @Nonnull Set<OWLEntity> sig, boolean verbose) {
        Set<OWLAxiom> enrichedModule = new HashSet<OWLAxiom>(module);
        if (verbose) {
            logger.info("\nEnriching with declaration axioms, annotation axioms, same/different individual axioms ...");
        }
        // Adding all entity declaration axioms
        // Adding all entity annotation axioms
        for (OWLEntity entity : sig) {
            Set<OWLDeclarationAxiom> declarationAxioms = ontology
                    .getDeclarationAxioms(entity);
            enrichedModule.addAll(declarationAxioms);
            if (verbose) {
                for (OWLDeclarationAxiom declarationAxiom : declarationAxioms) {
                    logger.info("  Added entity declaration axiom:   {}",
                            minusOntologyURI(declarationAxiom.toString()));
                }
            }
            Collection<OWLAxiom> axioms = ontology.filterAxioms(
                    Filters.annotations, entity.getIRI(), INCLUDED);
            enrichedModule.addAll(axioms);
            if (verbose) {
                for (OWLAxiom axiom : axioms) {
                    logger.info("  Added entity annotation axiom:   {}",
                            minusOntologyURI(axiom.toString()));
                }
            }
        }
        // Adding all same-individuals axioms
        // Adding all different-individuals axioms
        for (OWLEntity entity : sig) {
            if (OWLNamedIndividual.class.isAssignableFrom(entity.getClass())) {
                OWLIndividual individual = (OWLIndividual) entity;
                Set<OWLSameIndividualAxiom> sameIndividualAxioms = ontology
                        .getSameIndividualAxioms(individual);
                enrichedModule.addAll(sameIndividualAxioms);
                if (verbose) {
                    for (OWLSameIndividualAxiom sameIndividualAxiom : sameIndividualAxioms) {
                        logger.info(
                                "  Added same individual axiom:   {}",
                                minusOntologyURI(sameIndividualAxiom.toString()));
                    }
                }
                Set<OWLDifferentIndividualsAxiom> differentIndividualAxioms = ontology
                        .getDifferentIndividualAxioms(individual);
                enrichedModule.addAll(differentIndividualAxioms);
                if (verbose) {
                    for (OWLDifferentIndividualsAxiom differentIndividualsAxiom : differentIndividualAxioms) {
                        logger.info("  Added different individual axiom:   {}",
                                minusOntologyURI(differentIndividualsAxiom
                                        .toString()));
                    }
                }
            }
        }
        return enrichedModule;
    }

    /**
     * Minus ontology uri.
     * 
     * @param s
     *        the s
     * @return the string
     */
    @Nonnull
    String minusOntologyURI(@Nonnull String s) {
        String uri = manager.getOntologyDocumentIRI(rootOntology).toString()
                + "#";
        return s.replace(uri, "").replace("<", "").replace(">", "");
    }

    /**
     * Output signature.
     * 
     * @param preamble
     *        the preamble
     * @param sig
     *        the sig
     * @param verbose
     *        the verbose
     */
    void outputSignature(@Nonnull String preamble, @Nonnull Set<OWLEntity> sig,
            boolean verbose) {
        if (verbose) {
            logger.info(preamble);
            for (OWLEntity ent : sig) {
                logger.info("  {}", minusOntologyURI(ent.toString()));
            }
        }
    }

    /**
     * Extract unnested module.
     * 
     * @param sig
     *        the sig
     * @param cls
     *        the cls
     * @param verbose
     *        the verbose
     * @return the sets the
     */
    @Nonnull
    Set<OWLAxiom> extractUnnestedModule(@Nonnull Set<OWLEntity> sig,
            @Nonnull LocalityClass cls, boolean verbose) {
        outputSignature("\nExtracting " + cls
                + " module for the following seed signature ... ", sig, verbose);
        boolean[] subOnt = ontologyAxiomSet.getSubset(true);
        Set<OWLEntity> signature = new HashSet<OWLEntity>(sig);
        boolean[] module = extractLogicalAxioms(subOnt, signature, cls, verbose);
        Set<OWLAxiom> moduleAsSet = ontologyAxiomSet.toSet(module);
        return enrich(moduleAsSet, signature, verbose);
    }

    /**
     * Super or sub classes.
     * 
     * @param superOrSubClassLevel
     *        the super or sub class level
     * @param superVsSub
     *        the super vs sub
     * @param reasoner
     *        the reasoner
     * @param classesInSig
     *        the classes in sig
     * @return the sets the
     */
    @Nonnull
    Set<OWLClass> SuperOrSubClasses(int superOrSubClassLevel,
            boolean superVsSub, @Nonnull OWLReasoner reasoner,
            @Nonnull Set<OWLClass> classesInSig) {
        Set<OWLClass> superOrSubClasses = new HashSet<OWLClass>();
        if (superOrSubClassLevel < 0) {
            for (OWLClassExpression ent : classesInSig) {
                NodeSet<OWLClass> nodes;
                if (superVsSub) {
                    nodes = reasoner.getSuperClasses(ent, false);
                } else {
                    nodes = reasoner.getSubClasses(ent, false);
                }
                superOrSubClasses.addAll(nodes.getFlattened());
            }
        } else if (superOrSubClassLevel > 0) {
            Queue<OWLClass> toBeSuClassedNow;
            Queue<OWLClass> toBeSuClassedNext = new LinkedList<OWLClass>(
                    classesInSig);
            Queue<OWLClass> suClassesToBeAdded = new LinkedList<OWLClass>();
            for (int i = 0; i < superOrSubClassLevel; i++) {
                toBeSuClassedNow = toBeSuClassedNext;
                toBeSuClassedNext = new LinkedList<OWLClass>();
                for (OWLClassExpression ce : toBeSuClassedNow) {
                    Set<OWLClass> suClasses;
                    if (superVsSub) {
                        suClasses = reasoner.getSuperClasses(ce, true)
                                .getFlattened();
                    } else {
                        suClasses = reasoner.getSubClasses(ce, true)
                                .getFlattened();
                    }
                    for (OWLClass suClass : suClasses) {
                        if (!classesInSig.contains(suClass)
                                && !suClassesToBeAdded.contains(suClass)) {
                            toBeSuClassedNext.add(suClass);
                            suClassesToBeAdded.add(suClass);
                        }
                    }
                }
            }
            superOrSubClasses.addAll(suClassesToBeAdded);
        }
        return superOrSubClasses;
    }

    /**
     * Enrich signature.
     * 
     * @param sig
     *        the sig
     * @param superClassLevel
     *        the super class level
     * @param subClassLevel
     *        the sub class level
     * @param reasoner
     *        the reasoner
     * @return the sets the
     */
    @Nonnull
    Set<OWLEntity> enrichSignature(@Nonnull Set<OWLEntity> sig,
            int superClassLevel, int subClassLevel, OWLReasoner reasoner) {
        Set<OWLEntity> enrichedSig = new HashSet<OWLEntity>(sig);
        Set<OWLClass> classesInSig = new HashSet<OWLClass>();
        for (OWLEntity ent : sig) {
            if (OWLClass.class.isAssignableFrom(ent.getClass())) {
                classesInSig.add((OWLClass) ent);
            }
        }
        if (superClassLevel != 0) {
            enrichedSig.addAll(SuperOrSubClasses(superClassLevel, true,
                    reasoner, classesInSig));
        }
        if (subClassLevel != 0) {
            enrichedSig.addAll(SuperOrSubClasses(subClassLevel, false,
                    reasoner, classesInSig));
        }
        return enrichedSig;
    }

    @Override
    public Set<OWLAxiom> extract(Set<OWLEntity> sig) {
        return extract(sig, 0, 0, null, false);
    }

    @Override
    public Set<OWLAxiom> extract(Set<OWLEntity> sig, int superClassLevel,
            int subClassLevel, OWLReasoner reasoner) {
        return extract(sig, superClassLevel, subClassLevel, reasoner, false);
    }

    /**
     * Extracts a module from the associated ontology for a given signature and
     * the associated module type, and returns the module as a set of axioms.
     * The seed signature (set of entities) which determines the module is the
     * specified signature plus possibly all superclasses and/or subclasses of
     * the classes therein. Sub-/superclasses are determined using the specified
     * reasoner. The module will include annotation and declaration axioms for
     * all entities and axioms in it.
     * 
     * @param sig
     *        the seed signature (set of entities) for the module
     * @param superClassLevel
     *        determines whether superclasses are added to the signature before
     *        segment extraction, see below for admissible values
     * @param subClassLevel
     *        determines whether subclasses are added to the signature before
     *        segment extraction<br>
     *        Admissible values for superClassLevel (analogously for
     *        subClassLevel):
     *        <ul>
     *        <li>If superClassLevel greater than 0, then all classes C are
     *        included for which the class hierarchy computed by the reasoner
     *        contains a path of length at most superClassLevel downwards from C
     *        to some class from the signature.</li>
     *        <li>If superClassLevel = 0, then no super-/subclasses are added.</li>
     *        <li>If superClassLevel lesser than 0, then all direct and indirect
     *        super-/subclasses of any class in the signature are added.</li>
     *        </ul>
     * @param reasoner
     *        the reasoner to determine super-/subclasses. This can be an
     *        arbitrary reasoner, including a ToldClassHierarchyReasoner. It
     *        must have loaded the ontology. Can be null if superClassLevel and
     *        subClassLevel are 0.
     * @param verbose
     *        true if verbose output is required
     * @return the module
     */
    @Nonnull
    public Set<OWLAxiom> extract(@Nonnull Set<OWLEntity> sig,
            int superClassLevel, int subClassLevel, OWLReasoner reasoner,
            boolean verbose) {
        Set<OWLEntity> enrichedSig = enrichSignature(sig, superClassLevel,
                subClassLevel, reasoner);
        switch (moduleType) {
            case TOP: {
                return extractUnnestedModule(enrichedSig,
                        LocalityClass.TOP_TOP, verbose);
            }
            case BOT: {
                return extractUnnestedModule(enrichedSig,
                        LocalityClass.BOTTOM_BOTTOM, verbose);
            }
            case STAR: {
                boolean[] subOnt = ontologyAxiomSet.getSubset(true);
                boolean nextStepNecessary = true;
                boolean inFirstStep = true;
                LocalityClass localityClass = LocalityClass.BOTTOM_BOTTOM;
                Set<OWLEntity> seedSig = new HashSet<OWLEntity>(enrichedSig);
                while (nextStepNecessary) {
                    outputSignature("\nExtracting " + localityClass
                            + " module for the following seed signature: ",
                            enrichedSig, verbose);
                    int previousModuleSize = ontologyAxiomSet
                            .subsetCardinality(subOnt);
                    seedSig = new HashSet<OWLEntity>(enrichedSig);
                    subOnt = extractLogicalAxioms(subOnt, seedSig,
                            localityClass, verbose);
                    if (ontologyAxiomSet.subsetCardinality(subOnt) == previousModuleSize
                            && !inFirstStep) {
                        nextStepNecessary = false;
                    }
                    inFirstStep = false;
                    if (localityClass == LocalityClass.BOTTOM_BOTTOM) {
                        localityClass = LocalityClass.TOP_TOP;
                    } else {
                        localityClass = LocalityClass.BOTTOM_BOTTOM;
                    }
                }
                Set<OWLAxiom> moduleAsSet = ontologyAxiomSet.toSet(subOnt);
                return enrich(moduleAsSet, seedSig, verbose);
            }
            default:
                throw new OWLRuntimeException("Unsupported module type: "
                        + moduleType);
        }
    }

    @Override
    public OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI iri)
            throws OWLOntologyCreationException {
        return extractAsOntology(signature, iri, 0, 0, null, false);
    }

    @Override
    public OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI iri,
            int superClassLevel, int subClassLevel, OWLReasoner reasoner)
            throws OWLOntologyCreationException {
        return extractAsOntology(signature, iri, superClassLevel,
                subClassLevel, reasoner, false);
    }

    /**
     * Extract as ontology.
     * 
     * @param signature
     *        the signature
     * @param iri
     *        the iri
     * @param superClassLevel
     *        the super class level
     * @param subClassLevel
     *        the sub class level
     * @param reasoner
     *        the reasoner
     * @param verbose
     *        the verbose
     * @return the oWL ontology
     * @throws OWLOntologyCreationException
     *         the oWL ontology creation exception
     */
    @Nonnull
    OWLOntology extractAsOntology(@Nonnull Set<OWLEntity> signature,
            @Nonnull IRI iri, int superClassLevel, int subClassLevel,
            OWLReasoner reasoner, boolean verbose)
            throws OWLOntologyCreationException {
        Set<OWLAxiom> axs = extract(signature, superClassLevel, subClassLevel,
                reasoner, verbose);
        OWLOntology newOnt = manager.createOntology(iri);
        LinkedList<AddAxiom> addaxs = new LinkedList<AddAxiom>();
        for (OWLAxiom ax : axs) {
            addaxs.add(new AddAxiom(newOnt, ax));
        }
        manager.applyChanges(addaxs);
        return newOnt;
    }
}
