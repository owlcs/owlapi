package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.*;

import static org.semanticweb.owlapi.model.AxiomType.*;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;

import org.semanticweb.owlapi.util.OWLEntityCollector;

/*
 * Copyright (C) 2006, University of Manchester
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

/*
 *  based on OWLOntologyImpl svn revision 1294
 */


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 26-Oct-2006<br><br>
 */
public class OWLOntologyImpl extends OWLObjectImpl implements OWLMutableOntology {

    private final OWLOntologyManager manager;

    private OWLOntologyID ontologyID;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Sets of different kinds of axioms
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected OWLOntologyImplInternals internals;

    //private OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker();

    public OWLOntologyImpl(OWLOntologyManager manager, OWLOntologyID ontologyID) {
        super(manager.getOWLDataFactory());
        this.manager = manager;
        this.ontologyID = ontologyID;

        this.internals = new OWLOntologyImplInternalsDefaultImpl();

    }

    public OWLOntologyManager getOWLOntologyManager() {
        return manager;
    }

    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    public boolean isAnonymous() {
        return getOntologyID().isAnonymous();
    }

    protected int compareObjectOfSameType(OWLObject object) {
        if (object == this) {
            return 0;
        }
        OWLOntology other = (OWLOntology) object;
        return getOntologyID().compareTo(other.getOntologyID());
    }


    public boolean isEmpty() {
        for (AxiomType type : internals.getAxiomsByType().keySet()) {
            Set<OWLAxiom> axiomSet = internals.getAxiomsByType().get(type);
            if (axiomSet != null && !axiomSet.isEmpty()) {
                return false;
            }
        }
        return internals.getOntologyAnnotations().isEmpty();
    }


    public int getAxiomCount() {
        int count = 0;
        for (AxiomType type : AXIOM_TYPES) {
            Set<OWLAxiom> axiomSet = internals.getAxiomsByType().get(type);
            if (axiomSet != null) {
                count += axiomSet.size();
            }
        }
        return count;
    }


    public Set<OWLAxiom> getAxioms() {
        Set<OWLAxiom> axioms = createSet();
        for (AxiomType type : AXIOM_TYPES) {
            Set<OWLAxiom> owlAxiomSet = internals.getAxiomsByType().get(type);
            if (owlAxiomSet != null) {
                axioms.addAll(owlAxiomSet);
            }
        }
        return axioms;
    }


