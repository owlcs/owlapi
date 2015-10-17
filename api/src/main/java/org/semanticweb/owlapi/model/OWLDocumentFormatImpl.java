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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;

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

    private final Map<Serializable, Serializable> parameterMap = new HashMap<>();
    private @Nonnull OWLOntologyLoaderMetaData loaderMetaData = new NullLoaderMetaData();
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
    public static boolean isMissingType(OWLEntity entity, OWLOntology ontology) {
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
        Set<OWLOntology> transitiveImports = asSet(ontology.imports());
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
        if (val == null) {
            return defaultValue;
        }
        return val;
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
    public boolean equals(@Nullable Object obj) {
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

        NullLoaderMetaData() {}
    }
}
