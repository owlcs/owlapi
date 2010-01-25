package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
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
 * </p>
 * An <code>OWLOntologyManager</code> manages a set of ontologies. It is the main point for creating, loading and
 * accessing ontologies.
 * </p>
 * An <code>OWLOntologyManager</code> also manages the mapping betweem an ontology and its ontology document.
 */
public interface OWLOntologyManager extends OWLOntologySetProvider {

    /**
     * Gets a data factory which can be used to create OWL API objects such as classes, properties, individuals, axioms
     * etc.
     * @return A reference to a data factory for creating OWL API objects.
     */
    OWLDataFactory getOWLDataFactory();


    /**
     * Gets all of the ontologies that are managed by this manager.
     * @return The set of ontologies managed by this manager.
     */
    Set<OWLOntology> getOntologies();


    /**
     * Gets the ontologies that are managed by this manager that contain the specified axiom.
     * @param axiom The axioms
     * @return The set of ontologies such that for each ontology, O the specified axiom is contained in O.
     */
    Set<OWLOntology> getOntologies(OWLAxiom axiom);

    /**
     * Gets the versions (if any) of the ontology that have the specified IRI
     * @param ontology The ontology IRI
     * @return The set of ontologies that have the specified ontology IRI.
     */
    Set<OWLOntology> getVersions(IRI ontology);

    /**
     * Determines if there is an ontology with the specified IRI, and no version IRI, that is managed by this manager
     * @param ontologyIRI The IRI of the ontology to test for (the version IRI is assumed to be <code>null</code>)
     * @return <code>true</code> if there is an ontology with the specified IRI, and no version IRI, that is managed by this manager,
     *         otherwise <code>false</code>.
     */
    boolean contains(IRI ontologyIRI);


    /**
     * Determines if there is an ontology with the specified id that is managed by this manager
     * @param id The id of the ontology to test for
     * @return <code>true</code> if there is an ontology with the specified id that is managed by this manager,
     *         otherwise <code>false</code>.
     */
    boolean contains(OWLOntologyID id);

    /**
     * Gets a previously loaded/created ontology that has the specified ontology IRI and no version IRI.
     * @param ontologyIRI The IRI of the ontology to be retrieved.
     * @return The ontology that has the specified IRI and no version IRI, or <code>null</code> if this manager does
     * not manage an ontology with the specified IRI and no version IRI.
     */
    OWLOntology getOntology(IRI ontologyIRI);

    /**
     * Gets a previously loaded/created ontology that has the specified ontology ID
     * @param ontologyID The ID of the ontology to retrieve
     * @return The ontology that has the specified ID, or <code>null</code> if this manager does not manage an ontology
     * with the specified ontology ID.
     */
    OWLOntology getOntology(OWLOntologyID ontologyID);


    /**
     * Given an imports declaration, obtains the ontology that this import has been resolved to.
     * @param declaration The declaration that points to the imported ontology.
     * @return The ontology that the imports declaration resolves to, or <code>null</code> if the imports declaration
     * could not be resolved to an ontology, because the ontology was not loaded or has been removed from this
     * manager
     */
    OWLOntology getImportedOntology(OWLImportsDeclaration declaration);


    /**
     * Gets the set of <em>loaded</em> ontologies that the specified ontology is related to via the directlyImports relation as
     * defined in Section 3.4 of the OWL 2 Structural specification
     * @param ontology The ontology whose direct imports are to be retrieved.
     * @return The set of <em>loaded</em> ontologies that the specified ontology is related to via the directlyImports
     * relation.  If the ontology is not managed by this manager then the empty set will be returned.
     */
    Set<OWLOntology> getDirectImports(OWLOntology ontology);

    /**
     * Gets the set of ontologies that are in the transitive closure of the directly imports relation.
     * @param ontology The ontology whose imports are to be retrieved.
     * @return A set of <code>OWLOntology</code>ies that are in the transitive closure of the directly imports relation
     * of this ontology. If, for what ever reason, an imported ontology could not be loaded, then it will not be contained in the
     *         returned set of ontologies. If the ontology is not managed by this manager then the empty set will be returned.
     */
    Set<OWLOntology> getImports(OWLOntology ontology);

