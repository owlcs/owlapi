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
package org.coode.owlapi.obo12.parser;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br>
 * <br>
 */
class OBOConsumer implements OBOParserHandler {

    private static final Logger LOGGER = Logger
        .getLogger(OBOConsumer.class.getName());
    private static final String IMPORT_TAG_NAME = "import";
    @Nonnull
    private final OWLOntologyLoaderConfiguration configuration;
    private final OWLOntologyManager owlOntologyManager;
    @Nonnull
    private final OWLOntology ontology;
    private boolean inHeader;
    private String currentId;
    private Map<String, TagValueHandler> handlerMap;
    // private String defaultNamespace;
    private String defaultNamespaceTagValue = OBOVocabulary.OBO_IRI_BASE;
    private String stanzaType;
    private boolean termType;
    private boolean typedefType;
    private boolean instanceType;
    @Nonnull
    private final Set<OWLClassExpression> intersectionOfOperands;
    @Nonnull
    private final Set<OWLClassExpression> unionOfOperands = new HashSet<>();
    private Map<String, IRI> uriCache = new HashMap<>();
    private final Map<String, IRI> symbolicIdCache = new HashMap<>();
    private final Map<String, IRI> tagIRICache = new HashMap<>();
    private final IDSpaceManager idSpaceManager = new IDSpaceManager();
    private String ontologyTagValue = "";
    private String dataVersionTagValue = "";

    public OBOConsumer(@Nonnull OWLOntology ontology,
        @Nonnull OWLOntologyLoaderConfiguration configuration, IRI baseIRI) {
        this.configuration = configuration;
        owlOntologyManager = ontology.getOWLOntologyManager();
        this.ontology = ontology;
        intersectionOfOperands = new HashSet<>();
        uriCache = new HashMap<>();
        for (OBOVocabulary v : OBOVocabulary.values()) {
            tagIRICache.put(v.getName(), v.getIRI());
        }
        ontologyTagValue = getDefaultOntologyTagValue(baseIRI);
        loadBuiltinURIs();
        setupTagHandlers();
    }

