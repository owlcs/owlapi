/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.rdf.model;

import static org.semanticweb.owlapi.util.CollectionFactory.createLinkedSet;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DIFFERENT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DISJOINT_CLASSES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_DISJOINT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ALL_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_SOURCE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_TARGET;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ASSERTION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ASYMMETRIC_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_AXIOM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATATYPE_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISJOINT_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISJOINT_WITH;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISTINCT_MEMBERS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_FUNCTIONAL_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_HAS_KEY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_HAS_SELF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_HAS_VALUE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INTERSECTION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INVERSE_FUNCTIONAL_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_INVERSE_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_IRREFLEXIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MAX_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MAX_QUALIFIED_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MEMBERS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MIN_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_MIN_QUALIFIED_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONE_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONTOLOGY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_DATA_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_DATA_TYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_PROPERTY_CHAIN_AXIOM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_PROPERTY_DISJOINT_WITH;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_QUALIFIED_CARDINALITY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_REFLEXIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SAME_AS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SOME_VALUES_FROM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SOURCE_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SYMMETRIC_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TARGET_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TARGET_VALUE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TRANSITIVE_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_UNION_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_WITH_RESTRICTIONS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_SUBCLASS_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_SUB_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_FIRST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_LIST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_NIL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_REST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_TYPE;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ARGUMENTS;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ARGUMENT_1;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ARGUMENT_2;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.ATOM_LIST;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BODY;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BUILT_IN;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BUILT_IN_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.BUILT_IN_CLASS;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.CLASS_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.CLASS_PREDICATE;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DATAVALUED_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DATA_RANGE;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DATA_RANGE_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.DIFFERENT_INDIVIDUALS_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.HEAD;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.IMP;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.INDIVIDUAL_PROPERTY_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.PROPERTY_PREDICATE;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.SAME_INDIVIDUAL_ATOM;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.VARIABLE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRestriction;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.rdf.RDFRendererBase;
import org.semanticweb.owlapi.util.IndividualAppearance;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