    /**
     * Gets the imports closure for the specified ontology.
     * @param ontology The ontology whose imports closure is to be retrieved.
     * @return A <code>Set</code> of ontologies that contains the imports closure for the specified ontology.  This set
     *         will also include the specified ontology. Example: if A imports B and B imports C, then calling this
     *         method with A will return the set consisting of A, B and C. If, for what ever reason, an imported
     *         ontology could not be loaded, then it will not be contained in the returned set of ontologies.
     *          If the ontology is not managed by this manager then the empty set will be returned.
     */
    Set<OWLOntology> getImportsClosure(OWLOntology ontology);


    /**
     * Gets the topologically ordered imports closure.
     * @param ontology The ontology whose imports closure is to be determined.
     * @return A list that represents a topological ordering of the imports closure.  The first element in the list will
     *         be the specified ontology. If the ontology is not managed by this manager then an empty list will be returned.
     *
     */
    List<OWLOntology> getSortedImportsClosure(OWLOntology ontology);



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Ontology change
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Applies a list of changes to some or all of the ontologies that are managed by this manager.  The changes will be
     * applied to the appropriate ontologies.
     * @param changes The changes to be applied.
     * @return The changes that were actually applied.
     * @throws OWLOntologyChangeException If one or more of the changes could not be applied.  See subclasses of
     * ontology change exception for more specific details.
     * @throws OWLOntologyRenameException If one or more of the changes is an instance of {@link org.semanticweb.owlapi.model.SetOntologyID}
     * where the new {@link org.semanticweb.owlapi.model.OWLOntologyID} already belongs to an ontology managed by this
     * manager.
     */
    List<OWLOntologyChange> applyChanges(List<? extends OWLOntologyChange> changes) throws OWLOntologyRenameException;


    /**
     * A convenience method that adds a set of axioms to an ontology.  The appropriate AddAxiom change objects are
     * automatically generated.
     * @param ont    The ontology to which the axioms should be added.
     * @param axioms The axioms to be added.
     * @return A list of ontology changes that represent the changes which took place in order to add the axioms.
     * @throws OWLOntologyChangeException if there was a problem adding the axioms
     */
    List<OWLOntologyChange> addAxioms(OWLOntology ont, Set<? extends OWLAxiom> axioms);


    /**
     * A convenience method that adds a single axiom to an ontology. The appropriate AddAxiom change object is
     * automatically generated.
     * @param ont   The ontology to add the axiom to.
     * @param axiom The axiom to be added
     * @return A list of ontology changes that represent the changes that actually took place.
     * @throws OWLOntologyChangeException if there was a problem adding the axiom
     */
    List<OWLOntologyChange> addAxiom(OWLOntology ont, OWLAxiom axiom);

    /**
     * A convenience method that removes a single axiom from an ontology. The appropriate RemoveAxiom change object is
     * automatically generated.
     * @param ont   The ontology to remove the axiom from.
     * @param axiom The axiom to be removed
     * @return A list of ontology changes that represent the changes that actually took place.
     * @throws OWLOntologyChangeException if there was a problem removing the axiom
     */
    List<OWLOntologyChange> removeAxiom(OWLOntology ont, OWLAxiom axiom);

    /**
     * A convenience method that removes a set of axioms from an ontology.  The appropriate RemoveAxiom change objects are
     * automatically generated.
     * @param ont    The ontology from which the axioms should be removed.
     * @param axioms The axioms to be removed.
     * @return A list of ontology changes that represent the changes which took place in order to remove the axioms.
     * @throws OWLOntologyChangeException if there was a problem removing the axioms
     */
    List<OWLOntologyChange> removeAxioms(OWLOntology ont, Set<? extends OWLAxiom> axioms);


    /**
     * A convenience method that applies just one change to an ontology that is managed by this manager.
     * @param change The change to be applied
     * @return The changes that resulted of the applied ontology change.
     * @throws OWLOntologyChangeException If the change could not be applied.  See subclasses of ontology change
     *                                    exception for more specific details.
     * @throws OWLOntologyRenameException If one or more of the changes is an instance of {@link org.semanticweb.owlapi.model.SetOntologyID}
     * where the new {@link org.semanticweb.owlapi.model.OWLOntologyID} already belongs to an ontology managed by this
     * manager.
     */
    List<OWLOntologyChange> applyChange(OWLOntologyChange change) throws OWLOntologyRenameException;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Ontology creation
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new (empty) ontology that does not have an ontology IRI (and therefore does not have a version IRI).
     * A document IRI will automatically be generated.
     * @return The newly created ontology
     * @throws OWLOntologyCreationException if there was a problem creating the ontology
     */
    OWLOntology createOntology() throws OWLOntologyCreationException;

