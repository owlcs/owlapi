package org.semanticweb.owlapi.reasoner.structural;

import org.semanticweb.owlapi.reasoner.impl.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.apibinding.OWLManager;

import java.util.*;
import java.net.URI;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 04-Dec-2009
 */
public class StructuralReasoner extends OWLReasonerBase {

    private Map<OWLClass, OWLClassNode> cycles = new HashMap<OWLClass, OWLClassNode>();

    private Map<OWLClass, Set<OWLClass>> supersCache = new HashMap<OWLClass, Set<OWLClass>>();

    private Set<OWLClass> roots = new HashSet<OWLClass>();

    public StructuralReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration, BufferingMode bufferingMode) {
        super(rootOntology, configuration, bufferingMode);
    }

    protected void handleChanges(Set<OWLAxiom> addAxioms, Set<OWLAxiom> removeAxioms) {
    }

    public void interrupt() {
    }

    public void prepareReasoner() throws ReasonerInterruptedException, TimeOutException {
        computeCycles();
    }

    private void computeCycles() {
        Set<OWLClass> processed = new HashSet<OWLClass>();
        HashSet<Set<OWLClass>> result = new HashSet<Set<OWLClass>>();
        for (OWLOntology ont : getRootOntology().getImportsClosure()) {
            for (OWLClass cls : ont.getReferencedClasses()) {
                if (!processed.contains(cls)) {
                    tarjan(cls, 0, new Stack<OWLClass>(), new HashMap<OWLClass, Integer>(), new HashMap<OWLClass, Integer>(), result, processed, new HashSet<OWLClass>());
                }

            }
        }
        for (Set<OWLClass> cycle : result) {
            OWLClassNode node = new OWLClassNode(cycle);
            for (OWLClass cls : cycle) {
                cycles.put(cls, node);
                Set<OWLClass> rawSupers = new HashSet<OWLClass>(getRawSupers(cls, supersCache));
                rawSupers.removeAll(node.getEntities());
                if(rawSupers.isEmpty()) {
                    roots.add(cls);
                }
            }
        }

        roots.remove(getRootOntology().getOWLOntologyManager().getOWLDataFactory().getOWLThing());
    }


    private Set<OWLClass> getRawSupers(OWLClass cls, Map<OWLClass, Set<OWLClass>> cache) {
        Set<OWLClass> result = null;
        if (cache != null) {
            result = cache.get(cls);
        }
        if (result == null) {
            result = new HashSet<OWLClass>();
            for (OWLOntology ont : getRootOntology().getImportsClosure()) {
                for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(cls)) {
                    OWLClassExpression superCls = ax.getSuperClass();
                    if (!superCls.isAnonymous()) {
                        result.add(superCls.asOWLClass());
                    }
                    else if (superCls instanceof OWLObjectIntersectionOf) {
                        OWLObjectIntersectionOf intersectionOf = (OWLObjectIntersectionOf) superCls;
                        for (OWLClassExpression conjunct : intersectionOf.asConjunctSet()) {
                            if (!conjunct.isAnonymous()) {
                                result.add(conjunct.asOWLClass());
                            }
                        }
                    }
                }
                for (OWLEquivalentClassesAxiom ax : ont.getEquivalentClassesAxioms(cls)) {
                    for (OWLClassExpression ce : ax.getClassExpressionsMinus(cls)) {
                        if (!ce.isAnonymous()) {
                            result.add(ce.asOWLClass());
                        }
                        else if (ce instanceof OWLObjectIntersectionOf) {
                            OWLObjectIntersectionOf intersectionOf = (OWLObjectIntersectionOf) ce;
                            for (OWLClassExpression conjunct : intersectionOf.asConjunctSet()) {
                                if (!conjunct.isAnonymous()) {
                                    result.add(conjunct.asOWLClass());
                                }
                            }
                        }
                    }
                }
            }
            if (result.isEmpty()) {
                result.add(OWLDataFactoryImpl.getInstance().getOWLThing());
                roots.add(cls);
            }
            if (cache != null) {
                cache.put(cls, result);
            }
        }
        return result;
    }

    private Set<OWLClass> getRawSubs(OWLClass cls, Map<OWLClass, Set<OWLClass>> cache) {
        Set<OWLClass> result = null;
        if (cache != null) {
            result = cache.get(cls);
        }
        if (result == null) {
            result = new HashSet<OWLClass>();

            if (cls.isOWLThing()) {
                result.addAll(roots);
            }
            for (OWLOntology ont : getRootOntology().getImportsClosure()) {
                for (OWLAxiom ax : ont.getReferencingAxioms(cls)) {
                    if (ax instanceof OWLSubClassOfAxiom) {
                        OWLSubClassOfAxiom sca = (OWLSubClassOfAxiom) ax;
                        if (!sca.getSubClass().isAnonymous()) {
                            Set<OWLClassExpression> conjuncts = sca.getSuperClass().asConjunctSet();
                            if (conjuncts.contains(cls)) {
                                result.add(sca.getSubClass().asOWLClass());
                            }
                        }
                    }
                    else if (ax instanceof OWLEquivalentClassesAxiom) {
                        OWLEquivalentClassesAxiom eca = (OWLEquivalentClassesAxiom) ax;
                        for (OWLClassExpression ce : eca.getClassExpressions()) {
                            if (ce.containsConjunct(cls)) {
                                for (OWLClassExpression sub : eca.getClassExpressions()) {
                                    if (!sub.isAnonymous() && !sub.equals(ce)) {
                                        result.add(sub.asOWLClass());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (result.isEmpty() && !cls.isOWLNothing()) {
                result.add(OWLDataFactoryImpl.getInstance().getOWLNothing());
            }
        }
        return result;
    }


    public void tarjan(OWLClass cls, int index, Stack<OWLClass> stack, Map<OWLClass, Integer> indexMap, Map<OWLClass, Integer> lowlinkMap, Set<Set<OWLClass>> result, Set<OWLClass> processed, Set<OWLClass> stackClses) {
        processed.add(cls);
        indexMap.put(cls, index);
        lowlinkMap.put(cls, index);
        index = index + 1;
        stack.push(cls);
        stackClses.add(cls);
        for (OWLClass superCls : getRawSupers(cls, supersCache)) {
            if (!indexMap.containsKey(superCls)) {
                tarjan(superCls, index, stack, indexMap, lowlinkMap, result, processed, stackClses);
                lowlinkMap.put(cls, Math.min(lowlinkMap.get(cls), lowlinkMap.get(superCls)));
            }
            else if (stackClses.contains(superCls)) {
                lowlinkMap.put(cls, Math.min(lowlinkMap.get(cls), indexMap.get(superCls)));
            }
        }
        if (lowlinkMap.get(cls).equals(indexMap.get(cls))) {
            Set<OWLClass> scc = new HashSet<OWLClass>();
            while (true) {
                OWLClass clsPrime = stack.pop();
                stackClses.remove(clsPrime);
                scc.add(clsPrime);
                if (clsPrime.equals(cls)) {
                    break;
                }
            }
            if (scc.size() > 1) {
                result.add(scc);
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isConsistent() throws ReasonerInterruptedException, TimeOutException {
        return true;
    }

    public boolean isSatisfiable(OWLClassExpression classExpression) throws ReasonerInterruptedException, TimeOutException, ClassExpressionNotInProfileException, UndeclaredEntitiesException, InconsistentOntologyException {
        return true;
    }

    public Node<OWLClass> getUnsatisfiableClasses() throws ReasonerInterruptedException, TimeOutException {
        return OWLClassNode.getBottomNode();
    }

    public boolean isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, UndeclaredEntitiesException, InconsistentOntologyException {
        return getRootOntology().containsAxiom(axiom, true);
    }

    public boolean isEntailed(Set<? extends OWLAxiom> axioms) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, UndeclaredEntitiesException, InconsistentOntologyException {
        return true;
    }

    public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
        return true;
    }

    public NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            Set<OWLClass> clses = getRawSubs(ce.asOWLClass(), null);
            for (Iterator<OWLClass> clsIt = clses.iterator(); clsIt.hasNext();) {
                OWLClass cls = clsIt.next();
                OWLClassNode cycleNode = cycles.get(cls);
                if (cycleNode != null) {
                    if (!cycleNode.contains(ce.asOWLClass())) {
                        ns.addNode(cycleNode);
                    }
                    clsIt.remove();
                }
                else {
                    ns.addEntity(cls);
                }
            }
        }
        return ns;
    }

    public NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            Set<OWLClass> clses = getRawSupers(ce.asOWLClass(), supersCache);
            for (Iterator<OWLClass> clsIt = clses.iterator(); clsIt.hasNext();) {
                OWLClass cls = clsIt.next();
                OWLClassNode cycleNode = cycles.get(cls);
                if (cycleNode != null) {
                    ns.addNode(cycleNode);
                    clsIt.remove();
                }
                else {
                    ns.addEntity(cls);
                }
            }
        }
        return ns;
    }

    public Node<OWLClass> getEquivalentClasses(OWLClassExpression ce) throws InconsistentOntologyException, ClassExpressionNotInProfileException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        DefaultNode<OWLClass> node = null;
        if (!ce.isAnonymous()) {
            node = cycles.get(ce.asOWLClass());
        }
        if (node == null) {
            node = NodeFactory.getOWLClassNode();
        }
        return node;
    }

    public NodeSet<OWLObjectProperty> getSubObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLObjectProperty> getSuperObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public Node<OWLObjectProperty> getEquivalentObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public Node<OWLObjectProperty> getInverseObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLClass> getObjectPropertyDomains(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLClass> getObjectPropertyRanges(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {

        return null;
    }

    public NodeSet<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual ind, OWLObjectPropertyExpression pe) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind, OWLDataProperty pe) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    private void dumpHierarchy() {
        long t0 = System.currentTimeMillis();
        dumpHierarchy(OWLClassNode.getTopNode(), 1);
        long t1 = System.currentTimeMillis();
        System.out.println("Time to dump hierarchy: " + (t1 - t0));

    }

    private void dumpHierarchy(Node<OWLClass> par, int level) {
        printIndent(level);
        System.out.println(par);
        for (Node<OWLClass> childNode : getSubClasses(par.getRepresentativeElement(), true)) {
            if (!childNode.isBottomNode()) {
                dumpHierarchy(childNode, level + 1);
            }
        }
    }

    private void printIndent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
    }


    public static void main(String[] args) {
        try {
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            long t0 = System.currentTimeMillis();
            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("file:/Users/matthewhorridge/ontologies/Thesaurus.08.02d.owl.zip"));
            long t1 = System.currentTimeMillis();
            System.out.println("Time to load: " + (t1 - t0));
//            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));
//            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("file:/Users/matthewhorridge/Desktop/cycletest.owl"));
//            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("file:/Users/matthewhorridge/Desktop/yatbu.owl"));
            System.out.println("Loaded");
            System.out.println("Clses: " + ont.getReferencedClasses().size());
            for (int i = 0; i < 10; i++) {
                StructuralReasoner reasoner = new StructuralReasoner(ont, new SimpleConfiguration(), BufferingMode.NON_BUFFERING);
//            for(OWLClass cls : ont.getReferencedClasses()) {
//                reasoner.getRawSupers(cls, null);
//            }
                doPrepare(reasoner);
//                reasoner.dumpHierarchy();
            }


        }
        catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    private static void doPrepare(StructuralReasoner reasoner) {
        long t0 = System.currentTimeMillis();
        reasoner.prepareReasoner();
        long t1 = System.currentTimeMillis();
        System.out.println("Time to prepare reasoner: " + (t1 - t0));
    }


    private interface RawHierarchyProvider<T> {

        Collection<T> getParents(T child);

        Collection<T> getChild(T parent);
    }


    private class RawClassHierarchyProvider implements RawHierarchyProvider<OWLClass> {

        private Map<OWLClass, Collection<OWLClass>> cache = new HashMap<OWLClass, Collection<OWLClass>>();

        public Collection<OWLClass> getParents(OWLClass child) {
            Collection<OWLClass> result = null;
            if (cache != null) {
                result = cache.get(child);
            }
            if (result == null) {
                result = new HashSet<OWLClass>();
                for (OWLOntology ont : getRootOntology().getImportsClosure()) {
                    for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(child)) {
                        OWLClassExpression superCls = ax.getSuperClass();
                        if (!superCls.isAnonymous()) {
                            result.add(superCls.asOWLClass());
                        }
                        else if (superCls instanceof OWLObjectIntersectionOf) {
                            OWLObjectIntersectionOf intersectionOf = (OWLObjectIntersectionOf) superCls;
                            for (OWLClassExpression conjunct : intersectionOf.asConjunctSet()) {
                                if (!conjunct.isAnonymous()) {
                                    result.add(conjunct.asOWLClass());
                                }
                            }
                        }
                    }
                    for (OWLEquivalentClassesAxiom ax : ont.getEquivalentClassesAxioms(child)) {
                        for (OWLClassExpression ce : ax.getClassExpressionsMinus(child)) {
                            if (!ce.isAnonymous()) {
                                result.add(ce.asOWLClass());
                            }
                            else if (ce instanceof OWLObjectIntersectionOf) {
                                OWLObjectIntersectionOf intersectionOf = (OWLObjectIntersectionOf) ce;
                                for (OWLClassExpression conjunct : intersectionOf.asConjunctSet()) {
                                    if (!conjunct.isAnonymous()) {
                                        result.add(conjunct.asOWLClass());
                                    }
                                }
                            }
                        }
                    }
                }
                if (result.isEmpty()) {
                    result.add(OWLDataFactoryImpl.getInstance().getOWLThing());
                }
                if (cache != null) {
                    cache.put(child, result);
                }
            }
            return result;
        }

        public Collection<OWLClass> getChild(OWLClass parent) {
            return null;
        }
    }
}
