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
package org.semanticweb.owlapi.io;

import java.util.Collection;
import java.util.Optional;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.util.PriorityCollection;

/**
 * A document source provides a point for loading an ontology. A document source may provide three
 * ways of obtaining an ontology document:
 * <ol>
 * <li>From a {@link java.io.Reader}
 * <li>From an {@link java.io.InputStream}
 * <li>From an ontology document {@link org.semanticweb.owlapi.model.IRI}
 * </ol>
 * Consumers that use a document source will attempt to obtain a concrete representation of an
 * ontology in the above order. <br>
 * Note that while an ontology document source may appear similar to a SAX input source, an
 * important difference is that the getReader and getInputStream methods return new instances each
 * time the method is called. This allows multiple attempts at loading an ontology.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLOntologyDocumentSource {

    /**
     * @param parsers parsers to filter
     * @return filtered parsers - parsers that are incompatible with the known format or MIME type
     *         are skipped
     */
    default PriorityCollection<OWLParserFactory> filter(
        PriorityCollection<OWLParserFactory> parsers) {
        return parsers;
    }

    /**
     * @param parser parser to accept
     * @param o ontology to fill
     * @param config configuration for loading
     * @return document format for loaded ontology
     */
    OWLDocumentFormat acceptParser(OWLParser parser, OWLOntology o, OntologyConfigurator config);

    /**
     * @param parsableSchemes schemes that can be parsed
     * @return true if loading with schemes can be attempted
     */
    boolean loadingCanBeAttempted(Collection<String> parsableSchemes);

    /**
     * Gets the IRI of the ontology document.
     *
     * @return An IRI which represents the ontology document IRI
     */
    IRI getDocumentIRI();

    /**
     * @return An object containing metadata about loading
     */
    Optional<OWLOntologyLoaderMetaData> getOntologyLoaderMetaData();
}
