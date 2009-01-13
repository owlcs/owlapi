package uk.ac.manchester.cs.owl.inference.dig11;

import org.semanticweb.owl.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Iterator;
import java.util.Set;
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21-Nov-2006<br><br>
 */
public class DIGTranslatorImpl implements DIGTranslator {

    private OWLOntologyManager manager;

    private DocumentBuilder docBuilder;

    public static final String DL_NAMESPACE = "http://dl.kr.org/dig/2003/02/lang";


    public DIGTranslatorImpl(OWLOntologyManager manager) {
        this.manager = manager;

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    public Document createTellsDocument(String kbURI) {
        return createDIGDocument(Vocab.TELLS, kbURI);
    }


    public Document createAsksDocument(String kbURI) {
        return createDIGDocument(Vocab.ASKS, kbURI);
    }


    public Document createDIGDocument(String rootTagName, String kbURI) {
        Document doc = createDIGDocument(rootTagName);
        doc.getDocumentElement().setAttribute("uri", kbURI);
        return doc;
    }


    public Document createDIGDocument(String rootTagName) {
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement(rootTagName);

        // Set up the DIG namespaces
        rootElement.setAttribute("xmlns", DL_NAMESPACE); // Default namespace

//        rootElement.setAttribute("xmlns:xs", XML_SCHEMA_NAMESPACE);

        //       rootElement.setAttribute("xmlns:xsi", XML_SCHEMA_INSTANCE_NAMESPACE);

        //       rootElement.setAttribute("xsi:schemaLocation", DL_SCHEMA_LOCATION);

        doc.appendChild(rootElement);

        return doc;
    }


    public void translateToDIG(Set<OWLOntology> ontologies, Document doc, Element node) throws DIGReasonerException {
        DIGRenderer render = new DIGRenderer(manager, doc, node);
        for (OWLOntology ont : ontologies) {
            ont.accept(render);
        }
    }


    /**
     * Translates an <code>OWLObject</code> to DIG
     * @param i    The element to be translated
     * @param doc  The Document that the rendering will be created in
     * @param node The parent node that the dig rendereing will be appended to
     */
    public void translateToDIG(OWLObject i, Document doc, Node node) throws DIGReasonerException {
        DIGRenderer render = new DIGRenderer(manager, doc, (Element) node);
        i.accept(render);
    }


    public Iterator<DIGQueryResponse> getDIGQueryResponseIterator(OWLDataFactory factory, Document doc) throws
                                                                                                        DIGReasonerException {
        return new DIGQueryResponseIterator(doc, factory);
    }


    protected static Element createQueryElement(Document doc, String name, String queryID) {
        Element element = doc.createElement(name);
        element.setAttribute("id", queryID);
        return element;
    }


    // Primitive concept retrieval
    public void createAllConceptNamesQuery(Document doc, String queryID) {
        Element element = createQueryElement(doc, Vocab.ALL_CONCEPT_NAMES, queryID);

        doc.getDocumentElement().appendChild(element);
    }


    public void createAllPropertyNamesQuery(Document doc, String queryID) {
        Element element = createQueryElement(doc, Vocab.ALL_ROLE_NAMES, queryID);

        doc.getDocumentElement().appendChild(element);
    }


    public void createAllIndividualsQuery(Document doc, String queryID) {
        Element element = createQueryElement(doc, Vocab.ALL_INDIVIDUALS, queryID);

        doc.getDocumentElement().appendChild(element);
    }


    // Satisfiability
    public void createSatisfiableQuery(Document doc, String queryID, OWLDescription aClass) throws
                                                                                            DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.SATISFIABLE, queryID);

        translateToDIG(aClass, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createSatisfiableQuery(Document doc, String queryID, Set<OWLDescription> clses) throws
                                                                                                DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.SATISFIABLE, queryID);

        Element intersectionElement = doc.createElement(Vocab.AND);

        element.appendChild(intersectionElement);

        for (OWLDescription desc : clses) {
            translateToDIG(desc, doc, intersectionElement);
        }

