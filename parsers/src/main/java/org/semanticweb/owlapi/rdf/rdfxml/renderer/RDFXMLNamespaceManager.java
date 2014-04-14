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
package org.semanticweb.owlapi.rdf.rdfxml.renderer;

import static org.semanticweb.owlapi.model.Imports.EXCLUDED;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.3.0
 */
public class RDFXMLNamespaceManager extends OWLOntologyXMLNamespaceManager {

    /**
     * @param ontology
     *        ontology
     * @param format
     *        format
     */
    public RDFXMLNamespaceManager(@Nonnull OWLOntology ontology,
            @Nonnull OWLOntologyFormat format) {
        super(ontology, format);
    }

    @Override
    protected Set<OWLEntity> getEntitiesThatRequireNamespaces() {
        Set<OWLEntity> entities = new HashSet<OWLEntity>();
        for (OWLObjectPropertyAssertionAxiom ax : getOntology().getAxioms(
                AxiomType.OBJECT_PROPERTY_ASSERTION)) {
            entities.addAll(ax.getProperty().getSignature());
        }
        for (OWLDataPropertyAssertionAxiom ax : getOntology().getAxioms(
                AxiomType.DATA_PROPERTY_ASSERTION)) {
            entities.add(ax.getProperty().asOWLDataProperty());
        }
        entities.addAll(getOntology().getAnnotationPropertiesInSignature(
                EXCLUDED));
        return entities;
    }

    /** @return entities with invalid qnames */
    @Nonnull
    public Set<OWLEntity> getEntitiesWithInvalidQNames() {
        Set<OWLEntity> result = new HashSet<OWLEntity>();
        for (OWLEntity entity : getEntitiesThatRequireNamespaces()) {
            IRI iri = entity.getIRI();
            if (iri.getFragment() == null || iri.getFragment().isEmpty()) {
                result.add(entity);
            }
        }
        return result;
    }
}
