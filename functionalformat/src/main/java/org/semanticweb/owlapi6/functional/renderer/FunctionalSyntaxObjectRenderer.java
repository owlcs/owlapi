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
package org.semanticweb.owlapi6.functional.renderer;

import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.BODY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DECLARATION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DIFFERENTINDIVIDUALSATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DLSAFERULE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.HASKEY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.HEAD;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.IMPORT;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ONTOLOGY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SAMEINDIVIDUALATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBOBJECTPROPERTYCHAIN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBOBJECTPROPERTYOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.VARIABLE;
import static org.semanticweb.owlapi6.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.semanticweb.owlapi6.annotations.Renders;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.functional.parser.TokenMap;
import org.semanticweb.owlapi6.io.OWLObjectRenderer;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.HasAnnotations;
import org.semanticweb.owlapi6.model.HasOperands;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNaryAxiom;
import org.semanticweb.owlapi6.model.OWLNaryBooleanClassExpression;
import org.semanticweb.owlapi6.model.OWLNaryDataRange;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectType;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLPropertyRange;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi6.model.SWRLIndividualArgument;
import org.semanticweb.owlapi6.model.SWRLLiteralArgument;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi6.model.SWRLVariable;
import org.semanticweb.owlapi6.model.parameters.Imports;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;
import org.semanticweb.owlapi6.utility.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi6.utility.EscapeUtils;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class OWLObjectRenderer.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
@Renders(FunctionalSyntaxDocumentFormat.class)
public class FunctionalSyntaxObjectRenderer implements OWLObjectVisitor, OWLObjectRenderer {

    private static final Logger LOGGER =
        LoggerFactory.getLogger(FunctionalSyntaxObjectRenderer.class);
    protected final Optional<OWLOntology> ont;
    private final PrintWriter writer;
    private final Writer w;
    protected Optional<AnnotationValueShortFormProvider> labelMaker = Optional.empty();
    private Optional<PrefixManager> prefixManager = Optional.empty();
    private boolean writeEntitiesAsURIs = true;
    private boolean addMissingDeclarations = true;
    private boolean prettyPrint = false;
    private int tabIndex = 0;
    static EnumMap<OWLObjectType, BiFunction<OWLOntology, OWLEntity, Stream<? extends OWLAxiom>>> retrieve =
        retrieve();

    /** Empty constructor, for use from ToStringRenderer */
    @Inject
    public FunctionalSyntaxObjectRenderer() {
        this(null, new StringWriter());
    }

    /**
     * @param ontology the ontology
     * @param writer the writer
     */
    public FunctionalSyntaxObjectRenderer(@Nullable OWLOntology ontology, Writer writer) {
        ont = Optional.ofNullable(ontology);
        this.writer = new PrintWriter(writer);
        w = writer;
        ont.ifPresent(this::initFromOntology);
    }

    static EnumMap<OWLObjectType, BiFunction<OWLOntology, OWLEntity, Stream<? extends OWLAxiom>>> retrieve() {
        EnumMap<OWLObjectType, BiFunction<OWLOntology, OWLEntity, Stream<? extends OWLAxiom>>> map =
            new EnumMap<>(OWLObjectType.class);
        map.put(OWLObjectType.CLASS, (o, e) -> o.axioms((OWLClass) e, EXCLUDED));
        map.put(OWLObjectType.OBJECT_PROPERTY, (o, e) -> o.axioms((OWLObjectProperty) e, EXCLUDED));
        map.put(OWLObjectType.DATA_PROPERTY, (o, e) -> o.axioms((OWLDataProperty) e, EXCLUDED));
        map.put(OWLObjectType.NAMED_INDIVIDUAL,
            (o, e) -> o.axioms((OWLNamedIndividual) e, EXCLUDED));
        map.put(OWLObjectType.DATATYPE, (o, e) -> o.axioms((OWLDatatype) e, EXCLUDED));
        map.put(OWLObjectType.ANNOTATION_PROPERTY,
            (o, e) -> o.axioms((OWLAnnotationProperty) e, EXCLUDED));
        return map;
    }

