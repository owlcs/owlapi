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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AxiomChangeData;
import org.semanticweb.owlapi.change.RemoveAxiomData;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

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
    public OWLObjectTransformer(@Nonnull Predicate<Object> predicate, @Nonnull Function<T, T> transformer,
        @Nonnull OWLDataFactory df, @Nonnull Class<T> witness) {
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
    @Nonnull
    public List<AxiomChangeData> change(@Nonnull OWLObject o) {
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
    @Nonnull
    public List<OWLOntologyChange> change(@Nonnull OWLOntology ontology) {
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
                if (update(transform, axiom) != null) {
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
            return null;
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

        protected <Q extends OWLObject> Set<Q> t(Collection<Q> c) {
            Set<Q> list = new HashSet<>();
            for (Q t : c) {
                list.add(t(t));
            }
            return list;
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
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDeclarationAxiom(t(axiom.getEntity()), t(axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLDatatypeDefinitionAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDatatypeDefinitionAxiom(t(axiom.getDatatype()), t(axiom.getDataRange()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLAnnotationAssertionAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLAnnotationAssertionAxiom(t(axiom.getSubject()), t(axiom.getAnnotation()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLSubAnnotationPropertyOfAxiom(t(axiom.getSubProperty()), t(axiom.getSuperProperty()),
                t(axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLAnnotationPropertyDomainAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLAnnotationPropertyDomainAxiom(t(axiom.getProperty()), t(axiom.getDomain()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLAnnotationPropertyRangeAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLAnnotationPropertyRangeAxiom(t(axiom.getProperty()), t(axiom.getRange()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLSubClassOfAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLSubClassOfAxiom(t(axiom.getSubClass()), t(axiom.getSuperClass()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLNegativeObjectPropertyAssertionAxiom(t(axiom.getProperty()), t(axiom.getSubject()),
                t(axiom.getObject()), t(axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLAsymmetricObjectPropertyAxiom(t(axiom.getProperty()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLReflexiveObjectPropertyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLReflexiveObjectPropertyAxiom(t(axiom.getProperty()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLDisjointClassesAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDisjointClassesAxiom(t(axiom.getClassExpressions()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLDataPropertyDomainAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDataPropertyDomainAxiom(t(axiom.getProperty()), t(axiom.getDomain()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLObjectPropertyDomainAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLObjectPropertyDomainAxiom(t(axiom.getProperty()), t(axiom.getDomain()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLEquivalentObjectPropertiesAxiom(t(axiom.getProperties()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLNegativeDataPropertyAssertionAxiom(t(axiom.getProperty()), t(axiom.getSubject()), t(
                axiom.getObject()), t(axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLDifferentIndividualsAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDifferentIndividualsAxiom(t(axiom.getIndividuals()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDisjointDataPropertiesAxiom(t(axiom.getProperties()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDisjointObjectPropertiesAxiom(t(axiom.getProperties()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLObjectPropertyRangeAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLObjectPropertyRangeAxiom(t(axiom.getProperty()), t(axiom.getRange()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLObjectPropertyAssertionAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLObjectPropertyAssertionAxiom(t(axiom.getProperty()), t(axiom.getSubject()), t(axiom
                .getObject()), t(axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLFunctionalObjectPropertyAxiom(t(axiom.getProperty()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLSubObjectPropertyOfAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLSubObjectPropertyOfAxiom(t(axiom.getSubProperty()), t(axiom.getSuperProperty()), t(
                axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLDisjointUnionAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDisjointUnionAxiom(t(axiom.getOWLClass()), t(axiom.getClassExpressions()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLSymmetricObjectPropertyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLSymmetricObjectPropertyAxiom(t(axiom.getProperty()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLDataPropertyRangeAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDataPropertyRangeAxiom(t(axiom.getProperty()), t(axiom.getRange()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLFunctionalDataPropertyAxiom(t(axiom.getProperty()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLEquivalentDataPropertiesAxiom(t(axiom.getProperties()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLClassAssertionAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLClassAssertionAxiom(t(axiom.getClassExpression()), t(axiom.getIndividual()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLEquivalentClassesAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLEquivalentClassesAxiom(t(axiom.getClassExpressions()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLDataPropertyAssertionAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLDataPropertyAssertionAxiom(t(axiom.getProperty()), t(axiom.getSubject()), t(axiom
                .getObject()), t(axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLTransitiveObjectPropertyAxiom(t(axiom.getProperty()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLIrreflexiveObjectPropertyAxiom(t(axiom.getProperty()), t(axiom.getAnnotations())),
                axiom);
        }

        @Override
        public Object visit(OWLSubDataPropertyOfAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLSubDataPropertyOfAxiom(t(axiom.getSubProperty()), t(axiom.getSuperProperty()), t(
                axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLInverseFunctionalObjectPropertyAxiom(t(axiom.getProperty()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLSameIndividualAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLSameIndividualAxiom(t(axiom.getIndividuals()), t(axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLSubPropertyChainOfAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLSubPropertyChainOfAxiom(t(axiom.getPropertyChain()), t(axiom.getSuperProperty()), t(
                axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLInverseObjectPropertiesAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLInverseObjectPropertiesAxiom(t(axiom.getFirstProperty()), t(axiom
                .getSecondProperty()), t(axiom.getAnnotations())), axiom);
        }

        @Override
        public Object visit(OWLHasKeyAxiom axiom) {
            OWLAxiom transform = checkAxiom(axiom);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(axiom)) {
                return axiom;
            }
            return update(df.getOWLHasKeyAxiom(t(axiom.getClassExpression()), t(axiom.getPropertyExpressions()), t(axiom
                .getAnnotations())), axiom);
        }

        @Override
        public Object visit(SWRLRule rule) {
            OWLAxiom transform = checkAxiom(rule);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(rule)) {
                return rule;
            }
            return update(df.getSWRLRule(t(rule.getBody()), t(rule.getHead()), t(rule.getAnnotations())), rule);
        }

        @Override
        public Object visit(OWLClass ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLClass(t(ce.getIRI()));
        }

        @Override
        public Object visit(OWLObjectIntersectionOf ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectIntersectionOf(t(ce.getOperands()));
        }

        @Override
        public Object visit(OWLObjectUnionOf ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectUnionOf(t(ce.getOperands()));
        }

        @Override
        public Object visit(OWLObjectComplementOf ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectComplementOf(t(ce.getOperand()));
        }

        @Override
        public Object visit(OWLObjectSomeValuesFrom ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectSomeValuesFrom(t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLObjectAllValuesFrom ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectAllValuesFrom(t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLObjectHasValue ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectHasValue(t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLObjectMinCardinality ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectMinCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLObjectExactCardinality ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectExactCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLObjectMaxCardinality ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectMaxCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLObjectHasSelf ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectHasSelf(t(ce.getProperty()));
        }

        @Override
        public Object visit(OWLObjectOneOf ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLObjectOneOf(t(ce.getIndividuals()));
        }

        @Override
        public Object visit(OWLDataSomeValuesFrom ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLDataSomeValuesFrom(t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLDataAllValuesFrom ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLDataAllValuesFrom(t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLDataHasValue ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLDataHasValue(t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLDataMinCardinality ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLDataMinCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLDataExactCardinality ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLDataExactCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLDataMaxCardinality ce) {
            OWLObject transform = check(ce);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(ce)) {
                return ce;
            }
            return df.getOWLDataMaxCardinality(ce.getCardinality(), t(ce.getProperty()), t(ce.getFiller()));
        }

        @Override
        public Object visit(OWLDatatype node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getOWLDatatype(t(node.getIRI()));
        }

        @Override
        public Object visit(OWLDataComplementOf node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getOWLDataComplementOf(t(node.getDataRange()));
        }

        @Override
        public Object visit(OWLDataOneOf node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getOWLDataOneOf(t(node.getValues()));
        }

        @Override
        public Object visit(OWLDataIntersectionOf node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getOWLDataIntersectionOf(t(node.getOperands()));
        }

        @Override
        public Object visit(OWLDataUnionOf node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getOWLDataUnionOf(t(node.getOperands()));
        }

        @Override
        public Object visit(OWLDatatypeRestriction node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getOWLDatatypeRestriction(t(node.getDatatype()), t(node.getFacetRestrictions()));
        }

        @Override
        public Object visit(OWLLiteral node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
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
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getOWLFacetRestriction(t(node.getFacet()), t(node.getFacetValue()));
        }

        @Override
        public Object visit(OWLObjectProperty property) {
            OWLObject transform = check(property);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(property)) {
                return property;
            }
            return df.getOWLObjectProperty(t(property.getIRI()));
        }

        @Override
        public Object visit(OWLObjectInverseOf property) {
            OWLObject transform = check(property);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(property)) {
                return property;
            }
            return df.getOWLObjectInverseOf(t(property.getNamedProperty()));
        }

        @Override
        public Object visit(OWLDataProperty property) {
            OWLObject transform = check(property);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(property)) {
                return property;
            }
            return df.getOWLDataProperty(t(property.getIRI()));
        }

        @Override
        public Object visit(OWLAnnotationProperty property) {
            OWLObject transform = check(property);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(property)) {
                return property;
            }
            return df.getOWLAnnotationProperty(t(property.getIRI()));
        }

        @Override
        public Object visit(OWLNamedIndividual individual) {
            OWLObject transform = check(individual);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(individual)) {
                return individual;
            }
            return df.getOWLNamedIndividual(t(individual.getIRI()));
        }

        @Override
        public Object visit(OWLAnnotation node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getOWLAnnotation(t(node.getProperty()), t(node.getValue()), t(node.getAnnotations()));
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
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLClassAtom(t(node.getPredicate()), t(node.getArgument()));
        }

        @Override
        public Object visit(SWRLDataRangeAtom node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLDataRangeAtom(t(node.getPredicate()), t(node.getArgument()));
        }

        @Override
        public Object visit(SWRLObjectPropertyAtom node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLObjectPropertyAtom(t(node.getPredicate()), t(node.getFirstArgument()), t(node
                .getSecondArgument()));
        }

        @Override
        public Object visit(SWRLDataPropertyAtom node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLDataPropertyAtom(t(node.getPredicate()), t(node.getFirstArgument()), t(node
                .getSecondArgument()));
        }

        @Override
        public Object visit(SWRLBuiltInAtom node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLBuiltInAtom(t(node.getPredicate()), t(node.getArguments()));
        }

        @Override
        public Object visit(SWRLVariable node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLVariable(t(node.getIRI()));
        }

        @Override
        public Object visit(SWRLIndividualArgument node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLIndividualArgument(t(node.getIndividual()));
        }

        @Override
        public Object visit(SWRLLiteralArgument node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLLiteralArgument(t(node.getLiteral()));
        }

        @Override
        public Object visit(SWRLSameIndividualAtom node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLSameIndividualAtom(t(node.getFirstArgument()), t(node.getSecondArgument()));
        }

        @Override
        public Object visit(SWRLDifferentIndividualsAtom node) {
            OWLObject transform = check(node);
            if (transform != null) {
                return transform;
            }
            if (!predicate.apply(node)) {
                return node;
            }
            return df.getSWRLDifferentIndividualsAtom(t(node.getFirstArgument()), t(node.getSecondArgument()));
        }

        @Override
        public Object visit(OWLOntology ontology) {
            for (AxiomType<?> t : AxiomType.AXIOM_TYPES) {
                for (OWLAxiom ax : ontology.getAxioms(t)) {
                    ax.accept(this);
                }
            }
            for (OWLAnnotation a : ontology.getAnnotations()) {
                OWLAnnotation transform = t(a);
                if (transform != a) {
                    ontologyChanges.add(new RemoveOntologyAnnotation(ontology, a));
                    ontologyChanges.add(new AddOntologyAnnotation(ontology, transform));
                }
            }
            for (OWLImportsDeclaration id : ontology.getImportsDeclarations()) {
                OWLImportsDeclaration transform = check(id);
                if (transform != null) {
                    ontologyChanges.add(new RemoveImport(ontology, id));
                    ontologyChanges.add(new AddImport(ontology, transform));
                }
            }
            OWLOntologyID transform = check(ontology.getOntologyID());
            if (transform != null) {
                ontologyChanges.add(new SetOntologyID(ontology, transform));
            }
            // the ontology object is never modified
            return ontology;
        }
    }
}
