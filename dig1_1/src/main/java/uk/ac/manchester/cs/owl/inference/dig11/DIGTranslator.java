package uk.ac.manchester.cs.owl.inference.dig11;

import org.semanticweb.owl.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
public interface DIGTranslator {

    ///////////////////////////////////////////////
    //
    // DIG Document creation
    //
    ///////////////////////////////////////////////


    /**
     * Creates an XML DIG Document, whose root contains the appropriate
     * namespaces etc.
     *
     * @param rootTagName The name of the document root element.
     * @param kbURI       The URI of the knowledge base held in the external
     *                    DIG reasoner, which the document relates to.
     */
    public Document createDIGDocument(String rootTagName, String kbURI) throws DIGReasonerException;


    /**
     * Creates an XML DIG Document, whose root contains the appropriate
     * name spaces etc.  The document created does not contain a knowledge base
     * URI, and therfore cannot be used to formulate requests that will act
     * on a specific knowledge base.
     *
     * @param rootTagName The name of the document root element.
     */
    public Document createDIGDocument(String rootTagName);


    /**
     * A convenience method that creates an XML DIG Tells document.
     *
     * @param kbURI The URI that specifies the external DIG reasoner
     *              knowledge base that the tells request relates to.
     */
    public Document createTellsDocument(String kbURI);


    /**
     * A convenience method that creates an XML DIG Asks document.
     *
     * @param kbURI The URI that specifies the external DIG reasoner
     *              knowledge base that the ask request should act upon.
     */
    public Document createAsksDocument(String kbURI);


    /**
     * Translates a set of <code>OWLOntology</code> objects to DIG
     *
     * @param ontologies The ontologies to be translated
     * @param doc        The Document that the rendering will be created in
     * @param node       The parent node that the dig rendereing will be appended to
     * @throws DIGReasonerException
     */
    public void translateToDIG(Set<OWLOntology> ontologies, Document doc, Element node) throws OWLException, DIGReasonerException;


    /**
     * Translates an OWLObject to DIG
     *
     * @param i    The element to be translated
     * @param doc  The Document that the rendering will be created in
     * @param node The parent node that the dig rendereing will be appended to
     * @throws DIGReasonerException
     */
    public void translateToDIG(OWLObject i, Document doc, Node node) throws DIGReasonerException;

    // Primitive concept retrieval


    /**
     * Creates a DIG Query that will return a list of the primitive
     * concepts in the knowledge base.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @throws DIGReasonerException
     */
    public void createAllConceptNamesQuery(Document doc,
                                           String queryID) throws DIGReasonerException;


    /**
     * Creates a DIG Query that will return a list of properties
     * in the knowledge base.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @throws DIGReasonerException
     */
    public void createAllPropertyNamesQuery(Document doc,
                                            String queryID) throws DIGReasonerException;


    /**
     * Create a DIG Query that will return a list of the individuals
     * in the knowledge base.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @throws DIGReasonerException
     */
    public void createAllIndividualsQuery(Document doc,
                                          String queryID) throws DIGReasonerException;

    //////////////////////////////////////////////////////////////////////
    //
    // Satisfiability
    //
    //////////////////////////////////////////////////////////////////////


    /**
     * Creates a query that asks if a given class is satisfiable.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param aClass  The class whose satisfiability/consistency is to be determined.
     * @throws DIGReasonerException
     */
    public void createSatisfiableQuery(Document doc,
                                       String queryID,
                                       OWLDescription aClass) throws DIGReasonerException;


    /**
     * Creates a query that asks if the intersection of a set
     * of classes is satisfiable/consistent.
     *
     * @param doc          The document that the query will be created in/appended to.
     *                     Note that the knowledge base URI parameter of the document root
     *                     element will determine the knowledge base that this query relates to.
     * @param queryID      A <code>String</code> that represents an identifier
     *                     for the query, which can be used to match the query to its result.
     * @param descriptions A set of classes, whose intersection will be tested
     *                     for satisfiability.
     * @throws DIGReasonerException
     */
    public void createSatisfiableQuery(Document doc,
                                       String queryID,
                                       Set<OWLDescription> descriptions) throws DIGReasonerException;


    /**
     * Creates a query that asks if one concept subsumes another concept.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param cls1    The cconcept that is the subsumer
     * @param cls2    The concept that is the subsumee
     * @throws DIGReasonerException
     */
    public void createSubsumesQuery(Document doc,
                                    String queryID,
                                    OWLDescription cls1,
                                    OWLDescription cls2) throws DIGReasonerException;


