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
        Set<OWLAxiom> axioms = axiomsByLHS.get(lhs);
        if (axioms != null) {
            Set<OWLAxiom> ts = new TreeSet<OWLAxiom>(new OWLAxiomComparator());
            ts.addAll(axioms);
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
