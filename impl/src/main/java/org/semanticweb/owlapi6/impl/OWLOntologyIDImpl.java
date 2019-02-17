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
package org.semanticweb.owlapi6.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.NodeID;
import org.semanticweb.owlapi6.model.OWLOntologyID;

/**
 * An object that identifies an ontology. Since OWL 2, ontologies do not have to have an ontology
 * IRI, or if they have an ontology IRI then they can optionally also have a version IRI. Instances
 * of this OWLOntologyID class bundle identifying information of an ontology together. If an
 * ontology doesn't have an ontology IRI then we say that it is "anonymous".
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class OWLOntologyIDImpl implements OWLOntologyID {

    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final String ANON_PREFIX = "Anonymous-";
    private transient Optional<String> internalID = Optional.empty();
    private transient Optional<IRI> ontologyIRI;
    private transient Optional<IRI> versionIRI;
    private int hashCode;

    /**
     * Constructs an ontology identifier specifiying the ontology IRI and version IRI.
     *
     * @param iri The ontology IRI (may be absent)
     * @param version The version IRI (must be absent if the ontologyIRI is absent)
     */
    public OWLOntologyIDImpl(Optional<IRI> iri, Optional<IRI> version) {
        ontologyIRI = opt(iri);
        hashCode = 17;
        if (ontologyIRI.isPresent()) {
            hashCode += 37 * ontologyIRI.hashCode();
        } else {
            internalID = Optional.ofNullable(ANON_PREFIX + COUNTER.getAndIncrement());
            hashCode += 37 * internalID.hashCode();
        }
        versionIRI = opt(version);
        if (versionIRI.isPresent()) {
            if (!ontologyIRI.isPresent()) {
                throw new IllegalArgumentException(
                    "If the ontology IRI is null then it is not possible to specify a version IRI");
            }
            hashCode += 37 * versionIRI.hashCode();
        }
    }

    /**
     * Constructs an ontology identifier specifying that the ontology IRI (and hence the version
     * IRI) is not present.
     */
    public OWLOntologyIDImpl() {
        this(Optional.empty(), Optional.empty());
    }

    /**
     * Replace an optional with a blank node iri with an absent optional.
     *
     * @param i Optional to check
     * @return input optional if its iri is not a blank node iri, absent otherwise
     */
    private static Optional<IRI> opt(Optional<IRI> i) {
        if (NodeID.isAnonymousNodeIRI(i.orElse(null))) {
            return Optional.empty();
        }
        return i;
    }

    private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
        stream.defaultReadObject();
        ontologyIRI = Optional.ofNullable((IRI) stream.readObject());
        versionIRI = Optional.ofNullable((IRI) stream.readObject());
        internalID = Optional.ofNullable((String) stream.readObject());
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(ontologyIRI.orElse(null));
        stream.writeObject(versionIRI.orElse(null));
        stream.writeObject(internalID.orElse(null));
    }

    @Override
    public Optional<IRI> getOntologyIRI() {
        return ontologyIRI;
    }

    @Override
    public Optional<IRI> getVersionIRI() {
        return versionIRI;
    }

    @Override
    public Optional<String> getInternalID() {
        return internalID;
    }

    @Override
    public String toString() {
        if (ontologyIRI.isPresent()) {
            String template = "OntologyID(OntologyIRI(<%s>) VersionIRI(<%s>))";
            return String.format(template, ontologyIRI.get(), versionIRI.orElse(null));
        }
        return "OntologyID(" + internalID.orElse(null) + ')';
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLOntologyID)) {
            return false;
        }
        OWLOntologyID other = (OWLOntologyID) obj;
        if (isAnonymous() && other.isAnonymous()) {
            // both anonymous: check the anon version
            return internalID.equals(other.getInternalID());
        }
        if (isAnonymous() != other.isAnonymous()) {
            // one anonymous, one not: equals is false
            return false;
        }
        if (isNamed()) {
            boolean toReturn = ontologyIRI.equals(other.getOntologyIRI());
            if (!toReturn) {
                return toReturn;
            }
            // if toReturn is true, compare the version iris
            toReturn = versionIRI.equals(other.getVersionIRI());
            return toReturn;
        }
        // else this is anonymous and the other cannot be anonymous, so return
        // false
        return false;
    }
}
