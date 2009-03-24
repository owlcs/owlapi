package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;
import static org.semanticweb.owl.model.AxiomType.*;
import static org.semanticweb.owl.util.CollectionFactory.createMap;
import static org.semanticweb.owl.util.CollectionFactory.createSet;
import org.semanticweb.owl.util.OWLEntityCollector;

import java.net.URI;
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


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 26-Oct-2006<br><br>
 */
public class OWLOntologyImpl extends OWLObjectImpl implements OWLMutableOntology {

    private OWLOntologyID ontologyID;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Sets of different kinds of axioms
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    private Set<OWLAxiom> allAxioms = createSet();

    private Set<OWLImportsDeclaration> importsDeclarations = createSet();

    private Map<AxiomType, Set<OWLAxiom>> axiomsByType = createMap();

    private Set<SWRLRule> ruleAxioms = createSet();

    private Set<OWLClassAxiom> owlClassAxioms = null; // Build lazily

    private Set<OWLPropertyAxiom> owlObjectPropertyAxioms = null; // Build lazily

    private Set<OWLPropertyAxiom> owlDataPropertyAxioms = null; // Build lazily

    private Set<OWLIndividualAxiom> owlIndividualAxioms = null; // Build lazily

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

    private Map<OWLDatatype, Set<OWLAxiom>> owlDatatypeReferences = createMap();

    private Map<OWLAnnotationProperty, Set<OWLAxiom>> annotationPropertyReferences = createMap();

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

    private Map<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom> functionalObjectPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom> inverseFunctionalPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom> symmetricPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom> antiSymmetricPropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom> reflexivePropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom> irreflexivePropertyAxiomsByProperty = null; // Build lazily

    private Map<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom> transitivePropertyAxiomsByProperty = null; // Build lazily

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

    private Map<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom> functionalDataPropertyAxiomsByProperty = null; // Build lazily

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

//    private Map<OWLEntity, Set<OWLDeclaration>> owlDeclarationAxiomMap = null; // Build lazily

    private Map<OWLAnnotationSubject, Set<OWLAnnotationAssertionAxiom>> annotationAssertionAxiomsBySubject = null; // Build lazily

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Annotations
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<URI, Set<OWLAnnotationAxiom>> annotationAxiomsByAnnotationURI = createMap();


    private OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker();

    public OWLOntologyImpl(OWLDataFactory dataFactory, OWLOntologyID ontologyID) {
        super(dataFactory);
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


        if(annotationPropertyReferences == null) {
            throw new OWLRuntimeException("Internal Error: Annotation property reference index is null after init");
        }
    }

    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    public IRI getIRI() {
        return getOntologyID().getOntologyIRI();
    }

    public IRI getVersionIRI() {
        return getOntologyID().getVersionIRI();
    }

    public String toString() {
        return super.toString();
    }

    /**
     * Gets the URI of the ontology.
     */
    public URI getURI() {
        if (getIRI() != null) {
            return getIRI().toURI();
        } else {
            return null;
        }
    }


    protected int compareObjectOfSameType(OWLObject object) {
        if (object == this) {
            return 0;
        }
        OWLOntology other = (OWLOntology) object;
        return getOntologyID().compareTo(other.getOntologyID());
    }


    public boolean isEmpty() {
        // TODO:
        return false;
//        return allAxioms.isEmpty();
    }


    public int getAxiomCount() {
//         TODO:
        int count = 0;
        for (AxiomType type : AXIOM_TYPES) {
            Set<OWLAxiom> axiomSet = axiomsByType.get(type);
            if (axiomSet != null) {
                count += axiomSet.size();
            }
        }
        return count;
//        return logicalAxioms.size();
//        return allAxioms.size();
    }


