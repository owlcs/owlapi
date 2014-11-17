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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;

/** @author ignazio */
public class InitVisitorFactory {

    private InitVisitorFactory() {}

    /**
     * @author ignazio
     * @param <K>
     *        visitor return type
     */
    @SuppressWarnings("unchecked")
    public static class InitVisitor<K> implements OWLAxiomVisitorEx<K> {

        private final boolean sub;
        private final boolean named;

        /**
         * @param sub
         *        true for subclasses
         * @param named
         *        true for named classes
         */
        public InitVisitor(boolean sub, boolean named) {
            this.sub = sub;
            this.named = named;
        }

        @Override
        public K visit(@Nonnull OWLSubClassOfAxiom axiom) {
            OWLClassExpression c = sub ? axiom.getSubClass() : axiom
                    .getSuperClass();
            if (named && c.isAnonymous()) {
                return doDefault(axiom);
            }
            return (K) c;
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
            if (sub) {
                return (K) axiom.getSubProperty();
            } else {
                return (K) axiom.getSuperProperty();
            }
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(@Nonnull OWLClassAssertionAxiom axiom) {
            OWLClassExpression c = axiom.getClassExpression();
            if (named && c.isAnonymous()) {
                return doDefault(axiom);
            }
            return (K) c;
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
            if (sub) {
                return (K) axiom.getSubProperty();
            } else {
                return (K) axiom.getSuperProperty();
            }
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(@Nonnull OWLHasKeyAxiom axiom) {
            if (named && axiom.getClassExpression().isAnonymous()) {
                return doDefault(axiom);
            }
            return (K) axiom.getClassExpression().asOWLClass();
        }
    }

    /**
     * @author ignazio
     * @param <K>
     *        visitor return type
     */
    @SuppressWarnings("unchecked")
    public static class InitIndividualVisitor<K extends OWLObject> extends
            InitVisitor<K> {

        /**
         * @param sub
         *        true for subclasses
         * @param named
         *        true for named classes
         */
        public InitIndividualVisitor(boolean sub, boolean named) {
            super(sub, named);
        }

        @Nonnull
        @Override
        public K visit(@Nonnull OWLClassAssertionAxiom axiom) {
            return (K) axiom.getIndividual();
        }
    }

    /**
     * @author ignazio
     * @param <K>
     *        collection type
     */
    @SuppressWarnings("unchecked")
    public static class InitCollectionVisitor<K> implements
            OWLAxiomVisitorEx<Collection<K>> {

        private final boolean named;

        /**
         * @param named
         *        true for named classes
         */
        public InitCollectionVisitor(boolean named) {
            this.named = named;
        }

        @Override
        public Collection<K> doDefault(Object object) {
            return Collections.<K> emptySet();
        }

        @Nonnull
        @Override
        public Collection<K> visit(@Nonnull OWLDisjointClassesAxiom axiom) {
            Stream<OWLClassExpression> stream = axiom.classExpressions();
            if (named) {
                stream = stream.filter(c -> !c.isAnonymous());
            }
            return (Collection<K>) asList(stream);
        }

        @Nonnull
        @Override
        public Collection<K> visit(
                @Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
            return (Collection<K>) asList(axiom.properties());
        }

        @Nonnull
        @Override
        public Collection<K> visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
            return (Collection<K>) asList(axiom.individuals());
        }

        @Nonnull
        @Override
        public Collection<K>
                visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
            return (Collection<K>) asList(axiom.properties());
        }

        @Nonnull
        @Override
        public Collection<K> visit(
                @Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
            return (Collection<K>) asList(axiom.properties());
        }

        @Nonnull
        @Override
        public Collection<K> visit(@Nonnull OWLDisjointUnionAxiom axiom) {
            return CollectionFactory.createSet((K) axiom.getOWLClass());
        }

        @Nonnull
        @Override
        public Collection<K> visit(
                @Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
            return (Collection<K>) asList(axiom.properties());
        }

        @Nonnull
        @Override
        public Collection<K> visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
            Stream<OWLClassExpression> stream = axiom.classExpressions();
            if (named) {
                stream = stream.filter(c -> !c.isAnonymous());
            }
            return (Collection<K>) asList(stream);
        }

        @Nonnull
        @Override
        public Collection<K> visit(@Nonnull OWLSameIndividualAxiom axiom) {
            return (Collection<K>) asList(axiom.individuals());
        }

        @Nonnull
        @Override
        public Collection<K> visit(
                @Nonnull OWLInverseObjectPropertiesAxiom axiom) {
            return (Collection<K>) asList(axiom.properties());
        }
    }

//@formatter:off
    static final InitVisitor<OWLClass>                              CLASSSUBNAMED      = new InitVisitor<>          (true,  true);
    static final InitVisitor<OWLClassExpression>                    CLASSEXPRESSIONS   = new InitVisitor<>          (true,  true);
    static final InitVisitor<OWLClass>                              CLASSSUPERNAMED    = new InitVisitor<>          (false, true);
    static final InitCollectionVisitor<OWLClass>                    CLASSCOLLECTIONS   = new InitCollectionVisitor<>(true);
    static final InitCollectionVisitor<OWLObjectPropertyExpression> OPCOLLECTIONS      = new InitCollectionVisitor<>(true);
    static final InitCollectionVisitor<OWLDataPropertyExpression>   DPCOLLECTIONS      = new InitCollectionVisitor<>(true);
    static final InitCollectionVisitor<OWLIndividual>               ICOLLECTIONS       = new InitCollectionVisitor<>(true);
    static final InitVisitor<OWLObjectPropertyExpression>           OPSUBNAMED         = new InitVisitor<>          (true,  true);
    static final InitVisitor<OWLObjectPropertyExpression>           OPSUPERNAMED       = new InitVisitor<>          (false, true);
    static final InitVisitor<OWLDataPropertyExpression>             DPSUBNAMED         = new InitVisitor<>          (true,  true);
    static final InitVisitor<OWLDataPropertyExpression>             DPSUPERNAMED       = new InitVisitor<>          (false, true);
    static final InitVisitor<OWLIndividual>                         INDIVIDUALSUBNAMED = new InitIndividualVisitor<>(true,  true);
    static final InitVisitor<OWLAnnotationSubject>                  ANNOTSUPERNAMED    = new InitVisitor<>          (true,  true);
  //@formatter:on
}