/**
 * An abstract translator that can produce an RDF graph from an OWLOntology. Subclasses must provide
 * implementations to create concrete representations of resources, triples etc.
 *
 * @param <N> the basic node
 * @param <R> a resource node
 * @param <P> a predicate node
 * @param <L> a literal node
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public abstract class AbstractTranslator<N extends Serializable, R extends N, P extends N, L extends N>
    implements OWLObjectVisitor, SWRLObjectVisitor {

    protected final IndividualAppearance multipleOccurrences;
    private final OWLOntologyManager manager;
    private final OWLOntology ont;
    private final boolean useStrongTyping;
    private final Set<OWLIndividual> currentIndividuals = createLinkedSet();
    /**
     * Maps Objects to nodes.
     */
    private final Map<OWLObject, N> nodeMap = new HashMap<>();
    private final Map<OWLObject, N> expressionMap = new IdentityHashMap<>();
    protected RDFGraph graph = new RDFGraph();

    /**
     * @param manager the manager
     * @param ontology the ontology
     * @param useStrongTyping true if strong typing should be used
     * @param multiple will tell whether anonymous individuals need an id or not
     */
    public AbstractTranslator(OWLOntologyManager manager, OWLOntology ontology,
        boolean useStrongTyping, IndividualAppearance multiple) {
        this.ont = checkNotNull(ontology, "ontology cannot be null");
        this.manager = checkNotNull(manager, "manager cannot be null");
        this.useStrongTyping = useStrongTyping;
        multipleOccurrences = multiple;
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getEntity(), RDF_TYPE.getIRI(),
            axiom.getEntity().getEntityType().getIRI());
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        translateAnonymousNode(property);
        addTriple(property, OWL_INVERSE_OF.getIRI(), property.getInverse());
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_INTERSECTION_OF.getIRI(), node.operands());
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_UNION_OF.getIRI(), node.operands());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addTriple(node, OWL_DATATYPE_COMPLEMENT_OF.getIRI(), node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addListTriples(node, OWL_ONE_OF.getIRI(), node.values());
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), RDFS_DATATYPE.getIRI());
        addTriple(node, OWL_ON_DATA_TYPE.getIRI(), node.getDatatype());
        addListTriples(node, OWL_WITH_RESTRICTIONS.getIRI(), node.facetRestrictions());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        translateAnonymousNode(ce);
        addListTriples(ce, OWL_INTERSECTION_OF.getIRI(), ce.operands());
        addTriple(ce, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        translateAnonymousNode(ce);
        addTriple(ce, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addListTriples(ce, OWL_UNION_OF.getIRI(), ce.operands());
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        translateAnonymousNode(ce);
        addTriple(ce, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addTriple(ce, OWL_COMPLEMENT_OF.getIRI(), ce.getOperand());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        translateAnonymousNode(ce);
        addTriple(ce, RDF_TYPE.getIRI(), OWL_CLASS.getIRI());
        addListTriples(ce, OWL_ONE_OF.getIRI(), ce.individuals());
        processIfAnonymous(ce.individuals(), null);
    }

    // Translation of restrictions

    /**
     * Add type triples and the owl:onProperty triples for an OWLRestriction.
     *
     * @param desc The restriction
     * @param property property
     */
    private void addRestrictionCommonTriplePropertyRange(OWLRestriction desc,
        OWLPropertyExpression property) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(desc, OWL_ON_PROPERTY.getIRI(), property);
    }

    private void addRestrictionCommonTriplePropertyExpression(OWLRestriction desc,
        OWLPropertyExpression property) {
        translateAnonymousNode(desc);
        addTriple(desc, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(desc, OWL_ON_PROPERTY.getIRI(), property);
    }

    private void addObjectCardinalityRestrictionTriples(
        OWLCardinalityRestriction<OWLClassExpression> ce, OWLPropertyExpression p,
        OWLRDFVocabulary cardiPredicate) {
        addRestrictionCommonTriplePropertyRange(ce, p);
        addTriple(ce, cardiPredicate.getIRI(), toTypedConstant(ce.getCardinality()));
        if (ce.isQualified()) {
            if (ce.isObjectRestriction()) {
                addTriple(ce, OWL_ON_CLASS.getIRI(), ce.getFiller());
            } else {
                addTriple(ce, OWL_ON_DATA_RANGE.getIRI(), ce.getFiller());
            }
        }
    }

    private void addDataCardinalityRestrictionTriples(OWLCardinalityRestriction<OWLDataRange> ce,
        OWLPropertyExpression p, OWLRDFVocabulary cardiPredicate) {
        addRestrictionCommonTriplePropertyRange(ce, p);
        addTriple(ce, cardiPredicate.getIRI(), toTypedConstant(ce.getCardinality()));
        if (ce.isQualified()) {
            if (ce.isObjectRestriction()) {
                addTriple(ce, OWL_ON_CLASS.getIRI(), ce.getFiller());
            } else {
                addTriple(ce, OWL_ON_DATA_RANGE.getIRI(), ce.getFiller());
            }
        }
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        addRestrictionCommonTriplePropertyRange(ce, ce.getProperty());
        addTriple(ce, OWL_SOME_VALUES_FROM.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        addRestrictionCommonTriplePropertyRange(ce, ce.getProperty());
        addTriple(ce, OWL_ALL_VALUES_FROM.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        addRestrictionCommonTriplePropertyExpression(ce, ce.getProperty());
        addTriple(ce, OWL_HAS_VALUE.getIRI(), ce.getFiller());
        processIfAnonymous(ce.getFiller(), null);
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        translateAnonymousNode(ce);
        addTriple(ce, RDF_TYPE.getIRI(), OWL_RESTRICTION.getIRI());
        addTriple(ce, OWL_ON_PROPERTY.getIRI(), ce.getProperty());
        addTriple(ce, OWL_HAS_SELF.getIRI(), manager.getOWLDataFactory().getOWLLiteral(true));
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        if (ce.isQualified()) {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(),
                OWL_MIN_QUALIFIED_CARDINALITY);
        } else {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_MIN_CARDINALITY);
        }
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        if (ce.isQualified()) {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(),
                OWL_MAX_QUALIFIED_CARDINALITY);
        } else {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_MAX_CARDINALITY);
        }
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        if (ce.isQualified()) {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_QUALIFIED_CARDINALITY);
        } else {
            addObjectCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_CARDINALITY);
        }
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        addRestrictionCommonTriplePropertyRange(ce, ce.getProperty());
        addTriple(ce, OWL_SOME_VALUES_FROM.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        addRestrictionCommonTriplePropertyRange(ce, ce.getProperty());
        addTriple(ce, OWL_ALL_VALUES_FROM.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        addRestrictionCommonTriplePropertyExpression(ce, ce.getProperty());
        addTriple(ce, OWL_HAS_VALUE.getIRI(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        if (ce.isQualified()) {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(),
                OWL_MIN_QUALIFIED_CARDINALITY);
        } else {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_MIN_CARDINALITY);
        }
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        if (ce.isQualified()) {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(),
                OWL_MAX_QUALIFIED_CARDINALITY);
        } else {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_MAX_CARDINALITY);
        }
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        if (ce.isQualified()) {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_QUALIFIED_CARDINALITY);
        } else {
            addDataCardinalityRestrictionTriples(ce, ce.getProperty(), OWL_CARDINALITY);
        }
    }

    // Axioms
    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubClass(), RDFS_SUBCLASS_OF.getIRI(),
            axiom.getSuperClass());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.classExpressions().count() == 2) {
            addPairwise(axiom, axiom.classExpressions(), OWL_EQUIVALENT_CLASS.getIRI());
        } else {
            axiom.splitToAnnotatedPairs().stream().sorted().forEach(ax -> ax.accept(this));
        }
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        if (axiom.classExpressions().count() == 2) {
            addPairwise(axiom, axiom.classExpressions(), OWL_DISJOINT_WITH.getIRI());
        } else {
            translateAnonymousNode(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_CLASSES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.classExpressions());
            translateAnnotations(axiom);
        }
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getOWLClass(), OWL_DISJOINT_UNION_OF.getIRI(),
            axiom.classExpressions());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(),
            axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSuperProperty(), OWL_PROPERTY_CHAIN_AXIOM.getIRI(),
            axiom.getPropertyChain().stream());
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        if (axiom.properties().count() == 2) {
            addPairwise(axiom, axiom.properties(), OWL_EQUIVALENT_PROPERTY.getIRI());
        } else {
            axiom.splitToAnnotatedPairs().stream().sorted().forEach(ax -> ax.accept(this));
        }
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        if (axiom.properties().count() == 2) {
            addPairwise(axiom, axiom.properties(), OWL_PROPERTY_DISJOINT_WITH.getIRI());
        } else {
            translateAnonymousNode(axiom);
            translateAnnotations(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_PROPERTIES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.properties());
        }
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getFirstProperty(), OWL_INVERSE_OF.getIRI(),
            axiom.getSecondProperty());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_FUNCTIONAL_PROPERTY.getIRI());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_INVERSE_FUNCTIONAL_PROPERTY.getIRI());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_REFLEXIVE_PROPERTY.getIRI());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_IRREFLEXIVE_PROPERTY.getIRI());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_SYMMETRIC_PROPERTY.getIRI());
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_ASYMMETRIC_PROPERTY.getIRI());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_TRANSITIVE_PROPERTY.getIRI());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(),
            axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        if (axiom.properties().count() == 2) {
            addPairwise(axiom, axiom.properties(), OWL_EQUIVALENT_PROPERTY.getIRI());
        } else {
            axiom.splitToAnnotatedPairs().stream().sorted().forEach(ax -> ax.accept(this));
        }
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        if (axiom.properties().count() == 2) {
            addPairwise(axiom, axiom.properties(), OWL_PROPERTY_DISJOINT_WITH.getIRI());
        } else {
            translateAnonymousNode(axiom);
            translateAnnotations(axiom);
            addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DISJOINT_PROPERTIES.getIRI());
            addListTriples(axiom, OWL_MEMBERS.getIRI(), axiom.properties());
        }
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDF_TYPE.getIRI(),
            OWL_FUNCTIONAL_PROPERTY.getIRI());
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getDatatype(), OWL_EQUIVALENT_CLASS.getIRI(),
            axiom.getDataRange());
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getClassExpression(), OWL_HAS_KEY.getIRI(),
            axiom.propertyExpressions());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        addPairwise(axiom, axiom.individuals(), OWL_SAME_AS.getIRI());
        processIfAnonymous(axiom.individuals(), axiom);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getIRI(), OWL_ALL_DIFFERENT.getIRI());
        addListTriples(axiom, OWL_DISTINCT_MEMBERS.getIRI(), axiom.individuals());
        processIfAnonymous(axiom.individuals(), axiom);
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        addSingleTripleAxiom(axiom, axiom.getIndividual(), RDF_TYPE.getIRI(),
            axiom.getClassExpression());
        processIfAnonymous(axiom.getIndividual(), axiom);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        OWLObjectPropertyAssertionAxiom simplified = axiom.getSimplified();
        addSingleTripleAxiom(simplified, simplified.getSubject(), simplified.getProperty(),
            simplified.getObject());
        processIfAnonymous(simplified.getObject(), axiom);
        processIfAnonymous(simplified.getSubject(), axiom);
    }

    @Override
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

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getObject());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        translateAnonymousNode(axiom);
        addTriple(axiom, RDF_TYPE.getIRI(), OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        addTriple(axiom, OWL_SOURCE_INDIVIDUAL.getIRI(), axiom.getSubject());
        addTriple(axiom, OWL_ASSERTION_PROPERTY.getIRI(), axiom.getProperty());
        addTriple(axiom, OWL_TARGET_VALUE.getIRI(), axiom.getObject());
        translateAnnotations(axiom);
        processIfAnonymous(axiom.getSubject(), axiom);
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubject(), axiom.getProperty(), axiom.getValue());
        if (axiom.getValue() instanceof OWLAnonymousIndividual) {
            processIfAnonymous((OWLAnonymousIndividual) axiom.getValue(), axiom);
        }
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getSubProperty(), RDFS_SUB_PROPERTY_OF.getIRI(),
            axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_DOMAIN.getIRI(), axiom.getDomain());
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        addSingleTripleAxiom(axiom, axiom.getProperty(), RDFS_RANGE.getIRI(), axiom.getRange());
    }

    protected <T extends OWLObject> void putHasIRI(T i, Function<T, IRI> f) {
        nodeMap.computeIfAbsent(i, x -> getResourceNode(f.apply((T) x)));
    }

    protected void putLiteral(OWLLiteral i) {
        nodeMap.computeIfAbsent(i, x -> getLiteralNode(i));
    }

    protected void putProperty(OWLProperty p) {
        nodeMap.computeIfAbsent(p, x -> getPredicateNode(((OWLProperty) x).getIRI()));
    }

    @Override
    public void visit(OWLClass ce) {
        putHasIRI(ce, x -> x.getIRI());
        addStrongTyping(ce);
    }

    @Override
    public void visit(OWLDatatype node) {
        putHasIRI(node, x -> x.getIRI());
        addStrongTyping(node);
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        translateAnonymousNode(node);
        addTriple(node, node.getFacet().getIRI(), node.getFacetValue());
    }

    @Override
    public void visit(IRI iri) {
        putHasIRI(iri, x -> x);
    }

    @Override
    public void visit(OWLLiteral node) {
        putLiteral(node);
    }

    @Override
    public void visit(OWLDataProperty property) {
        putProperty(property);
        addStrongTyping(property);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        putProperty(property);
        addStrongTyping(property);
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        putProperty(property);
        addStrongTyping(property);
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        putHasIRI(individual, x -> x.getIRI());
        addStrongTyping(individual);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        translateAnonymousNode(individual);
    }

    @Override
    public void visit(OWLOntology ontology) {
        Optional<IRI> ontologyIRI = ontology.getOntologyID().getOntologyIRI();
        if (ontologyIRI.isPresent()) {
            putHasIRI(ontology, x -> x.getOntologyID().getOntologyIRI().get());
        } else {
            translateAnonymousNode(ontology);
        }
        addTriple(ontology, RDF_TYPE.getIRI(), OWL_ONTOLOGY.getIRI());
    }

    @Override
    public void visit(SWRLRule rule) {
        translateAnonymousNode(rule);
        translateAnnotations(rule);
        addTriple(rule, RDF_TYPE.getIRI(), IMP.getIRI());
        addTriple(rule, BODY.getIRI(), rule.body(), ATOM_LIST.getIRI());
        addTriple(rule, HEAD.getIRI(), rule.head(), ATOM_LIST.getIRI());
    }

    @Override
    public void visit(SWRLClassAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), CLASS_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, CLASS_PREDICATE.getIRI(), node.getPredicate());
        node.getArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getArgument());
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), DATA_RANGE_ATOM.getIRI());
        node.getPredicate().accept(this);
        addTriple(node, DATA_RANGE.getIRI(), node.getPredicate());
        node.getArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getArgument());
    }

    @Override
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

    @Override
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

    @Override
    public void visit(SWRLBuiltInAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), BUILT_IN_ATOM.getIRI());
        addTriple(node, BUILT_IN.getIRI(), node.getPredicate());
        addTriple(getResourceNode(node.getPredicate()), getPredicateNode(RDF_TYPE.getIRI()),
            getResourceNode(BUILT_IN_CLASS.getIRI()));
        addTriple(getNode(node), getPredicateNode(ARGUMENTS.getIRI()),
            translateList(new ArrayList<>(node.getArguments())));
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), DIFFERENT_INDIVIDUALS_ATOM.getIRI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        translateAnonymousNode(node);
        addTriple(node, RDF_TYPE.getIRI(), SAME_INDIVIDUAL_ATOM.getIRI());
        node.getFirstArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_1.getIRI(), node.getFirstArgument());
        node.getSecondArgument().accept((SWRLObjectVisitor) this);
        addTriple(node, ARGUMENT_2.getIRI(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLVariable node) {
        putHasIRI(node, x -> x.getIRI());
        if (!ont.containsIndividualInSignature(node.getIRI())) {
            addTriple(node, RDF_TYPE.getIRI(), VARIABLE.getIRI());
        }
    }

    protected void putSWRLEntity(SWRLArgument i, OWLObject o) {
        nodeMap.computeIfAbsent(i, (OWLObject x) -> {
            o.accept(this);
            return getMappedNode(o);
        });
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        putSWRLEntity(node, node.getIndividual());
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        putSWRLEntity(node, node.getLiteral());
    }

    // Methods to add triples
    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subject, IRI pred, OWLObject obj) {
        addSingleTripleAxiomRPN(ax, getNode(subject), getPredicateNode(pred), getNode(obj));
    }

    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subject, IRI pred, IRI obj) {
        addSingleTripleAxiomRPN(ax, getNode(subject), getPredicateNode(pred), getResourceNode(obj));
    }

    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subj, IRI pred,
        Stream<? extends OWLObject> obj) {
        addSingleTripleAxiomRPN(ax, getNode(subj), getPredicateNode(pred),
            translateList(asList(obj)));
    }

    private void addSingleTripleAxiom(OWLAxiom ax, OWLObject subj, OWLObject pred, OWLObject obj) {
        addSingleTripleAxiomRPN(ax, getNode(subj), getNode(pred), getNode(obj));
    }

    /**
     * Adds the representation of an axiom to the RDF graph where the axiom has a SINGLE MAIN TRIPLE
     * (specified by the subject, predicate, object parameters). The triple specified by the
     * subject, predicate and object parameters will be added to the graph. If the axiom has any
     * annotations on it then extra triples will be added. These will consist of three triples, that
     * "reify" (not in the RDF sense) the specified triple using the OWL 2 annotation vocabulary:
     * owl:annotatedSource, owl:annotatedProperty, owl:annotatedTarget, and other triples to encode
     * the annotations.
     *
     * @param ax The axiom that the triple specified as subject, pred, obj represents.
     * @param subject The subject of the triple representing the axiom
     * @param predicate The predicate of the triple representing the axiom
     * @param object The object of the triple representing the axiom
     */
    private void addSingleTripleAxiomRPN(OWLAxiom ax, R subject, P predicate, N object) {
        // Base triple
        addTriple(subject, predicate, object);
        if (!ax.isAnnotated()) {
            return;
        }
        // The axiom has annotations and we therefore need to reify the axiom in
        // order to add the annotations
        translateAnonymousNode(ax);
        addTriple(getNode(ax), getPredicateNode(RDF_TYPE.getIRI()),
            getResourceNode(OWL_AXIOM.getIRI()));
        addTriple(getNode(ax), getPredicateNode(OWL_ANNOTATED_SOURCE.getIRI()), subject);
        addTriple(getNode(ax), getPredicateNode(OWL_ANNOTATED_PROPERTY.getIRI()), predicate);
        addTriple(getNode(ax), getPredicateNode(OWL_ANNOTATED_TARGET.getIRI()), object);
        translateAnnotations(ax);
    }

    private void translateAnnotations(OWLAxiom ax) {
        translateAnonymousNode(ax);
        ax.annotations().forEach(a -> translateAnnotation(ax, a));
    }

    /**
     * Translates an annotation on a given subject. This method implements the TANN(ann, y)
     * translation in the spec
     *
     * @param subject The subject of the annotation
     * @param annotation The annotation
     */
    private void translateAnnotation(OWLObject subject, OWLAnnotation annotation) {
        // We first add the base triple
        addTriple(subject, annotation.getProperty().getIRI(), annotation.getValue());
        // if the annotation has a blank node as subject, add the triples here
        if (annotation.getValue() instanceof OWLAnonymousIndividual) {
            OWLAnonymousIndividual ind = (OWLAnonymousIndividual) annotation.getValue();
            translateAnonymousNode(ind);
            processIfAnonymous(ind);
        }
        // If the annotation doesn't have annotations on it then we're done
        if (annotation.annotations().count() == 0) {
            return;
        }
        // The annotation has annotations on it so we need to reify the
        // annotation
        // The main node is the annotation, it is typed as an annotation
        translateAnonymousNode(annotation);
        addTriple(annotation, RDF_TYPE.getIRI(), OWL_ANNOTATION.getIRI());
        addTriple(annotation, OWL_ANNOTATED_SOURCE.getIRI(), subject);
        addTriple(annotation, OWL_ANNOTATED_PROPERTY.getIRI(), annotation.getProperty().getIRI());
        addTriple(annotation, OWL_ANNOTATED_TARGET.getIRI(), annotation.getValue());
        annotation.annotations().forEach(a -> translateAnnotation(annotation, a));
    }

    @Override
    public void visit(OWLAnnotation node) {
        throw new OWLRuntimeException(
            "The translator should not be used directly on instances of OWLAnnotation because an annotation cannot be translated without a subject.");
    }

    private void translateAnonymousNode(OWLObject object) {
        if (object.isAnonymousExpression()) {
            expressionMap.put(object, getAnonymousNode(object));
        } else {
            nodeMap.put(object, getAnonymousNode(object));
        }
    }

    /**
     * @param object that has already been mapped
     * @param <T> type needed
     * @return mapped node, or null if the node is absent
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T getMappedNode(OWLObject object) {
        if (object.isAnonymousExpression()) {
            return (T) expressionMap.get(object);
        }
        return (T) nodeMap.get(object);
    }

    /**
     * @param subject subject
     * @param pred predicate
     * @param object object
     */
    public void addTriple(R subject, IRI pred, IRI object) {
        addTriple(subject, getPredicateNode(pred), getResourceNode(object));
    }

    /**
     * @param subject subject
     * @param pred predicate
     * @param object object
     */
    public void addTriple(R subject, IRI pred, OWLObject object) {
        addTriple(subject, getPredicateNode(pred), getNode(object));
    }

    /**
     * Gets a resource that has a IRI.
     *
     * @param iri The IRI of the resource
     * @return The resource with the specified IRI
     */
    protected abstract R getResourceNode(IRI iri);

    protected abstract P getPredicateNode(IRI iri);

    /**
     * Gets an anonymous resource.
     *
     * @param key A key for the resource. For a given key identity, the resources that are returned
     *        should be equal and have the same hashcode.
     * @return The resource
     */
    protected abstract R getAnonymousNode(Object key);

    protected abstract L getLiteralNode(OWLLiteral literal);

    protected abstract void addTriple(R subject, P pred, N object);

    private <O> O getNode(OWLObject obj) {
        O node = getMappedNode(obj);
        if (node == null) {
            obj.accept(this);
            node = getMappedNode(obj);
            if (node == null) {
                obj.accept(this);
                throw new IllegalStateException("Node is null. Attempting to get node for " + obj);
            }
        }
        return node;
    }

    private R translateList(List<? extends OWLObject> list) {
        return translateList(list, RDF_LIST.getIRI());
    }

    private R translateList(List<? extends OWLObject> list, IRI listType) {
        if (list.isEmpty()) {
            return getResourceNode(RDF_NIL.getIRI());
        }
        R main = getResourceNode(RDF_NIL.getIRI());
        int listSize = list.size() - 1;
        for (int i = listSize; i >= 0; i--) {
            R anonNode = getAnonymousNode(list.subList(i, listSize));
            addTriple(anonNode, getPredicateNode(RDF_TYPE.getIRI()), getResourceNode(listType));
            OWLObject obj = list.get(i);
            addTriple(anonNode, getPredicateNode(RDF_FIRST.getIRI()), getNode(obj));
            addTriple(anonNode, getPredicateNode(RDF_REST.getIRI()), main);
            main = anonNode;
        }
        return main;
    }

    private void addTriple(OWLObject subject, IRI pred, IRI object) {
        addTriple(getNode(subject), getPredicateNode(pred), getResourceNode(object));
    }

    private void addTriple(OWLObject subject, IRI pred, OWLObject object) {
        addTriple(getNode(subject), getPredicateNode(pred), getNode(object));
    }

    private void addListTriples(OWLObject subject, IRI pred, Stream<? extends OWLObject> objects) {
        addTriple(getNode(subject), getPredicateNode(pred), translateList(sortOptionally(objects)));
    }

    private void addTriple(OWLObject subject, IRI pred, Stream<? extends OWLObject> objects,
        IRI listType) {
        addTriple(getNode(subject), getPredicateNode(pred),
            translateList(asList(objects), listType));
    }

    private OWLLiteral toTypedConstant(int i) {
        return manager.getOWLDataFactory().getOWLLiteral(Integer.toString(i), manager
            .getOWLDataFactory().getOWLDatatype(XSDVocabulary.NON_NEGATIVE_INTEGER.getIRI()));
    }

    private void processIfAnonymous(Stream<? extends OWLIndividual> inds, @Nullable OWLAxiom root) {
        sortOptionally(inds).forEach(i -> processIfAnonymous(i, root));
    }

    private void processIfAnonymous(OWLIndividual ind, @Nullable OWLAxiom root) {
        if (!currentIndividuals.contains(ind)) {
            currentIndividuals.add(ind);
            if (ind.isAnonymous()) {
                sortOptionally(ont.axioms(ind)).stream()
                    .filter(ax -> root == null || !root.equals(ax)).forEach(ax -> ax.accept(this));
                sortOptionally(ont.annotationAssertionAxioms(ind.asOWLAnonymousIndividual()))
                    .forEach(ax -> ax.accept(this));
            }
            currentIndividuals.remove(ind);
        }
    }

    private void processIfAnonymous(OWLIndividual ind) {
        if (!currentIndividuals.contains(ind)) {
            currentIndividuals.add(ind);
            if (ind.isAnonymous()) {
                sortOptionally(ont.axioms(ind)).forEach(ax -> ax.accept(this));
                sortOptionally(ont.annotationAssertionAxioms(ind.asOWLAnonymousIndividual()))
                    .forEach(ax -> ax.accept(this));
            }
            currentIndividuals.remove(ind);
        }
    }

    private void addPairwise(OWLAxiom axiom, Stream<? extends OWLObject> objects, IRI iri) {
        List<? extends OWLObject> objectList = sortOptionally(objects);
        for (int i = 0; i < objectList.size(); i++) {
            for (int j = i; j < objectList.size(); j++) {
                if (i != j) {
                    addSingleTripleAxiom(axiom, objectList.get(i), iri, objectList.get(j));
                }
            }
        }
    }

    /**
     * Adds triples to strong type an entity. Triples are only added if the useStrongTyping flag is
     * set to {@code true} and the entity is not a built in entity.
     *
     * @param entity The entity for which strong typing triples should be added.
     */
    private void addStrongTyping(OWLEntity entity) {
        if (!useStrongTyping) {
            return;
        }
        if (!RDFRendererBase.isMissingType(entity, ont)) {
            return;
        }
        addTriple(entity, RDF_TYPE.getIRI(), entity.getEntityType().getIRI());
    }

    /**
     * @return the graph
     */
    public RDFGraph getGraph() {
        return graph;
    }

    /**
     * Clear the graph.
     */
    public void reset() {
        graph = new RDFGraph();
    }
}