    @SuppressWarnings("unchecked")
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        return (Set<T>) internals.getAxioms(axiomType, internals.getAxiomsByType(), false);
    }

    /**
     * Gets the axioms which are of the specified type, possibly from the imports closure of this ontology
     * @param axiomType The type of axioms to be retrived.
     * @param includeImportsClosure if <code>true</code> then axioms of the specified type will also be retrieved from
     * the imports closure of this ontology, if <code>false</code> then axioms of the specified type will only
     * be retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology (and its imports closure) - it will not be updated if the ontology changes.
     */
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getAxioms(axiomType);
        }
        Set<T> result = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getAxioms(axiomType));
        }
        return result;
    }

    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
        Set<OWLAxiom> axioms = internals.getAxiomsByType().get(axiomType);
        if (axioms == null) {
            return 0;
        }
        return axioms.size();
    }

    /**
     * Gets the axiom count of a specific type of axiom, possibly in the imports closure of this ontology
     * @param axiomType The type of axiom to count
     * @param includeImportsClosure Specifies that the imports closure should be included when counting axioms
     * @return The number of the specified types of axioms in this ontology
     */
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getAxiomCount(axiomType);
        }
        int result = 0;
        for (OWLOntology ont : getImportsClosure()) {
            result += ont.getAxiomCount(axiomType);
        }
        return result;
    }

    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        Set<OWLLogicalAxiom> axioms = createSet();
        for (AxiomType type : AXIOM_TYPES) {
            if (type.isLogical()) {
                Set<OWLAxiom> axiomSet = internals.getAxiomsByType().get(type);
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
        for (AxiomType type : AXIOM_TYPES) {
            if (type.isLogical()) {
                Set<OWLAxiom> axiomSet = internals.getAxiomsByType().get(type);
                if (axiomSet != null) {
                    count += axiomSet.size();
                }
            }
        }
        return count;
    }


    public Set<OWLAnnotation> getAnnotations() {
        return internals.getReturnSet(internals.getOntologyAnnotations());
    }

    public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty) {
        Set<OWLSubAnnotationPropertyOfAxiom> result = createSet();
        for (OWLSubAnnotationPropertyOfAxiom ax : internals.getAxiomsInternal(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            if (ax.getSubProperty().equals(subProperty)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(OWLAnnotationProperty property) {
        Set<OWLAnnotationPropertyDomainAxiom> result = createSet();
        for (OWLAnnotationPropertyDomainAxiom ax : internals.getAxiomsInternal(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            if (ax.getProperty().equals(property)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(OWLAnnotationProperty property) {
        Set<OWLAnnotationPropertyRangeAxiom> result = createSet();
        for (OWLAnnotationPropertyRangeAxiom ax : internals.getAxiomsInternal(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            if (ax.getProperty().equals(property)) {
                result.add(ax);
            }
        }
        return result;
    }

    public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
        return internals.getReturnSet(internals.getAxioms(entity, internals.getDeclarationsByEntity(), false));
    }


    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLAnnotationSubject subject) {
        Set<OWLAnnotationAssertionAxiom> axioms = createSet();
        axioms.addAll(internals.getAnnotationAssertionAxiomsBySubject(subject));
        return axioms;
    }


    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(IRI subject) {
        return internals.getAnnotationAssertionAxiomsBySubject(subject);
    }


    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return internals.getReturnSet(internals.getGeneralClassAxioms());
    }


    public Set<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxioms() {
        return internals.getReturnSet(internals.getPropertyChainSubPropertyAxioms());
    }


    public boolean containsAxiom(OWLAxiom axiom) {
        Set<OWLAxiom> axioms = internals.getAxiomsByType().get(axiom.getAxiomType());
        return axioms != null && axioms.contains(axiom);
    }

    /**
     * Determines if this ontology, and possibly the imports closure, contains the specified axiom.
     * @param axiom The axiom to test for.
     * @param includeImportsClosure if <code>true</code> the imports closure of this ontology will be searched for the
     * specific axiom, if <code>false</code> just this ontology will be searched.
     * @return <code>true</code> if the ontology contains the specified axioms, or <code>false</code> if the ontology
     *         doesn't contain the specified axiom.
     */
    public boolean containsAxiom(OWLAxiom axiom, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsAxiom(axiom);
        }
        for (OWLOntology ont : getImportsClosure()) {
            if (ont.containsAxiom(axiom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if this ontology contains the specified axiom, ignoring any annotations on this
     * axiom.
     * @param axiom The axiom to test for.
     * @return <code>true</code> if this ontology contains this axiom with or without annotations.
     */
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom) {
        if (axiom.isAnnotated()) {
            return internals.getLogicalAxiom2AnnotatedAxiomMap().containsKey(axiom.getAxiomWithoutAnnotations());
        }
        else {
            return containsAxiom(axiom) || internals.getLogicalAxiom2AnnotatedAxiomMap().containsKey(axiom);
        }
    }

    /**
     * Determines if this ontology and possibly its imports closure contains the specified axiom,
     * ignoring any annotations on this axiom.
     * @param axiom The axiom to test for.
     * @param includeImportsClosure if <code>true</code> the imports closure of this ontology will be searched for the
     * specified axiom. If <code>false</code> only this ontology will be searched for the specifed axiom.
     * @return <code>true</code> if this ontology contains this axiom with or without annotations.
     */
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsAxiomIgnoreAnnotations(axiom);
        }
        else {
            for (OWLOntology ont : getImportsClosure()) {
                if (ont.containsAxiomIgnoreAnnotations(axiom)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Gets the set of axioms that have the same "logical structure" as the specified axiom.
     * @param axiom The axiom that specifies the logical structure of the axioms to retrieve
     * @return A set of axioms such that for any two axioms, <code>axiomA</code> and
     *         <code>axiomB</code> in the set, <code>axiomA.getAxiomWithoutAnnotations()</code> is equal to
     *         <code>axiomB.getAxiomWithoutAnnotations()</code>.  The specified axiom will be contained in the set.
     */
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom) {
        Set<OWLAxiom> result = createSet();
        if (containsAxiom(axiom)) {
            result.add(axiom);
        }
        Set<OWLAxiom> annotated = internals.getLogicalAxiom2AnnotatedAxiomMap().get(axiom.getAxiomWithoutAnnotations());
        if (annotated != null) {
            result.addAll(annotated);
        }
        return result;
    }

    /**
     * Gets the set of axioms that have the same "logical structure" as the specified axiom, possibly searching
     * the imports closure of this ontology.
     * @param axiom The axiom that specifies the logical structure of the axioms to retrieve.  If this axiom is annotated
     * then the annotations are ignored.
     * @param includeImportsClosure if <code>true</code> then axioms in the imports closure of this ontology are returned,
     * if <code>false</code> only axioms in this ontology will be returned.
     * @return A set of axioms such that for any two axioms, <code>axiomA</code> and
     *         <code>axiomB</code> in the set, <code>axiomA.getAxiomWithoutAnnotations()</code> is equal to
     *         <code>axiomB.getAxiomWithoutAnnotations()</code>.  The specified axiom will be contained in the set.
     */
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getAxiomsIgnoreAnnotations(axiom);
        }
        Set<OWLAxiom> result = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getAxiomsIgnoreAnnotations(axiom));
        }
        return result;
    }

    public boolean containsClassInSignature(IRI owlClassIRI) {
        return internals.getOwlClassReferences().containsKey(getOWLDataFactory().getOWLClass(owlClassIRI));
    }

    public boolean containsClassInSignature(IRI owlClassIRI, boolean includeImportsClosure) {
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsClassInSignature(owlClassIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsObjectPropertyInSignature(IRI propIRI) {
        return internals.getOwlObjectPropertyReferences().containsKey(getOWLDataFactory().getOWLObjectProperty(propIRI));
    }

    public boolean containsObjectPropertyInSignature(IRI propIRI, boolean includeImportsClosure) {
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsObjectPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDataPropertyInSignature(IRI propIRI) {
        return internals.getOwlDataPropertyReferences().containsKey(getOWLDataFactory().getOWLDataProperty(propIRI));
    }

    public boolean containsDataPropertyInSignature(IRI propIRI, boolean includeImportsClosure) {
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsDataPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAnnotationPropertyInSignature(IRI propIRI) {
        boolean b = internals.getOwlAnnotationPropertyReferences().containsKey(getOWLDataFactory().getOWLAnnotationProperty(propIRI));
        if (b) {
            return true;
        }
        else {
            for (OWLAnnotation anno : internals.getOntologyAnnotations()) {
                if (anno.getProperty().getIRI().equals(propIRI)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsAnnotationPropertyInSignature(IRI propIRI, boolean includeImportsClosure) {
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsAnnotationPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsIndividualInSignature(IRI individualIRI) {
        return internals.getOwlIndividualReferences().containsKey(getOWLDataFactory().getOWLNamedIndividual(individualIRI));
    }


    public boolean containsIndividualInSignature(IRI individualIRI, boolean includeImportsClosure) {
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsIndividualInSignature(individualIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDatatypeInSignature(IRI datatypeIRI) {
        return internals.getOwlDatatypeReferences().containsKey(getOWLDataFactory().getOWLDatatype(datatypeIRI));
    }

    public boolean containsDatatypeInSignature(IRI datatypeIRI, boolean includeImportsClosure) {
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsDatatypeInSignature(datatypeIRI)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the entities in the signature of this ontology that have the specified IRI
     * @param iri The IRI
     * @return A set of entities that are in the signature of this ontology that have the specified IRI.  The
     *         set will be empty if there are no entities in the signature of this ontology with the specified IRI.
     */
    public Set<OWLEntity> getEntitiesInSignature(IRI iri) {
        Set<OWLEntity> result = createSet(6);
        if (containsClassInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLClass(iri));
        }
        if (containsObjectPropertyInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLObjectProperty(iri));
        }
        if (containsDataPropertyInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLDataProperty(iri));
        }
        if (containsIndividualInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLNamedIndividual(iri));
        }
        if (containsDatatypeInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLDatatype(iri));
        }
        if (containsAnnotationPropertyInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLAnnotationProperty(iri));
        }
        return result;
    }

    /**
     * Gets the entities in the signature of this ontology, and possibly its imports closure, that have the specified IRI
     * @param iri The IRI
     * @param includeImportsClosure Specifies if the imports closure signature should be taken into account
     * @return A set of entities that are in the signature of this ontology and possibly its imports closure
     *         that have the specified IRI.  The set will be empty if there are no entities in the signature of this ontology
     *         and possibly its imports closure with the specified IRI.
     */
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getEntitiesInSignature(iri);
        }
        else {
            Set<OWLEntity> result = createSet(6);
            for (OWLOntology ont : getImportsClosure()) {
                result.addAll(ont.getEntitiesInSignature(iri));
            }
            return result;
        }
    }

    public boolean containsReference(OWLClass owlClass) {
        return internals.getOwlClassReferences().containsKey(owlClass);
    }


    public boolean containsReference(OWLObjectProperty prop) {
        return internals.getOwlObjectPropertyReferences().containsKey(prop);
    }


    public boolean containsReference(OWLDataProperty prop) {
        return internals.getOwlDataPropertyReferences().containsKey(prop);
    }


    public boolean containsReference(OWLNamedIndividual ind) {
        return internals.getOwlIndividualReferences().containsKey(ind);
    }


    public boolean containsReference(OWLDatatype dt) {
        return internals.getOwlDatatypeReferences().containsKey(dt);
    }


    public boolean containsReference(OWLAnnotationProperty property) {
        return internals.getOwlAnnotationPropertyReferences().containsKey(property);
    }


    public boolean isDeclared(OWLEntity owlEntity) {
        OWLDeclarationAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(owlEntity);
        return internals.getAxiomsInternal(DECLARATION).contains(ax);
    }

    public boolean isDeclared(OWLEntity owlEntity, boolean includeImportsClosure) {
        if (isDeclared(owlEntity)) {
            return true;
        }
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (!ont.equals(this)) {
                if (ont.isDeclared(owlEntity)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsEntityInSignature(OWLEntity owlEntity) {
    	OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker();
        return entityReferenceChecker.containsReference(owlEntity);
    }

    /**
     * Determines if the ontology, and possibly its imports closure, contains a reference to the specified entity.
     * @param owlEntity The entity
     * @param includeImportsClosure Specifies whether the imports closure should be examined for the entity reference
     * or not.
     * @return <code>true</code> if the ontology contains a reference to the specified entity, otherwise
     *         <code>false</code> The set that is returned is a copy - it will not be updated if the ontology changes.
     *         It is therefore safe to apply changes to this ontology while iterating over this set.
     */
    public boolean containsEntityInSignature(OWLEntity owlEntity, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsEntityInSignature(owlEntity);
        }
        for (OWLOntology ont : getImportsClosure()) {
            if (ont.containsEntityInSignature(owlEntity)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEntityInSignature(IRI entityIRI) {
        if (containsClassInSignature(entityIRI)) {
            return true;
        }
        if (containsObjectPropertyInSignature(entityIRI)) {
            return true;
        }
        if (containsDataPropertyInSignature(entityIRI)) {
            return true;
        }
        if (containsIndividualInSignature(entityIRI)) {
            return true;
        }
        if (containsDatatypeInSignature(entityIRI)) {
            return true;
        }
        if (containsAnnotationPropertyInSignature(entityIRI)) {
            return true;
        }
        return false;
    }

    public boolean containsEntityInSignature(IRI entityIRI, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsEntityInSignature(entityIRI);
        }
        for (OWLOntology ont : getImportsClosure()) {
            if (ont.containsEntityInSignature(entityIRI)) {
                return true;
            }
        }
        return false;
    }

    public Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity) {
        Set<OWLAxiom> axioms;
        if (owlEntity instanceof OWLClass) {
            axioms = internals.getAxioms(owlEntity.asOWLClass(), internals.getOwlClassReferences(), false);
        }
        else if (owlEntity instanceof OWLObjectProperty) {
            axioms = internals.getAxioms(owlEntity.asOWLObjectProperty(), internals.getOwlObjectPropertyReferences(), false);
        }
        else if (owlEntity instanceof OWLDataProperty) {
            axioms = internals.getAxioms(owlEntity.asOWLDataProperty(), internals.getOwlDataPropertyReferences(), false);
        }
        else if (owlEntity instanceof OWLNamedIndividual) {
            axioms = internals.getAxioms(owlEntity.asOWLNamedIndividual(), internals.getOwlIndividualReferences(), false);
        }
        else if (owlEntity instanceof OWLDatatype) {
            axioms = internals.getAxioms(owlEntity.asOWLDatatype(), internals.getOwlDatatypeReferences(), false);
        }
        else if (owlEntity instanceof OWLAnnotationProperty) {
            axioms = internals.getAxioms(owlEntity.asOWLAnnotationProperty(), internals.getOwlAnnotationPropertyReferences(), false);
        }
        else {
            axioms = Collections.emptySet();
        }

        return createSet(axioms);
    }

    /**
     * Gets the axioms where the specified entity appears in the signature of the axiom. The set that is returned,
     * contains all axioms that directly reference the specified entity.
     * @param owlEntity The entity that should be directly referred to by an axiom that appears in the results set.
     * @param includeImportsClosure Specifies if the axioms returned should just be from this ontology, or from the
     * imports closure of this ontology.  If <code>true</code> the axioms returned will be from the imports closure
     * of this ontology, if <code>false</code> the axioms returned will just be from this ontology.
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    public Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity, boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getReferencingAxioms(owlEntity);
        }
        Set<OWLAxiom> result = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getReferencingAxioms(owlEntity));
        }
        return result;
    }

    /**
     * Gets the axioms that reference the specified anonymous individual
     * @param individual The individual
     * @return The axioms that reference the specified anonymous individual
     */
    public Set<OWLAxiom> getReferencingAxioms(OWLAnonymousIndividual individual) {
        return internals.getReturnSet(internals.getAxioms(individual, internals.getOwlAnonymousIndividualReferences(), false));
    }

    public Set<OWLClassAxiom> getAxioms(final OWLClass cls) {
        return internals.getAxioms(cls);
    }


    public Set<OWLObjectPropertyAxiom> getAxioms(final OWLObjectPropertyExpression prop) {
        final Set<OWLObjectPropertyAxiom> result = createSet(50);

        result.addAll(getAsymmetricObjectPropertyAxioms(prop));
        result.addAll(getReflexiveObjectPropertyAxioms(prop));
        result.addAll(getSymmetricObjectPropertyAxioms(prop));
        result.addAll(getIrreflexiveObjectPropertyAxioms(prop));
        result.addAll(getTransitiveObjectPropertyAxioms(prop));
        result.addAll(getInverseFunctionalObjectPropertyAxioms(prop));
        result.addAll(getFunctionalObjectPropertyAxioms(prop));
        result.addAll(getInverseObjectPropertyAxioms(prop));
        result.addAll(getObjectPropertyDomainAxioms(prop));
        result.addAll(getEquivalentObjectPropertiesAxioms(prop));
        result.addAll(getDisjointObjectPropertiesAxioms(prop));
        result.addAll(getObjectPropertyRangeAxioms(prop));
        result.addAll(getObjectSubPropertyAxiomsForSubProperty(prop));
        return result;
    }

    public Set<OWLAnnotationAxiom> getAxioms(final OWLAnnotationProperty prop) {
        Set<OWLAnnotationAxiom> result = createSet();
        for (OWLSubAnnotationPropertyOfAxiom ax : getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            if (ax.getSubProperty().equals(prop)) {
                result.add(ax);
            }
        }
        for (OWLAnnotationPropertyRangeAxiom ax : getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            if (ax.getProperty().equals(prop)) {
                result.add(ax);
            }
        }
        for (OWLAnnotationPropertyDomainAxiom ax : getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            if (ax.getProperty().equals(prop)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLDataPropertyAxiom> getAxioms(final OWLDataProperty prop) {
        final Set<OWLDataPropertyAxiom> result = createSet();
        result.addAll(getDataPropertyDomainAxioms(prop));
        result.addAll(getEquivalentDataPropertiesAxioms(prop));
        result.addAll(getDisjointDataPropertiesAxioms(prop));
        result.addAll(getDataPropertyRangeAxioms(prop));
        result.addAll(getFunctionalDataPropertyAxioms(prop));
        result.addAll(getDataSubPropertyAxiomsForSubProperty(prop));
        return result;
    }


    public Set<OWLIndividualAxiom> getAxioms(final OWLIndividual individual) {
        final Set<OWLIndividualAxiom> result = createSet();
        result.addAll(getClassAssertionAxioms(individual));
        result.addAll(getObjectPropertyAssertionAxioms(individual));
        result.addAll(getDataPropertyAssertionAxioms(individual));
        result.addAll(getNegativeObjectPropertyAssertionAxioms(individual));
        result.addAll(getNegativeDataPropertyAssertionAxioms(individual));
        result.addAll(getSameIndividualAxioms(individual));
        result.addAll(getDifferentIndividualAxioms(individual));
        return result;
    }

    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype) {
        return getDatatypeDefinitions(datatype);
    }

    public Set<OWLNamedObject> getReferencedObjects() {
        Set<OWLNamedObject> result = createSet();
        result.addAll(internals.getOwlClassReferences().keySet());
        // Consider doing this in a more efficient way (although typically, the number of
        // properties in an ontology isn't large)
        for (OWLObjectPropertyExpression prop : internals.getOwlObjectPropertyReferences().keySet()) {
            if (!prop.isAnonymous()) {
                result.add((OWLObjectProperty) prop);
            }
        }
        result.addAll(internals.getOwlDataPropertyReferences().keySet());
        result.addAll(internals.getOwlIndividualReferences().keySet());
        return result;
    }


    public Set<OWLEntity> getSignature() {
        // We might want to cache this for performance reasons,
        // but I'm not sure right now.
        Set<OWLEntity> entities = createSet();
        entities.addAll(getClassesInSignature());
        entities.addAll(getObjectPropertiesInSignature());
        entities.addAll(getDataPropertiesInSignature());
        entities.addAll(getIndividualsInSignature());
        entities.addAll(getDatatypesInSignature());
        entities.addAll(getAnnotationPropertiesInSignature());
        return entities;
    }

    public Set<OWLEntity> getSignature(boolean includeImportsClosure) {
        Set<OWLEntity> entities = getSignature();
        if (includeImportsClosure) {
            for (OWLOntology ont : getImportsClosure()) {
                if (!ont.equals(this)) {
                    entities.addAll(ont.getSignature());
                }
            }
        }
        return entities;
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return internals.getReturnSet(internals.getOwlClassReferences().keySet());
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return internals.getReturnSet(internals.getOwlDataPropertyReferences().keySet());
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return internals.getReturnSet(internals.getOwlObjectPropertyReferences().keySet());
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return internals.getReturnSet(internals.getOwlIndividualReferences().keySet());
    }

    /**
     * A convenience method that obtains the datatypes
     * that are in the signature of this object
     * @return A set containing the datatypes that are in the signature
     *         of this object.
     */
    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return internals.getReturnSet(internals.getOwlDatatypeReferences().keySet());
    }

    /**
     * Gets the classes that are referenced by axioms in this ontology, and possibly the imports closure of this
     * ontology.
     * @param includeImportsClosure Specifies whether referenced classes should be drawn from this ontology or the imports
     * closure.  If <code>true</code> then the set of referenced classes will be from the imports closure of this
     * ontology, if <code>false</code> then the set of referenced classes will just be from this ontology.
     * @return A set of named classes, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    public Set<OWLClass> getClassesInSignature(boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getClassesInSignature();
        }
        Set<OWLClass> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getClassesInSignature());
        }
        return results;
    }

    /**
     * Gets the object properties that are referenced by axioms in this ontology, and possibly the imports closure of this
     * ontology.
     * @param includeImportsClosure Specifies whether referenced object properties should be drawn from this ontology or the imports
     * closure.  If <code>true</code> then the set of referenced object properties will be from the imports closure of this
     * ontology, if <code>false</code> then the set of referenced object properties will just be from this ontology.
     * @return A set of object properties, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getObjectPropertiesInSignature();
        }
        Set<OWLObjectProperty> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getObjectPropertiesInSignature());
        }
        return results;
    }

    /**
     * Gets the data properties that are referenced by axioms in this ontology, and possibly the imports closure of this
     * ontology.
     * @param includeImportsClosure Specifies whether referenced data properties should be drawn from this ontology or the imports
     * closure.  If <code>true</code> then the set of referenced data properties will be from the imports closure of this
     * ontology, if <code>false</code> then the set of referenced data properties will just be from this ontology.
     * @return A set of data properties, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    public Set<OWLDataProperty> getDataPropertiesInSignature(boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getDataPropertiesInSignature();
        }
        Set<OWLDataProperty> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getDataPropertiesInSignature());
        }
        return results;
    }

    /**
     * Gets the named individuals that are referenced by axioms in this ontology, and possibly the imports closure of this
     * ontology.
     * @param includeImportsClosure Specifies whether referenced named individuals should be drawn from this ontology or the imports
     * closure.  If <code>true</code> then the set of referenced named individuals will be from the imports closure of this
     * ontology, if <code>false</code> then the set of referenced named individuals will just be from this ontology.
     * @return A set of named individuals, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    public Set<OWLNamedIndividual> getIndividualsInSignature(boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getIndividualsInSignature();
        }
        Set<OWLNamedIndividual> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getIndividualsInSignature());
        }
        return results;
    }

    /**
     * Gets the referenced anonymous individuals
     * @return The set of referenced anonymous individuals
     */
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals() {
        return internals.getReturnSet(internals.getOwlAnonymousIndividualReferences().keySet());
    }

    /**
     * Gets the datatypes that are referenced by this ontology and possibly its imports closure
     * @param includeImportsClosure Specifies whether referenced named individuals should be drawn from this ontology or the imports
     * closure of this ontology.  If <code>true</code> then the set of referenced named individuals will be from the
     * imports closure of this ontology, if <code>false</code> then the set of referenced named individuals will just
     * be from this ontology.
     * @return The set of datatypes that are referenced by axioms in this ontology and possibly its imports closure
     */
    public Set<OWLDatatype> getDatatypesInSignature(boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getDatatypesInSignature();
        }
        Set<OWLDatatype> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getDatatypesInSignature());
        }
        return results;
    }

    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        Set<OWLAnnotationProperty> props = createSet(internals.getOwlAnnotationPropertyReferences().keySet());
        for (OWLAnnotation anno : internals.getOntologyAnnotations()) {
            props.add(anno.getProperty());
        }
        return internals.getReturnSet(props);
    }

    public Set<OWLAnnotationProperty> getReferencedAnnotationProperties(boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getAnnotationPropertiesInSignature();
        }
        Set<OWLAnnotationProperty> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getAnnotationPropertiesInSignature());
        }
        return results;
    }

    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return internals.getReturnSet(internals.getImportsDeclarations());
    }

    /**
     * Gets the set of IRIs corresponding to the IRIs of the ontology documents that are directly imported by this ontology.
     * This corresponds to the IRIs defined by the directlyImportsDocuments relation as discussed in Section 3 of the
     * OWL 2 Structural specification.
     * @return A set of IRIs where each IRI represents the IRI of an ontology document that was directly imported by this
     *         ontology.
     * @throws org.semanticweb.owlapi.model.UnknownOWLOntologyException
     *          If this ontology is no longer managed by its manager because it was
     *          removed from the manager.
     */
    public Set<IRI> getDirectImportsDocuments() throws UnknownOWLOntologyException {
        Set<IRI> result = createSet();
        for (OWLImportsDeclaration importsDeclaration : internals.getImportsDeclarations()) {
            result.add(importsDeclaration.getIRI());
        }
        return result;
    }

    public Set<OWLOntology> getImports() throws UnknownOWLOntologyException {
        return manager.getImports(this);
    }

    /**
     * Gets the ontologies that are directly imported by this ontology.  This corresponds to the notion of logical
     * direct imports as discussed in Section 3.4 of the OWL 2 Structural Specification.  The direct imports are
     * obtained by accessing the directly imported ontology documents and converting (parsing) them into OWL 2 ontologies.
     * Note that there may be fewer ontologies in the set returned by this method than there are IRIs in the set returned by the
     * getDirectImportsDocuments method.  This will be the case if some of the ontologies that are directly imported by this ontology
     * are not loaded for whatever reason.
     * @return The set of ontologies that are <em>logically directly imported</em> by this ontology
     * @throws org.semanticweb.owlapi.model.UnknownOWLOntologyException
     *          If this ontology is no longer managed by its manager because it was removed
     *          from the manager.
     */
    public Set<OWLOntology> getDirectImports() throws UnknownOWLOntologyException {
        return manager.getDirectImports(this);
    }

    public Set<OWLOntology> getImportsClosure() throws UnknownOWLOntologyException {
        return getOWLOntologyManager().getImportsClosure(this);
    }

    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(OWLDatatype datatype) {
        Set<OWLDatatypeDefinitionAxiom> result = createSet();
        Set<OWLDatatypeDefinitionAxiom> axioms = internals.getAxiomsInternal(AxiomType.DATATYPE_DEFINITION);
        for (OWLDatatypeDefinitionAxiom ax : axioms) {
            if (ax.getDatatype().equals(datatype)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        return internals.getSubClassAxiomsForSubClass(cls);
    }


    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        return internals.getSubClassAxiomsForSuperClass(cls);
    }


    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls) {
        return internals.getEquivalentClassesAxioms(cls);
    }


    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        return internals.getDisjointClassesAxioms(cls);
    }


    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        return internals.getDisjointUnionAxioms(owlClass);
    }


    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
        return internals.getHasKeyAxioms(cls);
    }


    // Object properties

    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression property) {
        return internals.getObjectSubPropertyAxiomsForSubProperty(property);
    }


    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression property) {
        return internals.getObjectSubPropertyAxiomsForSuperProperty(property);
    }


    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        return internals.getObjectPropertyDomainAxioms(property);
    }


    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        return internals.getObjectPropertyRangeAxioms(property);
    }


    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return internals.getInverseObjectPropertyAxioms(property);
    }


    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(OWLObjectPropertyExpression property) {
        return internals.getEquivalentObjectPropertiesAxioms(property);
    }


    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(OWLObjectPropertyExpression property) {
        return internals.getDisjointObjectPropertiesAxioms(property);
    }

    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return internals.getFunctionalObjectPropertyAxioms(property);
    }


    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return internals.getInverseFunctionalObjectPropertyAxioms(property);
    }


    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return internals.getSymmetricObjectPropertyAxioms(property);
    }


    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return internals.getAsymmetricObjectPropertyAxioms(property);
    }


    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return internals.getReflexiveObjectPropertyAxioms(property);
    }


    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return internals.getIrreflexiveObjectPropertyAxioms(property);
    }


    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return internals.getTransitiveObjectPropertyAxioms(property);
    }


    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(OWLDataPropertyExpression property) {
        return internals.getFunctionalDataPropertyAxioms(property);
    }


    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty) {
        return internals.getDataSubPropertyAxiomsForSubProperty(lhsProperty);
    }


    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression property) {
        return internals.getDataSubPropertyAxiomsForSuperProperty(property);
    }


    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty property) {
        return internals.getDataPropertyDomainAxioms(property);
    }


    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(OWLDataProperty property) {
        return internals.getDataPropertyRangeAxioms(property);
    }


    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(OWLDataProperty property) {
        return internals.getEquivalentDataPropertiesAxioms(property);
    }


    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(OWLDataProperty property) {
        return internals.getDisjointDataPropertiesAxioms(property);
    }

    ////


    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual) {
        return internals.getClassAssertionAxioms(individual);
    }


    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass type) {
        return internals.getClassAssertionAxioms(type);
    }


    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual individual) {
        return internals.getDataPropertyAssertionAxioms(individual);
    }


    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return internals.getObjectPropertyAssertionAxioms(individual);
    }


    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return internals.getNegativeObjectPropertyAssertionAxioms(individual);
    }


    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        return internals.getNegativeDataPropertyAssertionAxioms(individual);
    }


    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual) {
        return internals.getSameIndividualAxioms(individual);
    }


    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual individual) {
        return internals.getDifferentIndividualAxioms(individual);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///
    /// Ontology Change handling mechanism
    ///
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //private ChangeAxiomVisitor changeVisitor = new ChangeAxiomVisitor();

    //private OWLOntologyChangeFilter changeFilter = new OWLOntologyChangeFilter();


    public List<OWLOntologyChange> applyChange(OWLOntologyChange change) {
        List<OWLOntologyChange> appliedChanges = new ArrayList<OWLOntologyChange>(2);
        OWLOntologyChangeFilter changeFilter = new OWLOntologyChangeFilter();
//        changeFilter.reset();
        change.accept(changeFilter);
        List<OWLOntologyChange> applied = changeFilter.getAppliedChanges();
        if (applied.size() == 1) {
            appliedChanges.add(change);
        }
        else {
            appliedChanges.addAll(applied);
        }
        return appliedChanges;
    }


    public List<OWLOntologyChange> applyChanges(List<OWLOntologyChange> changes) {
        List<OWLOntologyChange> appliedChanges = new ArrayList<OWLOntologyChange>();
        OWLOntologyChangeFilter changeFilter = new OWLOntologyChangeFilter();
        for (OWLOntologyChange change : changes) {
            change.accept(changeFilter);
            appliedChanges.addAll(changeFilter.getAppliedChanges());
            changeFilter.reset();
        }
        return appliedChanges;
    }


    private class OWLOntologyChangeFilter implements OWLOntologyChangeVisitor {

        private List<OWLOntologyChange> appliedChanges;


        public OWLOntologyChangeFilter() {
            appliedChanges = new ArrayList<OWLOntologyChange>();
        }


        public List<OWLOntologyChange> getAppliedChanges() {
            return appliedChanges;
        }


        public void reset() {
            appliedChanges.clear();
        }


        public void visit(RemoveAxiom change) {
            OWLAxiom axiom = change.getAxiom();
            if (containsAxiom(axiom)) {
            	ChangeAxiomVisitor changeVisitor = new ChangeAxiomVisitor();
                changeVisitor.setAddAxiom(false);
                axiom.accept(changeVisitor);
                appliedChanges.add(change);
                handleAxiomRemoved(axiom);
            }
        }


        public void visit(SetOntologyID change) {
            OWLOntologyID id = change.getNewOntologyID();
            if (!id.equals(ontologyID)) {
                appliedChanges.add(change);
                ontologyID = id;
            }
        }


        public void visit(AddAxiom change) {
            OWLAxiom axiom = change.getAxiom();
            if (!containsAxiom(axiom)) {
            	ChangeAxiomVisitor changeVisitor = new ChangeAxiomVisitor();
                changeVisitor.setAddAxiom(true);
                axiom.accept(changeVisitor);
                appliedChanges.add(change);
                handleAxiomAdded(axiom);
            }
        }


        public void visit(AddImport change) {
            if (!internals.getImportsDeclarations().contains(change.getImportDeclaration())) {
                appliedChanges.add(change);
                internals.getImportsDeclarations().add(change.getImportDeclaration());
            }
        }


        public void visit(RemoveImport change) {
            if (internals.getImportsDeclarations().contains(change.getImportDeclaration())) {
                appliedChanges.add(change);
                internals.getImportsDeclarations().remove(change.getImportDeclaration());
            }
        }


        public void visit(AddOntologyAnnotation change) {
            if (internals.getOntologyAnnotations().add(change.getAnnotation())) {
                appliedChanges.add(change);
            }
        }


        public void visit(RemoveOntologyAnnotation change) {
            if (internals.getOntologyAnnotations().remove(change.getAnnotation())) {
                appliedChanges.add(change);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Handlers for when axioms are added/removed, which perform various global indexing
    // housekeeping tasks.
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////

    //private OWLNamedObjectReferenceAdder referenceAdder = new OWLNamedObjectReferenceAdder();


    private void handleAxiomAdded(OWLAxiom axiom) {
        OWLEntityCollector entityCollector = new OWLEntityCollector();
        OWLNamedObjectReferenceAdder referenceAdder = new OWLNamedObjectReferenceAdder();
        axiom.accept(entityCollector);
        for (OWLEntity object : entityCollector.getObjects()) {
            referenceAdder.setAxiom(axiom);
            object.accept(referenceAdder);
        }
        for (OWLAnonymousIndividual ind : entityCollector.getAnonymousIndividuals()) {
            internals.addToIndexedSet(ind, internals.getOwlAnonymousIndividualReferences(), axiom);
        }
        if (axiom.isAnnotated()) {
            internals.addToIndexedSet(axiom.getAxiomWithoutAnnotations(), internals.getLogicalAxiom2AnnotatedAxiomMap(), axiom);
        }
    }


    //private OWLNamedObjectReferenceRemover referenceRemover = new OWLNamedObjectReferenceRemover();


    private void handleAxiomRemoved(OWLAxiom axiom) {
        OWLEntityCollector entityCollector = new OWLEntityCollector();
        OWLNamedObjectReferenceRemover referenceRemover = new OWLNamedObjectReferenceRemover();
        axiom.accept(entityCollector);
        for (OWLEntity object : entityCollector.getObjects()) {
            referenceRemover.setAxiom(axiom);
            object.accept(referenceRemover);
        }
        for (OWLAnonymousIndividual ind : entityCollector.getAnonymousIndividuals()) {
            internals.removeAxiomFromSet(ind, internals.getOwlAnonymousIndividualReferences(), axiom, true);
        }
        if (axiom.isAnnotated()) {
            internals.removeAxiomFromSet(axiom.getAxiomWithoutAnnotations(), internals.getLogicalAxiom2AnnotatedAxiomMap(), axiom, true);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // An inner helper class that adds the appropriate references indexes for a given axiom
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private class OWLNamedObjectReferenceAdder implements OWLEntityVisitor {

        private OWLAxiom axiom;


        public void setAxiom(OWLAxiom axiom) {
            this.axiom = axiom;
        }


        public void visit(OWLClass owlClass) {
            internals.addToIndexedSet(owlClass, internals.getOwlClassReferences(), axiom);
        }


        public void visit(OWLObjectProperty property) {
            internals.addToIndexedSet(property, internals.getOwlObjectPropertyReferences(), axiom);
        }


        public void visit(OWLDataProperty property) {
            internals.addToIndexedSet(property, internals.getOwlDataPropertyReferences(), axiom);
        }


        public void visit(OWLNamedIndividual owlIndividual) {
            internals.addToIndexedSet(owlIndividual, internals.getOwlIndividualReferences(), axiom);
        }

        public void visit(OWLAnnotationProperty property) {
            internals.addToIndexedSet(property, internals.getOwlAnnotationPropertyReferences(), axiom);
        }

        public void visit(OWLDatatype datatype) {
            internals.addToIndexedSet(datatype, internals.getOwlDatatypeReferences(), axiom);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // An inner helper class that removes the appropriate references indexes for a given axiom
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private class OWLNamedObjectReferenceRemover implements OWLEntityVisitor {

        private OWLAxiom axiom;


        public void setAxiom(OWLAxiom axiom) {
            this.axiom = axiom;
        }


        public void visit(OWLClass owlClass) {
            internals.removeAxiomFromSet(owlClass, internals.getOwlClassReferences(), axiom, true);
        }


        public void visit(OWLObjectProperty property) {
            internals.removeAxiomFromSet(property, internals.getOwlObjectPropertyReferences(), axiom, true);
        }


        public void visit(OWLDataProperty property) {
            internals.removeAxiomFromSet(property, internals.getOwlDataPropertyReferences(), axiom, true);
        }


        public void visit(OWLNamedIndividual owlIndividual) {
            internals.removeAxiomFromSet(owlIndividual, internals.getOwlIndividualReferences(), axiom, true);
        }

        public void visit(OWLAnnotationProperty property) {
            internals.removeAxiomFromSet(property, internals.getOwlAnnotationPropertyReferences(), axiom, true);
        }

        public void visit(OWLDatatype datatype) {
            internals.removeAxiomFromSet(datatype, internals.getOwlDatatypeReferences(), axiom, true);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Add/Remove axiom mechanism.  Each axiom gets visited by a visitor, which adds the axiom
    // to the appropriate index.
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private class ChangeAxiomVisitor implements OWLAxiomVisitor {

        private boolean addAxiom = false;


        public void setAddAxiom(boolean addAxiom) {
            this.addAxiom = addAxiom;
        }


        public void visit(OWLSubClassOfAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(SUBCLASS_OF, internals.getAxiomsByType(), axiom);
                if (!axiom.getSubClass().isAnonymous()) {
                    OWLClass subClass = (OWLClass) axiom.getSubClass();
                    internals.addToIndexedSet(subClass, internals.getSubClassAxiomsByLHS(), axiom);
                    internals.addToIndexedSet(subClass, internals.getClassAxiomsByClass(), axiom);
                }
                else {
                    internals.getGeneralClassAxioms().add(axiom);
                }
                if (!axiom.getSuperClass().isAnonymous()) {
                    internals.addToIndexedSet((OWLClass) axiom.getSuperClass(), internals.getSubClassAxiomsByRHS(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(SUBCLASS_OF, internals.getAxiomsByType(), axiom, true);
                if (!axiom.getSubClass().isAnonymous()) {
                    OWLClass subClass = (OWLClass) axiom.getSubClass();
                    internals.removeAxiomFromSet(subClass, internals.getSubClassAxiomsByLHS(), axiom, true);
                    internals.removeAxiomFromSet(subClass, internals.getClassAxiomsByClass(), axiom, true);
                }
                else {
                    internals.removeAxiomFromSet(axiom, internals.getGeneralClassAxioms());
                }
                if (!axiom.getSuperClass().isAnonymous()) {
                    internals.removeAxiomFromSet(axiom.getSuperClass().asOWLClass(), internals.getSubClassAxiomsByRHS(), axiom, true);
                }
            }
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(axiom.getSubject(), internals.getNegativeObjectPropertyAssertionAxiomsByIndividual(), axiom);
                internals.addToIndexedSet(NEGATIVE_OBJECT_PROPERTY_ASSERTION, internals.getAxiomsByType(), axiom);
            }
            else {
                internals.removeAxiomFromSet(NEGATIVE_OBJECT_PROPERTY_ASSERTION, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSubject(), internals.getNegativeObjectPropertyAssertionAxiomsByIndividual(), axiom, true);
            }
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(axiom.getProperty(), internals.getAsymmetricPropertyAxiomsByProperty(), axiom);
                internals.addToIndexedSet(ASYMMETRIC_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom);
            }
            else {
                internals.removeAxiomFromSet(ASYMMETRIC_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getAsymmetricPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(axiom.getProperty(), internals.getReflexivePropertyAxiomsByProperty(), axiom);
                internals.addToIndexedSet(REFLEXIVE_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom);
            }
            else {
                internals.removeAxiomFromSet(REFLEXIVE_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getReflexivePropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(DISJOINT_CLASSES, internals.getAxiomsByType(), axiom);
                boolean allAnon = true;
                // Index against each named class in the axiom
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        OWLClass cls = (OWLClass) desc;
                        internals.addToIndexedSet(cls, internals.getDisjointClassesAxiomsByClass(), axiom);
                        internals.addToIndexedSet(cls, internals.getClassAxiomsByClass(), axiom);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    internals.getGeneralClassAxioms().add(axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(DISJOINT_CLASSES, internals.getAxiomsByType(), axiom, true);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        OWLClass cls = (OWLClass) desc;
                        internals.removeAxiomFromSet(cls, internals.getDisjointClassesAxiomsByClass(), axiom, true);
                        internals.removeAxiomFromSet(cls, internals.getClassAxiomsByClass(), axiom, true);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    internals.getGeneralClassAxioms().remove(axiom);
                }
            }
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(DATA_PROPERTY_DOMAIN, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getDataPropertyDomainAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(DATA_PROPERTY_DOMAIN, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getDataPropertyDomainAxiomsByProperty(), axiom, true);
            }
        }

        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(OBJECT_PROPERTY_DOMAIN, internals.getAxiomsByType(), axiom);
                if (axiom.getProperty() instanceof OWLObjectProperty) {
                    internals.addToIndexedSet(axiom.getProperty(), internals.getObjectPropertyDomainAxiomsByProperty(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(OBJECT_PROPERTY_DOMAIN, internals.getAxiomsByType(), axiom, true);
                if (axiom.getProperty() instanceof OWLObjectProperty) {
                    internals.removeAxiomFromSet(axiom.getProperty(), internals.getObjectPropertyDomainAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(EQUIVALENT_OBJECT_PROPERTIES, internals.getAxiomsByType(), axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    internals.addToIndexedSet(prop, internals.getEquivalentObjectPropertyAxiomsByProperty(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(EQUIVALENT_OBJECT_PROPERTIES, internals.getAxiomsByType(), axiom, true);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    internals.removeAxiomFromSet(prop, internals.getEquivalentObjectPropertyAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(INVERSE_OBJECT_PROPERTIES, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getFirstProperty(), internals.getInversePropertyAxiomsByProperty(), axiom);
                internals.addToIndexedSet(axiom.getSecondProperty(), internals.getInversePropertyAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(INVERSE_OBJECT_PROPERTIES, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getFirstProperty(), internals.getInversePropertyAxiomsByProperty(), axiom, false);
                internals.removeAxiomFromSet(axiom.getSecondProperty(), internals.getInversePropertyAxiomsByProperty(), axiom, false);
            }
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(axiom.getSubject(), internals.getNegativeDataPropertyAssertionAxiomsByIndividual(), axiom);
                internals.addToIndexedSet(NEGATIVE_DATA_PROPERTY_ASSERTION, internals.getAxiomsByType(), axiom);
            }
            else {
                internals.removeAxiomFromSet(NEGATIVE_DATA_PROPERTY_ASSERTION, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSubject(), internals.getNegativeDataPropertyAssertionAxiomsByIndividual(), axiom, true);
            }
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            if (addAxiom) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    internals.addToIndexedSet(ind, internals.getDifferentIndividualsAxiomsByIndividual(), axiom);
                    internals.addToIndexedSet(DIFFERENT_INDIVIDUALS, internals.getAxiomsByType(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(DIFFERENT_INDIVIDUALS, internals.getAxiomsByType(), axiom, true);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    internals.removeAxiomFromSet(ind, internals.getDifferentIndividualsAxiomsByIndividual(), axiom, true);
                }
            }
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(DISJOINT_DATA_PROPERTIES, internals.getAxiomsByType(), axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    internals.addToIndexedSet(prop, internals.getDisjointDataPropertyAxiomsByProperty(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(DISJOINT_DATA_PROPERTIES, internals.getAxiomsByType(), axiom, true);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    internals.removeAxiomFromSet(prop, internals.getDisjointDataPropertyAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(DISJOINT_OBJECT_PROPERTIES, internals.getAxiomsByType(), axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    internals.addToIndexedSet(prop, internals.getDisjointObjectPropertyAxiomsByProperty(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(DISJOINT_OBJECT_PROPERTIES, internals.getAxiomsByType(), axiom, true);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    internals.removeAxiomFromSet(prop, internals.getDisjointObjectPropertyAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(OBJECT_PROPERTY_RANGE, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getObjectPropertyRangeAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(OBJECT_PROPERTY_RANGE, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getObjectPropertyRangeAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(OBJECT_PROPERTY_ASSERTION, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getSubject(), internals.getObjectPropertyAssertionsByIndividual(), axiom);
            }
            else {
                internals.removeAxiomFromSet(OBJECT_PROPERTY_ASSERTION, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSubject(), internals.getObjectPropertyAssertionsByIndividual(), axiom, true);
            }
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(FUNCTIONAL_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getFunctionalObjectPropertyAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(FUNCTIONAL_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getFunctionalObjectPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(SUB_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getSubProperty(), internals.getObjectSubPropertyAxiomsByLHS(), axiom);
                internals.addToIndexedSet(axiom.getSuperProperty(), internals.getObjectSubPropertyAxiomsByRHS(), axiom);
            }
            else {
                internals.removeAxiomFromSet(SUB_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSubProperty(), internals.getObjectSubPropertyAxiomsByLHS(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSuperProperty(), internals.getObjectSubPropertyAxiomsByRHS(), axiom, true);
            }
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(DISJOINT_UNION, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getOWLClass(), internals.getDisjointUnionAxiomsByClass(), axiom);
                internals.addToIndexedSet(axiom.getOWLClass(), internals.getClassAxiomsByClass(), axiom);
            }
            else {
                internals.removeAxiomFromSet(DISJOINT_UNION, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getOWLClass(), internals.getDisjointUnionAxiomsByClass(), axiom, true);
                internals.removeAxiomFromSet(axiom.getOWLClass(), internals.getClassAxiomsByClass(), axiom, true);
            }
        }


        public void visit(OWLDeclarationAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(DECLARATION, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getEntity(), internals.getDeclarationsByEntity(), axiom);
            }
            else {
                internals.removeAxiomFromSet(DECLARATION, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getEntity(), internals.getDeclarationsByEntity(), axiom, true);
            }
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(ANNOTATION_ASSERTION, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getSubject(), internals.getAnnotationAssertionAxiomsBySubject(), axiom);
            }
            else {
                internals.removeAxiomFromSet(ANNOTATION_ASSERTION, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSubject(), internals.getAnnotationAssertionAxiomsBySubject(), axiom, true);
            }
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(ANNOTATION_PROPERTY_DOMAIN, internals.getAxiomsByType(), axiom);
            }
            else {
                internals.removeAxiomFromSet(ANNOTATION_PROPERTY_DOMAIN, internals.getAxiomsByType(), axiom, true);
            }
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(ANNOTATION_PROPERTY_RANGE, internals.getAxiomsByType(), axiom);
            }
            else {
                internals.removeAxiomFromSet(ANNOTATION_PROPERTY_RANGE, internals.getAxiomsByType(), axiom, true);
            }
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(SUB_ANNOTATION_PROPERTY_OF, internals.getAxiomsByType(), axiom);
            }
            else {
                internals.removeAxiomFromSet(SUB_ANNOTATION_PROPERTY_OF, internals.getAxiomsByType(), axiom, true);
            }
        }

        public void visit(OWLHasKeyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(HAS_KEY, internals.getAxiomsByType(), axiom);
                if (!axiom.getClassExpression().isAnonymous()) {
                    internals.addToIndexedSet(axiom.getClassExpression().asOWLClass(), internals.getHasKeyAxiomsByClass(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(HAS_KEY, internals.getAxiomsByType(), axiom, true);
                if (!axiom.getClassExpression().isAnonymous()) {
                    internals.removeAxiomFromSet(axiom.getClassExpression().asOWLClass(), internals.getHasKeyAxiomsByClass(), axiom, true);
                }
            }
        }

        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(SYMMETRIC_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getSymmetricPropertyAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(SYMMETRIC_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getSymmetricPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(DATA_PROPERTY_RANGE, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getDataPropertyRangeAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(DATA_PROPERTY_RANGE, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getDataPropertyRangeAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(FUNCTIONAL_DATA_PROPERTY, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getFunctionalDataPropertyAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(FUNCTIONAL_DATA_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getFunctionalDataPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(EQUIVALENT_DATA_PROPERTIES, internals.getAxiomsByType(), axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    internals.addToIndexedSet(prop, internals.getEquivalentDataPropertyAxiomsByProperty(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(EQUIVALENT_DATA_PROPERTIES, internals.getAxiomsByType(), axiom, true);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    internals.removeAxiomFromSet(prop, internals.getEquivalentDataPropertyAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(axiom.getIndividual(), internals.getClassAssertionAxiomsByIndividual(), axiom);
                internals.addToIndexedSet(CLASS_ASSERTION, internals.getAxiomsByType(), axiom);
                if (!axiom.getClassExpression().isAnonymous()) {
                    internals.addToIndexedSet((OWLClass) axiom.getClassExpression(), internals.getClassAssertionAxiomsByClass(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(CLASS_ASSERTION, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getIndividual(), internals.getClassAssertionAxiomsByIndividual(), axiom, true);
                if (!axiom.getClassExpression().isAnonymous()) {
                    internals.removeAxiomFromSet((OWLClass) axiom.getClassExpression(), internals.getClassAssertionAxiomsByClass(), axiom, true);
                }
            }
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(EQUIVALENT_CLASSES, internals.getAxiomsByType(), axiom);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        internals.addToIndexedSet((OWLClass) desc, internals.getEquivalentClassesAxiomsByClass(), axiom);
                        internals.addToIndexedSet((OWLClass) desc, internals.getClassAxiomsByClass(), axiom);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    internals.getGeneralClassAxioms().add(axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(EQUIVALENT_CLASSES, internals.getAxiomsByType(), axiom, true);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        internals.removeAxiomFromSet((OWLClass) desc, internals.getEquivalentClassesAxiomsByClass(), axiom, true);
                        internals.removeAxiomFromSet((OWLClass) desc, internals.getClassAxiomsByClass(), axiom, true);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    internals.getGeneralClassAxioms().remove(axiom);
                }
            }
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(DATA_PROPERTY_ASSERTION, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getSubject(), internals.getDataPropertyAssertionsByIndividual(), axiom);
            }
            else {
                internals.removeAxiomFromSet(DATA_PROPERTY_ASSERTION, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSubject(), internals.getDataPropertyAssertionsByIndividual(), axiom, true);
            }
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(TRANSITIVE_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getTransitivePropertyAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(TRANSITIVE_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getTransitivePropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(IRREFLEXIVE_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getIrreflexivePropertyAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(IRREFLEXIVE_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getIrreflexivePropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(SUB_DATA_PROPERTY, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getSubProperty(), internals.getDataSubPropertyAxiomsByLHS(), axiom);
                internals.addToIndexedSet(axiom.getSuperProperty(), internals.getDataSubPropertyAxiomsByRHS(), axiom);
            }
            else {
                internals.removeAxiomFromSet(SUB_DATA_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSubProperty(), internals.getDataSubPropertyAxiomsByLHS(), axiom, true);
                internals.removeAxiomFromSet(axiom.getSuperProperty(), internals.getDataSubPropertyAxiomsByRHS(), axiom, true);
            }
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom);
                internals.addToIndexedSet(axiom.getProperty(), internals.getInverseFunctionalPropertyAxiomsByProperty(), axiom);
            }
            else {
                internals.removeAxiomFromSet(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom.getProperty(), internals.getInverseFunctionalPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(SAME_INDIVIDUAL, internals.getAxiomsByType(), axiom);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    internals.addToIndexedSet(ind, internals.getSameIndividualsAxiomsByIndividual(), axiom);
                }
            }
            else {
                internals.removeAxiomFromSet(SAME_INDIVIDUAL, internals.getAxiomsByType(), axiom, true);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    internals.removeAxiomFromSet(ind, internals.getSameIndividualsAxiomsByIndividual(), axiom, true);
                }
            }
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            if (addAxiom) {
                internals.addToIndexedSet(SUB_PROPERTY_CHAIN_OF, internals.getAxiomsByType(), axiom);
                internals.addAxiomToSet(axiom, internals.getPropertyChainSubPropertyAxioms());
            }
            else {
                internals.removeAxiomFromSet(SUB_PROPERTY_CHAIN_OF, internals.getAxiomsByType(), axiom, true);
                internals.removeAxiomFromSet(axiom, internals.getPropertyChainSubPropertyAxioms());
            }
        }


        public void visit(SWRLRule rule) {
            if (addAxiom) {
                internals.addToIndexedSet(SWRL_RULE, internals.getAxiomsByType(), rule);
            }
            else {
                internals.removeAxiomFromSet(SWRL_RULE, internals.getAxiomsByType(), rule, true);
            }
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            // Just use general indexing (on the assumption that there won't be many
            // datatype definitions).  This could always be optimised at a later stage.
            if (addAxiom) {
                internals.addToIndexedSet(DATATYPE_DEFINITION, internals.getAxiomsByType(), axiom);
            }
            else {
                internals.removeAxiomFromSet(DATATYPE_DEFINITION, internals.getAxiomsByType(), axiom, true);
            }
        }
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///
    /// Utility methods for getting/setting various values in maps and sets
    ///
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLOntology)) {
            return false;
        }
        OWLOntology other = (OWLOntology) obj;
        return ontologyID.equals(other.getOntologyID());
    }


    private class OWLEntityReferenceChecker implements OWLEntityVisitor {

        private boolean ref;


        public boolean containsReference(OWLEntity entity) {
            ref = false;
            entity.accept(this);
            return ref;
        }


        public void visit(OWLClass cls) {
            ref = OWLOntologyImpl.this.containsReference(cls);
        }


        public void visit(OWLDatatype datatype) {
            ref = OWLOntologyImpl.this.containsReference(datatype);
        }


        public void visit(OWLNamedIndividual individual) {
            ref = OWLOntologyImpl.this.containsReference(individual);
        }


        public void visit(OWLDataProperty property) {
            ref = OWLOntologyImpl.this.containsReference(property);
        }


        public void visit(OWLObjectProperty property) {
            ref = OWLOntologyImpl.this.containsReference(property);
        }


        public void visit(OWLAnnotationProperty property) {
            ref = OWLOntologyImpl.this.containsReference(property);
        }
    }
}
