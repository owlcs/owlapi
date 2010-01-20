package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
import static org.semanticweb.owlapi.model.AxiomType.*;
import static org.semanticweb.owlapi.util.CollectionFactory.createMap;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import org.semanticweb.owlapi.util.OWLEntityCollector;

import java.util.*;
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

    private OWLOntologyManager manager;

    private OWLOntologyID ontologyID;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Sets of different kinds of axioms
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private Set<OWLImportsDeclaration> importsDeclarations = createSet();

    private Set<OWLAnnotation> ontologyAnnotations = createSet();

    private Map<AxiomType, Set<OWLAxiom>> axiomsByType = createMap();

    private Map<OWLAxiom, Set<OWLAxiom>> logicalAxiom2AnnotatedAxiomMap = createMap();

    private Set<OWLClassAxiom> generalClassAxioms = createSet();

    private Set<OWLSubPropertyChainOfAxiom> propertyChainSubPropertyAxioms = createSet();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Referenced entities counts
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<OWLClass, Set<OWLAxiom>> owlClassReferences = createMap();

    private Map<OWLObjectProperty, Set<OWLAxiom>> owlObjectPropertyReferences = createMap();

    private Map<OWLDataProperty, Set<OWLAxiom>> owlDataPropertyReferences = createMap();

    private Map<OWLNamedIndividual, Set<OWLAxiom>> owlIndividualReferences = createMap();

    private Map<OWLAnonymousIndividual, Set<OWLAxiom>> owlAnonymousIndividualReferences = createMap();

    private Map<OWLDatatype, Set<OWLAxiom>> owlDatatypeReferences = createMap();

    private Map<OWLAnnotationProperty, Set<OWLAxiom>> owlAnnotationPropertyReferences = createMap();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // OWLClassAxioms by OWLClass
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<OWLClass, Set<OWLClassAxiom>> classAxiomsByClass = null; // Build lazily

    private Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByLHS = null; // Build lazily

    private Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByRHS = null; // Build lazily

    private Map<OWLClass, Set<OWLEquivalentClassesAxiom>> equivalentClassesAxiomsByClass = null; // Build lazily

    private Map<OWLClass, Set<OWLDisjointClassesAxiom>> disjointClassesAxiomsByClass = null; // Build lazily

    private Map<OWLClass, Set<OWLDisjointUnionAxiom>> disjointUnionAxiomsByClass = null; // Build lazily

    private Map<OWLClass, Set<OWLHasKeyAxiom>> hasKeyAxiomsByClass = null; // Build lazily

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // OWLObjectPropertyAxioms by OWLObjectProperty
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByLHS = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByRHS = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLEquivalentObjectPropertiesAxiom>> equivalentObjectPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLDisjointObjectPropertiesAxiom>> disjointObjectPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyDomainAxiom>> objectPropertyDomainAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyRangeAxiom>> objectPropertyRangeAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLFunctionalObjectPropertyAxiom>> functionalObjectPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLInverseFunctionalObjectPropertyAxiom>> inverseFunctionalPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLSymmetricObjectPropertyAxiom>> symmetricPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLAsymmetricObjectPropertyAxiom>> asymmetricPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLReflexiveObjectPropertyAxiom>> reflexivePropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLIrreflexiveObjectPropertyAxiom>> irreflexivePropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLTransitiveObjectPropertyAxiom>> transitivePropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, Set<OWLInverseObjectPropertiesAxiom>> inversePropertyAxiomsByProperty = null; // Build lazily

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // OWLDataPropertyAxioms by OWLDataProperty
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByLHS = null; // Build lazily

    private Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByRHS = null; // Build lazily

    private Map<OWLDataPropertyExpression, Set<OWLEquivalentDataPropertiesAxiom>> equivalentDataPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLDataPropertyExpression, Set<OWLDisjointDataPropertiesAxiom>> disjointDataPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLDataPropertyExpression, Set<OWLDataPropertyDomainAxiom>> dataPropertyDomainAxiomsByProperty = null; // Build lazily

    private Map<OWLDataPropertyExpression, Set<OWLDataPropertyRangeAxiom>> dataPropertyRangeAxiomsByProperty = null; // Build lazily

    private Map<OWLDataPropertyExpression, Set<OWLFunctionalDataPropertyAxiom>> functionalDataPropertyAxiomsByProperty = null; // Build lazily

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // OWLIndividualAxioms by OWLIndividual
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<OWLIndividual, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByIndividual = null; // Build lazily

    private Map<OWLClass, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByClass = null; // Build lazily

    private Map<OWLIndividual, Set<OWLObjectPropertyAssertionAxiom>> objectPropertyAssertionsByIndividual = null; // Build lazily

    private Map<OWLIndividual, Set<OWLDataPropertyAssertionAxiom>> dataPropertyAssertionsByIndividual = null; // Build lazily

    private Map<OWLIndividual, Set<OWLNegativeObjectPropertyAssertionAxiom>> negativeObjectPropertyAssertionAxiomsByIndividual = null; // Build lazily

    private Map<OWLIndividual, Set<OWLNegativeDataPropertyAssertionAxiom>> negativeDataPropertyAssertionAxiomsByIndividual = null; // Build lazily

    private Map<OWLIndividual, Set<OWLDifferentIndividualsAxiom>> differentIndividualsAxiomsByIndividual = null; // Build lazily

    private Map<OWLIndividual, Set<OWLSameIndividualAxiom>> sameIndividualsAxiomsByIndividual = null; // Build lazily

    private Map<OWLAnnotationSubject, Set<OWLAnnotationAssertionAxiom>> annotationAssertionAxiomsBySubject = null; // Build lazily

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Annotations
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    private Map<IRI, Set<OWLAnnotationAxiom>> annotationAxiomsByAnnotationURI = createMap();


    private OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker();

    public OWLOntologyImpl(OWLOntologyManager manager, OWLOntologyID ontologyID) {
        super(manager.getOWLDataFactory());
        this.manager = manager;
        this.ontologyID = ontologyID;

        if(owlClassReferences == null) {
            throw new OWLRuntimeException("Internal Error: Class reference index is null after init");
        }

        if(owlObjectPropertyReferences == null) {
            throw new OWLRuntimeException("Internal Error: Object property reference index is null after init");
        }

        if(owlDataPropertyReferences == null) {
            throw new OWLRuntimeException("Internal Error: Data property reference index is null after init");
        }

        if(owlIndividualReferences == null) {
            throw new OWLRuntimeException("Internal Error: Individual reference index is null after init");
        }


        if(owlAnnotationPropertyReferences == null) {
            throw new OWLRuntimeException("Internal Error: Annotation property reference index is null after init");
        }
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
        for(AxiomType type : axiomsByType.keySet()) {
            Set<OWLAxiom> axiomSet = axiomsByType.get(type);
            if(axiomSet != null && !axiomSet.isEmpty()) {
                return false;
            }
        }
        return ontologyAnnotations.isEmpty();
    }


    public int getAxiomCount() {
        int count = 0;
        for (AxiomType type : AXIOM_TYPES) {
            Set<OWLAxiom> axiomSet = axiomsByType.get(type);
            if (axiomSet != null) {
                count += axiomSet.size();
            }
        }
        return count;
    }


    public Set<OWLAxiom> getAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        for (AxiomType type : AXIOM_TYPES) {
            Set<OWLAxiom> owlAxiomSet = axiomsByType.get(type);
            if (owlAxiomSet != null) {
                axioms.addAll(owlAxiomSet);
            }
        }
        return axioms;
    }


    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        return (Set<T>) getAxioms(axiomType, axiomsByType, false);
    }

    /**
     * Gets the axioms which are of the specified type, possibly from the imports closure of this ontology
     * @param axiomType             The type of axioms to be retrived.
     * @param includeImportsClosure if <code>true</code> then axioms of the specified type will also be retrieved from
     *                              the imports closure of this ontology, if <code>false</code> then axioms of the specified type will only
     *                              be retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology (and its imports closure) - it will not be updated if the ontology changes.
     */
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getAxioms(axiomType);
        }
        Set<T> result = new HashSet<T>();
        for(OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getAxioms(axiomType));
        }
        return result;
    }

    private <T extends OWLAxiom> Set<T> getAxiomsInternal(AxiomType<T> axiomType) {
        return (Set<T>) getAxioms(axiomType, axiomsByType, false);
    }


    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
        Set<OWLAxiom> axioms = axiomsByType.get(axiomType);
        if (axioms == null) {
            return 0;
        }
        return axioms.size();
    }

    /**
     * Gets the axiom count of a specific type of axiom, possibly in the imports closure of this ontology
     * @param axiomType             The type of axiom to count
     * @param includeImportsClosure Specifies that the imports closure should be included when counting axioms
     * @return The number of the specified types of axioms in this ontology
     */
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getAxiomCount(axiomType);
        }
        int result = 0;
        for(OWLOntology ont : getImportsClosure()) {
            result += ont.getAxiomCount(axiomType);
        }
        return result;
    }

    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        Set<OWLLogicalAxiom> axioms = new HashSet<OWLLogicalAxiom>();
        for (AxiomType type : AXIOM_TYPES) {
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
        for (AxiomType type : AXIOM_TYPES) {
            if (type.isLogical()) {
                Set<OWLAxiom> axiomSet = axiomsByType.get(type);
                if (axiomSet != null) {
                    count += axiomSet.size();
                }
            }
        }
        return count;
    }


    public Set<OWLAnnotation> getAnnotations() {
        return getReturnSet(ontologyAnnotations);
    }

    public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty) {
        Set<OWLSubAnnotationPropertyOfAxiom> result = new HashSet<OWLSubAnnotationPropertyOfAxiom>();
        for(OWLSubAnnotationPropertyOfAxiom ax : getAxiomsInternal(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            if(ax.getSubProperty().equals(subProperty)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(OWLAnnotationProperty property) {
        Set<OWLAnnotationPropertyDomainAxiom> result = new HashSet<OWLAnnotationPropertyDomainAxiom>();
        for(OWLAnnotationPropertyDomainAxiom ax : getAxiomsInternal(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            if (ax.getProperty().equals(property)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(OWLAnnotationProperty property) {
        Set<OWLAnnotationPropertyRangeAxiom> result = new HashSet<OWLAnnotationPropertyRangeAxiom>();
        for(OWLAnnotationPropertyRangeAxiom ax : getAxiomsInternal(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            if(ax.getProperty().equals(property)) {
                result.add(ax);
            }
        }
        return result;
    }

    public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
        OWLDeclarationAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(entity);
        if (getAxiomsInternal(DECLARATION).contains(ax)) {
            return Collections.singleton(ax);
        } else {
            return Collections.emptySet();
        }
    }


    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLAnnotationSubject subject) {
        Set<OWLAnnotationAssertionAxiom> axioms = new HashSet<OWLAnnotationAssertionAxiom>();
        axioms.addAll(getAnnotationAssertionAxiomsBySubject(subject));
        return axioms;
    }


    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(IRI subject) {
        return getAnnotationAssertionAxiomsBySubject(subject);
    }


    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return getReturnSet(generalClassAxioms);
    }


    public Set<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxioms() {
        return getReturnSet(propertyChainSubPropertyAxioms);
    }


    public boolean containsAxiom(OWLAxiom axiom) {
        Set<OWLAxiom> axioms = axiomsByType.get(axiom.getAxiomType());
        return axioms != null && axioms.contains(axiom);
    }

    /**
     * Determines if this ontology, and possibly the imports closure, contains the specified axiom.
     * @param axiom                 The axiom to test for.
     * @param includeImportsClosure if <code>true</code> the imports closure of this ontology will be searched for the
     *                              specific axiom, if <code>false</code> just this ontology will be searched.
     * @return <code>true</code> if the ontology contains the specified axioms, or <code>false</code> if the ontology
     *         doesn't contain the specified axiom.
     */
    public boolean containsAxiom(OWLAxiom axiom, boolean includeImportsClosure) {
       if(!includeImportsClosure) {
           return containsAxiom(axiom);
       }
       for(OWLOntology ont : getImportsClosure()) {
           if(ont.containsAxiom(axiom)) {
               return true;
           }
       }
       return false;
    }

    /**
     * Determines if this ontology contains the specified axiom, ignoring any annotations on this
     * axiom.
     *
     * @param axiom The axiom to test for.
     * @return <code>true</code> if this ontology contains this axiom with or without annotations.
     */
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom) {
        if(axiom.isAnnotated()) {
            return logicalAxiom2AnnotatedAxiomMap.containsKey(axiom.getAxiomWithoutAnnotations());
        }
        else {
            return containsAxiom(axiom) || logicalAxiom2AnnotatedAxiomMap.containsKey(axiom);
        }
    }

    /**
     * Determines if this ontology and possibly its imports closure contains the specified axiom,
     * ignoring any annotations on this axiom.
     *
     * @param axiom The axiom to test for.
     * @param includeImportsClosure if <code>true</code> the imports closure of this ontology will be searched for the
     * specified axiom. If <code>false</code> only this ontology will be searched for the specifed axiom.
     * @return <code>true</code> if this ontology contains this axiom with or without annotations.
     */
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom, boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return containsAxiomIgnoreAnnotations(axiom);
        }
        else {
            for(OWLOntology ont : getImportsClosure()) {
                if(ont.containsAxiomIgnoreAnnotations(axiom)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Gets the set of axioms that have the same "logical structure" as the specified axiom.
     *
     * @param axiom The axiom that specifies the logical structure of the axioms to retrieve
     * @return A set of axioms such that for any two axioms, <code>axiomA</code> and
     *         <code>axiomB</code> in the set, <code>axiomA.getAxiomWithoutAnnotations()</code> is equal to
     *         <code>axiomB.getAxiomWithoutAnnotations()</code>.  The specified axiom will be contained in the set.
     */
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom) {
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        if (containsAxiom(axiom)) {
            result.add(axiom);
        }
        Set<OWLAxiom> annotated = logicalAxiom2AnnotatedAxiomMap.get(axiom.getAxiomWithoutAnnotations());
        if(annotated != null) {
            result.addAll(annotated);
        }
        return result;
    }

    /**
     * Gets the set of axioms that have the same "logical structure" as the specified axiom, possibly searching
     * the imports closure of this ontology.
     *
     * @param axiom The axiom that specifies the logical structure of the axioms to retrieve.  If this axiom is annotated
     * then the annotations are ignored.
     * @param includeImportsClosure if <code>true</code> then axioms in the imports closure of this ontology are returned,
     * if <code>false</code> only axioms in this ontology will be returned.
     * @return A set of axioms such that for any two axioms, <code>axiomA</code> and
     *         <code>axiomB</code> in the set, <code>axiomA.getAxiomWithoutAnnotations()</code> is equal to
     *         <code>axiomB.getAxiomWithoutAnnotations()</code>.  The specified axiom will be contained in the set.
     */
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom, boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getAxiomsIgnoreAnnotations(axiom);
        }
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        for(OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getAxiomsIgnoreAnnotations(axiom));
        }
        return result;
    }

    public boolean containsClassInSignature(IRI owlClassIRI) {
        return owlClassReferences.containsKey(getOWLDataFactory().getOWLClass(owlClassIRI));
    }

    public boolean containsClassInSignature(IRI owlClassIRI, boolean includeImportsClosure) {
        for(OWLOntology ont : manager.getImportsClosure(this)) {
            if(ont.containsClassInSignature(owlClassIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsObjectPropertyInSignature(IRI propIRI) {
        return owlObjectPropertyReferences.containsKey(getOWLDataFactory().getOWLObjectProperty(propIRI));
    }

    public boolean containsObjectPropertyInSignature(IRI propIRI, boolean includeImportsClosure) {
        for(OWLOntology ont : manager.getImportsClosure(this)) {
            if(ont.containsObjectPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDataPropertyInSignature(IRI propIRI) {
        return owlDataPropertyReferences.containsKey(getOWLDataFactory().getOWLDataProperty(propIRI));
    }

    public boolean containsDataPropertyInSignature(IRI propIRI, boolean includeImportsClosure) {
        for(OWLOntology ont : manager.getImportsClosure(this)) {
            if(ont.containsDataPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAnnotationPropertyInSignature(IRI propIRI) {
        boolean b = owlAnnotationPropertyReferences.containsKey(getOWLDataFactory().getOWLAnnotationProperty(propIRI));
        if(b) {
            return true;
        }
        else {
            for(OWLAnnotation anno : ontologyAnnotations) {
                if(anno.getProperty().getIRI().equals(propIRI)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsAnnotationPropertyInSignature(IRI propIRI, boolean includeImportsClosure) {
        for(OWLOntology ont : manager.getImportsClosure(this)) {
            if(ont.containsAnnotationPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsIndividualInSignature(IRI individualIRI) {
        return owlIndividualReferences.containsKey(getOWLDataFactory().getOWLNamedIndividual(individualIRI));
    }



    public boolean containsIndividualInSignature(IRI individualIRI, boolean includeImportsClosure) {
        for(OWLOntology ont : manager.getImportsClosure(this)) {
            if(ont.containsIndividualInSignature(individualIRI)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDatatypeInSignature(IRI datatypeIRI) {
        return owlDatatypeReferences.containsKey(getOWLDataFactory().getOWLDatatype(datatypeIRI));
    }

    public boolean containsDatatypeInSignature(IRI datatypeIRI, boolean includeImportsClosure) {
        for(OWLOntology ont : manager.getImportsClosure(this)) {
            if(ont.containsDatatypeInSignature(datatypeIRI)) {
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
        Set<OWLEntity> result = new HashSet<OWLEntity>(6);
        if(containsClassInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLClass(iri));
        }
        if(containsObjectPropertyInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLObjectProperty(iri));
        }
        if(containsDataPropertyInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLDataProperty(iri));
        }
        if(containsIndividualInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLNamedIndividual(iri));
        }
        if(containsDatatypeInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLDatatype(iri));
        }
        if(containsAnnotationPropertyInSignature(iri)) {
            result.add(manager.getOWLDataFactory().getOWLAnnotationProperty(iri));
        }
        return result;
    }

    /**
     * Gets the entities in the signature of this ontology, and possibly its imports closure, that have the specified IRI
     * @param iri                   The IRI
     * @param includeImportsClosure Specifies if the imports closure signature should be taken into account
     * @return A set of entities that are in the signature of this ontology and possibly its imports closure
     *         that have the specified IRI.  The set will be empty if there are no entities in the signature of this ontology
     *         and possibly its imports closure with the specified IRI.
     */
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getEntitiesInSignature(iri);
        }
        else {
            Set<OWLEntity> result = new HashSet<OWLEntity>(6);
            for(OWLOntology ont : getImportsClosure()) {
                result.addAll(ont.getEntitiesInSignature(iri));
            }
            return result;
        }
    }

    public boolean containsReference(OWLClass owlClass) {
        return owlClassReferences.containsKey(owlClass);
    }


    public boolean containsReference(OWLObjectProperty prop) {
        return owlObjectPropertyReferences.containsKey(prop);
    }


    public boolean containsReference(OWLDataProperty prop) {
        return owlDataPropertyReferences.containsKey(prop);
    }


    public boolean containsReference(OWLNamedIndividual ind) {
        return owlIndividualReferences.containsKey(ind);
    }


    public boolean containsReference(OWLDatatype dt) {
        return owlDatatypeReferences.containsKey(dt);
    }


    public boolean containsReference(OWLAnnotationProperty property) {
        return owlAnnotationPropertyReferences.containsKey(property);
    }


    public boolean isDeclared(OWLEntity owlEntity) {
        OWLDeclarationAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(owlEntity);
        return getAxiomsInternal(DECLARATION).contains(ax);
    }

    public boolean isDeclared(OWLEntity owlEntity, boolean includeImportsClosure) {
        if(isDeclared(owlEntity)) {
            return true;
        }
        for(OWLOntology ont : manager.getImportsClosure(this)) {
            if(!ont.equals(this)) {
                if(ont.isDeclared(owlEntity)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return entityReferenceChecker.containsReference(owlEntity);
    }

    /**
     * Determines if the ontology, and possibly its imports closure, contains a reference to the specified entity.
     * @param owlEntity             The entity
     * @param includeImportsClosure Specifies whether the imports closure should be examined for the entity reference
     *                              or not.
     * @return <code>true</code> if the ontology contains a reference to the specified entity, otherwise
     *         <code>false</code> The set that is returned is a copy - it will not be updated if the ontology changes.
     *         It is therefore safe to apply changes to this ontology while iterating over this set.
     */
    public boolean containsEntityInSignature(OWLEntity owlEntity, boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return containsEntityInSignature(owlEntity);
        }
        for(OWLOntology ont : getImportsClosure()) {
            if(ont.containsEntityInSignature(owlEntity)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEntityInSignature(IRI entityIRI) {
        if(containsClassInSignature(entityIRI)) {
            return true;
        }
        if(containsObjectPropertyInSignature(entityIRI)) {
            return true;
        }
        if(containsDataPropertyInSignature(entityIRI)) {
            return true;
        }
        if(containsIndividualInSignature(entityIRI)) {
            return true;
        }
        if(containsDatatypeInSignature(entityIRI)) {
            return true;
        }
        if(containsAnnotationPropertyInSignature(entityIRI)) {
            return true;
        }
        return false;
    }

    public boolean containsEntityInSignature(IRI entityIRI, boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return containsEntityInSignature(entityIRI);
        }
        for(OWLOntology ont : getImportsClosure()) {
            if(ont.containsEntityInSignature(entityIRI)) {
                return true;
            }
        }
        return false;
    }

    public Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity) {
        if (owlEntity instanceof OWLClass) {
            return getAxioms(owlEntity.asOWLClass(), owlClassReferences, false);
        }
        if (owlEntity instanceof OWLObjectProperty) {
            return getAxioms(owlEntity.asOWLObjectProperty(), owlObjectPropertyReferences, false);
        }
        if (owlEntity instanceof OWLDataProperty) {
            return getAxioms(owlEntity.asOWLDataProperty(), owlDataPropertyReferences, false);
        }
        if (owlEntity instanceof OWLNamedIndividual) {
            return getAxioms(owlEntity.asOWLNamedIndividual(), owlIndividualReferences, false);
        }
        if (owlEntity instanceof OWLDatatype) {
            return getAxioms(owlEntity.asOWLDatatype(), owlDatatypeReferences, false);
        }
        if(owlEntity instanceof OWLAnnotationProperty) {
            return getAxioms(owlEntity.asOWLAnnotationProperty(), owlAnnotationPropertyReferences, false);
        }
        return Collections.emptySet();
    }

    /**
     * Gets the axioms where the specified entity appears in the signature of the axiom. The set that is returned,
     * contains all axioms that directly reference the specified entity.
     * @param owlEntity             The entity that should be directly referred to by an axiom that appears in the results set.
     * @param includeImportsClosure Specifies if the axioms returned should just be from this ontology, or from the
     *                              imports closure of this ontology.  If <code>true</code> the axioms returned will be from the imports closure
     *                              of this ontology, if <code>false</code> the axioms returned will just be from this ontology.
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    public Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity, boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getReferencingAxioms(owlEntity);
        }
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        for(OWLOntology ont : getImportsClosure()) {
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
        return getAxioms(individual, owlAnonymousIndividualReferences, false);
    }

    public Set<OWLClassAxiom> getAxioms(final OWLClass cls) {
        if (classAxiomsByClass == null) {
            buildClassAxiomsByClassIndex();
        }
        return getAxioms(cls, classAxiomsByClass);
    }


    public Set<OWLObjectPropertyAxiom> getAxioms(final OWLObjectPropertyExpression prop) {
        final Set<OWLObjectPropertyAxiom> result = new HashSet<OWLObjectPropertyAxiom>(50);

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
        Set<OWLAnnotationAxiom> result = new HashSet<OWLAnnotationAxiom>();
        for(OWLAnnotationAssertionAxiom ax : getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            if(ax.getProperty().equals(prop)) {
                result.add(ax);
            }
        }
        for(OWLSubAnnotationPropertyOfAxiom ax : getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            if(ax.getSubProperty().equals(prop)) {
                result.add(ax);
            }
        }
        for(OWLAnnotationPropertyRangeAxiom ax : getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            if(ax.getProperty().equals(prop)) {
                result.add(ax);
            }
        }
        for(OWLAnnotationPropertyDomainAxiom ax : getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            if(ax.getProperty().equals(prop)) {
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
        final Set<OWLIndividualAxiom> result = new HashSet<OWLIndividualAxiom>();
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
        Set<OWLNamedObject> result = new HashSet<OWLNamedObject>();
        result.addAll(owlClassReferences.keySet());
        // Consider doing this in a more efficient way (although typically, the number of
        // properties in an ontology isn't large)
        for (OWLObjectPropertyExpression prop : owlObjectPropertyReferences.keySet()) {
            if (!prop.isAnonymous()) {
                result.add((OWLObjectProperty) prop);
            }
        }
        result.addAll(owlDataPropertyReferences.keySet());
        result.addAll(owlIndividualReferences.keySet());
        return result;
    }


    public Set<OWLEntity> getSignature() {
        // We might want to cache this for performance reasons,
        // but I'm not sure right now.
        Set<OWLEntity> entities = new HashSet<OWLEntity>();
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
        if(includeImportsClosure) {
            for(OWLOntology ont : getImportsClosure()) {
                if(!ont.equals(this)) {
                    entities.addAll(ont.getSignature());
                }
            }
        }
        return entities;
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return getReturnSet(owlClassReferences.keySet());
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return getReturnSet(owlDataPropertyReferences.keySet());
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return getReturnSet(owlObjectPropertyReferences.keySet());
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return getReturnSet(owlIndividualReferences.keySet());
    }

    /**
     * A convenience method that obtains the datatypes
     * that are in the signature of this object
     *
     * @return A set containing the datatypes that are in the signature
     *         of this object.
     */
    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return getReturnSet(owlDatatypeReferences.keySet());
    }

    /**
     * Gets the classes that are referenced by axioms in this ontology, and possibly the imports closure of this
     * ontology.
     * @param includeImportsClosure Specifies whether referenced classes should be drawn from this ontology or the imports
     *                              closure.  If <code>true</code> then the set of referenced classes will be from the imports closure of this
     *                              ontology, if <code>false</code> then the set of referenced classes will just be from this ontology.
     * @return A set of named classes, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    public Set<OWLClass> getClassesInSignature(boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getClassesInSignature();
        }
        Set<OWLClass> results = new HashSet<OWLClass>();
        for(OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getClassesInSignature());
        }
        return results;
    }

    /**
     * Gets the object properties that are referenced by axioms in this ontology, and possibly the imports closure of this
     * ontology.
     * @param includeImportsClosure Specifies whether referenced object properties should be drawn from this ontology or the imports
     *                              closure.  If <code>true</code> then the set of referenced object properties will be from the imports closure of this
     *                              ontology, if <code>false</code> then the set of referenced object properties will just be from this ontology.
     * @return A set of object properties, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getObjectPropertiesInSignature();
        }
        Set<OWLObjectProperty> results = new HashSet<OWLObjectProperty>();
        for(OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getObjectPropertiesInSignature());
        }
        return results;
    }

    /**
     * Gets the data properties that are referenced by axioms in this ontology, and possibly the imports closure of this
     * ontology.
     * @param includeImportsClosure Specifies whether referenced data properties should be drawn from this ontology or the imports
     *                              closure.  If <code>true</code> then the set of referenced data properties will be from the imports closure of this
     *                              ontology, if <code>false</code> then the set of referenced data properties will just be from this ontology.
     * @return A set of data properties, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    public Set<OWLDataProperty> getDataPropertiesInSignature(boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getDataPropertiesInSignature();
        }
        Set<OWLDataProperty> results = new HashSet<OWLDataProperty>();
        for(OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getDataPropertiesInSignature());
        }
        return results;
    }

    /**
     * Gets the named individuals that are referenced by axioms in this ontology, and possibly the imports closure of this
     * ontology.
     * @param includeImportsClosure Specifies whether referenced named individuals should be drawn from this ontology or the imports
     *                              closure.  If <code>true</code> then the set of referenced named individuals will be from the imports closure of this
     *                              ontology, if <code>false</code> then the set of referenced named individuals will just be from this ontology.
     * @return A set of named individuals, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    public Set<OWLNamedIndividual> getIndividualsInSignature(boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getIndividualsInSignature();
        }
        Set<OWLNamedIndividual> results = new HashSet<OWLNamedIndividual>();
        for(OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getIndividualsInSignature());
        }
        return results;
    }

    /**
     * Gets the referenced anonymous individuals
     * @return The set of referenced anonymous individuals
     */
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals() {
        return getReturnSet(owlAnonymousIndividualReferences.keySet());
    }

    /**
     * Gets the datatypes that are referenced by this ontology and possibly its imports closure
     * @param includeImportsClosure Specifies whether referenced named individuals should be drawn from this ontology or the imports
     *                              closure of this ontology.  If <code>true</code> then the set of referenced named individuals will be from the
     *                              imports closure of this ontology, if <code>false</code> then the set of referenced named individuals will just
     *                              be from this ontology.
     * @return The set of datatypes that are referenced by axioms in this ontology and possibly its imports closure
     */
    public Set<OWLDatatype> getDatatypesInSignature(boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getDatatypesInSignature();
        }
        Set<OWLDatatype> results = new HashSet<OWLDatatype>();
        for(OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getDatatypesInSignature());
        }
        return results;
    }

    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        Set<OWLAnnotationProperty> props = new HashSet<OWLAnnotationProperty>(owlAnnotationPropertyReferences.keySet());
        for(OWLAnnotation anno : ontologyAnnotations) {
            props.add(anno.getProperty());
        }
        return getReturnSet(props);
    }

    public Set<OWLAnnotationProperty> getReferencedAnnotationProperties(boolean includeImportsClosure) {
        if(!includeImportsClosure) {
            return getAnnotationPropertiesInSignature();
        }
        Set<OWLAnnotationProperty> results = new HashSet<OWLAnnotationProperty>();
        for(OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getAnnotationPropertiesInSignature());
        }
        return results;
    }

    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return getReturnSet(importsDeclarations);
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
        Set<IRI> result = new HashSet<IRI>();
        for(OWLImportsDeclaration importsDeclaration : importsDeclarations) {
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
        Set<OWLDatatypeDefinitionAxiom> result = new HashSet<OWLDatatypeDefinitionAxiom>();
        Set<OWLDatatypeDefinitionAxiom> axioms = getAxiomsInternal(AxiomType.DATATYPE_DEFINITION);
        for(OWLDatatypeDefinitionAxiom ax : axioms) {
            if(ax.getDatatype().equals(datatype)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        if (subClassAxiomsByLHS == null) {
            Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByLHS = createMap(); // masks member declaration
            for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
                if (!axiom.getSubClass().isAnonymous()) {
                    addToIndexedSet(axiom.getSubClass().asOWLClass(), subClassAxiomsByLHS, axiom);
                }
            }
            this.subClassAxiomsByLHS=subClassAxiomsByLHS;
        }
        return getReturnSet(getAxioms(cls, subClassAxiomsByLHS));
    }


    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        if (subClassAxiomsByRHS == null) {
            Map<OWLClass, Set<OWLSubClassOfAxiom>> subClassAxiomsByRHS = createMap();  // masks member declaration
            for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
                if (!axiom.getSuperClass().isAnonymous()) {
                    addToIndexedSet(axiom.getSuperClass().asOWLClass(), subClassAxiomsByRHS, axiom);
                }
            }
            this.subClassAxiomsByRHS=subClassAxiomsByRHS;
        }
        return getReturnSet(getAxioms(cls, subClassAxiomsByRHS));
    }


    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls) {
        if (equivalentClassesAxiomsByClass == null) {
            Map<OWLClass, Set<OWLEquivalentClassesAxiom>> equivalentClassesAxiomsByClass = createMap();  // masks member declaration
            for (OWLEquivalentClassesAxiom axiom : getAxiomsInternal(EQUIVALENT_CLASSES)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), equivalentClassesAxiomsByClass, axiom);
                    }
                }
            }
            this.equivalentClassesAxiomsByClass=equivalentClassesAxiomsByClass;
        }
        return getReturnSet(getAxioms(cls, equivalentClassesAxiomsByClass));
    }


    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        if (disjointClassesAxiomsByClass == null) {
            Map<OWLClass, Set<OWLDisjointClassesAxiom>> disjointClassesAxiomsByClass = createMap(); // masks member declaration
            for (OWLDisjointClassesAxiom axiom : getAxiomsInternal(DISJOINT_CLASSES)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), disjointClassesAxiomsByClass, axiom);
                    }
                }
            }
            this.disjointClassesAxiomsByClass=disjointClassesAxiomsByClass;
        }
        return getReturnSet(getAxioms(cls, disjointClassesAxiomsByClass));
    }


    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        if (disjointUnionAxiomsByClass == null) {
            Map<OWLClass, Set<OWLDisjointUnionAxiom>> disjointUnionAxiomsByClass = createMap(); // masks member declaration
            for (OWLDisjointUnionAxiom axiom : getAxiomsInternal(DISJOINT_UNION)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), disjointUnionAxiomsByClass, axiom);
                    }
                }
            }
            this.disjointUnionAxiomsByClass=disjointUnionAxiomsByClass;
        }
        return getReturnSet(getAxioms(owlClass, disjointUnionAxiomsByClass));
    }


    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
        if(hasKeyAxiomsByClass == null) {
            Map<OWLClass, Set<OWLHasKeyAxiom>> hasKeyAxiomsByClass = createMap(); // masks member declaration
            for(OWLHasKeyAxiom axiom : getAxiomsInternal(HAS_KEY)) {
                if (!axiom.getClassExpression().isAnonymous()) {
                    addToIndexedSet(axiom.getClassExpression().asOWLClass(), hasKeyAxiomsByClass, axiom);
                }
            }
            this.hasKeyAxiomsByClass=hasKeyAxiomsByClass;
        }
        return getReturnSet(getAxioms(cls, hasKeyAxiomsByClass));
    }



    // Object properties
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression property) {
        if (objectSubPropertyAxiomsByLHS == null) {
            Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByLHS = createMap(); // masks member declaration
            for (OWLSubObjectPropertyOfAxiom axiom : getAxiomsInternal(SUB_OBJECT_PROPERTY)) {
                addToIndexedSet(axiom.getSubProperty(), objectSubPropertyAxiomsByLHS, axiom);
            }
            this.objectSubPropertyAxiomsByLHS=objectSubPropertyAxiomsByLHS;
        }
        return getReturnSet(getAxioms(property, objectSubPropertyAxiomsByLHS));
    }


    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression property) {
        if (objectSubPropertyAxiomsByRHS == null) {
            Map<OWLObjectPropertyExpression, Set<OWLSubObjectPropertyOfAxiom>> objectSubPropertyAxiomsByRHS = createMap(); // masks member declaration
            for (OWLSubObjectPropertyOfAxiom axiom : getAxiomsInternal(SUB_OBJECT_PROPERTY)) {
                addToIndexedSet(axiom.getSuperProperty(), objectSubPropertyAxiomsByRHS, axiom);
            }
            this.objectSubPropertyAxiomsByRHS=objectSubPropertyAxiomsByRHS;
        }
        return getReturnSet(getAxioms(property, objectSubPropertyAxiomsByRHS));
    }


    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        if (objectPropertyDomainAxiomsByProperty == null) {
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyDomainAxiom>> objectPropertyDomainAxiomsByProperty = createMap(); // masks member declaration
            for (OWLObjectPropertyDomainAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_DOMAIN)) {
                addToIndexedSet(axiom.getProperty(), objectPropertyDomainAxiomsByProperty, axiom);
            }
            this.objectPropertyDomainAxiomsByProperty=objectPropertyDomainAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, objectPropertyDomainAxiomsByProperty));
    }


    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        if (objectPropertyRangeAxiomsByProperty == null) {
            Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyRangeAxiom>> objectPropertyRangeAxiomsByProperty = createMap(); // masks member declaration
            for (OWLObjectPropertyRangeAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_RANGE)) {
                addToIndexedSet(axiom.getProperty(), objectPropertyRangeAxiomsByProperty, axiom);
            }
            this.objectPropertyRangeAxiomsByProperty=objectPropertyRangeAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, objectPropertyRangeAxiomsByProperty));
    }


    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (inversePropertyAxiomsByProperty == null) {
            Map<OWLObjectPropertyExpression, Set<OWLInverseObjectPropertiesAxiom>> inversePropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLInverseObjectPropertiesAxiom axiom : getAxiomsInternal(INVERSE_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, inversePropertyAxiomsByProperty, axiom);
                }
            }
            this.inversePropertyAxiomsByProperty=inversePropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, inversePropertyAxiomsByProperty));
    }


    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
            OWLObjectPropertyExpression property) {
        if (equivalentObjectPropertyAxiomsByProperty == null) {
            Map<OWLObjectPropertyExpression, Set<OWLEquivalentObjectPropertiesAxiom>> equivalentObjectPropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLEquivalentObjectPropertiesAxiom axiom : getAxiomsInternal(EQUIVALENT_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentObjectPropertyAxiomsByProperty, axiom);
                }
            }
            this.equivalentObjectPropertyAxiomsByProperty=equivalentObjectPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, equivalentObjectPropertyAxiomsByProperty));
    }


    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
            OWLObjectPropertyExpression property) {
        if (disjointObjectPropertyAxiomsByProperty == null) {
            Map<OWLObjectPropertyExpression, Set<OWLDisjointObjectPropertiesAxiom>> disjointObjectPropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLDisjointObjectPropertiesAxiom axiom : getAxiomsInternal(DISJOINT_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointObjectPropertyAxiomsByProperty, axiom);
                }
            }
            this.disjointObjectPropertyAxiomsByProperty=disjointObjectPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, disjointObjectPropertyAxiomsByProperty));
    }

    private <T extends OWLObjectPropertyCharacteristicAxiom> Map<OWLObjectPropertyExpression, Set<T>> buildObjectPropertyCharacteristicsIndex(AxiomType<T> type) {
        Map<OWLObjectPropertyExpression, Set<T>> map = createMap();
        for(T ax : getAxiomsInternal(type)) {
            addToIndexedSet(ax.getProperty(), map, ax);
        }
        return map;
    }

    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (functionalObjectPropertyAxiomsByProperty == null) {
            functionalObjectPropertyAxiomsByProperty = buildObjectPropertyCharacteristicsIndex(FUNCTIONAL_OBJECT_PROPERTY);
        }
        return getReturnSet(getAxioms(property, functionalObjectPropertyAxiomsByProperty));
    }


    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(
            OWLObjectPropertyExpression property) {
        if (inverseFunctionalPropertyAxiomsByProperty == null) {
            inverseFunctionalPropertyAxiomsByProperty = buildObjectPropertyCharacteristicsIndex(INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
        }
        return getAxioms(property, inverseFunctionalPropertyAxiomsByProperty);
    }


    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (symmetricPropertyAxiomsByProperty == null) {
            symmetricPropertyAxiomsByProperty = buildObjectPropertyCharacteristicsIndex(SYMMETRIC_OBJECT_PROPERTY);
        }
        return getAxioms(property, symmetricPropertyAxiomsByProperty);
    }


    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (asymmetricPropertyAxiomsByProperty == null) {
            asymmetricPropertyAxiomsByProperty = buildObjectPropertyCharacteristicsIndex(ASYMMETRIC_OBJECT_PROPERTY);
        }
        return getAxioms(property, asymmetricPropertyAxiomsByProperty);
    }


    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (reflexivePropertyAxiomsByProperty == null) {
            reflexivePropertyAxiomsByProperty = buildObjectPropertyCharacteristicsIndex(REFLEXIVE_OBJECT_PROPERTY);
        }
        return getAxioms(property, reflexivePropertyAxiomsByProperty);
    }


    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (irreflexivePropertyAxiomsByProperty == null) {
            irreflexivePropertyAxiomsByProperty = buildObjectPropertyCharacteristicsIndex(IRREFLEXIVE_OBJECT_PROPERTY);
        }
        return getAxioms(property, irreflexivePropertyAxiomsByProperty);
    }


    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (transitivePropertyAxiomsByProperty == null) {
            transitivePropertyAxiomsByProperty = buildObjectPropertyCharacteristicsIndex(TRANSITIVE_OBJECT_PROPERTY);
        }
        return getAxioms(property, transitivePropertyAxiomsByProperty);
    }


    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(OWLDataPropertyExpression property) {
        if (functionalDataPropertyAxiomsByProperty == null) {
            Map<OWLDataPropertyExpression, Set<OWLFunctionalDataPropertyAxiom>> functionalDataPropertyAxiomsByProperty = createMap(); // masks member declaration
            for(OWLFunctionalDataPropertyAxiom ax : getAxiomsInternal(FUNCTIONAL_DATA_PROPERTY)) {
                addToIndexedSet(ax.getProperty(), functionalDataPropertyAxiomsByProperty, ax);
            }
            this.functionalDataPropertyAxiomsByProperty=functionalDataPropertyAxiomsByProperty;
        }
        return getAxioms(property, functionalDataPropertyAxiomsByProperty);
    }


    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty) {
        if (dataSubPropertyAxiomsByLHS == null) {
            Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByLHS = createMap(); // masks member declaration
            for (OWLSubDataPropertyOfAxiom axiom : getAxiomsInternal(SUB_DATA_PROPERTY)) {
                addToIndexedSet(axiom.getSubProperty(), dataSubPropertyAxiomsByLHS, axiom);
            }
            this.dataSubPropertyAxiomsByLHS=dataSubPropertyAxiomsByLHS;
        }
        return getReturnSet(getAxioms(lhsProperty, dataSubPropertyAxiomsByLHS));
    }


    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression property) {
        if (dataSubPropertyAxiomsByRHS == null) {
            Map<OWLDataPropertyExpression, Set<OWLSubDataPropertyOfAxiom>> dataSubPropertyAxiomsByRHS = createMap(); // masks member declaration
            for (OWLSubDataPropertyOfAxiom axiom : getAxiomsInternal(SUB_DATA_PROPERTY)) {
                addToIndexedSet(axiom.getSuperProperty(), dataSubPropertyAxiomsByRHS, axiom);
            }
            this.dataSubPropertyAxiomsByRHS=dataSubPropertyAxiomsByRHS;
        }
        return getReturnSet(getAxioms(property, dataSubPropertyAxiomsByRHS));
    }


    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty property) {
        if (dataPropertyDomainAxiomsByProperty == null) {
            Map<OWLDataPropertyExpression, Set<OWLDataPropertyDomainAxiom>> dataPropertyDomainAxiomsByProperty = createMap(); // masks member declaration
            for (OWLDataPropertyDomainAxiom axiom : getAxiomsInternal(DATA_PROPERTY_DOMAIN)) {
                addToIndexedSet(axiom.getProperty(), dataPropertyDomainAxiomsByProperty, axiom);
            }
            this.dataPropertyDomainAxiomsByProperty=dataPropertyDomainAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, dataPropertyDomainAxiomsByProperty));
    }


    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(OWLDataProperty property) {
        if (dataPropertyRangeAxiomsByProperty == null) {
            Map<OWLDataPropertyExpression, Set<OWLDataPropertyRangeAxiom>> dataPropertyRangeAxiomsByProperty = createMap(); // masks member declaration
            for (OWLDataPropertyRangeAxiom axiom : getAxiomsInternal(DATA_PROPERTY_RANGE)) {
                addToIndexedSet(axiom.getProperty(), dataPropertyRangeAxiomsByProperty, axiom);
            }
            this.dataPropertyRangeAxiomsByProperty=dataPropertyRangeAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, dataPropertyRangeAxiomsByProperty));
    }


    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(OWLDataProperty property) {
        if (equivalentDataPropertyAxiomsByProperty == null) {
            Map<OWLDataPropertyExpression, Set<OWLEquivalentDataPropertiesAxiom>> equivalentDataPropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLEquivalentDataPropertiesAxiom axiom : getAxiomsInternal(EQUIVALENT_DATA_PROPERTIES)) {
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentDataPropertyAxiomsByProperty, axiom);
                }
            }
            this.equivalentDataPropertyAxiomsByProperty=equivalentDataPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, equivalentDataPropertyAxiomsByProperty));
    }


    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(OWLDataProperty property) {
        if (disjointDataPropertyAxiomsByProperty == null) {
            Map<OWLDataPropertyExpression, Set<OWLDisjointDataPropertiesAxiom>> disjointDataPropertyAxiomsByProperty = createMap(); // masks member declaration
            for (OWLDisjointDataPropertiesAxiom axiom : getAxiomsInternal(DISJOINT_DATA_PROPERTIES)) {
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointDataPropertyAxiomsByProperty, axiom);
                }
            }
            this.disjointDataPropertyAxiomsByProperty=disjointDataPropertyAxiomsByProperty;
        }
        return getReturnSet(getAxioms(property, disjointDataPropertyAxiomsByProperty));
    }

    ////


    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual) {
        if (classAssertionAxiomsByIndividual == null) {
            Map<OWLIndividual, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLClassAssertionAxiom axiom : getAxiomsInternal(CLASS_ASSERTION)) {
                addToIndexedSet(axiom.getIndividual(), classAssertionAxiomsByIndividual, axiom);
            }
            this.classAssertionAxiomsByIndividual=classAssertionAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, classAssertionAxiomsByIndividual));
    }


    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass type) {
        if (classAssertionAxiomsByClass == null) {
            Map<OWLClass, Set<OWLClassAssertionAxiom>> classAssertionAxiomsByClass = createMap(); // masks member declaration
            for (OWLClassAssertionAxiom axiom : getAxiomsInternal(CLASS_ASSERTION)) {
                if (!axiom.getClassExpression().isAnonymous()) {
                    addToIndexedSet((OWLClass) axiom.getClassExpression(), classAssertionAxiomsByClass, axiom);
                }
            }
            this.classAssertionAxiomsByClass=classAssertionAxiomsByClass;
        }
        return getReturnSet(getAxioms(type, classAssertionAxiomsByClass));
    }


    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual individual) {
        if (dataPropertyAssertionsByIndividual == null) {
            Map<OWLIndividual, Set<OWLDataPropertyAssertionAxiom>> dataPropertyAssertionsByIndividual = createMap(); // masks member declaration
            for (OWLDataPropertyAssertionAxiom axiom : getAxiomsInternal(DATA_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), dataPropertyAssertionsByIndividual, axiom);
            }
            this.dataPropertyAssertionsByIndividual=dataPropertyAssertionsByIndividual;
        }
        return getReturnSet(getAxioms(individual, dataPropertyAssertionsByIndividual));
    }


    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        if (objectPropertyAssertionsByIndividual == null) {
            Map<OWLIndividual, Set<OWLObjectPropertyAssertionAxiom>> objectPropertyAssertionsByIndividual = createMap(); // masks member declaration
            for (OWLObjectPropertyAssertionAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), objectPropertyAssertionsByIndividual, axiom);
            }
            this.objectPropertyAssertionsByIndividual=objectPropertyAssertionsByIndividual;
        }
        return getReturnSet(getAxioms(individual, objectPropertyAssertionsByIndividual));
    }


    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
            OWLIndividual individual) {
        if (negativeObjectPropertyAssertionAxiomsByIndividual == null) {
            Map<OWLIndividual, Set<OWLNegativeObjectPropertyAssertionAxiom>> negativeObjectPropertyAssertionAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLNegativeObjectPropertyAssertionAxiom axiom : getAxiomsInternal(NEGATIVE_OBJECT_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), negativeObjectPropertyAssertionAxiomsByIndividual, axiom);
            }
            this.negativeObjectPropertyAssertionAxiomsByIndividual=negativeObjectPropertyAssertionAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, negativeObjectPropertyAssertionAxiomsByIndividual));
    }


    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        if (negativeDataPropertyAssertionAxiomsByIndividual == null) {
            Map<OWLIndividual, Set<OWLNegativeDataPropertyAssertionAxiom>> negativeDataPropertyAssertionAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLNegativeDataPropertyAssertionAxiom axiom : getAxiomsInternal(NEGATIVE_DATA_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), negativeDataPropertyAssertionAxiomsByIndividual, axiom);
            }
            this.negativeDataPropertyAssertionAxiomsByIndividual=negativeDataPropertyAssertionAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, negativeDataPropertyAssertionAxiomsByIndividual));
    }


    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual) {
        if (sameIndividualsAxiomsByIndividual == null) {
            Map<OWLIndividual, Set<OWLSameIndividualAxiom>> sameIndividualsAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLSameIndividualAxiom axiom : getAxiomsInternal(SAME_INDIVIDUAL)) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, sameIndividualsAxiomsByIndividual, axiom);
                }
            }
            this.sameIndividualsAxiomsByIndividual=sameIndividualsAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, sameIndividualsAxiomsByIndividual));
    }


    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual individual) {
        if (differentIndividualsAxiomsByIndividual == null) {
            Map<OWLIndividual, Set<OWLDifferentIndividualsAxiom>> differentIndividualsAxiomsByIndividual = createMap(); // masks member declaration
            for (OWLDifferentIndividualsAxiom axiom : getAxiomsInternal(DIFFERENT_INDIVIDUALS)) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, differentIndividualsAxiomsByIndividual, axiom);
                }
            }
            this.differentIndividualsAxiomsByIndividual=differentIndividualsAxiomsByIndividual;
        }
        return getReturnSet(getAxioms(individual, differentIndividualsAxiomsByIndividual));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///
    /// Ontology Change handling mechanism
    ///
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ChangeAxiomVisitor changeVisitor = new ChangeAxiomVisitor();

    private OWLOntologyChangeFilter changeFilter = new OWLOntologyChangeFilter();


    public List<OWLOntologyChange> applyChange(OWLOntologyChange change) {
        List<OWLOntologyChange> appliedChanges = new ArrayList<OWLOntologyChange>(2);
        changeFilter.reset();
        change.accept(changeFilter);
        List<OWLOntologyChange> applied = changeFilter.getAppliedChanges();
        if (applied.size() == 1) {
            appliedChanges.add(change);
        } else {
            appliedChanges.addAll(applied);
        }
        return appliedChanges;
    }


    public List<OWLOntologyChange> applyChanges(List<OWLOntologyChange> changes) {
        List<OWLOntologyChange> appliedChanges = new ArrayList<OWLOntologyChange>();
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
                changeVisitor.setAddAxiom(false);
                axiom.accept(changeVisitor);
                appliedChanges.add(change);
                handleAxiomRemoved(axiom);
            }
        }


        public void visit(SetOntologyID change) {
            OWLOntologyID id = change.getNewOntologyID();
            if(!id.equals(ontologyID)) {
                appliedChanges.add(change);
                ontologyID = id;
            }
        }


        public void visit(AddAxiom change) {
            OWLAxiom axiom = change.getAxiom();
            if (!containsAxiom(axiom)) {
                changeVisitor.setAddAxiom(true);
                axiom.accept(changeVisitor);
                appliedChanges.add(change);
                handleAxiomAdded(axiom);
            }
        }


        public void visit(AddImport change) {
            if(!importsDeclarations.contains(change.getImportDeclaration())) {
                appliedChanges.add(change);
                importsDeclarations.add(change.getImportDeclaration());
            }
        }


        public void visit(RemoveImport change) {
            if(importsDeclarations.contains(change.getImportDeclaration())) {
                appliedChanges.add(change);
                importsDeclarations.remove(change.getImportDeclaration());
            }
        }


        public void visit(AddOntologyAnnotation change) {
            if(ontologyAnnotations.add(change.getAnnotation())) {
                appliedChanges.add(change);
            }
        }


        public void visit(RemoveOntologyAnnotation change) {
            if(ontologyAnnotations.remove(change.getAnnotation())) {
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

    private OWLEntityCollector entityCollector = new OWLEntityCollector();

    private OWLNamedObjectReferenceAdder referenceAdder = new OWLNamedObjectReferenceAdder();


    private void handleAxiomAdded(OWLAxiom axiom) {
        entityCollector.reset();
        axiom.accept(entityCollector);
        for (OWLEntity object : entityCollector.getObjects()) {
            referenceAdder.setAxiom(axiom);
            object.accept(referenceAdder);
        }
        for(OWLAnonymousIndividual ind : entityCollector.getAnonymousIndividuals()) {
            addToIndexedSet(ind, owlAnonymousIndividualReferences, axiom);
        }
        if(axiom.isAnnotated()) {
            addToIndexedSet(axiom.getAxiomWithoutAnnotations(), logicalAxiom2AnnotatedAxiomMap, axiom);
        }
    }


    private OWLNamedObjectReferenceRemover referenceRemover = new OWLNamedObjectReferenceRemover();


    private void handleAxiomRemoved(OWLAxiom axiom) {
        entityCollector.reset();
        axiom.accept(entityCollector);
        for (OWLEntity object : entityCollector.getObjects()) {
            referenceRemover.setAxiom(axiom);
            object.accept(referenceRemover);
        }
        for(OWLAnonymousIndividual ind : entityCollector.getAnonymousIndividuals()) {
            removeAxiomFromSet(ind, owlAnonymousIndividualReferences, axiom, true);
        }
        if(axiom.isAnnotated()) {
            removeAxiomFromSet(axiom.getAxiomWithoutAnnotations(), logicalAxiom2AnnotatedAxiomMap, axiom, true);
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
            addToIndexedSet(owlClass, owlClassReferences, axiom);
        }


        public void visit(OWLObjectProperty property) {
            addToIndexedSet(property, owlObjectPropertyReferences, axiom);
        }


        public void visit(OWLDataProperty property) {
            addToIndexedSet(property, owlDataPropertyReferences, axiom);
        }


        public void visit(OWLNamedIndividual owlIndividual) {
            addToIndexedSet(owlIndividual, owlIndividualReferences, axiom);
        }

        public void visit(OWLAnnotationProperty property) {
            addToIndexedSet(property, owlAnnotationPropertyReferences, axiom);
        }

        public void visit(OWLDatatype datatype) {
            addToIndexedSet(datatype, owlDatatypeReferences, axiom);
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
            removeAxiomFromSet(owlClass, owlClassReferences, axiom, true);
        }


        public void visit(OWLObjectProperty property) {
            removeAxiomFromSet(property, owlObjectPropertyReferences, axiom, true);
        }


        public void visit(OWLDataProperty property) {
            removeAxiomFromSet(property, owlDataPropertyReferences, axiom, true);
        }


        public void visit(OWLNamedIndividual owlIndividual) {
            removeAxiomFromSet(owlIndividual, owlIndividualReferences, axiom, true);
        }

        public void visit(OWLAnnotationProperty property) {
            removeAxiomFromSet(property, owlAnnotationPropertyReferences, axiom, true);
        }

        public void visit(OWLDatatype datatype) {
            removeAxiomFromSet(datatype, owlDatatypeReferences, axiom, true);
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
                addToIndexedSet(SUBCLASS_OF, axiomsByType, axiom);
                if (!axiom.getSubClass().isAnonymous()) {
                    OWLClass subClass = (OWLClass) axiom.getSubClass();
                    addToIndexedSet(subClass, subClassAxiomsByLHS, axiom);
                    addToIndexedSet(subClass, classAxiomsByClass, axiom);
                } else {
                    generalClassAxioms.add(axiom);
                }
                if (!axiom.getSuperClass().isAnonymous()) {
                    addToIndexedSet((OWLClass) axiom.getSuperClass(), subClassAxiomsByRHS, axiom);
                }
            } else {
                removeAxiomFromSet(SUBCLASS_OF, axiomsByType, axiom, true);
                if (!axiom.getSubClass().isAnonymous()) {
                    OWLClass subClass = (OWLClass) axiom.getSubClass();
                    removeAxiomFromSet(subClass, subClassAxiomsByLHS, axiom, true);
                    removeAxiomFromSet(subClass, classAxiomsByClass, axiom, true);
                } else {
                    removeAxiomFromSet(axiom, generalClassAxioms);
                }
                if (!axiom.getSuperClass().isAnonymous()) {
                    removeAxiomFromSet(axiom.getSuperClass().asOWLClass(), subClassAxiomsByRHS, axiom, true);
                }
            }
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(axiom.getSubject(), negativeObjectPropertyAssertionAxiomsByIndividual, axiom);
                addToIndexedSet(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getSubject(), negativeObjectPropertyAssertionAxiomsByIndividual, axiom, true);
            }
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(axiom.getProperty(), asymmetricPropertyAxiomsByProperty, axiom);
                addToIndexedSet(ASYMMETRIC_OBJECT_PROPERTY, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(ASYMMETRIC_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), asymmetricPropertyAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(axiom.getProperty(), reflexivePropertyAxiomsByProperty, axiom);
                addToIndexedSet(REFLEXIVE_OBJECT_PROPERTY, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(REFLEXIVE_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), reflexivePropertyAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DISJOINT_CLASSES, axiomsByType, axiom);
                boolean allAnon = true;
                // Index against each named class in the axiom
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        OWLClass cls = (OWLClass) desc;
                        addToIndexedSet(cls, disjointClassesAxiomsByClass, axiom);
                        addToIndexedSet(cls, classAxiomsByClass, axiom);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    generalClassAxioms.add(axiom);
                }
            } else {
                removeAxiomFromSet(DISJOINT_CLASSES, axiomsByType, axiom, true);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        OWLClass cls = (OWLClass) desc;
                        removeAxiomFromSet(cls, disjointClassesAxiomsByClass, axiom, true);
                        removeAxiomFromSet(cls, classAxiomsByClass, axiom, true);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    generalClassAxioms.remove(axiom);
                }
            }
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DATA_PROPERTY_DOMAIN, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), dataPropertyDomainAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(DATA_PROPERTY_DOMAIN, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), dataPropertyDomainAxiomsByProperty, axiom, true);
            }
        }

        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(OBJECT_PROPERTY_DOMAIN, axiomsByType, axiom);
                if (axiom.getProperty() instanceof OWLObjectProperty) {
                    addToIndexedSet(axiom.getProperty(),
                                    objectPropertyDomainAxiomsByProperty,
                                    axiom);
                }
            } else {
                removeAxiomFromSet(OBJECT_PROPERTY_DOMAIN, axiomsByType, axiom, true);
                if (axiom.getProperty() instanceof OWLObjectProperty) {
                    removeAxiomFromSet(axiom.getProperty(),
                                       objectPropertyDomainAxiomsByProperty,
                                       axiom,
                                       true);
                }
            }
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(EQUIVALENT_OBJECT_PROPERTIES, axiomsByType, axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentObjectPropertyAxiomsByProperty, axiom);
                }
            } else {
                removeAxiomFromSet(EQUIVALENT_OBJECT_PROPERTIES, axiomsByType, axiom, true);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    removeAxiomFromSet(prop, equivalentObjectPropertyAxiomsByProperty, axiom, true);
                }
            }
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(INVERSE_OBJECT_PROPERTIES, axiomsByType, axiom);
                addToIndexedSet(axiom.getFirstProperty(), inversePropertyAxiomsByProperty, axiom);
                addToIndexedSet(axiom.getSecondProperty(), inversePropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(INVERSE_OBJECT_PROPERTIES, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getFirstProperty(), inversePropertyAxiomsByProperty, axiom, false);
                removeAxiomFromSet(axiom.getSecondProperty(), inversePropertyAxiomsByProperty, axiom, false);
            }
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(axiom.getSubject(), negativeDataPropertyAssertionAxiomsByIndividual, axiom);
                addToIndexedSet(NEGATIVE_DATA_PROPERTY_ASSERTION, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(NEGATIVE_DATA_PROPERTY_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getSubject(), negativeDataPropertyAssertionAxiomsByIndividual, axiom, true);
            }
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            if (addAxiom) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, differentIndividualsAxiomsByIndividual, axiom);
                    addToIndexedSet(DIFFERENT_INDIVIDUALS, axiomsByType, axiom);
                }
            } else {
                removeAxiomFromSet(DIFFERENT_INDIVIDUALS, axiomsByType, axiom, true);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    removeAxiomFromSet(ind, differentIndividualsAxiomsByIndividual, axiom, true);
                }
            }
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DISJOINT_DATA_PROPERTIES, axiomsByType, axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointDataPropertyAxiomsByProperty, axiom);
                }
            } else {
                removeAxiomFromSet(DISJOINT_DATA_PROPERTIES, axiomsByType, axiom, true);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    removeAxiomFromSet(prop, disjointDataPropertyAxiomsByProperty, axiom, true);
                }
            }
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DISJOINT_OBJECT_PROPERTIES, axiomsByType, axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointObjectPropertyAxiomsByProperty, axiom);
                }
            } else {
                removeAxiomFromSet(DISJOINT_OBJECT_PROPERTIES, axiomsByType, axiom, true);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    removeAxiomFromSet(prop, disjointObjectPropertyAxiomsByProperty, axiom, true);
                }
            }
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(OBJECT_PROPERTY_RANGE, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), objectPropertyRangeAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(OBJECT_PROPERTY_RANGE, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), objectPropertyRangeAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(OBJECT_PROPERTY_ASSERTION, axiomsByType, axiom);
                addToIndexedSet(axiom.getSubject(), objectPropertyAssertionsByIndividual, axiom);
            } else {
                removeAxiomFromSet(OBJECT_PROPERTY_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getSubject(), objectPropertyAssertionsByIndividual, axiom, true);
            }
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(FUNCTIONAL_OBJECT_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), functionalObjectPropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(FUNCTIONAL_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), functionalObjectPropertyAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SUB_OBJECT_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getSubProperty(), objectSubPropertyAxiomsByLHS, axiom);
                addToIndexedSet(axiom.getSuperProperty(), objectSubPropertyAxiomsByRHS, axiom);
            } else {
                removeAxiomFromSet(SUB_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getSubProperty(), objectSubPropertyAxiomsByLHS, axiom, true);
                removeAxiomFromSet(axiom.getSuperProperty(), objectSubPropertyAxiomsByRHS, axiom, true);
            }
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DISJOINT_UNION, axiomsByType, axiom);
                addToIndexedSet(axiom.getOWLClass(), disjointUnionAxiomsByClass, axiom);
                addToIndexedSet(axiom.getOWLClass(), classAxiomsByClass, axiom);
            } else {
                removeAxiomFromSet(DISJOINT_UNION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getOWLClass(), disjointUnionAxiomsByClass, axiom, true);
                removeAxiomFromSet(axiom.getOWLClass(), classAxiomsByClass, axiom, true);
            }
        }


        public void visit(OWLDeclarationAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DECLARATION, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(DECLARATION, axiomsByType, axiom, true);
            }
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(ANNOTATION_ASSERTION, axiomsByType, axiom);
                addToIndexedSet(axiom.getSubject(), annotationAssertionAxiomsBySubject, axiom);
            } else {
                removeAxiomFromSet(ANNOTATION_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getSubject(), annotationAssertionAxiomsBySubject, axiom, true);
            }
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(ANNOTATION_PROPERTY_DOMAIN, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(ANNOTATION_PROPERTY_DOMAIN, axiomsByType, axiom, true);
            }
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(ANNOTATION_PROPERTY_RANGE, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(ANNOTATION_PROPERTY_RANGE, axiomsByType, axiom, true);
            }
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SUB_ANNOTATION_PROPERTY_OF, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(SUB_ANNOTATION_PROPERTY_OF, axiomsByType, axiom, true);
            }
        }

        public void visit(OWLHasKeyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(HAS_KEY, axiomsByType, axiom);
                if (!axiom.getClassExpression().isAnonymous()) {
                    addToIndexedSet(axiom.getClassExpression().asOWLClass(), hasKeyAxiomsByClass, axiom);
                }
            } else {
                removeAxiomFromSet(HAS_KEY, axiomsByType, axiom, true);
                if (!axiom.getClassExpression().isAnonymous()) {
                    removeAxiomFromSet(axiom.getClassExpression().asOWLClass(), hasKeyAxiomsByClass, axiom, true);
                }
            }
        }

        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SYMMETRIC_OBJECT_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), symmetricPropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(SYMMETRIC_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), symmetricPropertyAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DATA_PROPERTY_RANGE, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), dataPropertyRangeAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(DATA_PROPERTY_RANGE, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), dataPropertyRangeAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(FUNCTIONAL_DATA_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), functionalDataPropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(FUNCTIONAL_DATA_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), functionalDataPropertyAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(EQUIVALENT_DATA_PROPERTIES, axiomsByType, axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentDataPropertyAxiomsByProperty, axiom);
                }
            } else {
                removeAxiomFromSet(EQUIVALENT_DATA_PROPERTIES, axiomsByType, axiom, true);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    removeAxiomFromSet(prop, equivalentDataPropertyAxiomsByProperty, axiom, true);
                }
            }
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(axiom.getIndividual(), classAssertionAxiomsByIndividual, axiom);
                addToIndexedSet(CLASS_ASSERTION, axiomsByType, axiom);
                if (!axiom.getClassExpression().isAnonymous()) {
                    addToIndexedSet((OWLClass) axiom.getClassExpression(), classAssertionAxiomsByClass, axiom);
                }
            } else {
                removeAxiomFromSet(CLASS_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getIndividual(), classAssertionAxiomsByIndividual, axiom, true);
                if (!axiom.getClassExpression().isAnonymous()) {
                    removeAxiomFromSet((OWLClass) axiom.getClassExpression(), classAssertionAxiomsByClass, axiom, true);
                }
            }
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(EQUIVALENT_CLASSES, axiomsByType, axiom);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet((OWLClass) desc, equivalentClassesAxiomsByClass, axiom);
                        addToIndexedSet((OWLClass) desc, classAxiomsByClass, axiom);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    generalClassAxioms.add(axiom);
                }
            } else {
                removeAxiomFromSet(EQUIVALENT_CLASSES, axiomsByType, axiom, true);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        removeAxiomFromSet((OWLClass) desc, equivalentClassesAxiomsByClass, axiom, true);
                        removeAxiomFromSet((OWLClass) desc, classAxiomsByClass, axiom, true);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    generalClassAxioms.remove(axiom);
                }
            }
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DATA_PROPERTY_ASSERTION, axiomsByType, axiom);
                addToIndexedSet(axiom.getSubject(), dataPropertyAssertionsByIndividual, axiom);
            } else {
                removeAxiomFromSet(DATA_PROPERTY_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getSubject(), dataPropertyAssertionsByIndividual, axiom, true);
            }
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(TRANSITIVE_OBJECT_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), transitivePropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(TRANSITIVE_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), transitivePropertyAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(IRREFLEXIVE_OBJECT_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), irreflexivePropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(IRREFLEXIVE_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), irreflexivePropertyAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SUB_DATA_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getSubProperty(), dataSubPropertyAxiomsByLHS, axiom);
                addToIndexedSet(axiom.getSuperProperty(), dataSubPropertyAxiomsByRHS, axiom);
            } else {
                removeAxiomFromSet(SUB_DATA_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getSubProperty(), dataSubPropertyAxiomsByLHS, axiom, true);
                removeAxiomFromSet(axiom.getSuperProperty(), dataSubPropertyAxiomsByRHS, axiom, true);
            }
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), inverseFunctionalPropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getProperty(), inverseFunctionalPropertyAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SAME_INDIVIDUAL, axiomsByType, axiom);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, sameIndividualsAxiomsByIndividual, axiom);
                }
            } else {
                removeAxiomFromSet(SAME_INDIVIDUAL, axiomsByType, axiom, true);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    removeAxiomFromSet(ind, sameIndividualsAxiomsByIndividual, axiom, true);
                }
            }
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SUB_PROPERTY_CHAIN_OF, axiomsByType, axiom);
                addAxiomToSet(axiom, propertyChainSubPropertyAxioms);
            } else {
                removeAxiomFromSet(SUB_PROPERTY_CHAIN_OF, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, propertyChainSubPropertyAxioms);
            }
        }


        public void visit(SWRLRule rule) {
            if (addAxiom) {
                addToIndexedSet(SWRL_RULE, axiomsByType, rule);
            } else {
                removeAxiomFromSet(SWRL_RULE, axiomsByType, rule, true);
            }
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            // Just use general indexing (on the assumption that there won't be many
            // datatype definitions).  This could always be optimised at a later stage.
            if(addAxiom) {
                addToIndexedSet(DATATYPE_DEFINITION, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(DATATYPE_DEFINITION, axiomsByType, axiom, true);
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


    private static <E> Set<E> getReturnSet(Set<E> set) {
        return new HashSet<E>(set);
    }

    private static <K extends OWLObject, V extends OWLAxiom> Set<V> getAxioms(K key, Map<K, Set<V>> map) {
        Set<V> axioms = map.get(key);
        if (axioms != null) {
            return Collections.unmodifiableSet(axioms);
        } else {
            return Collections.emptySet();
        }
    }


    private static <K, V extends OWLAxiom> Set<V> getAxioms(K key, Map<K, Set<V>> map, boolean create) {
        Set<V> axioms = map.get(key);
        if (axioms == null) {
            axioms = new FakeSet<V>();
            if (create) {
                map.put(key, axioms);
            }
        }
        return axioms;
    }


    /**
     * Adds an axiom to a set contained in a map, which maps some key (e.g. an entity such as and individual, class
     * etc.) to the set of axioms.
     *
     * @param key   The key that indexes the set of axioms
     * @param map   The map, which maps the key to a set of axioms, to which the axiom will be added.
     * @param axiom The axiom to be added
     */
    private static <K, V extends OWLAxiom> void addToIndexedSet(K key, Map<K, Set<V>> map, V axiom) {
        if (map == null) {
            return;
        }
        Set<V> axioms = map.get(key);
        if (axioms == null) {
            axioms = new FakeSet<V>();
            map.put(key, axioms);
        }
        axioms.add(axiom);
    }


    /**
     * Removes an axiom from a map that maps some key to the axiom
     * @param key The key that indexes the set of axioms
     * @param map The map which maps keys to sets of axioms
     * @param axiom The axiom to remove from the set of axioms
     */
    private static <K extends OWLObject, V extends OWLAxiom> void addAxiomToMap(K key, Map<K, V> map, V axiom) {
        if (map == null) {
            return;
        }
        map.put(key, axiom);
    }


    /**
     * Removes an axiom from a set of axioms, which is the value for a specified key in a specified map.
     *
     * @param key   The key that indexes the set of axioms.
     * @param map   The map, which maps keys to sets of axioms.
     * @param axiom The axiom to remove from the set of axioms.
     * @param removeSetIfEmpty Specifies whether or not the indexed set should be removed from the map if it
     * is empty after removing the specified axiom
     */
    private static <K, V extends OWLAxiom> void removeAxiomFromSet(K key, Map<K, Set<V>> map, V axiom,
                                                                   boolean removeSetIfEmpty) {
        if (map == null) {
            return;
        }
        Set<V> axioms = map.get(key);
        if (axioms != null) {
            axioms.remove(axiom);
            if (removeSetIfEmpty) {
                if (axioms.isEmpty()) {
                    map.remove(key);
                }
            }
        }
    }


    /**
     * A convenience method that adds an axiom to a set, but checks that the set isn't null before the axiom is added.
     * This is needed because many of the indexing sets are built lazily.
     *
     * @param axiom  The axiom to be added.
     * @param axioms The set of axioms that the axiom should be added to.  May be <code>null</code>.
     */
    private static <K extends OWLAxiom> void addAxiomToSet(K axiom, Set<K> axioms) {
        if (axioms != null && axiom != null) {
            axioms.add(axiom);
        }
    }


    private static <K extends OWLAxiom> void removeAxiomFromSet(K axiom, Set<K> axioms) {
        if (axioms != null) {
            axioms.remove(axiom);
        }
    }


    private static <K extends OWLObject, V extends OWLAxiom> void removeAxiomFromMap(K key, Map<K, V> map) {
        if (map != null) {
            map.remove(key);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Methods to build indexes
    ////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void buildClassAxiomsByClassIndex() {
        Map<OWLClass, Set<OWLClassAxiom>> classAxiomsByClass = createMap(); // masks member declaration
        for (OWLEquivalentClassesAxiom axiom : getAxiomsInternal(EQUIVALENT_CLASSES)) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    addToIndexedSet((OWLClass) desc, classAxiomsByClass, axiom);
                }
            }
        }

        for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS_OF)) {
            if (!axiom.getSubClass().isAnonymous()) {
                addToIndexedSet((OWLClass) axiom.getSubClass(), classAxiomsByClass, axiom);
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
        this.classAxiomsByClass=classAxiomsByClass;
    }


    private Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxiomsBySubject(OWLAnnotationSubject subject){
        if (annotationAssertionAxiomsBySubject == null) {
            Map<OWLAnnotationSubject, Set<OWLAnnotationAssertionAxiom>> annotationAssertionAxiomsBySubject = createMap(); // masks member declaration
            for (OWLAnnotationAssertionAxiom axiom : getAxiomsInternal(ANNOTATION_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), annotationAssertionAxiomsBySubject, axiom);
            }
            this.annotationAssertionAxiomsBySubject=annotationAssertionAxiomsBySubject;
        }
        return getReturnSet(getAxioms(subject, annotationAssertionAxiomsBySubject, false));
    }


    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLOntology)) {
            return false;
        }
        OWLOntology other = (OWLOntology) obj;
        return getOntologyID().equals(other.getOntologyID());
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
