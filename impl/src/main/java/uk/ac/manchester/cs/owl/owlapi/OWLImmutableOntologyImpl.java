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

import static org.semanticweb.owlapi.model.Imports.*;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.Imports;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPrimitive;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.Search;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLImmutableOntologyImpl extends OWLAxiomIndexImpl implements
        OWLOntology, Serializable {

    private static final long serialVersionUID = 40000L;
    protected final OWLOntologyManager manager;
    protected OWLOntologyID ontologyID;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.ONTOLOGY;
    }

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
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
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
        return getAxiomCount(EXCLUDED);
    }

    @Override
    public int getAxiomCount(Imports importsIncluded) {
        if (importsIncluded == EXCLUDED) {
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
        return getAxioms(EXCLUDED);
    }

    @Override
    public Set<OWLAxiom> getAxioms(Imports importsExcluded) {
        if (importsExcluded == EXCLUDED) {
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
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return getAxioms(axiomType);
        }
        Set<T> toReturn = createSet();
        for (OWLOntology o : getImportsClosure()) {
            toReturn.addAll(o.getAxioms(axiomType));
        }
        return toReturn;
    }

    @Override
    public Set<OWLAxiom> getTBoxAxioms(Imports includeImports) {
        Set<OWLAxiom> toReturn = new HashSet<OWLAxiom>();
        for (AxiomType<?> type : AxiomType.TBoxAxiomTypes) {
            toReturn.addAll(getAxioms(type, includeImports));
        }
        return toReturn;
    }

    @Override
    public Set<OWLAxiom> getABoxAxioms(Imports includeImports) {
        Set<OWLAxiom> toReturn = new HashSet<OWLAxiom>();
        for (AxiomType<?> type : AxiomType.ABoxAxiomTypes) {
            toReturn.addAll(getAxioms(type, includeImports));
        }
        return toReturn;
    }

    @Override
    public Set<OWLAxiom> getRBoxAxioms(Imports includeImports) {
        Set<OWLAxiom> toReturn = new HashSet<OWLAxiom>();
        for (AxiomType<?> type : AxiomType.RBoxAxiomTypes) {
            toReturn.addAll(getAxioms(type, includeImports));
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
    public Set<OWLLogicalAxiom> getLogicalAxioms(Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return ints.getLogicalAxioms();
        }
        Set<OWLLogicalAxiom> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getLogicalAxioms(EXCLUDED));
        }
        return result;
    }

    @Override
    public int getLogicalAxiomCount() {
        return ints.getLogicalAxiomCount();
    }

    @Override
    public int getLogicalAxiomCount(Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return ints.getLogicalAxiomCount();
        }
        int total = 0;
        for (OWLOntology o : getImportsClosure()) {
            total += o.getLogicalAxiomCount(EXCLUDED);
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
    public boolean containsAxiom(OWLAxiom axiom, Imports includeImports,
            Search ignoreAnnotations) {
        if (includeImports == EXCLUDED) {
            if (ignoreAnnotations == Search.CONSIDER_ANNOTATIONS) {
                return containsAxiom(axiom);
            } else {
                return containsAxiomIgnoreAnnotations(axiom);
            }
        }
        for (OWLOntology ont : getImportsClosure()) {
            if (ont.containsAxiom(axiom, EXCLUDED, ignoreAnnotations)) {
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
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return getAxiomsIgnoreAnnotations(axiom);
        }
        Set<OWLAxiom> result = createSet();
        for (OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getAxiomsIgnoreAnnotations(axiom, EXCLUDED));
        }
        return result;
    }

    @Override
    public boolean containsClassInSignature(IRI owlClassIRI,
            Imports includeImports) {
        return containsReference(
                manager.getOWLDataFactory().getOWLClass(owlClassIRI),
                includeImports);
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI propIRI,
            Imports includeImports) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLObjectProperty(propIRI), includeImports);
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI propIRI,
            Imports includeImports) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLDataProperty(propIRI), includeImports);
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI propIRI,
            Imports includeImports) {
        OWLAnnotationProperty p = manager.getOWLDataFactory()
                .getOWLAnnotationProperty(propIRI);
        return containsReference(p, includeImports)
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
            Imports includeImports) {
        return containsReference(manager.getOWLDataFactory()
                .getOWLNamedIndividual(individualIRI), includeImports);
    }

    @Override
    public boolean containsDatatypeInSignature(IRI datatypeIRI,
            Imports includeImports) {
        return containsReference(
                manager.getOWLDataFactory().getOWLDatatype(datatypeIRI),
                includeImports);
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri) {
        return getEntitiesInSignature(iri, EXCLUDED);
    }

    @Override
    public Set<OWLEntity>
            getEntitiesInSignature(IRI iri, Imports includeImports) {
        Set<OWLEntity> result = createSet(6);
        if (containsClassInSignature(iri, includeImports)) {
            result.add(manager.getOWLDataFactory().getOWLClass(iri));
        }
        if (containsObjectPropertyInSignature(iri, includeImports)) {
            result.add(manager.getOWLDataFactory().getOWLObjectProperty(iri));
        }
        if (containsDataPropertyInSignature(iri, includeImports)) {
            result.add(manager.getOWLDataFactory().getOWLDataProperty(iri));
        }
        if (containsIndividualInSignature(iri, includeImports)) {
            result.add(manager.getOWLDataFactory().getOWLNamedIndividual(iri));
        }
        if (containsDatatypeInSignature(iri, includeImports)) {
            result.add(manager.getOWLDataFactory().getOWLDatatype(iri));
        }
        if (containsAnnotationPropertyInSignature(iri, includeImports)) {
            result.add(manager.getOWLDataFactory()
                    .getOWLAnnotationProperty(iri));
        }
        return result;
    }

    @Override
    public boolean containsReference(final OWLEntity entity,
            Imports importsClosure) {
        if (importsClosure == EXCLUDED) {
            return ints.containsReference(entity);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.containsReference(entity, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDeclared(OWLEntity entity) {
        return ints.isDeclared(entity);
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity, Imports includeImports) {
        if (isDeclared(owlEntity)) {
            return true;
        }
        if (includeImports == INCLUDED) {
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
                EXCLUDED);
        return entityReferenceChecker.containsReference(owlEntity);
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity,
            Imports includeImports) {
        OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker(
                includeImports);
        return entityReferenceChecker.containsReference(owlEntity);
    }

    @Override
    public boolean containsEntityInSignature(IRI entityIRI,
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
            if (containsClassInSignature(entityIRI, EXCLUDED)) {
                return true;
            }
            if (containsObjectPropertyInSignature(entityIRI, EXCLUDED)) {
                return true;
            }
            if (containsDataPropertyInSignature(entityIRI, EXCLUDED)) {
                return true;
            }
            if (containsIndividualInSignature(entityIRI, EXCLUDED)) {
                return true;
            }
            if (containsDatatypeInSignature(entityIRI, EXCLUDED)) {
                return true;
            }
            if (containsAnnotationPropertyInSignature(entityIRI, EXCLUDED)) {
                return true;
            }
            return false;
        }
        for (OWLOntology ont : getImportsClosure()) {
            if (ont.containsEntityInSignature(entityIRI, EXCLUDED)) {
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
        entities.addAll(getAnnotationPropertiesInSignature(EXCLUDED));
        return entities;
    }

    @Override
    public Set<OWLEntity> getSignature(Imports includeImports) {
        Set<OWLEntity> entities = getSignature();
        if (includeImports == INCLUDED) {
            for (OWLOntology ont : getImportsClosure()) {
                if (!ont.equals(this)) {
                    entities.addAll(ont.getSignature(includeImports));
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
    public Set<OWLClass> getClassesInSignature(Imports includeImports) {
        if (includeImports == EXCLUDED) {
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
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
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
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
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
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
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
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return ints.get(OWLAnonymousIndividual.class, OWLAxiom.class)
                    .keySet();
        }
        Set<OWLAnonymousIndividual> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getReferencedAnonymousIndividuals(EXCLUDED));
        }
        return result;
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(Imports includeImports) {
        if (includeImports == EXCLUDED) {
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
            Imports includeImports) {
        Set<OWLAnnotationProperty> props = createSet();
        if (includeImports == EXCLUDED) {
            props.addAll(ints.get(OWLAnnotationProperty.class, OWLAxiom.class,
                    Search.IN_SUB_POSITION).keySet());
            for (OWLAnnotation anno : ints.getOntologyAnnotations(false)) {
                props.add(anno.getProperty());
            }
        } else {
            for (OWLOntology ont : getImportsClosure()) {
                props.addAll(ont.getAnnotationPropertiesInSignature(EXCLUDED));
            }
        }
        return props;
    }

    @Override
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return (Set<OWLImportsDeclaration>) ints.getImportsDeclarations(true);
    }

    @Override
    public Set<IRI> getDirectImportsDocuments() {
        Set<IRI> result = createSet();
        for (OWLImportsDeclaration importsDeclaration : ints
                .getImportsDeclarations(false)) {
            result.add(importsDeclaration.getIRI());
        }
        return result;
    }

    @Override
    public Set<OWLOntology> getImports() {
        return manager.getImports(this);
    }

    @Override
    public Set<OWLOntology> getDirectImports() {
        return manager.getDirectImports(this);
    }

    @Override
    public Set<OWLOntology> getImportsClosure() {
        return getOWLOntologyManager().getImportsClosure(this);
    }

    // Add/Remove axiom mechanism. Each axiom gets visited by a visitor, which
    // adds the axiom
    // to the appropriate index.
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

    // Utility methods for getting/setting various values in maps and sets
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
        private Imports includeImports;

        public OWLEntityReferenceChecker(Imports b) {
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

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass cls, Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return ints.get(OWLClass.class, OWLClassAxiom.class).getValues(cls);
        }
        Set<OWLClassAxiom> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(cls, EXCLUDED));
        }
        return result;
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
            OWLObjectPropertyExpression prop, Imports includeImports) {
        Set<OWLObjectPropertyAxiom> result = createSet(50);
        if (includeImports == EXCLUDED) {
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
                result.addAll(o.getAxioms(prop, EXCLUDED));
            }
        }
        return result;
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty prop,
            Imports includeImports) {
        Set<OWLAnnotationAxiom> result = createSet();
        if (includeImports == EXCLUDED) {
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
                result.addAll(o.getAxioms(prop, EXCLUDED));
            }
        }
        return result;
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty prop,
            Imports includeImports) {
        Set<OWLDataPropertyAxiom> result = createSet();
        if (includeImports == EXCLUDED) {
            result.addAll(getDataPropertyDomainAxioms(prop));
            result.addAll(getEquivalentDataPropertiesAxioms(prop));
            result.addAll(getDisjointDataPropertiesAxioms(prop));
            result.addAll(getDataPropertyRangeAxioms(prop));
            result.addAll(getFunctionalDataPropertyAxioms(prop));
            result.addAll(getDataSubPropertyAxiomsForSubProperty(prop));
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(prop, EXCLUDED));
            }
        }
        return result;
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual individual,
            Imports includeImports) {
        Set<OWLIndividualAxiom> result = createSet();
        if (includeImports == EXCLUDED) {
            result.addAll(getClassAssertionAxioms(individual));
            result.addAll(getObjectPropertyAssertionAxioms(individual));
            result.addAll(getDataPropertyAssertionAxioms(individual));
            result.addAll(getNegativeObjectPropertyAssertionAxioms(individual));
            result.addAll(getNegativeDataPropertyAssertionAxioms(individual));
            result.addAll(getSameIndividualAxioms(individual));
            result.addAll(getDifferentIndividualAxioms(individual));
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(individual, EXCLUDED));
            }
        }
        return result;
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype,
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return getDatatypeDefinitions(datatype);
        }
        Set<OWLDatatypeDefinitionAxiom> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(datatype, EXCLUDED));
        }
        return result;
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlEntity,
            Imports includeImports) {
        if (owlEntity instanceof OWLEntity) {
            if (includeImports == EXCLUDED) {
                return ints.getReferencingAxioms((OWLEntity) owlEntity);
            }
            Set<OWLAxiom> result = createSet();
            for (OWLOntology ont : getImportsClosure()) {
                result.addAll(ont.getReferencingAxioms(owlEntity, EXCLUDED));
            }
            return result;
        } else if (owlEntity instanceof OWLAnonymousIndividual) {
            return ints.get(OWLAnonymousIndividual.class, OWLAxiom.class)
                    .getValues((OWLAnonymousIndividual) owlEntity);
        }
        // TODO add support for looking up by IRI, OWLLiteral, etc.
        return Collections.emptySet();
    }

    // OWLAxiomIndex
    @Override
    public <A extends OWLAxiom> Set<A> getAxioms(Class<A> type,
            OWLObject entity, Imports includeImports, Search forSubPosition) {
        if (includeImports == EXCLUDED) {
            return getAxioms(type, entity.getClass(), entity, includeImports,
                    forSubPosition);
        }
        Set<A> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(type, entity, EXCLUDED, forSubPosition));
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends OWLAxiom> Set<A> getAxioms(Class<A> type,
            Class<? extends OWLObject> explicit, OWLObject entity,
            Imports includeImports, Search forSubPosition) {
        if (includeImports == EXCLUDED) {
            return ints.get((Class<OWLObject>) explicit, type, forSubPosition)
                    .getValues(entity);
        }
        Set<A> result = createSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(type, entity, EXCLUDED, forSubPosition));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(
            OWLAxiomSearchFilter filter, Object key, Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return (Collection<T>) ints.filterAxioms(filter, key);
        }
        // iterating over the import closure; using a set because there might be
        // duplicate axioms
        Set<T> toReturn = new HashSet<T>();
        for (OWLOntology o : getImportsClosure()) {
            toReturn.addAll((Collection<T>) o.filterAxioms(filter, key,
                    EXCLUDED));
        }
        return toReturn;
    }

    @Override
    public boolean contains(OWLAxiomSearchFilter filter, Object key,
            Imports includeImports) {
        if (includeImports == EXCLUDED) {
            return ints.contains(filter, key);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.contains(filter, key, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }
}
