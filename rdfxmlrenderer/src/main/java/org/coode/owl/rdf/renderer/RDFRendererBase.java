package org.coode.owl.rdf.renderer;

import org.coode.owl.rdf.model.*;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.SWRLVariableExtractor;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.util.*;
import java.io.IOException;
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
 * Date: 26-Jan-2008<br><br>
 */
public abstract class RDFRendererBase {

    public static final String RENDER_DECLARATION_AXIOMS_KEY = "RENDER_DECLARATION_AXIOMS_KEY";


    protected OWLOntologyManager manager;

    protected OWLOntology ontology;

    private RDFGraph graph;

    protected Set<URI> annotationURIs;

    protected Set<URI> prettyPrintedTypes;

    private boolean renderDeclarationAxioms;

    private OWLOntologyFormat format;


    public RDFRendererBase(OWLOntology ontology, OWLOntologyManager manager) {
        this(ontology, manager, manager.getOntologyFormat(ontology));
    }


    protected RDFRendererBase(OWLOntology ontology, OWLOntologyManager manager, OWLOntologyFormat format) {
        this.ontology = ontology;
        annotationURIs = ontology.getAnnotationURIs();
        this.manager = manager;
        this.format = format;
        renderDeclarationAxioms = (Boolean) format.getParameter(RENDER_DECLARATION_AXIOMS_KEY, false);
    }


    public RDFGraph getGraph() {
        return graph;
    }


    public OWLOntology getOntology() {
        return ontology;
    }


    protected abstract void beginDocument() throws IOException ;


