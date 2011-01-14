package org.coode.owlapi.rdf.renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.coode.owlapi.rdf.model.RDFGraph;
import org.coode.owlapi.rdf.model.RDFLiteralNode;
import org.coode.owlapi.rdf.model.RDFNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTranslator;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.AxiomSubjectProvider;
import org.semanticweb.owlapi.util.SWRLVariableExtractor;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Jan-2008<br><br>
 */
public abstract class RDFRendererBase {

    protected OWLOntologyManager manager;

    protected OWLOntology ontology;

    private RDFGraph graph;

    protected Set<IRI> prettyPrintedTypes;

    private OWLOntologyFormat format;


    public RDFRendererBase(OWLOntology ontology, OWLOntologyManager manager) {
        this(ontology, manager, manager.getOntologyFormat(ontology));
    }


    protected RDFRendererBase(OWLOntology ontology, OWLOntologyManager manager, OWLOntologyFormat format) {
        this.ontology = ontology;
        this.manager = manager;
        this.format = format;
    }


    public RDFGraph getGraph() {
        return graph;
    }


    public OWLOntology getOntology() {
        return ontology;
    }


    protected abstract void beginDocument() throws IOException;


    public void render() throws IOException {
        beginDocument();

        // Put imports at the top of the rendering

        renderOntologyHeader();

        // Annotation properties

        boolean first;
        List<OWLAnnotationProperty> annotationProperties = new ArrayList<OWLAnnotationProperty>(ontology.getAnnotationPropertiesInSignature());
        if (!annotationProperties.isEmpty()) {
            writeBanner("Annotation properties");
            for (OWLAnnotationProperty prop : annotationProperties) {
                if(createGraph(prop)) {
                    beginObject();
                    render(new RDFResourceNode(prop.getIRI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }

        Set<OWLDatatype> datatypes = ontology.getDatatypesInSignature();
        for (OWLDatatype datatype : new ArrayList<OWLDatatype>(datatypes)) {
            if (datatype.isBuiltIn()) {
                datatypes.remove(datatype);
            }
        }

        if (!datatypes.isEmpty()) {
            writeBanner("Datatypes");
            for (OWLDatatype datatype : toSortedSet(datatypes)) {
                if (createGraph(datatype)) {
                    beginObject();
                    writeDatatypeComment(datatype);
                    render(new RDFResourceNode(datatype.getIRI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }

        Set<OWLObjectProperty> objectProperties = ontology.getObjectPropertiesInSignature();
        if (!objectProperties.isEmpty()) {
            first = true;
            for (OWLObjectProperty prop : toSortedSet(objectProperties)) {
                if (createGraph(prop)) {
                    if (first) {
                        writeBanner("Object Properties");
                        first = false;
                    }
                    beginObject();
                    writeObjectPropertyComment(prop);
                    render(new RDFResourceNode(prop.getIRI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }

        Set<OWLDataProperty> dataProperties = ontology.getDataPropertiesInSignature();
        if (!dataProperties.isEmpty()) {
            first = true;
            for (OWLDataProperty prop : toSortedSet(ontology.getDataPropertiesInSignature())) {
                if (createGraph(prop)) {
                    if (first) {
                        first = false;
                        writeBanner("Data properties");
                    }
                    beginObject();
                    writeDataPropertyComment(prop);
                    render(new RDFResourceNode(prop.getIRI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }


        Set<OWLClass> clses = ontology.getClassesInSignature();
        if (!clses.isEmpty()) {
            first = true;
            for (OWLClass cls : toSortedSet(clses)) {
                if (createGraph(cls)) {
                    if (first) {
                        first = false;
                        writeBanner("Classes");
                    }
                    beginObject();
                    writeClassComment(cls);
                    render(new RDFResourceNode(cls.getIRI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }


        Set<OWLNamedIndividual> individuals = ontology.getIndividualsInSignature();
        if (!individuals.isEmpty()) {
            first = true;
            for (OWLNamedIndividual ind : toSortedSet(ontology.getIndividualsInSignature())) {
                if (createGraph(ind)) {
                    if (first) {
                        writeBanner("Individuals");
                        first = false;
                    }
                    beginObject();
                    writeIndividualComments(ind);
                    render(new RDFResourceNode(ind.getIRI()));
                    renderAnonRoots();
                    endObject();
                }
            }
        }

        for(OWLAnonymousIndividual anonInd : ontology.getReferencedAnonymousIndividuals()) {
            boolean anonRoot = true;
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            for(OWLAxiom ax : ontology.getReferencingAxioms(anonInd)) {
                if (!(ax instanceof OWLDifferentIndividualsAxiom)) {
                    AxiomSubjectProvider subjectProvider = new AxiomSubjectProvider();
                    OWLObject obj = subjectProvider.getSubject(ax);
                    if(!obj.equals(anonInd)) {
                        anonRoot = false;
                        break;
                    }
                    else {
                        axioms.add(ax);
                    }
                }
            }
            if(anonRoot) {
            	//TODO check this: in some cases it seems to cause a StackOverflow error.
                createGraph(axioms);
                renderAnonRoots();
            }
        }

        Set<IRI> annotatedIRIs = new HashSet<IRI>();
        for (OWLAnnotationAssertionAxiom ax : ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationSubject subject = ax.getSubject();
            if (subject instanceof IRI) {
                IRI iri = (IRI) subject;
                if (!ontology.containsEntityInSignature(iri)) {
                    annotatedIRIs.add(iri);
                }
            }
        }
        if (!annotatedIRIs.isEmpty()) {
            writeBanner("Annotations");
            for (IRI iri : annotatedIRIs) {
                beginObject();
                createGraph(ontology.getAnnotationAssertionAxioms(iri));
                render(new RDFResourceNode(iri));
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
        for (OWLHasKeyAxiom ax : ontology.getAxioms(AxiomType.HAS_KEY)) {
            if (ax.getClassExpression().isAnonymous()) {
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

        Set<SWRLRule> ruleAxioms = ontology.getAxioms(AxiomType.SWRL_RULE);
        createGraph(ruleAxioms);
        if (!ruleAxioms.isEmpty()) {
            writeBanner("Rules");
            SWRLVariableExtractor variableExtractor = new SWRLVariableExtractor();
            for (SWRLRule rule : ruleAxioms) {
                rule.accept(variableExtractor);
            }
            for (SWRLVariable var : variableExtractor.getVariables()) {
                render(new RDFResourceNode(var.getIRI()));
            }

            renderAnonRoots();
        }

        endDocument();
    }

    private void renderOntologyHeader() throws IOException {
        graph = new RDFGraph();
        OWLOntologyID ontID = ontology.getOntologyID();
        RDFResourceNode ontologyNode = null;
        int count = 0;
        if (ontID.getOntologyIRI() != null) {
            ontologyNode = new RDFResourceNode(ontID.getOntologyIRI());
            count++;
            if (ontID.getVersionIRI() != null) {
                graph.addTriple(new RDFTriple(ontologyNode, new RDFResourceNode(OWLRDFVocabulary.OWL_VERSION_IRI.getIRI()), new RDFResourceNode(ontID.getVersionIRI())));
                count++;
            }
        }
        else {
            ontologyNode = new RDFResourceNode(System.identityHashCode(ontology));
        }
        graph.addTriple(new RDFTriple(ontologyNode, new RDFResourceNode(OWLRDFVocabulary.RDF_TYPE.getIRI()), new RDFResourceNode(OWLRDFVocabulary.OWL_ONTOLOGY.getIRI())));
        for (OWLImportsDeclaration decl : ontology.getImportsDeclarations()) {
            graph.addTriple(new RDFTriple(ontologyNode, new RDFResourceNode(OWLRDFVocabulary.OWL_IMPORTS.getIRI()), new RDFResourceNode(decl.getIRI())));
            count++;
        }
        for (OWLAnnotation anno : ontology.getAnnotations()) {
            OWLAnnotationValueVisitorEx<RDFNode> valVisitor = new OWLAnnotationValueVisitorEx<RDFNode>() {
                public RDFNode visit(IRI iri) {
                    return new RDFResourceNode(iri);
                }

                public RDFNode visit(OWLAnonymousIndividual individual) {
                    return new RDFResourceNode(System.identityHashCode(individual));
                }

                public RDFNode visit(OWLLiteral literal) {
                	return RDFTranslator.translateLiteralNode(literal);
                }
            };
            RDFNode node = anno.getValue().accept(valVisitor);
            graph.addTriple(new RDFTriple(ontologyNode, new RDFResourceNode(anno.getProperty().getIRI()), node));
            count++;
        }
        if (count > 0) {
            render(ontologyNode);
        }
    }


//    private OWLOntologyFormat getOntologyFormat() {
//        return format;
//    }


    protected abstract void endDocument() throws IOException;


    protected abstract void writeIndividualComments(OWLNamedIndividual ind) throws IOException;


    protected abstract void writeClassComment(OWLClass cls) throws IOException;


    protected abstract void writeDataPropertyComment(OWLDataProperty prop) throws IOException;


    protected abstract void writeObjectPropertyComment(OWLObjectProperty prop) throws IOException;


    protected abstract void writeDatatypeComment(OWLDatatype datatype) throws IOException;


    protected abstract void writeAnnotationPropertyComment(OWLAnnotationProperty prop) throws IOException;


    protected void beginObject() throws IOException {

    }


    protected void endObject() throws IOException {

    }


//    private Set<OWLAnnotation> getAnnotationsForURIViaHack(Map<URI, Set<OWLAnnotation>> annoURIAnnotations, URI uri) {
//        Set<OWLAnnotation> annos = annoURIAnnotations.get(uri);
//        if (annos != null) {
//            return annos;
//        }
//        else {
//            return Collections.emptySet();
//        }
//    }


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
                for (OWLHasKeyAxiom ax : ontology.getAxioms(AxiomType.HAS_KEY)) {
                    if (ax.getClassExpression().equals(cls)) {
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
                for (OWLSubPropertyChainOfAxiom ax : ontology.getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    if (ax.getSuperProperty().equals(property)) {
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

    protected boolean shouldInsertDeclarations() {
        return !(format instanceof RDFOntologyFormat) || ((RDFOntologyFormat) format).isAddMissingTypes();
    }

    protected void createGraph(Set<? extends OWLObject> objects) {
        RDFTranslator translator = new RDFTranslator(manager, ontology, shouldInsertDeclarations());
        for (OWLObject obj : objects) {
            obj.accept(translator);
        }
        graph = translator.getGraph();
    }

    protected abstract void writeBanner(String name) throws IOException;


    private static <N extends OWLEntity> Set<N> toSortedSet(Set<N> entities) {
        Set<N> results = new TreeSet<N>(new OWLEntityIRIComparator());
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
            if (triple.getProperty().getIRI().equals(OWLRDFVocabulary.RDF_TYPE.getIRI())) {
                if (!triple.getObject().isAnonymous()) {
                    if (triple.getObject().getIRI().equals(OWLRDFVocabulary.RDF_LIST.getIRI())) {
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


    protected void toJavaList(RDFNode n, List<RDFNode> list) {
        RDFNode currentNode = n;
        while(currentNode != null) {
            for (RDFTriple triple : graph.getTriplesForSubject(currentNode)) {
                if (triple.getProperty().getIRI().equals(OWLRDFVocabulary.RDF_FIRST.getIRI())) {
                    list.add(triple.getObject());
                }
            }
            for (RDFTriple triple : graph.getTriplesForSubject(currentNode)) {
                if (triple.getProperty().getIRI().equals(OWLRDFVocabulary.RDF_REST.getIRI())) {
                    if (!triple.getObject().isAnonymous()) {
                        if (triple.getObject().getIRI().equals(OWLRDFVocabulary.RDF_NIL.getIRI())) {
                            // End of list
                            currentNode = null;
                        }
                    }
                    else {
                        // Should be another list
                        currentNode = triple.getObject();
//                        toJavaList(triple.getObject(), list);
                    }
                }
            }
        }

    }


    /**Comparator that uses IRI ordering to order entities.
     * XXX stateless, might be used through a singleton*/
    private static final class OWLEntityIRIComparator implements
			Comparator<OWLEntity> {
		public int compare(OWLEntity o1, OWLEntity o2) {
		    return o1.getIRI().compareTo(o2.getIRI());
		}
	}


	public static class TripleComparator implements Comparator<RDFTriple> {

        private List<IRI> orderedURIs;


        public TripleComparator() {
            orderedURIs = new ArrayList<IRI>();
            orderedURIs.add(OWLRDFVocabulary.RDF_TYPE.getIRI());
            orderedURIs.add(OWLRDFVocabulary.RDFS_LABEL.getIRI());
            orderedURIs.add(OWLRDFVocabulary.OWL_EQUIVALENT_CLASS.getIRI());
            orderedURIs.add(OWLRDFVocabulary.RDFS_SUBCLASS_OF.getIRI());
            orderedURIs.add(OWLRDFVocabulary.OWL_DISJOINT_WITH.getIRI());

            orderedURIs.add(OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI());
            orderedURIs.add(OWLRDFVocabulary.OWL_DATA_RANGE.getIRI());
            orderedURIs.add(OWLRDFVocabulary.OWL_ON_CLASS.getIRI());
        }


        private int getIndex(IRI iri) {
            int index = orderedURIs.indexOf(iri);
            if (index == -1) {
                index = orderedURIs.size();
            }
            return index;
        }


        public int compare(RDFTriple o1, RDFTriple o2) {
            int diff = getIndex(o1.getProperty().getIRI()) - getIndex(o2.getProperty().getIRI());
            if (diff == 0) {
                // Compare by subject, then predicate, then object

                if (!o1.getSubject().isAnonymous()) {
                    if (!o2.getSubject().isAnonymous()) {
                        diff = o1.getSubject().getIRI().compareTo(o2.getSubject().getIRI());
                    }
                    else {
                        diff = -1;
                    }
                }
                else {
                    if (!o2.getSubject().isAnonymous()) {
                        diff = 1;
                    }
                    else {
                        diff = 0;
                    }
                }

                if (diff == 0) {
                    diff = o2.getProperty().getIRI().compareTo(o2.getProperty().getIRI());
                    if (diff == 0) {
                        if (!o1.getObject().isLiteral()) {
                            // Resource
                            if (!o2.getObject().isLiteral()) {
                                // Resource
                                if (!o1.getObject().isAnonymous()) {
                                    if (!o2.getObject().isAnonymous()) {
                                        diff = o1.getObject().getIRI().compareTo(o2.getObject().getIRI());
                                    }
                                    else {
                                        diff = -1;
                                    }
                                }
                                else {
                                    if (!o2.getObject().isAnonymous()) {
                                        diff = 1;
                                    }
                                    else {
                                        diff = -1;
                                    }
                                }
                            }
                            else {
                                // Literal
                                // Literals first?
                                diff = 1;
                            }
                        }
                        else {
                            // Literal
                            if (!o2.getObject().isLiteral()) {
                                // Resource
                                diff = -1;
                            }
                            else {
                                // Literal
                                RDFLiteralNode lit1 = ((RDFLiteralNode) o1.getObject());
                                RDFLiteralNode lit2 = ((RDFLiteralNode) o2.getObject());
                                if (lit1.isTyped()) {
                                    if (lit2.isTyped()) {
                                        diff = lit1.getLiteral().compareTo(lit2.getLiteral());
                                        if (diff == 0) {
                                            diff = lit1.getDatatype().compareTo(lit2.getDatatype());
                                        }
                                    }
                                    else {
                                        diff = -1;
                                    }
                                }
                                else {
                                    if (lit2.isTyped()) {
                                        diff = 1;
                                    }
                                    else {
                                        if (lit1.getLang() != null) {
                                            if (lit2.getLang() != null) {
                                                diff = lit1.getLang().compareTo(lit2.getLang());
                                            }
                                        }
                                        else {
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
