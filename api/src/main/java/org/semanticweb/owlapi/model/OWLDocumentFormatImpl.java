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
package org.semanticweb.owlapi.model;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.RDFParserMetaData;
import org.semanticweb.owlapi.util.CollectionFactory;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * Represents the concrete representation format of an ontology. The equality of
 * an ontology format is defined by the equals and hashCode method (not its
 * identity).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLDocumentFormatImpl implements OWLDocumentFormat {

    private static final long serialVersionUID = 40000L;
    private final Map<Serializable, Serializable> parameterMap = new HashMap<>();
    @Nonnull private OWLOntologyLoaderMetaData loaderMetaData = new RDFParserMetaData();
    private boolean addMissingTypes = true;

    @Override
    public boolean isAddMissingTypes() {
        return addMissingTypes;
    }

    /**
     * Determines if a declaration axiom (type triple) needs to be added to the
     * specified ontology for the given entity.
     * 
     * @param entity
     *        The entity
     * @param ontology
     *        The ontology.
     * @return {@code false} if the entity is built in. {@code false} if the
     *         ontology doesn't contain the entity in its signature.
     *         {@code false} if the entity is already declared in the imports
     *         closure of the ontology. {@code false} if the transitive imports
     *         does not contain the ontology but the entity is contained in the
     *         signature of one of the imported ontologies, {@code true} if none
     *         of the previous conditions are met.
     */
    public static boolean isMissingType(@Nonnull OWLEntity entity, OWLOntology ontology) {
        // We don't need to declare built in entities
        if (entity.isBuiltIn()) {
            return false;
        }
        // If the ontology doesn't contain the entity in its signature then it
        // shouldn't declare it
        if (!ontology.containsEntityInSignature(entity)) {
            return false;
        }
        if (ontology.isDeclared(entity, INCLUDED)) {
            return false;
        }
        Set<OWLOntology> transitiveImports = ontology.getImports();
        if (!transitiveImports.contains(ontology)) {
            // See if the entity should be declared in an imported ontology
            for (OWLOntology importedOntology : transitiveImports) {
                if (importedOntology.containsEntityInSignature(entity)) {
                    // Leave it for that ontology to declare the entity
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param signature
     *        signature for the ontology
     * @param punnedEntities
     *        the set of entities that are known already to be punned
     * @param add
     *        true if missing declarations should be added. If false, no
     *        declarations will be added.
     * @return collection of IRIS used in illegal punnings
     */
    public static Collection<IRI> determineIllegalPunnings(boolean add, Collection<OWLEntity> signature,
        Collection<IRI> punnedEntities) {
        if (!add) {
            return CollectionFactory.emptySet();
        }
        // determine what entities are illegally punned
        Multimap<IRI, EntityType<?>> punnings = LinkedListMultimap.create();
        for (OWLEntity e : signature) {
            // disregard individuals as they do not give raise to illegal
            // punnings; only keep track of punned entities, ignore the rest
            if (!e.isOWLNamedIndividual() && punnedEntities.contains(e.getIRI())) {
                punnings.put(e.getIRI(), e.getEntityType());
            }
        }
        Collection<IRI> illegals = new HashSet<>();
        for (IRI i : punnings.keySet()) {
            Collection<EntityType<?>> puns = punnings.get(i);
            if (puns.contains(EntityType.OBJECT_PROPERTY) && puns.contains(EntityType.ANNOTATION_PROPERTY)) {
                illegals.add(i);
            } else if (puns.contains(EntityType.DATA_PROPERTY) && puns.contains(EntityType.ANNOTATION_PROPERTY)) {
                illegals.add(i);
            } else if (puns.contains(EntityType.DATA_PROPERTY) && puns.contains(EntityType.OBJECT_PROPERTY)) {
                illegals.add(i);
            } else if (puns.contains(EntityType.DATATYPE) && puns.contains(EntityType.CLASS)) {
                illegals.add(i);
            }
        }
        return illegals;
    }

    @Override
    public void setAddMissingTypes(boolean addMissingTypes) {
        this.addMissingTypes = addMissingTypes;
    }

    @Override
    public void setParameter(Serializable key, Serializable value) {
        parameterMap.put(key, value);
    }

    @Override
    public Serializable getParameter(Serializable key, Serializable defaultValue) {
        Serializable val = parameterMap.get(key);
        if (val != null) {
            return val;
        } else {
            return defaultValue;
        }
    }

    @Override
    public OWLOntologyLoaderMetaData getOntologyLoaderMetaData() {
        return loaderMetaData;
    }

    @Override
    public void setOntologyLoaderMetaData(OWLOntologyLoaderMetaData loaderMetaData) {
        this.loaderMetaData = loaderMetaData;
    }

    @Override
    public boolean isTextual() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof OWLDocumentFormat) {
            return ((OWLDocumentFormat) obj).getKey().equals(getKey());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    @Override
    public String toString() {
        return getKey();
    }

    private static class NullLoaderMetaData implements OWLOntologyLoaderMetaData, Serializable {

        private static final long serialVersionUID = 40000L;

        NullLoaderMetaData() {}
    }
}
