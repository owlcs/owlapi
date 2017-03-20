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

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.empty;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPrimitive;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.OWLAPIStreamUtils;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLImmutableOntologyImpl extends OWLAxiomIndexImpl
    implements OWLOntology, Serializable {

    @Nullable
    protected OWLOntologyManager manager;
    protected OWLDataFactory df;
    protected OWLOntologyID ontologyID;
    private final OWLEntityReferenceChecker entityReferenceChecker =
        new OWLEntityReferenceChecker();
    private PrefixManager prefixManager = new PrefixManagerImpl();

    /**
     * @param manager ontology manager
     * @param ontologyID ontology id
     */
    public OWLImmutableOntologyImpl(OWLOntologyManager manager, OWLOntologyID ontologyID) {
        this.manager = checkNotNull(manager, "manager cannot be null");
        this.ontologyID = checkNotNull(ontologyID, "ontologyID cannot be null");
        df = manager.getOWLDataFactory();
    }

    @Override
    public PrefixManager getPrefixManager() {
        return prefixManager;
    }

    @Override
    public void setPrefixManager(PrefixManager prefixManager) {
        this.prefixManager = verifyNotNull(prefixManager, "prefixManager cannot be null");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Ontology(").append(ontologyID).append(") [Axioms: ").append(ints.getAxiomCount())
            .append(" Logical Axioms: ").append(ints.getLogicalAxiomCount())
            .append("] First 20 axioms: {");
        ints.getAxioms().limit(20).forEach(a -> sb.append(a).append(' '));
        sb.append('}');
        return sb.toString();
    }

    @Override
    public OWLOntologyManager getOWLOntologyManager() {
        return verifyNotNull(manager, () -> "Manager on ontology " + getOntologyID()
            + " is null; the ontology is no longer associated to a manager. Ensure the ontology is not being used after being removed from its manager.");
    }

    @Override
    public void setOWLOntologyManager(@Nullable OWLOntologyManager manager) {
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
    public boolean containsAxiom(OWLAxiom axiom) {
        return Internals.contains(ints.getAxiomsByType(), axiom.getAxiomType(), axiom);
    }

    @Override
    public Stream<OWLAxiom> axioms() {
        return ints.getAxioms();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends OWLAxiom> Stream<T> axioms(AxiomType<T> axiomType) {
        return (Stream<T>) ints.getAxiomsByType().getValues(axiomType);
    }

    @Override
    public Stream<OWLLogicalAxiom> logicalAxioms() {
        return ints.getLogicalAxioms();
    }

    @Override
    public int getLogicalAxiomCount() {
        return ints.getLogicalAxiomCount();
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, Imports imports) {
        return imports.stream(this).mapToInt(o -> o.getAxiomCount(axiomType)).sum();
    }

    @Override
    public int getAxiomCount(Imports imports) {
        return imports.stream(this).mapToInt(o -> o.getAxiomCount()).sum();
    }

    @Override
    public Stream<OWLAxiom> tboxAxioms(Imports imports) {
        return AxiomType.TBoxAxiomTypes.stream().flatMap(t -> axioms(t, imports));
    }

    @Override
    public Stream<OWLAxiom> aboxAxioms(Imports imports) {
        return AxiomType.ABoxAxiomTypes.stream().flatMap(t -> axioms(t, imports));
    }

    @Override
    public Stream<OWLAxiom> rboxAxioms(Imports imports) {
        return AxiomType.RBoxAxiomTypes.stream().flatMap(t -> axioms(t, imports));
    }

    @Override
    public int getLogicalAxiomCount(Imports imports) {
        return imports.stream(this).mapToInt(o -> o.getLogicalAxiomCount()).sum();
    }

    @Override
    public Stream<OWLAnnotation> annotations() {
        return ints.getOntologyAnnotations();
    }

    @Override
    public Stream<OWLClassAxiom> generalClassAxioms() {
        return ints.getGeneralClassAxioms();
    }

    @Override
    public boolean containsAxiom(OWLAxiom axiom, Imports imports,
        AxiomAnnotations ignoreAnnotations) {
        return imports.stream(this).anyMatch(o -> ignoreAnnotations.contains(o, axiom));
    }

    @Override
    public Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom axiom) {
        return axioms(axiom.getAxiomType()).map(x -> (OWLAxiom) x)
            .filter(ax -> ax.equalsIgnoreAnnotations(axiom));
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom) {
        if (containsAxiom(axiom)) {
            return true;
        }
        return axioms(axiom.getAxiomType()).anyMatch(ax -> ax.equalsIgnoreAnnotations(axiom));
    }

    @Override
    public Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom axiom, Imports imports) {
        return imports.stream(this).flatMap(o -> o.axiomsIgnoreAnnotations(axiom));
    }

    @Override
    public boolean containsClassInSignature(IRI iri, Imports imports) {
        return imports.stream(this).anyMatch(o -> o.containsClassInSignature(iri));
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri, Imports imports) {
        return imports.stream(this).anyMatch(o -> o.containsObjectPropertyInSignature(iri));
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri, Imports imports) {
        return imports.stream(this).anyMatch(o -> o.containsDataPropertyInSignature(iri));
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri, Imports imports) {
        boolean result =
            imports.stream(this).anyMatch(o -> o.containsAnnotationPropertyInSignature(iri));
        if (result) {
            return result;
        }
        return checkOntologyAnnotations(df.getOWLAnnotationProperty(iri));
    }

    private boolean checkOntologyAnnotations(OWLAnnotationProperty p) {
        return ints.getOntologyAnnotations().anyMatch(ann -> ann.getProperty().equals(p));
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri, Imports imports) {
        return imports.stream(this).anyMatch(o -> o.containsIndividualInSignature(iri));
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri, Imports imports) {
        return imports.stream(this).anyMatch(o -> o.containsDatatypeInSignature(iri));
    }

    @Override
    public Stream<OWLEntity> entitiesInSignature(IRI iri) {
        Predicate<? super OWLEntity> matchIRI = c -> c.getIRI().equals(iri);
        return Stream.of(classesInSignature().filter(matchIRI),
            objectPropertiesInSignature().filter(matchIRI),
            dataPropertiesInSignature().filter(matchIRI), individualsInSignature().filter(matchIRI),
            datatypesInSignature().filter(matchIRI),
            annotationPropertiesInSignature().filter(matchIRI)).flatMap(x -> x);
    }

    private static void add(Set<IRI> punned, Set<IRI> test, OWLEntity e) {
        if (!test.add(e.getIRI())) {
            punned.add(e.getIRI());
        }
    }

    @Override
    public Set<IRI> getPunnedIRIs(Imports includeImportsClosure) {
        Set<IRI> punned = new HashSet<>();
        Set<IRI> test = new HashSet<>();
        if (includeImportsClosure == INCLUDED) {
            importsClosure().forEach(o -> {
                o.classesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
                o.dataPropertiesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
                o.objectPropertiesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
                o.annotationPropertiesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
                o.datatypesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
                o.individualsInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
            });
            if (punned.isEmpty()) {
                return Collections.emptySet();
            }
            return punned;
        } else {
            classesInSignature(EXCLUDED).forEach(e -> test.add(e.getIRI()));
            dataPropertiesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
            objectPropertiesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
            annotationPropertiesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
            datatypesInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
            individualsInSignature(EXCLUDED).forEach(e -> add(punned, test, e));
            if (punned.isEmpty()) {
                return Collections.emptySet();
            }
            return punned;
        }
    }

    @Override
    public boolean containsReference(OWLEntity entity, Imports includeImportsClosure) {
        if (includeImportsClosure == EXCLUDED) {
            return ints.containsReference(entity);
        }
        return importsClosure().anyMatch(o -> o.containsReference(entity, EXCLUDED));
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity) {
        return ints.isDeclared(owlEntity);
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        return entityReferenceChecker.containsReference(owlEntity);
    }

    @Override
    public Stream<OWLEntity> signature() {
        return Stream
            .of(classesInSignature(), objectPropertiesInSignature(), dataPropertiesInSignature(),
                individualsInSignature(), datatypesInSignature(), annotationPropertiesInSignature())
            .flatMap(x -> x);
    }

    @Override
    public Stream<OWLAnonymousIndividual> anonymousIndividuals() {
        return ints.get(OWLAnonymousIndividual.class, OWLAxiom.class).get().keySet().stream();
    }

    @Override
    public Stream<OWLClass> classesInSignature() {
        return ints.get(OWLClass.class, OWLAxiom.class).get().keySet().stream();
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature() {
        return ints.get(OWLDataProperty.class, OWLAxiom.class).get().keySet().stream();
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature() {
        return ints.get(OWLObjectProperty.class, OWLAxiom.class).get().keySet().stream();
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature() {
        return ints.get(OWLNamedIndividual.class, OWLAxiom.class).get().keySet().stream();
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature() {
        return ints.get(OWLDatatype.class, OWLAxiom.class).get().keySet().stream();
    }

    @Override
    public Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals() {
        return ints.get(OWLAnonymousIndividual.class, OWLAxiom.class).get().keySet().stream();
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature() {
        return Stream
            .concat(
                ints.get(OWLAnnotationProperty.class, OWLAxiom.class, Navigation.IN_SUB_POSITION)
                    .get().keySet().stream(),
                ints.getOntologyAnnotations().map(a -> a.getProperty()))
            .distinct().sorted();
    }

    @Override
    public Stream<OWLImportsDeclaration> importsDeclarations() {
        return ints.getImportsDeclarations();
    }

    @Override
    public Stream<IRI> directImportsDocuments() {
        return ints.getImportsDeclarations().map(OWLImportsDeclaration::getIRI);
    }

    @Override
    public Stream<OWLOntology> imports() {
        return getOWLOntologyManager().imports(this);
    }

    @Override
    public Stream<OWLOntology> directImports() {
        return getOWLOntologyManager().directImports(this);
    }

    @Override
    public Stream<OWLOntology> importsClosure() {
        return getOWLOntologyManager().importsClosure(this);
    }

    private class OWLEntityReferenceChecker implements OWLEntityVisitor, Serializable {

        private boolean ref;

        OWLEntityReferenceChecker() {}

        public boolean containsReference(OWLEntity entity) {
            ref = false;
            entity.accept(this);
            return ref;
        }

        @Override
        public void visit(OWLClass cls) {
            ref = OWLImmutableOntologyImpl.this.ints.containsClassInSignature(cls);
        }

        @Override
        public void visit(OWLDatatype datatype) {
            ref = OWLImmutableOntologyImpl.this.ints.containsDatatypeInSignature(datatype);
        }

        @Override
        public void visit(OWLNamedIndividual individual) {
            ref = OWLImmutableOntologyImpl.this.ints.containsIndividualInSignature(individual);
        }

        @Override
        public void visit(OWLDataProperty property) {
            ref = OWLImmutableOntologyImpl.this.ints.containsDataPropertyInSignature(property);
        }

        @Override
        public void visit(OWLObjectProperty property) {
            ref = OWLImmutableOntologyImpl.this.ints.containsObjectPropertyInSignature(property);
        }

        @Override
        public void visit(OWLAnnotationProperty property) {
            ref =
                OWLImmutableOntologyImpl.this.ints.containsAnnotationPropertyInSignature(property);
        }
    }

    @Override
    public Stream<OWLClassAxiom> axioms(OWLClass cls) {
        return ints.get(OWLClass.class, OWLClassAxiom.class).get().values(cls, OWLClassAxiom.class);
    }

    @Override
    public Stream<OWLObjectPropertyAxiom> axioms(OWLObjectPropertyExpression property) {
        return Stream.of(asymmetricObjectPropertyAxioms(property),
            reflexiveObjectPropertyAxioms(property), symmetricObjectPropertyAxioms(property),
            irreflexiveObjectPropertyAxioms(property), transitiveObjectPropertyAxioms(property),
            inverseFunctionalObjectPropertyAxioms(property),
            functionalObjectPropertyAxioms(property), inverseObjectPropertyAxioms(property),
            objectPropertyDomainAxioms(property), equivalentObjectPropertiesAxioms(property),
            disjointObjectPropertiesAxioms(property), objectPropertyRangeAxioms(property),
            objectSubPropertyAxiomsForSubProperty(property)).flatMap(x -> x);
    }

    @Override
    public Stream<OWLDataPropertyAxiom> axioms(OWLDataProperty property) {
        return Stream.of(dataPropertyDomainAxioms(property),
            equivalentDataPropertiesAxioms(property), disjointDataPropertiesAxioms(property),
            dataPropertyRangeAxioms(property), functionalDataPropertyAxioms(property),
            dataSubPropertyAxiomsForSubProperty(property)).flatMap(x -> x);
    }

    @Override
    public Stream<OWLIndividualAxiom> axioms(OWLIndividual individual) {
        return sortOptionally(
            Stream
                .of(classAssertionAxioms(individual), objectPropertyAssertionAxioms(individual),
                    dataPropertyAssertionAxioms(individual),
                    negativeObjectPropertyAssertionAxioms(individual),
                    negativeDataPropertyAssertionAxioms(individual),
                    sameIndividualAxioms(individual), differentIndividualAxioms(individual))
                .flatMap(x -> x),
            OWLIndividualAxiom.class).stream();
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> axioms(OWLDatatype datatype) {
        return datatypeDefinitions(datatype);
    }

    @Override
    public Stream<OWLAxiom> referencingAxioms(OWLPrimitive owlEntity) {
        if (owlEntity instanceof OWLEntity) {
            return ints.getReferencingAxioms((OWLEntity) owlEntity);
        } else if (owlEntity instanceof OWLAnonymousIndividual) {
            return ints.get(OWLAnonymousIndividual.class, OWLAxiom.class).get()
                .values((OWLAnonymousIndividual) owlEntity, OWLAxiom.class);
        } else if (owlEntity.isIRI()) {
            Set<OWLAxiom> axioms = new HashSet<>();
            String iriString = owlEntity.toString();
            // axioms referring entities with this IRI, data property assertions
            // with IRI as subject, annotations with IRI as subject or object.
            entitiesInSignature((IRI) owlEntity)
                .forEach(e -> OWLAPIStreamUtils.add(axioms, referencingAxioms(e)));
            axioms(AxiomType.DATA_PROPERTY_ASSERTION)
                .filter(ax -> OWL2Datatype.XSD_ANY_URI.matches(ax.getObject().getDatatype()))
                .filter(ax -> ax.getObject().getLiteral().equals(iriString)).forEach(axioms::add);
            axioms(AxiomType.ANNOTATION_ASSERTION)
                .forEach(ax -> examineAssertion(owlEntity, axioms, ax));
            return axioms.stream();
        } else if (owlEntity instanceof OWLLiteral) {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms(AxiomType.DATA_PROPERTY_ASSERTION).filter(ax -> ax.getObject().equals(owlEntity))
                .forEach(axioms::add);
            axioms(AxiomType.ANNOTATION_ASSERTION)
                .filter(ax -> owlEntity.equals(ax.getValue().asLiteral().orElse(null)))
                .forEach(axioms::add);
            AxiomType.AXIOM_TYPES.stream().flatMap(t -> axioms(t))
                .filter(ax -> hasLiteralInAnnotations(owlEntity, ax)).forEach(axioms::add);
            return axioms.stream();
        }
        return empty();
    }

    protected boolean hasLiteralInAnnotations(OWLPrimitive owlEntity, OWLAxiom ax) {
        return ax.annotations().anyMatch(a -> a.getValue().equals(owlEntity));
    }

    protected void examineAssertion(OWLPrimitive owlEntity, Set<OWLAxiom> axioms,
        OWLAnnotationAssertionAxiom ax) {
        if (ax.getSubject().equals(owlEntity)) {
            axioms.add(ax);
        } else {
            ax.getValue().asLiteral().ifPresent(lit -> {
                if (OWL2Datatype.XSD_ANY_URI.matches(lit.getDatatype())
                    && lit.getLiteral().equals(owlEntity.toString())) {
                    axioms.add(ax);
                }
            });
        }
    }

    // OWLAxiomIndex
    @Override
    @SuppressWarnings("unchecked")
    public <A extends OWLAxiom> Stream<A> axioms(Class<A> type,
        Class<? extends OWLObject> explicitClass, OWLObject entity, Navigation forSubPosition) {
        Optional<MapPointer<OWLObject, A>> optional =
            ints.get((Class<OWLObject>) explicitClass, type, forSubPosition);
        if (optional.isPresent()) {
            return optional.get().values(entity, type);
        }
        if (!(entity instanceof OWLEntity)) {
            return empty();
        }
        return axioms(AxiomType.getTypeForClass(type))
            .filter(a -> a.containsEntityInSignature((OWLEntity) entity));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OWLAxiom> Stream<T> axioms(OWLAxiomSearchFilter filter, Object key,
        Imports imports) {
        return imports.stream(this).flatMap(o -> (Stream<T>) o.axioms(filter, key));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OWLAxiom> Stream<T> axioms(OWLAxiomSearchFilter filter, Object key) {
        Collection<T> c = (Collection<T>) ints.filterAxioms(filter, key);
        return c.stream();
    }

    @Override
    public boolean contains(OWLAxiomSearchFilter filter, Object key) {
        return ints.contains(filter, key);
    }

    @Override
    public boolean contains(OWLAxiomSearchFilter filter, Object key, Imports imports) {
        return imports.stream(this).anyMatch(o -> o.contains(filter, key));
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri) {
        return ints.containsDatatypeInSignature(iri);
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
