package org.semanticweb.owlapi.model;


import java.util.Set;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group Date: 24-Oct-2006
 * </p>
 * Represents an OWL 2 <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Ontologies">Ontology</a> in the OWL 2 specification.
 * </p>
 * An <code>OWLOntology</code> consists of a possibly empty set of {@link org.semanticweb.owlapi.model.OWLAxiom}s
 * and a possibly empty set of {@link OWLAnnotation}s.  An ontology can have an ontology IRI which can be used to
 * identify the ontology.  If it has an ontology IRI then it may also have an ontology version IRI.  Since OWL 2, an
 * ontology need not have an ontology IRI.  (See the <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/">OWL
 * 2 Structural Specification</a>).
 * </p>
 * An ontology cannot be modified directly.  Changes must be applied via its <code>OWLOntologyManager</code>.
 */
public interface OWLOntology extends OWLObject {

    /**
     * Gets the manager that created this ontology. The manager is used by various methods on OWLOntology
     * to resolve imports
     *
     * @return The manager that created this ontology.
     */
    OWLOntologyManager getOWLOntologyManager();

    /**
     * Gets the identity of this ontology (i.e. ontology IRI + version IRI).
     *
     * @return The ID of this ontology.
     */
    OWLOntologyID getOntologyID();

    /**
     * Determines whether or not this ontology is anonymous.  An ontology is anonymous if it does not have an ontology
     * IRI.
     *
     * @return <code>true</code> if this ontology is anonymous, otherwise <code>false</code>
     */
    boolean isAnonymous();

    /**
     * Gets the annotations on this ontology.
     *
     * @return A set of annotations on this ontology.  The set returned will be a copy - modifying the set will have
     *         no effect on the annotations in this ontology, similarly, any changes that affect the annotations on this
     *         ontology will not change the returned set.
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
     *
     * @return The set of directlyImportsDocument IRIs.
     *
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
     *
     * @return A set of ontologies such that for this ontology O, and each ontology O' in the set, (O, O') is in the
     *         directlyImports relation.
     *
     * @throws UnknownOWLOntologyException If this ontology is no longer managed by its manager because it was removed
     *                                     from the manager.
     */
    Set<OWLOntology> getDirectImports() throws UnknownOWLOntologyException;


    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related to via the
     * <em>transitive closure</em> of the <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Imports">directlyImports relation</a>.  
     * <p>
     * For example, if this ontology imports ontology B, and ontology B imports ontology C, then this method will return the set consisting of
     * ontology B and ontology C.
     * </p>
     *
     * @return The set of ontologies that this ontology is related to via the transitive closure of the directlyImports
     *         relation. The set that is returned is a copy - it will not be updated if
     *         the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over this set.
     *
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
     *
     * @throws UnknownOWLOntologyException If this ontology is no longer managed by its manager because it was removed
     *                                     from the manager.
     */
    Set<OWLOntology> getImportsClosure() throws UnknownOWLOntologyException;


    /**
     * Gets the set of imports declarations for this ontology.  The set returned represents the set of IRIs that
     * correspond to the set of IRIs in an ontology's directlyImportsDocuments (see Section 3 in the OWL 2 structural
     * specification).
     *
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
     *
     * @return <code>true</code> if the ontology is empty, otherwise <code>false</code>.
     */
    boolean isEmpty();

    /**
     * Retrieves all of the axioms in this ontology.  Note that to test whether or not this ontology is empty (i.e. contains
     * no axioms, the isEmpty method is preferred over getAxioms().isEmpty(). )
     *
     * @return The set of all axioms in this ontology, including logical axioms and annotation axioms. The set that is
     *         returned is a copy of the axioms in the ontology - it will not be updated if the ontology changes.  It is
     *         recommended that the <code>containsAxiom</code> method is used to determine whether or not this ontology
     *         contains a particular axiom rather than using getAxioms().contains().
     */
    Set<OWLAxiom> getAxioms();


    /**
     * Gets the number of axioms in this ontology.
     *
     * @return The number of axioms in this ontology.
     */
    int getAxiomCount();


    /**
     * Gets all of the axioms in the ontology that affect the logical meaning of the ontology.  In other words, this
     * method returns all axioms that are not annotation axioms, or declaration axioms.
     *
     * @return A set of axioms which are of the type <code>OWLLogicalAxiom</code> The set that is returned is a copy of
     *         the axioms in the ontology - it will not be updated if the ontology changes.
     */
    Set<OWLLogicalAxiom> getLogicalAxioms();


    /**
     * Gets the number of logical axioms in this ontology.
     *
     * @return The number of axioms in this ontology.
     */
    int getLogicalAxiomCount();

