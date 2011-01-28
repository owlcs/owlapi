package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import static org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax.*;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLAxiomFilter;
import org.semanticweb.owlapi.util.OntologyIRIShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Apr-2007<br><br>
 */
public class ManchesterOWLSyntaxFrameRenderer extends ManchesterOWLSyntaxObjectRenderer implements OWLEntityVisitor {

    //private OWLOntology defaultOntology;

    private Set<OWLOntology> ontologies;

    private OntologyIRIShortFormProvider shortFormProvider = new OntologyIRIShortFormProvider();

    private Set<AxiomType<?>> filteredAxiomTypes = new HashSet<AxiomType<?>>();

    private boolean renderExtensions = false;

    private List<RendererListener> listeners = new ArrayList<RendererListener>();

    private OWLAxiomFilter axiomFilter = new OWLAxiomFilter() {
    	@SuppressWarnings("unused")
        public boolean passes(OWLAxiom axiom) {
            return true;
        }
    };

    private RenderingDirector renderingDirector = new DefaultRenderingDirector();

    public ManchesterOWLSyntaxFrameRenderer(OWLOntologyManager owlOntologyManager, OWLOntology ontology, Writer writer, ShortFormProvider entityShortFormProvider) {
        this(owlOntologyManager, Collections.singleton(ontology), ontology, writer, entityShortFormProvider);
    }
    @SuppressWarnings("unused")
    public ManchesterOWLSyntaxFrameRenderer(OWLOntologyManager owlOntologyManager, Set<OWLOntology> ontologies, OWLOntology defaultOntology, Writer writer, ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
        this.ontologies = new LinkedHashSet<OWLOntology>(ontologies);
        //this.defaultOntology = defaultOntology;

    }

    public void setRenderingDirector(RenderingDirector renderingDirector) {
        this.renderingDirector = renderingDirector;
    }

    public void addRendererListener(RendererListener listener) {
        listeners.add(listener);
    }

    public void removeRendererListener(RendererListener listener) {
        listeners.remove(listener);
    }


    public void setAxiomFilter(OWLAxiomFilter axiomFilter) {
        this.axiomFilter = axiomFilter;
    }

    public void clearFilteredAxiomTypes() {
        filteredAxiomTypes.clear();
    }

    public void addFilteredAxiomType(AxiomType<?> axiomType) {
        filteredAxiomTypes.add(axiomType);
    }

    public void setRenderExtensions(boolean renderExtensions) {
        this.renderExtensions = renderExtensions;
    }

    public Set<OWLOntology> getOntologies() {
        return ontologies;
    }