    private void initFromOntology(OWLOntology o) {
        prefixManager = Optional.of(new PrefixManagerImpl(o.getPrefixManager()));
        OntologyConfigurator config = o.getOWLOntologyManager().getOntologyConfigurator();
        addMissingDeclarations = config.shouldAddMissingTypes();
        prettyPrint = config.shouldPrettyPrintFunctionalSyntax();
        if (o.isNamed() && prefixManager.get().getDefaultPrefix() == null) {
            String existingDefault = prefixManager.get().getDefaultPrefix();
            String ontologyIRIString =
                o.getOntologyID().getOntologyIRI().map(Object::toString).orElse("");
            if (existingDefault == null || !existingDefault.startsWith(ontologyIRIString)) {
                String defaultPrefix = ontologyIRIString;
                if (!ontologyIRIString.endsWith("/") && !ontologyIRIString.endsWith("#")) {
                    defaultPrefix = ontologyIRIString + '#';
                }
                prefixManager.get().withDefaultPrefix(defaultPrefix);
            }
        }
        labelMaker = labelMaker(o);
    }

    protected Optional<AnnotationValueShortFormProvider> labelMaker(OWLOntology o) {
        Map<OWLAnnotationProperty, List<String>> prefLangMap = new HashMap<>();
        OWLOntologyManager manager = o.getOWLOntologyManager();
        OWLDataFactory df = manager.getOWLDataFactory();
        List<OWLAnnotationProperty> labels = Collections.singletonList(df.getRDFSLabel());
        return Optional.of(new AnnotationValueShortFormProvider(labels, prefLangMap, manager,
            prefixManager.get()));
    }

    private FunctionalSyntaxObjectRenderer accept(OWLObject o) {
        o.accept(this);
        return this;
    }

    /**
     * Set the add missing declaration flag.
     *
     * @param flag new value
     */
    public void setAddMissingDeclarations(boolean flag) {
        addMissingDeclarations = flag;
    }

    @Override
    public String render(OWLObject object) {
        accept(object);
        writer.flush();
        return w.toString();
    }

    @Override
    public void setPrefixManager(PrefixManager prefixManager) {
        this.prefixManager = Optional.ofNullable(prefixManager);
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        prefixManager.ifPresent(p -> p.setShortFormProvider(shortFormProvider));
    }

    private void writePrefix(String prefix, String namespace) {
        write(String.format("Prefix(%s=<%s>)\n", prefix, namespace));
    }

    private void writePrefixes() {
        prefixManager.ifPresent(p -> p.getPrefixName2PrefixMap().forEach(this::writePrefix));
    }

    private FunctionalSyntaxObjectRenderer write(int v) {
        if (prettyPrint && tabIndex > 0) {
            space();
        }
        return write(TokenMap.indexToken(v));
    }

    private FunctionalSyntaxObjectRenderer write(String s) {
        writer.write(s);
        return this;
    }

    private FunctionalSyntaxObjectRenderer writeFiller() {
        writer.write('\n');
        for (int i = 0; i < tabIndex * 4; i++) {
            writer.write(' ');
        }
        return this;
    }

    private FunctionalSyntaxObjectRenderer write(IRI iri) {
        if (prefixManager.isPresent()) {
            String qname = prefixManager.get().getPrefixIRIIgnoreQName(iri);
            if (qname != null) {
                boolean lastCharIsColon = qname.charAt(qname.length() - 1) == ':';
                if (!lastCharIsColon) {
                    write(qname);
                    return this;
                }
            }
        }
        return writeFullIRI(iri);
    }

    private FunctionalSyntaxObjectRenderer writeFullIRI(@Nullable IRI iri) {
        if (iri == null) {
            return write("<>");
        }
        return write(iri.toQuotedString());
    }

    private void writeImport(OWLImportsDeclaration decl) {
        write(IMPORT).writeOpenBracket().writeFullIRI(decl.getIRI()).writeCloseBracket()
            .writeReturn();
    }

