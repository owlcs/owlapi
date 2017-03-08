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

import static org.semanticweb.owlapi.model.AxiomType.EQUIVALENT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.model.AxiomType.INVERSE_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.model.AxiomType.SUB_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.model.AxiomType.SUB_PROPERTY_CHAIN_OF;
import static org.semanticweb.owlapi.model.AxiomType.SYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.model.AxiomType.TRANSITIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.search.Searcher.inverse;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class OWLObjectPropertyManager {

    private final OWLDataFactory df;
    private final OWLOntology ontology;
    private final Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> hierarchy =
        new HashMap<>();
    private final Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> reflexiveTransitiveClosure =
        new HashMap<>();
    private final Set<OWLObjectPropertyExpression> compositeProperties = new HashSet<>();
    private final Set<OWLObjectPropertyExpression> nonSimpleProperties = new HashSet<>();
    private final Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> partialOrdering =
        new HashMap<>();
    private boolean compositeDirty;
    private boolean hierarchyDirty;
    private boolean reflexiveTransitiveClosureDirty;
    private boolean simpleDirty;
    private boolean partialOrderingDirty;

    /**
     * @param ont the ontology to use
     */
    public OWLObjectPropertyManager(OWLOntology ont) {
        ontology = checkNotNull(ont, "ontology cannot be null");
        df = ontology.getOWLOntologyManager().getOWLDataFactory();
        reset();
    }

    /**
     * @param ontologies ontologies to search
     * @return sets of equivalent properties
     */
    public static Collection<Set<OWLObjectPropertyExpression>> getEquivalentObjectProperties(
        Stream<OWLOntology> ontologies) {
        checkNotNull(ontologies, "ontologies cannot be null");
        Set<Set<OWLObjectPropertyExpression>> result = new HashSet<>();
        Set<OWLObjectPropertyExpression> processed = new HashSet<>();
        Stream<OWLObjectPropertyExpression> properties =
            ontologies.flatMap(OWLOntology::objectPropertiesInSignature);
        properties.forEach(p -> {
            if (!processed.contains(p)) {
                tarjan(ontologies, p, 0, new LinkedList<OWLObjectPropertyExpression>(),
                    new HashMap<OWLObjectPropertyExpression, Integer>(),
                    new HashMap<OWLObjectPropertyExpression, Integer>(), result,
                    processed, new HashSet<OWLObjectPropertyExpression>());
            }
        });
        // Get maximal
        List<Set<OWLObjectPropertyExpression>> equivs = new ArrayList<>(result);
        Collections.sort(equivs, (o1, o2) -> Integer.compare(o1.size(), o2.size()));
        for (int i = 0; i < equivs.size(); i++) {
            for (int j = i; j < equivs.size(); j++) {
                if (equivs.get(j).containsAll(equivs.get(i))) {
                    equivs.remove(i);
                    break;
                }
            }
        }
        return equivs;
    }

    /**
     * @param ontologies The ontologies
     * @param prop the property
     * @param index index
     * @param stack stack
     * @param indexMap index map
     * @param lowlinkMap low link map
     * @param result result
     * @param processed processed
     * @param stackProps stack entities
     */
    public static void tarjan(Stream<OWLOntology> ontologies, OWLObjectPropertyExpression prop,
        int index, Deque<OWLObjectPropertyExpression> stack,
        Map<OWLObjectPropertyExpression, Integer> indexMap,
        Map<OWLObjectPropertyExpression, Integer> lowlinkMap,
        Set<Set<OWLObjectPropertyExpression>> result,
        Set<OWLObjectPropertyExpression> processed,
        Set<OWLObjectPropertyExpression> stackProps) {
        checkNotNull(ontologies, "ontologies cannot be null");
        checkNotNull(prop, "prop cannot be null");
        checkNotNull(stack, "stack cannot be null");
        checkNotNull(indexMap, "indexMap cannot be null");
        checkNotNull(lowlinkMap, "lowlinkMap cannot be null");
        checkNotNull(result, "result cannot be null");
        checkNotNull(processed, "processed cannot be null");
        checkNotNull(stackProps, "stackProps cannot be null");
        processed.add(prop);
        indexMap.put(prop, Integer.valueOf(index));
        lowlinkMap.put(prop, Integer.valueOf(index));
        stack.push(prop);
        stackProps.add(prop);
        ontologies.flatMap(ont -> ont.objectSubPropertyAxiomsForSubProperty(prop))
            .filter(ax -> ax.getSubProperty().equals(prop))
            .forEach(ax -> callTarjanAndPut(ontologies, prop, index, stack, indexMap,
                lowlinkMap, result, processed, stackProps,
                ax.getSuperProperty()));
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

    protected static void callTarjanAndPut(Stream<OWLOntology> ontologies,
        OWLObjectPropertyExpression prop, int index,
        Deque<OWLObjectPropertyExpression> stack,
        Map<OWLObjectPropertyExpression, Integer> indexMap,
        Map<OWLObjectPropertyExpression, Integer> lowlinkMap,
        Set<Set<OWLObjectPropertyExpression>> result,
        Set<OWLObjectPropertyExpression> processed,
        Set<OWLObjectPropertyExpression> stackProps,
        OWLObjectPropertyExpression supProp) {
        if (!indexMap.containsKey(supProp)) {
            tarjan(ontologies, supProp, index + 1, stack, indexMap, lowlinkMap, result, processed,
                stackProps);
            put(prop, lowlinkMap, supProp);
        } else if (stackProps.contains(supProp)) {
            putIndex(prop, indexMap, lowlinkMap, supProp);
        }
    }

    protected static void putIndex(OWLObjectPropertyExpression prop,
        Map<OWLObjectPropertyExpression, Integer> indexMap,
        Map<OWLObjectPropertyExpression, Integer> lowlinkMap,
        OWLObjectPropertyExpression supProp) {
        lowlinkMap.put(prop, Integer.valueOf(Math.min(lowlinkMap.get(prop).intValue(),
            indexMap.get(supProp).intValue())));
    }

    protected static void put(OWLObjectPropertyExpression prop,
        Map<OWLObjectPropertyExpression, Integer> lowlinkMap,
        OWLObjectPropertyExpression supProp) {
        putIndex(prop, lowlinkMap, lowlinkMap, supProp);
    }

    // Util methods
    private static Set<OWLObjectPropertyExpression> getReflexiveTransitiveClosure(
        OWLObjectPropertyExpression prop,
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map,
        Set<OWLObjectPropertyExpression> rtc,
        Set<OWLObjectPropertyExpression> processed) {
        checkNotNull(prop, "prop cannot be null");
        checkNotNull(map, "map cannot be null");
        checkNotNull(rtc, "rtc cannot be null");
        checkNotNull(processed, "processed cannot be null");
        if (processed.contains(prop)) {
            return rtc;
        }
        rtc.add(prop);
        processed.add(prop);
        Set<OWLObjectPropertyExpression> supers = map.get(prop);
        if (supers == null) {
            return rtc;
        }
        supers.forEach(sup -> getReflexiveTransitiveClosure(sup, map, rtc, processed));
        return rtc;
    }

    private static Set<OWLObjectPropertyExpression> getKeyValue(OWLObjectPropertyExpression key,
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map) {
        return map.computeIfAbsent(key, k -> new HashSet<>(4));
    }

    private void reset() {
        compositeDirty = true;
        hierarchyDirty = true;
        reflexiveTransitiveClosureDirty = true;
        simpleDirty = true;
        partialOrderingDirty = true;
    }

    /**
     * An object property expression PE is composite in Ax if Ax contains an axiom of the form
     * SubObjectPropertyOf(SubObjectPropertyChain(PE1 ... PEn) PE) with n greater than 1, or
     * SubObjectPropertyOf(SubObjectPropertyChain(PE1 ... PEn) INV(PE)) with n greater than 1, or
     * TransitiveObjectProperty(PE), or TransitiveObjectProperty(INV(PE)).
     *
     * @param expression The object property expression to be tested
     * @return {@code true} if the object property is composite (according to the above definition)
     * or {@code false} if the object property is not composite.
     */
    public boolean isComposite(OWLObjectPropertyExpression expression) {
        checkNotNull(expression, "expression cannot be null");
        return getCompositeProperties().contains(expression.getSimplified());
    }

    protected Stream<OWLObjectPropertyExpression> inverseProperties(
        OWLObjectPropertyExpression p1) {
        return inverse(ontology.inverseObjectPropertyAxioms(p1), p1);
    }

    protected Stream<OWLOntology> ontologies() {
        return Imports.INCLUDED.stream(ontology);
    }

    protected <T extends OWLAxiom> Stream<T> axioms(AxiomType<T> t) {
        return ontologies().flatMap(o -> o.axioms(t));
    }

    /**
     * @return the property expressions
     */
    public Set<OWLObjectPropertyExpression> getCompositeProperties() {
        if (compositeDirty) {
            compositeProperties.clear();
            compositeProperties.add(df.getOWLTopObjectProperty());
            compositeProperties.add(df.getOWLBottomObjectProperty());
            // We only depend on:
            // 1) Property chain axioms
            // 2) Transitive property axioms
            axioms(TRANSITIVE_OBJECT_PROPERTY).forEach(ax -> mark(ax.getProperty()));
            axioms(SUB_PROPERTY_CHAIN_OF).forEach(ax -> mark(ax.getSuperProperty()));
            compositeDirty = false;
        }
        return compositeProperties;
    }

    protected void mark(OWLObjectPropertyExpression p) {
        compositeProperties.add(p.getSimplified());
        compositeProperties.add(p.getInverseProperty().getSimplified());
        inverseProperties(p).forEach(i -> {
            compositeProperties.add(i.getSimplified());
            compositeProperties.add(i.getInverseProperty().getSimplified());
        });
    }

    /**
     * The object property hierarchy relation -&gt; is the smallest relation on object property
     * expressions for which the following conditions hold (A -&gt; B means that -&gt; holds for A
     * and B): if Ax contains an axiom SubObjectPropertyOf(PE1 PE2), then PE1 -&gt; PE2 holds; and
     * if Ax contains an axiom EquivalentObjectProperties(PE1 PE2), then PE1 -&gt; PE2 and PE2 -&gt;
     * PE1 hold; and if Ax contains an axiom InverseObjectProperties(PE1 PE2), then PE1 -&gt;
     * INV(PE2) and INV(PE2) -&gt; PE1 hold; and if Ax contains an axiom
     * SymmetricObjectProperty(PE), then PE -&gt; INV(PE) holds; and if PE1 -&gt; PE2 holds, then
     * INV(PE1) -&gt; INV(PE2) holds as well.
     *
     * @return A Map that maps sub properties to sets of super properties.
     */
    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> getPropertyHierarchy() {
        if (hierarchyDirty) {
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map =
                new HashMap<>();
            // Examine: SubObjectPropertyOf
            // EquivalentObjectProperties
            // InverseObjectProperties
            // SymmetricObjectProperty
            axioms(SUB_OBJECT_PROPERTY).forEach(ax -> getKeyValueSymmetric(map, ax.getSubProperty(),
                ax.getSuperProperty()));
            axioms(EQUIVALENT_OBJECT_PROPERTIES).forEach(ax -> pairwise(map, ax));
            axioms(INVERSE_OBJECT_PROPERTIES).forEach(ax -> {
                getKeyValueASymmetric(map, ax.getFirstProperty(), ax.getSecondProperty());
                getKeyValueASymmetric(map, ax.getSecondProperty(), ax.getFirstProperty());
            });
            axioms(SYMMETRIC_OBJECT_PROPERTY).forEach(
                ax -> getKeyValueASymmetric(map, ax.getProperty(), ax.getProperty()));
            hierarchy.clear();
            hierarchy.putAll(map);
            hierarchyDirty = false;
        }
        return hierarchy;
    }

    protected void pairwise(Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map,
        OWLEquivalentObjectPropertiesAxiom ax) {
        // Do pairwise
        ax.walkPairwise((p1, p2) -> {
            if (!p1.equals(p2)) {
                getKeyValueSymmetric(map, p1, p2);
                getKeyValueSymmetric(map, p2, p1);
            }
            return null;
        });
    }

    protected void getKeyValueSymmetric(
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map,
        OWLObjectPropertyExpression p1, OWLObjectPropertyExpression p2) {
        getKeyValue(p1.getSimplified(), map).add(p2.getSimplified());
        getKeyValue(p1.getInverseProperty().getSimplified(), map)
            .add(p2.getInverseProperty().getSimplified());
    }

    protected void getKeyValueASymmetric(
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map,
        OWLObjectPropertyExpression p1, OWLObjectPropertyExpression p2) {
        getKeyValue(p1.getSimplified(), map).add(p2.getInverseProperty().getSimplified());
        getKeyValue(p1.getInverseProperty().getSimplified(), map).add(p2.getSimplified());
    }

    /**
     * @return transitive reflexive closure
     */
    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> getHierarchyReflexiveTransitiveClosure() {
        if (reflexiveTransitiveClosureDirty) {
            // Produce a map of the transitive reflexive closure of this
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> rtcMap =
                new HashMap<>();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> propertyHierarchy =
                getPropertyHierarchy();
            getReferencedProperties()
                .forEach(prop -> rtcMap.put(prop, getReflexiveTransitiveClosure(prop,
                    propertyHierarchy, new HashSet<>(), new HashSet<>())));
            reflexiveTransitiveClosure.clear();
            reflexiveTransitiveClosure.putAll(rtcMap);
            reflexiveTransitiveClosureDirty = false;
        }
        return reflexiveTransitiveClosure;
    }

    /**
     * Tests to see if one property is a sub property of another property in the reflexive
     * transitive closure of the property hierarchy.
     *
     * @param sub The sub property
     * @param sup The super property
     * @return {@code true} if sub is the sub-property of sup, otherwise {@code false}
     */
    public boolean isSubPropertyOf(OWLObjectPropertyExpression sub,
        OWLObjectPropertyExpression sup) {
        checkNotNull(sub, "sub cannot be null");
        checkNotNull(sup, "sup cannot be null");
        return getHierarchyReflexiveTransitiveClosure().get(sub).contains(sup);
    }

    /**
     * The relation -&gt;* is the reflexive-transitive closure of -&gt;. An object property
     * expression PE is simple in Ax if, for each object property expression PE' such that PE'
     * -&gt;* PE holds, PE' is not composite.
     *
     * @param expression The expression to be tested.
     * @return {@code true} if the object property expression is simple, otherwise false.
     */
    public boolean isNonSimple(OWLObjectPropertyExpression expression) {
        checkNotNull(expression, "expression cannot be null");
        return getNonSimpleProperties().contains(expression.getSimplified());
    }

    /**
     * @return non simple properties
     */
    public Set<OWLObjectPropertyExpression> getNonSimpleProperties() {
        if (simpleDirty) {
            nonSimpleProperties.clear();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> reflexiveTransitiveClosureMap =
                getHierarchyReflexiveTransitiveClosure();
            getReferencedProperties().filter(this::isComposite).forEach(prop -> {
                // Supers are not simple
                nonSimpleProperties.add(prop);
                nonSimpleProperties.addAll(reflexiveTransitiveClosureMap.get(prop));
            });
            nonSimpleProperties.addAll(asList(nonSimpleProperties.stream()
                .map(p -> p.getInverseProperty().getSimplified())));
            simpleDirty = false;
        }
        return nonSimpleProperties;
    }

    /**
     * @return partial ordering
     */
    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> getPropertyPartialOrdering() {
        if (partialOrderingDirty) {
            partialOrdering.clear();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map =
                new HashMap<>(getPropertyHierarchy());
            axioms(SUB_PROPERTY_CHAIN_OF)
                .forEach(ax -> ax.getPropertyChain().forEach(p -> map(map, ax, p)));
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> ordering =
                new HashMap<>();
            getReferencedProperties()
                .forEach(prop -> ordering.put(prop, getReflexiveTransitiveClosure(prop,
                    map, new HashSet<>(), new HashSet<>())));
            partialOrdering.putAll(ordering);
            partialOrderingDirty = false;
        }
        return partialOrdering;
    }

    protected void map(Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map,
        OWLSubPropertyChainOfAxiom ax, OWLObjectPropertyExpression prop) {
        map.computeIfAbsent(prop.getSimplified(), k -> new HashSet<>())
            .add(ax.getSuperProperty().getSimplified());
        map.computeIfAbsent(prop.getInverseProperty().getSimplified(), k -> new HashSet<>())
            .add(ax.getSuperProperty().getInverseProperty().getSimplified());
    }

    /**
     * @param propA first property
     * @param propB second property
     * @return true if first property comes first in the default ordering
     */
    public boolean isLessThan(OWLObjectPropertyExpression propA,
        OWLObjectPropertyExpression propB) {
        checkNotNull(propA, "propA cannot be null");
        checkNotNull(propB, "propB cannot be null");
        return getPropertyPartialOrdering().get(propA.getSimplified())
            .contains(propB.getSimplified());
    }

    private Stream<OWLObjectPropertyExpression> getReferencedProperties() {
        return ontologies().flatMap(OWLOntology::objectPropertiesInSignature)
            .map(OWLObjectPropertyExpression::getSimplified);
    }

    /**
     * @return sets of equivalent properties
     */
    public Collection<Set<OWLObjectPropertyExpression>> getEquivalentObjectProperties() {
        return getEquivalentObjectProperties(ontology.importsClosure());
    }
}
