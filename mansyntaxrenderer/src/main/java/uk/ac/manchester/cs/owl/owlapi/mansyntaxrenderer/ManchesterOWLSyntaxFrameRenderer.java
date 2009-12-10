package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax;
import static org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax.*;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.*;

import java.io.Writer;
import java.util.*;
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
 * Date: 25-Apr-2007<br><br>
 */
public class ManchesterOWLSyntaxFrameRenderer extends ManchesterOWLSyntaxObjectRenderer implements OWLEntityVisitor {

    private OWLOntology defaultOntology;

    private Set<OWLOntology> ontologies;

    private OntologyIRIShortFormProvider shortFormProvider = new OntologyIRIShortFormProvider();

    private Set<AxiomType> filteredAxiomTypes = new HashSet<AxiomType>();

    private boolean renderExtensions = false;

    private List<RendererListener> listeners = new ArrayList<RendererListener>();

    private OWLAxiomFilter axiomFilter = new OWLAxiomFilter() {
        public boolean passes(OWLAxiom axiom) {
            return true;
        }
    };

    public ManchesterOWLSyntaxFrameRenderer(OWLOntologyManager owlOntologyManager, OWLOntology ontology, Writer writer, ShortFormProvider entityShortFormProvider) {
        this(owlOntologyManager, Collections.singleton(ontology), ontology, writer, entityShortFormProvider);
    }

