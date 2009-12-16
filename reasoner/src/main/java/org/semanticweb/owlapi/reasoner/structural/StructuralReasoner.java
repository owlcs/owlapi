package org.semanticweb.owlapi.reasoner.structural;

import org.semanticweb.owlapi.reasoner.impl.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.Version;

import java.util.*;

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

    private ClassHierarchyInfo classHierarchyInfo = new ClassHierarchyInfo();

    private ObjectPropertyHierarchyInfo objectPropertyHierarchyInfo = new ObjectPropertyHierarchyInfo();

    private DataPropertyHierarchyInfo dataPropertyHierarchyInfo = new DataPropertyHierarchyInfo();

    private HierarchyInfo<OWLClass> clsHierarchy;

    private static final Version version = new Version(1, 0, 0, 0);

    public StructuralReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration, BufferingMode bufferingMode) {
        super(rootOntology, configuration, bufferingMode);
    }

    /**
     * Gets the name of this reasoner.
     *
     * @return A string that represents the name of this reasoner.
     */
    public String getReasonerName() {
        return "Structural Reasoner";
    }

    /**
     * Gets the UndeclaredEntityPolicy in use by this reasoner.  The undeclared entity policy is set at reasoner
     * creation time.
     *
     * @return The policy.
     */
    public UndeclaredEntityPolicy getUndeclaredEntityPolicy() {
        return UndeclaredEntityPolicy.ALLOW;
    }

    /**
     * Gets the IndividualNodeSetPolicy  in use by this reasoner.  The policy is set at reasoner creation time.
     *
     * @return The policy.
     */
    public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
        return IndividualNodeSetPolicy.BY_NAME;
    }

    /**
     * Gets the version of this reasoner.
     *
     * @return The version of this reasoner. Not <code>null</code>.
     */
    public Version getReasonerVersion() {
        return version;
    }

    protected void handleChanges(Set<OWLAxiom> addAxioms, Set<OWLAxiom> removeAxioms) {
        // TODO: We just need to check we haven't formed or broken a cycle.
    }

    public void interrupt() {
    }

    public void prepareReasoner() throws ReasonerInterruptedException, TimeOutException {
        long t0 = System.currentTimeMillis();
        classHierarchyInfo.prepare();
        objectPropertyHierarchyInfo.prepare();
        dataPropertyHierarchyInfo.prepare();
        long t1 = System.currentTimeMillis();
        System.out.println("Time to prepare reasoner: " + (t1 - t0));
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
        return getRootOntology().containsAxiomIgnoreAnnotations(axiom, true);
    }

    public boolean isEntailed(Set<? extends OWLAxiom> axioms) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, UndeclaredEntitiesException, InconsistentOntologyException {
        for(OWLAxiom ax : axioms) {
            if(!getRootOntology().containsAxiomIgnoreAnnotations(ax, true)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
        return true;
    }

    public Node<OWLClass> getTopClassNode() {
        return classHierarchyInfo.getEquivalents(getDataFactory().getOWLThing());
    }

    public Node<OWLClass> getBottomClassNode() {
        return classHierarchyInfo.getEquivalents(getDataFactory().getOWLNothing());
    }

    public NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            return classHierarchyInfo.getNodeHierarchyChildren(ce.asOWLClass(), direct, ns);
        }
        return ns;
    }

    public NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            return classHierarchyInfo.getNodeHierarchyParents(ce.asOWLClass(), direct, ns);
        }
        return ns;
    }

    public Node<OWLClass> getEquivalentClasses(OWLClassExpression ce) throws InconsistentOntologyException, ClassExpressionNotInProfileException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return classHierarchyInfo.getEquivalents(ce.asOWLClass());
    }

    public NodeSet<OWLClass> getDisjointClasses(OWLClassExpression ce, boolean direct) {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        for(OWLOntology ont : getRootOntology().getImportsClosure()) {
            for(OWLDisjointClassesAxiom ax : ont.getDisjointClassesAxioms(ce.asOWLClass())) {
                for(OWLClassExpression disjoint : ax.getClassExpressionsMinus(ce)) {
                    if(!disjoint.isAnonymous()) {
                        ns.addNode(getEquivalentClasses(disjoint.asOWLClass()));
                        if(!direct) {
                            for(Node<OWLClass> nd : getSubClasses(disjoint, false)) {
                                if (!ns.isBottomSingleton()) {
                                    ns.addNode(nd);
                                }
                            }
                        }
                    }
                }
            }
        }
        return ns;
    }

    public Node<OWLObjectProperty> getTopObjectPropertyNode() {
        return objectPropertyHierarchyInfo.getEquivalents(getDataFactory().getOWLTopObjectProperty());
    }

    public Node<OWLObjectProperty> getBottomObjectPropertyNode() {
        return objectPropertyHierarchyInfo.getEquivalents(getDataFactory().getOWLBottomObjectProperty());
    }

    public NodeSet<OWLObjectProperty> getSubObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();
        if (!pe.isAnonymous()) {
            return objectPropertyHierarchyInfo.getNodeHierarchyChildren(pe.asOWLObjectProperty(), direct, ns);
        }
        return ns;
    }

    public NodeSet<OWLObjectProperty> getSuperObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();
        if (!pe.isAnonymous()) {
            return objectPropertyHierarchyInfo.getNodeHierarchyParents(pe.asOWLObjectProperty(), direct, ns);
        }
        return ns;
    }

    public Node<OWLObjectProperty> getEquivalentObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLObjectPropertyNode nd = new OWLObjectPropertyNode();
        if (!pe.isAnonymous()) {
            return objectPropertyHierarchyInfo.getEquivalents(pe.asOWLObjectProperty());
        }
        return nd;
    }

    public NodeSet<OWLObjectProperty> getDisjointObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public Node<OWLObjectProperty> getInverseObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLObjectPropertyNode node = new OWLObjectPropertyNode();
        for(OWLOntology ont : getRootOntology().getImportsClosure()) {
            for(OWLInverseObjectPropertiesAxiom axiom : ont.getInverseObjectPropertyAxioms(pe)) {
                for(OWLObjectPropertyExpression inv : axiom.getProperties()) {
                    if (!inv.equals(pe)) {
                        OWLObjectPropertyExpression simplified = inv.getSimplified();
                        if(!simplified.isAnonymous()) {
                            node.add(simplified.asOWLObjectProperty());
                            for(OWLObjectProperty prop : getEquivalentObjectProperties(simplified.asOWLObjectProperty())) {
                                node.add(prop);
                            }
                        }
                    }
                }
            }
        }
        return node;
    }

    public NodeSet<OWLClass> getObjectPropertyDomains(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public NodeSet<OWLClass> getObjectPropertyRanges(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }

    public Node<OWLDataProperty> getTopDataPropertyNode() {
        return dataPropertyHierarchyInfo.getEquivalents(getDataFactory().getOWLTopDataProperty());
    }

    public Node<OWLDataProperty> getBottomDataPropertyNode() {
        return dataPropertyHierarchyInfo.getEquivalents(getDataFactory().getOWLBottomDataProperty());
    }

    public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();
        return dataPropertyHierarchyInfo.getNodeHierarchyChildren(pe, direct, ns);
    }

    public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();
        return dataPropertyHierarchyInfo.getNodeHierarchyParents(pe, direct, ns);
    }

    public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return dataPropertyHierarchyInfo.getEquivalents(pe);
    }

    public NodeSet<OWLDataProperty> getDisjointDataProperties(OWLDataPropertyExpression pe, boolean direct) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
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

    public NodeSet<OWLNamedIndividual> getDifferentIndividuals(OWLNamedIndividual ind) throws InconsistentOntologyException, UndeclaredEntitiesException, ReasonerInterruptedException, TimeOutException {
        return null;
    }


    protected OWLDataFactory getDataFactory() {
        return getRootOntology().getOWLOntologyManager().getOWLDataFactory();
    }

    public void dumpClassHierarchy(boolean showBottomNode) {
        dumpClassHierarchy(OWLClassNode.getTopNode(), 0, showBottomNode);
    }

    private void dumpClassHierarchy(Node<OWLClass> cls, int level, boolean showBottomNode) {
        if (!showBottomNode && cls.isBottomNode()) {
            return;
        }
        printIndent(level);
        OWLClass representative = cls.getRepresentativeElement();
        System.out.println(getEquivalentClasses(representative));
        for (Node<OWLClass> subCls : getSubClasses(representative, true)) {
            dumpClassHierarchy(subCls, level + 1, showBottomNode);
        }
    }

    public void dumpObjectPropertyHierarchy(boolean showBottomNode) {
        dumpObjectPropertyHierarchy(OWLObjectPropertyNode.getTopNode(), 0, showBottomNode);
    }

    private void dumpObjectPropertyHierarchy(Node<OWLObjectProperty> cls, int level, boolean showBottomNode) {
        if (!showBottomNode && cls.isBottomNode()) {
            return;
        }
        printIndent(level);
        OWLObjectProperty representative = cls.getRepresentativeElement();
        System.out.println(getEquivalentObjectProperties(representative));
        for (Node<OWLObjectProperty> subProp : getSubObjectProperties(representative, true)) {
            dumpObjectPropertyHierarchy(subProp, level + 1, showBottomNode);
        }
    }


    public void dumpDataPropertyHierarchy(boolean showBottomNode) {
        dumpDataPropertyHierarchy(OWLDataPropertyNode.getTopNode(), 0, showBottomNode);
    }

    private void dumpDataPropertyHierarchy(Node<OWLDataProperty> cls, int level, boolean showBottomNode) {
        if (!showBottomNode && cls.isBottomNode()) {
            return;
        }
        printIndent(level);
        OWLDataProperty representative = cls.getRepresentativeElement();
        System.out.println(getEquivalentDataProperties(representative));
        for (Node<OWLDataProperty> subProp : getSubDataProperties(representative, true)) {
            dumpDataPropertyHierarchy(subProp, level + 1, showBottomNode);
        }
    }


    private void printIndent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
    }


    private abstract class HierarchyInfo<T extends OWLLogicalEntity> {

        private T topEntity;

        private Set<T> roots = new HashSet<T>();

        private Map<T, Node<T>> cycles = new HashMap<T, Node<T>>();

        private RawHierarchyProvider<T> rawHierarchyProvider;


        public HierarchyInfo(T topEntity, RawHierarchyProvider<T> rawHierarchyProvider) {
            this.topEntity = topEntity;
            this.rawHierarchyProvider = rawHierarchyProvider;
        }

        protected abstract Set<T> getEntities(OWLOntology ont);

        protected abstract DefaultNode<T> createNode(Set<T> cycle);

        protected abstract DefaultNode<T> createNode();

        private Collection<T> getChildrenInternal(T parent) {
            Collection<T> result;
            if (parent.isTopEntity()) {
                result = roots;
            }
            else {
                result = rawHierarchyProvider.getChildren(parent);
            }
            return result;
        }

        private Collection<T> getParentsInternal(T child, Map<T, Collection<T>> cache) {
            Collection<T> result = null;
            if (cache != null) {
                result = cache.get(child);
            }
            if (result == null) {
                result = rawHierarchyProvider.getParents(child);
                if (result.isEmpty()) {
                    roots.add(child);
                    result.add(topEntity);
                }
                if (cache != null) {
                    cache.put(child, result);
                }
            }
            return result;
        }


        public void prepare() {
            Map<T, Collection<T>> cache = new HashMap<T, Collection<T>>();
            Set<T> processed = new HashSet<T>();
            HashSet<Set<T>> result = new HashSet<Set<T>>();
            for (OWLOntology ont : getRootOntology().getImportsClosure()) {
                for (T entity : getEntities(ont)) {
                    if (!processed.contains(entity)) {
                        tarjan(entity, 0, new Stack<T>(), new HashMap<T, Integer>(), new HashMap<T, Integer>(), result, processed, new HashSet<T>(), cache);
                    }

                }
            }
            for (Set<T> entity : result) {
                DefaultNode<T> node = createNode(entity);
                for (T cls : entity) {
                    cycles.put(cls, node);
                    Set<T> rawSupers = new HashSet<T>(rawHierarchyProvider.getParents(cls));
                    rawSupers.removeAll(node.getEntities());
                    if (rawSupers.isEmpty()) {
                        roots.add(cls);
                    }
                }
            }
            roots.remove(topEntity);
        }

        public void tarjan(T entity, int index, Stack<T> stack, Map<T, Integer> indexMap, Map<T, Integer> lowlinkMap, Set<Set<T>> result, Set<T> processed, Set<T> stackEntities, Map<T, Collection<T>> cache) {
            processed.add(entity);
            indexMap.put(entity, index);
            lowlinkMap.put(entity, index);
            index = index + 1;
            stack.push(entity);
            stackEntities.add(entity);
            // First call to getParents - cache roots
            Collection<T> parents = getParentsInternal(entity, cache);
            if (parents.isEmpty()) {
                roots.add(entity);
            }

            for (T superEntity : parents) {
                if (!indexMap.containsKey(superEntity)) {
                    tarjan(superEntity, index, stack, indexMap, lowlinkMap, result, processed, stackEntities, cache);
                    lowlinkMap.put(entity, Math.min(lowlinkMap.get(entity), lowlinkMap.get(superEntity)));
                }
                else if (stackEntities.contains(superEntity)) {
                    lowlinkMap.put(entity, Math.min(lowlinkMap.get(entity), indexMap.get(superEntity)));
                }
            }
            if (lowlinkMap.get(entity).equals(indexMap.get(entity))) {
                Set<T> scc = new HashSet<T>();
                while (true) {
                    T clsPrime = stack.pop();
                    stackEntities.remove(clsPrime);
                    scc.add(clsPrime);
                    if (clsPrime.equals(entity)) {
                        break;
                    }
                }
                if (scc.size() > 1) {
                    result.add(scc);
                }
            }
        }


//        public void clearCycles() {
//            cycles.clear();
//        }

//        public Node<T> getCycle(T element) {
//            return cycles.get(element);
//        }

//        public void addCycle(T element, Node<T> cycle) {
//            cycles.put(element, cycle);
//        }

//        public Set<T> getRoots() {
//            return roots;
//        }
//
//        public void addRoot(T root) {
//            roots.add(root);
//        }

        public NodeSet<T> getNodeHierarchyChildren(T parent, boolean direct, DefaultNodeSet<T> ns) {
            Collection<T> children = getChildrenInternal(parent);
            for (Iterator<T> childIt = children.iterator(); childIt.hasNext();) {
                T child = childIt.next();
                Node<T> cycleNode = cycles.get(child);
                if (cycleNode != null) {
                    if (!cycleNode.contains(parent)) {
                        ns.addNode(cycleNode);
                    }
                    childIt.remove();
                }
                else {
                    ns.addEntity(child);
                }
                if (!direct) {
                    getNodeHierarchyChildren(child, direct, ns);
                }
            }
            return ns;
        }

        public NodeSet<T> getNodeHierarchyParents(T child, boolean direct, DefaultNodeSet<T> ns) {
            Collection<T> parents = getParentsInternal(child, null);
            for (Iterator<T> parIt = parents.iterator(); parIt.hasNext();) {
                T par = parIt.next();
                Node<T> cycleNode = cycles.get(par);
                if (cycleNode != null) {
                    ns.addNode(cycleNode);
                    parIt.remove();
                }
                else {
                    ns.addEntity(par);
                }
                if (!direct) {
                    getNodeHierarchyParents(par, direct, ns);
                }
            }
            return ns;
        }

        public Node<T> getEquivalents(T element) {
            Node<T> node = cycles.get(element);
            if (node == null) {
                DefaultNode<T> newNode = createNode();
                newNode.add(element);
                node = newNode;
            }
            return node;
        }
    }

    private class ClassHierarchyInfo extends HierarchyInfo<OWLClass> {

        private ClassHierarchyInfo() {
            super(getRootOntology().getOWLOntologyManager().getOWLDataFactory().getOWLThing(), new RawClassHierarchyProvider());
        }

        @Override
        protected DefaultNode<OWLClass> createNode(Set<OWLClass> cycle) {
            return new OWLClassNode(cycle);
        }

        @Override
        protected Set<OWLClass> getEntities(OWLOntology ont) {
            return ont.getClassesInSignature();
        }

        @Override
        protected DefaultNode<OWLClass> createNode() {
            return new OWLClassNode();
        }
    }

    private class ObjectPropertyHierarchyInfo extends HierarchyInfo<OWLObjectProperty> {

        private ObjectPropertyHierarchyInfo() {
            super(getDataFactory().getOWLTopObjectProperty(), new RawObjectPropertyHierarchyProvider());
        }

        @Override
        protected Set<OWLObjectProperty> getEntities(OWLOntology ont) {
            return ont.getObjectPropertiesInSignature();
        }

        @Override
        protected DefaultNode<OWLObjectProperty> createNode(Set<OWLObjectProperty> cycle) {
            return new OWLObjectPropertyNode(cycle);
        }

        @Override
        protected DefaultNode<OWLObjectProperty> createNode() {
            return new OWLObjectPropertyNode();
        }
    }

    private class DataPropertyHierarchyInfo extends HierarchyInfo<OWLDataProperty> {

        private DataPropertyHierarchyInfo() {
            super(getDataFactory().getOWLTopDataProperty(), new RawDataPropertyHierarchyProvider());
        }

        @Override
        protected Set<OWLDataProperty> getEntities(OWLOntology ont) {
            return ont.getDataPropertiesInSignature();
        }

        @Override
        protected DefaultNode<OWLDataProperty> createNode(Set<OWLDataProperty> cycle) {
            return new OWLDataPropertyNode(cycle);
        }

        @Override
        protected DefaultNode<OWLDataProperty> createNode() {
            return new OWLDataPropertyNode();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * An interface for objects who can provide the parents and children of some object.
     *
     * @param <T>
     */
    private interface RawHierarchyProvider<T> {

        Collection<T> getParents(T child);

        Collection<T> getChildren(T parent);

    }


    private class RawClassHierarchyProvider implements RawHierarchyProvider<OWLClass> {

        public Collection<OWLClass> getParents(OWLClass child) {
            Collection<OWLClass> result = new HashSet<OWLClass>();
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
            return result;
        }

        public Collection<OWLClass> getChildren(OWLClass parent) {
            Collection<OWLClass> result = new HashSet<OWLClass>();
            for (OWLOntology ont : getRootOntology().getImportsClosure()) {
                for (OWLAxiom ax : ont.getReferencingAxioms(parent)) {
                    if (ax instanceof OWLSubClassOfAxiom) {
                        OWLSubClassOfAxiom sca = (OWLSubClassOfAxiom) ax;
                        if (!sca.getSubClass().isAnonymous()) {
                            Set<OWLClassExpression> conjuncts = sca.getSuperClass().asConjunctSet();
                            if (conjuncts.contains(parent)) {
                                result.add(sca.getSubClass().asOWLClass());
                            }
                        }
                    }
                    else if (ax instanceof OWLEquivalentClassesAxiom) {
                        OWLEquivalentClassesAxiom eca = (OWLEquivalentClassesAxiom) ax;
                        for (OWLClassExpression ce : eca.getClassExpressions()) {
                            if (ce.containsConjunct(parent)) {
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
            if (result.isEmpty() && !parent.isOWLNothing()) {
                result.add(OWLDataFactoryImpl.getInstance().getOWLNothing());
            }
            return result;
        }
    }


    private class RawObjectPropertyHierarchyProvider implements RawHierarchyProvider<OWLObjectProperty> {

        public Collection<OWLObjectProperty> getParents(OWLObjectProperty child) {
            Set<OWLObjectProperty> properties = new HashSet<OWLObjectProperty>();
            for (OWLObjectPropertyExpression prop : child.getSuperProperties(getRootOntology().getImportsClosure())) {
                OWLObjectPropertyExpression simplifiedProp = prop.getSimplified();
                if (!simplifiedProp.isAnonymous()) {
                    properties.add(simplifiedProp.asOWLObjectProperty());
                }
            }
            return properties;
        }

        public Collection<OWLObjectProperty> getChildren(OWLObjectProperty parent) {
            Set<OWLObjectProperty> properties = new HashSet<OWLObjectProperty>();
            for (OWLObjectPropertyExpression prop : parent.getSubProperties(getRootOntology().getImportsClosure())) {
                OWLObjectPropertyExpression simplifiedProp = prop.getSimplified();
                if (!simplifiedProp.isAnonymous()) {
                    properties.add(simplifiedProp.asOWLObjectProperty());
                }
            }
            return properties;
        }
    }

    private class RawDataPropertyHierarchyProvider implements RawHierarchyProvider<OWLDataProperty> {

        public Collection<OWLDataProperty> getParents(OWLDataProperty child) {
            Set<OWLDataProperty> properties = new HashSet<OWLDataProperty>();
            for (OWLDataPropertyExpression prop : child.getSuperProperties(getRootOntology().getImportsClosure())) {
                properties.add(prop.asOWLDataProperty());
            }
            return properties;
        }

        public Collection<OWLDataProperty> getChildren(OWLDataProperty parent) {
            Set<OWLDataProperty> properties = new HashSet<OWLDataProperty>();
            for (OWLDataPropertyExpression prop : parent.getSubProperties(getRootOntology().getImportsClosure())) {
                properties.add(prop.asOWLDataProperty());
            }
            return properties;
        }
    }
}
