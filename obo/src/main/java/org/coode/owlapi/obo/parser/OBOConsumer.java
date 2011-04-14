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

package org.coode.owlapi.obo.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public class OBOConsumer implements OBOParserHandler {

    private static final Logger logger = Logger.getLogger(OBOConsumer.class.getName());

    private static final String IMPORT_TAG_NAME = "import";

    private OWLOntologyLoaderConfiguration configuration;

    private OWLOntologyManager owlOntologyManager;

    private OWLOntology ontology;

    private boolean inHeader;

    private String currentId;

    private Map<String, TagValueHandler> handlerMap;

    private String defaultNamespace;

    private String currentNamespace;

    private String stanzaType;

    private boolean termType;

    private boolean typedefType;

    private boolean instanceType;

    private Set<OWLClassExpression> intersectionOfOperands;

    private Set<OWLClassExpression> unionOfOperands;

    private Map<String, IRI> uriCache;


    @SuppressWarnings("deprecation")
    public OBOConsumer(OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) {
        this.configuration = configuration;
        this.owlOntologyManager = ontology.getOWLOntologyManager();
        this.ontology = ontology;
        defaultNamespace = OBOVocabulary.ONTOLOGY_URI_BASE;
        intersectionOfOperands = new HashSet<OWLClassExpression>();
        unionOfOperands = new HashSet<OWLClassExpression>();
        uriCache = new HashMap<String, IRI>();
        loadBuiltinURIs();
        setupTagHandlers();
    }

    public OBOConsumer(OWLOntologyManager owlOntologyManager, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) {
        this(ontology, configuration);
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }


    public OWLOntology getOntology() {
        return ontology;
    }


    public String getCurrentId() {
        return currentId;
    }


    public String getDefaultNamespace() {
        return defaultNamespace;
    }


    public void setDefaultNamespace(String defaultNamespace) {
        this.defaultNamespace = defaultNamespace;
    }


    public String getCurrentNamespace() {
        return currentNamespace;
    }


    public void setCurrentNamespace(String currentNamespace) {
        this.currentNamespace = currentNamespace;
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
        handlerMap = new HashMap<String, TagValueHandler>();
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
    }


    private void addTagHandler(TagValueHandler handler) {
        handlerMap.put(handler.getTag(), handler);
    }


    public void startHeader() {
        inHeader = true;
    }


    public void endHeader() {
        inHeader = false;
    }


    public void startStanza(String name) {
        currentId = null;
        currentNamespace = null;
        stanzaType = name;
        termType = stanzaType.equals(OBOVocabulary.TERM.getName());
        typedefType = false;
        instanceType = false;
        if (!termType) {
            typedefType = stanzaType.equals(OBOVocabulary.TYPEDEF.getName());
            if (!typedefType) {
                instanceType = stanzaType.equals(OBOVocabulary.INSTANCE.getName());
            }
        }
    }


    public void endStanza() {
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
        }
        else {
            equivalentClass = getDataFactory().getOWLObjectUnionOf(unionOfOperands);
        }
        createEquivalentClass(equivalentClass);
    }


    private void createIntersectionEquivalentClass() {
        OWLClassExpression equivalentClass;
        if (intersectionOfOperands.size() == 1) {
            equivalentClass = intersectionOfOperands.iterator().next();
        }
        else {
            equivalentClass = getDataFactory().getOWLObjectIntersectionOf(intersectionOfOperands);
        }
        createEquivalentClass(equivalentClass);
    }


    private void createEquivalentClass(OWLClassExpression classExpression) {
        OWLAxiom ax = getDataFactory().getOWLEquivalentClassesAxiom(CollectionFactory.createSet(getCurrentClass(), classExpression));
        getOWLOntologyManager().applyChange(new AddAxiom(ontology, ax));
    }


    public void handleTagValue(String tag, String value, String comment) {
        try {
            TagValueHandler handler = handlerMap.get(tag);
            if (handler != null) {
                handler.handle(currentId, value, comment);
            }
            else if (inHeader) {
                if (tag.equals(IMPORT_TAG_NAME)) {
                    IRI uri = IRI.create(value.trim());
                    OWLImportsDeclaration decl = owlOntologyManager.getOWLDataFactory().getOWLImportsDeclaration(uri);
                    owlOntologyManager.makeLoadImportRequest(decl, configuration);
                    owlOntologyManager.applyChange(new AddImport(ontology, decl));
                }
                else {
                    // Ontology annotations
                    OWLLiteral con = getDataFactory().getOWLLiteral(value);
                    OWLAnnotationProperty property = getDataFactory().getOWLAnnotationProperty(getIRI(tag));
                    OWLAnnotation anno = getDataFactory().getOWLAnnotation(property, con);
                    owlOntologyManager.applyChange(new AddOntologyAnnotation(ontology, anno));
                }
            }
            else if (currentId != null) {
                // Add as annotation
                if (configuration.isLoadAnnotationAxioms()) {
                    IRI subject = getIRI(currentId);
                    OWLLiteral con = getDataFactory().getOWLLiteral(value, "");
                    OWLAnnotationProperty property = getDataFactory().getOWLAnnotationProperty(getIRI(tag));
                    OWLAnnotation anno = getDataFactory().getOWLAnnotation(property, con);
                    OWLAnnotationAssertionAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(subject, anno);
                    owlOntologyManager.addAxiom(ontology, ax);
                    OWLDeclarationAxiom annotationPropertyDeclaration = getDataFactory().getOWLDeclarationAxiom(property);
                    owlOntologyManager.addAxiom(ontology, annotationPropertyDeclaration);
                }
            }

        }
        catch (UnloadableImportException e) {
            logger.severe(e.getMessage());
        }
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
        }
        else if (isTypedef()) {
            return getDataFactory().getOWLObjectProperty(getIRI(currentId));
        }
        else {
            return getDataFactory().getOWLNamedIndividual(getIRI(currentId));
        }
    }

    public IRI getTagIRI(String tagName) {
        return getIRI(tagName);
    }

    public IRI getIdIRI(String identifier) {
        if(identifier == null) {
            Thread.dumpStack();
        }
        if(identifier.indexOf(":") != -1) {
            return getIRI(identifier);
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append(defaultNamespace);
            sb.append(":");
            sb.append(identifier);
            return getIRI(sb.toString());
        }
    }

    private IRI getIRI(String s) {
        IRI iri = uriCache.get(s);
        if (iri != null) {
            return iri;
        }
        String escapedString = s.replace(" ", "%20");
        iri = OBOVocabulary.ID2IRI(escapedString);
        uriCache.put(s, iri);
        return iri;
    }
}
