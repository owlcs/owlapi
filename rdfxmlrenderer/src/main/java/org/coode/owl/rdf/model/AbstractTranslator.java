package org.coode.owl.rdf.model;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.OWLObjectVisitorAdapter;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import static org.semanticweb.owl.vocab.OWLRDFVocabulary.*;
import static org.semanticweb.owl.vocab.SWRLVocabulary.*;
import org.semanticweb.owl.vocab.XSDVocabulary;

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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br><br>
 * <p/>
 * An abstract translator that can produce an RDF graph from an OWLOntology.  Subclasses must
 * provide implementations to create concrete representations of resources, triples etc.
 */
public abstract class AbstractTranslator<NODE, RESOURCE extends NODE, PREDICATE extends NODE, LITERAL extends NODE> implements OWLObjectVisitor, SWRLObjectVisitor {

    private OWLOntologyManager manager;

    private OWLOntology ontology;


    public AbstractTranslator(OWLOntologyManager manager, OWLOntology ontology) {
        this.ontology = ontology;
        this.manager = manager;
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_ASYMMETRIC_PROPERTY.getURI());
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        addAxiom(axiom, axiom.getIndividual(), RDF_TYPE.getURI(), axiom.getClassExpression());
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                RDFS_DOMAIN.getURI(),
                axiom.getDomain());
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                RDFS_RANGE.getURI(),
                axiom.getRange());
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        addAxiom(axiom,
                axiom.getSubProperty(),
                RDFS_SUB_PROPERTY_OF.getURI(),
                axiom.getSuperProperty());
    }


    public void visit(OWLDeclarationAxiom axiom) {
        if (axiom.getEntity().isOWLClass()) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_CLASS.getURI());
        } else if (axiom.getEntity().isOWLDataProperty()) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_DATA_PROPERTY.getURI());
        } else if (axiom.getEntity().isOWLObjectProperty()) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getURI());
        } else if (axiom.getEntity().isOWLIndividual()) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL.getURI());
        } else if (axiom.getEntity().isOWLDatatype()) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.RDFS_DATATYPE.getURI());
        }
        else if(axiom.getEntity().isOWLAnnotationProperty()) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getURI());
        }
    }


    private void addPairwise(OWLAxiom axiom, Collection<? extends OWLObject> objects, URI uri) {
        List<? extends OWLObject> objectList = new ArrayList<OWLObject>(objects);
        for (int i = 0; i < objectList.size(); i++) {
            for (int j = i; j < objectList.size(); j++) {
                if (i != j) {
                    addAxiom(axiom, objectList.get(i), uri, objectList.get(j));
                }
            }
        }
    }


    private ClassExpressionComparator classExpressionComparator = new ClassExpressionComparator();


    /**
     * Renders a set of class expressions in a pairwise manner using the specified URI.  It
     * is assumed that the relationship described by the URI (e.g. disjointWith) is
     * symmetric.  The method delegates to the <code>addPairwise</code> method after sorting
     * the class expressions so that named classes appear first.
     *
     * @param axiom            The axiom which will dictate which axiom annotation get rendered
     * @param classExpressions The set of class expressions to be rendered.
     * @param uri              The URI which describes the relationship between pairs of class expressions.
     */
    private void addPairwiseClassExpressions(OWLAxiom axiom, Set<OWLClassExpression> classExpressions, URI uri) {
        List<OWLClassExpression> classExpressionList = new ArrayList<OWLClassExpression>(classExpressions);
        Collections.sort(classExpressionList, classExpressionComparator);
        addPairwise(axiom, classExpressionList, uri);

    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DIFFERENT.getURI());
        addTriple(axiom, OWL_DISTINCT_MEMBERS.getURI(), axiom.getIndividuals());
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() == 2) {
            addPairwiseClassExpressions(axiom, axiom.getClassExpressions(), OWL_DISJOINT_WITH.getURI());
        } else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_CLASSES.getURI());
            addTriple(axiom, OWL_MEMBERS.getURI(), axiom.getClassExpressions());
        }
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_DISJOINT_DATA_PROPERTIES.getURI());
        } else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_PROPERTIES.getURI());
            addTriple(axiom, OWL_MEMBERS.getURI(), axiom.getProperties());
        }
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_DISJOINT_OBJECT_PROPERTIES.getURI());
        } else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_PROPERTIES.getURI());
            addTriple(axiom, OWL_MEMBERS.getURI(), axiom.getProperties());
        }
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        addAxiom(axiom, axiom.getOWLClass(), OWL_DISJOINT_UNION_OF.getURI(), axiom.getClassExpressions());
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
//        translateAnnotation(axiom, axiom.getOWLAnnotation());
//        addTriple(axiom.getSubject(),
//                axiom.getProperty().getIRI(),
//                axiom.getOWLAnnotation().getValue());
        translateAnnotation(axiom.getSubject(), axiom.getAnnotation());
        addAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getValue());
