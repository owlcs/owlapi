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

package org.semanticweb.owlapi.io;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-Jun-2009
 */
public abstract class RDFOntologyFormat extends PrefixOWLOntologyFormat {

    private boolean addMissingTypes = true;

    private Set<RDFResourceParseError> errors = new HashSet<RDFResourceParseError>();

    /**
     * Determines if untyped entities should automatically be typed (declared) during rendering.  (This is a hint to an RDF
     * renderer - the reference implementation will respect this).
     * The render will check with the {@link #isMissingType(org.semanticweb.owlapi.model.OWLEntity, org.semanticweb.owlapi.model.OWLOntology)}
     * method to determine if it needs to add a type.
     * @return <code>true</code> if untyped entities should automatically be typed during rendering,
     *         otherwise <code>false</code>.
     */
    public boolean isAddMissingTypes() {
        return addMissingTypes;
    }

    /**
     * Determines if a declaration axiom (type triple) needs to be added to the specified ontology for the given entity.
     * @param entity The entity
     * @param ontology The ontology.
     * @return <code>false</code> if the entity is built in. <code>false</code> if the ontology doesn't contain
     *         the entity in its signature. <code>false</code> if the entity is already declared in the imports closure
     *         of the ontology. <code>false</code> if the transitive imports does not contain the ontology but the entity
     *         is contained in the signature of one of the imported ontologies, <code>true</code> if none of the previous conditions
     *         are met.
     */
    public static boolean isMissingType(OWLEntity entity, OWLOntology ontology) {
        // We don't need to declare built in entities
        if (entity.isBuiltIn()) {
            return false;
        }
        // If the ontology doesn't contain the entity in its signature then it shouldn't declare it
        if (!ontology.containsEntityInSignature(entity)) {
            return false;
        }
        if (ontology.isDeclared(entity, true)) {
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
     * Determines if untyped entities should automatically be typed during rendering.  By default this is true.
     * @param addMissingTypes <code>true</code> if untyped entities should automatically be typed during rendering,
     * otherwise <code>false</code>.
     */
    public void setAddMissingTypes(boolean addMissingTypes) {
        this.addMissingTypes = addMissingTypes;
    }

    @Override
    public RDFParserMetaData getOntologyLoaderMetaData() {
        return (RDFParserMetaData) super.getOntologyLoaderMetaData();
    }

    public void setOntologyLoaderMetaData(RDFParserMetaData loaderMetaData) {
        super.setOntologyLoaderMetaData(loaderMetaData);
    }

    public void addError(RDFResourceParseError error) {
        errors.add(error);
    }
}
