package org.coode.xml;

/**
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.NamespaceUtil;
import org.semanticweb.owl.vocab.PrefixOWLOntologyFormat;
import org.semanticweb.owl.vocab.Namespaces;

import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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

    private OWLOntologyManager man;

    private OWLOntology ontology;

    private NamespaceUtil namespaceUtil;

    private static String[] splitResults = new String[2];

    private OWLOntologyFormat ontologyFormat;


    public OWLOntologyXMLNamespaceManager(OWLOntologyManager man, OWLOntology ontology) {
        this(man, ontology, man.getOntologyFormat(ontology));
    }


    public OWLOntologyXMLNamespaceManager(OWLOntologyManager man, OWLOntology ontology, OWLOntologyFormat format) {
        super(getDefaultNamespace(ontology));
        this.man = man;
        this.ontology = ontology;
        namespaceUtil = new NamespaceUtil();
        ontologyFormat = format;
        processOntology();
    }


    protected OWLOntology getOntology() {
        return ontology;
    }


    private void processOntology() {
        namespaceUtil = new NamespaceUtil();
        if (ontologyFormat instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat namespaceFormat = (PrefixOWLOntologyFormat) ontologyFormat;
            Map<String, String> namespacesByPrefix = namespaceFormat.getNamespacesByPrefixMap();
            for (String prefixName : namespacesByPrefix.keySet()) {
                    String xmlnsPrefixName = prefixName.substring(0, prefixName.length() - 1);
                    String xmlnsPrefix = namespacesByPrefix.get(prefixName);
                    namespaceUtil.setPrefix(xmlnsPrefixName, xmlnsPrefix);
            }
        }
        if (!ontology.getRules().isEmpty()) {
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
        result.addAll(ontology.getReferencedClasses());
        result.addAll(ontology.getReferencedObjectProperties());
        result.addAll(ontology.getReferencedDataProperties());
        result.addAll(ontology.getReferencedIndividuals());
        result.addAll(ontology.getReferencedAnnotationProperties());
        return result;
    }


    private void processEntity(OWLNamedObject entity) {
        URI uri = entity.getURI();
        processURI(uri);
    }


    private void processURI(URI uri) {
        String s = uri.toString();
        namespaceUtil.split(s, splitResults);
        if (!(splitResults[0].equals("") || splitResults[1].equals(""))) {
            namespaceUtil.getPrefix(splitResults[0]);
        }
    }


    private static String getDefaultNamespace(OWLOntology ontology) {
        String base = ontology.getURI().toString();
        if (!base.endsWith("#") && !base.endsWith("/")) {
            base += "#";
        }
        return base;
    }


    /**
     * Gets a QName for a full URI.
     *
     * @param name The name which represents the full name.
     *
     * @return The QName representation or <code>null</code> if a QName could not be generated.
     */
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
            return prefix + ":" + splitResults[1];
        }
        else {
            return null;
        }
    }
}