    public Set<OWLAxiom> getAxioms() {
        // TODO:
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        for (AxiomType type : AXIOM_TYPES) {
            Set<OWLAxiom> owlAxiomSet = axiomsByType.get(type);
            if (owlAxiomSet != null) {
                axioms.addAll(owlAxiomSet);
            }
        }
        return axioms;
//        return new HashSet<OWLAxiom>(logicalAxioms);
//        return getReturnSet(allAxioms);
    }


    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        return (Set<T>) getAxioms(axiomType, axiomsByType, false);
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


    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        // TODO:
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
//        return getReturnSet(logicalAxioms);
    }


    public int getLogicalAxiomCount() {
        // TODO:
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
//        return logicalAxioms.size();
    }


    public Set<SWRLRule> getRules() {
        return getReturnSet(ruleAxioms);
    }


    public Set<OWLClassAxiom> getClassAxioms() {
        if (owlClassAxioms == null) {
            buildClassAxiomsIndex();
        }
        return getReturnSet(owlClassAxioms);
    }


    public Set<OWLPropertyAxiom> getObjectPropertyAxioms() {
        if (owlObjectPropertyAxioms == null) {
            buildObjectPropertyAxiomsIndex();
        }
        return getReturnSet(owlObjectPropertyAxioms);
    }


    public Set<OWLPropertyAxiom> getDataPropertyAxioms() {
        if (owlDataPropertyAxioms == null) {
            buildDataPropertyAxiomsIndex();
        }
        return getReturnSet(owlDataPropertyAxioms);
    }


    public Set<OWLIndividualAxiom> getIndividualAxioms() {
        if (owlIndividualAxioms == null) {
            buildIndividualAxiomsIndex();
        }
        return getReturnSet(owlIndividualAxioms);
    }


    public Set<OWLAnnotation> getAnnotations() {
        return Collections.emptySet();
    }

    public Set<OWLAnnotationAxiom> getAnnotationAxioms() {
        int size = getAxiomCount(ANNOTATION_ASSERTION) + getAxiomCount(ANNOTATION_PROPERTY_DOMAIN) +
                getAxiomCount(ANNOTATION_PROPERTY_RANGE) + getAxiomCount(SUB_ANNOTATION_PROPERTY_OF);
        Set<OWLAnnotationAxiom> result = new HashSet<OWLAnnotationAxiom>(size);
        result.addAll(getAxioms(ANNOTATION_ASSERTION));
        result.addAll(getAxioms(ANNOTATION_PROPERTY_DOMAIN));
        result.addAll(getAxioms(ANNOTATION_PROPERTY_RANGE));
        result.addAll(getAxioms(SUB_ANNOTATION_PROPERTY_OF));
        return result;
    }


    public Set<OWLDeclarationAxiom> getDeclarationAxioms() {
        return getAxioms(DECLARATION);
    }


    public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
        OWLDeclarationAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(entity);
        if (getAxiomsInternal(DECLARATION).contains(ax)) {
            return Collections.singleton(ax);
        } else {
            return Collections.emptySet();
        }
    }


    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLEntity entity) {
        return getAnnotationAssertionAxioms(entity.getIRI());
    }

    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(IRI subject) {
        if (annotationAssertionAxiomsBySubject == null) {
            annotationAssertionAxiomsBySubject = createMap();
            for (OWLAnnotationAssertionAxiom axiom : getAxiomsInternal(ANNOTATION_ASSERTION)) {
                if (axiom.getSubject() instanceof IRI) {
                    addToIndexedSet(axiom.getSubject(), annotationAssertionAxiomsBySubject, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(subject, annotationAssertionAxiomsBySubject, false));
    }

    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return getReturnSet(generalClassAxioms);
    }


    public Set<OWLSubPropertyChainOfAxiom> getPropertyChainSubPropertyAxioms() {
        return getReturnSet(propertyChainSubPropertyAxioms);
    }


    public boolean containsAxiom(OWLAxiom axiom) {
        // TODO:
        Set<OWLAxiom> axioms = axiomsByType.get(axiom.getAxiomType());
        if (axioms != null) {
            return axioms.contains(axiom);
        }
        return false;
//        return logicalAxioms.contains(axiom);
//        return allAxioms.contains(axiom);
    }


//    private Map<OWLClass, Set<OWLAxiom>> getOWLClassReferences() {
//        return getReturnMap(owlClassReferences);
//    }
//
//
//    private Map<OWLObjectProperty, Set<OWLAxiom>> getOWLObjectPropertyReferences() {
//        return getReturnMap(owlObjectPropertyReferences);
//    }
//
//
//    private Map<OWLDataProperty, Set<OWLAxiom>> getOWLDataPropertyReferences() {
//        return getReturnMap(owlDataPropertyReferences);
//    }
//
//
//    private Map<OWLNamedIndividual, Set<OWLAxiom>> getOWLIndividualReferences() {
//        return getReturnMap(owlIndividualReferences);
//    }
//
//
//    private Map<OWLDatatype, Set<OWLAxiom>> getOWLDatatypeReferences() {
//        return getReturnMap(owlDatatypeReferences);
//    }
//
//
//    private Map<OWLAnnotationProperty, Set<OWLAxiom>> getOWLAnnotationPropertyReferences() {
//        return getReturnMap(annotationPropertyReferences);
//    }


    public Set<URI> getAnnotationURIs() {
        return getReturnSet(annotationAxiomsByAnnotationURI.keySet());
    }


    public boolean containsClassReference(URI owlClassURI) {
        return owlClassReferences.containsKey(getOWLDataFactory().getOWLClass(owlClassURI));
    }


    public boolean containsObjectPropertyReference(URI propURI) {
        return owlObjectPropertyReferences.containsKey(getOWLDataFactory().getOWLObjectProperty(propURI));
    }


    public boolean containsDataPropertyReference(URI propURI) {
        return owlDataPropertyReferences.containsKey(getOWLDataFactory().getOWLDataProperty(propURI));
    }


    public boolean containsIndividualReference(URI individualURI) {
        return owlIndividualReferences.containsKey(getOWLDataFactory().getOWLNamedIndividual(individualURI));
    }


    public boolean containsDatatypeReference(URI datatypeURI) {
        return owlDatatypeReferences.containsKey(getOWLDataFactory().getOWLDatatype(datatypeURI));
    }


    public boolean isPunned(URI uri) {
        int count = 0;
        if (containsClassReference(uri)) {
            count++;
        }
        if (containsObjectPropertyReference(uri)) {
            count++;
            if (count > 1) {
                return true;
            }
        }
        if (containsDataPropertyReference(uri)) {
            count++;
            if (count > 1) {
                return true;
            }
        }
        if (containsIndividualReference(uri)) {
            count++;
            if (count > 1) {
                return true;
            }
        }
        if (containsDatatypeReference(uri)) {
            count++;
            if (count > 1) {
                return true;
            }
        }
        return false;
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


    public boolean containsReference(OWLIndividual ind) {
        return owlIndividualReferences.containsKey(ind);
    }


    public boolean containsReference(OWLDatatype dt) {
        return owlDatatypeReferences.containsKey(dt);
    }


    public boolean containsReference(OWLAnnotationProperty property) {
        return annotationPropertyReferences.containsKey(property);
    }


    public boolean containsEntityDeclaration(OWLEntity owlEntity) {
        OWLDeclarationAxiom ax = getOWLDataFactory().getOWLDeclarationAxiom(owlEntity);
        return getAxiomsInternal(DECLARATION).contains(ax);
    }


    public boolean containsEntityReference(OWLEntity owlEntity) {
        return entityReferenceChecker.containsReference(owlEntity);
    }

    public boolean containsEntityReference(URI uri) {
        if(containsClassReference(uri)) {
            return true;
        }
        if(containsObjectPropertyReference(uri)) {
            return true;
        }
        if(containsDataPropertyReference(uri)) {
            return true;
        }
        if(containsIndividualReference(uri)) {
            return true;
        }
        if(containsDatatypeReference(uri)) {
            return true;
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
            return getAxioms(owlEntity.asOWLIndividual(), owlIndividualReferences, false);
        }
        if (owlEntity instanceof OWLDatatype) {
            return getAxioms(owlEntity.asOWLDatatype(), owlDatatypeReferences, false);
        }
        if(owlEntity instanceof OWLAnnotationProperty) {
            return getAxioms(owlEntity.asOWLAnnotationProperty(), annotationPropertyReferences, false);
        }
        return Collections.emptySet();
    }


    public Set<OWLClassAxiom> getAxioms(final OWLClass cls) {
        if (classAxiomsByClass == null) {
            buildClassAxiomsByClassIndex();
        }
        return getAxioms(cls, classAxiomsByClass);
    }


    public Set<OWLObjectPropertyAxiom> getAxioms(final OWLObjectPropertyExpression prop) {
        final Set<OWLObjectPropertyAxiom> result = new HashSet<OWLObjectPropertyAxiom>(50);

        addAxiomToSet(getAsymmetricObjectPropertyAxiom(prop), result);
        addAxiomToSet(getReflexiveObjectPropertyAxiom(prop), result);
        addAxiomToSet(getSymmetricObjectPropertyAxiom(prop), result);
        addAxiomToSet(getIrreflexiveObjectPropertyAxiom(prop), result);
        addAxiomToSet(getTransitiveObjectPropertyAxiom(prop), result);
        addAxiomToSet(getInverseFunctionalObjectPropertyAxiom(prop), result);
        addAxiomToSet(getFunctionalObjectPropertyAxiom(prop), result);
        result.addAll(getInverseObjectPropertyAxioms(prop));
        result.addAll(getObjectPropertyDomainAxioms(prop));
        result.addAll(getEquivalentObjectPropertiesAxioms(prop));
        result.addAll(getDisjointObjectPropertiesAxiom(prop));
        result.addAll(getObjectPropertyRangeAxioms(prop));
        result.addAll(getObjectSubPropertyAxiomsForSubProperty(prop));
        return result;
    }

    public Set<OWLAnnotationAxiom> getAxioms(final OWLAnnotationProperty prop) {
        Set<OWLAnnotationAxiom> result = new HashSet<OWLAnnotationAxiom>();
        for(OWLAnnotationAssertionAxiom ax : getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            if(ax.getSubject().equals(prop.getURI())) {
                result.add(ax);
            }
        }
        for(OWLSubAnnotationPropertyOf ax : getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            if(ax.getSubProperty().equals(prop)) {
                result.add(ax);
            }
        }
        for(OWLAnnotationPropertyRange ax : getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            if(ax.getProperty().equals(prop)) {
                result.add(ax);
            }
        }
        for(OWLAnnotationPropertyDomain ax : getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            if(ax.getProperty().equals(prop)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLDataPropertyAxiom> getAxioms(final OWLDataProperty prop) {
        final Set<OWLDataPropertyAxiom> result = createSet();
        result.addAll(getDataPropertyDomainAxioms(prop));
        result.addAll(getEquivalentDataPropertiesAxiom(prop));
        result.addAll(getDisjointDataPropertiesAxiom(prop));
        result.addAll(getDataPropertyRangeAxiom(prop));
        OWLFunctionalDataPropertyAxiom ax = getFunctionalDataPropertyAxiom(prop);
        if (ax != null) {
            result.add(ax);
        }
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


    public Set<OWLEntity> getReferencedEntities() {
        // We might want to cache this for performance reasons,
        // but I'm not sure right now.
        Set<OWLEntity> entities = new HashSet<OWLEntity>();
        entities.addAll(getReferencedClasses());
        entities.addAll(getReferencedObjectProperties());
        entities.addAll(getReferencedDataProperties());
        entities.addAll(getReferencedIndividuals());
        entities.addAll(getReferencedDatatypes());
        return entities;
    }


    public Set<OWLClass> getReferencedClasses() {
        return getReturnSet(owlClassReferences.keySet());
    }


    public Set<OWLObjectProperty> getReferencedObjectProperties() {
        return getReturnSet(owlObjectPropertyReferences.keySet());
    }


    public Set<OWLDataProperty> getReferencedDataProperties() {
        return getReturnSet(owlDataPropertyReferences.keySet());
    }


    public Set<OWLNamedIndividual> getReferencedIndividuals() {
        return getReturnSet(owlIndividualReferences.keySet());
    }


    public Set<OWLDatatype> getReferencedDatatypes() {
        return getReturnSet(owlDatatypeReferences.keySet());
    }

    public Set<OWLAnnotationProperty> getReferencedAnnotationProperties() {
        return getReturnSet(annotationPropertyReferences.keySet());
    }

    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return getReturnSet(importsDeclarations);
    }


    public Set<OWLOntology> getImports(OWLOntologyManager ontologyManager) throws UnknownOWLOntologyException {
        return ontologyManager.getImports(this);
    }


    public Set<OWLDatatypeDefinition> getDatatypeDefinitions(OWLDatatype datatype) {
        Set<OWLDatatypeDefinition> result = new HashSet<OWLDatatypeDefinition>();
        Set<OWLDatatypeDefinition> axioms = getAxiomsInternal(AxiomType.DATATYPE_DEFINITION);
        for(OWLDatatypeDefinition ax : axioms) {
            if(ax.getDatatype().equals(datatype)) {
                result.add(ax);
            }
        }
        return result;
    }


    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        if (subClassAxiomsByLHS == null) {
            subClassAxiomsByLHS = createMap();
            for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS)) {
                if (!axiom.getSubClass().isAnonymous()) {
                    addToIndexedSet(axiom.getSubClass().asOWLClass(), subClassAxiomsByLHS, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(cls, subClassAxiomsByLHS));
    }


    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        if (subClassAxiomsByRHS == null) {
            subClassAxiomsByRHS = createMap();
            for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS)) {
                if (!axiom.getSuperClass().isAnonymous()) {
                    addToIndexedSet(axiom.getSuperClass().asOWLClass(), subClassAxiomsByRHS, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(cls, subClassAxiomsByRHS));
    }


    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls) {
        if (equivalentClassesAxiomsByClass == null) {
            equivalentClassesAxiomsByClass = createMap();
            for (OWLEquivalentClassesAxiom axiom : getAxiomsInternal(EQUIVALENT_CLASSES)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), equivalentClassesAxiomsByClass, axiom);
                    }
                }
            }
        }
        return getReturnSet(getAxioms(cls, equivalentClassesAxiomsByClass));
    }


    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        if (disjointClassesAxiomsByClass == null) {
            disjointClassesAxiomsByClass = createMap();
            for (OWLDisjointClassesAxiom axiom : getAxiomsInternal(DISJOINT_CLASSES)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), disjointClassesAxiomsByClass, axiom);
                    }
                }
            }
        }
        return getReturnSet(getAxioms(cls, disjointClassesAxiomsByClass));
    }


    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        if (disjointUnionAxiomsByClass == null) {
            disjointUnionAxiomsByClass = createMap();
            for (OWLDisjointUnionAxiom axiom : getAxiomsInternal(DISJOINT_UNION)) {
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        addToIndexedSet(desc.asOWLClass(), disjointUnionAxiomsByClass, axiom);
                    }
                }
            }
        }
        return getReturnSet(getAxioms(owlClass, disjointUnionAxiomsByClass));
    }


    // Object properties
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression property) {
        if (objectSubPropertyAxiomsByLHS == null) {
            objectSubPropertyAxiomsByLHS = createMap();
            for (OWLSubObjectPropertyOfAxiom axiom : getAxiomsInternal(SUB_OBJECT_PROPERTY)) {
                addToIndexedSet(axiom.getSubProperty(), objectSubPropertyAxiomsByLHS, axiom);
            }
        }
        return getReturnSet(getAxioms(property, objectSubPropertyAxiomsByLHS));
    }


    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression property) {
        if (objectSubPropertyAxiomsByRHS == null) {
            objectSubPropertyAxiomsByRHS = createMap();
            for (OWLSubObjectPropertyOfAxiom axiom : getAxiomsInternal(SUB_OBJECT_PROPERTY)) {
                addToIndexedSet(axiom.getSuperProperty(), objectSubPropertyAxiomsByRHS, axiom);
            }
        }
        return getReturnSet(getAxioms(property, objectSubPropertyAxiomsByRHS));
    }


    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        if (objectPropertyRangeAxiomsByProperty == null) {
            objectPropertyDomainAxiomsByProperty = createMap();
            for (OWLObjectPropertyDomainAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_DOMAIN)) {
                addToIndexedSet(axiom.getProperty(), objectPropertyDomainAxiomsByProperty, axiom);
            }
        }
        return getReturnSet(getAxioms(property, objectPropertyDomainAxiomsByProperty));
    }


    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        if (objectPropertyRangeAxiomsByProperty == null) {
            objectPropertyRangeAxiomsByProperty = createMap();
            for (OWLObjectPropertyRangeAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_RANGE)) {
                addToIndexedSet(axiom.getProperty(), objectPropertyRangeAxiomsByProperty, axiom);
            }
        }
        return getReturnSet(getAxioms(property, objectPropertyRangeAxiomsByProperty));
    }


    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        if (inversePropertyAxiomsByProperty == null) {
            inversePropertyAxiomsByProperty = createMap();
            for (OWLInverseObjectPropertiesAxiom axiom : getAxiomsInternal(INVERSE_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, inversePropertyAxiomsByProperty, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(property, inversePropertyAxiomsByProperty));
    }


    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
            OWLObjectPropertyExpression property) {
        if (equivalentObjectPropertyAxiomsByProperty == null) {
            equivalentObjectPropertyAxiomsByProperty = createMap();
            for (OWLEquivalentObjectPropertiesAxiom axiom : getAxiomsInternal(EQUIVALENT_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentObjectPropertyAxiomsByProperty, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(property, equivalentObjectPropertyAxiomsByProperty));
    }


    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxiom(
            OWLObjectPropertyExpression property) {
        if (disjointObjectPropertyAxiomsByProperty == null) {
            disjointObjectPropertyAxiomsByProperty = createMap();
            for (OWLDisjointObjectPropertiesAxiom axiom : getAxiomsInternal(DISJOINT_OBJECT_PROPERTIES)) {
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointObjectPropertyAxiomsByProperty, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(property, disjointObjectPropertyAxiomsByProperty));
    }


    public OWLFunctionalObjectPropertyAxiom getFunctionalObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        if (functionalObjectPropertyAxiomsByProperty == null) {
            functionalObjectPropertyAxiomsByProperty = createMap();
            for (OWLFunctionalObjectPropertyAxiom axiom : getAxiomsInternal(FUNCTIONAL_OBJECT_PROPERTY)) {
                functionalObjectPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }
        return functionalObjectPropertyAxiomsByProperty.get(property);
    }


    public OWLInverseFunctionalObjectPropertyAxiom getInverseFunctionalObjectPropertyAxiom(
            OWLObjectPropertyExpression property) {
        if (inverseFunctionalPropertyAxiomsByProperty == null) {
            inverseFunctionalPropertyAxiomsByProperty = createMap();
            for (OWLInverseFunctionalObjectPropertyAxiom axiom : getAxiomsInternal(INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
                inverseFunctionalPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }
        return inverseFunctionalPropertyAxiomsByProperty.get(property);
    }


    public OWLSymmetricObjectPropertyAxiom getSymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        if (symmetricPropertyAxiomsByProperty == null) {
            symmetricPropertyAxiomsByProperty = createMap();
            for (OWLSymmetricObjectPropertyAxiom axiom : getAxiomsInternal(SYMMETRIC_OBJECT_PROPERTY)) {
                symmetricPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }
        return symmetricPropertyAxiomsByProperty.get(property);
    }


    public OWLAsymmetricObjectPropertyAxiom getAsymmetricObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        if (antiSymmetricPropertyAxiomsByProperty == null) {
            antiSymmetricPropertyAxiomsByProperty = createMap();
            for (OWLAsymmetricObjectPropertyAxiom axiom : getAxiomsInternal(ANTI_SYMMETRIC_OBJECT_PROPERTY)) {
                antiSymmetricPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }
        return antiSymmetricPropertyAxiomsByProperty.get(property);
    }


    public OWLReflexiveObjectPropertyAxiom getReflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        if (reflexivePropertyAxiomsByProperty == null) {
            reflexivePropertyAxiomsByProperty = createMap();
            for (OWLReflexiveObjectPropertyAxiom axiom : getAxiomsInternal(REFLEXIVE_OBJECT_PROPERTY)) {
                reflexivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }
        return reflexivePropertyAxiomsByProperty.get(property);
    }


    public OWLIrreflexiveObjectPropertyAxiom getIrreflexiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        if (irreflexivePropertyAxiomsByProperty == null) {
            irreflexivePropertyAxiomsByProperty = createMap();
            for (OWLIrreflexiveObjectPropertyAxiom axiom : getAxiomsInternal(IRREFLEXIVE_OBJECT_PROPERTY)) {
                irreflexivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }
        return irreflexivePropertyAxiomsByProperty.get(property);
    }


    public OWLTransitiveObjectPropertyAxiom getTransitiveObjectPropertyAxiom(OWLObjectPropertyExpression property) {
        if (transitivePropertyAxiomsByProperty == null) {
            transitivePropertyAxiomsByProperty = createMap();
            for (OWLTransitiveObjectPropertyAxiom axiom : getAxiomsInternal(TRANSITIVE_OBJECT_PROPERTY)) {
                transitivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }
        return transitivePropertyAxiomsByProperty.get(property);
    }


    public OWLFunctionalDataPropertyAxiom getFunctionalDataPropertyAxiom(OWLDataPropertyExpression property) {
        if (functionalDataPropertyAxiomsByProperty == null) {
            functionalDataPropertyAxiomsByProperty = createMap();
            for (OWLFunctionalDataPropertyAxiom axiom : getAxiomsInternal(FUNCTIONAL_DATA_PROPERTY)) {
                functionalDataPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }
        return functionalDataPropertyAxiomsByProperty.get(property);
    }


    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty) {
        if (dataSubPropertyAxiomsByLHS == null) {
            dataSubPropertyAxiomsByLHS = createMap();
            for (OWLSubDataPropertyOfAxiom axiom : getAxiomsInternal(SUB_DATA_PROPERTY)) {
                addToIndexedSet(axiom.getSubProperty(), dataSubPropertyAxiomsByLHS, axiom);
            }
        }
        return getReturnSet(getAxioms(lhsProperty, dataSubPropertyAxiomsByLHS));
    }


    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression property) {
        if (dataSubPropertyAxiomsByRHS == null) {
            dataSubPropertyAxiomsByRHS = createMap();
            for (OWLSubDataPropertyOfAxiom axiom : getAxiomsInternal(SUB_DATA_PROPERTY)) {
                addToIndexedSet(axiom.getSuperProperty(), dataSubPropertyAxiomsByRHS, axiom);
            }
        }
        return getReturnSet(getAxioms(property, dataSubPropertyAxiomsByRHS));
    }


    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty property) {
        if (dataPropertyDomainAxiomsByProperty == null) {
            dataPropertyDomainAxiomsByProperty = createMap();
            for (OWLDataPropertyDomainAxiom axiom : getAxiomsInternal(DATA_PROPERTY_DOMAIN)) {
                addToIndexedSet(axiom.getProperty(), dataPropertyDomainAxiomsByProperty, axiom);
            }
        }
        return getReturnSet(getAxioms(property, dataPropertyDomainAxiomsByProperty));
    }


    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxiom(OWLDataProperty property) {
        if (dataPropertyRangeAxiomsByProperty == null) {
            dataPropertyRangeAxiomsByProperty = createMap();
            for (OWLDataPropertyRangeAxiom axiom : getAxiomsInternal(DATA_PROPERTY_RANGE)) {
                addToIndexedSet(axiom.getProperty(), dataPropertyRangeAxiomsByProperty, axiom);
            }
        }
        return getReturnSet(getAxioms(property, dataPropertyRangeAxiomsByProperty));
    }


    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxiom(OWLDataProperty property) {
        if (equivalentDataPropertyAxiomsByProperty == null) {
            equivalentDataPropertyAxiomsByProperty = createMap();
            for (OWLEquivalentDataPropertiesAxiom axiom : getAxiomsInternal(EQUIVALENT_DATA_PROPERTIES)) {
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentDataPropertyAxiomsByProperty, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(property, equivalentDataPropertyAxiomsByProperty));
    }


    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxiom(OWLDataProperty property) {
        if (disjointDataPropertyAxiomsByProperty == null) {
            disjointDataPropertyAxiomsByProperty = createMap();
            for (OWLDisjointDataPropertiesAxiom axiom : getAxiomsInternal(DISJOINT_DATA_PROPERTIES)) {
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointDataPropertyAxiomsByProperty, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(property, disjointDataPropertyAxiomsByProperty));
    }

    ////


    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual) {
        if (classAssertionAxiomsByIndividual == null) {
            classAssertionAxiomsByIndividual = createMap();
            for (OWLClassAssertionAxiom axiom : getAxiomsInternal(CLASS_ASSERTION)) {
                addToIndexedSet(axiom.getIndividual(), classAssertionAxiomsByIndividual, axiom);
            }
        }
        return getReturnSet(getAxioms(individual, classAssertionAxiomsByIndividual));
    }


    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass type) {
        if (classAssertionAxiomsByClass == null) {
            classAssertionAxiomsByClass = createMap();
            for (OWLClassAssertionAxiom axiom : getAxiomsInternal(CLASS_ASSERTION)) {
                if (!axiom.getClassExpression().isAnonymous()) {
                    addToIndexedSet((OWLClass) axiom.getClassExpression(), classAssertionAxiomsByClass, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(type, classAssertionAxiomsByClass));
    }


    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual individual) {
        if (dataPropertyAssertionsByIndividual == null) {
            dataPropertyAssertionsByIndividual = createMap();
            for (OWLDataPropertyAssertionAxiom axiom : getAxiomsInternal(DATA_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), dataPropertyAssertionsByIndividual, axiom);
            }
        }
        return getReturnSet(getAxioms(individual, dataPropertyAssertionsByIndividual));
    }


    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        if (objectPropertyAssertionsByIndividual == null) {
            objectPropertyAssertionsByIndividual = createMap();
            for (OWLObjectPropertyAssertionAxiom axiom : getAxiomsInternal(OBJECT_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), objectPropertyAssertionsByIndividual, axiom);
            }
        }
        return getReturnSet(getAxioms(individual, objectPropertyAssertionsByIndividual));
    }


    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
            OWLIndividual individual) {
        if (negativeObjectPropertyAssertionAxiomsByIndividual == null) {
            negativeObjectPropertyAssertionAxiomsByIndividual = createMap();
            for (OWLNegativeObjectPropertyAssertionAxiom axiom : getAxiomsInternal(NEGATIVE_OBJECT_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), negativeObjectPropertyAssertionAxiomsByIndividual, axiom);
            }
        }
        return getReturnSet(getAxioms(individual, negativeObjectPropertyAssertionAxiomsByIndividual));
    }


    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        if (negativeDataPropertyAssertionAxiomsByIndividual == null) {
            negativeDataPropertyAssertionAxiomsByIndividual = createMap();
            for (OWLNegativeDataPropertyAssertionAxiom axiom : getAxiomsInternal(NEGATIVE_DATA_PROPERTY_ASSERTION)) {
                addToIndexedSet(axiom.getSubject(), negativeDataPropertyAssertionAxiomsByIndividual, axiom);
            }
        }
        return getReturnSet(getAxioms(individual, negativeDataPropertyAssertionAxiomsByIndividual));
    }


    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual) {
        if (sameIndividualsAxiomsByIndividual == null) {
            sameIndividualsAxiomsByIndividual = createMap();
            for (OWLSameIndividualAxiom axiom : getAxiomsInternal(SAME_INDIVIDUAL)) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, sameIndividualsAxiomsByIndividual, axiom);
                }
            }
        }
        return getReturnSet(getAxioms(individual, sameIndividualsAxiomsByIndividual));
    }


    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual individual) {
        if (differentIndividualsAxiomsByIndividual == null) {
            differentIndividualsAxiomsByIndividual = createMap();
            for (OWLDifferentIndividualsAxiom axiom : getAxiomsInternal(DIFFERENT_INDIVIDUALS)) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, differentIndividualsAxiomsByIndividual, axiom);
                }
            }
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
            // TODO:
