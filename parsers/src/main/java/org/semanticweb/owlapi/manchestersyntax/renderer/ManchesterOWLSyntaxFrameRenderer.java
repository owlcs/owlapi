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
package org.semanticweb.owlapi.manchestersyntax.renderer;

import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.*;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;

import java.io.Writer;
import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAxiomFilter;
import org.semanticweb.owlapi.util.OWLObjectComparator;
import org.semanticweb.owlapi.util.OntologyIRIShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;

import com.google.common.base.Optional;

/**
 * The Class ManchesterOWLSyntaxFrameRenderer.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class ManchesterOWLSyntaxFrameRenderer extends ManchesterOWLSyntaxObjectRenderer implements OWLEntityVisitor {

    class SectionMap<O, V extends OWLAxiom> {

        @Nonnull
        private final Map<O, Collection<V>> object2Axioms = new LinkedHashMap<>();

        /**
         * @return true if empty
         */
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
            Collection<V> axioms = object2Axioms.get(o);
            if (axioms == null) {
                axioms = sortedCollection();
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

        /**
         * @return sections
         */
        @Nonnull
        public Collection<O> getSectionObjects() {
            return object2Axioms.keySet();
        }

        /**
         * @param sectionObject
         *        sectionObject
         * @return annotations for objects
         */
        @Nonnull
        public Collection<Collection<OWLAnnotation>> getAnnotationsForSectionObject(Object sectionObject) {
            Collection<V> axioms = object2Axioms.get(sectionObject);
            if (axioms == null) {
                return sortedSet();
            }
            Collection<Collection<OWLAnnotation>> annos = new ArrayList<>();
            for (OWLAxiom ax : axioms) {
                annos.add(sortedCollection(ax.getAnnotations()));
            }
            return annos;
        }
    }

    /** The ontologies. */
    private final Set<OWLOntology> ontologies;
    /** The short form provider. */
    private OntologyIRIShortFormProvider shortFormProvider = new OntologyIRIShortFormProvider();
    /** The filtered axiom types. */
    @Nonnull
    private final Set<AxiomType> filteredAxiomTypes = filtered();

    private static Set<AxiomType> filtered() {
        return Collections.singleton((AxiomType) AxiomType.SWRL_RULE);
    }

    /** The render extensions. */
    private boolean renderExtensions = false;
    /** The listeners. */
    @Nonnull
    private final List<RendererListener> listeners = new ArrayList<>();
    /** The axiom filter. */
    private OWLAxiomFilter axiomFilter = new OWLAxiomFilter() {

        @Override
        public boolean passes(OWLAxiom axiom) {
            return true;
        }
    };
    /** The rendering director. */
    private RenderingDirector renderingDirector = new DefaultRenderingDirector();
    @Nonnull
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
    public ManchesterOWLSyntaxFrameRenderer(@Nonnull OWLOntology ontology, @Nonnull Writer writer,
        @Nonnull ShortFormProvider entityShortFormProvider) {
        this(CollectionFactory.createSet(ontology), writer, entityShortFormProvider);
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
    public ManchesterOWLSyntaxFrameRenderer(@Nonnull Set<OWLOntology> ontologies, Writer writer,
        @Nonnull ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
        this.ontologies = new LinkedHashSet<>(ontologies);
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
    public void setOntologyIRIShortFormProvider(OntologyIRIShortFormProvider shortFormProvider) {
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
        assert ontology != null;
        writePrefixMap();
        writeNewLine();
        writeOntologyHeader(ontology);
        for (OWLAnnotationProperty prop : sortedCollection(ontology.getAnnotationPropertiesInSignature())) {
            assert prop != null;
            write(prop);
        }
        for (OWLDatatype datatype : sortedCollection(ontology.getDatatypesInSignature())) {
            assert datatype != null;
            write(datatype);
        }
        for (OWLObjectProperty prop : sortedCollection(ontology.getObjectPropertiesInSignature())) {
            assert prop != null;
            write(prop);
            OWLObjectPropertyExpression invProp = prop.getInverseProperty();
            if (!ontology.getAxioms(invProp, EXCLUDED).isEmpty()) {
                write(invProp);
            }
        }
        for (OWLDataProperty prop : sortedCollection(ontology.getDataPropertiesInSignature())) {
            assert prop != null;
            write(prop);
        }
        for (OWLClass cls : sortedCollection(ontology.getClassesInSignature())) {
            assert cls != null;
            write(cls);
        }
        for (OWLNamedIndividual ind : sortedCollection(ontology.getIndividualsInSignature())) {
            assert ind != null;
            write(ind);
        }
        for (OWLAnonymousIndividual ind : sortedCollection(ontology.getReferencedAnonymousIndividuals(EXCLUDED))) {
            assert ind != null;
            write(ind);
        }
        // Nary disjoint classes axioms
        event = new RendererEvent(this, ontology);
        for (OWLDisjointClassesAxiom ax : sortedCollection(ontology.getAxioms(AxiomType.DISJOINT_CLASSES))) {
            if (ax.getClassExpressions().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                map.put(ax.getClassExpressions(), ax);
                writeSection(DISJOINT_CLASSES, map, ",", false, ontology);
            }
        }
        // Nary equivalent classes axioms
        for (OWLEquivalentClassesAxiom ax : sortedCollection(ontology.getAxioms(AxiomType.EQUIVALENT_CLASSES))) {
            if (ax.getClassExpressions().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                map.put(ax.getClassExpressions(), ax);
                writeSection(EQUIVALENT_CLASSES, map, ",", false, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointObjectPropertiesAxiom ax : sortedCollection(ontology.getAxioms(
            AxiomType.DISJOINT_OBJECT_PROPERTIES))) {
            if (ax.getProperties().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                map.put(ax.getProperties(), ax);
                writeSection(DISJOINT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary equivalent properties
        for (OWLEquivalentObjectPropertiesAxiom ax : sortedCollection(ontology.getAxioms(
            AxiomType.EQUIVALENT_OBJECT_PROPERTIES))) {
            if (ax.getProperties().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                map.put(ax.getProperties(), ax);
                writeSection(EQUIVALENT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointDataPropertiesAxiom ax : sortedCollection(ontology.getAxioms(
            AxiomType.DISJOINT_DATA_PROPERTIES))) {
            if (ax.getProperties().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                map.put(ax.getProperties(), ax);
                writeSection(DISJOINT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary equivalent properties
        for (OWLEquivalentDataPropertiesAxiom ax : sortedCollection(ontology.getAxioms(
            AxiomType.EQUIVALENT_DATA_PROPERTIES))) {
            if (ax.getProperties().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                map.put(ax.getProperties(), ax);
                writeSection(EQUIVALENT_PROPERTIES, map, ",", false, ontology);
            }
        }
        // Nary different individuals
        for (OWLDifferentIndividualsAxiom ax : sortedCollection(ontology.getAxioms(AxiomType.DIFFERENT_INDIVIDUALS))) {
            if (ax.getIndividuals().size() > 2) {
                SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                map.put(ax.getIndividuals(), ax);
                writeSection(DIFFERENT_INDIVIDUALS, map, ",", false, ontology);
            }
        }
        for (SWRLRule rule : sortedCollection(ontology.getAxioms(AxiomType.SWRL_RULE))) {
            @Nonnull
            Set<SWRLRule> singleton = Collections.singleton(rule);
            writeSection(RULE, singleton, ", ", false);
        }
        flush();
    }

    /**
     * Write ontology header.
     * 
     * @param ontology
     *        the ontology
     */
    public void writeOntologyHeader(@Nonnull OWLOntology ontology) {
        event = new RendererEvent(this, ontology);
        fireFrameRenderingPrepared(ONTOLOGY.toString());
        write(ONTOLOGY.toString());
        write(":");
        writeSpace();
        if (!ontology.isAnonymous()) {
            int indent = getIndent();
            writeFullURI(ontology.getOntologyID().getOntologyIRI().get().toString());
            writeNewLine();
            pushTab(indent);
            Optional<IRI> versionIRI = ontology.getOntologyID().getVersionIRI();
            if (versionIRI.isPresent()) {
                writeFullURI(versionIRI.get().toString());
            }
            popTab();
        }
        fireFrameRenderingStarted(ONTOLOGY.toString());
        writeNewLine();
        for (OWLImportsDeclaration decl : sortedSet(ontology.getImportsDeclarations())) {
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
        writeSection(ANNOTATIONS, sortedCollection(ontology.getAnnotations()), ",", true);
        fireFrameRenderingFinished(ONTOLOGY.toString());
    }

    /** Write prefix map. */
    public void writePrefixMap() {
        ShortFormProvider sfp = getShortFormProvider();
        if (!(sfp instanceof ManchesterOWLSyntaxPrefixNameShortFormProvider)) {
            return;
        }
        ManchesterOWLSyntaxPrefixNameShortFormProvider prov = (ManchesterOWLSyntaxPrefixNameShortFormProvider) sfp;
        Map<String, String> prefixMap = prov.getPrefixName2PrefixMap();
        for (Map.Entry<String, String> e : new TreeMap<>(prefixMap).entrySet()) {
            write(PREFIX.toString());
            write(": ");
            write(e.getKey());
            write(" ");
            writeFullURI(e.getValue());
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
    public boolean isDisplayed(@Nullable OWLAxiom axiom) {
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
    public Collection<OWLAxiom> writeFrame(@Nonnull OWLEntity entity) {
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
        return CollectionFactory.emptySet();
    }

    /**
     * @param cls
     *        the cls
     * @return the sets the
     */
    @Nonnull
    public Collection<OWLAxiom> write(@Nonnull OWLClass cls) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(CLASS, cls));
        if (!isFiltered(AxiomType.EQUIVALENT_CLASSES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> equivalentClasses = new SectionMap<>();
                for (OWLEquivalentClassesAxiom ax : sortedCollection(ontology.getEquivalentClassesAxioms(cls))) {
                    if (ax.getClassExpressions().size() == 2 && isDisplayed(ax)) {
                        for (OWLClassExpression equivCls : sortedCollection(ax.getClassExpressionsMinus(cls))) {
                            equivalentClasses.put(equivCls, ax);
                        }
                        axioms.add(ax);
                    }
                }
                equivalentClasses.remove(cls);
                writeSection(EQUIVALENT_TO, equivalentClasses, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUBCLASS_OF)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> superclasses = new SectionMap<>();
                for (OWLSubClassOfAxiom ax : sortedCollection(ontology.getSubClassAxiomsForSubClass(cls))) {
                    if (isDisplayed(ax)) {
                        superclasses.put(ax.getSuperClass(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(SUBCLASS_OF, superclasses, ",", true, ontology);
            }
            if (renderExtensions) {
                for (OWLOntology ont : ontologies) {
                    SectionMap<Object, OWLAxiom> subClasses = new SectionMap<>();
                    for (OWLSubClassOfAxiom ax : sortedCollection(ont.getSubClassAxiomsForSuperClass(cls))) {
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
                for (OWLDisjointUnionAxiom ax : sortedCollection(ontology.getDisjointUnionAxioms(cls))) {
                    if (isDisplayed(ax)) {
                        Collection<OWLClassExpression> allDisjointClasses = sortedCollection(ax.getClassExpressions());
                        axioms.add(ax);
                        writeSection(DISJOINT_UNION_OF, allDisjointClasses, ", ", false, ontology);
                    }
                }
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_CLASSES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> disjointClasses = new SectionMap<>();
                for (OWLDisjointClassesAxiom ax : sortedCollection(ontology.getDisjointClassesAxioms(cls))) {
                    if (isDisplayed(ax)) {
                        if (ax.getClassExpressions().size() == 2) {
                            OWLClassExpression disjointWith = sortedCollection(ax.getClassExpressionsMinus(cls))
                                .iterator().next();
                            disjointClasses.put(disjointWith, ax);
                        }
                        axioms.add(ax);
                    }
                }
                writeSection(DISJOINT_WITH, disjointClasses, ", ", false, ontology);
                if (renderExtensions) {
                    // Handling of nary in frame style
                    for (OWLDisjointClassesAxiom ax : sortedCollection(ontology.getDisjointClassesAxioms(cls))) {
                        if (isDisplayed(ax) && ax.getClassExpressions().size() > 2) {
                            Collection<OWLClassExpression> allDisjointClasses = sortedCollection(ax
                                .getClassExpressions());
                            axioms.add(ax);
                            writeSection(DISJOINT_CLASSES, allDisjointClasses, ", ", false, ontology);
                        }
                    }
                }
            }
        }
        if (!isFiltered(AxiomType.HAS_KEY)) {
            for (OWLOntology ontology : ontologies) {
                for (OWLHasKeyAxiom ax : sortedCollection(ontology.getHasKeyAxioms(cls))) {
                    if (isDisplayed(ax)) {
                        SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                        map.put(ax.getPropertyExpressions(), ax);
                        writeSection(HAS_KEY, map, ", ", true, ontology);
                    }
                }
            }
        }
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> individuals = new SectionMap<>();
                for (OWLClassAssertionAxiom ax : sortedCollection(ontology.getClassAssertionAxioms(cls))) {
                    if (isDisplayed(ax) && (renderExtensions || ax.getIndividual().isAnonymous())) {
                        individuals.put(ax.getIndividual(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(INDIVIDUALS, individuals, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            for (OWLOntology ontology : ontologies) {
                Collection<OWLAxiom> rules = sortedCollection();
                for (SWRLRule rule : sortedCollection(ontology.getAxioms(AxiomType.SWRL_RULE))) {
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
    @Nonnull
    public Collection<OWLAxiom> write(@Nonnull OWLObjectPropertyExpression property) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(OBJECT_PROPERTY, property));
        if (!isFiltered(AxiomType.SUB_OBJECT_PROPERTY)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
                for (OWLSubObjectPropertyOfAxiom ax : sortedCollection(ontology
                    .getObjectSubPropertyAxiomsForSubProperty(property))) {
                    if (isDisplayed(ax)) {
                        properties.put(ax.getSuperProperty(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(SUB_PROPERTY_OF, properties, ",", true, ontology);
            }
            if (renderExtensions) {
                for (OWLOntology ontology : ontologies) {
                    SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
                    for (OWLSubObjectPropertyOfAxiom ax : sortedCollection(ontology
                        .getObjectSubPropertyAxiomsForSuperProperty(
                            property))) {
                        if (isDisplayed(ax)) {
                            properties.put(ax.getSubProperty(), ax);
                            axioms.add(ax);
                        }
                    }
                    writeSection(SUPER_PROPERTY_OF, properties, ",", true, ontology);
                }
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
                for (OWLEquivalentObjectPropertiesAxiom ax : sortedCollection(ontology
                    .getEquivalentObjectPropertiesAxioms(property))) {
                    if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                        Collection<OWLObjectPropertyExpression> props = sortedCollection(ax.getPropertiesMinus(
                            property));
                        properties.put(props.iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(EQUIVALENT_TO, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
                for (OWLDisjointObjectPropertiesAxiom ax : sortedCollection(ontology.getDisjointObjectPropertiesAxioms(
                    property))) {
                    if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        Collection<OWLObjectPropertyExpression> props = sortedCollection(ax.getPropertiesMinus(
                            property));
                        properties.put(props.iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(DISJOINT_WITH, properties, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
            for (OWLOntology ontology : ontologies) {
                for (OWLSubPropertyChainOfAxiom ax : sortedCollection(ontology.getAxioms(
                    AxiomType.SUB_PROPERTY_CHAIN_OF))) {
                    if (ax.getSuperProperty().equals(property) && isDisplayed(ax)) {
                        SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                        map.put(ax.getPropertyChain(), ax);
                        writeSection(SUB_PROPERTY_CHAIN, map, " o ", false, ontology);
                        axioms.add(ax);
                    }
                }
            }
        }
        for (OWLOntology ontology : ontologies) {
            SectionMap<Object, OWLAxiom> characteristics = new SectionMap<>();
            if (!isFiltered(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLFunctionalObjectPropertyAxiom ax : sortedCollection(ontology.getFunctionalObjectPropertyAxioms(
                    property))) {
                    if (isDisplayed(ax)) {
                        characteristics.put(FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : sortedCollection(ontology.getInverseFunctionalObjectPropertyAxioms(property))) {
                    if (isDisplayed(ax)) {
                        characteristics.put(INVERSE_FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : sortedCollection(ontology.getSymmetricObjectPropertyAxioms(property))) {
                    if (isDisplayed(ax)) {
                        characteristics.put(SYMMETRIC.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : sortedCollection(ontology.getTransitiveObjectPropertyAxioms(property))) {
                    if (isDisplayed(ax)) {
                        characteristics.put(TRANSITIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.REFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : sortedCollection(ontology.getReflexiveObjectPropertyAxioms(property))) {
                    if (isDisplayed(ax)) {
                        characteristics.put(REFLEXIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : sortedCollection(ontology.getIrreflexiveObjectPropertyAxioms(property))) {
                    if (isDisplayed(ax)) {
                        characteristics.put(IRREFLEXIVE.toString(), ax);
                        axioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : sortedCollection(ontology.getAsymmetricObjectPropertyAxioms(property))) {
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
                SectionMap<Object, OWLAxiom> expressions = new SectionMap<>();
                for (OWLObjectPropertyDomainAxiom ax : sortedCollection(ontology.getObjectPropertyDomainAxioms(
                    property))) {
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
                SectionMap<Object, OWLAxiom> expressions = new SectionMap<>();
                for (OWLObjectPropertyRangeAxiom ax : sortedCollection(ontology.getObjectPropertyRangeAxioms(
                    property))) {
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
                for (OWLInverseObjectPropertiesAxiom ax : sortedCollection(ontology.getInverseObjectPropertyAxioms(
                    property))) {
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
                Collection<OWLObject> rules = sortedCollection();
                for (SWRLRule rule : sortedCollection(ontology.getAxioms(AxiomType.SWRL_RULE))) {
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
    @Nonnull
    public Collection<OWLAxiom> write(@Nonnull OWLDataProperty property) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(DATA_PROPERTY, property));
        if (!isFiltered(AxiomType.FUNCTIONAL_DATA_PROPERTY)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> characteristics = new SectionMap<>();
                for (OWLAxiom ax : sortedCollection(ontology.getFunctionalDataPropertyAxioms(property))) {
                    if (isDisplayed(ax)) {
                        characteristics.put(FUNCTIONAL.toString(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(CHARACTERISTICS, characteristics, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_DOMAIN)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> domains = new SectionMap<>();
                for (OWLDataPropertyDomainAxiom ax : sortedCollection(ontology.getDataPropertyDomainAxioms(property))) {
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
                SectionMap<Object, OWLAxiom> ranges = new SectionMap<>();
                for (OWLDataPropertyRangeAxiom ax : sortedCollection(ontology.getDataPropertyRangeAxioms(property))) {
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
                SectionMap<Object, OWLAxiom> supers = new SectionMap<>();
                for (OWLSubDataPropertyOfAxiom ax : sortedCollection(ontology.getDataSubPropertyAxiomsForSubProperty(
                    property))) {
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
                SectionMap<Object, OWLAxiom> props = new SectionMap<>();
                for (OWLEquivalentDataPropertiesAxiom ax : sortedCollection(ontology.getEquivalentDataPropertiesAxioms(
                    property))) {
                    if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                        props.put(ax.getPropertiesMinus(property).iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(EQUIVALENT_TO, props, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> props = new SectionMap<>();
                for (OWLDisjointDataPropertiesAxiom ax : sortedCollection(ontology.getDisjointDataPropertiesAxioms(
                    property))) {
                    if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                        props.put(ax.getPropertiesMinus(property).iterator().next(), ax);
                        axioms.add(ax);
                    }
                }
                props.remove(property);
                writeSection(DISJOINT_WITH, props, ",", true, ontology);
            }
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            for (OWLOntology ontology : ontologies) {
                Collection<OWLAxiom> rules = new ArrayList<>();
                for (SWRLRule rule : sortedCollection(ontology.getAxioms(AxiomType.SWRL_RULE))) {
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
    @Nonnull
    public Collection<OWLAxiom> write(@Nonnull OWLIndividual individual) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(INDIVIDUAL, individual));
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> expressions = new SectionMap<>();
                for (OWLClassAssertionAxiom ax : sortedCollection(ontology.getClassAssertionAxioms(individual))) {
                    if (isDisplayed(ax)) {
                        expressions.put(ax.getClassExpression(), ax);
                        axioms.add(ax);
                    }
                }
                writeSection(TYPES, expressions, ",", true, ontology);
            }
        }
        for (OWLOntology ontology : ontologies) {
            Collection<OWLPropertyAssertionAxiom<?, ?>> assertions = sortedCollection();
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
                for (Iterator<OWLPropertyAssertionAxiom<?, ?>> it = assertions.iterator(); it.hasNext();) {
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
            for (OWLOntology ontology : ontologies) {
                Collection<OWLIndividual> inds = sortedCollection();
                Collection<OWLDifferentIndividualsAxiom> nary = sortedCollection();
                for (OWLDifferentIndividualsAxiom ax : ontology.getDifferentIndividualAxioms(individual)) {
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
                        writeSection(DIFFERENT_INDIVIDUALS, ax.getIndividuals(), ", ", false, ontology);
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
    @Nonnull
    public Collection<OWLAxiom> write(@Nonnull OWLDatatype datatype) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(DATATYPE, datatype));
        if (!isFiltered(AxiomType.DATATYPE_DEFINITION)) {
            for (OWLOntology ontology : ontologies) {
                Collection<OWLDataRange> dataRanges = sortedCollection();
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

    /**
     * @param rule
     *        the rule
     * @return written axioms
     */
    @Nonnull
    public Collection<OWLAxiom> write(@Nonnull SWRLRule rule) {
        List<OWLAxiom> axioms = new ArrayList<>(1);
        for (OWLOntology ontology : ontologies) {
            if (ontology.containsAxiom(rule)) {
                writeSection(RULE, CollectionFactory.createSet(rule), "", true, ontology);
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
    @Nonnull
    public Collection<OWLAxiom> write(@Nonnull OWLAnnotationProperty property) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(ANNOTATION_PROPERTY, property));
        if (!isFiltered(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            for (OWLOntology ont : ontologies) {
                Collection<OWLAnnotationProperty> props = sortedCollection();
                for (OWLSubAnnotationPropertyOfAxiom ax : ont.getSubAnnotationPropertyOfAxioms(property)) {
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
                for (OWLAnnotationPropertyDomainAxiom ax : ont.getAnnotationPropertyDomainAxioms(property)) {
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

    /**
     * Write entity start.
     * 
     * @param keyword
     *        the keyword
     * @param entity
     *        the entity
     * @return written axioms
     */
    private Collection<OWLAnnotationAssertionAxiom> writeEntityStart(@Nonnull ManchesterOWLSyntax keyword,
        @Nonnull OWLObject entity) {
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
        return CollectionFactory.emptySet();
    }

    /**
     * Write annotations.
     * 
     * @param subject
     *        the subject
     * @return written axioms
     */
    @Nonnull
    public Collection<OWLAnnotationAssertionAxiom> writeAnnotations(@Nonnull OWLAnnotationSubject subject) {
        Collection<OWLAnnotationAssertionAxiom> axioms = sortedCollection();
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for (OWLOntology ontology : ontologies) {
                SectionMap<Object, OWLAxiom> sectionMap = new SectionMap<>();
                for (OWLAnnotationAssertionAxiom ax : ontology.getAnnotationAssertionAxioms(subject)) {
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
    public void writeSection(@Nonnull ManchesterOWLSyntax keyword) {
        write("", keyword, "");
        write(":");
        writeSpace();
    }

    private void writeSection(@Nonnull ManchesterOWLSyntax keyword, @Nonnull SectionMap<Object, OWLAxiom> content,
        String delimeter, boolean newline, @Nonnull OWLOntology... ontologiesList) {
        String sec = keyword.toString();
        if (content.isNotEmpty() || renderingDirector.renderEmptyFrameSection(keyword, ontologiesList)) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(ontologiesList);
            incrementTab(4);
            writeNewLine();
            fireSectionRenderingStarted(sec);
            for (Iterator<Object> it = content.getSectionObjects().iterator(); it.hasNext();) {
                Object obj = it.next();
                Collection<Collection<OWLAnnotation>> annotationSets = content.getAnnotationsForSectionObject(obj);
                for (Iterator<Collection<OWLAnnotation>> annosSetIt = annotationSets.iterator(); annosSetIt
                    .hasNext();) {
                    Collection<OWLAnnotation> annos = annosSetIt.next();
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
                    } else if (obj instanceof Collection) {
                        for (Iterator<?> listIt = ((Collection<?>) obj).iterator(); listIt.hasNext();) {
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
     * @param delimiter
     *        the delimiter
     * @param newline
     *        the newline
     * @param ontologiesList
     *        the ontologies list
     */
    public void writeSection(@Nonnull ManchesterOWLSyntax keyword, @Nonnull Collection<?> content, String delimiter,
        boolean newline, @Nonnull OWLOntology... ontologiesList) {
        String sec = keyword.toString();
        if (!content.isEmpty() || renderingDirector.renderEmptyFrameSection(keyword, ontologiesList)) {
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
                    write(delimiter);
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
    public void writeComment(String commentDelim, String comment, boolean placeOnNewline) {
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
    private void writeOntologiesList(@Nonnull OWLOntology... ontologiesList) {
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
        DefaultRenderingDirector() {}

        @Override
        public boolean renderEmptyFrameSection(ManchesterOWLSyntax frameSectionKeyword, OWLOntology... ontologies) {
            return false;
        }
    }

    @Nonnull
    <E extends OWLObject> Collection<E> sortedCollection() {
        return new TreeSet<>(owlObjectComparator);
    }

    @Nonnull
    <E> Collection<E> sortedSet() {
        return new TreeSet<>();
    }

    @Nonnull
    <E> Collection<E> sortedSet(@Nonnull Collection<E> fromCollection) {
        Collection<E> set = sortedSet();
        set.addAll(fromCollection);
        return set;
    }

    @Nonnull
    <E extends OWLObject> Collection<E> sortedCollection(@Nonnull Collection<E> fromCollection) {
        Collection<E> set = sortedCollection();
        set.addAll(fromCollection);
        return set;
    }
}
