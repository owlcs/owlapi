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

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.CollectionFactory.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import org.semanticweb.owlapi.OWLAPIConfigProvider;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyStorageIOException;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.io.OntologyIRIMappingNotFoundException;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.ChangeApplied;
import org.semanticweb.owlapi.model.parameters.OntologyCopy;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAnnotationPropertyTransformer;
import org.semanticweb.owlapi.util.PriorityCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import uk.ac.manchester.cs.owl.owlapi.concurrent.ConcurrentPriorityCollection;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLOntologyManagerImpl implements OWLOntologyManager, OWLOntologyFactory.OWLOntologyCreationHandler,
    Serializable {

    private static final long serialVersionUID = 40000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(OWLOntologyManagerImpl.class);
    @Nonnull protected final Map<OWLOntologyID, OWLOntology> ontologiesByID = createSyncMap();
    @Nonnull protected final Map<OWLOntologyID, IRI> documentIRIsByID = createSyncMap();
    @Nonnull protected final Map<OWLOntologyID, OWLOntologyLoaderConfiguration> ontologyConfigurationsByOntologyID = new HashMap<>();
    @Nonnull protected final Map<OWLOntologyID, OWLDocumentFormat> ontologyFormatsByOntology = createSyncMap();
    @Nonnull protected final Map<OWLImportsDeclaration, OWLOntologyID> ontologyIDsByImportsDeclaration = createSyncMap();
    protected final AtomicInteger loadCount = new AtomicInteger(0);
    protected final AtomicInteger importsLoadCount = new AtomicInteger(0);
    @Nonnull protected final Set<IRI> importedIRIs = createSyncSet();
    @Nonnull protected final OWLDataFactory dataFactory;
    @Nonnull protected final Map<OWLOntologyID, Set<OWLOntology>> importsClosureCache = createSyncMap();
    @Nonnull protected final List<MissingImportListener> missingImportsListeners = createSyncList();
    @Nonnull protected final List<OWLOntologyLoaderListener> loaderListeners = createSyncList();
    @Nonnull protected final List<OWLOntologyChangeProgressListener> progressListeners = createSyncList();
    @Nonnull protected final AtomicLong autoGeneratedURICounter = new AtomicLong();
    private final AtomicBoolean broadcastChanges = new AtomicBoolean(true);
    @Nonnull protected OWLOntologyChangeBroadcastStrategy defaultChangeBroadcastStrategy = new DefaultChangeBroadcastStrategy();
    @Nonnull protected ImpendingOWLOntologyChangeBroadcastStrategy defaultImpendingChangeBroadcastStrategy = new DefaultImpendingChangeBroadcastStrategy();
    private transient Map<OWLOntologyChangeListener, OWLOntologyChangeBroadcastStrategy> listenerMap = createSyncMap();
    private transient Map<ImpendingOWLOntologyChangeListener, ImpendingOWLOntologyChangeBroadcastStrategy> impendingChangeListenerMap = createSyncMap();
    private transient List<OWLOntologyChangesVetoedListener> vetoListeners = new ArrayList<>();
    @Nonnull private Provider<OWLOntologyLoaderConfiguration> configProvider = new OWLAPIConfigProvider();
    @Nonnull private Optional<OWLOntologyLoaderConfiguration> config = Optional.absent();
    @Nonnull protected final PriorityCollection<OWLOntologyIRIMapper> documentMappers;
    @Nonnull protected final PriorityCollection<OWLOntologyFactory> ontologyFactories;
    @Nonnull protected final PriorityCollection<OWLParserFactory> parserFactories;
    @Nonnull protected final PriorityCollection<OWLStorerFactory> ontologyStorers;
    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;

    /**
     * @param dataFactory
     *        data factory
     */
    @Inject
    public OWLOntologyManagerImpl(@Nonnull OWLDataFactory dataFactory, ReadWriteLock readWriteLock) {
        this(dataFactory, readWriteLock, PriorityCollectionSorting.ON_SET_INJECTION_ONLY);
    }

    public OWLOntologyManagerImpl(@Nonnull OWLDataFactory dataFactory, ReadWriteLock readWriteLock,
        PriorityCollectionSorting sorting) {
        this.dataFactory = checkNotNull(dataFactory, "dataFactory cannot be null");
        this.readWriteLock = readWriteLock;
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
        documentMappers = new ConcurrentPriorityCollection<>(readWriteLock, sorting);
        ontologyFactories = new ConcurrentPriorityCollection<>(readWriteLock, sorting);
        parserFactories = new ConcurrentPriorityCollection<>(readWriteLock, sorting);
        ontologyStorers = new ConcurrentPriorityCollection<>(readWriteLock, sorting);
        installDefaultURIMappers();
        installDefaultOntologyFactories();
    }

    /**
     * @param first
     *        first id
     * @param second
     *        second id
     * @return true if the ids are equal or have the same ontology IRI. Ontology
     *         version is ignored.
     */
    private static boolean matchingIDs(OWLOntologyID first, OWLOntologyID second) {
        if (first.isAnonymous() || second.isAnonymous()) {
            return first.equals(second);
        }
        return first.getOntologyIRI().equals(second.getOntologyIRI());
    }

    @Nonnull
    protected static IRI getNextAutoGeneratedIRI() {
        return OWLOntologyDocumentSourceBase.getNextDocumentIRI("owlapi:ontology#ont");
    }

    @Override
    public void setOntologyLoaderConfigurationProvider(Provider<OWLOntologyLoaderConfiguration> provider) {
        writeLock.lock();
        try {
            configProvider = provider;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @Nonnull
    public OWLOntologyLoaderConfiguration getOntologyLoaderConfiguration() {
        readLock.lock();
        try {
            if (config.isPresent()) {
                return config.get();
            }
            config = Optional.of(configProvider.get());
            return config.get();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void setOntologyLoaderConfiguration(OWLOntologyLoaderConfiguration newConfig) {
        writeLock.lock();
        try {
            config = Optional.fromNullable(newConfig);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public OWLDataFactory getOWLDataFactory() {
        return dataFactory;
    }

    @Override
    public Set<OWLOntology> getOntologies() {
        readLock.lock();
        try {
            return new HashSet<>(ontologiesByID.values());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntology> getOntologies(OWLAxiom axiom) {
        readLock.lock();
        try {
            Set<OWLOntology> result = new HashSet<>(ontologiesByID.size());
            for (OWLOntology ont : getOntologies()) {
                if (ont.containsAxiom(axiom)) {
                    result.add(ont);
                }
            }
            return result;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains(OWLOntology ontology) {
        readLock.lock();
        try {
            return ontologiesByID.containsValue(ontology);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains(IRI ontologyIRI) {
        checkNotNull(ontologyIRI, "Ontology IRI cannot be null");
        readLock.lock();
        try {
            Set<OWLOntologyID> owlOntologyIDs = ontologiesByID.keySet();
            for (OWLOntologyID nextOntologyID : owlOntologyIDs) {
                if (ontologyIRI.equals(nextOntologyID.getOntologyIRI().orNull())) {
                    return true;
                }
            }
            for (OWLOntologyID ont : owlOntologyIDs) {
                if (ontologyIRI.equals(ont.getVersionIRI().orNull())) {
                    return true;
                }
            }
            // FIXME:
            // ParsableOWLOntologyFactory seems to call this method with a
            // document/physical IRI,
            // but this method fails the general case where the ontology was
            // loaded
            // from the given IRI directly, but was then renamed
            return false;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains(OWLOntologyID id) {
        readLock.lock();
        try {
            if (ontologiesByID.containsKey(id)) {
                return true;
            }
            for (OWLOntologyID nextOntologyID : ontologiesByID.keySet()) {
                if (!id.isAnonymous() && id.getOntologyIRI().equals(nextOntologyID.getOntologyIRI())) {
                    return true;
                }
            }
            return false;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsVersion(IRI ontologyVersionIRI) {
        readLock.lock();
        try {
            for (OWLOntologyID ont : ontologiesByID.keySet()) {
                if (ontologyVersionIRI.equals(ont.getVersionIRI().orNull())) {
                    return true;
                }
            }
            return false;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntologyID> getOntologyIDsByVersion(IRI ontologyVersionIRI) {
        readLock.lock();
        try {
            Set<OWLOntologyID> result = new TreeSet<>();
            for (OWLOntologyID ont : ontologiesByID.keySet()) {
                if (ontologyVersionIRI.equals(ont.getVersionIRI().orNull())) {
                    result.add(ont);
                }
            }
            return result;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public OWLOntology getOntology(IRI ontologyIRI) {
        readLock.lock();
        try {
            OWLOntologyID ontologyID = new OWLOntologyID(of(ontologyIRI), of((IRI) null));
            OWLOntology result = ontologiesByID.get(ontologyID);
            if (result == null) {
                for (OWLOntologyID nextOntologyID : ontologiesByID.keySet()) {
                    if (ontologyIRI.equals(nextOntologyID.getVersionIRI().orNull()) || ontologyIRI.equals(nextOntologyID
                        .getOntologyIRI().orNull()) || ontologyIRI.equals(nextOntologyID.getDefaultDocumentIRI()
                            .orNull())) {
                        result = ontologiesByID.get(nextOntologyID);
                    }
                }
            }
            return result;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public OWLOntology getOntology(OWLOntologyID ontologyID) {
        readLock.lock();
        try {
            OWLOntology result = ontologiesByID.get(ontologyID);
            if (result == null && !ontologyID.isAnonymous()) {
                for (OWLOntologyID nextOntologyID : ontologiesByID.keySet()) {
                    if (matchingIDs(ontologyID, nextOntologyID)) {
                        result = ontologiesByID.get(nextOntologyID);
                    }
                }
            }
            // HACK: This extra clause is necessary to make getOntology match
            // the
            // behaviour of createOntology in cases where a documentIRI has been
            // recorded, based on the mappers, but an ontology has not been
            // stored
            // in ontologiesByID
            if (result == null) {
                IRI documentIRI = getDocumentIRIFromMappers(ontologyID);
                if (documentIRI == null) {
                    if (!ontologyID.isAnonymous()) {
                        documentIRI = ontologyID.getDefaultDocumentIRI().orNull();
                    } else {
                        documentIRI = IRI.generateDocumentIRI();
                    }
                    Collection<IRI> existingDocumentIRIs = documentIRIsByID.values();
                    while (existingDocumentIRIs.contains(documentIRI)) {
                        documentIRI = IRI.generateDocumentIRI();
                    }
                }
                if (documentIRIsByID.values().contains(documentIRI)) {
                    throw new OWLRuntimeException(new OWLOntologyDocumentAlreadyExistsException(documentIRI));
                }
            }
            return result;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntology> getVersions(IRI ontologyIRI) {
        readLock.lock();
        try {
            Set<OWLOntology> result = new HashSet<>();
            for (OWLOntology ont : getOntologies()) {
                OWLOntologyID ontId = ont.getOntologyID();
                Optional<IRI> ontIRI = ontId.getOntologyIRI();
                if (ontIRI.isPresent() && ontIRI.get().equals(ontologyIRI)) {
                    result.add(ont);
                }
            }
            return result;
        } finally {
            readLock.unlock();
        }
    }

    @Nullable
    @Override
    public OWLOntology getImportedOntology(OWLImportsDeclaration declaration) {
        readLock.lock();
        try {
            OWLOntologyID ontologyID = ontologyIDsByImportsDeclaration.get(declaration);
            if (ontologyID == null) {
                // No such ontology has been loaded through an import
                // declaration, but it might have been loaded manually.
                // Using the IRI to retrieve it will either find the ontology or
                // return null.
                // Last possibility is an import by document IRI; if the
                // ontology is not found by IRI, check by document IRI.
                OWLOntology ontology = getOntology(declaration.getIRI());
                if (ontology == null) {
                    ontology = getOntologyByDocumentIRI(declaration.getIRI());
                }
                return ontology;
            } else {
                return getOntology(ontologyID);
            }
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntology> getDirectImports(OWLOntology ontology) {
        readLock.lock();
        try {
            if (!contains(ontology)) {
                throw new UnknownOWLOntologyException(ontology.getOntologyID());
            }
            Set<OWLOntology> imports = new HashSet<>();
            for (OWLImportsDeclaration axiom : ontology.getImportsDeclarations()) {
                assert axiom != null;
                OWLOntology importedOntology = getImportedOntology(axiom);
                if (importedOntology != null) {
                    imports.add(importedOntology);
                }
            }
            return imports;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntology> getImports(OWLOntology ontology) {
        readLock.lock();
        try {
            if (!contains(ontology)) {
                throw new UnknownOWLOntologyException(ontology.getOntologyID());
            }
            Set<OWLOntology> result = new HashSet<>();
            getImports(ontology, result);
            return result;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * A method that gets the imports of a given ontology.
     * 
     * @param ont
     *        The ontology whose (transitive) imports are to be retrieved.
     * @param result
     *        A place to store the result - the transitive closure of the
     *        imports will be stored in this result set.
     */
    private void getImports(@Nonnull OWLOntology ont, @Nonnull Set<OWLOntology> result) {
        readLock.lock();
        try {
            for (OWLOntology directImport : getDirectImports(ont)) {
                assert directImport != null;
                if (result.add(directImport)) {
                    getImports(directImport, result);
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntology> getImportsClosure(OWLOntology ontology) {
        readLock.lock();
        try {
            Set<OWLOntology> ontologies = importsClosureCache.get(ontology.getOntologyID());
            if (ontologies == null) {
                ontologies = new LinkedHashSet<>();
                getImportsClosure(ontology, ontologies);
                // store the wrapped set
                importsClosureCache.put(ontology.getOntologyID(), ontologies);
            }
            // the returned set can be mutated, but changes will not be
            // propagated
            // back
            return CollectionFactory.getCopyOnRequestSetFromMutableCollection(ontologies);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * A recursive method that gets the reflexive transitive closure of the
     * ontologies that are imported by this ontology.
     * 
     * @param ontology
     *        The ontology whose reflexive transitive closure is to be retrieved
     * @param ontologies
     *        a place to store the result
     */
    private void getImportsClosure(@Nonnull OWLOntology ontology, @Nonnull Set<OWLOntology> ontologies) {
        readLock.lock();
        try {
            ontologies.add(ontology);
            for (OWLOntology ont : getDirectImports(ontology)) {
                assert ont != null;
                if (!ontologies.contains(ont)) {
                    getImportsClosure(ont, ontologies);
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<OWLOntology> getSortedImportsClosure(OWLOntology ontology) {
        readLock.lock();
        try {
            return new ArrayList<>(ontology.getImportsClosure());
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Determines if a change is applicable. A change may not be applicable for
     * a number of reasons.
     * 
     * @param change
     *        The change to be tested.
     * @return {@code true} if the change is applicable, otherwise,
     *         {@code false}.
     */
    private boolean isChangeApplicable(OWLOntologyChange change) {
        OWLOntologyLoaderConfiguration ontologyConfig = ontologyConfigurationsByOntologyID.get(change.getOntology()
            .getOntologyID());
        if (ontologyConfig != null && !ontologyConfig.isLoadAnnotationAxioms() && change.isAddAxiom() && change
            .getAxiom() instanceof OWLAnnotationAxiom) {
            return false;
        }
        return true;
    }

    /**
     * Applies a change to an ontology and performs the necessary housekeeping
     * tasks.
     * 
     * @param change
     *        The change to be applied.
     * @return A list of changes that were actually applied.
     */
    private ChangeApplied enactChangeApplication(OWLOntologyChange change) {
        if (!isChangeApplicable(change)) {
            return ChangeApplied.UNSUCCESSFULLY;
        }
        OWLOntology ont = change.getOntology();
        if (!(ont instanceof OWLMutableOntology)) {
            throw new ImmutableOWLOntologyChangeException(change.getChangeData(), ont.toString());
        }
        checkForOntologyIDChange(change);
        ChangeApplied appliedChange = ((OWLMutableOntology) ont).applyChange(change);
        checkForImportsChange(change);
        return appliedChange;
    }

    @Override
    public ChangeApplied applyChanges(List<? extends OWLOntologyChange> changes) {
        writeLock.lock();
        try {
            try {
                broadcastImpendingChanges(changes);
            } catch (OWLOntologyChangeVetoException e) {
                // Some listener blocked the changes.
                broadcastOntologyChangesVetoed(changes, e);
                return ChangeApplied.UNSUCCESSFULLY;
            }
            boolean rollbackRequested = false;
            boolean allNoOps = true;
            // list of changes applied successfully. These are the changes that
            // will be reverted in case of a rollback
            List<OWLOntologyChange> appliedChanges = new ArrayList<>();
            fireBeginChanges(changes.size());
            for (OWLOntologyChange change : changes) {
                // once rollback is requested by a failed change, do not carry
                // out any more changes
                if (!rollbackRequested) {
                    assert change != null;
                    ChangeApplied enactChangeApplication = enactChangeApplication(change);
                    if (enactChangeApplication == ChangeApplied.UNSUCCESSFULLY) {
                        rollbackRequested = true;
                    }
                    if (enactChangeApplication == ChangeApplied.SUCCESSFULLY) {
                        allNoOps = false;
                        appliedChanges.add(change);
                    }
                    fireChangeApplied(change);
                }
            }
            if (rollbackRequested) {
                for (OWLOntologyChange c : appliedChanges) {
                    ChangeApplied enactChangeApplication = enactChangeApplication(c.reverseChange());
                    if (enactChangeApplication == ChangeApplied.UNSUCCESSFULLY) {
                        // rollback could not complete, throw an exception
                        throw new OWLRuntimeException("Rollback of changes unsuccessful: Change " + c
                            + " could not be rolled back");
                    }
                }
                appliedChanges.clear();
            }
            fireEndChanges();
            broadcastChanges(appliedChanges);
            if (rollbackRequested) {
                return ChangeApplied.UNSUCCESSFULLY;
            }
            if (allNoOps) {
                return ChangeApplied.NO_OPERATION;
            }
            return ChangeApplied.SUCCESSFULLY;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied addAxiom(@Nonnull OWLOntology ont, @Nonnull OWLAxiom axiom) {
        writeLock.lock();
        try {
            return addAxioms(ont, CollectionFactory.createSet(axiom));
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied addAxioms(@Nonnull OWLOntology ont, @Nonnull Set<? extends OWLAxiom> axioms) {
        writeLock.lock();
        try {
            // Write lock not needed at this point
            List<AddAxiom> changes = new ArrayList<>(axioms.size() + 2);
            for (OWLAxiom ax : axioms) {
                assert ax != null;
                changes.add(new AddAxiom(ont, ax));
            }
            return applyChanges(changes);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied removeAxiom(@Nonnull OWLOntology ont, @Nonnull OWLAxiom axiom) {
        writeLock.lock();
        try {
            // Write lock not needed at this point
            return removeAxioms(ont, CollectionFactory.createSet(axiom));
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied removeAxioms(@Nonnull OWLOntology ont, @Nonnull Set<? extends OWLAxiom> axioms) {
        // Write lock not needed at this point
        List<RemoveAxiom> changes = new ArrayList<>(axioms.size() + 2);
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            changes.add(new RemoveAxiom(ont, ax));
        }
        return applyChanges(changes);
    }

    @Override
    public ChangeApplied applyChange(@Nonnull OWLOntologyChange change) {
        writeLock.lock();
        try {
            // Write lock not needed at this point
            return applyChanges(CollectionFactory.list(change));
        } finally {
            writeLock.unlock();
        }
    }

    private void checkForImportsChange(OWLOntologyChange change) {
        // Called by a write lock holder
        if (change.isImportChange()) {
            resetImportsClosureCache();
            if (change instanceof AddImport) {
                OWLImportsDeclaration addImportDeclaration = ((AddImport) change).getImportDeclaration();
                boolean found = false;
                IRI iri = addImportDeclaration.getIRI();
                for (OWLOntologyID id : ontologiesByID.keySet()) {
                    if (iri.equals(id.getDefaultDocumentIRI().orNull()) || iri.equals(id.getOntologyIRI().orNull())
                        || iri.equals(id.getVersionIRI().orNull())) {
                        found = true;
                        ontologyIDsByImportsDeclaration.put(addImportDeclaration, id);
                    }
                }
                if (!found) {
                    // then the import does not refer to a known IRI for
                    // ontologies; check for a document IRI
                    for (Map.Entry<OWLOntologyID, IRI> e : documentIRIsByID.entrySet()) {
                        if (e.getValue().equals(iri)) {
                            // found the ontology id corresponding to the file
                            // location
                            ontologyIDsByImportsDeclaration.put(addImportDeclaration, e.getKey());
                        }
                    }
                }
            } else {
                // Remove the mapping from declaration to ontology
                OWLImportsDeclaration importDeclaration = ((RemoveImport) change).getImportDeclaration();
                ontologyIDsByImportsDeclaration.remove(importDeclaration);
                importedIRIs.remove(importDeclaration.getIRI());
            }
        }
    }

    private void checkForOntologyIDChange(OWLOntologyChange change) {
        if (change instanceof SetOntologyID) {
            SetOntologyID setID = (SetOntologyID) change;
            OWLOntology existingOntology = ontologiesByID.get(((SetOntologyID) change).getNewOntologyID());
            if (existingOntology != null && !change.getOntology().equals(existingOntology)) {
                if (!change.getOntology().getAxioms().equals(existingOntology.getAxioms())) {
                    LOGGER.error("OWLOntologyManagerImpl.checkForOntologyIDChange() existing:{}", existingOntology);
                    LOGGER.error("OWLOntologyManagerImpl.checkForOntologyIDChange() new:{}", change.getOntology());
                    throw new OWLOntologyRenameException(change.getChangeData(), ((SetOntologyID) change)
                        .getNewOntologyID());
                }
            }
            renameOntology(setID.getOriginalOntologyID(), setID.getNewOntologyID());
            resetImportsClosureCache();
        }
    }

    // Methods to create, load and reload ontologies
    @Override
    public void ontologyCreated(OWLOntology ontology) {
        // This method is called when a factory that we have asked to create or
        // load an ontology has created the ontology. We add the ontology to the
        // set of loaded ontologies.
        addOntology(ontology);
    }

    @Override
    public void setOntologyFormat(OWLOntology ontology, OWLDocumentFormat ontologyFormat) {
        writeLock.lock();
        try {
            OWLOntologyID ontologyID = ontology.getOntologyID();
            ontologyFormatsByOntology.put(ontologyID, ontologyFormat);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public OWLDocumentFormat getOntologyFormat(@Nonnull OWLOntology ontology) {
        readLock.lock();
        try {
            OWLOntologyID ontologyID = ontology.getOntologyID();
            return ontologyFormatsByOntology.get(ontologyID);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public OWLOntology createOntology() throws OWLOntologyCreationException {
        // Brand new ontology without a URI
        return createOntology(new OWLOntologyID());
    }

    @Override
    public OWLOntology createOntology(@Nonnull IRI ontologyIRI) throws OWLOntologyCreationException {
        return createOntology(new OWLOntologyID(of(ontologyIRI), absent()));
    }

    @Override
    public OWLOntology createOntology(@Nonnull OWLOntologyID ontologyID) throws OWLOntologyCreationException {
        writeLock.lock();
        try {
            OWLOntology ontology = ontologiesByID.get(ontologyID);
            if (ontology != null) {
                throw new OWLOntologyAlreadyExistsException(ontologyID);
            }
            IRI documentIRI = getDocumentIRIFromMappers(ontologyID);
            if (documentIRI == null) {
                if (!ontologyID.isAnonymous()) {
                    documentIRI = ontologyID.getDefaultDocumentIRI().orNull();
                } else {
                    documentIRI = IRI.generateDocumentIRI();
                }
                Collection<IRI> existingDocumentIRIs = documentIRIsByID.values();
                while (existingDocumentIRIs.contains(documentIRI)) {
                    documentIRI = IRI.generateDocumentIRI();
                }
            }
            assert documentIRI != null;
            if (documentIRIsByID.values().contains(documentIRI)) {
                throw new OWLOntologyDocumentAlreadyExistsException(documentIRI);
            }
            for (OWLOntologyFactory factory : ontologyFactories) {
                if (factory.canCreateFromDocumentIRI(documentIRI)) {
                    documentIRIsByID.put(ontologyID, documentIRI);
                    return factory.createOWLOntology(this, ontologyID, documentIRI, this);
                }
            }
            throw new OWLOntologyFactoryNotFoundException(documentIRI);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public OWLOntology createOntology(IRI ontologyIRI, Set<OWLOntology> ontologies)
        throws OWLOntologyCreationException {
        return createOntology(ontologyIRI, ontologies, false);
    }

    @Override
    public OWLOntology createOntology(IRI ontologyIRI, Set<OWLOntology> ontologies, boolean copyLogicalAxiomsOnly)
        throws OWLOntologyCreationException {
        writeLock.lock();
        try {
            if (contains(ontologyIRI)) {
                throw new OWLOntologyAlreadyExistsException(new OWLOntologyID(of(ontologyIRI), absent()));
            }
            OWLOntology ont = createOntology(ontologyIRI);
            Set<OWLAxiom> axioms = new HashSet<>();
            for (OWLOntology ontology : ontologies) {
                if (copyLogicalAxiomsOnly) {
                    axioms.addAll(ontology.getLogicalAxioms());
                } else {
                    axioms.addAll(ontology.getAxioms());
                }
            }
            addAxioms(ont, axioms);
            return ont;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public OWLOntology createOntology(Set<OWLAxiom> axioms, IRI ontologyIRI) throws OWLOntologyCreationException {
        writeLock.lock();
        try {
            if (contains(ontologyIRI)) {
                throw new OWLOntologyAlreadyExistsException(new OWLOntologyID(of(ontologyIRI), absent()));
            }
            OWLOntology ont = createOntology(ontologyIRI);
            addAxioms(ont, axioms);
            return ont;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public OWLOntology createOntology(Set<OWLAxiom> axioms) throws OWLOntologyCreationException {
        return createOntology(axioms, getNextAutoGeneratedIRI());
    }

    @Override
    public OWLOntology copyOntology(OWLOntology toCopy, OntologyCopy settings) throws OWLOntologyCreationException {
        writeLock.lock();
        try {
            checkNotNull(toCopy);
            checkNotNull(settings);
            OWLOntology toReturn = null;
            if (settings == OntologyCopy.MOVE) {
                toReturn = toCopy;
                ontologiesByID.put(toReturn.getOntologyID(), toReturn);
            } else if (settings == OntologyCopy.SHALLOW || settings == OntologyCopy.DEEP) {
                toReturn = createOntology(toCopy.getOntologyID());
                for (AxiomType<?> type : AxiomType.AXIOM_TYPES) {
                    assert type != null;
                    addAxioms(toReturn, toCopy.getAxioms(type));
                }
                for (OWLAnnotation a : toCopy.getAnnotations()) {
                    assert a != null;
                    applyChange(new AddOntologyAnnotation(toReturn, a));
                }
                for (OWLImportsDeclaration a : toCopy.getImportsDeclarations()) {
                    assert a != null;
                    applyChange(new AddImport(toReturn, a));
                }
            }
            // toReturn now initialized
            assert toReturn != null;
            OWLOntologyManager m = toCopy.getOWLOntologyManager();
            if (settings == OntologyCopy.MOVE || settings == OntologyCopy.DEEP) {
                setOntologyDocumentIRI(toReturn, m.getOntologyDocumentIRI(toCopy));
                OWLDocumentFormat f = m.getOntologyFormat(toCopy);
                if (f != null) {
                    setOntologyFormat(toReturn, f);
                }
            }
            if (settings == OntologyCopy.MOVE) {
                m.removeOntology(toCopy);
                // at this point toReturn and toCopy are the same object
                // change the manager on the ontology
                toReturn.setOWLOntologyManager(this);
            }
            return toReturn;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public OWLOntology loadOntology(IRI ontologyIRI) throws OWLOntologyCreationException {
        return loadOntology(ontologyIRI, false, getOntologyLoaderConfiguration());
    }

    @Nonnull
    protected OWLOntology loadOntology(@Nonnull IRI ontologyIRI, boolean allowExists,
        @Nonnull OWLOntologyLoaderConfiguration configuration) throws OWLOntologyCreationException {
        writeLock.lock();
        try {
            OWLOntology ontByID = null;
            // Check for matches on the ontology IRI first
            for (OWLOntologyID nextOntologyID : ontologiesByID.keySet()) {
                if (ontologyIRI.equals(nextOntologyID.getOntologyIRI().orNull())) {
                    ontByID = ontologiesByID.get(nextOntologyID);
                }
            }
            // This method may be called using a version IRI, so also check the
            // version IRI if necessary
            if (ontByID == null) {
                for (OWLOntologyID nextOntologyID : ontologiesByID.keySet()) {
                    if (ontologyIRI.equals(nextOntologyID.getVersionIRI().orNull())) {
                        ontByID = ontologiesByID.get(nextOntologyID);
                    }
                }
            }
            if (ontByID != null) {
                return ontByID;
            }
            OWLOntologyID id = new OWLOntologyID(of(ontologyIRI), absent());
            IRI documentIRI = getDocumentIRIFromMappers(id);
            if (documentIRI != null) {
                if (documentIRIsByID.values().contains(documentIRI) && !allowExists) {
                    throw new OWLOntologyDocumentAlreadyExistsException(documentIRI);
                }
                // The ontology might be being loaded, but its IRI might
                // not have been set (as is probably the case with RDF/XML!)
                OWLOntology ontByDocumentIRI = loadOntologyByDocumentIRI(documentIRI);
                if (ontByDocumentIRI != null) {
                    return ontByDocumentIRI;
                }
            } else {
                // Nothing we can do here. We can't get a document IRI to load
                // the ontology from.
                throw new OntologyIRIMappingNotFoundException(ontologyIRI);
            }
            return loadOntology(ontologyIRI, new IRIDocumentSource(documentIRI, null, null), configuration);
        } finally {
            writeLock.unlock();
        }
    }

    private OWLOntology loadOntologyByDocumentIRI(IRI documentIRI) {
        readLock.lock();
        try {
            for (OWLOntologyID ontID : documentIRIsByID.keySet()) {
                assert ontID != null;
                IRI docIRI = documentIRIsByID.get(ontID);
                if (docIRI != null && docIRI.equals(documentIRI)) {
                    return getOntology(ontID);
                }
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }
    private OWLOntology getOntologyByDocumentIRI(IRI documentIRI) {
        readLock.lock();
        try {
            for (OWLOntologyID ontID : documentIRIsByID.keySet()) {
                assert ontID != null;
                IRI docIRI = documentIRIsByID.get(ontID);
                if (documentIRI.equals(docIRI)) {
                    return ontologiesByID.get(ontID);
                }
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public OWLOntology loadOntologyFromOntologyDocument(IRI documentIRI) throws OWLOntologyCreationException {
        // Ontology URI not known in advance
        return loadOntology(null, new IRIDocumentSource(documentIRI, null, null), getOntologyLoaderConfiguration());
    }

    @Override
    public OWLOntology loadOntologyFromOntologyDocument(OWLOntologyDocumentSource documentSource)
        throws OWLOntologyCreationException {
        // Ontology URI not known in advance
        return loadOntology(null, documentSource, getOntologyLoaderConfiguration());
    }

    @Override
    public OWLOntology loadOntologyFromOntologyDocument(OWLOntologyDocumentSource documentSource,
        OWLOntologyLoaderConfiguration conf) throws OWLOntologyCreationException {
        return loadOntology(null, documentSource, conf);
    }

    @Override
    public OWLOntology loadOntologyFromOntologyDocument(File file) throws OWLOntologyCreationException {
        return loadOntologyFromOntologyDocument(new FileDocumentSource(file));
    }

    @Override
    public OWLOntology loadOntologyFromOntologyDocument(InputStream inputStream) throws OWLOntologyCreationException {
        return loadOntologyFromOntologyDocument(new StreamDocumentSource(inputStream));
    }

    /**
     * This is the method that all the other load method delegate to.
     * 
     * @param ontologyIRI
     *        The URI of the ontology to be loaded. This is only used to report
     *        to listeners and may be {@code null}
     * @param documentSource
     *        The input source that specifies where the ontology should be
     *        loaded from.
     * @param configuration
     *        load configuration
     * @return The ontology that was loaded.
     * @throws OWLOntologyCreationException
     *         If the ontology could not be loaded.
     */
    @Nonnull
    protected OWLOntology loadOntology(@Nullable IRI ontologyIRI, @Nonnull OWLOntologyDocumentSource documentSource,
        @Nonnull OWLOntologyLoaderConfiguration configuration) throws OWLOntologyCreationException {
        writeLock.lock();
        try {
            if (loadCount.get() != importsLoadCount.get()) {
                LOGGER.warn(
                    "Runtime Warning: Parsers should load imported ontologies using the makeImportLoadRequest method.");
            }
            fireStartedLoadingEvent(new OWLOntologyID(of(ontologyIRI), absent()), documentSource.getDocumentIRI(),
                loadCount.get() > 0);
            loadCount.incrementAndGet();
            broadcastChanges.set(false);
            Exception ex = null;
            OWLOntologyID idOfLoadedOntology = new OWLOntologyID();
            try {
                OWLOntology ontology = actualParse(documentSource, configuration);
                if (ontology != null) {
                    idOfLoadedOntology = ontology.getOntologyID();
                    return ontology;
                }
            } catch (OWLOntologyRenameException e) {
                // We loaded an ontology from a document and the
                // ontology turned out to have an IRI the same
                // as a previously loaded ontology
                ex = e;
                throw new OWLOntologyAlreadyExistsException(e.getOntologyID(), e);
            } catch (UnloadableImportException e) {
                ex = e;
                throw e;
            } catch (OWLRuntimeException e) {
                if (e.getCause() instanceof OWLOntologyCreationException) {
                    ex = (OWLOntologyCreationException) e.getCause();
                    throw (OWLOntologyCreationException) e.getCause();
                }
                throw e;
            } catch (OWLOntologyCreationException e) {
                ex = e;
                throw e;
            } finally {
                loadCount.decrementAndGet();
                if (loadCount.get() == 0) {
                    broadcastChanges.set(true);
                    // Completed loading ontology and imports
                }
                fireFinishedLoadingEvent(idOfLoadedOntology, documentSource.getDocumentIRI(), loadCount.get() > 0, ex);
            }
            throw new OWLOntologyFactoryNotFoundException(documentSource.getDocumentIRI());
        } finally {
            writeLock.unlock();
        }
    }

    protected OWLOntology actualParse(OWLOntologyDocumentSource documentSource,
        OWLOntologyLoaderConfiguration configuration) throws OWLOntologyCreationException {
        for (OWLOntologyFactory factory : ontologyFactories) {
            if (factory.canLoad(documentSource)) {
                // Note - there is no need to add the ontology here,
                // because it will be added
                // when the ontology is created.
                OWLOntology ontology = factory.loadOWLOntology(this, documentSource, this, configuration);
                fixIllegalPunnings(ontology);
                // Store the ontology to the document IRI mapping
                documentIRIsByID.put(ontology.getOntologyID(), documentSource.getDocumentIRI());
                ontologyConfigurationsByOntologyID.put(ontology.getOntologyID(), configuration);
                if (ontology instanceof HasTrimToSize) {
                    ((HasTrimToSize) ontology).trimToSize();
                }
                return ontology;
            }
        }
        return null;
    }

    protected void fixIllegalPunnings(OWLOntology o) {
        Collection<IRI> illegals = OWLDocumentFormatImpl.determineIllegalPunnings(true, o.getSignature(INCLUDED), o
            .getPunnedIRIs(INCLUDED));
        Multimap<IRI, OWLDeclarationAxiom> illegalDeclarations = HashMultimap.create();
        Set<OWLDeclarationAxiom> declarations = o.getAxioms(AxiomType.DECLARATION, INCLUDED);
        for (OWLDeclarationAxiom d : declarations) {
            if (illegals.contains(d.getEntity().getIRI())) {
                illegalDeclarations.put(d.getEntity().getIRI(), d);
            }
        }
        Map<OWLEntity, OWLEntity> replacementMap = new HashMap<>();
        for (Map.Entry<IRI, Collection<OWLDeclarationAxiom>> e : illegalDeclarations.asMap().entrySet()) {
            if (e.getValue().size() == 1) {
                // One declaration only: illegal punning comes from use or from
                // defaulting of types
                OWLDeclarationAxiom correctDeclaration = e.getValue().iterator().next();
                // currently we only know how to fix the incorrect defaulting of
                // properties to annotation properties
                OWLEntity entity = correctDeclaration.getEntity();
                if (entity.isOWLDataProperty() || entity.isOWLObjectProperty()) {
                    OWLAnnotationProperty wrongProperty = dataFactory.getOWLAnnotationProperty(entity.getIRI());
                    replacementMap.put(wrongProperty, entity);
                }
            } else {
                // Multiple declarations: bad data. Cannot be repaired
                // automatically.
                LOGGER.error("Illegal redeclarations of entities: reuse of entity {} in punning not allowed {}", e
                    .getKey(), e.getValue());
            }
        }
        for (OWLOntology ont : o.getImportsClosure()) {
            for (OWLEntity e : replacementMap.keySet()) {
                if (ont.containsEntityInSignature(e)) {
                    // then all axioms referring the annotation property
                    // must be rebuilt.
                    List<OWLAxiomChange> list = new ArrayList<>();
                    for (OWLAxiom ax : ont.getAxioms()) {
                        if (ax.getSignature().contains(e)) {
                            list.add(new RemoveAxiom(ont, ax));
                            OWLAnnotationPropertyTransformer changer = new OWLAnnotationPropertyTransformer(
                                replacementMap, dataFactory);
                            list.add(new AddAxiom(ont, changer.transformObject(ax)));
                        }
                    }
                    o.getOWLOntologyManager().applyChanges(list);
                }
            }
        }
    }

    @Override
    public void removeOntology(OWLOntology ontology) {
        writeLock.lock();
        try {
            removeOntology(ontology.getOntologyID());
            ontology.setOWLOntologyManager(null);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeOntology(OWLOntologyID ontologyID) {
        writeLock.lock();
        try {
            ontologiesByID.remove(ontologyID);
            ontologyFormatsByOntology.remove(ontologyID);
            documentIRIsByID.remove(ontologyID);
            resetImportsClosureCache();
        } finally {
            writeLock.unlock();
        }
    }

    private void addOntology(OWLOntology ont) {
        writeLock.lock();
        try {
            ontologiesByID.put(ont.getOntologyID(), ont);
            resetImportsClosureCache();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public IRI getOntologyDocumentIRI(OWLOntology ontology) {
        readLock.lock();
        try {
            if (!contains(ontology)) {
                throw new UnknownOWLOntologyException(ontology.getOntologyID());
            }
            return verifyNotNull(documentIRIsByID.get(ontology.getOntologyID()));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void setOntologyDocumentIRI(OWLOntology ontology, IRI documentIRI) {
        writeLock.lock();
        try {
            if (!ontologiesByID.containsKey(ontology.getOntologyID())) {
                throw new UnknownOWLOntologyException(ontology.getOntologyID());
            }
            documentIRIsByID.put(ontology.getOntologyID(), documentIRI);
            resetImportsClosureCache();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Handles a rename of an ontology. This method should only be called
     * *after* the change has been applied
     * 
     * @param oldID
     *        The original ID of the ontology
     * @param newID
     *        The new ID of the ontology
     */
    private void renameOntology(OWLOntologyID oldID, OWLOntologyID newID) {
        OWLOntology ont = ontologiesByID.get(oldID);
        if (ont == null) {
            // Nothing to rename!
            return;
        }
        ontologiesByID.remove(oldID);
        ontologiesByID.put(newID, ont);
        if (ontologyFormatsByOntology.containsKey(oldID)) {
            ontologyFormatsByOntology.put(newID, ontologyFormatsByOntology.remove(oldID));
        }
        IRI documentIRI = documentIRIsByID.remove(oldID);
        if (documentIRI != null) {
            documentIRIsByID.put(newID, documentIRI);
        }
        resetImportsClosureCache();
    }

    protected void resetImportsClosureCache() {
        writeLock.lock();
        try {
            importsClosureCache.clear();
        } finally {
            writeLock.unlock();
        }
    }

    // Methods to save ontologies
    @Override
    public void saveOntology(OWLOntology ontology) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            OWLDocumentFormat format = getOntologyFormat(ontology);
            saveOntology(ontology, format);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(@Nonnull OWLOntology ontology, OWLDocumentFormat ontologyFormat)
        throws OWLOntologyStorageException {
        readLock.lock();
        try {
            saveOntology(ontology, ontologyFormat, getOntologyDocumentIRI(ontology));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLOntology ontology, IRI documentIRI) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            OWLDocumentFormat format = getOntologyFormat(ontology);
            saveOntology(ontology, format, documentIRI);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLOntology ontology, OWLDocumentFormat ontologyFormat, IRI documentIRI)
        throws OWLOntologyStorageException {
        readLock.lock();
        try {
            try {
                for (OWLStorerFactory storerFactory : ontologyStorers) {
                    OWLStorer storer = storerFactory.createStorer();
                    if (storer.canStoreOntology(ontologyFormat)) {
                        storer.storeOntology(ontology, documentIRI, ontologyFormat);
                        return;
                    }
                }
                throw new OWLStorerNotFoundException(ontologyFormat);
            } catch (IOException e) {
                throw new OWLOntologyStorageIOException(e);
            }
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLOntology ontology, OutputStream outputStream) throws OWLOntologyStorageException {
        // Write lock not needed at this point
        saveOntology(ontology, new StreamDocumentTarget(outputStream));
    }

    @Override
    public void saveOntology(OWLOntology ontology, OWLDocumentFormat ontologyFormat, OutputStream outputStream)
        throws OWLOntologyStorageException {
        // Write lock not needed at this point
        saveOntology(ontology, ontologyFormat, new StreamDocumentTarget(outputStream));
    }

    @Override
    public void saveOntology(OWLOntology ontology, OWLOntologyDocumentTarget documentTarget)
        throws OWLOntologyStorageException {
        readLock.lock();
        try {
            saveOntology(ontology, getOntologyFormat(ontology), documentTarget);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLOntology ontology, OWLDocumentFormat ontologyFormat,
        OWLOntologyDocumentTarget documentTarget) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            try {
                for (OWLStorerFactory storerFactory : ontologyStorers) {
                    OWLStorer storer = storerFactory.createStorer();
                    if (storer.canStoreOntology(ontologyFormat)) {
                        storer.storeOntology(ontology, documentTarget, ontologyFormat);
                        return;
                    }
                }
                throw new OWLStorerNotFoundException(ontologyFormat);
            } catch (IOException e) {
                throw new OWLOntologyStorageIOException(e);
            }
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public PriorityCollection<OWLStorerFactory> getOntologyStorers() {
        // Locking done by collection
        return ontologyStorers;
    }

    @Override
    @Inject
    public void setOntologyStorers(Set<OWLStorerFactory> storers) {
        // Locking done by collection
        ontologyStorers.set(storers);
    }

    @Override
    public PriorityCollection<OWLOntologyIRIMapper> getIRIMappers() {
        // Locking done by collection
        return documentMappers;
    }

    @Override
    @Inject
    public void setIRIMappers(Set<OWLOntologyIRIMapper> mappers) {
        // Locking done by collection
        documentMappers.set(mappers);
    }

    @Override
    public void addIRIMapper(OWLOntologyIRIMapper mapper) {
        // Locking done by collection
        documentMappers.add(mapper);
    }

    @Override
    public void removeIRIMapper(OWLOntologyIRIMapper mapper) {
        // Locking done by collection
        documentMappers.remove(mapper);
    }

    @Override
    public void clearIRIMappers() {
        // Locking done by collection
        documentMappers.clear();
    }

    @Override
    public void addOntologyStorer(OWLStorerFactory storer) {
        // Locking done by collection
        ontologyStorers.add(storer);
    }

    @Override
    public void removeOntologyStorer(OWLStorerFactory storer) {
        // Locking done by collection
        ontologyStorers.remove(storer);
    }

    @Override
    public void clearOntologyStorers() {
        // Locking done by collection
        ontologyStorers.clear();
    }

    @Override
    public PriorityCollection<OWLParserFactory> getOntologyParsers() {
        // Locking done by collection
        return parserFactories;
    }

    @Override
    @Inject
    public void setOntologyParsers(Set<OWLParserFactory> parsers) {
        // Locking done by collection
        parserFactories.set(parsers);
    }

    @Override
    public PriorityCollection<OWLOntologyFactory> getOntologyFactories() {
        // Locking done by collection
        return ontologyFactories;
    }

    @Override
    @Inject
    public void setOntologyFactories(Set<OWLOntologyFactory> factories) {
        // Locking done by collection
        ontologyFactories.set(factories);
    }

    /**
     * Uses the mapper mechanism to obtain an ontology document IRI from an
     * ontology IRI.
     * 
     * @param ontologyID
     *        The ontology ID for which a document IRI is to be retrieved
     * @return The document IRI that corresponds to the ontology IRI, or
     *         {@code null} if no physical URI can be found.
     */
    @Nullable
    private IRI getDocumentIRIFromMappers(OWLOntologyID ontologyID) {
        Optional<IRI> defIRI = ontologyID.getDefaultDocumentIRI();
        if (!defIRI.isPresent()) {
            return null;
        }
        IRI iri = defIRI.get();
        assert iri != null;
        for (OWLOntologyIRIMapper mapper : documentMappers) {
            IRI documentIRI = mapper.getDocumentIRI(iri);
            if (documentIRI != null) {
                return documentIRI;
            }
        }
        return iri;
    }

    protected final void installDefaultURIMappers() {}

    protected final void installDefaultOntologyFactories() {
        // The default factories are the ones that can load
        // ontologies from http:// and file:// URIs
    }

    // Listener stuff - methods to add/remove listeners
    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        listenerMap = new ConcurrentHashMap<>();
        impendingChangeListenerMap = new ConcurrentHashMap<>();
        vetoListeners = new ArrayList<>();
    }

    @Override
    public void addOntologyChangeListener(OWLOntologyChangeListener listener) {
        writeLock.lock();
        try {
            listenerMap.put(listener, defaultChangeBroadcastStrategy);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Broadcasts to attached listeners, using the various broadcasting
     * strategies that were specified for each listener.
     * 
     * @param changes
     *        The ontology changes to broadcast
     */
    protected void broadcastChanges(@Nonnull List<? extends OWLOntologyChange> changes) {
        writeLock.lock();
        try {
            if (!broadcastChanges.get()) {
                return;
            }
            for (OWLOntologyChangeListener listener : new ArrayList<>(listenerMap.keySet())) {
                assert listener != null;
                OWLOntologyChangeBroadcastStrategy strategy = listenerMap.get(listener);
                if (strategy == null) {
                    // This listener may have been removed during the broadcast
                    // of
                    // the changes, so when we attempt to retrieve it from the
                    // map
                    // it isn't there (because we iterate over a copy).
                    continue;
                }
                try {
                    // Handle exceptions on a per listener basis. If we have
                    // badly behaving listeners, we don't want one listener
                    // to prevent the other listeners from receiving events.
                    strategy.broadcastChanges(listener, changes);
                } catch (Exception e) {
                    LOGGER.warn("BADLY BEHAVING LISTENER: {} has been removed", e.getMessage(), e);
                    listenerMap.remove(listener);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    protected void broadcastImpendingChanges(@Nonnull List<? extends OWLOntologyChange> changes) {
        writeLock.lock();
        try {
            if (!broadcastChanges.get()) {
                return;
            }
            for (ImpendingOWLOntologyChangeListener listener : new ArrayList<>(impendingChangeListenerMap.keySet())) {
                assert listener != null;
                ImpendingOWLOntologyChangeBroadcastStrategy strategy = impendingChangeListenerMap.get(listener);
                if (strategy != null) {
                    strategy.broadcastChanges(listener, changes);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void setDefaultChangeBroadcastStrategy(OWLOntologyChangeBroadcastStrategy strategy) {
        writeLock.lock();
        try {
            defaultChangeBroadcastStrategy = strategy;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addOntologyChangeListener(OWLOntologyChangeListener listener,
        OWLOntologyChangeBroadcastStrategy strategy) {
        writeLock.lock();
        try {
            listenerMap.put(listener, strategy);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addImpendingOntologyChangeListener(ImpendingOWLOntologyChangeListener listener) {
        writeLock.lock();
        try {
            impendingChangeListenerMap.put(listener, defaultImpendingChangeBroadcastStrategy);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeImpendingOntologyChangeListener(ImpendingOWLOntologyChangeListener listener) {
        writeLock.lock();
        try {
            impendingChangeListenerMap.remove(listener);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeOntologyChangeListener(OWLOntologyChangeListener listener) {
        writeLock.lock();
        try {
            listenerMap.remove(listener);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addOntologyChangesVetoedListener(OWLOntologyChangesVetoedListener listener) {
        writeLock.lock();
        try {
            vetoListeners.add(listener);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeOntologyChangesVetoedListener(OWLOntologyChangesVetoedListener listener) {
        writeLock.lock();
        try {
            vetoListeners.remove(listener);
        } finally {
            writeLock.unlock();
        }
    }

    private void broadcastOntologyChangesVetoed(@Nonnull List<? extends OWLOntologyChange> changes,
        @Nonnull OWLOntologyChangeVetoException veto) {
        writeLock.lock();
        try {
            for (OWLOntologyChangesVetoedListener listener : new ArrayList<>(vetoListeners)) {
                listener.ontologyChangesVetoed(changes, veto);
            }
        } finally {
            writeLock.unlock();
        }
    }

    // Imports etc.
    protected OWLOntology loadImports(OWLImportsDeclaration declaration,
        @Nonnull OWLOntologyLoaderConfiguration configuration) throws OWLOntologyCreationException {
        writeLock.lock();
        try {
            importsLoadCount.incrementAndGet();
            OWLOntology ont = null;
            try {
                ont = loadOntology(declaration.getIRI(), true, configuration);
            } catch (OWLOntologyCreationException e) {
                if (configuration.getMissingImportHandlingStrategy() == MissingImportHandlingStrategy.THROW_EXCEPTION) {
                    throw e;
                } else {
                    // Silent
                    MissingImportEvent evt = new MissingImportEvent(declaration.getIRI(), e);
                    fireMissingImportEvent(evt);
                }
            } finally {
                importsLoadCount.decrementAndGet();
            }
            return ont;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void makeLoadImportRequest(OWLImportsDeclaration declaration) {
        writeLock.lock();
        try {
            makeLoadImportRequest(declaration, getOntologyLoaderConfiguration());
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void makeLoadImportRequest(OWLImportsDeclaration declaration, OWLOntologyLoaderConfiguration configuration) {
        writeLock.lock();
        try {
            IRI iri = declaration.getIRI();
            if (!configuration.isIgnoredImport(iri) && !importedIRIs.contains(iri)) {
                importedIRIs.add(iri);
                try {
                    OWLOntology ont = loadImports(declaration, configuration);
                    if (ont != null) {
                        ontologyIDsByImportsDeclaration.put(declaration, ont.getOntologyID());
                    }
                } catch (OWLOntologyCreationException e) {
                    // Wrap as UnloadableImportException and throw
                    throw new UnloadableImportException(e, declaration);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addMissingImportListener(MissingImportListener listener) {
        writeLock.lock();
        try {
            missingImportsListeners.add(listener);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeMissingImportListener(@Nonnull MissingImportListener listener) {
        writeLock.lock();
        try {
            missingImportsListeners.remove(listener);
        } finally {
            writeLock.unlock();
        }
    }

    protected void fireMissingImportEvent(@Nonnull MissingImportEvent evt) {
        writeLock.lock();
        try {
            for (MissingImportListener listener : new ArrayList<>(missingImportsListeners)) {
                listener.importMissing(evt);
            }
        } finally {
            writeLock.unlock();
        }
    }

    // Other listeners etc.
    @Override
    public void addOntologyLoaderListener(OWLOntologyLoaderListener listener) {
        writeLock.lock();
        try {
            loaderListeners.add(listener);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeOntologyLoaderListener(OWLOntologyLoaderListener listener) {
        writeLock.lock();
        try {
            loaderListeners.remove(listener);
        } finally {
            writeLock.unlock();
        }
    }

    protected void fireStartedLoadingEvent(OWLOntologyID ontologyID, IRI documentIRI, boolean imported) {
        writeLock.lock();
        try {
            for (OWLOntologyLoaderListener listener : new ArrayList<>(loaderListeners)) {
                listener.startedLoadingOntology(new OWLOntologyLoaderListener.LoadingStartedEvent(ontologyID,
                    documentIRI, imported));
            }
        } finally {
            writeLock.unlock();
        }
    }

    protected void fireFinishedLoadingEvent(OWLOntologyID ontologyID, IRI documentIRI, boolean imported, Exception ex) {
        writeLock.lock();
        try {
            for (OWLOntologyLoaderListener listener : new ArrayList<>(loaderListeners)) {
                listener.finishedLoadingOntology(new OWLOntologyLoaderListener.LoadingFinishedEvent(ontologyID,
                    documentIRI, imported, ex));
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addOntologyChangeProgessListener(OWLOntologyChangeProgressListener listener) {
        writeLock.lock();
        try {
            progressListeners.add(listener);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeOntologyChangeProgessListener(OWLOntologyChangeProgressListener listener) {
        writeLock.lock();
        try {
            progressListeners.remove(listener);
        } finally {
            writeLock.unlock();
        }
    }

    protected void fireBeginChanges(int size) {
        writeLock.lock();
        try {
            if (!broadcastChanges.get()) {
                return;
            }
            for (OWLOntologyChangeProgressListener listener : progressListeners) {
                try {
                    listener.begin(size);
                } catch (Exception e) {
                    LOGGER.warn("BADLY BEHAVING LISTENER: {} has been removed", e.getMessage(), e);
                    progressListeners.remove(listener);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    protected void fireEndChanges() {
        writeLock.lock();
        try {
            if (!broadcastChanges.get()) {
                return;
            }
            for (OWLOntologyChangeProgressListener listener : progressListeners) {
                try {
                    listener.end();
                } catch (Exception e) {
                    LOGGER.warn("BADLY BEHAVING LISTENER: {} has been removed", e.getMessage(), e);
                    progressListeners.remove(listener);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    private void fireChangeApplied(@Nonnull OWLOntologyChange change) {
        writeLock.lock();
        try {
            if (!broadcastChanges.get()) {
                return;
            }
            if (progressListeners.isEmpty()) {
                return;
            }
            for (OWLOntologyChangeProgressListener listener : progressListeners) {
                try {
                    listener.appliedChange(change);
                } catch (Exception e) {
                    LOGGER.warn("BADLY BEHAVING LISTENER: {} has been removed", e.getMessage(), e);
                    progressListeners.remove(listener);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Nonnull
    protected <T> Optional<T> of(T t) {
        return Optional.fromNullable(t);
    }

    @Nonnull
    protected Optional<IRI> absent() {
        return Optional.absent();
    }
}
