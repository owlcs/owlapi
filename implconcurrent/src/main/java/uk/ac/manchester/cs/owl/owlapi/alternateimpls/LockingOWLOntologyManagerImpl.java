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
package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.ImpendingOWLOntologyChangeListener;
import org.semanticweb.owlapi.model.MissingImportEvent;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeBroadcastStrategy;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyChangeProgressListener;
import org.semanticweb.owlapi.model.OWLOntologyChangeVetoException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFactory;
import org.semanticweb.owlapi.model.OWLOntologyFactoryNotFoundException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyRenameException;
import org.semanticweb.owlapi.util.CollectionFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

/**
 * @author ignazio
 * threadsafe implementation
 */
public class LockingOWLOntologyManagerImpl extends OWLOntologyManagerImpl implements
		OWLOntologyManager, OWLOntologyFactory.OWLOntologyCreationHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1920953069359718891L;
	private final Set<Object> broadcastChanges = CollectionFactory.createSyncSet();
	private final Lock listenerLock = new ReentrantLock();
	private final Lock impendingLock = new ReentrantLock();

	private <V> List<V> createList() {
		return new CopyOnWriteArrayList<V>();
	}

	@SuppressWarnings("javadoc")
	public LockingOWLOntologyManagerImpl(OWLDataFactory dataFactory) {
		super(dataFactory);
		ontologiesByID = CollectionFactory.createSyncMap();
		documentIRIsByID = CollectionFactory.createSyncMap();
		ontologyFormatsByOntology = CollectionFactory.createSyncMap();
		documentMappers = createList();
		ontologyFactories = createList();
		ontologyIDsByImportsDeclaration = CollectionFactory.createSyncMap();
		ontologyStorers = createList();
		importsClosureCache = CollectionFactory.createSyncMap();
		missingImportsListeners = createList();
		loaderListeners = createList();
		progressListeners = createList();
	}

	@Override
	protected synchronized OWLOntology loadOntology(IRI ontologyIRI,
			OWLOntologyDocumentSource documentSource,
			OWLOntologyLoaderConfiguration configuration)
			throws OWLOntologyCreationException {
		Object loadKey = new Object();
		if (loadCount != importsLoadCount) {
			System.err
					.println("Runtime Warning: Parsers should load imported ontologies using the makeImportLoadRequest method.");
		}
		fireStartedLoadingEvent(new OWLOntologyID(ontologyIRI),
				documentSource.getDocumentIRI(), loadCount > 0);
		loadCount++;
		broadcastChanges.add(loadKey);
		OWLOntologyCreationException ex = null;
		OWLOntologyID idOfLoadedOntology = new OWLOntologyID();
		try {
			for (OWLOntologyFactory factory : ontologyFactories) {
				if (factory.canLoad(documentSource)) {
					try {
						// Note - there is no need to add the ontology here, because it will be added
						// when the ontology is created.
						OWLOntology ontology = factory.loadOWLOntology(documentSource,
								this, configuration);
						idOfLoadedOntology = ontology.getOntologyID();
						// Store the ontology to the document IRI mapping
						documentIRIsByID.put(ontology.getOntologyID(),
								documentSource.getDocumentIRI());
						return ontology;
					} catch (OWLOntologyRenameException e) {
						// We loaded an ontology from a document and the ontology turned out to have an IRI the same
						// as a previously loaded ontology
						throw new OWLOntologyAlreadyExistsException(e.getOntologyID(), e);
					}
				}
			}
		} catch (OWLOntologyCreationException e) {
			ex = e;
			throw e;
		}
		finally {
			loadCount--;
			// the current load key
			broadcastChanges.remove(loadKey);
			//                 Completed loading ontology and imports
			fireFinishedLoadingEvent(idOfLoadedOntology, documentSource.getDocumentIRI(),
					loadCount > 0, ex);
		}
		throw new OWLOntologyFactoryNotFoundException(documentSource.getDocumentIRI());
	}

	@Override
	public void addOntologyChangeListener(OWLOntologyChangeListener listener) {
		listenerLock.lock();
		try {
			super.addOntologyChangeListener(listener);
		}
		finally {
			listenerLock.unlock();
		}
	}

	@Override
	protected void broadcastChanges(List<? extends OWLOntologyChange> changes) {
		listenerLock.lock();
		try {
			super.broadcastChanges(changes);
		}
		finally {
			listenerLock.unlock();
		}
	}

	@Override
	protected void broadcastImpendingChanges(List<? extends OWLOntologyChange> changes)
			throws OWLOntologyChangeVetoException {
		impendingLock.lock();
		try {
			super.broadcastImpendingChanges(changes);
		}
		finally {
			impendingLock.unlock();
		}
	}

	@Override
	public void addOntologyChangeListener(OWLOntologyChangeListener listener,
			OWLOntologyChangeBroadcastStrategy strategy) {
		listenerLock.lock();
		try {
			super.addOntologyChangeListener(listener, strategy);
		}
		finally {
			listenerLock.unlock();
		}
	}

	@Override
	public void addImpendingOntologyChangeListener(
			ImpendingOWLOntologyChangeListener listener) {
		impendingLock.lock();
		try {
			super.addImpendingOntologyChangeListener(listener);
		}
		finally {
			impendingLock.unlock();
		}
	}

	@Override
	public void removeImpendingOntologyChangeListener(
			ImpendingOWLOntologyChangeListener listener) {
		impendingLock.lock();
		try {
			super.removeImpendingOntologyChangeListener(listener);
		}
		finally {
			impendingLock.unlock();
		}
	}

	@Override
	public void removeOntologyChangeListener(OWLOntologyChangeListener listener) {
		listenerLock.lock();
		try {
			super.removeOntologyChangeListener(listener);
		}
		finally {
			listenerLock.unlock();
		}
	}

	@Override
	protected synchronized OWLOntology loadImports(OWLImportsDeclaration declaration,
			OWLOntologyLoaderConfiguration configuration)
			throws OWLOntologyCreationException {
			importsLoadCount++;

		OWLOntology ont = null;
		try {
			ont = loadOntology(declaration.getIRI(), true, configuration);
		} catch (OWLOntologyCreationException e) {
			//XXX note: uses both the configuration and this manager silent missing imports handling; should be removed when the manager mechanism is removed
			if (!configuration.isSilentMissingImportsHandling()
					&& !isSilentMissingImportsHandling()) {
				throw e;
			} else {
				// Silent
				MissingImportEvent evt = new MissingImportEvent(declaration.getIRI(), e);
				fireMissingImportEvent(evt);
			}
		}
		finally {
				importsLoadCount--;
		}
		return ont;
	}

	@Override
	protected void fireBeginChanges(int size) {
		try {
			if (!broadcastChanges.isEmpty()) {
				return;
			}
			for (OWLOntologyChangeProgressListener lsnr : progressListeners) {
				lsnr.begin(size);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void fireEndChanges() {
		try {
			if (!broadcastChanges.isEmpty()) {
				return;
			}
			for (OWLOntologyChangeProgressListener lsnr : progressListeners) {
				lsnr.end();
			}
		} catch (Throwable e) {
			// Listener threw an exception
			e.printStackTrace();
		}
	}

	@Override
	protected void fireChangeApplied(OWLOntologyChange change) {
		try {
			if (!broadcastChanges.isEmpty()) {
				return;
			}
			if (progressListeners.isEmpty()) {
				return;
			}
			for (OWLOntologyChangeProgressListener lsnr : progressListeners) {
				lsnr.appliedChange(change);
			}
		} catch (Throwable e) {
			// Listener threw an exception
			e.printStackTrace();
		}
	}
}
