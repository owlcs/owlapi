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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.search.Searcher.inverse;
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
import java.util.Stack;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class OWLObjectPropertyManager {

    static class SetSizeComparator implements
            Comparator<Set<OWLObjectPropertyExpression>>, Serializable {

        private static final long serialVersionUID = 40000L;

        @Override
        public int compare(Set<OWLObjectPropertyExpression> o1,
                Set<OWLObjectPropertyExpression> o2) {
            return o1.size() - o2.size();
        }
    }

    @Nonnull
    private final OWLOntologyManager man;
    @Nonnull
    private final OWLOntology ontology;
    @Nonnull
    private final Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> hierarchy = new HashMap<>();
    @Nonnull
    private final Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> reflexiveTransitiveClosure = new HashMap<>();
    @Nonnull
    private final Set<OWLObjectPropertyExpression> compositeProperties = new HashSet<>();
    @Nonnull
    private final Set<OWLObjectPropertyExpression> nonSimpleProperties = new HashSet<>();
    @Nonnull
    private final Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> partialOrdering = new HashMap<>();
    private boolean compositeDirty;
    private boolean hierarchyDirty;
    private boolean reflexiveTransitiveClosureDirty;
    private boolean simpleDirty;
    private boolean partialOrderingDirty;

    /**
     * @param manager
     *        the ontology manager to use
     * @param ont
     *        the ontology to use
     */
    public OWLObjectPropertyManager(@Nonnull OWLOntologyManager manager,
            @Nonnull OWLOntology ont) {
        man = checkNotNull(manager, "manager cannot be null");
        ontology = checkNotNull(ont, "ontology cannot be null");
        reset();
    }

    private void reset() {
        compositeDirty = true;
        hierarchyDirty = true;
        reflexiveTransitiveClosureDirty = true;
        simpleDirty = true;
        partialOrderingDirty = true;
    }

    /** clear the object and its resources. */
    public void dispose() {}

    @Nonnull
    protected Set<OWLOntology> getOntologies() {
        return man.getImportsClosure(ontology);
    }

    /**
     * An object property expression PE is composite in Ax if Ax contains an
     * axiom of the form SubObjectPropertyOf(SubObjectPropertyChain(PE1 ... PEn)
     * PE) with n greater than 1, or
     * SubObjectPropertyOf(SubObjectPropertyChain(PE1 ... PEn) INV(PE)) with n
     * greater than 1, or TransitiveObjectProperty(PE), or
     * TransitiveObjectProperty(INV(PE)).
     * 
     * @param expression
     *        The object property expression to be tested
     * @return {@code true} if the object property is composite (according to
     *         the above definition) or {@code false} if the object property is
     *         not composite.
     */
    public boolean isComposite(@Nonnull OWLObjectPropertyExpression expression) {
        checkNotNull(expression, "expression cannot be null");
        return getCompositeProperties().contains(expression.getSimplified());
    }

    /** @return the property expressions */
    @Nonnull
    public Set<OWLObjectPropertyExpression> getCompositeProperties() {
        if (compositeDirty) {
            compositeProperties.clear();
            compositeProperties.add(man.getOWLDataFactory()
                    .getOWLTopObjectProperty());
            compositeProperties.add(man.getOWLDataFactory()
                    .getOWLBottomObjectProperty());
            // We only depend on:
            // 1) Property chain axioms
            // 2) Transitive property axioms
            for (OWLOntology ont : getOntologies()) {
                for (OWLTransitiveObjectPropertyAxiom ax : ont
                        .getAxioms(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
                    markComposite(ax.getProperty());
                    for (OWLObjectPropertyExpression namedInv : inverse(
                            ontology.getInverseObjectPropertyAxioms(ax
                                    .getProperty()), ax.getProperty())) {
                        assert namedInv != null;
                        markComposite(namedInv);
                    }
                }
                for (OWLSubPropertyChainOfAxiom ax : ont
                        .getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    markComposite(ax.getSuperProperty());
                    for (OWLObjectPropertyExpression namedInv : inverse(
                            ontology.getInverseObjectPropertyAxioms(ax
                                    .getSuperProperty()), ax.getSuperProperty())) {
                        assert namedInv != null;
                        markComposite(namedInv);
                    }
                }
            }
            compositeDirty = false;
        }
        return compositeProperties;
    }

    private void markComposite(@Nonnull OWLObjectPropertyExpression prop) {
        checkNotNull(prop, "prop cannot be null");
        compositeProperties.add(prop.getSimplified());
        compositeProperties.add(prop.getInverseProperty().getSimplified());
    }

    /**
     * The object property hierarchy relation -&gt; is the smallest relation on
     * object property expressions for which the following conditions hold (A
     * -&gt; B means that -&gt; holds for A and B): if Ax contains an axiom
     * SubObjectPropertyOf(PE1 PE2), then PE1 -&gt; PE2 holds; and if Ax
     * contains an axiom EquivalentObjectProperties(PE1 PE2), then PE1 -&gt; PE2
     * and PE2 -&gt; PE1 hold; and if Ax contains an axiom
     * InverseObjectProperties(PE1 PE2), then PE1 -&gt; INV(PE2) and INV(PE2)
     * -&gt; PE1 hold; and if Ax contains an axiom SymmetricObjectProperty(PE),
     * then PE -&gt; INV(PE) holds; and if PE1 -&gt; PE2 holds, then INV(PE1)
     * -&gt; INV(PE2) holds as well.
     * 
     * @return A Map that maps sub properties to sets of super properties.
     */
    @Nonnull
    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>
            getPropertyHierarchy() {
        if (hierarchyDirty) {
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map = new HashMap<>();
            // Examine: SubObjectPropertyOf
            // EquivalentObjectProperties
            // InverseObjectProperties
            // SymmetricObjectProperty
            for (OWLOntology ont : getOntologies()) {
                for (OWLSubObjectPropertyOfAxiom ax : ont
                        .getAxioms(AxiomType.SUB_OBJECT_PROPERTY)) {
                    getKeyValue(ax.getSubProperty().getSimplified(), map).add(
                            ax.getSuperProperty().getSimplified());
                    getKeyValue(
                            ax.getSubProperty().getInverseProperty()
                                    .getSimplified(), map).add(
                            ax.getSuperProperty().getInverseProperty()
                                    .getSimplified());
                }
                for (OWLEquivalentObjectPropertiesAxiom ax : ont
                        .getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
                    // Do pairwise
                    for (OWLObjectPropertyExpression propA : ax.getProperties()) {
                        for (OWLObjectPropertyExpression propB : ax
                                .getProperties()) {
                            if (!propA.equals(propB)) {
                                getKeyValue(propA.getSimplified(), map).add(
                                        propB.getSimplified());
                                getKeyValue(propB.getSimplified(), map).add(
                                        propA.getSimplified());
                                getKeyValue(
                                        propA.getInverseProperty()
                                                .getSimplified(), map).add(
                                        propB.getInverseProperty()
                                                .getSimplified());
                                getKeyValue(
                                        propB.getInverseProperty()
                                                .getSimplified(), map).add(
                                        propA.getInverseProperty()
                                                .getSimplified());
                            }
                        }
                    }
                }
                for (OWLInverseObjectPropertiesAxiom ax : ont
                        .getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES)) {
                    getKeyValue(ax.getFirstProperty().getSimplified(), map)
                            .add(ax.getSecondProperty().getInverseProperty()
                                    .getSimplified());
                    getKeyValue(
                            ax.getSecondProperty().getInverseProperty()
                                    .getSimplified(), map).add(
                            ax.getFirstProperty().getSimplified());
                    getKeyValue(
                            ax.getFirstProperty().getInverseProperty()
                                    .getSimplified(), map).add(
                            ax.getSecondProperty().getSimplified());
                    getKeyValue(ax.getSecondProperty().getSimplified(), map)
                            .add(ax.getFirstProperty().getInverseProperty()
                                    .getSimplified());
                }
                for (OWLSymmetricObjectPropertyAxiom ax : ont
                        .getAxioms(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
                    getKeyValue(ax.getProperty().getSimplified(), map).add(
                            ax.getProperty().getInverseProperty()
                                    .getSimplified());
                    getKeyValue(
                            ax.getProperty().getInverseProperty()
                                    .getSimplified(), map).add(
                            ax.getProperty().getSimplified());
                }
            }
            hierarchy.clear();
            hierarchy.putAll(map);
            hierarchyDirty = false;
        }
        return hierarchy;
    }

    /** @return transitive reflexive closure */
    @Nonnull
    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>
            getHierarchyReflexiveTransitiveClosure() {
        if (reflexiveTransitiveClosureDirty) {
            // Produce a map of the transitive reflexive closure of this
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> rtcMap = new HashMap<>();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> propertyHierarchy = getPropertyHierarchy();
            for (OWLObjectPropertyExpression prop : getReferencedProperties()) {
                assert prop != null;
                Set<OWLObjectPropertyExpression> processed = new HashSet<>();
                Set<OWLObjectPropertyExpression> rtc = new HashSet<>();
                getReflexiveTransitiveClosure(prop, propertyHierarchy, rtc,
                        processed);
                rtcMap.put(prop, rtc);
            }
            reflexiveTransitiveClosure.clear();
            reflexiveTransitiveClosure.putAll(rtcMap);
            reflexiveTransitiveClosureDirty = false;
        }
        return reflexiveTransitiveClosure;
    }

    /**
     * Tests to see if one property is a sub property of another property in the
     * reflexive transitive closure of the property hierarchy.
     * 
     * @param sub
     *        The sub property
     * @param sup
     *        The super property
     * @return {@code true} if sub is the sub-property of sup, otherwise
     *         {@code false}
     */
    public boolean isSubPropertyOf(@Nonnull OWLObjectPropertyExpression sub,
            @Nonnull OWLObjectPropertyExpression sup) {
        checkNotNull(sub, "sub cannot be null");
        checkNotNull(sup, "sup cannot be null");
        Set<OWLObjectPropertyExpression> supers = getHierarchyReflexiveTransitiveClosure()
                .get(sub);
        if (supers == null) {
            return false;
        } else {
            return supers.contains(sup);
        }
    }

    /**
     * The relation -&gt;* is the reflexive-transitive closure of -&gt;. An
     * object property expression PE is simple in Ax if, for each object
     * property expression PE' such that PE' -&gt;* PE holds, PE' is not
     * composite.
     * 
     * @param expression
     *        The expression to be tested.
     * @return {@code true} if the object property expression is simple,
     *         otherwise false.
     */
    public boolean isNonSimple(@Nonnull OWLObjectPropertyExpression expression) {
        checkNotNull(expression, "expression cannot be null");
        return getNonSimpleProperties().contains(expression.getSimplified());
    }

    /** @return non simple properties */
    @Nonnull
    public Set<OWLObjectPropertyExpression> getNonSimpleProperties() {
        if (simpleDirty) {
            nonSimpleProperties.clear();
            Set<OWLObjectPropertyExpression> props = getReferencedProperties();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> reflexiveTransitiveClosureMap = getHierarchyReflexiveTransitiveClosure();
            for (OWLObjectPropertyExpression prop : getReferencedProperties()) {
                assert prop != null;
                if (isComposite(prop)) {
                    // Supers are not simple
                    Set<OWLObjectPropertyExpression> rtc = reflexiveTransitiveClosureMap
                            .get(prop);
                    props.removeAll(rtc);
                    nonSimpleProperties.add(prop);
                    nonSimpleProperties.addAll(rtc);
                }
            }
            for (OWLObjectPropertyExpression prop : new ArrayList<>(
                    nonSimpleProperties)) {
                nonSimpleProperties.add(prop.getInverseProperty()
                        .getSimplified());
            }
            simpleDirty = false;
        }
        return nonSimpleProperties;
    }

    /** @return partial ordering */
    @Nonnull
    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>
            getPropertyPartialOrdering() {
        if (partialOrderingDirty) {
            partialOrdering.clear();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map = new HashMap<>(
                    getPropertyHierarchy());
            for (OWLOntology ont : getOntologies()) {
                for (OWLSubPropertyChainOfAxiom ax : ont
                        .getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    for (OWLObjectPropertyExpression prop : ax
                            .getPropertyChain()) {
                        Set<OWLObjectPropertyExpression> sups = map.get(prop
                                .getSimplified());
                        if (sups == null) {
                            sups = new HashSet<>();
                            map.put(prop, sups);
                        }
                        sups.add(ax.getSuperProperty().getSimplified());
                        Set<OWLObjectPropertyExpression> supsInv = map.get(prop
                                .getInverseProperty().getSimplified());
                        if (supsInv == null) {
                            supsInv = new HashSet<>();
                            map.put(prop.getInverseProperty().getSimplified(),
                                    supsInv);
                        }
                        supsInv.add(ax.getSuperProperty().getInverseProperty()
                                .getSimplified());
                    }
                }
            }
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> ordering = new HashMap<>();
            for (OWLObjectPropertyExpression prop : getReferencedProperties()) {
                assert prop != null;
                Set<OWLObjectPropertyExpression> processed = new HashSet<>();
                Set<OWLObjectPropertyExpression> rtc = new HashSet<>();
                getReflexiveTransitiveClosure(prop, map, rtc, processed);
                ordering.put(prop, rtc);
            }
            partialOrdering.putAll(ordering);
            partialOrderingDirty = false;
        }
        return partialOrdering;
    }

    /**
     * @param propA
     *        first property
     * @param propB
     *        second property
     * @return true if first property comes first in the default ordering
     */
    public boolean isLessThan(@Nonnull OWLObjectPropertyExpression propA,
            @Nonnull OWLObjectPropertyExpression propB) {
        checkNotNull(propA, "propA cannot be null");
        checkNotNull(propB, "propB cannot be null");
        Set<OWLObjectPropertyExpression> props = getPropertyPartialOrdering()
                .get(propA.getSimplified());
        if (props == null) {
            return false;
        } else {
            return props.contains(propB.getSimplified());
        }
    }

    @Nonnull
    private Set<OWLObjectPropertyExpression> getReferencedProperties() {
        Set<OWLObjectPropertyExpression> props = new HashSet<>();
        for (OWLOntology ont : getOntologies()) {
            for (OWLObjectPropertyExpression prop : ont
                    .getObjectPropertiesInSignature()) {
                props.add(prop.getSimplified());
            }
        }
        return props;
    }

    /**
     * @param ontologies
     *        ontologies to search
     * @return sets of equivalent properties
     */
    @Nonnull
    public static Collection<Set<OWLObjectPropertyExpression>>
            getEquivalentObjectProperties(@Nonnull Set<OWLOntology> ontologies) {
        checkNotNull(ontologies, "ontologies cannot be null");
        Set<Set<OWLObjectPropertyExpression>> result = new HashSet<>();
        Set<OWLObjectPropertyExpression> processed = new HashSet<>();
        Set<OWLObjectPropertyExpression> properties = new HashSet<>();
        for (OWLOntology ont : ontologies) {
            properties.addAll(ont.getObjectPropertiesInSignature());
        }
        for (OWLObjectPropertyExpression prop : properties) {
            assert prop != null;
            if (processed.contains(prop)) {
                continue;
            }
            tarjan(ontologies, prop, 0,
                    new Stack<OWLObjectPropertyExpression>(),
                    new HashMap<OWLObjectPropertyExpression, Integer>(),
                    new HashMap<OWLObjectPropertyExpression, Integer>(),
                    result, processed,
                    new HashSet<OWLObjectPropertyExpression>());
        }
        // Get maximal
        List<Set<OWLObjectPropertyExpression>> equivs = new ArrayList<>(result);
        Collections.sort(equivs, new SetSizeComparator());
        for (int i = 0; i < equivs.size(); i++) {
            Set<OWLObjectPropertyExpression> first = equivs.get(i);
            for (int j = i; j < equivs.size(); j++) {
                Set<OWLObjectPropertyExpression> second = equivs.get(j);
                if (second.size() > first.size() && second.containsAll(first)) {
                    equivs.remove(i);
                    break;
                }
            }
        }
        return equivs;
    }

    /** @return sets of equivalent properties */
    @Nonnull
    public Collection<Set<OWLObjectPropertyExpression>>
            getEquivalentObjectProperties() {
        return getEquivalentObjectProperties(getOntologies());
    }

    /**
     * @param ontologies
     *        The ontologies
     * @param prop
     *        the property
     * @param index
     *        index
     * @param stack
     *        stack
     * @param indexMap
     *        index map
     * @param lowlinkMap
     *        low link map
     * @param result
     *        result
     * @param processed
     *        processed
     * @param stackProps
     *        stack entities
     */
    public static void tarjan(@Nonnull Set<OWLOntology> ontologies,
            @Nonnull OWLObjectPropertyExpression prop, int index,
            @Nonnull Stack<OWLObjectPropertyExpression> stack,
            @Nonnull Map<OWLObjectPropertyExpression, Integer> indexMap,
            @Nonnull Map<OWLObjectPropertyExpression, Integer> lowlinkMap,
            @Nonnull Set<Set<OWLObjectPropertyExpression>> result,
            @Nonnull Set<OWLObjectPropertyExpression> processed,
            @Nonnull Set<OWLObjectPropertyExpression> stackProps) {
        checkNotNull(ontologies, "ontologies cannot be null");
        checkNotNull(prop, "prop cannot be null");
        checkNotNull(stack, "stack cannot be null");
        checkNotNull(indexMap, "indexMap cannot be null");
        checkNotNull(lowlinkMap, "lowlinkMap cannot be null");
        checkNotNull(result, "result cannot be null");
        checkNotNull(processed, "processed cannot be null");
        checkNotNull(stackProps, "stackProps cannot be null");
        processed.add(prop);
        indexMap.put(prop, index);
        lowlinkMap.put(prop, index);
        stack.push(prop);
        stackProps.add(prop);
        for (OWLOntology ont : ontologies) {
            for (OWLSubObjectPropertyOfAxiom ax : ont
                    .getObjectSubPropertyAxiomsForSubProperty(prop)) {
                if (ax.getSubProperty().equals(prop)) {
                    OWLObjectPropertyExpression supProp = ax.getSuperProperty();
                    if (!indexMap.containsKey(supProp)) {
                        tarjan(ontologies, supProp, index + 1, stack, indexMap,
                                lowlinkMap, result, processed, stackProps);
                        lowlinkMap.put(
                                prop,
                                Math.min(lowlinkMap.get(prop),
                                        lowlinkMap.get(supProp)));
                    } else if (stackProps.contains(supProp)) {
                        lowlinkMap.put(
                                prop,
                                Math.min(lowlinkMap.get(prop),
                                        indexMap.get(supProp)));
                    }
                }
            }
        }
        if (lowlinkMap.get(prop).equals(indexMap.get(prop))) {
            Set<OWLObjectPropertyExpression> scc = new HashSet<>();
            OWLObjectPropertyExpression propPrime;
            while (true) {
                propPrime = stack.pop();
                stackProps.remove(propPrime);
                scc.add(propPrime);
                if (propPrime.equals(prop)) {
                    break;
                }
            }
            if (scc.size() > 1) {
                result.add(scc);
            }
        }
    }

    // Util methods
    private static
            void
            getReflexiveTransitiveClosure(
                    @Nonnull OWLObjectPropertyExpression prop,
                    @Nonnull Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map,
                    @Nonnull Set<OWLObjectPropertyExpression> rtc,
                    @Nonnull Set<OWLObjectPropertyExpression> processed) {
        checkNotNull(prop, "prop cannot be null");
        checkNotNull(map, "map cannot be null");
        checkNotNull(rtc, "rtc cannot be null");
        checkNotNull(processed, "processed cannot be null");
        if (processed.contains(prop)) {
            return;
        }
        rtc.add(prop);
        processed.add(prop);
        Set<OWLObjectPropertyExpression> supers = map.get(prop);
        if (supers == null) {
            return;
        }
        for (OWLObjectPropertyExpression sup : supers) {
            assert sup != null;
            getReflexiveTransitiveClosure(sup, map, rtc, processed);
        }
    }

    @Nonnull
    private static
            Set<OWLObjectPropertyExpression>
            getKeyValue(
                    @Nonnull OWLObjectPropertyExpression key,
                    @Nonnull Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map) {
        checkNotNull(key, "key cannot be null");
        checkNotNull(map, "map cannot be null");
        Set<OWLObjectPropertyExpression> vals = map.get(key);
        if (vals == null) {
            vals = new HashSet<>(4);
            map.put(key, vals);
        }
        return vals;
    }
}
