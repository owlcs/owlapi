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

package org.semanticweb.owlapi.util;

import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;
import org.semanticweb.owlapi.model.OWLPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Jun-2007<br><br>
 * <p/>
 * A short form provider that generates short forms from the values of object
 * property assertions or data property assertions if the entity is an individual.
 * If the entity whose short form is not being generated is not an individual
 * (i.e. it is a class, property etc.) then an alternate short form provider is
 * used.  (As a side note, the use case for this particular short form provider
 * came from the SKOS community, which have individuals that have preferredLabel
 * property assertions).
 */
public class PropertyAssertionValueShortFormProvider implements ShortFormProvider {

    private List<OWLPropertyExpression<?,?>> properties;

    private Map<OWLDataPropertyExpression, List<String>> preferredLanguageMap;

    private OWLOntologySetProvider ontologySetProvider;

    private ShortFormProvider alternateShortFormProvider;


    /**
     * Constructs a property value short form provider. Using <code>SimpleShortFormProvider</code> as the
     * alternate short form provider (see other constructor for details).
     */
    public PropertyAssertionValueShortFormProvider(List<OWLPropertyExpression<?,?>> properties, Map<OWLDataPropertyExpression, List<String>> preferredLanguageMap,
                                                   OWLOntologySetProvider ontologySetProvider) {
        this(properties, preferredLanguageMap, ontologySetProvider, new SimpleShortFormProvider());
    }


    /**
     * Constructs a property value short form provider.
     *
     * @param properties                 A <code>List</code> of preferred properties.  The list is searched from
     *                                   start to end, so that property assertions whose property is at the start of the list have a higher
     *                                   priority and are selected over properties that appear towards or at the end of the list.
     * @param preferredLanguageMap       A map which maps data properties to preferred languages.  For any given
     *                                   data property there may be a list of preferred languages for the values of that property
     *                                   Languages at the start of the list
     *                                   have a higher priority over languages at the end of the list.  This parameter may be empty but it
     *                                   must not be <code>null</code>.
     * @param ontologySetProvider        An <code>OWLOntologySetProvider</code> which provides a set of ontology
     *                                   from which candidate annotation axioms should be taken.  For a given entity, all ontologies are
     *                                   examined.
     * @param alternateShortFormProvider A short form provider which will be used to generate the short form
     *                                   for an entity that does not have any property values (e.g. class, property).  This provider will also be used in the case where
     *                                   the value of an annotation is an <code>OWLIndividual</code> for providing the short form of the individual.
     */
    public PropertyAssertionValueShortFormProvider(List<OWLPropertyExpression<?,?>> properties, Map<OWLDataPropertyExpression, List<String>> preferredLanguageMap,
                                                   OWLOntologySetProvider ontologySetProvider,
                                                   ShortFormProvider alternateShortFormProvider) {
        this.properties = properties;
        this.preferredLanguageMap = preferredLanguageMap;
        this.ontologySetProvider = ontologySetProvider;
        this.alternateShortFormProvider = alternateShortFormProvider;
    }


    public String getShortForm(OWLEntity entity) {
        int lastURIMatchIndex = Integer.MAX_VALUE;
        int lastLangMatchIndex = Integer.MAX_VALUE;
        if (!(entity instanceof OWLIndividual)) {
            return alternateShortFormProvider.getShortForm(entity);
        }
        OWLIndividual individual = (OWLIndividual) entity;
        // The candidate value to be rendered, we select this based on
        // ranking of annotation URI and ranking of lang (if present)
        OWLObject candidateValue = null;
        for (OWLOntology ontology : ontologySetProvider.getOntologies()) {

            for (OWLObjectPropertyAssertionAxiom ax : ontology.getObjectPropertyAssertionAxioms(individual)) {
                int index = properties.indexOf(ax.getProperty());
                if (index == -1) {
                    continue;
                }
                if (index < lastURIMatchIndex) {
                    candidateValue = ax.getObject();
                }
            }
            for (OWLDataPropertyAssertionAxiom ax : ontology.getDataPropertyAssertionAxioms(individual)) {
                int index = properties.indexOf(ax.getProperty());
                if (index == -1) {
                    continue;
                }
                if (index == lastURIMatchIndex) {
                    // Different property value but same prop, as previous candidate - look at lang tag for that URI
                    // and see if we take priority over the previous one
                    OWLObject obj = ax.getObject();
                    if (obj instanceof OWLLiteral) {
                        List<String> langList = preferredLanguageMap.get(ax.getProperty());
                        if (langList != null) {
                            // There is no need to check if lang is null.  It may well be that no
                            // lang is preferred over any other lang.
                            OWLLiteral lit = (OWLLiteral) obj;
                            int langIndex = langList.indexOf(lit.getLang());
                            if (langIndex != -1 && langIndex < lastLangMatchIndex) {
                                lastLangMatchIndex = langIndex;
                                candidateValue = ax.getObject();
                            }
                        }

                    }
                } else if (index < lastURIMatchIndex) {
                    // Better match than previous URI - wipe out previous match!
                    lastURIMatchIndex = index;
                    candidateValue = ax.getObject();
                }
            }


        }

        if (candidateValue != null) {
            return getRendering(candidateValue);
        } else {
            return alternateShortFormProvider.getShortForm(entity);
        }
    }


    /**
     * Obtains the rendering of the specified object.  If the object
     * is a constant then the rendering is equal to the literal value,
     * if the object is an individual then the rendering is equal to
     * the rendering of the individual as provided by the alternate
     * short form provider
     *
     * @param object The object to the rendered
     * @return The rendering of the object.
     */
    private String getRendering(OWLObject object) {
        // We return the literal value of constants or use the alternate
        // short form provider to render individuals.
        if (object instanceof OWLLiteral) {
            return ((OWLLiteral) object).getLiteral();
        } else {
            return alternateShortFormProvider.getShortForm((OWLEntity) object);
        }
    }


    public List<OWLPropertyExpression<?,?>> getProperties() {
        return properties;
    }


    public Map<OWLDataPropertyExpression, List<String>> getPreferredLanguageMap() {
        return preferredLanguageMap;
    }


    public void dispose() {
    }
}
