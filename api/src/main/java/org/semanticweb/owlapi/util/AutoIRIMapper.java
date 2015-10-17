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
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.annotations.HasPriority;
import org.semanticweb.owlapi.io.DocumentSources;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.Splitter;

/**
 * A mapper which given a root folder attempts to automatically discover and map
 * files to ontologies. The mapper is only capable of mapping ontologies in
 * RDF/XML and OWL/XML (other serialisations are not supported).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@HasPriority(1)
public class AutoIRIMapper extends DefaultHandler implements OWLOntologyIRIMapper, Serializable {

    private final Set<String> fileExtensions = new HashSet<>();
    private boolean mapped;
    private final boolean recursive;
    private final Map<String, OntologyRootElementHandler> handlerMap = createMap();
    private final Map<IRI, IRI> ontologyIRI2PhysicalURIMap = createMap();
    private final Map<String, IRI> oboFileMap = createMap();
    private final String directoryPath;
    private transient File currentFile;

    /**
     * Creates an auto-mapper which examines ontologies that reside in the
     * specified root folder (and possibly sub-folders).
     * 
     * @param rootDirectory
     *        The root directory which should be searched for ontologies.
     * @param recursive
     *        Sub directories will be searched recursively if {@code true}.
     */
    public AutoIRIMapper(File rootDirectory, boolean recursive) {
        directoryPath = checkNotNull(rootDirectory, "rootDirectory cannot be null").getAbsolutePath();
        this.recursive = recursive;
        fileExtensions.add("owl");
        fileExtensions.add("xml");
        fileExtensions.add("rdf");
        fileExtensions.add("omn");
        fileExtensions.add("ofn");
        mapped = false;
        handlerMap.put(Namespaces.RDF + "RDF", new RDFXMLOntologyRootElementHandler());
        handlerMap.put(OWLXMLVocabulary.ONTOLOGY.toString(), new OWLXMLOntologyRootElementHandler());
    }

    protected File getDirectory() {
        return new File(directoryPath);
    }

    /**
     * The mapper only examines files that have specified file extensions. This
     * method returns the file extensions that cause a file to be examined.
     * 
     * @return A {@code Set} of file extensions.
     */
    public Set<String> getFileExtensions() {
        return new HashSet<>(fileExtensions);
    }

    /**
     * Sets the extensions of files that are to be examined for ontological
     * content. (By default the extensions are, owl, xml and rdf). Only files
     * that have the specified extensions will be examined to see if they
     * contain ontologies.
     * 
     * @param extensions
     *        the set of extensions
     */
    public void setFileExtensions(Collection<String> extensions) {
        fileExtensions.clear();
        fileExtensions.addAll(extensions);
    }

    /**
     * Gets the set of ontology IRIs that this mapper has found.
     * 
     * @return A {@code Set} of ontology (logical) URIs
     */
    public Set<IRI> getOntologyIRIs() {
        if (!mapped) {
            mapFiles();
        }
        return new HashSet<>(ontologyIRI2PhysicalURIMap.keySet());
    }

    /** update the map. */
    public void update() {
        mapFiles();
    }

    @Override
    public @Nullable IRI getDocumentIRI(IRI ontologyIRI) {
        if (!mapped) {
            mapFiles();
        }
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

    private void mapFiles() {
        mapped = true;
        ontologyIRI2PhysicalURIMap.clear();
        processFile(getDirectory());
    }

    private void processFile(File f) {
        if (f.isHidden()) {
            return;
        }
        File[] files = f.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory() && recursive) {
                processFile(file);
            } else if (file.getName().endsWith(".obo")) {
                oboFileMap.put(file.getName(), IRI.create(file));
            } else if (file.getName().endsWith(".ofn")) {
                parseFSSFile(file);
            } else if (file.getName().endsWith(".omn")) {
                parseManchesterSyntaxFile(file);
            } else {
                fileExtensions.stream().filter(ext -> file.getName().endsWith(ext)).forEach(x -> parseFile(file));
            }
        }
    }

    static final Pattern pattern = Pattern.compile("Ontology\\(<([^>]+)>");

    /**
     * Search first 100 lines for FSS style Ontology(&lt;IRI&gt; ...
     * 
     * @param file
     *        the file to parse
     */
    private void parseFSSFile(File file) {
        try (InputStream input = new FileInputStream(file);
            Reader reader = new InputStreamReader(input, "UTF-8");
            BufferedReader br = new BufferedReader(reader)) {
            String line = "";
            IRI ontologyIRI = null;
            Matcher m = pattern.matcher(line);
            int n = 0;
            while ((line = br.readLine()) != null && n++ < 100) {
                m.reset(line);
                if (m.matches()) {
                    String group = m.group(1);
                    assert group != null;
                    ontologyIRI = IRI.create(group);
                    addMapping(ontologyIRI, file);
                    break;
                }
            }
        } catch (@SuppressWarnings("unused") IOException e) {
            // if we can't parse a file, then we can't map it
        }
    }

    private void parseFile(File file) {
        try (FileInputStream in = new FileInputStream(file);
            BufferedInputStream delegate = new BufferedInputStream(in);
            InputStream is = DocumentSources.wrap(delegate);) {
            currentFile = file;
            SAXParsers.initParserWithOWLAPIStandards(null).parse(is, this);
        } catch (@SuppressWarnings("unused") Exception e) {
            // if we can't parse a file, then we can't map it
        }
    }

    private void parseManchesterSyntaxFile(File file) {
        try (FileInputStream input = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader)) {
            // Ontology: <URI>
            String line = br.readLine();
            while (line != null) {
                if (parseManLine(file, line) != null) {
                    return;
                }
                line = br.readLine();
            }
        } catch (@SuppressWarnings("unused") IOException e) {
            // if we can't parse a file, then we can't map it
        }
    }

    private @Nullable IRI parseManLine(File file, String line) {
        for (String tok : Splitter.on(" ").split(line)) {
            if (tok.startsWith("<") && tok.endsWith(">")) {
                IRI iri = unquote(tok);
                addMapping(iri, file);
                return iri;
            }
        }
        return null;
    }

    /**
     * @param tok
     *        token
     * @return IRI without quotes (&lt; and &gt;)
     */
    static IRI unquote(String tok) {
        String substring = tok.substring(1, tok.length() - 1);
        assert substring != null;
        return IRI.create(substring);
    }

    @Override
    public void startElement(@Nullable String uri, @Nullable String localName, @Nullable String qName,
        @Nullable Attributes attributes) throws SAXException {
        OntologyRootElementHandler handler = handlerMap.get(uri + localName);
        if (handler != null) {
            IRI ontologyIRI = handler.handle(checkNotNull(attributes));
            if (ontologyIRI != null && currentFile != null) {
                addMapping(ontologyIRI, verifyNotNull(currentFile));
            }
            throw new SAXException();
        }
    }

    /**
     * @param ontologyIRI
     *        ontology
     * @param file
     *        file
     */
    protected void addMapping(IRI ontologyIRI, File file) {
        ontologyIRI2PhysicalURIMap.put(ontologyIRI, IRI.create(file));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AutoIRIMapper: (");
        sb.append(ontologyIRI2PhysicalURIMap.size()).append(" ontologies)\n");
        ontologyIRI2PhysicalURIMap
            .forEach((k, v) -> sb.append("    ").append(k.toQuotedString()).append(" -> ").append(v).append('\n'));
        return sb.toString();
    }

    /**
     * A simple interface which extracts an ontology IRI from a set of element
     * attributes.
     */
    private interface OntologyRootElementHandler extends Serializable {

        /**
         * Gets the ontology IRI.
         * 
         * @param attributes
         *        The attributes which will be examined for the ontology IRI.
         * @return The ontology IRI or {@code null} if no ontology IRI could be
         *         found.
         */
        @Nullable
        IRI handle(Attributes attributes);
    }

    /**
     * A handler to handle RDF/XML files. The xml:base (if present) is taken to
     * be the ontology URI of the ontology document being parsed.
     */
    private static class RDFXMLOntologyRootElementHandler implements OntologyRootElementHandler, Serializable {

        RDFXMLOntologyRootElementHandler() {}

        @Override
        public @Nullable IRI handle(Attributes attributes) {
            String baseValue = attributes.getValue(Namespaces.XML.toString(), "base");
            if (baseValue == null) {
                return null;
            }
            return IRI.create(baseValue);
        }
    }

    /** A handler that can handle OWL/XML files. */
    private static class OWLXMLOntologyRootElementHandler implements OntologyRootElementHandler, Serializable {

        OWLXMLOntologyRootElementHandler() {}

        @Override
        public @Nullable IRI handle(Attributes attributes) {
            String ontURI = attributes.getValue(Namespaces.OWL.toString(), "ontologyIRI");
            if (ontURI == null) {
                ontURI = attributes.getValue("ontologyIRI");
            }
            if (ontURI == null) {
                return null;
            }
            return IRI.create(ontURI);
        }
    }
}
