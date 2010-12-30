package org.semanticweb.owlapi.util;

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

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 25-Feb-2008<br><br>
 */
public class OWLObjectPropertyManager {

    private static final class SetSizeComparator implements
			Comparator<Set<OWLObjectPropertyExpression>> {
		public int compare(Set<OWLObjectPropertyExpression> o1, Set<OWLObjectPropertyExpression> o2) {
		    return o1.size() - o2.size();
		}
	}


	private OWLOntologyManager man;

    private OWLOntology ontology;

    private Set<OWLObjectPropertyExpression> properties = new HashSet<OWLObjectPropertyExpression>();

    private Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> hierarchy;

    private Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> reflexiveTransitiveClosure;

    private Set<OWLObjectPropertyExpression> compositeProperties;

    private Set<OWLObjectPropertyExpression> nonSimpleProperties;

    private Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> partialOrdering;

    private boolean compositeDirty;

    private boolean hierarchyDirty;

    private boolean reflexiveTransitiveClosureDirty;

    private boolean simpleDirty;

    private boolean partialOrderingDirty;


    public OWLObjectPropertyManager(OWLOntologyManager manager, OWLOntology ont) {
        this.man = manager;
        this.hierarchy = new HashMap<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>();
        this.reflexiveTransitiveClosure = new HashMap<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>();
        this.compositeProperties = new HashSet<OWLObjectPropertyExpression>();
        this.nonSimpleProperties = new HashSet<OWLObjectPropertyExpression>();
        this.partialOrdering = new HashMap<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>();
        setOntology(ont);
    }


    private void reset() {
        compositeDirty = true;
        hierarchyDirty = true;
        reflexiveTransitiveClosureDirty = true;
        simpleDirty = true;
        partialOrderingDirty = true;
    }


    public void dispose() {

    }


