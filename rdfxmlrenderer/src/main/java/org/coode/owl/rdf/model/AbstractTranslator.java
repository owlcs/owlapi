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


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_ASYMMETRIC_PROPERTY.getURI());
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
        // Skip - these get added when an axiom gets translated
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        addAxiom(axiom, axiom.getIndividual(), RDF_TYPE.getURI(), axiom.getDescription());
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                isPunned(axiom.getProperty()) ? OWL_DATA_PROPERTY_DOMAIN.getURI() : RDFS_DOMAIN.getURI(),
                axiom.getDomain());
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                isPunned(axiom.getProperty()) ? OWL_DATA_PROPERTY_RANGE.getURI() : RDFS_RANGE.getURI(),
                axiom.getRange());
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        addAxiom(axiom,
                axiom.getSubProperty(),
                isPunned(axiom.getSubProperty(),
                        axiom.getSuperProperty()) ? OWL_SUB_DATA_PROPERTY_OF.getURI() : RDFS_SUB_PROPERTY_OF.getURI(),
                axiom.getSuperProperty());
    }


    public void visit(OWLDeclarationAxiom axiom) {
        if (axiom.getEntity() instanceof OWLClass) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_CLASS.getURI());
        } else if (axiom.getEntity() instanceof OWLDataProperty) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_DATA_PROPERTY.getURI());
        } else if (axiom.getEntity() instanceof OWLObjectProperty) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getURI());
        } else if (axiom.getEntity() instanceof OWLIndividual) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_INDIVIDUAL.getURI());
        } else if (axiom.getEntity() instanceof OWLDatatype) {
            addAxiom(axiom,
                    axiom.getEntity(),
                    OWLRDFVocabulary.RDF_TYPE.getURI(),
                    OWLRDFVocabulary.OWL_DATATYPE.getURI());
        }
        // Annotations
        for (OWLAnnotationAxiom ax : ontology.getEntityAnnotationAxioms(axiom.getEntity())) {
            addTriple(axiom.getEntity(),
                    ax.getAnnotation().getAnnotationURI(),
                    ax.getAnnotation().getAnnotationValue());
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


    private DescriptionComparator descriptionComparator = new DescriptionComparator();


    /**
     * Renders a set of descriptions in a pairwise manner using the specified URI.  It
     * is assumed that the relationship described by the URI (e.g. disjointWith) is
     * symmetric.  The method delegates to the <code>addPairwise</code> method after sorting
     * the descriptions so that named classes appear first.
     *
     * @param axiom            The axiom which will dictate which axiom annotation get rendered
     * @param classExpressions The set of descriptions to be rendered.
     * @param uri              The URI which describes the relationship between pairs of descriptions.
     */
    private void addPairwiseDescriptions(OWLAxiom axiom, Set<OWLClassExpression> classExpressions, URI uri) {
        List<OWLClassExpression> classExpressionList = new ArrayList<OWLClassExpression>(classExpressions);
        Collections.sort(classExpressionList, descriptionComparator);
        addPairwise(axiom, classExpressionList, uri);

    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DIFFERENT.getURI());
        addTriple(axiom, OWL_DISTINCT_MEMBERS.getURI(), axiom.getIndividuals());
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        if (axiom.getDescriptions().size() == 2 || !ontology.getAnnotations(axiom).isEmpty()) {
            addPairwiseDescriptions(axiom, axiom.getDescriptions(), OWL_DISJOINT_WITH.getURI());
        } else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_CLASSES.getURI());
            addTriple(axiom, OWL_MEMBERS.getURI(), axiom.getDescriptions());
        }
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2 || !ontology.getAnnotations(axiom).isEmpty()) {
            addPairwise(axiom, axiom.getProperties(), OWL_DISJOINT_DATA_PROPERTIES.getURI());
        } else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_PROPERTIES.getURI());
            addTriple(axiom, OWL_MEMBERS.getURI(), axiom.getProperties());
        }
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2 || !ontology.getAnnotations(axiom).isEmpty()) {
            addPairwise(axiom, axiom.getProperties(), OWL_DISJOINT_OBJECT_PROPERTIES.getURI());
        } else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_PROPERTIES.getURI());
            addTriple(axiom, OWL_MEMBERS.getURI(), axiom.getProperties());
        }
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        addAxiom(axiom, axiom.getOWLClass(), OWL_DISJOINT_UNION_OF.getURI(), axiom.getDescriptions());
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        addTriple(axiom.getSubject(),
                axiom.getAnnotation().getAnnotationURI(),
                axiom.getAnnotation().getAnnotationValue());
        addTriple(getResourceNode(axiom.getAnnotation().getAnnotationURI()),
                getPredicateNode(RDF_TYPE.getURI()),
                getResourceNode(OWL_ANNOTATION_PROPERTY.getURI()));
        axiom.getSubject().accept(this);
        if (!axiom.getAnnotation().isAnnotationByConstant()) {
            OWLIndividual ind = (OWLIndividual) axiom.getAnnotation().getAnnotationValue();
            processAnonymousIndividual(ind);
        }
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        addPairwiseDescriptions(axiom, axiom.getDescriptions(), OWL_EQUIVALENT_CLASS.getURI());
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
        addTriple(axiom.getSubject(), OWL_IMPORTS.getURI(), axiom.getImportedOntologyURI());
        addTriple(getResourceNode(axiom.getImportedOntologyURI()),
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


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        RESOURCE anonNode = getAnonymousNode(axiom);
        RESOURCE list = translateList(axiom.getPropertyChain());
        addTriple(anonNode, getPredicateNode(OWL_PROPERTY_CHAIN.getURI()), list);
        addAxiom(axiom,
                anonNode,
                getPredicateNode(RDFS_SUB_PROPERTY_OF.getURI()),
                getResourceNode(axiom.getSuperProperty()));
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                isPunned(axiom.getProperty()) ? OWL_OBJECT_PROPERTY_DOMAIN.getURI() : RDFS_DOMAIN.getURI(),
                axiom.getDomain());
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        addAxiom(axiom,
                axiom.getProperty(),
                isPunned(axiom.getProperty()) ? OWL_OBJECT_PROPERTY_RANGE.getURI() : RDFS_RANGE.getURI(),
                axiom.getRange());
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        addAxiom(axiom,
                axiom.getSubProperty(),
                isPunned(axiom.getSubProperty(),
                        axiom.getSuperProperty()) ? OWL_SUB_OBJECT_PROPERTY_OF.getURI() : RDFS_SUB_PROPERTY_OF.getURI(),
                axiom.getSuperProperty());
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        addAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_REFLEXIVE_PROPERTY.getURI());
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        addPairwise(axiom, axiom.getIndividuals(), OWL_SAME_AS.getURI());
    }


    public void visit(OWLSubClassAxiom axiom) {
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
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
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


    public void visit(OWLDataAllRestriction desc) {
        translateDataRestrictionStart(desc);
        addTriple(desc, OWL_ALL_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        translateDataCardinality(desc, OWL_CARDINALITY.getURI());
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
        translateDataCardinality(desc, OWL_MAX_CARDINALITY.getURI());
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        translateDataCardinality(desc, OWL_MIN_CARDINALITY.getURI());
    }


    public void visit(OWLDataSomeRestriction desc) {
        translateDataRestrictionStart(desc);
        addTriple(desc, OWL_SOME_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLDataValueRestriction desc) {
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


    public void visit(OWLObjectAllRestriction desc) {
        translateObjectRestrictionStart(desc);
        addTriple(desc, OWL_ALL_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        translateObjectCardinality(desc, OWL_CARDINALITY.getURI());
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        translateObjectCardinality(desc, OWL_MAX_CARDINALITY.getURI());
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        translateObjectCardinality(desc, OWL_MIN_CARDINALITY.getURI());
    }


    public void visit(OWLObjectOneOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, OWL_ONE_OF.getURI(), desc.getIndividuals());
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
    }


    public void visit(OWLObjectSelfRestriction desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getURI(), OWL_SELF_RESTRICTION.getURI());
        addTriple(desc, OWL_ON_PROPERTY.getURI(), desc.getProperty());
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        translateObjectRestrictionStart(desc);
        addTriple(desc, OWL_SOME_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLObjectValueRestriction desc) {
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
        addTriple(node, RDF_TYPE.getURI(), OWL_DATA_RANGE.getURI());
        addTriple(node, OWL_ONE_OF.getURI(), node.getValues());
    }


    public void visit(OWLDataComplementOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), OWL_DATA_RANGE.getURI());
        addTriple(node, OWL_COMPLEMENT_OF.getURI(), node.getDataRange());
    }


    public void visit(OWLDatatype node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getURI()));
        }
        addTriple(node, RDF_TYPE.getURI(), OWL_DATATYPE.getURI());
    }


    public void visit(OWLDataRangeRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), OWL_DATA_RANGE.getURI());
        addTriple(node, OWL_ON_DATA_RANGE.getURI(), node.getDataRange());
        for (OWLDataRangeFacetRestriction restriction : node.getFacetRestrictions()) {
            translateAnonymousNode(node);
            addTriple(node, restriction.getFacet().getURI(), restriction.getFacetValue());
        }
    }


    public void visit(OWLDataRangeFacetRestriction node) {

    }


    public void visit(OWLTypedLiteral node) {
        nodeMap.put(node, getLiteralNode(node.getString(), node.getDataType().getURI()));
    }


    public void visit(OWLRDFTextLiteral node) {
        nodeMap.put(node, getLiteralNode(node.getString(), node.getLang()));
    }


    public void visit(OWLDataProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getURI()));
        }
        addTriple(property, RDF_TYPE.getURI(), OWL_DATA_PROPERTY.getURI());
    }


    public void visit(OWLObjectProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getURI()));
        }
        addTriple(property, RDF_TYPE.getURI(), OWL_OBJECT_PROPERTY.getURI());
    }


    public void visit(OWLObjectPropertyInverse property) {
        translateAnonymousNode(property);
        addTriple(property, OWL_INVERSE_OF.getURI(), property.getInverse());
        addTriple(property, RDF_TYPE.getURI(), OWL_OBJECT_PROPERTY.getURI());
    }


    public void visit(OWLIndividual individual) {
        if (!individual.isAnonymous()) {
            if (!nodeMap.containsKey(individual)) {
                nodeMap.put(individual, getResourceNode(individual.getURI()));
            }
            nodeMap.put(individual, getResourceNode(individual.getURI()));
        } else {
            translateAnonymousNode(individual);
        }
    }


    public void visit(OWLConstantAnnotation annotation) {
        // Gets added in line with axioms
    }


    public void visit(OWLObjectAnnotation annotation) {
        // Gets added in line with axioms
    }


    public void visit(OWLOntology ontology) {
        if (!nodeMap.containsKey(ontology)) {
            nodeMap.put(ontology, getResourceNode(ontology.getURI()));
        }
        addTriple(ontology, RDF_TYPE.getURI(), OWL_ONTOLOGY.getURI());
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
        axiom.getSubject().accept(this);
        addTriple(axiom.getSubject(),
                axiom.getAnnotation().getAnnotationURI(),
                axiom.getAnnotation().getAnnotationValue());
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
        for (OWLAxiom ax : ontology.getEntityAnnotationAxioms(ind)) {
            ax.accept(this);
        }

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
        // If the axiom has any annotations, we have to reify it - bloody RDF!
        if (ontology.getAnnotations(ax).isEmpty() && !(ax instanceof OWLNegativeObjectPropertyAssertionAxiom) && !(ax instanceof OWLNegativeDataPropertyAssertionAxiom)) {
            // Just do a straight translation
            addTriple(subj, pred, obj);
        } else {
            // Reify!
            translateAnonymousNode(ax);
            addTriple(getResourceNode(ax), getPredicateNode(RDF_SUBJECT.getURI()), subj);
            addTriple(getResourceNode(ax), getPredicateNode(RDF_PREDICATE.getURI()), pred);
            addTriple(getResourceNode(ax), getPredicateNode(RDF_OBJECT.getURI()), obj);
            // Now add the annos
            for (OWLAnnotationAxiom annoAx : ax.getAnnotationAxioms(ontology)) {
                addTriple(getResourceNode(annoAx.getAnnotation().getAnnotationURI()),
                        getPredicateNode(RDF_TYPE.getURI()),
                        getResourceNode(OWL_ANNOTATION_PROPERTY.getURI()));
                addTriple(ax, annoAx.getAnnotation().getAnnotationURI(), annoAx.getAnnotation().getAnnotationValue());
                addTriple(ax, RDF_TYPE.getURI(), OWL_AXIOM.getURI());
            }
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
//            punned = ontology.isPunned(property.getURI());
        }


        public void visit(OWLObjectProperty property) {
            punned = ontology.containsDataPropertyReference(property.getURI());
//            punned = ontology.isPunned(property.getURI());
        }


        public void visit(OWLObjectPropertyInverse property) {
            property.getInverse().accept(this);
        }


        public void visit(OWLDatatype node) {
            punned = ontology.isPunned(node.getURI());
        }


        public void visit(OWLIndividual individual) {
            if (individual.isAnonymous()) {
                punned = false;
            } else {
                punned = ontology.isPunned(individual.getURI());
            }
        }

    }


    private class DescriptionComparator implements Comparator<OWLClassExpression> {

        public int compare(OWLClassExpression o1, OWLClassExpression o2) {
            if (!o1.isAnonymous()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
