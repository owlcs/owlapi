package org.coode.owl.rdf.rdfxml;

import org.coode.owl.rdf.model.RDFLiteralNode;
import org.coode.owl.rdf.model.RDFNode;
import org.coode.owl.rdf.model.RDFResourceNode;
import org.coode.owl.rdf.model.RDFTriple;
import org.coode.owl.rdf.renderer.RDFRendererBase;
import org.coode.string.EscapeUtils;
import org.coode.xml.OWLOntologyXMLNamespaceManager;
import org.coode.xml.XMLWriterFactory;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_DESCRIPTION;

import java.io.Writer;
import java.io.IOException;
import java.net.URI;
import java.util.*;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 06-Dec-2006<br><br>
 */
public class RDFXMLRenderer extends RDFRendererBase {

    private RDFXMLWriter writer;

    private Set<RDFResourceNode> pending;

    private Set<RDFResourceNode> renderedAnonymousNodes;

    public RDFXMLRenderer(OWLOntologyManager manager, OWLOntology ontology, Writer w) {
        this(manager, ontology, w, manager.getOntologyFormat(ontology));
    }


    public RDFXMLRenderer(OWLOntologyManager manager, OWLOntology ontology, Writer w, OWLOntologyFormat format) {
        super(ontology, manager, format);
        pending = new HashSet<RDFResourceNode>();
        renderedAnonymousNodes = new HashSet<RDFResourceNode>();

        OWLOntologyXMLNamespaceManager nsm = new OWLOntologyXMLNamespaceManager(manager,
                ontology,
                format);
        String defaultNamespace = nsm.getDefaultNamespace();
        String base;
        if(defaultNamespace.endsWith("#")) {
            base = defaultNamespace.substring(0, defaultNamespace.length() - 1);
        }
        else {
            base = defaultNamespace;
        }
        writer = new RDFXMLWriter(XMLWriterFactory.getInstance().createXMLWriter(w,
                nsm,
                base));
        prettyPrintedTypes = new HashSet<URI>();
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_CLASS.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_DATA_PROPERTY.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_RESTRICTION.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_THING.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_NOTHING.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_ONTOLOGY.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.RDFS_DATATYPE.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_AXIOM.getURI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_ANNOTATION.getURI());
    }


    protected void beginDocument() throws IOException  {
        writer.startDocument();
    }


    protected void endDocument() throws IOException  {
        writer.endDocument();
        writer.writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
    }


    protected void writeIndividualComments(OWLNamedIndividual ind) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(ind.getURI().toString()));
    }


    protected void writeClassComment(OWLClass cls) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(cls.getURI().toString()));
    }


    protected void writeDataPropertyComment(OWLDataProperty prop) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(prop.getURI().toString()));
    }


    protected void writeObjectPropertyComment(OWLObjectProperty prop) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(prop.getURI().toString()));
    }

    protected void writeAnnotationPropertyComment(OWLAnnotationProperty prop) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(prop.getURI().toString()));
    }

    protected void writeDatatypeComment(OWLDatatype datatype) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(datatype.getURI().toString()));
    }

    protected void writeBanner(String name) throws IOException  {
        writer.writeComment(
                "\n///////////////////////////////////////////////////////////////////////////////////////\n" + "//\n" + "// " + name + "\n" + "//\n" + "///////////////////////////////////////////////////////////////////////////////////////\n");
    }


    public void render(RDFResourceNode node) throws IOException {
        if (pending.contains(node)) {
            // We essentially remove all structure sharing during parsing - any cycles therefore indicate a bug!
//            throw new IllegalStateException("Rendering cycle!  This indicates structure sharing and should not happen! (Node: " + node.toString() + ")");
            return;
        }
        pending.add(node);
        Set<RDFTriple> triples = new TreeSet<RDFTriple>(new TripleComparator());
        triples.addAll(getGraph().getTriplesForSubject(node));
//        if (node.isAnonymous() && !renderedAnonymousNodes.contains(node)) {
//
//            renderedAnonymousNodes.add(node);
//        }
        RDFTriple candidatePrettyPrintTypeTriple = null;
        for (RDFTriple triple : triples) {
            URI propertyURI = triple.getProperty().getURI();
            if (propertyURI.equals(OWLRDFVocabulary.RDF_TYPE.getURI()) && !triple.getObject().isAnonymous()) {
                if (OWLRDFVocabulary.BUILT_IN_VOCABULARY.contains(triple.getObject().getURI())) {
                    if (prettyPrintedTypes.contains(triple.getObject().getURI())) {
                        candidatePrettyPrintTypeTriple = triple;
                    }
                } else {
                    candidatePrettyPrintTypeTriple = triple;
                }
            }
        }
        if (candidatePrettyPrintTypeTriple == null) {
            writer.writeStartElement(RDF_DESCRIPTION.getURI());
        } else {
            writer.writeStartElement(candidatePrettyPrintTypeTriple.getObject().getURI());
        }
        if (!node.isAnonymous()) {
            writer.writeAboutAttribute(node.getURI());
        }
//        else {
//            if (getGraph().isAnonymousNodeSharedSubject(node)) {
//                writer.writeNodeIDAttribute(node);
//            }
//        }
        for (RDFTriple triple : triples) {
            if (candidatePrettyPrintTypeTriple != null && candidatePrettyPrintTypeTriple.equals(triple)) {
                continue;
            }
            writer.writeStartElement(triple.getProperty().getURI());
            RDFNode objectNode = triple.getObject();
            if (!objectNode.isLiteral()) {
                RDFResourceNode objectRes = (RDFResourceNode) objectNode;
                if (objectRes.isAnonymous()) {
                    // Special rendering for lists
                    if (isObjectList(objectRes)) {
                        writer.writeParseTypeAttribute();
                        List<RDFNode> list = new ArrayList<RDFNode>();
                        toJavaList(objectRes, list);
                        for (RDFNode n : list) {
                            if (n.isAnonymous()) {
                                render((RDFResourceNode) n);
                            } else {
                                if (n.isLiteral()) {
                                    RDFLiteralNode litNode = (RDFLiteralNode) n;
                                    writer.writeStartElement(OWLRDFVocabulary.RDFS_LITERAL.getURI());
                                    if (litNode.getDatatype() != null) {
                                        writer.writeDatatypeAttribute(litNode.getDatatype());
                                    } else if (litNode.getLang() != null) {
                                        writer.writeLangAttribute(litNode.getLang());
                                    }
                                    writer.writeTextContent((litNode.getLiteral()));
                                    writer.writeEndElement();
                                } else {
                                    writer.writeStartElement(RDF_DESCRIPTION.getURI());
                                    writer.writeAboutAttribute(n.getURI());
                                    writer.writeEndElement();
                                }
                            }
                        }
                    } else {
                        render(objectRes);
                    }
                } else {
                    writer.writeResourceAttribute(objectRes.getURI());
                }
            } else {
                RDFLiteralNode rdfLiteralNode = ((RDFLiteralNode) objectNode);
                if (rdfLiteralNode.getDatatype() != null) {
                    writer.writeDatatypeAttribute(rdfLiteralNode.getDatatype());
                } else if (rdfLiteralNode.getLang() != null) {
                    writer.writeLangAttribute(rdfLiteralNode.getLang());
                }
                writer.writeTextContent(rdfLiteralNode.getLiteral());
            }
            writer.writeEndElement();
        }
        writer.writeEndElement();
        pending.remove(node);
    }
}
