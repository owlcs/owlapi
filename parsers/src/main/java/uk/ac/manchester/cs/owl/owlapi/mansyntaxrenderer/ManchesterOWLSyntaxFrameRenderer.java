/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
import org.semanticweb.owlapi.util.OntologyIRIShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * The Class ManchesterOWLSyntaxFrameRenderer.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 25-Apr-2007
 */
public class ManchesterOWLSyntaxFrameRenderer extends
        ManchesterOWLSyntaxObjectRenderer implements OWLEntityVisitor {

    /**
     * The ontologies.
     */
    private Set<OWLOntology> ontologies;
    /**
     * The short form provider.
     */
    private OntologyIRIShortFormProvider shortFormProvider = new OntologyIRIShortFormProvider();
    /**
     * The filtered axiom types.
     */
    private Set<AxiomType<?>> filteredAxiomTypes = new HashSet<AxiomType<?>>();
    /**
     * The render extensions.
     */
    private boolean renderExtensions = false;
    /**
     * Whether to render ontologies lists in extensions *
     */
    private boolean renderOntologyLists = false;
    /**
     * The listeners.
     */
    private List<RendererListener> listeners = new ArrayList<RendererListener>();
    /**
     * The axiom filter.
     */
    private OWLAxiomFilter axiomFilter = new OWLAxiomFilter() {

        @Override
        public boolean passes(OWLAxiom axiom) {
            return true;
        }
    };
    /**
     * The rendering director.
     */
    private RenderingDirector renderingDirector = new DefaultRenderingDirector();
    /**
     * The event.
     */
    private RendererEvent event;

    /**
     * Instantiates a new manchester owl syntax frame renderer.
     * 
     * @param owlOntologyManager
     *        the owl ontology manager
     * @param ontology
     *        the ontology
     * @param writer
     *        the writer
     * @param entityShortFormProvider
     *        the entity short form provider
     */
    @Deprecated
    @SuppressWarnings("unused")
    public ManchesterOWLSyntaxFrameRenderer(
            OWLOntologyManager owlOntologyManager, OWLOntology ontology,
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
    }

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
     * @param owlOntologyManager
     *        the owl ontology manager
     * @param ontologies
     *        the ontologies
     * @param defaultOntology
     *        the default ontology
     * @param writer
     *        the writer
     * @param entityShortFormProvider
     *        the entity short form provider
     */
    @Deprecated
    @SuppressWarnings("unused")
    public ManchesterOWLSyntaxFrameRenderer(
            OWLOntologyManager owlOntologyManager, Set<OWLOntology> ontologies,
            OWLOntology defaultOntology, Writer writer,
            ShortFormProvider entityShortFormProvider) {
        this(ontologies, writer, entityShortFormProvider);
    }

    /**
     * Instantiates a new manchester owl syntax frame renderer.
     * 
     * @param ontologies
     *        the ontologies
     * @param defaultOntology
     *        the default ontology
     * @param writer
     *        the writer
     * @param entityShortFormProvider
     *        the entity short form provider
     */
    @Deprecated
    @SuppressWarnings("unused")
    public ManchesterOWLSyntaxFrameRenderer(Set<OWLOntology> ontologies,
            OWLOntology defaultOntology, Writer writer,
            ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
        this.ontologies = new LinkedHashSet<OWLOntology>(ontologies);
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

    /**
     * Clear filtered axiom types.
     */
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
     * Specifies whether or not ontology lists should be rendered after frame
     * section keywords. Ontology lists are not part of the Manchester Syntax
     * specification - they are an extension for use in editing tools and the
     * like. The default behaviour is not to render the ontology list. An
     * example of an ontology list is: Class: A SubClassOf [in X, Y, Z] B. Where
     * X, Y and Z are ontologies. This indicates that A SubClassOf B occurs in
     * ontologies X, Y and Z. <br>
     * Note that, for backwards compatibility, setting
     * {@link #setRenderExtensions(boolean)} to {@code true} will also cause
     * ontology lists to be rendered (along with other extensions).
     * 
     * @param renderOntologyList
     *        Whether or not to render the ontology list for each section.
     *        {@code true} if the list should be rendered, otherwise
     *        {@code false}. The default behaviour is {@code false}.
     */
    public void setRenderOntologyLists(boolean renderOntologyList) {
        renderOntologyLists = renderOntologyList;
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
        OWLOntology ontology = getOntologies().iterator().next();
        writePrefixMap();
        writeNewLine();
        writeOntologyHeader(ontology);
        for (OWLAnnotationProperty prop : ontology
                .getAnnotationPropertiesInSignature()) {
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
        for (OWLAnonymousIndividual ind : ontology
                .getReferencedAnonymousIndividuals()) {
            write(ind);
        }
        writeAllNaryAxiomsInOntology(ontology);
        Set<SWRLRule> rules = ontology.getAxioms(AxiomType.SWRL_RULE);
        writeRules(rules);
        flush();
    }

    /**
     * Writes out the specified rules.
     * 
     * @param rules
     *        The rules to be written. Not {@code null}.
     * @param ontologiesToWrite
     *        the ontologies
     */
    public void writeRules(Set<SWRLRule> rules,
            OWLOntology... ontologiesToWrite) {
        for (SWRLRule rule : rules) {
            writeSection(RULE, Collections.singleton(rule), ", ", false,
                    ontologiesToWrite);
        }
    }

    /**
     * Writes the Nary axioms in the specified ontology.
     * 
     * @param ontology
     *        The ontology. Not {@code null}.
     */
    private void writeAllNaryAxiomsInOntology(OWLOntology ontology) {
        // Nary disjoint classes axioms
        event = new RendererEvent(this, ontology);
        for (OWLDisjointClassesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                writeDisjointClassesFrame(ax, ontology);
            }
        }
        // Nary equivalent classes axioms
        for (OWLEquivalentClassesAxiom ax : ontology
                .getAxioms(AxiomType.EQUIVALENT_CLASSES)) {
            if (ax.getClassExpressions().size() > 2) {
                writeEquivalentClassesFrame(ax, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointObjectPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                writeDisjointObjectPropertiesFrame(ax, ontology);
            }
        }
        // Nary equivalent properties
        for (OWLEquivalentObjectPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                writeEquivalentObjectPropertiesFrame(ax, ontology);
            }
        }
        // Nary disjoint properties
        for (OWLDisjointDataPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                writeDisjointDataPropertiesFrame(ax, ontology);
            }
        }
        // Nary equivalent properties
        for (OWLEquivalentDataPropertiesAxiom ax : ontology
                .getAxioms(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            if (ax.getProperties().size() > 2) {
                writeEquivalentDataPropertiesFrame(ax, ontology);
            }
        }
        // Nary different individuals
        for (OWLDifferentIndividualsAxiom ax : ontology
                .getAxioms(AxiomType.DIFFERENT_INDIVIDUALS)) {
            if (ax.getIndividuals().size() > 2) {
                writeDifferentIndividualsFrame(ax, ontology);
            }
        }
    }

    /**
     * Writes out the Nary axiom frames that contain the specified entity as an
     * operand. These are either, DisjointClasses, EquivalentClasses,
     * DisjointObjectProperties, DisjointDataProperties,
     * EquivalentObjectProperties, EquivalentDataProperties, SameIndividual or
     * DifferentIndividuals axioms that have more than two operands, one of
     * which is the specified entity.
     * 
     * @param entity
     *        The entity. Not {@code null}.
     */
    public void writeEntityNaryAxioms(OWLEntity entity) {
        entity.accept(new OWLEntityVisitor() {

            @Override
            public void visit(OWLClass cls) {
                writeClassNaryAxiomFrames(cls);
            }

            @Override
            public void visit(OWLObjectProperty property) {
                writeObjectPropertyNaryAxiomFrames(property);
            }

            @Override
            public void visit(OWLDataProperty property) {
                writeDataPropertyNaryAxiomFrames(property);
            }

            @Override
            public void visit(OWLNamedIndividual individual) {
                writeIndividualNaryAxiomFrames(individual);
            }

            @Override
            public void visit(OWLDatatype datatype) {}

            @Override
            public void visit(OWLAnnotationProperty property) {}
        });
    }

    void writeClassNaryAxiomFrames(OWLClass cls) {
        for (OWLOntology ontology : ontologies) {
            for (OWLDisjointClassesAxiom ax : ontology
                    .getDisjointClassesAxioms(cls)) {
                if (ax.getClassExpressions().size() > 2) {
                    writeDisjointClassesFrame(ax, ontology);
                }
            }
        }
        for (OWLOntology ontology : ontologies) {
            for (OWLEquivalentClassesAxiom ax : ontology
                    .getEquivalentClassesAxioms(cls)) {
                if (ax.getClassExpressions().size() > 2) {
                    writeEquivalentClassesFrame(ax, ontology);
                }
            }
        }
    }

    private void writeObjectPropertyNaryAxiomFrames(OWLObjectProperty property) {
        for (OWLOntology ontology : ontologies) {
            for (OWLEquivalentObjectPropertiesAxiom ax : ontology
                    .getEquivalentObjectPropertiesAxioms(property)) {
                if (ax.getProperties().size() > 2) {
                    writeEquivalentObjectPropertiesFrame(ax, ontology);
                }
            }
        }
        for (OWLOntology ontology : ontologies) {
            for (OWLDisjointObjectPropertiesAxiom ax : ontology
                    .getDisjointObjectPropertiesAxioms(property)) {
                if (ax.getProperties().size() > 2) {
                    writeDisjointObjectPropertiesFrame(ax, ontology);
                }
            }
        }
    }

    private void writeDataPropertyNaryAxiomFrames(OWLDataProperty property) {
        for (OWLOntology ontology : ontologies) {
            for (OWLEquivalentDataPropertiesAxiom ax : ontology
                    .getEquivalentDataPropertiesAxioms(property)) {
                if (ax.getProperties().size() > 2) {
                    writeEquivalentDataPropertiesFrame(ax, ontology);
                }
            }
        }
        for (OWLOntology ontology : ontologies) {
            for (OWLDisjointDataPropertiesAxiom ax : ontology
                    .getDisjointDataPropertiesAxioms(property)) {
                if (ax.getProperties().size() > 2) {
                    writeDisjointDataPropertiesFrame(ax, ontology);
                }
            }
        }
    }

    void writeIndividualNaryAxiomFrames(OWLIndividual ind) {
        for (OWLOntology ontology : ontologies) {
            for (OWLSameIndividualAxiom ax : ontology
                    .getSameIndividualAxioms(ind)) {
                if (ax.getIndividuals().size() > 2) {
                    writeSameIndividualFrame(ax, ontology);
                }
            }
        }
        for (OWLOntology ontology : ontologies) {
            for (OWLDifferentIndividualsAxiom ax : ontology
                    .getDifferentIndividualAxioms(ind)) {
                if (ax.getIndividuals().size() > 2) {
                    writeDifferentIndividualsFrame(ax, ontology);
                }
            }
        }
    }

    private void writeSameIndividualFrame(OWLSameIndividualAxiom ax,
            OWLOntology... o) {
        SectionMap map = new SectionMap();
        map.add(ax.getIndividuals(), ax);
        writeSection(SAME_INDIVIDUAL, map, ", ", false, o);
    }

    private void writeDifferentIndividualsFrame(
            OWLDifferentIndividualsAxiom ax, OWLOntology... o) {
        SectionMap map = new SectionMap();
        map.add(ax.getIndividuals(), ax);
        writeSection(DIFFERENT_INDIVIDUALS, map, ", ", false, o);
    }

    private void writeEquivalentDataPropertiesFrame(
            OWLEquivalentDataPropertiesAxiom ax, OWLOntology... o) {
        SectionMap map = new SectionMap();
        map.add(ax.getProperties(), ax);
        writeSection(EQUIVALENT_PROPERTIES, map, ", ", false, o);
    }

    private void writeDisjointDataPropertiesFrame(
            OWLDisjointDataPropertiesAxiom ax, OWLOntology... o) {
        SectionMap map = new SectionMap();
        map.add(ax.getProperties(), ax);
        writeSection(DISJOINT_PROPERTIES, map, ", ", false, o);
    }

    private void writeEquivalentObjectPropertiesFrame(
            OWLEquivalentObjectPropertiesAxiom ax, OWLOntology... o) {
        SectionMap map = new SectionMap();
        map.add(ax.getProperties(), ax);
        writeSection(EQUIVALENT_PROPERTIES, map, ", ", false, o);
    }

    private void writeDisjointObjectPropertiesFrame(
            OWLDisjointObjectPropertiesAxiom ax, OWLOntology... o) {
        SectionMap map = new SectionMap();
        map.add(ax.getProperties(), ax);
        writeSection(DISJOINT_PROPERTIES, map, ", ", false, o);
    }

    private void writeEquivalentClassesFrame(OWLEquivalentClassesAxiom ax,
            OWLOntology... ontologies) {
        SectionMap map = new SectionMap();
        map.add(ax.getClassExpressions(), ax);
        writeSection(EQUIVALENT_CLASSES, map, ", ", false, ontologies);
    }

    private void writeDisjointClassesFrame(OWLDisjointClassesAxiom ax,
            OWLOntology... ontologies) {
        SectionMap map = new SectionMap();
        map.add(ax.getClassExpressions(), ax);
        writeSection(DISJOINT_CLASSES, map, ", ", false, ontologies);
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
            writeFullURI(decl.getURI().toString());
            writeNewLine();
            fireSectionRenderingFinished(IMPORT.toString());
        }
        writeNewLine();
        writeSection(ANNOTATIONS, ontology.getAnnotations(), ",", true);
        fireFrameRenderingFinished(ONTOLOGY.toString());
    }

    /**
     * Write prefix map.
     */
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
     * Writes out a class frame for the specified class.
     * 
     * @param cls
     *        The class. Not {@code null}.
     * @return the sets axioms that were written out.
     */
    public Set<OWLAxiom> write(OWLClass cls) {
        Set<OWLAxiom> writtenAxioms = new HashSet<OWLAxiom>();
        writtenAxioms.addAll(writeEntityStart(CLASS, cls));
        writeClassEquivalentToIfNotFiltered(cls, writtenAxioms);
        writeClassSubClassOfIfNotFiltered(cls, writtenAxioms);
        writeClassDisjointUnionOfIfNotFiltered(cls, writtenAxioms);
        writeClassDisjointWithIfNotFiltered(cls, writtenAxioms);
        writeClassHasKeyIfNotFiltered(cls, writtenAxioms);
        if (renderExtensions) {
            writeExtensionClassSuperClassOfIfNotFiltered(cls, writtenAxioms);
            writeExtensionClassIndividualsIfNotFiltered(cls, writtenAxioms);
        }
        writeEntitySectionEnd(CLASS.toString());
        return writtenAxioms;
    }

    private void writeClassEquivalentToIfNotFiltered(OWLClass cls,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.EQUIVALENT_CLASSES)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap equivalentClasses = new SectionMap();
            for (OWLEquivalentClassesAxiom ax : ontology
                    .getEquivalentClassesAxioms(cls)) {
                if (ax.getClassExpressions().size() == 2) {
                    if (isDisplayed(ax)) {
                        for (OWLClassExpression equivCls : ax
                                .getClassExpressionsMinus(cls)) {
                            equivalentClasses.add(equivCls, ax);
                        }
                        writtenAxioms.add(ax);
                    }
                }
            }
            equivalentClasses.remove(cls);
            writeSection(EQUIVALENT_TO, equivalentClasses, ",", true, ontology);
        }
    }

    private void writeClassSubClassOfIfNotFiltered(OWLClass cls,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.SUBCLASS_OF)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap superclasses = new SectionMap();
            for (OWLSubClassOfAxiom ax : ontology
                    .getSubClassAxiomsForSubClass(cls)) {
                if (isDisplayed(ax)) {
                    superclasses.add(ax.getSuperClass(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(SUBCLASS_OF, superclasses, ",", true, ontology);
        }
    }

    private void writeClassDisjointUnionOfIfNotFiltered(OWLClass cls,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.DISJOINT_UNION)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            for (OWLDisjointUnionAxiom ax : ontology
                    .getDisjointUnionAxioms(cls)) {
                if (isDisplayed(ax)) {
                    Set<OWLClassExpression> allDisjointClasses = createTreeSet(ax
                            .getClassExpressions());
                    writtenAxioms.add(ax);
                    writeSection(DISJOINT_UNION_OF, allDisjointClasses, ", ",
                            false, ontology);
                }
            }
        }
    }

    private void writeClassDisjointWithIfNotFiltered(OWLClass cls,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.DISJOINT_CLASSES)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap disjointClasses = new SectionMap();
            Set<OWLDisjointClassesAxiom> disjointClassesAxioms = ontology
                    .getDisjointClassesAxioms(cls);
            for (OWLDisjointClassesAxiom ax : disjointClassesAxioms) {
                if (isDisplayed(ax)) {
                    if (ax.getClassExpressions().size() == 2) {
                        OWLClassExpression disjointWith = ax
                                .getClassExpressionsMinus(cls).iterator()
                                .next();
                        disjointClasses.add(disjointWith, ax);
                    }
                    writtenAxioms.add(ax);
                }
            }
            writeSection(DISJOINT_WITH, disjointClasses, ", ", false, ontology);
        }
    }

    private void writeClassHasKeyIfNotFiltered(OWLClass cls,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.HAS_KEY)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            for (OWLHasKeyAxiom ax : ontology.getHasKeyAxioms(cls)) {
                if (isDisplayed(ax)) {
                    SectionMap map = new SectionMap();
                    map.add(ax.getPropertyExpressions(), ax);
                    writeSection(HAS_KEY, map, ", ", true, ontology);
                    writtenAxioms.add(ax);
                }
            }
        }
    }

    /**
     * @param predicate
     *        predicate to write
     */
    public void writeRulesContainingPredicate(OWLObject predicate) {
        if (isFiltered(AxiomType.SWRL_RULE)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            for (SWRLRule rule : ontology.getAxioms(AxiomType.SWRL_RULE)) {
                if (isDisplayed(rule)) {
                    if (containsPredicate(predicate, rule)) {
                        writeSection(RULE, Collections.singleton(rule), ", ",
                                true, ontology);
                    }
                }
            }
        }
    }

    private boolean containsPredicate(OWLObject predicate, SWRLRule rule) {
        for (SWRLAtom atom : rule.getHead()) {
            if (atom.getPredicate().equals(predicate)) {
                return true;
            }
        }
        for (SWRLAtom atom : rule.getBody()) {
            if (atom.getPredicate().equals(predicate)) {
                return true;
            }
        }
        return false;
    }

    private void writeExtensionClassIndividualsIfNotFiltered(OWLClass cls,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.CLASS_ASSERTION)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap individuals = new SectionMap();
            for (OWLClassAssertionAxiom ax : ontology
                    .getClassAssertionAxioms(cls)) {
                if (isDisplayed(ax)) {
                    if (renderExtensions || ax.getIndividual().isAnonymous()) {
                        individuals.add(ax.getIndividual(), ax);
                        writtenAxioms.add(ax);
                    }
                }
            }
            writeSection(INDIVIDUALS, individuals, ",", true, ontology);
        }
    }

    private void writeExtensionClassSuperClassOfIfNotFiltered(OWLClass cls,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.SUBCLASS_OF)) {
            return;
        }
        for (OWLOntology ont : getOntologies()) {
            SectionMap subClasses = new SectionMap();
            for (OWLSubClassOfAxiom ax : ont
                    .getSubClassAxiomsForSuperClass(cls)) {
                if (isDisplayed(ax)) {
                    subClasses.add(ax.getSubClass(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(SUPERCLASS_OF, subClasses, ",", true, ont);
        }
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
        Set<OWLAxiom> writtenAxioms = new HashSet<OWLAxiom>();
        writtenAxioms.addAll(writeEntityStart(OBJECT_PROPERTY, property));
        writeObjectPropertyDomainIfNotFiltered(property, writtenAxioms);
        writeObjectPropertyRangeIfNotFiltered(property, writtenAxioms);
        writeObjectPropertyCharacteristicsIfNotFiltered(property, writtenAxioms);
        writeObjectPropertySubPropertyOfIfNotFiltered(property, writtenAxioms);
        writeObjectPropertyEquivalentToIfNotFiltered(property, writtenAxioms);
        writeObjectPropertyDisjointWithIfNotFiltered(property, writtenAxioms);
        writeObjectPropertyInverseOfIfNotFiltered(property, writtenAxioms);
        writeObjectPropertySubPropertyChainIfNotFiltered(property,
                writtenAxioms);
        if (renderExtensions) {
            writeExtensionObjectPropertySuperPropertyOfIfNotFiltered(property,
                    writtenAxioms);
        }
        writeEntitySectionEnd(OBJECT_PROPERTY.toString());
        return writtenAxioms;
    }

    private void writeObjectPropertyInverseOfIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.INVERSE_OBJECT_PROPERTIES)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            Set<OWLObjectPropertyExpression> properties = createTreeSet();
            for (OWLInverseObjectPropertiesAxiom ax : ontology
                    .getInverseObjectPropertyAxioms(property)) {
                if (isDisplayed(ax)) {
                    if (ax.getFirstProperty().equals(property)) {
                        properties.add(ax.getSecondProperty());
                    } else {
                        properties.add(ax.getFirstProperty());
                    }
                    writtenAxioms.add(ax);
                }
            }
            writeSection(INVERSE_OF, properties, ",", true, ontology);
        }
    }

    private void writeObjectPropertyRangeIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.OBJECT_PROPERTY_RANGE)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap expressions = new SectionMap();
            for (OWLObjectPropertyRangeAxiom ax : ontology
                    .getObjectPropertyRangeAxioms(property)) {
                if (isDisplayed(ax)) {
                    expressions.add(ax.getRange(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(RANGE, expressions, ",", true, ontology);
        }
    }

    private void writeObjectPropertyDomainIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap expressions = new SectionMap();
            for (OWLObjectPropertyDomainAxiom ax : ontology
                    .getObjectPropertyDomainAxioms(property)) {
                if (isDisplayed(ax)) {
                    expressions.add(ax.getDomain(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(DOMAIN, expressions, ",", true, ontology);
        }
    }

    private void writeObjectPropertyCharacteristicsIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        for (OWLOntology ontology : getOntologies()) {
            SectionMap characteristics = new SectionMap();
            if (!isFiltered(AxiomType.FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLFunctionalObjectPropertyAxiom ax : ontology
                        .getFunctionalObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(FUNCTIONAL.toString(), ax);
                        writtenAxioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getInverseFunctionalObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(INVERSE_FUNCTIONAL.toString(), ax);
                        writtenAxioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.SYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getSymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(SYMMETRIC.toString(), ax);
                        writtenAxioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.TRANSITIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getTransitiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(TRANSITIVE.toString(), ax);
                        writtenAxioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.REFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getReflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(REFLEXIVE.toString(), ax);
                        writtenAxioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.IRREFLEXIVE_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getIrreflexiveObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(IRREFLEXIVE.toString(), ax);
                        writtenAxioms.add(ax);
                    }
                }
            }
            if (!isFiltered(AxiomType.ASYMMETRIC_OBJECT_PROPERTY)) {
                for (OWLAxiom ax : ontology
                        .getAsymmetricObjectPropertyAxioms(property)) {
                    if (isDisplayed(ax)) {
                        characteristics.add(ASYMMETRIC.toString(), ax);
                        writtenAxioms.add(ax);
                    }
                }
            }
            writeSection(CHARACTERISTICS, characteristics, ",", true, ontology);
        }
    }

    private void writeObjectPropertySubPropertyChainIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            for (OWLSubPropertyChainOfAxiom ax : ontology
                    .getAxioms(AxiomType.SUB_PROPERTY_CHAIN_OF)) {
                if (ax.getSuperProperty().equals(property)) {
                    if (isDisplayed(ax)) {
                        SectionMap map = new SectionMap();
                        map.add(ax.getPropertyChain(), ax);
                        writeSection(SUB_PROPERTY_CHAIN, map, " o ", false,
                                ontology);
                        writtenAxioms.add(ax);
                    }
                }
            }
        }
    }

    private void writeObjectPropertyDisjointWithIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.DISJOINT_OBJECT_PROPERTIES)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap properties = new SectionMap();
            for (OWLDisjointObjectPropertiesAxiom ax : ontology
                    .getDisjointObjectPropertiesAxioms(property)) {
                if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                    Set<OWLObjectPropertyExpression> props = ax
                            .getPropertiesMinus(property);
                    properties.add(props.iterator().next(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(DISJOINT_WITH, properties, ",", true, ontology);
        }
    }

    private void writeObjectPropertyEquivalentToIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.EQUIVALENT_OBJECT_PROPERTIES)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap properties = new SectionMap();
            for (OWLEquivalentObjectPropertiesAxiom ax : ontology
                    .getEquivalentObjectPropertiesAxioms(property)) {
                if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                    Set<OWLObjectPropertyExpression> props = ax
                            .getPropertiesMinus(property);
                    properties.add(props.iterator().next(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(EQUIVALENT_TO, properties, ",", true, ontology);
        }
    }

    private void writeObjectPropertySubPropertyOfIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.SUB_OBJECT_PROPERTY)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap properties = new SectionMap();
            for (OWLSubObjectPropertyOfAxiom ax : ontology
                    .getObjectSubPropertyAxiomsForSubProperty(property)) {
                if (isDisplayed(ax)) {
                    properties.add(ax.getSuperProperty(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(SUB_PROPERTY_OF, properties, ",", true, ontology);
        }
    }

    private void writeExtensionObjectPropertySuperPropertyOfIfNotFiltered(
            OWLObjectPropertyExpression property, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.SUB_OBJECT_PROPERTY)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap properties = new SectionMap();
            for (OWLSubObjectPropertyOfAxiom ax : ontology
                    .getObjectSubPropertyAxiomsForSuperProperty(property)) {
                if (isDisplayed(ax)) {
                    properties.add(ax.getSubProperty(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(SUPER_PROPERTY_OF, properties, ",", true, ontology);
        }
    }

    /**
     * @param property
     *        the property
     * @return the sets the
     */
    public Set<OWLAxiom> write(OWLDataProperty property) {
        Set<OWLAxiom> writtenAxioms = new HashSet<OWLAxiom>();
        writtenAxioms.addAll(writeEntityStart(DATA_PROPERTY, property));
        writeDataPropertyDomainIfNotFiltered(property, writtenAxioms);
        writeDataPropertyRangeIfNotFiltered(property, writtenAxioms);
        writeDataPropertyCharacteristicsIfNotFiltered(property, writtenAxioms);
        writeDataPropertySubPropertyOfIfNotFiltered(property, writtenAxioms);
        writeDataPropertyEquivalentToIfNotFiltered(property, writtenAxioms);
        writeDataPropertyDisjointWithIfNotFiltered(property, writtenAxioms);
        writeEntitySectionEnd(DATA_PROPERTY.toString());
        return writtenAxioms;
    }

    private void writeDataPropertyDisjointWithIfNotFiltered(
            OWLDataProperty property, Set<OWLAxiom> axioms) {
        if (isFiltered(AxiomType.DISJOINT_DATA_PROPERTIES)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap props = new SectionMap();
            for (OWLDisjointDataPropertiesAxiom ax : ontology
                    .getDisjointDataPropertiesAxioms(property)) {
                if (ax.getProperties().size() == 2 && isDisplayed(ax)) {
                    props.add(
                            ax.getPropertiesMinus(property).iterator().next(),
                            ax);
                    axioms.add(ax);
                }
            }
            props.remove(property);
            writeSection(DISJOINT_WITH, props, ",", true, ontology);
        }
    }

    private void writeDataPropertyEquivalentToIfNotFiltered(
            OWLDataProperty property, Set<OWLAxiom> axioms) {
        if (isFiltered(AxiomType.EQUIVALENT_DATA_PROPERTIES)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap props = new SectionMap();
            for (OWLEquivalentDataPropertiesAxiom ax : ontology
                    .getEquivalentDataPropertiesAxioms(property)) {
                if (isDisplayed(ax) && ax.getProperties().size() == 2) {
                    props.add(
                            ax.getPropertiesMinus(property).iterator().next(),
                            ax);
                    axioms.add(ax);
                }
            }
            writeSection(EQUIVALENT_TO, props, ",", true, ontology);
        }
    }

    private void writeDataPropertySubPropertyOfIfNotFiltered(
            OWLDataProperty property, Set<OWLAxiom> axioms) {
        if (isFiltered(AxiomType.SUB_DATA_PROPERTY)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap supers = new SectionMap();
            for (OWLSubDataPropertyOfAxiom ax : ontology
                    .getDataSubPropertyAxiomsForSubProperty(property)) {
                if (isDisplayed(ax)) {
                    supers.add(ax.getSuperProperty(), ax);
                    axioms.add(ax);
                }
            }
            writeSection(SUB_PROPERTY_OF, supers, ",", true, ontology);
        }
    }

    private void writeDataPropertyRangeIfNotFiltered(OWLDataProperty property,
            Set<OWLAxiom> axioms) {
        if (isFiltered(AxiomType.DATA_PROPERTY_RANGE)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap ranges = new SectionMap();
            for (OWLDataPropertyRangeAxiom ax : ontology
                    .getDataPropertyRangeAxioms(property)) {
                if (isDisplayed(ax)) {
                    ranges.add(ax.getRange(), ax);
                    axioms.add(ax);
                }
            }
            writeSection(RANGE, ranges, ",", true, ontology);
        }
    }

    private void writeDataPropertyDomainIfNotFiltered(OWLDataProperty property,
            Set<OWLAxiom> axioms) {
        if (isFiltered(AxiomType.DATA_PROPERTY_DOMAIN)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap domains = new SectionMap();
            for (OWLDataPropertyDomainAxiom ax : ontology
                    .getDataPropertyDomainAxioms(property)) {
                if (isDisplayed(ax)) {
                    domains.add(ax.getDomain(), ax);
                    axioms.add(ax);
                }
            }
            writeSection(DOMAIN, domains, ",", true, ontology);
        }
    }

    private void writeDataPropertyCharacteristicsIfNotFiltered(
            OWLDataProperty property, Set<OWLAxiom> axioms) {
        if (isFiltered(AxiomType.FUNCTIONAL_DATA_PROPERTY)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap characteristics = new SectionMap();
            for (OWLAxiom ax : ontology
                    .getFunctionalDataPropertyAxioms(property)) {
                if (isDisplayed(ax)) {
                    characteristics.add(FUNCTIONAL.toString(), ax);
                    axioms.add(ax);
                }
            }
            writeSection(CHARACTERISTICS, characteristics, ",", true, ontology);
        }
    }

    /**
     * @param individual
     *        the individual
     * @return the sets the
     */
    public Set<OWLAxiom> write(OWLIndividual individual) {
        Set<OWLAxiom> writtenAxioms = new HashSet<OWLAxiom>();
        writtenAxioms.addAll(writeEntityStart(INDIVIDUAL, individual));
        writeIndividualTypesIfNotFiltered(individual, writtenAxioms);
        writeIndividualFactsIfNotFiltered(individual);
        writeIndividualSameAsIfNotFiltered(individual, writtenAxioms);
        writeIndividualDifferentFromIfNotFiltered(individual, writtenAxioms);
        writeEntitySectionEnd(INDIVIDUAL.toString());
        return writtenAxioms;
    }

    private void writeIndividualDifferentFromIfNotFiltered(
            OWLIndividual individual, Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.DIFFERENT_INDIVIDUALS)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            Set<OWLIndividual> inds = createTreeSet();
            for (OWLDifferentIndividualsAxiom ax : ontology
                    .getDifferentIndividualAxioms(individual)) {
                if (ax.getIndividuals().size() == 2 && isDisplayed(ax)) {
                    inds.addAll(ax.getIndividuals());
                    writtenAxioms.add(ax);
                }
            }
            inds.remove(individual);
            writeSection(DIFFERENT_FROM, inds, ",", true, ontology);
        }
    }

    private void writeIndividualSameAsIfNotFiltered(OWLIndividual individual,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.SAME_INDIVIDUAL)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            Set<OWLIndividual> inds = createTreeSet();
            for (OWLSameIndividualAxiom ax : ontology
                    .getSameIndividualAxioms(individual)) {
                if (isDisplayed(ax)) {
                    inds.addAll(ax.getIndividuals());
                    writtenAxioms.add(ax);
                }
            }
            inds.remove(individual);
            writeSection(SAME_AS, inds, ",", true, ontology);
        }
    }

    private void writeIndividualFactsIfNotFiltered(OWLIndividual individual) {
        for (OWLOntology ontology : getOntologies()) {
            List<OWLPropertyAssertionAxiom<?, ?>> assertions = new ArrayList<OWLPropertyAssertionAxiom<?, ?>>();
            if (!isFiltered(AxiomType.OBJECT_PROPERTY_ASSERTION)) {
                assertions.addAll(ontology
                        .getObjectPropertyAssertionAxioms(individual));
            }
            if (!isFiltered(AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION)) {
                assertions.addAll(ontology
                        .getNegativeObjectPropertyAssertionAxioms(individual));
            }
            if (!isFiltered(AxiomType.DATA_PROPERTY_ASSERTION)) {
                assertions.addAll(ontology
                        .getDataPropertyAssertionAxioms(individual));
            }
            if (!isFiltered(AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION)) {
                assertions.addAll(ontology
                        .getNegativeDataPropertyAssertionAxioms(individual));
            }
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
    }

    private void writeIndividualTypesIfNotFiltered(OWLIndividual individual,
            Set<OWLAxiom> writtenAxioms) {
        if (isFiltered(AxiomType.CLASS_ASSERTION)) {
            return;
        }
        for (OWLOntology ontology : getOntologies()) {
            SectionMap expressions = new SectionMap();
            for (OWLClassAssertionAxiom ax : ontology
                    .getClassAssertionAxioms(individual)) {
                if (isDisplayed(ax)) {
                    expressions.add(ax.getClassExpression(), ax);
                    writtenAxioms.add(ax);
                }
            }
            writeSection(TYPES, expressions, ",", true, ontology);
        }
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
            for (OWLOntology ontology : getOntologies()) {
                Set<OWLDataRange> dataRanges = createTreeSet();
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
     * @return the sets the
     */
    public Set<OWLAxiom> write(SWRLRule rule) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>(1);
        for (OWLOntology ontology : getOntologies()) {
            if (ontology.containsAxiom(rule)) {
                writeSection(RULE, Collections.singleton(rule), "", true,
                        ontology);
                axioms.add(rule);
            }
        }
        return axioms;
    }

    /**
     * Gets the ontologies.
     * 
     * @return the ontologies
     */
    public Set<OWLOntology> getOntologies() {
        return ontologies;
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

    /**
     * Writes ontologies list if ontology list rendering is enabled. Nothing is
     * written if ontology list rendering is not enabled.
     * 
     * @param ontologiesList
     *        the ontologies list. Not {@code null}.
     */
    private void writeOntologiesList(OWLOntology... ontologiesList) {
        if (!shouldRenderOntologyLists()) {
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
     * Detemines whether or not ontology lists should be rendered.
     * 
     * @return {@code true} if ontology lists should be rendered, otherwise
     *         {@code false}.
     */
    private boolean shouldRenderOntologyLists() {
        // Render extensions overrides the renderOntologyLists setting for
        // backwards compatibility.
        return renderOntologyLists || renderExtensions;
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

    /**
     * @param property
     *        the property
     * @return the sets the
     */
    public Set<OWLAxiom> write(OWLAnnotationProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(ANNOTATION_PROPERTY, property));
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for (OWLOntology ont : getOntologies()) {
                Set<OWLAnnotation> annos = createTreeSet();
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
            for (OWLOntology ont : getOntologies()) {
                Set<OWLAnnotationProperty> props = createTreeSet();
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
            for (OWLOntology ont : getOntologies()) {
                Set<IRI> iris = createTreeSet();
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
            for (OWLOntology ont : getOntologies()) {
                Set<IRI> iris = createTreeSet();
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
     * @return the sets the
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
     * @return the sets the
     */
    public Set<OWLAnnotationAssertionAxiom> writeAnnotations(
            OWLAnnotationSubject subject) {
        Set<OWLAnnotationAssertionAxiom> axioms = new HashSet<OWLAnnotationAssertionAxiom>();
        if (!isFiltered(AxiomType.ANNOTATION_ASSERTION)) {
            for (OWLOntology ontology : getOntologies()) {
                SectionMap sectionMap = new SectionMap();
                // Set<OWLAnnotation> annos = createTreeSet();
                for (OWLAnnotationAssertionAxiom ax : ontology
                        .getAnnotationAssertionAxioms(subject)) {
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
    public void writeSection(ManchesterOWLSyntax keyword, SectionMap content,
            String delimeter, boolean newline, OWLOntology... ontologiesList) {
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
            for (Iterator<?> it = content.getSectionObjects().iterator(); it
                    .hasNext();) {
                Object obj = it.next();
                Set<Set<OWLAnnotation>> annotationSets = content
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

    private <E extends OWLObject> TreeSet<E> createTreeSet() {
        return new TreeSet<E>();
    }

    private <E extends OWLObject> TreeSet<E> createTreeSet(
            Collection<? extends E> fromCollection) {
        return new TreeSet<E>(fromCollection);
    }

    /**
     * The Class DefaultRenderingDirector.
     */
    private static class DefaultRenderingDirector implements RenderingDirector {

        /**
         * Instantiates a new default rendering director.
         */
        public DefaultRenderingDirector() {}

        @Override
        public boolean renderEmptyFrameSection(
                ManchesterOWLSyntax frameSectionKeyword,
                OWLOntology... ontologies) {
            return false;
        }
    }
}