        doc.getDocumentElement().appendChild(element);
    }


    public void createSubsumesQuery(Document doc, String queryID, OWLDescription cls1, OWLDescription cls2) throws
                                                                                                            DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.SUBSUMES, queryID);

        translateToDIG(cls1, doc, element);

        translateToDIG(cls2, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createDisjointQuery(Document doc, String queryID, OWLDescription cls1, OWLDescription cls2) throws
                                                                                                            DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.DISJOINT, queryID);

        translateToDIG(cls1, doc, element);

        translateToDIG(cls2, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    // Concept hierarchy
    public void createDirectSuperConceptsQuery(Document doc, String queryID, OWLDescription aClass) throws
                                                                                                    DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.PARENTS, queryID);

        translateToDIG(aClass, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createDirectSuperConceptsQuery(Document doc, String queryID, Set<OWLDescription> clses) throws
                                                                                                        DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.PARENTS, queryID);
        Element andElement = doc.createElement(Vocab.AND);
        element.appendChild(andElement);
        for (OWLDescription desc : clses) {
            translateToDIG(desc, doc, andElement);
        }
        doc.getDocumentElement().appendChild(element);
    }


    public void createDirectSubConceptsQuery(Document doc, String queryID, OWLDescription aClass) throws
                                                                                                  DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.CHILDREN, queryID);

        translateToDIG(aClass, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createAncestorConceptsQuery(Document doc, String queryID, OWLDescription aClass) throws
                                                                                                 DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.ANCESTORS, queryID);

        translateToDIG(aClass, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createDescendantConceptsQuery(Document doc, String queryID, OWLDescription aClass) throws
                                                                                                   DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.DESCENDANTS, queryID);

        translateToDIG(aClass, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createEquivalentConceptsQuery(Document doc, String queryID, OWLDescription aClass) throws
                                                                                                   DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.EQUIVALENT, queryID);

        translateToDIG(aClass, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    // Role hierarchy
    public void createDirectSuperPropertiesQuery(Document doc, String queryID, OWLObjectProperty property) throws
                                                                                                           DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.R_PARENTS, queryID);

        translateToDIG(property, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createDirectSubPropertiesQuery(Document doc, String queryID, OWLObjectProperty property) throws
                                                                                                         DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.R_CHILDREN, queryID);

        translateToDIG(property, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createAncestorPropertiesQuery(Document doc, String queryID, OWLObjectProperty property) throws
                                                                                                        DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.R_ANCESTORS, queryID);

        translateToDIG(property, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createDescendantPropertiesQuery(Document doc, String queryID, OWLObjectProperty property) throws
                                                                                                          DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.R_DESCENDANTS, queryID);

        translateToDIG(property, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    // Individuals
    public void createInstancesOfConceptQuery(Document doc, String queryID, OWLDescription aClass) throws
                                                                                                   DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.INSTANCES, queryID);

        translateToDIG(aClass, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createIndividualTypesQuery(Document doc, String queryID, OWLIndividual ins) throws
                                                                                            DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.TYPES, queryID);

        translateToDIG(ins, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createIndividualInstanceOfConceptQuery(Document doc, String queryID, OWLIndividual ins,
                                                       OWLDescription aClass) throws DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.INSTANCE, queryID);

        translateToDIG(ins, doc, element);

        translateToDIG(aClass, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createPropertyFillersQuery(Document doc, String queryID, OWLIndividual ins,
                                           OWLObjectProperty property) throws DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.ROLE_FILLERS, queryID);

        translateToDIG(ins, doc, element);

        translateToDIG(property, doc, element);

        doc.getDocumentElement().appendChild(element);
    }


    public void createRelatedIndividualsQuery(Document doc, String queryID, OWLObjectProperty property) throws
                                                                                                        DIGReasonerException {
        Element element = createQueryElement(doc, Vocab.RELATED_INDIVIDUALS, queryID);

        translateToDIG(property, doc, element);

        doc.getDocumentElement().appendChild(element);
    }
}
