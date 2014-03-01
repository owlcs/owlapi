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

import static org.semanticweb.owlapi.util.CollectionFactory.createSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
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
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
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
import org.semanticweb.owlapi.model.OWLMutableOntology;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
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
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitor;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/*
 *  based on OWLOntologyImpl svn revision 1294
 */
/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 26-Oct-2006
 */
public class OWLOntologyImpl extends OWLObjectImpl implements
        OWLMutableOntology, Serializable {

    private static final long serialVersionUID = 30406L;
    private final OWLOntologyManager manager;
    protected OWLOntologyID ontologyID;
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Sets of different kinds of axioms
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected Internals internals;

    /**
     * @param manager
     *        ontology manager
     * @param ontologyID
     *        ontology id
     */
    public OWLOntologyImpl(OWLOntologyManager manager, OWLOntologyID ontologyID) {
        super();
        this.manager = manager;
        this.ontologyID = ontologyID;
        internals = new InternalsImpl();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ontology(");
        sb.append(ontologyID.toString());
        sb.append(") ");
        sb.append("[Axioms: ");
        sb.append(internals.getAxiomCount());
        sb.append(" Logical Axioms: ");
        sb.append(internals.getLogicalAxiomCount());
        sb.append("]");
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
        return internals.isEmpty();
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
        return internals.contains(internals.getAxiomsByType(),
                axiom.getAxiomType(), axiom);
    }

    @Override
    public int getAxiomCount() {
        return internals.getAxiomCount();
    }

    @Override
    public Set<OWLAxiom> getAxioms() {
        return internals.getAxioms();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        return (Set<T>) internals.getValues(internals.getAxiomsByType(),
                axiomType);
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
        return internals.getAxiomCount(axiomType);
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        return internals.getLogicalAxioms();
    }

    @Override
    public int getLogicalAxiomCount() {
        return internals.getLogicalAxiomCount();
    }

    @Override
    public Set<OWLAnnotation> getAnnotations() {
        return internals.getOntologyAnnotations();
    }

    @Override
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity entity) {
        return internals.getValues(internals.getDeclarationsByEntity(), entity);
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
            OWLAnnotationSubject subject) {
        return internals.getValues(
                internals.getAnnotationAssertionAxiomsBySubject(), subject);
    }

    @Override
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return internals.getGeneralClassAxioms();
    }

    @Override
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

    @Override
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom) {
        Set<OWLAxiom> set = internals.getValues(internals.getAxiomsByType(),
                axiom.getAxiomType());
        for (OWLAxiom ax : set) {
            if (ax.equalsIgnoreAnnotations(axiom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsAxiomIgnoreAnnotations(axiom);
        } else {
            for (OWLOntology ont : getImportsClosure()) {
                if (ont.containsAxiomIgnoreAnnotations(axiom)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom) {
        Set<OWLAxiom> result = createSet();
        if (containsAxiom(axiom)) {
            result.add(axiom);
        }
        Set<OWLAxiom> set = internals.getValues(internals.getAxiomsByType(),
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
            result.addAll(ont.getAxiomsIgnoreAnnotations(axiom));
        }
        return result;
    }

    @Override
    public boolean containsClassInSignature(IRI owlClassIRI) {
        return containsReference(manager.getOWLDataFactory().getOWLClass(
                owlClassIRI));
    }

    @Override
    public boolean containsClassInSignature(IRI owlClassIRI,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsClassInSignature(owlClassIRI);
        }
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsClassInSignature(owlClassIRI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI propIRI) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLObjectProperty(propIRI));
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI propIRI,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsObjectPropertyInSignature(propIRI);
        }
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsObjectPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI propIRI) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLDataProperty(propIRI));
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI propIRI,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsDataPropertyInSignature(propIRI);
        }
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsDataPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI propIRI) {
        final OWLAnnotationProperty owlAnnotationProperty = manager
                .getOWLDataFactory().getOWLAnnotationProperty(propIRI);
        boolean b = containsReference(owlAnnotationProperty);
        if (b) {
            return true;
        } else {
            for (OWLAnnotation anno : internals.getOntologyAnnotations()) {
                if (anno.getProperty().equals(owlAnnotationProperty)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI propIRI,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsAnnotationPropertyInSignature(propIRI);
        }
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsAnnotationPropertyInSignature(propIRI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsIndividualInSignature(IRI individualIRI) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLNamedIndividual(individualIRI));
    }

    @Override
    public boolean containsIndividualInSignature(IRI individualIRI,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsIndividualInSignature(individualIRI);
        }
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsIndividualInSignature(individualIRI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsDatatypeInSignature(IRI datatypeIRI) {
        return containsReference(manager.getOWLDataFactory().getOWLDatatype(
                datatypeIRI));
    }

    @Override
    public boolean containsDatatypeInSignature(IRI datatypeIRI,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return containsDatatypeInSignature(datatypeIRI);
        }
        for (OWLOntology ont : manager.getImportsClosure(this)) {
            if (ont.containsDatatypeInSignature(datatypeIRI)) {
                return true;
            }
        }
        return false;
    }

    @Override
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
            result.add(manager.getOWLDataFactory()
                    .getOWLAnnotationProperty(iri));
        }
        return result;
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getEntitiesInSignature(iri);
        } else {
            Set<OWLEntity> result = createSet(6);
            for (OWLOntology ont : getImportsClosure()) {
                result.addAll(ont.getEntitiesInSignature(iri));
            }
            return result;
        }
    }

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    // @Override
            public
            boolean containsReference(OWLClass owlClass) {
        return internals.contains(internals.getOwlClassReferences(), owlClass);
    }

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    // @Override
            public
            boolean containsReference(OWLObjectProperty prop) {
        return internals.contains(internals.getOwlObjectPropertyReferences(),
                prop);
    }

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    // @Override
            public
            boolean containsReference(OWLDataProperty prop) {
        return internals.contains(internals.getOwlDataPropertyReferences(),
                prop);
    }

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    // @Override
            public
            boolean containsReference(OWLNamedIndividual ind) {
        return internals.contains(internals.getOwlIndividualReferences(), ind);
    }

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    // @Override
            public
            boolean containsReference(OWLDatatype dt) {
        return internals.contains(internals.getOwlDatatypeReferences(), dt);
    }

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    // @Override
            public
            boolean containsReference(OWLAnnotationProperty property) {
        return internals.contains(
                internals.getOwlAnnotationPropertyReferences(), property);
    }

    @Override
    public boolean isDeclared(OWLEntity entity) {
        return internals.isDeclared(manager.getOWLDataFactory()
                .getOWLDeclarationAxiom(entity));
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(
            OWLDatatype datatype) {
        return internals.filterAxioms(datatypeDefFilter, datatype);
    }

    @Override
    public Set<OWLSubAnnotationPropertyOfAxiom>
            getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty) {
        return internals.filterAxioms(subAnnPropertyFilter, subProperty);
    }

    @Override
    public Set<OWLAnnotationPropertyDomainAxiom>
            getAnnotationPropertyDomainAxioms(OWLAnnotationProperty property) {
        return internals.filterAxioms(apDomainFilter, property);
    }

    @Override
    public Set<OWLAnnotationPropertyRangeAxiom>
            getAnnotationPropertyRangeAxioms(OWLAnnotationProperty property) {
        return internals.filterAxioms(apRangeFilter, property);
    }

    OWLAxiomSearchFilter<OWLDatatypeDefinitionAxiom, OWLDatatype> datatypeDefFilter = new OWLAxiomSearchFilter<OWLDatatypeDefinitionAxiom, OWLDatatype>() {

        private static final long serialVersionUID = 30406L;

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

        private static final long serialVersionUID = 30406L;

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

        private static final long serialVersionUID = 30406L;

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

        private static final long serialVersionUID = 30406L;

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
        OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker();
        return entityReferenceChecker.containsReference(owlEntity);
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity,
            boolean includeImportsClosure) {
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

    @Override
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

    @Override
    public boolean containsEntityInSignature(IRI entityIRI,
            boolean includeImportsClosure) {
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

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity) {
        final ReferencedAxiomsCollector referencedAxiomsCollector = new ReferencedAxiomsCollector();
        return owlEntity.accept(referencedAxiomsCollector);
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity,
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getReferencingAxioms(owlEntity);
        }
        Set<OWLAxiom> result = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getReferencingAxioms(owlEntity));
        }
        return result;
    }

    @Override
    public Set<OWLAxiom>
            getReferencingAxioms(OWLAnonymousIndividual individual) {
        return internals.getValues(
                internals.getOwlAnonymousIndividualReferences(), individual);
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(final OWLClass cls) {
        return internals.getValues(internals.getClassAxiomsByClass(), cls);
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
            final OWLObjectPropertyExpression prop) {
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype) {
        return getDatatypeDefinitions(datatype);
    }

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    @Deprecated
    public Set<OWLNamedObject> getReferencedObjects() {
        Set<OWLNamedObject> result = createSet();
        result.addAll(internals.getKeyset(internals.getOwlClassReferences()));
        // Consider doing this in a more efficient way (although typically, the
        // number of
        // properties in an ontology isn't large)
        for (OWLObjectPropertyExpression prop : internals.getKeyset(internals
                .getOwlObjectPropertyReferences())) {
            if (!prop.isAnonymous()) {
                result.add((OWLObjectProperty) prop);
            }
        }
        result.addAll(internals.getKeyset(internals
                .getOwlDataPropertyReferences()));
        result.addAll(internals.getKeyset(internals
                .getOwlIndividualReferences()));
        return result;
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
        entities.addAll(getAnnotationPropertiesInSignature());
        return entities;
    }

    @Override
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
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return internals.getKeyset(internals
                .getOwlAnonymousIndividualReferences());
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return internals.getKeyset(internals.getOwlClassReferences());
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return internals.getKeyset(internals.getOwlDataPropertyReferences());
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return internals.getKeyset(internals.getOwlObjectPropertyReferences());
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return internals.getKeyset(internals.getOwlIndividualReferences());
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return internals.getKeyset(internals.getOwlDatatypeReferences());
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
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals() {
        return internals.getKeyset(internals
                .getOwlAnonymousIndividualReferences());
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
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        Set<OWLAnnotationProperty> props = createSet(internals
                .getKeyset(internals.getOwlAnnotationPropertyReferences()));
        for (OWLAnnotation anno : internals.getOntologyAnnotations()) {
            props.add(anno.getProperty());
        }
        return props;
    }

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    @Deprecated
    public Set<OWLAnnotationProperty> getReferencedAnnotationProperties(
            boolean includeImportsClosure) {
        if (!includeImportsClosure) {
            return getAnnotationPropertiesInSignature();
        }
        Set<OWLAnnotationProperty> results = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getAnnotationPropertiesInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return internals.getImportsDeclarations();
    }

    @Override
    public Set<IRI> getDirectImportsDocuments()
            throws UnknownOWLOntologyException {
        Set<IRI> result = createSet();
        for (OWLImportsDeclaration importsDeclaration : internals
                .getImportsDeclarations()) {
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

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        return internals.getValues(internals.getSubClassAxiomsByLHS(), cls);
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        return internals.getValues(internals.getSubClassAxiomsByRHS(), cls);
    }

    @Override
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(
            OWLClass cls) {
        return internals.getValues(
                internals.getEquivalentClassesAxiomsByClass(), cls);
    }

    @Override
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        return internals.getValues(internals.getDisjointClassesAxiomsByClass(),
                cls);
    }

    @Override
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        return internals.getValues(internals.getDisjointUnionAxiomsByClass(),
                owlClass);
    }

    @Override
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
        return internals.getValues(internals.getHasKeyAxiomsByClass(), cls);
    }

    // Object properties
    @Override
    public Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSubProperty(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(internals.getObjectSubPropertyAxiomsByLHS(),
                property);
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSuperProperty(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(internals.getObjectSubPropertyAxiomsByRHS(),
                property);
    }

    @Override
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
            OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getObjectPropertyDomainAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
            OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getObjectPropertyRangeAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
            OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getInversePropertyAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertiesAxioms(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getEquivalentObjectPropertyAxiomsByProperty(),
                property);
    }

    @Override
    public Set<OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertiesAxioms(
                    OWLObjectPropertyExpression property) {
        return internals
                .getValues(
                        internals.getDisjointObjectPropertyAxiomsByProperty(),
                        property);
    }

    @Override
    public Set<OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getFunctionalObjectPropertyAxiomsByProperty(),
                property);
    }

    @Override
    public Set<OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getInverseFunctionalPropertyAxiomsByProperty(),
                property);
    }

    @Override
    public Set<OWLSymmetricObjectPropertyAxiom>
            getSymmetricObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getSymmetricPropertyAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getAsymmetricPropertyAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLReflexiveObjectPropertyAxiom>
            getReflexiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getReflexivePropertyAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getIrreflexivePropertyAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLTransitiveObjectPropertyAxiom>
            getTransitiveObjectPropertyAxioms(
                    OWLObjectPropertyExpression property) {
        return internals.getValues(
                internals.getTransitivePropertyAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
            OWLDataPropertyExpression property) {
        return internals
                .getValues(
                        internals.getFunctionalDataPropertyAxiomsByProperty(),
                        property);
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty) {
        return internals.getValues(internals.getDataSubPropertyAxiomsByLHS(),
                lhsProperty);
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsForSuperProperty(
                    OWLDataPropertyExpression property) {
        return internals.getValues(internals.getDataSubPropertyAxiomsByRHS(),
                property);
    }

    @Override
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
            OWLDataProperty property) {
        return internals.getValues(
                internals.getDataPropertyDomainAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
            OWLDataProperty property) {
        return internals.getValues(
                internals.getDataPropertyRangeAxiomsByProperty(), property);
    }

    @Override
    public Set<OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertiesAxioms(OWLDataProperty property) {
        return internals
                .getValues(
                        internals.getEquivalentDataPropertyAxiomsByProperty(),
                        property);
    }

    @Override
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
            OWLDataProperty property) {
        return internals.getValues(
                internals.getDisjointDataPropertyAxiomsByProperty(), property);
    }

    // //
    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            OWLIndividual individual) {
        return internals.getValues(
                internals.getClassAssertionAxiomsByIndividual(), individual);
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            OWLClassExpression type) {
        return internals.getValues(internals.getClassAssertionAxiomsByClass(),
                type);
    }

    @Override
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
            OWLIndividual individual) {
        return internals.getValues(
                internals.getDataPropertyAssertionsByIndividual(), individual);
    }

    @Override
    public Set<OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return internals
                .getValues(internals.getObjectPropertyAssertionsByIndividual(),
                        individual);
    }

    @Override
    public Set<OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return internals.getValues(internals
                .getNegativeObjectPropertyAssertionAxiomsByIndividual(),
                individual);
    }

    @Override
    public Set<OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        return internals.getValues(
                internals.getNegativeDataPropertyAssertionAxiomsByIndividual(),
                individual);
    }

    @Override
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(
            OWLIndividual individual) {
        return internals.getValues(
                internals.getSameIndividualsAxiomsByIndividual(), individual);
    }

    @Override
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
            OWLIndividual individual) {
        return internals.getValues(
                internals.getDifferentIndividualsAxiomsByIndividual(),
                individual);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // /
    // / Ontology Change handling mechanism
    // /
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<OWLOntologyChange> applyChange(OWLOntologyChange change) {
        List<OWLOntologyChange> appliedChanges = new ArrayList<OWLOntologyChange>(
                2);
        OWLOntologyChangeFilter changeFilter = new OWLOntologyChangeFilter();
        change.accept(changeFilter);
        List<OWLOntologyChange> applied = changeFilter.getAppliedChanges();
        if (applied.size() == 1) {
            appliedChanges.add(change);
        } else {
            appliedChanges.addAll(applied);
        }
        return appliedChanges;
    }

    @Override
    public List<OWLOntologyChange>
            applyChanges(List<OWLOntologyChange> changes) {
        List<OWLOntologyChange> appliedChanges = new ArrayList<OWLOntologyChange>();
        OWLOntologyChangeFilter changeFilter = new OWLOntologyChangeFilter();
        for (OWLOntologyChange change : changes) {
            change.accept(changeFilter);
            appliedChanges.addAll(changeFilter.getAppliedChanges());
            changeFilter.reset();
        }
        return appliedChanges;
    }

    private final class ReferencedAxiomsCollector implements
            OWLEntityVisitorEx<Set<OWLAxiom>> {

        public ReferencedAxiomsCollector() {}

        @Override
        public Set<OWLAxiom> visit(OWLClass cls) {
            return internals.getValues(internals.getOwlClassReferences(), cls);
        }

        @Override
        public Set<OWLAxiom> visit(OWLObjectProperty property) {
            return internals.getValues(
                    internals.getOwlObjectPropertyReferences(), property);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDataProperty property) {
            return internals.getValues(
                    internals.getOwlDataPropertyReferences(), property);
        }

        @Override
        public Set<OWLAxiom> visit(OWLNamedIndividual individual) {
            return internals.getValues(internals.getOwlIndividualReferences(),
                    individual);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDatatype datatype) {
            return internals.getValues(internals.getOwlDatatypeReferences(),
                    datatype);
        }

        @Override
        public Set<OWLAxiom> visit(OWLAnnotationProperty property) {
            return internals.getValues(
                    internals.getOwlAnnotationPropertyReferences(), property);
        }
    }

    protected class OWLOntologyChangeFilter implements
            OWLOntologyChangeVisitor, Serializable {

        private static final long serialVersionUID = 30406L;
        private final List<OWLOntologyChange> appliedChanges;

        public OWLOntologyChangeFilter() {
            appliedChanges = new ArrayList<OWLOntologyChange>();
        }

        /** @return applied changes */
        public List<OWLOntologyChange> getAppliedChanges() {
            return appliedChanges;
        }

        /** reset filter */
        public void reset() {
            appliedChanges.clear();
        }

        @Override
        public void visit(RemoveAxiom change) {
            if (internals.removeAxiom(change.getAxiom())) {
                appliedChanges.add(change);
            }
        }

        @Override
        public void visit(SetOntologyID change) {
            OWLOntologyID id = change.getNewOntologyID();
            if (!id.equals(ontologyID)) {
                appliedChanges.add(change);
                ontologyID = id;
            }
        }

        @Override
        public void visit(AddAxiom change) {
            if (internals.addAxiom(change.getAxiom())) {
                appliedChanges.add(change);
            }
        }

        @Override
        public void visit(AddImport change) {
            // TODO change this to be done inside
            if (internals.addImportsDeclaration(change.getImportDeclaration())) {
                appliedChanges.add(change);
            }
        }

        @Override
        public void visit(RemoveImport change) {
            if (internals.removeImportsDeclaration(change
                    .getImportDeclaration())) {
                appliedChanges.add(change);
            }
        }

        @Override
        public void visit(AddOntologyAnnotation change) {
            if (internals.addOntologyAnnotation(change.getAnnotation())) {
                appliedChanges.add(change);
            }
        }

        @Override
        public void visit(RemoveOntologyAnnotation change) {
            if (internals.removeOntologyAnnotation(change.getAnnotation())) {
                appliedChanges.add(change);
            }
        }
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

    // XXX not in the interface
    @SuppressWarnings("javadoc")
    // @Override
            public
            void accept(OWLNamedObjectVisitor visitor) {
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

        private static final long serialVersionUID = 30406L;
        private boolean ref;

        public OWLEntityReferenceChecker() {}

        public boolean containsReference(OWLEntity entity) {
            ref = false;
            entity.accept(this);
            return ref;
        }

        @Override
        public void visit(OWLClass cls) {
            ref = OWLOntologyImpl.this.containsReference(cls);
        }

        @Override
        public void visit(OWLDatatype datatype) {
            ref = OWLOntologyImpl.this.containsReference(datatype);
        }

        @Override
        public void visit(OWLNamedIndividual individual) {
            ref = OWLOntologyImpl.this.containsReference(individual);
        }

        @Override
        public void visit(OWLDataProperty property) {
            ref = OWLOntologyImpl.this.containsReference(property);
        }

        @Override
        public void visit(OWLObjectProperty property) {
            ref = OWLOntologyImpl.this.containsReference(property);
        }

        @Override
        public void visit(OWLAnnotationProperty property) {
            ref = OWLOntologyImpl.this.containsReference(property);
        }
    }
}
