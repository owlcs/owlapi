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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.modularity.OntologySegmenter;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(SyntacticLocalityModuleExtractor.class);

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
        final @Nonnull OWLAxiom[] ax;

        /**
         * Creates a new OntologyAxiomSet from a given set of axioms without
         * looking up the referenced entities.
         * 
         * @param axs
         *        the set of axioms representing the ontology
         */
        OntologyAxiomSet(List<OWLAxiom> axs) {
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
        public OWLAxiom getAxiom(int i) {
            return ax[i];
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
        public boolean[] getSubset(boolean init) {
            boolean[] subset = new boolean[ax.length];
            Arrays.fill(subset, init);
            return subset;
        }

        /**
         * Clones an array of Booleans that represents a subset of this set.
         * 
         * @param oldSubset
         *        an array representing the original subset
         * @return an array representing the new subset
         */
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
        public Set<OWLAxiom> toSet(boolean[] subset) {
            HashSet<OWLAxiom> axs = new HashSet<>();
            for (int i = 0; i < ax.length; i++) {
                if (subset[i]) {
                    axs.add(ax[i]);
                }
            }
            return axs;
        }
    }

    @Nonnull /** Type of module. */
    private ModuleType moduleType;
    /** Represents the associated ontology. */
    private final OntologyAxiomSet ontologyAxiomSet;
    private final IRI rootOntology;
    private final @Nonnull OWLOntology ontology;
    /** Represents the manager for the associated ontology. */
    private final OWLOntologyManager manager;

    /**
     * Creates a new module extractor for a subset of a given ontology, its
     * manager, and a specified type of locality.
     * 
     * @param man
     *        the manager for the associated ontology
     * @param iri
     *        IRI to exclude from logging
     * @param axs
     *        the subset of the ontology as a set of axioms
     * @param moduleType
     *        the type of module this extractor will construct
     */
    public SyntacticLocalityModuleExtractor(OWLOntologyManager man, IRI iri, Stream<OWLAxiom> axs,
        ModuleType moduleType) {
        this.moduleType = checkNotNull(moduleType, "moduleType cannot be null");
        manager = checkNotNull(man, "man cannot be null");
        rootOntology = iri;
        List<OWLAxiom> collect = asList(axs);
        ontologyAxiomSet = new OntologyAxiomSet(collect);
        try {
            ontology = checkNotNull(man.createOntology(collect));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
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
    public SyntacticLocalityModuleExtractor(OWLOntologyManager man, OWLOntology ont, ModuleType moduleType) {
        this(man, ont.getOntologyID().getOntologyIRI().orElse(null), asAxiomSet(ont), moduleType);
    }

    private static Stream<OWLAxiom> asAxiomSet(OWLOntology ont) {
        return ont.axioms(Imports.INCLUDED);
    }

    /**
     * Changes the module type for this extractor without deleting the stored
     * referenced entities.
     * 
     * @param moduleType
     *        the new type of module
     */
    public void setModuleType(ModuleType moduleType) {
        this.moduleType = checkNotNull(moduleType, "moduleType cannot be null");
    }

    /**
     * Returns the module type for this extractor.
     * 
     * @return module type for this extractor
     */
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
     * @return an array of Booleans representing the module
     */
    boolean[] extractLogicalAxioms(boolean[] subOnt, Set<OWLEntity> signature, LocalityClass localityClass) {
        boolean[] mod = ontologyAxiomSet.getSubset(false);
        boolean[] q2 = ontologyAxiomSet.cloneSubset(subOnt);
        SyntacticLocalityEvaluator sle = new SyntacticLocalityEvaluator(localityClass);
        boolean change = true;
        int loopNumber = 0;
        while (change) {
            change = false;
            loopNumber++;
            LOGGER.info("  Loop {}", loopNumber);
            for (int i = 0; i < q2.length; i++) {
                if (q2[i]) {
                    if (!sle.isLocal(ontologyAxiomSet.getAxiom(i), signature)) {
                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("      Non-local axiom:   {}",
                                minusOntologyURI(ontologyAxiomSet.getAxiom(i).toString()));
                        }
                        mod[i] = true;
                        q2[i] = false;
                        int oldSize = signature.size();
                        add(ontologyAxiomSet.getAxiom(i).signature(), signature);
                        // only triggering a change when the signature has
                        // changed doesn't improve performance
                        if (signature.size() > oldSize) {
                            change = true;
                            LOGGER.info("    New signature:   {}", signature);
                        }
                    } else {
                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("      Local axiom:       {}",
                                minusOntologyURI(ontologyAxiomSet.getAxiom(i).toString()));
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
    Set<OWLAxiom> extract(Set<OWLAxiom> subOnt, Set<OWLEntity> signature, LocalityClass localityClass,
        boolean verbose) {
        HashSet<OWLAxiom> mod = new HashSet<>();
        HashSet<OWLAxiom> q2 = new HashSet<>(subOnt);
        SyntacticLocalityEvaluator sle = new SyntacticLocalityEvaluator(localityClass);
        boolean change = true;
        int loopNumber = 0;
        while (change) {
            change = false;
            loopNumber++;
            if (verbose) {
                LOGGER.info("  Loop {}", loopNumber);
            }
            HashSet<OWLAxiom> q2remove = new HashSet<>();
            for (OWLAxiom ax : q2) {
                if (!sle.isLocal(ax, signature)) {
                    if (verbose) {
                        LOGGER.info("      Non-local axiom:   {}", minusOntologyURI(ax.toString()));
                    }
                    mod.add(ax);
                    q2remove.add(ax);
                    int oldSize = signature.size();
                    add(ax.signature(), signature);
                    // only triggering a change when the signature has changed
                    // doesn't improve performance
                    if (signature.size() > oldSize) {
                        change = true;
                        if (verbose) {
                            LOGGER.info("    New signature:   {}", signature);
                        }
                    }
                } else {
                    if (verbose) {
                        LOGGER.info("      Local axiom:       {}", minusOntologyURI(ax.toString()));
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
     * @return a set of axioms representing the enriched module
     */
    Set<OWLAxiom> enrich(Set<OWLAxiom> module, Set<OWLEntity> sig) {
        Set<OWLAxiom> enrichedModule = new HashSet<>(module);
        LOGGER.info("\nEnriching with declaration axioms, annotation axioms, same/different individual axioms ...");
        // Adding all entity declaration axioms
        // Adding all entity annotation axioms
        for (OWLEntity entity : sig) {
            List<OWLDeclarationAxiom> declarationAxioms = asList(ontology.declarationAxioms(entity));
            enrichedModule.addAll(declarationAxioms);
            if (LOGGER.isInfoEnabled()) {
                declarationAxioms.forEach(
                    a -> LOGGER.info("  Added entity declaration axiom:   {}", minusOntologyURI(a.toString())));
            }
        }
        Set<IRI> iris = new HashSet<>(sig.size());
        for (OWLEntity i : sig) {
            iris.add(i.getIRI());
        }
        ontology.axioms(AxiomType.ANNOTATION_ASSERTION).forEach(annotation -> {
            if (iris.contains(annotation.getSubject())) {
                enrichedModule.add(annotation);
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("  Added entity annotation axiom:   {}", minusOntologyURI(annotation.toString()));
                }
            }
        });
        // Adding all same-individuals axioms
        // Adding all different-individuals axioms
        for (OWLEntity entity : sig) {
            if (entity.isOWLNamedIndividual()) {
                List<OWLSameIndividualAxiom> sameIndividualAxioms = asList(
                    ontology.sameIndividualAxioms(entity.asOWLNamedIndividual()));
                enrichedModule.addAll(sameIndividualAxioms);
                if (LOGGER.isInfoEnabled()) {
                    sameIndividualAxioms.forEach(
                        i -> LOGGER.info("  Added same individual axiom:   {}", minusOntologyURI(i.toString())));
                }
                List<OWLDifferentIndividualsAxiom> differentIndividualAxioms = asList(
                    ontology.differentIndividualAxioms(entity.asOWLNamedIndividual()));
                enrichedModule.addAll(differentIndividualAxioms);
                if (LOGGER.isInfoEnabled()) {
                    differentIndividualAxioms.forEach(a -> LOGGER.info("  Added different individual axiom:   {}",
                        minusOntologyURI(a.toString())));
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
    String minusOntologyURI(String s) {
        String uri = rootOntology != null ? rootOntology + "#" : "";
        return s.replace(uri, "").replace("<", "").replace(">", "");
    }

    /**
     * Output signature.
     * 
     * @param preamble
     *        the preamble
     * @param sig
     *        the sig
     */
    void outputSignature(String preamble, Set<OWLEntity> sig) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(preamble);
            sig.forEach(e -> LOGGER.info("  {}", minusOntologyURI(e.toString())));
        }
    }

    /**
     * Extract unnested module.
     * 
     * @param sig
     *        the sig
     * @param cls
     *        the cls
     * @return the sets the
     */
    Set<OWLAxiom> extractUnnestedModule(Set<OWLEntity> sig, LocalityClass cls) {
        outputSignature("\nExtracting " + cls + " module for the following seed signature ... ", sig);
        boolean[] subOnt = ontologyAxiomSet.getSubset(true);
        Set<OWLEntity> signature = new HashSet<>(sig);
        boolean[] module = extractLogicalAxioms(subOnt, signature, cls);
        Set<OWLAxiom> moduleAsSet = ontologyAxiomSet.toSet(module);
        return enrich(moduleAsSet, signature);
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
    static Set<OWLClass> SuperOrSubClasses(int superOrSubClassLevel, boolean superVsSub, @Nullable OWLReasoner reasoner,
        Set<OWLClass> classesInSig) {
        checkNotNull(reasoner);
        assert reasoner != null;
        Set<OWLClass> superOrSubClasses = new HashSet<>();
        if (superOrSubClassLevel < 0) {
            for (OWLClassExpression ent : classesInSig) {
                NodeSet<OWLClass> nodes;
                if (superVsSub) {
                    nodes = reasoner.getSuperClasses(ent, false);
                } else {
                    nodes = reasoner.getSubClasses(ent, false);
                }
                add(superOrSubClasses, nodes.entities());
            }
        } else if (superOrSubClassLevel > 0) {
            Queue<OWLClass> toBeSuClassedNow;
            Queue<OWLClass> toBeSuClassedNext = new LinkedList<>(classesInSig);
            Queue<OWLClass> suClassesToBeAdded = new LinkedList<>();
            for (int i = 0; i < superOrSubClassLevel; i++) {
                toBeSuClassedNow = toBeSuClassedNext;
                toBeSuClassedNext = new LinkedList<>();
                for (OWLClassExpression ce : toBeSuClassedNow) {
                    Set<OWLClass> suClasses;
                    if (superVsSub) {
                        suClasses = asSet(reasoner.getSuperClasses(ce, true).entities());
                    } else {
                        suClasses = asSet(reasoner.getSubClasses(ce, true).entities());
                    }
                    for (OWLClass suClass : suClasses) {
                        if (!classesInSig.contains(suClass) && !suClassesToBeAdded.contains(suClass)) {
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
    Set<OWLEntity> enrichSignature(Set<OWLEntity> sig, int superClassLevel, int subClassLevel,
        @Nullable OWLReasoner reasoner) {
        Set<OWLEntity> enrichedSig = new HashSet<>(sig);
        Set<OWLClass> classesInSig = new HashSet<>();
        for (OWLEntity ent : sig) {
            if (ent.isOWLClass()) {
                classesInSig.add(ent.asOWLClass());
            }
        }
        if (superClassLevel != 0) {
            enrichedSig.addAll(SuperOrSubClasses(superClassLevel, true, reasoner, classesInSig));
        }
        if (subClassLevel != 0) {
            enrichedSig.addAll(SuperOrSubClasses(subClassLevel, false, reasoner, classesInSig));
        }
        return enrichedSig;
    }

    @Override
    public Set<OWLAxiom> extract(Set<OWLEntity> signature) {
        return extract(signature, 0, 0, null);
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
     *        <li>If superClassLevel = 0, then no super-/subclasses are added.
     *        </li>
     *        <li>If superClassLevel lesser than 0, then all direct and indirect
     *        super-/subclasses of any class in the signature are added.</li>
     *        </ul>
     * @param reasoner
     *        the reasoner to determine super-/subclasses. This can be an
     *        arbitrary reasoner, including a ToldClassHierarchyReasoner. It
     *        must have loaded the ontology. Can be null if superClassLevel and
     *        subClassLevel are 0.
     * @return the module
     */
    @Override
    public Set<OWLAxiom> extract(Set<OWLEntity> sig, int superClassLevel, int subClassLevel,
        @Nullable OWLReasoner reasoner) {
        Set<OWLEntity> enrichedSig = enrichSignature(sig, superClassLevel, subClassLevel, reasoner);
        switch (moduleType) {
            case TOP:
                return extractUnnestedModule(enrichedSig, LocalityClass.TOP_TOP);
            case BOT:
                return extractUnnestedModule(enrichedSig, LocalityClass.BOTTOM_BOTTOM);
            case STAR:
                boolean[] subOnt = ontologyAxiomSet.getSubset(true);
                boolean nextStepNecessary = true;
                boolean inFirstStep = true;
                LocalityClass localityClass = LocalityClass.BOTTOM_BOTTOM;
                Set<OWLEntity> seedSig = new HashSet<>(enrichedSig);
                while (nextStepNecessary) {
                    outputSignature("\nExtracting " + localityClass + " module for the following seed signature: ",
                        enrichedSig);
                    int previousModuleSize = ontologyAxiomSet.subsetCardinality(subOnt);
                    seedSig = new HashSet<>(enrichedSig);
                    subOnt = extractLogicalAxioms(subOnt, seedSig, localityClass);
                    if (ontologyAxiomSet.subsetCardinality(subOnt) == previousModuleSize && !inFirstStep) {
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
                return enrich(moduleAsSet, seedSig);
            default:
                throw new OWLRuntimeException("Unsupported module type: " + moduleType);
        }
    }

    @Override
    public OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI iri) throws OWLOntologyCreationException {
        return extractAsOntology(signature, iri, 0, 0, null);
    }

    @Override
    public OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI iri, int superClassLevel, int subClassLevel,
        @Nullable OWLReasoner reasoner) throws OWLOntologyCreationException {
        return manager.createOntology(extract(signature, superClassLevel, subClassLevel, reasoner), iri);
    }
}