    private void writeSortedEntities(Function<IRI, Stream<OWLAnnotationAssertionAxiom>> annotations,
        String bannerComment, String entityTypeName, Stream<? extends OWLEntity> entities,
        Set<OWLAxiom> writtenAxioms) {
        List<? extends OWLEntity> sortOptionally = asList(entities.sorted());
        if (!sortOptionally.isEmpty()) {
            writeEntities(annotations, bannerComment, entityTypeName, sortOptionally,
                writtenAxioms);
            writeReturn();
        }
    }

    private void writeEntities(Function<IRI, Stream<OWLAnnotationAssertionAxiom>> annotations,
        String comment, String entityTypeName, List<? extends OWLEntity> entities,
        Set<OWLAxiom> writtenAxioms) {
        boolean haveWrittenBanner = false;
        for (OWLEntity owlEntity : entities) {
            List<? extends OWLAxiom> axiomsForEntity =
                asList(retrieve(owlEntity).filter(ax -> !writtenAxioms.contains(ax)));
            List<OWLAnnotationAssertionAxiom> list = asList(
                annotations.apply(owlEntity.getIRI()).filter(ax -> !writtenAxioms.contains(ax)));
            if (axiomsForEntity.isEmpty() && list.isEmpty()) {
                continue;
            }
            if (!haveWrittenBanner) {
                write("############################\n#   " + comment
                    + "\n############################\n\n");
                haveWrittenBanner = true;
            }
            axiomsForEntity.sort(null);
            list.sort(null);
            writeEntity2(owlEntity, entityTypeName, axiomsForEntity, list, writtenAxioms);
        }
    }

    private void writeEntity2(OWLEntity entity, String entityTypeName,
        List<? extends OWLAxiom> axiomsForEntity,
        List<OWLAnnotationAssertionAxiom> annotationAssertionAxioms,
        Set<OWLAxiom> alreadyWrittenAxioms) {
        write("# " + entityTypeName + ": " + getIRIString(entity) + " (" + getEntityLabel(entity)
            + ")\n\n");
        annotationAssertionAxioms.stream().filter(alreadyWrittenAxioms::add)
            .forEach(this::acceptAndReturn);
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

    private Stream<? extends OWLAxiom> retrieve(OWLEntity entity) {
        return ont.map(o -> retrieve(entity, o)).orElse(Stream.empty());
    }

    protected Stream<? extends OWLAxiom> retrieve(OWLEntity entity, OWLOntology o) {
        return retrieve.get(entity.type()).apply(o, entity);
    }

    private String getIRIString(OWLEntity entity) {
        return prefixManager.map(p -> p.getShortForm(entity))
            .orElse(entity.getIRI().toQuotedString());
    }

    private String getEntityLabel(OWLEntity entity) {
        return labelMaker.map(l -> l.getShortForm(entity).replace("\n", "\n# "))
            .orElse(entity.getIRI().toQuotedString());
    }

    private void writeAxioms(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        writeAnnotations(entity, alreadyWrittenAxioms);
        List<OWLAxiom> writtenAxioms = new ArrayList<>();
        Stream<? extends OWLAxiom> stream = retrieve(entity).filter(alreadyWrittenAxioms::contains)
            .filter(ax -> ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS))
            .filter(ax -> ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
                && ((OWLDisjointClassesAxiom) ax).getOperandsAsList().size() > 2)
            .sorted();
        stream.forEach(ax -> acceptAndAdd(writtenAxioms, ax));
        alreadyWrittenAxioms.addAll(writtenAxioms);
    }

    private FunctionalSyntaxObjectRenderer acceptAndAdd(Collection<OWLAxiom> axioms,
        OWLAxiom axiom) {
        accept(axiom);
        axioms.add(axiom);
        return writeReturn();
    }

