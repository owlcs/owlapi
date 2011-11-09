/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.model.AxiomType.*;
import static uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
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
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.MultiMap;

import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitCollectionVisitor;
import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitVisitor;

/** this class encapsulates all lazily built indexes */
public abstract class AbstractInternalsImpl implements Internals {
	protected <K, V extends OWLAxiom> MapPointer<K, V> build(AxiomType<?> t,
			OWLAxiomVisitorEx<?> v) {
		MapPointer<K, V> m = new MapPointer<K, V>(t, v);
		m.map = buildMap();
		return m;
	}

	protected <K, V extends OWLAxiom> MapPointer<K, V> build() {
		MapPointer<K, V> m = new MapPointer<K, V>(null, null);
		m.map = buildMap();
		return m;
	}

	protected <K, V extends OWLAxiom> MapPointer<K, V> buildLazy(AxiomType<?> t,
			OWLAxiomVisitorEx<?> v) {
		MapPointer<K, V> m = new MapPointer<K, V>(t, v);
		return m;
	}

	protected <K, V> MultiMap<K, V> buildMap() {
		return new MultiMap<K, V>(false, true);
	}

	// lazy init
	protected class MapPointer<K, V extends OWLAxiom> implements Internals.Pointer<K, V> {
		protected volatile MultiMap<K, V> map;
		final AxiomType<?> type;
		final OWLAxiomVisitorEx<?> visitor;

		public MapPointer(AxiomType<?> t, OWLAxiomVisitorEx<?> v) {
			type = t;
			visitor = v;
		}

		public MultiMap<K, V> getMap(){
			return map;
		}

		public MapPointer(AxiomType<?> t, OWLAxiomVisitorEx<?> v, boolean threadsafe,
				boolean useSet) {
			this(t, v);
			map = new MultiMap<K, V>(threadsafe, useSet);
		}

		public void init() {
			if (type == null || map != null) {
				return;
			}
			if (visitor instanceof InitVisitor) {
				fill(this, type, (InitVisitor<K>) visitor);
			} else {
				fill(this, type, (InitCollectionVisitor<K>) visitor);
			}
		}
	}

	protected final MapPointer<OWLClass, OWLClassAxiom> classAxiomsByClass = new MapPointer<OWLClass, OWLClassAxiom>(
			null, null) {
		@Override
		public void init() {
			if (map != null) {
				return;
			}
			// special case: this map needs other maps to be initialized first
			map = buildMap();
			for (OWLClass c : getKeyset(getEquivalentClassesAxiomsByClass())) {
				for (OWLClassAxiom ax : getValues(getEquivalentClassesAxiomsByClass(), c)) {
					add(this, c, ax);
				}
			}
			//Maps.SubClassAxiomsByLHS.initMap(impl);
			for (OWLClass c : getKeyset(getSubClassAxiomsByLHS())) {
				for (OWLClassAxiom ax : getValues(getSubClassAxiomsByLHS(), c)) {
					add(this, c, ax);
				}
			}
			//Maps.DisjointClassesAxiomsByClass.initMap(impl);
			for (OWLClass c : getKeyset(getDisjointClassesAxiomsByClass())) {
				for (OWLClassAxiom ax : getValues(getDisjointClassesAxiomsByClass(), c)) {
					add(this, c, ax);
				}
			}
			//Maps.DisjointUnionAxiomsByClass.initMap(impl);
			for (OWLClass c : getKeyset(getDisjointUnionAxiomsByClass())) {
				for (OWLClassAxiom ax : getValues(getDisjointUnionAxiomsByClass(), c)) {
					add(this, c, ax);
				}
			}
		}
	};

	protected <K, V extends OWLAxiom> void fill(MapPointer<K, V> map, AxiomType<?> type,
			InitVisitorFactory.InitVisitor<K> visitor) {
		if (map.map != null) {
			return;
		}
		map.map = buildMap();
		for (V ax : (Set<V>) getValues(getAxiomsByType(), type)) {
			K key = ax.accept(visitor);
			if (key != null) {
				map.map.put(key, ax);
			}
		}
	}

	// NOTE: the parameter is reassigned inside the method, the field that is passed in is not modified in the original object
	protected <K, V extends OWLAxiom> void fill(MapPointer<K, V> map, AxiomType<?> type,
			InitVisitorFactory.InitCollectionVisitor<K> visitor) {
		if (map.map != null) {
			return;
		}
		map.map = buildMap();
		for (V ax : (Set<V>) getValues(getAxiomsByType(), type)) {
			Collection<K> keys = ax.accept(visitor);
			for (K key : keys) {
				add(map, key, ax);
			}
		}
	}

