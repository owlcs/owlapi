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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPrimitive;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLImmutableOntologyImpl extends OWLObjectImpl implements
        OWLOntology, Serializable {

    private static final long serialVersionUID = 40000L;
    protected final OWLOntologyManager manager;
    protected OWLOntologyID ontologyID;
    protected Internals ints;

    /**
     * @param manager
     *        ontology manager
     * @param ontologyID
     *        ontology id
     */
    public OWLImmutableOntologyImpl(@Nonnull OWLOntologyManager manager,
            @Nonnull OWLOntologyID ontologyID) {
        super();
        this.manager = checkNotNull(manager, "manager cannot be null");
        this.ontologyID = checkNotNull(ontologyID, "ontologyID cannot be null");
        ints = new Internals();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ontology(");
        sb.append(ontologyID.toString());
        sb.append(") [Axioms: ");
        int axiomCount = ints.getAxiomCount();
        sb.append(axiomCount);
        sb.append(" Logical Axioms: ");
        sb.append(ints.getLogicalAxiomCount());
        sb.append("] First 20 axioms: {");
        int counter = 0;
        for (OWLAxiom ax : ints.getAxioms()) {
            sb.append(ax).append(" ");
            counter++;
            if (counter == 20) {
                break;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public OWLOntologyManager getOWLOntologyManager() {
        return manager;
    }

    @Override
    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    @Override
    public boolean isAnonymous() {
        return ontologyID.isAnonymous();
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        if (object == this) {
            return 0;
        }
        OWLOntology other = (OWLOntology) object;
        return ontologyID.compareTo(other.getOntologyID());
    }

    @Override
    public boolean isEmpty() {
        return ints.isEmpty();
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getAxiomCount(axiomType);
        }
        int result = 0;
        for (OWLOntology ont : getImportsClosure()) {
            result += ont.getAxiomCount(axiomType);
        }
        return result;
    }

    @Override
    public boolean containsAxiom(OWLAxiom axiom) {
        return ints.contains(ints.getAxiomsByType(), axiom.getAxiomType(),
                axiom);
    }

    @Override
    public int getAxiomCount() {
        return getAxiomCount(false);
    }

    @Override
    public int getAxiomCount(boolean imports) {
        if (!imports) {
            return ints.getAxiomCount();
        }
        int total = 0;
        for (OWLOntology o : getImportsClosure()) {
            total += o.getAxiomCount();
        }
        return total;
    }

    @Override
    public Set<OWLAxiom> getAxioms() {
        return getAxioms(false);
    }

    @Override
    public Set<OWLAxiom> getAxioms(boolean imports) {
        if (!imports) {
            return ints.getAxioms();
        }
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        for (OWLOntology o : getImportsClosure()) {
            axioms.addAll(o.getAxioms());
        }
        return axioms;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        return (Set<T>) ints.getAxiomsByType().getValues(axiomType);
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType,
            boolean includeImportsClosure) {
        if (includeImportsClosure) {
            Set<T> toReturn = createSet();
            for (OWLOntology o : getImportsClosure()) {
                toReturn.addAll(o.getAxioms(axiomType));
            }
            return toReturn;
        } else {
            return getAxioms(axiomType);
        }
    }

    @Override
    public Set<OWLAxiom> getTBoxAxioms(boolean includeImportsClosure) {
        Set<OWLAxiom> toReturn = new HashSet<OWLAxiom>();
        for (AxiomType<?> type : AxiomType.TBoxAxiomTypes) {
            toReturn.addAll(getAxioms(type, includeImportsClosure));
        }
        return toReturn;
    }

    @Override
    public Set<OWLAxiom> getABoxAxioms(boolean includeImportsClosure) {
        Set<OWLAxiom> toReturn = new HashSet<OWLAxiom>();
        for (AxiomType<?> type : AxiomType.ABoxAxiomTypes) {
            toReturn.addAll(getAxioms(type, includeImportsClosure));
        }
        return toReturn;
    }

    @Override
    public Set<OWLAxiom> getRBoxAxioms(boolean includeImportsClosure) {
        Set<OWLAxiom> toReturn = new HashSet<OWLAxiom>();
        for (AxiomType<?> type : AxiomType.RBoxAxiomTypes) {
            toReturn.addAll(getAxioms(type, includeImportsClosure));
        }
        return toReturn;
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
        return ints.getAxiomCount(axiomType);
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        return ints.getLogicalAxioms();
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(boolean includeImports) {
        if (!includeImports) {
            return ints.getLogicalAxioms();
        }
        Set<OWLLogicalAxiom> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getLogicalAxioms(false));
        }
        return result;
    }

    @Override
    public int getLogicalAxiomCount() {
        return ints.getLogicalAxiomCount();
    }

    @Override
    public int getLogicalAxiomCount(boolean includeImports) {
        if (!includeImports) {
            return ints.getLogicalAxiomCount();
        }
        int total = 0;
        for (OWLOntology o : getImportsClosure()) {
            total += o.getLogicalAxiomCount(false);
        }
        return total;
    }

    @Override
    public Set<OWLAnnotation> getAnnotations() {
        return (Set<OWLAnnotation>) ints.getOntologyAnnotations(true);
    }

    @Override
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return ints.getGeneralClassAxioms();
    }

    @Override
    public boolean containsAxiom(OWLAxiom axiom, boolean includeImportsClosure,
            boolean ignoreAnnotations) {
        if (!includeImportsClosure) {
            if (!ignoreAnnotations) {
                return containsAxiom(axiom);
            } else {
                return containsAxiomIgnoreAnnotations(axiom);
            }
        }
        for (OWLOntology ont : getImportsClosure()) {
            if (ont.containsAxiom(axiom, false, ignoreAnnotations)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom) {
        Set<OWLAxiom> set = ints.getAxiomsByType().getValues(
                axiom.getAxiomType());
        for (OWLAxiom ax : set) {
            if (ax.equalsIgnoreAnnotations(axiom)) {
                return true;
            }
        }
        return false;
    }

    private Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom) {
        Set<OWLAxiom> result = createSet();
        if (containsAxiom(axiom)) {
            result.add(axiom);
        }
        Set<OWLAxiom> set = ints.getAxiomsByType().getValues(
                axiom.getAxiomType());
        for (OWLAxiom ax : set) {
            if (ax.equalsIgnoreAnnotations(axiom)) {
                result.add(ax);
            }
        }
        return result;
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getAxiomsIgnoreAnnotations(axiom);
        }
        Set<OWLAxiom> result = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getAxiomsIgnoreAnnotations(axiom, false));
        }
        return result;
    }

    @Override
    public boolean containsClassInSignature(IRI owlClassIRI,
            boolean includeImportsClosure) {
        return containsReference(
                manager.getOWLDataFactory().getOWLClass(owlClassIRI),
                includeImportsClosure);
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI propIRI,
            boolean includeImportsClosure) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLObjectProperty(propIRI), includeImportsClosure);
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI propIRI,
            boolean includeImportsClosure) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLDataProperty(propIRI), includeImportsClosure);
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI propIRI,
            boolean includeImportsClosure) {
        OWLAnnotationProperty p = manager.getOWLDataFactory()
                .getOWLAnnotationProperty(propIRI);
        return containsReference(p, includeImportsClosure)
                || checkOntologyAnnotations(p);
    }

    private boolean checkOntologyAnnotations(
            OWLAnnotationProperty owlAnnotationProperty) {
        for (OWLAnnotation anno : ints.getOntologyAnnotations(false)) {
            if (anno.getProperty().equals(owlAnnotationProperty)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsIndividualInSignature(IRI individualIRI,
            boolean includeImportsClosure) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLNamedIndividual(individualIRI), includeImportsClosure);
    }

    @Override
    public boolean containsDatatypeInSignature(IRI datatypeIRI,
            boolean includeImportsClosure) {
        return containsReference(
                manager.getOWLDataFactory().getOWLDatatype(datatypeIRI),
                includeImportsClosure);
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri) {
        return getEntitiesInSignature(iri, false);
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri,
            boolean includeImportsClosure) {
        Set<OWLEntity> result = createSet(6);
        if (containsClassInSignature(iri, includeImportsClosure)) {
            result.add(manager.getOWLDataFactory().getOWLClass(iri));
        }
        if (containsObjectPropertyInSignature(iri, includeImportsClosure)) {
            result.add(manager.getOWLDataFactory().getOWLObjectProperty(iri));
        }
        if (containsDataPropertyInSignature(iri, includeImportsClosure)) {
            result.add(manager.getOWLDataFactory().getOWLDataProperty(iri));
        }
        if (containsIndividualInSignature(iri, includeImportsClosure)) {
            result.add(manager.getOWLDataFactory().getOWLNamedIndividual(iri));
        }
        if (containsDatatypeInSignature(iri, includeImportsClosure)) {
            result.add(manager.getOWLDataFactory().getOWLDatatype(iri));
        }
        if (containsAnnotationPropertyInSignature(iri, includeImportsClosure)) {
            result.add(manager.getOWLDataFactory()
                    .getOWLAnnotationProperty(iri));
        }
        return result;
    }

    @Override
    public boolean containsReference(final OWLEntity entity,
            boolean importsClosure) {
        if (!importsClosure) {
            return ints.containsReference(entity);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.containsReference(entity, false)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDeclared(OWLEntity entity) {
        return ints.isDeclared(entity);
    }

    OWLAxiomSearchFilter<OWLDatatypeDefinitionAxiom, OWLDatatype> datatypeDefFilter = new OWLAxiomSearchFilter<OWLDatatypeDefinitionAxiom, OWLDatatype>() {

        private static final long serialVersionUID = 40000L;

        @Override
        public boolean pass(OWLDatatypeDefinitionAxiom axiom, OWLDatatype p) {
            return axiom.getDatatype().equals(p);
        }

        @Override
        public AxiomType<OWLDatatypeDefinitionAxiom> getAxiomType() {
            return AxiomType.DATATYPE_DEFINITION;
        }
    };
    OWLAxiomSearchFilter<OWLSubAnnotationPropertyOfAxiom, OWLAnnotationProperty> subAnnPropertyFilter = new OWLAxiomSearchFilter<OWLSubAnnotationPropertyOfAxiom, OWLAnnotationProperty>() {

        private static final long serialVersionUID = 40000L;

        @Override
        public boolean pass(OWLSubAnnotationPropertyOfAxiom axiom,
                OWLAnnotationProperty p) {
            return axiom.getSubProperty().equals(p);
        }

        @Override
        public AxiomType<OWLSubAnnotationPropertyOfAxiom> getAxiomType() {
            return AxiomType.SUB_ANNOTATION_PROPERTY_OF;
        }
    };
    OWLAxiomSearchFilter<OWLAnnotationPropertyRangeAxiom, OWLAnnotationProperty> apRangeFilter = new OWLAxiomSearchFilter<OWLAnnotationPropertyRangeAxiom, OWLAnnotationProperty>() {

        private static final long serialVersionUID = 40000L;

        @Override
        public boolean pass(OWLAnnotationPropertyRangeAxiom axiom,
                OWLAnnotationProperty p) {
            return axiom.getProperty().equals(p);
        }

        @Override
        public AxiomType<OWLAnnotationPropertyRangeAxiom> getAxiomType() {
            return AxiomType.ANNOTATION_PROPERTY_RANGE;
        }
    };
    OWLAxiomSearchFilter<OWLAnnotationPropertyDomainAxiom, OWLAnnotationProperty> apDomainFilter = new OWLAxiomSearchFilter<OWLAnnotationPropertyDomainAxiom, OWLAnnotationProperty>() {

        private static final long serialVersionUID = 40000L;

        @Override
        public boolean pass(OWLAnnotationPropertyDomainAxiom axiom,
                OWLAnnotationProperty p) {
            return axiom.getProperty().equals(p);
        }

        @Override
        public AxiomType<OWLAnnotationPropertyDomainAxiom> getAxiomType() {
            return AxiomType.ANNOTATION_PROPERTY_DOMAIN;
        }
    };

    @Override
    public boolean
            isDeclared(OWLEntity owlEntity, boolean includeImportsClosure) {
        if (isDeclared(owlEntity)) {
            return true;
        }
        if (includeImportsClosure) {
            for (OWLOntology ont : manager.getImportsClosure(this)) {
                if (!ont.equals(this) && ont.isDeclared(owlEntity)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker(
                false);
        return entityReferenceChecker.containsReference(owlEntity);
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity,
            boolean includeImportsClosure) {
        OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker(
                includeImportsClosure);
        return entityReferenceChecker.containsReference(owlEntity);
    }

    @Override
    public boolean containsEntityInSignature(IRI entityIRI,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            if (containsClassInSignature(entityIRI, false)) {
                return true;
            }
            if (containsObjectPropertyInSignature(entityIRI, false)) {
                return true;
            }
            if (containsDataPropertyInSignature(entityIRI, false)) {
                return true;
            }
            if (containsIndividualInSignature(entityIRI, false)) {
                return true;
            }
            if (containsDatatypeInSignature(entityIRI, false)) {
                return true;
            }
            if (containsAnnotationPropertyInSignature(entityIRI, false)) {
                return true;
            }
            return false;
        }
        for (OWLOntology ont : getImportsClosure()) {
            if (ont.containsEntityInSignature(entityIRI, false)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<OWLEntity> getSignature() {
        // We might want to cache this for performance reasons,
        // but I'm not sure right now.
        Set<OWLEntity> entities = createSet();
        entities.addAll(getClassesInSignature());
        entities.addAll(getObjectPropertiesInSignature());
        entities.addAll(getDataPropertiesInSignature());
        entities.addAll(getIndividualsInSignature());
        entities.addAll(getDatatypesInSignature());
        entities.addAll(getAnnotationPropertiesInSignature(false));
        return entities;
    }

    @Override
    public Set<OWLEntity> getSignature(boolean includeImportsClosure) {
        Set<OWLEntity> entities = getSignature();
        if (includeImportsClosure) {
            for (OWLOntology ont : getImportsClosure()) {
                if (!ont.equals(this)) {
                    entities.addAll(ont.getSignature(includeImportsClosure));
                }
            }
        }
        return entities;
    }

    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return ints.get(OWLAnonymousIndividual.class, OWLAxiom.class).keySet();
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return ints.get(OWLClass.class, OWLAxiom.class).keySet();
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return ints.get(OWLDataProperty.class, OWLAxiom.class).keySet();
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return ints.get(OWLObjectProperty.class, OWLAxiom.class).keySet();
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return ints.get(OWLNamedIndividual.class, OWLAxiom.class).keySet();
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return ints.get(OWLDatatype.class, OWLAxiom.class).keySet();
    }

    @Override
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

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getObjectPropertiesInSignature();
        }
        Set<OWLObjectProperty> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getObjectPropertiesInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getDataPropertiesInSignature();
        }
        Set<OWLDataProperty> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getDataPropertiesInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getIndividualsInSignature();
        }
        Set<OWLNamedIndividual> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getIndividualsInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return ints.get(OWLAnonymousIndividual.class, OWLAxiom.class)
                    .keySet();
        }
        Set<OWLAnonymousIndividual> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getReferencedAnonymousIndividuals(false));
        }
        return result;
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getDatatypesInSignature();
        }
        Set<OWLDatatype> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getDatatypesInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(
            boolean includeImportsClosure) {
        Set<OWLAnnotationProperty> props = createSet();
        if (!includeImportsClosure) {
            props.addAll(ints.get(OWLAnnotationProperty.class, OWLAxiom.class,
                    false).keySet());
            for (OWLAnnotation anno : ints.getOntologyAnnotations(false)) {
                props.add(anno.getProperty());
            }
        } else {
            for (OWLOntology ont : getImportsClosure()) {
                props.addAll(ont.getAnnotationPropertiesInSignature(false));
            }
        }
        return props;
    }

    @Override
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return (Set<OWLImportsDeclaration>) ints.getImportsDeclarations(true);
    }

    @Override
    public Set<IRI> getDirectImportsDocuments()
            throws UnknownOWLOntologyException {
        Set<IRI> result = createSet();
        for (OWLImportsDeclaration importsDeclaration : ints
                .getImportsDeclarations(false)) {
            result.add(importsDeclaration.getIRI());
        }
        return result;
    }

    @Override
    public Set<OWLOntology> getImports() throws UnknownOWLOntologyException {
        return manager.getImports(this);
    }

    @Override
    public Set<OWLOntology> getDirectImports()
            throws UnknownOWLOntologyException {
        return manager.getDirectImports(this);
    }

    @Override
    public Set<OWLOntology> getImportsClosure()
            throws UnknownOWLOntologyException {
        return getOWLOntologyManager().getImportsClosure(this);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Handlers for when axioms are added/removed, which perform various global
    // indexing
    // housekeeping tasks.
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Add/Remove axiom mechanism. Each axiom gets visited by a visitor, which
    // adds the axiom
    // to the appropriate index.
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /
    // / Utility methods for getting/setting various values in maps and sets
    // /
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
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

    @Override
    public int hashCode() {
        return ontologyID.hashCode();
    }

    private class OWLEntityReferenceChecker implements OWLEntityVisitor,
            Serializable {

        private static final long serialVersionUID = 40000L;
        private boolean ref;
        private boolean includeImports;

        public OWLEntityReferenceChecker(boolean b) {
            includeImports = b;
        }

        public boolean containsReference(OWLEntity entity) {
            ref = false;
            entity.accept(this);
            return ref;
        }

        @Override
        public void visit(OWLClass cls) {
            ref = OWLImmutableOntologyImpl.this.containsReference(cls,
                    includeImports);
        }

        @Override
        public void visit(OWLDatatype datatype) {
            ref = OWLImmutableOntologyImpl.this.containsReference(datatype,
                    includeImports);
        }

        @Override
        public void visit(OWLNamedIndividual individual) {
            ref = OWLImmutableOntologyImpl.this.containsReference(individual,
                    includeImports);
        }

        @Override
        public void visit(OWLDataProperty property) {
            ref = OWLImmutableOntologyImpl.this.containsReference(property,
                    includeImports);
        }

        @Override
        public void visit(OWLObjectProperty property) {
            ref = OWLImmutableOntologyImpl.this.containsReference(property,
                    includeImports);
        }

        @Override
        public void visit(OWLAnnotationProperty property) {
            ref = OWLImmutableOntologyImpl.this.containsReference(property,
                    includeImports);
        }
    }

    // ////////////////////////////////////////
    // OWLAxiomIndex
    // ////////////////////////////////////////
    private Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity) {
        return ints.getReferencingAxioms(owlEntity);
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlEntity,
            boolean includeImportsClosure) {
        if (owlEntity instanceof OWLEntity) {
            if (!includeImportsClosure) {
                return getReferencingAxioms((OWLEntity) owlEntity);
            }
            Set<OWLAxiom> result = createSet();
            for (OWLOntology ont : getImportsClosure()) {
                result.addAll(ont.getReferencingAxioms(owlEntity, false));
            }
            return result;
        } else if (owlEntity instanceof OWLAnonymousIndividual) {
            return ints.get(OWLAnonymousIndividual.class, OWLAxiom.class)
                    .getValues((OWLAnonymousIndividual) owlEntity);
        }
        // TODO add support for looking up by IRI, OWLLiteral, etc.
        return Collections.emptySet();
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass cls, boolean includeImports) {
        if (!includeImports) {
            return ints.get(OWLClass.class, OWLClassAxiom.class).getValues(cls);
        }
        Set<OWLClassAxiom> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(cls, false));
        }
        return result;
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
            OWLObjectPropertyExpression prop, boolean includeImports) {
        Set<OWLObjectPropertyAxiom> result = createSet(50);
        if (!includeImports) {
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
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(prop, false));
            }
        }
        return result;
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty prop,
            boolean includeImports) {
        Set<OWLAnnotationAxiom> result = createSet();
        if (!includeImports) {
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
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(prop, false));
            }
        }
        return result;
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty prop,
            boolean includeImports) {
        Set<OWLDataPropertyAxiom> result = createSet();
        if (!includeImports) {
            result.addAll(getDataPropertyDomainAxioms(prop));
            result.addAll(getEquivalentDataPropertiesAxioms(prop));
            result.addAll(getDisjointDataPropertiesAxioms(prop));
            result.addAll(getDataPropertyRangeAxioms(prop));
            result.addAll(getFunctionalDataPropertyAxioms(prop));
            result.addAll(getDataSubPropertyAxiomsForSubProperty(prop));
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(prop, false));
            }
        }
        return result;
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual individual,
            boolean includeImports) {
        Set<OWLIndividualAxiom> result = createSet();
        if (!includeImports) {
            result.addAll(getClassAssertionAxioms(individual));
            result.addAll(getObjectPropertyAssertionAxioms(individual));
            result.addAll(getDataPropertyAssertionAxioms(individual));
            result.addAll(getNegativeObjectPropertyAssertionAxioms(individual));
            result.addAll(getNegativeDataPropertyAssertionAxioms(individual));
            result.addAll(getSameIndividualAxioms(individual));
            result.addAll(getDifferentIndividualAxioms(individual));
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(individual, false));
            }
        }
        return result;
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype,
            boolean includeImports) {
        if (!includeImports) {
            return getDatatypeDefinitions(datatype);
        }
        Set<OWLDatatypeDefinitionAxiom> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(datatype, false));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <A extends OWLAxiom> Set<A>
            getAxioms(Class<A> type, OWLPrimitive entity,
                    boolean includeImports, boolean forSubPosition) {
        if (!includeImports) {
            return ints.get((Class<OWLPrimitive>) entity.getClass(), type)
                    .getValues(entity);
        }
        Set<A> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(type, entity, false, forSubPosition));
        }
        return result;
    }

    @Override
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
        return ints.get(OWLEntity.class, OWLDeclarationAxiom.class).getValues(
                entity);
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
            OWLAnnotationSubject subject) {
        return ints.get(OWLAnnotationSubject.class,
                OWLAnnotationAssertionAxiom.class).getValues(subject);
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(
            OWLDatatype datatype) {
        return ints.filterAxioms(datatypeDefFilter, datatype);
    }

    @Override
    public Set<OWLSubAnnotationPropertyOfAxiom>
            getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty) {
        return ints.filterAxioms(subAnnPropertyFilter, subProperty);
    }

    @Override
    public Set<OWLAnnotationPropertyDomainAxiom>
            getAnnotationPropertyDomainAxioms(OWLAnnotationProperty property) {
        return ints.filterAxioms(apDomainFilter, property);
    }

    @Override
    public Set<OWLAnnotationPropertyRangeAxiom>
            getAnnotationPropertyRangeAxioms(OWLAnnotationProperty property) {
        return ints.filterAxioms(apRangeFilter, property);
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        return ints.get(OWLClass.class, OWLSubClassOfAxiom.class)
                .getValues(cls);
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        return ints.get(OWLClass.class, OWLSubClassOfAxiom.class, true)
                .getValues(cls);
    }

    @Override
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(
            OWLClass cls) {
        return ints.get(OWLClass.class, OWLEquivalentClassesAxiom.class)
                .getValues(cls);
    }

    @Override
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        return ints.get(OWLClass.class, OWLDisjointClassesAxiom.class)
                .getValues(cls);
    }

    @Override
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        return ints.get(OWLClass.class, OWLDisjointUnionAxiom.class).getValues(
                owlClass);
    }

    @Override
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
        return ints.get(OWLClass.class, OWLHasKeyAxiom.class).getValues(cls);
    }

    // Object properties
    @Override
    public Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSubProperty(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLSubObjectPropertyOfAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSuperProperty(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLSubObjectPropertyOfAxiom.class, true).getValues(property);
    }

    @Override
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
            OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLObjectPropertyDomainAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
            OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLObjectPropertyRangeAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
            OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLInverseObjectPropertiesAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertiesAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLEquivalentObjectPropertiesAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertiesAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLDisjointObjectPropertiesAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLFunctionalObjectPropertyAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLInverseFunctionalObjectPropertyAxiom.class).getValues(
                property);
    }

    @Override
    public Set<OWLSymmetricObjectPropertyAxiom>
            getSymmetricObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLSymmetricObjectPropertyAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLAsymmetricObjectPropertyAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLReflexiveObjectPropertyAxiom>
            getReflexiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLReflexiveObjectPropertyAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLIrreflexiveObjectPropertyAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLTransitiveObjectPropertyAxiom>
            getTransitiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return ints.get(OWLObjectPropertyExpression.class,
                OWLTransitiveObjectPropertyAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
            OWLDataPropertyExpression property) {
        return ints.get(OWLDataPropertyExpression.class,
                OWLFunctionalDataPropertyAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty) {
        return ints.get(OWLDataPropertyExpression.class,
                OWLSubDataPropertyOfAxiom.class).getValues(lhsProperty);
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsForSuperProperty(
                    OWLDataPropertyExpression property) {
        return ints.get(OWLDataPropertyExpression.class,
                OWLSubDataPropertyOfAxiom.class, true).getValues(property);
    }

    @Override
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
            OWLDataProperty property) {
        return ints.get(OWLDataPropertyExpression.class,
                OWLDataPropertyDomainAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
            OWLDataProperty property) {
        return ints.get(OWLDataPropertyExpression.class,
                OWLDataPropertyRangeAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertiesAxioms(OWLDataProperty property) {
        return ints.get(OWLDataPropertyExpression.class,
                OWLEquivalentDataPropertiesAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
            OWLDataProperty property) {
        return ints.get(OWLDataPropertyExpression.class,
                OWLDisjointDataPropertiesAxiom.class).getValues(property);
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            OWLIndividual individual) {
        return ints.get(OWLIndividual.class, OWLClassAssertionAxiom.class)
                .getValues(individual);
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            OWLClassExpression type) {
        return ints.get(OWLClassExpression.class, OWLClassAssertionAxiom.class)
                .getValues(type);
    }

    @Override
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
            OWLIndividual individual) {
        return ints.get(OWLIndividual.class,
                OWLDataPropertyAssertionAxiom.class).getValues(individual);
    }

    @Override
    public Set<OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return ints.get(OWLIndividual.class,
                OWLObjectPropertyAssertionAxiom.class).getValues(individual);
    }

    @Override
    public Set<OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return ints.get(OWLIndividual.class,
                OWLNegativeObjectPropertyAssertionAxiom.class).getValues(
                individual);
    }

    @Override
    public Set<OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        return ints.get(OWLIndividual.class,
                OWLNegativeDataPropertyAssertionAxiom.class).getValues(
                individual);
    }

    @Override
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(
            OWLIndividual individual) {
        return ints.get(OWLIndividual.class, OWLSameIndividualAxiom.class)
                .getValues(individual);
    }

    @Override
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
            OWLIndividual individual) {
        return ints
                .get(OWLIndividual.class, OWLDifferentIndividualsAxiom.class)
                .getValues(individual);
    }
}
