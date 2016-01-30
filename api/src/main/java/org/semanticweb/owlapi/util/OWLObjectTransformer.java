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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * Transform axioms by rewriting parts of them.
 * 
 * @author Ignazio
 * @since 4.1.4
 * @param <T>
 *        type to transform
 */
public class OWLObjectTransformer<T> {

    private Function<T, T> transformer;
    private Predicate<Object> predicate;
    private Class<T> witness;
    private OWLDataFactory df;

    /**
     * @param predicate
     *        the predicate to match the axioms to rebuild
     * @param transformer
     *        the transformer to apply
     * @param df
     *        data factory to use for changes
     * @param witness
     *        witness class for the transformer
     */
    public OWLObjectTransformer(Predicate<Object> predicate, Function<T, T> transformer, OWLDataFactory df,
        Class<T> witness) {
        this.predicate = checkNotNull(predicate, "predicate cannot be null");
        this.transformer = checkNotNull(transformer, "transformer cannot be null");
        this.df = checkNotNull(df, "df cannot be null");
        this.witness = checkNotNull(witness, "witness cannot be null");
    }

    /**
     * Create the required changes for this transformation to be applied to the
     * input. Note: these are AxiomChangeData changes, not ontology specific
     * changes. There is no requirement for the input to be an ontology or
     * included in an ontology.
     * 
     * @param o
     *        object to transform. Must be an axiom or an ontology for the
     *        change to be meaningful.
     * @return A list of axiom changes that should be applied.
     */
    public List<AxiomChangeData> change(OWLObject o) {
        checkNotNull(o, "o cannot be null");
        List<AxiomChangeData> changes = new ArrayList<>();
        // no ontology changes will be collected
        Visitor<T> v = new Visitor<>(new ArrayList<OWLOntologyChange>(), changes, predicate, transformer, df, witness);
        o.accept(v);
        return changes;
    }

    /**
     * Create the required changes for this transformation to be applied to the
     * input. These changes are specific to the input ontology.
     * 
     * @param ontology
     *        ontology to transform.
     * @return A list of changes that should be applied.
     */
    public List<OWLOntologyChange> change(OWLOntology ontology) {
        checkNotNull(ontology, "ontology cannot be null");
        List<AxiomChangeData> changes = new ArrayList<>();
        List<OWLOntologyChange> ontologyChanges = new ArrayList<>();
        Visitor<T> v = new Visitor<>(ontologyChanges, changes, predicate, transformer, df, witness);
        ontology.accept(v);
        for (AxiomChangeData change : changes) {
            ontologyChanges.add(change.createOntologyChange(ontology));
        }
        return ontologyChanges;
    }

    private static class Visitor<T> implements OWLObjectVisitorEx<Object> {

        private List<AxiomChangeData> changes;
        private List<OWLOntologyChange> ontologyChanges;
        private Predicate<Object> predicate;
        private Function<T, T> transformer;
        private Class<T> witness;
        private OWLDataFactory df;

        Visitor(List<OWLOntologyChange> ontologyChanges, List<AxiomChangeData> changes, Predicate<Object> predicate,
            Function<T, T> transformer, OWLDataFactory df, Class<T> witness) {
            this.changes = changes;
            this.ontologyChanges = ontologyChanges;
            this.predicate = predicate;
            this.transformer = transformer;
            this.df = df;
            this.witness = witness;
        }

        /**
         * Check and transform an axiom.
         * 
         * @param axiom
         *        axiom to check
         * @return the transformed axiom, or null if the axiom was not
         *         transformed
         */
        @SuppressWarnings({ "unchecked" })
        @Nullable
        protected OWLAxiom checkAxiom(OWLAxiom axiom) {
            if (witness.isInstance(axiom)) {
                OWLAxiom transform = ((Function<OWLAxiom, OWLAxiom>) transformer).apply(axiom);
                if (update(transform, axiom) == transform) {
                    return transform;
                }
            }
            return null;
        }

