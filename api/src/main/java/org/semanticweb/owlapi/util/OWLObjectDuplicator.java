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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLObjectDuplicator extends TransformerVisitorBase<Object> {

    protected final RemappingIndividualProvider anonProvider;
    private final Map<OWLEntity, IRI> replacementMap;
    private final Map<OWLLiteral, OWLLiteral> replacementLiterals;

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory and uri
     * replacement map.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     * @param entityIRIReplacementMap The map to use for the replacement of URIs. Any uris the
     * appear in the map will be replaced as objects are duplicated. This can be used to "rename"
     * entities.
     */
    public OWLObjectDuplicator(Map<OWLEntity, IRI> entityIRIReplacementMap, OWLOntologyManager m) {
        this(entityIRIReplacementMap, Collections.<OWLLiteral, OWLLiteral>emptyMap(), m);
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory and uri
     * replacement map.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     * @param entityIRIReplacementMap The map to use for the replacement of URIs. Any uris the
     * appear in the map will be replaced as objects are duplicated. This can be used to "rename"
     * entities.
     * @param literals replacement literals
     */
    public OWLObjectDuplicator(Map<OWLEntity, IRI> entityIRIReplacementMap,
        Map<OWLLiteral, OWLLiteral> literals, OWLOntologyManager m) {
        super(x -> true, x -> x instanceof OWLFacet ? x : null,
            checkNotNull(m, "ontology manager cannot be null").getOWLDataFactory(),
            Object.class);
        anonProvider = new RemappingIndividualProvider(m.getOntologyConfigurator(), df);
        replacementMap = new HashMap<>(checkNotNull(entityIRIReplacementMap,
            "entityIRIReplacementMap cannot be null"));
        replacementLiterals = checkNotNull(literals, "literals cannot be null");
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     */
    public OWLObjectDuplicator(OWLOntologyManager m) {
        this(Collections.<OWLEntity, IRI>emptyMap(), m);
    }

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory and uri
     * replacement map.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     * @param iriReplacementMap The map to use for the replacement of URIs. Any uris the appear in
     * the map will be replaced as objects are duplicated. This can be used to "rename" entities.
     */
    public OWLObjectDuplicator(OWLOntologyManager m, Map<IRI, IRI> iriReplacementMap) {
        this(remap(iriReplacementMap, m.getOWLDataFactory()), m);
    }

    private static Map<OWLEntity, IRI> remap(Map<IRI, IRI> iriReplacementMap, OWLDataFactory df) {
        Map<OWLEntity, IRI> map = new HashMap<>();
        iriReplacementMap.forEach((k, v) -> {
            map.put(df.getOWLClass(k), v);
            map.put(df.getOWLObjectProperty(k), v);
            map.put(df.getOWLDataProperty(k), v);
            map.put(df.getOWLNamedIndividual(k), v);
            map.put(df.getOWLDatatype(k), v);
            map.put(df.getOWLAnnotationProperty(k), v);
        });
        return map;
    }

    /**
     * @param object the object to duplicate
     * @param <O> return type
     * @return the duplicate
     */
    public <O extends OWLObject> O duplicateObject(O object) {
        checkNotNull(object, "object cannot be null");
        return t(object);
    }

    /**
     * Given an IRI belonging to an entity, returns a IRI. This may be the same IRI that the entity
     * has, or an alternative IRI if a replacement has been specified.
     *
     * @param entity The entity
     * @return The IRI
     */
    private IRI t(OWLEntity entity) {
        IRI replacement = replacementMap.get(entity);
        if (replacement == null) {
            return entity.getIRI();
        }
        return replacement;
    }

    @Override
    public OWLLiteral visit(OWLLiteral node) {
        OWLLiteral l = replacementLiterals.get(node);
        if (l != null) {
            return l;
        }
        if (node.hasLang()) {
            return df.getOWLLiteral(node.getLiteral(), node.getLang());
        }
        return df.getOWLLiteral(node.getLiteral(), df.getOWLDatatype(t(node.getDatatype())));
    }

    @Override
    public OWLObjectInverseOf visit(OWLObjectInverseOf property) {
        OWLObjectPropertyExpression inverse = property.getInverse();
        if (inverse.isAnonymous()) {
            return df.getOWLObjectInverseOf(
                df.getOWLObjectProperty(t(property.getNamedProperty())));
        }
        return df.getOWLObjectInverseOf(df.getOWLObjectProperty(t(inverse.asOWLObjectProperty())));
    }

    @Override
    public OWLOntology visit(OWLOntology ontology) {
        // Should we duplicate ontologies here? Probably not.
        return ontology;
    }

    @Override
    public OWLAnonymousIndividual visit(OWLAnonymousIndividual individual) {
        return anonProvider.getOWLAnonymousIndividual(individual.getID().getID());
    }

    @Override
    public IRI visit(IRI iri) {
        for (EntityType<?> entityType : EntityType.values()) {
            OWLEntity entity = df.getOWLEntity(entityType, iri);
            IRI replacementIRI = replacementMap.get(entity);
            if (replacementIRI != null) {
                return replacementIRI;
            }
        }
        return iri;
    }
}
