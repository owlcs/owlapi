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
package org.semanticweb.owlapi.reasoner.structural;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.impl.DefaultNode;
import org.semanticweb.owlapi.reasoner.impl.DefaultNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNode;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNode;
import org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNode;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNode;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLReasonerBase;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.Version;

/**
 * This is a simple structural reasoner that essentially answers with told
 * information. It is incomplete.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class StructuralReasoner extends OWLReasonerBase {

    @Nonnull
    private final ClassHierarchyInfo classHierarchyInfo = new ClassHierarchyInfo();
    @Nonnull
    private final ObjectPropertyHierarchyInfo objectPropertyHierarchyInfo = new ObjectPropertyHierarchyInfo();
    @Nonnull
    private final DataPropertyHierarchyInfo dataPropertyHierarchyInfo = new DataPropertyHierarchyInfo();
    @Nonnull
    private static final Version VERSION = new Version(1, 0, 0, 0);
    private boolean interrupted = false;
    @Nonnull
    protected final ReasonerProgressMonitor pm;
    private boolean prepared = false;

    /**
     * @param rootOntology
     *        the ontology
     * @param configuration
     *        the reasoner configuration
     * @param bufferingMode
     *        the buffering mode
     */
    public StructuralReasoner(@Nonnull OWLOntology rootOntology,
            @Nonnull OWLReasonerConfiguration configuration,
            @Nonnull BufferingMode bufferingMode) {
        super(rootOntology, configuration, bufferingMode);
        checkNotNull(configuration, "configuration cannot be null");
        pm = configuration.getProgressMonitor();
        prepareReasoner();
    }

    @Nonnull
    @Override
    public String getReasonerName() {
        return "Structural Reasoner";
    }

    @Override
    public FreshEntityPolicy getFreshEntityPolicy() {
        return FreshEntityPolicy.ALLOW;
    }

    @Override
    public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
        return IndividualNodeSetPolicy.BY_NAME;
    }

    @Nonnull
    @Override
    public Version getReasonerVersion() {
        return VERSION;
    }

    @Override
    protected void handleChanges(@Nonnull Set<OWLAxiom> addAxioms,
            @Nonnull Set<OWLAxiom> removeAxioms) {
        handleChanges(addAxioms, removeAxioms, classHierarchyInfo);
        handleChanges(addAxioms, removeAxioms, objectPropertyHierarchyInfo);
        handleChanges(addAxioms, removeAxioms, dataPropertyHierarchyInfo);
    }

    private static <T extends OWLObject> void handleChanges(
            @Nonnull Set<OWLAxiom> added, @Nonnull Set<OWLAxiom> removed,
            @Nonnull AbstractHierarchyInfo<T> hierarchyInfo) {
        Set<T> sig = hierarchyInfo.getEntitiesInSignature(added);
        sig.addAll(hierarchyInfo.getEntitiesInSignature(removed));
        hierarchyInfo.processChanges(sig, added, removed);
    }

    @Override
    public void interrupt() {
        interrupted = true;
    }

    private void ensurePrepared() {
        if (!prepared) {
            prepareReasoner();
        }
    }

    /**
     * @throws ReasonerInterruptedException
     *         on interruption
     * @throws TimeOutException
     *         on timeout
     */
    public final void prepareReasoner() {
        classHierarchyInfo.computeHierarchy();
        objectPropertyHierarchyInfo.computeHierarchy();
        dataPropertyHierarchyInfo.computeHierarchy();
        prepared = true;
    }

    @Override
    public void precomputeInferences(InferenceType... inferenceTypes) {
        prepareReasoner();
    }

    @Override
    public boolean isPrecomputed(InferenceType inferenceType) {
        return true;
    }

    @Override
    public Set<InferenceType> getPrecomputableInferenceTypes() {
        return CollectionFactory.createSet(InferenceType.CLASS_HIERARCHY,
                InferenceType.OBJECT_PROPERTY_HIERARCHY,
                InferenceType.DATA_PROPERTY_HIERARCHY);
    }

    protected void throwExceptionIfInterrupted() {
        if (interrupted) {
            interrupted = false;
            throw new ReasonerInterruptedException();
        }
    }

    @Override
    public boolean isConsistent() {
        return true;
    }

    @Override
    public boolean isSatisfiable(@Nonnull OWLClassExpression classExpression) {
        return !classExpression.isAnonymous()
                && !getEquivalentClasses(classExpression.asOWLClass())
                        .contains(getDataFactory().getOWLNothing());
    }

    @Nonnull
    @Override
    public Node<OWLClass> getUnsatisfiableClasses() {
        return OWLClassNode.getBottomNode();
    }

    @Override
    public boolean isEntailed(OWLAxiom axiom) {
        return getRootOntology().containsAxiom(axiom, INCLUDED,
                AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
    }

    @Override
    public boolean isEntailed(@Nonnull Set<? extends OWLAxiom> axioms) {
        for (OWLAxiom ax : axioms) {
            if (!getRootOntology().containsAxiom(ax, INCLUDED,
                    AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
        return false;
    }

    @Override
    public Node<OWLClass> getTopClassNode() {
        ensurePrepared();
        return classHierarchyInfo
                .getEquivalents(getDataFactory().getOWLThing());
    }

    @Override
    public Node<OWLClass> getBottomClassNode() {
        ensurePrepared();
        return classHierarchyInfo.getEquivalents(getDataFactory()
                .getOWLNothing());
    }

    @Nonnull
    @Override
    public NodeSet<OWLClass> getSubClasses(@Nonnull OWLClassExpression ce,
            boolean direct) {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            ensurePrepared();
            return classHierarchyInfo.getNodeHierarchyChildren(ce.asOWLClass(),
                    direct, ns);
        }
        return ns;
    }

    @Nonnull
    @Override
    public NodeSet<OWLClass> getSuperClasses(@Nonnull OWLClassExpression ce,
            boolean direct) {
        OWLClassNodeSet ns = new OWLClassNodeSet();
        if (!ce.isAnonymous()) {
            ensurePrepared();
            return classHierarchyInfo.getNodeHierarchyParents(ce.asOWLClass(),
                    direct, ns);
        }
        return ns;
    }

    @Override
    public Node<OWLClass> getEquivalentClasses(@Nonnull OWLClassExpression ce) {
        ensurePrepared();
        if (!ce.isAnonymous()) {
            return classHierarchyInfo.getEquivalents(ce.asOWLClass());
        } else {
            return new OWLClassNode();
        }
    }

    @Nonnull
    @Override
    public NodeSet<OWLClass> getDisjointClasses(@Nonnull OWLClassExpression ce) {
        ensurePrepared();
        OWLClassNodeSet nodeSet = new OWLClassNodeSet();
        if (ce.isAnonymous()) {
            return nodeSet;
        }
        getRootOntology().importsClosure()
                .flatMap(o -> o.disjointClassesAxioms(ce.asOWLClass()))
                .flatMap(ax -> ax.classExpressions())
                .filter(op -> !op.isAnonymous())
                .forEach(op -> nodeSet.addNode(getEquivalentClasses(op)));
        return nodeSet;
    }

    @Override
    public Node<OWLObjectPropertyExpression> getTopObjectPropertyNode() {
        ensurePrepared();
        return objectPropertyHierarchyInfo.getEquivalents(getDataFactory()
                .getOWLTopObjectProperty());
    }

    @Override
    public Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode() {
        ensurePrepared();
        return objectPropertyHierarchyInfo.getEquivalents(getDataFactory()
                .getOWLBottomObjectProperty());
    }

    @Nonnull
    @Override
    public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
            OWLObjectPropertyExpression pe, boolean direct) {
        OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();
        ensurePrepared();
        return objectPropertyHierarchyInfo.getNodeHierarchyChildren(pe, direct,
                ns);
    }

    @Nonnull
    @Override
    public NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(
            OWLObjectPropertyExpression pe, boolean direct) {
        OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();
        ensurePrepared();
        return objectPropertyHierarchyInfo.getNodeHierarchyParents(pe, direct,
                ns);
    }

    @Override
    public Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(
            OWLObjectPropertyExpression pe) {
        ensurePrepared();
        return objectPropertyHierarchyInfo.getEquivalents(pe);
    }

    @Nonnull
    @Override
    public NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(
            OWLObjectPropertyExpression pe) {
        return new OWLObjectPropertyNodeSet();
    }

    @Override
    public Node<OWLObjectPropertyExpression> getInverseObjectProperties(
            @Nonnull OWLObjectPropertyExpression pe) {
        ensurePrepared();
        OWLObjectPropertyExpression inv = pe.getInverseProperty()
                .getSimplified();
        return getEquivalentObjectProperties(inv);
    }

    @Nonnull
    @Override
    public NodeSet<OWLClass> getObjectPropertyDomains(
            @Nonnull OWLObjectPropertyExpression pe, boolean direct) {
        ensurePrepared();
        DefaultNodeSet<OWLClass> result = new OWLClassNodeSet();
        Consumer<? super OWLObjectPropertyDomainAxiom> domains = axiom -> {
            result.addNode(getEquivalentClasses(axiom.getDomain()));
            if (!direct) {
                result.addAllNodes(getSuperClasses(axiom.getDomain(), false)
                        .nodes());
            }
        };
        Consumer<? super OWLObjectPropertyRangeAxiom> inverseRanges = ax -> {
            result.addNode(getEquivalentClasses(ax.getRange()));
            if (!direct) {
                result.addAllNodes(getSuperClasses(ax.getRange(), false)
                        .nodes());
            }
        };
        getRootOntology().importsClosure()
                .flatMap(o -> o.objectPropertyDomainAxioms(pe))
                .forEach(domains);
        getRootOntology().importsClosure().forEach(
                o -> {
                    getInverseObjectProperties(pe).entities().forEach(
                            invPe -> o.objectPropertyRangeAxioms(invPe)
                                    .forEach(inverseRanges));
                });
        return result;
    }

    @Nonnull
    @Override
    public NodeSet<OWLClass> getObjectPropertyRanges(
            @Nonnull OWLObjectPropertyExpression pe, boolean direct) {
        ensurePrepared();
        DefaultNodeSet<OWLClass> result = new OWLClassNodeSet();
        getRootOntology()
                .importsClosure()
                .forEach(
                        ontology -> {
                            ontology.objectPropertyRangeAxioms(pe)
                                    .forEach(
                                            axiom -> {
                                                result.addNode(getEquivalentClasses(axiom
                                                        .getRange()));
                                                if (!direct) {
                                                    result.addAllNodes(getSuperClasses(
                                                            axiom.getRange(),
                                                            false).nodes());
                                                }
                                            });
                            getInverseObjectProperties(pe)
                                    .entities()
                                    .flatMap(
                                            e -> ontology
                                                    .objectPropertyDomainAxioms(e))
                                    .forEach(
                                            axiom -> {
                                                result.addNode(getEquivalentClasses(axiom
                                                        .getDomain()));
                                                if (!direct) {
                                                    result.addAllNodes(getSuperClasses(
                                                            axiom.getDomain(),
                                                            false).nodes());
                                                }
                                            });
                        });
        return result;
    }

    @Override
    public Node<OWLDataProperty> getTopDataPropertyNode() {
        ensurePrepared();
        return dataPropertyHierarchyInfo.getEquivalents(getDataFactory()
                .getOWLTopDataProperty());
    }

    @Override
    public Node<OWLDataProperty> getBottomDataPropertyNode() {
        ensurePrepared();
        return dataPropertyHierarchyInfo.getEquivalents(getDataFactory()
                .getOWLBottomDataProperty());
    }

    @Nonnull
    @Override
    public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty pe,
            boolean direct) {
        ensurePrepared();
        OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();
        return dataPropertyHierarchyInfo.getNodeHierarchyChildren(pe, direct,
                ns);
    }

    @Nonnull
    @Override
    public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty pe,
            boolean direct) {
        ensurePrepared();
        OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();
        return dataPropertyHierarchyInfo
                .getNodeHierarchyParents(pe, direct, ns);
    }

    @Override
    public Node<OWLDataProperty>
            getEquivalentDataProperties(OWLDataProperty pe) {
        ensurePrepared();
        return dataPropertyHierarchyInfo.getEquivalents(pe);
    }

    @Nonnull
    @Override
    public NodeSet<OWLDataProperty> getDisjointDataProperties(
            @Nonnull OWLDataPropertyExpression pe) {
        ensurePrepared();
        DefaultNodeSet<OWLDataProperty> result = new OWLDataPropertyNodeSet();
        getRootOntology()
                .importsClosure()
                .flatMap(
                        o -> o.disjointDataPropertiesAxioms(pe
                                .asOWLDataProperty()))
                .forEach(
                        axiom -> {
                            for (OWLDataPropertyExpression dpe : axiom
                                    .getPropertiesMinus(pe)) {
                                if (!dpe.isAnonymous()) {
                                    result.addNode(dataPropertyHierarchyInfo
                                            .getEquivalents(dpe
                                                    .asOWLDataProperty()));
                                    result.addAllNodes(getSubDataProperties(
                                            dpe.asOWLDataProperty(), false)
                                            .nodes());
                                }
                            }
                        });
        return result;
    }

    @Nonnull
    @Override
    public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty pe,
            boolean direct) {
        ensurePrepared();
        DefaultNodeSet<OWLClass> result = new OWLClassNodeSet();
        getRootOntology().importsClosure()
                .flatMap(o -> o.dataPropertyDomainAxioms(pe))
                .forEach(ax -> addClasses(direct, result, ax.getDomain()));
        return result;
    }

    protected void addClasses(boolean direct, DefaultNodeSet<OWLClass> result,
            OWLClassExpression domain) {
        result.addNode(getEquivalentClasses(domain));
        if (!direct) {
            result.addAllNodes(getSuperClasses(domain, false).nodes());
        }
    }

    @Nonnull
    @Override
    public NodeSet<OWLClass> getTypes(OWLNamedIndividual ind, boolean direct) {
        ensurePrepared();
        DefaultNodeSet<OWLClass> result = new OWLClassNodeSet();
        getRootOntology()
                .importsClosure()
                .flatMap(o -> o.classAssertionAxioms(ind))
                .forEach(
                        ax -> addClasses(direct, result,
                                ax.getClassExpression()));
        return result;
    }

    @Nonnull
    @Override
    public NodeSet<OWLNamedIndividual> getInstances(
            @Nonnull OWLClassExpression ce, boolean direct) {
        ensurePrepared();
        DefaultNodeSet<OWLNamedIndividual> result = new OWLNamedIndividualNodeSet();
        if (ce.isAnonymous()) {
            return result;
        }
        OWLClass cls = ce.asOWLClass();
        Set<OWLClass> clses = new HashSet<>();
        clses.add(cls);
        if (!direct) {
            clses.addAll(asList(getSubClasses(cls, false).entities()));
        }
        for (OWLClass curCls : clses) {
            getRootOntology()
                    .importsClosure()
                    .flatMap(o -> o.classAssertionAxioms(curCls))
                    .map(ax -> ax.getIndividual())
                    .filter(i -> !i.isAnonymous())
                    .map(i -> i.asOWLNamedIndividual())
                    .forEach(
                            i -> {
                                if (getIndividualNodeSetPolicy().equals(
                                        IndividualNodeSetPolicy.BY_SAME_AS)) {
                                    result.addNode(getSameIndividuals(i));
                                } else {
                                    result.addNode(new OWLNamedIndividualNode(i));
                                }
                            });
        }
        return result;
    }

    @Nonnull
    @Override
    public NodeSet<OWLNamedIndividual> getObjectPropertyValues(
            OWLNamedIndividual ind, @Nonnull OWLObjectPropertyExpression pe) {
        ensurePrepared();
        OWLNamedIndividualNodeSet result = new OWLNamedIndividualNodeSet();
        Node<OWLObjectPropertyExpression> inverses = getInverseObjectProperties(pe);
        getRootOntology()
                .importsClosure()
                .flatMap(o -> o.objectPropertyAssertionAxioms(ind))
                .forEach(
                        axiom -> {
                            if (!axiom.getObject().isAnonymous()
                                    && axiom.getProperty().getSimplified()
                                            .equals(pe.getSimplified())) {
                                if (getIndividualNodeSetPolicy().equals(
                                        IndividualNodeSetPolicy.BY_SAME_AS)) {
                                    result.addNode(getSameIndividuals(axiom
                                            .getObject().asOWLNamedIndividual()));
                                } else {
                                    result.addNode(new OWLNamedIndividualNode(
                                            axiom.getObject()
                                                    .asOWLNamedIndividual()));
                                }
                            }
                            // Inverse of pe
                            if (axiom.getObject().equals(ind)
                                    && !axiom.getSubject().isAnonymous()) {
                                OWLObjectPropertyExpression invPe = axiom
                                        .getProperty().getInverseProperty()
                                        .getSimplified();
                                if (!invPe.isAnonymous()
                                        && inverses.contains(invPe
                                                .asOWLObjectProperty())) {
                                    if (getIndividualNodeSetPolicy().equals(
                                            IndividualNodeSetPolicy.BY_SAME_AS)) {
                                        result.addNode(getSameIndividuals(axiom
                                                .getObject()
                                                .asOWLNamedIndividual()));
                                    } else {
                                        result.addNode(new OWLNamedIndividualNode(
                                                axiom.getObject()
                                                        .asOWLNamedIndividual()));
                                    }
                                }
                            }
                        });
        // Could do other stuff like inspecting owl:hasValue restrictions
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual ind,
            OWLDataProperty pe) {
        ensurePrepared();
        Set<OWLLiteral> literals = new HashSet<>();
        Set<OWLDataProperty> superProperties = asSet(Stream.concat(
                getSuperDataProperties(pe, false).entities(),
                getEquivalentDataProperties(pe).entities()));
        getRootOntology()
                .importsClosure()
                .flatMap(o -> o.dataPropertyAssertionAxioms(ind))
                .forEach(
                        ax -> {
                            if (superProperties.contains(ax.getProperty()
                                    .asOWLDataProperty())) {
                                literals.add(ax.getObject());
                            }
                        });
        return literals;
    }

    @Nonnull
    @Override
    public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual ind) {
        ensurePrepared();
        Set<OWLNamedIndividual> inds = new HashSet<>();
        Set<OWLSameIndividualAxiom> processed = new HashSet<>();
        List<OWLNamedIndividual> stack = new LinkedList<>();
        stack.add(ind);
        while (!stack.isEmpty()) {
            OWLNamedIndividual currentInd = stack.remove(0);
            Stream<OWLSameIndividualAxiom> axioms = getRootOntology()
                    .importsClosure()
                    .flatMap(o -> o.sameIndividualAxioms(currentInd))
                    .filter(ax -> processed.add(ax));
            axioms.forEach(ax -> ax.individuals().filter(i -> i.isNamed())
                    .filter(i -> inds.add(i.asOWLNamedIndividual()))
                    .forEach(i -> stack.add(i.asOWLNamedIndividual())));
        }
        if (inds.isEmpty()) {
            inds.add(ind);
        }
        return new OWLNamedIndividualNode(inds);
    }

    @Nonnull
    @Override
    public NodeSet<OWLNamedIndividual> getDifferentIndividuals(
            OWLNamedIndividual ind) {
        ensurePrepared();
        Set<OWLNamedIndividual> inds = new HashSet<>();
        Set<OWLDifferentIndividualsAxiom> processed = new HashSet<>();
        List<OWLNamedIndividual> stack = new LinkedList<>();
        stack.add(ind);
        while (!stack.isEmpty()) {
            OWLNamedIndividual currentInd = stack.remove(0);
            Stream<OWLDifferentIndividualsAxiom> axioms = Imports.INCLUDED
                    .stream(getRootOntology())
                    .flatMap(o -> o.differentIndividualAxioms(currentInd))
                    .filter(ax -> processed.add(ax));
            axioms.forEach(ax -> ax.individuals()
                    .filter(i -> i.isOWLNamedIndividual())
                    .map(i -> i.asOWLNamedIndividual()).forEach(i -> {
                        if (inds.add(i)) {
                            stack.add(i);
                        }
                    }));
        }
        if (inds.isEmpty()) {
            inds.add(ind);
        }
        return new OWLNamedIndividualNodeSet(asSet(inds.stream().map(
                i -> getSameIndividuals(i))));
    }

    protected OWLDataFactory getDataFactory() {
        return getRootOntology().getOWLOntologyManager().getOWLDataFactory();
    }

    /**
     * @param showBottomNode
     *        true if bottom node is to be showed
     */
    public void dumpClassHierarchy(boolean showBottomNode) {
        dumpClassHierarchy(OWLClassNode.getTopNode(), 0, showBottomNode);
    }

    private void dumpClassHierarchy(@Nonnull Node<OWLClass> cls, int level,
            boolean showBottomNode) {
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

    /**
     * @param showBottomNode
     *        true if bottom node is to be showed
     */
    public void dumpObjectPropertyHierarchy(boolean showBottomNode) {
        dumpObjectPropertyHierarchy(OWLObjectPropertyNode.getTopNode(), 0,
                showBottomNode);
    }

    private void dumpObjectPropertyHierarchy(
            @Nonnull Node<OWLObjectPropertyExpression> cls, int level,
            boolean showBottomNode) {
        if (!showBottomNode && cls.isBottomNode()) {
            return;
        }
        printIndent(level);
        OWLObjectPropertyExpression representative = cls
                .getRepresentativeElement();
        System.out.println(getEquivalentObjectProperties(representative));
        for (Node<OWLObjectPropertyExpression> subProp : getSubObjectProperties(
                representative, true)) {
            dumpObjectPropertyHierarchy(subProp, level + 1, showBottomNode);
        }
    }

    /**
     * @param showBottomNode
     *        true if bottom node is to be showed
     */
    public void dumpDataPropertyHierarchy(boolean showBottomNode) {
        dumpDataPropertyHierarchy(OWLDataPropertyNode.getTopNode(), 0,
                showBottomNode);
    }

    private void dumpDataPropertyHierarchy(@Nonnull Node<OWLDataProperty> cls,
            int level, boolean showBottomNode) {
        if (!showBottomNode && cls.isBottomNode()) {
            return;
        }
        printIndent(level);
        OWLDataProperty representative = cls.getRepresentativeElement();
        System.out.println(getEquivalentDataProperties(representative));
        for (Node<OWLDataProperty> subProp : getSubDataProperties(
                representative, true)) {
            dumpDataPropertyHierarchy(subProp, level + 1, showBottomNode);
        }
    }

    private static void printIndent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
    }

    // HierarchyInfo
    private abstract class AbstractHierarchyInfo<T extends OWLObject> {

        private final RawHierarchyProvider<T> rawParentChildProvider;
        /** The entity that always appears in the top node in the hierarchy. */
        @Nonnull
        T topEntity;
        /** The entity that always appears as the bottom node in the hierarchy. */
        @Nonnull
        T bottomEntity;
        @Nonnull
        private final Set<T> directChildrenOfTopNode = new HashSet<>();
        @Nonnull
        private final Set<T> directParentsOfBottomNode = new HashSet<>();
        private final NodeCache<T> nodeCache;
        private final String name;
        private int classificationSize;

        AbstractHierarchyInfo(String name, @Nonnull T topEntity,
                @Nonnull T bottomEntity,
                RawHierarchyProvider<T> rawParentChildProvider) {
            this.topEntity = topEntity;
            this.bottomEntity = bottomEntity;
            nodeCache = new NodeCache<>(this);
            this.rawParentChildProvider = rawParentChildProvider;
            this.name = name;
        }

        public RawHierarchyProvider<T> getRawParentChildProvider() {
            return rawParentChildProvider;
        }

        /**
         * Gets the set of relevant entities from the specified ontology.
         * 
         * @param ont
         *        The ontology
         * @return A set of entities to be "classified"
         */
        @Nonnull
        protected abstract Stream<T> getEntities(@Nonnull OWLOntology ont);

        /**
         * Creates a node for a given set of entities.
         * 
         * @param cycle
         *        The set of entities
         * @return A node
         */
        @Nonnull
        protected abstract DefaultNode<T> createNode(@Nonnull Set<T> cycle);

        @Nonnull
        protected abstract DefaultNode<T> createNode();

        /**
         * Gets the set of relevant entities in a particular axiom.
         * 
         * @param ax
         *        The axiom
         * @return The set of relevant entities in the signature of the
         *         specified axiom
         */
        @Nonnull
        protected abstract Stream<? extends T> getEntitiesInSignature(
                @Nonnull OWLAxiom ax);

        @Nonnull
        Set<T> getEntitiesInSignature(@Nonnull Set<OWLAxiom> axioms) {
            return asSet(axioms.stream().flatMap(
                    ax -> getEntitiesInSignature(ax)));
        }

        public void computeHierarchy() {
            pm.reasonerTaskStarted("Computing " + name + " hierarchy");
            pm.reasonerTaskBusy();
            nodeCache.clear();
            Map<T, Collection<T>> cache = new HashMap<>();
            Set<T> entities = asSet(getRootOntology().importsClosure().flatMap(
                    o -> getEntities(o)));
            classificationSize = entities.size();
            pm.reasonerTaskProgressChanged(0, classificationSize);
            updateForSignature(entities, cache);
            pm.reasonerTaskStopped();
        }

        private void updateForSignature(@Nonnull Set<T> signature,
                @Nullable Map<T, Collection<T>> cache) {
            HashSet<Set<T>> cyclesResult = new HashSet<>();
            Set<T> processed = new HashSet<>();
            nodeCache.clearTopNode();
            nodeCache.clearBottomNode();
            nodeCache.clearNodes(signature);
            directChildrenOfTopNode.removeAll(signature);
            Set<T> equivTopOrChildrenOfTop = new HashSet<>();
            Set<T> equivBottomOrParentsOfBottom = new HashSet<>();
            for (T entity : signature) {
                if (!processed.contains(entity)) {
                    pm.reasonerTaskProgressChanged(processed.size(),
                            signature.size());
                    tarjan(entity, 0, new Stack<T>(),
                            new HashMap<T, Integer>(),
                            new HashMap<T, Integer>(), cyclesResult, processed,
                            new HashSet<T>(), cache, equivTopOrChildrenOfTop,
                            equivBottomOrParentsOfBottom);
                    throwExceptionIfInterrupted();
                }
            }
            // Store new cycles
            for (Set<T> cycle : cyclesResult) {
                nodeCache.addNode(cycle);
            }
            directChildrenOfTopNode.addAll(equivTopOrChildrenOfTop);
            nodeCache.getTopNode().entities()
                    .forEach(e -> directChildrenOfTopNode.remove(e));
            directParentsOfBottomNode.addAll(equivBottomOrParentsOfBottom);
            nodeCache.getBottomNode().entities()
                    .forEach(e -> directParentsOfBottomNode.remove(e));
            // Now check that each found cycle has a proper parent an child
            for (Set<T> node : cyclesResult) {
                if (!node.contains(topEntity) && !node.contains(bottomEntity)) {
                    boolean childOfTop = true;
                    for (T element : node) {
                        Collection<T> parents = rawParentChildProvider
                                .getParents(element);
                        parents.removeAll(node);
                        nodeCache.getTopNode().entities()
                                .forEach(e -> parents.remove(e));
                        if (!parents.isEmpty()) {
                            childOfTop = false;
                            break;
                        }
                    }
                    if (childOfTop) {
                        directChildrenOfTopNode.addAll(node);
                    }
                    boolean parentOfBottom = true;
                    for (T element : node) {
                        Collection<T> children = rawParentChildProvider
                                .getChildren(element);
                        children.removeAll(node);
                        nodeCache.getBottomNode().entities()
                                .forEach(e -> children.remove(e));
                        if (!children.isEmpty()) {
                            parentOfBottom = false;
                            break;
                        }
                    }
                    if (parentOfBottom) {
                        directParentsOfBottomNode.addAll(node);
                    }
                }
            }
        }

        /**
         * Processes the specified signature that represents the signature of
         * potential changes.
         * 
         * @param signature
         *        The signature
         * @param added
         *        added axioms
         * @param removed
         *        removed axioms
         */
        @SuppressWarnings("unused")
        public void processChanges(@Nonnull Set<T> signature,
                @Nonnull Set<OWLAxiom> added, @Nonnull Set<OWLAxiom> removed) {
            updateForSignature(signature, null);
        }

        /**
         * Applies the tarjan algorithm for a given entity. This computes the
         * cycle that the entity is involved in (if any).
         * 
         * @param entity
         *        The entity
         * @param inputIndex
         *        index
         * @param stack
         *        stack
         * @param indexMap
         *        index map
         * @param lowlinkMap
         *        low link map
         * @param result
         *        result
         * @param processed
         *        processed
         * @param stackEntities
         *        stack entities
         * @param cache
         *        A cache of children to parents - may be {@code null} if no
         *        caching is to take place.
         * @param childrenOfTop
         *        A set of entities that have a raw parent that is the top
         *        entity
         * @param parentsOfBottom
         *        A set of entities that have a raw parent that is the bottom
         *        entity
         */
        public void tarjan(@Nonnull T entity, int inputIndex,
                @Nonnull Stack<T> stack, @Nonnull Map<T, Integer> indexMap,
                @Nonnull Map<T, Integer> lowlinkMap,
                @Nonnull Set<Set<T>> result, @Nonnull Set<T> processed,
                @Nonnull Set<T> stackEntities,
                @Nullable Map<T, Collection<T>> cache,
                @Nonnull Set<T> childrenOfTop, @Nonnull Set<T> parentsOfBottom) {
            int index = inputIndex;
            throwExceptionIfInterrupted();
            if (processed.add(entity)) {
                Collection<T> rawChildren = rawParentChildProvider
                        .getChildren(entity);
                if (rawChildren.isEmpty() || rawChildren.contains(bottomEntity)) {
                    parentsOfBottom.add(entity);
                }
            }
            pm.reasonerTaskProgressChanged(processed.size(), classificationSize);
            indexMap.put(entity, index);
            lowlinkMap.put(entity, index);
            index += 1;
            stack.push(entity);
            stackEntities.add(entity);
            // Get the raw parents - cache if necessary
            Collection<T> rawParents = null;
            if (cache != null) {
                // We are therefore caching raw parents of children.
                rawParents = cache.get(entity);
                if (rawParents == null) {
                    // Not in cache!
                    rawParents = rawParentChildProvider.getParents(entity);
                    // Note down if our entity is a
                    if (rawParents.isEmpty() || rawParents.contains(topEntity)) {
                        childrenOfTop.add(entity);
                    }
                    cache.put(entity, rawParents);
                }
            } else {
                rawParents = rawParentChildProvider.getParents(entity);
                // Note down if our entity is a
                if (rawParents.isEmpty() || rawParents.contains(topEntity)) {
                    childrenOfTop.add(entity);
                }
            }
            for (T superEntity : rawParents) {
                if (!indexMap.containsKey(superEntity)) {
                    tarjan(superEntity, index, stack, indexMap, lowlinkMap,
                            result, processed, stackEntities, cache,
                            childrenOfTop, parentsOfBottom);
                    lowlinkMap.put(
                            entity,
                            Math.min(lowlinkMap.get(entity),
                                    lowlinkMap.get(superEntity)));
                } else if (stackEntities.contains(superEntity)) {
                    lowlinkMap.put(
                            entity,
                            Math.min(lowlinkMap.get(entity),
                                    indexMap.get(superEntity)));
                }
            }
            if (lowlinkMap.get(entity).equals(indexMap.get(entity))) {
                Set<T> scc = new HashSet<>();
                while (true) {
                    T clsPrime = stack.pop();
                    stackEntities.remove(clsPrime);
                    scc.add(clsPrime);
                    if (clsPrime.equals(entity)) {
                        break;
                    }
                }
                if (scc.size() > 1) {
                    // We ADD a cycle
                    result.add(scc);
                }
            }
        }

        @Nonnull
        public NodeSet<T> getNodeHierarchyChildren(@Nonnull T parent,
                boolean direct, @Nonnull DefaultNodeSet<T> ns) {
            Node<T> node = nodeCache.getNode(parent);
            if (node.isBottomNode()) {
                return ns;
            }
            Set<T> directChildren = new HashSet<>();
            for (T equiv : node) {
                directChildren
                        .addAll(rawParentChildProvider.getChildren(equiv));
                if (directParentsOfBottomNode.contains(equiv)) {
                    ns.addNode(nodeCache.getBottomNode());
                }
            }
            node.entities().forEach(e -> directChildren.remove(e));
            if (node.isTopNode()) {
                // Special treatment
                directChildren.addAll(directChildrenOfTopNode);
            }
            for (Node<T> childNode : nodeCache.getNodes(directChildren)) {
                ns.addNode(childNode);
            }
            if (!direct) {
                for (T child : directChildren) {
                    getNodeHierarchyChildren(child, direct, ns);
                }
            }
            return ns;
        }

        @Nonnull
        public NodeSet<T> getNodeHierarchyParents(@Nonnull T child,
                boolean direct, @Nonnull DefaultNodeSet<T> ns) {
            Node<T> node = nodeCache.getNode(child);
            if (node.isTopNode()) {
                return ns;
            }
            Set<T> directParents = new HashSet<>();
            for (T equiv : node) {
                directParents.addAll(rawParentChildProvider.getParents(equiv));
                if (directChildrenOfTopNode.contains(equiv)) {
                    ns.addNode(nodeCache.getTopNode());
                }
            }
            node.entities().forEach(e -> directParents.remove(e));
            if (node.isBottomNode()) {
                // Special treatment
                directParents.addAll(directParentsOfBottomNode);
            }
            for (Node<T> parentNode : nodeCache.getNodes(directParents)) {
                ns.addNode(parentNode);
            }
            if (!direct) {
                for (T parent : directParents) {
                    getNodeHierarchyParents(parent, direct, ns);
                }
            }
            return ns;
        }

        @Nonnull
        public Node<T> getEquivalents(@Nonnull T element) {
            return nodeCache.getNode(element);
        }
    }

    private static class NodeCache<T extends OWLObject> {

        @Nonnull
        private final AbstractHierarchyInfo<T> hierarchyInfo;
        private Node<T> topNode;
        private Node<T> bottomNode;
        @Nonnull
        private final Map<T, Node<T>> map = new HashMap<>();

        protected NodeCache(@Nonnull AbstractHierarchyInfo<T> hierarchyInfo) {
            this.hierarchyInfo = hierarchyInfo;
            clearTopNode();
            clearBottomNode();
        }

        public void addNode(@Nonnull Node<T> node) {
            node.entities().forEach(e -> {
                map.put(e, node);
                if (e.isTopEntity()) {
                    topNode = node;
                } else if (e.isBottomEntity()) {
                    bottomNode = node;
                }
            });
        }

        @Nonnull
        public Set<Node<T>> getNodes(@Nonnull Set<T> elements) {
            Set<Node<T>> result = new HashSet<>();
            for (T element : elements) {
                result.add(getNode(element));
            }
            return result;
        }

        @Nonnull
        public Node<T> getNode(@Nonnull T containing) {
            Node<T> parentNode = map.get(containing);
            if (parentNode != null) {
                return parentNode;
            } else {
                return hierarchyInfo.createNode(CollectionFactory
                        .createSet(containing));
            }
        }

        public void addNode(@Nonnull Set<T> elements) {
            addNode(hierarchyInfo.createNode(elements));
        }

        @Nonnull
        public Node<T> getTopNode() {
            return verifyNotNull(topNode);
        }

        @Nonnull
        public Node<T> getBottomNode() {
            return verifyNotNull(bottomNode);
        }

        public final void clearTopNode() {
            removeNode(hierarchyInfo.topEntity);
            topNode = hierarchyInfo.createNode(CollectionFactory
                    .createSet(hierarchyInfo.topEntity));
            addNode(getTopNode());
        }

        public final void clearBottomNode() {
            removeNode(hierarchyInfo.bottomEntity);
            bottomNode = hierarchyInfo.createNode(CollectionFactory
                    .createSet(hierarchyInfo.bottomEntity));
            addNode(getBottomNode());
        }

        public void clearNodes(@Nonnull Set<T> containing) {
            for (T entity : containing) {
                removeNode(entity);
            }
        }

        public void clear() {
            map.clear();
            clearTopNode();
            clearBottomNode();
        }

        public void removeNode(T containing) {
            Node<T> node = map.remove(containing);
            if (node != null) {
                node.entities().forEach(e -> map.remove(e));
            }
        }
    }

    private class ClassHierarchyInfo extends AbstractHierarchyInfo<OWLClass> {

        ClassHierarchyInfo() {
            super("class", getDataFactory().getOWLThing(), getDataFactory()
                    .getOWLNothing(), new RawClassHierarchyProvider());
        }

        @Nonnull
        @Override
        protected Stream<OWLClass> getEntitiesInSignature(@Nonnull OWLAxiom ax) {
            return ax.classesInSignature();
        }

        @Nonnull
        @Override
        protected DefaultNode<OWLClass>
                createNode(@Nonnull Set<OWLClass> cycle) {
            return new OWLClassNode(cycle);
        }

        @Nonnull
        @Override
        protected Stream<OWLClass> getEntities(@Nonnull OWLOntology ont) {
            return ont.classesInSignature();
        }

        @Nonnull
        @Override
        protected DefaultNode<OWLClass> createNode() {
            return new OWLClassNode();
        }
    }

    private class ObjectPropertyHierarchyInfo extends
            AbstractHierarchyInfo<OWLObjectPropertyExpression> {

        ObjectPropertyHierarchyInfo() {
            super("object property",
                    getDataFactory().getOWLTopObjectProperty(),
                    getDataFactory().getOWLBottomObjectProperty(),
                    new RawObjectPropertyHierarchyProvider());
        }

        @Nonnull
        @Override
        protected Stream<OWLObjectPropertyExpression> getEntitiesInSignature(
                @Nonnull OWLAxiom ax) {
            Set<OWLObjectPropertyExpression> result = new HashSet<>();
            ax.objectPropertiesInSignature().forEach(p -> {
                result.add(p);
                result.add(p.getInverseProperty());
            });
            return result.stream();
        }

        @Nonnull
        @Override
        protected Stream<OWLObjectPropertyExpression> getEntities(
                @Nonnull OWLOntology ont) {
            Set<OWLObjectPropertyExpression> result = new HashSet<>();
            ont.objectPropertiesInSignature().forEach(p -> {
                result.add(p);
                result.add(p.getInverseProperty());
            });
            return result.stream();
        }

        @Nonnull
        @Override
        protected DefaultNode<OWLObjectPropertyExpression> createNode(
                Set<OWLObjectPropertyExpression> cycle) {
            return new OWLObjectPropertyNode(cycle);
        }

        @Nonnull
        @Override
        protected DefaultNode<OWLObjectPropertyExpression> createNode() {
            return new OWLObjectPropertyNode();
        }

        @Override
        public void processChanges(
                @Nonnull Set<OWLObjectPropertyExpression> signature,
                @Nonnull Set<OWLAxiom> added, @Nonnull Set<OWLAxiom> removed) {
            boolean rebuild = false;
            for (OWLAxiom ax : added) {
                if (ax instanceof OWLObjectPropertyAxiom) {
                    rebuild = true;
                    break;
                }
            }
            if (!rebuild) {
                for (OWLAxiom ax : removed) {
                    if (ax instanceof OWLObjectPropertyAxiom) {
                        rebuild = true;
                        break;
                    }
                }
            }
            if (rebuild) {
                ((RawObjectPropertyHierarchyProvider) getRawParentChildProvider())
                        .rebuild();
            }
            super.processChanges(signature, added, removed);
        }
    }

    private class DataPropertyHierarchyInfo extends
            AbstractHierarchyInfo<OWLDataProperty> {

        DataPropertyHierarchyInfo() {
            super("data property", getDataFactory().getOWLTopDataProperty(),
                    getDataFactory().getOWLBottomDataProperty(),
                    new RawDataPropertyHierarchyProvider());
        }

        @Override
        protected Stream<OWLDataProperty> getEntitiesInSignature(
                @Nonnull OWLAxiom ax) {
            return ax.dataPropertiesInSignature();
        }

        @Override
        protected Stream<OWLDataProperty> getEntities(@Nonnull OWLOntology ont) {
            return ont.dataPropertiesInSignature();
        }

        @Nonnull
        @Override
        protected DefaultNode<OWLDataProperty> createNode(
                Set<OWLDataProperty> cycle) {
            return new OWLDataPropertyNode(cycle);
        }

        @Nonnull
        @Override
        protected DefaultNode<OWLDataProperty> createNode() {
            return new OWLDataPropertyNode();
        }
    }

    /**
     * An interface for objects who can provide the parents and children of some
     * object.
     * 
     * @param <T>
     *        type of elements
     */
    private interface RawHierarchyProvider<T> {

        /**
         * Gets the parents as asserted. These parents may also be children
         * (resulting in equivalences).
         * 
         * @param child
         *        The child whose parents are to be retrieved
         * @return The raw asserted parents of the specified child. If the child
         *         does not have any parents then the empty set can be returned.
         */
        @Nonnull
        Collection<T> getParents(@Nonnull T child);

        /**
         * Gets the children as asserted.
         * 
         * @param parent
         *        The parent whose children are to be retrieved
         * @return The raw asserted children of the speicified parent
         */
        @Nonnull
        Collection<T> getChildren(@Nonnull T parent);
    }

    private class RawClassHierarchyProvider implements
            RawHierarchyProvider<OWLClass> {

        RawClassHierarchyProvider() {}

        @Nonnull
        @Override
        public Collection<OWLClass> getParents(OWLClass child) {
            Collection<OWLClass> result = new HashSet<>();
            for (OWLOntology ont : asList(getRootOntology().importsClosure())) {
                for (OWLSubClassOfAxiom ax : asList(ont
                        .subClassAxiomsForSubClass(child))) {
                    OWLClassExpression superCls = ax.getSuperClass();
                    if (!superCls.isAnonymous()) {
                        result.add(superCls.asOWLClass());
                    } else if (superCls instanceof OWLObjectIntersectionOf) {
                        OWLObjectIntersectionOf intersectionOf = (OWLObjectIntersectionOf) superCls;
                        for (OWLClassExpression conjunct : intersectionOf
                                .asConjunctSet()) {
                            if (!conjunct.isAnonymous()) {
                                result.add(conjunct.asOWLClass());
                            }
                        }
                    }
                }
                for (OWLEquivalentClassesAxiom ax : asList(ont
                        .equivalentClassesAxioms(child))) {
                    for (OWLClassExpression ce : ax
                            .getClassExpressionsMinus(child)) {
                        if (!ce.isAnonymous()) {
                            result.add(ce.asOWLClass());
                        } else if (ce instanceof OWLObjectIntersectionOf) {
                            OWLObjectIntersectionOf intersectionOf = (OWLObjectIntersectionOf) ce;
                            for (OWLClassExpression conjunct : intersectionOf
                                    .asConjunctSet()) {
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

        @Nonnull
        @Override
        public Collection<OWLClass> getChildren(OWLClass parent) {
            Collection<OWLClass> result = new HashSet<>();
            for (OWLAxiom ax : asSet(getRootOntology().referencingAxioms(
                    parent, INCLUDED))) {
                if (ax instanceof OWLSubClassOfAxiom) {
                    OWLSubClassOfAxiom sca = (OWLSubClassOfAxiom) ax;
                    if (!sca.getSubClass().isAnonymous()) {
                        Set<OWLClassExpression> conjuncts = sca.getSuperClass()
                                .asConjunctSet();
                        if (conjuncts.contains(parent)) {
                            result.add(sca.getSubClass().asOWLClass());
                        }
                    }
                } else if (ax instanceof OWLEquivalentClassesAxiom) {
                    OWLEquivalentClassesAxiom eca = (OWLEquivalentClassesAxiom) ax;
                    eca.classExpressions()
                            .filter(ce -> ce.containsConjunct(parent))
                            .forEach(
                                    ce -> eca
                                            .classExpressions()
                                            .forEach(
                                                    sub -> {
                                                        if (!sub.isAnonymous()
                                                                && !sub.equals(ce)) {
                                                            result.add(sub
                                                                    .asOWLClass());
                                                        }
                                                    }));
                }
            }
            return result;
        }
    }

    private class RawObjectPropertyHierarchyProvider implements
            RawHierarchyProvider<OWLObjectPropertyExpression> {

        private OWLObjectPropertyManager propertyManager;
        private Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> sub2Super;
        private Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> super2Sub;

        RawObjectPropertyHierarchyProvider() {
            rebuild();
        }

        public final void rebuild() {
            propertyManager = new OWLObjectPropertyManager(getRootOntology());
            sub2Super = propertyManager.getPropertyHierarchy();
            super2Sub = new HashMap<>();
            for (OWLObjectPropertyExpression sub : sub2Super.keySet()) {
                for (OWLObjectPropertyExpression superProp : sub2Super.get(sub)) {
                    Set<OWLObjectPropertyExpression> subs = super2Sub
                            .get(superProp);
                    if (subs == null) {
                        subs = new HashSet<>();
                        super2Sub.put(superProp, subs);
                    }
                    subs.add(sub);
                }
            }
        }

        @Nonnull
        @Override
        public Collection<OWLObjectPropertyExpression> getParents(
                @Nonnull OWLObjectPropertyExpression child) {
            if (child.isBottomEntity()) {
                return Collections.emptySet();
            }
            Set<OWLObjectPropertyExpression> propertyExpressions = sub2Super
                    .get(child);
            if (propertyExpressions == null) {
                return Collections.emptySet();
            } else {
                return new HashSet<>(propertyExpressions);
            }
        }

        @Nonnull
        @Override
        public Collection<OWLObjectPropertyExpression> getChildren(
                @Nonnull OWLObjectPropertyExpression parent) {
            if (parent.isTopEntity()) {
                return Collections.emptySet();
            }
            Set<OWLObjectPropertyExpression> propertyExpressions = super2Sub
                    .get(parent);
            if (propertyExpressions == null) {
                return Collections.emptySet();
            } else {
                return new HashSet<>(propertyExpressions);
            }
        }
    }

    private class RawDataPropertyHierarchyProvider implements
            RawHierarchyProvider<OWLDataProperty> {

        RawDataPropertyHierarchyProvider() {}

        @Nonnull
        @Override
        public Collection<OWLDataProperty> getParents(
                @Nonnull OWLDataProperty child) {
            Set<OWLDataProperty> properties = new HashSet<>();
            Stream<OWLAxiom> axioms = getRootOntology().axioms(
                    Filters.subDataPropertyWithSub, child, INCLUDED);
            sup(axioms, OWLDataPropertyExpression.class).forEach(
                    p -> properties.add(p.asOWLDataProperty()));
            return properties;
        }

        @Nonnull
        @Override
        public Collection<OWLDataProperty> getChildren(
                @Nonnull OWLDataProperty parent) {
            Set<OWLDataProperty> properties = new HashSet<>();
            Stream<OWLAxiom> axioms = getRootOntology().axioms(
                    Filters.subDataPropertyWithSuper, parent, INCLUDED);
            sub(axioms, OWLDataPropertyExpression.class).forEach(
                    p -> properties.add(p.asOWLDataProperty()));
            return properties;
        }
    }
}