//        addTriple(getResourceNode(axiom.getProperty()),
//                getPredicateNode(RDF_TYPE.getIRI()),
//                getResourceNode(OWL_ANNOTATION_PROPERTY.getIRI()));

//        if (axiom.getOWLAnnotation().getValue().isAnonymousIndividual()) {
//            OWLIndividual ind = (OWLIndividual) axiom.getOWLAnnotation().getValue().asAnonymousIndividual();
//            processAnonymousIndividual(ind);
//        }
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getURI(), axiom.getDomain());
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getURI(), axiom.getRange());
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        addAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getURI(), axiom.getSuperProperty());
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
        addPairwiseClassExpressions(axiom, axiom.getClassExpressions(), OWL_EQUIVALENT_CLASS.getURI());
    }

    public void visit(OWLHasKeyAxiom axiom) {
        addAxiom(axiom, axiom.getClassExpression(), OWL_HAS_KEY.getURI(), axiom.getPropertyExpressions());
    }


    public void visit(OWLDatatypeDefinition axiom) {
        addAxiom(axiom, axiom.getDatatype(), OWL_EQUIVALENT_CLASS.getURI(), axiom.getDataRange());
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        addPairwise(axiom,
                axiom.getProperties(),
                isPunned(axiom.getProperties()) ? OWL_EQUIVALENT_DATA_PROPERTIES.getURI() : OWL_EQUIVALENT_PROPERTY.getURI());
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        addPairwise(axiom,
                axiom.getProperties(),
                isPunned(axiom.getProperties()) ? OWL_EQUIVALENT_OBJECT_PROPERTIES.getURI() : OWL_EQUIVALENT_PROPERTY.getURI());
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                RDF_TYPE.getURI(),
                isPunned(axiom.getProperty()) ? OWL_FUNCTIONAL_DATA_PROPERTY.getURI() : OWL_FUNCTIONAL_PROPERTY.getURI());
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                RDF_TYPE.getURI(),
                isPunned(axiom.getProperty()) ? OWL_FUNCTIONAL_OBJECT_PROPERTY.getURI() : OWL_FUNCTIONAL_PROPERTY.getURI());
    }


    public void visit(OWLImportsDeclaration axiom) {
        addTriple(ontology, OWL_IMPORTS.getURI(), axiom.getURI());
        addTriple(getResourceNode(axiom.getURI()),
                getPredicateNode(RDF_TYPE.getURI()),
                getResourceNode(OWL_ONTOLOGY.getURI()));
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_INVERSE_FUNCTIONAL_PROPERTY.getURI());
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        addAxiom(axiom, axiom.getFirstProperty(), OWL_INVERSE_OF.getURI(), axiom.getSecondProperty());
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_IRREFLEXIVE_PROPERTY.getURI());
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getURI(), OWL_NEGATIVE_DATA_PROPERTY_ASSERTION.getURI());
        addAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getURI(), OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION.getURI());

        addAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
        processAnonymousIndividual(axiom.getObject());
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        addAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
        processAnonymousIndividual(axiom.getObject());
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        RESOURCE anonNode = getAnonymousNode(axiom);
        RESOURCE list = translateList(axiom.getPropertyChain());
        addTriple(anonNode, getPredicateNode(OWL_PROPERTY_CHAIN.getURI()), list);
        addAxiom(axiom,
                anonNode,
                getPredicateNode(RDFS_SUB_PROPERTY_OF.getURI()),
                getResourceNode(axiom.getSuperProperty()));
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getURI(), axiom.getDomain());
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                isPunned(axiom.getProperty()) ? OWL_OBJECT_PROPERTY_RANGE.getURI() : RDFS_RANGE.getURI(),
                axiom.getRange());
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        addAxiom(axiom,
                axiom.getSubProperty(),
                isPunned(axiom.getSubProperty(),
                        axiom.getSuperProperty()) ? OWL_SUB_OBJECT_PROPERTY_OF.getURI() : RDFS_SUB_PROPERTY_OF.getURI(),
                axiom.getSuperProperty());
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_REFLEXIVE_PROPERTY.getURI());
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        addPairwise(axiom, axiom.getIndividuals(), OWL_SAME_AS.getURI());
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        addAxiom(axiom, axiom.getSubClass(), RDFS_SUBCLASS_OF.getURI(), axiom.getSuperClass());
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_SYMMETRIC_PROPERTY.getURI());
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_TRANSITIVE_PROPERTY.getURI());
    }


    public void visit(OWLObjectIntersectionOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, OWL_INTERSECTION_OF.getURI(), desc.getOperands());
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
    }


    public void visit(OWLClass desc) {
        if (!nodeMap.containsKey(desc)) {
            nodeMap.put(desc, getResourceNode(desc.getURI()));
        }
//        addTriple(desc, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Data restrictions
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void translateDataRestrictionStart(OWLRestriction desc) {
        translateAnonymousNode(desc);
        addTriple(desc,
                RDF_TYPE.getURI(),
                OWL_RESTRICTION.getURI());
        addTriple(desc, OWL_ON_PROPERTY.getURI(), desc.getProperty());
    }


    private void translateDataCardinality(OWLDataCardinalityRestriction desc, URI cardinalityURI) {
        translateDataRestrictionStart(desc);
        addTriple(desc, cardinalityURI, toTypedConstant(desc.getCardinality()));
        if (desc.isQualified()) {
            addTriple(desc, OWL_CLASS.getURI(), desc.getFiller());
        }
    }


    public void visit(OWLDataAllValuesFrom desc) {
        translateDataRestrictionStart(desc);
        addTriple(desc, OWL_ALL_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLDataExactCardinality desc) {
        translateDataCardinality(desc, OWL_CARDINALITY.getURI());
    }


    public void visit(OWLDataMaxCardinality desc) {
        translateDataCardinality(desc, OWL_MAX_CARDINALITY.getURI());
    }


    public void visit(OWLDataMinCardinality desc) {
        translateDataCardinality(desc, OWL_MIN_CARDINALITY.getURI());
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        translateDataRestrictionStart(desc);
        addTriple(desc, OWL_SOME_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLDataHasValue desc) {
        translateDataRestrictionStart(desc);
        addTriple(desc, OWL_HAS_VALUE.getURI(), desc.getValue());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLObjectComplementOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
        addTriple(desc, OWL_COMPLEMENT_OF.getURI(), desc.getOperand());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////
    //// Object restrictions
    ////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void translateObjectRestrictionStart(OWLRestriction desc) {
        translateAnonymousNode(desc);
        addTriple(desc,
                RDF_TYPE.getURI(),
                OWL_RESTRICTION.getURI());
        addTriple(desc, OWL_ON_PROPERTY.getURI(), desc.getProperty());
    }


    private void translateObjectCardinality(OWLObjectCardinalityRestriction desc, URI cardinalityURI) {
        translateObjectRestrictionStart(desc);
        addTriple(desc, cardinalityURI, toTypedConstant(desc.getCardinality()));
        if (desc.isQualified()) {
            addTriple(desc, OWL_ON_CLASS.getURI(), desc.getFiller());
        }
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        translateObjectRestrictionStart(desc);
        addTriple(desc, OWL_ALL_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLObjectExactCardinality desc) {
        translateObjectCardinality(desc, desc.isQualified() ? OWL_QUALIFIED_CARDINALITY.getURI() : OWL_CARDINALITY.getURI());
    }


    public void visit(OWLObjectMaxCardinality desc) {
        translateObjectCardinality(desc, desc.isQualified() ? OWL_MAX_QUALIFIED_CARDINALITY.getURI() : OWL_MAX_CARDINALITY.getURI());
    }


    public void visit(OWLObjectMinCardinality desc) {
        translateObjectCardinality(desc, desc.isQualified() ? OWL_MIN_QUALIFIED_CARDINALITY.getURI() : OWL_MIN_CARDINALITY.getURI());
    }


    public void visit(OWLObjectOneOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, OWL_ONE_OF.getURI(), desc.getIndividuals());
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
    }


    public void visit(OWLObjectHasSelf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getURI(), OWL_SELF_RESTRICTION.getURI());
        addTriple(desc, OWL_ON_PROPERTY.getURI(), desc.getProperty());
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        translateObjectRestrictionStart(desc);
        addTriple(desc, OWL_SOME_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLObjectHasValue desc) {
        translateObjectRestrictionStart(desc);
        addTriple(desc, OWL_HAS_VALUE.getURI(), desc.getValue());
    }


    public void visit(OWLObjectUnionOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, OWL_UNION_OF.getURI(), desc.getOperands());
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
    }


    public void visit(OWLDataOneOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
        addTriple(node, OWL_ONE_OF.getURI(), node.getValues());
    }


    public void visit(OWLDataComplementOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
        addTriple(node, OWL_COMPLEMENT_OF.getURI(), node.getDataRange());
    }


    public void visit(OWLDataIntersectionOf node) {
        translateAnonymousNode(node);
        addTriple(node, OWL_INTERSECTION_OF.getURI(), node.getOperands());
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
    }

    public void visit(OWLDataUnionOf node) {
        translateAnonymousNode(node);
        addTriple(node, OWL_UNION_OF.getURI(), node.getOperands());
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
    }

    public void visit(OWLDatatype node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getURI()));
        }
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
    }


    public void visit(OWLDatatypeRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
        addTriple(node, OWL_ON_DATA_TYPE.getURI(), node.getDatatype());
        addTriple(node, OWL_WITH_RESTRICTIONS.getURI(), node.getFacetRestrictions());
    }


    public void visit(OWLFacetRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, node.getFacet().getIRI().toURI(), node.getFacetValue());
    }

    public void visit(IRI iri) {
        if (!nodeMap.containsKey(iri)) {
            nodeMap.put(iri, getResourceNode(iri.toURI()));
        }
    }

    public void visit(OWLAnnotation node) {
        
    }

    public void visit(OWLTypedLiteral node) {
        nodeMap.put(node, getLiteralNode(node.getLiteral(), node.getDatatype().getURI()));
    }


    public void visit(OWLStringLiteral node) {
        nodeMap.put(node, getLiteralNode(node.getLiteral(), node.getLang()));
    }


    public void visit(OWLDataProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getURI()));
        }