    private static String getDefaultOntologyTagValue(IRI baseIRI) {
        URI baseURI = baseIRI.toURI();
        String baseURIPath = baseURI.getPath();
        if (baseURIPath == null) {
            return baseIRI.toString();
        }
        int lastSlashIndex = baseURIPath.lastIndexOf("/");
        if (lastSlashIndex == -1
            || lastSlashIndex + 1 == baseURIPath.length()) {
            return baseURIPath;
        }
        return baseURIPath.substring(lastSlashIndex + 1);
    }

    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }

    @Nonnull
    public OWLOntology getOntology() {
        return ontology;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void addSymbolicIdMapping(String symbolicName, IRI fullIRI) {
        symbolicIdCache.put(symbolicName, fullIRI);
    }

    /**
     * Sets the value of the default-namespace tag for the current ontology
     * being parsed.
     * 
     * @param defaultNamespaceTagValue
     *        The value of the default-namespace tag.
     */
    public void setDefaultNamespaceTagValue(String defaultNamespaceTagValue) {
        this.defaultNamespaceTagValue = defaultNamespaceTagValue;
    }

    /**
     * Gets the default-namespace tag value for the current ontology. If not
     * default-namespace tag value has been set explicitly then this method
     * returns the default value which is equal to
     * {@link OBOVocabulary#OBO_IRI_BASE}.
     * 
     * @return The default-namespace tag value. Not <code>null</code>.
     */
    public String getDefaultNamespaceTagValue() {
        return defaultNamespaceTagValue;
    }

    /**
     * Sets the value of the ontology tag for the current ontology that is being
     * parsed. This is used to construct an
     * {@link org.semanticweb.owlapi.model.OWLOntologyID} for the current
     * ontology once the ontology header has been parsed in its entirety.
     * 
     * @param ontologyTagValue
     *        The ontology tag value. Ultimately, this will be translated to an
     *        IRI.
     */
    public void setOntologyTagValue(String ontologyTagValue) {
        this.ontologyTagValue = ontologyTagValue;
    }

    /**
     * Sets the value of the data-version tag for the current ontology that is
     * being parsed. This is used to construct an
     * {@link org.semanticweb.owlapi.model.OWLOntologyID} for the current
     * ontology once the ontology header has been parsed in its entirety.
     * 
     * @param dataVersionTagValue
     *        The data-version tag value. Ultimately, this will be translated to
     *        an IRI.
     */
    public void setDataVersionTagValue(String dataVersionTagValue) {
        this.dataVersionTagValue = dataVersionTagValue;
    }

    public void registerIdSpace(String idSpacePrefix, String iriPrefix) {
        idSpaceManager.setIRIPrefix(idSpacePrefix, iriPrefix);
    }

    /**
     * Gets a COPY of the {@link IDSpaceManager} held by this OBOConsumer.
     * 
     * @return A copy of the IDSpaceManager held by this consumer.
     */
    public IDSpaceManager getIdSpaceManager() {
        return idSpaceManager;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public void addUnionOfOperand(OWLClassExpression classExpression) {
        unionOfOperands.add(classExpression);
    }

    public void addIntersectionOfOperand(OWLClassExpression classExpression) {
        intersectionOfOperands.add(classExpression);
    }

    public String getStanzaType() {
        return stanzaType;
    }

    public boolean isTerm() {
        return termType;
    }

    public boolean isTypedef() {
        return typedefType;
    }

    public boolean isInstanceType() {
        return instanceType;
    }

    private void loadBuiltinURIs() {
        for (OBOVocabulary v : OBOVocabulary.values()) {
            uriCache.put(v.getName(), v.getIRI());
        }
    }

    private void setupTagHandlers() {
        handlerMap = new HashMap<>();
        addTagHandler(new OntologyTagValueHandler(this));
        addTagHandler(new IDTagValueHandler(this));
        addTagHandler(new NameTagValueHandler(this));
        addTagHandler(new IsATagValueHandler(this));
        addTagHandler(new PartOfTagValueHandler(this));
        addTagHandler(new TransitiveTagValueHandler(this));
        addTagHandler(new SymmetricTagValueHandler(this));
        addTagHandler(new RelationshipTagValueHandler(this));
        addTagHandler(new UnionOfHandler(this));
        addTagHandler(new IntersectionOfHandler(this));
        addTagHandler(new DisjointFromHandler(this));
        addTagHandler(new AsymmetricHandler(this));
        addTagHandler(new InverseHandler(this));
        addTagHandler(new ReflexiveHandler(this));
        addTagHandler(new TransitiveOverHandler(this));
        addTagHandler(new DefaultNamespaceTagValueHandler(this));
        addTagHandler(new SynonymTagValueHandler(this));
        addTagHandler(new XRefTagHandler(this));
        addTagHandler(new DefTagValueHandler(this));
        addTagHandler(new IsObsoleteTagValueHandler(this));
        addTagHandler(new IDSpaceTagValueHandler(this));
        addTagHandler(new DataVersionTagValueHandler(this));
        addTagHandler(new SynonymTypeDefTagHandler(this));
        addTagHandler(new AltIdTagValueHandler(this));
    }

    private void addTagHandler(TagValueHandler handler) {
        handlerMap.put(handler.getTagName(), handler);
    }

    @Override
    public void startHeader() {
        inHeader = true;
    }

    @Override
    public void endHeader() {
        inHeader = false;
        setOntologyId();
    }

    /**
     * Sets the {@link org.semanticweb.owlapi.model.OWLOntologyID} of the
     * ontology being parsed from the tag values held by the
     * {@link #ontologyTagValue} and {@link #dataVersionTagValue} field. IRIs
     * for each field are generated and used to construct the
     * {@link org.semanticweb.owlapi.model.OWLOntologyID}.
     */
    private void setOntologyId() {
        IRI ontologyIRI = IRI.create(
            idSpaceManager.getIRIPrefix(ontologyTagValue) + ontologyTagValue);
        IRI versionIRI = null;
        if (dataVersionTagValue.length() > 0) {
            versionIRI = IRI
                .create(ontologyIRI.toString() + "/" + dataVersionTagValue);
        }
        OWLOntologyID ontologyID = new OWLOntologyID(optional(ontologyIRI),
            optional(versionIRI));
        ontology.getOWLOntologyManager()
            .applyChange(new SetOntologyID(ontology, ontologyID));
    }

    @Override
    public void startFrame(String name) {
        currentId = null;
        defaultNamespaceTagValue = null;
        stanzaType = name;
        termType = stanzaType.equals(OBOVocabulary.TERM.getName());
        typedefType = false;
        instanceType = false;
        if (!termType) {
            typedefType = stanzaType.equals(OBOVocabulary.TYPEDEF.getName());
            if (!typedefType) {
                instanceType = stanzaType
                    .equals(OBOVocabulary.INSTANCE.getName());
            }
        }
    }

    @Override
    public void endFrame() {
        if (!unionOfOperands.isEmpty()) {
            createUnionEquivalentClass();
            unionOfOperands.clear();
        }
        if (!intersectionOfOperands.isEmpty()) {
            createIntersectionEquivalentClass();
            intersectionOfOperands.clear();
        }
    }

    private void createUnionEquivalentClass() {
        OWLClassExpression equivalentClass;
        if (unionOfOperands.size() == 1) {
            equivalentClass = unionOfOperands.iterator().next();
        } else {
            equivalentClass = getDataFactory()
                .getOWLObjectUnionOf(unionOfOperands);
        }
        createEquivalentClass(equivalentClass);
    }

    private void createIntersectionEquivalentClass() {
        OWLClassExpression equivalentClass;
        if (intersectionOfOperands.size() == 1) {
            equivalentClass = intersectionOfOperands.iterator().next();
        } else {
            equivalentClass = getDataFactory()
                .getOWLObjectIntersectionOf(intersectionOfOperands);
        }
        createEquivalentClass(equivalentClass);
    }

    private void createEquivalentClass(OWLClassExpression classExpression) {
        OWLAxiom ax = getDataFactory().getOWLEquivalentClassesAxiom(
            CollectionFactory.createSet(getCurrentClass(), classExpression));
        getOWLOntologyManager().applyChange(new AddAxiom(ontology, ax));
    }

    @Override
    public void handleTagValue(String tag, String value, String qualifierBlock,
        String comment) {
        try {
            TagValueHandler handler = handlerMap.get(tag);
            if (handler != null) {
                handler.handle(currentId, value, qualifierBlock, comment);
            } else if (inHeader) {
                if (tag.equals(IMPORT_TAG_NAME)) {
                    String trim = value.trim();
                    IRI uri = IRI.create(trim);
                    OWLImportsDeclaration decl = owlOntologyManager
                        .getOWLDataFactory().getOWLImportsDeclaration(uri);
                    owlOntologyManager.makeLoadImportRequest(decl,
                        configuration);
                    owlOntologyManager
                        .applyChange(new AddImport(ontology, decl));
                } else {
                    // Ontology annotations
                    OWLLiteral con = getDataFactory()
                        .getOWLLiteral(unescapeTagValue(value));
                    OWLAnnotationProperty property = getDataFactory()
                        .getOWLAnnotationProperty(getIRIFromTagName(tag));
                    OWLAnnotation anno = getDataFactory()
                        .getOWLAnnotation(property, con);
                    owlOntologyManager
                        .applyChange(new AddOntologyAnnotation(ontology, anno));
                }
            } else if (currentId != null) {
                // Add as annotation
                if (configuration.isLoadAnnotationAxioms()) {
                    IRI subject = getIRI(currentId);
                    OWLLiteral con = getDataFactory()
                        .getOWLLiteral(unescapeTagValue(value));
                    IRI annotationPropertyIRI = getIRIFromTagName(tag);
                    OWLAnnotationProperty property = getDataFactory()
                        .getOWLAnnotationProperty(annotationPropertyIRI);
                    OWLAnnotation anno = getDataFactory()
                        .getOWLAnnotation(property, con);
                    OWLAnnotationAssertionAxiom ax = getDataFactory()
                        .getOWLAnnotationAssertionAxiom(subject, anno);
                    owlOntologyManager.addAxiom(ontology, ax);
                    OWLDeclarationAxiom annotationPropertyDeclaration = getDataFactory()
                        .getOWLDeclarationAxiom(property);
                    owlOntologyManager.addAxiom(ontology,
                        annotationPropertyDeclaration);
                }
            }
        } catch (UnloadableImportException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Nonnull
    public String unescapeTagValue(String value) {
        String unquoted;
        if (value.startsWith("\"") && value.endsWith("\"")) {
            unquoted = value.substring(1, value.length() - 1);
        } else {
            unquoted = value;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < unquoted.length(); i++) {
            char ch = unquoted.charAt(i);
            if (ch != '\\') {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private OWLDataFactory getDataFactory() {
        return getOWLOntologyManager().getOWLDataFactory();
    }

    public OWLClass getCurrentClass() {
        return getDataFactory().getOWLClass(getIRI(currentId));
    }

    public OWLEntity getCurrentEntity() {
        if (isTerm()) {
            return getCurrentClass();
        } else if (isTypedef()) {
            // Sometimes, we can have symbolic names e.g. has_part. It's not
            // really clear how to tell the difference
            IRI propertyIRI = getRelationIRIFromSymbolicIdOrOBOId(currentId);
            return getDataFactory().getOWLObjectProperty(propertyIRI);
        } else {
            return getDataFactory().getOWLNamedIndividual(getIRI(currentId));
        }
    }

    /**
     * Gets an IRI from a tag name.
     * 
     * @param tagName
     *        The tag name.
     * @return The IRI for the tag name. For built in tags this is obtained from
     *         the {@link OBOVocabulary} enum. Not <code>null</code>.
     * @throws NullPointerException
     *         if tagName is null.
     */
    @Nonnull
    public IRI getIRIFromTagName(String tagName) {
        checkNotNull(tagName, "tagName must not be null");
        IRI tagIRI = tagIRICache.get(tagName);
        if (tagIRI != null) {
            return tagIRI;
        } else {
            IRI freshTagIRI = IRI.create(OBOVocabulary.OBO_IRI_BASE + tagName);
            tagIRICache.put(tagName, freshTagIRI);
            return freshTagIRI;
        }
    }

    /**
     * Gets an IRI from an OBO ID. The OBO ID may be a canonical OBO ID of the
     * form idspace:sequence or it may be a non-canonical ID.
     * 
     * @param oboId
     *        The OBO ID
     * @return An IRI obtained from the translation of the OBO ID.
     */
    @Nonnull
    public IRI getIRIFromOBOId(String oboId) {
        checkNotNull(oboId, "oboId must not be null.");
        return getIRI(oboId);
    }

    @Nonnull
    public IRI getRelationIRIFromSymbolicIdOrOBOId(String symbolicIdOrOBOId) {
        IRI fullIRI = symbolicIdCache.get(symbolicIdOrOBOId);
        if (fullIRI != null) {
            return fullIRI;
        }
        OBOIdType idType = OBOIdType.getIdType(symbolicIdOrOBOId);
        if (idType == null) {
            throw new OWLRuntimeException(
                "Invalid ID: " + symbolicIdOrOBOId + " in frame " + currentId);
        } else {
            return idType.getIRIFromOBOId(ontology.getOntologyID(),
                idSpaceManager, symbolicIdOrOBOId);
        }
    }

    @Nonnull
    private IRI getIRI(String s) {
        String trimmed = s.trim();
        IRI iri = uriCache.get(trimmed);
        if (iri != null) {
            return iri;
        }
        OWLOntologyID ontologyID = getOntology().getOntologyID();
        OBOIdType type = OBOIdType.getIdType(trimmed);
        if (type == null) {
            throw new OWLRuntimeException("Not a valid OBO ID: " + s);
        }
        IRI freshIRI = type.getIRIFromOBOId(ontologyID, idSpaceManager,
            trimmed);
        uriCache.put(trimmed, freshIRI);
        return freshIRI;
    }

    private static final Pattern XREF_PATTERN = Pattern
        .compile("([^\"]*)\\s*(\"((\\\"|[^\"])*)\")?");
    private static final int XREF_ID_GROUP = 1;
    private static final int XREF_QUOTED_STRING_GROUP = 3;

    public OWLAnnotation parseXRef(@Nonnull String xref) {
        Matcher matcher = XREF_PATTERN.matcher(xref);
        if (matcher.matches()) {
            OWLDataFactory df = getDataFactory();
            String xrefQuotedString = matcher.group(XREF_QUOTED_STRING_GROUP);
            // the quoted string is a description of the xref. I can't find
            // anywhere to put this.
            // Just add as a comment for now
            @Nonnull
            Set<OWLAnnotation> xrefDescriptions = new HashSet<>();
            if (xrefQuotedString != null) {
                xrefDescriptions.add(df.getOWLAnnotation(df.getRDFSComment(),
                    df.getOWLLiteral(xrefQuotedString)));
            }
            String xrefId = matcher.group(XREF_ID_GROUP).trim();
            OBOIdType idType = OBOIdType.getIdType(xrefId);
            OWLAnnotationValue annotationValue;
            if (idType != null) {
                annotationValue = getIRIFromOBOId(xrefId);
            } else {
                annotationValue = getDataFactory().getOWLLiteral(xrefId);
            }
            OWLAnnotationProperty xrefProperty = df
                .getOWLAnnotationProperty(OBOVocabulary.XREF.getIRI());
            return df.getOWLAnnotation(xrefProperty, annotationValue,
                xrefDescriptions);
        } else {
            OWLDataFactory df = getDataFactory();
            OWLAnnotationProperty xrefProperty = df
                .getOWLAnnotationProperty(OBOVocabulary.XREF.getIRI());
            return df.getOWLAnnotation(xrefProperty, df.getOWLLiteral(xref));
        }
    }
}
