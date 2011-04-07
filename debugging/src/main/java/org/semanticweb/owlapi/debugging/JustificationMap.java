/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.debugging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;
import org.semanticweb.owlapi.util.OWLEntityCollector;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 30-Apr-2007<br><br>
 */
public class JustificationMap {

    private Set<OWLAxiom> axioms;

    private Set<OWLAxiom> rootAxioms;

    private Set<OWLAxiom> usedAxioms;

    private Map<OWLAxiom, Set<OWLAxiom>> map;

    private Map<OWLEntity, Set<OWLAxiom>> axiomsByRHS;

    private Map<OWLEntity, Set<OWLAxiom>> axiomsByLHS;

    private OWLClassExpression desc;


    public JustificationMap(OWLClassExpression desc, Set<OWLAxiom> axioms) {
        this.axioms = axioms;
        this.desc = desc;
        this.rootAxioms = new HashSet<OWLAxiom>();
        map = new HashMap<OWLAxiom, Set<OWLAxiom>>();
        usedAxioms = new HashSet<OWLAxiom>();
        axiomsByRHS = new HashMap<OWLEntity, Set<OWLAxiom>>();
        axiomsByLHS = new HashMap<OWLEntity, Set<OWLAxiom>>();
        createMap();
    }


    private void createMap() {
        for (OWLAxiom ax : axioms) {
            OWLAxiomPartExtractor extractor = new OWLAxiomPartExtractor();
            ax.accept(extractor);
            Set<OWLEntity> rhscollected=new HashSet<OWLEntity>();
            OWLEntityCollector rhsCollector = new OWLEntityCollector(rhscollected);
            for (OWLObject rhsObject : extractor.getRHS()) {
                rhsObject.accept(rhsCollector);
            }
            for (OWLEntity rhsEntity : rhscollected) {
                index(rhsEntity, axiomsByRHS, ax);
            }
            Set<OWLEntity> lhscollected=new HashSet<OWLEntity>();
            OWLEntityCollector lhsCollector = new OWLEntityCollector(lhscollected);
            for (OWLObject lhsObject : extractor.getLHS()) {
                lhsObject.accept(lhsCollector);
            }
            for (OWLEntity lhsEntity : lhscollected) {
                index(lhsEntity, axiomsByLHS, ax);
            }
        }
        buildChildren(desc);
    }

    private Set<OWLAxiom> getAxiomsByLHS(OWLEntity lhs) {
        Set<OWLAxiom> axiomSet = axiomsByLHS.get(lhs);
        if (axiomSet != null) {
            Set<OWLAxiom> ts = new TreeSet<OWLAxiom>(new OWLAxiomComparator());
            ts.addAll(axiomSet);
            return ts;
        }
        else {
            return Collections.emptySet();
        }
    }


//    private Set<OWLAxiom> getAxiomsByRHS(OWLEntity rhs) {
//        Set<OWLAxiom> axioms = axiomsByRHS.get(rhs);
//        if (axioms != null) {
//            return axioms;
//        }
//        else {
//            return Collections.emptySet();
//        }
//    }

    private void buildChildren(OWLClassExpression seed) {
        // Return the axioms that have the entity on the LHS
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        for (OWLEntity ent : seed.getSignature()) {
            Set<OWLAxiom> axs = getAxiomsByLHS(ent);
            for (OWLAxiom ax : axs) {
                result.add(ax);
                usedAxioms.add(ax);
            }
        }
        rootAxioms.addAll(result);
        buildChildren(result);
    }


    private void buildChildren(Set<OWLAxiom> axiomSet) {
        List<Set<OWLAxiom>> axiomChildren = new ArrayList<Set<OWLAxiom>>();
        for (OWLAxiom ax : axiomSet) {
            Set<OWLAxiom> children = build(ax);
            for (OWLAxiom childAx : children) {
                index(ax, map, childAx);
            }
            axiomChildren.add(children);
        }
        for (Set<OWLAxiom> children : axiomChildren) {
            buildChildren(children);
        }
    }


    private Set<OWLAxiom> build(OWLAxiom parentAxiom) {
        usedAxioms.add(parentAxiom);
        OWLAxiomPartExtractor extractor = new OWLAxiomPartExtractor();
        parentAxiom.accept(extractor);
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        for (OWLObject obj : extractor.getRHS()) {
            for (OWLEntity ent : obj.getSignature()) {
                Set<OWLAxiom> axs = getAxiomsByLHS(ent);
                for (OWLAxiom ax : axs) {
                    if (!usedAxioms.contains(ax)) {
                        result.add(ax);
                        usedAxioms.add(ax);
                    }
                }
            }
        }
        return result;
    }

//
//    public String toString() {
//        StringB
//        System.out.println("LHS----------------------------------------------");
//        for(OWLEntity entity : axiomsByLHS.keySet()) {
//            System.out.println(entity);
//            for(OWLAxiom lhsAx : axiomsByLHS.get(entity)) {
//                System.out.println("    " + lhsAx);
//            }
//        }
//
//        System.out.println("RHS----------------------------------------------");
//        for(OWLEntity entity : axiomsByRHS.keySet()) {
//            System.out.println(entity);
//            for(OWLAxiom lhsAx : axiomsByRHS.get(entity)) {
//                System.out.println("    " + lhsAx);
//            }
//        }
//
//    }


