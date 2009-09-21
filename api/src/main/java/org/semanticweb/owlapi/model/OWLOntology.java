package org.semanticweb.owlapi.model;


import java.util.Set;
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group Date: 24-Oct-2006
 * An <code>OWLOntology</code> consists of a possibly empty set of {@link org.semanticweb.owlapi.model.OWLAxiom}s
 * and a possibly empty set of {@link OWLAnnotation}s.  An ontology can have an ontology IRI which can be used to
 * identify the ontology.  If it has an ontology IRI then it may also have an ontology version IRI.  Since OWL 2, an
 * ontology need not have an ontology IRI.
 *
 * An ontology cannot be modified directly.  Changes must be applied via its <code>OWLOntologyManager</code>.
 */
public interface OWLOntology extends OWLObject {

    /**
     * Gets the manager that created this ontology. The manager is used by various methods on OWLOntology
     * to resolve imports
     * @return The manager that created this ontology.
     */
    OWLOntologyManager getOWLOntologyManager();

    /**
     * Gets the identity of this ontology (i.e. ontology IRI + version IRI)
     * @return The ID of this ontology.
     */
    OWLOntologyID getOntologyID();

    /**
     * Determines whether or not this ontology is anonymous.  An ontology is anonymous if it does not have an ontology
     * IRI.
     * @return <code>true</code> if this ontology is anonymous, otherwise <code>false</code>
     */
    boolean isAnonymous();

