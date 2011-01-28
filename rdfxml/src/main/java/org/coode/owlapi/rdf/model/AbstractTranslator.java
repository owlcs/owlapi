package org.coode.owlapi.rdf.model;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;


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
        addSingleTripleAxiom(axiom, axiom.getEntity(), RDF_TYPE.getIRI(), axiom.getEntity().accept(OWLEntityTypeProvider.INSTANCE));
    }


    public void visit(OWLObjectInverseOf property) {
        translateAnonymousNode(property);
        addTriple(property, OWL_INVERSE_OF.getIRI(), property.getInverse());
    }

    public void visit(OWLDataIntersectionOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_INTERSECTION_OF.getIRI(), node.getOperands());

    }

    public void visit(OWLDataUnionOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_UNION_OF.getIRI(), node.getOperands());
    }

    public void visit(OWLDataComplementOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addTriple(node, OWL_DATATYPE_COMPLEMENT_OF.getIRI(), node.getDataRange());
    }

    public void visit(OWLDataOneOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_ONE_OF.getIRI(), node.getValues());
    }

    public void visit(OWLDatatypeRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addTriple(node, OWL_ON_DATA_TYPE.getIRI(), node.getDatatype());
        addListTriples(node, OWL_WITH_RESTRICTIONS.getIRI(), node.getFacetRestrictions());
    }


    public void visit(OWLObjectIntersectionOf desc) {
        translateAnonymousNode(desc);
        addListTriples(desc, OWL_INTERSECTION_OF.getIRI(), desc.getOperands());
        addTriple(desc, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
    }


    public void visit(OWLObjectUnionOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addListTriples(desc, OWL_UNION_OF.getIRI(), desc.getOperands());
    }

    public void visit(OWLObjectComplementOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addTriple(desc, OWL_COMPLEMENT_OF.getIRI(), desc.getOperand());
    }


    public void visit(OWLObjectOneOf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addListTriples(desc, OWL_ONE_OF.getIRI(), desc.getIndividuals());
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
    private <R extends OWLPropertyRange, P extends OWLPropertyExpression<R,P>, F extends OWLPropertyRange> void addRestrictionCommonTriplePropertyRange(OWLRestriction<R, P, F> desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(desc, OWL_ON_PROPERTY.getIRI(), desc.getProperty());
    }
    
    private <R extends OWLPropertyRange, P extends OWLPropertyExpression<R,P>, F extends OWLPropertyExpression<R,P>> void addRestrictionCommonTriplePropertyExpression(OWLRestriction<R, P, F> desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(desc, OWL_ON_PROPERTY.getIRI(), desc.getProperty());
    }
    
    private void addObjectCardinalityRestrictionTriples(OWLCardinalityRestriction<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression> ce, OWLRDFVocabulary cardiPredicate) {
    	addRestrictionCommonTriplePropertyRange(ce);
        addTriple(ce, cardiPredicate.getIRI(), toTypedConstant(ce.getCardinality()));
        if (ce.isQualified()) {
            if (ce.isObjectRestriction()) {
                addTriple(ce, OWL_ON_CLASS.getIRI(), ce.getFiller());
            } else {
                addTriple(ce, OWL_ON_DATA_RANGE.getIRI(), ce.getFiller());
            }
        }

    }

    private void addDataCardinalityRestrictionTriples(OWLCardinalityRestriction<OWLDataRange, OWLDataPropertyExpression, OWLDataRange> ce, OWLRDFVocabulary cardiPredicate) {
    	addRestrictionCommonTriplePropertyRange(ce);
        addTriple(ce, cardiPredicate.getIRI(), toTypedConstant(ce.getCardinality()));
        if (ce.isQualified()) {
            if (ce.isObjectRestriction()) {
                addTriple(ce, OWL_ON_CLASS.getIRI(), ce.getFiller());
            }
            else {
                addTriple(ce, OWL_ON_DATA_RANGE.getIRI(), ce.getFiller());
            }
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////

    public void visit(OWLObjectSomeValuesFrom desc) {
    	addRestrictionCommonTriplePropertyRange(desc);
        addTriple(desc, OWL_SOME_VALUES_FROM.getIRI(), desc.getFiller());
    }


    public void visit(OWLObjectAllValuesFrom desc) {
    	addRestrictionCommonTriplePropertyRange(desc);
        addTriple(desc, OWL_ALL_VALUES_FROM.getIRI(), desc.getFiller());
    }


    public void visit(OWLObjectHasValue desc) {
    	addRestrictionCommonTriplePropertyExpression(desc);
        addTriple(desc, OWL_HAS_VALUE.getIRI(), desc.getValue());
        processIfAnonymous(desc.getValue(), null);
    }

    public void visit(OWLObjectHasSelf desc) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(desc, OWL_ON_PROPERTY.getIRI(), desc.getProperty());
        addTriple(desc, OWL_HAS_SELF.getIRI(), manager.getOWLDataFactory().getOWLLiteral(true));
    }


    public void visit(OWLObjectMinCardinality desc) {
        if (desc.isQualified()) {
            addObjectCardinalityRestrictionTriples(desc, OWL_MIN_QUALIFIED_CARDINALITY);
        }
        else {
        	addObjectCardinalityRestrictionTriples(desc, OWL_MIN_CARDINALITY);
        }
    }


    public void visit(OWLObjectMaxCardinality desc) {
        if (desc.isQualified()) {
        	addObjectCardinalityRestrictionTriples(desc, OWL_MAX_QUALIFIED_CARDINALITY);
        }
        else {
        	addObjectCardinalityRestrictionTriples(desc, OWL_MAX_CARDINALITY);
        }
    }

    public void visit(OWLObjectExactCardinality desc) {
        if (desc.isQualified()) {
        	addObjectCardinalityRestrictionTriples(desc, OWL_QUALIFIED_CARDINALITY);
        }
        else {
        	addObjectCardinalityRestrictionTriples(desc, OWL_CARDINALITY);
        }
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        addRestrictionCommonTriplePropertyRange(desc);
        addTriple(desc, OWL_SOME_VALUES_FROM.getIRI(), desc.getFiller());
    }

    public void visit(OWLDataAllValuesFrom desc) {
        addRestrictionCommonTriplePropertyRange(desc);
        addTriple(desc, OWL_ALL_VALUES_FROM.getIRI(), desc.getFiller());
    }

    public void visit(OWLDataHasValue desc) {
        addRestrictionCommonTriplePropertyExpression(desc);
        addTriple(desc, OWL_HAS_VALUE.getIRI(), desc.getValue());
    }

    public void visit(OWLDataMinCardinality desc) {
        if (desc.isQualified()) {
        	addDataCardinalityRestrictionTriples(desc, OWL_MIN_QUALIFIED_CARDINALITY);
        }
        else {
        	addDataCardinalityRestrictionTriples(desc, OWL_MIN_CARDINALITY);
        }
    }

    public void visit(OWLDataMaxCardinality desc) {
        if (desc.isQualified()) {
        	addDataCardinalityRestrictionTriples(desc, OWL_MAX_QUALIFIED_CARDINALITY);
        }
        else {
        	addDataCardinalityRestrictionTriples(desc, OWL_MAX_CARDINALITY);
        }

    }

    public void visit(OWLDataExactCardinality desc) {
        if (desc.isQualified()) {
        	addDataCardinalityRestrictionTriples(desc, OWL_QUALIFIED_CARDINALITY);
        }
        else {
        	addDataCardinalityRestrictionTriples(desc, OWL_CARDINALITY);
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
        addSingleTripleAxiom(axiom, axiom.getSubClass(), RDFS_SUBCLASS_OF.getIRI(), axiom.getSuperClass());
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() == 2) {
            addPairwiseClassExpressions(axiom, axiom.getClassExpressions(), OWL_EQUIVALENT_CLASS.getIRI());
        }
        else {
            //see http://www.w3.org/TR/2009/REC-owl2-mapping-to-rdf-20091027/
            List<OWLClassExpression> list = axiom.getClassExpressionsAsList();
            int count = list.size();
            for (int i = 0; i + 1 < count; i++) {
                addTriple(list.get(i), OWL_EQUIVALENT_CLASS.getIRI(), list.get(i + 1));
            }
        }

    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() == 2) {
            addPairwiseClassExpressions(axiom, axiom.getClassExpressions(), OWL_DISJOINT_WITH.getIRI());
        }
        else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_CLASSES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.getClassExpressions());
            translateAnnotations(axiom);
        }
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getOWLClass(), OWL_DISJOINT_UNION_OF.getIRI(), axiom.getClassExpressions());
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(), axiom.getSuperProperty());
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSuperProperty(), OWL_PROPERTY_CHAIN_AXIOM.getIRI(), axiom.getPropertyChain());
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        addPairwise(axiom, axiom.getProperties(), OWL_EQUIVALENT_PROPERTY.getIRI());
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_PROPERTY_DISJOINT_WITH.getIRI());
        }
        else {
            translateAnonymousNode(axiom);
            translateAnnotations(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_PROPERTIES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.getProperties());
        }
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }

    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getFirstProperty(), OWL_INVERSE_OF.getIRI(), axiom.getSecondProperty());
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(), OWL_FUNCTIONAL_PROPERTY.getIRI());
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(), OWL_INVERSE_FUNCTIONAL_PROPERTY.getIRI());
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(), OWL_REFLEXIVE_PROPERTY.getIRI());
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(), OWL_IRREFLEXIVE_PROPERTY.getIRI());
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(), OWL_SYMMETRIC_PROPERTY.getIRI());
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(), OWL_ASYMMETRIC_PROPERTY.getIRI());
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(), OWL_TRANSITIVE_PROPERTY.getIRI());
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(), axiom.getSuperProperty());
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        addPairwise(axiom, axiom.getProperties(), OWL_EQUIVALENT_PROPERTY.getIRI());
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        if (axiom.getProperties().size() == 2) {
            addPairwise(axiom, axiom.getProperties(), OWL_PROPERTY_DISJOINT_WITH.getIRI());
        }
        else {
            translateAnonymousNode(axiom);
            translateAnnotations(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_PROPERTIES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.getProperties());
        }
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(), OWL_FUNCTIONAL_PROPERTY.getIRI());
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getDatatype(), OWL_EQUIVALENT_CLASS.getIRI(), axiom.getDataRange());
    }

    public void visit(OWLHasKeyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getClassExpression(), OWL_HAS_KEY.getIRI(), axiom.getPropertyExpressions());
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        addPairwise(axiom, axiom.getIndividuals(), OWL_SAME_AS.getIRI());
        processIfAnonymous(axiom.getIndividuals(), axiom);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DIFFERENT.getIRI());
        addListTriples(axiom, OWL_DISTINCT_MEMBERS.getIRI(), axiom.getIndividuals());
        processIfAnonymous(axiom.getIndividuals(), axiom);
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        addSingleTripleAxiom(axiom, axiom.getIndividual(), RDF_TYPE.getIRI(), axiom.getClassExpression());
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
        addTriple(axiom, RDF_TYPE.getIRI(), OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        addTriple(axiom, OWL_SOURCE_INDIVIDUAL.getIRI(), axiom.getSubject());
        addTriple(axiom, OWL_ASSERTION_PROPERTY.getIRI(), axiom.getProperty());
        addTriple(axiom, OWL_TARGET_INDIVIDUAL.getIRI(), axiom.getObject());
        translateAnnotations(axiom);
        processIfAnonymous(axiom.getSubject(), axiom);
        processIfAnonymous(axiom.getObject(), axiom);
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        translateAnonymousNode(axiom);
        for (OWLAnnotation anno : axiom.getAnnotations()) {
            addTriple(axiom, anno.getProperty().getIRI(), anno.getValue());
        }
        addTriple(axiom, RDF_TYPE.getIRI(), OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        addTriple(axiom, OWL_SOURCE_INDIVIDUAL.getIRI(), axiom.getSubject());
        addTriple(axiom, OWL_ASSERTION_PROPERTY.getIRI(), axiom.getProperty());
        addTriple(axiom, OWL_TARGET_VALUE.getIRI(), axiom.getObject());
        processIfAnonymous(axiom.getSubject(), axiom);
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getValue());
        if (axiom.getValue() instanceof OWLAnonymousIndividual) {
            processIfAnonymous((OWLAnonymousIndividual) axiom.getValue(), axiom);
        }
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(), axiom.getSuperProperty());
    }


    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }

    public void visit(OWLClass desc) {
        if (!nodeMap.containsKey(desc)) {
            nodeMap.put(desc, getResourceNode(desc.getIRI()));
        }
        addStrongTyping(desc);
    }


    public void visit(OWLDatatype node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getIRI()));
        }
        addStrongTyping(node);
    }


    public void visit(OWLFacetRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, node.getFacet().getIRI(), node.getFacetValue());
    }

    public void visit(IRI iri) {
        if (!nodeMap.containsKey(iri)) {
            nodeMap.put(iri, getResourceNode(iri));
        }
    }

    public void visit(OWLLiteral node) {
        nodeMap.put(node, getLiteralNode(node));
    }

    public void visit(OWLDataProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getIRI()));
        }
        addStrongTyping(property);
    }


    public void visit(OWLObjectProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getIRI()));
        }
        addStrongTyping(property);
    }

    public void visit(OWLAnnotationProperty property) {
        if (!nodeMap.containsKey(property)) {
            nodeMap.put(property, getPredicateNode(property.getIRI()));
        }
        addStrongTyping(property);
    }


    public void visit(OWLNamedIndividual individual) {
        if (!nodeMap.containsKey(individual)) {
            nodeMap.put(individual, getResourceNode(individual.getIRI()));
        }
        addStrongTyping(individual);
    }


    public void visit(OWLAnonymousIndividual individual) {
        translateAnonymousNode(individual);
    }

    public void visit(OWLOntology ontology) {
        if (ontology.isAnonymous()) {
            translateAnonymousNode(ontology);
        }
        else {
            if (!nodeMap.containsKey(ontology)) {
                nodeMap.put(ontology, getResourceNode(ontology.getOntologyID().getOntologyIRI()));
            }
        }
        addTriple(ontology, RDF_TYPE.getIRI(), OWL_ONTOLOGY.getIRI());
    }


    public void visit(SWRLRule rule) {
//        if (!rule.isAnonymous()) {
//            if (!nodeMap.containsKey(rule)) {
//                nodeMap.put(rule, getResourceNode(rule.getIRI()));
//            }
//        }
//        else {
        translateAnonymousNode(rule);
//        }
        addTriple(rule, RDF_TYPE.getIRI(), IMP.getIRI());

        Set<SWRLAtom> antecedent = rule.getBody();
        addTriple(rule, BODY.getIRI(), antecedent, ATOM_LIST.getIRI());
        Set<SWRLAtom> consequent = rule.getHead();
        addTriple(rule, HEAD.getIRI(), consequent, ATOM_LIST.getIRI());
    }


    public void visit(SWRLClassAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), CLASS_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, CLASS_PREDICATE.getIRI(), node.getPredicate());
        node.getArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getArgument());
    }


    public void visit(SWRLDataRangeAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), DATA_RANGE_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, DATA_RANGE.getIRI(), node.getPredicate());
        node.getArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getArgument());
    }


    public void visit(SWRLObjectPropertyAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), INDIVIDUAL_PROPERTY_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, PROPERTY_PREDICATE.getIRI(), node.getPredicate());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }


    public void visit(SWRLDataPropertyAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), DATAVALUED_PROPERTY_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, PROPERTY_PREDICATE.getIRI(), node.getPredicate());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }


    public void visit(SWRLBuiltInAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), BUILT_IN_ATOM.getIRI());
        addTriple(node, BUILT_IN.getIRI(), node.getPredicate());
        addTriple(getResourceNode(node.getPredicate()), getPredicateNode(BUILT_IN_CLASS.getIRI()), getResourceNode(BUILT_IN_CLASS.getIRI()));
        addTriple(getResourceNode(node), getPredicateNode(ARGUMENTS.getIRI()), translateList(new ArrayList<OWLObject>(node.getArguments())));
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), DIFFERENT_INDIVIDUALS_ATOM.getIRI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }


    public void visit(SWRLSameIndividualAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), SAME_INDIVIDUAL_ATOM.getIRI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }


    public void visit(SWRLVariable node) {
        if (!nodeMap.containsKey(node)) {
            nodeMap.put(node, getResourceNode(node.getIRI()));
        }
        addTriple(node, RDF_TYPE.getIRI(), VARIABLE.getIRI());
    }


    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
        nodeMap.put(node, nodeMap.get(node.getIndividual()));
    }


    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
        nodeMap.put(node, nodeMap.get(node.getLiteral()));
    }