    /**
     * Creates a new ontology that is initialised to contain specific axioms. The ontology will not have an IRI.
     * The document IRI of the created ontology will be auto-generated.
     * @param axioms The axioms that should be copied into the new ontology
     * @return An ontology without an IRI that contains all of the specified axioms
     * @throws OWLOntologyCreationException if there was a problem creating the new ontology.
     * @throws OWLOntologyChangeException   if there was a problem copying the axioms.
     */
    OWLOntology createOntology(Set<OWLAxiom> axioms) throws OWLOntologyCreationException;


    /**
     * Creates a new ontology that has the specified ontology IRI and is initialised to contain specific axioms.
     * @param ontologyIRI The IRI of the new ontology.
     * </p>
     * The ontology document IRI of the created ontology will be set to the value returned
     * by any installed {@link org.semanticweb.owlapi.model.OWLOntologyIRIMapper}s.  If no mappers are installed
     * or the ontology IRI was not mapped to a document IRI by any of the installed mappers, then the ontology document
     * IRI will be set to the value of <code>ontologyIRI</code>.
     *
     * @param axioms      The axioms that should be copied into the new ontology
     * @return An ontology that has the specified IRI and contains all of the specified axioms
     * @throws OWLOntologyCreationException if there was a problem creating the new ontology, if the new ontology
     *                                      already exists in this manager.
     * @throws OWLOntologyChangeException   if there was a problem copying the axioms.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology with the specified
     * <code>ontologyIRI</code>.
     * @throws OWLOntologyDocumentAlreadyExistsException if the specified <code>ontologyIRI</code> is mapped to a
     * ontology document IRI for which there already exists a mapping in this manager. 
     */
    OWLOntology createOntology(Set<OWLAxiom> axioms, IRI ontologyIRI) throws OWLOntologyCreationException;


    /**
     * Creates a new (empty) ontology that has the specified ontology IRI (and no version IRI).
     * </p>
     * The ontology document IRI of the created ontology will be set to the value returned
     * by any installed {@link org.semanticweb.owlapi.model.OWLOntologyIRIMapper}s.  If no mappers are installed
     * or the ontology IRI was not mapped to a document IRI by any of the installed mappers, then the ontology document
     * IRI will be set to the value of <code>ontologyIRI</code>.
     * @param ontologyIRI The IRI of the ontology to be created.  The ontology IRI will be mapped to a document IRI in
     *                    order to determine the type of ontology factory that will be used to create the ontology.  If
     *                    this mapping is <code>null</code> then a default (in memory) implementation of the ontology
     *                    will most likely be created.
     * @return The newly created ontology, or if an ontology with the specified IRI already exists then this existing
     *         ontology will be returned.
     * @throws OWLOntologyCreationException If the ontology could not be created.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology with the specified
     * <code>ontologyIRI</code> (and no version IRI).
     * @throws OWLOntologyDocumentAlreadyExistsException if the specified <code>ontologyIRI</code> is mapped to a
     * ontology document IRI for which there already exists a mapping in this manager.
     */
    OWLOntology createOntology(IRI ontologyIRI) throws OWLOntologyCreationException;


    /**
     * Creates a new (empty) ontology that has the specified ontology ID.
     * @param ontologyID The ID of the ontology to be created.
     * </p>
     * The ontology document IRI of the created ontology will be set to the value returned
     * by any installed {@link org.semanticweb.owlapi.model.OWLOntologyIRIMapper}s.  If no mappers are installed
     * or the ontology IRI was not mapped to a document IRI by any of the installed mappers, then the ontology document
     * IRI will be set to the value of <code>ontologyIRI</code>.
     * @return The newly created ontology, or if an ontology with the specified IRI already exists then this existing
     *         ontology will be returned.
     * @throws OWLOntologyCreationException If the ontology could not be created.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology with the specified
     * <code>ontologyID</code> (and no version IRI).
     * @throws OWLOntologyDocumentAlreadyExistsException if the specified <code>ontologyID</code> is mapped to a
     * ontology document IRI for which there already exists a mapping in this manager.
     */
    OWLOntology createOntology(OWLOntologyID ontologyID) throws OWLOntologyCreationException;