    public ManchesterOWLSyntaxFrameRenderer(OWLOntologyManager owlOntologyManager, Set<OWLOntology> ontologies, OWLOntology defaultOntology, Writer writer, ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
        this.ontologies = new LinkedHashSet<OWLOntology>(ontologies);
        this.defaultOntology = defaultOntology;

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

    public void addFilteredAxiomType(AxiomType axiomType) {
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
                Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                map.put(ax.getClassExpressions(), ax.getAnnotations());
                writeSection(DISJOINT_CLASSES, map, ",", false, ontology);
            }
        }
        // Nary equivalent classes axioms
        for (OWLEquivalentClassesAxiom ax : ontology.getAxioms(AxiomType.EQUIVALENT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                map.put(ax.getClassExpressions(), ax.getAnnotations());
                writeSection(EQUIVALENT_CLASSES, map, ",", false, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointObjectPropertiesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                map.put(ax.getProperties(), ax.getAnnotations());
                writeSection(DISJOINT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary equivlant properties
        for (OWLEquivalentObjectPropertiesAxiom ax : ontology.getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                map.put(ax.getProperties(), ax.getAnnotations());
                writeSection(EQUIVALENT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointDataPropertiesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                map.put(ax.getProperties(), ax.getAnnotations());
                writeSection(DISJOINT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary equivalent properties
        for (OWLEquivalentDataPropertiesAxiom ax : ontology.getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                map.put(ax.getProperties(), ax.getAnnotations());
                writeSection(EQUIVALENT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary different individuals
        for (OWLDifferentIndividualsAxiom ax : ontology.getAxioms(AxiomType.DIFFERENT_INDIVIDUALS)) {
            if (ax.getIndividuals().size() > 2) {
                Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                map.put(ax.getIndividuals(), ax.getAnnotations());
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

    public boolean isFiltered(AxiomType axiomType) {
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
                Map<OWLClassExpression, Set<OWLAnnotation>> equivalentClasses = new TreeMap<OWLClassExpression, Set<OWLAnnotation>>();
                for (OWLEquivalentClassesAxiom ax : ontology.getEquivalentClassesAxioms(cls)) {
                    if (ax.getClassExpressions().size() == 2) {
                        if (isDisplayed(ax)) {
                            for (OWLClassExpression equivCls : ax.getClassExpressionsMinus(cls)) {
                                equivalentClasses.put(equivCls, ax.getAnnotations());
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
                Map<OWLClassExpression, Set<OWLAnnotation>> superclasses = new TreeMap<OWLClassExpression, Set<OWLAnnotation>>();
                for (OWLSubClassOfAxiom ax : ontology.getSubClassAxiomsForSubClass(cls)) {
                    if (isDisplayed(ax)) {
                        superclasses.put(ax.getSuperClass(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(SUBCLASS_OF, superclasses, ",", true, ontology);
            }
            if (renderExtensions) {
                for (OWLOntology ont : getOntologies()) {
                    Set<OWLClassExpression> subClasses = new TreeSet<OWLClassExpression>();
                    for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSuperClass(cls)) {
                        if (isDisplayed(ax)) {
                            subClasses.add(ax.getSubClass());
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
                Map<OWLClassExpression, Set<OWLAnnotation>> disjointClasses = new TreeMap<OWLClassExpression, Set<OWLAnnotation>>();
                for (OWLDisjointClassesAxiom ax : ontology.getDisjointClassesAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        if (ax.getClassExpressions().size() <= 2) {
                            pairwiseDisjointClassesAxioms.add(ax);
                            OWLClassExpression disjointWith = ax.getClassExpressionsMinus(cls).iterator().next();
                            disjointClasses.put(disjointWith, ax.getAnnotations());
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
                        Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                        map.put(ax.getPropertyExpressions(), ax.getAnnotations());
                        writeSection(HAS_KEY, map, ", ", true, ontology);
                    }
                }
            }
        }
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLIndividual, Set<OWLAnnotation>> individuals = new TreeMap<OWLIndividual, Set<OWLAnnotation>>();
                for (OWLClassAssertionAxiom ax : ontology.getClassAssertionAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        if (renderExtensions || ax.getIndividual().isAnonymous()) {
                            individuals.put(ax.getIndividual(), ax.getAnnotations());
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
                Map<OWLObjectPropertyExpression, Set<OWLAnnotation>> properties = new TreeMap<OWLObjectPropertyExpression, Set<OWLAnnotation>>();
                for (OWLSubObjectPropertyOfAxiom ax : ontology.getObjectSubPropertyAxiomsForSubProperty(property)) {
                    if (isDisplayed(ax)) {
                        properties.put(ax.getSuperProperty(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, properties, ",", true, ontology);
            }
            if (renderExtensions) {
                for (OWLOntology ontology : getOntologies()) {
                    Map<OWLObjectPropertyExpression, Set<OWLAnnotation>> properties = new TreeMap<OWLObjectPropertyExpression, Set<OWLAnnotation>>();
                    for (OWLSubObjectPropertyOfAxiom ax : ontology.getObjectSubPropertyAxiomsForSuperProperty(property)) {
                        if (isDisplayed(ax)) {
                            properties.put(ax.getSubProperty(), ax.getAnnotations());
                            axioms.add(ax);
                        }
                    }
                    writeSection(SUPER_PROPERTY_OF, properties, ",", true, ontology);
                }
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLObjectPropertyExpression, Set<OWLAnnotation>> properties = new TreeMap<OWLObjectPropertyExpression, Set<OWLAnnotation>>();
                for (OWLEquivalentObjectPropertiesAxiom ax : ontology.getEquivalentObjectPropertiesAxioms(property)) {
                    if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                        Set<OWLObjectPropertyExpression> props = ax.getPropertiesMinus(property);
                        properties.put(props.iterator().next(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(EQUIVALENT_TO, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLObjectPropertyExpression, Set<OWLAnnotation>> properties = new TreeMap<OWLObjectPropertyExpression, Set<OWLAnnotation>>();
                for (OWLDisjointObjectPropertiesAxiom ax : ontology.getDisjointObjectPropertiesAxioms(property)) {
                    if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        Set<OWLObjectPropertyExpression> props = ax.getPropertiesMinus(property);
                        properties.put(props.iterator().next(), ax.getAnnotations());
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
                            Map<Object, Set<OWLAnnotation>> map = new HashMap<Object, Set<OWLAnnotation>>();
                            map.put(ax.getPropertyChain(), ax.getAnnotations());
                            writeSection(SUB_PROPERTY_CHAIN, map, " o ", false, ontology);
                            axioms.add(ax);
                        }
                    }
                }
            }
        }

        for (OWLOntology ontology : getOntologies()) {
            Map<String, Set<OWLAnnotation>> characteristics = new LinkedHashMap<String, Set<OWLAnnotation>>();
            if (!isFiltered(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLFunctionalObjectPropertyAxiom ax : ontology.getFunctionalObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(FUNCTIONAL.toString(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
            }

            if (!isFiltered(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getInverseFunctionalObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(INVERSE_FUNCTIONAL.toString(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getSymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(SYMMETRIC.toString(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getTransitiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(TRANSITIVE.toString(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.REFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getReflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(REFLEXIVE.toString(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getIrreflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(IRREFLEXIVE.toString(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology.getAsymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(ASYMMETRIC.toString(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
            }
            writeSection(CHARACTERISTICS, characteristics, ",", true, ontology);
        }

        if (!isFiltered(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLClassExpression, Set<OWLAnnotation>> expressions = new TreeMap<OWLClassExpression, Set<OWLAnnotation>>();
                for (OWLObjectPropertyDomainAxiom ax : ontology.getObjectPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        expressions.put(ax.getDomain(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(DOMAIN, expressions, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.OBJECT_PROPERTY_RANGE)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLClassExpression, Set<OWLAnnotation>> expressions = new TreeMap<OWLClassExpression, Set<OWLAnnotation>>();
                for (OWLObjectPropertyRangeAxiom ax : ontology.getObjectPropertyRangeAxioms(property)) {
                    if (isDisplayed(ax)) {
                        expressions.put(ax.getRange(), ax.getAnnotations());
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
                Map<String, Set<OWLAnnotation>> characteristics = new HashMap<String, Set<OWLAnnotation>>();
                for (OWLAxiom ax : ontology.getFunctionalDataPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(FUNCTIONAL.toString(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(CHARACTERISTICS, characteristics, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLClassExpression, Set<OWLAnnotation>> domains = new TreeMap<OWLClassExpression, Set<OWLAnnotation>>();
                for (OWLDataPropertyDomainAxiom ax : ontology.getDataPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        domains.put(ax.getDomain(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(DOMAIN, domains, ",", true, ontology);

            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_RANGE)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLDataRange, Set<OWLAnnotation>> ranges = new TreeMap<OWLDataRange, Set<OWLAnnotation>>();
                for (OWLDataPropertyRangeAxiom ax : ontology.getDataPropertyRangeAxioms(property)) {
                    if (isDisplayed(ax)) {
                        ranges.put(ax.getRange(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(RANGE, ranges, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUB_DATA_PROPERTY)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLDataPropertyExpression, Set<OWLAnnotation>> supers = new TreeMap<OWLDataPropertyExpression, Set<OWLAnnotation>>();
                for (OWLSubDataPropertyOfAxiom ax : ontology.getDataSubPropertyAxiomsForSubProperty(property)) {
                    if (isDisplayed(ax)) {
                        supers.put(ax.getSuperProperty(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, supers, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLDataPropertyExpression, Set<OWLAnnotation>> props = new TreeMap<OWLDataPropertyExpression, Set<OWLAnnotation>>();
                for (OWLEquivalentDataPropertiesAxiom ax : ontology.getEquivalentDataPropertiesAxioms(property)) {
                    if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                        props.put(ax.getPropertiesMinus(property).iterator().next(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(EQUIVALENT_TO, props, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Map<OWLDataPropertyExpression, Set<OWLAnnotation>> props = new TreeMap<OWLDataPropertyExpression, Set<OWLAnnotation>>();
                for (OWLDisjointDataPropertiesAxiom ax : ontology.getDisjointDataPropertiesAxioms(property)) {
                    if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        props.put(ax.getPropertiesMinus(property).iterator().next(), ax.getAnnotations());
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
                Map<OWLClassExpression, Set<OWLAnnotation>> expressions = new TreeMap<OWLClassExpression, Set<OWLAnnotation>>();
                for (OWLClassAssertionAxiom ax : ontology.getClassAssertionAxioms(individual)) {
                    if (isDisplayed(ax)) {
                        expressions.put(ax.getClassExpression(), ax.getAnnotations());
                        axioms.add(ax);
                    }
                }
                writeSection(TYPES, expressions, ",", true, ontology);
            }
        }
        for (OWLOntology ontology : getOntologies()) {
            // Facts - messy!
            Map<OWLObjectPropertyExpression, Map<OWLIndividual, Set<OWLAnnotation>>> objectMap = new TreeMap<OWLObjectPropertyExpression, Map<OWLIndividual, Set<OWLAnnotation>>>();
            if (!isFiltered(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
                for (OWLObjectPropertyAssertionAxiom ax : ontology.getObjectPropertyAssertionAxioms(individual)) {
                    if (isDisplayed(ax)) {
                        axioms.add((ax));
                        Map<OWLIndividual, Set<OWLAnnotation>> inds = objectMap.get(ax.getProperty());
                        if (inds == null) {
                            inds = new TreeMap<OWLIndividual, Set<OWLAnnotation>>();
                            objectMap.put(ax.getProperty(), inds);
                        }
                        inds.put(ax.getObject(), ax.getAnnotations());
                    }
                }
            }
            Map<OWLDataPropertyExpression, Map<OWLLiteral, Set<OWLAnnotation>>> dataMap = new TreeMap<OWLDataPropertyExpression, Map<OWLLiteral, Set<OWLAnnotation>>>();
            if (!isFiltered(AxiomType.DATA_PROPERTY_ASSERTION)) {
                for (OWLDataPropertyAssertionAxiom ax : ontology.getDataPropertyAssertionAxioms(individual)) {
                    if (isDisplayed(ax)) {
                        axioms.add((ax));
                        Map<OWLLiteral, Set<OWLAnnotation>> objs = dataMap.get(ax.getProperty());
                        if (objs == null) {
                            objs = new TreeMap<OWLLiteral, Set<OWLAnnotation>>();
                            dataMap.put(ax.getProperty(), objs);
                        }
                        objs.put(ax.getObject(), ax.getAnnotations());
                    }
                }
            }
            // Negative facts
            Map<OWLObjectPropertyExpression, Map<OWLIndividual, Set<OWLAnnotation>>> negObjectMap = new TreeMap<OWLObjectPropertyExpression, Map<OWLIndividual, Set<OWLAnnotation>>>();
            if (!isFiltered(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION)) {
                for (OWLNegativeObjectPropertyAssertionAxiom ax : ontology.getNegativeObjectPropertyAssertionAxioms(individual)) {
                    if (isDisplayed(ax)) {
                        axioms.add((ax));
                        Map<OWLIndividual, Set<OWLAnnotation>> inds = objectMap.get(ax.getProperty());
                        if (inds == null) {
                            inds = new TreeMap<OWLIndividual, Set<OWLAnnotation>>();
                            negObjectMap.put(ax.getProperty(), inds);
                        }
                        inds.put(ax.getObject(), ax.getAnnotations());
                    }
                }
            }
            Map<OWLDataPropertyExpression, Map<OWLLiteral, Set<OWLAnnotation>>> negDataMap = new TreeMap<OWLDataPropertyExpression, Map<OWLLiteral, Set<OWLAnnotation>>>();
            if (!isFiltered(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION)) {
                for (OWLNegativeDataPropertyAssertionAxiom ax : ontology.getNegativeDataPropertyAssertionAxioms(individual)) {
                    if (isDisplayed(ax)) {
                        axioms.add((ax));
                        Map<OWLLiteral, Set<OWLAnnotation>> objs = negDataMap.get(ax.getProperty());
                        if (objs == null) {
                            objs = new TreeMap<OWLLiteral, Set<OWLAnnotation>>();
                            negDataMap.put(ax.getProperty(), objs);
                        }
                        objs.put(ax.getObject(), ax.getAnnotations());
                    }
                }
            }

            if (!objectMap.isEmpty() || !dataMap.isEmpty() || !negObjectMap.isEmpty() || !negDataMap.isEmpty()) {
                fireSectionRenderingPrepared(FACTS.toString());
                writeSection(FACTS);
                writeSpace();
                writeOntologiesList(ontology);
                incrementTab(4);
                writeNewLine();
                fireSectionRenderingStarted(FACTS.toString());
                writeFacts(objectMap, false);
                if (!objectMap.isEmpty() && (!dataMap.isEmpty() || !negObjectMap.isEmpty() || !negDataMap.isEmpty())) {
                    write(",");
                    writeNewLine();
                }
                writeFacts(dataMap, false);
                if (!dataMap.isEmpty() && (!negObjectMap.isEmpty() || !negDataMap.isEmpty())) {
                    write(",");
                    writeNewLine();
                }
                writeFacts(negObjectMap, true);
                if (!negDataMap.isEmpty() && !negObjectMap.isEmpty()) {
                    write(",");
                    writeNewLine();
                }
                writeFacts(negDataMap, true);
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


    private <K extends OWLObject, V extends OWLObject> void writeFacts(Map<K, Map<V, Set<OWLAnnotation>>> relationshipsMap, boolean negative) {
        for (Iterator<K> propIt = relationshipsMap.keySet().iterator(); propIt.hasNext();) {
            K prop = propIt.next();
            Map<V, Set<OWLAnnotation>> object2AnnotationsMap = relationshipsMap.get(prop);
            for (Iterator<V> it = object2AnnotationsMap.keySet().iterator(); it.hasNext();) {
                fireSectionItemPrepared(FACTS.toString());
                V ind = it.next();
                Set<OWLAnnotation> annotations = object2AnnotationsMap.get(ind);
                if (!annotations.isEmpty()) {
                    write(ANNOTATIONS.toString());
                    write(": ");
                    pushTab(getIndent() + 1);
                    for (Iterator<OWLAnnotation> annoIt = annotations.iterator(); annoIt.hasNext();) {
                        OWLAnnotation anno = annoIt.next();
                        anno.accept(this);
                        if (annoIt.hasNext()) {
                            write(", ");
                            writeNewLine();
                        }
                    }
                    popTab();
                    writeNewLine();
                    writeNewLine();
                }
                if (negative) {
                    write(NOT);
                    writeSpace();
                }
                prop.accept(this);
                writeSpace();
                writeSpace();
                ind.accept(this);
                fireSectionItemFinished(FACTS.toString());
                if (it.hasNext()) {
                    write(",");
                    writeNewLine();
                }
            }
            if (propIt.hasNext()) {
                write(",");
                writeNewLine();
            }
        }
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
                Set<OWLAnnotation> annos = new TreeSet<OWLAnnotation>();
                for (OWLAnnotationAssertionAxiom ax : ontology.getAnnotationAssertionAxioms(subject)) {
                    if (isDisplayed(ax)) {
                        axioms.add(ax);
                        annos.add(ax.getAnnotation());
                    }
                }
                writeSection(ANNOTATIONS, annos, ",", true, ontology);
            }
        }
        return axioms;
    }


    public void writeSection(ManchesterOWLSyntax keyword) {
        write("", keyword, "");
        write(":");
        writeSpace();
    }


    public void writeSection(ManchesterOWLSyntax keyword, Map<? extends Object, Set<OWLAnnotation>> content, String delimeter, boolean newline, OWLOntology... ontologies) {
        String sec = keyword.toString();
        if (!content.isEmpty()) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(ontologies);
            incrementTab(4);
            writeNewLine();
            fireSectionRenderingStarted(sec);
            for (Iterator<? extends Object> it = content.keySet().iterator(); it.hasNext();) {
                Object obj = it.next();
                fireSectionItemPrepared(sec);
                Set<OWLAnnotation> annos = content.get(obj);
                if (!annos.isEmpty()) {
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
                    writeNewLine();
                    writeNewLine();
                }
                if (obj instanceof OWLObject) {
                    ((OWLObject) obj).accept(this);
                }
                else if (obj instanceof Collection) {
                    for (Iterator<Object> listIt = ((Collection) obj).iterator(); listIt.hasNext();) {
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
            }
            fireSectionRenderingFinished(sec);
            popTab();
            writeNewLine();
            writeNewLine();
        }
    }

    public void writeSection(ManchesterOWLSyntax keyword, Collection<? extends Object> content, String delimeter, boolean newline, OWLOntology... ontologies) {

        String sec = keyword.toString();
        if (!content.isEmpty()) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(ontologies);
            incrementTab(4);
            writeNewLine();
            fireSectionRenderingStarted(sec);
            for (Iterator<? extends Object> it = content.iterator(); it.hasNext();) {
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
        if (ontologies.length == 1) {
            if (defaultOntology != null) {
            }
        }
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

}