    private void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
        for(OWLOntology ont : man.getImportsClosure(ontology)) {
            for(OWLObjectProperty prop : ont.getObjectPropertiesInSignature()) {
                properties.add(prop);
                properties.add(prop.getInverseProperty());
            }
        }
        reset();
    }


    protected Set<OWLOntology> getOntologies() {
        return man.getImportsClosure(ontology);
    }


    /**
     * An object property expression PE is composite in Ax if Ax contains an axiom of the form
     * SubObjectPropertyOf(SubObjectPropertyChain(PE1 ... PEn) PE) with n > 1, or
     * SubObjectPropertyOf(SubObjectPropertyChain(PE1 ... PEn) INV(PE)) with n > 1, or
     * TransitiveObjectProperty(PE), or
     * TransitiveObjectProperty(INV(PE)).
     *
     * @param expression The object property expression to be tested
     * @return <code>true</code> if the object property is composite (according to the above definition)
     *         or <code>false</code> if the object property is not composite.
     */
    public boolean isComposite(OWLObjectPropertyExpression expression) {
        return getCompositeProperties().contains(expression.getSimplified());
    }


    public Set<OWLObjectPropertyExpression> getCompositeProperties() {
        if (compositeDirty) {
            compositeProperties.clear();
            compositeProperties.add(man.getOWLDataFactory().getOWLTopObjectProperty());
            compositeProperties.add(man.getOWLDataFactory().getOWLBottomObjectProperty());
            // We only depend on:
            //   1) Property chain axioms
            //   2) Transitive property axioms
            for (OWLOntology ont : getOntologies()) {
                for (OWLTransitiveObjectPropertyAxiom ax : ont.getAxioms(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
                    markComposite(ax.getProperty());
                    for (OWLObjectPropertyExpression namedInv : ax.getProperty().getInverses(ontology)) {
                        markComposite(namedInv);
                    }
                }
                for (OWLSubPropertyChainOfAxiom ax : ont.getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    markComposite(ax.getSuperProperty());
                    for (OWLObjectPropertyExpression namedInv : ax.getSuperProperty().getInverses(ontology)) {
                        markComposite(namedInv);
                    }
                }
            }
            compositeDirty = false;
        }
        return compositeProperties;
    }


    private void markComposite(OWLObjectPropertyExpression prop) {
        compositeProperties.add(prop.getSimplified());
        compositeProperties.add(prop.getInverseProperty().getSimplified());
    }


    /**
     * The object property hierarchy relation → is the smallest relation on object property expressions
     * for which the following conditions hold (A → B means that → holds for A and B):
     * if Ax contains an axiom SubObjectPropertyOf(PE1 PE2), then PE1 → PE2 holds; and
     * if Ax contains an axiom EquivalentObjectProperties(PE1 PE2), then PE1 → PE2 and PE2 → PE1 hold; and
     * if Ax contains an axiom InverseObjectProperties(PE1 PE2), then PE1 → INV(PE2) and INV(PE2) → PE1 hold; and
     * if Ax contains an axiom SymmetricObjectProperty(PE), then PE → INV(PE) holds; and
     * if PE1 → PE2 holds, then INV(PE1) → INV(PE2) holds as well.
     *
     * @return A Map that maps sub properties to sets of super properties.
     */
    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> getPropertyHierarchy() {
        if (hierarchyDirty) {
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map = new HashMap<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>();
            // Examine: SubObjectPropertyOf
            //          EquivalentObjectProperties
            //          InverseObjectProperties
            //          SymmetricObjectProperty

            for (OWLOntology ont : getOntologies()) {
                for (OWLSubObjectPropertyOfAxiom ax : ont.getAxioms(AxiomType.SUB_OBJECT_PROPERTY)) {
                    getKeyValue(ax.getSubProperty().getSimplified(), map).add(ax.getSuperProperty().getSimplified());
                    getKeyValue(ax.getSubProperty().getInverseProperty().getSimplified(),
                            map).add(ax.getSuperProperty().getInverseProperty().getSimplified());
                }
                for (OWLEquivalentObjectPropertiesAxiom ax : ont.getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
                    // Do pairwise
                    for (OWLObjectPropertyExpression propA : ax.getProperties()) {
                        for (OWLObjectPropertyExpression propB : ax.getProperties()) {
                            if (!propA.equals(propB)) {
                                getKeyValue(propA.getSimplified(), map).add(propB.getSimplified());
                                getKeyValue(propB.getSimplified(), map).add(propA.getSimplified());
                                getKeyValue(propA.getInverseProperty().getSimplified(),
                                        map).add(propB.getInverseProperty().getSimplified());
                                getKeyValue(propB.getInverseProperty().getSimplified(),
                                        map).add(propA.getInverseProperty().getSimplified());
                            }
                        }
                    }
                }
                for (OWLInverseObjectPropertiesAxiom ax : ont.getAxioms(AxiomType.INVERSE_OBJECT_PROPERTIES)) {
                    getKeyValue(ax.getFirstProperty().getSimplified(),
                            map).add(ax.getSecondProperty().getInverseProperty().getSimplified());
                    getKeyValue(ax.getSecondProperty().getInverseProperty().getSimplified(), map).add(ax.getFirstProperty().getSimplified());
                    getKeyValue(ax.getFirstProperty().getInverseProperty().getSimplified(),
                            map).add(ax.getSecondProperty().getSimplified());
                    getKeyValue(ax.getSecondProperty().getSimplified(), map).add(ax.getFirstProperty().getInverseProperty().getSimplified());
                }
                for (OWLSymmetricObjectPropertyAxiom ax : ont.getAxioms(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
                    getKeyValue(ax.getProperty().getSimplified(),
                            map).add(ax.getProperty().getInverseProperty().getSimplified());
                    getKeyValue(ax.getProperty().getInverseProperty().getSimplified(),
                            map).add(ax.getProperty().getSimplified());
                }
            }
            hierarchy.clear();
            hierarchy.putAll(map);
            hierarchyDirty = false;
        }
        return hierarchy;
    }


    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> getHierarchyReflexiveTransitiveClosure() {
        if (reflexiveTransitiveClosureDirty) {
            // Produce a map of the transitive reflexive closure of this
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> rtcMap = new HashMap<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> hierarchy = getPropertyHierarchy();
            for (OWLObjectPropertyExpression prop : getReferencedProperties()) {
                Set<OWLObjectPropertyExpression> processed = new HashSet<OWLObjectPropertyExpression>();
                Set<OWLObjectPropertyExpression> rtc = new HashSet<OWLObjectPropertyExpression>();
                getReflexiveTransitiveClosure(prop, hierarchy, rtc, processed);
                rtcMap.put(prop, rtc);
            }
            reflexiveTransitiveClosure.clear();
            reflexiveTransitiveClosure.putAll(rtcMap);
            reflexiveTransitiveClosureDirty = false;
        }
        return reflexiveTransitiveClosure;
    }

    /**
     * Tests to see if one property is a sub property of another property in the reflexive transitive
     * closure of the property hierarchy
     *
     * @param sub The sub property
     * @param sup The super property
     * @return <code>true</code> if sub is the sub-property of sup, otherwise <code>false</code>
     */
    public boolean isSubPropertyOf(OWLObjectPropertyExpression sub, OWLObjectPropertyExpression sup) {
        Set<OWLObjectPropertyExpression> supers = getHierarchyReflexiveTransitiveClosure().get(sub);
        if (supers == null) {
            return false;
        } else {
            return supers.contains(sup);
        }
    }


    /**
     * The relation →* is the reflexive-transitive closure of →.
     * An object property expression PE is simple in Ax if,
     * for each object property expression PE' such that PE' →* PE holds, PE' is not composite.
     *
     * @param expression The expression to be tested.
     * @return <code>true</code> if the object property expression is simple, otherwise false.
     */
    public boolean isNonSimple(OWLObjectPropertyExpression expression) {
        return getNonSimpleProperties().contains(expression.getSimplified());
    }


    public Set<OWLObjectPropertyExpression> getNonSimpleProperties() {
        if (simpleDirty) {
            nonSimpleProperties.clear();
            Set<OWLObjectPropertyExpression> props = getReferencedProperties();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> reflexiveTransitiveClosure = getHierarchyReflexiveTransitiveClosure();
            for (OWLObjectPropertyExpression prop : getReferencedProperties()) {
                if (isComposite(prop)) {
                    // Supers are not simple
                    Set<OWLObjectPropertyExpression> rtc = reflexiveTransitiveClosure.get(prop);
                    props.removeAll(rtc);
                    nonSimpleProperties.add(prop);
                    nonSimpleProperties.addAll(rtc);
                }
            }
            for (OWLObjectPropertyExpression prop : new ArrayList<OWLObjectPropertyExpression>(nonSimpleProperties)) {
                nonSimpleProperties.add(prop.getInverseProperty().getSimplified());
            }
            simpleDirty = false;
        }
        return nonSimpleProperties;
    }

    public Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> getPropertyPartialOrdering() {
        if (partialOrderingDirty) {
            partialOrdering.clear();
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map = new HashMap<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>(getPropertyHierarchy());
            for (OWLOntology ont : getOntologies()) {
                for (OWLSubPropertyChainOfAxiom ax : ont.getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    for (OWLObjectPropertyExpression prop : ax.getPropertyChain()) {
                        Set<OWLObjectPropertyExpression> sups = map.get(prop.getSimplified());
                        if (sups == null) {
                            sups = new HashSet<OWLObjectPropertyExpression>();
                            map.put(prop, sups);
                        }
                        sups.add(ax.getSuperProperty().getSimplified());
                        Set<OWLObjectPropertyExpression> supsInv = map.get(prop.getInverseProperty().getSimplified());
                        if (supsInv == null) {
                            supsInv = new HashSet<OWLObjectPropertyExpression>();
                            map.put(prop.getInverseProperty().getSimplified(), supsInv);
                        }
                        supsInv.add(ax.getSuperProperty().getInverseProperty().getSimplified());
                    }
                }
            }
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> ordering = new HashMap<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>>();
            for (OWLObjectPropertyExpression prop : getReferencedProperties()) {
                Set<OWLObjectPropertyExpression> processed = new HashSet<OWLObjectPropertyExpression>();
                Set<OWLObjectPropertyExpression> rtc = new HashSet<OWLObjectPropertyExpression>();
                getReflexiveTransitiveClosure(prop, map, rtc, processed);
                ordering.put(prop, rtc);
            }
            partialOrdering.putAll(ordering);
            partialOrderingDirty = false;
        }
        return partialOrdering;
    }

    public boolean isLessThan(OWLObjectPropertyExpression propA, OWLObjectPropertyExpression propB) {
        Set<OWLObjectPropertyExpression> props = getPropertyPartialOrdering().get(propA.getSimplified());
        if (props == null) {
            return false;
        } else {
            return props.contains(propB.getSimplified());
        }
    }

    private Set<OWLObjectPropertyExpression> getReferencedProperties() {
        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
        for (OWLOntology ont : getOntologies()) {
            for (OWLObjectPropertyExpression prop : ont.getObjectPropertiesInSignature()) {
                props.add(prop.getSimplified());
            }
        }
        return props;
    }


    public static Collection<Set<OWLObjectPropertyExpression>> getEquivalentObjectProperties(Set<OWLOntology> ontologies) {
        Set<Set<OWLObjectPropertyExpression>> result = new HashSet<Set<OWLObjectPropertyExpression>>();

        Set<OWLObjectPropertyExpression> processed = new HashSet<OWLObjectPropertyExpression>();
        Set<OWLObjectPropertyExpression> properties = new HashSet<OWLObjectPropertyExpression>();
        for (OWLOntology ont : ontologies) {
            properties.addAll(ont.getObjectPropertiesInSignature());
        }


        for (OWLObjectPropertyExpression prop : properties) {
            if (processed.contains(prop)) {
                continue;
            }
            tarjan(ontologies,
                    prop,
                    0,
                    new Stack<OWLObjectPropertyExpression>(),
                    new HashMap<OWLObjectPropertyExpression, Integer>(),
                    new HashMap<OWLObjectPropertyExpression, Integer>(),
                    result,
                    processed,
                    new HashSet<OWLObjectPropertyExpression>());
        }
        // Get maximal
        List<Set<OWLObjectPropertyExpression>> equivs = new ArrayList<Set<OWLObjectPropertyExpression>>(result);
        Collections.sort(equivs, new SetSizeComparator());
        for (int i = 0; i < equivs.size(); i++) {
            Set<OWLObjectPropertyExpression> first = equivs.get(i);
            for (int j = i; j < equivs.size(); j++) {
                Set<OWLObjectPropertyExpression> second = equivs.get(j);
                if (second.size() > first.size()) {
                    if (second.containsAll(first)) {
                        equivs.remove(i);
                        break;
                    }
                }
            }
        }
        for (OWLOntology ont : ontologies) {
            for (OWLEquivalentObjectPropertiesAxiom ax : ont.getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
// XXX should anything be done about this?
            }
        }
        return equivs;
    }

    public Collection<Set<OWLObjectPropertyExpression>> getEquivalentObjectProperties() {
        return getEquivalentObjectProperties(getOntologies());
    }


    public static void tarjan(Set<OWLOntology> ontologies, OWLObjectPropertyExpression prop, int index,
                              Stack<OWLObjectPropertyExpression> stack,
                              Map<OWLObjectPropertyExpression, Integer> indexMap,
                              Map<OWLObjectPropertyExpression, Integer> lowlinkMap,
                              Set<Set<OWLObjectPropertyExpression>> result,
                              Set<OWLObjectPropertyExpression> processed,
                              Set<OWLObjectPropertyExpression> stackProps) {
        processed.add(prop);
        indexMap.put(prop, index);
        lowlinkMap.put(prop, index);
        index = index + 1;
        stack.push(prop);
        stackProps.add(prop);
        for (OWLOntology ont : ontologies) {
            for (OWLSubObjectPropertyOfAxiom ax : ont.getObjectSubPropertyAxiomsForSubProperty(prop)) {
                if (ax.getSubProperty().equals(prop)) {
                    OWLObjectPropertyExpression supProp = ax.getSuperProperty();
                    if (!indexMap.containsKey(supProp)) {
                        tarjan(ontologies, supProp, index, stack, indexMap, lowlinkMap, result, processed, stackProps);
                        lowlinkMap.put(prop, Math.min(lowlinkMap.get(prop), lowlinkMap.get(supProp)));
                    } else if (stackProps.contains(supProp)) {
                        lowlinkMap.put(prop, Math.min(lowlinkMap.get(prop), indexMap.get(supProp)));
                    }
                }
            }
        }
        if (lowlinkMap.get(prop).equals(indexMap.get(prop))) {
            Set<OWLObjectPropertyExpression> scc = new HashSet<OWLObjectPropertyExpression>();
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Util methods
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private static void getReflexiveTransitiveClosure(OWLObjectPropertyExpression prop,
                                                      Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map,
                                                      Set<OWLObjectPropertyExpression> rtc,
                                                      Set<OWLObjectPropertyExpression> processed) {
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
            getReflexiveTransitiveClosure(sup, map, rtc, processed);
        }
    }


    private static Set<OWLObjectPropertyExpression> getKeyValue(OWLObjectPropertyExpression key,
                                                                Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> map) {
        Set<OWLObjectPropertyExpression> vals = map.get(key);
        if (vals == null) {
            vals = new HashSet<OWLObjectPropertyExpression>(4);
            map.put(key, vals);
        }
        return vals;
    }
}