//    private void processAnonymousIndividual(OWLIndividual ind) {
//        if (!ind.isAnonymous()) {
//            return;
//        }
//        for (OWLAxiom ax : ontology.getAxioms(ind)) {
//            ax.accept(this);
//        }
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods to add triples
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Maps Objects to nodes
     */
    private Map<OWLObject, NODE> nodeMap = new IdentityHashMap<OWLObject, NODE>();


    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subject, IRI pred, OWLObject obj) {
        addSingleTripleAxiom(ax, getResourceNode(subject), getPredicateNode(pred), getNode(obj));
    }


    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subject, IRI pred, IRI obj) {
        addSingleTripleAxiom(ax, getResourceNode(subject), getPredicateNode(pred), getResourceNode(obj));
    }


    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subj, IRI pred, Collection<? extends OWLObject> obj) {
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
     * @param ax The axiom that the triple specified as subject, pred, obj represents.
     * @param subject The subject of the triple representing the axiom
     * @param predicate The predicate of the triple representing the axiom
     * @param object The object of the triple representing the axiom
     */
    private void addSingleTripleAxiom(OWLAxiom ax, RESOURCE subject, PREDICATE predicate, NODE object) {
        // Base triple
        addTriple(subject, predicate, object);
        if (ax.getAnnotations().isEmpty()) {
            return;
        }
        // The axiom has annotations and we therefore need to reify the axiom in order to add the annotations
        translateAnonymousNode(ax);
        addTriple(getResourceNode(ax), getPredicateNode(RDF_TYPE.getIRI()), getResourceNode(OWL_AXIOM.getIRI()));
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_SOURCE.getIRI()), subject);
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_PROPERTY.getIRI()), predicate);
        addTriple(getResourceNode(ax), getPredicateNode(OWL_ANNOTATED_TARGET.getIRI()), object);
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
     * @param subject The subject of the annotation
     * @param annotation The annotation
     */
    private void translateAnnotation(OWLObject subject, OWLAnnotation annotation) {
        // We first add the base triple
        addTriple(subject, annotation.getProperty().getIRI(), annotation.getValue());
        // If the annotation doesn't have annotations on it then we're done
        if (annotation.getAnnotations().isEmpty()) {
            return;
        }
        // The annotation has annotations on it so we need to reify the annotation
        // The main node is the annotation, it is typed as an annotation
        translateAnonymousNode(annotation);
        addTriple(annotation, RDF_TYPE.getIRI(), OWL_ANNOTATION.getIRI());
        addTriple(annotation, OWL_ANNOTATED_SOURCE.getIRI(), subject);
        addTriple(annotation, OWL_ANNOTATED_PROPERTY.getIRI(), annotation.getProperty().getIRI());
        addTriple(annotation, OWL_ANNOTATED_TARGET.getIRI(), annotation.getValue());
        for (OWLAnnotation anno : annotation.getAnnotations()) {
            translateAnnotation(annotation, anno);
        }
    }
    @SuppressWarnings("unused")
    public void visit(OWLAnnotation node) {
        throw new OWLRuntimeException("The translator should not be used directly on instances of OWLAnnotation because an annotation cannot be translated without a subject.");
    }

    private void translateAnonymousNode(OWLObject object) {
        nodeMap.put(object, getAnonymousNode(object));
    }


    /**
     * Gets a resource that has a IRI
     * @param IRI The IRI of the resource
     * @return The resource with the specified IRI
     */
    protected abstract RESOURCE getResourceNode(IRI IRI);


    protected abstract PREDICATE getPredicateNode(IRI IRI);


    /**
     * Gets an anonymous resource.
     * @param key A key for the resource.  For a given key identity, the resources
     * that are returned should be equal and have the same hashcode.
     * @return The resource
     */
    protected abstract RESOURCE getAnonymousNode(Object key);


    protected abstract LITERAL getLiteralNode(OWLLiteral literal);
