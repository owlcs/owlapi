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

import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.util.CollectionFactory.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLImmutableOntologyImpl extends OWLAxiomIndexImpl implements OWLOntology, Serializable {

    private static final long serialVersionUID = 40000L;
    @Nonnull protected OWLOntologyManager manager;
    @Nonnull protected OWLOntologyID ontologyID;

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
    public OWLImmutableOntologyImpl(@Nonnull OWLOntologyManager manager, @Nonnull OWLOntologyID ontologyID) {
        this.manager = checkNotNull(manager, "manager cannot be null");
        this.ontologyID = checkNotNull(ontologyID, "ontologyID cannot be null");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ontology(");
        sb.append(ontologyID);
        sb.append(") [Axioms: ");
        int axiomCount = ints.getAxiomCount();
        sb.append(axiomCount);
        sb.append(" Logical Axioms: ");
        sb.append(ints.getLogicalAxiomCount());
        sb.append("] First 20 axioms: {");
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
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, boolean includeImportsClosure) {
        return getAxiomCount(axiomType, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public int getAxiomCount(boolean includeImportsClosure) {
        return getAxiomCount(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getAxiomCount(axiomType);
        }
        int result = 0;
        for (OWLOntology ont : getImportsClosure()) {
            result += ont.getAxiomCount(axiomType);
        }
        return result;
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom axiom) {
        return Internals.contains(ints.getAxiomsByType(), axiom.getAxiomType(), axiom);
    }

    @Override
    public int getAxiomCount() {
        return getAxiomCount(EXCLUDED);
    }

    @Override
    public int getAxiomCount(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
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
    public Set<OWLAxiom> getAxioms(boolean b) {
        return getAxioms(Imports.fromBoolean(b));
    }

    @Override
    public Set<OWLAxiom> getAxioms(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return asSet(ints.getAxioms());
        }
        Set<OWLAxiom> axioms = new HashSet<>();
        for (OWLOntology o : getImportsClosure()) {
            axioms.addAll(o.getAxioms());
        }
        return axioms;
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        return (Set<T>) asSet(ints.getAxiomsByType().getValues(axiomType));
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, boolean includeImportsClosure) {
        return getAxioms(axiomType, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getAxioms(axiomType);
        }
        Set<T> toReturn = createLinkedSet();
        for (OWLOntology o : getImportsClosure()) {
            toReturn.addAll(o.getAxioms(axiomType));
        }
        return toReturn;
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getTBoxAxioms(Imports includeImportsClosure) {
        Set<OWLAxiom> toReturn = new HashSet<>();
        for (AxiomType<?> type : AxiomType.TBoxAxiomTypes) {
            assert type != null;
            toReturn.addAll(getAxioms(type, includeImportsClosure));
        }
        return toReturn;
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getABoxAxioms(Imports includeImportsClosure) {
        Set<OWLAxiom> toReturn = new HashSet<>();
        for (AxiomType<?> type : AxiomType.ABoxAxiomTypes) {
            assert type != null;
            toReturn.addAll(getAxioms(type, includeImportsClosure));
        }
        return toReturn;
    }

    @Nonnull
    @Override
    public Set<OWLAxiom> getRBoxAxioms(Imports includeImportsClosure) {
        Set<OWLAxiom> toReturn = new HashSet<>();
        for (AxiomType<?> type : AxiomType.RBoxAxiomTypes) {
            assert type != null;
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
    public Set<OWLLogicalAxiom> getLogicalAxioms(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.getLogicalAxioms();
        }
        Set<OWLLogicalAxiom> result = createLinkedSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getLogicalAxioms(EXCLUDED));
        }
        return result;
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(boolean includeImportsClosure) {
        return getLogicalAxioms(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public int getLogicalAxiomCount() {
        return ints.getLogicalAxiomCount();
    }

    @Override
    public int getLogicalAxiomCount(boolean includeImportsClosure) {
        return getLogicalAxiomCount(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public int getLogicalAxiomCount(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.getLogicalAxiomCount();
        }
        int total = 0;
        for (OWLOntology o : getImportsClosure()) {
            total += o.getLogicalAxiomCount(EXCLUDED);
        }
        return total;
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
    public boolean containsAxiom(@Nonnull OWLAxiom axiom, boolean includeImportsClosure) {
        return containsAxiom(axiom, Imports.fromBoolean(includeImportsClosure),
            AxiomAnnotations.CONSIDER_AXIOM_ANNOTATIONS);
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom axiom, Imports includeImportsClosure,
        AxiomAnnotations ignoreAnnotations) {
        if (includeImportsClosure == EXCLUDED) {
            if (ignoreAnnotations == AxiomAnnotations.CONSIDER_AXIOM_ANNOTATIONS) {
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

    @Override
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom axiom) {
        for (OWLAxiom ax : ints.getAxiomsByType().getValues(axiom.getAxiomType())) {
            if (ax.equalsIgnoreAnnotations(axiom)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom axiom, boolean importsIncluded) {
        return containsAxiom(axiom, Imports.fromBoolean(importsIncluded), AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom axiom) {
        Set<OWLAxiom> result = createLinkedSet();
        if (containsAxiom(axiom)) {
            result.add(axiom);
        }
        for (OWLAxiom ax : ints.getAxiomsByType().getValues(axiom.getAxiomType())) {
            if (ax.equalsIgnoreAnnotations(axiom)) {
                result.add(ax);
            }
        }
        return result;
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom axiom, boolean includeImportsClosure) {
        return getAxiomsIgnoreAnnotations(axiom, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom axiom, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getAxiomsIgnoreAnnotations(axiom);
        }
        Set<OWLAxiom> result = createLinkedSet();
        for (OWLOntology ont : getImportsClosure()) {
            result.addAll(ont.getAxiomsIgnoreAnnotations(axiom, EXCLUDED));
        }
        return result;
    }

    @Override
    public boolean containsClassInSignature(IRI owlClassIRI, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.containsClassInSignature(owlClassIRI);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.containsClassInSignature(owlClassIRI, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI owlObjectPropertyIRI, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.containsObjectPropertyInSignature(owlObjectPropertyIRI);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.containsObjectPropertyInSignature(owlObjectPropertyIRI, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI owlDataPropertyIRI, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.containsDataPropertyInSignature(owlDataPropertyIRI);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.containsDataPropertyInSignature(owlDataPropertyIRI, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI owlAnnotationPropertyIRI, Imports includeImportsClosure) {
        OWLAnnotationProperty p = manager.getOWLDataFactory().getOWLAnnotationProperty(owlAnnotationPropertyIRI);
        if (includeImportsClosure == INCLUDED) {
            for (OWLOntology o : getImportsClosure()) {
                if (o.containsAnnotationPropertyInSignature(owlAnnotationPropertyIRI, EXCLUDED)) {
                    return true;
                }
            }
        } else {
            if (ints.containsAnnotationPropertyInSignature(owlAnnotationPropertyIRI)) {
                return true;
            }
        }
        return checkOntologyAnnotations(p);
    }

    private boolean checkOntologyAnnotations(OWLAnnotationProperty owlAnnotationProperty) {
        for (OWLAnnotation anno : ints.getOntologyAnnotations(false)) {
            if (anno.getProperty().equals(owlAnnotationProperty)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsIndividualInSignature(IRI owlIndividualIRI, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.containsIndividualInSignature(owlIndividualIRI);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.containsIndividualInSignature(owlIndividualIRI, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsDatatypeInSignature(IRI owlDatatypeIRI, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.containsDatatypeInSignature(owlDatatypeIRI);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.containsDatatypeInSignature(owlDatatypeIRI, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI entityIRI) {
        return getEntitiesInSignature(entityIRI, EXCLUDED);
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, Imports includeImportsClosure) {
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
            result.add(manager.getOWLDataFactory().getOWLAnnotationProperty(iri));
        }
        return result;
    }

    @Override
    public Set<IRI> getPunnedIRIs(Imports includeImportsClosure) {
        Set<IRI> punned = new HashSet<>();
        Set<IRI> test = new HashSet<>();
        if (includeImportsClosure == INCLUDED) {
            for (OWLOntology o : getImportsClosure()) {
                for (OWLEntity e : o.getClassesInSignature(EXCLUDED)) {
                    if (!test.add(e.getIRI())) {
                        punned.add(e.getIRI());
                    }
                }
                for (OWLEntity e : o.getDataPropertiesInSignature(EXCLUDED)) {
                    if (!test.add(e.getIRI())) {
                        punned.add(e.getIRI());
                    }
                }
                for (OWLEntity e : o.getObjectPropertiesInSignature(EXCLUDED)) {
                    if (!test.add(e.getIRI())) {
                        punned.add(e.getIRI());
                    }
                }
                for (OWLEntity e : o.getAnnotationPropertiesInSignature(EXCLUDED)) {
                    if (!test.add(e.getIRI())) {
                        punned.add(e.getIRI());
                    }
                }
                for (OWLEntity e : o.getDatatypesInSignature(EXCLUDED)) {
                    if (!test.add(e.getIRI())) {
                        punned.add(e.getIRI());
                    }
                }
                for (OWLEntity e : o.getIndividualsInSignature(EXCLUDED)) {
                    if (!test.add(e.getIRI())) {
                        punned.add(e.getIRI());
                    }
                }
            }
            if (punned.isEmpty()) {
                return CollectionFactory.<IRI> emptySet();
            }
            return punned;
        } else {
            for (OWLEntity e : getClassesInSignature(EXCLUDED)) {
                test.add(e.getIRI());
            }
            for (OWLEntity e : getDataPropertiesInSignature(EXCLUDED)) {
                if (!test.add(e.getIRI())) {
                    punned.add(e.getIRI());
                }
            }
            for (OWLEntity e : getObjectPropertiesInSignature(EXCLUDED)) {
                if (!test.add(e.getIRI())) {
                    punned.add(e.getIRI());
                }
            }
            for (OWLEntity e : getAnnotationPropertiesInSignature(EXCLUDED)) {
                if (!test.add(e.getIRI())) {
                    punned.add(e.getIRI());
                }
            }
            for (OWLEntity e : getDatatypesInSignature(EXCLUDED)) {
                if (!test.add(e.getIRI())) {
                    punned.add(e.getIRI());
                }
            }
            for (OWLEntity e : getIndividualsInSignature(EXCLUDED)) {
                if (!test.add(e.getIRI())) {
                    punned.add(e.getIRI());
                }
            }
            if (punned.isEmpty()) {
                return CollectionFactory.<IRI> emptySet();
            }
            return punned;
        }
    }

    @Override
    public boolean containsReference(@Nonnull OWLEntity entity, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
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
    public boolean isDeclared(OWLEntity owlEntity) {
        return ints.isDeclared(owlEntity);
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity, Imports includeImportsClosure) {
        if (isDeclared(owlEntity)) {
            return true;
        }
        if (includeImportsClosure == INCLUDED) {
            for (OWLOntology ont : manager.getImportsClosure(this)) {
                if (!ont.equals(this) && ont.isDeclared(owlEntity)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity) {
        return containsEntityInSignature(owlEntity, EXCLUDED);
    }

    private final OWLEntityReferenceChecker entityReferenceChecker = new OWLEntityReferenceChecker();

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity, Imports includeImportsClosure) {
        if (includeImportsClosure != INCLUDED) {
            return entityReferenceChecker.containsReference(owlEntity);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.containsEntityInSignature(owlEntity, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEntityInSignature(IRI entityIRI, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
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
        Set<OWLEntity> entities = createLinkedSet();
        entities.addAll(getClassesInSignature());
        entities.addAll(getObjectPropertiesInSignature());
        entities.addAll(getDataPropertiesInSignature());
        entities.addAll(getIndividualsInSignature());
        entities.addAll(getDatatypesInSignature());
        entities.addAll(getAnnotationPropertiesInSignature(EXCLUDED));
        return entities;
    }

    @Override
    public Set<OWLEntity> getSignature(Imports includeImportsClosure) {
        Set<OWLEntity> entities = getSignature();
        if (includeImportsClosure == INCLUDED) {
            for (OWLOntology ont : getImportsClosure()) {
                entities.addAll(ont.getSignature(EXCLUDED));
            }
        }
        return entities;
    }

    @Nonnull
    private static <T> Set<T> asSet(Iterable<T> i) {
        if (i instanceof Set) {
            // in this case we can use a list for the defensive copy
            List<T> list = new ArrayList<>();
            Iterables.addAll(list, i);
            return CollectionFactory.getCopyOnRequestSetFromImmutableCollection(list);
        }
        // if the input is not a set, we need to make sure there are no
        // duplicates
        Set<T> set = new HashSet<>();
        Iterables.addAll(set, i);
        return set;
    }

    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return asSet(ints.get(OWLAnonymousIndividual.class, OWLAxiom.class).get().keySet());
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return asSet(ints.get(OWLClass.class, OWLAxiom.class).get().keySet());
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return asSet(ints.get(OWLDataProperty.class, OWLAxiom.class).get().keySet());
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return asSet(ints.get(OWLObjectProperty.class, OWLAxiom.class).get().keySet());
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return asSet(ints.get(OWLNamedIndividual.class, OWLAxiom.class).get().keySet());
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return asSet(ints.get(OWLDatatype.class, OWLAxiom.class).get().keySet());
    }

    @Override
    public Set<OWLClass> getClassesInSignature(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getClassesInSignature();
        }
        Set<OWLClass> results = createLinkedSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getClassesInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getObjectPropertiesInSignature();
        }
        Set<OWLObjectProperty> results = createLinkedSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getObjectPropertiesInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getDataPropertiesInSignature();
        }
        Set<OWLDataProperty> results = createLinkedSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getDataPropertiesInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getIndividualsInSignature();
        }
        Set<OWLNamedIndividual> results = createLinkedSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getIndividualsInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return asSet(ints.get(OWLAnonymousIndividual.class, OWLAxiom.class).get().keySet());
        }
        Set<OWLAnonymousIndividual> result = createLinkedSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getReferencedAnonymousIndividuals(EXCLUDED));
        }
        return result;
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getDatatypesInSignature();
        }
        Set<OWLDatatype> results = createLinkedSet();
        for (OWLOntology ont : getImportsClosure()) {
            results.addAll(ont.getDatatypesInSignature());
        }
        return results;
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(Imports includeImportsClosure) {
        Set<OWLAnnotationProperty> props = createLinkedSet();
        if (includeImportsClosure == EXCLUDED) {
            Iterables.addAll(props, ints.get(OWLAnnotationProperty.class, OWLAxiom.class, Navigation.IN_SUB_POSITION)
                .get().keySet());
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

    @Nonnull
    @Override
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return (Set<OWLImportsDeclaration>) ints.getImportsDeclarations(true);
    }

    @Override
    public Set<IRI> getDirectImportsDocuments() {
        Set<IRI> result = createLinkedSet();
        for (OWLImportsDeclaration importsDeclaration : ints.getImportsDeclarations(false)) {
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

    private class OWLEntityReferenceChecker implements OWLEntityVisitor, Serializable {

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
            ref = OWLImmutableOntologyImpl.this.ints.containsClassInSignature(cls);
        }

        @Override
        public void visit(@Nonnull OWLDatatype datatype) {
            ref = OWLImmutableOntologyImpl.this.ints.containsDatatypeInSignature(datatype);
        }

        @Override
        public void visit(@Nonnull OWLNamedIndividual individual) {
            ref = OWLImmutableOntologyImpl.this.ints.containsIndividualInSignature(individual);
        }

        @Override
        public void visit(@Nonnull OWLDataProperty property) {
            ref = OWLImmutableOntologyImpl.this.ints.containsDataPropertyInSignature(property);
        }

        @Override
        public void visit(@Nonnull OWLObjectProperty property) {
            ref = OWLImmutableOntologyImpl.this.ints.containsObjectPropertyInSignature(property);
        }

        @Override
        public void visit(@Nonnull OWLAnnotationProperty property) {
            ref = OWLImmutableOntologyImpl.this.ints.containsAnnotationPropertyInSignature(property);
        }
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass cls, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return asSet(ints.get(OWLClass.class, OWLClassAxiom.class).get().getValues(cls));
        }
        Set<OWLClassAxiom> result = createLinkedSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(cls, EXCLUDED));
        }
        return result;
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(OWLObjectPropertyExpression property, Imports includeImportsClosure) {
        Set<OWLObjectPropertyAxiom> result = createSet(50);
        if (includeImportsClosure == EXCLUDED) {
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
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(property, EXCLUDED));
            }
        }
        return result;
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty property, Imports includeImportsClosure) {
        Set<OWLAnnotationAxiom> result = createLinkedSet();
        if (includeImportsClosure == EXCLUDED) {
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
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(property, EXCLUDED));
            }
        }
        return result;
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty property, Imports includeImportsClosure) {
        Set<OWLDataPropertyAxiom> result = createLinkedSet();
        if (includeImportsClosure == EXCLUDED) {
            result.addAll(getDataPropertyDomainAxioms(property));
            result.addAll(getEquivalentDataPropertiesAxioms(property));
            result.addAll(getDisjointDataPropertiesAxioms(property));
            result.addAll(getDataPropertyRangeAxioms(property));
            result.addAll(getFunctionalDataPropertyAxioms(property));
            result.addAll(getDataSubPropertyAxiomsForSubProperty(property));
        } else {
            for (OWLOntology o : getImportsClosure()) {
                result.addAll(o.getAxioms(property, EXCLUDED));
            }
        }
        return result;
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual individual, Imports includeImportsClosure) {
        Set<OWLIndividualAxiom> result = createLinkedSet();
        if (includeImportsClosure == EXCLUDED) {
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
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return getDatatypeDefinitions(datatype);
        }
        Set<OWLDatatypeDefinitionAxiom> result = createLinkedSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(datatype, EXCLUDED));
        }
        return result;
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlEntity) {
        return getReferencingAxioms(owlEntity, EXCLUDED);
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlEntity, boolean includeImports) {
        return getReferencingAxioms(owlEntity, Imports.fromBoolean(includeImports));
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlEntity, Imports includeImportsClosure) {
        if (owlEntity instanceof OWLEntity) {
            if (includeImportsClosure == EXCLUDED) {
                return asSet(ints.getReferencingAxioms((OWLEntity) owlEntity));
            }
            Set<OWLAxiom> result = createLinkedSet();
            for (OWLOntology ont : getImportsClosure()) {
                result.addAll(ont.getReferencingAxioms(owlEntity, EXCLUDED));
            }
            return result;
        } else if (owlEntity instanceof OWLAnonymousIndividual) {
            return asSet(ints.get(OWLAnonymousIndividual.class, OWLAxiom.class).get().getValues(
                (OWLAnonymousIndividual) owlEntity));
        } else if (owlEntity instanceof IRI) {
            Set<OWLAxiom> axioms = new HashSet<>();
            // axioms referring entities with this IRI, data property assertions
            // with IRI as subject, annotations with IRI as subject or object.
            Set<OWLEntity> entities = getEntitiesInSignature((IRI) owlEntity, includeImportsClosure);
            for (OWLEntity e : entities) {
                assert e != null;
                axioms.addAll(getReferencingAxioms(e, includeImportsClosure));
            }
            for (OWLDataPropertyAssertionAxiom ax : getAxioms(AxiomType.DATA_PROPERTY_ASSERTION)) {
                if (ax.getObject().getDatatype().getIRI().equals(OWL2Datatype.XSD_ANY_URI.getIRI())) {
                    if (ax.getObject().getLiteral().equals(owlEntity.toString())) {
                        axioms.add(ax);
                    }
                }
            }
            for (OWLAnnotationAssertionAxiom ax : getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                if (ax.getSubject().equals(owlEntity)) {
                    axioms.add(ax);
                } else if (ax.getValue().asLiteral().isPresent()) {
                    Optional<OWLLiteral> lit = ax.getValue().asLiteral();
                    if (lit.get().getDatatype().getIRI().equals(OWL2Datatype.XSD_ANY_URI.getIRI())) {
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
                if (ax.getObject().equals(owlEntity)) {
                    axioms.add(ax);
                }
            }
            for (OWLAnnotationAssertionAxiom ax : getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
                if (owlEntity.equals(ax.getValue().asLiteral().orNull())) {
                    axioms.add(ax);
                }
            }
            for (AxiomType<?> t : AxiomType.AXIOM_TYPES) {
                for (OWLAxiom ax : getAxioms(t)) {
                    if (hasLiteralInAnnotations(owlEntity, ax)) {
                        axioms.add(ax);
                    }
                }
            }
            return axioms;
        }
        return CollectionFactory.emptySet();
    }

    protected boolean hasLiteralInAnnotations(OWLPrimitive owlEntity, OWLAxiom ax) {
        for (OWLAnnotation ann : ax.getAnnotations()) {
            if (ann.getValue().equals(owlEntity)) {
                return true;
            }
        }
        return false;
    }

    // OWLAxiomIndex
    @Override
    public <A extends OWLAxiom> Set<A> getAxioms(@Nonnull Class<A> type, @Nonnull OWLObject entity,
        Imports includeImports, Navigation forSubPosition) {
        if (includeImports == EXCLUDED) {
            return getAxioms(type, entity.getClass(), entity, EXCLUDED, forSubPosition);
        }
        Set<A> result = createLinkedSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(type, entity, EXCLUDED, forSubPosition));
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends OWLAxiom> Set<A> getAxioms(@Nonnull Class<A> type,
        @Nonnull Class<? extends OWLObject> explicitClass, @Nonnull OWLObject entity, @Nonnull Imports includeImports,
        @Nonnull Navigation forSubPosition) {
        if (includeImports == EXCLUDED) {
            Optional<MapPointer<OWLObject, A>> optional = ints.get((Class<OWLObject>) explicitClass, type,
                forSubPosition);
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
        Set<A> result = createLinkedSet();
        for (OWLOntology o : getImportsClosure()) {
            result.addAll(o.getAxioms(type, entity, EXCLUDED, forSubPosition));
        }
        return result;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(@Nonnull OWLAxiomSearchFilter filter, @Nonnull Object key,
        Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return (Collection<T>) ints.filterAxioms(filter, key);
        }
        // iterating over the import closure; using a set because there might be
        // duplicate axioms
        Set<T> toReturn = new HashSet<>();
        for (OWLOntology o : getImportsClosure()) {
            toReturn.addAll((Collection<T>) o.filterAxioms(filter, key, EXCLUDED));
        }
        return toReturn;
    }

    @Override
    public boolean contains(@Nonnull OWLAxiomSearchFilter filter, @Nonnull Object key, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.contains(filter, key);
        }
        for (OWLOntology o : getImportsClosure()) {
            if (o.contains(filter, key, EXCLUDED)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass cls) {
        return getAxioms(cls, false);
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(OWLObjectPropertyExpression property) {
        return getAxioms(property, EXCLUDED);
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty property) {
        return getAxioms(property, EXCLUDED);
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual individual) {
        return getAxioms(individual, EXCLUDED);
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty property) {
        return getAxioms(property, EXCLUDED);
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype) {
        return getAxioms(datatype, EXCLUDED);
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass cls, boolean includeImportsClosure) {
        return getAxioms(cls, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(OWLObjectPropertyExpression property, boolean includeImportsClosure) {
        return getAxioms(property, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty property, boolean includeImportsClosure) {
        return getAxioms(property, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual individual, boolean includeImportsClosure) {
        return getAxioms(individual, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty property, boolean includeImportsClosure) {
        return getAxioms(property, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype, boolean includeImportsClosure) {
        return getAxioms(datatype, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public void saveOntology() throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this);
    }

    @Override
    public void saveOntology(IRI documentIRI) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, documentIRI);
    }

    @Override
    public void saveOntology(OutputStream outputStream) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, outputStream);
    }

    @Override
    public void saveOntology(OWLDocumentFormat ontologyFormat) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat);
    }

    @Override
    public void saveOntology(OWLDocumentFormat ontologyFormat, IRI documentIRI) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat, documentIRI);
    }

    @Override
    public void saveOntology(OWLDocumentFormat ontologyFormat, OutputStream outputStream)
        throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat, outputStream);
    }

    @Override
    public void saveOntology(OWLOntologyDocumentTarget documentTarget) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, documentTarget);
    }

    @Override
    public void saveOntology(OWLDocumentFormat ontologyFormat, OWLOntologyDocumentTarget documentTarget)
        throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat, documentTarget);
    }

    @Override
    public boolean containsDatatypeInSignature(IRI owlDatatypeIRI) {
        return containsDatatypeInSignature(owlDatatypeIRI, EXCLUDED);
    }

    @Override
    public boolean containsEntityInSignature(IRI entityIRI) {
        return containsEntityInSignature(entityIRI, EXCLUDED);
    }

    @Override
    public boolean containsClassInSignature(IRI owlClassIRI) {
        return containsClassInSignature(owlClassIRI, EXCLUDED);
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI owlObjectPropertyIRI) {
        return containsObjectPropertyInSignature(owlObjectPropertyIRI, EXCLUDED);
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI owlDataPropertyIRI) {
        return containsDataPropertyInSignature(owlDataPropertyIRI, EXCLUDED);
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI owlAnnotationPropertyIRI) {
        return containsAnnotationPropertyInSignature(owlAnnotationPropertyIRI, EXCLUDED);
    }

    @Override
    public boolean containsIndividualInSignature(IRI owlIndividualIRI) {
        return containsIndividualInSignature(owlIndividualIRI, EXCLUDED);
    }

    @Override
    public boolean containsReference(OWLEntity entity) {
        return containsReference(entity, EXCLUDED);
    }

    @Override
    public Set<OWLClass> getClassesInSignature(boolean includeImportsClosure) {
        return getClassesInSignature(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean includeImportsClosure) {
        return getObjectPropertiesInSignature(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(boolean includeImportsClosure) {
        return getDataPropertiesInSignature(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(boolean includeImportsClosure) {
        return getIndividualsInSignature(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(boolean includeImportsClosure) {
        return getReferencedAnonymousIndividuals(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(boolean includeImportsClosure) {
        return getDatatypesInSignature(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(boolean includeImportsClosure) {
        return getAnnotationPropertiesInSignature(Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity, boolean includeImportsClosure) {
        return containsEntityInSignature(owlEntity, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public boolean containsEntityInSignature(IRI entityIRI, boolean includeImportsClosure) {
        return containsEntityInSignature(entityIRI, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public boolean containsClassInSignature(IRI owlClassIRI, boolean includeImportsClosure) {
        return containsClassInSignature(owlClassIRI, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI owlObjectPropertyIRI, boolean includeImportsClosure) {
        return containsObjectPropertyInSignature(owlObjectPropertyIRI, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI owlDataPropertyIRI, boolean includeImportsClosure) {
        return containsDataPropertyInSignature(owlDataPropertyIRI, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI owlAnnotationPropertyIRI, boolean includeImportsClosure) {
        return containsAnnotationPropertyInSignature(owlAnnotationPropertyIRI, Imports.fromBoolean(
            includeImportsClosure));
    }

    @Override
    public boolean containsDatatypeInSignature(IRI owlDatatypeIRI, boolean includeImportsClosure) {
        return containsDatatypeInSignature(owlDatatypeIRI, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public boolean containsIndividualInSignature(IRI owlIndividualIRI, boolean includeImportsClosure) {
        return containsIndividualInSignature(owlIndividualIRI, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, boolean includeImportsClosure) {
        return getEntitiesInSignature(iri, Imports.fromBoolean(includeImportsClosure));
    }

    @Override
    public boolean containsReference(OWLEntity entity, boolean includeImportsClosure) {
        return containsReference(entity, Imports.fromBoolean(includeImportsClosure));
    }
}
