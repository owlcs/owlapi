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
 * </p>
 * This is a simple structural reasoner that essentially answers with told information.  It is incomplete.
 */
public class StructuralReasoner extends OWLReasonerBase {

    private ClassHierarchyInfo classHierarchyInfo = new ClassHierarchyInfo();

    private ObjectPropertyHierarchyInfo objectPropertyHierarchyInfo = new ObjectPropertyHierarchyInfo();

    private DataPropertyHierarchyInfo dataPropertyHierarchyInfo = new DataPropertyHierarchyInfo();


    private static final Version version = new Version(1, 0, 0, 0);

    private boolean interrupted = false;

    private ReasonerProgressMonitor pm;

    public StructuralReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration, BufferingMode bufferingMode) {
        super(rootOntology, configuration, bufferingMode);
        pm = configuration.getProgressMonitor();
        if(pm == null) {
            pm = new NullReasonerProgressMonitor();
        }
    }

    /**
     * Gets the name of this reasoner.
     *
     * @return A string that represents the name of this reasoner.
     */
    public String getReasonerName() {
        return "Structural Reasoner";
    }


    public FreshEntityPolicy getFreshEntityPolicy() {
        return FreshEntityPolicy.ALLOW;
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
        handleChanges(addAxioms, removeAxioms, classHierarchyInfo);
        handleChanges(addAxioms, removeAxioms, objectPropertyHierarchyInfo);
        handleChanges(addAxioms, removeAxioms, dataPropertyHierarchyInfo);
    }

    private <T extends OWLLogicalEntity> void handleChanges(Set<OWLAxiom> added, Set<OWLAxiom> removed, HierarchyInfo<T> hierarchyInfo) {
        Set<T> sig = hierarchyInfo.getEntitiesInSignature(added);
        sig.addAll(hierarchyInfo.getEntitiesInSignature(removed));
        hierarchyInfo.processChanges(sig);

    }

    public void interrupt() {
        interrupted = true;
    }

    public void prepareReasoner() throws ReasonerInterruptedException, TimeOutException {
        classHierarchyInfo.prepare();
        objectPropertyHierarchyInfo.prepare();
        dataPropertyHierarchyInfo.prepare();
    }


    private void checkForInterrupt() {
        if (interrupted) {
            interrupted = false;
            throw new ReasonerInterruptedException();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isConsistent() throws ReasonerInterruptedException, TimeOutException {
        return true;
    }

    public boolean isSatisfiable(OWLClassExpression classExpression) throws ReasonerInterruptedException, TimeOutException, ClassExpressionNotInProfileException, FreshEntitiesException, InconsistentOntologyException {
        return true;
    }

    public Node<OWLClass> getUnsatisfiableClasses() throws ReasonerInterruptedException, TimeOutException {
        return OWLClassNode.getBottomNode();
    }

    public boolean isEntailed(OWLAxiom axiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException {
        return getRootOntology().containsAxiomIgnoreAnnotations(axiom, true);
    }

    public boolean isEntailed(Set<? extends OWLAxiom> axioms) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException {
        for (OWLAxiom ax : axioms) {
            if (!getRootOntology().containsAxiomIgnoreAnnotations(ax, true)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
        return false;
    }

    public Node<OWLClass> getTopClassNode() {
        return classHierarchyInfo.getEquivalents(getDataFactory().getOWLThing());
    }

    public Node<OWLClass> getBottomClassNode() {
        return classHierarchyInfo.getEquivalents(getDataFactory().getOWLNothing());
    }

    public NodeSet<OWLClass> getSubClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            return classHierarchyInfo.getNodeHierarchyChildren(ce.asOWLClass(), direct, ns);
        }
        return ns;
    }

    public NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            return classHierarchyInfo.getNodeHierarchyParents(ce.asOWLClass(), direct, ns);
        }
        return ns;
    }

    public Node<OWLClass> getEquivalentClasses(OWLClassExpression ce) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return classHierarchyInfo.getEquivalents(ce.asOWLClass());
    }

    public NodeSet<OWLClass> getDisjointClasses(OWLClassExpression ce, boolean direct) {
        OWLClassNodeSet nodeSet = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            for(OWLOntology ontology : getRootOntology().getImportsClosure()) {
                for(OWLDisjointClassesAxiom ax : ontology.getDisjointClassesAxioms(ce.asOWLClass())) {
                    for(OWLClassExpression op : ax.getClassExpressions()) {
                        if(!op.isAnonymous()) {
                            nodeSet.addNode(getEquivalentClasses(op));
                        }
                    }
                }
            }
        }
        return nodeSet;
    }

    public Node<OWLObjectProperty> getTopObjectPropertyNode() {
        return objectPropertyHierarchyInfo.getEquivalents(getDataFactory().getOWLTopObjectProperty());
    }

    public Node<OWLObjectProperty> getBottomObjectPropertyNode() {
        return objectPropertyHierarchyInfo.getEquivalents(getDataFactory().getOWLBottomObjectProperty());
    }

    public NodeSet<OWLObjectProperty> getSubObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();
        if (!pe.isAnonymous()) {
            return objectPropertyHierarchyInfo.getNodeHierarchyChildren(pe.asOWLObjectProperty(), direct, ns);
        }
        return ns;
    }

    public NodeSet<OWLObjectProperty> getSuperObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();
        if (!pe.isAnonymous()) {
            return objectPropertyHierarchyInfo.getNodeHierarchyParents(pe.asOWLObjectProperty(), direct, ns);
        }
        return ns;
    }

    public Node<OWLObjectProperty> getEquivalentObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLObjectPropertyNode nd = new OWLObjectPropertyNode();
        if (!pe.isAnonymous()) {
            return objectPropertyHierarchyInfo.getEquivalents(pe.asOWLObjectProperty());
        }
        return nd;
    }

    public NodeSet<OWLObjectProperty> getDisjointObjectProperties(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return new OWLObjectPropertyNodeSet();
    }

    public Node<OWLObjectProperty> getInverseObjectProperties(OWLObjectPropertyExpression pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        Set<OWLObjectProperty> props = new HashSet<OWLObjectProperty>();
        Set<OWLObjectProperty> equivalentObjectProperties = getEquivalentObjectProperties(pe).getEntities();
        for (OWLObjectProperty property : equivalentObjectProperties) {
            for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
                for (OWLInverseObjectPropertiesAxiom axiom : ontology.getInverseObjectPropertyAxioms(property)) {
                    for (OWLObjectPropertyExpression invProp : axiom.getPropertiesMinus(property)) {
                        OWLObjectPropertyExpression invPropSimp = invProp.getSimplified();
                        if (!invProp.isAnonymous()) {
                            props.add(invPropSimp.asOWLObjectProperty());
                            props.addAll(getEquivalentObjectProperties(invPropSimp).getEntities());
                        }
                    }
                }
            }
        }
        return new OWLObjectPropertyNode(props);
    }

    public NodeSet<OWLClass> getObjectPropertyDomains(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        DefaultNodeSet<OWLClass> result = new OWLClassNodeSet();
        for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
            for (OWLObjectPropertyDomainAxiom axiom : ontology.getObjectPropertyDomainAxioms(pe)) {
                result.addNode(getEquivalentClasses(axiom.getDomain()));
                if (!direct) {
                    result.addAllNodes(getSuperClasses(axiom.getDomain(), false).getNodes());
                }
            }

            for (OWLObjectPropertyExpression invPe : getInverseObjectProperties(pe).getEntities()) {
                for (OWLObjectPropertyRangeAxiom axiom : ontology.getObjectPropertyRangeAxioms(invPe)) {
                    result.addNode(getEquivalentClasses(axiom.getRange()));
                    if (!direct) {
                        result.addAllNodes(getSuperClasses(axiom.getRange(), false).getNodes());
                    }
                }
            }
        }
        return result;
    }

    public NodeSet<OWLClass> getObjectPropertyRanges(OWLObjectPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        DefaultNodeSet<OWLClass> result = new OWLClassNodeSet();
        for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
            for (OWLObjectPropertyRangeAxiom axiom : ontology.getObjectPropertyRangeAxioms(pe)) {
                result.addNode(getEquivalentClasses(axiom.getRange()));
                if (!direct) {
                    result.addAllNodes(getSuperClasses(axiom.getRange(), false).getNodes());
                }
            }
            for (OWLObjectPropertyExpression invPe : getInverseObjectProperties(pe).getEntities()) {
                for (OWLObjectPropertyDomainAxiom axiom : ontology.getObjectPropertyDomainAxioms(invPe)) {
                    result.addNode(getEquivalentClasses(axiom.getDomain()));
                    if (!direct) {
                        result.addAllNodes(getSuperClasses(axiom.getDomain(), false).getNodes());
                    }
                }
            }
        }
        return result;
    }

    public Node<OWLDataProperty> getTopDataPropertyNode() {
        return dataPropertyHierarchyInfo.getEquivalents(getDataFactory().getOWLTopDataProperty());
    }

    public Node<OWLDataProperty> getBottomDataPropertyNode() {
        return dataPropertyHierarchyInfo.getEquivalents(getDataFactory().getOWLBottomDataProperty());
    }

    public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();
        return dataPropertyHierarchyInfo.getNodeHierarchyChildren(pe, direct, ns);
    }

    public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();
        return dataPropertyHierarchyInfo.getNodeHierarchyParents(pe, direct, ns);
    }

    public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return dataPropertyHierarchyInfo.getEquivalents(pe);
    }

    public NodeSet<OWLDataProperty> getDisjointDataProperties(OWLDataPropertyExpression pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        DefaultNodeSet<OWLDataProperty> result = new OWLDataPropertyNodeSet();
        for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
            for (OWLDisjointDataPropertiesAxiom axiom : ontology.getDisjointDataPropertiesAxioms(pe.asOWLDataProperty())) {
                for (OWLDataPropertyExpression dpe : axiom.getPropertiesMinus(pe)) {
                    if (!dpe.isAnonymous()) {
                        result.addNode(dataPropertyHierarchyInfo.getEquivalents(dpe.asOWLDataProperty()));
                        if (!direct) {
                            result.addAllNodes(getSubDataProperties(dpe.asOWLDataProperty(), false).getNodes());
                        }
                    }
                }
            }
        }
        return result;
    }

    public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty pe, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        DefaultNodeSet<OWLClass> result = new OWLClassNodeSet();
        for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
            for (OWLDataPropertyDomainAxiom axiom : ontology.getDataPropertyDomainAxioms(pe)) {
                result.addNode(getEquivalentClasses(axiom.getDomain()));
                if (!direct) {
                    result.addAllNodes(getSuperClasses(axiom.getDomain(), false).getNodes());
                }
            }
        }
        return result;
    }

    public NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        DefaultNodeSet<OWLClass> result = new OWLClassNodeSet();
        for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
            for (OWLClassAssertionAxiom axiom : ontology.getClassAssertionAxioms(ind)) {
                OWLClassExpression ce = axiom.getClassExpression();
                if (!ce.isAnonymous()) {
                    result.addNode(classHierarchyInfo.getEquivalents(ce.asOWLClass()));
                    if (!direct) {
                        result.addAllNodes(getSuperClasses(ce, false).getNodes());
                    }
                }
            }
        }
        return result;
    }

    public NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression ce, boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        DefaultNodeSet<OWLNamedIndividual> result = new OWLNamedIndividualNodeSet();
        if (!ce.isAnonymous()) {
            OWLClass cls = ce.asOWLClass();
            for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
                for (OWLClassAssertionAxiom axiom : ontology.getClassAssertionAxioms(cls)) {
                    OWLIndividual individual = axiom.getIndividual();
                    if (!individual.isAnonymous()) {
                        if (getIndividualNodeSetPolicy().equals(IndividualNodeSetPolicy.BY_SAME_AS)) {
                            result.addNode(getSameIndividuals(individual.asOWLNamedIndividual()));
                        }
                        else {
                            result.addNode(new OWLNamedIndividualNode(individual.asOWLNamedIndividual()));
                        }
                        if (!direct) {
                            for (Node<OWLClass> node : getSubClasses(ce, false)) {
                                for (OWLClass c : node.getEntities()) {
                                    result.addAllNodes(getInstances(c, true).getNodes());
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public NodeSet<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual ind, OWLObjectPropertyExpression pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        OWLNamedIndividualNodeSet result = new OWLNamedIndividualNodeSet();
        Node<OWLObjectProperty> inverses = getInverseObjectProperties(pe);
        for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
            for (OWLObjectPropertyAssertionAxiom axiom : ontology.getObjectPropertyAssertionAxioms(ind)) {
                if (!axiom.getObject().isAnonymous()) {
                    if (axiom.getProperty().getSimplified().equals(pe.getSimplified())) {
                        if (getIndividualNodeSetPolicy().equals(IndividualNodeSetPolicy.BY_SAME_AS)) {
                            result.addNode(getSameIndividuals(axiom.getObject().asOWLNamedIndividual()));
                        }
                        else {
                            result.addNode(new OWLNamedIndividualNode(axiom.getObject().asOWLNamedIndividual()));
                        }
                    }
                }
                // Inverse of pe
                if (axiom.getObject().equals(ind) && !axiom.getSubject().isAnonymous()) {
                    OWLObjectPropertyExpression invPe = axiom.getProperty().getInverseProperty().getSimplified();
                    if (!invPe.isAnonymous() && inverses.contains(invPe.asOWLObjectProperty())) {
                        if (getIndividualNodeSetPolicy().equals(IndividualNodeSetPolicy.BY_SAME_AS)) {
                            result.addNode(getSameIndividuals(axiom.getObject().asOWLNamedIndividual()));
                        }
                        else {
                            result.addNode(new OWLNamedIndividualNode(axiom.getObject().asOWLNamedIndividual()));
                        }
                    }
                }

            }
        }
        // Could do other stuff like inspecting owl:hasValue restrictions
        return result;
    }

    /**
     * Gets the data property values for the specified individual and data property.
     *
     * @param ind The individual that is the subject of the data property values
     * @param pe The data property whose values are to be retrieved for the specified individual
     * @return A set of <code>OWLLiteral</code>s containing literals such that for each literal <code>l</code> in the
     *         set, either there is an explicit data property assertion in the set of reasoner axioms
     *         <code>DataPropertyAssertion(pe, ind, l)</code>, or, there is an explicit
     *         data property assertion in the set of reasoner axioms <code>DataPropertyAssertion(S, ind, l)</code> and
     *         the set of reasoner axioms entails <code>SubDataPropertyOf(S, pe)</code>.
     *
     * @throws InconsistentOntologyException if the imports closure of the root ontology is inconsistent
     * @throws org.semanticweb.owlapi.reasoner.FreshEntitiesException   if the signature of the individual and property is not contained within the signature
     *                                       of the imports closure of the root ontology and the undeclared entity policy of this reasoner is set to {@link org.semanticweb.owlapi.reasoner.FreshEntityPolicy#DISALLOW}.
     * @throws ReasonerInterruptedException  if the reasoning process was interrupted for any particular reason (for example if
     *                                       reasoning was cancelled by a client process)
     * @throws TimeOutException              if the reasoner timed out during a basic reasoning operation. See {@link #getTimeOut()}.
     */
    public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind, OWLDataProperty pe) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        Set<OWLLiteral> literals = new HashSet<OWLLiteral>();
        Set<OWLDataProperty> superProperties = getSuperDataProperties(pe, false).getFlattened();
        superProperties.addAll(getEquivalentDataProperties(pe).getEntities());
        for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
            for (OWLDataPropertyAssertionAxiom axiom : ontology.getDataPropertyAssertionAxioms(ind)) {
                if (superProperties.contains(axiom.getProperty().asOWLDataProperty())) {
                    literals.add(axiom.getObject());
                }
            }
        }
        return literals;
    }

    public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        Set<OWLNamedIndividual> inds = new HashSet<OWLNamedIndividual>();
        Set<OWLSameIndividualAxiom> processed = new HashSet<OWLSameIndividualAxiom>();
        List<OWLNamedIndividual> stack = new ArrayList<OWLNamedIndividual>();
        stack.add(ind);
        while (!stack.isEmpty()) {
            OWLNamedIndividual currentInd = stack.remove(0);
            for (OWLOntology ontology : getRootOntology().getImportsClosure()) {
                for (OWLSameIndividualAxiom axiom : ontology.getSameIndividualAxioms(currentInd)) {
                    if (!processed.contains(axiom)) {
                        processed.add(axiom);
                        for (OWLIndividual i : axiom.getIndividuals()) {
                            if (!i.isAnonymous()) {
                                OWLNamedIndividual namedInd = ind.asOWLNamedIndividual();
                                if (!inds.contains(namedInd)) {
                                    inds.add(namedInd);
                                    stack.add(ind);
                                }
                            }
                        }
                    }
                }
            }
        }

        return new OWLNamedIndividualNode(inds);
    }

    public NodeSet<OWLNamedIndividual> getDifferentIndividuals(OWLNamedIndividual ind) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException {
        return new OWLNamedIndividualNodeSet();
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

        private T bottomEntity;

        private Set<T> roots = new HashSet<T>();

        private Map<T, Node<T>> cycles = new HashMap<T, Node<T>>();

        private RawHierarchyProvider<T> rawHierarchyProvider;

        private String name;

        private int classificationSize;

        public HierarchyInfo(String name, T topEntity, T bottomEntity, RawHierarchyProvider<T> rawHierarchyProvider) {
            this.topEntity = topEntity;
            this.bottomEntity = bottomEntity;
            this.rawHierarchyProvider = rawHierarchyProvider;
            this.name = name;
        }

        protected abstract Set<T> getEntities(OWLOntology ont);

        protected abstract DefaultNode<T> createNode(Set<T> cycle);

        protected abstract DefaultNode<T> createNode();

        protected abstract Set<T> getEntitiesInSignature(OWLAxiom ax);

        public Set<T> getEntitiesInSignature(Set<OWLAxiom> axioms) {
            Set<T> result = new HashSet<T>();
            for(OWLAxiom ax : axioms) {
                result.addAll(getEntitiesInSignature(ax));
            }
            return result;
        }

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
            pm.reasonerTaskStarted("Computing " + name + " hierarchy");
            pm.reasonerTaskBusy();
            cycles.clear();
            Map<T, Collection<T>> cache = new HashMap<T, Collection<T>>();
            Set<T> processed = new HashSet<T>();
            HashSet<Set<T>> result = new HashSet<Set<T>>();
            Set<T> entities = new HashSet<T>();
            for (OWLOntology ont : getRootOntology().getImportsClosure()) {
                entities.addAll(getEntities(ont));
            }
            classificationSize = entities.size();
            pm.reasonerTaskProgressChanged(0, classificationSize);
            computeCyclesForSignature(entities, cache, processed, result);
            addCycles(result);
            pm.reasonerTaskStopped();
        }

        private void computeCyclesForSignature(Set<T> signature, Map<T, Collection<T>> cache, Set<T> processed, HashSet<Set<T>> result) {
            for (T entity : signature) {
                if (!processed.contains(entity)) {
                    pm.reasonerTaskProgressChanged(processed.size(), signature.size());
                    tarjan(entity, 0, new Stack<T>(), new HashMap<T, Integer>(), new HashMap<T, Integer>(), result, processed, new HashSet<T>(), cache);
                    checkForInterrupt();
                }
            }
        }

        private void addCycles(HashSet<Set<T>> forCycles) {
            for (Set<T> entity : forCycles) {
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

        /**
         * Processes the specified signature that represents the signature of potential changes
         * @param signature The signature
         */
        public void processChanges(Set<T> signature) {
            // Break existing cycles - they will be reformed if necessary
            removeCyclesForSignature(signature);
            // Compute new cycles
            HashSet<Set<T>> result = new HashSet<Set<T>>();
            computeCyclesForSignature(signature, new HashMap<T, Collection<T>>(), new HashSet<T>(), result);
            // Add them
            addCycles(result);

        }

        /**
         * Removes all cycles that contain entities in the specified signature
         * @param signature The signature - cycles that contain any part of the signature will be removed.
         */
        private void removeCyclesForSignature(Set<T> signature) {
            for(T entity : cycles.keySet()) {
                if(signature.contains(entity)) {
                    cycles.remove(entity);
                }
            }
            for(T entity : signature) {
                roots.remove(entity);
            }


        }

        public Set<Set<T>> computeCycleForEntites(Set<T> entities) {
            Map<T, Collection<T>> cache = new HashMap<T, Collection<T>>();
            Set<T> processed = new HashSet<T>();
            HashSet<Set<T>> result = new HashSet<Set<T>>();
            for (T entity : entities) {
                tarjan(entity, 0, new Stack<T>(), new HashMap<T, Integer>(), new HashMap<T, Integer>(), result, processed, new HashSet<T>(), cache);
            }
            return result;
        }

        public void tarjan(T entity, int index, Stack<T> stack, Map<T, Integer> indexMap, Map<T, Integer> lowlinkMap, Set<Set<T>> result, Set<T> processed, Set<T> stackEntities, Map<T, Collection<T>> cache) {
            checkForInterrupt();
            processed.add(entity);
            pm.reasonerTaskProgressChanged(processed.size(), classificationSize);
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

        public NodeSet<T> getNodeHierarchyChildren(T parent, boolean direct, DefaultNodeSet<T> ns) {
            if(parent.equals(bottomEntity)) {
                return ns;
            }
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
            if(child.equals(topEntity)) {
                return ns;
            }
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
            super("class",
                    getDataFactory().getOWLThing(),
                    getDataFactory().getOWLNothing(),
                    new RawClassHierarchyProvider());
        }


        @Override
        protected Set<OWLClass> getEntitiesInSignature(OWLAxiom ax) {
            return ax.getClassesInSignature();
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
            super("object property",
                    getDataFactory().getOWLTopObjectProperty(),
                    getDataFactory().getOWLBottomObjectProperty(),
                    new RawObjectPropertyHierarchyProvider());
        }

        @Override
        protected Set<OWLObjectProperty> getEntitiesInSignature(OWLAxiom ax) {
            return ax.getObjectPropertiesInSignature();
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
            super("data property",
                    getDataFactory().getOWLTopDataProperty(),
                    getDataFactory().getOWLBottomDataProperty(),
                    new RawDataPropertyHierarchyProvider());
        }

        @Override
        protected Set<OWLDataProperty> getEntitiesInSignature(OWLAxiom ax) {
            return ax.getDataPropertiesInSignature();
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
