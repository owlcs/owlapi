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
package org.semanticweb.owlapi.rdf;

import static java.util.stream.Collectors.toList;
import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.model.RDFGraph;
import org.semanticweb.owlapi.rdf.model.RDFTranslator;
import org.semanticweb.owlapi.util.AlwaysOutputId;
import org.semanticweb.owlapi.util.AxiomSubjectProviderEx;
import org.semanticweb.owlapi.util.IndividualAppearance;
import org.semanticweb.owlapi.util.OWLAnonymousIndividualsWithMultipleOccurrences;
import org.semanticweb.owlapi.util.SWRLVariableExtractor;

import gnu.trove.map.custom_hash.TObjectIntCustomHashMap;
import gnu.trove.strategy.IdentityHashingStrategy;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public abstract class RDFRendererBase {

    private static final @Nonnull String ANNOTATION_PROPERTIES_BANNER_TEXT = "Annotation properties";
    private static final @Nonnull String DATATYPES_BANNER_TEXT = "Datatypes";
    private static final @Nonnull String OBJECT_PROPERTIES_BANNER_TEXT = "Object Properties";
    private static final @Nonnull String DATA_PROPERTIES_BANNER_TEXT = "Data properties";
    private static final @Nonnull String CLASSES_BANNER_TEXT = "Classes";
    private static final @Nonnull String INDIVIDUALS_BANNER_TEXT = "Individuals";
    private static final @Nonnull String ANNOTATED_IRIS_BANNER_TEXT = "Annotations";
    /** General axioms. */
    private static final @Nonnull String GENERAL_AXIOMS_BANNER_TEXT = "General axioms";
    /** Rules banner. */
    private static final @Nonnull String RULES_BANNER_TEXT = "Rules";
    protected final @Nonnull OWLOntology ontology;
    protected RDFGraph graph;
    protected final @Nonnull Set<IRI> prettyPrintedTypes = asSet(Stream.of(OWL_CLASS, OWL_OBJECT_PROPERTY,
        OWL_DATA_PROPERTY, OWL_ANNOTATION_PROPERTY, OWL_RESTRICTION, OWL_THING, OWL_NOTHING, OWL_ONTOLOGY,
        OWL_ANNOTATION_PROPERTY, OWL_NAMED_INDIVIDUAL, RDFS_DATATYPE, OWL_AXIOM, OWL_ANNOTATION).map(a -> a.getIRI()));
    private final OWLDocumentFormat format;
    private Set<IRI> punned;
    protected final IndividualAppearance occurrences;

    /**
     * @param ontology
     *        ontology
     */
    public RDFRendererBase(OWLOntology ontology) {
        this(ontology, ontology.getOWLOntologyManager().getOntologyFormat(ontology));
    }

    protected RDFRendererBase(OWLOntology ontology, OWLDocumentFormat format) {
        this.ontology = ontology;
        this.format = format;
        if (AnonymousIndividualProperties.shouldSaveIdsForAllAnonymousIndividuals()) {
            occurrences = new AlwaysOutputId();
        } else {
            OWLAnonymousIndividualsWithMultipleOccurrences visitor = new OWLAnonymousIndividualsWithMultipleOccurrences();
            occurrences = visitor;
            ontology.accept(visitor);
        }
    }

    /** Hooks for subclasses */
    /**
     * Called before the ontology document is rendered.
     */
    protected abstract void beginDocument();

    /**
     * Called after the ontology document has been rendered.
     */
    protected abstract void endDocument();

    /**
     * Called before an OWLObject such as an entity, anonymous individual, rule
     * etc. is rendered.
     */
    protected void beginObject() {}

    /**
     * Called after an OWLObject such as an entity, anonymous individual, rule
     * etc. has been rendered.
     */
    protected void endObject() {}

    /**
     * Called before an annotation property is rendered to give subclasses the
     * chance to prefix the rendering with comments etc.
     * 
     * @param prop
     *        The property being rendered
     */
    protected abstract void writeAnnotationPropertyComment(OWLAnnotationProperty prop);

    /**
     * Called before a data property is rendered to give subclasses the chance
     * to prefix the rendering with comments etc.
     * 
     * @param prop
     *        The property being rendered
     */
    protected abstract void writeDataPropertyComment(OWLDataProperty prop);

    /**
     * Called before an object property is rendered.
     * 
     * @param prop
     *        The property being rendered
     */
    protected abstract void writeObjectPropertyComment(OWLObjectProperty prop);

    /**
     * Called before a class is rendered to give subclasses the chance to prefix
     * the rendering with comments etc.
     * 
     * @param cls
     *        The class being rendered
     */
    protected abstract void writeClassComment(OWLClass cls);

    /**
     * Called before a datatype is rendered to give subclasses the chance to
     * prefix the rendering with comments etc.
     * 
     * @param datatype
     *        The datatype being rendered
     */
    protected abstract void writeDatatypeComment(OWLDatatype datatype);

    /**
     * Called before an individual is rendered to give subclasses the chance to
     * prefix the rendering with comments etc.
     * 
     * @param ind
     *        The individual being rendered
     */
    protected abstract void writeIndividualComments(OWLNamedIndividual ind);

    /** Render document. */
    public void render() {
        graph = new RDFGraph();
        punned = ontology.getPunnedIRIs(EXCLUDED);
        beginDocument();
        renderOntologyHeader();
        renderOntologyComponents();
        endDocument();
    }

    private void renderOntologyComponents() {
        renderInOntologySignatureEntities(OWLDocumentFormat.determineIllegalPunnings(shouldInsertDeclarations(),
            asList(ontology.signature()), ontology.getPunnedIRIs(INCLUDED)));
        renderAnonymousIndividuals();
        renderUntypedIRIAnnotationAssertions();
        renderGeneralAxioms();
        renderSWRLRules();
    }

    private void renderInOntologySignatureEntities(Collection<IRI> illegalPuns) {
        renderEntities(ontology.annotationPropertiesInSignature(), ANNOTATION_PROPERTIES_BANNER_TEXT, illegalPuns);
        renderEntities(ontology.datatypesInSignature(), DATATYPES_BANNER_TEXT, illegalPuns);
        renderEntities(ontology.objectPropertiesInSignature(), OBJECT_PROPERTIES_BANNER_TEXT, illegalPuns);
        renderEntities(ontology.dataPropertiesInSignature(), DATA_PROPERTIES_BANNER_TEXT, illegalPuns);
        renderEntities(ontology.classesInSignature(), CLASSES_BANNER_TEXT, illegalPuns);
        renderEntities(ontology.individualsInSignature(), INDIVIDUALS_BANNER_TEXT, illegalPuns);
    }

    /**
     * Renders a set of entities.
     * 
     * @param entities
     *        The entities. Not null.
     * @param bannerText
     *        The banner text that will prefix the rendering of the entities if
     *        anything is rendered. Not null. May be empty, in which case no
     *        banner will be written.
     * @param illegalPuns
     *        illegal puns
     */
    private void renderEntities(Stream<? extends OWLEntity> entities, String bannerText, Collection<IRI> illegalPuns) {
        boolean firstRendering = true;
        for (OWLEntity entity : toSortedSet(entities)) {
            if (createGraph(entity, illegalPuns)) {
                if (firstRendering) {
                    firstRendering = false;
                    if (!bannerText.isEmpty()) {
                        writeBanner(bannerText);
                    }
                }
                renderEntity(entity);
            }
        }
    }

    private void renderEntity(OWLEntity entity) {
        beginObject();
        writeEntityComment(entity);
        render(new RDFResourceIRI(entity.getIRI()));
        renderAnonRoots();
        endObject();
    }

    /**
     * Calls the appropriate hook method to write the comments for an entity.
     * 
     * @param entity
     *        The entity for which comments should be written.
     */
    private void writeEntityComment(OWLEntity entity) {
        if (entity.isOWLClass()) {
            writeClassComment(entity.asOWLClass());
        } else if (entity.isOWLDatatype()) {
            writeDatatypeComment(entity.asOWLDatatype());
        } else if (entity.isOWLObjectProperty()) {
            writeObjectPropertyComment(entity.asOWLObjectProperty());
        } else if (entity.isOWLDataProperty()) {
            writeDataPropertyComment(entity.asOWLDataProperty());
        } else if (entity.isOWLAnnotationProperty()) {
            writeAnnotationPropertyComment(entity.asOWLAnnotationProperty());
        } else if (entity.isOWLNamedIndividual()) {
            writeIndividualComments(entity.asOWLNamedIndividual());
        }
    }

    private void renderUntypedIRIAnnotationAssertions() {
        Set<IRI> annotatedIRIs = new TreeSet<>();
        ontology.axioms(AxiomType.ANNOTATION_ASSERTION).forEach(ax -> {
            OWLAnnotationSubject subject = ax.getSubject();
            if (subject instanceof IRI) {
                IRI iri = (IRI) subject;
                if (punned.contains(iri) || !ontology.containsEntityInSignature(iri)) {
                    annotatedIRIs.add(iri);
                }
            }
        });
        if (!annotatedIRIs.isEmpty()) {
            writeBanner(ANNOTATED_IRIS_BANNER_TEXT);
            for (IRI iri : annotatedIRIs) {
                beginObject();
                createGraph(ontology.annotationAssertionAxioms(iri));
                render(new RDFResourceIRI(iri));
                renderAnonRoots();
                endObject();
            }
        }
    }

    private void renderAnonymousIndividuals() {
        for (OWLAnonymousIndividual anonInd : sortOptionally(ontology.referencedAnonymousIndividuals())) {
            boolean anonRoot = true;
            Set<OWLAxiom> axioms = new TreeSet<>();
            for (OWLAxiom ax : sortOptionally(ontology.referencingAxioms(anonInd))) {
                if (!(ax instanceof OWLDifferentIndividualsAxiom)) {
                    OWLObject obj = AxiomSubjectProviderEx.getSubject(ax);
                    if (!obj.equals(anonInd)) {
                        anonRoot = false;
                        break;
                    } else {
                        axioms.add(ax);
                    }
                }
            }
            if (anonRoot) {
                createGraph(axioms.stream());
                renderAnonRoots();
            }
        }
    }

    private void renderSWRLRules() {
        List<SWRLRule> ruleAxioms = asList(ontology.axioms(AxiomType.SWRL_RULE).sorted());
        createGraph(ruleAxioms.stream());
        if (!ruleAxioms.isEmpty()) {
            writeBanner(RULES_BANNER_TEXT);
            SWRLVariableExtractor variableExtractor = new SWRLVariableExtractor();
            ruleAxioms.forEach(rule -> rule.accept(variableExtractor));
            variableExtractor.getVariables().forEach(var -> render(new RDFResourceIRI(var.getIRI())));
            renderAnonRoots();
        }
    }

    private void renderGeneralAxioms() {
        boolean haveWrittenBanner = false;
        List<OWLAxiom> generalAxioms = getGeneralAxioms();
        for (OWLAxiom axiom : generalAxioms) {
            createGraph(Stream.of(axiom));
            Set<RDFResourceBlankNode> rootNodes = graph.getRootAnonymousNodes();
            if (!rootNodes.isEmpty()) {
                if (!haveWrittenBanner) {
                    writeBanner(GENERAL_AXIOMS_BANNER_TEXT);
                    haveWrittenBanner = true;
                }
                beginObject();
                renderAnonRoots();
                endObject();
            }
        }
    }

    /**
     * Gets the general axioms in the ontology. These are axioms such as
     * DifferentIndividuals, General Class axioms which do not describe or
     * define a named class and so can't be written out as a frame, nary
     * disjoint classes, disjoint object properties, disjoint data properties
     * and HasKey axioms where the class expression is anonymous.
     * 
     * @return A set of axioms that are general axioms (and can't be written out
     *         in a frame-based style).
     */
    private List<OWLAxiom> getGeneralAxioms() {
        List<OWLAxiom> generalAxioms = new ArrayList<>();
        add(generalAxioms, ontology.generalClassAxioms());
        add(generalAxioms, ontology.axioms(AxiomType.DIFFERENT_INDIVIDUALS));
        ontology.axioms(AxiomType.DISJOINT_CLASSES)
            .filter(ax -> ax.classExpressions().count() > 2)
            .forEach(ax -> generalAxioms.add(ax));
        ontology.axioms(AxiomType.DISJOINT_OBJECT_PROPERTIES).filter(ax -> ax.properties().count() > 2).forEach(
            ax -> generalAxioms.add(ax));
        ontology.axioms(AxiomType.DISJOINT_DATA_PROPERTIES).filter(ax -> ax.properties().count() > 2).forEach(
            ax -> generalAxioms.add(ax));
        ontology.axioms(AxiomType.HAS_KEY).filter(ax -> ax.getClassExpression().isAnonymous()).forEach(
            ax -> generalAxioms.add(ax));
        generalAxioms.sort(null);
        return generalAxioms;
    }

    protected void renderOntologyHeader() {
        RDFTranslator translator = new RDFTranslator(
            ontology.getOWLOntologyManager(), ontology,
            shouldInsertDeclarations(), occurrences);
        graph = translator.getGraph();
        RDFResource ontologyHeaderNode = createOntologyHeaderNode(translator);
        addVersionIRIToOntologyHeader(ontologyHeaderNode, translator);
        addImportsDeclarationsToOntologyHeader(ontologyHeaderNode, translator);
        addAnnotationsToOntologyHeader(ontologyHeaderNode, translator);
        if (!graph.isEmpty()) {
            render(ontologyHeaderNode);
        }
    }

    private RDFResource createOntologyHeaderNode(RDFTranslator translator) {
        ontology.accept(translator);
        return translator.getMappedNode(ontology);
    }

    private void addVersionIRIToOntologyHeader(RDFResource ontologyHeaderNode, RDFTranslator translator) {
        OWLOntologyID ontID = ontology.getOntologyID();
        if (ontID.getVersionIRI().isPresent()) {
            translator.addTriple(ontologyHeaderNode, OWL_VERSION_IRI.getIRI(), ontID.getVersionIRI().get());
        }
    }

    private void addImportsDeclarationsToOntologyHeader(RDFResource ontologyHeaderNode, RDFTranslator translator) {
        ontology.importsDeclarations().forEach(decl -> translator.addTriple(ontologyHeaderNode, OWL_IMPORTS.getIRI(),
            decl.getIRI()));
    }

    private void addAnnotationsToOntologyHeader(RDFResource ontologyHeaderNode, RDFTranslator translator) {
        ontology.annotations().forEach(a -> {
            translator.addTriple(ontologyHeaderNode,
                a.getProperty().getIRI(), a.getValue());
            if (a.getValue() instanceof OWLAnonymousIndividual) {
                OWLAnonymousIndividual i = (OWLAnonymousIndividual) a.getValue();
                ontology.referencingAxioms(i).forEach(ax -> ax.accept(translator));
            }
        });
    }

    private boolean createGraph(OWLEntity entity, Collection<IRI> illegalPuns) {
        final Set<OWLAxiom> axioms = new TreeSet<>();
        // Don't write out duplicates for punned annotations!
        if (!punned.contains(entity.getIRI())) {
            add(axioms, ontology.annotationAssertionAxioms(entity.getIRI(), EXCLUDED));
        }
        add(ontology.declarationAxioms(entity), axioms);
        entity.accept(new OWLEntityVisitor() {

            @Override
            public void visit(OWLClass cls) {
                for (OWLAxiom ax : asList(ontology.axioms(cls))) {
                    if (ax instanceof OWLDisjointClassesAxiom) {
                        OWLDisjointClassesAxiom disjAx = (OWLDisjointClassesAxiom) ax;
                        if (disjAx.classExpressions().count() > 2) {
                            continue;
                        }
                    }
                    axioms.add(ax);
                }
                ontology.axioms(AxiomType.HAS_KEY).filter(ax -> ax.getClassExpression().equals(cls))
                    .forEach(ax -> axioms.add(ax));
            }

            @Override
            public void visit(OWLDatatype datatype) {
                add(ontology.datatypeDefinitions(datatype), axioms);
                createGraph(axioms.stream());
            }

            @Override
            public void visit(OWLNamedIndividual individual) {
                for (OWLAxiom ax : sortOptionally(ontology.axioms(individual))) {
                    if (ax instanceof OWLDifferentIndividualsAxiom) {
                        continue;
                    }
                    axioms.add(ax);
                }
                // for object property assertion axioms where the property is
                // anonymous and the individual is the object, the renderer will
                // save the simplified version of the axiom.
                // As they will have subject and object inverted, we need to
                // collect them here, otherwise the triple will not be included
                // because the subject will not match
                for (OWLAxiom ax : asList(ontology.referencingAxioms(individual))) {
                    if (ax instanceof OWLObjectPropertyAssertionAxiom) {
                        OWLObjectPropertyAssertionAxiom candidate = (OWLObjectPropertyAssertionAxiom) ax;
                        if (candidate.getProperty().isAnonymous() && candidate.getObject().equals(individual)) {
                            axioms.add(candidate);
                        }
                    }
                }
            }

            @Override
            public void visit(OWLDataProperty property) {
                for (OWLAxiom ax : asList(ontology.axioms(property))) {
                    if (ax instanceof OWLDisjointDataPropertiesAxiom
                        && ((OWLDisjointDataPropertiesAxiom) ax).properties().count() > 2) {
                        continue;
                    }
                    axioms.add(ax);
                }
            }

            @Override
            public void visit(OWLObjectProperty property) {
                for (OWLAxiom ax : ontology.axioms(property).collect(toList())) {
                    if (ax instanceof OWLDisjointObjectPropertiesAxiom
                        && ((OWLDisjointObjectPropertiesAxiom) ax).properties().count() > 2) {
                        continue;
                    }
                    axioms.add(ax);
                }
                ontology.axioms(AxiomType.SUB_PROPERTY_CHAIN_OF).filter(ax -> ax.getSuperProperty().equals(property))
                    .forEach(ax -> axioms.add(ax));
                add(ontology.axioms(
                    ontology.getOWLOntologyManager().getOWLDataFactory().getOWLObjectInverseOf(property)), axioms);
            }

            @Override
            public void visit(OWLAnnotationProperty property) {
                add(ontology.axioms(property), axioms);
            }
        });
        if (axioms.isEmpty() && shouldInsertDeclarations() && !illegalPuns.contains(entity.getIRI())
            && OWLDocumentFormatImpl.isMissingType(entity, ontology)) {
            axioms.add(ontology.getOWLOntologyManager().getOWLDataFactory().getOWLDeclarationAxiom(entity));
        }
        createGraph(axioms.stream());
        return !axioms.isEmpty();
    }

    protected boolean shouldInsertDeclarations() {
        return format == null || format.isAddMissingTypes();
    }

    private AtomicInteger nextBlankNodeId = new AtomicInteger(1);
    private TObjectIntCustomHashMap<Object> blankNodeMap = new TObjectIntCustomHashMap<>(
        new IdentityHashingStrategy<>());

    protected RDFResourceBlankNode getBlankNodeFor(Object key, boolean isIndividual, boolean needId) {
        int id = blankNodeMap.get(key);
        if (id == 0) {
            id = nextBlankNodeId.getAndIncrement();
            blankNodeMap.put(key, id);
        }
        return new RDFResourceBlankNode(id, isIndividual, needId);
    }

    private class SequentialBlankNodeRDFTranslator extends RDFTranslator {

        public SequentialBlankNodeRDFTranslator() {
            super(ontology.getOWLOntologyManager(), ontology, shouldInsertDeclarations(), occurrences);
        }

        @Override
        protected RDFResourceBlankNode getAnonymousNode(Object key) {
            checkNotNull(key, "key cannot be null");
            boolean isIndividual = key instanceof OWLAnonymousIndividual;
            boolean needId = false;
            if (isIndividual) {
                OWLAnonymousIndividual anonymousIndividual = (OWLAnonymousIndividual) key;
                needId = multipleOccurrences.appearsMultipleTimes(anonymousIndividual);
                key = anonymousIndividual.getID().getID();
            }
            return getBlankNodeFor(key, isIndividual, needId);
        }
    }

    protected void createGraph(Stream<? extends OWLObject> objects) {
        RDFTranslator translator = new SequentialBlankNodeRDFTranslator();
        objects.forEach(obj -> obj.accept(translator));
        graph = translator.getGraph();
    }

    protected abstract void writeBanner(String name);

    private static List<OWLEntity> toSortedSet(Stream<? extends OWLEntity> entities) {
        Stream<? extends OWLEntity> sorted = entities.sorted((o1, o2) -> o1.getIRI().compareTo(o2.getIRI()));
        return asList(sorted, OWLEntity.class);
    }

    /** Render anonymous roots. */
    public void renderAnonRoots() {
        graph.getRootAnonymousNodes().stream().sorted().forEach(node -> render(node));
    }

    /**
     * Renders the triples in the current graph into a concrete format.
     * Subclasses of this class decide upon how the triples get rendered.
     * 
     * @param node
     *        The main node to be rendered
     */
    public abstract void render(RDFResource node);

    protected boolean isObjectList(RDFResource node) {
        for (RDFTriple triple : graph.getTriplesForSubject(node)) {
            if (triple.getPredicate().getIRI().equals(RDF_TYPE.getIRI()) && !triple.getObject().isAnonymous()
                && triple.getObject().getIRI().equals(RDF_LIST.getIRI())) {
                List<RDFNode> items = new ArrayList<>();
                toJavaList(node, items);
                for (RDFNode n : items) {
                    if (n.isLiteral()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    protected void toJavaList(RDFNode n, List<RDFNode> list) {
        RDFNode currentNode = n;
        while (currentNode != null) {
            for (RDFTriple triple : graph.getTriplesForSubject(currentNode)) {
                if (triple.getPredicate().getIRI().equals(RDF_FIRST.getIRI())) {
                    list.add(triple.getObject());
                }
            }
            for (RDFTriple triple : graph.getTriplesForSubject(currentNode)) {
                if (triple.getPredicate().getIRI().equals(RDF_REST.getIRI())) {
                    if (!triple.getObject().isAnonymous()) {
                        if (triple.getObject().getIRI().equals(RDF_NIL.getIRI())) {
                            // End of list
                            currentNode = null;
                        }
                    } else {
                        if (triple.getObject() instanceof RDFResource) {
                            // Should be another list
                            currentNode = triple.getObject();
                            // toJavaList(triple.getObject(), list);
                        }
                    }
                }
            }
        }
    }
}