    /**
     * Gets the annotations on this ontology.
     * @return A set of annotations on this ontology.  The set returned will be a copy - modifying the set will have
     * no effect on the annotations in this ontology, similarly, any changes that affect the annotations on this
     * ontology will not change the returned set.
     */
    Set<OWLAnnotation> getAnnotations();

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Imported ontologies
    //
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets the set of document IRIs that are directly imported by this ontology.
     * This corresponds to the IRIs defined by the directlyImportsDocument association as discussed in Section 3.4 of the
     * OWL 2 Structural specification.
     * @return The set of directlyImportsDocument IRIs.
     * @throws UnknownOWLOntologyException If this ontology is no longer managed by its manager because it was
     *                                     removed from the manager.
     */
    Set<IRI> getDirectImportsDocuments() throws UnknownOWLOntologyException;

    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related to via the
     * directlyImports relation.  See Section 3.4 of the OWL 2 specification for the definition of the directlyImports
     * relation.
     * <p>
     * Note that there may be fewer ontologies in the set returned by this method than there are IRIs in the set returned by the
     * {@link #getDirectImportsDocuments()} method.  This will be the case if some of the ontologies that are directly imported by this ontology
     * are not loaded for what ever reason.
     * </p>
     * @return A set of ontologies such that for this ontology O, and each ontology O' in the set, (O, O') is in the
     * directlyImports relation.
     * @throws UnknownOWLOntologyException If this ontology is no longer managed by its manager because it was removed
     *                                     from the manager.
     */
    Set<OWLOntology> getDirectImports() throws UnknownOWLOntologyException;


    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related to via the
     * <em>transitive closure</em> of the directlyImports relation.  See Section 3.4 of the OWL 2 specification for the definition
     * of the directlyImports relation.
     * <p>
     * For example, if this ontology imports ontology B, and ontology B imports ontology C, then this method will return the set consisting of
     * ontology B and ontology C.
     * </p>
     *
     *
     * @return The set of ontologies that this ontology is related to via the transitive closure of the directlyImports
     * relation as defined in Section 3.4 of the OWL 2 Structural Specification. The set that is returned is a copy - it will not be updated if
     * the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over this set.
     * @throws UnknownOWLOntologyException if this ontology is no longer managed by its manager because it was removed
     *                                     from the manager.
     */
    Set<OWLOntology> getImports() throws UnknownOWLOntologyException;


    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related to via the
     * <em>reflexive transitive closure</em> of the directlyImports relation
     * as defined in Section 3.4 of the OWL 2 Structural Specification. (i.e. The set returned
     * includes all ontologies returned by the {@link #getImports()} method plus this ontology.)
     *
     * <p>
     * For example, if this ontology imports ontology B, and ontology B imports ontology C, then this method will return the set consisting of
     * this ontology, ontology B and ontology C.
     * </p>
     *
     * @return The set of ontologies in the reflexive transitive closure of the directlyImports relation.
     * @throws UnknownOWLOntologyException If this ontology is no longer managed by its manager because it was removed
     * from the manager.
     */
    Set<OWLOntology> getImportsClosure() throws UnknownOWLOntologyException;


    /**
     * Gets the set of imports declarations for this ontology.  The set returned represents the set of IRIs that
     * correspond to the set of IRIs in an ontology's directlyImportsDocuments (see Section 3 in the OWL 2 structural
     * specification).
     * @return The set of imports declarations that correspond to the set of ontology document IRIs that are directly
     *         imported by this ontology.
     *         The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLImportsDeclaration> getImportsDeclarations();


    

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods to retrive class, property and individual axioms
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Determines if this ontology is empty - an ontology is empty if it does not contain
     * any axioms (i.e. {@link #getAxioms()} returns the empty set), and it does not have any annotations (i.e.
     * {@link #getAnnotations()} returns the empty set).
     * @return <code>true</code> if the ontology is empty, otherwise <code>false</code>.
     */
    boolean isEmpty();

    /**
     * Retrieves all of the axioms in this ontology.  Note that to test whether or not this ontology is empty (i.e. contains
     * no axioms, the isEmpty method is preferred over getAxioms().isEmpty(). )
     * @return The set of all axioms in this ontology, including logical axioms and annotation axioms. The set that is
     *         returned is a copy of the axioms in the ontology - it will not be updated if the ontology changes.  It is
     *         recommended that the <code>containsAxiom</code> method is used to determine whether or not this ontology
     *         contains a particular axiom rather than using getAxioms().contains().
     */
    Set<OWLAxiom> getAxioms();


    /**
     * Gets the number of axioms in this ontology.
     * @return The number of axioms in this ontology.
     */
    int getAxiomCount();


    /**
     * Gets all of the axioms in the ontology that affect the logical meaning of the ontology.  In other words, this
     * method returns all axioms that are not annotation axioms, or declaration axioms.
     * @return A set of axioms which are of the type <code>OWLLogicalAxiom</code> The set that is returned is a copy of
     *         the axioms in the ontology - it will not be updated if the ontology changes.
     */
    Set<OWLLogicalAxiom> getLogicalAxioms();


    /**
     * Gets the number of logical axioms in this ontology.
     * @return The number of axioms in this ontology.
     */
    int getLogicalAxiomCount();

    /**
     * Gets the axioms which are of the specified type.
     * @param axiomType The type of axioms to be retrived.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology - it will not be updated if the ontology changes.
     */
    <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType);


    /**
     * Gets the axioms which are of the specified type.
     * @param axiomType             The type of axioms to be retrived.
     * @param includeImportsClosure if <code>true</code> then axioms of the specified type will also be retrieved from
     *                              the imports closure of this ontology, if <code>false</code> then axioms of the specified type will only
     *                              be retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology (and its imports closure) - it will not be updated if the ontology changes.
     */
    <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, boolean includeImportsClosure);


