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

import static org.semanticweb.owlapi.model.AxiomType.ANNOTATION_ASSERTION;
import static org.semanticweb.owlapi.model.AxiomType.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi.model.AxiomType.DISJOINT_CLASSES;
import static org.semanticweb.owlapi.model.AxiomType.DISJOINT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.model.AxiomType.DISJOINT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.model.AxiomType.HAS_KEY;
import static org.semanticweb.owlapi.model.AxiomType.SUB_PROPERTY_CHAIN_OF;
import static org.semanticweb.owlapi.model.AxiomType.SWRL_RULE;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_AXIOM;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NOTHING;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ONTOLOGY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_THING;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_FIRST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_LIST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_NIL;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_REST;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDF_TYPE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyWriterConfiguration;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.rdf.model.RDFGraph;
import org.semanticweb.owlapi.rdf.model.RDFTranslator;
import org.semanticweb.owlapi.util.AxiomAppearance;
import org.semanticweb.owlapi.util.AxiomSubjectProviderEx;
import org.semanticweb.owlapi.util.IndividualAppearance;
import org.semanticweb.owlapi.util.OWLAnonymousIndividualsWithMultipleOccurrences;
import org.semanticweb.owlapi.util.OWLObjectDesharer;
import org.semanticweb.owlapi.util.SWRLVariableExtractor;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public abstract class RDFRendererBase {

    private static final String ANNOTATION_PROPERTIES_BANNER_TEXT = "Annotation properties";
    private static final String DATATYPES_BANNER_TEXT = "Datatypes";
    private static final String OBJECT_PROPERTIES_BANNER_TEXT = "Object Properties";
    private static final String DATA_PROPERTIES_BANNER_TEXT = "Data properties";
    private static final String CLASSES_BANNER_TEXT = "Classes";
    private static final String INDIVIDUALS_BANNER_TEXT = "Individuals";
    private static final String ANNOTATED_IRIS_BANNER_TEXT = "Annotations";
    /**
     * General axioms.
     */
    private static final String GENERAL_AXIOMS_BANNER_TEXT = "General axioms";
    /**
     * Rules banner.
     */
    private static final String RULES_BANNER_TEXT = "Rules";
    protected final OWLOntology ontology;
    protected final OWLDataFactory df;
    protected final Set<IRI> prettyPrintedTypes =
        asUnorderedSet(Stream
            .of(OWL_CLASS, OWL_OBJECT_PROPERTY, OWL_DATA_PROPERTY, OWL_ANNOTATION_PROPERTY,
                OWL_RESTRICTION, OWL_THING, OWL_NOTHING, OWL_ONTOLOGY, OWL_ANNOTATION_PROPERTY,
                OWL_NAMED_INDIVIDUAL, RDFS_DATATYPE, OWL_AXIOM, OWL_ANNOTATION)
            .map(a -> a.getIRI()));
    protected final IndividualAppearance occurrences;
    protected final AxiomAppearance axiomOccurrences;
    protected final Set<OWLAxiom> translatedAxioms = new HashSet<>();
    protected final OWLOntologyWriterConfiguration config;
    protected final Set<RDFResource> pending = new HashSet<>();
    @Nullable
    private final OWLDocumentFormat format;
    private final Set<IRI> punned;
    @Nullable
    protected RDFGraph graph;
    private AtomicInteger nextBlankNodeId = new AtomicInteger(1);
    private final Deque<RDFResourceBlankNode> nodesToRenderSeparately = new LinkedList<>();
    private final Set<RDFResourceBlankNode> renderedNodes = new HashSet<>();
    private final Map<Object, Integer> blankNodeMap = new IdentityHashMap<>();
    private final OWLObjectDesharer desharer;

    /**
     * @param ontology ontology
     */
    public RDFRendererBase(OWLOntology ontology) {
        this(ontology, ontology.getFormat(),
            ontology.getOWLOntologyManager().getOntologyWriterConfiguration());
    }

    protected RDFRendererBase(OWLOntology ontology, @Nullable OWLDocumentFormat format,
        OWLOntologyWriterConfiguration config) {
        this.ontology = ontology;
        this.config = config;
        OWLOntologyManager m = this.ontology.getOWLOntologyManager();
        desharer = new OWLObjectDesharer(m);
        df = m.getOWLDataFactory();
        this.format = format;
        if (m.getOntologyWriterConfiguration().shouldSaveIdsForAllAnonymousIndividuals()) {
            occurrences = x -> true;
            axiomOccurrences = x -> true;
        } else {
            OWLAnonymousIndividualsWithMultipleOccurrences visitor =
                new OWLAnonymousIndividualsWithMultipleOccurrences();
            occurrences = visitor;
            ontology.accept(visitor);
            axiomOccurrences = x -> x.annotations().anyMatch(a -> !a.annotationsAsList().isEmpty());
        }
        punned = ontology.getPunnedIRIs(EXCLUDED);
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
     * Called before an OWLObject such as an entity, anonymous individual, rule etc. is rendered.
     */
    protected void beginObject() {}

    /**
     * Called after an OWLObject such as an entity, anonymous individual, rule etc. has been
     * rendered.
     */
    protected void endObject() {}

    /**
     * Called before an annotation property is rendered to give subclasses the chance to prefix the
     * rendering with comments etc.
     *
     * @param prop The property being rendered
     */
    protected abstract void writeAnnotationPropertyComment(OWLAnnotationProperty prop);

    /**
     * Called before a data property is rendered to give subclasses the chance to prefix the
     * rendering with comments etc.
     *
     * @param prop The property being rendered
     */
    protected abstract void writeDataPropertyComment(OWLDataProperty prop);

    /**
     * Called before an object property is rendered.
     *
     * @param prop The property being rendered
     */
    protected abstract void writeObjectPropertyComment(OWLObjectProperty prop);

    /**
     * Called before a class is rendered to give subclasses the chance to prefix the rendering with
     * comments etc.
     *
     * @param cls The class being rendered
     */
    protected abstract void writeClassComment(OWLClass cls);

    /**
     * Called before a datatype is rendered to give subclasses the chance to prefix the rendering
     * with comments etc.
     *
     * @param datatype The datatype being rendered
     */
    protected abstract void writeDatatypeComment(OWLDatatype datatype);

    /**
     * Called before an individual is rendered to give subclasses the chance to prefix the rendering
     * with comments etc.
     *
     * @param ind The individual being rendered
     */
    protected abstract void writeIndividualComments(OWLNamedIndividual ind);

    /**
     * Render document.
     */
    public void render() {
        graph = new RDFGraph();
        beginDocument();
        renderOntologyHeader();
        renderOntologyComponents();
        endDocument();
    }

    private void renderOntologyComponents() {
        renderInOntologySignatureEntities(OWLDocumentFormat.determineIllegalPunnings(
            shouldInsertDeclarations(), ontology.signature(), ontology.getPunnedIRIs(INCLUDED)));
        renderAnonymousIndividuals();
        renderUntypedIRIAnnotationAssertions();
        renderGeneralAxioms();
        renderSWRLRules();
    }

    private void renderInOntologySignatureEntities(Collection<IRI> illegalPuns) {
        renderEntities(ontology.annotationPropertiesInSignature(),
            ANNOTATION_PROPERTIES_BANNER_TEXT, illegalPuns);
        renderEntities(ontology.datatypesInSignature(), DATATYPES_BANNER_TEXT, illegalPuns);
        renderEntities(ontology.objectPropertiesInSignature(), OBJECT_PROPERTIES_BANNER_TEXT,
            illegalPuns);
        renderEntities(ontology.dataPropertiesInSignature(), DATA_PROPERTIES_BANNER_TEXT,
            illegalPuns);
        renderEntities(ontology.classesInSignature(), CLASSES_BANNER_TEXT, illegalPuns);
        renderEntities(ontology.individualsInSignature(), INDIVIDUALS_BANNER_TEXT, illegalPuns);
    }

    /**
     * Renders a set of entities.
     *
     * @param entities The entities. Not null.
     * @param bannerText The banner text that will prefix the rendering of the entities if anything
     *        is rendered. Not null. May be empty, in which case no banner will be written.
     * @param illegalPuns illegal puns
     */
    private void renderEntities(Stream<? extends OWLEntity> entities, String bannerText,
        Collection<IRI> illegalPuns) {
        AtomicBoolean firstRendering = new AtomicBoolean(true);
        entities.sorted().filter(e -> createGraph(e, illegalPuns))
            .forEach(e -> render(e, firstRendering, bannerText));
    }

    private void render(OWLEntity entity, AtomicBoolean firstRendering, String bannerText) {
        if (config.shouldUseBanners() && firstRendering.getAndSet(false) && !bannerText.isEmpty()) {
            writeBanner(bannerText);
        }
        renderEntity(entity);
    }

    private void renderEntity(OWLEntity entity) {
        beginObject();
        writeEntityComment(entity);
        render(new RDFResourceIRI(verifyAbsolute(entity.getIRI())), true);
        renderAnonRoots();
        endObject();
    }

    /**
     * Calls the appropriate hook method to write the comments for an entity.
     *
     * @param entity The entity for which comments should be written.
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

    private void addIfUntyped(OWLAnnotationSubject o, Collection<IRI> set) {
        IRI iri = (IRI) o;
        if (punned.contains(iri) || !ontology.containsEntityInSignature(iri)) {
            set.add(iri);
        }
    }

    private void renderUntypedIRIAnnotationAssertions() {
        Collection<IRI> annotatedIRIs = new HashSet<>();
        ontology.axioms(ANNOTATION_ASSERTION).filter(ax -> ax.getSubject().isIRI())
            .forEach(ax -> addIfUntyped(ax.getSubject(), annotatedIRIs));
        if (!annotatedIRIs.isEmpty()) {
            writeBanner(ANNOTATED_IRIS_BANNER_TEXT);
            annotatedIRIs.stream().sorted().forEach(this::renderIRI);
        }
    }

    protected void renderIRI(IRI iri) {
        beginObject();
        createGraph(asList(ontology.annotationAssertionAxioms(iri)));
        render(new RDFResourceIRI(verifyAbsolute(iri)), true);
        renderAnonRoots();
        endObject();
    }

    private void renderAnonymousIndividuals() {
        ontology.referencedAnonymousIndividuals().forEach(this::renderAnon);
    }

    protected void renderAnon(OWLAnonymousIndividual anonInd) {
        List<OWLAxiom> axioms = new ArrayList<>();
        if (ontology.referencingAxioms(anonInd)
            .filter(ax -> !(ax instanceof OWLDifferentIndividualsAxiom))
            .noneMatch(ax -> shouldNotRender(anonInd, axioms, ax))) {
            createGraph(axioms);
            renderAnonRoots();
        }
    }

    protected boolean shouldNotRender(OWLAnonymousIndividual anonInd, List<OWLAxiom> axioms,
        OWLAxiom ax) {
        if (!AxiomSubjectProviderEx.getSubject(ax).equals(anonInd)) {
            return true;
        }
        axioms.add(ax);
        return false;
    }

    private void renderSWRLRules() {
        List<SWRLRule> ruleAxioms = asList(ontology.axioms(SWRL_RULE).sorted());
        if (!ruleAxioms.isEmpty()) {
            createGraph(ruleAxioms);
            writeBanner(RULES_BANNER_TEXT);
            SWRLVariableExtractor variableExtractor = new SWRLVariableExtractor();
            ruleAxioms.forEach(rule -> rule.accept(variableExtractor));
            variableExtractor.getVariables()
                .forEach(var -> render(new RDFResourceIRI(verifyAbsolute(var.getIRI())), true));
            renderAnonRoots();
        }
    }

    protected IRI verifyAbsolute(IRI iri) {
        return AbsoluteIRIHelper.verifyAbsolute(iri, format, ontology);
    }

    private void renderGeneralAxioms() {
        AtomicBoolean bannerWritten = new AtomicBoolean(false);
        getGeneralAxioms().forEach(ax -> renderGeneral(bannerWritten, ax));
    }

    protected void renderGeneral(AtomicBoolean bannerWritten, OWLAxiom axiom) {
        createGraph(axiom);
        Set<RDFResourceBlankNode> rootNodes = getRDFGraph().getRootAnonymousNodes();
        if (!rootNodes.isEmpty()) {
            if (!bannerWritten.getAndSet(true)) {
                writeBanner(GENERAL_AXIOMS_BANNER_TEXT);
            }
            beginObject();
            renderAnonRoots();
            endObject();
        }
    }

    protected RDFGraph getRDFGraph() {
        return verifyNotNull(graph, "rdfGraph not initialised yet");
    }

    /**
     * Gets the general axioms in the ontology. These are axioms such as DifferentIndividuals,
     * General Class axioms which do not describe or define a named class and so can't be written
     * out as a frame, nary disjoint classes, disjoint object properties, disjoint data properties
     * and HasKey axioms where the class expression is anonymous.
     *
     * @return A set of axioms that are general axioms (and can't be written out in a frame-based
     *         style).
     */
    private List<OWLAxiom> getGeneralAxioms() {
        List<OWLAxiom> generalAxioms = new ArrayList<>();
        add(generalAxioms, ontology.generalClassAxioms());
        add(generalAxioms, ontology.axioms(DIFFERENT_INDIVIDUALS));
        add(generalAxioms,
            ontology.axioms(DISJOINT_CLASSES).filter(ax -> ax.classExpressions().count() > 2));
        add(generalAxioms,
            ontology.axioms(DISJOINT_OBJECT_PROPERTIES).filter(ax -> ax.properties().count() > 2));
        add(generalAxioms,
            ontology.axioms(DISJOINT_DATA_PROPERTIES).filter(ax -> ax.properties().count() > 2));
        add(generalAxioms,
            ontology.axioms(HAS_KEY).filter(ax -> ax.getClassExpression().isAnonymous()));
        generalAxioms.sort(null);
        return generalAxioms;
    }

    protected void renderOntologyHeader() {
        createGraph(ontology);
        RDFResource node = getRDFGraph().getOntology();
        if (node != null) {
            render(node);
        }
    }

    protected void render(RDFResource node) {
        render(node, true);
        getRDFGraph().getSubjectsForObject(node).forEach(n -> render(n, true));
    }

    private boolean createGraph(OWLEntity entity, Collection<IRI> illegalPuns) {
        final List<OWLAxiom> axioms = new ArrayList<>();
        add(axioms, ontology.declarationAxioms(entity));
        entity.accept(new GraphVisitor(ontology, axioms));
        if (axioms.isEmpty() && shouldInsertDeclarations() && !illegalPuns.contains(entity.getIRI())
            && OWLDocumentFormat.isMissingType(entity, ontology)) {
            axioms.add(df.getOWLDeclarationAxiom(entity));
        }
        // Don't write out duplicates for punned annotations!
        if (!punned.contains(entity.getIRI())) {
            add(axioms, ontology.annotationAssertionAxioms(entity.getIRI(), EXCLUDED));
        }
        createGraph(axioms);
        return !axioms.isEmpty();
    }

    protected boolean shouldInsertDeclarations() {
        return format == null || verifyNotNull(format).isAddMissingTypes();
    }

    protected void createGraph(List<? extends OWLObject> objects) {
        objects.sort(null);
        RDFTranslator translator = new RDFTranslator(ontology.getOWLOntologyManager(), ontology,
            format, shouldInsertDeclarations(), occurrences, axiomOccurrences, nextBlankNodeId,
            blankNodeMap, translatedAxioms);
        objects.stream().map(this::deshare).forEach(translator::translate);
        graph = translator.getGraph();
        getRDFGraph().forceIdOutput();
    }

    protected void createGraph(OWLObject o) {
        createGraph(Collections.singletonList(o));
    }

    protected OWLObject deshare(OWLObject o) {
        if (o.hasSharedStructure()) {
            return o.accept(desharer);
        }
        return o;
    }

    protected abstract void writeBanner(String name);

    /**
     * Render anonymous roots.
     */
    public void renderAnonRoots() {
        getRDFGraph().getRootAnonymousNodes().stream().sorted().forEach(x -> render(x, true));
    }

    /**
     * Renders the triples in the current graph into a concrete format. Subclasses of this class
     * decide upon how the triples get rendered.
     *
     * @param node The main node to be rendered
     * @param root true if this is the root call to render, false otherwise
     */
    protected abstract void render(RDFResource node, boolean root);

    protected boolean isObjectList(RDFResource node) {
        for (RDFTriple triple : getRDFGraph().getTriplesForSubject(node)) {
            if (triple.getPredicate().getIRI().equals(RDF_TYPE.getIRI())
                && !triple.getObject().isAnonymous()
                && triple.getObject().getIRI().equals(RDF_LIST.getIRI())) {
                List<RDFNode> items = new ArrayList<>();
                toJavaList(node, items);
                return items.stream().noneMatch(RDFNode::isLiteral);
            }
        }
        return false;
    }

    protected void toJavaList(RDFNode n, List<RDFNode> list) {
        RDFNode currentNode = n;
        while (currentNode != null) {
            for (RDFTriple triple : getRDFGraph().getTriplesForSubject(currentNode)) {
                if (triple.getPredicate().getIRI().equals(RDF_FIRST.getIRI())) {
                    list.add(triple.getObject());
                }
            }
            for (RDFTriple triple : getRDFGraph().getTriplesForSubject(currentNode)) {
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
                        }
                    }
                }
            }
        }
    }

    protected RDFTriple remapNodesIfNecessary(final RDFResource node, final RDFTriple triple) {
        return triple;
    }

    static final class GraphVisitor implements OWLEntityVisitor {

        private final List<OWLAxiom> axioms;
        private OWLOntology ontology;

        GraphVisitor(OWLOntology ontology, List<OWLAxiom> axioms) {
            this.axioms = axioms;
            this.ontology = ontology;
        }

        static boolean inverse(OWLAxiom ax, OWLNamedIndividual i) {
            if (ax instanceof OWLObjectPropertyAssertionAxiom) {
                OWLObjectPropertyAssertionAxiom candidate = (OWLObjectPropertyAssertionAxiom) ax;
                if (candidate.getProperty().isAnonymous() && candidate.getObject().equals(i)) {
                    return true;
                }
            }
            return false;
        }

        static boolean inverseFirst(OWLAxiom ax, OWLNamedIndividual i) {
            if (ax instanceof OWLObjectPropertyAssertionAxiom) {
                OWLObjectPropertyAssertionAxiom candidate = (OWLObjectPropertyAssertionAxiom) ax;
                if (candidate.getProperty().isAnonymous() && candidate.getSubject().equals(i)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void visit(OWLClass cls) {
            add(axioms, ontology.axioms(cls).filter(GraphVisitor::threewayDisjoint)
                .filter(ax -> disjoint(ax, cls)).filter(ax -> equiv(ax, cls)));
            add(axioms, ontology.axioms(HAS_KEY).filter(ax -> ax.getClassExpression().equals(cls)));
        }

        static boolean equiv(OWLAxiom ax, OWLClass cls) {
            if (ax instanceof OWLEquivalentClassesAxiom) {
                OWLEquivalentClassesAxiom invAxiom = (OWLEquivalentClassesAxiom) ax;
                if (!invAxiom.getOperandsAsList().get(0).equals(cls)) {
                    return false;
                }
            }
            return true;
        }

        static boolean disjoint(OWLAxiom ax, OWLClass cls) {
            if (ax instanceof OWLDisjointClassesAxiom) {
                OWLDisjointClassesAxiom invAxiom = (OWLDisjointClassesAxiom) ax;
                if (!invAxiom.getOperandsAsList().get(0).equals(cls)) {
                    return false;
                }
            }
            return true;
        }

        static boolean disjoint(OWLAxiom ax, OWLDataProperty cls) {
            if (ax instanceof OWLDisjointDataPropertiesAxiom) {
                OWLDisjointDataPropertiesAxiom invAxiom = (OWLDisjointDataPropertiesAxiom) ax;
                if (!invAxiom.getOperandsAsList().get(0).equals(cls)) {
                    return false;
                }
            }
            return true;
        }

        static boolean disjoint(OWLAxiom ax, OWLObjectProperty cls) {
            if (ax instanceof OWLDisjointObjectPropertiesAxiom) {
                OWLDisjointObjectPropertiesAxiom invAxiom = (OWLDisjointObjectPropertiesAxiom) ax;
                if (!invAxiom.getOperandsAsList().get(0).equals(cls)) {
                    return false;
                }
            }
            return true;
        }

        static boolean equiv(OWLAxiom ax, OWLDataProperty cls) {
            if (ax instanceof OWLEquivalentDataPropertiesAxiom) {
                OWLEquivalentDataPropertiesAxiom invAxiom = (OWLEquivalentDataPropertiesAxiom) ax;
                if (!invAxiom.getOperandsAsList().get(0).equals(cls)) {
                    return false;
                }
            }
            return true;
        }

        static boolean equiv(OWLAxiom ax, OWLObjectProperty cls) {
            if (ax instanceof OWLEquivalentObjectPropertiesAxiom) {
                OWLEquivalentObjectPropertiesAxiom invAxiom =
                    (OWLEquivalentObjectPropertiesAxiom) ax;
                if (!invAxiom.getOperandsAsList().get(0).equals(cls)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void visit(OWLDatatype datatype) {
            add(axioms, ontology.datatypeDefinitions(datatype));
        }

        @Override
        public void visit(OWLNamedIndividual individual) {
            add(axioms,
                ontology.axioms(individual)
                    .filter(ax -> !(ax instanceof OWLDifferentIndividualsAxiom))
                    .filter(ax -> same(ax, individual)).filter(ax -> inverseFirst(ax, individual)));
            // for object property assertion axioms where the property is
            // anonymous and the individual is the object, the renderer will
            // save the simplified version of the axiom.
            // As they will have subject and object inverted, we need to
            // collect them here, otherwise the triple will not be included
            // because the subject will not match
            add(axioms,
                ontology.referencingAxioms(individual).filter(ax -> inverse(ax, individual)));
        }

        @Override
        public void visit(OWLDataProperty property) {
            add(axioms, ontology.axioms(property).filter(GraphVisitor::threewayDisjointData)
                .filter(ax -> equiv(ax, property)).filter(ax -> disjoint(ax, property)));
        }

        @Override
        public void visit(OWLObjectProperty property) {
            add(axioms,
                ontology.axioms(property).filter(GraphVisitor::threewayDisjointObject)
                    .filter(ax -> inverse(ax, property)).filter(ax -> equiv(ax, property))
                    .filter(ax -> disjoint(ax, property)));
            add(axioms, ontology.axioms(SUB_PROPERTY_CHAIN_OF)
                .filter(ax -> ax.getSuperProperty().equals(property)));
            OWLObjectInverseOf inverse = ontology.getOWLOntologyManager().getOWLDataFactory()
                .getOWLObjectInverseOf(property);
            add(axioms, ontology.axioms(inverse));
        }

        @Override
        public void visit(OWLAnnotationProperty property) {
            add(axioms, ontology.axioms(property));
        }

        static boolean inverse(OWLAxiom ax, OWLObjectProperty p) {
            if (ax instanceof OWLInverseObjectPropertiesAxiom) {
                OWLInverseObjectPropertiesAxiom invAxiom = (OWLInverseObjectPropertiesAxiom) ax;
                // inverse properties axioms where the first property is not the property being
                // rendered will be rendered when the first property is rendered
                if (!invAxiom.getFirstProperty().equals(p)) {
                    return false;
                }
            }
            return true;
        }

        static boolean same(OWLAxiom ax, OWLIndividual i) {
            if (ax instanceof OWLSameIndividualAxiom) {
                OWLSameIndividualAxiom invAxiom = (OWLSameIndividualAxiom) ax;
                // inverse properties axioms where the first property is not the property being
                // rendered will be rendered when the first property is rendered
                if (!invAxiom.getOperandsAsList().get(0).equals(i)) {
                    return false;
                }
            }
            return true;
        }

        static boolean threewayDisjoint(OWLAxiom ax) {
            if (ax instanceof OWLDisjointClassesAxiom) {
                OWLDisjointClassesAxiom disjAx = (OWLDisjointClassesAxiom) ax;
                if (disjAx.getOperandsAsList().size() > 2) {
                    return false;
                }
            }
            return true;
        }

        static boolean threewayDisjointData(OWLAxiom ax) {
            return !(ax instanceof OWLDisjointDataPropertiesAxiom
                && ((OWLDisjointDataPropertiesAxiom) ax).getOperandsAsList().size() > 2);
        }

        static boolean threewayDisjointObject(OWLAxiom ax) {
            return !(ax instanceof OWLDisjointObjectPropertiesAxiom
                && ((OWLDisjointObjectPropertiesAxiom) ax).getOperandsAsList().size() > 2);
        }
    }

    protected void defer(RDFNode object) {
        nodesToRenderSeparately.add((RDFResourceBlankNode) object);
    }

    protected void deferredRendering() {
        while (!nodesToRenderSeparately.isEmpty()) {
            RDFResourceBlankNode polled = nodesToRenderSeparately.poll();
            if (renderedNodes.add(polled)) {
                render(polled, false);
            }
        }
    }
}
