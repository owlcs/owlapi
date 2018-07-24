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

import static org.semanticweb.owlapi.util.CollectionFactory.createMap;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.annotations.HasPriority;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.Splitter;

/**
 * A mapper which given a root folder attempts to automatically discover and map files to
 * ontologies. The mapper is only capable of mapping ontologies in RDF/XML and OWL/XML (other
 * serialisations are not supported).
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
@HasPriority(1)
public class ZipIRIMapper extends DefaultHandler implements OWLOntologyIRIMapper, Serializable {

    static final Pattern pattern = Pattern.compile("Ontology\\(<([^>]+)>");
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipIRIMapper.class);
    private final Set<String> fileExtensions =
        new HashSet<>(Arrays.asList(".owl", ".xml", ".rdf", ".omn", ".ofn", ".obo"));
    private final Map<String, OntologyRootElementHandler> handlerMap = createMap();
    private final Map<IRI, IRI> ontologyIRI2PhysicalURIMap = createMap();
    private final Map<String, IRI> oboFileMap = createMap();
    @Nullable
    private IRI currentFile;

    /**
     * Creates an auto-mapper which examines ontologies that reside in the specified zip file.
     *
     * @param zip The zip file to map.
     * @param baseIRI base iri for physical IRIs
     * @throws IOException if an exception reading from input is raised
     */
    public ZipIRIMapper(File zip, String baseIRI) throws IOException {
        this(new ZipFile(zip), baseIRI);
    }

    /**
     * Creates an auto-mapper which examines ontologies that reside in the specified zip file.
     *
     * @param zip The zip file to map.
     * @param baseIRI base iri for physical IRIs
     * @throws IOException if an exception reading from input is raised
     */
    public ZipIRIMapper(ZipFile zip, String baseIRI) throws IOException {
        /**
         * A handler to handle RDF/XML files. The xml:base (if present) is taken to be the ontology
         * URI of the ontology document being parsed.
         */
        handlerMap.put(Namespaces.RDF + "RDF", this::baseIRI);
        /** A handler that can handle OWL/XML files. */
        handlerMap.put(OWLXMLVocabulary.ONTOLOGY.toString(), this::ontologyIRI);
        processFile(zip, baseIRI);

    }

    @Nullable
    protected IRI ontologyIRI(Attributes attributes) {
        String ontURI = attributes.getValue(Namespaces.OWL.toString(), "ontologyIRI");
        if (ontURI == null) {
            ontURI = attributes.getValue("ontologyIRI");
        }
        if (ontURI == null) {
            return null;
        }
        return IRI.create(ontURI);
    }

    @Nullable
    protected IRI baseIRI(Attributes attributes) {
        String baseValue = attributes.getValue(Namespaces.XML.toString(), "base");
        if (baseValue == null) {
            return null;
        }
        return IRI.create(baseValue);
    }

    /**
     * @param tok token
     * @return IRI without quotes (&lt; and &gt;)
     */
    static IRI unquote(String tok) {
        String substring = tok.substring(1, tok.length() - 1);
        assert substring != null;
        return IRI.create(substring);
    }

    /**
     * The mapper only examines files that have specified file extensions. This method returns the
     * file extensions that cause a file to be examined.
     *
     * @return A {@code Set} of file extensions.
     */
    public Set<String> getFileExtensions() {
        return new HashSet<>(fileExtensions);
    }

    /**
     * Sets the extensions of files that are to be examined for ontological content. (By default the
     * extensions are, owl, xml and rdf). Only files that have the specified extensions will be
     * examined to see if they contain ontologies.
     *
     * @param extensions the set of extensions
     */
    public void setFileExtensions(Collection<String> extensions) {
        fileExtensions.clear();
        fileExtensions.addAll(extensions);
    }

    /**
     * Gets the mappings of ontology IRIs that this mapper has found.
     *
     * @return A {@code Stream} of map entries, from logical to physical IRI
     */
    public Stream<Map.Entry<IRI, IRI>> mappedEntries() {
        return ontologyIRI2PhysicalURIMap.entrySet().stream();
    }

    @Override
    @Nullable
    public IRI getDocumentIRI(IRI ontologyIRI) {
        if (ontologyIRI.toString().endsWith(".obo")) {
            String path = ontologyIRI.toURI().getPath();
            if (path != null) {
                int lastSepIndex = path.lastIndexOf('/');
                String name = path.substring(lastSepIndex + 1, path.length());
                IRI documentIRI = oboFileMap.get(name);
                if (documentIRI != null) {
                    return documentIRI;
                }
            }
        }
        return ontologyIRI2PhysicalURIMap.get(ontologyIRI);
    }

    /**
     * @return obo mappings
     */
    public Stream<Map.Entry<String, IRI>> oboMappings() {
        return oboFileMap.entrySet().stream();
    }

    /**
     * @return iri mappings
     */
    public Stream<Map.Entry<IRI, IRI>> iriMappings() {
        return ontologyIRI2PhysicalURIMap.entrySet().stream();
    }

    private void processFile(ZipFile archive, String baseIRI) throws IOException {
        Enumeration<? extends ZipEntry> entries = archive.entries();
        while (entries.hasMoreElements()) {
            parseIfExtensionSupported(archive, entries.nextElement(), baseIRI);
        }
    }

    protected void parseIfExtensionSupported(ZipFile file, ZipEntry e, String baseIRI)
        throws IOException {
        String name = e.getName();
        int lastIndexOf = name.lastIndexOf('.');
        if (lastIndexOf < 0) {
            // no extension for the file, nothing to do
            return;
        }
        IRI physicalIRI = IRI.create(baseIRI + name);
        String extension = name.substring(lastIndexOf);
        if (".obo".equals(extension)) {
            oboFileMap.put(name, physicalIRI);
        } else {
            try (InputStream in = file.getInputStream(e)) {
                if (".ofn".equals(extension)) {
                    IRI logical = parseFSSFile(in);
                    if (logical != null) {
                        ontologyIRI2PhysicalURIMap.put(logical, physicalIRI);
                    }
                } else if (".omn".equals(extension)) {
                    IRI logical = parseManchesterSyntaxFile(in);
                    if (logical != null) {
                        ontologyIRI2PhysicalURIMap.put(logical, physicalIRI);
                    }
                } else if (fileExtensions.contains(extension)) {
                    IRI logical = parseFile(in);
                    if (logical != null) {
                        ontologyIRI2PhysicalURIMap.put(logical, physicalIRI);
                    }
                }
            }
        }
    }

    /**
     * Search first 100 lines for FSS style Ontology(&lt;IRI&gt; ...
     *
     * @param input the file to parse
     * @return iri
     */
    @Nullable
    private static IRI parseFSSFile(InputStream input) {
        try (Reader reader = new InputStreamReader(input, "UTF-8");
            BufferedReader br = new BufferedReader(reader)) {
            String line = "";
            Matcher m = pattern.matcher(line);
            int n = 0;
            while ((line = br.readLine()) != null && n++ < 100) {
                m.reset(line);
                if (m.matches()) {
                    String group = m.group(1);
                    assert group != null;
                    return IRI.create(group);
                }
            }
        } catch (IOException e) {
            // if we can't parse a file, then we can't map it
            LOGGER.debug("Exception reading file", e);
        }
        return null;
    }

    @Nullable
    private IRI parseFile(InputStream file) {
        try {
            currentFile = null;
            // Using the default expansion limit. If the ontology IRI cannot be
            // found before 64000 entities are expanded, the file is too
            // expensive to parse.
            SAXParsers.initParserWithOWLAPIStandards(null, "64000").parse(file, this);
        } catch (SAXException | IOException e) {
            // if we can't parse a file, then we can't map it
            LOGGER.debug("Exception reading file", e);
        }
        return currentFile;
    }

    @Nullable
    private static IRI parseManchesterSyntaxFile(InputStream input) {
        try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader)) {
            // Ontology: <URI>
            String line = br.readLine();
            while (line != null) {
                IRI iri = parseManLine(line);
                if (iri != null) {
                    return iri;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            // if we can't parse a file, then we can't map it
            LOGGER.debug("Exception reading file", e);
        }
        return null;
    }

    @Nullable
    private static IRI parseManLine(String line) {
        for (String tok : Splitter.on(" ").split(line)) {
            if (tok.startsWith("<") && tok.endsWith(">")) {
                return unquote(tok);
            }
        }
        return null;
    }

    @Override
    public void startElement(@Nullable String uri, @Nullable String localName,
        @Nullable String qName, @Nullable Attributes attributes) throws SAXException {
        OntologyRootElementHandler handler = handlerMap.get(uri + localName);
        if (handler != null) {
            IRI ontologyIRI = handler.handle(checkNotNull(attributes));
            if (ontologyIRI != null && currentFile == null) {
                currentFile = ontologyIRI;
            }
            throw new SAXException();
        }
    }

    /**
     * @param ontologyIRI ontology
     * @param file file
     */
    protected void addMapping(IRI ontologyIRI, File file) {
        ontologyIRI2PhysicalURIMap.put(ontologyIRI, IRI.create(file));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AutoIRIMapper: (");
        sb.append(ontologyIRI2PhysicalURIMap.size()).append(" ontologies)\n");
        ontologyIRI2PhysicalURIMap.forEach((k, v) -> sb.append("    ").append(k.toQuotedString())
            .append(" -> ").append(v).append('\n'));
        return sb.toString();
    }

    /**
     * A simple interface which extracts an ontology IRI from a set of element attributes.
     */
    @FunctionalInterface
    private interface OntologyRootElementHandler extends Serializable {

        /**
         * Gets the ontology IRI.
         *
         * @param attributes The attributes which will be examined for the ontology IRI.
         * @return The ontology IRI or {@code null} if no ontology IRI could be found.
         */
        @Nullable
        IRI handle(Attributes attributes);
    }
}