    /**
     * Creates a new ontology that has the specified ontology IRI and is initialised to contain the axioms that are
     * contained in the specified ontologies.  Note that the specified ontologies need not be managed by this manager.
     * </p>
     * The ontology document IRI of the created ontology will be set to the value returned
     * by any installed {@link org.semanticweb.owlapi.model.OWLOntologyIRIMapper}s.  If no mappers are installed
     * or the ontology IRI was not mapped to a document IRI by any of the installed mappers, then the ontology document
     * IRI will be set to the value of <code>ontologyIRI</code>.
     * @param ontologyIRI           The IRI of the new ontology.
     * @param ontologies            The ontologies whose axioms should be copied into the new ontology
     * @param copyLogicalAxiomsOnly If set to <code>true</code> only logical axioms are copied into the new ontology.
     *                              If set to <code>false</code> then all axioms (including annotation axioms) are
     *                              copied into the new ontology.
     * @return An ontology that has the specified IRI and contains all of the axioms that are contained in the specified
     *         ontologies possibly minus all non-logical axioms
     * @throws OWLOntologyCreationException if there was a problem creating the new ontology, if the new ontology
     *                                      already exists in this manager.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology with the specified
     * <code>ontologyIRI</code> (and no ontology version IRI).
     * @throws OWLOntologyDocumentAlreadyExistsException if the specified <code>ontologyIRI</code> is mapped to a
     * ontology document IRI for which there already exists a mapping in this manager.
     */
    OWLOntology createOntology(IRI ontologyIRI, Set<OWLOntology> ontologies, boolean copyLogicalAxiomsOnly) throws OWLOntologyCreationException;