    public void writeOntology() throws OWLRendererException {
        if (ontologies.size() != 1) {
            throw new RuntimeException("Can only render one ontology");
        }
        OWLOntology ontology = getOntologies().iterator().next();
        writePrefixMap();
        writeNewLine();
        writeOntologyHeader(ontology);

        for (OWLAnnotationProperty prop : ontology.getAnnotationPropertiesInSignature()) {
            write(prop);
        }
        for (OWLDatatype datatype : ontology.getDatatypesInSignature()) {
            write(datatype);
        }
        for (OWLObjectProperty prop : ontology.getObjectPropertiesInSignature()) {
            write(prop);
            OWLObjectPropertyExpression invProp = prop.getInverseProperty();
            if (!ontology.getAxioms(invProp).isEmpty()) {
                write(invProp);
            }
        }
        for (OWLDataProperty prop : ontology.getDataPropertiesInSignature()) {
            write(prop);
        }
        for (OWLClass cls : ontology.getClassesInSignature()) {
            write(cls);
        }
        for (OWLNamedIndividual ind : ontology.getIndividualsInSignature()) {
            write(ind);
        }
        for (OWLAnonymousIndividual ind : ontology.getReferencedAnonymousIndividuals()) {
            write(ind);
        }
        // Nary disjoint classes axioms
        event = new RendererEvent(this, ontology);
        for (OWLDisjointClassesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                SectionMap map = new SectionMap();
                map.add(ax.getClassExpressions(), ax);
                writeSection(DISJOINT_CLASSES, map, ",", false, ontology);
            }
        }
        // Nary equivalent classes axioms
        for (OWLEquivalentClassesAxiom ax : ontology.getAxioms(AxiomType.EQUIVALENT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                SectionMap map = new SectionMap();
                map.add(ax.getClassExpressions(), ax);
                writeSection(EQUIVALENT_CLASSES, map, ",", false, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointObjectPropertiesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                SectionMap map = new SectionMap();
                map.add(ax.getProperties(), ax);
                writeSection(DISJOINT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary equivlant properties
        for (OWLEquivalentObjectPropertiesAxiom ax : ontology.getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                SectionMap map = new SectionMap();
                map.add(ax.getProperties(), ax);
                writeSection(EQUIVALENT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointDataPropertiesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                SectionMap map = new SectionMap();
                map.add(ax.getProperties(), ax);
                writeSection(DISJOINT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary equivalent properties
        for (OWLEquivalentDataPropertiesAxiom ax : ontology.getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                SectionMap map = new SectionMap();
                map.add(ax.getProperties(), ax);
                writeSection(EQUIVALENT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary different individuals
        for (OWLDifferentIndividualsAxiom ax : ontology.getAxioms(AxiomType.DIFFERENT_INDIVIDUALS)) {
            if (ax.getIndividuals().size() > 2) {
                SectionMap map = new SectionMap();
                map.add(ax.getIndividuals(), ax);
                writeSection(DIFFERENT_INDIVIDUALS, map, ",", false, ontology);
            }
        }
        for (SWRLRule rule : ontology.getAxioms(AxiomType.SWRL_RULE)) {
            writeSection(RULE, Collections.singleton(rule), ", ", false);
        }
        flush();
    }


    public void writeOntologyHeader(OWLOntology ontology) {
        event = new RendererEvent(this, ontology);
        fireFrameRenderingPrepared(ONTOLOGY.toString());
        write(ONTOLOGY.toString());
        write(":");
        writeSpace();
        if (!ontology.isAnonymous()) {
            int indent = getIndent();
            writeFullURI(ontology.getOntologyID().getOntologyIRI().toString());
            writeNewLine();
            pushTab(indent);
            if (ontology.getOntologyID().getVersionIRI() != null) {
                writeFullURI(ontology.getOntologyID().getVersionIRI().toString());
            }
            popTab();
        }
        fireFrameRenderingStarted(ONTOLOGY.toString());
        writeNewLine();
        for (OWLImportsDeclaration decl : ontology.getImportsDeclarations()) {
            fireSectionItemPrepared(IMPORT.toString());
            write(IMPORT.toString());
            write(":");
            writeSpace();
            fireSectionRenderingStarted(IMPORT.toString());
            writeFullURI(decl.getURI().toString());
            writeNewLine();
            fireSectionRenderingFinished(IMPORT.toString());
        }
        writeNewLine();
        writeSection(ANNOTATIONS, ontology.getAnnotations(), ",", true);
        fireFrameRenderingFinished(ONTOLOGY.toString());
    }


    public void writePrefixMap() {
        ShortFormProvider sfp = getShortFormProvider();
        if (!(sfp instanceof ManchesterOWLSyntaxPrefixNameShortFormProvider)) {
            return;
        }
        ManchesterOWLSyntaxPrefixNameShortFormProvider prov = (ManchesterOWLSyntaxPrefixNameShortFormProvider) sfp;
        Map<String, String> prefixMap = new HashMap<String, String>();
        for (String prefixName : prov.getPrefixManager().getPrefixName2PrefixMap().keySet()) {
            String prefix = prov.getPrefixManager().getPrefix(prefixName);
            prefixMap.put(prefixName, prefix);
            write(PREFIX.toString());
            write(": ");
            write(prefixName);
            write(" ");
            writeFullURI(prefix);
            writeNewLine();
        }
        if (!prefixMap.isEmpty()) {
            writeNewLine();
            writeNewLine();
        }
    }


    public void writeFullURI(String uri) {
        write("<");
        write(uri);
        write(">");
    }

//    protected void write(IRI uri) {
//        String qname = null;
//        ShortFormProvider shortFormProvider = getShortFormProvider();
//        if(pm != null) {
//            qname = getPrefixManager().getPrefixIRI(uri);
//        }
//        if (qname != null) {
//            super.write(qname);
//        } else {
//            writeFullURI(uri.toString());
//        }
//    }

    public boolean isFiltered(AxiomType<?> axiomType) {
        return filteredAxiomTypes.contains(axiomType);
    }

    public boolean isDisplayed(OWLAxiom axiom) {
        if (axiom == null) {
            return false;
        }
        return axiomFilter.passes(axiom);
    }

    public Set<OWLAxiom> writeFrame(OWLEntity entity) {
        if (entity.isOWLClass()) {
            return write(entity.asOWLClass());
        }
        if (entity.isOWLObjectProperty()) {
            return write(entity.asOWLObjectProperty());
        }
        if (entity.isOWLDataProperty()) {
            return write(entity.asOWLDataProperty());
        }
        if (entity.isOWLNamedIndividual()) {
            return write(entity.asOWLNamedIndividual());
        }
        if (entity.isOWLAnnotationProperty()) {
            return write(entity.asOWLAnnotationProperty());
        }
        if (entity.isOWLDatatype()) {
            return write(entity.asOWLDatatype());
        }
        return Collections.emptySet();
    }

    public Set<OWLAxiom> write(OWLClass cls) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(CLASS, cls));

        if (!isFiltered(AxiomType.EQUIVALENT_CLASSES)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap equivalentClasses = new SectionMap();
                for (OWLEquivalentClassesAxiom ax : ontology.getEquivalentClassesAxioms(cls)) {
                    if (ax.getClassExpressions().size() == 2) {
                        if (isDisplayed(ax)) {
                            for (OWLClassExpression equivCls : ax.getClassExpressionsMinus(cls)) {
                                equivalentClasses.add(equivCls, ax);
                            }
                            axioms.add(ax);
                        }
                    }
                }
                equivalentClasses.remove(cls);
                writeSection(EQUIVALENT_TO, equivalentClasses, ",", true, ontology);
            }
        }

        if (!isFiltered(AxiomType.SUBCLASS_OF)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap superclasses = new SectionMap();
                for (OWLSubClassOfAxiom ax : ontology.getSubClassAxiomsForSubClass(cls)) {
                    if (isDisplayed(ax)) {
                        superclasses.add(ax.getSuperClass(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(SUBCLASS_OF, superclasses, ",", true, ontology);
            }
            if (renderExtensions) {
                for (OWLOntology ont : getOntologies()) {
                    SectionMap subClasses = new SectionMap();
                    for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSuperClass(cls)) {
                        if (isDisplayed(ax)) {
                            subClasses.add(ax.getSubClass(), ax);
                            axioms.add(ax);
                        }
                    }
                    writeSection(SUPERCLASS_OF, subClasses, ",", true, ont);
                }
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_CLASSES)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLAxiom> pairwiseDisjointClassesAxioms = new HashSet<OWLAxiom>();
                SectionMap disjointClasses = new SectionMap();
                for (OWLDisjointClassesAxiom ax : ontology.getDisjointClassesAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        if (ax.getClassExpressions().size() == 2) {
                            pairwiseDisjointClassesAxioms.add(ax);
                            OWLClassExpression disjointWith = ax.getClassExpressionsMinus(cls).iterator().next();
                            disjointClasses.add(disjointWith, ax);
                        }
                        axioms.add(ax);
                    }
                }
                writeSection(DISJOINT_WITH, disjointClasses, ", ", false, ontology);
                if (renderExtensions) {
                    // Handling of nary in frame style
                    Set<OWLClassExpression> naryDisjointClasses = new TreeSet<OWLClassExpression>();
                    for (OWLDisjointClassesAxiom ax : ontology.getDisjointClassesAxioms(cls)) {
                        if (isDisplayed(ax)) {
                            if (ax.getClassExpressions().size() > 2) {
                                Set<OWLClassExpression> allDisjointClasses = new TreeSet<OWLClassExpression>(ax.getClassExpressions());
                                allDisjointClasses.remove(cls);
                                naryDisjointClasses.addAll(allDisjointClasses);
                                axioms.add(ax);
                            }
                        }
                    }
                    writeSection(DISJOINT_CLASSES, naryDisjointClasses, ", ", false, ontology);
                }
            }
        }
        if (!isFiltered(AxiomType.HAS_KEY)) {
            for (OWLOntology ontology : getOntologies()) {
                for (OWLHasKeyAxiom ax : ontology.getHasKeyAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        SectionMap map = new SectionMap();
                        map.add(ax.getPropertyExpressions(), ax);
                        writeSection(HAS_KEY, map, ", ", true, ontology);
                    }
                }
            }
        }
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap individuals = new SectionMap();
                for (OWLClassAssertionAxiom ax : ontology.getClassAssertionAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        if (renderExtensions || ax.getIndividual().isAnonymous()) {
                            individuals.add(ax.getIndividual(), ax);
                            axioms.add(ax);
                        }
                    }
                }
                writeSection(INDIVIDUALS, individuals, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLAxiom> rules = new HashSet<OWLAxiom>();
                for (SWRLRule rule : ontology.getAxioms(AxiomType.SWRL_RULE)) {
                    if (isDisplayed(rule)) {
                        for (SWRLAtom atom : rule.getHead()) {
                            if (atom.getPredicate().equals(cls)) {
                                writeSection(RULE, rules, ", ", true, ontology);
                                break;
                            }
                        }
                    }
                }
            }

        }
        writeEntitySectionEnd(CLASS.toString());
        return axioms;
    }


    protected void writeEntitySectionEnd(String type) {
        fireFrameRenderingFinished(type);
        popTab();
        writeNewLine();
    }


    public Set<OWLAxiom> write(OWLObjectPropertyExpression property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(OBJECT_PROPERTY, property));
        if (!isFiltered(AxiomType.SUB_OBJECT_PROPERTY)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap properties = new SectionMap();
                for (OWLSubObjectPropertyOfAxiom ax : ontology.getObjectSubPropertyAxiomsForSubProperty(property)) {
                    if (isDisplayed(ax)) {
                        properties.add(ax.getSuperProperty(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, properties, ",", true, ontology);
            }
            if (renderExtensions) {
                for (OWLOntology ontology : getOntologies()) {
                    SectionMap properties = new SectionMap();
                    for (OWLSubObjectPropertyOfAxiom ax : ontology.getObjectSubPropertyAxiomsForSuperProperty(property)) {
                        if (isDisplayed(ax)) {
                            properties.add(ax.getSubProperty(), ax);
                            axioms.add(ax);
                        }
                    }
                    writeSection(SUPER_PROPERTY_OF, properties, ",", true, ontology);
                }
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap properties = new SectionMap();
                for (OWLEquivalentObjectPropertiesAxiom ax : ontology.getEquivalentObjectPropertiesAxioms(property)) {
                    if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                        Set<OWLObjectPropertyExpression> props = ax.getPropertiesMinus(property);
                        properties.add(props.iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(EQUIVALENT_TO, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap properties = new SectionMap();
                for (OWLDisjointObjectPropertiesAxiom ax : ontology.getDisjointObjectPropertiesAxioms(property)) {
                    if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        Set<OWLObjectPropertyExpression> props = ax.getPropertiesMinus(property);
                        properties.add(props.iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(DISJOINT_WITH, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
            for (OWLOntology ontology : getOntologies()) {
                for (OWLSubPropertyChainOfAxiom ax : ontology.getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    if (ax.getSuperProperty().equals(property)) {
                        if (isDisplayed(ax)) {
                            SectionMap map = new SectionMap();
                            map.add(ax.getPropertyChain(), ax);
                            writeSection(SUB_PROPERTY_CHAIN, map, " o ", false, ontology);
                            axioms.add(ax);
                        }
                    }
                }
            }
        }

        for (OWLOntology ontology : getOntologies()) {
            SectionMap characteristics = new SectionMap();
            if (!isFiltered(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLFunctionalObjectPropertyAxiom ax : ontology.getFunctionalObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }

            if (!isFiltered(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getInverseFunctionalObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(INVERSE_FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getSymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(SYMMETRIC.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getTransitiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(TRANSITIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.REFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getReflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(REFLEXIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getIrreflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(IRREFLEXIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getAsymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(ASYMMETRIC.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            writeSection(CHARACTERISTICS, characteristics, ",", true, ontology);
        }

        if (!isFiltered(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap expressions = new SectionMap();
                for (OWLObjectPropertyDomainAxiom ax : ontology.getObjectPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        expressions.add(ax.getDomain(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(DOMAIN, expressions, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.OBJECT_PROPERTY_RANGE)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap expressions = new SectionMap();
                for (OWLObjectPropertyRangeAxiom ax : ontology.getObjectPropertyRangeAxioms(property)) {
                    if (isDisplayed(ax)) {
                        expressions.add(ax.getRange(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(RANGE, expressions, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.INVERSE_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLObjectPropertyExpression> properties = new TreeSet<OWLObjectPropertyExpression>();
                for (OWLInverseObjectPropertiesAxiom ax : ontology.getInverseObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        if (ax.getFirstProperty().equals(property)) {
                            properties.add(ax.getSecondProperty());
                        }
                        else {
                            properties.add(ax.getFirstProperty());
                        }
                        axioms.add(ax);
                    }
                }
                writeSection(INVERSE_OF, properties, ",", true, ontology);
            }
        }

        if (!isFiltered(AxiomType.SWRL_RULE)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLAxiom> rules = new HashSet<OWLAxiom>();
                for (SWRLRule rule : ontology.getAxioms(AxiomType.SWRL_RULE)) {
                    if (isDisplayed(rule)) {
                        for (SWRLAtom atom : rule.getHead()) {
                            if (atom.getPredicate().equals(property)) {
                                rules.add(rule);
                                writeSection(RULE, rules, "�", true, ontology);
                                break;
                            }
                        }
                    }
                }

            }

        }
        writeEntitySectionEnd(OBJECT_PROPERTY.toString());
        return axioms;
    }


    public Set<OWLAxiom> write(OWLDataProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(DATA_PROPERTY, property));
        if (!isFiltered(AxiomType.FUNCTIONAL_DATA_PROPERTY)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap characteristics = new SectionMap();
                for (OWLAxiom ax : ontology.getFunctionalDataPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(CHARACTERISTICS, characteristics, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap domains = new SectionMap();
                for (OWLDataPropertyDomainAxiom ax : ontology.getDataPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        domains.add(ax.getDomain(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(DOMAIN, domains, ",", true, ontology);

            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_RANGE)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap ranges = new SectionMap();
                for (OWLDataPropertyRangeAxiom ax : ontology.getDataPropertyRangeAxioms(property)) {
                    if (isDisplayed(ax)) {
                        ranges.add(ax.getRange(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(RANGE, ranges, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUB_DATA_PROPERTY)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap supers = new SectionMap();
                for (OWLSubDataPropertyOfAxiom ax : ontology.getDataSubPropertyAxiomsForSubProperty(property)) {
                    if (isDisplayed(ax)) {
                        supers.add(ax.getSuperProperty(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, supers, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap props = new SectionMap();
                for (OWLEquivalentDataPropertiesAxiom ax : ontology.getEquivalentDataPropertiesAxioms(property)) {
                    if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                        props.add(ax.getPropertiesMinus(property).iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(EQUIVALENT_TO, props, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap props = new SectionMap();
                for (OWLDisjointDataPropertiesAxiom ax : ontology.getDisjointDataPropertiesAxioms(property)) {
                    if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        props.add(ax.getPropertiesMinus(property).iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                props.remove(property);
                writeSection(DISJOINT_WITH, props, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLAxiom> rules = new HashSet<OWLAxiom>();
                for (SWRLRule rule : ontology.getAxioms(AxiomType.SWRL_RULE)) {
                    if (isDisplayed(rule)) {
                        for (SWRLAtom atom : rule.getHead()) {
                            if (atom.getPredicate().equals(property)) {
                                writeSection(RULE, rules, "", true, ontology);
                                break;
                            }
                        }
                    }
                }
            }

        }
        writeEntitySectionEnd(DATA_PROPERTY.toString());
        return axioms;
    }


    public Set<OWLAxiom> write(OWLIndividual individual) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(INDIVIDUAL, individual));
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap expressions = new SectionMap();
                for (OWLClassAssertionAxiom ax : ontology.getClassAssertionAxioms(individual)) {
                    if (isDisplayed(ax)) {
                        expressions.add(ax.getClassExpression(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(TYPES, expressions, ",", true, ontology);
            }
        }
        for (OWLOntology ontology : getOntologies()) {

            List<OWLPropertyAssertionAxiom> assertions = new ArrayList<OWLPropertyAssertionAxiom>();
            assertions.addAll(ontology.getObjectPropertyAssertionAxioms(individual));
            assertions.addAll(ontology.getNegativeObjectPropertyAssertionAxioms(individual));
            assertions.addAll(ontology.getDataPropertyAssertionAxioms(individual));
            assertions.addAll(ontology.getNegativeDataPropertyAssertionAxioms(individual));


            if (!assertions.isEmpty()) {
                fireSectionRenderingPrepared(FACTS.toString());
                writeSection(FACTS);
                writeSpace();
                writeOntologiesList(ontology);
                incrementTab(1);
                writeNewLine();
                fireSectionRenderingStarted(FACTS.toString());

                for (Iterator<OWLPropertyAssertionAxiom> it = assertions.iterator(); it.hasNext();) {
                    OWLPropertyAssertionAxiom ax = it.next();
                    fireSectionItemPrepared(FACTS.toString());
                    Set<OWLAnnotation> annos = ax.getAnnotations();
                    if (!annos.isEmpty()) {
                        writeAnnotations(annos);
                        pushTab(getIndent() + 1);
                    }


                    if (ax instanceof OWLNegativeDataPropertyAssertionAxiom || ax instanceof OWLNegativeObjectPropertyAssertionAxiom) {
                        write(NOT);
                        writeSpace();
                    }
                    ax.getProperty().accept(this);
                    writeSpace();
                    writeSpace();
                    ax.getObject().accept(this);
                    if(!annos.isEmpty()) {
                        popTab();
                    }
                    fireSectionItemFinished(FACTS.toString());
                    if (it.hasNext()) {
                        write(",");
                        writeNewLine();
                    }

                }
                popTab();
                writeNewLine();
                writeNewLine();
            }
        }


        if (!isFiltered(AxiomType.SAME_INDIVIDUAL)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLIndividual> inds = new TreeSet<OWLIndividual>();
                for (OWLSameIndividualAxiom ax : ontology.getSameIndividualAxioms(individual)) {
                    if (isDisplayed(ax)) {
                        inds.addAll(ax.getIndividuals());
                        axioms.add(ax);
                    }
                }
                inds.remove(individual);
                writeSection(SAME_AS, inds, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DIFFERENT_INDIVIDUALS)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLIndividual> inds = new TreeSet<OWLIndividual>();
                Set<OWLDifferentIndividualsAxiom> nary = new TreeSet<OWLDifferentIndividualsAxiom>();
                for (OWLDifferentIndividualsAxiom ax : ontology.getDifferentIndividualAxioms(individual)) {
                    if (ax.getIndividuals().size() == 2 && isDisplayed(ax)) {
                        inds.addAll(ax.getIndividuals());
                        axioms.add(ax);
                    }
                    else {
                        nary.add(ax);
                    }
                }
                inds.remove(individual);
                writeSection(DIFFERENT_FROM, inds, ",", true, ontology);
                if (renderExtensions) {
                    for (OWLDifferentIndividualsAxiom ax : nary) {
                        writeSection(DIFFERENT_INDIVIDUALS, ax.getIndividuals(), ", ", false, ontology);
                    }
                }
            }
        }
        writeEntitySectionEnd(INDIVIDUAL.toString());
        return axioms;
    }



    public Set<OWLAxiom> write(OWLDatatype datatype) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(DATATYPE, datatype));
        if (!isFiltered(AxiomType.DATATYPE_DEFINITION)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLDataRange> dataRanges = new TreeSet<OWLDataRange>();
                for (OWLDatatypeDefinitionAxiom ax : ontology.getDatatypeDefinitions(datatype)) {
                    if (isDisplayed(ax)) {
                        axioms.add(ax);
                        dataRanges.add(ax.getDataRange());
                    }
                }
                writeSection(EQUIVALENT_TO, dataRanges, ",", true, ontology);
            }
        }
        writeEntitySectionEnd(DATATYPE.toString());
        return axioms;
    }

    public Set<OWLAxiom> write(SWRLRule rule) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>(1);
        for (OWLOntology ontology : getOntologies()) {
            if (ontology.containsAxiom(rule)) {
                writeSection(RULE, Collections.singleton(rule), "", true, ontology);
                axioms.add(rule);
            }
        }
        return axioms;
    }

    public Set<OWLAxiom> write(OWLAnnotationProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(ANNOTATION_PROPERTY, property));
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for (OWLOntology ont : getOntologies()) {
                Set<OWLAnnotation> annos = new TreeSet<OWLAnnotation>();
                for (OWLAnnotationAssertionAxiom ax : ont.getAnnotationAssertionAxioms(property.getIRI())) {
                    if (isDisplayed(ax)) {
                        annos.add(ax.getAnnotation());
                    }
                }
                writeSection(ANNOTATIONS, annos, ",", true, ont);
            }
        }
        if (!isFiltered(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            for (OWLOntology ont : getOntologies()) {
                Set<OWLAnnotationProperty> props = new TreeSet<OWLAnnotationProperty>();
                for (OWLSubAnnotationPropertyOfAxiom ax : ont.getSubAnnotationPropertyOfAxioms(property)) {
                    if (isDisplayed(ax)) {
                        props.add(ax.getSuperProperty());
                    }
                }
                writeSection(SUB_PROPERTY_OF, props, ",", true, ont);
            }
        }
        if (!isFiltered(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            for (OWLOntology ont : getOntologies()) {
                Set<IRI> iris = new TreeSet<IRI>();
                for (OWLAnnotationPropertyDomainAxiom ax : ont.getAnnotationPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        iris.add(ax.getDomain());
                    }
                }
                writeSection(DOMAIN, iris, ",", true, ont);
            }
        }
        if (!isFiltered(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            for (OWLOntology ont : getOntologies()) {
                Set<IRI> iris = new TreeSet<IRI>();
                for (OWLAnnotationPropertyRangeAxiom ax : ont.getAnnotationPropertyRangeAxioms(property)) {
                    if (isDisplayed(ax)) {
                        iris.add(ax.getRange());
                    }
                }
                writeSection(RANGE, iris, ",", true, ont);
            }
        }
        writeEntitySectionEnd(ANNOTATION_PROPERTY.toString());
        return axioms;
    }





    private Set<OWLAnnotationAssertionAxiom> writeEntityStart(ManchesterOWLSyntax keyword, OWLObject entity) {
        event = new RendererEvent(this, entity);
        String kw = keyword.toString();
        fireFrameRenderingPrepared(kw);
        writeSection(keyword);
        entity.accept(this);
        fireFrameRenderingStarted(kw);
        writeNewLine();
        incrementTab(4);
        writeNewLine();
        if (entity instanceof OWLEntity) {
            return writeAnnotations(((OWLEntity) entity).getIRI());
        }
        else if (entity instanceof OWLAnonymousIndividual) {
            return writeAnnotations((OWLAnonymousIndividual) entity);
        }
        return Collections.emptySet();
    }


    public Set<OWLAnnotationAssertionAxiom> writeAnnotations(OWLAnnotationSubject subject) {
        Set<OWLAnnotationAssertionAxiom> axioms = new HashSet<OWLAnnotationAssertionAxiom>();
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap sectionMap = new SectionMap();
                Set<OWLAnnotation> annos = new TreeSet<OWLAnnotation>();
                for (OWLAnnotationAssertionAxiom ax : ontology.getAnnotationAssertionAxioms(subject)) {
                    if (isDisplayed(ax)) {
                        axioms.add(ax);
                        sectionMap.add(ax.getAnnotation(), ax);
                    }
                }
                writeSection(ANNOTATIONS, sectionMap, ",", true, ontology);
            }
        }
        return axioms;
    }


    public void writeSection(ManchesterOWLSyntax keyword) {
        write("", keyword, "");
        write(":");
        writeSpace();
    }


    public void writeSection(ManchesterOWLSyntax keyword, SectionMap content, String delimeter, boolean newline, OWLOntology... ontologies) {
        String sec = keyword.toString();
        if (!content.isEmpty() || renderingDirector.renderEmptyFrameSection(keyword, ontologies)) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(ontologies);
            incrementTab(4);
            writeNewLine();
            fireSectionRenderingStarted(sec);
            for (Iterator<?> it = content.getSectionObjects().iterator(); it.hasNext();) {
                Object obj = it.next();
                Set<Set<OWLAnnotation>> annotationSets = content.getAnnotationsForSectionObject(obj);
                for (Iterator<Set<OWLAnnotation>> annosSetIt = annotationSets.iterator(); annosSetIt.hasNext();) {
                    Set<OWLAnnotation> annos = annosSetIt.next();
                    fireSectionItemPrepared(sec);
                    if (!annos.isEmpty()) {
                        incrementTab(4);
                        writeNewLine();
                        write(ManchesterOWLSyntax.ANNOTATIONS.toString());
                        write(": ");
                        pushTab(getIndent() + 1);
                        for (Iterator<OWLAnnotation> annoIt = annos.iterator(); annoIt.hasNext();) {
                            annoIt.next().accept(this);
                            if (annoIt.hasNext()) {
                                write(", ");
                                writeNewLine();
                            }
                        }
                        popTab();
                        popTab();
                        writeNewLine();
                    }
                    // Write actual object
                    if (obj instanceof OWLObject) {
                        ((OWLObject) obj).accept(this);
                    }
                    else if (obj instanceof Collection) {
                        for (Iterator<?> listIt = ((Collection<?>) obj).iterator(); listIt.hasNext();) {
                            Object o = listIt.next();
                            if (o instanceof OWLObject) {
                                ((OWLObject) o).accept(this);
                            }
                            else {
                                write(o.toString());
                            }
                            if (listIt.hasNext()) {
                                write(delimeter);
                                if (newline) {
                                    writeNewLine();
                                }
                            }
                        }

                    }
                    else {
                        write(obj.toString());
                    }
                    if (it.hasNext()) {
                        write(delimeter);
                        fireSectionItemFinished(sec);
                        if (newline) {
                            writeNewLine();
                        }
                    }
                    else {
                        fireSectionItemFinished(sec);
                    }

                    if (annosSetIt.hasNext()) {
                        write(",");
                        writeNewLine();
                    }
                }
            }
            fireSectionRenderingFinished(sec);
            popTab();
            writeNewLine();
            writeNewLine();
        }
    }

    public void writeSection(ManchesterOWLSyntax keyword, Collection<?> content, String delimeter, boolean newline, OWLOntology... ontologies) {

        String sec = keyword.toString();
        if (!content.isEmpty() || renderingDirector.renderEmptyFrameSection(keyword, ontologies)) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(ontologies);
            incrementTab(4);
            writeNewLine();
            fireSectionRenderingStarted(sec);
            for (Iterator<?> it = content.iterator(); it.hasNext();) {
                Object obj = it.next();
                fireSectionItemPrepared(sec);
                if (obj instanceof OWLObject) {
                    ((OWLObject) obj).accept(this);
                }
                else {
                    write(obj.toString());
                }
                if (it.hasNext()) {
                    write(delimeter);
                    fireSectionItemFinished(sec);
                    if (newline) {
                        writeNewLine();
                    }
                }
                else {
                    fireSectionItemFinished(sec);
                }
            }
            fireSectionRenderingFinished(sec);
            popTab();
            writeNewLine();
            writeNewLine();
        }
    }

    public void writeComment(String comment, boolean placeOnNewline) {
        writeComment("#", comment, placeOnNewline);
    }


    public void writeComment(String commentDelim, String comment, boolean placeOnNewline) {
        if (placeOnNewline) {
            writeNewLine();
        }
        write(commentDelim);
        write(comment);
        writeNewLine();
    }

    private void writeOntologiesList(OWLOntology... ontologies) {
        if (!renderExtensions) {
            return;
        }
        if (ontologies.length == 0) {
            return;
        }
//        if (ontologies.length == 1) {
//            if (defaultOntology != null) {
//            }
//        }
        write("[in ");
        int count = 0;
        for (OWLOntology ont : ontologies) {
            write(shortFormProvider.getShortForm(ont));
            count++;
            if (count < ontologies.length) {
                write(", ");
            }
        }
        write("]");
    }

    private RendererEvent event;

    private void fireFrameRenderingPrepared(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.frameRenderingPrepared(section, event);
        }
    }

    private void fireFrameRenderingStarted(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.frameRenderingStarted(section, event);
        }
    }

    private void fireFrameRenderingFinished(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.frameRenderingFinished(section, event);
        }
    }

    private void fireSectionRenderingPrepared(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionRenderingPrepared(section, event);
        }
    }


    private void fireSectionRenderingStarted(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionRenderingStarted(section, event);
        }
    }

    private void fireSectionRenderingFinished(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionRenderingFinished(section, event);
        }
    }

    private void fireSectionItemPrepared(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionItemPrepared(section, event);
        }
    }

    private void fireSectionItemFinished(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionItemFinished(section, event);
        }
    }


    private static class DefaultRenderingDirector implements RenderingDirector {
        @SuppressWarnings("unused")
        public boolean renderEmptyFrameSection(ManchesterOWLSyntax frameSectionKeyword, OWLOntology... ontologies) {
            return false;
        }
    }

}
