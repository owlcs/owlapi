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
package org.semanticweb.owlapi.rdf.turtle.renderer;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.formats.RDFOntologyFormat;
import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.rdf.RDFRendererBase;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.EscapeUtils;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class TurtleRenderer extends RDFRendererBase {

    private PrintWriter writer;
    private PrefixManager pm;
    private Set<RDFResource> pending;
    private String base;
    private OWLOntologyFormat format;

    /**
     * @param ontology
     *        ontology
     * @param writer
     *        writer
     * @param format
     *        format
     */
    public TurtleRenderer(@Nonnull OWLOntology ontology, Writer writer,
            OWLOntologyFormat format) {
        super(ontology, format);
        this.format = checkNotNull(format, "format cannot be null");
        this.writer = new PrintWriter(writer);
        pending = new HashSet<RDFResource>();
        pm = new DefaultPrefixManager();
        if (!ontology.isAnonymous()) {
            String ontologyIRIString = ontology.getOntologyID()
                    .getOntologyIRI().get().toString();
            String defaultPrefix = ontologyIRIString;
            if (!ontologyIRIString.endsWith("/")) {
                defaultPrefix = ontologyIRIString + "#";
            }
            pm.setDefaultPrefix(defaultPrefix);
        }
        if (format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat prefixFormat = (PrefixOWLOntologyFormat) format;
            pm.copyPrefixesFrom(prefixFormat);
            pm.setPrefixComparator(prefixFormat.getPrefixComparator());
        }
        base = "";
    }

    @SuppressWarnings("null")
    private void writeNamespaces() {
        for (Map.Entry<String, String> e : pm.getPrefixName2PrefixMap()
                .entrySet()) {
            write("@prefix ");
            write(e.getKey());
            write(" ");
            writeAsURI(e.getValue());
            write(" .");
            writeNewLine();
        }
    }

    int bufferLength = 0;
    int lastNewLineIndex = 0;
    @Nonnull
    Stack<Integer> tabs = new Stack<Integer>();

    protected void pushTab() {
        tabs.push(getIndent());
    }

    protected void popTab() {
        if (!tabs.isEmpty()) {
            tabs.pop();
        }
    }

    private void write(@Nonnull String s) {
        int newLineIndex = s.indexOf('\n');
        if (newLineIndex != -1) {
            lastNewLineIndex = bufferLength + newLineIndex;
        }
        writer.write(s);
        bufferLength += s.length();
    }

    private int getCurrentPos() {
        return bufferLength;
    }

    private int getIndent() {
        return getCurrentPos() - lastNewLineIndex;
    }

    @SuppressWarnings("null")
    private void writeAsURI(@Nonnull String s) {
        write("<");
        if (s.startsWith(base)) {
            write(s.substring(base.length()));
        } else {
            write(s);
        }
        write(">");
    }

    private void write(@Nonnull IRI iri) {
        if (iri.equals(ontology.getOntologyID().getOntologyIRI().orNull())) {
            writeAsURI(iri.toString());
        } else {
            String name = pm.getPrefixIRI(iri);
            if (name == null) {
                // No QName!
                writeAsURI(iri.toString());
            } else {
                if (name.indexOf(':') != -1) {
                    write(name);
                } else {
                    write(":");
                    write(name);
                }
            }
        }
    }

    private void writeNewLine() {
        write("\n");
        int tabIndent = 0;
        if (!tabs.isEmpty()) {
            tabIndent = tabs.peek();
        }
        for (int i = 1; i < tabIndent; i++) {
            write(" ");
        }
    }

    private void write(@Nonnull RDFNode node) {
        if (node.isLiteral()) {
            write((RDFLiteral) node);
        } else {
            write((RDFResource) node);
        }
    }

    private void write(@Nonnull RDFLiteral node) {
        if (!node.isPlainLiteral()) {
            if (node.getDatatype().equals(XSDVocabulary.INTEGER.getIRI())) {
                write(node.getLexicalValue());
            } else if (node.getDatatype()
                    .equals(XSDVocabulary.DECIMAL.getIRI())) {
                write(node.getLexicalValue());
            } else {
                writeStringLiteral(node.getLexicalValue());
                write("^^");
                write(node.getDatatype());
            }
        } else {
            writeStringLiteral(node.getLexicalValue());
            if (node.hasLang()) {
                write("@");
                write(node.getLang());
            }
        }
    }

    private void writeStringLiteral(@Nonnull String literal) {
        String escapedLiteral = EscapeUtils.escapeString(literal);
        if (escapedLiteral.indexOf('\n') != -1) {
            write("\"\"\"");
            write(escapedLiteral);
            write("\"\"\"");
        } else {
            write("\"");
            write(escapedLiteral);
            write("\"");
        }
    }

    @SuppressWarnings("null")
    private void write(@Nonnull RDFResource node) {
        if (!node.isAnonymous()) {
            write(node.getIRI());
        } else {
            pushTab();
            if (!isObjectList(node)) {
                render(node);
            } else {
                // List - special syntax
                List<RDFNode> list = new ArrayList<RDFNode>();
                toJavaList(node, list);
                pushTab();
                write("(");
                write(" ");
                pushTab();
                for (Iterator<RDFNode> it = list.iterator(); it.hasNext();) {
                    write(it.next());
                    if (it.hasNext()) {
                        writeNewLine();
                    }
                }
                popTab();
                writeNewLine();
                write(")");
                popTab();
            }
            popTab();
        }
    }

    @Override
    protected void beginDocument() {
        // Namespaces
        writeNamespaces();
        write("@base ");
        write("<");
        if (!ontology.isAnonymous()) {
            write(ontology.getOntologyID().getOntologyIRI().get().toString());
        } else {
            write(Namespaces.OWL.toString());
        }
        write("> .");
        writeNewLine();
        writeNewLine();
        // Ontology URI
    }

    @Override
    protected void endDocument() {
        writer.flush();
        writer.println();
        writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        if (format instanceof RDFOntologyFormat
                && !((RDFOntologyFormat) format).isAddMissingTypes()) {
            // missing type declarations could have been omitted, adding a
            // comment to document it
            writeComment("Warning: type declarations were not added automatically.");
        }
        writer.flush();
    }

    @Override
    protected void writeClassComment(@Nonnull OWLClass cls) {
        writeComment(cls.getIRI().toString());
    }

    @Override
    protected void writeObjectPropertyComment(@Nonnull OWLObjectProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeDataPropertyComment(@Nonnull OWLDataProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeIndividualComments(@Nonnull OWLNamedIndividual ind) {
        writeComment(ind.getIRI().toString());
    }

    @Override
    protected void writeAnnotationPropertyComment(
            @Nonnull OWLAnnotationProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeDatatypeComment(@Nonnull OWLDatatype datatype) {
        writeComment(datatype.getIRI().toString());
    }

    private void writeComment(@Nonnull String comment) {
        write("###  ");
        write(comment);
        writeNewLine();
        writeNewLine();
    }

    @Override
    protected void endObject() {
        writeNewLine();
        writeNewLine();
        writeNewLine();
    }

    @Override
    protected void writeBanner(String name) {
        writeNewLine();
        writeNewLine();
        writer.println("#################################################################");
        writer.println("#");
        writer.print("#    ");
        writer.println(name);
        writer.println("#");
        writer.println("#################################################################");
        writeNewLine();
        writeNewLine();
    }

    int level = 0;

    @Override
    public void render(@Nonnull RDFResource node) {
        level++;
        Collection<RDFTriple> triples;
        if (pending.contains(node)) {
            // We essentially remove all structure sharing during parsing - any
            // cycles therefore indicate a bug!
            triples = Collections.emptyList();
        } else {
            triples = graph.getTriplesForSubject(node, true);
        }
        pending.add(node);
        RDFResource lastSubject = null;
        RDFResourceIRI lastPredicate = null;
        boolean first = true;
        for (RDFTriple triple : triples) {
            RDFResource subj = triple.getSubject();
            RDFResourceIRI pred = triple.getPredicate();
            if (lastSubject != null
                    && (subj.equals(lastSubject) || subj.isAnonymous())) {
                if (lastPredicate != null && pred.equals(lastPredicate)) {
                    // Only the object differs from previous triple
                    // Just write the object
                    write(" ,");
                    writeNewLine();
                    write(triple.getObject());
                } else {
                    // The predicate, object differ from previous triple
                    // Just write the predicate and object
                    write(" ;");
                    popTab();
                    if (!subj.isAnonymous()) {
                        writeNewLine();
                    }
                    writeNewLine();
                    write(triple.getPredicate());
                    write(" ");
                    pushTab();
                    write(triple.getObject());
                }
            } else {
                if (!first) {
                    popTab();
                    popTab();
                    writeNewLine();
                }
                // Subject, preficate and object are different from last triple
                if (!node.isAnonymous()) {
                    write(triple.getSubject());
                    write(" ");
                } else {
                    pushTab();
                    write("[");
                    write(" ");
                }
                pushTab();
                write(triple.getPredicate());
                write(" ");
                pushTab();
                write(triple.getObject());
            }
            lastSubject = subj;
            lastPredicate = pred;
            first = false;
        }
        if (node.isAnonymous()) {
            popTab();
            popTab();
            if (triples.isEmpty()) {
                write("[ ");
            } else {
                writeNewLine();
            }
            write("]");
            popTab();
        } else {
            popTab();
            popTab();
        }
        if (level == 1 && !triples.isEmpty()) {
            write(" .\n");
        }
        writer.flush();
        pending.remove(node);
        level--;
    }
}
