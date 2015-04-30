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
package org.semanticweb.owlapi.functional.renderer;

import static org.semanticweb.owlapi.model.parameters.Imports.*;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LABEL;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.EscapeUtils;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * The Class OWLObjectRenderer.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class FunctionalSyntaxObjectRenderer implements OWLObjectVisitor {

    @Nonnull
    private DefaultPrefixManager defaultPrefixManager;
    private PrefixManager prefixManager;
    protected final OWLOntology ont;
    private final Writer writer;
    private boolean writeEntitiesAsURIs = true;
    private boolean addMissingDeclarations = true;
    protected AnnotationValueShortFormProvider labelMaker = null;

    /**
     * @param ontology
     *        the ontology
     * @param writer
     *        the writer
     */
    public FunctionalSyntaxObjectRenderer(@Nonnull OWLOntology ontology,
        Writer writer) {
        ont = ontology;
        this.writer = writer;
        defaultPrefixManager = new DefaultPrefixManager();
        prefixManager = defaultPrefixManager;
        OWLDocumentFormat ontologyFormat = ontology.getOWLOntologyManager()
            .getOntologyFormat(ontology);
        // reuse the setting on the existing format, if there is one
        if (ontologyFormat != null) {
            addMissingDeclarations = ontologyFormat.isAddMissingTypes();
        }
        if (ontologyFormat instanceof PrefixDocumentFormat) {
            prefixManager
                .copyPrefixesFrom((PrefixDocumentFormat) ontologyFormat);
            prefixManager.setPrefixComparator(
                ((PrefixDocumentFormat) ontologyFormat).getPrefixComparator());
        }
        if (!ontology.isAnonymous()) {
            String existingDefault = prefixManager.getDefaultPrefix();
            String ontologyIRIString = ontology.getOntologyID().getOntologyIRI()
                .get().toString();
            if (existingDefault == null
                || !existingDefault.startsWith(ontologyIRIString)) {
                String defaultPrefix = ontologyIRIString;
                if (!ontologyIRIString.endsWith("/")) {
                    defaultPrefix = ontologyIRIString + '#';
                }
                prefixManager.setDefaultPrefix(defaultPrefix);
            }
        }
        Map<OWLAnnotationProperty, List<String>> prefLangMap = new HashMap<>();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLDataFactory df = manager.getOWLDataFactory();
        OWLAnnotationProperty labelProp = df
            .getOWLAnnotationProperty(RDFS_LABEL.getIRI());
        labelMaker = new AnnotationValueShortFormProvider(
            Collections.singletonList(labelProp), prefLangMap, manager,
            defaultPrefixManager);
    }

    /**
     * Set the add missing declaration flag.
     * 
     * @param flag
     *        new value
     */
    public void setAddMissingDeclarations(boolean flag) {
        addMissingDeclarations = flag;
    }

    /**
     * @param prefixManager
     *        the new prefix manager
     */
    public void setPrefixManager(PrefixManager prefixManager) {
        this.prefixManager = prefixManager;
        if (prefixManager instanceof DefaultPrefixManager) {
            defaultPrefixManager = (DefaultPrefixManager) prefixManager;
        }
    }

    protected void writePrefix(@Nonnull String prefix,
        @Nonnull String namespace) {
        write("Prefix");
        writeOpenBracket();
        write(prefix);
        write("=");
        write("<");
        write(namespace);
        write(">");
        writeCloseBracket();
        writeReturn();
    }

    protected void writePrefixes() {
        prefixManager.getPrefixName2PrefixMap()
            .forEach((k, v) -> writePrefix(k, v));
    }

    private void write(@Nonnull OWLXMLVocabulary v) {
        write(v.getShortForm());
    }

    private void write(@Nonnull String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(@Nonnull IRI iri) {
        String qname = prefixManager.getPrefixIRI(iri);
        if (qname != null) {
            boolean lastCharIsColon = qname.charAt(qname.length() - 1) == ':';
            if (!lastCharIsColon) {
                write(qname);
                return;
            }
        }
        writeFullIRI(iri);
    }

    private void writeFullIRI(@Nonnull IRI iri) {
        write("<");
        write(iri.toString());
        write(">");
    }

    @Override
    public void visit(@Nonnull OWLOntology ontology) {
        writePrefixes();
        writeReturn();
        writeReturn();
        write(ONTOLOGY);
        writeOpenBracket();
        if (!ontology.isAnonymous()) {
            writeFullIRI(ontology.getOntologyID().getOntologyIRI().get());
            Optional<IRI> versionIRI = ontology.getOntologyID().getVersionIRI();
            if (versionIRI.isPresent()) {
                writeReturn();
                writeFullIRI(versionIRI.get());
            }
            writeReturn();
        }
        ontology.importsDeclarations().forEach(decl -> {
            write(IMPORT);
            writeOpenBracket();
            writeFullIRI(decl.getIRI());
            writeCloseBracket();
            writeReturn();
        } );
        ontology.annotations().sorted().forEach(a -> {
            a.accept(this);
            writeReturn();
        } );
        writeReturn();
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        List<OWLEntity> signature = sortOptionally(ontology.signature());
        Collection<IRI> illegals = OWLDocumentFormat.determineIllegalPunnings(
            addMissingDeclarations, signature, ont.getPunnedIRIs(INCLUDED));
        signature.forEach(e -> writeDeclarations(e, writtenAxioms, illegals));
        writeSortedEntities("Annotation Properties", "Annotation Property",
            ontology.annotationPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Object Properties", "Object Property",
            ontology.objectPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Data Properties", "Data Property",
            ontology.dataPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Datatypes", "Datatype",
            ontology.datatypesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Classes", "Class",
            ontology.classesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities("Named Individuals", "Individual",
            ontology.individualsInSignature(EXCLUDED), writtenAxioms);
        ontology.axioms().filter(ax -> !writtenAxioms.contains(ax))
            .forEach(ax -> {
                ax.accept(this);
                writeReturn();
            } );
        signature.forEach(e -> writeAxioms(e, writtenAxioms));
        ontology.axioms().filter(ax -> !writtenAxioms.contains(ax))
            .forEach(ax -> {
                ax.accept(this);
                writeReturn();
            } );
        writeCloseBracket();
        flush();
    }

    private void writeSortedEntities(String bannerComment,
        String entityTypeName, @Nonnull Stream<? extends OWLEntity> entities,
        Set<OWLAxiom> writtenAxioms) {
        List<? extends OWLEntity> sortOptionally = sortOptionally(entities);
        if (!sortOptionally.isEmpty()) {
            writeEntities(bannerComment, entityTypeName, sortOptionally,
                writtenAxioms);
            writeln();
        }
    }

    private void writeln() {
        writeReturn();
    }

    private void writeln(@Nonnull String s) {
        write(s);
        writeReturn();
    }

    private void writeEntities(String comment, String entityTypeName,
        List<? extends OWLEntity> entities, Set<OWLAxiom> writtenAxioms) {
        boolean haveWrittenBanner = false;
        for (OWLEntity owlEntity : entities) {
            List<? extends OWLAxiom> axiomsForEntity = asList(
                getUnsortedAxiomsForEntity(owlEntity)
                    .filter(ax -> !writtenAxioms.contains(ax)));
            List<OWLAnnotationAssertionAxiom> list = asList(
                ont.annotationAssertionAxioms(owlEntity.getIRI())
                    .filter(ax -> !writtenAxioms.contains(ax)));
            if (axiomsForEntity.isEmpty() && list.isEmpty()) {
                continue;
            }
            if (!haveWrittenBanner) {
                writeln("############################");
                writeln("#   " + comment);
                writeln("############################");
                writeln();
                haveWrittenBanner = true;
            }
            writeEntity2(owlEntity, entityTypeName,
                sortOptionally(axiomsForEntity), sortOptionally(list),
                writtenAxioms);
        }
    }

    /**
     * Writes out the axioms that define the specified entity.
     * 
     * @param entity
     *        The entity
     * @return The set of axioms that was written out
     */
    @Nonnull
    protected Set<OWLAxiom> writeAxioms(@Nonnull OWLEntity entity) {
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        writeAxioms(entity, writtenAxioms);
        return writtenAxioms;
    }

    /**
     * Writes out the axioms that define the specified entity.
     * 
     * @param entity
     *        The entity
     * @return The set of axioms that was written out
     */
    @Nonnull
    protected Set<OWLAxiom> writeEntity(@Nonnull OWLEntity entity) {
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        writeEntity(entity, writtenAxioms);
        return writtenAxioms;
    }

    protected void writeEntity(@Nonnull OWLEntity entity,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms) {
        writeEntity2(entity, "",
            sortOptionally(getUnsortedAxiomsForEntity(entity)),
            sortOptionally(ont.annotationAssertionAxioms(entity.getIRI())),
            alreadyWrittenAxioms);
    }

    protected void writeEntity2(@Nonnull OWLEntity entity,
        String entityTypeName,
        @Nonnull List<? extends OWLAxiom> axiomsForEntity,
        @Nonnull List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms) {
        writeln("# " + entityTypeName + ": " + getIRIString(entity) + " ("
            + getEntityLabel(entity) + ")");
        writeln();
        sortOptionally(annotationAssertionAxioms).stream()
            .filter(ax -> alreadyWrittenAxioms.add(ax)).forEach(ax -> {
                ax.accept(this);
                writeReturn();
            } );
        List<? extends OWLAxiom> axs = axiomsForEntity;
        for (OWLAxiom ax : axs) {
            if (ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS)) {
                continue;
            }
            if (ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
                && ((OWLDisjointClassesAxiom) ax).classExpressions()
                    .count() > 2) {
                continue;
            }
            ax.accept(this);
            alreadyWrittenAxioms.add(ax);
            writeReturn();
        }
        writeln();
    }

    @Nonnull
    private Stream<? extends OWLAxiom> getUnsortedAxiomsForEntity(
        @Nonnull OWLEntity entity) {
        return entity
            .accept(new OWLEntityVisitorEx<Stream<? extends OWLAxiom>>() {

                @Nonnull
                @Override
                public Stream<? extends OWLAxiom> visit(@Nonnull OWLClass cls) {
                    return ont.axioms(cls, EXCLUDED);
                }

                @Nonnull
                @Override
                public Stream<? extends OWLAxiom> visit(
                    @Nonnull OWLObjectProperty property) {
                    return ont.axioms(property, EXCLUDED);
                }

                @Nonnull
                @Override
                public Stream<? extends OWLAxiom> visit(
                    @Nonnull OWLDataProperty property) {
                    return ont.axioms(property, EXCLUDED);
                }

                @Nonnull
                @Override
                public Stream<? extends OWLAxiom> visit(
                    @Nonnull OWLNamedIndividual individual) {
                    return ont.axioms(individual, EXCLUDED);
                }

                @Nonnull
                @Override
                public Stream<? extends OWLAxiom> visit(
                    @Nonnull OWLDatatype datatype) {
                    return ont.axioms(datatype, EXCLUDED);
                }

                @Nonnull
                @Override
                public Stream<? extends OWLAxiom> visit(
                    @Nonnull OWLAnnotationProperty property) {
                    return ont.axioms(property, EXCLUDED);
                }
            });
    }

    @Nonnull
    private String getIRIString(@Nonnull OWLEntity entity) {
        return defaultPrefixManager.getShortForm(entity);
    }

    private String getEntityLabel(@Nonnull OWLEntity entity) {
        return labelMaker.getShortForm(entity);
    }

    private void writeAxioms(@Nonnull OWLEntity entity,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms) {
        writeAnnotations(entity, alreadyWrittenAxioms);
        List<? extends OWLAxiom> axs = sortOptionally(
            getUnsortedAxiomsForEntity(entity));
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        for (OWLAxiom ax : axs) {
            if (alreadyWrittenAxioms.contains(ax)) {
                continue;
            }
            if (ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS)) {
                continue;
            }
            if (ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
                && ((OWLDisjointClassesAxiom) ax).classExpressions()
                    .count() > 2) {
                continue;
            }
            ax.accept(this);
            writtenAxioms.add(ax);
            writeReturn();
        }
        alreadyWrittenAxioms.addAll(writtenAxioms);
    }

    /**
     * Writes out the declaration axioms for the specified entity.
     * 
     * @param entity
     *        The entity
     * @return The axioms that were written out
     */
    @Nonnull
    protected Set<OWLAxiom> writeDeclarations(@Nonnull OWLEntity entity) {
        Set<OWLAxiom> axioms = new HashSet<>();
        sortOptionally(ont.declarationAxioms(entity)).forEach(ax -> {
            ax.accept(this);
            axioms.add(ax);
            writeReturn();
        } );
        return axioms;
    }

    private void writeDeclarations(@Nonnull OWLEntity entity,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms, Collection<IRI> illegals) {
        Collection<OWLDeclarationAxiom> axioms = sortOptionally(
            ont.declarationAxioms(entity));
        for (OWLDeclarationAxiom ax : axioms) {
            if (alreadyWrittenAxioms.add(ax)) {
                ax.accept(this);
                writeReturn();
            }
        }
        // if multiple illegal declarations already exist, they have already
        // been outputted
        // the renderer cannot take responsibility for removing them
        // It should not add declarations for illegally punned entities here,
        // though
        if (addMissingDeclarations && axioms.isEmpty()) {
            // if declarations should be added, check if the IRI is illegally
            // punned
            if (!entity.isBuiltIn() && !illegals.contains(entity.getIRI())
                && !ont.isDeclared(entity, Imports.INCLUDED)) {
                OWLDeclarationAxiom declaration = ont.getOWLOntologyManager()
                    .getOWLDataFactory().getOWLDeclarationAxiom(entity);
                declaration.accept(this);
                writeReturn();
            }
        }
    }

    /**
     * Writes of the annotation for the specified entity.
     * 
     * @param entity
     *        The entity
     * @param alreadyWrittenAxioms
     *        already written axioms, to be updated with the newly written
     *        axioms
     */
    protected void writeAnnotations(@Nonnull OWLEntity entity,
        @Nonnull Set<OWLAxiom> alreadyWrittenAxioms) {
        sortOptionally(ont.annotationAssertionAxioms(entity.getIRI())).stream()
            .filter(ax -> alreadyWrittenAxioms.add(ax)).forEach(ax -> {
                ax.accept(this);
                writeReturn();
            } );
    }

    /**
     * Write.
     * 
     * @param v
     *        the v
     * @param o
     *        the o
     */
    protected void write(@Nonnull OWLXMLVocabulary v, @Nonnull OWLObject o) {
        write(v);
        writeOpenBracket();
        o.accept(this);
        writeCloseBracket();
    }

    private void write(@Nonnull Stream<? extends OWLObject> objects) {
        write(asList(objects));
    }

    private void write(@Nonnull List<? extends OWLObject> objects) {
        if (objects.size() > 1) {
            for (Iterator<? extends OWLObject> it = objects.iterator(); it
                .hasNext();) {
                it.next().accept(this);
                if (it.hasNext()) {
                    writeSpace();
                }
            }
        } else if (objects.size() == 1) {
            objects.iterator().next().accept(this);
        }
    }

    protected void writeOpenBracket() {
        write("(");
    }

    protected void writeCloseBracket() {
        write(")");
    }

    protected void writeSpace() {
        write(" ");
    }

    protected void writeReturn() {
        write("\n");
    }

    protected void writeAnnotations(@Nonnull OWLAxiom ax) {
        ax.annotations().sorted().forEach(a -> {
            a.accept(this);
            writeSpace();
        } );
    }

    protected void writeAxiomStart(@Nonnull OWLXMLVocabulary v,
        @Nonnull OWLAxiom axiom) {
        write(v);
        writeOpenBracket();
        writeAnnotations(axiom);
    }

    protected void writeAxiomEnd() {
        writeCloseBracket();
    }

    protected void writePropertyCharacteristic(@Nonnull OWLXMLVocabulary v,
        @Nonnull OWLAxiom ax, @Nonnull OWLPropertyExpression prop) {
        writeAxiomStart(v, ax);
        prop.accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(ASYMMETRIC_OBJECT_PROPERTY, axiom,
            axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        writeAxiomStart(CLASS_ASSERTION, axiom);
        axiom.getClassExpression().accept(this);
        writeSpace();
        axiom.getIndividual().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_DATA_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDeclarationAxiom axiom) {
        writeAxiomStart(DECLARATION, axiom);
        writeEntitiesAsURIs = false;
        axiom.getEntity().accept(this);
        writeEntitiesAsURIs = true;
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        List<OWLIndividual> individuals = axiom.getIndividualsAsList();
        if (individuals.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DIFFERENT_INDIVIDUALS, axiom);
        write(sortOptionally(individuals));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = asList(
            axiom.classExpressions());
        if (classExpressions.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_CLASSES, axiom);
        write(sortOptionally(classExpressions));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> properties = asSet(axiom.properties());
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_DATA_PROPERTIES, axiom);
        write(sortOptionally(properties));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> properties = asSet(axiom.properties());
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_OBJECT_PROPERTIES, axiom);
        write(sortOptionally(properties));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        writeAxiomStart(DISJOINT_UNION, axiom);
        axiom.getOWLClass().accept(this);
        writeSpace();
        write(sortOptionally(axiom.classExpressions()));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        writeAxiomStart(ANNOTATION_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getValue().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        List<OWLClassExpression> classExpressions = asList(
            axiom.classExpressions());
        if (classExpressions.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_CLASSES, axiom);
        write(sortOptionally(classExpressions));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> properties = asSet(axiom.properties());
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_DATA_PROPERTIES, axiom);
        write(sortOptionally(properties));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> properties = asSet(axiom.properties());
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_OBJECT_PROPERTIES, axiom);
        write(sortOptionally(properties));
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_DATA_PROPERTY, axiom,
            axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_OBJECT_PROPERTY, axiom,
            axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiom,
            axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        writeAxiomStart(INVERSE_OBJECT_PROPERTIES, axiom);
        axiom.getFirstProperty().accept(this);
        writeSpace();
        axiom.getSecondProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(IRREFLEXIVE_OBJECT_PROPERTY, axiom,
            axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_DATA_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        write(OBJECT_PROPERTY_CHAIN);
        writeOpenBracket();
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getPropertyChain()
            .iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
            }
        }
        writeCloseBracket();
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(REFLEXIVE_OBJECT_PROPERTY, axiom,
            axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        List<OWLIndividual> individuals = axiom.getIndividualsAsList();
        if (individuals.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(SAME_INDIVIDUAL, axiom);
        write(individuals);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        writeAxiomStart(SUB_CLASS_OF, axiom);
        axiom.getSubClass().accept(this);
        writeSpace();
        axiom.getSuperClass().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(SYMMETRIC_OBJECT_PROPERTY, axiom,
            axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(TRANSITIVE_OBJECT_PROPERTY, axiom,
            axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLClass ce) {
        if (!writeEntitiesAsURIs) {
            write(CLASS);
            writeOpenBracket();
        }
        ce.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    private <F extends OWLPropertyRange> void writeRestriction(
        @Nonnull OWLXMLVocabulary v,
        @Nonnull OWLCardinalityRestriction<F> restriction,
        @Nonnull OWLPropertyExpression p) {
        write(v);
        writeOpenBracket();
        write(Integer.toString(restriction.getCardinality()));
        writeSpace();
        p.accept(this);
        if (restriction.isQualified()) {
            writeSpace();
            restriction.getFiller().accept(this);
        }
        writeCloseBracket();
    }

    private void writeRestriction(@Nonnull OWLXMLVocabulary v,
        @Nonnull OWLQuantifiedDataRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(@Nonnull OWLXMLVocabulary v,
        @Nonnull OWLQuantifiedObjectRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(@Nonnull OWLXMLVocabulary v,
        @Nonnull OWLPropertyExpression prop, @Nonnull OWLObject filler) {
        write(v);
        writeOpenBracket();
        prop.accept(this);
        writeSpace();
        filler.accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(DATA_ALL_VALUES_FROM, ce);
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality ce) {
        writeRestriction(DATA_EXACT_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality ce) {
        writeRestriction(DATA_MAX_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality ce) {
        writeRestriction(DATA_MIN_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(DATA_SOME_VALUES_FROM, ce);
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue ce) {
        writeRestriction(DATA_HAS_VALUE, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(OBJECT_ALL_VALUES_FROM, ce);
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf ce) {
        write(OBJECT_COMPLEMENT_OF, ce.getOperand());
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality ce) {
        writeRestriction(OBJECT_EXACT_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        write(OBJECT_INTERSECTION_OF);
        writeOpenBracket();
        write(sortOptionally(ce.operands()));
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality ce) {
        writeRestriction(OBJECT_MAX_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality ce) {
        writeRestriction(OBJECT_MIN_CARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf ce) {
        write(OBJECT_ONE_OF);
        writeOpenBracket();
        write(sortOptionally(ce.individuals()));
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf ce) {
        write(OBJECT_HAS_SELF, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(OBJECT_SOME_VALUES_FROM, ce);
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf ce) {
        write(OBJECT_UNION_OF);
        writeOpenBracket();
        write(sortOptionally(ce.operands()));
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue ce) {
        writeRestriction(OBJECT_HAS_VALUE, ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        write(DATA_COMPLEMENT_OF, node.getDataRange());
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        write(DATA_ONE_OF);
        writeOpenBracket();
        write(sortOptionally(node.values()));
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        if (!writeEntitiesAsURIs) {
            write(DATATYPE);
            writeOpenBracket();
        }
        node.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        write(DATATYPE_RESTRICTION);
        writeOpenBracket();
        node.getDatatype().accept(this);
        sortOptionally(node.facetRestrictions()).forEach(r -> {
            writeSpace();
            r.accept(this);
        } );
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        write(node.getFacet().getIRI());
        writeSpace();
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        write("\"");
        write(EscapeUtils.escapeString(node.getLiteral()));
        write("\"");
        if (node.hasLang()) {
            write("@");
            write(node.getLang());
        } else if (!node.isRDFPlainLiteral()) {
            write("^^");
            write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(@Nonnull OWLDataProperty property) {
        if (!writeEntitiesAsURIs) {
            write(DATA_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectProperty property) {
        if (!writeEntitiesAsURIs) {
            write(OBJECT_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        write(OBJECT_INVERSE_OF);
        writeOpenBracket();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual individual) {
        if (!writeEntitiesAsURIs) {
            write(NAMED_INDIVIDUAL);
            writeOpenBracket();
        }
        individual.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLHasKeyAxiom axiom) {
        writeAxiomStart(HAS_KEY, axiom);
        axiom.getClassExpression().accept(this);
        writeSpace();
        writeOpenBracket();
        for (Iterator<? extends OWLPropertyExpression> it = sortOptionally(
            axiom.objectPropertyExpressions()).iterator(); it.hasNext();) {
            OWLPropertyExpression prop = it.next();
            prop.accept(this);
            if (it.hasNext()) {
                writeSpace();
            }
        }
        writeCloseBracket();
        writeSpace();
        writeOpenBracket();
        for (Iterator<? extends OWLPropertyExpression> it = sortOptionally(
            axiom.dataPropertyExpressions()).iterator(); it.hasNext();) {
            OWLPropertyExpression prop = it.next();
            prop.accept(this);
            if (it.hasNext()) {
                writeSpace();
            }
        }
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_ANNOTATION_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull OWLDataIntersectionOf node) {
        write(DATA_INTERSECTION_OF);
        writeOpenBracket();
        write(sortOptionally(node.operands()));
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDataUnionOf node) {
        write(DATA_UNION_OF);
        writeOpenBracket();
        write(sortOptionally(node.operands()));
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLAnnotationProperty property) {
        if (!writeEntitiesAsURIs) {
            write(ANNOTATION_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(@Nonnull OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(@Nonnull IRI iri) {
        write(iri);
    }

    @Override
    public void visit(@Nonnull OWLAnnotation node) {
        write(ANNOTATION);
        writeOpenBracket();
        node.annotations().sorted().forEach(a -> {
            a.accept(this);
            writeSpace();
        } );
        node.getProperty().accept(this);
        writeSpace();
        node.getValue().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        writeAxiomStart(DATATYPE_DEFINITION, axiom);
        axiom.getDatatype().accept(this);
        writeSpace();
        axiom.getDataRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull SWRLRule rule) {
        writeAxiomStart(DL_SAFE_RULE, rule);
        write(BODY);
        writeOpenBracket();
        write(rule.body());
        writeCloseBracket();
        write(HEAD);
        writeOpenBracket();
        write(rule.head());
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(@Nonnull SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLClassAtom node) {
        write(CLASS_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLDataRangeAtom node) {
        write(DATA_RANGE_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLObjectPropertyAtom node) {
        write(OBJECT_PROPERTY_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLDataPropertyAtom node) {
        write(DATA_PROPERTY_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLBuiltInAtom node) {
        write(BUILT_IN_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        write(node.getArguments());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLVariable node) {
        write(VARIABLE);
        writeOpenBracket();
        node.getIRI().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        write(DIFFERENT_INDIVIDUALS_ATOM);
        writeOpenBracket();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull SWRLSameIndividualAtom node) {
        write(SAME_INDIVIDUAL_ATOM);
        writeOpenBracket();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }
}