    private void writeDeclarations(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms,
        Collection<IRI> illegals) {
        if (ont.isPresent()) {
            Collection<OWLDeclarationAxiom> axioms =
                asList(ont.get().declarationAxioms(entity).sorted());
            axioms.stream().filter(alreadyWrittenAxioms::add).forEach(this::acceptAndReturn);
            // if multiple illegal declarations already exist, they have already
            // been outputted the renderer cannot take responsibility for
            // removing them. It should not add declarations for illegally
            // punned entities here, though
            if (addMissingDeclarations && axioms.isEmpty() && !entity.isBuiltIn()
                && !illegals.contains(entity.getIRI())
                && !ont.get().isDeclared(entity, Imports.INCLUDED)) {
                OWLDataFactory df = ont.get().getOWLOntologyManager().getOWLDataFactory();
                acceptAndReturn(df.getOWLDeclarationAxiom(entity));
            }
        }
    }

    private FunctionalSyntaxObjectRenderer acceptAndReturn(OWLObject ax) {
        return accept(ax).writeReturn();
    }

    /**
     * Writes of the annotation for the specified entity.
     *
     * @param entity The entity
     * @param alreadyWrittenAxioms already written axioms, to be updated with the newly written
     *        axioms
     */
    private void writeAnnotations(OWLEntity entity, Set<OWLAxiom> alreadyWrittenAxioms) {
        ont.ifPresent(o -> o.annotationAssertionAxioms(entity.getIRI()).sorted()
            .filter(alreadyWrittenAxioms::add).forEach(this::acceptAndReturn));
    }

    private FunctionalSyntaxObjectRenderer write(List<? extends OWLObject> objects) {
        if (objects.isEmpty()) {
            return this;
        }
        tabIndex++;
        accept(objects.get(0));
        for (int i = 1; i < objects.size(); i++) {
            space().accept(objects.get(i));
        }
        tabIndex--;
        return this;
    }

    private FunctionalSyntaxObjectRenderer write(int v, List<? extends OWLObject> objects) {
        return write(v).writeOpenBracket().write(objects).writeCloseBracket();
    }

    private FunctionalSyntaxObjectRenderer write(int v, OWLObject... objects) {
        write(v).writeOpenBracket();
        accept(objects[0]);
        for (int i = 1; i < objects.length; i++) {
            writeSpace().accept(objects[i]);
        }
        return writeCloseBracket();
    }

    private FunctionalSyntaxObjectRenderer space() {
        if (prettyPrint) {
            return writeFiller();
        }
        return writeSpace();
    }

    private FunctionalSyntaxObjectRenderer writeOpenBracket() {
        writer.write('(');
        return this;
    }

    private FunctionalSyntaxObjectRenderer writeCloseBracket() {
        writer.write(')');
        return this;
    }

    private FunctionalSyntaxObjectRenderer writeSpace() {
        writer.write(' ');
        return this;
    }

    private FunctionalSyntaxObjectRenderer writeReturn() {
        writer.write('\n');
        return this;
    }

    private FunctionalSyntaxObjectRenderer writeAnnotations(HasAnnotations ax) {
        if (ax.annotationsAsList().isEmpty()) {
            return this;
        }
        return write(ax.annotationsAsList()).space();
    }

