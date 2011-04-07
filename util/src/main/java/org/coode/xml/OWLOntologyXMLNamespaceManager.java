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

package org.coode.xml;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.NamespaceUtil;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Medical Informatics Group<br> Date:
 * 30-May-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br> www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * Developed as part of the CO-ODE project http://www.co-ode.org
 * <p/>
 * <p/>
 * The OWLOntologyNamespaceManager wraps a NamespaceManager (OWLOntologyFormat).  In the case where the appropriate
 * prefixes and mappings don't exist in the NamespaceManager (OWLOntologyFormat) this manager will create them.
 */
public class OWLOntologyXMLNamespaceManager extends XMLWriterNamespaceManager {

    //private OWLOntologyManager man;

    private OWLOntology ontology;

    private NamespaceUtil namespaceUtil;

    private static String[] splitResults = new String[2];

    private OWLOntologyFormat ontologyFormat;


    public OWLOntologyXMLNamespaceManager(OWLOntologyManager man, OWLOntology ontology) {
        this(ontology, man.getOntologyFormat(ontology));
    }


    public OWLOntologyXMLNamespaceManager(OWLOntology ontology, OWLOntologyFormat format) {
        super(getDefaultNamespace(ontology, format));
        //this.man = man;
        this.ontology = ontology;
        namespaceUtil = new NamespaceUtil();
        ontologyFormat = format;
        addWellKnownNamespace("skos", Namespaces.SKOS.toString());
        addWellKnownNamespace("dc", DublinCoreVocabulary.NAME_SPACE);
        processOntology();
    }


    protected OWLOntology getOntology() {
        return ontology;
    }


    private void processOntology() {
        namespaceUtil = new NamespaceUtil();
        if (ontologyFormat instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat namespaceFormat = (PrefixOWLOntologyFormat) ontologyFormat;
            Map<String, String> namespacesByPrefix = namespaceFormat.getPrefixName2PrefixMap();
            for (String prefixName : namespacesByPrefix.keySet()) {
                    String xmlnsPrefixName = prefixName.substring(0, prefixName.length() - 1);
                    String xmlnsPrefix = namespacesByPrefix.get(prefixName);
                    namespaceUtil.setPrefix(xmlnsPrefix, xmlnsPrefixName);
            }
        }
        if (ontology.getAxiomCount(AxiomType.SWRL_RULE) != 0) {
            namespaceUtil.setPrefix(Namespaces.SWRL.toString(), "swrl");
            namespaceUtil.setPrefix(Namespaces.SWRLB.toString(), "swrlb");
        }

        Set<OWLEntity> entities = getEntitiesThatRequireNamespaces();
        for (OWLEntity ent : entities) {
            processEntity(ent);
        }

        Map<String, String> ns2prefixMap = namespaceUtil.getNamespace2PrefixMap();
        for (String ns : ns2prefixMap.keySet()) {
            if (!ns.equals(Namespaces.OWL11.toString()) && !ns.equals(Namespaces.OWL11XML.toString())) {
                setPrefix(ns2prefixMap.get(ns), ns);
            }
        }
    }


    protected Set<OWLEntity> getEntitiesThatRequireNamespaces() {
        Set<OWLEntity> result = new HashSet<OWLEntity>();
        result.addAll(ontology.getClassesInSignature());
        result.addAll(ontology.getObjectPropertiesInSignature());
        result.addAll(ontology.getDataPropertiesInSignature());
        result.addAll(ontology.getIndividualsInSignature());
        result.addAll(ontology.getAnnotationPropertiesInSignature());
        return result;
    }


    private void processEntity(OWLNamedObject entity) {
        IRI iri = entity.getIRI();
        processIRI(iri);
    }


    private void processIRI(IRI iri) {
        String s = iri.toString();
        namespaceUtil.split(s, splitResults);
        if (!(splitResults[0].equals("") || splitResults[1].equals(""))) {
            namespaceUtil.getPrefix(splitResults[0]);
        }
    }


    /**
     * Gets a suggested default namespace bases on the ID of an ontology.  If the ontology has an IRI then
     * this IRI will be used to suggest a default namespace, otherwise, the OWL namespace will be returned
     * as the default namespace
     * @param ontology The ontology
     * @return A suggested default namespace
     */
    private static String getDefaultNamespace(OWLOntology ontology, OWLOntologyFormat format) {
        if(format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat prefixOWLOntologyFormat = (PrefixOWLOntologyFormat) format;
            String defaultPrefix = prefixOWLOntologyFormat.getDefaultPrefix();
            if(defaultPrefix != null) {
                return defaultPrefix;
            }
        }
        if(ontology.getOntologyID().isAnonymous()) {
            // What do we return here? Just return the OWL namespace for now.
            return Namespaces.OWL.toString();
        }
        else {
            String base = ontology.getOntologyID().getOntologyIRI().toString();
            if (!base.endsWith("#") && !base.endsWith("/")) {
                base += "#";
            }
            return base;
        }
    }


    /**
     * Gets a QName for a full URI.
     *
     * @param name The name which represents the full name.
     *
     * @return The QName representation or <code>null</code> if a QName could not be generated.
     */
    @Override
	public String getQName(String name) {
        namespaceUtil.split(name, splitResults);
        if (splitResults[0].equals(getDefaultNamespace())) {
            return splitResults[1];
        }
        if (name.startsWith("xmlns") || name.startsWith("xml:")) {
            return name;
        }
        if (splitResults[0].equals("") && splitResults[1].equals("")) {
            // Couldn't split
            return name;
        }
        String prefix = getPrefixForNamespace(splitResults[0]);
        if (prefix != null) {
            if (prefix.length() > 0) {
                return prefix + ":" + splitResults[1];
            }
            else {
                return splitResults[1];
            }
        }
        else {
            return null;
        }
    }
}