        protected OWLAxiom update(OWLAxiom transform, OWLAxiom axiom) {
            if (!axiom.equals(transform)) {
                changes.add(new RemoveAxiomData(axiom));
                changes.add(new AddAxiomData(transform));
                return transform;
            }
            return axiom;
        }

        /**
         * Check and transform an object.
         * 
         * @param o
         *        object to check
         * @return the transformed object, or null if the axiom was not
         *         transformed
         */
        @SuppressWarnings({ "unchecked" })
        @Nullable
        protected <Q> Q check(Q o) {
            if (witness.isInstance(o)) {
                Q transform = (Q) transformer.apply(witness.cast(o));
                if (transform != o) {
                    return transform;
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        protected <Q extends OWLObject> Q t(Q t) {
            return (Q) t.accept(this);
        }

        @SuppressWarnings("unchecked")
        protected OWLFacet t(OWLFacet t) {
            return (OWLFacet) transformer.apply((T) t);
        }

        protected <Q extends OWLObject> Collection<Q> t(Stream<Q> c) {
            return asList(c.map(this::t));
        }

        protected <Q extends OWLObject> List<Q> t(List<Q> c) {
            List<Q> list = new ArrayList<>();
            for (Q t : c) {
                list.add(t(t));
            }
            return list;
        }

        @Override
        public Object visit(OWLDeclarationAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDeclarationAxiom(t(axiom.getEntity()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLDatatypeDefinitionAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDatatypeDefinitionAxiom(t(axiom.getDatatype()), t(axiom
                .getDataRange()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLAnnotationAssertionAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLAnnotationAssertionAxiom(t(axiom.getSubject()), t(axiom
                .getAnnotation()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLSubAnnotationPropertyOfAxiom(t(axiom.getSubProperty()), t(axiom
                .getSuperProperty()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLAnnotationPropertyDomainAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLAnnotationPropertyDomainAxiom(t(axiom.getProperty()), t(axiom
                .getDomain()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLAnnotationPropertyRangeAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLAnnotationPropertyRangeAxiom(t(axiom.getProperty()), t(axiom
                .getRange()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLSubClassOfAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLSubClassOfAxiom(t(axiom.getSubClass()), t(axiom.getSuperClass()), t(
                axiom.annotations())));
        }

        @Override
        public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLNegativeObjectPropertyAssertionAxiom(t(axiom.getProperty()), t(axiom
                .getSubject()), t(axiom.getObject()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLAsymmetricObjectPropertyAxiom(t(axiom.getProperty()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLReflexiveObjectPropertyAxiom(t(axiom.getProperty()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLDisjointClassesAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDisjointClassesAxiom(t(axiom.classExpressions()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLDataPropertyDomainAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDataPropertyDomainAxiom(t(axiom.getProperty()), t(axiom
                .getDomain()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLObjectPropertyDomainAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLObjectPropertyDomainAxiom(t(axiom.getProperty()), t(axiom
                .getDomain()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLEquivalentObjectPropertiesAxiom(t(axiom.properties()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLNegativeDataPropertyAssertionAxiom(t(axiom.getProperty()), t(axiom
                .getSubject()), t(axiom.getObject()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLDifferentIndividualsAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDifferentIndividualsAxiom(t(axiom.individuals()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDisjointDataPropertiesAxiom(t(axiom.properties()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDisjointObjectPropertiesAxiom(t(axiom.properties()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLObjectPropertyRangeAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLObjectPropertyRangeAxiom(t(axiom.getProperty()), t(axiom
                .getRange()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLObjectPropertyAssertionAxiom(t(axiom.getProperty()), t(axiom
                .getSubject()), t(axiom.getObject()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLFunctionalObjectPropertyAxiom(t(axiom.getProperty()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLSubObjectPropertyOfAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLSubObjectPropertyOfAxiom(t(axiom.getSubProperty()), t(axiom
                .getSuperProperty()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLDisjointUnionAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDisjointUnionAxiom(t(axiom.getOWLClass()), t(axiom
                .classExpressions()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLSymmetricObjectPropertyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLSymmetricObjectPropertyAxiom(t(axiom.getProperty()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLDataPropertyRangeAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDataPropertyRangeAxiom(t(axiom.getProperty()), t(axiom.getRange()),
                t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLFunctionalDataPropertyAxiom(t(axiom.getProperty()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLEquivalentDataPropertiesAxiom(t(axiom.properties()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLClassAssertionAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLClassAssertionAxiom(t(axiom.getClassExpression()), t(axiom
                .getIndividual()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLEquivalentClassesAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLEquivalentClassesAxiom(t(axiom.classExpressions()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLDataPropertyAssertionAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLDataPropertyAssertionAxiom(t(axiom.getProperty()), t(axiom
                .getSubject()), t(axiom.getObject()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLTransitiveObjectPropertyAxiom(t(axiom.getProperty()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLIrreflexiveObjectPropertyAxiom(t(axiom.getProperty()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLSubDataPropertyOfAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLSubDataPropertyOfAxiom(t(axiom.getSubProperty()), t(axiom
                .getSuperProperty()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLInverseFunctionalObjectPropertyAxiom(t(axiom.getProperty()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLSameIndividualAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLSameIndividualAxiom(t(axiom.individuals()), t(axiom
                .annotations())));
        }

        @Override
        public Object visit(OWLSubPropertyChainOfAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLSubPropertyChainOfAxiom(t(axiom.getPropertyChain()), t(axiom
                .getSuperProperty()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLInverseObjectPropertiesAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLInverseObjectPropertiesAxiom(t(axiom.getFirstProperty()), t(axiom
                .getSecondProperty()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLHasKeyAxiom axiom) {
            return visitAxiom(axiom, () -> df.getOWLHasKeyAxiom(t(axiom.getClassExpression()), t(axiom
                .propertyExpressions()), t(axiom.annotations())));
        }

        @Override
        public Object visit(SWRLRule axiom) {
            return visitAxiom(axiom, () -> df.getSWRLRule(t(axiom.body()), t(axiom.head()), t(axiom.annotations())));
        }

        @Override
        public Object visit(OWLClass ce) {
            return visit(ce, () -> df.getOWLClass(t(ce.getIRI())));
        }

        @Override
        public Object visit(OWLObjectIntersectionOf ce) {
            return visit(ce, () -> df.getOWLObjectIntersectionOf(t(ce.operands())));
        }

        @Override
        public Object visit(OWLObjectUnionOf ce) {
            return visit(ce, () -> df.getOWLObjectUnionOf(t(ce.operands())));
        }

        @Override
        public Object visit(OWLObjectComplementOf ce) {
            return visit(ce, () -> df.getOWLObjectComplementOf(t(ce.getOperand())));
        }

        @Override
        public Object visit(OWLObjectSomeValuesFrom ce) {
            return visit(ce, () -> df.getOWLObjectSomeValuesFrom(t(ce.getProperty()), t(ce.getFiller())));
        }

        @Override
        public Object visit(OWLObjectAllValuesFrom ce) {
            return visit(ce, () -> df.getOWLObjectAllValuesFrom(t(ce.getProperty()), t(ce.getFiller())));
        }

        @Override
        public Object visit(OWLObjectHasValue ce) {
            return visit(ce, () -> df.getOWLObjectHasValue(t(ce.getProperty()), t(ce.getFiller())));
        }

        @Override
        public Object visit(OWLObjectMinCardinality ce) {
            return visit(ce, () -> df.getOWLObjectMinCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce
                .getFiller())));
        }

        @Override
        public Object visit(OWLObjectExactCardinality ce) {
            return visit(ce, () -> df.getOWLObjectExactCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce
                .getFiller())));
        }

        @Override
        public Object visit(OWLObjectMaxCardinality ce) {
            return visit(ce, () -> df.getOWLObjectMaxCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce
                .getFiller())));
        }

        @Override
        public Object visit(OWLObjectHasSelf ce) {
            return visit(ce, () -> df.getOWLObjectHasSelf(t(ce.getProperty())));
        }

        @Override
        public Object visit(OWLObjectOneOf ce) {
            return visit(ce, () -> df.getOWLObjectOneOf(t(ce.individuals())));
        }

        @Override
        public Object visit(OWLDataSomeValuesFrom ce) {
            return visit(ce, () -> df.getOWLDataSomeValuesFrom(t(ce.getProperty()), t(ce.getFiller())));
        }

        @Override
        public Object visit(OWLDataAllValuesFrom ce) {
            return visit(ce, () -> df.getOWLDataAllValuesFrom(t(ce.getProperty()), t(ce.getFiller())));
        }

        @Override
        public Object visit(OWLDataHasValue ce) {
            return visit(ce, () -> df.getOWLDataHasValue(t(ce.getProperty()), t(ce.getFiller())));
        }

        @Override
        public Object visit(OWLDataMinCardinality ce) {
            return visit(ce, () -> df.getOWLDataMinCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce
                .getFiller())));
        }

        @Override
        public Object visit(OWLDataExactCardinality ce) {
            return visit(ce, () -> df.getOWLDataExactCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce
                .getFiller())));
        }

        @Override
        public Object visit(OWLDataMaxCardinality ce) {
            return visit(ce, () -> df.getOWLDataMaxCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce
                .getFiller())));
        }

        @Override
        public Object visit(OWLDatatype node) {
            return visit(node, () -> df.getOWLDatatype(t(node.getIRI())));
        }

        @Override
        public Object visit(OWLDataComplementOf node) {
            return visit(node, () -> df.getOWLDataComplementOf(t(node.getDataRange())));
        }

        @Override
        public Object visit(OWLDataOneOf node) {
            return visit(node, () -> df.getOWLDataOneOf(t(node.values())));
        }

        @Override
        public Object visit(OWLDataIntersectionOf node) {
            return visit(node, () -> df.getOWLDataIntersectionOf(t(node.operands())));
        }

        @Override
        public Object visit(OWLDataUnionOf node) {
            return visit(node, () -> df.getOWLDataUnionOf(t(node.operands())));
        }

        @Override
        public Object visit(OWLDatatypeRestriction node) {
            return visit(node, () -> df.getOWLDatatypeRestriction(t(node.getDatatype()), t(node.facetRestrictions())));
        }

        @Override
        public Object visit(OWLLiteral node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.test(node)) {
                return node;
            }
            // plain literal is a terminal; if the transform did not make a
            // change, then no change is required
            if (node.isRDFPlainLiteral()) {
                return node;
            }
            return df.getOWLLiteral(node.getLiteral(), t(node.getDatatype()));
        }

        @Override
        public Object visit(OWLFacetRestriction node) {
            return visit(node, () -> df.getOWLFacetRestriction(t(node.getFacet()), t(node.getFacetValue())));
        }

        @Override
        public Object visit(OWLObjectProperty property) {
            return visit(property, () -> df.getOWLObjectProperty(t(property.getIRI())));
        }

        @Override
        public Object visit(OWLObjectInverseOf property) {
            return visit(property, () -> df.getOWLObjectInverseOf(t(property.getNamedProperty())));
        }

        @Override
        public Object visit(OWLDataProperty property) {
            return visit(property, () -> df.getOWLDataProperty(t(property.getIRI())));
        }

        @Override
        public Object visit(OWLAnnotationProperty property) {
            return visit(property, () -> df.getOWLAnnotationProperty(t(property.getIRI())));
        }

        @Override
        public Object visit(OWLNamedIndividual individual) {
            return visit(individual, () -> df.getOWLNamedIndividual(t(individual.getIRI())));
        }

        @Override
        public Object visit(OWLAnnotation node) {
            return visit(node, () -> df.getOWLAnnotation(t(node.getProperty()), t(node.getValue()), t(node
                .annotations())));
        }

        @Override
        public Object visit(IRI iri) {
            OWLObject transform = check(iri);
            if (transform != null) {
                return transform;
            }
            // IRI is a terminal; if the transform did not make a change, then
            // no change is required
            return iri;
        }

        @Override
        public Object visit(OWLAnonymousIndividual individual) {
            OWLObject transform = check(individual);
            if (transform != null) {
                return transform;
            }
            // OWLAnonymousIndividual is a terminal; if the transform did not
            // make a change, then no change is required
            return individual;
        }

        @Override
        public Object visit(SWRLClassAtom node) {
            return visit(node, () -> df.getSWRLClassAtom(t(node.getPredicate()), t(node.getArgument())));
        }

        @Override
        public Object visit(SWRLDataRangeAtom node) {
            return visit(node, () -> df.getSWRLDataRangeAtom(t(node.getPredicate()), t(node.getArgument())));
        }

        @Override
        public Object visit(SWRLObjectPropertyAtom node) {
            return visit(node, () -> df.getSWRLObjectPropertyAtom(t(node.getPredicate()), t(node.getFirstArgument()), t(
                node.getSecondArgument())));
        }

        @Override
        public Object visit(SWRLDataPropertyAtom node) {
            return visit(node, () -> df.getSWRLDataPropertyAtom(t(node.getPredicate()), t(node.getFirstArgument()), t(
                node.getSecondArgument())));
        }

        @Override
        public Object visit(SWRLBuiltInAtom node) {
            return visit(node, () -> df.getSWRLBuiltInAtom(t(node.getPredicate()), t(node.getArguments())));
        }

        @Override
        public Object visit(SWRLVariable node) {
            return visit(node, () -> df.getSWRLVariable(t(node.getIRI())));
        }

        @Override
        public Object visit(SWRLIndividualArgument node) {
            return visit(node, () -> df.getSWRLIndividualArgument(t(node.getIndividual())));
        }

        @Override
        public Object visit(SWRLLiteralArgument node) {
            return visit(node, () -> df.getSWRLLiteralArgument(t(node.getLiteral())));
        }

        @Override
        public Object visit(SWRLSameIndividualAtom node) {
            return visit(node, () -> df.getSWRLSameIndividualAtom(t(node.getFirstArgument()), t(node
                .getSecondArgument())));
        }

        @Override
        public Object visit(SWRLDifferentIndividualsAtom node) {
            return visit(node, () -> df.getSWRLDifferentIndividualsAtom(t(node.getFirstArgument()), t(node
                .getSecondArgument())));
        }

        protected Object visit(OWLObject node, Supplier<Object> rebuilder) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.test(node)) {
                return node;
            }
            return rebuilder.get();
        }

        protected Object visitAxiom(OWLAxiom node, Supplier<OWLAxiom> rebuilder) {
            OWLObject transform = checkAxiom(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.test(node)) {
                return node;
            }
            return update(rebuilder.get(), node);
        }

        @Override
        public Object visit(OWLOntology ontology) {
            AxiomType.AXIOM_TYPES.stream().flatMap(t -> ontology.axioms(t)).forEach(ax -> ax.accept(this));
            ontology.annotations().forEach(a -> {
                OWLAnnotation transform = t(a);
                if (transform != a) {
                    ontologyChanges.add(new RemoveOntologyAnnotation(ontology, a));
                    ontologyChanges.add(new AddOntologyAnnotation(ontology, transform));
                }
            });
            ontology.importsDeclarations().forEach(id -> {
                OWLImportsDeclaration transform = check(id);
                if (transform != null) {
                    ontologyChanges.add(new RemoveImport(ontology, id));
                    ontologyChanges.add(new AddImport(ontology, transform));
                }
            });
            OWLOntologyID transform = check(ontology.getOntologyID());
            if (transform != null) {
                ontologyChanges.add(new SetOntologyID(ontology, transform));
            }
            // the ontology object is never modified
            return ontology;
        }
    }
}
