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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
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
import org.semanticweb.owlapi.util.OWLAxiomVisitorExAdapter;

/** @author ignazio */
public class InitVisitorFactory {

    /**
     * @author ignazio
     * @param <K>
     *        visitor return type
     */
    @SuppressWarnings("unchecked")
    public static class InitVisitor<K> extends OWLAxiomVisitorExAdapter<K> {

        private static final long serialVersionUID = 40000L;
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
            OWLClassExpression c = sub ? axiom.getSubClass() : axiom
                    .getSuperClass();
            if (named && c.isAnonymous()) {
                return null;
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
                return null;
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
                return null;
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

        private static final long serialVersionUID = 40000L;

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
    public static class InitCollectionVisitor<K> extends
            OWLAxiomVisitorExAdapter<Collection<K>> {

        private static final long serialVersionUID = 40000L;
        private final boolean named;

        /**
         * @param named
         *        true for named classes
         */
        public InitCollectionVisitor(boolean named) {
            this.named = named;
        }

        @Override
        public Collection<K> visit(OWLDisjointClassesAxiom axiom) {
            List<OWLClassExpression> list = new ArrayList<OWLClassExpression>(
                    axiom.getClassExpressions());
            if (named) {
                deleteAnonymousClasses(list);
            }
            return (Collection<K>) list;
        }

        private void deleteAnonymousClasses(List<OWLClassExpression> list) {
            for (int i = 0; i < list.size();) {
                if (list.get(i).isAnonymous()) {
                    list.remove(i);
                } else {
                    i++;
                }
            }
        }

        @Override
        public Collection<K> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }

        @Override
        public Collection<K> visit(OWLDifferentIndividualsAxiom axiom) {
            return (Collection<K>) axiom.getIndividuals();
        }

        @Override
        public Collection<K> visit(OWLDisjointDataPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }

        @Override
        public Collection<K> visit(OWLDisjointObjectPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }

        @Override
        public Collection<K> visit(OWLDisjointUnionAxiom axiom) {
            return (Collection<K>) Collections.singleton(axiom.getOWLClass());
        }

        @Override
        public Collection<K> visit(OWLEquivalentDataPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }

        @Override
        public Collection<K> visit(OWLEquivalentClassesAxiom axiom) {
            List<OWLClassExpression> list = new ArrayList<OWLClassExpression>(
                    axiom.getClassExpressions());
            if (named) {
                deleteAnonymousClasses(list);
            }
            return (Collection<K>) list;
        }

        @Override
        public Collection<K> visit(OWLSameIndividualAxiom axiom) {
            return (Collection<K>) axiom.getIndividuals();
        }

        @Override
        public Collection<K> visit(OWLInverseObjectPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }
    }

    static final InitVisitor<OWLClass> classsubnamed = new InitVisitor<OWLClass>(
            true, true);
    static final InitVisitor<OWLClassExpression> classexpressions = new InitVisitor<OWLClassExpression>(
            true, true);
    static final InitVisitor<OWLClass> classsupernamed = new InitVisitor<OWLClass>(
            false, true);
    static final InitCollectionVisitor<OWLClass> classcollections = new InitCollectionVisitor<OWLClass>(
            true);
    static final InitCollectionVisitor<OWLObjectPropertyExpression> opcollections = new InitCollectionVisitor<OWLObjectPropertyExpression>(
            true);
    static final InitCollectionVisitor<OWLDataPropertyExpression> dpcollections = new InitCollectionVisitor<OWLDataPropertyExpression>(
            true);
    static final InitCollectionVisitor<OWLIndividual> icollections = new InitCollectionVisitor<OWLIndividual>(
            true);
    static final InitVisitor<OWLObjectPropertyExpression> opsubnamed = new InitVisitor<OWLObjectPropertyExpression>(
            true, true);
    static final InitVisitor<OWLObjectPropertyExpression> opsupernamed = new InitVisitor<OWLObjectPropertyExpression>(
            false, true);
    static final InitVisitor<OWLDataPropertyExpression> dpsubnamed = new InitVisitor<OWLDataPropertyExpression>(
            true, true);
    static final InitVisitor<OWLDataPropertyExpression> dpsupernamed = new InitVisitor<OWLDataPropertyExpression>(
            false, true);
    static final InitVisitor<OWLIndividual> individualsubnamed = new InitIndividualVisitor<OWLIndividual>(
            true, true);
    static final InitVisitor<OWLAnnotationSubject> annotsupernamed = new InitVisitor<OWLAnnotationSubject>(
            true, true);
}