    /**
     * Creates a query that asks if one concept is disjoint with another concept.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param cls1    The first concept
     * @param cls2    The second concept
     * @throws DIGReasonerException
     */
    public void createDisjointQuery(Document doc,
                                    String queryID,
                                    OWLDescription cls1,
                                    OWLDescription cls2) throws DIGReasonerException;

    //////////////////////////////////////////////////////////////////////////////////
    //
    // Concept hierarchy
    //
    //////////////////////////////////////////////////////////////////////////////////


    /**
     * Creates a query that asks for the direct super concepts of a given concept.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param aClass  The concept whose super concepts are to be asked for.
     * @throws DIGReasonerException
     */
    public void createDirectSuperConceptsQuery(Document doc,
                                               String queryID,
                                               OWLDescription aClass) throws DIGReasonerException;


    /**
     * Creates a query that asks for the direct super concepts of an intersection
     * of classes.
     *
     * @param doc          The document that the query will be created in/appended to.
     *                     Note that the knowledge base URI parameter of the document root
     *                     element will determine the knowledge base that this query relates to.
     * @param queryID      A <code>String</code> that represents an identifier
     *                     for the query, which can be used to match the query to its result.
     * @param descriptions A set of classes whose intersection super concepts are to be asked for.
     * @throws DIGReasonerException
     */
    public void createDirectSuperConceptsQuery(Document doc,
                                               String queryID,
                                               Set<OWLDescription> descriptions) throws DIGReasonerException;


    /**
     * Creates a query that asks for the direct sub concepts of a given concept.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param aClass  The concept whose sub concepts are to be asked for.
     * @throws DIGReasonerException
     */
    public void createDirectSubConceptsQuery(Document doc,
                                             String queryID,
                                             OWLDescription aClass) throws DIGReasonerException;


    /**
     * Creates a query that asks for the ancestor concepts of a given concept,
     * this includes all super concepts (not just direct ones).
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param aClass  The concept whose ancestor concepts are to be asked for.
     * @throws DIGReasonerException
     */
    public void createAncestorConceptsQuery(Document doc,
                                            String queryID,
                                            OWLDescription aClass) throws DIGReasonerException;


    /**
     * Creates a query that asks for the descendant concepts of a given
     * concept.  This includes all sub concepts (not just direct ones).
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param aClass  The concept whose subconcepts are to be asked for.
     * @throws DIGReasonerException
     */
    public void createDescendantConceptsQuery(Document doc,
                                              String queryID,
                                              OWLDescription aClass) throws DIGReasonerException;


    /**
     * Creates a query that asks for the concepts that are equivalent (i.e.
     * have the same extension as) to a given concept.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param aClass  The concepts whose equivalent concepts are to be asked for.
     * @throws DIGReasonerException
     */
    public void createEquivalentConceptsQuery(Document doc,
                                              String queryID,
                                              OWLDescription aClass) throws DIGReasonerException;

    ///////////////////////////////////////////////////////////////////////////////
    //
    // Role hierarchy
    //
    ///////////////////////////////////////////////////////////////////////////////


    /**
     * Creates a query that asks for the direct super properties of a given
     * property.
     *
     * @param doc      The document that the query will be created in/appended to.
     *                 Note that the knowledge base URI parameter of the document root
     *                 element will determine the knowledge base that this query relates to.
     * @param queryID  A <code>String</code> that represents an identifier
     *                 for the query, which can be used to match the query to its result.
     * @param property The property (property) whose direct super properties
     *                 are to be asked for.
     * @throws DIGReasonerException
     */
    public void createDirectSuperPropertiesQuery(Document doc,
                                                 String queryID,
                                                 OWLObjectProperty property) throws DIGReasonerException;


    /**
     * Creates a query that asks for the direct sub properties of a given
     * property.
     *
     * @param doc      The document that the query will be created in/appended to.
     *                 Note that the knowledge base URI parameter of the document root
     *                 element will determine the knowledge base that this query relates to.
     * @param queryID  A <code>String</code> that represents an identifier
     *                 for the query, which can be used to match the query to its result.
     * @param property The property (property) whose direct sub properites are to be asked for.
     * @throws DIGReasonerException
     */
    public void createDirectSubPropertiesQuery(Document doc,
                                               String queryID,
                                               OWLObjectProperty property) throws DIGReasonerException;