//        addTriple(property, RDF_TYPE.getIRI(), OWL_DATA_PROPERTY.getIRI());
    }


    public void visit(OWLObjectProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getURI()));
        }
//        addTriple(property, RDF_TYPE.getIRI(), OWL_OBJECT_PROPERTY.getIRI());
    }

    public void visit(OWLAnnotationProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getURI()));
        }
//        addTriple(property, RDF_TYPE.getIRI(), OWL_ANNOTATION_PROPERTY.getIRI());
    }

    public void visit(OWLObjectPropertyInverse property) {
        translateAnonymousNode(property);
        addTriple(property, OWL_INVERSE_OF.getURI(), property.getInverse());
        addTriple(property, RDF_TYPE.getURI(), OWL_OBJECT_PROPERTY.getURI());
    }


    public void visit(OWLNamedIndividual individual) {
        if (!nodeMap.containsKey(individual)) {
            nodeMap.put(individual, getResourceNode(individual.getURI()));
        }
        nodeMap.put(individual, getResourceNode(individual.getURI()));

    }

    public void visit(OWLAnonymousIndividual individual) {
        translateAnonymousNode(individual);
    }

    public void visit(OWLOntology ontology) {
        if(ontology.isAnonymous()) {
            translateAnonymousNode(ontology);
        }
        else {
            if (!nodeMap.containsKey(ontology)) {
                nodeMap.put(ontology, getResourceNode(ontology.getOntologyID().getOntologyIRI().toURI()));
            }
        }
        addTriple(ontology, RDF_TYPE.getURI(), OWL_ONTOLOGY.getURI());
    }


    public void visit(SWRLRule rule) {
        if (!rule.isAnonymous()) {
            if (!nodeMap.containsKey(rule)) {
                nodeMap.put(rule, getResourceNode(rule.getURI()));
            }
        } else {
            translateAnonymousNode(rule);
        }
        addTriple(rule, RDF_TYPE.getURI(), IMP.getURI());

        Set<SWRLAtom> antecedent = rule.getBody();
        addTriple(rule, BODY.getURI(), antecedent, ATOM_LIST.getURI());
        Set<SWRLAtom> consequent = rule.getHead();
        addTriple(rule, HEAD.getURI(), consequent, ATOM_LIST.getURI());
    }


    public void visit(SWRLClassAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), CLASS_ATOM.getURI());
        node.getPredicate().accept(this);
        addTriple(node, CLASS_PREDICATE.getURI(), node.getPredicate());
        node.getArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getURI(), node.getArgument());
    }


    public void visit(SWRLDataRangeAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), DATA_RANGE_ATOM.getURI());
        node.getPredicate().accept(this);
        addTriple(node, DATA_RANGE.getURI(), node.getPredicate());
        node.getArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getURI(), node.getArgument());
    }


    public void visit(SWRLObjectPropertyAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), INDIVIDUAL_PROPERTY_ATOM.getURI());
        node.getPredicate().accept(this);
        addTriple(node, PROPERTY_PREDICATE.getURI(), node.getPredicate());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getURI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getURI(), node.getSecondArgument());
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), DATAVALUED_PROPERTY_ATOM.getURI());
        node.getPredicate().accept(this);
        addTriple(node, PROPERTY_PREDICATE.getURI(), node.getPredicate());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getURI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getURI(), node.getSecondArgument());
    }


    public void visit(SWRLBuiltInAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), BUILT_IN_ATOM.getURI());
        addTriple(node, BUILT_IN.getURI(), node.getPredicate().getURI());
        addTriple(getResourceNode(node.getPredicate().getURI()),
                getPredicateNode(BUILT_IN_CLASS.getURI()),
                getResourceNode(BUILT_IN_CLASS.getURI()));
        addTriple(getResourceNode(node),
                getPredicateNode(ARGUMENTS.getURI()),
                translateList(new ArrayList<OWLObject>(node.getArguments())));
    }


    public void visit(SWRLDifferentFromAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), DIFFERENT_INDIVIDUALS_ATOM.getURI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getURI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getURI(), node.getSecondArgument());
    }


    public void visit(SWRLSameAsAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), SAME_INDIVIDUAL_ATOM.getURI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getURI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getURI(), node.getSecondArgument());
    }


    public void visit(SWRLAtomDVariable node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getURI()));
        }
        addTriple(node, RDF_TYPE.getURI(), VARIABLE.getURI());
    }


    public void visit(SWRLAtomIVariable node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getURI()));
        }
        addTriple(node, RDF_TYPE.getURI(), VARIABLE.getURI());
    }


    public void visit(SWRLAtomIndividualObject node) {
        node.getIndividual().accept(this);
        nodeMap.put(node, nodeMap.get(node));
    }


    public void visit(SWRLAtomConstantObject node) {
        node.getConstant().accept(this);
        nodeMap.put(node, nodeMap.get(node.getConstant()));
    }


    private void processAnonymousIndividual(OWLIndividual ind) {
        if (!ind.isAnonymous()) {
            return;
        }
        for (OWLAxiom ax : ontology.getAxioms(ind)) {
            ax.accept(this);
        }
//        for (OWLAxiom ax : ontology.getAnnotationAssertionAxioms(ind)) {
//            ax.accept(this);
//        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods to add triples
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Maps Objects to nodes
     */
    private Map<Object, NODE> nodeMap = new IdentityHashMap<Object, NODE>();


    private void addAxiom(OWLAxiom ax, OWLObject subject, URI pred, OWLObject obj) {
        addAxiom(ax, getResourceNode(subject), getPredicateNode(pred), getNode(obj));
    }


    private void addAxiom(OWLAxiom ax, OWLObject subject, URI pred, URI obj) {
        addAxiom(ax, getResourceNode(subject), getPredicateNode(pred), getResourceNode(obj));
    }


    private void addAxiom(OWLAxiom ax, OWLObject subj, URI pred, Set<? extends OWLObject> obj) {
        addAxiom(ax, getResourceNode(subj), getPredicateNode(pred), translateList(new ArrayList<OWLObject>(obj)));
    }


    private void addAxiom(OWLAxiom ax, OWLObject subj, OWLObject pred, OWLObject obj) {
        addAxiom(ax, getResourceNode(subj), getPredicateNode(pred), getNode(obj));
    }


    private void addAxiom(OWLAxiom ax, RESOURCE subj, PREDICATE pred, NODE obj) {
        // Base triple
        addTriple(subj, pred, obj);
        if(ax.getAnnotations().isEmpty()) {
            return;
        }
        // The axiom has annotations and we therefore need to reify the axiom in order to add the annotations
        translateAnonymousNode(ax);
        addTriple(getResourceNode(ax), getPredicateNode(RDF_TYPE.getURI()), getResourceNode(OWL_AXIOM.getURI()));
        addTriple(getResourceNode(ax), getPredicateNode(OWL_SUBJECT.getURI()), subj);
        addTriple(getResourceNode(ax), getPredicateNode(OWL_PREDICATE.getURI()), pred);
        addTriple(getResourceNode(ax), getPredicateNode(OWL_OBJECT.getURI()), obj);
        translateAnnotations(ax);
    }

    private void translateAnnotations(OWLAxiom ax) {
        translateAnonymousNode(ax);
        for(OWLAnnotation anno : ax.getAnnotations()) {
            translateAnnotation(ax, anno);
        }
    }

    /**
     * Translates an annotation on a given subject.  This method implements the TANN(ann, y) translation
     * in the spec
     * @param subject The subject of the annotation
     * @param annotation The annotation
     */
    private void translateAnnotation(OWLObject subject, OWLAnnotation annotation) {
        // We first add the base triple
        addTriple(subject, annotation.getProperty().getURI(), annotation.getValue());
        // If the annotation doesn't have annotations on it then we're done
        if(annotation.getAnnotations().isEmpty()) {
            return;
        }
        // The annotation has annotations on it so we need to reify the annotation
        // The main node is the annotation, it is typed as an annotation
        translateAnonymousNode(annotation);
        addTriple(annotation, RDF_TYPE.getURI(), OWL_ANNOTATION.getURI());
        if (isAnonymous(subject)) {
            translateAnonymousNode(subject);
        }
        addTriple(annotation, OWL_SUBJECT.getURI(), subject);
        addTriple(annotation, OWL_PREDICATE.getURI(), annotation.getProperty().getURI());
        addTriple(annotation, OWL_OBJECT.getURI(), annotation.getValue());
        for(OWLAnnotation anno : annotation.getAnnotations()) {
            translateAnnotation(annotation, anno);
        }
    }

    private void translateAnonymousNode(OWLObject object) {
        nodeMap.put(object, getAnonymousNode(object));
    }


    /**
     * Gets a resource that has a URI
     *
     * @param uri The URI of the resource
     */
    protected abstract RESOURCE getResourceNode(URI uri);


    protected abstract PREDICATE getPredicateNode(URI uri);


    /**
     * Gets an anonymous resource.
     *
     * @param key A key for the resource.  For a given key identity, the resources
     *            that are returned should be equal and have the same hashcode.
     */
    protected abstract RESOURCE getAnonymousNode(Object key);


    /**
     * Gets a literal node that represents a typed literal.
     *
     * @param literal  The literal
     * @param datatype The datatype that types the literal
     */
    protected abstract LITERAL getLiteralNode(String literal, URI datatype);


    protected abstract LITERAL getLiteralNode(String literal, String lang);


    protected abstract void addTriple(RESOURCE subject, PREDICATE pred, NODE object);


    private RESOURCE getResourceNode(OWLObject object) {
        RESOURCE r = (RESOURCE) nodeMap.get(object);
        if (r == null) {
            object.accept(this);
            r = (RESOURCE) nodeMap.get(object);
            if (r == null) {
                throw new IllegalStateException("Node is null!");
            }
        }
        return r;
    }


    private PREDICATE getPredicateNode(OWLObject object) {
        PREDICATE p = (PREDICATE) nodeMap.get(object);
        if (p == null) {
            object.accept(this);
            p = (PREDICATE) nodeMap.get(object);
            if (p == null) {
                throw new IllegalStateException("Node is null!");
            }
        }
        return p;
    }


    private NODE getNode(OWLObject obj) {
        NODE node = nodeMap.get(obj);
        if (node == null) {
            obj.accept(this);
            node = nodeMap.get(obj);
            if (node == null) {
                throw new IllegalStateException("Node is null. Attempting to get node for " + obj);
            }
        }
        return node;
    }


    private RESOURCE translateList(List<? extends OWLObject> list) {
        return translateList(list, RDF_LIST.getURI());
    }

    private RESOURCE translateList(List<? extends OWLObject> list, URI listType) {
        if (list.isEmpty()) {
            return getResourceNode(RDF_NIL.getURI());
        }
        RESOURCE main = getAnonymousNode(list);
        addTriple(main, getPredicateNode(RDF_TYPE.getURI()), getResourceNode(listType));
        addTriple(main, getPredicateNode(RDF_FIRST.getURI()), getNode(list.get(0)));
        addTriple(main, getPredicateNode(RDF_REST.getURI()), translateList(list.subList(1, list.size()), listType));
        return main;
    }


    private void addTriple(OWLObject subject, URI pred, URI object) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), getResourceNode(object));
    }


    private void addTriple(URI subj, URI pred, URI obj) {
        addTriple(getResourceNode(subj), getPredicateNode(pred), getResourceNode(obj));
    }


    private void addTriple(OWLObject subject, URI pred, OWLObject object) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), getNode(object));
    }


    private void addTriple(OWLObject subject, URI pred, Set<? extends OWLObject> objects) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), translateList(new ArrayList<OWLObject>(objects)));
    }

    private void addTriple(OWLObject subject, URI pred, Set<? extends OWLObject> objects, URI listType) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), translateList(new ArrayList<OWLObject>(objects), listType));
    }

    private OWLTypedLiteral toTypedConstant(int i) {
        return manager.getOWLDataFactory().getOWLTypedLiteral(Integer.toString(i),
                manager.getOWLDataFactory().getOWLDatatype(XSDVocabulary.NON_NEGATIVE_INTEGER.getURI()));
    }

    private boolean isAnonymous(OWLObject object) {
        return !(object instanceof OWLEntity || object instanceof IRI);
    }

    private boolean isObjectPunned(OWLObject entity) {
        punningChecker.reset();
        entity.accept(punningChecker);
        return punningChecker.isPunned();
    }


    private boolean isPunned(OWLObject... objects) {
        for (OWLObject obj : objects) {
            if (!isObjectPunned(obj)) {
                return false;
            }
        }
        return true;
    }


    private boolean isPunned(Set<? extends OWLObject> objects) {
        for (OWLObject obj : objects) {
            if (!isPunned(obj)) {
                return false;
            }
        }
        return true;
    }


    private PunningChecker punningChecker = new PunningChecker();


    private class PunningChecker extends OWLObjectVisitorAdapter {

        private boolean punned;


        public void reset() {
            punned = false;
        }


        public boolean isPunned() {
            return punned;
        }


        public void visit(OWLClass desc) {
            punned = ontology.isPunned(desc.getURI());
        }


        public void visit(OWLDataProperty property) {
            punned = ontology.containsObjectPropertyReference(property.getURI());
//            punned = ontology.isPunned(property.getIRI());
        }


        public void visit(OWLObjectProperty property) {
            punned = ontology.containsDataPropertyReference(property.getURI());
//            punned = ontology.isPunned(property.getIRI());
        }


        public void visit(OWLObjectPropertyInverse property) {
            property.getInverse().accept(this);
        }


        public void visit(OWLDatatype node) {
            punned = ontology.isPunned(node.getURI());
        }


        public void visit(OWLNamedIndividual individual) {
            if (individual.isAnonymous()) {
                punned = false;
            } else {
                punned = ontology.isPunned(individual.getURI());
            }
        }

    }


    private class ClassExpressionComparator implements Comparator<OWLClassExpression> {

        public int compare(OWLClassExpression o1, OWLClassExpression o2) {
            if (!o1.isAnonymous()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
