package uk.ac.manchester.cs.owl.owlapi;

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
import static org.semanticweb.owlapi.model.AxiomType.AXIOM_TYPES;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;

public class InternalsImpl extends AbstractInternalsImpl {
	protected Set<OWLImportsDeclaration> importsDeclarations;
	protected Set<OWLAnnotation> ontologyAnnotations;
	protected Map<AxiomType<?>, Set<OWLAxiom>> axiomsByType;
	protected Map<OWLAxiom, Set<OWLAxiom>> logicalAxiom2AnnotatedAxiomMap;
	protected Set<OWLClassAxiom> generalClassAxioms;
	protected Set<OWLSubPropertyChainOfAxiom> propertyChainSubPropertyAxioms;
	protected Map<OWLClass, Set<OWLAxiom>> owlClassReferences;
	protected Map<OWLObjectProperty, Set<OWLAxiom>> owlObjectPropertyReferences;
	protected Map<OWLDataProperty, Set<OWLAxiom>> owlDataPropertyReferences;
	protected Map<OWLNamedIndividual, Set<OWLAxiom>> owlIndividualReferences;
	protected Map<OWLAnonymousIndividual, Set<OWLAxiom>> owlAnonymousIndividualReferences;
	protected Map<OWLDatatype, Set<OWLAxiom>> owlDatatypeReferences;
	protected Map<OWLAnnotationProperty, Set<OWLAxiom>> owlAnnotationPropertyReferences;
	protected Map<OWLEntity, Set<OWLDeclarationAxiom>> declarationsByEntity;

	public InternalsImpl() {
		initMaps();
	}

	protected void initMaps() {
		this.importsDeclarations = createSet();
		this.ontologyAnnotations = createSet();
		this.axiomsByType = createMap();
		this.logicalAxiom2AnnotatedAxiomMap = createMap();
		this.generalClassAxioms = createSet();
		this.propertyChainSubPropertyAxioms = createSet();
		this.owlClassReferences = createMap();
		this.owlObjectPropertyReferences = createMap();
		this.owlDataPropertyReferences = createMap();
		this.owlIndividualReferences = createMap();
		this.owlAnonymousIndividualReferences = createMap();
		this.owlDatatypeReferences = createMap();
		this.owlAnnotationPropertyReferences = createMap();
		this.declarationsByEntity = createMap();
	}

	public boolean isDeclared(OWLDeclarationAxiom ax) {
		return declarationsByEntity.containsKey(ax.getEntity());
	}

	public boolean isEmpty() {
		for (Set<OWLAxiom> axiomSet : axiomsByType.values()) {
			if (!axiomSet.isEmpty()) {
				return false;
			}
		}
		return ontologyAnnotations.isEmpty();
	}

	public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(
			OWLDatatype datatype) {
		Set<OWLDatatypeDefinitionAxiom> result = createSet();
		Set<OWLDatatypeDefinitionAxiom> axioms = getAxiomsInternal(AxiomType.DATATYPE_DEFINITION);
		for (OWLDatatypeDefinitionAxiom ax : axioms) {
			if (ax.getDatatype().equals(datatype)) {
				result.add(ax);
			}
		}
		return result;
	}

