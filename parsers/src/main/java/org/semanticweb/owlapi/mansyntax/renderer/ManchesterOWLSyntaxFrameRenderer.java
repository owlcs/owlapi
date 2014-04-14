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
package org.semanticweb.owlapi.mansyntax.renderer;

import static org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.*;
import static org.semanticweb.owlapi.model.Imports.EXCLUDED;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax;
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
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
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
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.OWLAxiomFilter;
import org.semanticweb.owlapi.util.OWLObjectComparator;
import org.semanticweb.owlapi.util.OntologyIRIShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * The Class ManchesterOWLSyntaxFrameRenderer.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class ManchesterOWLSyntaxFrameRenderer extends
        ManchesterOWLSyntaxObjectRenderer implements OWLEntityVisitor {

    static class SectionMap<O, V extends OWLAxiom> {

        private Map<O, Set<V>> object2Axioms = new LinkedHashMap<O, Set<V>>();

        /** @return true if empty */
        public boolean isNotEmpty() {
            return !object2Axioms.isEmpty();
        }

        /**
         * @param o
         *        key
         * @param forAxiom
         *        axiom to add
         */
        public void put(O o, V forAxiom) {
            Set<V> axioms = object2Axioms.get(o);
            if (axioms == null) {
                axioms = new LinkedHashSet<V>();
                object2Axioms.put(o, axioms);
            }
            axioms.add(forAxiom);
        }

        /**
         * @param o
         *        object to remove
         */
        public void remove(O o) {
            object2Axioms.remove(o);
        }

        /** @return sections */
        public Collection<O> getSectionObjects() {
            return object2Axioms.keySet();
        }

        /**
         * @param sectionObject
         *        sectionObject
         * @return annotations for objects
         */
        public Set<Set<OWLAnnotation>> getAnnotationsForSectionObject(
                Object sectionObject) {
            Collection<V> axioms = object2Axioms.get(sectionObject);
            if (axioms == null) {
                return new HashSet<Set<OWLAnnotation>>();
            }
            Set<Set<OWLAnnotation>> annos = new HashSet<Set<OWLAnnotation>>();
            for (OWLAxiom ax : axioms) {
                annos.add(ax.getAnnotations());
            }
            return annos;
        }
    }

    /** The ontologies. */
    private Set<OWLOntology> ontologies;
    /** The short form provider. */
    private OntologyIRIShortFormProvider shortFormProvider = new OntologyIRIShortFormProvider();
    /** The filtered axiom types. */
    private Set<AxiomType<?>> filteredAxiomTypes = new HashSet<AxiomType<?>>();
    /** The render extensions. */
    private boolean renderExtensions = false;
    /** The listeners. */
    private List<RendererListener> listeners = new ArrayList<RendererListener>();
    /** The axiom filter. */
    private OWLAxiomFilter axiomFilter = new OWLAxiomFilter() {

        @Override
        public boolean passes(OWLAxiom axiom) {
            return true;
        }
    };
    /** The rendering director. */
    private RenderingDirector renderingDirector = new DefaultRenderingDirector();
    private final OWLObjectComparator owlObjectComparator;

    /**
     * Instantiates a new manchester owl syntax frame renderer.
     * 
     * @param ontology
     *        the ontology
     * @param writer
     *        the writer
     * @param entityShortFormProvider
     *        the entity short form provider
     */
    public ManchesterOWLSyntaxFrameRenderer(OWLOntology ontology,
            Writer writer, ShortFormProvider entityShortFormProvider) {
        this(Collections.singleton(ontology), writer, entityShortFormProvider);
    }

    /**
     * Instantiates a new manchester owl syntax frame renderer.
     * 
     * @param ontologies
     *        the ontologies
     * @param writer
     *        the writer
     * @param entityShortFormProvider
     *        the entity short form provider
     */
    public ManchesterOWLSyntaxFrameRenderer(Set<OWLOntology> ontologies,
            Writer writer, ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
        this.ontologies = new LinkedHashSet<OWLOntology>(ontologies);
        owlObjectComparator = new OWLObjectComparator(entityShortFormProvider);
    }

    /**
     * Sets the rendering director.
     * 
     * @param renderingDirector
     *        the new rendering director
     */
    public void setRenderingDirector(RenderingDirector renderingDirector) {
        this.renderingDirector = renderingDirector;
    }

    /**
     * @param shortFormProvider
     *        short form provider to be used
     */
    public void setOntologyIRIShortFormProvider(
            OntologyIRIShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

    /**
     * Adds the renderer listener.
     * 
     * @param listener
     *        the listener
     */
    public void addRendererListener(RendererListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the renderer listener.
     * 
     * @param listener
     *        the listener
     */
    public void removeRendererListener(RendererListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sets the axiom filter.
     * 
     * @param axiomFilter
     *        the new axiom filter
     */
    public void setAxiomFilter(OWLAxiomFilter axiomFilter) {
        this.axiomFilter = axiomFilter;
    }

    /** Clear filtered axiom types. */
    public void clearFilteredAxiomTypes() {
        filteredAxiomTypes.clear();
    }

    /**
     * Adds the filtered axiom type.
     * 
     * @param axiomType
     *        the axiom type
     */
    public void addFilteredAxiomType(AxiomType<?> axiomType) {
        filteredAxiomTypes.add(axiomType);
    }

    /**
     * Sets the render extensions.
     * 
     * @param renderExtensions
     *        the new render extensions
     */
    public void setRenderExtensions(boolean renderExtensions) {
        this.renderExtensions = renderExtensions;
    }

    /**
     * Write ontology.
     * 
     * @throws OWLRendererException
     *         the oWL renderer exception
     */
    public void writeOntology() throws OWLRendererException {
        if (ontologies.size() != 1) {
            throw new OWLRuntimeException("Can only render one ontology");
        }
        OWLOntology ontology = ontologies.iterator().next();
        writePrefixMap();
        writeNewLine();
        writeOntologyHeader(ontology);
        for (OWLAnnotationProperty prop : ontology
                .getAnnotationPropertiesInSignature(EXCLUDED)) {
            write(prop);
        }
        for (OWLDatatype datatype : ontology.getDatatypesInSignature()) {
            write(datatype);
        }
        for (OWLObjectProperty prop : ontology.getObjectPropertiesInSignature()) {
            write(prop);
            OWLObjectPropertyExpression invProp = prop.getInverseProperty();
            if (!ontology.getAxioms(invProp, EXCLUDED).isEmpty()) {
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
        for (OWLAnonymousIndividual ind : ontology
                .getReferencedAnonymousIndividuals(EXCLUDED)) {
            write(ind);
        }
        // Nary disjoint classes axioms
        event = new RendererEvent(this, ontology);
        for (OWLDisjointClassesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                map.put(ax.getClassExpressions(), ax);
                writeSection(DISJOINT_CLASSES, map, ",", false, ontology);
            }
        }
        // Nary equivalent classes axioms
        for (OWLEquivalentClassesAxiom ax : ontology
                .getAxioms(AxiomType.EQUIVALENT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                map.put(ax.getClassExpressions(), ax);
                writeSection(EQUIVALENT_CLASSES, map, ",", false, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointObjectPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                map.put(ax.getProperties(), ax);
                writeSection(DISJOINT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary equivalent properties
        for (OWLEquivalentObjectPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                map.put(ax.getProperties(), ax);
                writeSection(EQUIVALENT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointDataPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                map.put(ax.getProperties(), ax);
                writeSection(DISJOINT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary equivalent properties
        for (OWLEquivalentDataPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                map.put(ax.getProperties(), ax);
                writeSection(EQUIVALENT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary different individuals
        for (OWLDifferentIndividualsAxiom ax : ontology
                .getAxioms(AxiomType.DIFFERENT_INDIVIDUALS)) {
            if (ax.getIndividuals().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                map.put(ax.getIndividuals(), ax);
                writeSection(DIFFERENT_INDIVIDUALS, map, ",", false, ontology);
            }
        }
        for (SWRLRule rule : ontology.getAxioms(AxiomType.SWRL_RULE)) {
            writeSection(RULE, Collections.singleton(rule), ", ", false);
        }
        flush();
    }

    /**
     * Write ontology header.
     * 
     * @param ontology
     *        the ontology
     */
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
                writeFullURI(ontology.getOntologyID().getVersionIRI()
                        .toString());
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
            writeFullURI(decl.getIRI().toString());
            writeNewLine();
            fireSectionRenderingFinished(IMPORT.toString());
        }
        writeNewLine();
        writeSection(ANNOTATIONS, ontology.getAnnotations(), ",", true);
        fireFrameRenderingFinished(ONTOLOGY.toString());
    }

    /** Write prefix map. */
    public void writePrefixMap() {
        ShortFormProvider sfp = getShortFormProvider();
        if (!(sfp instanceof ManchesterOWLSyntaxPrefixNameShortFormProvider)) {
            return;
        }
        ManchesterOWLSyntaxPrefixNameShortFormProvider prov = (ManchesterOWLSyntaxPrefixNameShortFormProvider) sfp;
        Map<String, String> prefixMap = new HashMap<String, String>();
        PrefixManager prefixManager = prov.getPrefixManager();
        for (String prefixName : prefixManager.getPrefixName2PrefixMap()
                .keySet()) {
            String prefix = prefixManager.getPrefix(prefixName);
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

    /**
     * Write full uri.
     * 
     * @param uri
     *        the uri
     */
    public void writeFullURI(String uri) {
        write("<");
        write(uri);
        write(">");
    }

    /**
     * Checks if is filtered.
     * 
     * @param axiomType
     *        the axiom type
     * @return true, if is filtered
     */
    public boolean isFiltered(AxiomType<?> axiomType) {
        return filteredAxiomTypes.contains(axiomType);
    }

    /**
     * Checks if is displayed.
     * 
     * @param axiom
     *        the axiom
     * @return true, if is displayed
     */
    public boolean isDisplayed(OWLAxiom axiom) {
        if (axiom == null) {
            return false;
        }
        return axiomFilter.passes(axiom);
    }

    /**
     * Write frame.
     * 
     * @param entity
     *        the entity
     * @return the sets the
     */
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

    /**
     * @param cls
     *        the cls
     * @return the sets the
     */
    public Set<OWLAxiom> write(OWLClass cls) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(CLASS, cls));
        if (!isFiltered(AxiomType.EQUIVALENT_CLASSES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> equivalentClasses = new SectionMap<Object, OWLAxiom>();
                for (OWLEquivalentClassesAxiom ax : ontology
                        .getEquivalentClassesAxioms(cls)) {
                    if (ax.getClassExpressions().size() == 2 && isDisplayed(ax)) {
                        for (OWLClassExpression equivCls : ax
                                .getClassExpressionsMinus(cls)) {
                            equivalentClasses.put(equivCls, ax);
                        }
                        axioms.add(ax);
                    }
                }
                equivalentClasses.remove(cls);
                writeSection(EQUIVALENT_TO, equivalentClasses, ",", true,
                        ontology);
            }
        }
        if (!isFiltered(AxiomType.SUBCLASS_OF)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> superclasses = new SectionMap<Object, OWLAxiom>();
                for (OWLSubClassOfAxiom ax : ontology
                        .getSubClassAxiomsForSubClass(cls)) {
                    if (isDisplayed(ax)) {
                        superclasses.put(ax.getSuperClass(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(SUBCLASS_OF, superclasses, ",", true, ontology);
            }
            if (renderExtensions) {
                for (OWLOntology ont : ontologies) {
                    SectionMap<Object, OWLAxiom> subClasses = new SectionMap<Object, OWLAxiom>();
                    for (OWLSubClassOfAxiom ax : ont
                            .getSubClassAxiomsForSuperClass(cls)) {
                        if (isDisplayed(ax)) {
                            subClasses.put(ax.getSubClass(), ax);
                            axioms.add(ax);
                        }
                    }
                    writeSection(SUPERCLASS_OF, subClasses, ",", true, ont);
                }
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_UNION)) {
            for (OWLOntology ontology : ontologies) {
                // Handling of nary in frame style
                for (OWLDisjointUnionAxiom ax : ontology
                        .getDisjointUnionAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        Collection<OWLClassExpression> allDisjointClasses = sortedCollection(ax
                                .getClassExpressions());
                        axioms.add(ax);
                        writeSection(DISJOINT_UNION_OF, allDisjointClasses,
                                ", ", false, ontology);
                    }
                }
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_CLASSES)) {
            for (OWLOntology ontology : ontologies) {
                Set<OWLAxiom> pairwiseDisjointClassesAxioms = new HashSet<OWLAxiom>();
                SectionMap<Object, OWLAxiom> disjointClasses = new SectionMap<Object, OWLAxiom>();
                for (OWLDisjointClassesAxiom ax : ontology
                        .getDisjointClassesAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        if (ax.getClassExpressions().size() == 2) {
                            pairwiseDisjointClassesAxioms.add(ax);
                            OWLClassExpression disjointWith = ax
                                    .getClassExpressionsMinus(cls).iterator()
                                    .next();
                            disjointClasses.put(disjointWith, ax);
                        }
                        axioms.add(ax);
                    }
                }
                writeSection(DISJOINT_WITH, disjointClasses, ", ", false,
                        ontology);
                if (renderExtensions) {
                    // Handling of nary in frame style
                    for (OWLDisjointClassesAxiom ax : ontology
                            .getDisjointClassesAxioms(cls)) {
                        if (isDisplayed(ax)
                                && ax.getClassExpressions().size() > 2) {
                            Collection<OWLClassExpression> allDisjointClasses = sortedCollection(ax
                                    .getClassExpressions());
                            axioms.add(ax);
                            writeSection(DISJOINT_CLASSES, allDisjointClasses,
                                    ", ", false, ontology);
                        }
                    }
                }
            }
        }
        if (!isFiltered(AxiomType.HAS_KEY)) {
            for (OWLOntology ontology : ontologies) {
                for (OWLHasKeyAxiom ax : ontology.getHasKeyAxioms(cls)) {
                    if (isDisplayed(ax)) {
                        SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                        map.put(ax.getPropertyExpressions(), ax);
                        writeSection(HAS_KEY, map, ", ", true, ontology);
                    }
                }
            }
        }
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> individuals = new SectionMap<Object, OWLAxiom>();
                for (OWLClassAssertionAxiom ax : ontology
                        .getClassAssertionAxioms(cls)) {
                    if (isDisplayed(ax)
                            && (renderExtensions || ax.getIndividual()
                                    .isAnonymous())) {
                        individuals.put(ax.getIndividual(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(INDIVIDUALS, individuals, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            for (OWLOntology ontology : ontologies) {
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

    /**
     * Write entity section end.
     * 
     * @param type
     *        the type
     */
    protected void writeEntitySectionEnd(String type) {
        fireFrameRenderingFinished(type);
        popTab();
        writeNewLine();
    }

    /**
     * @param property
     *        the property
     * @return the sets the
     */
    public Set<OWLAxiom> write(OWLObjectPropertyExpression property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(OBJECT_PROPERTY, property));
        if (!isFiltered(AxiomType.SUB_OBJECT_PROPERTY)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> properties = new SectionMap<Object, OWLAxiom>();
                for (OWLSubObjectPropertyOfAxiom ax : ontology
                        .getObjectSubPropertyAxiomsForSubProperty(property)) {
                    if (isDisplayed(ax)) {
                        properties.put(ax.getSuperProperty(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, properties, ",", true, ontology);
            }
            if (renderExtensions) {
                for (OWLOntology ontology : ontologies) {
                    SectionMap<Object, OWLAxiom> properties = new SectionMap<Object, OWLAxiom>();
                    for (OWLSubObjectPropertyOfAxiom ax : ontology
                            .getObjectSubPropertyAxiomsForSuperProperty(property)) {
                        if (isDisplayed(ax)) {
                            properties.put(ax.getSubProperty(), ax);
                            axioms.add(ax);
                        }
                    }
                    writeSection(SUPER_PROPERTY_OF, properties, ",", true,
                            ontology);
                }
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> properties = new SectionMap<Object, OWLAxiom>();
                for (OWLEquivalentObjectPropertiesAxiom ax : ontology
                        .getEquivalentObjectPropertiesAxioms(property)) {
                    if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                        Set<OWLObjectPropertyExpression> props = ax
                                .getPropertiesMinus(property);
                        properties.put(props.iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(EQUIVALENT_TO, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> properties = new SectionMap<Object, OWLAxiom>();
                for (OWLDisjointObjectPropertiesAxiom ax : ontology
                        .getDisjointObjectPropertiesAxioms(property)) {
                    if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        Set<OWLObjectPropertyExpression> props = ax
                                .getPropertiesMinus(property);
                        properties.put(props.iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(DISJOINT_WITH, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
            for (OWLOntology ontology : ontologies) {
                for (OWLSubPropertyChainOfAxiom ax : ontology
                        .getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                    if (ax.getSuperProperty().equals(property)
                            && isDisplayed(ax)) {
                        SectionMap<Object, OWLAxiom> map = new SectionMap<Object, OWLAxiom>();
                        map.put(ax.getPropertyChain(), ax);
                        writeSection(SUB_PROPERTY_CHAIN, map, " o ", false,
                                ontology);
                        axioms.add(ax);
                    }
                }
            }
        }
        for (OWLOntology ontology : ontologies) {
            SectionMap<Object, OWLAxiom> characteristics = new SectionMap<Object, OWLAxiom>();
            if (!isFiltered(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLFunctionalObjectPropertyAxiom ax : ontology
                        .getFunctionalObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getInverseFunctionalObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(INVERSE_FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getSymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(SYMMETRIC.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getTransitiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(TRANSITIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.REFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getReflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(REFLEXIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getIrreflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(IRREFLEXIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getAsymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(ASYMMETRIC.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            writeSection(CHARACTERISTICS, characteristics, ",", true, ontology);
        }
        if (!isFiltered(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> expressions = new SectionMap<Object, OWLAxiom>();
                for (OWLObjectPropertyDomainAxiom ax : ontology
                        .getObjectPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        expressions.put(ax.getDomain(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(DOMAIN, expressions, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.OBJECT_PROPERTY_RANGE)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> expressions = new SectionMap<Object, OWLAxiom>();
                for (OWLObjectPropertyRangeAxiom ax : ontology
                        .getObjectPropertyRangeAxioms(property)) {
                    if (isDisplayed(ax)) {
                        expressions.put(ax.getRange(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(RANGE, expressions, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.INVERSE_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : ontologies) {
                Collection<OWLObjectPropertyExpression> properties = sortedCollection();
                for (OWLInverseObjectPropertiesAxiom ax : ontology
                        .getInverseObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        if (ax.getFirstProperty().equals(property)) {
                            properties.add(ax.getSecondProperty());
                        } else {
                            properties.add(ax.getFirstProperty());
                        }
                        axioms.add(ax);
                    }
                }
                writeSection(INVERSE_OF, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            for (OWLOntology ontology : ontologies) {
                Set<OWLAxiom> rules = new HashSet<OWLAxiom>();
                for (SWRLRule rule : ontology.getAxioms(AxiomType.SWRL_RULE)) {
                    if (isDisplayed(rule)) {
                        for (SWRLAtom atom : rule.getHead()) {
                            if (atom.getPredicate().equals(property)) {
                                rules.add(rule);
                                writeSection(RULE, rules, ",", true, ontology);
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

    /**
     * @param property
     *        the property
     * @return the sets the
     */
    public Set<OWLAxiom> write(OWLDataProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(DATA_PROPERTY, property));
        if (!isFiltered(AxiomType.FUNCTIONAL_DATA_PROPERTY)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> characteristics = new SectionMap<Object, OWLAxiom>();
                for (OWLAxiom ax : ontology
                        .getFunctionalDataPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.put(FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(CHARACTERISTICS, characteristics, ",", true,
                        ontology);
            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> domains = new SectionMap<Object, OWLAxiom>();
                for (OWLDataPropertyDomainAxiom ax : ontology
                        .getDataPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        domains.put(ax.getDomain(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(DOMAIN, domains, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_RANGE)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> ranges = new SectionMap<Object, OWLAxiom>();
                for (OWLDataPropertyRangeAxiom ax : ontology
                        .getDataPropertyRangeAxioms(property)) {
                    if (isDisplayed(ax)) {
                        ranges.put(ax.getRange(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(RANGE, ranges, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUB_DATA_PROPERTY)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> supers = new SectionMap<Object, OWLAxiom>();
                for (OWLSubDataPropertyOfAxiom ax : ontology
                        .getDataSubPropertyAxiomsForSubProperty(property)) {
                    if (isDisplayed(ax)) {
                        supers.put(ax.getSuperProperty(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, supers, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> props = new SectionMap<Object, OWLAxiom>();
                for (OWLEquivalentDataPropertiesAxiom ax : ontology
                        .getEquivalentDataPropertiesAxioms(property)) {
                    if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                        props.put(ax.getPropertiesMinus(property).iterator()
                                .next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(EQUIVALENT_TO, props, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> props = new SectionMap<Object, OWLAxiom>();
                for (OWLDisjointDataPropertiesAxiom ax : ontology
                        .getDisjointDataPropertiesAxioms(property)) {
                    if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        props.put(ax.getPropertiesMinus(property).iterator()
                                .next(), ax);
                        axioms.add(ax);
                    }
                }
                props.remove(property);
                writeSection(DISJOINT_WITH, props, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            for (OWLOntology ontology : ontologies) {
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

    /**
     * @param individual
     *        the individual
     * @return the sets the
     */
    public Set<OWLAxiom> write(OWLIndividual individual) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(INDIVIDUAL, individual));
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> expressions = new SectionMap<Object, OWLAxiom>();
                for (OWLClassAssertionAxiom ax : ontology
                        .getClassAssertionAxioms(individual)) {
                    if (isDisplayed(ax)) {
                        expressions.put(ax.getClassExpression(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(TYPES, expressions, ",", true, ontology);
            }
        }
        for (OWLOntology ontology : ontologies) {
            List<OWLPropertyAssertionAxiom<?, ?>> assertions = new ArrayList<OWLPropertyAssertionAxiom<?, ?>>();
            assertions.addAll(ontology
                    .getObjectPropertyAssertionAxioms(individual));
            assertions.addAll(ontology
                    .getNegativeObjectPropertyAssertionAxioms(individual));
            assertions.addAll(ontology
                    .getDataPropertyAssertionAxioms(individual));
            assertions.addAll(ontology
                    .getNegativeDataPropertyAssertionAxioms(individual));
            if (!assertions.isEmpty()) {
                fireSectionRenderingPrepared(FACTS.toString());
                writeSection(FACTS);
                writeSpace();
                writeOntologiesList(ontology);
                incrementTab(1);
                writeNewLine();
                fireSectionRenderingStarted(FACTS.toString());
                for (Iterator<OWLPropertyAssertionAxiom<?, ?>> it = assertions
                        .iterator(); it.hasNext();) {
                    OWLPropertyAssertionAxiom<?, ?> ax = it.next();
                    fireSectionItemPrepared(FACTS.toString());
                    Set<OWLAnnotation> annos = ax.getAnnotations();
                    if (!annos.isEmpty()) {
                        writeAnnotations(annos);
                        pushTab(getIndent() + 1);
                    }
                    if (ax instanceof OWLNegativeDataPropertyAssertionAxiom
                            || ax instanceof OWLNegativeObjectPropertyAssertionAxiom) {
                        write(NOT);
                        writeSpace();
                    }
                    ax.getProperty().accept(this);
                    writeSpace();
                    writeSpace();
                    ax.getObject().accept(this);
                    if (!annos.isEmpty()) {
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
            for (OWLOntology ontology : ontologies) {
                Collection<OWLIndividual> inds = sortedCollection();
                for (OWLSameIndividualAxiom ax : ontology
                        .getSameIndividualAxioms(individual)) {
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
            for (OWLOntology ontology : ontologies) {
                Collection<OWLIndividual> inds = sortedCollection();
                Collection<OWLDifferentIndividualsAxiom> nary = sortedCollection();
                for (OWLDifferentIndividualsAxiom ax : ontology
                        .getDifferentIndividualAxioms(individual)) {
                    if (ax.getIndividuals().size() == 2 && isDisplayed(ax)) {
                        inds.addAll(ax.getIndividuals());
                        axioms.add(ax);
                    } else {
                        nary.add(ax);
                    }
                }
                inds.remove(individual);
                writeSection(DIFFERENT_FROM, inds, ",", true, ontology);
                if (renderExtensions) {
                    for (OWLDifferentIndividualsAxiom ax : nary) {
                        writeSection(DIFFERENT_INDIVIDUALS,
                                ax.getIndividuals(), ", ", false, ontology);
                    }
                }
            }
        }
        writeEntitySectionEnd(INDIVIDUAL.toString());
        return axioms;
    }

    /**
     * @param datatype
     *        the datatype
     * @return the sets the
     */
    public Set<OWLAxiom> write(OWLDatatype datatype) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(DATATYPE, datatype));
        if (!isFiltered(AxiomType.DATATYPE_DEFINITION)) {
            for (OWLOntology ontology : ontologies) {
                Collection<OWLDataRange> dataRanges = sortedCollection();
                for (OWLDatatypeDefinitionAxiom ax : ontology
                        .getDatatypeDefinitions(datatype)) {
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

    /**
     * @param rule
     *        the rule
     * @return written axioms
     */
    public Set<OWLAxiom> write(SWRLRule rule) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>(1);
        for (OWLOntology ontology : ontologies) {
            if (ontology.containsAxiom(rule)) {
                writeSection(RULE, Collections.singleton(rule), "", true,
                        ontology);
                axioms.add(rule);
            }
        }
        return axioms;
    }

    /**
     * @param property
     *        the property
     * @return written axioms
     */
    public Set<OWLAxiom> write(OWLAnnotationProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(ANNOTATION_PROPERTY, property));
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for (OWLOntology ont : ontologies) {
                Collection<OWLAnnotation> annos = sortedCollection();
                for (OWLAnnotationAssertionAxiom ax : ont
                        .getAnnotationAssertionAxioms(property.getIRI())) {
                    if (isDisplayed(ax)) {
                        annos.add(ax.getAnnotation());
                    }
                }
                writeSection(ANNOTATIONS, annos, ",", true, ont);
            }
        }
        if (!isFiltered(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            for (OWLOntology ont : ontologies) {
                Collection<OWLAnnotationProperty> props = sortedCollection();
                for (OWLSubAnnotationPropertyOfAxiom ax : ont
                        .getSubAnnotationPropertyOfAxioms(property)) {
                    if (isDisplayed(ax)) {
                        props.add(ax.getSuperProperty());
                    }
                }
                writeSection(SUB_PROPERTY_OF, props, ",", true, ont);
            }
        }
        if (!isFiltered(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            for (OWLOntology ont : ontologies) {
                Collection<IRI> iris = sortedCollection();
                for (OWLAnnotationPropertyDomainAxiom ax : ont
                        .getAnnotationPropertyDomainAxioms(property)) {
                    if (isDisplayed(ax)) {
                        iris.add(ax.getDomain());
                    }
                }
                writeSection(DOMAIN, iris, ",", true, ont);
            }
        }
        if (!isFiltered(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            for (OWLOntology ont : ontologies) {
                Collection<IRI> iris = sortedCollection();
                for (OWLAnnotationPropertyRangeAxiom ax : ont
                        .getAnnotationPropertyRangeAxioms(property)) {
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

    /**
     * Write entity start.
     * 
     * @param keyword
     *        the keyword
     * @param entity
     *        the entity
     * @return written axioms
     */
    private Set<OWLAnnotationAssertionAxiom> writeEntityStart(
            ManchesterOWLSyntax keyword, OWLObject entity) {
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
        } else if (entity instanceof OWLAnonymousIndividual) {
            return writeAnnotations((OWLAnonymousIndividual) entity);
        }
        return Collections.emptySet();
    }

    /**
     * Write annotations.
     * 
     * @param subject
     *        the subject
     * @return written axioms
     */
    public Set<OWLAnnotationAssertionAxiom> writeAnnotations(
            OWLAnnotationSubject subject) {
        Set<OWLAnnotationAssertionAxiom> axioms = new HashSet<OWLAnnotationAssertionAxiom>();
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> sectionMap = new SectionMap<Object, OWLAxiom>();
                for (OWLAnnotationAssertionAxiom ax : ontology
                        .getAnnotationAssertionAxioms(subject)) {
                    if (isDisplayed(ax)) {
                        axioms.add(ax);
                        sectionMap.put(ax.getAnnotation(), ax);
                    }
                }
                writeSection(ANNOTATIONS, sectionMap, ",", true, ontology);
            }
        }
        return axioms;
    }

    /**
     * Write section.
     * 
     * @param keyword
     *        the keyword
     */
    public void writeSection(ManchesterOWLSyntax keyword) {
        write("", keyword, "");
        write(":");
        writeSpace();
    }

    private void writeSection(ManchesterOWLSyntax keyword,
            SectionMap<Object, OWLAxiom> content, String delimeter,
            boolean newline, OWLOntology... ontologiesList) {
        String sec = keyword.toString();
        if (content.isNotEmpty()
                || renderingDirector.renderEmptyFrameSection(keyword,
                        ontologiesList)) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(ontologiesList);
            incrementTab(4);
            writeNewLine();
            fireSectionRenderingStarted(sec);
            for (Iterator<Object> it = content.getSectionObjects().iterator(); it
                    .hasNext();) {
                Object obj = it.next();
                Collection<Set<OWLAnnotation>> annotationSets = content
                        .getAnnotationsForSectionObject(obj);
                for (Iterator<Set<OWLAnnotation>> annosSetIt = annotationSets
                        .iterator(); annosSetIt.hasNext();) {
                    Set<OWLAnnotation> annos = annosSetIt.next();
                    fireSectionItemPrepared(sec);
                    if (!annos.isEmpty()) {
                        incrementTab(4);
                        writeNewLine();
                        write(ManchesterOWLSyntax.ANNOTATIONS.toString());
                        write(": ");
                        pushTab(getIndent() + 1);
                        for (Iterator<OWLAnnotation> annoIt = annos.iterator(); annoIt
                                .hasNext();) {
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
                    } else if (obj instanceof Collection) {
                        for (Iterator<?> listIt = ((Collection<?>) obj)
                                .iterator(); listIt.hasNext();) {
                            Object o = listIt.next();
                            if (o instanceof OWLObject) {
                                ((OWLObject) o).accept(this);
                            } else {
                                write(o.toString());
                            }
                            if (listIt.hasNext()) {
                                write(delimeter);
                                if (newline) {
                                    writeNewLine();
                                }
                            }
                        }
                    } else {
                        write(obj.toString());
                    }
                    if (annosSetIt.hasNext()) {
                        write(",");
                        writeNewLine();
                    }
                }
                if (it.hasNext()) {
                    write(delimeter);
                    fireSectionItemFinished(sec);
                    if (newline) {
                        writeNewLine();
                    }
                } else {
                    fireSectionItemFinished(sec);
                }
            }
            fireSectionRenderingFinished(sec);
            popTab();
            writeNewLine();
            writeNewLine();
        }
    }

    /**
     * Write section.
     * 
     * @param keyword
     *        the keyword
     * @param content
     *        the content
     * @param delimeter
     *        the delimeter
     * @param newline
     *        the newline
     * @param ontologiesList
     *        the ontologies list
     */
    public void writeSection(ManchesterOWLSyntax keyword,
            Collection<?> content, String delimeter, boolean newline,
            OWLOntology... ontologiesList) {
        String sec = keyword.toString();
        if (!content.isEmpty()
                || renderingDirector.renderEmptyFrameSection(keyword,
                        ontologiesList)) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(ontologiesList);
            incrementTab(4);
            writeNewLine();
            fireSectionRenderingStarted(sec);
            for (Iterator<?> it = content.iterator(); it.hasNext();) {
                Object obj = it.next();
                fireSectionItemPrepared(sec);
                if (obj instanceof OWLObject) {
                    ((OWLObject) obj).accept(this);
                } else {
                    write(obj.toString());
                }
                if (it.hasNext()) {
                    write(delimeter);
                    fireSectionItemFinished(sec);
                    if (newline) {
                        writeNewLine();
                    }
                } else {
                    fireSectionItemFinished(sec);
                }
            }
            fireSectionRenderingFinished(sec);
            popTab();
            writeNewLine();
            writeNewLine();
        }
    }

    /**
     * Write comment.
     * 
     * @param comment
     *        the comment
     * @param placeOnNewline
     *        the place on newline
     */
    public void writeComment(String comment, boolean placeOnNewline) {
        writeComment("#", comment, placeOnNewline);
    }

    /**
     * @param commentDelim
     *        the comment delim
     * @param comment
     *        the comment
     * @param placeOnNewline
     *        the place on newline
     */
    public void writeComment(String commentDelim, String comment,
            boolean placeOnNewline) {
        if (placeOnNewline) {
            writeNewLine();
        }
        write(commentDelim);
        write(comment);
        writeNewLine();
    }

    /**
     * Write ontologies list.
     * 
     * @param ontologiesList
     *        the ontologies list
     */
    private void writeOntologiesList(OWLOntology... ontologiesList) {
        if (!renderExtensions) {
            return;
        }
        if (ontologiesList.length == 0) {
            return;
        }
        write("[in ");
        int count = 0;
        for (OWLOntology ont : ontologiesList) {
            write(shortFormProvider.getShortForm(ont));
            count++;
            if (count < ontologiesList.length) {
                write(", ");
            }
        }
        write("]");
    }

    /** The event. */
    private RendererEvent event;

    /**
     * Fire frame rendering prepared.
     * 
     * @param section
     *        the section
     */
    private void fireFrameRenderingPrepared(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.frameRenderingPrepared(section, event);
        }
    }

    /**
     * Fire frame rendering started.
     * 
     * @param section
     *        the section
     */
    private void fireFrameRenderingStarted(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.frameRenderingStarted(section, event);
        }
    }

    /**
     * Fire frame rendering finished.
     * 
     * @param section
     *        the section
     */
    private void fireFrameRenderingFinished(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.frameRenderingFinished(section, event);
        }
    }

    /**
     * Fire section rendering prepared.
     * 
     * @param section
     *        the section
     */
    private void fireSectionRenderingPrepared(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionRenderingPrepared(section, event);
        }
    }

    /**
     * Fire section rendering started.
     * 
     * @param section
     *        the section
     */
    private void fireSectionRenderingStarted(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionRenderingStarted(section, event);
        }
    }

    /**
     * Fire section rendering finished.
     * 
     * @param section
     *        the section
     */
    private void fireSectionRenderingFinished(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionRenderingFinished(section, event);
        }
    }

    /**
     * Fire section item prepared.
     * 
     * @param section
     *        the section
     */
    private void fireSectionItemPrepared(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionItemPrepared(section, event);
        }
    }

    /**
     * Fire section item finished.
     * 
     * @param section
     *        the section
     */
    private void fireSectionItemFinished(String section) {
        if (listeners.isEmpty()) {
            return;
        }
        for (RendererListener listener : listeners) {
            listener.sectionItemFinished(section, event);
        }
    }

    /** The Class DefaultRenderingDirector. */
    private static class DefaultRenderingDirector implements RenderingDirector {

        /** Instantiates a new default rendering director. */
        public DefaultRenderingDirector() {}

        @Override
        public boolean renderEmptyFrameSection(
                ManchesterOWLSyntax frameSectionKeyword,
                OWLOntology... ontologies) {
            return false;
        }
    }

    private <E extends OWLObject> Collection<E> sortedCollection() {
        return new TreeSet<E>(owlObjectComparator);
    }

    private <E extends OWLObject> Collection<E> sortedCollection(
            Collection<? extends E> fromCollection) {
        Collection<E> set = sortedCollection();
        set.addAll(fromCollection);
        return set;
    }
}