//    /**
//     * Gets a literal node that represents a typed literal.
//     * @param literal The literal
//     * @param datatype The datatype that types the literal
//     * @return The literal
//     */
//    protected abstract LITERAL getLiteralNode(String literal, IRI datatype);
//
//
//    protected abstract LITERAL getLiteralNode(String literal, String lang);


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
        return translateList(list, RDF_LIST.getIRI());
    }

    private RESOURCE translateList(List<? extends OWLObject> list, IRI listType) {
        if (list.isEmpty()) {
            return getResourceNode(RDF_NIL.getIRI());
        }
        RESOURCE main = getResourceNode(RDF_NIL.getIRI());
        int listSize = list.size() - 1;
        for (int i = listSize; i >= 0; i--) {
            RESOURCE anonNode = getAnonymousNode(list.subList(i, listSize));
            addTriple(anonNode, getPredicateNode(RDF_TYPE.getIRI()), getResourceNode(listType));
            addTriple(anonNode, getPredicateNode(RDF_FIRST.getIRI()), getNode(list.get(i)));
            addTriple(anonNode, getPredicateNode(RDF_REST.getIRI()), main);
            main = anonNode;
        }
        return main;

    }


    private void addTriple(OWLObject subject, IRI pred, IRI object) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), getResourceNode(object));
    }

    private void addTriple(OWLObject subject, IRI pred, OWLObject object) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), getNode(object));
    }

    private void addListTriples(OWLObject subject, IRI pred, Set<? extends OWLObject> objects) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), translateList(new ArrayList<OWLObject>(objects)));
    }

    private void addTriple(OWLObject subject, IRI pred, Set<? extends OWLObject> objects, IRI listType) {
        addTriple(getResourceNode(subject), getPredicateNode(pred), translateList(new ArrayList<OWLObject>(objects), listType));
    }

    private OWLLiteral toTypedConstant(int i) {
        return manager.getOWLDataFactory().getOWLLiteral(Integer.toString(i), manager.getOWLDataFactory().getOWLDatatype(XSDVocabulary.NON_NEGATIVE_INTEGER.getIRI()));
    }

    private void processIfAnonymous(Set<OWLIndividual> inds, OWLAxiom root) {
        for (OWLIndividual ind : inds) {
            processIfAnonymous(ind, root);
        }
    }

    private Set<OWLIndividual> currentIndividuals = new HashSet<OWLIndividual>();

    private void processIfAnonymous(OWLIndividual ind, OWLAxiom root) {
        if (!currentIndividuals.contains(ind)) {
            currentIndividuals.add(ind);
            if (ind.isAnonymous()) {
                for (OWLAxiom ax : ontology.getAxioms(ind)) {
                    if (root == null || !root.equals(ax)) {
                        ax.accept(this);
                    }
                }
                for (OWLAnnotationAssertionAxiom ax : ontology.getAnnotationAssertionAxioms(ind.asOWLAnonymousIndividual())) {
                    ax.accept(this);
                }
            }
            currentIndividuals.remove(ind);
        }
    }

