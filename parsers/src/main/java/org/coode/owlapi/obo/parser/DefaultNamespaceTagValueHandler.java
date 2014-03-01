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

import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;

/**
 * OBO Namespaces are NOT like XML Namespaces. They are NOT used to form
 * abbreviations for IRIs. The description below, taken from the OBOEdit manual,
 * explains their provenance. <br>
 * <h3>OBO Namespaces and Ontology Name (OBO Syntax and Semantics Document:
 * Section 4.3)</h3> Note that OBO namespaces are not the same as OWL namespaces
 * - the analog of OWL namespaces are OBO ID spaces. OBO namespaces are
 * semantics-free properties of a frame that allow partitioning of an ontology
 * into sub-ontologies. For example, the GO is partitioned into 3 ontologies (3
 * OBO namespaces, 1 OWL namespace). <br>
 * Every frame must have exactly one namespace. However, these do not need to be
 * explicitly assigned. After parsing an OBO Document, any frame without a
 * namespace is assigned the default-namespace, from the OBO Document header. If
 * this is not specified, the Parser assigns a namespace arbitrarily. It is
 * recommended this is equivalent to the URL or file path from which the
 * document was retrieved. <br>
 * Every OBODoc should have an "ontology" tag specified in the header. If this
 * is not specified, then the parser should supply a default value. This value
 * should be derived from the URL of the source of the ontology (typically using
 * http or file schemes). <br>
 * <h3>OBO Namespaces (From the OBOEdit Manual)</h3> OBO files are designed to
 * be easily merged and separated. Most tools that use OBO files can load many
 * OBO files at once. If several ontologies have been loaded together and saved
 * into a single file, it would be impossible to know which terms came from
 * which file unless the origin of each term is indicated somehow. Namespaces
 * are used to solve this problem by indicating a "logical ontology" to which
 * every term, relation, instance OR relationship belongs, i.e., each entity is
 * tagged with a Namespace that indicates which ontology it is part of. <br>
 * Namespaces are user-definable. Every ontology object belongs to a single
 * namespace. When terms from many ontologies have been loaded together,
 * namespaces are used to break the merged ontology back into separate files.
 * 
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group, Date: 01-Sep-2008
 */
public class DefaultNamespaceTagValueHandler extends AbstractTagValueHandler {

    /**
     * @param consumer
     *        consumer
     */
    public DefaultNamespaceTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.DEFAULT_NAMESPACE.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        // Just register the namespace with the consumer and add it as an
        // annotation to the ontology
        getConsumer().setDefaultNamespaceTagValue(value);
        // Add an annotation to the ontology
        OWLAnnotation annotation = getAnnotationForTagValuePair(
                OBOVocabulary.DEFAULT_NAMESPACE.getName(), value);
        applyChange(new AddOntologyAnnotation(getOntology(), annotation));
    }
}