	protected final MapPointer<OWLClass, OWLSubClassOfAxiom> subClassAxiomsByLHS = buildLazy(
			SUBCLASS_OF, classsubnamed);
	protected final MapPointer<OWLClass, OWLSubClassOfAxiom> subClassAxiomsByRHS = buildLazy(
			SUBCLASS_OF, classsupernamed);
	protected final MapPointer<OWLClass, OWLEquivalentClassesAxiom> equivalentClassesAxiomsByClass = buildLazy(
			EQUIVALENT_CLASSES, classcollections);
	protected final MapPointer<OWLClass, OWLDisjointClassesAxiom> disjointClassesAxiomsByClass = buildLazy(
			DISJOINT_CLASSES, classcollections);
	protected final MapPointer<OWLClass, OWLDisjointUnionAxiom> disjointUnionAxiomsByClass = buildLazy(
			DISJOINT_UNION, classcollections);
	protected final MapPointer<OWLClass, OWLHasKeyAxiom> hasKeyAxiomsByClass = buildLazy(
			HAS_KEY, classsupernamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsByLHS = buildLazy(
			SUB_OBJECT_PROPERTY, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsByRHS = buildLazy(
			SUB_OBJECT_PROPERTY, opsupernamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom> equivalentObjectPropertyAxiomsByProperty = buildLazy(
			EQUIVALENT_OBJECT_PROPERTIES, opcollections);
	protected final MapPointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom> disjointObjectPropertyAxiomsByProperty = buildLazy(
			DISJOINT_OBJECT_PROPERTIES, opcollections);
	protected final MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom> objectPropertyDomainAxiomsByProperty = buildLazy(
			OBJECT_PROPERTY_DOMAIN, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom> objectPropertyRangeAxiomsByProperty = buildLazy(
			OBJECT_PROPERTY_RANGE, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom> functionalObjectPropertyAxiomsByProperty = buildLazy(
			FUNCTIONAL_OBJECT_PROPERTY, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom> inverseFunctionalPropertyAxiomsByProperty = buildLazy(
			INVERSE_FUNCTIONAL_OBJECT_PROPERTY, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom> symmetricPropertyAxiomsByProperty = buildLazy(
			SYMMETRIC_OBJECT_PROPERTY, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom> asymmetricPropertyAxiomsByProperty = buildLazy(
			ASYMMETRIC_OBJECT_PROPERTY, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom> reflexivePropertyAxiomsByProperty = buildLazy(
			REFLEXIVE_OBJECT_PROPERTY, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom> irreflexivePropertyAxiomsByProperty = buildLazy(
			IRREFLEXIVE_OBJECT_PROPERTY, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom> transitivePropertyAxiomsByProperty = buildLazy(
			TRANSITIVE_OBJECT_PROPERTY, opsubnamed);
	protected final MapPointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom> inversePropertyAxiomsByProperty = buildLazy(
			INVERSE_OBJECT_PROPERTIES, opcollections);
	protected final MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsByLHS = buildLazy(
			SUB_DATA_PROPERTY, dpsubnamed);
	protected final MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsByRHS = buildLazy(
			SUB_DATA_PROPERTY, dpsupernamed);
	protected final MapPointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom> equivalentDataPropertyAxiomsByProperty = buildLazy(
			EQUIVALENT_DATA_PROPERTIES, dpcollections);
	protected final MapPointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom> disjointDataPropertyAxiomsByProperty = buildLazy(
			DISJOINT_DATA_PROPERTIES, dpcollections);
	protected final MapPointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom> dataPropertyDomainAxiomsByProperty = buildLazy(
			DATA_PROPERTY_DOMAIN, dpsubnamed);
	protected final MapPointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom> dataPropertyRangeAxiomsByProperty = buildLazy(
			DATA_PROPERTY_RANGE, dpsubnamed);
	protected final MapPointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom> functionalDataPropertyAxiomsByProperty = buildLazy(
			FUNCTIONAL_DATA_PROPERTY, dpsubnamed);
	protected final MapPointer<OWLIndividual, OWLClassAssertionAxiom> classAssertionAxiomsByIndividual = buildLazy(
			CLASS_ASSERTION, individualsubnamed);
	protected final MapPointer<OWLClassExpression, OWLClassAssertionAxiom> classAssertionAxiomsByClass = buildLazy(
			CLASS_ASSERTION, classexpressions);
	protected final MapPointer<OWLIndividual, OWLObjectPropertyAssertionAxiom> objectPropertyAssertionsByIndividual = buildLazy(
			OBJECT_PROPERTY_ASSERTION, individualsubnamed);
	protected final MapPointer<OWLIndividual, OWLDataPropertyAssertionAxiom> dataPropertyAssertionsByIndividual = buildLazy(
			DATA_PROPERTY_ASSERTION, individualsubnamed);
	protected final MapPointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom> negativeObjectPropertyAssertionAxiomsByIndividual = buildLazy(
			NEGATIVE_OBJECT_PROPERTY_ASSERTION, individualsubnamed);
	protected final MapPointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom> negativeDataPropertyAssertionAxiomsByIndividual = buildLazy(
			NEGATIVE_DATA_PROPERTY_ASSERTION, individualsubnamed);
	protected final MapPointer<OWLIndividual, OWLDifferentIndividualsAxiom> differentIndividualsAxiomsByIndividual = buildLazy(
			DIFFERENT_INDIVIDUALS, icollections);
	protected final MapPointer<OWLIndividual, OWLSameIndividualAxiom> sameIndividualsAxiomsByIndividual = buildLazy(
			SAME_INDIVIDUAL, icollections);
	protected final MapPointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom> annotationAssertionAxiomsBySubject = buildLazy(
			ANNOTATION_ASSERTION, annotsupernamed);

	protected <K, V> Map<K, V> createMap() {
		return CollectionFactory.createMap();
	}

	public MapPointer<OWLClass, OWLClassAxiom> getClassAxiomsByClass() {
		return this.classAxiomsByClass;
	}

	public MapPointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByLHS() {
		return this.subClassAxiomsByLHS;
	}

	public MapPointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByRHS() {
		return this.subClassAxiomsByRHS;
	}

	public MapPointer<OWLClass, OWLEquivalentClassesAxiom> getEquivalentClassesAxiomsByClass() {
		return this.equivalentClassesAxiomsByClass;
	}

	public MapPointer<OWLClass, OWLDisjointClassesAxiom> getDisjointClassesAxiomsByClass() {
		return this.disjointClassesAxiomsByClass;
	}

	public MapPointer<OWLClass, OWLDisjointUnionAxiom> getDisjointUnionAxiomsByClass() {
		return this.disjointUnionAxiomsByClass;
	}

	public MapPointer<OWLClass, OWLHasKeyAxiom> getHasKeyAxiomsByClass() {
		return this.hasKeyAxiomsByClass;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsByLHS() {
		return this.objectSubPropertyAxiomsByLHS;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsByRHS() {
		return this.objectSubPropertyAxiomsByRHS;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertyAxiomsByProperty() {
		return this.equivalentObjectPropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertyAxiomsByProperty() {
		return this.disjointObjectPropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxiomsByProperty() {
		return this.objectPropertyDomainAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxiomsByProperty() {
		return this.objectPropertyRangeAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxiomsByProperty() {
		return this.functionalObjectPropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalPropertyAxiomsByProperty() {
		return this.inverseFunctionalPropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom> getSymmetricPropertyAxiomsByProperty() {
		return this.symmetricPropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom> getAsymmetricPropertyAxiomsByProperty() {
		return this.asymmetricPropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom> getReflexivePropertyAxiomsByProperty() {
		return this.reflexivePropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom> getIrreflexivePropertyAxiomsByProperty() {
		return this.irreflexivePropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom> getTransitivePropertyAxiomsByProperty() {
		return this.transitivePropertyAxiomsByProperty;
	}

	public MapPointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom> getInversePropertyAxiomsByProperty() {
		return this.inversePropertyAxiomsByProperty;
	}

	public MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsByLHS() {
		return this.dataSubPropertyAxiomsByLHS;
	}

	public MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsByRHS() {
		return this.dataSubPropertyAxiomsByRHS;
	}

	public MapPointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertyAxiomsByProperty() {
		return this.equivalentDataPropertyAxiomsByProperty;
	}

	public MapPointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom> getDisjointDataPropertyAxiomsByProperty() {
		return this.disjointDataPropertyAxiomsByProperty;
	}

	public MapPointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom> getDataPropertyDomainAxiomsByProperty() {
		return this.dataPropertyDomainAxiomsByProperty;
	}

	public MapPointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom> getDataPropertyRangeAxiomsByProperty() {
		return this.dataPropertyRangeAxiomsByProperty;
	}

	public MapPointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxiomsByProperty() {
		return this.functionalDataPropertyAxiomsByProperty;
	}

	public MapPointer<OWLIndividual, OWLClassAssertionAxiom> getClassAssertionAxiomsByIndividual() {
		return this.classAssertionAxiomsByIndividual;
	}

	public MapPointer<OWLClassExpression, OWLClassAssertionAxiom> getClassAssertionAxiomsByClass() {
		return this.classAssertionAxiomsByClass;
	}

	public MapPointer<OWLIndividual, OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionsByIndividual() {
		return this.objectPropertyAssertionsByIndividual;
	}

	public MapPointer<OWLIndividual, OWLDataPropertyAssertionAxiom> getDataPropertyAssertionsByIndividual() {
		return this.dataPropertyAssertionsByIndividual;
	}

	public MapPointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxiomsByIndividual() {
		return this.negativeObjectPropertyAssertionAxiomsByIndividual;
	}

	public MapPointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxiomsByIndividual() {
		return this.negativeDataPropertyAssertionAxiomsByIndividual;
	}

	public MapPointer<OWLIndividual, OWLDifferentIndividualsAxiom> getDifferentIndividualsAxiomsByIndividual() {
		return this.differentIndividualsAxiomsByIndividual;
	}

	public MapPointer<OWLIndividual, OWLSameIndividualAxiom> getSameIndividualsAxiomsByIndividual() {
		return this.sameIndividualsAxiomsByIndividual;
	}

	public MapPointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom> getAnnotationAssertionAxiomsBySubject() {
		return this.annotationAssertionAxiomsBySubject;
	}
}