	public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(
			OWLAnnotationProperty subProperty) {
		Set<OWLSubAnnotationPropertyOfAxiom> result = createSet();
		for (OWLSubAnnotationPropertyOfAxiom ax : getAxiomsInternal(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
			if (ax.getSubProperty().equals(subProperty)) {
				result.add(ax);
			}
		}
		return result;
	}

	public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(
			OWLAnnotationProperty property) {
		Set<OWLAnnotationPropertyDomainAxiom> result = createSet();
		for (OWLAnnotationPropertyDomainAxiom ax : getAxiomsInternal(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
			if (ax.getProperty().equals(property)) {
				result.add(ax);
			}
		}
		return result;
	}

	public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(
			OWLAnnotationProperty property) {
		Set<OWLAnnotationPropertyRangeAxiom> result = createSet();
		for (OWLAnnotationPropertyRangeAxiom ax : getAxiomsInternal(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
			if (ax.getProperty().equals(property)) {
				result.add(ax);
			}
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <T extends OWLAxiom> Set<T> getAxiomsInternal(AxiomType<T> axiomType) {
		return (Set<T>) getAxioms(axiomType, axiomsByType, false);
	}
	
	public Set<OWLAxiom> getReferencingAxioms(OWLAnonymousIndividual individual) {
	    return getReturnSet(getAxioms(individual, owlAnonymousIndividualReferences, false));
	}
	
	 public Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity) {
	        Set<OWLAxiom> axioms;
	        if (owlEntity instanceof OWLClass) {
	            axioms = getAxioms(owlEntity.asOWLClass(), owlClassReferences, false);
	        }
	        else if (owlEntity instanceof OWLObjectProperty) {
	            axioms = getAxioms(owlEntity.asOWLObjectProperty(), owlObjectPropertyReferences, false);
	        }
	        else if (owlEntity instanceof OWLDataProperty) {
	            axioms = getAxioms(owlEntity.asOWLDataProperty(), owlDataPropertyReferences, false);
	        }
	        else if (owlEntity instanceof OWLNamedIndividual) {
	            axioms = getAxioms(owlEntity.asOWLNamedIndividual(), owlIndividualReferences, false);
	        }
	        else if (owlEntity instanceof OWLDatatype) {
	            axioms = getAxioms(owlEntity.asOWLDatatype(), owlDatatypeReferences, false);
	        }
	        else if (owlEntity instanceof OWLAnnotationProperty) {
	            axioms = getAxioms(owlEntity.asOWLAnnotationProperty(), owlAnnotationPropertyReferences, false);
	        }
	        else {
	            axioms = Collections.emptySet();
	        }

	        return getReturnSet(axioms);
	    }

	public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
		return getReturnSet(getAxioms(entity,
				declarationsByEntity, false));
	}
	
	public Set<OWLImportsDeclaration> getImportsDeclarations() {
		return this.getReturnSet(this.importsDeclarations);
	}

	public boolean addImportsDeclaration(OWLImportsDeclaration importDeclaration) {
		if (this.importsDeclarations.contains(importDeclaration)) {
			return false;
		}
		this.importsDeclarations.add(importDeclaration);
		return true;
	}

	public boolean removeImportsDeclaration(
			OWLImportsDeclaration importDeclaration) {
		if (!this.importsDeclarations.contains(importDeclaration)) {
			return false;
		}
		this.importsDeclarations.remove(importDeclaration);
		return true;
	}

	public Set<OWLAnnotation> getOntologyAnnotations() {
		return this.getReturnSet(this.ontologyAnnotations);
	}

	public boolean addOntologyAnnotation(OWLAnnotation ann) {
		return ontologyAnnotations.add(ann);
	}

	public boolean removeOntologyAnnotation(OWLAnnotation ann) {
		return ontologyAnnotations.remove(ann);
	}

	public boolean containsAxiom(OWLAxiom axiom) {
		Set<OWLAxiom> axioms = axiomsByType.get(axiom.getAxiomType());
		return axioms != null && axioms.contains(axiom);
	}

	public int getAxiomCount() {
		int count = 0;
		for (AxiomType<?> type : AXIOM_TYPES) {
			Set<OWLAxiom> axiomSet = axiomsByType.get(type);
			if (axiomSet != null) {
				count += axiomSet.size();
			}
		}
		return count;
	}

	public Set<OWLAxiom> getAxioms() {
		Set<OWLAxiom> axioms = createSet();
		for (AxiomType<?> type : AXIOM_TYPES) {
			Set<OWLAxiom> owlAxiomSet = axiomsByType.get(type);
			if (owlAxiomSet != null) {
				axioms.addAll(owlAxiomSet);
			}
		}
		return axioms;
	}

	@SuppressWarnings("unchecked")
	public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
		return (Set<T>) getAxioms(axiomType, axiomsByType, false);
	}

	/**
	 * Gets the axioms which are of the specified type, possibly from the
	 * imports closure of this ontology
	 * 
	 * @param axiomType
	 *            The type of axioms to be retrived.
	 * @param includeImportsClosure
	 *            if <code>true</code> then axioms of the specified type will
	 *            also be retrieved from the imports closure of this ontology,
	 *            if <code>false</code> then axioms of the specified type will
	 *            only be retrieved from this ontology.
	 * @return A set containing the axioms which are of the specified type. The
	 *         set that is returned is a copy of the axioms in the ontology (and
	 *         its imports closure) - it will not be updated if the ontology
	 *         changes.
	 */
	public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType,
			Collection<OWLOntology> importsClosure) {
		if (importsClosure == null || importsClosure.size() == 0) {
			return getAxioms(axiomType);
		}
		Set<T> result = createSet();
		for (OWLOntology ont : importsClosure) {
			result.addAll(ont.getAxioms(axiomType));
		}
		return result;
	}

	public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
		Set<OWLAxiom> axioms = axiomsByType.get(axiomType);
		if (axioms == null) {
			return 0;
		}
		return axioms.size();
	}

