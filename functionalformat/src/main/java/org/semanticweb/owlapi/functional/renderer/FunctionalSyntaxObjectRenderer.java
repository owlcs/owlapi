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

import static org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserConstants.*;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LABEL;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.semanticweb.owlapi.functional.parser.TokenMap;
import org.semanticweb.owlapi.annotations.Renders;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi.utilities.ShortFormProvider;
import org.semanticweb.owlapi.utility.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.utility.EscapeUtils;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class OWLObjectRenderer.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@Renders(FunctionalSyntaxDocumentFormat.class)
public class FunctionalSyntaxObjectRenderer implements OWLObjectVisitor, OWLObjectRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionalSyntaxObjectRenderer.class);
    protected final Optional<OWLOntology> ont;
    private final Writer writer;
    protected Optional<AnnotationValueShortFormProvider> labelMaker = Optional.empty();
    private Optional<PrefixManager> prefixManager = Optional.empty();
    private boolean writeEntitiesAsURIs = true;
    private boolean addMissingDeclarations = true;
    private boolean prettyPrint = false;
    private int tabIndex = 0;

    /** Empty constructor, for use from ToStringRenderer */
    @Inject
    public FunctionalSyntaxObjectRenderer() {
        this(null, new StringWriter());
    }

    /**
     * @param ontology
     *        the ontology
     * @param writer
     *        the writer
     */
    public FunctionalSyntaxObjectRenderer(@Nullable OWLOntology ontology, Writer writer) {
        ont = Optional.ofNullable(ontology);
        this.writer = writer;
        ont.ifPresent(this::initFromOntology);
    }

    private void initFromOntology(OWLOntology o) {
        prefixManager = Optional.of(new PrefixManagerImpl(o.getPrefixManager()));
        OntologyConfigurator config = o.getOWLOntologyManager().getOntologyConfigurator();
        addMissingDeclarations = config.shouldAddMissingTypes();
        prettyPrint = config.shouldPrettyPrintFunctionalSyntax();
        if (o.isNamed() && prefixManager.get().getDefaultPrefix() == null) {
            String existingDefault = prefixManager.get().getDefaultPrefix();
            String ontologyIRIString = o.getOntologyID().getOntologyIRI().map(Object::toString).orElse("");
            if (existingDefault == null || !existingDefault.startsWith(ontologyIRIString)) {
                String defaultPrefix = ontologyIRIString;
                if (!ontologyIRIString.endsWith("/") && !ontologyIRIString.endsWith("#")) {
                    defaultPrefix = ontologyIRIString + '#';
                }
                prefixManager.get().withDefaultPrefix(defaultPrefix);
            }
        }
        Map<OWLAnnotationProperty, List<String>> prefLangMap = new HashMap<>();
        OWLOntologyManager manager = o.getOWLOntologyManager();
        OWLDataFactory df = manager.getOWLDataFactory();
        OWLAnnotationProperty labelProp = df.getOWLAnnotationProperty(RDFS_LABEL.getIRI());
        labelMaker = Optional.of(new AnnotationValueShortFormProvider(Collections.singletonList(labelProp), prefLangMap,
            manager, prefixManager.get()));
    }

    private void accept(OWLObject o) {
        o.accept(this);
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

    @Override
    public String render(OWLObject object) {
        accept(object);
        return writer.toString();
    }

    @Override
    public void setPrefixManager(PrefixManager prefixManager) {
        this.prefixManager = Optional.ofNullable(prefixManager);
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        prefixManager.ifPresent(p -> p.setShortFormProvider(shortFormProvider));
    }

    protected void writePrefix(String prefix, String namespace) {
        write(String.format("Prefix(%s=<%s>)\n", prefix, namespace));
    }

    protected void writePrefixes() {
        prefixManager.ifPresent(p -> p.getPrefixName2PrefixMap().forEach(this::writePrefix));
    }

    private void write(int v) {
        if (prettyPrint && tabIndex > 0) {
            space();
        }
        write(TokenMap.indexToken(v));
    }

    private void write(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(char s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(char... s) {
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

    private void write(IRI iri) {
        if (prefixManager.isPresent()) {
            String qname = prefixManager.get().getPrefixIRI(iri);
            if (qname != null) {
                boolean lastCharIsColon = qname.charAt(qname.length() - 1) == ':';
                if (!lastCharIsColon) {
                    write(qname);
                    return;
                }
            }
        }
        writeFullIRI(iri);
    }

    private void writeFullIRI(@Nullable IRI iri) {
        if (iri == null) {
            write("<>");
            return;
        }
        write(iri.toQuotedString());
    }

    @Override
    public void visit(OWLOntology ontology) {
        writePrefixes();
        writeReturn();
        writeReturn();
        write(ONTOLOGY);
        writeOpenBracket();
        if (ontology.isNamed()) {
            writeFullIRI(ontology.getOntologyID().getOntologyIRI().orElse(null));
            ontology.getOntologyID().getVersionIRI().ifPresent(v -> {
                writeReturn();
                writeFullIRI(v);
            });
            writeReturn();
        }
        ontology.importsDeclarations().forEach(this::writeImport);
        ontology.annotations().forEach(this::acceptAndReturn);
        writeReturn();
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        Collection<IRI> illegals = ontology.determineIllegalPunnings(addMissingDeclarations);
        ontology.signature().forEach(e -> writeDeclarations(e, writtenAxioms, illegals));
        writeSortedEntities(ontology, "Annotation Properties", "Annotation Property",
            ontology.annotationPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(ontology, "Object Properties", "Object Property",
            ontology.objectPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(ontology, "Data Properties", "Data Property", ontology.dataPropertiesInSignature(EXCLUDED),
            writtenAxioms);
        writeSortedEntities(ontology, "Datatypes", "Datatype", ontology.datatypesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(ontology, "Classes", "Class", ontology.classesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(ontology, "Named Individuals", "Individual", ontology.individualsInSignature(EXCLUDED),
            writtenAxioms);
        ontology.signature().forEach(e -> writeAxioms(e, writtenAxioms));
        ontology.axioms().filter(ax -> !writtenAxioms.contains(ax)).sorted().forEach(this::acceptAndReturn);
        writeCloseBracket();
        flush();
    }

    protected void writeImport(OWLImportsDeclaration decl) {
        write(IMPORT);
        writeOpenBracket();
        writeFullIRI(decl.getIRI());
        writeCloseBracket();
        writeReturn();
    }

    private void writeSortedEntities(OWLOntology ontology, String bannerComment, String entityTypeName,
        Stream<? extends OWLEntity> entities, Set<OWLAxiom> writtenAxioms) {
        List<? extends OWLEntity> sortOptionally = asList(entities.sorted());
        if (!sortOptionally.isEmpty()) {
            writeEntities(ontology, bannerComment, entityTypeName, sortOptionally, writtenAxioms);
            writeReturn();
        }
    }

    private void writeln(String s) {
        write(s);
        writeReturn();
    }

    private void writeEntities(OWLOntology ontology, String comment, String entityTypeName,
        List<? extends OWLEntity> entities, Set<OWLAxiom> writtenAxioms) {
        boolean haveWrittenBanner = false;
        for (OWLEntity owlEntity : entities) {
            List<? extends OWLAxiom> axiomsForEntity = asList(
                getUnsortedAxiomsForEntity(owlEntity).filter(ax -> !writtenAxioms.contains(ax)));
            List<OWLAnnotationAssertionAxiom> list = asList(
                ontology.annotationAssertionAxioms(owlEntity.getIRI()).filter(ax -> !writtenAxioms.contains(ax)));
            if (axiomsForEntity.isEmpty() && list.isEmpty()) {
                continue;
            }
            if (!haveWrittenBanner) {
                writeln("############################\n#   " + comment + "\n############################\n");
                haveWrittenBanner = true;
            }
            axiomsForEntity.sort(null);
            list.sort(null);
            writeEntity2(owlEntity, entityTypeName, axiomsForEntity, list, writtenAxioms);
        }
    }

    protected void writeEntity2(OWLEntity entity, String entityTypeName, List<? extends OWLAxiom> axiomsForEntity,
        List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms, Set<OWLAxiom> alreadyWrittenAxioms) {
        writeln("# " + entityTypeName + ": " + getIRIString(entity) + " (" + getEntityLabel(entity) + ")");
        writeReturn();
        annotationAssertionAxioms.stream().filter(alreadyWrittenAxioms::add).forEach(this::acceptAndReturn);
        axiomsForEntity.stream().filter(this::shouldWrite).filter(alreadyWrittenAxioms::add)
            .forEach(this::acceptAndReturn);
        writeReturn();
    }

    private boolean shouldWrite(OWLAxiom ax) {
        if (ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS)) {
            return false;
        }
        return !ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
            || !(((OWLDisjointClassesAxiom) ax).getOperandsAsList().size() > 2);
    }

    private Stream<? extends OWLAxiom> getUnsortedAxiomsForEntity(OWLEntity entity) {
        if (ont.isPresent()) {
            return entity.accept(new AxiomRetriever(ont.get()));
        }
        return Stream.empty();
    }

    private String getIRIString(OWLEntity entity) {
        if (prefixManager.isPresent()) {
            return prefixManager.get().getShortForm(entity);
        }
        return entity.getIRI().toQuotedString();
    }

    private String getEntityLabel(OWLEntity entity) {
        if (labelMaker.isPresent()) {
            return labelMaker.get().getShortForm(entity).replace("\n", "\n# ");
        }
        return entity.getIRI().toQuotedString();
    }

    private void writeAxioms(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        writeAnnotations(entity, alreadyWrittenAxioms);
        List<OWLAxiom> writtenAxioms = new ArrayList<>();
        Stream<? extends OWLAxiom> stream = getUnsortedAxiomsForEntity(entity).filter(alreadyWrittenAxioms::contains)
            .filter(ax -> ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS))
            .filter(ax -> ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
                && ((OWLDisjointClassesAxiom) ax).classExpressions().count() > 2)
            .sorted();
        stream.forEach(ax -> acceptAndAdd(writtenAxioms, ax));
        alreadyWrittenAxioms.addAll(writtenAxioms);
    }

    /**
     * Writes out the declaration axioms for the specified entity.
     *
     * @param entity
     *        The entity
     * @return The axioms that were written out
     */
    protected Set<OWLAxiom> writeDeclarations(OWLEntity entity) {
        Set<OWLAxiom> axioms = new HashSet<>();
        ont.ifPresent(o -> o.declarationAxioms(entity).sorted().forEach(ax -> acceptAndAdd(axioms, ax)));
        return axioms;
    }

    private void acceptAndAdd(Collection<OWLAxiom> axioms, OWLAxiom axiom) {
        accept(axiom);
        axioms.add(axiom);
        writeReturn();
    }

    private void writeDeclarations(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms, Collection<IRI> illegals) {
        if (ont.isPresent()) {
            Collection<OWLDeclarationAxiom> axioms = asList(ont.get().declarationAxioms(entity).sorted());
            axioms.stream().filter(alreadyWrittenAxioms::add).forEach(this::acceptAndReturn);
            // if multiple illegal declarations already exist, they have already
            // been outputted the renderer cannot take responsibility for
            // removing them. It should not add declarations for illegally
            // punned entities here, though
            if (addMissingDeclarations && axioms.isEmpty() && !entity.isBuiltIn() && !illegals.contains(entity.getIRI())
                && !ont.get().isDeclared(entity, Imports.INCLUDED)) {
                OWLDataFactory df = ont.get().getOWLOntologyManager().getOWLDataFactory();
                acceptAndReturn(df.getOWLDeclarationAxiom(entity));
            }
        }
    }

    protected void acceptAndReturn(OWLObject ax) {
        accept(ax);
        writeReturn();
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
    protected void writeAnnotations(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        ont.ifPresent(o -> o.annotationAssertionAxioms(entity.getIRI()).sorted().filter(alreadyWrittenAxioms::add)
            .forEach(this::acceptAndReturn));
    }

    private void write(List<? extends OWLObject> objects) {
        if (objects.isEmpty()) {
            return;
        }
        tabIndex++;
        accept(objects.get(0));
        for (int i = 1; i < objects.size(); i++) {
            space();
            accept(objects.get(i));
        }
        tabIndex--;
    }

    private void write(List<OWLAnnotation> anns, OWLObject... objects) {
        writeOpenBracket();
        writeAnnotations(anns);
        accept(objects[0]);
        for (int i = 1; i < objects.length; i++) {
            writeSpace();
            accept(objects[i]);
        }
        writeCloseBracket();
    }

    private void write(int v, List<? extends OWLObject> objects) {
        write(v);
        writeOpenBracket();
        write(objects);
        writeCloseBracket();
    }

    private void write(OWLObject... objects) {
        writeOpenBracket();
        accept(objects[0]);
        for (int i = 1; i < objects.length; i++) {
            writeSpace();
            accept(objects[i]);
        }
        writeCloseBracket();
    }

    private void write(int v, OWLObject... objects) {
        write(v);
        writeOpenBracket();
        accept(objects[0]);
        for (int i = 1; i < objects.length; i++) {
            writeSpace();
            accept(objects[i]);
        }
        writeCloseBracket();
    }

    protected void space() {
        if (prettyPrint) {
            write(filler());
        } else {
            writeSpace();
        }
    }

    protected char[] filler() {
        char[] c = new char[tabIndex * 4 + 1];
        Arrays.fill(c, ' ');
        c[0] = '\n';
        return c;
    }

    protected void writeOpenBracket() {
        write('(');
    }

    protected void writeCloseBracket() {
        write(')');
    }

    protected void writeSpace() {
        write(' ');
    }

    protected void writeReturn() {
        write('\n');
    }

    protected void writeAnnotations(HasAnnotations ax) {
        writeAnnotations(ax.annotationsAsList());
    }

    protected void writeAnnotations(List<OWLAnnotation> l) {
        if (l.isEmpty()) {
            return;
        }
        write(l);
        space();
    }

    protected void writeAnnotatedElement(int v, HasAnnotations ax, OWLObject... objects) {
        write(v);
        write(ax.annotationsAsList(), objects);
    }

    protected void writeElement(int v, OWLObject... objects) {
        write(v);
        write(objects);
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        writeAnnotatedElement(ASYMMETRICOBJECTPROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        writeAnnotatedElement(CLASSASSERTION, axiom, axiom.getClassExpression(), axiom.getIndividual());
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writeAnnotatedElement(DATAPROPERTYASSERTION, axiom, axiom.getProperty(), axiom.getSubject(), axiom.getObject());
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writeAnnotatedElement(DATAPROPERTYDOMAIN, axiom, axiom.getProperty(), axiom.getDomain());
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writeAnnotatedElement(DATAPROPERTYRANGE, axiom, axiom.getProperty(), axiom.getRange());
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        writeAnnotatedElement(SUBDATAPROPERTYOF, axiom, axiom.getSubProperty(), axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        write(DECLARATION);
        writeOpenBracket();
        writeAnnotations(axiom);
        writeEntitiesAsURIs = false;
        accept(axiom.getEntity());
        writeEntitiesAsURIs = true;
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        writeAxiomsWithCollections(DIFFERENTINDIVIDUALS, axiom, axiom.getOperandsAsList());
    }

    protected void writeAxiomsWithCollections(int v, OWLAxiom axiom, List<? extends OWLObject> l) {
        if (l.size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}", axiom.getClass().getSimpleName(), axiom);
            return;
        }
        write(v);
        writeOpenBracket();
        writeAnnotations(axiom);
        write(l);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        writeAxiomsWithCollections(DISJOINTCLASSES, axiom, axiom.getOperandsAsList());
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        writeAxiomsWithCollections(DISJOINTDATAPROPERTIES, axiom, axiom.getOperandsAsList());
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        writeAxiomsWithCollections(DISJOINTOBJECTPROPERTIES, axiom, axiom.getOperandsAsList());
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        write(DISJOINTUNION);
        writeOpenBracket();
        writeAnnotations(axiom);
        accept(axiom.getOWLClass());
        writeSpace();
        write(axiom.getOperandsAsList());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        writeAnnotatedElement(ANNOTATIONASSERTION, axiom, axiom.getProperty(), axiom.getSubject(), axiom.getValue());
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        writeAxiomsWithCollections(EQUIVALENTCLASSES, axiom, axiom.getOperandsAsList());
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        writeAxiomsWithCollections(EQUIVALENTDATAPROPERTIES, axiom, axiom.getOperandsAsList());
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        writeAxiomsWithCollections(EQUIVALENTOBJECTPROPERTIES, axiom, axiom.getOperandsAsList());
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writeAnnotatedElement(FUNCTIONALDATAPROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writeAnnotatedElement(FUNCTIONALOBJECTPROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writeAnnotatedElement(INVERSEFUNCTIONALOBJECTPROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writeAnnotatedElement(INVERSEOBJECTPROPERTIES, axiom, axiom.getFirstProperty(), axiom.getSecondProperty());
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writeAnnotatedElement(IRREFLEXIVEOBJECTPROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        writeAnnotatedElement(NEGATIVEDATAPROPERTYASSERTION, axiom, axiom.getProperty(), axiom.getSubject(),
            axiom.getObject());
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writeAnnotatedElement(NEGATIVEOBJECTPROPERTYASSERTION, axiom, axiom.getProperty(), axiom.getSubject(),
            axiom.getObject());
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writeAnnotatedElement(OBJECTPROPERTYASSERTION, axiom, axiom.getProperty(), axiom.getSubject(),
            axiom.getObject());
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        write(SUBOBJECTPROPERTYOF);
        writeOpenBracket();
        writeAnnotations(axiom);
        write(SUBOBJECTPROPERTYCHAIN, axiom.getPropertyChain());
        writeSpace();
        accept(axiom.getSuperProperty());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeAnnotatedElement(OBJECTPROPERTYDOMAIN, axiom, axiom.getProperty(), axiom.getDomain());
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeAnnotatedElement(OBJECTPROPERTYRANGE, axiom, axiom.getProperty(), axiom.getRange());
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        writeAnnotatedElement(SUBOBJECTPROPERTYOF, axiom, axiom.getSubProperty(), axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writeAnnotatedElement(REFLEXIVEOBJECTPROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        writeAxiomsWithCollections(SAMEINDIVIDUAL, axiom, axiom.getOperandsAsList());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        writeAnnotatedElement(SUBCLASSOF, axiom, axiom.getSubClass(), axiom.getSuperClass());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writeAnnotatedElement(SYMMETRICOBJECTPROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writeAnnotatedElement(TRANSITIVEOBJECTPROPERTY, axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLClass ce) {
        printConditionally(CLASS, ce);
    }

    private void printConditionally(int v, HasIRI i) {
        if (!writeEntitiesAsURIs) {
            write(v);
            writeOpenBracket();
        }
        accept(i.getIRI());
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    private <F extends OWLPropertyRange> void writeRestriction(int v, OWLCardinalityRestriction<F> restriction,
        OWLPropertyExpression p) {
        write(v);
        writeOpenBracket();
        write(Integer.toString(restriction.getCardinality()));
        writeSpace();
        accept(p);
        if (restriction.isQualified()) {
            writeSpace();
            accept(restriction.getFiller());
        }
        writeCloseBracket();
    }

    private void writeSingleton(int v, List<? extends OWLObject> l) {
        if (l.size() == 1) {
            accept(l.get(0));
            return;
        }
        write(v, l);
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        write(DATAALLVALUESFROM);
        write(((OWLQuantifiedRestriction<? extends OWLObject>) ce).getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeRestriction(DATAEXACTCARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeRestriction(DATAMAXCARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeRestriction(DATAMINCARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        write(DATASOMEVALUESFROM);
        write(((OWLQuantifiedRestriction<? extends OWLObject>) ce).getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        write(DATAHASVALUE);
        write(ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        write(OBJECTALLVALUESFROM);
        write(((OWLQuantifiedRestriction<? extends OWLObject>) ce).getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write(OBJECTCOMPLEMENTOF, ce.getOperand());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeRestriction(OBJECTEXACTCARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        writeSingleton(OBJECTINTERSECTIONOF, ce.getOperandsAsList());
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writeRestriction(OBJECTMAXCARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeRestriction(OBJECTMINCARDINALITY, ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        write(OBJECTONEOF, ce.getOperandsAsList());
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        write(OBJECTHASSELF, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        write(OBJECTSOMEVALUESFROM);
        write(((OWLQuantifiedRestriction<? extends OWLObject>) ce).getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        writeSingleton(OBJECTUNIONOF, ce.getOperandsAsList());
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        write(OBJECTHASVALUE);
        write(ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        write(DATACOMPLEMENTOF, node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        write(DATAONEOF, node.getOperandsAsList());
    }

    @Override
    public void visit(OWLDatatype node) {
        printConditionally(DATATYPE, node);
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        write(DATATYPERESTRICTION);
        writeOpenBracket();
        accept(node.getDatatype());
        writeSpace();
        write(node.facetRestrictionsAsList());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getIRI());
        writeSpace();
        accept(node.getFacetValue());
    }

    @Override
    public void visit(OWLLiteral node) {
        write('"');
        write(EscapeUtils.escapeString(node.getLiteral()));
        write('"');
        if (node.hasLang()) {
            write('@');
            write(node.getLang());
        } else if (!node.isRDFPlainLiteral() && !OWL2Datatype.XSD_STRING.matches(node.getDatatype())) {
            write("^^");
            write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        printConditionally(DATAPROP, property);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        printConditionally(OBJECTPROP, property);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        write(OBJECTINVERSEOF, property.getInverse());
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        printConditionally(NAMEDINDIVIDUAL, individual);
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        write(HASKEY);
        writeOpenBracket();
        writeAnnotations(axiom);
        accept(axiom.getClassExpression());
        writeSpace();
        writeOpenBracket();
        write(asList(axiom.objectPropertyExpressions()));
        writeCloseBracket();
        writeSpace();
        writeOpenBracket();
        write(asList(axiom.dataPropertyExpressions()));
        writeCloseBracket();
        writeCloseBracket();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        writeAnnotatedElement(ANNOTATIONPROPERTYDOMAIN, axiom, axiom.getProperty(), axiom.getDomain());
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        writeAnnotatedElement(ANNOTATIONPROPERTYRANGE, axiom, axiom.getProperty(), axiom.getRange());
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        writeAnnotatedElement(SUBANNOTATIONPROPERTYOF, axiom, axiom.getSubProperty(), axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        writeSingleton(DATAINTERSECTIONOF, node.getOperandsAsList());
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        writeSingleton(DATAUNIONOF, node.getOperandsAsList());
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        printConditionally(ANNOTATIONPROPERTY, property);
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(IRI iri) {
        write(iri);
    }

    @Override
    public void visit(OWLAnnotation node) {
        writeAnnotatedElement(ANNOTATION, node, node.getProperty(), node.getValue());
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        writeAnnotatedElement(DATATYPEDEFINITION, axiom, axiom.getDatatype(), axiom.getDataRange());
    }

    @Override
    public void visit(SWRLRule rule) {
        write(DLSAFERULE);
        writeOpenBracket();
        writeAnnotations(rule);
        write(BODY, rule.bodyList());
        write(HEAD, rule.headList());
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        accept(node.getIndividual());
    }

    @Override
    public void visit(SWRLClassAtom node) {
        write(CLASSATOM, node.getPredicate(), node.getArgument());
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        write(DATARANGEATOM, node.getPredicate(), node.getArgument());
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        write(OBJECTPROPERTYATOM, node.getPredicate(), node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        write(DATAPROPERTYATOM, node.getPredicate(), node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        write(BUILTINATOM);
        writeOpenBracket();
        write(node.getPredicate());
        writeSpace();
        write(node.getArguments());
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLVariable node) {
        write(VARIABLE);
        writeOpenBracket();
        String namespace = node.getIRI().getNamespace();
        if ("urn:swrl:var#".equals(namespace) || "urn:swrl#".equals(namespace)) {
            write("<urn:swrl:var#" + node.getIRI().getFragment() + ">");
        } else {
            accept(node.getIRI());
        }
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        accept(node.getLiteral());
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        write(DIFFERENTINDIVIDUALSATOM, node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        write(SAMEINDIVIDUALATOM, node.getFirstArgument(), node.getSecondArgument());
    }

    class AxiomRetriever implements OWLEntityVisitorEx<Stream<? extends OWLAxiom>> {

        OWLOntology o;

        public AxiomRetriever(OWLOntology o) {
            this.o = o;
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLClass cls) {
            return o.axioms(cls, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLObjectProperty property) {
            return o.axioms(property, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLDataProperty property) {
            return o.axioms(property, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLNamedIndividual individual) {
            return o.axioms(individual, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLDatatype datatype) {
            return o.axioms(datatype, EXCLUDED);
        }

        @Override
        public Stream<? extends OWLAxiom> visit(OWLAnnotationProperty property) {
            return o.axioms(property, EXCLUDED);
        }
    }
}
