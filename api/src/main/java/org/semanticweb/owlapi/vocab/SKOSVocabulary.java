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
package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.model.EntityType.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public enum SKOSVocabulary implements HasShortForm, HasIRI, HasPrefixedName {
//@formatter:off
    /** BROADMATCH        */  BROADMATCH          ("broadMatch",          OBJECT_PROPERTY), 
    /** BROADER           */  BROADER             ("broader",             OBJECT_PROPERTY), 
    /** BROADERTRANSITIVE */  BROADERTRANSITIVE   ("broaderTransitive",   OBJECT_PROPERTY), 
    /** CLOSEMATCH        */  CLOSEMATCH          ("closeMatch",          OBJECT_PROPERTY), 
    /** EXACTMATCH        */  EXACTMATCH          ("exactMatch",          OBJECT_PROPERTY), 
    /** HASTOPCONCEPT     */  HASTOPCONCEPT       ("hasTopConcept",       OBJECT_PROPERTY), 
    /** INSCHEME          */  INSCHEME            ("inScheme",            OBJECT_PROPERTY), 
    /** MAPPINGRELATION   */  MAPPINGRELATION     ("mappingRelation",     OBJECT_PROPERTY), 
    /** MEMBER            */  MEMBER              ("member",              OBJECT_PROPERTY), 
    /** MEMBERLIST        */  MEMBERLIST          ("memberList",          OBJECT_PROPERTY), 
    /** NARROWMATCH       */  NARROWMATCH         ("narrowMatch",         OBJECT_PROPERTY), 
    /** NARROWER          */  NARROWER            ("narrower",            OBJECT_PROPERTY), 
    /** NARROWTRANSITIVE  */  NARROWTRANSITIVE    ("narrowTransitive",    OBJECT_PROPERTY), 
    /** RELATED           */  RELATED             ("related",             OBJECT_PROPERTY), 
    /** RELATEDMATCH      */  RELATEDMATCH        ("relatedMatch",        OBJECT_PROPERTY), 
    /** SEMANTICRELATION  */  SEMANTICRELATION    ("semanticRelation",    OBJECT_PROPERTY), 
    /** TOPCONCEPTOF      */  TOPCONCEPTOF        ("topConceptOf",        OBJECT_PROPERTY), 
    /** COLLECTION        */  COLLECTION          ("Collection",          CLASS), 
    /** CONCEPT           */  CONCEPT             ("Concept",             CLASS), 
    /** CONCEPTSCHEME     */  CONCEPTSCHEME       ("ConceptScheme",       CLASS), 
    /** ORDEREDCOLLECTION */  ORDEREDCOLLECTION   ("OrderedCollection",   CLASS), 
    /** TOPCONCEPT        */  TOPCONCEPT          ("TopConcept",          CLASS), 
    /** ALTLABEL          */  ALTLABEL            ("altLabel",            ANNOTATION_PROPERTY), 
    /** CHANGENOTE        */  CHANGENOTE          ("changeNote",          ANNOTATION_PROPERTY), 
    /** DEFINITION        */  DEFINITION          ("definition",          ANNOTATION_PROPERTY), 
    /** EDITORIALNOTE     */  EDITORIALNOTE       ("editorialNote",       ANNOTATION_PROPERTY), 
    /** EXAMPLE           */  EXAMPLE             ("example",             ANNOTATION_PROPERTY), 
    /** HIDDENLABEL       */  HIDDENLABEL         ("hiddenLabel",         ANNOTATION_PROPERTY), 
    /** HISTORYNOTE       */  HISTORYNOTE         ("historyNote",         ANNOTATION_PROPERTY), 
    /** NOTE              */  NOTE                ("note",                ANNOTATION_PROPERTY), 
    /** PREFLABEL         */  PREFLABEL           ("prefLabel",           ANNOTATION_PROPERTY), 
    /** SCOPENOTE         */  SCOPENOTE           ("scopeNote",           ANNOTATION_PROPERTY),
    /** @deprecated No longer used */
    @Deprecated
    DOCUMENT("Document", CLASS),
    /** @deprecated No longer used */
    @Deprecated
    IMAGE("Image", CLASS),
    /** @deprecated No longer used */
    @Deprecated
    COLLECTABLEPROPERTY("CollectableProperty", ANNOTATION_PROPERTY),
    /** @deprecated No longer used */
    @Deprecated
    RESOURCE("Resource", CLASS),
    /** @deprecated No longer used */
    @Deprecated
    COMMENT("comment", ANNOTATION_PROPERTY);
//@formatter:on
    /** all IRIs */
    public static final Set<IRI> ALL_IRIS;
    static {
        ALL_IRIS = new HashSet<>();
        for (SKOSVocabulary v : SKOSVocabulary.values()) {
            ALL_IRIS.add(v.getIRI());
        }
    }
    @Nonnull
    private final String localName;
    @Nonnull
    private final IRI iri;
    @Nonnull
    private final EntityType<?> entityType;
    @Nonnull
    private final String prefixedName;

    SKOSVocabulary(@Nonnull String localname, @Nonnull EntityType<?> entityType) {
        localName = localname;
        prefixedName = Namespaces.SKOS.getPrefixName() + ':' + localname;
        this.entityType = entityType;
        iri = IRI.create(Namespaces.SKOS.toString(), localname);
    }

    /** @return entity type */
    public EntityType<?> getEntityType() {
        return entityType;
    }

    /** @return local name */
    public String getLocalName() {
        return localName;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    /**
     * @param dataFactory
     *        data factory to use
     * @return set of SKOS annotation properties
     */
    public static Set<OWLAnnotationProperty> getAnnotationProperties(
            OWLDataFactory dataFactory) {
        Set<OWLAnnotationProperty> result = new HashSet<>();
        for (SKOSVocabulary v : values()) {
            if (v.entityType.equals(ANNOTATION_PROPERTY)) {
                result.add(dataFactory.getOWLAnnotationProperty(v.iri));
            }
        }
        return result;
    }

    /**
     * @param dataFactory
     *        data factory to use
     * @return set of SKOS object properties
     */
    public static Set<OWLObjectProperty> getObjectProperties(
            OWLDataFactory dataFactory) {
        Set<OWLObjectProperty> result = new HashSet<>();
        for (SKOSVocabulary v : values()) {
            if (v.entityType.equals(OBJECT_PROPERTY)) {
                result.add(dataFactory.getOWLObjectProperty(v.iri));
            }
        }
        return result;
    }

    /**
     * @param dataFactory
     *        data factory to use
     * @return set of SKOS data properties
     */
    public static Set<OWLDataProperty> getDataProperties(
            OWLDataFactory dataFactory) {
        Set<OWLDataProperty> result = new HashSet<>();
        for (SKOSVocabulary v : values()) {
            if (v.entityType.equals(DATA_PROPERTY)) {
                result.add(dataFactory.getOWLDataProperty(v.iri));
            }
        }
        return result;
    }

    /**
     * @param dataFactory
     *        data factory to use
     * @return set of SKOS classes
     */
    public static Set<OWLClass> getClasses(OWLDataFactory dataFactory) {
        Set<OWLClass> result = new HashSet<>();
        for (SKOSVocabulary v : values()) {
            if (v.entityType.equals(CLASS)) {
                result.add(dataFactory.getOWLClass(v.iri));
            }
        }
        return result;
    }

    @Override
    @Nonnull
    public String getShortForm() {
        return localName;
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }
}