	public Set<OWLLogicalAxiom> getLogicalAxioms() {
		Set<OWLLogicalAxiom> axioms = createSet();
		for (AxiomType<?> type : AXIOM_TYPES) {
			if (type.isLogical()) {
				Set<OWLAxiom> axiomSet = axiomsByType.get(type);
				if (axiomSet != null) {
					for (OWLAxiom ax : axiomSet) {
						axioms.add((OWLLogicalAxiom) ax);
					}
				}
			}
		}
		return axioms;
	}

	public int getLogicalAxiomCount() {
		int count = 0;
		for (AxiomType<?> type : AXIOM_TYPES) {
			if (type.isLogical()) {
				Set<OWLAxiom> axiomSet = axiomsByType.get(type);
				if (axiomSet != null) {
					count += axiomSet.size();
				}
			}
		}
		return count;
	}

	public void addAxiomsByType(AxiomType<?> type, OWLAxiom axiom) {
		addToIndexedSet(type, axiomsByType, axiom);
	}
	
	public void removeAxiomsByType(AxiomType<?> type, OWLAxiom axiom) {
		removeAxiomFromSet(type, axiomsByType, axiom, true);
	}

	public Map<OWLAxiom, Set<OWLAxiom>> getLogicalAxiom2AnnotatedAxiomMap() {
		return new HashMap<OWLAxiom, Set<OWLAxiom>>(
				this.logicalAxiom2AnnotatedAxiomMap);
	}

	public Set<OWLAxiom> getLogicalAxiom2AnnotatedAxiom(OWLAxiom ax) {
		return getReturnSet(logicalAxiom2AnnotatedAxiomMap.get(ax
				.getAxiomWithoutAnnotations()));
	}

