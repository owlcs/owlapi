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
package org.semanticweb.owlapi.owlxml.renderer;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.PrintWriter;
import java.util.Map;

import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.OWLStorer;
import org.semanticweb.owlapi.io.OWLStorerParameters;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.vocab.Namespaces;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class OWLXMLStorer implements OWLStorer {

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontologyFormat instanceof OWLXMLDocumentFormat;
    }

    @Override
    public void storeOntology(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format,
        OWLStorerParameters storerParameters) throws OWLOntologyStorageException {
        render(ontology, writer, format, storerParameters);
    }

    /**
     * @param ontology ontology
     * @param writer writer
     * @param format format
     * @param storerParameters storer parameters
     * @throws OWLOntologyStorageException renderer error
     */
    public static void render(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format,
        OWLStorerParameters storerParameters) throws OWLOntologyStorageException {
        checkNotNull(ontology, "ontology cannot be null");
        checkNotNull(writer, "writer cannot be null");
        checkNotNull(format, "format cannot be null");
        try {
            OWLXMLWriter w = new OWLXMLWriter(writer, ontology, storerParameters.getEncoding());
            w.startDocument(ontology);
            writePrefixes(ontology, w);
            OWLXMLObjectRenderer ren = new OWLXMLObjectRenderer(w);
            ontology.accept(ren);
            w.endDocument();
            writer.flush();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    protected static void writePrefixes(OWLOntology ontology, OWLXMLWriter w) {
        PrefixManager fromPrefixFormat = ontology.getPrefixManager();
        Map<String, String> map = fromPrefixFormat.getPrefixName2PrefixMap();
        for (Map.Entry<String, String> e : map.entrySet()) {
            if (e.getValue() != null && !e.getValue().isEmpty()) {
                w.writePrefix(e.getKey(), e.getValue());
            }
        }
        writeDefaultPrefix(w, map, "rdf:", Namespaces.RDF);
        writeDefaultPrefix(w, map, "rdfs:", Namespaces.RDFS);
        writeDefaultPrefix(w, map, "xsd:", Namespaces.XSD);
        writeDefaultPrefix(w, map, "owl:", Namespaces.OWL);
    }

    protected static void writeDefaultPrefix(OWLXMLWriter w, Map<String, String> map, String prefix,
        Namespaces defaultValue) {
        if (!map.containsKey(prefix)) {
            w.writePrefix(prefix, defaultValue.toString());
        }
    }
}
