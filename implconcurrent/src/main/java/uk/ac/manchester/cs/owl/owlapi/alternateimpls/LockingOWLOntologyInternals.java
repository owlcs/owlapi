package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

/*
 * Copyright (C) 2010, University of Manchester
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

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImplInternalsDefaultImpl;

public class LockingOWLOntologyInternals extends OWLOntologyImplInternalsDefaultImpl {
    private final Map<Maps, Lock> locks = new IdentityHashMap<OWLOntologyImplInternalsDefaultImpl.Maps, Lock>(Maps.values().length);

    public LockingOWLOntologyInternals() {
        for (Maps m : Maps.values()) {
            locks.put(m, new ReentrantLock());
        }
    }

    @Override
    protected void initMaps() {
        this.importsDeclarations = CollectionFactory.createSyncSet();
        this.ontologyAnnotations = CollectionFactory.createSyncSet();
        this.axiomsByType = CollectionFactory.createSyncMap();
        this.logicalAxiom2AnnotatedAxiomMap = CollectionFactory.createSyncMap();
        this.generalClassAxioms = CollectionFactory.createSyncSet();
        this.propertyChainSubPropertyAxioms = CollectionFactory.createSyncSet();
        this.owlClassReferences = CollectionFactory.createSyncMap();
        this.owlObjectPropertyReferences = CollectionFactory.createSyncMap();
        this.owlDataPropertyReferences = CollectionFactory.createSyncMap();
        this.owlIndividualReferences = CollectionFactory.createSyncMap();
        this.owlAnonymousIndividualReferences = CollectionFactory.createSyncMap();
        this.owlDatatypeReferences = CollectionFactory.createSyncMap();
        this.owlAnnotationPropertyReferences = CollectionFactory.createSyncMap();
        this.declarationsByEntity = CollectionFactory.createSyncMap();
    }

    @Override
    protected <K, V> Map<K, V> createMap() {
        return CollectionFactory.createSyncMap();
    }

    protected <K, V> void initMapEntry(Map<K, Set<V>> map, K key, V... elements) {
        synchronized (map) {
            if (!map.containsKey(key)) {
                Set<V> set = CollectionFactory.createSyncSet();
                for (V v : elements) {
                    set.add(v);
                }
                map.put(key, set);
            }
        }
    }

    @Override
    public <K, V extends OWLAxiom> void addToIndexedSet(K key, Map<K, Set<V>> map, V axiom) {
        if (map == null) {
            return;
        }
        Set<V> axioms = map.get(key);
        if (axioms == null) {
            initMapEntry(map, key, axiom);
            //axioms = map.get(key);
        }
        else {
            axioms.add(axiom);
        }
    }

    @Override
    public <K, V extends OWLAxiom> void removeAxiomFromSet(K key, Map<K, Set<V>> map, V axiom, boolean removeSetIfEmpty) {
        if (map == null) {
            return;
        }
        Set<V> axioms = map.get(key);
        if (axioms != null) {
            axioms.remove(axiom);
            if (removeSetIfEmpty) {
                if (axioms.isEmpty()) {
                    synchronized (map) {
                        map.remove(key);
                    }
                }
            }
        }
    }

    @Override
    public <K, V extends OWLAxiom> Set<V> getAxioms(K key, Map<K, Set<V>> map, boolean create) {
        Set<V> axioms = map.get(key);
        if (axioms == null) {
            if (create) {
                initMapEntry(map, key);
            }
            else {
                axioms = Collections.emptySet();
            }
        }
        else {
            axioms = Collections.unmodifiableSet(axioms);
        }
        return axioms;
    }

    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        Maps.SubClassAxiomsByLHS.initMap(this, locks.get(Maps.SubClassAxiomsByLHS), subClassAxiomsByLHS);
        return super.getSubClassAxiomsForSubClass(cls);
    }

    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        Maps.SubClassAxiomsByRHS.initMap(this, locks.get(Maps.SubClassAxiomsByRHS), subClassAxiomsByRHS);
        return super.getSubClassAxiomsForSuperClass(cls);
    }

    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls) {
        Maps.EquivalentClassesAxiomsByClass.initMap(this, locks.get(Maps.EquivalentClassesAxiomsByClass), equivalentClassesAxiomsByClass);
        return super.getEquivalentClassesAxioms(cls);
    }

    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        Maps.DisjointClassesAxiomsByClass.initMap(this, locks.get(Maps.DisjointClassesAxiomsByClass), disjointClassesAxiomsByClass);
        return super.getDisjointClassesAxioms(cls);
    }

    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        Maps.DisjointUnionAxiomsByClass.initMap(this, locks.get(Maps.DisjointUnionAxiomsByClass), disjointUnionAxiomsByClass);
        return super.getDisjointUnionAxioms(owlClass);
    }

    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
        Maps.HasKeyAxiomsByClass.initMap(this, locks.get(Maps.HasKeyAxiomsByClass), hasKeyAxiomsByClass);
        return super.getHasKeyAxioms(cls);
    }

    // Object properties
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression property) {
        Maps.ObjectSubPropertyAxiomsByLHS.initMap(this, locks.get(Maps.ObjectSubPropertyAxiomsByLHS), objectSubPropertyAxiomsByLHS);
        return super.getObjectSubPropertyAxiomsForSubProperty(property);
    }

    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression property) {
        Maps.ObjectSubPropertyAxiomsByRHS.initMap(this, locks.get(Maps.ObjectSubPropertyAxiomsByRHS), objectSubPropertyAxiomsByRHS);
        return super.getObjectSubPropertyAxiomsForSuperProperty(property);

    }

    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        Maps.ObjectPropertyDomainAxiomsByProperty.initMap(this, locks.get(Maps.ObjectPropertyDomainAxiomsByProperty), objectPropertyDomainAxiomsByProperty);
        return super.getObjectPropertyDomainAxioms(property);
    }

    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        Maps.ObjectPropertyRangeAxiomsByProperty.initMap(this, locks.get(Maps.ObjectPropertyRangeAxiomsByProperty), objectPropertyRangeAxiomsByProperty);
        return super.getObjectPropertyRangeAxioms(property);
    }

    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        Maps.InversePropertyAxiomsByProperty.initMap(this, locks.get(Maps.InversePropertyAxiomsByProperty), inversePropertyAxiomsByProperty);
        return super.getInverseObjectPropertyAxioms(property);
    }

    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(OWLObjectPropertyExpression property) {
        Maps.EquivalentObjectPropertyAxiomsByProperty.initMap(this, locks.get(Maps.EquivalentObjectPropertyAxiomsByProperty), equivalentObjectPropertyAxiomsByProperty);
        return super.getEquivalentObjectPropertiesAxioms(property);
    }

    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(OWLObjectPropertyExpression property) {
        Maps.DisjointObjectPropertyAxiomsByProperty.initMap(this, locks.get(Maps.DisjointObjectPropertyAxiomsByProperty), disjointObjectPropertyAxiomsByProperty);
        return super.getDisjointObjectPropertiesAxioms(property);
    }

    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        Maps.FunctionalObjectPropertyAxiomsByProperty.initMap(this, locks.get(Maps.FunctionalObjectPropertyAxiomsByProperty), functionalObjectPropertyAxiomsByProperty);
        return super.getFunctionalObjectPropertyAxioms(property);
    }

    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        Maps.InverseFunctionalPropertyAxiomsByProperty.initMap(this, locks.get(Maps.InverseFunctionalPropertyAxiomsByProperty), inverseFunctionalPropertyAxiomsByProperty);
        return super.getInverseFunctionalObjectPropertyAxioms(property);
    }

    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        Maps.SymmetricPropertyAxiomsByProperty.initMap(this, locks.get(Maps.SymmetricPropertyAxiomsByProperty), symmetricPropertyAxiomsByProperty);
        return super.getSymmetricObjectPropertyAxioms(property);
    }

    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        Maps.AsymmetricPropertyAxiomsByProperty.initMap(this, locks.get(Maps.AsymmetricPropertyAxiomsByProperty), asymmetricPropertyAxiomsByProperty);
        return super.getAsymmetricObjectPropertyAxioms(property);
    }

    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        Maps.ReflexivePropertyAxiomsByProperty.initMap(this, locks.get(Maps.ReflexivePropertyAxiomsByProperty), reflexivePropertyAxiomsByProperty);
        return super.getReflexiveObjectPropertyAxioms(property);
    }

    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        Maps.IrreflexivePropertyAxiomsByProperty.initMap(this, locks.get(Maps.IrreflexivePropertyAxiomsByProperty), irreflexivePropertyAxiomsByProperty);
        return super.getIrreflexiveObjectPropertyAxioms(property);
    }

    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        Maps.TransitivePropertyAxiomsByProperty.initMap(this, locks.get(Maps.TransitivePropertyAxiomsByProperty), transitivePropertyAxiomsByProperty);
        return super.getTransitiveObjectPropertyAxioms(property);
    }

    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(OWLDataPropertyExpression property) {
        Maps.FunctionalDataPropertyAxiomsByProperty.initMap(this, locks.get(Maps.FunctionalDataPropertyAxiomsByProperty), functionalDataPropertyAxiomsByProperty);
        return super.getFunctionalDataPropertyAxioms(property);
    }

    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty) {
        Maps.DataSubPropertyAxiomsByLHS.initMap(this, locks.get(Maps.DataSubPropertyAxiomsByLHS), dataSubPropertyAxiomsByLHS);
        return super.getDataSubPropertyAxiomsForSubProperty(lhsProperty);
    }

    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression property) {
        Maps.DataSubPropertyAxiomsByRHS.initMap(this, locks.get(Maps.DataSubPropertyAxiomsByRHS), dataSubPropertyAxiomsByRHS);
        return super.getDataSubPropertyAxiomsForSuperProperty(property);
    }

    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty property) {
        Maps.DataPropertyDomainAxiomsByProperty.initMap(this, locks.get(Maps.DataPropertyDomainAxiomsByProperty), dataPropertyDomainAxiomsByProperty);
        return super.getDataPropertyDomainAxioms(property);
    }

    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(OWLDataProperty property) {
        Maps.DataPropertyRangeAxiomsByProperty.initMap(this, locks.get(Maps.DataPropertyRangeAxiomsByProperty), dataPropertyRangeAxiomsByProperty);
        return super.getDataPropertyRangeAxioms(property);
    }

    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(OWLDataProperty property) {
        Maps.EquivalentDataPropertyAxiomsByProperty.initMap(this, locks.get(Maps.EquivalentDataPropertyAxiomsByProperty), equivalentDataPropertyAxiomsByProperty);
        return super.getEquivalentDataPropertiesAxioms(property);
    }

    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(OWLDataProperty property) {
        Maps.DisjointDataPropertyAxiomsByProperty.initMap(this, locks.get(Maps.DisjointDataPropertyAxiomsByProperty), disjointDataPropertyAxiomsByProperty);
        return super.getDisjointDataPropertiesAxioms(property);
    }

    ////
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual) {
        Maps.ClassAssertionAxiomsByIndividual.initMap(this, locks.get(Maps.ClassAssertionAxiomsByIndividual), classAssertionAxiomsByIndividual);
        return super.getClassAssertionAxioms(individual);
    }

    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass type) {
        Maps.ClassAssertionAxiomsByClass.initMap(this, locks.get(Maps.ClassAssertionAxiomsByClass), classAssertionAxiomsByClass);
        return super.getClassAssertionAxioms(type);
    }

    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual individual) {
        Maps.DataPropertyAssertionsByIndividual.initMap(this, locks.get(Maps.DataPropertyAssertionsByIndividual), dataPropertyAssertionsByIndividual);
        return super.getDataPropertyAssertionAxioms(individual);
    }

    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        Maps.ObjectPropertyAssertionsByIndividual.initMap(this, locks.get(Maps.ObjectPropertyAssertionsByIndividual), objectPropertyAssertionsByIndividual);
        return super.getObjectPropertyAssertionAxioms(individual);
    }

    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(OWLIndividual individual) {
        Maps.NegativeObjectPropertyAssertionAxiomsByIndividual.initMap(this, locks.get(Maps.NegativeObjectPropertyAssertionAxiomsByIndividual), negativeObjectPropertyAssertionAxiomsByIndividual);
        return super.getNegativeObjectPropertyAssertionAxioms(individual);
    }

    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        Maps.NegativeDataPropertyAssertionAxiomsByIndividual.initMap(this, locks.get(Maps.NegativeDataPropertyAssertionAxiomsByIndividual), negativeDataPropertyAssertionAxiomsByIndividual);
        return super.getNegativeDataPropertyAssertionAxioms(individual);
    }

    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual) {
        Maps.SameIndividualsAxiomsByIndividual.initMap(this, locks.get(Maps.SameIndividualsAxiomsByIndividual), sameIndividualsAxiomsByIndividual);
        return super.getSameIndividualAxioms(individual);
    }

    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual individual) {
        Maps.DifferentIndividualsAxiomsByIndividual.initMap(this, locks.get(Maps.DifferentIndividualsAxiomsByIndividual), differentIndividualsAxiomsByIndividual);
        return super.getDifferentIndividualAxioms(individual);
    }

    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxiomsBySubject(OWLAnnotationSubject subject) {
        Maps.AnnotationAssertionAxiomsBySubject.initMap(this, locks.get(Maps.AnnotationAssertionAxiomsBySubject), annotationAssertionAxiomsBySubject);
        return super.getAnnotationAssertionAxiomsBySubject(subject);
    }

    public Set<OWLClassAxiom> getAxioms(OWLClass cls) {
        Maps.ClassAxiomsByClass.initMap(this, locks.get(Maps.ClassAxiomsByClass), classAxiomsByClass);
        return super.getAxioms(cls);
    }
}