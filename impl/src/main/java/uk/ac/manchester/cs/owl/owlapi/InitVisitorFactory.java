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

import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;

/**
 * @author ignazio
 */
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
        public K visit(OWLSubClassOfAxiom axiom) {
            OWLClassExpression c = sub ? axiom.getSubClass() : axiom.getSuperClass();
            if (named && c.isAnonymous()) {
                return doDefault(axiom);
            }
            return (K) c;
        }

        @Override
        public K visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLDataPropertyDomainAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLObjectPropertyDomainAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLObjectPropertyRangeAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLObjectPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (sub) {
                return (K) axiom.getSubProperty();
            } else {
                return (K) axiom.getSuperProperty();
            }
        }

        @Override
        public K visit(OWLAnnotationAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLSymmetricObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLDataPropertyRangeAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLFunctionalDataPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLClassAssertionAxiom axiom) {
            OWLClassExpression c = axiom.getClassExpression();
            if (named && c.isAnonymous()) {
                return doDefault(axiom);
            }
            return (K) c;
        }

        @Override
        public K visit(OWLDataPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLTransitiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLSubDataPropertyOfAxiom axiom) {
            if (sub) {
                return (K) axiom.getSubProperty();
            } else {
                return (K) axiom.getSuperProperty();
            }
        }

        @Override
        public K visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLHasKeyAxiom axiom) {
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
    public static class InitIndividualVisitor<K extends OWLObject> extends InitVisitor<K> {

        /**
         * @param sub
         *        true for subclasses
         * @param named
         *        true for named classes
         */
        public InitIndividualVisitor(boolean sub, boolean named) {
            super(sub, named);
        }

        @Override
        public K visit(OWLClassAssertionAxiom axiom) {
            return (K) axiom.getIndividual();
        }
    }

    /**
     * @author ignazio
     * @param <K>
     *        collection type
     */
    @SuppressWarnings("unchecked")
    public static class InitCollectionVisitor<K> implements OWLAxiomVisitorEx<Stream<K>> {

        private final boolean named;

        /**
         * @param named
         *        true for named classes
         */
        public InitCollectionVisitor(boolean named) {
            this.named = named;
        }

        @Override
        public Stream<K> doDefault(Object object) {
            return Stream.empty();
        }

        @Override
        public Stream<K> visit(OWLDisjointClassesAxiom axiom) {
            Stream<OWLClassExpression> stream = axiom.classExpressions();
            if (named) {
                stream = stream.filter(c -> !c.isAnonymous());
            }
            return (Stream<K>) stream;
        }

        @Override
        public Stream<K> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            return (Stream<K>) axiom.properties();
        }

        @Override
        public Stream<K> visit(OWLDifferentIndividualsAxiom axiom) {
            return (Stream<K>) axiom.individuals();
        }

        @Override
        public Stream<K> visit(OWLDisjointDataPropertiesAxiom axiom) {
            return (Stream<K>) axiom.properties();
        }

        @Override
        public Stream<K> visit(OWLDisjointObjectPropertiesAxiom axiom) {
            return (Stream<K>) axiom.properties();
        }

        @Override
        public Stream<K> visit(OWLDisjointUnionAxiom axiom) {
            return Stream.of((K) axiom.getOWLClass());
        }

        @Override
        public Stream<K> visit(OWLEquivalentDataPropertiesAxiom axiom) {
            return (Stream<K>) axiom.properties();
        }

        @Override
        public Stream<K> visit(OWLEquivalentClassesAxiom axiom) {
            Stream<OWLClassExpression> stream = axiom.classExpressions();
            if (named) {
                stream = stream.filter(c -> !c.isAnonymous());
            }
            return (Stream<K>) stream;
        }

        @Override
        public Stream<K> visit(OWLSameIndividualAxiom axiom) {
            return (Stream<K>) axiom.individuals();
        }

        @Override
        public Stream<K> visit(OWLInverseObjectPropertiesAxiom axiom) {
            return (Stream<K>) axiom.properties();
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
