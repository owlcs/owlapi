/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2011, The University of Queensland
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 * 
 * 
 * Alternatively, the contents of this file may be used under the terms of the Apache License,
 * Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable
 * instead of those above.
 * 
 * Copyright 2011, The University of Queensland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.semanticweb.owlapi.rio;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.rdf4j.RDF4JException;
import org.eclipse.rdf4j.common.iteration.CloseableIteration;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.util.Namespaces;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserParameters;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OntologyConfigurator;

/**
 * An implementation of the OWLOntologyDocumentSource interface that does not implement any of the
 * InputStream, Reader, or IRI source retrieval methods. Instead it holds a reference to an iterator
 * that will generate bare triples to be interpreted by a Sesame Rio based OWLParser implementation.
 *
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class RioMemoryTripleSource implements OWLOntologyDocumentSource {

    private final Map<String, String> namespaces = new LinkedHashMap<>();
    private final Iterator<Statement> statementIterator;
    private final IRI documentIRI;
    private OWLParserParameters parameters;

    /**
     * Creates a RioMemoryTripleSource using an {@link Iterable} of {@link Statement} objects.
     *
     * @param statements An {@link Iterator} of {@link Statement} objects that make up this source.
     */
    public RioMemoryTripleSource(Iterator<Statement> statements) {
        documentIRI = IRI.getNextDocumentIRI("rio-memory-triples:");
        statementIterator = checkNotNull(statements, "statements cannot be null");
    }

    /**
     * Creates a RioMemoryTripleSource using an {@link Iterable} of {@link Statement} objects.
     *
     * @param statements An {@link Iterator} of {@link Statement} objects that make up this source.
     * @param namespaces A Map of namespaces from prefix to full URI which are to be used by this
     *        source.
     */
    public RioMemoryTripleSource(Iterator<Statement> statements, Map<String, String> namespaces) {
        this(statements);
        this.namespaces.putAll(namespaces);
    }

    /**
     * Creates a RioMemoryTripleSource using an {@link Iterable} of {@link Statement} objects. <br>
     * If the Iterable is an instance of {@link Model}, the namespaces from the model are also used.
     *
     * @param statements A {@link CloseableIteration} of {@link Statement} objects that make up this
     *        source.
     */
    public RioMemoryTripleSource(Iterable<Statement> statements) {
        documentIRI = IRI.getNextDocumentIRI("rio-memory-triples:");
        statementIterator = statements.iterator();
        if (statements instanceof Model) {
            namespaces.putAll(Namespaces.asMap(((Model) statements).getNamespaces()));
        }
    }

    /**
     * Creates a RioMemoryTripleSource using a closeable iteration. Internally this wraps the
     * statements as an Iterator, and hence all statements must be read for the iterator to be
     * closed automatically.
     *
     * @param statements A {@link CloseableIteration} of {@link Statement} objects that make up this
     *        source.
     */
    public RioMemoryTripleSource(
        final CloseableIteration<Statement, ? extends RDF4JException> statements) {
        documentIRI = IRI.getNextDocumentIRI("rio-memory-triples:");
        statementIterator = new StatementIterator(statements);
    }

    /**
     * Creates a RioMemoryTripleSource using a closeable iteration. Internally this wraps the
     * statements as an Iterator, and hence all statements must be read for the iterator to be
     * closed automatically.
     *
     * @param statements A {@link CloseableIteration} of {@link Statement} objects that make up this
     *        source.
     * @param namespaces A Map of namespaces from prefix to full URI which are to be used by this
     *        source.
     */
    public RioMemoryTripleSource(
        final CloseableIteration<Statement, ? extends RDF4JException> statements,
        final Map<String, String> namespaces) {
        this(statements);
        this.namespaces.putAll(namespaces);
    }

    @Override
    public Optional<OWLOntologyLoaderMetaData> getOntologyLoaderMetaData() {
        return Optional.ofNullable(parameters == null ? null : parameters.getLoaderMetaData());
    }

    @Override
    public OWLDocumentFormat acceptParser(OWLParser parser, OWLOntology o,
        OntologyConfigurator config) {
        parameters = new OWLParserParameters(o, config, documentIRI);
        return ((RioParser) parser).parse(this, parameters);
    }

    @Override
    public IRI getDocumentIRI() {
        return documentIRI;
    }

    /**
     * @return namespace map
     */
    public Map<String, String> getNamespaces() {
        return namespaces;
    }

    /**
     * @param nextNamespaces map of namespaces to set
     */
    public void setNamespaces(Map<String, String> nextNamespaces) {
        namespaces.clear();
        namespaces.putAll(nextNamespaces);
    }

    /**
     * @return statements
     */
    public Iterator<Statement> getStatementIterator() {
        return statementIterator;
    }

    /**
     * @param nextNamespaces set of namespaces to set
     */
    public void setNamespaces(Set<Namespace> nextNamespaces) {
        namespaces.clear();
        namespaces.putAll(Namespaces.asMap(nextNamespaces));
    }

    @Override
    public boolean loadingCanBeAttempted(Collection<String> parsableSchemes) {
        return parsableSchemes.contains(documentIRI.getScheme());
    }

    static final class StatementIterator implements Iterator<Statement> {

        private final CloseableIteration<Statement, ? extends RDF4JException> statements;

        StatementIterator(CloseableIteration<Statement, ? extends RDF4JException> statements) {
            this.statements = statements;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove statements using this iterator");
        }

        @Override
        public Statement next() {
            return statements.next();
        }

        @Override
        public boolean hasNext() {
            return statements.hasNext();
        }
    }
}