    /**
     * Gets the axioms which are of the specified type.
     *
     * @param axiomType The type of axioms to be retrived.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology - it will not be updated if the ontology changes.
     */
    <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType);


    /**
     * Gets the axioms which are of the specified type.
     *
     * @param axiomType The type of axioms to be retrived.
     * @param includeImportsClosure if <code>true</code> then axioms of the specified type will also be retrieved from
     * the imports closure of this ontology, if <code>false</code> then axioms of the specified type will only
     * be retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology (and its imports closure) - it will not be updated if the ontology changes.
     */
    <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, boolean includeImportsClosure);
    
    /**
     * Gets the axioms that form the TBox for this ontology, i.e., the ones whose type is in the AxiomType::TBoxAxiomTypes
     * 
     *
     * @param includeImportsClosure if <code>true</code> then axioms of the specified type will also be retrieved from
     * the imports closure of this ontology, if <code>false</code> then axioms of the specified type will only
     * be retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology (and its imports closure) - it will not be updated if the ontology changes.
     */
    Set<OWLAxiom> getTBoxAxioms(boolean includeImportsClosure);
    /**
     * Gets the axioms that form the ABox for this ontology, i.e., the ones whose type is in the AxiomType::ABoxAxiomTypes
     *
     * @param includeImportsClosure if <code>true</code> then axioms of the specified type will also be retrieved from
     * the imports closure of this ontology, if <code>false</code> then axioms of the specified type will only
     * be retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology (and its imports closure) - it will not be updated if the ontology changes.
     */
    Set<OWLAxiom> getABoxAxioms(boolean includeImportsClosure);
    /**
     * Gets the axioms that form the RBox for this ontology, i.e., the ones whose type is in the AxiomType::RBoxAxiomTypes
     *
     * @param includeImportsClosure if <code>true</code> then axioms of the specified type will also be retrieved from
     * the imports closure of this ontology, if <code>false</code> then axioms of the specified type will only
     * be retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in the ontology (and its imports closure) - it will not be updated if the ontology changes.
     */
    Set<OWLAxiom> getRBoxAxioms(boolean includeImportsClosure);


    /**
     * Gets the axiom count of a specific type of axiom
     *
     * @param axiomType The type of axiom to count
     * @return The number of the specified types of axioms in this ontology
     */
    <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType);

    /**
     * Gets the axiom count of a specific type of axiom, possibly in the imports closure of this ontology
     *
     * @param axiomType The type of axiom to count
     * @param includeImportsClosure Specifies that the imports closure should be included when counting axioms
     * @return The number of the specified types of axioms in this ontology
     */
    <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, boolean includeImportsClosure);

    /**
     * Determines if this ontology contains the specified axiom.
     *
     * @param axiom The axiom to test for.
     * @return <code>true</code> if the ontology contains the specified axioms, or <code>false</code> if the ontology
     *         doesn't contain the specified axiom.
     */
    boolean containsAxiom(OWLAxiom axiom);

    /**
     * Determines if this ontology, and possibly the imports closure, contains the specified axiom.
     *
     * @param axiom The axiom to test for.
     * @param includeImportsClosure if <code>true</code> the imports closure of this ontology will be searched for the
     * specific axiom, if <code>false</code> just this ontology will be searched.
     * @return <code>true</code> if the ontology contains the specified axioms, or <code>false</code> if the ontology
     *         doesn't contain the specified axiom.
     */
    boolean containsAxiom(OWLAxiom axiom, boolean includeImportsClosure);

    /**
     * Determines if this ontology contains the specified axiom, but ignoring any annotations on this
     * axiom.  For example, if the ontology contains <code>SubClassOf(Annotation(p V) A B)</code> then this method
     * will return <code>true</code> if the ontology contains <code>SubClassOf(A B)</code> or
     * <code>SubClassOf(Annotation(q S) A B)</code> for any annotation property <code>q</code> and any annotation
     * value <code>S</code>.
     *
     * @param axiom The axiom to test for.
     * @return <code>true</code> if this ontology contains this axiom with or without annotations.
     */
    boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom);


    /**
     * Gets the set of axioms contained in this ontology that have the same "logical structure" as the specified axiom.
     *
     * @param axiom The axiom that specifies the logical structure of the axioms to retrieve.  If this axiom is annotated
     * then the annotations are ignored.
     * @return A set of axioms such that for any two axioms, <code>axiomA</code> and
     *         <code>axiomB</code> in the set, <code>axiomA.getAxiomWithoutAnnotations()</code> is equal to
     *         <code>axiomB.getAxiomWithoutAnnotations()</code>.  The specified axiom will be contained in the set.
     */
    Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom);

    /**
     * Gets the set of axioms contained in this ontology that have the same "logical structure" as the specified axiom, possibly searching
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
    Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom axiom, boolean includeImportsClosure);


    /**
     * Determines if this ontology and possibly its imports closure contains the specified axiom but
     * ignoring any annotations on this axiom. For example, if the ontology contains
     * <code>SubClassOf(Annotation(p V) A B)</code> then this method
     * will return <code>true</code> if the ontology contains <code>SubClassOf(A B)</code> or
     * <code>SubClassOf(Annotation(q S) A B)</code> for any annotation property <code>q</code> and any annotation
     * value <code>S</code>.
     *
     * @param axiom The axiom to test for.
     * @param includeImportsClosure if <code>true</code> the imports closure of this ontology will be searched for the
     * specified axiom. If <code>false</code> only this ontology will be searched for the specifed axiom.
     * @return <code>true</code> if this ontology contains this axiom with or without annotations.
     */
    boolean containsAxiomIgnoreAnnotations(OWLAxiom axiom, boolean includeImportsClosure);

    /**
     * Gets the set of general axioms in this ontology.  This includes: <ul> <li>Subclass axioms that have a complex
     * class as the subclass</li> <li>Equivalent class axioms that don't contain any named classes
     * (<code>OWLClass</code>es)</li> <li>Disjoint class axioms that don't contain any named classes
     * (<code>OWLClass</code>es)</li> </ul>
     *
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
     * Gets the entities that are in the signature of this ontology.  The signature of an ontology is the set of
     * entities that are used to build axioms and annotations in the ontology.
     * (See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Entities.2C_Literals.2C_and_Anonymous_Individuals">The OWL 2 Structural Specification</a>)
     *
     * @return A set of <code>OWLEntity</code> objects. The set that is returned is a copy - it will not be updated if
     *         the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over this
     *         set.
     * @see #getClassesInSignature()
     * @see #getObjectPropertiesInSignature()
     * @see #getDataPropertiesInSignature()
     * @see #getIndividualsInSignature()
     */
    Set<OWLEntity> getSignature();


    /**
     * Gets the entities that are in the signature of this ontology.  The signature of an ontology is the set of
     * entities that are used to build axioms and annotations in the ontology.
     * (See <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Entities.2C_Literals.2C_and_Anonymous_Individuals">The OWL 2 Structural Specification</a>)
     *
     * @param includeImportsClosure Specifies whether or not the returned set of entities should represent the signature
     * of just this ontology, or the signature of the imports closure of this ontology.
     * @return A set of <code>OWLEntity</code> objects. The set that is returned is a copy - it will not be updated if
     *         the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over this
     *         set.
     * @see #getClassesInSignature()
     * @see #getObjectPropertiesInSignature()
     * @see #getDataPropertiesInSignature()
     * @see #getIndividualsInSignature()
     */
    Set<OWLEntity> getSignature(boolean includeImportsClosure);

    /**
     * Gets the classes that are in the signature of this ontology.
     * @see #getSignature()
     * @return A set of named classes, which are referenced by any axiom in this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLClass> getClassesInSignature();


    /**
     * Gets the classes that are in the signature of this ontology, and possibly the imports closure of this
     * ontology.
     *
     * @param includeImportsClosure Specifies whether classes should be drawn from the signature of just this ontology or the
     * imports closure of this ontology.  If <code>true</code> then the set of classes returned will correspond to the union
     * of the classes in the signatures of the ontologies in the imports closure of this ontology. If <code>false</code>
     * then the set of classes returned will correspond to the classes that are in the signature of this just this ontology.
     * @return A set of classes that are in the signature of this ontology and possibly the union of the signatures of
     * the ontologies in the imports closure of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLClass> getClassesInSignature(boolean includeImportsClosure);


    /**
     * Gets the object properties that are in the signature of this ontology.
     * @see #getSignature() 
     * @return A set of object properties which are in the signature of this ontology. The set that is returned
     *         is a copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    Set<OWLObjectProperty> getObjectPropertiesInSignature();



    /**
     * Gets the object properties that are in the signature of this ontology, and possibly the imports closure of this
     * ontology.
     *
     * @param includeImportsClosure Specifies whether object properties should be drawn from the signature of just this ontology or the
     * imports closure of this ontology.  If <code>true</code> then the set of object properties returned will correspond to the union
     * of the object properties in the signatures of the ontologies in the imports closure of this ontology. If <code>false</code>
     * then the set of object properties returned will correspond to the object properties that are in the signature of this just this ontology.
     * @return A set of object properties that are in the signature of this ontology and possibly the union of the signatures of
     * the ontologies in the imports closure of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean includeImportsClosure);


    /**
     * Gets the data properties that are in the signature of this ontology.
     * @see #getSignature()
     * @return A set of data properties, which are in the signature of this ontology. The set that is returned is
     *         a copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLDataProperty> getDataPropertiesInSignature();

    /**
     * Gets the data properties that are in the signature of this ontology, and possibly the imports closure of this
     * ontology.
     *
     * @param includeImportsClosure Specifies whether data properties should be drawn from the signature of just this ontology or the
     * imports closure of this ontology.  If <code>true</code> then the set of data properties returned will correspond to the union
     * of the data properties in the signatures of the ontologies in the imports closure of this ontology. If <code>false</code>
     * then the set of data properties returned will correspond to the data properties that are in the signature of this just this ontology.
     * @return A set of data properties that are in the signature of this ontology and possibly the union of the signatures of
     * the ontologies in the imports closure of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLDataProperty> getDataPropertiesInSignature(boolean includeImportsClosure);

    /**
     * Gets the individuals that are in the signature of this ontology.
     * @see #getSignature()
     * @return A set of individuals, which are in the signature of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLNamedIndividual> getIndividualsInSignature();

    /**
     * Gets the individuals that are in the signature of this ontology, and possibly the imports closure of this
     * ontology.
     * @see #getSignature()
     * @param includeImportsClosure Specifies whether individuals should be drawn from the signature of just this ontology or the
     * imports closure of this ontology.  If <code>true</code> then the set of individuals returned will correspond to the union
     * of the individuals in the signatures of the ontologies in the imports closure of this ontology. If <code>false</code>
     * then the set of individuals returned will correspond to the individuals that are in the signature of this just this ontology.
     * @return A set of individuals that are in the signature of this ontology and possibly the union of the signatures of
     * the ontologies in the imports closure of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLNamedIndividual> getIndividualsInSignature(boolean includeImportsClosure);


    /**
     * Gets the referenced anonymous individuals
     *
     * @return The set of referenced anonymous individuals
     */
    Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals();


    /**
     * Gets the datatypes that are in the signature of this ontology.
     * @see #getSignature()
     * @return A set of datatypes, which are in the signature of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLDatatype> getDatatypesInSignature();

    /**
     * Gets the datatypes that are in the signature of this ontology, and possibly the imports closure of this
     * ontology.
     * @see #getSignature()
     * @param includeImportsClosure Specifies whether datatypes should be drawn from the signature of just this ontology or the
     * imports closure of this ontology.  If <code>true</code> then the set of datatypes returned will correspond to the union
     * of the datatypes in the signatures of the ontologies in the imports closure of this ontology. If <code>false</code>
     * then the set of datatypes returned will correspond to the datatypes that are in the signature of this just this ontology.
     * @return A set of datatypes that are in the signature of this ontology and possibly the union of the signatures of
     * the ontologies in the imports closure of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLDatatype> getDatatypesInSignature(boolean includeImportsClosure);

    /**
     * Gets the annotation properties that are in the signature of this ontology.
     * @see #getSignature()
     * @return A set of annotation properties, which are in the signature of this ontology. The set that is returned is a
     *         copy - it will not be updated if the ontology changes.  It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature();


    /**
     * Gets the axioms where the specified entity appears in the signature of the axiom. The set that is returned,
     * contains all axioms that directly reference the specified entity.
     *
     * @param owlEntity The entity that should be directly referred to by an axiom that appears in the results set.
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity);

    /**
     * Gets the axioms where the specified entity appears in the signature of the axiom. The set that is returned,
     * contains all axioms that directly reference the specified entity.
     *
     * @param owlEntity The entity that should be directly referred to by an axiom that appears in the results set.
     * @param includeImportsClosure Specifies if the axioms returned should just be from this ontology, or from the
     * imports closure of this ontology.  If <code>true</code> the axioms returned will be from the imports closure
     * of this ontology, if <code>false</code> the axioms returned will just be from this ontology.
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity, boolean includeImportsClosure);


    /**
     * Gets the axioms that reference the specified anonymous individual
     *
     * @param individual The individual
     * @return The axioms that reference the specified anonymous individual
     */
    Set<OWLAxiom> getReferencingAxioms(OWLAnonymousIndividual individual);

    /**
     * Determines if the signature of the ontology contains the specified entity.
     *
     * @param owlEntity The entity
     * @return <code>true</code> if the signature of this ontology contains <code>owlEntity</code>, otherwise
     *         <code>false</code>.
     */
    boolean containsEntityInSignature(OWLEntity owlEntity);

    /**
     * Determines if the signature of this ontology, and possibly the signature of any of the ontologies in the imports
     * closure of this ontology, contains the specified entity.
     *
     * @param owlEntity The entity
     * @param includeImportsClosure Specifies whether the imports closure should be examined for the entity reference
     * or not.
     * @return <code>true</code> if the ontology contains a reference to the specified entity, otherwise
     *         <code>false</code> The set that is returned is a copy - it will not be updated if the ontology changes.
     *         It is therefore safe to apply changes to this ontology while iterating over this set.
     */
    boolean containsEntityInSignature(OWLEntity owlEntity, boolean includeImportsClosure);


    /**
     * Determines if the signature of this ontology contains a class, object property, data property, named individual,
     * annotation property or datatype with the specified IRI.
     * @param entityIRI The IRI to test for.
     * @return <code>true</code> if the signature of this ontology contains a class, object property, data property,
     * named individual, annotation property or datatype with the specified IRI.
     */
    boolean containsEntityInSignature(IRI entityIRI);

    /**
     * Determines if the signature of this ontology and possibly its imports closure contains a class, object property,
     * data property, named individual, annotation property or datatype with the specified IRI.
     * @param entityIRI The IRI to test for.
     * @param includeImportsClosure Specifies whether the imports closure of this ontology should be examined or not.
     * @return If <code>includeImportsClosure=true</code> then returns
     * <code>true</code> if the signature of this ontology or the signature of an ontology in the imports closure of this
     * ontology contains a class, object property, data property,
     * named individual, annotation property or datatype with the specified IRI.  If <code>includeImportsClosure=false</code>
     * then returns <code>true</code> if the signature of this ontology contains a class, object property, data property,
     * named individual, annotation property or datatype with the specified IRI.
     */
    boolean containsEntityInSignature(IRI entityIRI, boolean includeImportsClosure);


    /**
     * Determines if this ontology declares an entity i.e. it contains a declaration axiom for the specified entity.
     *
     * @param owlEntity The entity to be tested for
     * @return <code>true</code> if the ontology contains a declaration for the specified entity, otherwise
     *         <code>false</code>.
     */
    boolean isDeclared(OWLEntity owlEntity);


    /**
     * Determines if this ontology or its imports closure declares an entity i.e.
     * contains a declaration axiom for the specified entity.
     *
     * @param owlEntity The entity to be tested for
     * @param includeImportsClosure <code>true</code> if the imports closure of this ontology should be examined,
     * <code>false</code> if just this ontology should be examined.
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
     * Determines if the signature of this ontology contains an OWLClass with the specified IRI.
     *
     * @param owlClassIRI The IRI of the OWLClass to check for.
     * @return <code>true</code> if the signature of this ontology contains an OWLClass that has <code>owlClassIRI</code> as
     * its IRI, otherwise <code>false</code>.
     */
    boolean containsClassInSignature(IRI owlClassIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature of one of the ontologies in the imports
     * closure of this ontology, contains an OWLClass that has the specified IRI.
     * @param owlClassIRI The IRI of the class to check for
     * @param includeImportsClosure <code>true</code> if the signature of the ontologies in the imports closure of this
     * ontology should be checked, <code>false</code> if just the signature of this ontology should be chekced.
     * @return If <code>includeImportsClosure=true</code> then returns <code>true</code> if there is an OWLClass with
     * <code>owlClassIRI</code> as its IRI in the signature of at least one ontology in the imports clousre of this ontology
     * and <code>false</code> if this is not the case.  If <code>includeImportsClosure=false</code> then returns <code>true</code>
     * if the signature of this ontology contains an OWLClass that has <code>owlClassIRI</code> as its IRI and <code>false</code>
     * if the signature of this ontology does not contain a class with <code>owlClassIRI</code> as its IRI.
     */
    boolean containsClassInSignature(IRI owlClassIRI, boolean includeImportsClosure);


    /**
     * Determines if the signature of this ontology contains an OWLObjectProperty with the specified IRI.
     *
     * @param owlObjectPropertyIRI The IRI of the OWLObjectProperty to check for.
     * @return <code>true</code> if the signature of this ontology contains an OWLObjectProperty that has <code>owlObjectPropertyIRI</code> as
     * its IRI, otherwise <code>false</code>.
     */
    boolean containsObjectPropertyInSignature(IRI owlObjectPropertyIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature of one of the ontologies in the imports
     * closure of this ontology, contains an OWLObjectProperty that has the specified IRI.
     * @param owlObjectPropertyIRI The IRI of the OWLObjectProperty to check for
     * @param includeImportsClosure <code>true</code> if the signature of the ontologies in the imports closure of this
     * ontology should be checked, <code>false</code> if just the signature of this ontology should be chekced.
     * @return If <code>includeImportsClosure=true</code> then returns <code>true</code> if there is an OWLObjectProperty with
     * <code>owlObjectPropertyIRI</code> as its IRI in the signature of at least one ontology in the imports clousre of this ontology
     * and <code>false</code> if this is not the case.  If <code>includeImportsClosure=false</code> then returns <code>true</code>
     * if the signature of this ontology contains an OWLObjectProperty that has <code>owlObjectPropertyIRI</code> as its IRI and <code>false</code>
     * if the signature of this ontology does not contain a class with <code>owlObjectPropertyIRI</code> as its IRI.
     */
    boolean containsObjectPropertyInSignature(IRI owlObjectPropertyIRI, boolean includeImportsClosure);


    /**
     * Determines if the signature of this ontology contains an OWLDataProperty with the specified IRI.
     *
     * @param owlDataPropertyIRI The IRI of the OWLDataProperty to check for.
     * @return <code>true</code> if the signature of this ontology contains an OWLDataProperty that has <code>owlDataPropertyIRI</code> as
     * its IRI, otherwise <code>false</code>.
     */
    boolean containsDataPropertyInSignature(IRI owlDataPropertyIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature of one of the ontologies in the imports
     * closure of this ontology, contains an OWLDataProperty that has the specified IRI.
     * @param owlDataPropertyIRI The IRI of the OWLDataProperty to check for
     * @param includeImportsClosure <code>true</code> if the signature of the ontologies in the imports closure of this
     * ontology should be checked, <code>false</code> if just the signature of this ontology should be chekced.
     * @return If <code>includeImportsClosure=true</code> then returns <code>true</code> if there is an OWLDataProperty with
     * <code>owlDataPropertyIRI</code> as its IRI in the signature of at least one ontology in the imports clousre of this ontology
     * and <code>false</code> if this is not the case.  If <code>includeImportsClosure=false</code> then returns <code>true</code>
     * if the signature of this ontology contains an OWLDataProperty that has <code>owlDataPropertyIRI</code> as its IRI and <code>false</code>
     * if the signature of this ontology does not contain a class with <code>owlDataPropertyIRI</code> as its IRI.
     */
    boolean containsDataPropertyInSignature(IRI owlDataPropertyIRI, boolean includeImportsClosure);


     /**
     * Determines if the signature of this ontology contains an OWLAnnotationProperty with the specified IRI.
     *
     * @param owlAnnotationPropertyIRI The IRI of the OWLAnnotationProperty to check for.
     * @return <code>true</code> if the signature of this ontology contains an OWLAnnotationProperty that has <code>owlAnnotationPropertyIRI</code> as
     * its IRI, otherwise <code>false</code>.
     */
    boolean containsAnnotationPropertyInSignature(IRI owlAnnotationPropertyIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature of one of the ontologies in the imports
     * closure of this ontology, contains an OWLAnnotationProperty that has the specified IRI.
     * @param owlAnnotationPropertyIRI The IRI of the OWLAnnotationProperty to check for
     * @param includeImportsClosure <code>true</code> if the signature of the ontologies in the imports closure of this
     * ontology should be checked, <code>false</code> if just the signature of this ontology should be chekced.
     * @return If <code>includeImportsClosure=true</code> then returns <code>true</code> if there is an OWLAnnotationProperty with
     * <code>owlAnnotationPropertyIRI</code> as its IRI in the signature of at least one ontology in the imports clousre of this ontology
     * and <code>false</code> if this is not the case.  If <code>includeImportsClosure=false</code> then returns <code>true</code>
     * if the signature of this ontology contains an OWLAnnotationProperty that has <code>owlAnnotationPropertyIRI</code> as its IRI and <code>false</code>
     * if the signature of this ontology does not contain a class with <code>owlAnnotationPropertyIRI</code> as its IRI.
     */
    boolean containsAnnotationPropertyInSignature(IRI owlAnnotationPropertyIRI, boolean includeImportsClosure);


    /**
     * Determines if the signature of this ontology contains an OWLNamedIndividual with the specified IRI.
     *
     * @param owlIndividualIRI The IRI of the OWLNamedIndividual to check for.
     * @return <code>true</code> if the signature of this ontology contains an OWLNamedIndividual that has <code>owlIndividualIRI</code> as
     * its IRI, otherwise <code>false</code>.
     */
    boolean containsIndividualInSignature(IRI owlIndividualIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature of one of the ontologies in the imports
     * closure of this ontology, contains an OWLNamedIndividual that has the specified IRI.
     * @param owlIndividualIRI The IRI of the OWLNamedIndividual to check for
     * @param includeImportsClosure <code>true</code> if the signature of the ontologies in the imports closure of this
     * ontology should be checked, <code>false</code> if just the signature of this ontology should be chekced.
     * @return If <code>includeImportsClosure=true</code> then returns <code>true</code> if there is an OWLNamedIndividual with
     * <code>owlIndividualIRI</code> as its IRI in the signature of at least one ontology in the imports closure of this ontology
     * and <code>false</code> if this is not the case.  If <code>includeImportsClosure=false</code> then returns <code>true</code>
     * if the signature of this ontology contains an OWLNamedIndividual that has <code>owlIndividualIRI</code> as its IRI and <code>false</code>
     * if the signature of this ontology does not contain a class with <code>owlIndividualIRI</code> as its IRI.
     */
    boolean containsIndividualInSignature(IRI owlIndividualIRI, boolean includeImportsClosure);


    /**
     * Determines if the signature of this ontology contains an OWLDatatype with the specified IRI.
     *
     * @param owlDatatypeIRI The IRI of the OWLDatatype to check for.
     * @return <code>true</code> if the signature of this ontology contains an OWLDatatype that has <code>owlDatatypeIRI</code> as
     * its IRI, otherwise <code>false</code>.
     */
    boolean containsDatatypeInSignature(IRI owlDatatypeIRI);

    /**
     * Determines if the signature of this ontology, or possibly the signature of one of the ontologies in the imports
     * closure of this ontology, contains an OWLDatatype that has the specified IRI.
     * @param owlDatatypeIRI The IRI of the OWLDatatype to check for
     * @param includeImportsClosure <code>true</code> if the signature of the ontologies in the imports closure of this
     * ontology should be checked, <code>false</code> if just the signature of this ontology should be chekced.
     * @return If <code>includeImportsClosure=true</code> then returns <code>true</code> if there is an OWLDatatype with
     * <code>owlDatatypeIRI</code> as its IRI in the signature of at least one ontology in the imports closure of this ontology
     * and <code>false</code> if this is not the case.  If <code>includeImportsClosure=false</code> then returns <code>true</code>
     * if the signature of this ontology contains an OWLDatatype that has <code>owlDatatypeIRI</code> as its IRI and <code>false</code>
     * if the signature of this ontology does not contain a class with <code>owlDatatypeIRI</code> as its IRI.
     */
    boolean containsDatatypeInSignature(IRI owlDatatypeIRI, boolean includeImportsClosure);

    /**
     * Gets the entities in the signature of this ontology that have the specified IRI.
     *
     * @param iri The IRI of the entities to be retrieved.
     * @return A set of entities that are in the signature of this ontology that have the specified IRI.  The
     *         set will be empty if there are no entities in the signature of this ontology with the specified IRI.
     */
    Set<OWLEntity> getEntitiesInSignature(IRI iri);

    /**
     * Gets the entities in the signature of this ontology, and possibly the signature of the imports closure of this
     * ontology, that have the specified IRI.
     *
     * @param iri The IRI of the entitied to be retrieved.
     * @param includeImportsClosure Specifies if the signatures of the ontologies in the imports closure of this ontology
     * should also be taken into account
     * @return If <code>includeImportsClosure=true</code> then returns a set of entities that are in the signature of this
     * ontology or the signature of an ontology in the imports closure of this ontology that have <code>iri</code> as their
     * IRI.  If <code>includeImportsClosure=false</code> then returns the entities in the signature of just this ontology
     * that have <code>iri</code> as their IRI.
     */
    Set<OWLEntity> getEntitiesInSignature(IRI iri, boolean includeImportsClosure);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Axioms that form part of a description of a named entity
    //
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets the axioms that form the definition/description of a class.
     *
     * @param cls The class whose describing axioms are to be retrieved.
     * @return A set of class axioms that describe the class.  This set includes
     *         <ul> <li>Subclass axioms where the
     *                  subclass is equal to the specified class
     *              </li>
     *              <li>Equivalent class axioms where the specified class is an
     *         operand in the equivalent class axiom</li> <li>Disjoint class axioms where the specified class is an
     *         operand in the disjoint class axiom</li> <li>Disjoint union axioms, where the specified class is the
     *         named class that is equivalent to the disjoint union</li> </ul> The set that is returned is a copy - it
     *         will not be updated if the ontology changes.  It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    Set<OWLClassAxiom> getAxioms(OWLClass cls);


    /**
     * Gets the axioms that form the definition/description of an object property.
     *
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
     *
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
     *
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
     *
     * @param property The property whose definition axioms are to be retrieved
     * @return A set of axioms that includes <ul><li>Annotation subpropertyOf axioms where the specified property is
     *         the sub property</li><li>Annotation property domain axioms that specify a domain for the specified property</li>
     *         <li>Annotation property range axioms that specify a range for the specified property</li></ul>
     */
    Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty property);

    /**
     * Gets the datatype definition axioms for the specified datatype
     *
     * @param datatype The datatype
     * @return The set of datatype definition axioms for the specified datatype
     */
    Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype datatype);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Annotation axioms
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the <code>SubAnnotationPropertyOfAxiom</code>s where the specified property is the sub-property.
     * @param subProperty The sub-property of the axioms to be retrieved.
     * @return A set of <code>OWLSubAnnotationPropertyOfAxiom</code>s such that the sub-property is equal
     * to <code>subProperty</code>.
     */
    Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty);


    /**
     * Gets the <code>OWLAnnotationPropertyDomainAxiom</code>s where the specified property is the property
     * in the domain axiom.
     * @param property The property that the axiom specifies a domain for.
     * @return A set of <code>OWLAnnotationPropertyDomainAxiom</code>s such that the property is equal
     * to <code>property</code>.
     */
    Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(OWLAnnotationProperty property);



    /**
     * Gets the <code>OWLAnnotationPropertyRangeAxiom</code>s where the specified property is the property
     * in the range axiom.
     * @param property The property that the axiom specifies a range for.
     * @return A set of <code>OWLAnnotationPropertyRangeAxiom</code>s such that the property is equal
     * to <code>property</code>.
     */
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
     *
     * @param subject The entity that is the subject of the set of returned axioms.
     * @return The set of declaration axioms. Note that this set will be a copy and will not be updated if the ontology
     *         changes.  It is therefore safe to iterate over this set while making changes to the ontology.
     */
    Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity subject);

    /**
     * Gets the axioms that annotate the specified entity.
     *
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
     *
     * @param cls The class that is equal to the left hand side of the axiom (subclass).
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls);


    /**
     * Gets all of the subclass axioms where the right hand side (the superclass) is equal to the specified class.
     *
     * @param cls The class
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls);


    /**
     * Gets all of the equivalent axioms in this ontology that contain the specified class as an operand.
     *
     * @param cls The class
     * @return A set of equivalent class axioms that contain the specified class as an operand.  The set that is
     *         returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls);


    /**
     * Gets the set of disjoint class axioms that contain the specified class as an operand.
     *
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
     *
     * @param owlClass The class that indexes the axioms to be retrieved.
     * @return The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass);


    /**
     * Gets the has key axioms that have the specified class as their subject.
     *
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
     * Gets the {@link org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom}s where the sub-property is equal to the specified property.
     *
     * @param subProperty The property which is equal to the sub property of the retrived axioms.
     * @return  A set of {@link org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom}s such that each axiom in the set is of the form
     *          <code>SubObjectPropertyOf(subProperty, pe)</code>.
     *          The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression subProperty);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom}s where the super-property (returned by
     * {@link OWLSubObjectPropertyOfAxiom#getSuperProperty()}) is equal to the specified property.
     *
     * @param superProperty The property which is equal to the super-property of the retrived axioms.
     * @return  A set of {@link org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom}s such that each axiom in the set is of the form
     *          <code>SubObjectPropertyOf(pe, superProperty)</code>.
     *          The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression superProperty);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom}s where the property (returned by
     * {@link org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom#getProperty()}) is equal to the specified property.
     *
     * @param property The property which is equal to the property of the retrived axioms.
     * @return  A set of {@link org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom}s such that each axiom in the
     * set is of the form <code>ObjectPropertyDomain(pe, ce)</code>. The set that is returned is a copy - it will not
     * be updated if the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over
     * this set.
     */
    Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(OWLObjectPropertyExpression property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom}s where the property (returned by
     * {@link org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom#getProperty()}) is equal to the
     * specified property.
     * @param property The property which is equal to the property of the retieved axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom}s such that each axiom in the
     * set is of the form <code>ObjectPropertyRange(property, ce)</code>. The set that is returned is a copy - it will not
     * be updated if the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over
     * this set.
     */
    Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(OWLObjectPropertyExpression property);


    /**
     * Gets the {@link OWLInverseObjectPropertiesAxiom}s where the specified property
     * is contained in the set returned by {@link org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom#getProperties()}.
     * @param property The property which is equal to the property of the retrieved axioms.
     * @return A set of {@link OWLInverseObjectPropertiesAxiom}s such that each axiom in the
     * set is of the form <code>InverseObjectProperties(property, pe)</code> or <code>InverseObjectProperties(pe, property)</code>.
     * The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(OWLObjectPropertyExpression property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom}s that make the specified property
     * equivalent to some other object property expression(s).
     * @param property The property that the retrieved axioms make equivalent to some other property expressions. For each
     * axiom retrieved the set of properties returned by {@link OWLEquivalentObjectPropertiesAxiom#getProperties()} will
     * contain property. 
     * @return A set of {@link org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom}s such that each axiom
     * in the set is of the form <code>EquivalentObjectProperties(pe0, ..., property, ..., pen)</code>. The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(OWLObjectPropertyExpression property);


    
    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom}s that make the specified property
     * disjoint with some other object property expression(s).
     * @param property The property that the retrieved axioms makes disjoint to some other property expressions. For each
     * axiom retrieved the set of properties returned by {@link OWLDisjointObjectPropertiesAxiom#getProperties()} will
     * contain property. 
     * @return A set of {@link org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom}s such that each axiom
     * in the set is of the form <code>DisjointObjectProperties(pe0, ..., property, ..., pen)</code>. The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(OWLObjectPropertyExpression property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom}s contained in this ontology that
     * make the specified object property functional.
     * @param property The property (returned by {@link org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom#getProperty()})
     * that is made functional by the axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom}s such that each axiom in the
     * set is of the form <code>FunctionalObjectProperty(property)</code>.
     */
    Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property);


    /**
     * Gets the {@link OWLInverseFunctionalObjectPropertyAxiom}s contained in this ontology that
     * make the specified object property inverse functional.
     * @param property The property (returned by {@link org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom#getProperty()})
     * that is made inverse functional by the axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom}s such that each axiom in the
     * set is of the form <code>InverseFunctionalObjectProperty(property)</code>.
     */
    Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom}s contained in this ontology that
     * make the specified object property symmetric.
     * @param property The property (returned by {@link org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom#getProperty()})
     * that is made symmetric by the axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom}s such that each axiom in the
     * set is of the form <code>SymmetricObjectProperty(property)</code>.
     */
    Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom}s contained in this ontology that
     * make the specified object property asymmetric.
     * @param property The property (returned by {@link org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom#getProperty()})
     * that is made asymmetric by the axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom}s such that each axiom in the
     * set is of the form <code>AsymmetricObjectProperty(property)</code>.
     */
    Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom}s contained in this ontology that
     * make the specified object property reflexive.
     * @param property The property (returned by {@link org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom#getProperty()})
     * that is made reflexive by the axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom}s such that each axiom in the
     * set is of the form <code>ReflexiveObjectProperty(property)</code>.
     */
    Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom}s contained in this ontology that
     * make the specified object property irreflexive.
     * @param property The property (returned by {@link org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom#getProperty()})
     * that is made irreflexive by the axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom}s such that each axiom in the
     * set is of the form <code>IrreflexiveObjectProperty(property)</code>.
     */
    Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom}s contained in this ontology that
     * make the specified object property transitive.
     * @param property The property (returned by {@link org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom#getProperty()})
     * that is made transitive by the axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom}s such that each axiom in the
     * set is of the form <code>TransitiveObjectProperty(property)</code>.
     */
    Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(OWLObjectPropertyExpression property);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data properties
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom}s where the sub-property is equal to the specified property.
     *
     * @param subProperty The property which is equal to the sub property of the retrived axioms.
     * @return  A set of {@link org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom}s such that each axiom in the set is of the form
     *          <code>SubDataPropertyOf(subProperty, pe)</code>.
     *          The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty subProperty);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom}s where the super-property (returned by
     * {@link OWLSubDataPropertyOfAxiom#getSuperProperty()}) is equal to the specified property.
     *
     * @param superProperty The property which is equal to the super-property of the retrived axioms.
     * @return  A set of {@link org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom}s such that each axiom in the set is of the form
     *          <code>SubDataPropertyOf(pe, superProperty)</code>.
     *          The set that is returned is a copy - it will not be updated if the ontology changes.  It is therefore
     *         safe to apply changes to this ontology while iterating over this set.
     */
    Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression superProperty);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom}s where the property (returned by
     * {@link org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom#getProperty()}) is equal to the specified property.
     *
     * @param property The property which is equal to the property of the retrived axioms.
     * @return  A set of {@link org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom}s such that each axiom in the
     * set is of the form <code>DataPropertyDomain(pe, ce)</code>. The set that is returned is a copy - it will not
     * be updated if the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over
     * this set.
     */
    Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom}s where the property (returned by
     * {@link org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom#getProperty()}) is equal to the
     * specified property.
     * @param property The property which is equal to the property of the retieved axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom}s such that each axiom in the
     * set is of the form <code>DataPropertyRange(property, ce)</code>. The set that is returned is a copy - it will not
     * be updated if the ontology changes.  It is therefore safe to apply changes to this ontology while iterating over
     * this set.
     */
    Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(OWLDataProperty property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom}s that make the specified property
     * equivalent to some other data property expression(s).
     * @param property The property that the retrieved axioms make equivalent to some other property expressions. For each
     * axiom retrieved the set of properties returned by {@link OWLEquivalentDataPropertiesAxiom#getProperties()} will
     * contain property. 
     * @return A set of {@link org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom}s such that each axiom
     * in the set is of the form <code>EquivalentDataProperties(pe0, ..., property, ..., pen)</code>. The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(OWLDataProperty property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom}s that make the specified property
     * disjoint with some other data property expression(s).
     * @param property The property that the retrieved axioms makes disjoint to some other property expressions. For each
     * axiom retrieved the set of properties returned by {@link OWLDisjointDataPropertiesAxiom#getProperties()} will
     * contain property. 
     * @return A set of {@link org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom}s such that each axiom
     * in the set is of the form <code>DisjointDataProperties(pe0, ..., property, ..., pen)</code>. The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(OWLDataProperty property);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom}s contained in this ontology that
     * make the specified data property functional.
     * @param property The property (returned by {@link org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom#getProperty()})
     * that is made functional by the axioms.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom}s such that each axiom in the
     * set is of the form <code>FunctionalDataProperty(property)</code>. The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(OWLDataPropertyExpression property);

    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Individuals
    //
    //////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLClassAssertionAxiom}s contained in this ontology that
     * make the specified <code>individual</code> an instance of some class expression.
     * @param individual The individual that the returned axioms make an instance of some class expression.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLClassAssertionAxiom}s such that each axiom in the set
     * is of the form <code>ClassAssertion(ce, individual)</code> (for each axiom {@link OWLClassAssertionAxiom#getIndividual()} is
     * equal to <code>individual</code>). The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLClassAssertionAxiom}s contained in this ontology that make the
     * specified class expression, <code>ce</code>, a type for some individual.
     * @param ce The class expression that the returned axioms make a type for some individual.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLClassAssertionAxiom}s such that each axiom in the set
     * is of the form <code>ClassAssertion(ce, ind)</code> (for each axiom {@link OWLClassAssertionAxiom#getClassExpression()} is
     * equal to <code>ce</code>). The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClass ce);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom}s contained in this ontology that
     * have the specified <code>individual</code> as the subject of the axiom.
     * @param individual The individual that the returned axioms have as a subject.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom}s such that each axiom in
     * the set is of the form <code>DataPropertyAssertion(dp, individual, l)</code> (for each axiom {@link OWLDataPropertyAssertionAxiom#getSubject()} is
     * equal to <code>individual</code>). The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual individual);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom}s contained in this ontology that
     * have the specified <code>individual</code> as the subject of the axiom.
     * @param individual The individual that the returned axioms have as a subject.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom}s such that each axiom in
     * the set is of the form <code>ObjectPropertyAssertion(dp, individual, obj)</code> (for each axiom {@link OWLObjectPropertyAssertionAxiom#getSubject()} is
     * equal to <code>individual</code>). The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual individual);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom}s contained in this ontology that
     * have the specified <code>individual</code> as the subject of the axiom.
     * @param individual The individual that the returned axioms have as a subject.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom}s such that each axiom in
     * the set is of the form <code>NegativeObjectPropertyAssertion(dp, individual, obj)</code> (for each axiom {@link OWLNegativeObjectPropertyAssertionAxiom#getSubject()} is
     * equal to <code>individual</code>). The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(OWLIndividual individual);


    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom}s contained in this ontology that
     * have the specified <code>individual</code> as the subject of the axiom.
     * @param individual The individual that the returned axioms have as a subject.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom}s such that each axiom in
     * the set is of the form <code>NegativeDataPropertyAssertion(dp, individual, obj)</code> (for each axiom {@link OWLNegativeDataPropertyAssertionAxiom#getSubject()} is
     * equal to <code>individual</code>). The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(OWLIndividual individual);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSameIndividualAxiom}s contained in this ontology that make
     * the specified <code>individual</code> the same as some other individual.
     * @param individual The individual that the returned axioms make the same as some other individual.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLSameIndividualAxiom}s such that each axiom in the set
     * is of the form <code>SameIndividual(individual, ind, ...)</code> (for each axiom returned {@link OWLSameIndividualAxiom#getIndividuals()}
     * contains <code>individual</code>. The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom}s contained in this ontology that make
     * the specified <code>individual</code> different to some other individual.
     * @param individual The individual that the returned axioms make the different as some other individual.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom}s such that each axiom in the set
     * is of the form <code>DifferentIndividuals(individual, ind, ...)</code> (for each axiom returned {@link OWLDifferentIndividualsAxiom#getIndividuals()}
     * contains <code>individual</code>. The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual individual);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom}s contained in this ontology that provide
     * a definition for the specified datatype.
     * @param datatype The datatype for which the returned axioms provide a definition.
     * @return A set of {@link org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom}s such that for each axiom in the
     * set {@link OWLDatatypeDefinitionAxiom#getDatatype()} is equal to <code>datatype</code>. The set that is
     * returned is a copy - it will not be updated if the ontology changes.  It is therefore safe to
     * apply changes to this ontology while iterating over this set.
     */
    Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(OWLDatatype datatype);
}