	public void addLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax) {
		addToIndexedSet(ax.getAxiomWithoutAnnotations(),
				logicalAxiom2AnnotatedAxiomMap, ax);
	}

	public void removeLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax) {
		removeAxiomFromSet(ax.getAxiomWithoutAnnotations(),
				logicalAxiom2AnnotatedAxiomMap, ax, true);
	}

	public boolean containsLogicalAxiom2AnnotatedAxiomMap(OWLAxiom ax) {
		return logicalAxiom2AnnotatedAxiomMap.containsKey(ax
				.getAxiomWithoutAnnotations());
	}

	public Set<OWLClassAxiom> getGeneralClassAxioms() {
		return getReturnSet(this.generalClassAxioms);
	}

	public void addGeneralClassAxioms(OWLClassAxiom ax) {
		this.generalClassAxioms.add(ax);
	}

	public void removeGeneralClassAxioms(OWLClassAxiom ax) {
		this.generalClassAxioms.remove(ax);
	}

	public Set<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxioms() {
		return getReturnSet(this.propertyChainSubPropertyAxioms);
	}

	public void addPropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax) {
		this.propertyChainSubPropertyAxioms.add(ax);
	}

	public void removePropertyChainSubPropertyAxioms(
			OWLSubPropertyChainOfAxiom ax) {
		this.propertyChainSubPropertyAxioms.remove(ax);
	}

	public Map<OWLClass, Set<OWLAxiom>> getOwlClassReferences() {
		return new HashMap<OWLClass, Set<OWLAxiom>>(this.owlClassReferences);
	}

	public void removeOwlClassReferences(OWLClass c, OWLAxiom ax) {
		removeAxiomFromSet(c, owlClassReferences, ax, true);
	}

	public void addOwlClassReferences(OWLClass c, OWLAxiom ax) {
		addToIndexedSet(c, owlClassReferences, ax);
	}

	public boolean containsOwlClassReferences(OWLClass c) {
		return this.owlClassReferences.containsKey(c);
	}

	public Map<OWLObjectProperty, Set<OWLAxiom>> getOwlObjectPropertyReferences() {
		return new HashMap<OWLObjectProperty, Set<OWLAxiom>>(
				this.owlObjectPropertyReferences);
	}

	public void removeOwlObjectPropertyReferences(OWLObjectProperty p, OWLAxiom ax) {
        removeAxiomFromSet(p, owlObjectPropertyReferences, ax, true);
	}

	public void addOwlObjectPropertyReferences(OWLObjectProperty p, OWLAxiom ax) {
		addToIndexedSet(p, owlObjectPropertyReferences, ax);
	}

	public boolean containsOwlObjectPropertyReferences(OWLObjectProperty c) {
		return this.owlObjectPropertyReferences.containsKey(c);
	}
 
	public Map<OWLDataProperty, Set<OWLAxiom>> getOwlDataPropertyReferences() {
		return new HashMap<OWLDataProperty, Set<OWLAxiom>>( this.owlDataPropertyReferences);
	}

	public void removeOwlDataPropertyReferences(OWLDataProperty c, OWLAxiom ax) {
		removeAxiomFromSet(c, owlDataPropertyReferences, ax, true);
	}

	public void addOwlDataPropertyReferences(OWLDataProperty c, OWLAxiom ax) {
		addToIndexedSet(c, owlDataPropertyReferences, ax);
	}

	public boolean containsOwlDataPropertyReferences(OWLDataProperty c) {
		return this.owlDataPropertyReferences.containsKey(c);
	}

	public Map<OWLNamedIndividual, Set<OWLAxiom>> getOwlIndividualReferences() {
		return this.owlIndividualReferences;
	}


	public void removeOwlIndividualReferences(OWLNamedIndividual c, OWLAxiom ax) {
		removeAxiomFromSet(c, owlIndividualReferences, ax, true);
	}

	public void addOwlIndividualReferences(OWLNamedIndividual c, OWLAxiom ax) {
		addToIndexedSet(c, owlIndividualReferences, ax);
	}

	public boolean containsOwlIndividualReferences(OWLNamedIndividual c) {
		return this.owlIndividualReferences.containsKey(c);
	}

	public Map<OWLAnonymousIndividual, Set<OWLAxiom>> getOwlAnonymousIndividualReferences() {
		return new HashMap<OWLAnonymousIndividual, Set<OWLAxiom>>( this.owlAnonymousIndividualReferences);
	}


	public void removeOwlAnonymousIndividualReferences(OWLAnonymousIndividual c, OWLAxiom ax) {
		removeAxiomFromSet(c, owlAnonymousIndividualReferences, ax, true);
	}

	public void addOwlAnonymousIndividualReferences(OWLAnonymousIndividual c, OWLAxiom ax) {
		addToIndexedSet(c, owlAnonymousIndividualReferences, ax);
	}

	public boolean containsOwlAnonymousIndividualReferences(OWLAnonymousIndividual c) {
		return this.owlAnonymousIndividualReferences.containsKey(c);
	}

	public Map<OWLDatatype, Set<OWLAxiom>> getOwlDatatypeReferences() {
		return new HashMap<OWLDatatype, Set<OWLAxiom>>( this.owlDatatypeReferences);
	}


	public void removeOwlDatatypeReferences(OWLDatatype c, OWLAxiom ax) {
		removeAxiomFromSet(c, owlDatatypeReferences, ax, true);
	}

	public void addOwlDatatypeReferences(OWLDatatype c, OWLAxiom ax) {
		addToIndexedSet(c, owlDatatypeReferences, ax);
	}

	public boolean containsOwlDatatypeReferences(OWLDatatype c) {
		return this.owlDatatypeReferences.containsKey(c);
	}

	public Map<OWLAnnotationProperty, Set<OWLAxiom>> getOwlAnnotationPropertyReferences() {
		return new HashMap<OWLAnnotationProperty, Set<OWLAxiom>>( this.owlAnnotationPropertyReferences);
	}


	public void removeOwlAnnotationPropertyReferences(OWLAnnotationProperty c, OWLAxiom ax) {
		removeAxiomFromSet(c, owlAnnotationPropertyReferences, ax, true);
	}

	public void addOwlAnnotationPropertyReferences(OWLAnnotationProperty c, OWLAxiom ax) {
		addToIndexedSet(c, owlAnnotationPropertyReferences, ax);
	}

	public boolean containsOwlAnnotationPropertyReferences(OWLAnnotationProperty c) {
		return this.owlAnnotationPropertyReferences.containsKey(c);
	}

	public Map<OWLEntity, Set<OWLDeclarationAxiom>> getDeclarationsByEntity() {
		return new HashMap<OWLEntity, Set<OWLDeclarationAxiom>>( this.declarationsByEntity);
	}


	public void removeDeclarationsByEntity(OWLEntity c, OWLDeclarationAxiom ax) {
		removeAxiomFromSet(c, declarationsByEntity, ax, true);
	}

	public void addDeclarationsByEntity(OWLEntity c, OWLDeclarationAxiom ax) {
		addToIndexedSet(c, declarationsByEntity, ax);
	}

	public boolean containsDeclarationsByEntity(OWLEntity c) {
		return this.declarationsByEntity.containsKey(c);
	}
}