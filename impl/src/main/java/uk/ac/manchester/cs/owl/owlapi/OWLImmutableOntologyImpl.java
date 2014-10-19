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

import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomCollectionNoArgs;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLPrimitive;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import com.google.common.collect.Iterables;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLImmutableOntologyImpl extends OWLAxiomIndexImpl implements
        OWLOntology, OWLAxiomCollectionNoArgs, Serializable {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    protected OWLOntologyManager manager;
    @Nonnull
    protected OWLDataFactory df;
    @Nonnull
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
        this.manager = checkNotNull(manager, "manager cannot be null");
        this.ontologyID = checkNotNull(ontologyID, "ontologyID cannot be null");
        df = manager.getOWLDataFactory();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Ontology(").append(ontologyID).append(") [Axioms: ")
                .append(ints.getAxiomCount()).append(" Logical Axioms: ")
                .append(ints.getLogicalAxiomCount())
                .append("] First 20 axioms: {");
        for (OWLAxiom ax : Iterables.limit(ints.getAxioms(), 20)) {
            sb.append(ax).append(' ');
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public OWLOntologyManager getOWLOntologyManager() {
        return manager;
    }

    @Override
    public void setOWLOntologyManager(OWLOntologyManager manager) {
        this.manager = manager;
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
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
        return ints.getAxiomCount(axiomType);
    }

    @Override
    public int getAxiomCount() {
        return ints.getAxiomCount();
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom axiom) {
        return Internals.contains(ints.getAxiomsByType(), axiom.getAxiomType(),
                axiom);
    }

    @Override
    public Set<OWLAxiom> getAxioms() {
        return asSet(ints.getAxioms());
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        return (Set<T>) asSet(ints.getAxiomsByType().getValues(axiomType));
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        return ints.getLogicalAxioms();
    }

    @Override
    public int getLogicalAxiomCount() {
        return ints.getLogicalAxiomCount();
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType,
            Imports imports) {
        return imports.count(this, o -> o.getAxiomCount(axiomType));
    }

    @Override
    public int getAxiomCount(Imports imports) {
        return imports.count(this, o -> o.getAxiomCount());
    }

    @Override
    public Set<OWLAxiom> getAxioms(Imports imports) {
        return imports.collect(this, o -> o.getAxioms().stream());
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType,
            Imports imports) {
        return imports.collect(this, o -> o.getAxioms(axiomType).stream());
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(Imports imports) {
        return imports.collect(this, o -> o.getLogicalAxioms().stream());
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getTBoxAxioms(Imports imports) {
        return AxiomType.TBoxAxiomTypes.stream()
                .flatMap(t -> getAxioms(t, imports).stream())
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getABoxAxioms(Imports imports) {
        return AxiomType.ABoxAxiomTypes.stream()
                .flatMap(t -> getAxioms(t, imports).stream())
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getRBoxAxioms(Imports imports) {
        return AxiomType.RBoxAxiomTypes.stream()
                .flatMap(t -> getAxioms(t, imports).stream())
                .collect(Collectors.toSet());
    }

    @Override
    public int getLogicalAxiomCount(Imports imports) {
        return imports.count(this, o -> o.getLogicalAxiomCount());
    }

    @Nonnull
    @Override
    public Set<OWLAnnotation> getAnnotations() {
        return (Set<OWLAnnotation>) ints.getOntologyAnnotations(true);
    }

    @Override
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return ints.getGeneralClassAxioms();
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom axiom, Imports imports,
            AxiomAnnotations ignoreAnnotations) {
        return imports
                .anyMatch(this, o -> ignoreAnnotations.contains(o, axiom));
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom axiom) {
        @SuppressWarnings("unchecked")
        Stream<OWLAxiom> filter = (Stream<OWLAxiom>) getAxioms(
                axiom.getAxiomType()).stream().filter(
                ax -> ax.equalsIgnoreAnnotations(axiom));
        if (containsAxiom(axiom)) {
            filter = Stream.concat(filter, Stream.of(axiom));
        }
        return filter.collect(Collectors.toSet());
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom axiom) {
        if (containsAxiom(axiom)) {
            return true;
        }
        return getAxioms(axiom.getAxiomType()).stream().anyMatch(
                (ax) -> ax.equalsIgnoreAnnotations(axiom));
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom axiom,
            Imports imports) {
        return imports.collect(this, (o) -> o.getAxiomsIgnoreAnnotations(axiom)
                .stream());
    }

    @Override
    public boolean containsClassInSignature(IRI iri, Imports imports) {
        return imports.anyMatch(this, (o) -> o.containsClassInSignature(iri));
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri, Imports imports) {
        return imports.anyMatch(this,
                (o) -> o.containsObjectPropertyInSignature(iri));
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri, Imports imports) {
        return imports.anyMatch(this,
                (o) -> o.containsDataPropertyInSignature(iri));
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri,
            Imports imports) {
        boolean result = imports.anyMatch(this,
                (o) -> o.containsAnnotationPropertyInSignature(iri));
        if (result) {
            return result;
        }
        return checkOntologyAnnotations(df.getOWLAnnotationProperty(iri));
    }

    private boolean checkOntologyAnnotations(OWLAnnotationProperty p) {
        Iterable<OWLAnnotation> anns = ints.getOntologyAnnotations(false);
        return Iterables.any(anns, (ann) -> ann.getProperty().equals(p));
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri, Imports imports) {
        return imports.anyMatch(this,
                (o) -> o.containsIndividualInSignature(iri));
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri, Imports imports) {
        return imports
                .anyMatch(this, (o) -> o.containsDatatypeInSignature(iri));
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri) {
        Set<OWLEntity> result = createSet(6);
        if (containsClassInSignature(iri)) {
            result.add(df.getOWLClass(iri));
        }
        if (containsObjectPropertyInSignature(iri)) {
            result.add(df.getOWLObjectProperty(iri));
        }
        if (containsDataPropertyInSignature(iri)) {
            result.add(df.getOWLDataProperty(iri));
        }
        if (containsIndividualInSignature(iri)) {
            result.add(df.getOWLNamedIndividual(iri));
        }
        if (containsDatatypeInSignature(iri)) {
            result.add(df.getOWLDatatype(iri));
        }
        if (containsAnnotationPropertyInSignature(iri)) {
            result.add(df.getOWLAnnotationProperty(iri));
        }
        return result;
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, Imports imports) {
        return imports.collect(this, o -> o.getEntitiesInSignature(iri)
                .stream());
    }

    @Override
    public Set<IRI> getPunnedIRIs(Imports imports) {
        Set<IRI> punned = new HashSet<>();
        Set<IRI> test = new HashSet<>();
        imports.stream(this).forEach(
                (o) -> {
                    Stream.of(o.getClassesInSignature(),
                            o.getDataPropertiesInSignature(),
                            o.getObjectPropertiesInSignature(),
                            o.getAnnotationPropertiesInSignature(),
                            o.getDatatypesInSignature(),
                            o.getIndividualsInSignature())
                            .flatMap(s -> s.stream())
                            .filter(e -> test.add(e.getIRI()))
                            .forEach(e -> punned.add(e.getIRI()));
                });
        if (punned.isEmpty()) {
            return Collections.emptySet();
        }
        return punned;
    }

    @Override
    public boolean
            containsReference(@Nonnull OWLEntity entity, Imports imports) {
        return imports.anyMatch(this, o -> o.containsReference(entity));
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity) {
        return ints.isDeclared(owlEntity);
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity, Imports imports) {
        return imports.anyMatch(this, o -> o.isDeclared(owlEntity));
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity) {
        return entityReferenceChecker.containsReference(owlEntity);
    }

    private final OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker();

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity,
            Imports imports) {
        return imports.anyMatch(this,
                o -> o.containsEntityInSignature(owlEntity));
    }

    @Override
    public boolean containsEntityInSignature(IRI entityIRI, Imports imports) {
        return imports.anyMatch(this,
                o -> o.containsEntityInSignature(entityIRI));
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
    public Set<OWLEntity> getSignature(Imports imports) {
        return imports.collect(this, o -> o.getSignature().stream());
    }

    @Nonnull
    private static <T> Set<T> asSet(Iterable<T> i) {
        List<T> list = new ArrayList<>();
        Iterables.addAll(list, i);
        return CollectionFactory.copy(list);
    }

    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return asSet(ints.get(OWLAnonymousIndividual.class, OWLAxiom.class)
                .get().keySet());
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return asSet(ints.get(OWLClass.class, OWLAxiom.class).get().keySet());
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return asSet(ints.get(OWLDataProperty.class, OWLAxiom.class).get()
                .keySet());
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return asSet(ints.get(OWLObjectProperty.class, OWLAxiom.class).get()
                .keySet());
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return asSet(ints.get(OWLNamedIndividual.class, OWLAxiom.class).get()
                .keySet());
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return asSet(ints.get(OWLDatatype.class, OWLAxiom.class).get().keySet());
    }

    @Override
    public Set<OWLClass> getClassesInSignature(Imports imports) {
        return imports.collect(this, o -> o.getClassesInSignature().stream());
    }

    @Override
    public Set<OWLObjectProperty>
            getObjectPropertiesInSignature(Imports imports) {
        return imports.collect(this, o -> o.getObjectPropertiesInSignature()
                .stream());
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(Imports imports) {
        return imports.collect(this, o -> o.getDataPropertiesInSignature()
                .stream());
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(Imports imports) {
        return imports.collect(this, o -> o.getIndividualsInSignature()
                .stream());
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals() {
        return asSet(ints.get(OWLAnonymousIndividual.class, OWLAxiom.class)
                .get().keySet());
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(
            Imports imports) {
        return imports.collect(this, o -> o.getReferencedAnonymousIndividuals()
                .stream());
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(Imports imports) {
        return imports.collect(this, o -> o.getDatatypesInSignature().stream());
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        Set<OWLAnnotationProperty> props = createSet();
        Iterables.addAll(
                props,
                ints.get(OWLAnnotationProperty.class, OWLAxiom.class,
                        Navigation.IN_SUB_POSITION).get().keySet());
        ints.getOntologyAnnotations(false).forEach(
                a -> props.add(a.getProperty()));
        return props;
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(
            Imports imports) {
        return imports.collect(this, o -> o
                .getAnnotationPropertiesInSignature().stream());
    }

    @Nonnull
    @Override
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return asSet(ints.getImportsDeclarations(true));
    }

    @Override
    public Set<IRI> getDirectImportsDocuments() {
        Set<IRI> result = createSet();
        ints.getImportsDeclarations(false).forEach(i -> result.add(i.getIRI()));
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
    public void accept(@Nonnull OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(@Nonnull OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLNamedObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(@Nonnull OWLObjectVisitorEx<O> visitor) {
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

        OWLEntityReferenceChecker() {}

        private static final long serialVersionUID = 40000L;
        private boolean ref;

        public boolean containsReference(@Nonnull OWLEntity entity) {
            ref = false;
            entity.accept(this);
            return ref;
        }

        @Override
        public void visit(@Nonnull OWLClass cls) {
            ref = OWLImmutableOntologyImpl.this.ints
                    .containsClassInSignature(cls);
        }

        @Override
        public void visit(@Nonnull OWLDatatype datatype) {
            ref = OWLImmutableOntologyImpl.this.ints
                    .containsDatatypeInSignature(datatype);
        }

        @Override
        public void visit(@Nonnull OWLNamedIndividual individual) {
            ref = OWLImmutableOntologyImpl.this.ints
                    .containsIndividualInSignature(individual);
        }

        @Override
        public void visit(@Nonnull OWLDataProperty property) {
            ref = OWLImmutableOntologyImpl.this.ints
                    .containsDataPropertyInSignature(property);
        }

        @Override
        public void visit(@Nonnull OWLObjectProperty property) {
            ref = OWLImmutableOntologyImpl.this.ints
                    .containsObjectPropertyInSignature(property);
        }

        @Override
        public void visit(@Nonnull OWLAnnotationProperty property) {
            ref = OWLImmutableOntologyImpl.this.ints
                    .containsAnnotationPropertyInSignature(property);
        }
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass cls) {
        return asSet(ints.get(OWLClass.class, OWLClassAxiom.class).get()
                .getValues(cls));
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass cls, Imports imports) {
        return imports.collect(this, o -> o.getAxioms(cls).stream());
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
            OWLObjectPropertyExpression property) {
        Set<OWLObjectPropertyAxiom> result = createSet(50);
        result.addAll(getAsymmetricObjectPropertyAxioms(property));
        result.addAll(getReflexiveObjectPropertyAxioms(property));
        result.addAll(getSymmetricObjectPropertyAxioms(property));
        result.addAll(getIrreflexiveObjectPropertyAxioms(property));
        result.addAll(getTransitiveObjectPropertyAxioms(property));
        result.addAll(getInverseFunctionalObjectPropertyAxioms(property));
        result.addAll(getFunctionalObjectPropertyAxioms(property));
        result.addAll(getInverseObjectPropertyAxioms(property));
        result.addAll(getObjectPropertyDomainAxioms(property));
        result.addAll(getEquivalentObjectPropertiesAxioms(property));
        result.addAll(getDisjointObjectPropertiesAxioms(property));
        result.addAll(getObjectPropertyRangeAxioms(property));
        result.addAll(getObjectSubPropertyAxiomsForSubProperty(property));
        return result;
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
            OWLObjectPropertyExpression property, Imports imports) {
        return imports.collect(this, o -> o.getAxioms(property).stream());
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty property) {
        Set<OWLAnnotationAxiom> result = createSet();
        for (OWLSubAnnotationPropertyOfAxiom ax : getAxioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            if (ax.getSubProperty().equals(property)) {
                result.add(ax);
            }
        }
        for (OWLAnnotationPropertyRangeAxiom ax : getAxioms(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            if (ax.getProperty().equals(property)) {
                result.add(ax);
            }
        }
        for (OWLAnnotationPropertyDomainAxiom ax : getAxioms(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            if (ax.getProperty().equals(property)) {
                result.add(ax);
            }
        }
        return result;
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty property,
            Imports imports) {
        return imports.collect(this, o -> o.getAxioms(property).stream());
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty property) {
        Set<OWLDataPropertyAxiom> result = createSet();
        result.addAll(getDataPropertyDomainAxioms(property));
        result.addAll(getEquivalentDataPropertiesAxioms(property));
        result.addAll(getDisjointDataPropertiesAxioms(property));
        result.addAll(getDataPropertyRangeAxioms(property));
        result.addAll(getFunctionalDataPropertyAxioms(property));
        result.addAll(getDataSubPropertyAxiomsForSubProperty(property));
        return result;
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty property,
            Imports imports) {
        return imports.collect(this, o -> o.getAxioms(property).stream());
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual individual) {
        Set<OWLIndividualAxiom> result = createSet();
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
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual individual,
            Imports imports) {
        return imports.collect(this, o -> o.getAxioms(individual).stream());
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype) {
        return getDatatypeDefinitions(datatype);
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype,
            Imports imports) {
        return imports.collect(this, o -> o.getDatatypeDefinitions(datatype)
                .stream());
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlEntity) {
        if (owlEntity instanceof OWLEntity) {
            return asSet(ints.getReferencingAxioms((OWLEntity) owlEntity));
        } else if (owlEntity instanceof OWLAnonymousIndividual) {
            return asSet(ints.get(OWLAnonymousIndividual.class, OWLAxiom.class)
                    .get().getValues((OWLAnonymousIndividual) owlEntity));
        } else if (owlEntity instanceof IRI) {
            Set<OWLAxiom> axioms = new HashSet<>();
            // axioms referring entities with this IRI, data property assertions
            // with IRI as subject, annotations with IRI as subject or object.
            getEntitiesInSignature((IRI) owlEntity).forEach(
                    e -> axioms.addAll(getReferencingAxioms(e)));
            for (OWLDataPropertyAssertionAxiom ax : getAxioms(AxiomType.DATA_PROPERTY_ASSERTION)) {
                if (OWL2Datatype.XSD_ANY_URI.matches(ax.getObject()
                        .getDatatype())) {
                    if (ax.getObject().getLiteral()
                            .equals(owlEntity.toString())) {
                        axioms.add(ax);
                    }
                }
            }
            for (OWLAnnotationAssertionAxiom ax : getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                if (ax.getSubject().equals(owlEntity)) {
                    axioms.add(ax);
                } else if (ax.getValue().asLiteral().isPresent()) {
                    Optional<OWLLiteral> lit = ax.getValue().asLiteral();
                    if (OWL2Datatype.XSD_ANY_URI.matches(lit.get()
                            .getDatatype())) {
                        if (lit.get().getLiteral().equals(owlEntity.toString())) {
                            axioms.add(ax);
                        }
                    }
                }
            }
            return axioms;
        } else if (owlEntity instanceof OWLLiteral) {
            Set<OWLAxiom> axioms = new HashSet<>();
            for (OWLDataPropertyAssertionAxiom ax : getAxioms(AxiomType.DATA_PROPERTY_ASSERTION)) {
                if (OWL2Datatype.XSD_ANY_URI.matches(ax.getObject()
                        .getDatatype())) {
                    if (ax.getObject().equals(owlEntity)) {
                        axioms.add(ax);
                    }
                }
            }
            for (OWLAnnotationAssertionAxiom ax : getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                if (owlEntity.equals(ax.getValue().asLiteral().orElse(null))) {
                    axioms.add(ax);
                }
            }
            return axioms;
        }
        return Collections.emptySet();
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlEntity,
            Imports imports) {
        return imports.collect(this, o -> o.getReferencingAxioms(owlEntity)
                .stream());
    }

    // OWLAxiomIndex
    @Override
    public <A extends OWLAxiom> Set<A> getAxioms(@Nonnull Class<A> type,
            @Nonnull OWLObject entity, Imports imports,
            Navigation forSubPosition) {
        return imports.collect(
                this,
                o -> o.getAxioms(type, entity.getClass(), entity,
                        forSubPosition).stream());
    }

    @Override
    public <A extends OWLAxiom> Set<A> getAxioms(@Nonnull Class<A> type,
            @Nonnull OWLObject entity, Navigation forSubPosition) {
        return getAxioms(type, entity.getClass(), entity, forSubPosition);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends OWLAxiom> Set<A> getAxioms(@Nonnull Class<A> type,
            @Nonnull Class<? extends OWLObject> explicitClass,
            @Nonnull OWLObject entity, @Nonnull Navigation forSubPosition) {
        Optional<MapPointer<OWLObject, A>> optional = ints.get(
                (Class<OWLObject>) explicitClass, type, forSubPosition);
        if (optional.isPresent()) {
            return asSet(optional.get().getValues(entity));
        }
        Set<A> toReturn = new HashSet<>();
        for (A ax : getAxioms(AxiomType.getTypeForClass(type))) {
            if (ax.getSignature().contains(entity)) {
                toReturn.add(ax);
            }
        }
        return toReturn;
    }

    @Override
    public <A extends OWLAxiom> Set<A> getAxioms(@Nonnull Class<A> type,
            @Nonnull Class<? extends OWLObject> explicitClass,
            @Nonnull OWLObject entity, @Nonnull Imports imports,
            @Nonnull Navigation forSubPosition) {
        return imports.collect(this,
                o -> o.getAxioms(type, explicitClass, entity, forSubPosition)
                        .stream());
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(
            @Nonnull OWLAxiomSearchFilter filter, @Nonnull Object key,
            Imports imports) {
        return (Set<T>) imports.collect(this, o -> o.filterAxioms(filter, key)
                .stream());
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(
            @Nonnull OWLAxiomSearchFilter filter, @Nonnull Object key) {
        return (Collection<T>) ints.filterAxioms(filter, key);
    }

    @Override
    public boolean contains(@Nonnull OWLAxiomSearchFilter filter,
            @Nonnull Object key) {
        return ints.contains(filter, key);
    }

    @Override
    public boolean contains(@Nonnull OWLAxiomSearchFilter filter,
            @Nonnull Object key, Imports imports) {
        return imports.anyMatch(this, o -> o.contains(filter, key));
    }

    @Override
    public void saveOntology() throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this);
    }

    @Override
    public void saveOntology(IRI documentIRI)
            throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, documentIRI);
    }

    @Override
    public void saveOntology(OutputStream outputStream)
            throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, outputStream);
    }

    @Override
    public void saveOntology(OWLDocumentFormat ontologyFormat)
            throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat);
    }

    @Override
    public void saveOntology(OWLDocumentFormat ontologyFormat, IRI documentIRI)
            throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat, documentIRI);
    }

    @Override
    public void saveOntology(OWLDocumentFormat ontologyFormat,
            OutputStream outputStream) throws OWLOntologyStorageException {
        getOWLOntologyManager()
                .saveOntology(this, ontologyFormat, outputStream);
    }

    @Override
    public void saveOntology(OWLOntologyDocumentTarget documentTarget)
            throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, documentTarget);
    }

    @Override
    public void saveOntology(OWLDocumentFormat ontologyFormat,
            OWLOntologyDocumentTarget documentTarget)
            throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat,
                documentTarget);
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri) {
        return ints.containsDatatypeInSignature(iri);
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
    public boolean containsClassInSignature(IRI iri) {
        return ints.containsClassInSignature(iri);
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri) {
        return ints.containsObjectPropertyInSignature(iri);
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri) {
        return ints.containsDataPropertyInSignature(iri);
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri) {
        return ints.containsAnnotationPropertyInSignature(iri);
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri) {
        return ints.containsIndividualInSignature(iri);
    }

    @Override
    public boolean containsReference(OWLEntity entity) {
        return ints.containsReference(entity);
    }
}