    private static <K, V> void index(K key, Map<K, Set<V>> map, V value) {
        Set<V> values = map.get(key);
        if (values == null) {
            values = new HashSet<V>();
            map.put(key, values);
        }
        values.add(value);
    }


    public Set<OWLAxiom> getRootAxioms() {
        return rootAxioms;
    }


    public Set<OWLAxiom> getChildAxioms(OWLAxiom ax) {
        Set<OWLAxiom> result = map.get(ax);
        if (result != null) {
            return result;
        }
        else {
            return Collections.emptySet();
        }
    }


    private static class OWLAxiomPartExtractor extends OWLAxiomVisitorAdapter {

        private Set<OWLObject> rhs;

        private Set<OWLObject> lhs;


        public Set<OWLObject> getRHS() {
            return rhs;
        }


        public Set<OWLObject> getLHS() {
            return lhs;
        }


        public OWLAxiomPartExtractor() {
            rhs = new HashSet<OWLObject>();
            lhs = new HashSet<OWLObject>();
        }


        @Override
		public void visit(OWLSubClassOfAxiom axiom) {
            rhs.add(axiom.getSuperClass());
            lhs.add(axiom.getSubClass());
        }


        @Override
		public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            rhs.add(axiom.getObject());
            rhs.add(axiom.getProperty());
            lhs.add(axiom.getSubject());
        }


        @Override
		public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLDisjointClassesAxiom axiom) {
            rhs.addAll(axiom.getClassExpressions());
            lhs.addAll(axiom.getClassExpressions());
        }


        @Override
		public void visit(OWLDataPropertyDomainAxiom axiom) {
            rhs.add(axiom.getDomain());
            lhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLObjectPropertyDomainAxiom axiom) {
            rhs.add(axiom.getDomain());
            lhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        @Override
		public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            rhs.add(axiom.getProperty());
            rhs.add(axiom.getObject());
            lhs.add(axiom.getSubject());
        }


        @Override
		public void visit(OWLDifferentIndividualsAxiom axiom) {
            rhs.addAll(axiom.getIndividuals());
            lhs.addAll(axiom.getIndividuals());
        }


        @Override
		public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        @Override
		public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        @Override
		public void visit(OWLObjectPropertyRangeAxiom axiom) {
            rhs.add(axiom.getRange());
            lhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            rhs.add(axiom.getProperty());
            rhs.add(axiom.getObject());
            lhs.add(axiom.getSubject());
        }


        @Override
		public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            rhs.add(axiom.getSuperProperty());
            lhs.add(axiom.getSubProperty());
        }


        @Override
		public void visit(OWLDisjointUnionAxiom axiom) {
            rhs.addAll(axiom.getClassExpressions());
            rhs.add(axiom.getOWLClass());
            lhs.add(axiom.getOWLClass());
            lhs.addAll(axiom.getClassExpressions());
        }


        @Override
		public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLDataPropertyRangeAxiom axiom) {
            rhs.add(axiom.getRange());
            lhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        @Override
		public void visit(OWLClassAssertionAxiom axiom) {
            rhs.add(axiom.getClassExpression());
            lhs.add(axiom.getIndividual());
        }


        @Override
		public void visit(OWLEquivalentClassesAxiom axiom) {
            rhs.addAll(axiom.getClassExpressions());
            lhs.addAll(axiom.getClassExpressions());
        }


        @Override
		public void visit(OWLDataPropertyAssertionAxiom axiom) {
            rhs.add(axiom.getProperty());
            lhs.add(axiom.getSubject());
        }


        @Override
		public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLSubDataPropertyOfAxiom axiom) {
            rhs.add(axiom.getSuperProperty());
        }


        @Override
		public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        @Override
		public void visit(OWLSameIndividualAxiom axiom) {
            rhs.addAll(axiom.getIndividuals());
            lhs.addAll(axiom.getIndividuals());
        }


        @Override
		public void visit(OWLSubPropertyChainOfAxiom axiom) {
            rhs.add(axiom.getSuperProperty());
            lhs.addAll(axiom.getPropertyChain());
        }


        @Override
		public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        @Override
        @SuppressWarnings("unused")
		public void visit(SWRLRule rule) {
        }
    }
    @SuppressWarnings("unused")
    private static class OWLAxiomComparator extends OWLAxiomVisitorAdapter implements Comparator<OWLAxiom> {

		public OWLAxiomComparator() {
		}

		@Override
		public void visit(OWLSubClassOfAxiom axiom) {
            result = 0;
        }

        @Override
		public void visit(OWLEquivalentClassesAxiom axiom) {
            result = 1;
        }

        @Override
		public void visit(OWLDisjointClassesAxiom axiom) {
            result = 2;
        }


        private int result;

        public int compare(OWLAxiom o1, OWLAxiom o2) {
            result = 0;
            o1.accept(this);
            int result1 = result;
            o2.accept(this);
            int result2 = result;
            int diff = result2 - result1;
            if (diff != 0) {
                return diff;
            }
            else {
                return -1;
            }
        }
    }
}