    /**
     * Creates a query that asks for the ancestor properties (i.e. all super properties
     * and not just direct super properties) of a given property.
     *
     * @param doc      The document that the query will be created in/appended to.
     *                 Note that the knowledge base URI parameter of the document root
     *                 element will determine the knowledge base that this query relates to.
     * @param queryID  A <code>String</code> that represents an identifier
     *                 for the query, which can be used to match the query to its result.
     * @param property The property (property) whose super properties are to be asked for.
     * @throws DIGReasonerException
     */
    public void createAncestorPropertiesQuery(Document doc,
                                              String queryID,
                                              OWLObjectProperty property) throws DIGReasonerException;


    /**
     * Creates a query that asks for the descendant properties (i.e. all sub
     * properties and not just the direct sub properties) of a given property.
     *
     * @param doc      The document that the query will be created in/appended to.
     *                 Note that the knowledge base URI parameter of the document root
     *                 element will determine the knowledge base that this query relates to.
     * @param queryID  A <code>String</code> that represents an identifier
     *                 for the query, which can be used to match the query to its result.
     * @param property The property (property) whose sub properties are to be asked for.
     * @throws DIGReasonerException
     */
    public void createDescendantPropertiesQuery(Document doc,
                                                String queryID,
                                                OWLObjectProperty property) throws DIGReasonerException;

    /////////////////////////////////////////////////////////////////////////////////
    //
    // Individuals
    //
    ////////////////////////////////////////////////////////////////////////////////


    /**
     * Creates a query that asks for the instances (individuals belonging to)
     * a given concept.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param aClass  The concept (RDFSClass) whose instances are to be asked for.
     * @throws DIGReasonerException
     */
    public void createInstancesOfConceptQuery(Document doc,
                                              String queryID,
                                              OWLDescription aClass) throws DIGReasonerException;


    /**
     * Creates a query that asks for the types (concepts) that an individual
     * belongs to.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param ins     The individual (RDFIndividual) whose types are to
     *                be asked for.
     * @throws DIGReasonerException
     */
    public void createIndividualTypesQuery(Document doc,
                                           String queryID,
                                           OWLIndividual ins) throws DIGReasonerException;


    /**
     * Creates a query that asks if an individual is an instance (member of)
     * a given class.
     *
     * @param doc     The document that the query will be created in/appended to.
     *                Note that the knowledge base URI parameter of the document root
     *                element will determine the knowledge base that this query relates to.
     * @param queryID A <code>String</code> that represents an identifier
     *                for the query, which can be used to match the query to its result.
     * @param ins     The individual.
     * @param aClass  The class.
     * @throws DIGReasonerException
     */
    public void createIndividualInstanceOfConceptQuery(Document doc,
                                                       String queryID,
                                                       OWLIndividual ins,
                                                       OWLDescription aClass) throws DIGReasonerException;


    /**
     * Creates a query that asks for the fillers for a qiven individual/property
     * pair.
     *
     * @param doc      The document that the query will be created in/appended to.
     *                 Note that the knowledge base URI parameter of the document root
     *                 element will determine the knowledge base that this query relates to.
     * @param queryID  A <code>String</code> that represents an identifier
     *                 for the query, which can be used to match the query to its result.
     * @param ins      The individual (instance) that the property is 'attached' to.
     * @param property The property whose fillers are to be retrieved.
     * @throws DIGReasonerException
     */
    public void createPropertyFillersQuery(Document doc,
                                           String queryID,
                                           OWLIndividual ins,
                                           OWLObjectProperty property) throws DIGReasonerException;


    /**
     * Creates a query that asks for the individuals that are related
     * to each other via a given property.
     *
     * @param doc      The document that the query will be created in/appended to.
     *                 Note that the knowledge base URI parameter of the document root
     *                 element will determine the knowledge base that this query relates to.
     * @param queryID  A <code>String</code> that represents an identifier
     *                 for the query, which can be used to match the query to its result.
     * @param property The property (property).
     * @throws DIGReasonerException
     */
    public void createRelatedIndividualsQuery(Document doc,
                                              String queryID,
                                              OWLObjectProperty property) throws DIGReasonerException;


    /**
     * Returns an iterator that can be used to
     * traverse the responses to multiple queries.
     * The iterator will iterate over <code>DIGQueryResonse</code>
     * objects.
     */
    public Iterator<DIGQueryResponse> getDIGQueryResponseIterator(OWLDataFactory factory, Document doc) throws DIGReasonerException;
}
