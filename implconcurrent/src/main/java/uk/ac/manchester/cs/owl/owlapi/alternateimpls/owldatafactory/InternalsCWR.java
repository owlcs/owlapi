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

package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.CollectionFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;
/**
 * @author ignazio
 * concurrent hash maps with weak references used for cache
 */
public final class InternalsCWR implements OWLDataFactoryInternals {
	final ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> classesByURI;
	final ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> objectPropertiesByURI;
	final ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> dataPropertiesByURI;
	final ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> datatypesByURI;
	final ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> individualsByURI;
	final ConcurrentHashMap<IRI, WeakReference<? extends OWLEntity>> annotationPropertiesByURI;
	final OWLDataFactory factory;
    private final Thread reaper = new Thread() {
        @Override
		public void run() {
            this.setPriority(MIN_PRIORITY);
            while (true) {
                clean(classesByURI);
                clean(dataPropertiesByURI);
                clean(annotationPropertiesByURI);
                clean(datatypesByURI);
                clean(individualsByURI);
                clean(objectPropertiesByURI);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void clean(Map<IRI, WeakReference<? extends OWLEntity>> map) {
            if (map != null) {
                List<IRI> l = new ArrayList<IRI>();
                for (Map.Entry<IRI, WeakReference<? extends OWLEntity>> w : map.entrySet()) {
                    if (w.getValue().get() == null) {
                        l.add(w.getKey());
                    }
                }
                for (IRI i : l) {
                    map.remove(i);
                }

            }
        }
    };
    /**
     * @param f the factory to refer to
     */
    public InternalsCWR(OWLDataFactory f) {
        factory = f;
        classesByURI = CollectionFactory.createSyncMap();
        objectPropertiesByURI = CollectionFactory.createSyncMap();
        dataPropertiesByURI = CollectionFactory.createSyncMap();
        datatypesByURI = CollectionFactory.createSyncMap();
        individualsByURI = CollectionFactory.createSyncMap();
        annotationPropertiesByURI = CollectionFactory.createSyncMap();
        reaper.setDaemon(true);
        reaper.start();
    }

    private OWLEntity unwrap(Map<IRI, WeakReference<? extends OWLEntity>> map, IRI iri, BuildableObjects type) {
        OWLEntity toReturn = null;
        while (toReturn == null) {
            WeakReference<? extends OWLEntity> r = map.get(iri);
            if (r == null || r.get() == null) {
                toReturn = type.build(factory, iri);
                r = new WeakReference<OWLEntity>(toReturn);
                map.put(iri, r);
            }
            else {
                toReturn = r.get();
            }
        }
        return toReturn;
    }

    private enum BuildableObjects {
        OWLCLASS {
            @Override
            OWLEntity build(OWLDataFactory f, IRI iri) {
                return new OWLClassImpl(f, iri);
            }
        },
        OWLOBJECTPROPERTY {
            @Override
            OWLEntity build(OWLDataFactory f, IRI iri) {
                return new OWLObjectPropertyImpl(f, iri);
            }
        },
        OWLDATAPROPERTY {
            @Override
            OWLEntity build(OWLDataFactory f, IRI iri) {
                return new OWLDataPropertyImpl(f, iri);
            }
        },
        OWLNAMEDINDIVIDUAL {
            @Override
            OWLEntity build(OWLDataFactory f, IRI iri) {
                return new OWLNamedIndividualImpl(f, iri);
            }
        },
        OWLDATATYPE {
            @Override
            OWLEntity build(OWLDataFactory f, IRI iri) {
                return new OWLDatatypeImpl(f, iri);
            }
        },
        OWLANNOTATIONPROPERTY {
            @Override
            OWLEntity build(OWLDataFactory f, IRI iri) {
                return new OWLAnnotationPropertyImpl(f, iri);
            }
        };

        abstract OWLEntity build(OWLDataFactory f, IRI iri);
    }

    public OWLClass getOWLClass(IRI iri) {
        return (OWLClass) unwrap(classesByURI, iri, BuildableObjects.OWLCLASS);
    }

    public void purge() {
        classesByURI.clear();
        objectPropertiesByURI.clear();
        dataPropertiesByURI.clear();
        datatypesByURI.clear();
        individualsByURI.clear();
        annotationPropertiesByURI.clear();
    }

    public OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return (OWLObjectProperty) unwrap(objectPropertiesByURI, iri, BuildableObjects.OWLOBJECTPROPERTY);
    }

    public OWLDataProperty getOWLDataProperty(IRI iri) {
        return (OWLDataProperty) unwrap(dataPropertiesByURI, iri, BuildableObjects.OWLDATAPROPERTY);
    }

    public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return (OWLNamedIndividual) unwrap(individualsByURI, iri, BuildableObjects.OWLNAMEDINDIVIDUAL);
    }

    public OWLDatatype getOWLDatatype(IRI iri) {
        return (OWLDatatype) unwrap(datatypesByURI, iri, BuildableObjects.OWLDATATYPE);
    }

    public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return (OWLAnnotationProperty) unwrap(annotationPropertiesByURI, iri, BuildableObjects.OWLANNOTATIONPROPERTY);
    }
}