    /**
     * Gets the axiom count of a specific type of axiom
     * @param axiomType The type of axiom to count
     * @return The number of the specified types of axioms in this ontology
     */
    <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType);

    /**
     * Gets the axiom count of a specific type of axiom, possibly in the imports closure of this ontology
     * @param axiomType             The type of axiom to count
     * @param includeImportsClosure Specifies that the imports closure should be included when counting axioms
     * @return The number of the specified types of axioms in this ontology
     */
    <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, boolean includeImportsClosure);

    /**
     * Determines if this ontology contains the specified axiom.
     * @param axiom The axiom to test for.
     * @return <code>true</code> if the ontology contains the specified axioms, or <code>false</code> if the ontology
     *         doesn't contain the specified axiom.
     */
    boolean containsAxiom(OWLAxiom axiom);

    /**
     * Determines if this ontology, and possibly the imports closure, contains the specified axiom.
     * @param axiom                 The axiom to test for.
     * @param includeImportsClosure if <code>true</code> the imports closure of this ontology will be searched for the
     *                              specific axiom, if <code>false</code> just this ontology will be searched.
     * @return <code>true</code> if the ontology contains the specified axioms, or <code>false</code> if the ontology
     *         doesn't contain the specified axiom.
     */
    boolean containsAxiom(OWLAxiom axiom, boolean includeImportsClosure);


    /**
     * Gets the set of general axioms in this ontology.  This includes: <ul> <li>Subclass axioms that have a complex
     * class as the subclass</li> <li>Equivalent class axioms that don't contain any named classes
     * (<code>OWLClass</code>es)</li> <li>Disjoint class axioms that don't contain any named classes
     * (<code>OWLClass</code>es)</li> </ul>
     * @return The set that is returned is a copy of the axioms in the ontology - it will not be updated if the ontology
     *         changes.  It is therefore safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLClassAxiom> getGeneralClassAxioms();

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // References/usage
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the entities that are referenced by axioms in this ontology.
     * @return A set of <code>OWLEntity</code> objects. The set that is returned is a copy - it will not be updated if
     *         the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over this
     *         set.
     */
    Set<OWLEntity> getReferencedEntities();


    /**
     * Gets the classes that are referenced by axioms in this ontology.
     * @return A set of named classes, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLClass> getReferencedClasses();


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
    Set<OWLClass> getReferencedClasses(boolean includeImportsClosure);


    /**
     * Gets the object properties that are referenced by axioms (including annotation axioms) in this ontology.
     * @return A set of object properties, which are referenced by any axiom in this ontology. The set that is returned
     *         is a copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    Set<OWLObjectProperty> getReferencedObjectProperties();


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
    Set<OWLObjectProperty> getReferencedObjectProperties(boolean includeImportsClosure);


    /**
     * Gets the data properties that are referenced by axioms in this ontology.
     * @return A set of data properties, which are referenced by any axiom in this ontology. The set that is returned is
     *         a copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLDataProperty> getReferencedDataProperties();

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
    Set<OWLDataProperty> getReferencedDataProperties(boolean includeImportsClosure);

    /**
     * Gets the individuals that are referenced by axioms in this ontology.
     * @return A set of individuals, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLNamedIndividual> getReferencedIndividuals();

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
    Set<OWLNamedIndividual> getReferencedIndividuals(boolean includeImportsClosure);


    /**
     * Gets the referenced anonymous individuals
     * @return The set of referenced anonymous individuals
     */
    Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals();


    /**
     * Gets the datatypes that are referenced by this ontology
     * @return The set of referenced datatypes
     */
    Set<OWLDatatype> getReferencedDatatypes();

    /**
     * Gets the datatypes that are referenced by this ontology and possibly its imports closure
     * @param includeImportsClosure Specifies whether referenced named individuals should be drawn from this ontology or the imports
     *                              closure of this ontology.  If <code>true</code> then the set of referenced named individuals will be from the
     *                              imports closure of this ontology, if <code>false</code> then the set of referenced named individuals will just
     *                              be from this ontology.
     * @return The set of datatypes that are referenced by axioms in this ontology and possibly its imports closure
     */
    Set<OWLDatatype> getReferencedDatatypes(boolean includeImportsClosure);

    Set<OWLAnnotationProperty> getReferencedAnnotationProperties();

    Set<OWLAnnotationProperty> getReferencedAnnotationProperties(boolean includeImportsClosure);

    /**
     * Gets the axioms where the specified entity appears in the signature of the axiom. The set that is returned,
     * contains all axioms that directly reference the specified entity.
     * @param owlEntity The entity that should be directly referred to by an axiom that appears in the results set.
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity);

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
    Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity, boolean includeImportsClosure);


    /**
     * Gets the axioms that reference the specified anonymous individual
     * @param individual The individual
     * @return The axioms that reference the specified anonymous individual
     */
    Set<OWLAxiom> getReferencingAxioms(OWLAnonymousIndividual individual);

    /**
     * Determines if the ontology contains a reference to the specified entity.
     * @param owlEntity The entity
     * @return <code>true</code> if the ontology contains a reference to the specified entity, otherwise
     *         <code>false</code> The set that is returned is a copy - it will not be updated if the ontology changes.
     *         It is therefore safe to apply changes to this ontology while iterating over this set.
     */
    boolean containsEntityReference(OWLEntity owlEntity);

    /**
     * Determines if the ontology, and possibly its imports closure, contains a reference to the specified entity.
     * @param owlEntity             The entity
     * @param includeImportsClosure Specifies whether the imports closure should be examined for the entity reference
     *                              or not.
     * @return <code>true</code> if the ontology contains a reference to the specified entity, otherwise
     *         <code>false</code> The set that is returned is a copy - it will not be updated if the ontology changes.
     *         It is therefore safe to apply changes to this ontology while iterating over this set.
     */
    boolean containsEntityReference(OWLEntity owlEntity, boolean includeImportsClosure);


    boolean containsEntityReference(IRI entityIRI);


    boolean containsEntityReference(IRI entityIRI, boolean includeImportsClosure);


    /**
     * Determines if this ontology declares an entity i.e. it contains a declaration axiom for the specified entity.
     * @param owlEntity The entity to be tested for
     * @return <code>true</code> if the ontology contains a declaration for the specified entity, otherwise
     *         <code>false</code>.
     */
    boolean isDeclared(OWLEntity owlEntity);


    /**
     * Determines if this ontology or its imports closure declares an entity i.e.
     * contains a declaration axiom for the specified entity.
     * @param owlEntity             The entity to be tested for
     * @param includeImportsClosure <code>true</code> if the imports closure of this ontology should be examined,
     *                              <code>false</code> if just this ontology should be examined.
     * @return <code>true</code> if the ontology or its imports closure contains a declaration for the specified entity, otherwise
     *         <code>false</code>.
     */
    boolean isDeclared(OWLEntity owlEntity, boolean includeImportsClosure);

    //////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Access by IRI
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Determines if the ontology contains a reference to a class that has a specific IRI.
     * @param owlClassIRI The IRI to test for.
     * @return <code>true</code> if the ontology refers to a class with the specified IRI, otherwise <code>false</code>
     */
    boolean containsClassReference(IRI owlClassIRI);

    boolean containsClassReference(IRI owlClassIRI, boolean includeImportsClosure);


    /**
     * Determines if the ontology contains a reference to an object property that has a specific IRI.
     * @param propIRI The IRI of the property
     * @return <code>true</code> if the ontology references (ontology signature contains) a property that has the
     *         specified IRI.
     */
    boolean containsObjectPropertyReference(IRI propIRI);

    boolean containsObjectPropertyReference(IRI propIRI, boolean includeImportsClosure);


    /**
     * Determines if the ontology contains a reference to a data property that has a specific IRI.
     * @param propIRI The IRI to check for
     * @return <code>true</code> if the ontology references (ontology signature contains) a property that has the
     *         specified IRI.
     */
    boolean containsDataPropertyReference(IRI propIRI);

    boolean containsDataPropertyReference(IRI propIRI, boolean includeImportsClosure);


    /**
     * Determines if the ontology contains a reference to an annotation property that has a specific IRI.
     * @param propIRI The IRI to check for
     * @return <code>true</code> if the ontology references (ontology signature contains) a property that has the
     *         specified URI.
     */
    boolean containsAnnotationPropertyReference(IRI propIRI);

    boolean containsAnnotationPropertyReference(IRI propIRI, boolean includeImportsClosure);


    /**
     * Determines if the ontology contains a reference to an individual that has a specific IRI.
     * @param individualIRI The IRI of the individual
     * @return <code>true</code> if the ontology references (ontology signature contains) an individual that has the
     *         specified IRI.
     */
    boolean containsIndividualReference(IRI individualIRI);

    boolean containsIndividualReference(IRI individualIRI, boolean includeImportsClosure);


    boolean containsDatatypeReference(IRI datatypeIRI);

    boolean containsDatatypeReference(IRI datatypeIRI, boolean includeImportsClosure);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Axioms that form part of a description of a named entity
    //
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets the axioms that form the definition/description of a class.
     * @param cls The class whose describing axioms are to be retrieved.
     * @return A set of class axioms that describe the class.  This set includes <ul> <li>Subclass axioms where the
     *         subclass is equal to the specified class</li> <li>Equivalent class axioms where the specified class is an
     *         operand in the equivalent class axiom</li> <li>Disjoint class axioms where the specified class is an
     *         operand in the disjoint class axiom</li> <li>Disjoint union axioms, where the specified class is the
     *         named class that is equivalent to the disjoint union</li> </ul> The set that is returned is a copy - it
     *         will not be updated if the ontology changes.  It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    Set<OWLClassAxiom> getAxioms(OWLClass cls);


    /**
     * Gets the axioms that form the definition/description of an object property.
     * @param prop The property whose defining axioms are to be retrieved.
     * @return A set of object property axioms that includes <ul> <li>Sub-property axioms where the sub property is
     *         equal to the specified property</li> <li>Equivalent property axioms where the axiom contains the
     *         specified property</li> <li>Equivalent property axioms that contain the inverse of the specified
     *         property</li> <li>Disjoint property axioms that contain the specified property</li> <li>Domain axioms
     *         that specify a domain of the specified property</li> <li>Range axioms that specify a range of the
     *         specified property</li> <li>Any property characteristic axiom (i.e. Functional, Symmetric, Reflexive
     *         etc.) whose subject is the specified property</li> <li>Inverse properties axioms that contain the
     *         specified property</li> </ul> The set that is returned is a copy - it will not be updated if the ontology
     *         changes.  It is therefore safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLObjectPropertyAxiom> getAxioms(OWLObjectPropertyExpression prop);


    /**
     * Gets the axioms that form the definition/description of a data property.
     * @param prop The property whose defining axioms are to be retrieved.
     * @return A set of data property axioms that includes <ul> <li>Sub-property axioms where the sub property is equal
     *         to the specified property</li> <li>Equivalent property axioms where the axiom contains the specified
     *         property</li> <li>Disjoint property axioms that contain the specified property</li> <li>Domain axioms
     *         that specify a domain of the specified property</li> <li>Range axioms that specify a range of the
     *         specified property</li> <li>Any property characteristic axiom (i.e. Functional, Symmetric, Reflexive
     *         etc.) whose subject is the specified property</li> </ul> The set that is returned is a copy - it will not
     *         be updated if the ontology changes.  It is therefore safe to apply changes to this ontology while
     *         iterating over this set.
     */
    Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty prop);


    /**
     * Gets the axioms that form the definition/description of an individual
     * @param individual The individual whose defining axioms are to be retrieved.
     * @return A set of individual axioms that includes <ul> <li>Individual type assertions that assert the type of the
     *         specified individual</li> <li>Same individuals axioms that contain the specified individual</li>
     *         <li>Different individuals axioms that contain the specified individual</li> <li>Object property assertion
     *         axioms whose subject is the specified individual</li> <li>Data property assertion axioms whose subject is
     *         the specified individual</li> <li>Negative object property assertion axioms whose subject is the
     *         specified individual</li> <li>Negative data property assertion axioms whose subject is the specified
     *         individual</li> </ul> The set that is returned is a copy - it will not be updated if the ontology
     *         changes.
     */
    Set<OWLIndividualAxiom> getAxioms(OWLIndividual individual);

    /**
     * Gets the axioms that form the definition/description of an annotation property.
     * @param property The property whose definition axioms are to be retrieved
     * @return A set of axioms that includes <ul><li>Annotation subpropertyOf axioms where the specified property is
     *         the sub property</li><li>Annotation property domain axioms that specify a domain for the specified property</li>
     *         <li>Annotation property range axioms that specify a range for the specified property</li></ul>
     */
    Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty property);

    /**
     * Gets the datatype definition axioms for the specified datatype
     * @param datatype The datatype
     * @return The set of datatype definition axioms for the specified datatype
     */
    Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Annotation axioms
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty);

    Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(OWLAnnotationProperty property);

    Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(OWLAnnotationProperty property);



    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Various methods that provide axioms relating to specific entities that allow
    // frame style views to be composed for a particular entity.  Such functionality is
    // useful for ontology editors and browsers.
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the declaration axioms for specified entity.
     * @param subject The entity that is the subject of the set of returned axioms.
     * @return The set of declaration axioms. Note that this set will be a copy and will not be updated if the ontology
     *         changes.  It is therefore safe to iterate over this set while making changes to the ontology.
     */
    Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity subject);

    /**
     * Gets the axioms that annotate the specified entity.
     * @param entity The entity whose annotations are to be retrieved.
     * @return The set of entity annotation axioms. Note that this set will be a copy and will not be updated if the
     *         ontology changes.  It is therefore safe to iterate over this set while making changes to the ontology.
     */
    Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLAnnotationSubject entity);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Classes
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets all of the subclass axioms where the left hand side (the subclass) is equal to the specified class.
     * @param cls The class that is equal to the left hand side of the axiom (subclass).
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls);


    /**
     * Gets all of the subclass axioms where the right hand side (the superclass) is equal to the specified class.
     * @param cls The class
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls);


    /**
     * Gets all of the equivalent axioms in this ontology that contain the specified class as an operand.
     * @param cls The class
     * @return A set of equivalent class axioms that contain the specified class as an operand.  The set that is
     *         returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls);


    /**
     * Gets the set of disjoint class axioms that contain the specified class as an operand.
     * @param cls The class that should be contained in the set of disjoint class axioms that will be returned.
     * @return The set of disjoint axioms that contain the specified class.  The set that is returned is a copy - it
     *         will not be updated if the ontology changes.  It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls);


    /**
     * Gets the set of disjoint union axioms that have the specified class as the named class that is equivalent to the
     * disjoint union of operands.  For example, if the ontology contained the axiom DisjointUnion(A, propP some C, D,
     * E) this axiom would be returned for class A (but not for D or E).
     * @param owlClass The class that indexes the axioms to be retrieved.
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass);


    /**
     * Gets the has key axioms that have the specified class as their subject.
     * @param cls The subject of the has key axioms
     * @return The set of has key axioms that have cls as their subject. The set that is returned is a copy -
     *         it will not be updated if the ontology changes.  It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Object properties
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the object property sub property axioms where the specified property is on the left hand side of the axiom.
     * @param property The property which is on the left hand side of the axiom.
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression property);


    Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression property);


    Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(OWLObjectPropertyExpression property);


    Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(OWLObjectPropertyExpression property);


    Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(OWLObjectPropertyExpression property);


    Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(OWLObjectPropertyExpression property);


    Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(OWLObjectPropertyExpression property);


    Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property);


    Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property);


    Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property);


    /**
     * Gets the axiom that states that this property is asymmetric.  Note that this will return an
     * antisymmetric property axiom.  The name of this interfaces is due to legacy reasons.
     * @param property The property
     * @return The axiom that states that this property is asymmetric, or <code>null</code> if there is
     *         no axiom that states this.
     */
    Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property);

    Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property);

    Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property);

    Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(OWLObjectPropertyExpression property);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data properties
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty lhsProperty);


    Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression property);


    Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty property);


    Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(OWLDataProperty property);


    Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(OWLDataProperty property);


    Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(OWLDataProperty property);


    Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(OWLDataPropertyExpression property);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Individuals
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual);


    Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass type);


    Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual individual);


    Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual individual);


    Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(OWLIndividual individual);


    Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(OWLIndividual individual);


    Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual);


    Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual individual);

    Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(OWLDatatype datatype);
}