    public void render() throws IOException {
        beginDocument();

        // Put imports at the top of the rendering

        renderOntologyHeader();

        // Annotation properties

        boolean first;

        Set<OWLAnnotationProperty> annotationProperties = new HashSet<OWLAnnotationProperty>(ontology.getReferencedAnnotationProperties());
        if(!annotationProperties.isEmpty()) {
            writeBanner("Annotation properties");
            for (OWLAnnotationProperty prop : annotationProperties) {
                createGraph(prop);
                render(new RDFResourceNode(prop.getURI()));
            }
        }

        Set<OWLDatatype> datatypes = ontology.getReferencedDatatypes();
        for(OWLDatatype datatype : new HashSet<OWLDatatype>(datatypes)) {
            if(datatype.isBuiltIn()) {
                datatypes.remove(datatype);
            }
        }
        
        if(!datatypes.isEmpty()) {
            writeBanner("Datatypes");
            for(OWLDatatype datatype : toSortedSet(datatypes)) {
                if(createGraph(datatype)) {
                    beginObject();
                    writeDatatypeComment(datatype);
                    render(new RDFResourceNode(datatype.getURI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }

        Set<OWLObjectProperty> objectProperties = ontology.getReferencedObjectProperties();
        if (!objectProperties.isEmpty()) {
            first = true;
            for (OWLObjectProperty prop : toSortedSet(objectProperties)) {
                if (createGraph(prop)) {
                    if(first) {
                        writeBanner("Object Properties");
                        first = false;
                    }
                    beginObject();
                    writeObjectPropertyComment(prop);
                    render(new RDFResourceNode(prop.getURI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }

        Set<OWLDataProperty> dataProperties = ontology.getReferencedDataProperties();
        if (!dataProperties.isEmpty()) {
            first = true;
            for (OWLDataProperty prop : toSortedSet(ontology.getReferencedDataProperties())) {
                if (createGraph(prop)) {
                    if(first) {
                        first = false;
                        writeBanner("Data properties");
                    }
                    beginObject();
                    writeDataPropertyComment(prop);
                    render(new RDFResourceNode(prop.getURI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }


        
        Set<OWLClass> clses = ontology.getReferencedClasses();
        if (!clses.isEmpty()) {
            first = true;
            for (OWLClass cls : toSortedSet(clses)) {
                if (createGraph(cls)) {
                    if(first) {
                        first = false;
                        writeBanner("Classes");
                    }
                    beginObject();
                    writeClassComment(cls);
                    render(new RDFResourceNode(cls.getURI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }


        Set<? extends OWLIndividual> individuals = ontology.getReferencedIndividuals();
        if (!individuals.isEmpty()) {
            first = true;
            for (OWLNamedIndividual ind : toSortedSet(ontology.getReferencedIndividuals())) {
                if (createGraph(ind)) {
                    if(first) {
                        writeBanner("Individuals");
                        first = false;
                    }
                    beginObject();
                    writeIndividualComments(ind);
                    if (!ind.isAnonymous()) {
                        render(new RDFResourceNode(ind.getURI()));
                        renderAnonRoots();
                    } else {
                        render(new RDFResourceNode(System.identityHashCode(ind)));
                    }

                    endObject();
                }
            }
        }

        Set<IRI> annotatedIRIs = new HashSet<IRI>();
        for(OWLAnnotationAssertionAxiom ax : ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationSubject subject = ax.getSubject();
            if(subject instanceof IRI) {
                IRI iri = (IRI) subject;
                if(!ontology.containsEntityReference(iri.toURI())) {
                    annotatedIRIs.add(iri);
                }
            }
        }
        if(!annotatedIRIs.isEmpty()) {
            writeBanner("Annotations");
            for (IRI iri : annotatedIRIs) {
                beginObject();
                createGraph(ontology.getAnnotationAssertionAxioms(iri));
                render(new RDFResourceNode(iri.toURI()));
                renderAnonRoots();
                endObject();
            }
        }


        Set<OWLAxiom> generalAxioms = new HashSet<OWLAxiom>();
        generalAxioms.addAll(ontology.getGeneralClassAxioms());
        generalAxioms.addAll(ontology.getAxioms(AxiomType.DIFFERENT_INDIVIDUALS));
        for (OWLDisjointClassesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                generalAxioms.add(ax);
            }
        }
        for (OWLDisjointObjectPropertiesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                generalAxioms.add(ax);
            }
        }

        for (OWLDisjointDataPropertiesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                generalAxioms.add(ax);
            }
        }
        for(OWLHasKeyAxiom ax : ontology.getAxioms(AxiomType.HAS_KEY)) {
            if(ax.getClassExpression().isAnonymous()) {
                generalAxioms.add(ax);
            }
        }
        createGraph(generalAxioms);

        Set<RDFResourceNode> rootNodes = graph.getRootAnonymousNodes();
        if (!rootNodes.isEmpty()) {
            writeBanner("General axioms");
            beginObject();
            renderAnonRoots();
            endObject();
        }

        Set<SWRLRule> ruleAxioms = ontology.getRules();
        createGraph(ruleAxioms);
        if (!ruleAxioms.isEmpty()) {
            writeBanner("Rules");
            SWRLVariableExtractor variableExtractor = new SWRLVariableExtractor();
            for (SWRLRule rule : ruleAxioms) {
                beginObject();
                if (!rule.isAnonymous()) {
                    render(new RDFResourceNode(rule.getURI()));
                }
                rule.accept(variableExtractor);
                endObject();
            }
            for (SWRLAtomVariable var : variableExtractor.getIVariables()) {
                render(new RDFResourceNode(var.getURI()));
            }

            for (SWRLAtomVariable var : variableExtractor.getDVariables()) {
                render(new RDFResourceNode(var.getURI()));
            }
            renderAnonRoots();
        }

        endDocument();
    }

    private void renderOntologyHeader() throws IOException {
        graph = new RDFGraph();
        OWLOntologyID ontID = ontology.getOntologyID();
        RDFResourceNode ontologyNode = null;
        if(ontID.getOntologyIRI() != null) {
            ontologyNode = new RDFResourceNode(ontID.getOntologyIRI().toURI());
            graph.addTriple(new RDFTriple(ontologyNode,
                            new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getURI()),
                            new RDFResourceNode(OWLRDFVocabulary.OWL_ONTOLOGY.getURI())));
            if(ontID.getVersionIRI() != null) {
                graph.addTriple(new RDFTriple(ontologyNode,
                                new RDFResourceNode(OWLRDFVocabulary.OWL_VERSION_IRI.getURI()),
                                new RDFResourceNode(ontID.getVersionIRI().toURI())));
            }
        }
        else {
            ontologyNode = new RDFResourceNode(System.identityHashCode(ontology));
        }
        for(OWLImportsDeclaration decl : ontology.getImportsDeclarations()) {
            graph.addTriple(new RDFTriple(ontologyNode,
                new RDFResourceNode(OWLRDFVocabulary.OWL_IMPORTS.getURI()),
                new RDFResourceNode(decl.getURI())));
        }
        for(OWLAnnotation anno : ontology.getAnnotations()) {
            OWLAnnotationValueVisitorEx<RDFNode> valVisitor = new OWLAnnotationValueVisitorEx<RDFNode>() {
                public RDFNode visit(IRI iri) {
                    return new RDFResourceNode(iri.toURI());
                }

                public RDFNode visit(OWLAnonymousIndividual individual) {
                    return new RDFResourceNode(System.identityHashCode(individual));
                }

                public RDFNode visit(OWLTypedLiteral literal) {
                    return new RDFLiteralNode(literal.getLiteral(), literal.asOWLStringLiteral().getDatatype().getURI());
                }

                public RDFNode visit(OWLStringLiteral literal) {
                    return new RDFLiteralNode(literal.getLiteral(), literal.asRDFTextLiteral().getLang());
                }
            };
            RDFNode node = anno.getValue().accept(valVisitor);


            graph.addTriple(new RDFTriple(
                    ontologyNode,
                    new RDFResourceNode(anno.getProperty().getURI()),
                    node));
        }
        render(ontologyNode);
    }


    private OWLOntologyFormat getOntologyFormat() {
        return format;
    }


    protected abstract void endDocument() throws IOException;


    protected abstract void writeIndividualComments(OWLNamedIndividual ind) throws IOException ;


    protected abstract void writeClassComment(OWLClass cls) throws IOException ;


    protected abstract void writeDataPropertyComment(OWLDataProperty prop) throws IOException ;


    protected abstract void writeObjectPropertyComment(OWLObjectProperty prop) throws IOException ;


    protected abstract void writeDatatypeComment(OWLDatatype datatype) throws IOException ;


    protected abstract void writeAnnotationPropertyComment(OWLAnnotationProperty prop) throws IOException ;


    protected void beginObject() throws IOException  {

    }


    protected void endObject() throws IOException  {

    }


    private Set<OWLAnnotation> getAnnotationsForURIViaHack(Map<URI, Set<OWLAnnotation>> annoURIAnnotations, URI uri) {
        Set<OWLAnnotation> annos = annoURIAnnotations.get(uri);
        if (annos != null) {
            return annos;
        } else {
            return Collections.emptySet();
        }
    }


    private boolean createGraph(OWLEntity entity) {
        final Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(entity.getAnnotationAssertionAxioms(ontology));
        axioms.addAll(ontology.getDeclarationAxioms(entity));

        entity.accept(new OWLEntityVisitor() {
            public void visit(OWLClass cls) {
                for (OWLAxiom ax : ontology.getAxioms(cls)) {
                    if (ax instanceof OWLDisjointClassesAxiom) {
                        OWLDisjointClassesAxiom disjAx = (OWLDisjointClassesAxiom) ax;
                        if (disjAx.getClassExpressions().size() > 2) {
                            continue;
                        }
                    }
                    axioms.add(ax);
                }
                for(OWLHasKeyAxiom ax : ontology.getAxioms(AxiomType.HAS_KEY)) {
                    if(ax.getClassExpression().equals(cls)) {
                        axioms.add(ax);
                    }
                }
                createGraph(axioms);
            }


            public void visit(OWLDatatype datatype) {
                axioms.addAll(ontology.getDatatypeDefinitions(datatype));
                createGraph(axioms);
            }


            public void visit(OWLNamedIndividual individual) {
                for (OWLAxiom ax : ontology.getAxioms(individual)) {
                    if (ax instanceof OWLDifferentIndividualsAxiom) {
                        continue;
                    }
                    axioms.add(ax);
                }

                createGraph(axioms);
            }


            public void visit(OWLDataProperty property) {
                for (OWLAxiom ax : ontology.getAxioms(property)) {
                    if (ax instanceof OWLDisjointDataPropertiesAxiom) {
                        if (((OWLDisjointDataPropertiesAxiom) ax).getProperties().size() > 2) {
                            continue;
                        }
                    }
                    axioms.add(ax);
                }
                createGraph(axioms);
            }


            public void visit(OWLObjectProperty property) {
                for (OWLAxiom ax : ontology.getAxioms(property)) {
                    if (ax instanceof OWLDisjointObjectPropertiesAxiom) {
                        if (((OWLDisjointObjectPropertiesAxiom) ax).getProperties().size() > 2) {
                            continue;
                        }
                    }
                    axioms.add(ax);
                }
                for(OWLSubPropertyChainOfAxiom ax : ontology.getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    if(ax.getSuperProperty().equals(property)) {
                        axioms.add(ax);
                    }
                }
                axioms.addAll(ontology.getAxioms(manager.getOWLDataFactory().getOWLObjectInverseOf(property)));
                createGraph(axioms);
            }

            public void visit(OWLAnnotationProperty property) {
                axioms.addAll(ontology.getAxioms(property));
                createGraph(axioms);
            }
        });
//        addTypeTriple(entity);
        return !axioms.isEmpty();
    }


    private void createGraph(Set<? extends OWLObject> objects) {
        RDFTranslator translator = new RDFTranslator(manager, ontology);
        for (OWLObject obj : objects) {
            obj.accept(translator);
        }
        graph = translator.getGraph();
    }


    private void addTypeTriple(OWLEntity entity) {
        graph = new RDFGraph();
        entity.accept(new OWLEntityVisitor() {
            public void visit(OWLClass cls) {
                graph.addTriple(new RDFTriple(new RDFResourceNode(cls.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.OWL_CLASS.getURI())));
            }


            public void visit(OWLDatatype datatype) {
                graph.addTriple(new RDFTriple(new RDFResourceNode(datatype.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.RDFS_DATATYPE.getURI())));
            }


            public void visit(OWLNamedIndividual individual) {
                graph.addTriple(new RDFTriple(new RDFResourceNode(individual.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL.getURI())));
            }


            public void visit(OWLDataProperty property) {
                graph.addTriple(new RDFTriple(new RDFResourceNode(property.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.OWL_DATA_PROPERTY.getURI())));
//                if (annotationURIs.contains(property.getIRI())) {
//                    graph.addTriple(new RDFTriple(new RDFResourceNode(property.getIRI()),
//                            new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getIRI()),
//                            new RDFResourceNode(OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getIRI())));
//                }
            }


            public void visit(OWLObjectProperty property) {
                graph.addTriple(new RDFTriple(new RDFResourceNode(property.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getURI())));
//                if (annotationURIs.contains(property.getIRI())) {
//                    graph.addTriple(new RDFTriple(new RDFResourceNode(property.getIRI()),
//                            new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getIRI()),
//                            new RDFResourceNode(OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getIRI())));
//                }
            }

            public void visit(OWLAnnotationProperty property) {
                graph.addTriple(new RDFTriple(new RDFResourceNode(property.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getURI()),
                        new RDFResourceNode(OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getURI())));
            }
        });
    }


    protected abstract void writeBanner(String name) throws IOException ;


    private static <N extends OWLEntity> Set<N> toSortedSet(Set<N> entities) {
        Set<N> results = new TreeSet<N>(new Comparator<OWLEntity>() {
            public int compare(OWLEntity o1, OWLEntity o2) {
                return o1.getURI().compareTo(o2.getURI());
            }
        });
        results.addAll(entities);
        return results;
    }


    public void renderAnonRoots() throws IOException {
        for (RDFResourceNode node : graph.getRootAnonymousNodes()) {
            render(node);
        }
    }


    public abstract void render(RDFResourceNode node) throws IOException;


    protected boolean isObjectList(RDFResourceNode node) {
        for (RDFTriple triple : graph.getTriplesForSubject(node)) {
            if (triple.getProperty().getURI().equals(OWLRDFVocabulary.RDF_TYPE.getURI())) {
                if (!triple.getObject().isAnonymous()) {
                    if (triple.getObject().getURI().equals(OWLRDFVocabulary.RDF_LIST.getURI())) {
                        List<RDFNode> items = new ArrayList<RDFNode>();
                        toJavaList(node, items);
                        for (RDFNode n : items) {
                            if (n.isLiteral()) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }


    protected void toJavaList(RDFNode node, List<RDFNode> list) {
        for (RDFTriple triple : graph.getTriplesForSubject(node)) {
            if (triple.getProperty().getURI().equals(OWLRDFVocabulary.RDF_FIRST.getURI())) {
                list.add(triple.getObject());
                break;
            }
        }
        for (RDFTriple triple : graph.getTriplesForSubject(node)) {
            if (triple.getProperty().getURI().equals(OWLRDFVocabulary.RDF_REST.getURI())) {
                if (!triple.getObject().isAnonymous()) {
                    if (triple.getObject().getURI().equals(OWLRDFVocabulary.RDF_NIL.getURI())) {
                        // End of list
                    }
                } else {
                    // Should be another list
                    toJavaList(triple.getObject(), list);
                }
            }
        }
    }


    public static class TripleComparator implements Comparator<RDFTriple> {

        private List<URI> orderedURIs;


        public TripleComparator() {
            orderedURIs = new ArrayList<URI>();
            orderedURIs.add(OWLRDFVocabulary.RDF_TYPE.getURI());
            orderedURIs.add(OWLRDFVocabulary.RDFS_LABEL.getURI());
            orderedURIs.add(OWLRDFVocabulary.OWL_EQUIVALENT_CLASS.getURI());
            orderedURIs.add(OWLRDFVocabulary.RDFS_SUBCLASS_OF.getURI());
            orderedURIs.add(OWLRDFVocabulary.OWL_DISJOINT_WITH.getURI());

            orderedURIs.add(OWLRDFVocabulary.OWL_ON_PROPERTY.getURI());
            orderedURIs.add(OWLRDFVocabulary.OWL_DATA_RANGE.getURI());
            orderedURIs.add(OWLRDFVocabulary.OWL_ON_CLASS.getURI());

            orderedURIs.add(OWLRDFVocabulary.RDF_SUBJECT.getURI());
            orderedURIs.add(OWLRDFVocabulary.RDF_PREDICATE.getURI());
            orderedURIs.add(OWLRDFVocabulary.RDF_OBJECT.getURI());

            orderedURIs.add(OWLRDFVocabulary.OWL_SUBJECT.getURI());
            orderedURIs.add(OWLRDFVocabulary.OWL_PREDICATE.getURI());
            orderedURIs.add(OWLRDFVocabulary.OWL_OBJECT.getURI());

        }


        private int getIndex(URI uri) {
            int index = orderedURIs.indexOf(uri);
            if (index == -1) {
                index = orderedURIs.size();
            }
            return index;
        }


        public int compare(RDFTriple o1, RDFTriple o2) {
            int diff = getIndex(o1.getProperty().getURI()) - getIndex(o2.getProperty().getURI());
            if (diff == 0) {
                // Compare by subject, then predicate, then object

                if (!o1.getSubject().isAnonymous()) {
                    if (!o2.getSubject().isAnonymous()) {
                        diff = o1.getSubject().getURI().compareTo(o2.getSubject().getURI());
                    } else {
                        diff = -1;
                    }
                } else {
                    if (!o2.getSubject().isAnonymous()) {
                        diff = 1;
                    } else {
                        diff = 0;
                    }
                }

                if (diff == 0) {
                    diff = o2.getProperty().getURI().compareTo(o2.getProperty().getURI());
                    if (diff == 0) {
                        if (!o1.getObject().isLiteral()) {
                            // Resource
                            if (!o2.getObject().isLiteral()) {
                                // Resource
                                if (!o1.getObject().isAnonymous()) {
                                    if (!o2.getObject().isAnonymous()) {
                                        diff = o1.getObject().getURI().compareTo(o2.getObject().getURI());
                                    } else {
                                        diff = -1;
                                    }
                                } else {
                                    if (!o2.getObject().isAnonymous()) {
                                        diff = 1;
                                    } else {
                                        diff = -1;
                                    }
                                }
                            } else {
                                // Literal
                                // Literals first?
                                diff = 1;
                            }
                        } else {
                            // Literal
                            if (!o2.getObject().isLiteral()) {
                                // Resource
                                diff = -1;
                            } else {
                                // Literal
                                RDFLiteralNode lit1 = ((RDFLiteralNode) o1.getObject());
                                RDFLiteralNode lit2 = ((RDFLiteralNode) o2.getObject());
                                if (lit1.isTyped()) {
                                    if (lit2.isTyped()) {
                                        diff = lit1.getLiteral().compareTo(lit2.getLiteral());
                                        if (diff == 0) {
                                            diff = lit1.getDatatype().compareTo(lit2.getDatatype());
                                        }
                                    } else {
                                        diff = -1;
                                    }
                                } else {
                                    if (lit2.isTyped()) {
                                        diff = 1;
                                    } else {
                                        if (lit1.getLang() != null) {
                                            if (lit2.getLang() != null) {
                                                diff = lit1.getLang().compareTo(lit2.getLang());
                                            }
                                        } else {
                                            diff = -1;
                                        }
                                        if (diff == 0) {
                                            diff = lit1.getLiteral().compareTo(lit2.getLiteral());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (diff == 0) {
                diff = 1;
            }
            return diff;
        }
    }
}
