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
import static org.semanticweb.owlapi.model.AxiomType.*;
import static org.semanticweb.owlapi.util.CollectionFactory.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
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
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImplInternalsDefaultImpl;

public class LockingOWLOntologyInternals extends
		OWLOntologyImplInternalsDefaultImpl {
	public LockingOWLOntologyInternals() {
		this.importsDeclarations = createSyncSet();
		this.ontologyAnnotations = createSyncSet();
		this.axiomsByType = createSyncMap();
		this.logicalAxiom2AnnotatedAxiomMap = createSyncMap();
		this.generalClassAxioms = createSyncSet();
		this.propertyChainSubPropertyAxioms = createSyncSet();
		this.owlClassReferences = createSyncMap();
		this.owlObjectPropertyReferences = createSyncMap();
		this.owlDataPropertyReferences = createSyncMap();
		this.owlIndividualReferences = createSyncMap();
		this.owlAnonymousIndividualReferences = createSyncMap();
		this.owlDatatypeReferences = createSyncMap();
		this.owlAnnotationPropertyReferences = createSyncMap();
		this.declarationsByEntity = createSyncMap();
	}

	protected <K, V> void initMapEntry(Map<K, Set<V>> map, K key, V... elements) {
		synchronized (map) {
			if (!map.containsKey(key)) {
				Set<V> set = createSyncSet();
				for (V v : elements) {
					set.add(v);
				}
				map.put(key, set);
			}
		}
	}

	@Override
	public <K, V extends OWLAxiom> void addToIndexedSet(K key,
			Map<K, Set<V>> map, V axiom) {
		if (map == null) {
			return;
		}
		Set<V> axioms = map.get(key);
		if (axioms == null) {
			initMapEntry(map, key, axiom);
			//axioms = map.get(key);
		} else {
			axioms.add(axiom);
		}
	}

	@Override
	public <K, V extends OWLAxiom> void removeAxiomFromSet(K key,
			Map<K, Set<V>> map, V axiom, boolean removeSetIfEmpty) {
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
	public <K, V extends OWLAxiom> Set<V> getAxioms(K key, Map<K, Set<V>> map,
			boolean create) {
		Set<V> axioms = map.get(key);
		if (axioms == null) {
			if (create) {
				initMapEntry(map, key);
			} else {
				axioms = Collections.emptySet();
			}
		} else {
			axioms = Collections.unmodifiableSet(axioms);
		}
		return axioms;
	}

	@Override
	public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
		if (subClassAxiomsByLHS == null) {
			Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByLHS = createSyncMap(); // masks member declaration
			for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
				if (!axiom.getSubClass().isAnonymous()) {
					addToIndexedSet(axiom.getSubClass().asOWLClass(),
							subClassAxiomsByLHS, axiom);
				}
			}
			this.subClassAxiomsByLHS = subClassAxiomsByLHS;
		}
		return getReturnSet(getAxioms(cls, subClassAxiomsByLHS));
	}

	@Override
	public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
		if (subClassAxiomsByRHS == null) {
			Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByRHS = createSyncMap(); // masks member declaration
			for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
				if (!axiom.getSuperClass().isAnonymous()) {
					addToIndexedSet(axiom.getSuperClass().asOWLClass(),
							subClassAxiomsByRHS, axiom);
				}
			}
			this.subClassAxiomsByRHS = subClassAxiomsByRHS;
		}
		return getReturnSet(getAxioms(cls, subClassAxiomsByRHS));
	}

	@Override
	public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(
			OWLClass cls) {
		if (equivalentClassesAxiomsByClass == null) {
			Map<OWLClass, Set<OWLEquivalentClassesAxiom>> equivalentClassesAxiomsByClass = createSyncMap(); // masks member declaration
			for (OWLEquivalentClassesAxiom axiom : getAxiomsInternal(EQUIVALENT_CLASSES)) {
				for (OWLClassExpression desc : axiom.getClassExpressions()) {
					if (!desc.isAnonymous()) {
						addToIndexedSet(desc.asOWLClass(),
								equivalentClassesAxiomsByClass, axiom);
					}
				}
			}
			this.equivalentClassesAxiomsByClass = equivalentClassesAxiomsByClass;
		}
		return getReturnSet(getAxioms(cls, equivalentClassesAxiomsByClass));
	}

	@Override
	public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
		if (disjointClassesAxiomsByClass == null) {
			Map<OWLClass, Set<OWLDisjointClassesAxiom>> disjointClassesAxiomsByClass = createSyncMap(); // masks member declaration
			for (OWLDisjointClassesAxiom axiom : getAxiomsInternal(DISJOINT_CLASSES)) {
				for (OWLClassExpression desc : axiom.getClassExpressions()) {
					if (!desc.isAnonymous()) {
						addToIndexedSet(desc.asOWLClass(),
								disjointClassesAxiomsByClass, axiom);
					}
				}
			}
			this.disjointClassesAxiomsByClass = disjointClassesAxiomsByClass;
		}
		return getReturnSet(getAxioms(cls, disjointClassesAxiomsByClass));
	}

	@Override
	public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
		if (getDisjointUnionAxiomsByClass() == null) {
			Map<OWLClass, Set<OWLDisjointUnionAxiom>> disjointUnionAxiomsByClass = createSyncMap(); // masks member declaration
			for (OWLDisjointUnionAxiom axiom : getAxiomsInternal(DISJOINT_UNION)) {
				for (OWLClassExpression desc : axiom.getClassExpressions()) {
					if (!desc.isAnonymous()) {
						addToIndexedSet(desc.asOWLClass(),
								disjointUnionAxiomsByClass, axiom);
					}
				}
			}
			this.disjointUnionAxiomsByClass = disjointUnionAxiomsByClass;
		}
		return getReturnSet(getAxioms(owlClass, getDisjointUnionAxiomsByClass()));
	}

	@Override
	public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
		if (getHasKeyAxiomsByClass() == null) {
			Map<OWLClass, Set<OWLHasKeyAxiom>> hasKeyAxiomsByClass = createSyncMap(); // masks member declaration
			for (OWLHasKeyAxiom axiom : getAxiomsInternal(HAS_KEY)) {
				if (!axiom.getClassExpression().isAnonymous()) {
					addToIndexedSet(axiom.getClassExpression().asOWLClass(),
							hasKeyAxiomsByClass, axiom);
				}
			}
			this.hasKeyAxiomsByClass = hasKeyAxiomsByClass;
		}
		return getReturnSet(getAxioms(cls, getHasKeyAxiomsByClass()));
	}

	// Object properties
	@Override
	public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
			OWLObjectPropertyExpression property) {
		if (getObjectSubPropertyAxiomsByLHS() == null) {
			Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByLHS = createSyncMap(); // masks member declaration
			for (OWLSubObjectPropertyOfAxiom axiom : getAxiomsInternal(SUB_OBJECT_PROPERTY)) {
				addToIndexedSet(axiom.getSubProperty(),
						objectSubPropertyAxiomsByLHS, axiom);
			}
			this.objectSubPropertyAxiomsByLHS = objectSubPropertyAxiomsByLHS;
		}
		return getReturnSet(getAxioms(property,
				getObjectSubPropertyAxiomsByLHS()));
	}

	@Override
	public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(
			OWLObjectPropertyExpression property) {
		if (getObjectSubPropertyAxiomsByRHS() == null) {
			Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByRHS = createSyncMap(); // masks member declaration
			for (OWLSubObjectPropertyOfAxiom axiom : getAxiomsInternal(SUB_OBJECT_PROPERTY)) {
				addToIndexedSet(axiom.getSuperProperty(),
						objectSubPropertyAxiomsByRHS, axiom);
			}
			this.objectSubPropertyAxiomsByRHS = objectSubPropertyAxiomsByRHS;
		}
		return getReturnSet(getAxioms(property,
				getObjectSubPropertyAxiomsByRHS()));
	}

	@Override
	public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
			OWLObjectPropertyExpression property) {
		if (getObjectPropertyDomainAxiomsByProperty() == null) {
			Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyDomainAxiom>> objectPropertyDomainAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLObjectPropertyDomainAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_DOMAIN)) {
				addToIndexedSet(axiom.getProperty(),
						objectPropertyDomainAxiomsByProperty, axiom);
			}
			this.objectPropertyDomainAxiomsByProperty = objectPropertyDomainAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getObjectPropertyDomainAxiomsByProperty()));
	}

	@Override
	public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
			OWLObjectPropertyExpression property) {
		if (getObjectPropertyRangeAxiomsByProperty() == null) {
			Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyRangeAxiom>> objectPropertyRangeAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLObjectPropertyRangeAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_RANGE)) {
				addToIndexedSet(axiom.getProperty(),
						objectPropertyRangeAxiomsByProperty, axiom);
			}
			this.objectPropertyRangeAxiomsByProperty = objectPropertyRangeAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getObjectPropertyRangeAxiomsByProperty()));
	}

	@Override
	public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
			OWLObjectPropertyExpression property) {
		if (getInversePropertyAxiomsByProperty() == null) {
			Map<OWLObjectPropertyExpression, Set<OWLInverseObjectPropertiesAxiom>> inversePropertyAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLInverseObjectPropertiesAxiom axiom : getAxiomsInternal(INVERSE_OBJECT_PROPERTIES)) {
				for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
					addToIndexedSet(prop, inversePropertyAxiomsByProperty,
							axiom);
				}
			}
			this.inversePropertyAxiomsByProperty = inversePropertyAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getInversePropertyAxiomsByProperty()));
	}

	@Override
	public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
			OWLObjectPropertyExpression property) {
		if (getEquivalentObjectPropertyAxiomsByProperty() == null) {
			Map<OWLObjectPropertyExpression, Set<OWLEquivalentObjectPropertiesAxiom>> equivalentObjectPropertyAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLEquivalentObjectPropertiesAxiom axiom : getAxiomsInternal(EQUIVALENT_OBJECT_PROPERTIES)) {
				for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
					addToIndexedSet(prop,
							equivalentObjectPropertyAxiomsByProperty, axiom);
				}
			}
			this.equivalentObjectPropertyAxiomsByProperty = equivalentObjectPropertyAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getEquivalentObjectPropertyAxiomsByProperty()));
	}

	@Override
	public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
			OWLObjectPropertyExpression property) {
		if (getDisjointObjectPropertyAxiomsByProperty() == null) {
			Map<OWLObjectPropertyExpression, Set<OWLDisjointObjectPropertiesAxiom>> disjointObjectPropertyAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLDisjointObjectPropertiesAxiom axiom : getAxiomsInternal(DISJOINT_OBJECT_PROPERTIES)) {
				for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
					addToIndexedSet(prop,
							disjointObjectPropertyAxiomsByProperty, axiom);
				}
			}
			this.disjointObjectPropertyAxiomsByProperty = disjointObjectPropertyAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getDisjointObjectPropertyAxiomsByProperty()));
	}

	@Override
	protected <T extends OWLObjectPropertyCharacteristicAxiom> Map<OWLObjectPropertyExpression, Set<T>> buildObjectPropertyCharacteristicsIndex(
			AxiomType<T> type) {
		Map<OWLObjectPropertyExpression, Set<T>> map = createSyncMap();
		for (T ax : getAxiomsInternal(type)) {
			addToIndexedSet(ax.getProperty(), map, ax);
		}
		return map;
	}

	@Override
	public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
			OWLDataPropertyExpression property) {
		if (getFunctionalDataPropertyAxiomsByProperty() == null) {
			Map<OWLDataPropertyExpression, Set<OWLFunctionalDataPropertyAxiom>> functionalDataPropertyAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLFunctionalDataPropertyAxiom ax : getAxiomsInternal(FUNCTIONAL_DATA_PROPERTY)) {
				addToIndexedSet(ax.getProperty(),
						functionalDataPropertyAxiomsByProperty, ax);
			}
			this.functionalDataPropertyAxiomsByProperty = functionalDataPropertyAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getFunctionalDataPropertyAxiomsByProperty()));
	}

	@Override
	public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(
			OWLDataProperty lhsProperty) {
		if (getDataSubPropertyAxiomsByLHS() == null) {
			Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByLHS = createSyncMap(); // masks member declaration
			for (OWLSubDataPropertyOfAxiom axiom : getAxiomsInternal(SUB_DATA_PROPERTY)) {
				addToIndexedSet(axiom.getSubProperty(),
						dataSubPropertyAxiomsByLHS, axiom);
			}
			this.dataSubPropertyAxiomsByLHS = dataSubPropertyAxiomsByLHS;
		}
		return getReturnSet(getAxioms(lhsProperty,
				getDataSubPropertyAxiomsByLHS()));
	}

	@Override
	public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
			OWLDataPropertyExpression property) {
		if (getDataSubPropertyAxiomsByRHS() == null) {
			Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByRHS = createSyncMap(); // masks member declaration
			for (OWLSubDataPropertyOfAxiom axiom : getAxiomsInternal(SUB_DATA_PROPERTY)) {
				addToIndexedSet(axiom.getSuperProperty(),
						dataSubPropertyAxiomsByRHS, axiom);
			}
			this.dataSubPropertyAxiomsByRHS = dataSubPropertyAxiomsByRHS;
		}
		return getReturnSet(getAxioms(property, getDataSubPropertyAxiomsByRHS()));
	}

	@Override
	public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
			OWLDataProperty property) {
		if (getDataPropertyDomainAxiomsByProperty() == null) {
			Map<OWLDataPropertyExpression, Set<OWLDataPropertyDomainAxiom>> dataPropertyDomainAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLDataPropertyDomainAxiom axiom : getAxiomsInternal(DATA_PROPERTY_DOMAIN)) {
				addToIndexedSet(axiom.getProperty(),
						dataPropertyDomainAxiomsByProperty, axiom);
			}
			this.dataPropertyDomainAxiomsByProperty = dataPropertyDomainAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getDataPropertyDomainAxiomsByProperty()));
	}

	@Override
	public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
			OWLDataProperty property) {
		if (getDataPropertyRangeAxiomsByProperty() == null) {
			Map<OWLDataPropertyExpression, Set<OWLDataPropertyRangeAxiom>> dataPropertyRangeAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLDataPropertyRangeAxiom axiom : getAxiomsInternal(DATA_PROPERTY_RANGE)) {
				addToIndexedSet(axiom.getProperty(),
						dataPropertyRangeAxiomsByProperty, axiom);
			}
			this.dataPropertyRangeAxiomsByProperty = dataPropertyRangeAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getDataPropertyRangeAxiomsByProperty()));
	}

	@Override
	public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(
			OWLDataProperty property) {
		if (getEquivalentDataPropertyAxiomsByProperty() == null) {
			Map<OWLDataPropertyExpression, Set<OWLEquivalentDataPropertiesAxiom>> equivalentDataPropertyAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLEquivalentDataPropertiesAxiom axiom : getAxiomsInternal(EQUIVALENT_DATA_PROPERTIES)) {
				for (OWLDataPropertyExpression prop : axiom.getProperties()) {
					addToIndexedSet(prop,
							equivalentDataPropertyAxiomsByProperty, axiom);
				}
			}
			this.equivalentDataPropertyAxiomsByProperty = equivalentDataPropertyAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getEquivalentDataPropertyAxiomsByProperty()));
	}

	@Override
	public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
			OWLDataProperty property) {
		if (getDisjointDataPropertyAxiomsByProperty() == null) {
			Map<OWLDataPropertyExpression, Set<OWLDisjointDataPropertiesAxiom>> disjointDataPropertyAxiomsByProperty = createSyncMap(); // masks member declaration
			for (OWLDisjointDataPropertiesAxiom axiom : getAxiomsInternal(DISJOINT_DATA_PROPERTIES)) {
				for (OWLDataPropertyExpression prop : axiom.getProperties()) {
					addToIndexedSet(prop, disjointDataPropertyAxiomsByProperty,
							axiom);
				}
			}
			this.disjointDataPropertyAxiomsByProperty = disjointDataPropertyAxiomsByProperty;
		}
		return getReturnSet(getAxioms(property,
				getDisjointDataPropertyAxiomsByProperty()));
	}

	////
	@Override
	public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
			OWLIndividual individual) {
		if (getClassAssertionAxiomsByIndividual() == null) {
			Map<OWLIndividual, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByIndividual = createSyncMap(); // masks member declaration
			for (OWLClassAssertionAxiom axiom : getAxiomsInternal(CLASS_ASSERTION)) {
				addToIndexedSet(axiom.getIndividual(),
						classAssertionAxiomsByIndividual, axiom);
			}
			this.classAssertionAxiomsByIndividual = classAssertionAxiomsByIndividual;
		}
		return getReturnSet(getAxioms(individual,
				getClassAssertionAxiomsByIndividual()));
	}

	@Override
	public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass type) {
		if (getClassAssertionAxiomsByClass() == null) {
			Map<OWLClass, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByClass = createSyncMap(); // masks member declaration
			for (OWLClassAssertionAxiom axiom : getAxiomsInternal(CLASS_ASSERTION)) {
				if (!axiom.getClassExpression().isAnonymous()) {
					addToIndexedSet((OWLClass) axiom.getClassExpression(),
							classAssertionAxiomsByClass, axiom);
				}
			}
			this.classAssertionAxiomsByClass = classAssertionAxiomsByClass;
		}
		return getReturnSet(getAxioms(type, getClassAssertionAxiomsByClass()));
	}

	@Override
	public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
			OWLIndividual individual) {
		if (getDataPropertyAssertionsByIndividual() == null) {
			Map<OWLIndividual, Set<OWLDataPropertyAssertionAxiom>> dataPropertyAssertionsByIndividual = createSyncMap(); // masks member declaration
			for (OWLDataPropertyAssertionAxiom axiom : getAxiomsInternal(DATA_PROPERTY_ASSERTION)) {
				addToIndexedSet(axiom.getSubject(),
						dataPropertyAssertionsByIndividual, axiom);
			}
			this.dataPropertyAssertionsByIndividual = dataPropertyAssertionsByIndividual;
		}
		return getReturnSet(getAxioms(individual,
				getDataPropertyAssertionsByIndividual()));
	}

	@Override
	public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(
			OWLIndividual individual) {
		if (getObjectPropertyAssertionsByIndividual() == null) {
			Map<OWLIndividual, Set<OWLObjectPropertyAssertionAxiom>> objectPropertyAssertionsByIndividual = createSyncMap(); // masks member declaration
			for (OWLObjectPropertyAssertionAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_ASSERTION)) {
				addToIndexedSet(axiom.getSubject(),
						objectPropertyAssertionsByIndividual, axiom);
			}
			this.objectPropertyAssertionsByIndividual = objectPropertyAssertionsByIndividual;
		}
		return getReturnSet(getAxioms(individual,
				getObjectPropertyAssertionsByIndividual()));
	}

	@Override
	public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
			OWLIndividual individual) {
		if (getNegativeObjectPropertyAssertionAxiomsByIndividual() == null) {
			Map<OWLIndividual, Set<OWLNegativeObjectPropertyAssertionAxiom>> negativeObjectPropertyAssertionAxiomsByIndividual = createSyncMap(); // masks member declaration
			for (OWLNegativeObjectPropertyAssertionAxiom axiom : getAxiomsInternal(NEGATIVE_OBJECT_PROPERTY_ASSERTION)) {
				addToIndexedSet(axiom.getSubject(),
						negativeObjectPropertyAssertionAxiomsByIndividual,
						axiom);
			}
			this.negativeObjectPropertyAssertionAxiomsByIndividual = negativeObjectPropertyAssertionAxiomsByIndividual;
		}
		return getReturnSet(getAxioms(individual,
				getNegativeObjectPropertyAssertionAxiomsByIndividual()));
	}

	@Override
	public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(
			OWLIndividual individual) {
		if (getNegativeDataPropertyAssertionAxiomsByIndividual() == null) {
			Map<OWLIndividual, Set<OWLNegativeDataPropertyAssertionAxiom>> negativeDataPropertyAssertionAxiomsByIndividual = createSyncMap(); // masks member declaration
			for (OWLNegativeDataPropertyAssertionAxiom axiom : getAxiomsInternal(NEGATIVE_DATA_PROPERTY_ASSERTION)) {
				addToIndexedSet(axiom.getSubject(),
						negativeDataPropertyAssertionAxiomsByIndividual, axiom);
			}
			this.negativeDataPropertyAssertionAxiomsByIndividual = negativeDataPropertyAssertionAxiomsByIndividual;
		}
		return getReturnSet(getAxioms(individual,
				getNegativeDataPropertyAssertionAxiomsByIndividual()));
	}

	@Override
	public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(
			OWLIndividual individual) {
		if (getSameIndividualsAxiomsByIndividual() == null) {
			Map<OWLIndividual, Set<OWLSameIndividualAxiom>> sameIndividualsAxiomsByIndividual = createSyncMap(); // masks member declaration
			for (OWLSameIndividualAxiom axiom : getAxiomsInternal(SAME_INDIVIDUAL)) {
				for (OWLIndividual ind : axiom.getIndividuals()) {
					addToIndexedSet(ind, sameIndividualsAxiomsByIndividual,
							axiom);
				}
			}
			this.sameIndividualsAxiomsByIndividual = sameIndividualsAxiomsByIndividual;
		}
		return getReturnSet(getAxioms(individual,
				getSameIndividualsAxiomsByIndividual()));
	}

	@Override
	public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
			OWLIndividual individual) {
		if (getDifferentIndividualsAxiomsByIndividual() == null) {
			Map<OWLIndividual, Set<OWLDifferentIndividualsAxiom>> differentIndividualsAxiomsByIndividual = createSyncMap(); // masks member declaration
			for (OWLDifferentIndividualsAxiom axiom : getAxiomsInternal(DIFFERENT_INDIVIDUALS)) {
				for (OWLIndividual ind : axiom.getIndividuals()) {
					addToIndexedSet(ind,
							differentIndividualsAxiomsByIndividual, axiom);
				}
			}
			this.differentIndividualsAxiomsByIndividual = differentIndividualsAxiomsByIndividual;
		}
		return getReturnSet(getAxioms(individual,
				getDifferentIndividualsAxiomsByIndividual()));
	}

	@Override
	public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxiomsBySubject(
			OWLAnnotationSubject subject) {
		if (annotationAssertionAxiomsBySubject == null) {
			Map<OWLAnnotationSubject, Set<OWLAnnotationAssertionAxiom>> annotationAssertionAxiomsBySubject = createSyncMap(); // masks member declaration
			for (OWLAnnotationAssertionAxiom axiom : getAxiomsInternal(ANNOTATION_ASSERTION)) {
				addToIndexedSet(axiom.getSubject(),
						annotationAssertionAxiomsBySubject, axiom);
			}
			this.annotationAssertionAxiomsBySubject = annotationAssertionAxiomsBySubject;
		}
		return getReturnSet(getAxioms(subject,
				annotationAssertionAxiomsBySubject, false));
	}

	@Override
	public void buildClassAxiomsByClassIndex() {
		Map<OWLClass, Set<OWLClassAxiom>> classAxiomsByClass = createSyncMap(); // masks member declaration
		for (OWLEquivalentClassesAxiom axiom : getAxiomsInternal(EQUIVALENT_CLASSES)) {
			for (OWLClassExpression desc : axiom.getClassExpressions()) {
				if (!desc.isAnonymous()) {
					addToIndexedSet((OWLClass) desc, classAxiomsByClass, axiom);
				}
			}
		}
		for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
			if (!axiom.getSubClass().isAnonymous()) {
				addToIndexedSet((OWLClass) axiom.getSubClass(),
						classAxiomsByClass, axiom);
			}
		}
		for (OWLDisjointClassesAxiom axiom : getAxiomsInternal(DISJOINT_CLASSES)) {
			for (OWLClassExpression desc : axiom.getClassExpressions()) {
				if (!desc.isAnonymous()) {
					addToIndexedSet((OWLClass) desc, classAxiomsByClass, axiom);
				}
			}
		}
		for (OWLDisjointUnionAxiom axiom : getAxiomsInternal(DISJOINT_UNION)) {
			addToIndexedSet(axiom.getOWLClass(), classAxiomsByClass, axiom);
		}
		this.classAxiomsByClass = classAxiomsByClass;
	}
}