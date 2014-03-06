package org.semanticweb.owlapi.model;

import java.util.Set;

import javax.annotation.Nonnull;

public interface OWLSignature extends HasGetEntitiesInSignature,
        HasClassesInSignature, HasObjectPropertiesInSignature,
        HasDataPropertiesInSignature, HasDatatypesInSignature,
        HasIndividualsInSignature, HasContainsEntityInSignature {

    /**
     * Gets the classes that are in the signature of this ontology.
     * 
     * @see #getSignature()
     * @return A set of named classes, which are referenced by any axiom in this
     *         ontology. The set that is returned is a copy - it will not be
     *         updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    @Override
    @Nonnull
    Set<OWLClass> getClassesInSignature();

    /**
     * Gets the classes that are in the signature of this ontology, and possibly
     * the imports closure of this ontology.
     * 
     * @param includeImportsClosure
     *        Specifies whether classes should be drawn from the signature of
     *        just this ontology or the imports closure of this ontology. If
     *        {@code true} then the set of classes returned will correspond to
     *        the union of the classes in the signatures of the ontologies in
     *        the imports closure of this ontology. If {@code false} then the
     *        set of classes returned will correspond to the classes that are in
     *        the signature of this just this ontology.
     * @return A set of classes that are in the signature of this ontology and
     *         possibly the union of the signatures of the ontologies in the
     *         imports closure of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     */
    @Nonnull
    Set<OWLClass> getClassesInSignature(boolean includeImportsClosure);

    /**
     * Gets the object properties that are in the signature of this ontology.
     * 
     * @see #getSignature()
     * @return A set of object properties which are in the signature of this
     *         ontology. The set that is returned is a copy - it will not be
     *         updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    @Override
    @Nonnull
    Set<OWLObjectProperty> getObjectPropertiesInSignature();

    /**
     * Gets the object properties that are in the signature of this ontology,
     * and possibly the imports closure of this ontology.
     * 
     * @param includeImportsClosure
     *        Specifies whether object properties should be drawn from the
     *        signature of just this ontology or the imports closure of this
     *        ontology. If {@code true} then the set of object properties
     *        returned will correspond to the union of the object properties in
     *        the signatures of the ontologies in the imports closure of this
     *        ontology. If {@code false} then the set of object properties
     *        returned will correspond to the object properties that are in the
     *        signature of this just this ontology.
     * @return A set of object properties that are in the signature of this
     *         ontology and possibly the union of the signatures of the
     *         ontologies in the imports closure of this ontology. The set that
     *         is returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    @Nonnull
    Set<OWLObjectProperty> getObjectPropertiesInSignature(
            boolean includeImportsClosure);

    /**
     * Gets the data properties that are in the signature of this ontology.
     * 
     * @see #getSignature()
     * @return A set of data properties, which are in the signature of this
     *         ontology. The set that is returned is a copy - it will not be
     *         updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    @Override
    @Nonnull
    Set<OWLDataProperty> getDataPropertiesInSignature();

    /**
     * Gets the data properties that are in the signature of this ontology, and
     * possibly the imports closure of this ontology.
     * 
     * @param includeImportsClosure
     *        Specifies whether data properties should be drawn from the
     *        signature of just this ontology or the imports closure of this
     *        ontology. If {@code true} then the set of data properties returned
     *        will correspond to the union of the data properties in the
     *        signatures of the ontologies in the imports closure of this
     *        ontology. If {@code false} then the set of data properties
     *        returned will correspond to the data properties that are in the
     *        signature of this just this ontology.
     * @return A set of data properties that are in the signature of this
     *         ontology and possibly the union of the signatures of the
     *         ontologies in the imports closure of this ontology. The set that
     *         is returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    @Nonnull
    Set<OWLDataProperty> getDataPropertiesInSignature(
            boolean includeImportsClosure);

    /**
     * Gets the individuals that are in the signature of this ontology.
     * 
     * @see #getSignature()
     * @return A set of individuals, which are in the signature of this
     *         ontology. The set that is returned is a copy - it will not be
     *         updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    @Override
    @Nonnull
    Set<OWLNamedIndividual> getIndividualsInSignature();

    /**
     * Gets the individuals that are in the signature of this ontology, and
     * possibly the imports closure of this ontology.
     * 
     * @see #getSignature()
     * @param includeImportsClosure
     *        Specifies whether individuals should be drawn from the signature
     *        of just this ontology or the imports closure of this ontology. If
     *        {@code true} then the set of individuals returned will correspond
     *        to the union of the individuals in the signatures of the
     *        ontologies in the imports closure of this ontology. If
     *        {@code false} then the set of individuals returned will correspond
     *        to the individuals that are in the signature of this just this
     *        ontology.
     * @return A set of individuals that are in the signature of this ontology
     *         and possibly the union of the signatures of the ontologies in the
     *         imports closure of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     */
    @Nonnull
    Set<OWLNamedIndividual> getIndividualsInSignature(
            boolean includeImportsClosure);

    /**
     * Gets the referenced anonymous individuals.
     * 
     * @return The set of referenced anonymous individuals
     */
    @Nonnull
    Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals();

    /**
     * Gets the datatypes that are in the signature of this ontology.
     * 
     * @see #getSignature()
     * @return A set of datatypes, which are in the signature of this ontology.
     *         The set that is returned is a copy - it will not be updated if
     *         the ontology changes. It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    @Override
    @Nonnull
    Set<OWLDatatype> getDatatypesInSignature();

    /**
     * Gets the datatypes that are in the signature of this ontology, and
     * possibly the imports closure of this ontology.
     * 
     * @see #getSignature()
     * @param includeImportsClosure
     *        Specifies whether datatypes should be drawn from the signature of
     *        just this ontology or the imports closure of this ontology. If
     *        {@code true} then the set of datatypes returned will correspond to
     *        the union of the datatypes in the signatures of the ontologies in
     *        the imports closure of this ontology. If {@code false} then the
     *        set of datatypes returned will correspond to the datatypes that
     *        are in the signature of this just this ontology.
     * @return A set of datatypes that are in the signature of this ontology and
     *         possibly the union of the signatures of the ontologies in the
     *         imports closure of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     */
    @Nonnull
    Set<OWLDatatype> getDatatypesInSignature(boolean includeImportsClosure);

    /**
     * Gets the annotation properties that are in the signature of this
     * ontology.
     * 
     * @see #getSignature()
     * @return A set of annotation properties, which are in the signature of
     *         this ontology. The set that is returned is a copy - it will not
     *         be updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature();

    /**
     * Determines if the signature of the ontology contains the specified
     * entity.
     * 
     * @param owlEntity
     *        The entity
     * @return {@code true} if the signature of this ontology contains
     *         {@code owlEntity}, otherwise {@code false}.
     */
    @Override
    boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity);

    /**
     * Determines if the signature of this ontology, and possibly the signature
     * of any of the ontologies in the imports closure of this ontology,
     * contains the specified entity.
     * 
     * @param owlEntity
     *        The entity
     * @param includeImportsClosure
     *        Specifies whether the imports closure should be examined for the
     *        entity reference or not.
     * @return {@code true} if the ontology contains a reference to the
     *         specified entity, otherwise {@code false} The set that is
     *         returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity,
            boolean includeImportsClosure);

    /**
     * Determines if the signature of this ontology contains a class, object
     * property, data property, named individual, annotation property or
     * datatype with the specified IRI.
     * 
     * @param entityIRI
     *        The IRI to test for.
     * @return {@code true} if the signature of this ontology contains a class,
     *         object property, data property, named individual, annotation
     *         property or datatype with the specified IRI.
     */
    boolean containsEntityInSignature(@Nonnull IRI entityIRI);

    /**
     * Determines if the signature of this ontology and possibly its imports
     * closure contains a class, object property, data property, named
     * individual, annotation property or datatype with the specified IRI.
     * 
     * @param entityIRI
     *        The IRI to test for.
     * @param includeImportsClosure
     *        Specifies whether the imports closure of this ontology should be
     *        examined or not.
     * @return If {@code includeImportsClosure=true} then returns {@code true}
     *         if the signature of this ontology or the signature of an ontology
     *         in the imports closure of this ontology contains a class, object
     *         property, data property, named individual, annotation property or
     *         datatype with the specified IRI. If
     *         {@code includeImportsClosure=false} then returns {@code true} if
     *         the signature of this ontology contains a class, object property,
     *         data property, named individual, annotation property or datatype
     *         with the specified IRI.
     */
    boolean containsEntityInSignature(@Nonnull IRI entityIRI,
            boolean includeImportsClosure);

    // ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Access by IRI
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Determines if the signature of this ontology contains an OWLClass with
     * the specified IRI.
     * 
     * @param owlClassIRI
     *        The IRI of the OWLClass to check for.
     * @return {@code true} if the signature of this ontology contains an
     *         OWLClass that has {@code owlClassIRI} as its IRI, otherwise
     *         {@code false}.
     */
    boolean containsClassInSignature(@Nonnull IRI owlClassIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature
     * of one of the ontologies in the imports closure of this ontology,
     * contains an OWLClass that has the specified IRI.
     * 
     * @param owlClassIRI
     *        The IRI of the class to check for
     * @param includeImportsClosure
     *        {@code true} if the signature of the ontologies in the imports
     *        closure of this ontology should be checked, {@code false} if just
     *        the signature of this ontology should be chekced.
     * @return If {@code includeImportsClosure=true} then returns {@code true}
     *         if there is an OWLClass with {@code owlClassIRI} as its IRI in
     *         the signature of at least one ontology in the imports clousre of
     *         this ontology and {@code false} if this is not the case. If
     *         {@code includeImportsClosure=false} then returns {@code true} if
     *         the signature of this ontology contains an OWLClass that has
     *         {@code owlClassIRI} as its IRI and {@code false} if the signature
     *         of this ontology does not contain a class with
     *         {@code owlClassIRI} as its IRI.
     */
    boolean containsClassInSignature(@Nonnull IRI owlClassIRI,
            boolean includeImportsClosure);

    /**
     * Determines if the signature of this ontology contains an
     * OWLObjectProperty with the specified IRI.
     * 
     * @param owlObjectPropertyIRI
     *        The IRI of the OWLObjectProperty to check for.
     * @return {@code true} if the signature of this ontology contains an
     *         OWLObjectProperty that has {@code owlObjectPropertyIRI} as its
     *         IRI, otherwise {@code false}.
     */
            boolean
            containsObjectPropertyInSignature(@Nonnull IRI owlObjectPropertyIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature
     * of one of the ontologies in the imports closure of this ontology,
     * contains an OWLObjectProperty that has the specified IRI.
     * 
     * @param owlObjectPropertyIRI
     *        The IRI of the OWLObjectProperty to check for
     * @param includeImportsClosure
     *        {@code true} if the signature of the ontologies in the imports
     *        closure of this ontology should be checked, {@code false} if just
     *        the signature of this ontology should be chekced.
     * @return If {@code includeImportsClosure=true} then returns {@code true}
     *         if there is an OWLObjectProperty with
     *         {@code owlObjectPropertyIRI} as its IRI in the signature of at
     *         least one ontology in the imports clousre of this ontology and
     *         {@code false} if this is not the case. If
     *         {@code includeImportsClosure=false} then returns {@code true} if
     *         the signature of this ontology contains an OWLObjectProperty that
     *         has {@code owlObjectPropertyIRI} as its IRI and {@code false} if
     *         the signature of this ontology does not contain a class with
     *         {@code owlObjectPropertyIRI} as its IRI.
     */
    boolean containsObjectPropertyInSignature(
            @Nonnull IRI owlObjectPropertyIRI, boolean includeImportsClosure);

    /**
     * Determines if the signature of this ontology contains an OWLDataProperty
     * with the specified IRI.
     * 
     * @param owlDataPropertyIRI
     *        The IRI of the OWLDataProperty to check for.
     * @return {@code true} if the signature of this ontology contains an
     *         OWLDataProperty that has {@code owlDataPropertyIRI} as its IRI,
     *         otherwise {@code false}.
     */
    boolean containsDataPropertyInSignature(@Nonnull IRI owlDataPropertyIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature
     * of one of the ontologies in the imports closure of this ontology,
     * contains an OWLDataProperty that has the specified IRI.
     * 
     * @param owlDataPropertyIRI
     *        The IRI of the OWLDataProperty to check for
     * @param includeImportsClosure
     *        {@code true} if the signature of the ontologies in the imports
     *        closure of this ontology should be checked, {@code false} if just
     *        the signature of this ontology should be chekced.
     * @return If {@code includeImportsClosure=true} then returns {@code true}
     *         if there is an OWLDataProperty with {@code owlDataPropertyIRI} as
     *         its IRI in the signature of at least one ontology in the imports
     *         clousre of this ontology and {@code false} if this is not the
     *         case. If {@code includeImportsClosure=false} then returns
     *         {@code true} if the signature of this ontology contains an
     *         OWLDataProperty that has {@code owlDataPropertyIRI} as its IRI
     *         and {@code false} if the signature of this ontology does not
     *         contain a class with {@code owlDataPropertyIRI} as its IRI.
     */
    boolean containsDataPropertyInSignature(@Nonnull IRI owlDataPropertyIRI,
            boolean includeImportsClosure);

    /**
     * Determines if the signature of this ontology contains an
     * OWLAnnotationProperty with the specified IRI.
     * 
     * @param owlAnnotationPropertyIRI
     *        The IRI of the OWLAnnotationProperty to check for.
     * @return {@code true} if the signature of this ontology contains an
     *         OWLAnnotationProperty that has {@code owlAnnotationPropertyIRI}
     *         as its IRI, otherwise {@code false}.
     */
    boolean containsAnnotationPropertyInSignature(
            @Nonnull IRI owlAnnotationPropertyIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature
     * of one of the ontologies in the imports closure of this ontology,
     * contains an OWLAnnotationProperty that has the specified IRI.
     * 
     * @param owlAnnotationPropertyIRI
     *        The IRI of the OWLAnnotationProperty to check for
     * @param includeImportsClosure
     *        {@code true} if the signature of the ontologies in the imports
     *        closure of this ontology should be checked, {@code false} if just
     *        the signature of this ontology should be chekced.
     * @return If {@code includeImportsClosure=true} then returns {@code true}
     *         if there is an OWLAnnotationProperty with
     *         {@code owlAnnotationPropertyIRI} as its IRI in the signature of
     *         at least one ontology in the imports clousre of this ontology and
     *         {@code false} if this is not the case. If
     *         {@code includeImportsClosure=false} then returns {@code true} if
     *         the signature of this ontology contains an OWLAnnotationProperty
     *         that has {@code owlAnnotationPropertyIRI} as its IRI and
     *         {@code false} if the signature of this ontology does not contain
     *         a class with {@code owlAnnotationPropertyIRI} as its IRI.
     */
    boolean
            containsAnnotationPropertyInSignature(
                    @Nonnull IRI owlAnnotationPropertyIRI,
                    boolean includeImportsClosure);

    /**
     * Determines if the signature of this ontology contains an
     * OWLNamedIndividual with the specified IRI.
     * 
     * @param owlIndividualIRI
     *        The IRI of the OWLNamedIndividual to check for.
     * @return {@code true} if the signature of this ontology contains an
     *         OWLNamedIndividual that has {@code owlIndividualIRI} as its IRI,
     *         otherwise {@code false}.
     */
    boolean containsIndividualInSignature(@Nonnull IRI owlIndividualIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature
     * of one of the ontologies in the imports closure of this ontology,
     * contains an OWLNamedIndividual that has the specified IRI.
     * 
     * @param owlIndividualIRI
     *        The IRI of the OWLNamedIndividual to check for
     * @param includeImportsClosure
     *        {@code true} if the signature of the ontologies in the imports
     *        closure of this ontology should be checked, {@code false} if just
     *        the signature of this ontology should be chekced.
     * @return If {@code includeImportsClosure=true} then returns {@code true}
     *         if there is an OWLNamedIndividual with {@code owlIndividualIRI}
     *         as its IRI in the signature of at least one ontology in the
     *         imports closure of this ontology and {@code false} if this is not
     *         the case. If {@code includeImportsClosure=false} then returns
     *         {@code true} if the signature of this ontology contains an
     *         OWLNamedIndividual that has {@code owlIndividualIRI} as its IRI
     *         and {@code false} if the signature of this ontology does not
     *         contain a class with {@code owlIndividualIRI} as its IRI.
     */
    boolean containsIndividualInSignature(@Nonnull IRI owlIndividualIRI,
            boolean includeImportsClosure);

    /**
     * Determines if the signature of this ontology contains an OWLDatatype with
     * the specified IRI.
     * 
     * @param owlDatatypeIRI
     *        The IRI of the OWLDatatype to check for.
     * @return {@code true} if the signature of this ontology contains an
     *         OWLDatatype that has {@code owlDatatypeIRI} as its IRI, otherwise
     *         {@code false}.
     */
    boolean containsDatatypeInSignature(@Nonnull IRI owlDatatypeIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature
     * of one of the ontologies in the imports closure of this ontology,
     * contains an OWLDatatype that has the specified IRI.
     * 
     * @param owlDatatypeIRI
     *        The IRI of the OWLDatatype to check for
     * @param includeImportsClosure
     *        {@code true} if the signature of the ontologies in the imports
     *        closure of this ontology should be checked, {@code false} if just
     *        the signature of this ontology should be chekced.
     * @return If {@code includeImportsClosure=true} then returns {@code true}
     *         if there is an OWLDatatype with {@code owlDatatypeIRI} as its IRI
     *         in the signature of at least one ontology in the imports closure
     *         of this ontology and {@code false} if this is not the case. If
     *         {@code includeImportsClosure=false} then returns {@code true} if
     *         the signature of this ontology contains an OWLDatatype that has
     *         {@code owlDatatypeIRI} as its IRI and {@code false} if the
     *         signature of this ontology does not contain a class with
     *         {@code owlDatatypeIRI} as its IRI.
     */
    boolean containsDatatypeInSignature(@Nonnull IRI owlDatatypeIRI,
            boolean includeImportsClosure);

    /**
     * Gets the entities in the signature of this ontology that have the
     * specified IRI.
     * 
     * @param iri
     *        The IRI of the entities to be retrieved.
     * @return A set of entities that are in the signature of this ontology that
     *         have the specified IRI. The set will be empty if there are no
     *         entities in the signature of this ontology with the specified
     *         IRI.
     */
    @Override
    Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri);

    /**
     * Gets the entities in the signature of this ontology, and possibly the
     * signature of the imports closure of this ontology, that have the
     * specified IRI.
     * 
     * @param iri
     *        The IRI of the entitied to be retrieved.
     * @param includeImportsClosure
     *        Specifies if the signatures of the ontologies in the imports
     *        closure of this ontology should also be taken into account
     * @return If {@code includeImportsClosure=true} then returns a set of
     *         entities that are in the signature of this ontology or the
     *         signature of an ontology in the imports closure of this ontology
     *         that have {@code iri} as their IRI. If
     *         {@code includeImportsClosure=false} then returns the entities in
     *         the signature of just this ontology that have {@code iri} as
     *         their IRI.
     */
    @Nonnull
    Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri,
            boolean includeImportsClosure);

    /**
     * @param entity
     *        entyty to check
     * @return true if entity is referenced
     */
    boolean containsReference(@Nonnull OWLClass entity);

    /**
     * @param entity
     *        entyty to check
     * @return true if entity is referenced
     */
    boolean containsReference(@Nonnull OWLObjectProperty entity);

    /**
     * @param entity
     *        entyty to check
     * @return true if entity is referenced
     */
    boolean containsReference(@Nonnull OWLDataProperty entity);

    /**
     * @param entity
     *        entyty to check
     * @return true if entity is referenced
     */
    boolean containsReference(@Nonnull OWLNamedIndividual entity);

    /**
     * @param dt
     *        dt to check
     * @return true if entity is referenced
     */
    boolean containsReference(@Nonnull OWLDatatype dt);

    /**
     * @param property
     *        property to check
     * @return true if entity is referenced
     */
    boolean containsReference(@Nonnull OWLAnnotationProperty property);
}
