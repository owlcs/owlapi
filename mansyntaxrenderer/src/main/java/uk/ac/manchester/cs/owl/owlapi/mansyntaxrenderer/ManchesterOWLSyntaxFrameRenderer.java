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

    private OWLAxiomFilter axiomFilter = new OWLAxiomFilter() {
        public boolean passes(OWLAxiom axiom) {
            return true;
        }
    };

    public ManchesterOWLSyntaxFrameRenderer(OWLOntologyManager owlOntologyManager, OWLOntology ontology,
                                            Writer writer, ShortFormProvider entityShortFormProvider) {
        this(owlOntologyManager, Collections.singleton(ontology), ontology, writer, entityShortFormProvider);
    }

    public ManchesterOWLSyntaxFrameRenderer(OWLOntologyManager owlOntologyManager,
                                            Set<OWLOntology> ontologies,
                                            OWLOntology defaultOntology,
                                            Writer writer, ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
        this.ontologies = new LinkedHashSet<OWLOntology>(ontologies);
        this.defaultOntology = defaultOntology;

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

    //    private OWLOntology getOntology() {
//        return this.ontology;
//    }


    Set<OWLOntology> getOntologies() {
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

        for(OWLAnnotationProperty prop : ontology.getReferencedAnnotationProperties()) {
            write(prop);
        }
        for(OWLDatatype datatype : ontology.getReferencedDatatypes()) {
            write(datatype);
        }
        for (OWLObjectProperty prop : ontology.getReferencedObjectProperties()) {
            write(prop);
            OWLObjectPropertyExpression invProp = prop.getInverseProperty();
            if(!ontology.getAxioms(invProp).isEmpty()) {
                write(invProp);
            }
        }
        for (OWLDataProperty prop : ontology.getReferencedDataProperties()) {
            write(prop);
        }
        for (OWLClass cls : ontology.getReferencedClasses()) {
            write(cls);
        }
        for (OWLNamedIndividual ind : ontology.getReferencedIndividuals()) {
            write(ind);
        }
        for(OWLAnonymousIndividual ind : ontology.getReferencedAnonymousIndividuals()) {
            write(ind);
        }
        // Nary disjoint classes axioms
        for (OWLDisjointClassesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                writeSection(DISJOINT_CLASSES, ax.getClassExpressions(), ",", false);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointObjectPropertiesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                writeSection(DISJOINT_OBJECT_PROPERTIES, ax.getProperties(), ",", false);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointDataPropertiesAxiom ax : ontology.getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                writeSection(DISJOINT_DATA_PROPERTIES, ax.getProperties(), ",", false);
            }
        }
        // Nary different individuals
        for (OWLDifferentIndividualsAxiom ax : ontology.getAxioms(AxiomType.DIFFERENT_INDIVIDUALS)) {
            if (ax.getIndividuals().size() > 2) {
                writeSection(DIFFERENT_INDIVIDUALS, ax.getIndividuals(), ",", false);
            }
        }
        for(SWRLRule rule : ontology.getRules()) {
            writeSection(RULE, Collections.singleton(rule), ", ", false);
        }
        flush();
    }


    public void writeOntologyHeader(OWLOntology ontology) {
        write(ONTOLOGY.toString());
        write(":");
        writeSpace();
        if (!ontology.isAnonymous()) {
            int indent = getIndent();
            writeFullURI(ontology.getOntologyID().getOntologyIRI().toString());
            writeNewLine();
            pushTab(indent);
            if(ontology.getVersionIRI() != null) {
               writeFullURI(ontology.getVersionIRI().toURI().toString());
            }
            popTab();
        }
        writeNewLine();
        for (OWLImportsDeclaration decl : ontology.getImportsDeclarations()) {
            write(IMPORT.toString());
            write(":");
            writeSpace();
            writeFullURI(decl.getURI().toString());
            writeNewLine();
        }
        writeNewLine();
        writeSection(ANNOTATIONS, ontology.getAnnotations(), ",", true);
    }


    public void writePrefixMap() {
        ShortFormProvider sfp = getShortFormProvider();
        if(!(sfp instanceof ManchesterOWLSyntaxPrefixNameShortFormProvider)) {
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
        if(axiom == null) {
            return false;
        }
        return axiomFilter.passes(axiom);
    }

    public Set<OWLAxiom> writeFrame(OWLEntity entity) {
        if(entity.isOWLClass()) {
            return write(entity.asOWLClass());
        }
        if(entity.isOWLObjectProperty()) {
            return write(entity.asOWLObjectProperty());
        }
        if(entity.isOWLDataProperty()) {
            return write(entity.asOWLDataProperty());
        }
        if(entity.isOWLNamedIndividual()) {
            return write(entity.asOWLNamedIndividual());
        }
        if(entity.isOWLAnnotationProperty()) {
            return write(entity.asOWLAnnotationProperty());
        }
        if(entity.isOWLDatatype()) {
            return write(entity.asOWLDatatype());
        }
        return Collections.emptySet();
    }

    public Set<OWLAxiom> write(OWLClass cls) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(CLASS, cls));
        if (!isFiltered(AxiomType.EQUIVALENT_CLASSES)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLClassExpression> equivalentClasses = new TreeSet<OWLClassExpression>();
                for (OWLEquivalentClassesAxiom ax : ontology.getEquivalentClassesAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        equivalentClasses.addAll(ax.getClassExpressions());
                        axioms.add(ax);
                    }
                }
                equivalentClasses.remove(cls);
                writeSection(EQUIVALENT_TO, equivalentClasses, ",", true, ontology);
            }
        }

        if (!isFiltered(AxiomType.SUBCLASS)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLClassExpression> superclasses = new TreeSet<OWLClassExpression>();
                for (OWLSubClassOfAxiom ax : ontology.getSubClassAxiomsForSubClass(cls)) {
                    if (isDisplayed(ax)) {
                        superclasses.add(ax.getSuperClass());
                        axioms.add(ax);
                    }
                }
                writeSection(SUBCLASS_OF, superclasses, ",", true, ontology);
            }
            if (renderExtensions) {
                for(OWLOntology ont : getOntologies()) {
                    Set<OWLClassExpression> subClasses = new TreeSet<OWLClassExpression>();
                    for(OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSuperClass(cls)) {
                        if(isDisplayed(ax)) {
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
                Set<OWLClassExpression> disjointClasses = new TreeSet<OWLClassExpression>();
                for (OWLDisjointClassesAxiom ax : ontology.getDisjointClassesAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        if (ax.getClassExpressions().size() <= 2) {
                            pairwiseDisjointClassesAxioms.add(ax);
                            disjointClasses.addAll(ax.getClassExpressions());
                        }
                        axioms.add(ax);
                    }
                }
                disjointClasses.remove(cls);
                writeSection(DISJOINT_WITH, disjointClasses, ", ", false, ontology);
                if (renderExtensions) {
                    // Handling of nary in frame style
                    Set<OWLClassExpression> naryDisjointClasses = new TreeSet<OWLClassExpression>();
                    for(OWLDisjointClassesAxiom ax : ontology.getDisjointClassesAxioms(cls)) {
                        if(isDisplayed(ax)) {
                            if(ax.getClassExpressions().size() > 2) {
                                Set<OWLClassExpression> allDisjointClasses = new TreeSet<OWLClassExpression>( ax.getClassExpressions());
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
        if(!isFiltered(AxiomType.HAS_KEY)) {
            for(OWLOntology ontology : getOntologies()) {
                for(OWLHasKeyAxiom ax : ontology.getHasKeyAxioms(cls)) {
                    if(isDisplayed(ax)) {
                        writeSection(HAS_KEY, ax.getPropertyExpressions(), ", ", true, ontology);
                    }
                }
            }
        }
        if(!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for(OWLOntology ontology : getOntologies()) {
                Set<OWLIndividual> individuals = new TreeSet<OWLIndividual>();
                for(OWLClassAssertionAxiom ax : ontology.getClassAssertionAxioms(cls)) {
                    if(isDisplayed(ax)) {
                        if (renderExtensions || ax.getIndividual().isAnonymous()) {
                            individuals.add(ax.getIndividual());
                            axioms.add(ax);
                        }
                    }
                }
                writeSection(INDIVIDUALS, individuals, ",", true, ontology);
            }
        }
        if(!isFiltered(AxiomType.SWRL_RULE)) {
            for(OWLOntology ontology : getOntologies()) {
                Set<OWLAxiom> rules = new HashSet<OWLAxiom>();
                for(SWRLRule rule : ontology.getRules()) {
                    if (isDisplayed(rule)) {
                        for(SWRLAtom atom : rule.getHead()) {
                            if(atom.getPredicate().equals(cls)) {
                                writeSection(RULE, rules, ", ", true, ontology);
                                break;
                            }
                        }
                    }
                }
            }

        }
        writeEntitySectionEnd();
        return axioms;
    }


    protected void writeEntitySectionEnd() {
        popTab();
        writeNewLine();
    }


    public Set<OWLAxiom> write(OWLObjectPropertyExpression property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(OBJECT_PROPERTY, property));
        if (!isFiltered(AxiomType.SUB_OBJECT_PROPERTY)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLObjectPropertyExpression> properties = new TreeSet<OWLObjectPropertyExpression>();
                for(OWLSubObjectPropertyOfAxiom ax : ontology.getObjectSubPropertyAxiomsForSubProperty(property)) {
                    if(isDisplayed(ax)) {
                        properties.add(ax.getSuperProperty());
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, properties, ",", true, ontology);
            }
            if(renderExtensions) {
                for(OWLOntology ontology : getOntologies()) {
                    Set<OWLObjectPropertyExpression> properties = new TreeSet<OWLObjectPropertyExpression>();
                    for(OWLSubObjectPropertyOfAxiom ax : ontology.getObjectSubPropertyAxiomsForSuperProperty(property)) {
                        if(isDisplayed(ax)) {
                            properties.add(ax.getSubProperty());
                            axioms.add(ax);
                        }
                    }
                    writeSection(SUPER_PROPERTY_OF, properties, ",", true, ontology);
                }
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLObjectPropertyExpression> properties = new TreeSet<OWLObjectPropertyExpression>();
                for(OWLEquivalentObjectPropertiesAxiom ax : ontology.getEquivalentObjectPropertiesAxioms(property)) {
                    if(isDisplayed(ax)) {
                        properties.addAll(ax.getProperties());
                        axioms.add(ax);
                    }
                }
                properties.remove(property);
                writeSection(EQUIVALENT_TO, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLObjectPropertyExpression> properties = new TreeSet<OWLObjectPropertyExpression>();
                for(OWLDisjointObjectPropertiesAxiom ax : ontology.getDisjointObjectPropertiesAxioms(property)) {
                    if(ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        properties.addAll(ax.getProperties());
                        axioms.add(ax);
                    }
                }
                properties.remove(property);
                writeSection(DISJOINT_WITH, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
            for (OWLOntology ontology : getOntologies()) {
                for (OWLSubPropertyChainOfAxiom ax : ontology.getPropertyChainSubPropertyAxioms()) {
                    if (ax.getSuperProperty().equals(property)) {
                        if (isDisplayed(ax)) {
                            writeSection(SUB_PROPERTY_CHAIN, new LinkedHashSet<OWLObjectPropertyExpression>(ax.getPropertyChain()), " o ", false, ontology);
                            axioms.add(ax);
                        }
                    }
                }
            }
        }

        for (OWLOntology ontology : getOntologies()) {
            List<String> characteristics = new ArrayList<String>();
            if (!isFiltered(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)) {
                for(OWLFunctionalObjectPropertyAxiom ax : ontology.getFunctionalObjectPropertyAxioms(property)) {
                    if(isDisplayed(ax)) {
                        characteristics.add(FUNCTIONAL.toString());
                        axioms.add(ax);
                    }
                }
            }

            if (!isFiltered(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
                    for(OWLAxiom ax : ontology.getInverseFunctionalObjectPropertyAxioms(property)) {
                        if (isDisplayed(ax)) {
                            characteristics.add(INVERSE_FUNCTIONAL.toString());
                            axioms.add(ax);
                        }
                    }
            }
            if (!isFiltered(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
                    for(OWLAxiom ax : ontology.getSymmetricObjectPropertyAxioms(property)) {
                        if (isDisplayed(ax)) {
                            characteristics.add(SYMMETRIC.toString());
                            axioms.add(ax);
                        }
                    }
            }
            if (!isFiltered(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
                for(OWLAxiom ax : ontology.getTransitiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(TRANSITIVE.toString());
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.REFLEXIVE_OBJECT_PROPERTY)) {
                for(OWLAxiom ax : ontology.getReflexiveObjectPropertyAxioms(property)) {
                    if(isDisplayed(ax)) {
                        characteristics.add(REFLEXIVE.toString());
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY)) {
                for(OWLAxiom ax : ontology.getIrreflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(IRREFLEXIVE.toString());
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
                for(OWLAxiom ax : ontology.getAsymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(ANTI_SYMMETRIC.toString());
                        axioms.add(ax);
                    }
                }
            }
            writeSection(CHARACTERISTICS, new LinkedHashSet(characteristics), ",", true, ontology);
        }

        if (!isFiltered(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLClassExpression> expressions = new TreeSet<OWLClassExpression>();
                for(OWLObjectPropertyDomainAxiom ax : ontology.getObjectPropertyDomainAxioms(property)) {
                    if(isDisplayed(ax)) {
                        expressions.add(ax.getDomain());
                        axioms.add(ax);
                    }
                }
                writeSection(DOMAIN, expressions, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_RANGE)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLClassExpression> expressions = new TreeSet<OWLClassExpression>();
                for(OWLObjectPropertyRangeAxiom ax : ontology.getObjectPropertyRangeAxioms(property)) {
                    if(isDisplayed(ax)) {
                        expressions.add(ax.getRange());
                        axioms.add(ax);
                    }
                }
                writeSection(RANGE, expressions, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.INVERSE_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLObjectPropertyExpression> properties = new TreeSet<OWLObjectPropertyExpression>();
                for(OWLInverseObjectPropertiesAxiom ax : ontology.getInverseObjectPropertyAxioms(property)) {
                    if(isDisplayed(ax)) {
                        properties.addAll(ax.getProperties());
                        axioms.add(ax);
                    }
                }
                properties.remove(property);
                writeSection(INVERSE_OF, properties, ",", true, ontology);
            }
        }

        if(!isFiltered(AxiomType.SWRL_RULE)) {
            for(OWLOntology ontology : getOntologies()) {
                Set<OWLAxiom> rules = new HashSet<OWLAxiom>();
                for(SWRLRule rule : ontology.getRules()) {
                    if (isDisplayed(rule)) {
                        for(SWRLAtom atom : rule.getHead()) {
                            if(atom.getPredicate().equals(property)) {
                                rules.add(rule);
                                writeSection(RULE, rules, "�", true, ontology);
                                break;
                            }
                        }
                    }
                }

            }

        }
        writeEntitySectionEnd();
        return axioms;
    }


    public Set<OWLAxiom> write(OWLDataProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(DATA_PROPERTY, property));
        if (!isFiltered(AxiomType.FUNCTIONAL_DATA_PROPERTY)) {
            for (OWLOntology ontology : getOntologies()) {
                List<String> characteristics = new ArrayList<String>();
                for(OWLAxiom ax : ontology.getFunctionalDataPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(FUNCTIONAL.toString());
                        axioms.add(ax);
                    }
                }
                writeSection(CHARACTERISTICS, new LinkedHashSet(characteristics), ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLClassExpression> domains = new TreeSet<OWLClassExpression>();
                for(OWLDataPropertyDomainAxiom ax : ontology.getDataPropertyDomainAxioms(property)) {
                    if(isDisplayed(ax)) {
                        domains.add(ax.getDomain());
                        axioms.add(ax);
                    }
                }
                writeSection(DOMAIN, domains, ",", true, ontology);

            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_RANGE)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLDataRange> ranges = new TreeSet<OWLDataRange>();
                for(OWLDataPropertyRangeAxiom ax : ontology.getDataPropertyRangeAxioms(property)) {
                    if(isDisplayed(ax)) {
                        ranges.add(ax.getRange());
                        axioms.add(ax);
                    }
                }
                writeSection(RANGE, ranges, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUB_DATA_PROPERTY)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLDataPropertyExpression> supers = new TreeSet<OWLDataPropertyExpression>();
                for(OWLSubDataPropertyOfAxiom ax : ontology.getDataSubPropertyAxiomsForSubProperty(property)) {
                    if(isDisplayed(ax)) {
                        supers.add(ax.getSuperProperty());
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, supers, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLDataPropertyExpression> props = new TreeSet<OWLDataPropertyExpression>();
                for(OWLEquivalentDataPropertiesAxiom ax : ontology.getEquivalentDataPropertiesAxioms(property)) {
                    if(isDisplayed(ax)) {
                        props.addAll(ax.getProperties());
                        axioms.add(ax);
                    }
                }
                props.remove(property);
                writeSection(EQUIVALENT_TO, props, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLDataPropertyExpression> props = new TreeSet<OWLDataPropertyExpression>();
                for(OWLDisjointDataPropertiesAxiom ax : ontology.getDisjointDataPropertiesAxioms(property)) {
                    if(ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        props.addAll(ax.getProperties());
                        axioms.add(ax);
                    }
                }
                props.remove(property);
                writeSection(DISJOINT_WITH, props, ",", true, ontology);
            }
        }
        if(!isFiltered(AxiomType.SWRL_RULE)) {
            for(OWLOntology ontology : getOntologies()) {
                Set<OWLAxiom> rules = new HashSet<OWLAxiom>();
                for(SWRLRule rule : ontology.getRules()) {
                    if (isDisplayed(rule)) {
                        for(SWRLAtom atom : rule.getHead()) {
                            if(atom.getPredicate().equals(property)) {
                                writeSection(RULE, rules, "�", true, ontology);
                                break;
                            }
                        }
                    }
                }
            }

        }
        writeEntitySectionEnd();
        return axioms;
    }


    public Set<OWLAxiom> write(OWLIndividual individual) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(INDIVIDUAL, individual));
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLClassExpression> expressions = new TreeSet<OWLClassExpression>();
                for(OWLClassAssertionAxiom ax : ontology.getClassAssertionAxioms(individual)) {
                    if(isDisplayed(ax)) {
                        expressions.add(ax.getClassExpression());
                        axioms.add(ax);
                    }
                }
                writeSection(TYPES, expressions, ",", true, ontology);
            }
        }
        for (OWLOntology ontology : getOntologies()) {
            // Facts - messy!
            Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectMap = new TreeMap<OWLObjectPropertyExpression, Set<OWLIndividual>>();
            if (!isFiltered(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
                for(OWLObjectPropertyAssertionAxiom ax : ontology.getObjectPropertyAssertionAxioms(individual)) {
                    if(isDisplayed(ax)) {
                        axioms.add((ax));
                        Set<OWLIndividual> inds = objectMap.get(ax.getProperty());
                        if(inds == null) {
                            inds = new TreeSet<OWLIndividual>();
                            objectMap.put(ax.getProperty(), inds);
                        }
                        inds.add(ax.getObject());
                    }
                }
            }
            Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataMap = new TreeMap<OWLDataPropertyExpression, Set<OWLLiteral>>();
            if (!isFiltered(AxiomType.DATA_PROPERTY_ASSERTION)) {
                for(OWLDataPropertyAssertionAxiom ax : ontology.getDataPropertyAssertionAxioms(individual)) {
                    if(isDisplayed(ax)) {
                        axioms.add((ax));
                        Set<OWLLiteral> objs = dataMap.get(ax.getProperty());
                        if(objs == null) {
                            objs = new TreeSet<OWLLiteral>();
                            dataMap.put(ax.getProperty(), objs);
                        }
                        objs.add(ax.getObject());
                    }
                }
            }
            // Negative facts
            Map<OWLObjectPropertyExpression, Set<OWLIndividual>> negObjectMap = new TreeMap<OWLObjectPropertyExpression, Set<OWLIndividual>>();
            if (!isFiltered(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION)) {
                for(OWLNegativeObjectPropertyAssertionAxiom ax : ontology.getNegativeObjectPropertyAssertionAxioms(individual)) {
                    if(isDisplayed(ax)) {
                        axioms.add((ax));
                        Set<OWLIndividual> inds = objectMap.get(ax.getProperty());
                        if(inds == null) {
                            inds = new TreeSet<OWLIndividual>();
                            negObjectMap.put(ax.getProperty(), inds);
                        }
                        inds.add(ax.getObject());
                    }
                }
            }
            Map<OWLDataPropertyExpression, Set<OWLLiteral>> negDataMap = new TreeMap<OWLDataPropertyExpression, Set<OWLLiteral>>();
            if (!isFiltered(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION)) {
                for(OWLNegativeDataPropertyAssertionAxiom ax : ontology.getNegativeDataPropertyAssertionAxioms(individual)) {
                    if(isDisplayed(ax)) {
                        axioms.add((ax));
                        Set<OWLLiteral> objs = negDataMap.get(ax.getProperty());
                        if(objs == null) {
                            objs = new TreeSet<OWLLiteral>();
                            negDataMap.put(ax.getProperty(), objs);
                        }
                        objs.add(ax.getObject());
                    }
                }
            }

            if (!objectMap.isEmpty() || !dataMap.isEmpty() || !negObjectMap.isEmpty() || !negDataMap.isEmpty()) {
                writeSection(FACTS);
                writeSpace();
                writeOntologiesList(ontology);
                incrementTab(4);
                writeNewLine();
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
                for(OWLSameIndividualAxiom ax : ontology.getSameIndividualAxioms(individual)) {
                    if(isDisplayed(ax)) {
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
                for(OWLDifferentIndividualsAxiom ax : ontology.getDifferentIndividualAxioms(individual)) {
                    if(ax.getIndividuals().size() == 2 && isDisplayed(ax)) {
                        inds.addAll(ax.getIndividuals());
                        axioms.add(ax);
                    }
                }
                inds.remove(individual);
                writeSection(DIFFERENT_FROM, inds, ",", true, ontology);
            }
        }
        writeEntitySectionEnd();
        return axioms;
    }

    public Set<OWLAxiom> write(OWLDatatype datatype) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(DATATYPE, datatype));
        if (!isFiltered(AxiomType.DATATYPE_DEFINITION)) {
            for(OWLOntology ontology : getOntologies()) {
                Set<OWLDataRange> dataRanges = new TreeSet<OWLDataRange>();
                for(OWLDatatypeDefinitionAxiom ax : ontology.getDatatypeDefinitions(datatype)) {
                    if(isDisplayed(ax)) {
                        axioms.add(ax);
                        dataRanges.add(ax.getDataRange());
                    }
                }
                writeSection(EQUIVALENT_TO, dataRanges, ",", true, ontology);
            }
        }
        writeEntitySectionEnd();
        return axioms;
    }

    public Set<OWLAxiom> write(SWRLRule rule) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>(1);
        for (OWLOntology ontology : getOntologies()) {
            if (ontology.containsAxiom(rule)) {
                writeSection(RULE, Collections.singleton(rule), "�", true, ontology);
                axioms.add(rule);
            }
        }
        return axioms;
    }

    public Set<OWLAxiom> write(OWLAnnotationProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(ANNOTATION_PROPERTY, property));
        if(!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for(OWLOntology ont : getOntologies()) {
                Set<OWLAnnotation> annos = new TreeSet<OWLAnnotation>();
                for(OWLAnnotationAssertionAxiom ax : ont.getAnnotationAssertionAxioms(property)) {
                    if (isDisplayed(ax)) {
                        annos.add(ax.getAnnotation());
                    }
                }
                writeSection(ANNOTATIONS, annos, ",", true, ont);
            }
        }
        if(!isFiltered(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            for(OWLOntology ont : getOntologies()) {
                Set<OWLAnnotationProperty> props = new TreeSet<OWLAnnotationProperty>();
                for(OWLSubAnnotationPropertyOfAxiom ax : ont.getSubAnnotationPropertyOfAxioms(property)) {
                    if (isDisplayed(ax)) {
                        props.add(ax.getSuperProperty());
                    }
                }
                writeSection(SUB_PROPERTY_OF, props, ",", true, ont);
            }
        }
        if(!isFiltered(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            for(OWLOntology ont : getOntologies()) {
                Set<IRI> iris = new TreeSet<IRI>();
                for(OWLAnnotationPropertyDomainAxiom ax : ont.getAnnotationPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        iris.add(ax.getDomain());
                    }
                }
                writeSection(DOMAIN, iris, ",", true, ont);
            }
        }
        if(!isFiltered(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            for(OWLOntology ont : getOntologies()) {
                Set<IRI> iris = new TreeSet<IRI>();
                for(OWLAnnotationPropertyRangeAxiom ax : ont.getAnnotationPropertyRangeAxioms(property)) {
                    if (isDisplayed(ax)) {
                        iris.add(ax.getRange());
                    }
                }
                writeSection(RANGE, iris, ",", true, ont);
            }
        }
        writeEntitySectionEnd();
        return axioms;
    }


    private <K extends OWLObject, V extends OWLObject> void writeFacts(Map<K, Set<V>> relationshipsMap,
                                                                       boolean negative) {
        for (Iterator<K> propIt = relationshipsMap.keySet().iterator(); propIt.hasNext();) {
            K prop = propIt.next();
            for (Iterator<? extends OWLObject> it = relationshipsMap.get(prop).iterator(); it.hasNext();) {
                if (negative) {
                    write(NOT);
                    writeSpace();
                }
                prop.accept(this);
                writeSpace();
                writeSpace();
                OWLObject ind = it.next();
                ind.accept(this);
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
        writeSection(keyword);
        entity.accept(this);
        writeNewLine();
        incrementTab(4);
        writeNewLine();
        if (entity instanceof OWLEntity) {
            return writeAnnotations((OWLEntity) entity);
        }
        else if(entity instanceof OWLAnonymousIndividual) {
            return writeAnnotations((OWLAnonymousIndividual) entity);
        }
        return Collections.emptySet();
    }


    public Set<OWLAnnotationAssertionAxiom> writeAnnotations(OWLAnnotationSubject entity) {
        Set<OWLAnnotationAssertionAxiom> axioms = new HashSet<OWLAnnotationAssertionAxiom>();
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLAnnotation> annos = new TreeSet<OWLAnnotation>();
                for(OWLAnnotationAssertionAxiom ax : ontology.getAnnotationAssertionAxioms(entity)) {
                    if(isDisplayed(ax)) {
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


    public void writeSection(ManchesterOWLSyntax keyword, Set<? extends Object> content, String delimeter,
                             boolean newline, OWLOntology... ontologies) {

        if (!content.isEmpty()) {
            writeSection(keyword);
            writeOntologiesList(ontologies);
            incrementTab(4);
            writeNewLine();
            for (Iterator<? extends Object> it = content.iterator(); it.hasNext();) {
                Object obj = it.next();
                if (obj instanceof OWLObject) {
                    ((OWLObject) obj).accept(this);
                } else {
                    write(obj.toString());
                }
                if (it.hasNext()) {
                    write(delimeter);
                    if (newline) {
                        writeNewLine();
                    }
                }
            }
            popTab();
            writeNewLine();
            writeNewLine();
        }
    }

    private void writeOntologiesList(OWLOntology... ontologies) {
        if(!renderExtensions) {
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


}
