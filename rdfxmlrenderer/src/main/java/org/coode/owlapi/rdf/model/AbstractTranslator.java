package org.coode.owlapi.rdf.model;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.*;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

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
 * An abstract translator that can produce an RDF graph from an OWLOntology.  Subclasses must
 * provide implementations to create concrete representations of resources, triples etc.
 */
public abstract class AbstractTranslator<NODE, RESOURCE extends NODE, PREDICATE extends NODE, LITERAL extends NODE> implements OWLObjectVisitor, SWRLObjectVisitor {

    private OWLOntologyManager manager;

    private OWLOntology ontology;

    private boolean useStrongTyping = true;


    public AbstractTranslator(OWLOntologyManager manager, OWLOntology ontology, boolean useStrongTyping) {
        this.ontology = ontology;
        this.manager = manager;
        this.useStrongTyping = useStrongTyping;
    }


    public void visit(OWLDeclarationAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getEntity(), RDF_TYPE.getURI(), axiom.getEntity().accept(OWLEntityTypeProvider.INSTANCE));
    }


    public void visit(OWLObjectInverseOf property) {
        translateAnonymousNode(property);
        addTriple(property, OWL_INVERSE_OF.getURI(), property.getInverse());
    }

    public void visit(OWLDataIntersectionOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
        addListTriples(node, OWL_INTERSECTION_OF.getURI(), node.getOperands());

    }

    public void visit(OWLDataUnionOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
        addListTriples(node, OWL_UNION_OF.getURI(), node.getOperands());
    }

    public void visit(OWLDataComplementOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
        addTriple(node, OWL_COMPLEMENT_OF.getURI(), node.getDataRange());
    }

    public void visit(OWLDataOneOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
        addListTriples(node, OWL_ONE_OF.getURI(), node.getValues());
    }

    public void visit(OWLDatatypeRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
        addTriple(node, OWL_ON_DATA_TYPE.getURI(), node.getDatatype());
        addListTriples(node, OWL_WITH_RESTRICTIONS.getURI(), node.getFacetRestrictions());
    }


    public void visit(OWLObjectIntersectionOf desc) {
        translateAnonymousNode(desc);
        addListTriples(desc, OWL_INTERSECTION_OF.getURI(), desc.getOperands());
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
    }


    public void visit(OWLObjectUnionOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
        addListTriples(desc, OWL_UNION_OF.getURI(), desc.getOperands());
    }

    public void visit(OWLObjectComplementOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
        addTriple(desc, OWL_COMPLEMENT_OF.getURI(), desc.getOperand());
    }


    public void visit(OWLObjectOneOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getURI(), OWL_CLASS.getURI());
        addListTriples(desc, OWL_ONE_OF.getURI(), desc.getIndividuals());
        processIfAnonymous(desc.getIndividuals(), null);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Translation of restrictions
    ////
    ////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * Add type triples and the owl:onProperty triples for an OWLRestriction
     * @param desc The restriction
     */
    private void addRestrictionCommonTriples(OWLRestriction desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getURI(), OWL_RESTRICTION.getURI());
        addTriple(desc, OWL_ON_PROPERTY.getURI(), desc.getProperty());
    }


    private void addCardinalityRestrictionTriples(OWLCardinalityRestriction ce, OWLRDFVocabulary cardiPredicate) {
        addRestrictionCommonTriples(ce);
        addTriple(ce, cardiPredicate.getURI(), toTypedConstant(ce.getCardinality()));
        if (ce.isQualified()) {
            if (ce.isObjectRestriction()) {
                addTriple(ce, OWL_ON_CLASS.getURI(), ce.getFiller());
            }
            else {
                addTriple(ce, OWL_ON_DATA_RANGE.getURI(), ce.getFiller());
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////

    public void visit(OWLObjectSomeValuesFrom desc) {
        addRestrictionCommonTriples(desc);
        addTriple(desc, OWL_SOME_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        addRestrictionCommonTriples(desc);
        addTriple(desc, OWL_ALL_VALUES_FROM.getURI(), desc.getFiller());
    }


    public void visit(OWLObjectHasValue desc) {
        addRestrictionCommonTriples(desc);
        addTriple(desc, OWL_HAS_VALUE.getURI(), desc.getValue());
        processIfAnonymous(desc.getValue(), null);
    }

    public void visit(OWLObjectHasSelf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getURI(), OWL_RESTRICTION.getURI());
        addTriple(desc, OWL_ON_PROPERTY.getURI(), desc.getProperty());
        addTriple(desc, OWL_HAS_SELF.getURI(), manager.getOWLDataFactory().getOWLTypedLiteral(true));
    }


    public void visit(OWLObjectMinCardinality desc) {
        if (desc.isQualified()) {
            addCardinalityRestrictionTriples(desc, OWL_MIN_QUALIFIED_CARDINALITY);
        }
        else {
            addCardinalityRestrictionTriples(desc, OWL_MIN_CARDINALITY);
        }
    }


    public void visit(OWLObjectMaxCardinality desc) {
        if (desc.isQualified()) {
            addCardinalityRestrictionTriples(desc, OWL_MAX_QUALIFIED_CARDINALITY);
        }
        else {
            addCardinalityRestrictionTriples(desc, OWL_MAX_CARDINALITY);
        }
    }

    public void visit(OWLObjectExactCardinality desc) {
        if (desc.isQualified()) {
            addCardinalityRestrictionTriples(desc, OWL_QUALIFIED_CARDINALITY);
        }
        else {
            addCardinalityRestrictionTriples(desc, OWL_CARDINALITY);
        }
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        addRestrictionCommonTriples(desc);
        addTriple(desc, OWL_SOME_VALUES_FROM.getURI(), desc.getFiller());
    }

    public void visit(OWLDataAllValuesFrom desc) {
        addRestrictionCommonTriples(desc);
        addTriple(desc, OWL_ALL_VALUES_FROM.getURI(), desc.getFiller());
    }

    public void visit(OWLDataHasValue desc) {
        addRestrictionCommonTriples(desc);
        addTriple(desc, OWL_HAS_VALUE.getURI(), desc.getValue());
    }

    public void visit(OWLDataMinCardinality desc) {
        if (desc.isQualified()) {
            addCardinalityRestrictionTriples(desc, OWL_MIN_QUALIFIED_CARDINALITY);
        }
        else {
            addCardinalityRestrictionTriples(desc, OWL_MIN_CARDINALITY);
        }
    }

    public void visit(OWLDataMaxCardinality desc) {
        if (desc.isQualified()) {
            addCardinalityRestrictionTriples(desc, OWL_MAX_QUALIFIED_CARDINALITY);
        }
        else {
            addCardinalityRestrictionTriples(desc, OWL_MAX_CARDINALITY);
        }

    }

    public void visit(OWLDataExactCardinality desc) {
        if (desc.isQualified()) {
            addCardinalityRestrictionTriples(desc, OWL_QUALIFIED_CARDINALITY);
        }
        else {
            addCardinalityRestrictionTriples(desc, OWL_CARDINALITY);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    ////
    ////  Axioms
    ////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLSubClassOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubClass(), RDFS_SUBCLASS_OF.getURI(), axiom.getSuperClass());
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
        addPairwiseClassExpressions(axiom, axiom.getClassExpressions(), OWL_EQUIVALENT_CLASS.getURI());
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() == 2) {
            addPairwiseClassExpressions(axiom, axiom.getClassExpressions(), OWL_DISJOINT_WITH.getURI());
        }
        else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_CLASSES.getURI());
            addListTriples(axiom, OWL_MEMBERS.getURI(), axiom.getClassExpressions());
        }
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getOWLClass(), OWL_DISJOINT_UNION_OF.getURI(), axiom.getClassExpressions());
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getURI(), axiom.getSuperProperty());
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSuperProperty(), OWL_PROPERTY_CHAIN_AXIOM.getURI(), axiom.getPropertyChain());
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        addPairwise(axiom, axiom.getProperties(), OWL_EQUIVALENT_PROPERTY.getURI());
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_PROPERTY_DISJOINT_WITH.getURI());
        }
        else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_PROPERTIES.getURI());
            addListTriples(axiom, OWL_MEMBERS.getURI(), axiom.getProperties());
        }
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getURI(), axiom.getDomain());
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getURI(), axiom.getRange());
    }

    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getFirstProperty(), OWL_INVERSE_OF.getURI(), axiom.getSecondProperty());
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_FUNCTIONAL_PROPERTY.getURI());
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_INVERSE_FUNCTIONAL_PROPERTY.getURI());
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_REFLEXIVE_PROPERTY.getURI());
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_IRREFLEXIVE_PROPERTY.getURI());
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_SYMMETRIC_PROPERTY.getURI());
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_ASYMMETRIC_PROPERTY.getURI());
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_TRANSITIVE_PROPERTY.getURI());
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getURI(), axiom.getSuperProperty());
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        addPairwise(axiom, axiom.getProperties(), OWL_EQUIVALENT_PROPERTY.getURI());
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_PROPERTY_DISJOINT_WITH.getURI());
        }
        else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DISJOINT_PROPERTIES.getURI());
            addListTriples(axiom, OWL_MEMBERS.getURI(), axiom.getProperties());
        }
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getURI(), axiom.getDomain());
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getURI(), axiom.getRange());
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getURI(), OWL_FUNCTIONAL_PROPERTY.getURI());
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getDatatype(), OWL_EQUIVALENT_CLASS.getURI(), axiom.getDataRange());
    }

    public void visit(OWLHasKeyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getClassExpression(), OWL_HAS_KEY.getURI(), axiom.getPropertyExpressions());
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        addPairwise(axiom, axiom.getIndividuals(), OWL_SAME_AS.getURI());
        processIfAnonymous(axiom.getIndividuals(), axiom);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getURI(), OWL_ALL_DIFFERENT.getURI());
        addListTriples(axiom, OWL_DISTINCT_MEMBERS.getURI(), axiom.getIndividuals());
        processIfAnonymous(axiom.getIndividuals(), axiom);
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        addSingleTripleAxiom(axiom, axiom.getIndividual(), RDF_TYPE.getURI(), axiom.getClassExpression());
        processIfAnonymous(axiom.getIndividual(), axiom);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        OWLObjectPropertyAssertionAxiom simplified = axiom.getSimplified();
        addSingleTripleAxiom(simplified, simplified.getSubject(), simplified.getProperty(), simplified.getObject());
        processIfAnonymous(simplified.getObject(), axiom);
        processIfAnonymous(simplified.getSubject(), axiom);
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getURI(), OWL_NEGATIVE_PROPERTY_ASSERTION.getURI());
        addTriple(axiom, OWL_SOURCE_INDIVIDUAL.getURI(), axiom.getSubject());
        addTriple(axiom, OWL_ASSERTION_PROPERTY.getURI(), axiom.getProperty());
        addTriple(axiom, OWL_TARGET_INDIVIDUAL.getURI(), axiom.getObject());
        translateAnnotations(axiom);
        processIfAnonymous(axiom.getSubject(), axiom);
        processIfAnonymous(axiom.getObject(), axiom);
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getURI(), OWL_NEGATIVE_PROPERTY_ASSERTION.getURI());
        addTriple(axiom, OWL_SOURCE_INDIVIDUAL.getURI(), axiom.getSubject());
        addTriple(axiom, OWL_ASSERTION_PROPERTY.getURI(), axiom.getProperty());
        addTriple(axiom, OWL_TARGET_VALUE.getURI(), axiom.getObject());
        processIfAnonymous(axiom.getSubject(), axiom);
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getValue());
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getURI(), axiom.getSuperProperty());
    }


    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getURI(), axiom.getDomain());
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getURI(), axiom.getRange());
    }

    public void visit(OWLClass desc) {
        if (!nodeMap.containsKey(desc)) {
            nodeMap.put(desc, getResourceNode(desc.getURI()));
        }
        addStrongTyping(desc);
    }


    public void visit(OWLDatatype node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getURI()));
        }
        addTriple(node, RDF_TYPE.getURI(), RDFS_DATATYPE.getURI());
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
        addStrongTyping(property);
    }


    public void visit(OWLObjectProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getURI()));
        }
        addStrongTyping(property);
    }

    public void visit(OWLAnnotationProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getURI()));
        }
        addStrongTyping(property);
    }


    public void visit(OWLNamedIndividual individual) {
        if (!nodeMap.containsKey(individual)) {
            nodeMap.put(individual, getResourceNode(individual.getURI()));
        }
        addStrongTyping(individual);
    }

    private Set<OWLAnonymousIndividual> processing = new HashSet<OWLAnonymousIndividual>();

    public void visit(OWLAnonymousIndividual individual) {
        translateAnonymousNode(individual);
//        if(!processing.contains(individual)) {
//            processing.add(individual);
//            for (OWLAxiom ax : ontology.getAxioms(individual)) {
//                ax.accept(this);
//            }
//            processing.remove(individual);
//        }

    }

    public void visit(OWLOntology ontology) {
        if (ontology.isAnonymous()) {
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
                nodeMap.put(rule, getResourceNode(rule.getIRI()));
            }
        }
        else {
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


    public void visit(SWRLDataPropertyAtom node) {
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
        addTriple(node, BUILT_IN.getURI(), node.getPredicate().toURI());
        addTriple(getResourceNode(node.getPredicate().toURI()), getPredicateNode(BUILT_IN_CLASS.getURI()), getResourceNode(BUILT_IN_CLASS.getURI()));
        addTriple(getResourceNode(node), getPredicateNode(ARGUMENTS.getURI()), translateList(new ArrayList<OWLObject>(node.getArguments())));
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), DIFFERENT_INDIVIDUALS_ATOM.getURI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getURI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getURI(), node.getSecondArgument());
    }


    public void visit(SWRLSameIndividualAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getURI(), SAME_INDIVIDUAL_ATOM.getURI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getURI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getURI(), node.getSecondArgument());
    }


    public void visit(SWRLLiteralVariable node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getIRI()));
        }
        addTriple(node, RDF_TYPE.getURI(), VARIABLE.getURI());
    }


    public void visit(SWRLIndividualVariable node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getIRI()));
        }
        addTriple(node, RDF_TYPE.getURI(), VARIABLE.getURI());
    }


    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
        nodeMap.put(node, nodeMap.get(node));
    }


    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
        nodeMap.put(node, nodeMap.get(node.getLiteral()));
    }


    private void processAnonymousIndividual(OWLIndividual ind) {
        if (!ind.isAnonymous()) {
            return;
        }
        for (OWLAxiom ax : ontology.getAxioms(ind)) {
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


    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subject, URI pred, OWLObject obj) {
        addSingleTripleAxiom(ax, getResourceNode(subject), getPredicateNode(pred), getNode(obj));
    }


    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subject, URI pred, URI obj) {
        addSingleTripleAxiom(ax, getResourceNode(subject), getPredicateNode(pred), getResourceNode(obj));
    }


    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subj, URI pred, Collection<? extends OWLObject> obj) {
        addSingleTripleAxiom(ax, getResourceNode(subj), getPredicateNode(pred), translateList(new ArrayList<OWLObject>(obj)));
    }


    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subj, OWLObject pred, OWLObject obj) {
        addSingleTripleAxiom(ax, getResourceNode(subj), getPredicateNode(pred), getNode(obj));
    }


    /**
     * Adds the representation of an axiom to the RDF graph where the axiom has a SINGLE MAIN TRIPLE
     * (specified by the subject, predicate, object parameters). The triple specified by the subject, predicate and
     * object parameters will be added to the graph.  If the axiom has any annotations on it then extra triples will
     * be added.  These will consist of three triples, that "reify" (not in the RDF sense) the specified triple using
     * the OWL 2 annotation vocabulary: owl:annotatedSource, owl:annotatedProperty, owl:annotatedTarget, and other
     * triples to encode the annotations.
     * @param ax        The axiom that the triple specified as subject, pred, obj represents.
     * @param subject   The subject of the triple representing the axiom
     * @param predicate The predicate of the triple representing the axiom
     * @param object    The object of the triple representing the axiom
     */
    private void addSingleTripleAxiom(OWLAxiom ax, RESOURCE subject, PREDICATE predicate, NODE object) {
        // Base triple
        addTriple(subject, predicate, object);
        if (ax.getAnnotations().isEmpty()) {
            return;
        }
        // The axiom has annotations and we therefore need to reify the axiom in order to add the annotations
        translateAnonymousNode(ax);
        addTriple(getResourceNode(ax), getPredicateNode(RDF_TYPE.getURI()), getResourceNode(OWL_AXIOM.getURI()));
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_SOURCE.getURI()), subject);
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_PROPERTY.getURI()), predicate);
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_TARGET.getURI()), object);
        translateAnnotations(ax);
    }

    private void translateAnnotations(OWLAxiom ax) {
        translateAnonymousNode(ax);
        for (OWLAnnotation anno : ax.getAnnotations()) {
            translateAnnotation(ax, anno);
        }
    }

    /**
     * Translates an annotation on a given subject.  This method implements the TANN(ann, y) translation
     * in the spec
     * @param subject    The subject of the annotation
     * @param annotation The annotation
     */
    private void translateAnnotation(OWLObject subject, OWLAnnotation annotation) {
        // We first add the base triple
        addTriple(subject, annotation.getProperty().getURI(), annotation.getValue());
        // If the annotation doesn't have annotations on it then we're done
        if (annotation.getAnnotations().isEmpty()) {
            return;
        }
        // The annotation has annotations on it so we need to reify the annotation
        // The main node is the annotation, it is typed as an annotation
        translateAnonymousNode(annotation);
        addTriple(annotation, RDF_TYPE.getURI(), OWL_ANNOTATION.getURI());
        addTriple(annotation, OWL_ANNOTATED_SOURCE.getURI(), subject);
        addTriple(annotation, OWL_ANNOTATED_PROPERTY.getURI(), annotation.getProperty().getURI());
        addTriple(annotation, OWL_ANNOTATED_TARGET.getURI(), annotation.getValue());
        for (OWLAnnotation anno : annotation.getAnnotations()) {
            translateAnnotation(annotation, anno);
        }
    }

    public void visit(OWLAnnotation node) {
        throw new OWLRuntimeException("The translator should not be used directly on instances of OWLAnnotation because an annotation cannot be translated without a subject.");
    }

    private void translateAnonymousNode(OWLObject object) {
        nodeMap.put(object, getAnonymousNode(object));
    }


    /**
     * Gets a resource that has a URI
     * @param uri The URI of the resource
     */
    protected abstract RESOURCE getResourceNode(URI uri);


    protected abstract PREDICATE getPredicateNode(URI uri);


    /**
     * Gets an anonymous resource.
     * @param key A key for the resource.  For a given key identity, the resources
     *            that are returned should be equal and have the same hashcode.
     */
    protected abstract RESOURCE getAnonymousNode(Object key);


    /**
     * Gets a literal node that represents a typed literal.
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
        RESOURCE main = getResourceNode(RDF_NIL.getURI());
        int listSize = list.size() - 1;
        for ( int i = listSize; i >= 0; i-- ) {
            RESOURCE anonNode = getAnonymousNode(list.subList(i, listSize));
            addTriple(anonNode, getPredicateNode(RDF_TYPE.getURI()), getResourceNode(listType));
            addTriple(anonNode, getPredicateNode(RDF_FIRST.getURI()), getNode(list.get(i)));
            addTriple(anonNode, getPredicateNode(RDF_REST.getURI()), main);
            main = anonNode;
        }
        return main;

    }


    private void addTriple(OWLObject subject, URI pred, URI object) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), getResourceNode(object));
    }

//    private void addTriple(URI subj, URI pred, URI obj) {
//        addTriple(getResourceNode(subj), getPredicateNode(pred), getResourceNode(obj));
//    }


    private void addTriple(OWLObject subject, URI pred, OWLObject object) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), getNode(object));
    }


    private void addListTriples(OWLObject subject, URI pred, Set<? extends OWLObject> objects) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), translateList(new ArrayList<OWLObject>(objects)));
    }

    private void addTriple(OWLObject subject, URI pred, Set<? extends OWLObject> objects, URI listType) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), translateList(new ArrayList<OWLObject>(objects), listType));
    }

    private OWLTypedLiteral toTypedConstant(int i) {
        return manager.getOWLDataFactory().getOWLTypedLiteral(Integer.toString(i), manager.getOWLDataFactory().getOWLDatatype(XSDVocabulary.NON_NEGATIVE_INTEGER.getURI()));
    }

    private void processIfAnonymous(Set<OWLIndividual> inds, OWLAxiom root) {
        for(OWLIndividual ind : inds) {
            processIfAnonymous(ind, root);
        }
    }

    private void processIfAnonymous(OWLIndividual ind, OWLAxiom root) {
        if(ind.isAnonymous()) {
            for(OWLAxiom ax : ontology.getAxioms(ind)) {
                if (root == null || !root.equals(ax)) {
                    ax.accept(this);
                }
            }
        }
    }

    private boolean isAnonymous(OWLObject object) {
        return !(object instanceof OWLEntity || object instanceof IRI);
    }

    private void addPairwise(OWLAxiom axiom, Collection<? extends OWLObject> objects, URI uri) {
        List<? extends OWLObject> objectList = new ArrayList<OWLObject>(objects);
        for (int i = 0; i < objectList.size(); i++) {
            for (int j = i; j < objectList.size(); j++) {
                if (i != j) {
                    addSingleTripleAxiom(axiom, objectList.get(i), uri, objectList.get(j));
                }
            }
        }
    }


    /**
     * Renders a set of class expressions in a pairwise manner using the specified URI.  It
     * is assumed that the relationship described by the URI (e.g. disjointWith) is
     * symmetric.  The method delegates to the <code>addPairwise</code> method after sorting
     * the class expressions so that named classes appear first.
     * @param axiom            The axiom which will dictate which axiom annotation get rendered
     * @param classExpressions The set of class expressions to be rendered.
     * @param uri              The URI which describes the relationship between pairs of class expressions.
     */
    private void addPairwiseClassExpressions(OWLAxiom axiom, Set<OWLClassExpression> classExpressions, URI uri) {
        List<OWLClassExpression> classExpressionList = new ArrayList<OWLClassExpression>(classExpressions);
        addPairwise(axiom, classExpressionList, uri);

    }


    /**
     * Adds triples to strong type an entity.  Triples are only added if the useStrongTyping flag
     * is set to <code>true</code> and the entity is not a built in entity.
     * @param entity The entity for which strong typing triples should be added.
     */
    private void addStrongTyping(OWLEntity entity) {
        if (!useStrongTyping) {
            return;
        }
        if (entity.isBuiltIn()) {
            return;
        }
        addTriple(entity, RDF_TYPE.getURI(), entity.accept(OWLEntityTypeProvider.INSTANCE));
    }


    /**
     * Visits entities and returns their RDF type
     */
    private static class OWLEntityTypeProvider implements OWLEntityVisitorEx<URI> {

        public static OWLEntityTypeProvider INSTANCE = new OWLEntityTypeProvider();

        public URI visit(OWLClass cls) {
            return OWL_CLASS.getURI();
        }

        public URI visit(OWLObjectProperty property) {
            return OWL_OBJECT_PROPERTY.getURI();
        }

        public URI visit(OWLDataProperty property) {
            return OWL_DATA_PROPERTY.getURI();
        }

        public URI visit(OWLNamedIndividual individual) {
            return OWL_NAMED_INDIVIDUAL.getURI();
        }

        public URI visit(OWLDatatype datatype) {
            return RDFS_DATATYPE.getURI();
        }

        public URI visit(OWLAnnotationProperty property) {
            return OWL_ANNOTATION_PROPERTY.getURI();
        }
    }

}