    private void printConditionally(OWLEntity i) {
        if (!writeEntitiesAsURIs) {
            write(TokenMap.tokenForType(i.type())).writeOpenBracket();
        }
        accept(i.getIRI());
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    private <F extends OWLPropertyRange> void writeRestriction(
        OWLCardinalityRestriction<F> restriction) {
        write(TokenMap.tokenForType(restriction.type())).writeOpenBracket()
            .write(Integer.toString(restriction.getCardinality())).writeSpace()
            .accept(restriction.getProperty());
        if (restriction.isQualified()) {
            writeSpace().accept(restriction.getFiller());
        }
        writeCloseBracket();
    }

    private FunctionalSyntaxObjectRenderer writeSingleton(OWLObject o) {
        @SuppressWarnings("unchecked")
        List<OWLObject> l = ((HasOperands<OWLObject>) o).getOperandsAsList();
        return l.size() == 1 ? accept(l.get(0))
            : write(TokenMap.tokenForType(o.type())).writeOpenBracket().write(l)
                .writeCloseBracket();
    }

    @Override
    public void doDefault(OWLObject object) {
        if (object instanceof OWLEntity) {
            printConditionally((OWLEntity) object);
            return;
        }
        if (object instanceof OWLCardinalityRestriction) {
            writeRestriction((OWLCardinalityRestriction<?>) object);
            return;
        }
        if (object instanceof OWLNaryDataRange || object instanceof OWLNaryBooleanClassExpression) {
            writeSingleton(object);
            return;
        }
        if (object instanceof OWLNaryAxiom
            && ((OWLNaryAxiom<?>) object).getOperandsAsList().size() < 2) {
            LOGGER.warn("{} with less than two elements skipped {}",
                object.getClass().getSimpleName(), object);
            return;
        }
        name(object).writeOpenBracket();
        if (object instanceof HasAnnotations) {
            writeAnnotations((HasAnnotations) object);
        }
        iterate(object.componentStream()).writeCloseBracket();
    }

    private FunctionalSyntaxObjectRenderer name(OWLObject object) {
        return write(TokenMap.tokenForType(object.type()));
    }

    private FunctionalSyntaxObjectRenderer render(Collection<? extends OWLObject> objects) {
        Iterator<? extends OWLObject> it = objects.iterator();
        while (it.hasNext()) {
            accept(it.next());
            if (it.hasNext()) {
                writeSpace();
            }
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    private FunctionalSyntaxObjectRenderer iterate(Stream<?> componentStream) {
        Iterator<?> it = componentStream.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Collection) {
                render((Collection<? extends OWLObject>) o);
            } else if (o instanceof OWLObject) {
                accept((OWLObject) o);
            } else {
                write(o.toString());
            }
            if (it.hasNext()) {
                writeSpace();
            }
        }
        return this;
    }

    @Override
    public void visit(OWLOntology o) {
        writePrefixes();
        writeReturn().writeReturn().write(ONTOLOGY).writeOpenBracket();
        if (o.isNamed()) {
            writeFullIRI(o.getOntologyID().getOntologyIRI().orElse(null));
            o.getOntologyID().getVersionIRI().ifPresent(v -> writeReturn().writeFullIRI(v));
            writeReturn();
        }
        o.importsDeclarations().forEach(this::writeImport);
        o.annotations().forEach(this::acceptAndReturn);
        writeReturn();
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        Collection<IRI> illegals = o.determineIllegalPunnings(addMissingDeclarations);
        o.signature().forEach(e -> writeDeclarations(e, writtenAxioms, illegals));
        Function<IRI, Stream<OWLAnnotationAssertionAxiom>> annotations =
            x -> o.annotationAssertionAxioms(x);
        writeSortedEntities(annotations, "Annotation Properties", "Annotation Property",
            o.annotationPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(annotations, "Object Properties", "Object Property",
            o.objectPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(annotations, "Data Properties", "Data Property",
            o.dataPropertiesInSignature(EXCLUDED), writtenAxioms);
        writeSortedEntities(annotations, "Datatypes", "Datatype", o.datatypesInSignature(EXCLUDED),
            writtenAxioms);
        writeSortedEntities(annotations, "Classes", "Class", o.classesInSignature(EXCLUDED),
            writtenAxioms);
        writeSortedEntities(annotations, "Named Individuals", "Individual",
            o.individualsInSignature(EXCLUDED), writtenAxioms);
        o.signature().forEach(e -> writeAxioms(e, writtenAxioms));
        o.axioms().filter(ax -> !writtenAxioms.contains(ax)).sorted()
            .forEach(this::acceptAndReturn);
        writeCloseBracket().writer.flush();
    }

    /**
     * @param axioms axioms to render
     * @return Stirng containing the axioms, rendered as if they were the only content of a fresh
     *         ontology
     */
    public String renderAxioms(Collection<OWLAxiom> axioms) {
        writePrefixes();
        writeReturn().writeReturn().write(ONTOLOGY).writeOpenBracket().writeReturn();
        Set<OWLAxiom> writtenAxioms = new HashSet<>();
        Set<OWLEntity> signature = asSet(axioms.stream().flatMap(OWLAxiom::signature));
        Collection<IRI> illegals = OWLOntology.illegalPunnings(addMissingDeclarations, signature);
        signature.forEach(e -> writeDeclarations(e, writtenAxioms, illegals));
        Function<IRI, Stream<OWLAnnotationAssertionAxiom>> o = annotations(axioms);
        writeSortedEntities(o, "Annotation Properties", "Annotation Property",
            signature.stream().filter(OWLEntity::isOWLAnnotationProperty), writtenAxioms);
        writeSortedEntities(o, "Object Properties", "Object Property",
            signature.stream().filter(OWLEntity::isOWLObjectProperty), writtenAxioms);
        writeSortedEntities(o, "Data Properties", "Data Property",
            signature.stream().filter(OWLEntity::isOWLDataProperty), writtenAxioms);
        writeSortedEntities(o, "Datatypes", "Datatype",
            signature.stream().filter(OWLEntity::isOWLDatatype), writtenAxioms);
        writeSortedEntities(o, "Classes", "Class", signature.stream().filter(OWLEntity::isOWLClass),
            writtenAxioms);
        writeSortedEntities(o, "Named Individuals", "Individual",
            signature.stream().filter(OWLEntity::isIndividual), writtenAxioms);
        signature.forEach(e -> writeAxioms(e, writtenAxioms));
        axioms.stream().filter(ax -> !writtenAxioms.contains(ax)).sorted()
            .forEach(this::acceptAndReturn);
        writeCloseBracket().writer.flush();
        writer.flush();
        return w.toString();
    }

    protected Function<IRI, Stream<OWLAnnotationAssertionAxiom>> annotations(
        Collection<OWLAxiom> axioms) {
        return x -> axioms.stream().filter(ax -> annotations(ax, x))
            .map(OWLAnnotationAssertionAxiom.class::cast);
    }

    boolean annotations(OWLAxiom ax, IRI i) {
        return ax instanceof OWLAnnotationAssertionAxiom
            && ((OWLAnnotationAssertionAxiom) ax).getSubject().equals(i);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        write(DECLARATION).writeOpenBracket().writeAnnotations(axiom);
        writeEntitiesAsURIs = false;
        accept(axiom.getEntity());
        writeEntitiesAsURIs = true;
        writeCloseBracket();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        write(SUBOBJECTPROPERTYOF).writeOpenBracket().writeAnnotations(axiom)
            .write(SUBOBJECTPROPERTYCHAIN, axiom.getPropertyChain()).writeSpace()
            .accept(axiom.getSuperProperty()).writeCloseBracket();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getIRI()).writeSpace().accept(node.getFacetValue());
    }

    @Override
    public void visit(OWLLiteral node) {
        writer.write('"');
        write(EscapeUtils.escapeString(node.getLiteral()));
        writer.write('"');
        if (node.hasLang()) {
            writer.write('@');
            write(node.getLang());
        } else if (!node.isRDFPlainLiteral()
            && !OWL2Datatype.XSD_STRING.matches(node.getDatatype())) {
            write("^^").write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        write(HASKEY).writeOpenBracket().writeAnnotations(axiom).accept(axiom.getClassExpression())
            .writeSpace();
        writeOpenBracket().write(axiom.objectPropertyExpressionsAsList()).writeCloseBracket()
            .writeSpace();
        writeOpenBracket().write(axiom.dataPropertyExpressionsAsList()).writeCloseBracket()
            .writeCloseBracket();
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
    public void visit(SWRLRule rule) {
        write(DLSAFERULE).writeOpenBracket().writeAnnotations(rule).write(BODY, rule.bodyList())
            .write(HEAD, rule.headList()).writeCloseBracket();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        accept(node.getIndividual());
    }

    @Override
    public void visit(SWRLVariable node) {
        write(VARIABLE).writeOpenBracket();
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
}