//            if (allAxioms.contains(axiom)) {
            if (containsAxiom(axiom)) {
                changeVisitor.setAddAxiom(false);
                axiom.accept(changeVisitor);
                appliedChanges.add(change);
                if (axiom.isLogicalAxiom()) {
//                    logicalAxioms.remove((OWLLogicalAxiom) axiom);
                }
                handleAxiomRemoved(axiom);
            }
        }


        public void visit(SetOntologyURI change) {
            URI newOntURI = change.getNewURI();
            OWLOntologyID id = new OWLOntologyID(getOWLDataFactory().getIRI(newOntURI), ontologyID.getVersionIRI());
            if (!id.equals(ontologyID)) {
                appliedChanges.add(change);
                ontologyID = id;
            }
        }

        public void visit(AddAxiom change) {
            OWLAxiom axiom = change.getAxiom();
            // TODO:
//            if (!allAxioms.contains(axiom)) {
            if (!containsAxiom(axiom)) {
                changeVisitor.setAddAxiom(true);
                axiom.accept(changeVisitor);
                appliedChanges.add(change);
                if (axiom.isLogicalAxiom()) {
//                    logicalAxioms.add((OWLLogicalAxiom) axiom);
                }
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
            if(!importsDeclarations.contains(change.getImportDeclaration())) {
                appliedChanges.add(change);
                importsDeclarations.remove(change.getImportDeclaration());
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
        // TODO
//        allAxioms.add(axiom);
        entityCollector.reset();
        axiom.accept(entityCollector);
        for (OWLEntity object : entityCollector.getObjects()) {
            referenceAdder.setAxiom(axiom);
            object.accept(referenceAdder);
        }
    }


    private OWLNamedObjectReferenceRemover referenceRemover = new OWLNamedObjectReferenceRemover();


    private void handleAxiomRemoved(OWLAxiom axiom) {
//        allAxioms.remove(axiom);
        // TODO:
        entityCollector.reset();
        axiom.accept(entityCollector);
        for (OWLEntity object : entityCollector.getObjects()) {
            referenceRemover.setAxiom(axiom);
            object.accept(referenceRemover);
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
            addToIndexedSet(property, annotationPropertyReferences, axiom);
        }

        public void visit(OWLOntology ontology) {

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
            removeAxiomFromSet(property, annotationPropertyReferences, axiom, true);
        }

        public void visit(OWLOntology ontology) {
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
                addAxiomToSet(axiom, owlClassAxioms);
                addToIndexedSet(SUBCLASS, axiomsByType, axiom);
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
                removeAxiomFromSet(axiom, owlClassAxioms);
                removeAxiomFromSet(SUBCLASS, axiomsByType, axiom, true);
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
                addAxiomToSet(axiom, owlIndividualAxioms);
                addToIndexedSet(axiom.getSubject(), negativeObjectPropertyAssertionAxiomsByIndividual, axiom);
                addToIndexedSet(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlIndividualAxioms);
                removeAxiomFromSet(axiom.getSubject(), negativeObjectPropertyAssertionAxiomsByIndividual, axiom, true);
            }
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addAxiomToMap(axiom.getProperty(), antiSymmetricPropertyAxiomsByProperty, axiom);
                addToIndexedSet(ANTI_SYMMETRIC_OBJECT_PROPERTY, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(ANTI_SYMMETRIC_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromMap(axiom.getProperty(), antiSymmetricPropertyAxiomsByProperty);
            }
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addAxiomToMap(axiom.getProperty(), reflexivePropertyAxiomsByProperty, axiom);
                addToIndexedSet(REFLEXIVE_OBJECT_PROPERTY, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(REFLEXIVE_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromMap(axiom.getProperty(), reflexivePropertyAxiomsByProperty);
            }
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlClassAxioms);
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
                removeAxiomFromSet(axiom, owlClassAxioms);
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
                addAxiomToSet(axiom, owlDataPropertyAxioms);
                addToIndexedSet(axiom.getProperty(), dataPropertyDomainAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(DATA_PROPERTY_DOMAIN, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlDataPropertyAxioms);
                removeAxiomFromSet(axiom.getProperty(), dataPropertyDomainAxiomsByProperty, axiom, true);
            }
        }

        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addToIndexedSet(OBJECT_PROPERTY_DOMAIN, axiomsByType, axiom);
                if (axiom.getProperty() instanceof OWLObjectProperty) {
                    addToIndexedSet((OWLObjectProperty) axiom.getProperty(),
                            objectPropertyDomainAxiomsByProperty,
                            axiom);
                }
            } else {
                removeAxiomFromSet(OBJECT_PROPERTY_DOMAIN, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                if (axiom.getProperty() instanceof OWLObjectProperty) {
                    removeAxiomFromSet((OWLObjectProperty) axiom.getProperty(),
                            objectPropertyDomainAxiomsByProperty,
                            axiom,
                            true);
                }
            }
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addToIndexedSet(EQUIVALENT_OBJECT_PROPERTIES, axiomsByType, axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentObjectPropertyAxiomsByProperty, axiom);
                }
            } else {
                removeAxiomFromSet(EQUIVALENT_OBJECT_PROPERTIES, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    removeAxiomFromSet(prop, equivalentObjectPropertyAxiomsByProperty, axiom, true);
                }
            }
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addToIndexedSet(INVERSE_OBJECT_PROPERTIES, axiomsByType, axiom);
                addToIndexedSet(axiom.getFirstProperty(), inversePropertyAxiomsByProperty, axiom);
                addToIndexedSet(axiom.getSecondProperty(), inversePropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(INVERSE_OBJECT_PROPERTIES, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromSet(axiom.getFirstProperty(), inversePropertyAxiomsByProperty, axiom, false);
                removeAxiomFromSet(axiom.getSecondProperty(), inversePropertyAxiomsByProperty, axiom, false);
            }
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlIndividualAxioms);
                addToIndexedSet(axiom.getSubject(), negativeDataPropertyAssertionAxiomsByIndividual, axiom);
                addToIndexedSet(NEGATIVE_DATA_PROPERTY_ASSERTION, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(NEGATIVE_DATA_PROPERTY_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlIndividualAxioms);
                removeAxiomFromSet(axiom.getSubject(), negativeDataPropertyAssertionAxiomsByIndividual, axiom, true);
            }
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlIndividualAxioms);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, differentIndividualsAxiomsByIndividual, axiom);
                    addToIndexedSet(DIFFERENT_INDIVIDUALS, axiomsByType, axiom);
                }
            } else {
                removeAxiomFromSet(DIFFERENT_INDIVIDUALS, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlIndividualAxioms);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    removeAxiomFromSet(ind, differentIndividualsAxiomsByIndividual, axiom, true);
                }
            }
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlDataPropertyAxioms);
                addToIndexedSet(DISJOINT_DATA_PROPERTIES, axiomsByType, axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointDataPropertyAxiomsByProperty, axiom);
                }
            } else {
                removeAxiomFromSet(DISJOINT_DATA_PROPERTIES, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlDataPropertyAxioms);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    removeAxiomFromSet(prop, disjointDataPropertyAxiomsByProperty, axiom, true);
                }
            }
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DISJOINT_OBJECT_PROPERTIES, axiomsByType, axiom);
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, disjointObjectPropertyAxiomsByProperty, axiom);
                }
            } else {
                removeAxiomFromSet(DISJOINT_OBJECT_PROPERTIES, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    removeAxiomFromSet(prop, disjointObjectPropertyAxiomsByProperty, axiom, true);
                }
            }
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addToIndexedSet(OBJECT_PROPERTY_RANGE, axiomsByType, axiom);
                addToIndexedSet(axiom.getProperty(), objectPropertyRangeAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(OBJECT_PROPERTY_RANGE, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromSet(axiom.getProperty(), objectPropertyRangeAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlIndividualAxioms);
                addToIndexedSet(OBJECT_PROPERTY_ASSERTION, axiomsByType, axiom);
                addToIndexedSet(axiom.getSubject(), objectPropertyAssertionsByIndividual, axiom);
            } else {
                removeAxiomFromSet(OBJECT_PROPERTY_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlIndividualAxioms);
                removeAxiomFromSet(axiom.getSubject(), objectPropertyAssertionsByIndividual, axiom, true);
            }
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addToIndexedSet(FUNCTIONAL_OBJECT_PROPERTY, axiomsByType, axiom);
                addAxiomToMap(axiom.getProperty(), functionalObjectPropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(FUNCTIONAL_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromMap(axiom.getProperty(), functionalObjectPropertyAxiomsByProperty);
            }
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addToIndexedSet(SUB_OBJECT_PROPERTY, axiomsByType, axiom);
                addToIndexedSet(axiom.getSubProperty(), objectSubPropertyAxiomsByLHS, axiom);
                addToIndexedSet(axiom.getSuperProperty(), objectSubPropertyAxiomsByRHS, axiom);
            } else {
                removeAxiomFromSet(SUB_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromSet(axiom.getSubProperty(), objectSubPropertyAxiomsByLHS, axiom, true);
                removeAxiomFromSet(axiom.getSuperProperty(), objectSubPropertyAxiomsByRHS, axiom, true);
            }
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlClassAxioms);
                addToIndexedSet(DISJOINT_UNION, axiomsByType, axiom);
                addToIndexedSet(axiom.getOWLClass(), disjointUnionAxiomsByClass, axiom);
                addToIndexedSet(axiom.getOWLClass(), classAxiomsByClass, axiom);
            } else {
                removeAxiomFromSet(DISJOINT_UNION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlClassAxioms);
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
                addToIndexedSet(axiom.getAnnotation().getProperty().getURI(), annotationAxiomsByAnnotationURI, axiom);
            } else {
                removeAxiomFromSet(ANNOTATION_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom.getSubject(), annotationAssertionAxiomsBySubject, axiom, true);
                removeAxiomFromSet(axiom.getAnnotation().getProperty().getURI(),
                        annotationAxiomsByAnnotationURI,
                        axiom,
                        true);
            }
        }

        public void visit(OWLAnnotationPropertyDomain axiom) {
            if (addAxiom) {
                addToIndexedSet(ANNOTATION_PROPERTY_DOMAIN, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(ANNOTATION_PROPERTY_DOMAIN, axiomsByType, axiom, true);
            }
        }

        public void visit(OWLAnnotationPropertyRange axiom) {
            if (addAxiom) {
                addToIndexedSet(ANNOTATION_PROPERTY_RANGE, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(ANNOTATION_PROPERTY_RANGE, axiomsByType, axiom, true);
            }
        }

        public void visit(OWLSubAnnotationPropertyOf axiom) {
            if (addAxiom) {
                addToIndexedSet(SUB_ANNOTATION_PROPERTY_OF, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(SUB_ANNOTATION_PROPERTY_OF, axiomsByType, axiom, true);
            }
        }

        public void visit(OWLHasKeyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(HAS_KEY, axiomsByType, axiom);
            } else {
                removeAxiomFromSet(HAS_KEY, axiomsByType, axiom, true);
            }
        }

        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SYMMETRIC_OBJECT_PROPERTY, axiomsByType, axiom);
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addAxiomToMap(axiom.getProperty(), symmetricPropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(SYMMETRIC_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromMap(axiom.getProperty(), symmetricPropertyAxiomsByProperty);
            }
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(DATA_PROPERTY_RANGE, axiomsByType, axiom);
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addToIndexedSet(axiom.getProperty(), dataPropertyRangeAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(DATA_PROPERTY_RANGE, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromSet(axiom.getProperty(), dataPropertyRangeAxiomsByProperty, axiom, true);
            }
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(FUNCTIONAL_DATA_PROPERTY, axiomsByType, axiom);
                addAxiomToSet(axiom, owlDataPropertyAxioms);
                addAxiomToMap(axiom.getProperty(), functionalDataPropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(FUNCTIONAL_DATA_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlDataPropertyAxioms);
                removeAxiomFromMap(axiom.getProperty(), functionalDataPropertyAxiomsByProperty);
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(EQUIVALENT_DATA_PROPERTIES, axiomsByType, axiom);
                addAxiomToSet(axiom, owlDataPropertyAxioms);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    addToIndexedSet(prop, equivalentDataPropertyAxiomsByProperty, axiom);
                }
            } else {
                removeAxiomFromSet(EQUIVALENT_DATA_PROPERTIES, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlDataPropertyAxioms);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    removeAxiomFromSet(prop, equivalentDataPropertyAxiomsByProperty, axiom, true);
                }
            }
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlIndividualAxioms);
                addToIndexedSet(axiom.getIndividual(), classAssertionAxiomsByIndividual, axiom);
                addToIndexedSet(CLASS_ASSERTION, axiomsByType, axiom);
                if (!axiom.getClassExpression().isAnonymous()) {
                    addToIndexedSet((OWLClass) axiom.getClassExpression(), classAssertionAxiomsByClass, axiom);
                }
            } else {
                removeAxiomFromSet(CLASS_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlIndividualAxioms);
                removeAxiomFromSet(axiom.getIndividual(), classAssertionAxiomsByIndividual, axiom, true);
                if (!axiom.getClassExpression().isAnonymous()) {
                    removeAxiomFromSet((OWLClass) axiom.getClassExpression(), classAssertionAxiomsByClass, axiom, true);
                }
            }
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            if (addAxiom) {
                addAxiomToSet(axiom, owlClassAxioms);
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
                removeAxiomFromSet(axiom, owlClassAxioms);
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
                addAxiomToSet(axiom, owlIndividualAxioms);
                addToIndexedSet(DATA_PROPERTY_ASSERTION, axiomsByType, axiom);
                addToIndexedSet(axiom.getSubject(), dataPropertyAssertionsByIndividual, axiom);
            } else {
                removeAxiomFromSet(DATA_PROPERTY_ASSERTION, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlIndividualAxioms);
                removeAxiomFromSet(axiom.getSubject(), dataPropertyAssertionsByIndividual, axiom, true);
            }
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(TRANSITIVE_OBJECT_PROPERTY, axiomsByType, axiom);
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addAxiomToMap(axiom.getProperty(), transitivePropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(TRANSITIVE_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromMap(axiom.getProperty(), transitivePropertyAxiomsByProperty);
            }
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(IRREFLEXIVE_OBJECT_PROPERTY, axiomsByType, axiom);
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addAxiomToMap(axiom.getProperty(), irreflexivePropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(IRREFLEXIVE_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromMap(axiom.getProperty(), irreflexivePropertyAxiomsByProperty);
            }
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SUB_DATA_PROPERTY, axiomsByType, axiom);
                addAxiomToSet(axiom, owlDataPropertyAxioms);
                addToIndexedSet(axiom.getSubProperty(), dataSubPropertyAxiomsByLHS, axiom);
                addToIndexedSet(axiom.getSuperProperty(), dataSubPropertyAxiomsByRHS, axiom);
            } else {
                removeAxiomFromSet(SUB_DATA_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlDataPropertyAxioms);
                removeAxiomFromSet(axiom.getSubProperty(), dataSubPropertyAxiomsByLHS, axiom, true);
                removeAxiomFromSet(axiom.getSuperProperty(), dataSubPropertyAxiomsByRHS, axiom, true);
            }
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiomsByType, axiom);
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addAxiomToMap(axiom.getProperty(), inverseFunctionalPropertyAxiomsByProperty, axiom);
            } else {
                removeAxiomFromSet(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromMap(axiom.getProperty(), inverseFunctionalPropertyAxiomsByProperty);
            }
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SAME_INDIVIDUAL, axiomsByType, axiom);
                addAxiomToSet(axiom, owlIndividualAxioms);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    addToIndexedSet(ind, sameIndividualsAxiomsByIndividual, axiom);
                }
            } else {
                removeAxiomFromSet(SAME_INDIVIDUAL, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlIndividualAxioms);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    removeAxiomFromSet(ind, sameIndividualsAxiomsByIndividual, axiom, true);
                }
            }
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            if (addAxiom) {
                addToIndexedSet(SUB_PROPERTY_CHAIN_OF, axiomsByType, axiom);
                addAxiomToSet(axiom, owlObjectPropertyAxioms);
                addAxiomToSet(axiom, propertyChainSubPropertyAxioms);
            } else {
                removeAxiomFromSet(SUB_PROPERTY_CHAIN_OF, axiomsByType, axiom, true);
                removeAxiomFromSet(axiom, owlObjectPropertyAxioms);
                removeAxiomFromSet(axiom, propertyChainSubPropertyAxioms);
            }
        }


        public void visit(SWRLRule rule) {
            if (addAxiom) {
                addToIndexedSet(SWRL_RULE, axiomsByType, rule);
                addAxiomToSet(rule, ruleAxioms);
            } else {
                removeAxiomFromSet(SWRL_RULE, axiomsByType, rule, true);
                removeAxiomFromSet(rule, ruleAxioms);
            }
        }


        public void visit(OWLDatatypeDefinition axiom) {
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


    private static <K, V> Map<K, V> getReturnMap(Map<K, V> map) {
        return new HashMap<K, V>(map);
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


    private void buildClassAxiomsIndex() {
        owlClassAxioms = createSet();
        owlClassAxioms.addAll(getAxiomsInternal(SUBCLASS));
        owlClassAxioms.addAll(getAxiomsInternal(EQUIVALENT_CLASSES));
        owlClassAxioms.addAll(getAxiomsInternal(DISJOINT_CLASSES));
        owlClassAxioms.addAll(getAxiomsInternal(DISJOINT_UNION));
    }


    private void buildObjectPropertyAxiomsIndex() {
        owlObjectPropertyAxioms = createSet();
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(ANTI_SYMMETRIC_OBJECT_PROPERTY));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(REFLEXIVE_OBJECT_PROPERTY));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(OBJECT_PROPERTY_DOMAIN));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(EQUIVALENT_OBJECT_PROPERTIES));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(DISJOINT_OBJECT_PROPERTIES));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(OBJECT_PROPERTY_RANGE));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(FUNCTIONAL_OBJECT_PROPERTY));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(SUB_OBJECT_PROPERTY));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(SYMMETRIC_OBJECT_PROPERTY));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(TRANSITIVE_OBJECT_PROPERTY));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(IRREFLEXIVE_OBJECT_PROPERTY));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(INVERSE_FUNCTIONAL_OBJECT_PROPERTY));
        owlObjectPropertyAxioms.addAll(getAxiomsInternal(SUB_PROPERTY_CHAIN_OF));
    }


    private void buildDataPropertyAxiomsIndex() {
        owlDataPropertyAxioms = createSet();
        owlDataPropertyAxioms.addAll(getAxiomsInternal(DATA_PROPERTY_DOMAIN));
        owlDataPropertyAxioms.addAll(getAxiomsInternal(DISJOINT_DATA_PROPERTIES));
        owlDataPropertyAxioms.addAll(getAxiomsInternal(DATA_PROPERTY_RANGE));
        owlDataPropertyAxioms.addAll(getAxiomsInternal(FUNCTIONAL_DATA_PROPERTY));
        owlDataPropertyAxioms.addAll(getAxiomsInternal(EQUIVALENT_DATA_PROPERTIES));
        owlDataPropertyAxioms.addAll(getAxiomsInternal(SUB_DATA_PROPERTY));
    }


    private void buildIndividualAxiomsIndex() {
        owlIndividualAxioms = createSet();
        owlIndividualAxioms.addAll(getAxiomsInternal(NEGATIVE_OBJECT_PROPERTY_ASSERTION));
        owlIndividualAxioms.addAll(getAxiomsInternal(NEGATIVE_DATA_PROPERTY_ASSERTION));
        owlIndividualAxioms.addAll(getAxiomsInternal(DIFFERENT_INDIVIDUALS));
        owlIndividualAxioms.addAll(getAxiomsInternal(OBJECT_PROPERTY_ASSERTION));
        owlIndividualAxioms.addAll(getAxiomsInternal(CLASS_ASSERTION));
        owlIndividualAxioms.addAll(getAxiomsInternal(DATA_PROPERTY_ASSERTION));
        owlIndividualAxioms.addAll(getAxiomsInternal(SAME_INDIVIDUAL));
    }


    private void buildClassAxiomsByClassIndex() {
        classAxiomsByClass = new HashMap<OWLClass, Set<OWLClassAxiom>>();
        for (OWLEquivalentClassesAxiom axiom : getAxiomsInternal(EQUIVALENT_CLASSES)) {
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    addToIndexedSet((OWLClass) desc, classAxiomsByClass, axiom);
                }
            }
        }

        for (OWLSubClassOfAxiom axiom : getAxiomsInternal(SUBCLASS)) {
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