//    private boolean isAnonymous(OWLObject object) {
//        return !(object instanceof OWLEntity || object instanceof IRI);
//    }

    private void addPairwise(OWLAxiom axiom, Collection<? extends OWLObject> objects, IRI IRI) {
        List<? extends OWLObject> objectList = new ArrayList<OWLObject>(objects);
        for (int i = 0; i < objectList.size(); i++) {
            for (int j = i; j < objectList.size(); j++) {
                if (i != j) {
                    addSingleTripleAxiom(axiom, objectList.get(i), IRI, objectList.get(j));
                }
            }
        }
    }


    /**
     * Renders a set of class expressions in a pairwise manner using the specified IRI.  It
     * is assumed that the relationship described by the IRI (e.g. disjointWith) is
     * symmetric.  The method delegates to the <code>addPairwise</code> method after sorting
     * the class expressions so that named classes appear first.
     * @param axiom The axiom which will dictate which axiom annotation get rendered
     * @param classExpressions The set of class expressions to be rendered.
     * @param IRI The IRI which describes the relationship between pairs of class expressions.
     */
    private void addPairwiseClassExpressions(OWLAxiom axiom, Set<OWLClassExpression> classExpressions, IRI IRI) {
        List<OWLClassExpression> classExpressionList = new ArrayList<OWLClassExpression>(classExpressions);
        addPairwise(axiom, classExpressionList, IRI);

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
        if(!RDFOntologyFormat.isMissingType(entity, ontology)) {
            return;
        }
        addTriple(entity, RDF_TYPE.getIRI(), entity.accept(OWLEntityTypeProvider.INSTANCE));
    }


    /**
     * Visits entities and returns their RDF type
     */
    @SuppressWarnings("unused")
    private static class OWLEntityTypeProvider implements OWLEntityVisitorEx<IRI> {

        public static OWLEntityTypeProvider INSTANCE = new OWLEntityTypeProvider();

        public IRI visit(OWLClass cls) {
            return OWL_CLASS.getIRI();
        }

        public IRI visit(OWLObjectProperty property) {
            return OWL_OBJECT_PROPERTY.getIRI();
        }

        public IRI visit(OWLDataProperty property) {
            return OWL_DATA_PROPERTY.getIRI();
        }

        public IRI visit(OWLNamedIndividual individual) {
            return OWL_NAMED_INDIVIDUAL.getIRI();
        }

        public IRI visit(OWLDatatype datatype) {
            return RDFS_DATATYPE.getIRI();
        }

        public IRI visit(OWLAnnotationProperty property) {
            return OWL_ANNOTATION_PROPERTY.getIRI();
        }
    }

}