    /**
     * Creates a new ontology that has the specified ontology IRI and is initialised to contain the axioms that are
     * contained in the specified ontologies.  Note that the specified ontologies need not be managed by this manager.
     * </p>
     * The ontology document IRI of the created ontology will be set to the value returned
     * by any installed {@link org.semanticweb.owlapi.model.OWLOntologyIRIMapper}s.  If no mappers are installed
     * or the ontology IRI was not mapped to a document IRI by any of the installed mappers, then the ontology document
     * IRI will be set to the value of <code>ontologyIRI</code>.
     * @param ontologyIRI The IRI of the new ontology.
     * @param ontologies  The ontologies whose axioms should be copied into the new ontology
     * @return An ontology that has the specified IRI and contains all of the axioms that are contained in the specified
     *         ontologies
     * @throws OWLOntologyCreationException if there was a problem creating the new ontology, if the new ontology
     *                                      already exists in this manager.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology with the specified
     * <code>ontologyIRI</code> (and no version IRI).
     * @throws OWLOntologyDocumentAlreadyExistsException if the specified <code>ontologyIRI</code> is mapped to a
     * ontology document IRI for which there already exists a mapping in this manager.
     */
    OWLOntology createOntology(IRI ontologyIRI, Set<OWLOntology> ontologies) throws OWLOntologyCreationException;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Loading
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Loads an ontology that is assumed to have the specified <code>ontologyIRI</code> as its IRI or version IRI.
     * </p>
     * The ontology IRI will be mapped to an ontology document IRI.  The mapping will be determined using one of the loaded
     * {@link OWLOntologyIRIMapper} objects. By default, if no custom <code>OWLOntologyIRIMapper</code>s have been registered
     * using the {@link #addIRIMapper(OWLOntologyIRIMapper)} method, or no mapping can be found, the ontology document
     * IRI is taken to be the specified ontology IRI. 
     * @param ontologyIRI The IRI that identifies the ontology.  It is expected that the ontology will also have this
     * IRI (although the OWL API will tolerated situations where this is not the case).
     * @return The <code>OWLOntology</code> representation of the ontology that was loaded.
     * @throws OWLOntologyCreationException If there was a problem in creating and loading the ontology.
     * @throws org.semanticweb.owlapi.io.UnparsableOntologyException if the ontology was being parsed from a document and
     * the document contained syntax errors.
     * @throws UnloadableImportException if the ontology imports ontologies and one of the imports could not be loaded
     * for what ever reason. If silent missing imports handling is set to <code>true</code> then this exception will
     * not be thrown.  The <code>UnloadableImportException</code> contains information about the import declaration
     * that triggered the import and the cause of this exception is an <code>OWLOntologyCreationException</code>
     * which contains information about why the import could not be loaded.
     * @throws org.semanticweb.owlapi.io.OWLOntologyCreationIOException if there was an <code>IOException</code>
     * when trying to load the ontology.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology with the specified
     * <code>ontologyIRI</code> (where the ontology doesn't have a version IRI).
     * @throws OWLOntologyDocumentAlreadyExistsException if the specified <code>ontologyIRI</code> is mapped to a
     * ontology document IRI for which there already exists a mapping in this manager.
     */
    OWLOntology loadOntology(IRI ontologyIRI) throws OWLOntologyCreationException;


    /**
     * Loads an ontology from an ontology document specified by an IRI.  In contrast the the {@link #loadOntology(IRI)}
     * method, <i>no mapping</i> is performed on the specified IRI.
     * @param documentIRI The ontology document IRI where the ontology will be loaded from.
     * @return The ontology that was loaded.
     * @throws OWLOntologyCreationException If there was a problem in creating and loading the ontology.
     * @throws org.semanticweb.owlapi.io.UnparsableOntologyException if the ontology was being parsed from a document and
     * the document contained syntax errors.
     * @throws UnloadableImportException if the ontology imports ontologies and one of the imports could not be loaded
     * for what ever reason. If silent missing imports handling is set to <code>true</code> then this exception will
     * not be thrown.  The <code>UnloadableImportException</code> contains information about the import declaration
     * that triggered the import and the cause of this exception is an <code>OWLOntologyCreationException</code>
     * which contains information about why the import could not be loaded.
     * @throws org.semanticweb.owlapi.io.OWLOntologyCreationIOException if there was an <code>IOException</code>
     * when trying to load the ontology.
     * @throws OWLOntologyDocumentAlreadyExistsException if the specified <code>documentIRI</code> is already
     * the document IRI for a loaded ontology.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology whose ontology IRI and
     * version IRI is the same as the ontology IRI and version IRI of the ontology contained in the document pointed
     * to by <code>documentIRI</code>.
     */
    OWLOntology loadOntologyFromOntologyDocument(IRI documentIRI) throws OWLOntologyCreationException;

    /**
     * Loads an ontology from an ontology document contained in a local file.  The loaded ontology will be assigned
     * a document IRI that corresponds to the file IRI.
     * @param file The file that contains a representation of an ontology
     * @return The ontology that was parsed from the file.
     * @throws OWLOntologyCreationException If there was a problem in creating and loading the ontology.
     * @throws org.semanticweb.owlapi.io.UnparsableOntologyException if the ontology could not be parsed.
     * @throws UnloadableImportException if the ontology imports ontologies and one of the imports could not be loaded
     * for what ever reason. If silent missing imports handling is set to <code>true</code> then this exception will
     * not be thrown.  The <code>UnloadableImportException</code> contains information about the import declaration
     * that triggered the import and the cause of this exception is an <code>OWLOntologyCreationException</code>
     * which contains information about why the import could not be loaded.
     * @throws org.semanticweb.owlapi.io.OWLOntologyCreationIOException if there was an <code>IOException</code>
     * when trying to load the ontology.
     * @throws OWLOntologyDocumentAlreadyExistsException if the IRI of the specified file is already
     * the document IRI for a loaded ontology.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology whose ontology IRI and
     * version IRI is the same as the ontology IRI and version IRI of the ontology contained in the document pointed
     * to by <code>documentIRI</code>.
     */
    OWLOntology loadOntologyFromOntologyDocument(File file) throws OWLOntologyCreationException;

     /**
     * Loads an ontology from an ontology document obtained from an input stream.  The loaded ontology will be assigned
     * an auto-generated document IRI with "inputstream" as its scheme.
     * @param inputStream The input stream that can be used to obtain a representation of an ontology
     * @return The ontology that was parsed from the input stream.
     * @throws OWLOntologyCreationException If there was a problem in creating and loading the ontology.
     * @throws org.semanticweb.owlapi.io.UnparsableOntologyException if the ontology could not be parsed.
     * @throws UnloadableImportException if the ontology imports ontologies and one of the imports could not be loaded
     * for what ever reason. If silent missing imports handling is set to <code>true</code> then this exception will
     * not be thrown.  The <code>UnloadableImportException</code> contains information about the import declaration
     * that triggered the import and the cause of this exception is an <code>OWLOntologyCreationException</code>
     * which contains information about why the import could not be loaded.
     * @throws org.semanticweb.owlapi.io.OWLOntologyCreationIOException if there was an <code>IOException</code>
     * when trying to load the ontology.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology whose ontology IRI and
     * version IRI is the same as the ontology IRI and version IRI of the ontology obtained from parsing the content
      * of the input stream.
     */
    OWLOntology loadOntologyFromOntologyDocument(InputStream inputStream) throws OWLOntologyCreationException;

    /**
     * A convenience method that load an ontology from an input source.
     * @param documentSource The input source that describes where the ontology should be loaded from.
     * @return The ontology that was loaded.
     * @throws OWLOntologyCreationException If there was a problem in creating and loading the ontology.
     * @throws org.semanticweb.owlapi.io.UnparsableOntologyException if the ontology was being parsed from a document and
     * the document contained syntax errors.
     * @throws UnloadableImportException if the ontology imports ontologies and one of the imports could not be loaded
     * for what ever reason. If silent missing imports handling is set to <code>true</code> then this exception will
     * not be thrown.  The <code>UnloadableImportException</code> contains information about the import declaration
     * that triggered the import and the cause of this exception is an <code>OWLOntologyCreationException</code>
     * which contains information about why the import could not be loaded.
     * @throws org.semanticweb.owlapi.io.OWLOntologyCreationIOException if there was an <code>IOException</code>
     * when trying to load the ontology.
     * @throws OWLOntologyDocumentAlreadyExistsException if the document IRI of the input source is already
     * the document IRI for a loaded ontology.
     * @throws OWLOntologyAlreadyExistsException if the manager already contains an ontology whose ontology IRI and
     * version IRI is the same as the ontology IRI and version IRI of the ontology contained in the document
     * represented by the input source.
     */
    OWLOntology loadOntologyFromOntologyDocument(OWLOntologyDocumentSource documentSource) throws OWLOntologyCreationException;

    /**
     * Attempts to remove an ontology.  The ontology which is identified by the specified IRI is removed regardless of
     * whether it is referenced by other ontologies via imports statements.
     * @param ontology The ontology to be removed.  If this manager does not manage the ontology then nothing happens.
     */
    void removeOntology(OWLOntology ontology);


    /**
     * Gets the document IRI for a given ontology.  This will either be the document IRI from where the ontology was
     * obtained from during loading, or the document IRI which was specified (via a mapper) when the (empty) ontology
     * was created.  Note that this may not correspond to the first document IRI found in the list of mappings from
     * ontology IRI to document IRI. The reason for this is that it might not have been possible to load the ontology
     * from the first document IRI found in the mapping table.
     * @param ontology The ontology whose document IRI is to be obtained.
     * @return The document IRI of the ontology or <code>null</code>.
     * @throws UnknownOWLOntologyException If the specified ontology is not managed by this manager.
     */
    IRI getOntologyDocumentIRI(OWLOntology ontology);


    /**
     * Overrides the current document IRI for a given ontology.  This method does not alter the IRI mappers which are
     * installed, but alters the actual document IRI of an ontology that has already been loaded.
     * @param ontology    The ontology that has already been loaded.
     * @param documentIRI The new ontology document IRI
     * @throws UnknownOWLOntologyException If the specified ontology is not managed by this manager.
     */
    void setOntologyDocumentIRI(OWLOntology ontology, IRI documentIRI) throws UnknownOWLOntologyException;


    /**
     * Gets the ontology format for the specified ontology.
     * @param ontology The ontology whose format it to be obtained.
     * @return The format of the ontology.
     * @throws UnknownOWLOntologyException If the specified ontology is not managed by this manager.
     */
    OWLOntologyFormat getOntologyFormat(OWLOntology ontology) throws UnknownOWLOntologyException;


    /**
     * Sets the format for the specified ontology.
     * @param ontology       The ontology whose format is to be set.
     * @param ontologyFormat The format for the specified ontology.
     * @throws UnknownOWLOntologyException If the specified ontology is not managed by this manager.
     */
    void setOntologyFormat(OWLOntology ontology, OWLOntologyFormat ontologyFormat);


    /**
     * Saves the specified ontology.  The ontology will be saved to the location that it was loaded from, or if it was
     * created programmatically, it will be saved to the location specified by an ontology IRI mapper at creation time.
     * The ontology will be saved in the same format which it was loaded from, or the default ontology format if the
     * ontology was created programmatically.
     * @param ontology The ontology to be saved.
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with saving the ontology,
     *                                     or the ontology can't be saved in the format it was loaded from.
     * @throws UnknownOWLOntologyException if this manager does not manage the specified ontology
     */
    void saveOntology(OWLOntology ontology) throws OWLOntologyStorageException;


    /**
     * Saves the specified ontology, using the specified document IRI to determine where/how the ontology should be saved.
     * @param ontology    The ontology to be saved.
     * @param documentIRI The document IRI where the ontology should be saved to
     * @throws OWLOntologyStorageException If the ontology cannot be saved
     * @throws UnknownOWLOntologyException if the specified ontology is not managed by this manager.
     */
    void saveOntology(OWLOntology ontology, IRI documentIRI) throws OWLOntologyStorageException;


    /**
     * Saves the specified ontology, to the specified output stream
     * @param ontology The ontology to be saved.
     * @param outputStream The output stream where the ontology will be saved to
     * @throws OWLOntologyStorageException If there was a problem saving this ontology to the specified output stream
     * @throws UnknownOWLOntologyException if this manager does not manage the specified ontology.
     */
    void saveOntology(OWLOntology ontology, OutputStream outputStream) throws OWLOntologyStorageException;

    /**
     * Saves the specified ontology in the specified ontology format to its document URI.
     * @param ontology The ontology to be saved.
     * @param ontologyFormat The format in which the ontology should be saved.
     * @throws OWLOntologyStorageException If the ontology cannot be saved.
     * @throws UnknownOWLOntologyException if the specified ontology is not managed by this manager
     */
    void saveOntology(OWLOntology ontology, OWLOntologyFormat ontologyFormat) throws OWLOntologyStorageException;

    /**
     * Saves the specified ontology to the specified document IRI in the specified ontology format.
     * @param ontology The ontology to be saved
     * @param ontologyFormat The format in which to save the ontology
     * @param documentIRI The document IRI where the ontology should be saved to
     * @throws OWLOntologyStorageException If the ontology could not be saved.
     * @throws UnknownOWLOntologyException if the specified ontology is not managed by the manager.
     */
    void saveOntology(OWLOntology ontology, OWLOntologyFormat ontologyFormat, IRI documentIRI) throws OWLOntologyStorageException;

    /**
     * Saves the specified ontology to the specified output stream in the specified ontology format.
     * @param ontology The ontology to be saved
     * @param ontologyFormat The format in which to save the ontology
     * @param outputStream The output stream where the ontology will be saved to.
     * @throws OWLOntologyStorageException If the ontology could not be saved.
     * @throws UnknownOWLOntologyException if the specified ontology is not managed by the manager.
     */
    void saveOntology(OWLOntology ontology, OWLOntologyFormat ontologyFormat, OutputStream outputStream) throws OWLOntologyStorageException;


    /**
     * Saves the specified ontology to the specified {@link org.semanticweb.owlapi.io.OWLOntologyDocumentTarget}.
     * @param ontology The ontology to be saved.
     * @param documentTarget The output target where the ontology will be saved to.
     * @throws OWLOntologyStorageException If the ontology could not be saved.
     * @throws UnknownOWLOntologyException if the specified ontology is not managed by this manager.
     */
    void saveOntology(OWLOntology ontology, OWLOntologyDocumentTarget documentTarget) throws OWLOntologyStorageException;

    /**
     * Saves the specified ontology to the specified output target in the specified ontology format.
     * @param ontology The ontology to be saved.
     * @param ontologyFormat The output format in which to save the ontology
     * @param documentTarget The output target where the ontology will be saved to
     * @throws OWLOntologyStorageException If the ontology could not be saved.
     * @throws UnknownOWLOntologyException If the specified ontology is not managed by this manager.
     */
    void saveOntology(OWLOntology ontology, OWLOntologyFormat ontologyFormat, OWLOntologyDocumentTarget documentTarget) throws OWLOntologyStorageException;


    /**
     * Adds a mapper to this manager.  The mapper is used to obtain ontology document IRIs for ontology IRIs.  The
     * mapper will be added so that it is given the highest priority (i.e. it will be tried first).
     * @param mapper The mapper to be added.
     */
    void addIRIMapper(OWLOntologyIRIMapper mapper);


    /**
     * Removes a mapper from this manager.
     * @param mapper The mapper to be removed. If this manager does not managed the specified mapper then nothing will
     * happen.
     */
    void removeIRIMapper(OWLOntologyIRIMapper mapper);


    /**
     * Clears any installed IRI mappers
     */
    void clearIRIMappers();


    /**
     * Adds an ontology factory that is capable of creating an ontology given a particular document IRI.
     * @param factory The factory to be added.
     */
    void addOntologyFactory(OWLOntologyFactory factory);


    /**
     * Removes a previously added factory.
     * @param factory The factory to be removed.
     */
    void removeOntologyFactory(OWLOntologyFactory factory);


    /**
     * Add an ontology storer.
     * @param storer The storer to be added
     */
    void addOntologyStorer(OWLOntologyStorer storer);


    /**
     * Removes a previously added storer
     * @param storer The storer to be removed
     */
    void removeOntologyStorer(OWLOntologyStorer storer);


    /**
     * Adds an ontology change listener, which listens to all changes for all ontologies.  To customise the
     * changes/ontologies that are listened to, the <code>addOntologyChangeListener</code> method which takes a
     * broadcast strategy as an argument should be used.
     * @param listener The listener to be added.
     */
    void addOntologyChangeListener(OWLOntologyChangeListener listener);


    /**
     * Adds an ontology change listener, which listens to ontology changes. An ontology change broadcast strategy must
     * be specified, which determines the changes that are broadcast to the listener.
     * @param listener The listener to be added.
     * @param strategy The strategy that should be used for broadcasting changes to the listener.
     */
    void addOntologyChangeListener(OWLOntologyChangeListener listener, OWLOntologyChangeBroadcastStrategy strategy);

    /**
     * Sets the default strategy that is used to broadcast ontology changes.
     * @param strategy The strategy to be used for broadcasting changes.  This strategy will override any previously
     *                 set broadcast strategy.  The strategy should not be <code>null</code>.
     * @see {@link org.semanticweb.owlapi.model.DefaultChangeBroadcastStrategy}
     * @see {@link org.semanticweb.owlapi.model.EDTChangeBroadcastStrategy}
     */
    void setDefaultChangeBroadcastStrategy(OWLOntologyChangeBroadcastStrategy strategy);

    /**
     * Removes a previously added listener.
     * @param listener The listener to be removed.
     */
    void removeOntologyChangeListener(OWLOntologyChangeListener listener);


    /**
     * Requests that the manager loads an imported ontology that is described by an imports statement.  This method is
     * generally used by parsers and other kinds of loaders.  For simply loading an ontology, use the loadOntologyXXX
     * methods.
     * @param declaration The declaration that describes the import to be loaded.
     * @throws UnloadableImportException if there was a problem creating and loading the import and
     * silent missing imports handling is not turned on.  If silent missing import handling is turned on then
     * this exception will not be thrown.
     */
    void makeLoadImportRequest(OWLImportsDeclaration declaration) throws UnloadableImportException;


    /**
     * The default behaviour when an import cannot be loaded is to throw an exception.  This completely stops the
     * loading process.  If it is desired that loading continues then this option can be set with this method.
     * @param b <code>true</code> if loading should continue when an imported ontology cannot be loaded, other wise
     *          <code>false</code>.  The default value is <code>false</code>.
     */
    void setSilentMissingImportsHandling(boolean b);


    /**
     * Determines if silent missing imports handling is enabled.
     * @return <code>true</code> if silent missing imports handler is enabled, otherwise <code>false</code>.
     */
    boolean isSilentMissingImportsHandling();


    /**
     * In the case where silent missing imports handling is enabled, a listener can be attached via this method so that
     * there is a mechanism that allows clients to be informed of the reason when an import cannot be loaded.
     * @param listener The listener to be added.
     */
    void addMissingImportListener(MissingImportListener listener);


    /**
     * Removes a previously added missing import listener.
     * @param listener The listener to be removed.
     */
    void removeMissingImportListener(MissingImportListener listener);


    /**
     * Adds an ontology loaded listener to this manager.
     * @param listener The listener to be added.
     */
    void addOntologyLoaderListener(OWLOntologyLoaderListener listener);

    /**
     * Removes a previously added ontology loaded listener.
     * @param listener The listener to be removed.
     */
    void removeOntologyLoaderListener(OWLOntologyLoaderListener listener);

    /**
     * Adds an ontology change progress listener.
     * @param listener The listener to be added.
     */
    void addOntologyChangeProgessListener(OWLOntologyChangeProgressListener listener);

    /**
     * Removes a previously added ontology change listener.
     * @param listener The listener to be removed.
     */
    void removeOntologyChangeProgessListener(OWLOntologyChangeProgressListener listener);


}
