package org.semanticweb.owl.util;

import org.semanticweb.owl.model.OWLOntologyURIMapper;
import org.semanticweb.owl.model.OWLRuntimeException;
import org.semanticweb.owl.vocab.Namespaces;
import org.semanticweb.owl.vocab.OWLXMLVocabulary;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Copyright (C) 2007, University of Manchester
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


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Apr-2007<br><br>
 * <p/>
 * A mapper which given a root folder attempts to automatically discover and
 * map files to ontologies.  The mapper is only capable of mapping ontologies
 * in RDF/XML and OWL/XML (other serialisations are not supported).
 */
public class AutoURIMapper extends DefaultHandler implements OWLOntologyURIMapper {

    private Set<String> fileExtensions;

    private File directory;

    private boolean mapped;

    private boolean recursive;

    private Map<String, OntologyRootElementHandler> handlerMap;

    private File currentFile;

    private Map<URI, URI> uriMap;

    private Map<String, URI> oboFileMap;

    private SAXParserFactory parserFactory;


    /**
     * Creates an auto-mapper which examines ontologies that reside in
     * the specified root folder (and possibly sub-folders).
     * @param rootDirectory The root directory which should be searched
     *                      for ontologies.
     * @param recursive     Sub directories will be searched recursively if
     *                      <code>true</code>.
     */
    public AutoURIMapper(File rootDirectory, boolean recursive) {
        this.directory = rootDirectory;
        this.recursive = recursive;
        uriMap = new HashMap<URI, URI>();
        oboFileMap = new HashMap<String, URI>();
        fileExtensions = new HashSet<String>();
        fileExtensions.add("owl");
        fileExtensions.add("xml");
        fileExtensions.add("rdf");
        mapped = false;
        handlerMap = new HashMap<String, OntologyRootElementHandler>();
        handlerMap.put(Namespaces.RDF + "RDF", new RDFXMLOntologyRootElementHandler());
        handlerMap.put(OWLXMLVocabulary.ONTOLOGY.toString(), new OWLXMLOntologyRootElementHandler());
        parserFactory = SAXParserFactory.newInstance();
        parserFactory.setNamespaceAware(true);
    }


    /**
     * The mapper only examines files that have specified file extensions.
     * This method returns the file extensions that cause a file to be
     * examined.
     * @return A <code>Set</code> of file extensions.
     */
    public Set<String> getFileExtensions() {
        return fileExtensions;
    }


    /**
     * Sets the extensions of files that are to be examined for
     * ontological content. (By default the extensions are, owl,
     * xml and rdf).  Only files that have the specified extensions
     * will be examined to see if they contain ontologies.
     */
    public void setFileExtensions(Set<String> extensions) {
        this.fileExtensions.clear();
        this.fileExtensions.addAll(extensions);
    }


    /**
     * Gets the set of ontology URIs that this mapper has found
     * @return A <code>Set</code> of ontology (logical) URIs
     */
    public Set<URI> getOntologyURIs() {
        if (!mapped) {
            mapFiles();
        }
        return new HashSet<URI>(uriMap.keySet());
    }


    public void update() {
        mapFiles();
    }


    public URI getPhysicalURI(URI ontologyURI) {
        if (!mapped) {
            mapFiles();
        }
        if (ontologyURI.toString().endsWith(".obo")) {
            String path = ontologyURI.getPath();
            if (path != null) {
                int lastSepIndex = path.lastIndexOf('/');
                String name = path.substring(lastSepIndex + 1, path.length());
                URI physicalURI = oboFileMap.get(name);
                if (physicalURI != null) {
                    return physicalURI;
                }
            }
        }
        return uriMap.get(ontologyURI);
    }


    private void mapFiles() {
        mapped = true;
        uriMap.clear();
        processFile(directory);
    }


    private void processFile(File f) {
        if (f.isHidden()) {
            return;
        }
        for (File file : f.listFiles()) {
            if (file.isDirectory() && recursive) {
                processFile(file);
            }
            else {
                boolean parsedFile = false;
                if (file.getName().endsWith(".obo")) {
                    oboFileMap.put(file.getName(), file.toURI());
                }
                else {
                    for (String ext : fileExtensions) {
                        if (file.getName().endsWith(ext)) {
                            parseFile(file);
                            parsedFile = true;
                        }
                    }
                }
            }
        }
    }


    private void parseFile(File file) {
        try {
            currentFile = file;
            SAXParser parser = parserFactory.newSAXParser();
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            parser.parse(is, this);
        }
        catch (ParserConfigurationException e) {
            throw new OWLRuntimeException(e);
        }
        catch (SAXException e) {
            // We simply aren't interested in any parsing problems - if
            // we can't parse a file, then we can't map it and we don't
            // care!
        }
        catch (IOException e) {
            // Again - these kinds of exceptions are of no interest to us!
        }
    }


    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        OntologyRootElementHandler handler = handlerMap.get(uri + localName);
        if (handler != null) {
            URI ontologyURI = handler.handle(attributes);
            if (ontologyURI != null) {
                uriMap.put(ontologyURI, currentFile.toURI());
            }
            throw new SAXException();
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AutoURIMapper: (");
        sb.append(uriMap.size());
        sb.append(" ontologies)\n");
        for (URI uri : uriMap.keySet()) {
            sb.append("    ");
            sb.append(uri);
            sb.append(" -> ");
            sb.append(uriMap.get(uri));
            sb.append("\n");
        }
        return sb.toString();
    }


    /**
     * A simple interface which extracts an ontology URI from a set
     * of element attributes.
     */
    private interface OntologyRootElementHandler {

        /**
         * Gets the ontology URI.
         * @param attributes The attributes which will be examined
         *                   for the ontology URI.
         * @return The ontology URI or <code>null</code> if no ontology
         *         URI could be found.
         */
        URI handle(Attributes attributes);
    }


    /**
     * A handler to handle RDF/XML files.  The xml:base (if present) is taken to be
     * the ontology URI of the ontology document being parsed.
     */
    private class RDFXMLOntologyRootElementHandler implements OntologyRootElementHandler {

        public URI handle(Attributes attributes) {
            String baseValue = attributes.getValue(Namespaces.XML.toString(), "base");
            if (baseValue == null) {
                return null;
            }
            return URI.create(baseValue);
        }
    }


    /**
     * A handler that can handle OWL/XML files.
     */
    private class OWLXMLOntologyRootElementHandler implements OntologyRootElementHandler {

        public URI handle(Attributes attributes) {
            String ontURI = attributes.getValue(Namespaces.OWL2XML.toString(), "URI");
            if (ontURI == null) {
                ontURI = attributes.getValue("URI");
            }
            if (ontURI == null) {
                return null;
            }
            return URI.create(ontURI);
        }
    }
}
