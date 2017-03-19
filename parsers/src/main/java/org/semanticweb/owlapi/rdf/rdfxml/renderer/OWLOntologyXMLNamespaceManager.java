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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.NamespaceUtil;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;

/**
 * The OWLOntologyNamespaceManager wraps a NamespaceManager (OWLDocumentFormat). In the case where
 * the appropriate prefixes and mappings don't exist in the NamespaceManager (OWLDocumentFormat)
 * this manager will create them.
 *
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics Group
 * @since 2.0.0
 */
public class OWLOntologyXMLNamespaceManager extends XMLWriterNamespaceManager {

    private final OWLOntology ontology;
    private final NamespaceUtil namespaceUtil = new NamespaceUtil();
    private final OWLDocumentFormat ontologyFormat;

    /**
     * @param ontology ontology
     * @param format format
     */
    public OWLOntologyXMLNamespaceManager(OWLOntology ontology, OWLDocumentFormat format) {
        super(getDefaultNamespace(ontology, format));
        this.ontology = checkNotNull(ontology, "ontology cannot be null");
        ontologyFormat = checkNotNull(format, "format cannot be null");
        addWellKnownNamespace("skos", Namespaces.SKOS.toString());
        addWellKnownNamespace("dc", DublinCoreVocabulary.NAME_SPACE);
        processOntology();
    }

    /**
     * Gets a suggested default namespace bases on the ID of an ontology. If the ontology has an IRI
     * then this IRI will be used to suggest a default namespace, otherwise, the OWL namespace will
     * be returned as the default namespace
     *
     * @param ontology The ontology
     * @param format format
     * @return A suggested default namespace
     */
    private static String getDefaultNamespace(OWLOntology ontology, OWLDocumentFormat format) {
        checkNotNull(ontology, "ontology cannot be null");
        checkNotNull(format, "format cannot be null");
        String defaultPrefix = ontology.getPrefixManager().getDefaultPrefix();
        if (defaultPrefix != null) {
            return defaultPrefix;
        }
        if (ontology.getOntologyID().isAnonymous()) {
            // What do we return here? Just return the OWL namespace for now.
            return Namespaces.OWL.toString();
        } else {
            String base = ontology.getOntologyID().getOntologyIRI().get().toString();
            if (!base.endsWith("#") && !base.endsWith("/")) {
                base += "#";
            }
            return base;
        }
    }

    protected OWLOntology getOntology() {
        return ontology;
    }

    private final void processOntology() {
        Map<String, String> namespacesByPrefix =
            ontology.getPrefixManager().getPrefixName2PrefixMap();
        for (Map.Entry<String, String> e : namespacesByPrefix.entrySet()) {
            String xmlnsPrefixName = e.getKey().substring(0, e.getKey().length() - 1);
            namespaceUtil.setPrefix(verifyNotNull(e.getValue()), verifyNotNull(xmlnsPrefixName));
        }
        if (ontology.getAxiomCount(AxiomType.SWRL_RULE) != 0) {
            namespaceUtil.setPrefix(Namespaces.SWRL.toString(), "swrl");
            namespaceUtil.setPrefix(Namespaces.SWRLB.toString(), "swrlb");
        }
        getEntitiesThatRequireNamespaces().forEach(this::processEntity);
        namespaceUtil.getNamespace2PrefixMap().forEach(this::setOWL2Prefix);
    }

    protected void setOWL2Prefix(String k, String v) {
        if (!Namespaces.OWL11.inNamespace(k) && !Namespaces.OWL11XML.inNamespace(k)) {
            setPrefix(v, k);
        }
    }

    protected Set<OWLEntity> getEntitiesThatRequireNamespaces() {
        Set<OWLEntity> result = new HashSet<>();
        add(result, ontology.classesInSignature());
        add(result, ontology.objectPropertiesInSignature());
        add(result, ontology.dataPropertiesInSignature());
        add(result, ontology.individualsInSignature());
        add(result, ontology.annotationPropertiesInSignature());
        return result;
    }

    private void processEntity(OWLNamedObject entity) {
        processIRI(checkNotNull(entity, "entity cannot be null").getIRI());
    }

    private void processIRI(IRI iri) {
        String ns = checkNotNull(iri, "iri cannot be null").getNamespace();
        if (!(ns.isEmpty() || !iri.getRemainder().isPresent())) {
            namespaceUtil.getPrefix(ns);
        }
    }

    @Override
    @Nullable
    public String getQName(String name) {
        checkNotNull(name, "name cannot be null");
        String ns = XMLUtils.getNCNamePrefix(name);
        String fragment = XMLUtils.getNCNameSuffix(name);
        if (ns.equals(getDefaultNamespace())) {
            return fragment;
        }
        if (name.startsWith("xmlns") || name.startsWith("xml:")) {
            return name;
        }
        if (ns.isEmpty() || fragment == null || fragment.isEmpty()) {
            // Couldn't split
            return name;
        }
        String prefix = getPrefixForNamespace(ns);
        if (prefix == null) {
            return null;
        }
        if (prefix.isEmpty()) {
            return fragment;
        }
        return prefix + ":" + fragment;
    }
}
