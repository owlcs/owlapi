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
package uk.ac.manchester.cs.owl.explanation.ordering;

import static org.semanticweb.owlapi.model.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

/**
 * Provides ordering and indenting of explanations based on various ordering
 * heuristics.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class ExplanationOrdererImpl implements ExplanationOrderer {

    private Set<OWLAxiom> currentExplanation;
    private final Map<OWLEntity, Set<OWLAxiom>> lhs2AxiomMap = new HashMap<OWLEntity, Set<OWLAxiom>>();
    private final Map<OWLAxiom, Set<OWLEntity>> entitiesByAxiomRHS = new HashMap<OWLAxiom, Set<OWLEntity>>();
    private final SeedExtractor seedExtractor = new SeedExtractor();
    private final OWLOntologyManager man;
    private OWLOntology ont;
    private final Map<OWLObject, Set<OWLAxiom>> mappedAxioms = new HashMap<OWLObject, Set<OWLAxiom>>();
    private final Set<OWLAxiom> consumedAxioms = new HashSet<OWLAxiom>();
    private final Set<AxiomType<?>> passTypes = new HashSet<AxiomType<?>>();

    /**
     * Instantiates a new explanation orderer impl.
     * 
     * @param m
     *        the manager to use
     */
    public ExplanationOrdererImpl(@Nonnull OWLOntologyManager m) {
        currentExplanation = Collections.emptySet();
        man = checkNotNull(m, "m cannot be null");
        // I'm not sure what to do with disjoint classes yet. At the
        // moment, we just shove them at the end at the top level.
        passTypes.add(AxiomType.DISJOINT_CLASSES);
    }

    private void reset() {
        lhs2AxiomMap.clear();
        entitiesByAxiomRHS.clear();
        consumedAxioms.clear();
    }

    @Override
    public ExplanationTree getOrderedExplanation(OWLAxiom entailment,
            Set<OWLAxiom> axioms) {
        currentExplanation = new HashSet<OWLAxiom>(axioms);
        buildIndices();
        ExplanationTree root = new EntailedAxiomTree(entailment);
        insertChildren(seedExtractor.getSource(entailment), root);
        OWLEntity currentTarget = seedExtractor.getTarget(entailment);
        Set<OWLAxiom> axs = root.getUserObjectClosure();
        List<OWLAxiom> rootAxioms = new ArrayList<OWLAxiom>();
        for (OWLAxiom ax : axioms) {
            if (!axs.contains(ax)) {
                rootAxioms.add(ax);
            }
        }
        Collections.sort(rootAxioms, new TargetAxiomsComparator(
                getTargetAxioms(currentTarget)));
        for (OWLAxiom ax : rootAxioms) {
            root.addChild(new ExplanationTree(ax));
        }
        return root;
    }

    /**
     * Gets the target axioms.
     * 
     * @param currentTarget
     *        the current target
     * @return the target axioms
     */
    @Nonnull
    private Set<OWLAxiom> getTargetAxioms(@Nonnull OWLEntity currentTarget) {
        Set<OWLAxiom> targetAxioms = new HashSet<OWLAxiom>();
        if (currentTarget.isOWLClass()) {
            targetAxioms.addAll(ont.getAxioms(currentTarget.asOWLClass(),
                    EXCLUDED));
        }
        if (currentTarget.isOWLObjectProperty()) {
            targetAxioms.addAll(ont.getAxioms(
                    currentTarget.asOWLObjectProperty(), EXCLUDED));
        }
        if (currentTarget.isOWLDataProperty()) {
            targetAxioms.addAll(ont.getAxioms(
                    currentTarget.asOWLDataProperty(), EXCLUDED));
        }
        if (currentTarget.isOWLNamedIndividual()) {
            targetAxioms.addAll(ont.getAxioms(
                    currentTarget.asOWLNamedIndividual(), EXCLUDED));
        }
        return targetAxioms;
    }

    @Nonnull
    private List<OWLEntity> getRHSEntitiesSorted(@Nonnull OWLAxiom ax) {
        Collection<OWLEntity> entities = getRHSEntities(ax);
        List<OWLEntity> sortedEntities = new ArrayList<OWLEntity>(entities);
        Collections.sort(sortedEntities, propertiesFirstComparator);
        return sortedEntities;
    }

    private void insertChildren(@Nonnull OWLEntity entity,
            @Nonnull ExplanationTree tree) {
        Set<OWLAxiom> currentPath = new HashSet<OWLAxiom>(
                tree.getUserObjectPathToRoot());
        Set<? extends OWLAxiom> axioms = Collections.emptySet();
        if (entity.isOWLClass()) {
            axioms = ont.getAxioms(entity.asOWLClass(), EXCLUDED);
        } else if (entity.isOWLObjectProperty()) {
            axioms = ont.getAxioms(entity.asOWLObjectProperty(), EXCLUDED);
        } else if (entity.isOWLDataProperty()) {
            axioms = ont.getAxioms(entity.asOWLDataProperty(), EXCLUDED);
        } else if (entity.isOWLNamedIndividual()) {
            axioms = ont.getAxioms(entity.asOWLNamedIndividual(), EXCLUDED);
        }
        for (OWLAxiom ax : axioms) {
            if (passTypes.contains(ax.getAxiomType())) {
                continue;
            }
            Set<OWLAxiom> mapped = getIndexedSet(entity, mappedAxioms, true);
            if (consumedAxioms.contains(ax) || mapped.contains(ax)
                    || currentPath.contains(ax)) {
                continue;
            }
            mapped.add(ax);
            consumedAxioms.add(ax);
            ExplanationTree child = new ExplanationTree(ax);
            tree.addChild(child);
            for (OWLEntity ent : getRHSEntitiesSorted(ax)) {
                insertChildren(ent, child);
            }
        }
        sortChildrenAxioms(tree);
    }

    /** The comparator. */
    private static Comparator<Tree<OWLAxiom>> comparator = new OWLAxiomTreeComparator();

    private void sortChildrenAxioms(@Nonnull ExplanationTree tree) {
        tree.sortChildren(comparator);
    }

    private static AtomicLong randomstart = new AtomicLong(
            System.currentTimeMillis());

    private void buildIndices() {
        reset();
        AxiomMapBuilder builder = new AxiomMapBuilder();
        for (OWLAxiom ax : currentExplanation) {
            ax.accept(builder);
        }
        try {
            if (ont != null) {
                man.removeOntology(ont);
            }
            ont = man.createOntology(IRI.create("http://www.semanticweb.org/",
                    "ontology" + randomstart.incrementAndGet()));
            List<AddAxiom> changes = new ArrayList<AddAxiom>();
            for (OWLAxiom ax : currentExplanation) {
                changes.add(new AddAxiom(ont, ax));
                ax.accept(builder);
            }
            man.applyChanges(changes);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /**
     * A utility method that obtains a set of axioms that are indexed by some
     * object.
     * 
     * @param <K>
     *        the key type
     * @param <E>
     *        the element type
     * @param obj
     *        The object that indexed the axioms
     * @param map
     *        The map that provides the index structure
     * @param addIfEmpty
     *        A flag that indicates whether an empty set of axiom should be
     *        added to the index if there is not value present for the indexing
     *        object.
     * @return A set of axioms (may be empty)
     */
    @Nonnull
    private static <K, E> Set<E> getIndexedSet(@Nonnull K obj,
            @Nonnull Map<K, Set<E>> map, boolean addIfEmpty) {
        Set<E> values = map.get(obj);
        if (values == null) {
            values = new HashSet<E>();
            if (addIfEmpty) {
                map.put(obj, values);
            }
        }
        return values;
    }

    /**
     * Gets axioms that have a LHS corresponding to the specified entity.
     * 
     * @param lhs
     *        The entity that occurs on the left hand side of the axiom.
     * @return A set of axioms that have the specified entity as their left hand
     *         side.
     */
    @Nonnull
    protected Set<OWLAxiom> getAxiomsForLHS(@Nonnull OWLEntity lhs) {
        return getIndexedSet(lhs, lhs2AxiomMap, true);
    }

    /**
     * Gets the rHS entities.
     * 
     * @param axiom
     *        the axiom
     * @return the rHS entities
     */
    @Nonnull
    private Collection<OWLEntity> getRHSEntities(@Nonnull OWLAxiom axiom) {
        return getIndexedSet(axiom, entitiesByAxiomRHS, true);
    }

    /**
     * Index axioms by rhs entities.
     * 
     * @param rhs
     *        the rhs
     * @param axiom
     *        the axiom
     */
    protected void indexAxiomsByRHSEntities(@Nonnull OWLObject rhs,
            @Nonnull OWLAxiom axiom) {
        getIndexedSet(axiom, entitiesByAxiomRHS, true).addAll(
                rhs.getSignature());
    }

    /** The Class TargetAxiomsComparator. */
    private static class TargetAxiomsComparator implements Comparator<OWLAxiom> {

        private final Set<OWLAxiom> targetAxioms;

        /**
         * Instantiates a new target axioms comparator.
         * 
         * @param targetAxioms
         *        the target axioms
         */
        TargetAxiomsComparator(@Nonnull Set<OWLAxiom> targetAxioms) {
            this.targetAxioms = checkNotNull(targetAxioms,
                    "targetAxioms cannot be null");
        }

        @Override
        public int compare(OWLAxiom o1, OWLAxiom o2) {
            if (targetAxioms.contains(o1)) {
                return 1;
            }
            if (targetAxioms.contains(o2)) {
                return -1;
            }
            return 0;
        }
    }

    /** The Class PropertiesFirstComparator. */
    private static final class PropertiesFirstComparator implements
            Comparator<OWLObject> {

        public PropertiesFirstComparator() {}

        @Override
        public int compare(OWLObject o1, OWLObject o2) {
            if (o1 instanceof OWLProperty) {
                return -1;
            } else {
                if (o1.equals(o2)) {
                    return 0;
                }
                return 1;
            }
        }
    }

    /** The properties first comparator. */
    private static PropertiesFirstComparator propertiesFirstComparator = new PropertiesFirstComparator();

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /** tree comparator. */
    private static final class OWLAxiomTreeComparator implements
            Comparator<Tree<OWLAxiom>>, Serializable {

        private static final long serialVersionUID = 40000L;

        public OWLAxiomTreeComparator() {}

        @Override
        public int compare(Tree<OWLAxiom> o1, Tree<OWLAxiom> o2) {
            OWLAxiom ax1 = o1.getUserObject();
            OWLAxiom ax2 = o2.getUserObject();
            // Equivalent classes axioms always come last
            if (ax1 instanceof OWLEquivalentClassesAxiom) {
                return 1;
            }
            if (ax2 instanceof OWLEquivalentClassesAxiom) {
                return -1;
            }
            if (ax1 instanceof OWLPropertyAxiom) {
                return -1;
            }
            int diff = childDiff(o1, o2);
            if (diff != 0) {
                return diff;
            }
            if (ax1 instanceof OWLSubClassOfAxiom
                    && ax2 instanceof OWLSubClassOfAxiom) {
                OWLSubClassOfAxiom sc1 = (OWLSubClassOfAxiom) ax1;
                OWLSubClassOfAxiom sc2 = (OWLSubClassOfAxiom) ax2;
                return sc1.getSuperClass().compareTo(sc2.getSuperClass());
            }
            return 1;
        }

        private int childDiff(Tree<OWLAxiom> o1, Tree<OWLAxiom> o2) {
            int childCount1 = o1.getChildCount();
            childCount1 = childCount1 > 0 ? 0 : 1;
            int childCount2 = o2.getChildCount();
            childCount2 = childCount2 > 0 ? 0 : 1;
            int diff = childCount1 - childCount2;
            return diff;
        }
    }

    /** The Class SeedExtractor. */
    private static class SeedExtractor extends OWLAxiomVisitorAdapter {

        private OWLEntity source;
        private OWLEntity target;

        public SeedExtractor() {}

        /**
         * @param axiom
         *        the axiom
         * @return the source
         */
        public OWLEntity getSource(@Nonnull OWLAxiom axiom) {
            axiom.accept(this);
            return source;
        }

        /**
         * @param axiom
         *        the axiom
         * @return the target
         */
        public OWLEntity getTarget(@Nonnull OWLAxiom axiom) {
            axiom.accept(this);
            return target;
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            if (!axiom.getSubClass().isAnonymous()) {
                source = axiom.getSubClass().asOWLClass();
            }
            if (!axiom.getSuperClass().isOWLNothing()) {
                OWLClassExpression classExpression = axiom.getSuperClass();
                if (!classExpression.isAnonymous()) {
                    target = classExpression.asOWLClass();
                }
            }
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            for (OWLClassExpression ce : axiom.getClassExpressions()) {
                if (!ce.isAnonymous()) {
                    if (source == null) {
                        source = ce.asOWLClass();
                    } else if (target == null) {
                        target = ce.asOWLClass();
                    } else {
                        break;
                    }
                }
            }
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (!axiom.getSubProperty().isAnonymous()) {
                source = axiom.getSubProperty().asOWLObjectProperty();
            }
            if (!axiom.getSuperProperty().isAnonymous()) {
                target = axiom.getSuperProperty().asOWLObjectProperty();
            }
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                source = axiom.getIndividual().asOWLNamedIndividual();
                target = axiom.getClassExpression().asOWLClass();
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            for (OWLClass cls : axiom.getNamedClasses()) {
                if (source == null) {
                    source = cls;
                } else if (target == null) {
                    target = cls;
                } else {
                    break;
                }
            }
        }

        @Override
        public void visit(SWRLRule rule) {}
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    /** A visitor that indexes axioms by their left and right hand sides. */
    private class AxiomMapBuilder extends OWLAxiomVisitorAdapter {

        public AxiomMapBuilder() {}

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            if (!axiom.getSubClass().isAnonymous()) {
                getAxiomsForLHS(axiom.getSubClass().asOWLClass()).add(axiom);
                indexAxiomsByRHSEntities(axiom.getSuperClass(), axiom);
            }
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
        }

        @Override
        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    getAxiomsForLHS(desc.asOWLClass()).add(axiom);
                }
                indexAxiomsByRHSEntities(desc, axiom);
            }
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {
            getAxiomsForLHS(axiom.getProperty().asOWLDataProperty()).add(axiom);
            indexAxiomsByRHSEntities(axiom.getDomain(), axiom);
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
            indexAxiomsByRHSEntities(axiom.getDomain(), axiom);
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                if (!prop.isAnonymous()) {
                    getAxiomsForLHS(prop.asOWLObjectProperty()).add(axiom);
                }
                indexAxiomsByRHSEntities(prop, axiom);
            }
        }

        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {
            for (OWLIndividual ind : axiom.getIndividuals()) {
                if (!ind.isAnonymous()) {
                    getAxiomsForLHS(ind.asOWLNamedIndividual()).add(axiom);
                    indexAxiomsByRHSEntities(ind, axiom);
                }
            }
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                getAxiomsForLHS(prop.asOWLDataProperty()).add(axiom);
                indexAxiomsByRHSEntities(prop, axiom);
            }
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                if (!prop.isAnonymous()) {
                    getAxiomsForLHS(prop.asOWLObjectProperty()).add(axiom);
                }
                indexAxiomsByRHSEntities(prop, axiom);
            }
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
            indexAxiomsByRHSEntities(axiom.getRange(), axiom);
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (!axiom.getSubProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getSubProperty().asOWLObjectProperty())
                        .add(axiom);
            }
            indexAxiomsByRHSEntities(axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            getAxiomsForLHS(axiom.getOWLClass()).add(axiom);
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
        }

        @Override
        public void visit(OWLDataPropertyRangeAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLDataProperty()).add(
                        axiom);
            }
            indexAxiomsByRHSEntities(axiom.getRange(), axiom);
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLDataProperty()).add(
                        axiom);
            }
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                getAxiomsForLHS(prop.asOWLDataProperty()).add(axiom);
                indexAxiomsByRHSEntities(prop, axiom);
            }
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            if (!axiom.getIndividual().isAnonymous()) {
                getAxiomsForLHS(axiom.getIndividual().asOWLNamedIndividual())
                        .add(axiom);
                indexAxiomsByRHSEntities(axiom.getClassExpression(), axiom);
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    getAxiomsForLHS(desc.asOWLClass()).add(axiom);
                }
                indexAxiomsByRHSEntities(desc, axiom);
            }
        }

        @Override
        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            indexAxiomsByRHSEntities(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            getAxiomsForLHS(axiom.getSubProperty().asOWLDataProperty()).add(
                    axiom);
            indexAxiomsByRHSEntities(axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (!axiom.getProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getProperty().asOWLObjectProperty()).add(
                        axiom);
            }
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            for (OWLIndividual ind : axiom.getIndividuals()) {
                if (!ind.isAnonymous()) {
                    getAxiomsForLHS(ind.asOWLNamedIndividual()).add(axiom);
                    indexAxiomsByRHSEntities(ind, axiom);
                }
            }
        }

        @Override
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            if (!axiom.getFirstProperty().isAnonymous()) {
                getAxiomsForLHS(axiom.getFirstProperty().asOWLObjectProperty())
                        .add(axiom);
            }
            indexAxiomsByRHSEntities(axiom.getFirstProperty(), axiom);
            indexAxiomsByRHSEntities(axiom.getSecondProperty(), axiom);
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                indexAxiomsByRHSEntities(axiom.getClassExpression()
                        .asOWLClass(), axiom);
            }
        }
    }
}
