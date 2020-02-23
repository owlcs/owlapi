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

import static org.semanticweb.owlapi.util.CollectionFactory.createLinkedSet;
import static org.semanticweb.owlapi.util.CollectionFactory.createMap;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IsAnonymous;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
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
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * Provides ordering and indenting of explanations based on various ordering heuristics.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class ExplanationOrdererImplNoManager implements ExplanationOrderer {

    /**
     * The comparator.
     */
    protected static final Comparator<Tree<OWLAxiom>> COMPARATOR = (o1, o2) -> {
        OWLAxiom ax1 = o1.getUserObject();
        OWLAxiom ax2 = o2.getUserObject();
        if (Objects.equals(ax1, ax2)) {
            return 0;
        }
        // Equivalent classes axioms always come last
        if (ax1 instanceof OWLEquivalentClassesAxiom
            && !(ax2 instanceof OWLEquivalentClassesAxiom)) {
            return 1;
        }
        if (ax2 instanceof OWLEquivalentClassesAxiom
            && !(ax1 instanceof OWLEquivalentClassesAxiom)) {
            return -1;
        }
        // property axioms always first
        if (ax1 instanceof OWLPropertyAxiom && !(ax2 instanceof OWLPropertyAxiom)) {
            return -1;
        }
        if (ax2 instanceof OWLPropertyAxiom && !(ax1 instanceof OWLPropertyAxiom)) {
            return 1;
        }

        int diff = childDiff(o1, o2);
        if (diff != 0) {
            return diff;
        }
        if (ax1 instanceof OWLSubClassOfAxiom && ax2 instanceof OWLSubClassOfAxiom) {
            return ((OWLSubClassOfAxiom) ax1).getSuperClass()
                .compareTo(((OWLSubClassOfAxiom) ax2).getSuperClass());
        }
        return 0;
    };
    /**
     * The properties first comparator.
     */
    protected static final Comparator<OWLObject> PROPERTIESFIRST = (o1, o2) -> {
        if (o1.equals(o2)) {
            return 0;
        }
        if (o1 instanceof OWLProperty && o2 instanceof OWLProperty) {
            return o1.compareTo(o2);
        }
        if (o1 instanceof OWLProperty) {
            return -1;
        }
        return 1;
    };
    private final Map<OWLEntity, Set<OWLAxiom>> lhs2AxiomMap = createMap();
    private final Map<OWLAxiom, Set<OWLEntity>> entitiesByAxiomRHS = createMap();
    private final SeedExtractor seedExtractor = new SeedExtractor();
    private final Map<OWLObject, Set<OWLAxiom>> mappedAxioms = createMap();
    private final Set<OWLAxiom> consumedAxioms = createLinkedSet();
    private final Set<AxiomType<?>> passTypes = createLinkedSet();
    protected Set<OWLAxiom> currentExplanation;
    private final Map<OWLEntity, Set<OWLAxiom>> axioms = new HashMap<>();

    /**
     * Instantiates a new explanation orderer impl.
     */
    public ExplanationOrdererImplNoManager() {
        currentExplanation = Collections.emptySet();
        // I'm not sure what to do with disjoint classes yet. At the
        // moment, we just shove them at the end at the top level.
        passTypes.add(AxiomType.DISJOINT_CLASSES);
    }

    protected static void sortChildrenAxioms(ExplanationTree tree) {
        tree.sortChildren(COMPARATOR);
    }

    protected static int childDiff(Tree<OWLAxiom> o1, Tree<OWLAxiom> o2) {
        int childCount1 = o1.getChildCount();
        childCount1 = childCount1 > 0 ? 0 : 1;
        int childCount2 = o2.getChildCount();
        childCount2 = childCount2 > 0 ? 0 : 1;
        return childCount1 - childCount2;
    }

    protected void reset() {
        lhs2AxiomMap.clear();
        entitiesByAxiomRHS.clear();
        consumedAxioms.clear();
    }

    @Override
    public ExplanationTree getOrderedExplanation(OWLAxiom entailment, Set<OWLAxiom> current) {
        currentExplanation = new HashSet<>(current);
        buildIndices();
        ExplanationTree root = new EntailedAxiomTree(entailment);
        insertChildren(seedExtractor.getSource(entailment), root);
        OWLEntity currentTarget = seedExtractor.getTarget(entailment);
        Set<OWLAxiom> axs = root.getUserObjectClosure();
        List<OWLAxiom> rootAxioms = new ArrayList<>();
        for (OWLAxiom ax : current) {
            if (!axs.contains(ax)) {
                rootAxioms.add(ax);
            }
        }
        Set<OWLAxiom> targetAxioms = getTargetAxioms(currentTarget);
        Collections.sort(rootAxioms, (o1, o2) -> {
            boolean o1in = targetAxioms.contains(o1);
            boolean o2in = targetAxioms.contains(o2);
            if (o1in == o2in) {
                return 0;
            }
            if (o1in) {
                return 1;
            }
            return -1;
        });
        rootAxioms.forEach(ax -> root.addChild(new ExplanationTree(ax)));
        return root;
    }

    /**
     * Gets the target axioms.
     *
     * @param target the current target
     * @return the target axioms
     */
    protected Set<OWLAxiom> getTargetAxioms(OWLEntity target) {
        return axioms.getOrDefault(target, Collections.emptySet());
    }

    private Stream<OWLEntity> getRHSEntitiesSorted(OWLAxiom ax) {
        return getRHSEntities(ax).stream().sorted(PROPERTIESFIRST);
    }

    private void insertChildren(@Nullable OWLEntity entity, ExplanationTree tree) {
        if (entity == null) {
            return;
        }
        Set<OWLAxiom> currentPath = new HashSet<>(tree.getUserObjectPathToRoot());
        getAxioms(entity).filter(ax -> !passTypes.contains(ax.getAxiomType())).forEach(ax -> {
            Set<OWLAxiom> mapped = mappedAxioms.computeIfAbsent(entity, x -> createLinkedSet());
            if (!consumedAxioms.contains(ax) && !mapped.contains(ax) && !currentPath.contains(ax)) {
                mapped.add(ax);
                consumedAxioms.add(ax);
                ExplanationTree child = new ExplanationTree(ax);
                tree.addChild(child);
                getRHSEntitiesSorted(ax).forEach(ent -> insertChildren(ent, child));
            }
        });
        sortChildrenAxioms(tree);
    }

    protected Stream<? extends OWLAxiom> getAxioms(OWLEntity entity) {
        return getTargetAxioms(entity).stream();
    }

    protected void buildIndices() {
        reset();
        AxiomMapBuilder builder = new AxiomMapBuilder();
        currentExplanation.forEach(ax -> ax.accept(builder));
        currentExplanation.forEach(
            ax -> ax.signature().forEach(c -> axioms.computeIfAbsent(c, x -> new HashSet<>())));
    }

    /**
     * Gets axioms that have a LHS corresponding to the specified entity.
     *
     * @param lhs The entity that occurs on the left hand side of the axiom.
     * @return A set of axioms that have the specified entity as their left hand side.
     */
    protected Set<OWLAxiom> getAxiomsForLHS(OWLEntity lhs) {
        return lhs2AxiomMap.computeIfAbsent(lhs, x -> createLinkedSet());
    }

    protected void addLHS(IsAnonymous e, OWLAxiom ax) {
        if (e.isNamed()) {
            getAxiomsForLHS((OWLEntity) e).add(ax);
        }
    }

    protected void addLHSAndRHS(IsAnonymous a, OWLObject b, OWLAxiom ax) {
        addLHS(a, ax);
        indexAxiomsByRHSEntities(b, ax);
    }

    /**
     * Gets the rHS entities.
     *
     * @param axiom the axiom
     * @return the rHS entities
     */
    private Collection<OWLEntity> getRHSEntities(OWLAxiom axiom) {
        return entitiesByAxiomRHS.computeIfAbsent(axiom, x -> createLinkedSet());
    }

    /**
     * Index axioms by rhs entities.
     *
     * @param rhs   the rhs
     * @param axiom the axiom
     */
    protected void indexAxiomsByRHSEntities(OWLObject rhs, OWLAxiom axiom) {
        add(entitiesByAxiomRHS.computeIfAbsent(axiom, x -> createLinkedSet()), rhs.signature());
    }

    /**
     * The Class SeedExtractor.
     */
    private static class SeedExtractor implements OWLAxiomVisitor {

        @Nullable
        private OWLEntity source;
        @Nullable
        private OWLEntity target;

        SeedExtractor() {}

        /**
         * @param axiom the axiom
         * @return the source
         */
        @Nullable
        public OWLEntity getSource(OWLAxiom axiom) {
            axiom.accept(this);
            return source;
        }

        /**
         * @param axiom the axiom
         * @return the target
         */
        public OWLEntity getTarget(OWLAxiom axiom) {
            axiom.accept(this);
            return verifyNotNull(target);
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            setSource(axiom.getSubClass());
            if (!axiom.getSuperClass().isOWLNothing()) {
                setTarget(axiom.getSuperClass());
            }
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            axiom.classExpressions().forEach(this::overrideNullSourceOrTarget);
        }

        protected void overrideNullSourceOrTarget(IsAnonymous ce) {
            if (source == null && setSource(ce)) {
                return;
            }
            if (target == null) {
                setTarget(ce);
            }
        }

        protected boolean setTarget(IsAnonymous ce) {
            if (ce.isAnonymous()) {
                return false;
            }
            target = (OWLEntity) ce;
            return true;
        }

        protected boolean setSource(IsAnonymous ce) {
            if (ce.isAnonymous()) {
                return false;
            }
            source = (OWLEntity) ce;
            return true;
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            setSource(axiom.getSubProperty());
            setTarget(axiom.getSuperProperty());
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                setSource(axiom.getIndividual());
                setTarget(axiom.getClassExpression());
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            axiom.namedClasses().forEach(this::overrideNullSourceOrTarget);
        }

        @Override
        public void visit(SWRLRule rule) {
            // SWRL rules not supported
        }
    }

    /**
     * A visitor that indexes axioms by their left and right hand sides.
     */
    protected class AxiomMapBuilder implements OWLAxiomVisitor {

        AxiomMapBuilder() {}

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            addLHSAndRHS(axiom.getSubClass(), axiom.getSuperClass(), axiom);
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            addLHS(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            addLHS(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            axiom.classExpressions().forEach(desc -> addLHSAndRHS(desc, desc, axiom));
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {
            addLHSAndRHS(axiom.getProperty(), axiom.getDomain(), axiom);
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            addLHSAndRHS(axiom.getProperty(), axiom.getDomain(), axiom);
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            axiom.properties().forEach(prop -> addLHSAndRHS(prop, prop, axiom));
        }

        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {
            axiom.individuals().forEach(ind -> addLHSAndRHS(ind, ind, axiom));
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            axiom.properties().forEach(prop -> addLHSAndRHS(prop, prop, axiom));
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            axiom.properties().forEach(prop -> addLHSAndRHS(prop, prop, axiom));
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            addLHSAndRHS(axiom.getProperty(), axiom.getRange(), axiom);
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            addLHS(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            addLHSAndRHS(axiom.getSubProperty(), axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            getAxiomsForLHS(axiom.getOWLClass()).add(axiom);
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            addLHS(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLDataPropertyRangeAxiom axiom) {
            addLHSAndRHS(axiom.getProperty(), axiom.getRange(), axiom);
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            addLHS(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            axiom.properties().forEach(prop -> addLHSAndRHS(prop, prop, axiom));
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            addLHSAndRHS(axiom.getIndividual(), axiom.getClassExpression(), axiom);
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            axiom.classExpressions().forEach(desc -> addLHSAndRHS(desc, desc, axiom));
        }

        @Override
        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            indexAxiomsByRHSEntities(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            addLHS(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            addLHS(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            addLHSAndRHS(axiom.getSubProperty(), axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            addLHS(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            axiom.individuals().forEach(ind -> addLHSAndRHS(ind, ind, axiom));
        }

        @Override
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            addLHSAndRHS(axiom.getFirstProperty(), axiom.getSecondProperty(), axiom);
            indexAxiomsByRHSEntities(axiom.getFirstProperty(), axiom);
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                indexAxiomsByRHSEntities(axiom.getClassExpression().asOWLClass(), axiom);
            }
        }
    }
}
