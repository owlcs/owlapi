package org.coode.owlapi.turtle;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.coode.owlapi.rdf.model.RDFLiteralNode;
import org.coode.owlapi.rdf.model.RDFNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.coode.owlapi.rdf.renderer.RDFRendererBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.vocab.XSDVocabulary;


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


    public TurtleRenderer(OWLOntology ontology, OWLOntologyManager manager, Writer writer, OWLOntologyFormat format) {
        super(ontology, manager, format);
        this.writer = new PrintWriter(writer);
        pending = new HashSet<RDFResourceNode>();
        pm = new DefaultPrefixManager();
        if (!ontology.isAnonymous()) {
            pm.setDefaultPrefix(ontology.getOntologyID().getOntologyIRI() + "#");
        }
        if(format instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat prefixFormat = (PrefixOWLOntologyFormat) format;
            for(String prefixName : prefixFormat.getPrefixNames()) {
                pm.setPrefix(prefixName, prefixFormat.getPrefix(prefixName));
            }
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


//    private void writeIndent(int indent) {
//        for (int i = 1; i < indent; i++) {
//            write(" ");
//        }
//    }


    private void writeAsURI(String s) {
        write("<");
        if (s.startsWith(base)) {
            write(s.substring(base.length()));
        }
        else {
            write(s);
        }
        write(">");
    }


    private void write(IRI iri) {

        if (iri.equals(getOntology().getOntologyID().getOntologyIRI())) {
            writeAsURI(iri.toString());
        }
        else {
            String name = pm.getPrefixIRI(iri);
            if (name == null) {
                // No QName!
                writeAsURI(iri.toString());
            }
            else {
                if (name.indexOf(':') != -1) {
                    write(name);
                }
                else {
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
        }
        else {
            write((RDFResourceNode) node);
        }
    }


    private void write(RDFLiteralNode node) {
        if (node.getDatatype() != null) {
            if (node.getDatatype().equals(manager.getOWLDataFactory().getIntegerOWLDatatype().getIRI())) {
                write(node.getLiteral());
            }
            else if (node.getDatatype().equals(XSDVocabulary.DECIMAL.getIRI())) {
                write(node.getLiteral());
            }
            else {
                writeStringLiteral(node.getLiteral());
                write("^^");
                write(node.getDatatype());
            }
        }
        else {
            writeStringLiteral(node.getLiteral());
            if (node.getLang() != null) {
                write("@");
                write(node.getLang());
            }
        }
    }


    private void writeStringLiteral(String literal) {
        String escapedLiteral;
        if (literal.indexOf("\"") != -1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < literal.length(); i++) {
                char ch = literal.charAt(i);
                if (ch == '\"') {
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
        }
        else {
            write("\"");
            write(escapedLiteral);
            write("\"");
        }
    }


    private void write(RDFResourceNode node) {
        if (!node.isAnonymous()) {
            write(node.getIRI());
        }
        else {
            pushTab();
            if (!isObjectList(node)) {
                render(node);
            }
            else {
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


    @Override
	protected void endDocument() {
        writer.flush();
        writer.println();
        writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        writer.flush();
    }


    @Override
	protected void writeClassComment(OWLClass cls) {
        writeComment(cls.getIRI().toString());
    }


    @Override
	protected void writeObjectPropertyComment(OWLObjectProperty prop) {
        writeComment(prop.getIRI().toString());
    }


    @Override
	protected void writeDataPropertyComment(OWLDataProperty prop) {
        writeComment(prop.getIRI().toString());
    }


    @Override
	protected void writeIndividualComments(OWLNamedIndividual ind) {
        writeComment(ind.getIRI().toString());
    }

    @Override
	protected void writeAnnotationPropertyComment(OWLAnnotationProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    @Override
	protected void writeDatatypeComment(OWLDatatype datatype) {
        writeComment(datatype.getIRI().toString());
    }

    private void writeComment(String comment) {
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

    /**
     * Renders the triples whose subject is the specified node
     * @param node The node
     */
    @Override
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
                }
                else {
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
            }
            else {
                if (!first) {
                    popTab();
                    popTab();
                    writeNewLine();
                }
                // Subject, preficate and object are different from last triple
                if (!node.isAnonymous()) {
                    write(triple.getSubject());
                    write(" ");
                }
                else {
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
            if (triples.isEmpty()) {
                write("[ ");
            }
            else {
                writeNewLine();
            }
            write("]");
            popTab();
        }
        else {
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
