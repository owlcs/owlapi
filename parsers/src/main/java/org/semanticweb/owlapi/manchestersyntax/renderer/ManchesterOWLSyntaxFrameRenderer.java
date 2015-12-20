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

import static java.util.stream.Collectors.toList;
import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.io.Writer;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

import com.google.common.collect.Sets;

/**
 * The Class ManchesterOWLSyntaxFrameRenderer.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class ManchesterOWLSyntaxFrameRenderer extends ManchesterOWLSyntaxObjectRenderer implements OWLEntityVisitor {

    private class SectionMap<O, V extends OWLAxiom> {

        @Nonnull private final Map<O, Collection<V>> object2Axioms = new LinkedHashMap<>();

        SectionMap() {}

        boolean isNotEmpty() {
            return !object2Axioms.isEmpty();
        }

        void put(O obj, V forAxiom) {
            Collection<V> axioms = object2Axioms.get(obj);
            if (axioms == null) {
                axioms = sortedCollection();
                object2Axioms.put(obj, axioms);
            }
            axioms.add(forAxiom);
        }

        void remove(O obj) {
            object2Axioms.remove(obj);
        }

        Collection<O> getSectionObjects() {
            return object2Axioms.keySet();
        }

        Collection<Collection<OWLAnnotation>> getAnnotationsForSectionObject(Object sectionObject) {
            Collection<V> axioms = object2Axioms.get(sectionObject);
            if (axioms == null) {
                return sortedSet();
            }
            Collection<Collection<OWLAnnotation>> annos = new ArrayList<>();
            axioms.forEach(ax -> annos.add(asList(ax.annotations().sorted(ooc))));
            return annos;
        }
    }

    private final OWLOntology o;
    private OntologyIRIShortFormProvider shortFormProvider = new OntologyIRIShortFormProvider();
    @Nonnull private final Set<AxiomType<?>> filteredAxiomTypes = Sets.newHashSet(AxiomType.SWRL_RULE);
    private boolean renderExtensions = false;
    @Nonnull private final List<RendererListener> listeners = new ArrayList<>();
    private OWLAxiomFilter axiomFilter = axiom -> true;
    private RenderingDirector renderingDirector = new DefaultRenderingDirector();
    @Nonnull protected final OWLObjectComparator ooc;
    private final Predicate<OWLAxiom> props = ax -> ((OWLNaryPropertyAxiom<?>) ax).properties().count() == 2;
    /** The event. */
    private RendererEvent event;

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
    public ManchesterOWLSyntaxFrameRenderer(OWLOntology ontology, Writer writer,
        ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
        o = ontology;
        ooc = new OWLObjectComparator(entityShortFormProvider);
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
    public ManchesterOWLSyntaxFrameRenderer(Collection<OWLOntology> ontologies, Writer writer,
        ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
        if (ontologies.size() != 1) {
            throw new OWLRuntimeException("Can only render one ontology");
        }
        o = ontologies.iterator().next();
        ooc = new OWLObjectComparator(entityShortFormProvider);
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
        writePrefixMap();
        writeNewLine();
        writeOntologyHeader();
        o.annotationPropertiesInSignature().sorted(ooc).forEach(this::write);
        o.datatypesInSignature().sorted(ooc).forEach(this::write);
        o.objectPropertiesInSignature().sorted(ooc).forEach(prop -> {
            write(prop);
            OWLObjectPropertyExpression invProp = prop.getInverseProperty();
            if (o.axioms(invProp).count() > 0) {
                write(invProp);
            }
        });
        o.dataPropertiesInSignature().sorted(ooc).forEach(this::write);
        o.classesInSignature().sorted(ooc).forEach(this::write);
        o.individualsInSignature().sorted(ooc).forEach(this::write);
        o.referencedAnonymousIndividuals().sorted(ooc).forEach(this::write);
        // Nary disjoint classes axioms
        event = new RendererEvent(this, o);
        o.axioms(AxiomType.DISJOINT_CLASSES).sorted(ooc).forEach(ax -> writeMoreThanTwo(ax, ax.classExpressions(),
            DISJOINT_CLASSES));
        // Nary equivalent classes axioms
        o.axioms(AxiomType.EQUIVALENT_CLASSES).sorted(ooc).forEach(ax -> writeMoreThanTwo(ax, ax.classExpressions(),
            EQUIVALENT_CLASSES));
        // Nary disjoint properties
        o.axioms(AxiomType.DISJOINT_OBJECT_PROPERTIES).sorted(ooc).forEach(ax -> writeMoreThanTwo(ax, ax.properties(),
            DISJOINT_PROPERTIES));
        // Nary equivalent properties
        o.axioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES).sorted(ooc).forEach(ax -> writeMoreThanTwo(ax, ax.properties(),
            EQUIVALENT_PROPERTIES));
        // Nary disjoint properties
        o.axioms(AxiomType.DISJOINT_DATA_PROPERTIES).sorted(ooc).forEach(ax -> writeMoreThanTwo(ax, ax.properties(),
            DISJOINT_PROPERTIES));
        // Nary equivalent properties
        o.axioms(AxiomType.EQUIVALENT_DATA_PROPERTIES).sorted(ooc).forEach(ax -> writeMoreThanTwo(ax, ax.properties(),
            EQUIVALENT_PROPERTIES));
        // Nary different individuals
        o.axioms(AxiomType.DIFFERENT_INDIVIDUALS).sorted(ooc).forEach(ax -> writeMoreThanTwo(ax, ax.individuals(),
            DIFFERENT_INDIVIDUALS));
        o.axioms(AxiomType.SWRL_RULE).sorted(ooc).forEach(rule -> writeSection(RULE, Collections.singleton(rule)
            .iterator(), ", ", false));
        flush();
    }

    protected <T> void writeMoreThanTwo(OWLAxiom ax, Stream<T> stream, ManchesterOWLSyntax section) {
        List<T> individuals = asList(stream);
        if (individuals.size() > 2) {
            SectionMap<Object, OWLAxiom> map = new SectionMap<>();
            map.put(individuals, ax);
            writeSection(section, map, ",", false);
        }
    }

    /**
     * Write ontology header.
     */
    public void writeOntologyHeader() {
        event = new RendererEvent(this, o);
        fireFrameRenderingPrepared(ONTOLOGY.toString());
        write(ONTOLOGY.toString());
        write(":");
        writeSpace();
        if (!o.isAnonymous()) {
            int indent = getIndent();
            writeFullURI(o.getOntologyID().getOntologyIRI().get().toString());
            writeNewLine();
            pushTab(indent);
            Optional<IRI> versionIRI = o.getOntologyID().getVersionIRI();
            if (versionIRI.isPresent()) {
                writeFullURI(versionIRI.get().toString());
            }
            popTab();
        }
        fireFrameRenderingStarted(ONTOLOGY.toString());
        writeNewLine();
        o.importsDeclarations().sorted().forEach(this::writeImports);
        writeNewLine();
        writeSection(ANNOTATIONS, o.annotations().iterator(), ",", true);
        fireFrameRenderingFinished(ONTOLOGY.toString());
    }

    protected void writeImports(OWLImportsDeclaration decl) {
        fireSectionItemPrepared(IMPORT.toString());
        write(IMPORT.toString());
        write(":");
        writeSpace();
        fireSectionRenderingStarted(IMPORT.toString());
        writeFullURI(decl.getIRI().toString());
        writeNewLine();
        fireSectionRenderingFinished(IMPORT.toString());
    }

    /** Write prefix map. */
    public void writePrefixMap() {
        ShortFormProvider sfp = getShortFormProvider();
        if (!(sfp instanceof ManchesterOWLSyntaxPrefixNameShortFormProvider)) {
            return;
        }
        ManchesterOWLSyntaxPrefixNameShortFormProvider prov = (ManchesterOWLSyntaxPrefixNameShortFormProvider) sfp;
        Map<String, String> prefixMap = prov.getPrefixName2PrefixMap();
        prefixMap.entrySet().stream().sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey())).forEach(value -> {
            write(PREFIX.toString());
            write(": ");
            write(value.getKey());
            write(" ");
            writeFullURI(value.getValue());
            writeNewLine();
        });
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
    public Collection<OWLAxiom> writeFrame(OWLEntity entity) {
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

    protected <T extends OWLAxiom> Stream<T> filtersort(Stream<T> s) {
        return s.filter(this::isDisplayed).sorted(ooc);
    }

    protected <T extends OWLAxiom> Stream<T> filtersort(Stream<T> s, Predicate<OWLAxiom> extra) {
        return s.filter(this::isDisplayed).filter(extra).sorted(ooc);
    }

    /**
     * @param cls
     *        the cls
     * @return the sets the
     */
    public Collection<OWLAxiom> write(OWLClass cls) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(CLASS, cls));
        if (!isFiltered(AxiomType.EQUIVALENT_CLASSES)) {
            SectionMap<Object, OWLAxiom> equivalentClasses = new SectionMap<>();
            filtersort(o.equivalentClassesAxioms(cls),
                ax -> ((OWLEquivalentClassesAxiom) ax).classExpressions().count() == 2).forEach(ax -> {
                    ax.getClassExpressionsMinus(cls).forEach(c -> equivalentClasses.put(c, ax));
                    axioms.add(ax);
                });
            equivalentClasses.remove(cls);
            writeSection(EQUIVALENT_TO, equivalentClasses, ",", true);
        }
        if (!isFiltered(AxiomType.SUBCLASS_OF)) {
            SectionMap<Object, OWLAxiom> superclasses = new SectionMap<>();
            filtersort(o.subClassAxiomsForSubClass(cls)).forEach(ax -> {
                superclasses.put(ax.getSuperClass(), ax);
                axioms.add(ax);
            });
            writeSection(SUBCLASS_OF, superclasses, ",", true);
            if (renderExtensions) {
                SectionMap<Object, OWLAxiom> subClasses = new SectionMap<>();
                filtersort(o.subClassAxiomsForSuperClass(cls)).forEach(ax -> {
                    subClasses.put(ax.getSubClass(), ax);
                    axioms.add(ax);
                });
                writeSection(SUPERCLASS_OF, subClasses, ",", true);
            }
        }
        if (!isFiltered(AxiomType.DISJOINT_UNION)) {
            // Handling of nary in frame style
            filtersort(o.disjointUnionAxioms(cls)).forEach(ax -> {
                axioms.add(ax);
                writeSection(DISJOINT_UNION_OF, ax.classExpressions().iterator(), ", ", false);
            });
        }
        if (!isFiltered(AxiomType.DISJOINT_CLASSES)) {
            SectionMap<Object, OWLAxiom> disjointClasses = new SectionMap<>();
            filtersort(o.disjointClassesAxioms(cls)).forEach(ax -> {
                if (ax.classExpressions().count() == 2) {
                    OWLClassExpression disjointWith = ax.getClassExpressionsMinus(cls).iterator().next();
                    disjointClasses.put(disjointWith, ax);
                }
                axioms.add(ax);
            });
            writeSection(DISJOINT_WITH, disjointClasses, ", ", false);
            if (renderExtensions) {
                // Handling of nary in frame style
                filtersort(o.disjointClassesAxioms(cls)).forEach(ax -> {
                    if (ax.classExpressions().count() > 2) {
                        axioms.add(ax);
                        writeSection(DISJOINT_CLASSES, ax.classExpressions().iterator(), ", ", false);
                    }
                });
            }
        }
        if (!isFiltered(AxiomType.HAS_KEY)) {
            filtersort(o.hasKeyAxioms(cls)).forEach(ax -> {
                SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                map.put(asList(ax.propertyExpressions()), ax);
                writeSection(HAS_KEY, map, ", ", true);
            });
        }
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            SectionMap<Object, OWLAxiom> individuals = new SectionMap<>();
            filtersort(o.classAssertionAxioms(cls), ax -> renderExtensions ||
                ((OWLClassAssertionAxiom) ax).getIndividual().isAnonymous()).forEach(ax -> {
                    individuals.put(ax.getIndividual(), ax);
                    axioms.add(ax);
                });
            writeSection(INDIVIDUALS, individuals, ",", true);
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            // XXX used at all?
            Set<OWLAxiom> rules = new HashSet<>();
            filtersort(o.axioms(AxiomType.SWRL_RULE))
                .forEach(rule -> {
                    for (SWRLAtom atom : asList(rule.head())) {
                        if (atom.getPredicate().equals(cls)) {
                            writeSection(RULE, rules.iterator(), ", ", true);
                            break;
                        }
                    }
                });
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
    public Collection<OWLAxiom> write(OWLObjectPropertyExpression property) {
        Collection<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(OBJECT_PROPERTY, property));
        if (!isFiltered(AxiomType.SUB_OBJECT_PROPERTY)) {
            SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
            filtersort(o.objectSubPropertyAxiomsForSubProperty(property)).forEach(ax -> {
                properties.put(ax.getSuperProperty(), ax);
                axioms.add(ax);
            });
            writeSection(SUB_PROPERTY_OF, properties, ",", true);
            if (renderExtensions) {
                SectionMap<Object, OWLAxiom> extproperties = new SectionMap<>();
                filtersort(o.objectSubPropertyAxiomsForSuperProperty(property)).forEach(ax -> {
                    extproperties.put(ax.getSubProperty(), ax);
                    axioms.add(ax);
                });
                writeSection(SUPER_PROPERTY_OF, extproperties, ",", true);
            }
        }
        if (!isFiltered(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
            filtersort(o.equivalentObjectPropertiesAxioms(property), props).forEach(ax -> {
                properties.put(ax.getPropertiesMinus(property).iterator().next(), ax);
                axioms.add(ax);
            });
            writeSection(EQUIVALENT_TO, properties, ",", true);
        }
        if (!isFiltered(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
            filtersort(o.disjointObjectPropertiesAxioms(property), props).forEach(ax -> {
                properties.put(ax.getPropertiesMinus(property).iterator().next(), ax);
                axioms.add(ax);
            });
            writeSection(DISJOINT_WITH, properties, ",", true);
        }
        if (!isFiltered(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
            filtersort(o.axioms(AxiomType.SUB_PROPERTY_CHAIN_OF), ax -> ((OWLSubPropertyChainOfAxiom) ax)
                .getSuperProperty().equals(property))
                    .forEach(ax -> {
                        SectionMap<Object, OWLAxiom> map = new SectionMap<>();
                        map.put(ax.getPropertyChain(), ax);
                        writeSection(SUB_PROPERTY_CHAIN, map, " o ", false);
                        axioms.add(ax);
                    });
        }
        SectionMap<Object, OWLAxiom> characteristics = new SectionMap<>();
        if (!isFiltered(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)) {
            filtersort(o.functionalObjectPropertyAxioms(property)).forEach(ax -> {
                characteristics.put(FUNCTIONAL.toString(), ax);
                axioms.add(ax);
            });
        }
        if (!isFiltered(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
            filtersort(o.inverseFunctionalObjectPropertyAxioms(property)).forEach(ax -> {
                characteristics.put(INVERSE_FUNCTIONAL.toString(), ax);
                axioms.add(ax);
            });
        }
        if (!isFiltered(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
            filtersort(o.symmetricObjectPropertyAxioms(property)).forEach(ax -> {
                characteristics.put(SYMMETRIC.toString(), ax);
                axioms.add(ax);
            });
        }
        if (!isFiltered(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
            filtersort(o.transitiveObjectPropertyAxioms(property)).forEach(ax -> {
                characteristics.put(TRANSITIVE.toString(), ax);
                axioms.add(ax);
            });
        }
        if (!isFiltered(AxiomType.REFLEXIVE_OBJECT_PROPERTY)) {
            filtersort(o.reflexiveObjectPropertyAxioms(property)).forEach(ax -> {
                characteristics.put(REFLEXIVE.toString(), ax);
                axioms.add(ax);
            });
        }
        if (!isFiltered(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY)) {
            filtersort(o.irreflexiveObjectPropertyAxioms(property)).forEach(ax -> {
                characteristics.put(IRREFLEXIVE.toString(), ax);
                axioms.add(ax);
            });
        }
        if (!isFiltered(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
            filtersort(o.asymmetricObjectPropertyAxioms(property)).forEach(ax -> {
                characteristics.put(ASYMMETRIC.toString(), ax);
                axioms.add(ax);
            });
        }
        writeSection(CHARACTERISTICS, characteristics, ",", true);
        if (!isFiltered(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            SectionMap<Object, OWLAxiom> expressions = new SectionMap<>();
            filtersort(o.objectPropertyDomainAxioms(property)).forEach(ax -> {
                expressions.put(ax.getDomain(), ax);
                axioms.add(ax);
            });
            writeSection(DOMAIN, expressions, ",", true);
        }
        if (!isFiltered(AxiomType.OBJECT_PROPERTY_RANGE)) {
            SectionMap<Object, OWLAxiom> expressions = new SectionMap<>();
            filtersort(o.objectPropertyRangeAxioms(property)).forEach(ax -> {
                expressions.put(ax.getRange(), ax);
                axioms.add(ax);
            });
            writeSection(RANGE, expressions, ",", true);
        }
        if (!isFiltered(AxiomType.INVERSE_OBJECT_PROPERTIES)) {
            Collection<OWLObjectPropertyExpression> properties = sortedCollection();
            filtersort(o.inverseObjectPropertyAxioms(property)).forEach(ax -> {
                if (ax.getFirstProperty().equals(property)) {
                    properties.add(ax.getSecondProperty());
                } else {
                    properties.add(ax.getFirstProperty());
                }
                axioms.add(ax);
            });
            writeSection(INVERSE_OF, properties.iterator(), ",", true);
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            Collection<OWLAxiom> rules = sortedCollection();
            filtersort(o.axioms(AxiomType.SWRL_RULE))
                .forEach(rule -> {
                    for (SWRLAtom atom : asList(rule.head())) {
                        if (atom.getPredicate().equals(property)) {
                            rules.add(rule);
                            writeSection(RULE, rules.iterator(), ",", true);
                            break;
                        }
                    }
                });
        }
        writeEntitySectionEnd(OBJECT_PROPERTY.toString());
        return axioms;
    }

    /**
     * @param property
     *        the property
     * @return the sets the
     */
    public Collection<OWLAxiom> write(OWLDataProperty property) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(DATA_PROPERTY, property));
        if (!isFiltered(AxiomType.FUNCTIONAL_DATA_PROPERTY)) {
            SectionMap<Object, OWLAxiom> characteristics = new SectionMap<>();
            filtersort(o.functionalDataPropertyAxioms(property)).forEach(ax -> {
                characteristics.put(FUNCTIONAL.toString(), ax);
                axioms.add(ax);
            });
            writeSection(CHARACTERISTICS, characteristics, ",", true);
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_DOMAIN)) {
            SectionMap<Object, OWLAxiom> domains = new SectionMap<>();
            filtersort(o.dataPropertyDomainAxioms(property))
                .forEach(ax -> {
                    domains.put(ax.getDomain(), ax);
                    axioms.add(ax);
                });
            writeSection(DOMAIN, domains, ",", true);
        }
        if (!isFiltered(AxiomType.DATA_PROPERTY_RANGE)) {
            SectionMap<Object, OWLAxiom> ranges = new SectionMap<>();
            filtersort(o.dataPropertyRangeAxioms(property))
                .forEach(ax -> {
                    ranges.put(ax.getRange(), ax);
                    axioms.add(ax);
                });
            writeSection(RANGE, ranges, ",", true);
        }
        if (!isFiltered(AxiomType.SUB_DATA_PROPERTY)) {
            SectionMap<Object, OWLAxiom> supers = new SectionMap<>();
            filtersort(o.dataSubPropertyAxiomsForSubProperty(property)).forEach(ax -> {
                supers.put(ax.getSuperProperty(), ax);
                axioms.add(ax);
            });
            writeSection(SUB_PROPERTY_OF, supers, ",", true);
        }
        if (!isFiltered(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
            filtersort(
                o.equivalentDataPropertiesAxioms(property), props).forEach(ax -> {
                    properties.put(ax.getPropertiesMinus(property).iterator().next(), ax);
                    axioms.add(ax);
                });
            writeSection(EQUIVALENT_TO, properties, ",", true);
        }
        if (!isFiltered(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            SectionMap<Object, OWLAxiom> properties = new SectionMap<>();
            filtersort(o.disjointDataPropertiesAxioms(property), props).forEach(ax -> {
                properties.put(ax.getPropertiesMinus(property).iterator().next(), ax);
                axioms.add(ax);
            });
            properties.remove(property);
            writeSection(DISJOINT_WITH, properties, ",", true);
        }
        if (!isFiltered(AxiomType.SWRL_RULE)) {
            // XXX is rules used?
            List<OWLAxiom> rules = new ArrayList<>();
            filtersort(o.axioms(AxiomType.SWRL_RULE)).forEach(
                rule -> {
                    for (SWRLAtom atom : asList(rule.head())) {
                        if (atom.getPredicate().equals(property)) {
                            writeSection(RULE, rules.iterator(), "", true);
                            break;
                        }
                    }
                });
        }
        writeEntitySectionEnd(DATA_PROPERTY.toString());
        return axioms;
    }

    /**
     * @param individual
     *        the individual
     * @return the sets the
     */
    public Collection<OWLAxiom> write(OWLIndividual individual) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(INDIVIDUAL, individual));
        if (!isFiltered(AxiomType.CLASS_ASSERTION)) {
            SectionMap<Object, OWLAxiom> expressions = new SectionMap<>();
            filtersort(o.classAssertionAxioms(individual)).forEach(ax -> {
                expressions.put(ax.getClassExpression(), ax);
                axioms.add(ax);
            });
            writeSection(TYPES, expressions, ",", true);
        }
        List<OWLPropertyAssertionAxiom<?, ?>> assertions = Stream
            .of(o.objectPropertyAssertionAxioms(individual),
                o.negativeObjectPropertyAssertionAxioms(individual),
                o.dataPropertyAssertionAxioms(individual),
                o.negativeDataPropertyAssertionAxioms(individual))
            .flatMap(x -> x).sorted(ooc).collect(toList());
        if (!assertions.isEmpty()) {
            fireSectionRenderingPrepared(FACTS.toString());
            writeSection(FACTS);
            writeSpace();
            writeOntologiesList(o);
            incrementTab(1);
            writeNewLine();
            fireSectionRenderingStarted(FACTS.toString());
            for (Iterator<OWLPropertyAssertionAxiom<?, ?>> it = assertions.iterator(); it.hasNext();) {
                OWLPropertyAssertionAxiom<?, ?> ax = it.next();
                fireSectionItemPrepared(FACTS.toString());
                Iterator<OWLAnnotation> annos = ax.annotations().iterator();
                boolean isNotEmpty = annos.hasNext();
                if (isNotEmpty) {
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
                if (isNotEmpty) {
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
        if (!isFiltered(AxiomType.SAME_INDIVIDUAL)) {
            Collection<OWLIndividual> inds = sortedCollection();
            filtersort(o.sameIndividualAxioms(individual)).forEach(ax -> {
                add(inds, ax.individuals());
                axioms.add(ax);
            });
            inds.remove(individual);
            writeSection(SAME_AS, inds.iterator(), ",", true);
        }
        if (!isFiltered(AxiomType.DIFFERENT_INDIVIDUALS)) {
            Collection<OWLIndividual> inds = sortedCollection();
            Collection<OWLDifferentIndividualsAxiom> nary = sortedCollection();
            filtersort(o.differentIndividualAxioms(individual)).forEach(ax -> {
                if (ax.individuals().count() == 2) {
                    add(inds, ax.individuals());
                    axioms.add(ax);
                } else {
                    nary.add(ax);
                }
            });
            inds.remove(individual);
            writeSection(DIFFERENT_FROM, inds.iterator(), ",", true);
            if (renderExtensions) {
                nary.forEach(ax -> writeSection(DIFFERENT_INDIVIDUALS, ax.individuals().iterator(), ", ", false));
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
    public Collection<OWLAxiom> write(OWLDatatype datatype) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(DATATYPE, datatype));
        if (!isFiltered(AxiomType.DATATYPE_DEFINITION)) {
            Collection<OWLDataRange> dataRanges = sortedCollection();
            o.datatypeDefinitions(datatype).filter(this::isDisplayed).forEach(ax -> {
                axioms.add(ax);
                dataRanges.add(ax.getDataRange());
            });
            writeSection(EQUIVALENT_TO, dataRanges.iterator(), ",", true);
        }
        writeEntitySectionEnd(DATATYPE.toString());
        return axioms;
    }

    /**
     * @param rule
     *        the rule
     * @return written axioms
     */
    public Collection<OWLAxiom> write(SWRLRule rule) {
        List<OWLAxiom> axioms = new ArrayList<>(1);
        if (o.containsAxiom(rule)) {
            writeSection(RULE, CollectionFactory.createSet(rule).iterator(), "", true);
            axioms.add(rule);
        }
        return axioms;
    }

    /**
     * @param property
     *        the property
     * @return written axioms
     */
    public Collection<OWLAxiom> write(OWLAnnotationProperty property) {
        List<OWLAxiom> axioms = new ArrayList<>();
        axioms.addAll(writeEntityStart(ANNOTATION_PROPERTY, property));
        if (!isFiltered(AxiomType.SUB_ANNOTATION_PROPERTY_OF)) {
            Collection<OWLAnnotationProperty> properties = sortedCollection();
            o.subAnnotationPropertyOfAxioms(property).filter(this::isDisplayed)
                .forEach(ax -> properties.add(ax.getSuperProperty()));
            writeSection(SUB_PROPERTY_OF, properties.iterator(), ",", true);
        }
        if (!isFiltered(AxiomType.ANNOTATION_PROPERTY_DOMAIN)) {
            Collection<IRI> iris = sortedCollection();
            o.annotationPropertyDomainAxioms(property).filter(this::isDisplayed).forEach(ax -> iris.add(ax
                .getDomain()));
            writeSection(DOMAIN, iris.iterator(), ",", true);
        }
        if (!isFiltered(AxiomType.ANNOTATION_PROPERTY_RANGE)) {
            Collection<IRI> iris = sortedCollection();
            o.annotationPropertyRangeAxioms(property).filter(this::isDisplayed).forEach(ax -> iris.add(ax.getRange()));
            writeSection(RANGE, iris.iterator(), ",", true);
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
    private Collection<OWLAnnotationAssertionAxiom> writeEntityStart(ManchesterOWLSyntax keyword, OWLObject entity) {
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
    public Collection<OWLAnnotationAssertionAxiom> writeAnnotations(OWLAnnotationSubject subject) {
        Collection<OWLAnnotationAssertionAxiom> axioms = sortedCollection();
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            SectionMap<Object, OWLAxiom> sectionMap = new SectionMap<>();
            filtersort(o.annotationAssertionAxioms(subject)).forEach(ax -> {
                axioms.add(ax);
                sectionMap.put(ax.getAnnotation(), ax);
            });
            writeSection(ANNOTATIONS, sectionMap, ",", true);
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

    private void writeSection(ManchesterOWLSyntax keyword, SectionMap<Object, OWLAxiom> content, String delimeter,
        boolean newline) {
        String sec = keyword.toString();
        if (content.isNotEmpty() || renderingDirector.renderEmptyFrameSection(keyword, o)) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(o);
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
                            Object object = listIt.next();
                            if (object instanceof OWLObject) {
                                ((OWLObject) object).accept(this);
                            } else {
                                write(object.toString());
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
     */
    public void writeSection(ManchesterOWLSyntax keyword, Iterator<?> content, String delimiter, boolean newline) {
        String sec = keyword.toString();
        if (content.hasNext() || renderingDirector.renderEmptyFrameSection(keyword, o)) {
            fireSectionRenderingPrepared(sec);
            writeSection(keyword);
            writeOntologiesList(o);
            incrementTab(4);
            writeNewLine();
            fireSectionRenderingStarted(sec);
            while (content.hasNext()) {
                Object obj = content.next();
                fireSectionItemPrepared(sec);
                if (obj instanceof OWLObject) {
                    ((OWLObject) obj).accept(this);
                } else {
                    write(obj.toString());
                }
                if (content.hasNext()) {
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
        listeners.forEach(l -> l.frameRenderingPrepared(section, event));
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
        listeners.forEach(l -> l.frameRenderingStarted(section, event));
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
        listeners.forEach(l -> l.frameRenderingFinished(section, event));
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
        listeners.forEach(l -> l.sectionRenderingPrepared(section, event));
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
        listeners.forEach(l -> l.sectionRenderingStarted(section, event));
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
        listeners.forEach(l -> l.sectionRenderingFinished(section, event));
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
        listeners.forEach(l -> l.sectionItemPrepared(section, event));
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
        listeners.forEach(l -> l.sectionItemFinished(section, event));
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

    <E extends OWLObject> Collection<E> sortedCollection() {
        return new TreeSet<>(ooc);
    }

    static <E> Collection<E> sortedSet() {
        return new TreeSet<>();
    }
}
