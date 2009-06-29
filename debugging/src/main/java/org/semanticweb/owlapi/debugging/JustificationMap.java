package org.semanticweb.owlapi.debugging;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;
import org.semanticweb.owlapi.util.OWLEntityCollector;

import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
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
            OWLEntityCollector rhsCollector = new OWLEntityCollector();
            for (OWLObject rhsObject : extractor.getRHS()) {
                rhsObject.accept(rhsCollector);
            }
            for (OWLEntity rhsEntity : rhsCollector.getObjects()) {
                index(rhsEntity, axiomsByRHS, ax);
            }
            OWLEntityCollector lhsCollector = new OWLEntityCollector();
            for (OWLObject lhsObject : extractor.getLHS()) {
                lhsObject.accept(lhsCollector);
            }
            for (OWLEntity lhsEntity : lhsCollector.getObjects()) {
                index(lhsEntity, axiomsByLHS, ax);
            }
        }
        buildChildren(desc);
    }

    private Set<OWLAxiom> getAxiomsByLHS(OWLEntity lhs) {
        Set<OWLAxiom> axioms = axiomsByLHS.get(lhs);
        if (axioms != null) {
            Set<OWLAxiom> ts = new TreeSet<OWLAxiom>(new OWLAxiomComparator());
            ts.addAll(axioms);
            return ts;
        } else {
            return Collections.emptySet();
        }
    }


    private Set<OWLAxiom> getAxiomsByRHS(OWLEntity rhs) {
        Set<OWLAxiom> axioms = axiomsByRHS.get(rhs);
        if (axioms != null) {
            return axioms;
        } else {
            return Collections.emptySet();
        }
    }

    private void buildChildren(OWLClassExpression seed) {
        // Return the axioms that have the entity on the LHS
        OWLEntityCollector collector = new OWLEntityCollector();
        seed.accept(collector);
        Set<OWLAxiom> result = new HashSet<OWLAxiom>();
        for (OWLEntity ent : collector.getObjects()) {
            Set<OWLAxiom> axs = getAxiomsByLHS(ent);
            for (OWLAxiom ax : axs) {
                result.add(ax);
                usedAxioms.add(ax);
            }
        }
        rootAxioms.addAll(result);
        buildChildren(result);
    }


    private void buildChildren(Set<OWLAxiom> axioms) {
        List<Set<OWLAxiom>> axiomChildren = new ArrayList<Set<OWLAxiom>>();
        for (OWLAxiom ax : axioms) {
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
            OWLEntityCollector collector = new OWLEntityCollector();
            obj.accept(collector);
            for (OWLEntity ent : collector.getObjects()) {
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
        } else {
            return Collections.emptySet();
        }
    }


    private class OWLAxiomPartExtractor extends OWLAxiomVisitorAdapter {

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


        public void visit(OWLSubClassOfAxiom axiom) {
            rhs.add(axiom.getSuperClass());
            lhs.add(axiom.getSubClass());
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            rhs.add(axiom.getObject());
            rhs.add(axiom.getProperty());
            lhs.add(axiom.getSubject());
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            rhs.addAll(axiom.getClassExpressions());
            lhs.addAll(axiom.getClassExpressions());
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            rhs.add(axiom.getDomain());
            lhs.add(axiom.getProperty());
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            rhs.add(axiom.getDomain());
            lhs.add(axiom.getProperty());
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            rhs.add(axiom.getProperty());
            rhs.add(axiom.getObject());
            lhs.add(axiom.getSubject());
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            rhs.addAll(axiom.getIndividuals());
            lhs.addAll(axiom.getIndividuals());
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            rhs.add(axiom.getRange());
            lhs.add(axiom.getProperty());
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            rhs.add(axiom.getProperty());
            rhs.add(axiom.getObject());
            lhs.add(axiom.getSubject());
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            rhs.add(axiom.getSuperProperty());
            lhs.add(axiom.getSubProperty());
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            rhs.addAll(axiom.getClassExpressions());
            rhs.add(axiom.getOWLClass());
            lhs.add(axiom.getOWLClass());
            lhs.addAll(axiom.getClassExpressions());
        }


        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            rhs.add(axiom.getRange());
            lhs.add(axiom.getProperty());
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            rhs.add(axiom.getClassExpression());
            lhs.add(axiom.getIndividual());
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            rhs.addAll(axiom.getClassExpressions());
            lhs.addAll(axiom.getClassExpressions());
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            rhs.add(axiom.getProperty());
            lhs.add(axiom.getSubject());
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            rhs.add(axiom.getSuperProperty());
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            rhs.add(axiom.getProperty());
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            rhs.addAll(axiom.getIndividuals());
            lhs.addAll(axiom.getIndividuals());
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            rhs.add(axiom.getSuperProperty());
            lhs.addAll(axiom.getPropertyChain());
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            rhs.addAll(axiom.getProperties());
            lhs.addAll(axiom.getProperties());
        }


        public void visit(SWRLRule rule) {
        }
    }

    private class OWLAxiomComparator extends OWLAxiomVisitorAdapter implements Comparator<OWLAxiom> {


        public void visit(OWLSubClassOfAxiom axiom) {
            result = 0;
        }

        public void visit(OWLEquivalentClassesAxiom axiom) {
            result = 1;
        }

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
            } else {
                return -1;
            }
        }
    }
}
