package org.coode.owlapi.rdf.rdfxml;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_DESCRIPTION;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.coode.owlapi.rdf.model.RDFLiteralNode;
import org.coode.owlapi.rdf.model.RDFNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.coode.owlapi.rdf.renderer.RDFRendererBase;
import org.coode.string.EscapeUtils;
import org.coode.xml.OWLOntologyXMLNamespaceManager;
import org.coode.xml.XMLWriterFactory;
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
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Bio-Health Informatics Group<br> Date:
 * 06-Dec-2006<br><br>
 */
public class RDFXMLRenderer extends RDFRendererBase {

    private RDFXMLWriter writer;

    private Set<RDFResourceNode> pending;

    //private Set<RDFResourceNode> renderedAnonymousNodes;

    public RDFXMLRenderer(OWLOntologyManager manager, OWLOntology ontology, Writer w) {
        this(manager, ontology, w, manager.getOntologyFormat(ontology));
    }


    public RDFXMLRenderer(OWLOntologyManager manager, OWLOntology ontology, Writer w, OWLOntologyFormat format) {
        super(ontology, manager, format);
        pending = new HashSet<RDFResourceNode>();
        //renderedAnonymousNodes = new HashSet<RDFResourceNode>();

        OWLOntologyXMLNamespaceManager nsm = new OWLOntologyXMLNamespaceManager(
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
        prettyPrintedTypes = new HashSet<IRI>();
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_CLASS.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_DATA_PROPERTY.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_RESTRICTION.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_THING.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_NOTHING.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_ONTOLOGY.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.RDFS_DATATYPE.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_AXIOM.getIRI());
        prettyPrintedTypes.add(OWLRDFVocabulary.OWL_ANNOTATION.getIRI());
    }


    @Override
	protected void beginDocument() throws IOException  {
        writer.startDocument();
    }


    @Override
	protected void endDocument() throws IOException  {
        writer.endDocument();
        writer.writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
    }


    @Override
	protected void writeIndividualComments(OWLNamedIndividual ind) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(ind.getIRI().toString()));
    }


    @Override
	protected void writeClassComment(OWLClass cls) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(cls.getIRI().toString()));
    }


    @Override
	protected void writeDataPropertyComment(OWLDataProperty prop) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(prop.getIRI().toString()));
    }


    @Override
	protected void writeObjectPropertyComment(OWLObjectProperty prop) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(prop.getIRI().toString()));
    }

    @Override
	protected void writeAnnotationPropertyComment(OWLAnnotationProperty prop) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(prop.getIRI().toString()));
    }

    @Override
	protected void writeDatatypeComment(OWLDatatype datatype) throws IOException  {
        writer.writeComment(EscapeUtils.escapeXML(datatype.getIRI().toString()));
    }

    @Override
	protected void writeBanner(String name) throws IOException  {
        writer.writeComment(
                "\n///////////////////////////////////////////////////////////////////////////////////////\n" + "//\n" + "// " + name + "\n" + "//\n" + "///////////////////////////////////////////////////////////////////////////////////////\n");
    }


    @Override
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
            IRI propertyIRI = triple.getProperty().getIRI();
            if (propertyIRI.equals(OWLRDFVocabulary.RDF_TYPE.getIRI()) && !triple.getObject().isAnonymous()) {
                if (OWLRDFVocabulary.BUILT_IN_VOCABULARY_IRIS.contains(triple.getObject().getIRI())) {
                    if (prettyPrintedTypes.contains(triple.getObject().getIRI())) {
                        candidatePrettyPrintTypeTriple = triple;
                    }
                } else {
                    candidatePrettyPrintTypeTriple = triple;
                }
            }
        }
        if (candidatePrettyPrintTypeTriple == null) {
            writer.writeStartElement(RDF_DESCRIPTION.getIRI());
        } else {
            writer.writeStartElement(candidatePrettyPrintTypeTriple.getObject().getIRI());
        }
        if (!node.isAnonymous()) {
            writer.writeAboutAttribute(node.getIRI());
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
            writer.writeStartElement(triple.getProperty().getIRI());
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
                                    writer.writeStartElement(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
                                    if (litNode.getDatatype() != null) {
                                        writer.writeDatatypeAttribute(litNode.getDatatype());
                                    } else if (litNode.getLang() != null) {
                                        writer.writeLangAttribute(litNode.getLang());
                                    }
                                    writer.writeTextContent((litNode.getLiteral()));
                                    writer.writeEndElement();
                                } else {
                                    writer.writeStartElement(RDF_DESCRIPTION.getIRI());
                                    writer.writeAboutAttribute(n.getIRI());
                                    writer.writeEndElement();
                                }
                            }
                        }
                    } else {
                        render(objectRes);
                    }
                } else {
                    writer.writeResourceAttribute(objectRes.getIRI());
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
