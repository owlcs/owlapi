package org.coode.owlapi.obo.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public class OBOConsumer implements OBOParserHandler {

    private static final Logger logger = Logger.getLogger(OBOConsumer.class.getName());

    private OWLOntologyLoaderConfiguration configuration;

    private OWLOntologyManager owlOntologyManager;

    private OWLOntology ontology;

    private boolean inHeader;

    private String currentId;

    private Map<String, TagValueHandler> handlerMap;

    private String defaultNamespace;

    private String currentNamespace;

    private String stanzaType;

    private boolean termType;

    private boolean typedefType;

    private boolean instanceType;

    private Set<OWLClassExpression> intersectionOfOperands;

    private Set<OWLClassExpression> unionOfOperands;

    private Map<String, IRI> uriCache;


    public OBOConsumer(OWLOntologyManager owlOntologyManager, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) {
        this.configuration = configuration;
        this.owlOntologyManager = owlOntologyManager;
        this.ontology = ontology;
        defaultNamespace = OBOVocabulary.ONTOLOGY_URI_BASE;
        intersectionOfOperands = new HashSet<OWLClassExpression>();
        unionOfOperands = new HashSet<OWLClassExpression>();
        uriCache = new HashMap<String, IRI>();
        loadBuiltinURIs();
        setupTagHandlers();
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }


    public OWLOntology getOntology() {
        return ontology;
    }


    public String getCurrentId() {
        return currentId;
    }


    public String getDefaultNamespace() {
        return defaultNamespace;
    }


    public void setDefaultNamespace(String defaultNamespace) {
        this.defaultNamespace = defaultNamespace;
    }


    public String getCurrentNamespace() {
        return currentNamespace;
    }


    public void setCurrentNamespace(String currentNamespace) {
        this.currentNamespace = currentNamespace;
    }


    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }


    public void addUnionOfOperand(OWLClassExpression classExpression) {
        unionOfOperands.add(classExpression);
    }


    public void addIntersectionOfOperand(OWLClassExpression classExpression) {
        intersectionOfOperands.add(classExpression);
    }


    public String getStanzaType() {
        return stanzaType;
    }


    public boolean isTerm() {
        return termType;
    }


    public boolean isTypedef() {
        return typedefType;
    }


    public boolean isInstanceType() {
        return instanceType;
    }

    private void loadBuiltinURIs() {
        for (OBOVocabulary v : OBOVocabulary.values()) {
            uriCache.put(v.getName(), v.getIRI());
        }
    }


    private void setupTagHandlers() {
        handlerMap = new HashMap<String, TagValueHandler>();
        addTagHandler(new IDTagValueHandler(this));
        addTagHandler(new NameTagValueHandler(this));
        addTagHandler(new IsATagValueHandler(this));
        addTagHandler(new PartOfTagValueHandler(this));
        addTagHandler(new TransitiveTagValueHandler(this));
        addTagHandler(new SymmetricTagValueHandler(this));
        addTagHandler(new RelationshipTagValueHandler(this));
        addTagHandler(new UnionOfHandler(this));
        addTagHandler(new IntersectionOfHandler(this));
        addTagHandler(new DisjointFromHandler(this));
        addTagHandler(new AsymmetricHandler(this));
        addTagHandler(new InverseHandler(this));
        addTagHandler(new ReflexiveHandler(this));
        addTagHandler(new TransitiveOverHandler(this));
        addTagHandler(new DefaultNamespaceTagValueHandler(this));
    }


    private void addTagHandler(TagValueHandler handler) {
        handlerMap.put(handler.getTag(), handler);
    }


    public void startHeader() {
        inHeader = true;
    }


    public void endHeader() {
        inHeader = false;
    }


    public void startStanza(String name) {
        currentId = null;
        currentNamespace = null;
        stanzaType = name;
        termType = stanzaType.equals(OBOVocabulary.TERM.getName());
        typedefType = false;
        instanceType = false;
        if (!termType) {
            typedefType = stanzaType.equals(OBOVocabulary.TYPEDEF.getName());
            if (!typedefType) {
                instanceType = stanzaType.equals(OBOVocabulary.INSTANCE.getName());
            }
        }
    }


    public void endStanza() {
        if (!unionOfOperands.isEmpty()) {
            createUnionEquivalentClass();
            unionOfOperands.clear();
        }

        if (!intersectionOfOperands.isEmpty()) {
            createIntersectionEquivalentClass();
            intersectionOfOperands.clear();
        }
    }


    private void createUnionEquivalentClass() {
        OWLClassExpression equivalentClass;
        if (unionOfOperands.size() == 1) {
            equivalentClass = unionOfOperands.iterator().next();
        }
        else {
            equivalentClass = getDataFactory().getOWLObjectUnionOf(unionOfOperands);
        }
        createEquivalentClass(equivalentClass);
    }


    private void createIntersectionEquivalentClass() {
        OWLClassExpression equivalentClass;
        if (intersectionOfOperands.size() == 1) {
            equivalentClass = intersectionOfOperands.iterator().next();
        }
        else {
            equivalentClass = getDataFactory().getOWLObjectIntersectionOf(intersectionOfOperands);
        }
        createEquivalentClass(equivalentClass);
    }


    private void createEquivalentClass(OWLClassExpression classExpression) {
        OWLAxiom ax = getDataFactory().getOWLEquivalentClassesAxiom(CollectionFactory.createSet(getCurrentClass(), classExpression));
        getOWLOntologyManager().applyChange(new AddAxiom(ontology, ax));
    }


    public void handleTagValue(String tag, String value) {
        try {
            TagValueHandler handler = handlerMap.get(tag);
            if (handler != null) {
                handler.handle(currentId, value);
            }
            else if (inHeader) {
                if (tag.equals("import")) {
                    IRI uri = IRI.create(value.trim());
                    OWLImportsDeclaration decl = owlOntologyManager.getOWLDataFactory().getOWLImportsDeclaration(uri);
                    owlOntologyManager.makeLoadImportRequest(decl, configuration);
                    owlOntologyManager.applyChange(new AddImport(ontology, decl));
                }
                else {
                    // Ontology annotations
                    OWLLiteral con = getDataFactory().getOWLLiteral(value);
                    OWLAnnotationProperty property = getDataFactory().getOWLAnnotationProperty(getIRI(tag));
                    OWLAnnotation anno = getDataFactory().getOWLAnnotation(property, con);
                    owlOntologyManager.applyChange(new AddOntologyAnnotation(ontology, anno));
                }
            }
            else if (currentId != null) {
                // Add as annotation
                if (configuration.isLoadAnnotationAxioms()) {
                    IRI subject = getIRI(currentId);
                    OWLLiteral con = getDataFactory().getOWLLiteral(value, "");
                    OWLAnnotationProperty property = getDataFactory().getOWLAnnotationProperty(getIRI(tag));
                    OWLAnnotation anno = getDataFactory().getOWLAnnotation(property, con);
                    OWLAnnotationAssertionAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(subject, anno);
                    owlOntologyManager.applyChange(new AddAxiom(ontology, ax));
                }
            }

        }
        catch (UnloadableImportException e) {
            logger.severe(e.getMessage());
        }
    }


    private OWLDataFactory getDataFactory() {
        return getOWLOntologyManager().getOWLDataFactory();
    }


    public OWLClass getCurrentClass() {
        return getDataFactory().getOWLClass(getIRI(currentId));
    }

    public OWLEntity getCurrentEntity() {
        if (isTerm()) {
            return getCurrentClass();
        }
        else if (isTypedef()) {
            return getDataFactory().getOWLObjectProperty(getIRI(currentId));
        }
        else {
            return getDataFactory().getOWLNamedIndividual(getIRI(currentId));
        }
    }


    public IRI getIRI(String s) {
        if (s == null) {
            for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
                System.out.println(e);
            }
        }
        IRI iri = uriCache.get(s);
        if (iri != null) {
            return iri;
        }
        String localName = s;
        String namespace = getDefaultNamespace();
//        int sepIndex = s.indexOf(':');
//        if (sepIndex != -1) {
        localName = s.replace(':', '_');
//            localName = s.substring(sepIndex + 1, localName.length());
//        }
        if (currentNamespace != null) {
            namespace = currentNamespace;
        }
        localName = localName.replace(' ', '-');
        iri = IRI.create(namespace + localName);
        uriCache.put(s, iri);

        return iri;
    }
}
