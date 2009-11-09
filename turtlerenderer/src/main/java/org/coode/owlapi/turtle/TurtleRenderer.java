package org.coode.owlapi.turtle;

import org.coode.owlapi.rdf.model.RDFLiteralNode;
import org.coode.owlapi.rdf.model.RDFNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.coode.owlapi.rdf.renderer.RDFRendererBase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
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
 * Date: 26-Jan-2008<br><br>
 */
public class TurtleRenderer extends RDFRendererBase {

    private PrintWriter writer;

    private Set<RDFResourceNode> pending;

    private DefaultPrefixManager pm;

    private String base;


    public TurtleRenderer(OWLOntology ontology, OWLOntologyManager manager, Writer writer) {
        super(ontology, manager);
        this.writer = new PrintWriter(writer);
        pending = new HashSet<RDFResourceNode>();
        pm = new DefaultPrefixManager();
        if(!ontology.isAnonymous()) {
            pm.setDefaultPrefix(ontology.getOntologyID().getOntologyIRI() + "#");
        }
        base = "";
    }


    private void writeNamespaces() {
        for (String prefixName : pm.getPrefixName2PrefixMap().keySet()) {
            String prefix = pm.getPrefix(prefixName);
            write("@prefix ");
            write(prefixName);
            write(" ");
            writeAsURI(prefix);
            write(" .");
            writeNewLine();
        }
    }


    int bufferLength = 0;

    int lastNewLineIndex = 0;

    Stack<Integer> tabs = new Stack<Integer>();


    public void pushTab() {
        tabs.push(getIndent());
    }


    public void popTab() {
        if (!tabs.isEmpty()) {
            tabs.pop();
        }
    }


    private void write(String s) {
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


    private void writeIndent(int indent) {
        for (int i = 1; i < indent; i++) {
            write(" ");
        }
    }


    private void writeAsURI(String s) {
        write("<");
        if (s.startsWith(base)) {
            write(s.substring(base.length()));
        } else {
            write(s);
        }
        write(">");
    }


    private void write(IRI iri) {

        if (iri.equals(getOntology().getOntologyID().getOntologyIRI())) {
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


    private void write(RDFNode node) {
        if (node.isLiteral()) {
            write((RDFLiteralNode) node);
        } else {
            write((RDFResourceNode) node);
        }
    }


    private void write(RDFLiteralNode node) {
        if (node.isTyped()) {
            if (node.getDatatype().equals(XSDVocabulary.INTEGER.getURI())) {
                write(node.getLiteral());
            } else if (node.getDatatype().equals(XSDVocabulary.DECIMAL.getURI())) {
                write(node.getLiteral());
            } else {
                writeStringLiteral(node.getLiteral());
                write("^^");
                write(node.getDatatype());
            }
        } else {
            writeStringLiteral(node.getLiteral());
            if (node.getLang() != null) {
                write("@");
                write(node.getLang());
            }
        }
    }


    private void writeStringLiteral(String literal) {
        String escapedLiteral;
        if(literal.indexOf("\"") != -1) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < literal.length(); i++) {
                char ch = literal.charAt(i);
                if(ch == '\"') {
                    sb.append("\\\"");
                }
                else {
                    sb.append(ch);
                }
            }
            escapedLiteral = sb.toString();
        }
        else {
            escapedLiteral = literal;
        }
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


    private void write(RDFResourceNode node) {
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


    protected void beginDocument() {
        // Namespaces
        writeNamespaces();
        write("@base ");
        write("<");
        if (!getOntology().isAnonymous()) {
            write(getOntology().getOntologyID().getOntologyIRI().toString());
        }
        else {
            write(Namespaces.OWL.toString());
        }
        write("> .");
        writeNewLine();
        writeNewLine();
        // Ontology URI
    }


    protected void endDocument() {
        writer.flush();
        writer.println();
        writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        writer.flush();
    }


    protected void writeClassComment(OWLClass cls) {
        writeComment(cls.getIRI().toString());
    }


    protected void writeObjectPropertyComment(OWLObjectProperty prop) {
        writeComment(prop.getIRI().toString());
    }


    protected void writeDataPropertyComment(OWLDataProperty prop) {
        writeComment(prop.getIRI().toString());
    }


    protected void writeIndividualComments(OWLNamedIndividual ind) {
        writeComment(ind.getIRI().toString());
    }

    protected void writeAnnotationPropertyComment(OWLAnnotationProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    protected void writeDatatypeComment(OWLDatatype datatype) {
        writeComment(datatype.getIRI().toString());
    }

    private void writeComment(String comment) {
        write("###  ");
        write(comment);
        writeNewLine();
        writeNewLine();
    }


    protected void endObject() {
        writeNewLine();
        writeNewLine();
        writeNewLine();
    }


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

    /**
     * Renders the triples whose subject is the specified node
     *
     * @param node The node
     */
    public void render(RDFResourceNode node) {
        level++;
        if (pending.contains(node)) {
            // We essentially remove all structure sharing during parsing - any cycles therefore indicate a bug!
            throw new IllegalStateException("Rendering cycle!  This indicates structure sharing and should not happen!");
        }
        pending.add(node);
        Set<RDFTriple> triples = new TreeSet<RDFTriple>(new TripleComparator());
        triples.addAll(getGraph().getTriplesForSubject(node));

        RDFResourceNode lastSubject = null;
        RDFResourceNode lastPredicate = null;
        boolean first = true;
        for (RDFTriple triple : triples) {
            RDFResourceNode subj = triple.getSubject();
            RDFResourceNode pred = triple.getProperty();
            if (lastSubject != null && (subj.equals(lastSubject) || subj.isAnonymous())) {
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
                    write(triple.getProperty());
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
                write(triple.getProperty());
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
            if(triples.isEmpty()) {
                write("[ ");
            }
            else {
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
