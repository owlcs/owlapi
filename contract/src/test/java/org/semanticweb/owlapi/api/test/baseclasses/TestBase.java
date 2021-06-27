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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;
import org.semanticweb.owlapi.api.test.anonymous.AnonymousIndividualsNormaliser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TrigDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
@Timeout(value = 1000, unit = TimeUnit.SECONDS)
public abstract class TestBase {

    @TempDir
    public File folder;
    private static final String BLANK = "blank";
    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected static final String uriBase = "http://www.semanticweb.org/owlapi/test";
    protected static OWLDataFactory df;

    public static final String OBO = "http://purl.obolibrary.org/obo/";

    public static final OWLObjectProperty S = ObjectProperty(iri("s"));
    public static final OWLClass K = Class(iri("K"));
    public static final OWLClass G = Class(iri("G"));
    public static final OWLClass F = Class(iri("F"));
    public static final OWLClass E = Class(iri("E"));
    public static final OWLClass D = Class(iri("D"));
    public static final OWLClass C = Class(iri("C"));

    public static final OWLClass C1 = Class(iri(OBO, "TEST_1"));
    public static final OWLClass C2 = Class(iri(OBO, "TEST_2"));
    public static final OWLClass C3 = Class(iri(OBO, "TEST_3"));
    public static final OWLClass C4 = Class(iri(OBO, "TEST_4"));
    public static final OWLClass C5 = Class(iri(OBO, "TEST_5"));


    public static final OWLDataProperty DR = DataProperty(iri("r"));
    public static final OWLObjectProperty R = ObjectProperty(iri("r"));
    public static final OWLClass B = Class(iri("B"));
    public static final OWLClass A = Class(iri("A"));
    public static final OWLDataProperty DQ = DataProperty(iri("q"));
    public static final OWLDataProperty DPROP = DataProperty(iri("prop"));
    public static final OWLObjectProperty PROP = ObjectProperty(iri("prop"));
    public static final OWLObjectProperty Q = ObjectProperty(iri("q"));
    public static final OWLDataProperty DP = DataProperty(iri("p"));
    public static final OWLDataProperty DPP = DataProperty(iri("dp"));
    public static final OWLObjectProperty P = ObjectProperty(iri("p"));
    public static final OWLNamedIndividual J = NamedIndividual(iri("j"));
    public static final OWLNamedIndividual I = NamedIndividual(iri("i"));
    public static final OWLNamedIndividual indA = NamedIndividual(iri("a"));
    public static final OWLObjectComplementOf notC = ObjectComplementOf(C);
    public static final OWLObjectComplementOf notB = ObjectComplementOf(B);
    public static final OWLObjectComplementOf notA = ObjectComplementOf(A);
    public static final OWLNamedIndividual i = NamedIndividual(iri("I"));
    public static final OWLAnnotationProperty AP = AnnotationProperty(iri("propA"));
    public static final OWLDataProperty PD = DataProperty(iri("propD"));
    public static final OWLDatatype DT = Datatype(iri("DT"));
    public static final OWLAnnotationProperty areaTotal =
        AnnotationProperty(IRI("http://dbpedia.org/ontology/", "areaTotal"));
    public static final IRI southAfrica = IRI("http://dbpedia.org/resource/", "South_Africa");
    public static final OWLLiteral oneMillionth = Literal("1.0E-7", OWL2Datatype.XSD_DOUBLE);

    protected static OntologyConfigurator masterConfigurator;
    protected final File RESOURCES = resources();
    protected OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
    protected OWLOntologyManager m = setupManager();
    protected OWLOntologyManager m1 = setupManager();

    public static IRI iri(String name) {
        return IRI(uriBase + '#', name);
    }

    public static IRI iri(File file) {
        return IRI.create(file);
    }

    public static IRI iri(String p, String s) {
        return IRI.create(p, s);
    }

    private static final File resources() {
        try {
            return new File(TestBase.class.getResource("/owlapi.properties").toURI())
                .getParentFile();
        } catch (URISyntaxException e) {
            throw new OWLRuntimeException("NO RESOURCE FOLDER ACCESSIBLE", e);
        }
    }

    public static List<OWLDocumentFormat> formats() {
        return Arrays.asList(new RDFXMLDocumentFormat(), new RioRDFXMLDocumentFormat(),
            new RDFJsonDocumentFormat(), new OWLXMLDocumentFormat(),
            new FunctionalSyntaxDocumentFormat(), new TurtleDocumentFormat(),
            new RioTurtleDocumentFormat(), new ManchesterSyntaxDocumentFormat(),
            new TrigDocumentFormat(), new RDFJsonLDDocumentFormat(), new NTriplesDocumentFormat(),
            new NQuadsDocumentFormat());
    }

    public static Stream<OWLDocumentFormat> formatsSkip(Class<?> c) {
        return formats().stream().filter(x -> !c.isInstance(x));
    }

    public static void assertThrowsWithMessage(String message, Class<? extends Throwable> c,
        Executable r) {
        assertThrows(c, r, message);
    }

    public static void assertThrowsWithCauseMessage(Class<?> wrapper, Class<?> c,
        @Nullable String message, Executable r) {
        assertThrowsWithCausePredicate(wrapper, c,
            e -> assertTrue(message == null || e.getMessage().contains(message)), r);
    }

    public static void assertThrowsWithPredicate(Class<?> c, Consumer<Throwable> p, Executable r) {
        try {
            r.execute();
        } catch (Throwable e) {
            assertEquals(c, e.getClass());
            p.accept(e);
        }
    }

    public static void assertThrowsWithCausePredicate(Class<?> wrapper, Class<?> c,
        Consumer<Throwable> p, Executable r) {
        try {
            r.execute();
        } catch (Throwable e) {
            assertEquals(wrapper, e.getClass());
            assertNotNull(e.getCause());
            assertEquals(c, e.getCause().getClass());
            p.accept(e.getCause());
        }
    }

    public static void assertThrowsWithCause(Class<?> wrapper, Class<?> c, Executable r) {
        assertThrowsWithCauseMessage(wrapper, c, null, r);
    }

    @BeforeAll
    public static void setupManagers() {
        masterConfigurator = new OntologyConfigurator();
        df = OWLManager.getOWLDataFactory();
    }

    protected static OWLOntologyManager setupManager() {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        man.setOntologyConfigurator(masterConfigurator);
        return man;
    }

    protected static <S> Set<S> singleton(S s) {
        return Collections.singleton(s);
    }

    protected <T> T get(Optional<T> t) {
        return t.get();
    }

    protected OWLOntology ontologyFromClasspathFile(String fileName) {
        return ontologyFromClasspathFile(fileName, config);
    }

    protected OWLOntology ontologyFromClasspathFile(String fileName,
        @Nullable OWLDocumentFormat format) {
        return ontologyFromClasspathFile(fileName, config, format);
    }

    protected OWLOntology ontologyFromClasspathFile(String fileName,
        OWLOntologyLoaderConfiguration configuration) {
        try (InputStream in = getClass().getResourceAsStream('/' + fileName)) {
            return m1.loadOntologyFromOntologyDocument(new StreamDocumentSource(in), configuration);
        } catch (OWLOntologyCreationException | IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology ontologyFromClasspathFile(String fileName,
        OWLOntologyLoaderConfiguration configuration, @Nullable OWLDocumentFormat f) {
        if (f == null) {
            return ontologyFromClasspathFile(fileName, configuration);
        }
        URL resource = getClass().getResource('/' + fileName);
        try (InputStream resourceAsStream = new FileInputStream(new File(resource.toURI()))) {
            return m1.loadOntologyFromOntologyDocument(
                new StreamDocumentSource(resourceAsStream, IRI.create(resource), f, null),
                configuration);
        } catch (OWLOntologyCreationException | IOException | URISyntaxException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected Set<OWLAxiom> stripSimpleDeclarations(Collection<OWLAxiom> axioms) {
        Set<OWLAxiom> toReturn = new HashSet<>();
        for (OWLAxiom ax : axioms) {
            if (!isSimpleDeclaration(ax)) {
                toReturn.add(ax);
            }
        }
        return toReturn;
    }

    protected boolean isSimpleDeclaration(OWLAxiom ax) {
        return ax.isOfType(AxiomType.DECLARATION) && ax.annotationsAsList().isEmpty();
    }

    private static String str(Stream<?> s) {
        return s.map(Object::toString).map(f -> f.replace(" ", "\n ").replace("(", "(\n"))
            .collect(Collectors.joining("\n"));
    }

    public boolean equal(OWLOntology ont1, OWLOntology ont2) {
        if (ont1.isNamed() && ont2.isNamed()) {
            assertEquals(ont1.getOntologyID(), ont2.getOntologyID());
        }
        if (!Objects.equals(asSet(ont1.annotations()), asSet(ont2.annotations()))) {
            assertEquals(str(ont1.annotations()), str(ont2.annotations()));
        }
        Set<OWLAxiom> axioms1;
        Set<OWLAxiom> axioms2;
        // This isn't great - we normalise axioms by changing the ids of
        // individuals. This relies on the fact that
        // we iterate over objects in the same order for the same set of axioms!
        axioms1 = new AnonymousIndividualsNormaliser(ont1.getOWLOntologyManager())
            .getNormalisedAxioms(ont1.axioms());
        axioms2 = new AnonymousIndividualsNormaliser(ont1.getOWLOntologyManager())
            .getNormalisedAxioms(ont2.axioms());
        OWLDocumentFormat ontologyFormat = ont2.getNonnullFormat();
        applyEquivalentsRoundtrip(axioms1, axioms2, ontologyFormat);
        if (ontologyFormat instanceof ManchesterSyntaxDocumentFormat) {
            // drop GCIs from the expected axioms, they won't be there
            Iterator<OWLAxiom> it = axioms1.iterator();
            while (it.hasNext()) {
                OWLAxiom next = it.next();
                if (next instanceof OWLSubClassOfAxiom) {
                    OWLSubClassOfAxiom n = (OWLSubClassOfAxiom) next;
                    if (n.getSubClass().isAnonymous() && n.getSuperClass().isAnonymous()) {
                        it.remove();
                    }
                }
            }
        }
        PlainLiteralTypeFoldingAxiomSet a = new PlainLiteralTypeFoldingAxiomSet(axioms1);
        PlainLiteralTypeFoldingAxiomSet b = new PlainLiteralTypeFoldingAxiomSet(axioms2);
        if (!a.equals(b)) {
            int counter = 0;
            StringBuilder sb = new StringBuilder();
            Set<OWLAxiom> leftOnly = new HashSet<>();
            Set<OWLAxiom> rightOnly = new HashSet<>();
            for (OWLAxiom ax : a) {
                if (!b.contains(ax) && !isIgnorableAxiom(ax, false)) {
                    leftOnly.add(ax);
                    sb.append("Rem axiom: ").append(ax).append('\n');
                    counter++;
                }
            }
            for (OWLAxiom ax : b) {
                if (!a.contains(ax) && !isIgnorableAxiom(ax, true)) {
                    rightOnly.add(ax);
                    sb.append("Add axiom: ").append(ax).append('\n');
                    counter++;
                }
            }
            if (counter > 0 && !rightOnly.equals(leftOnly)) {
                // a test fails on OpenJDK implementations because of
                // ordering
                // testing here if blank node ids are the only difference
                boolean fixed = !verifyErrorIsDueToBlankNodesId(leftOnly, rightOnly);
                if (fixed) {
                    if (logger.isTraceEnabled()) {
                        String x = getClass().getSimpleName()
                            + " roundTripOntology() Failing to match axioms: \n" + sb
                            + topOfStackTrace();
                        logger.trace(x);
                    }
                    fail(getClass().getSimpleName()
                        + " roundTripOntology() Failing to match axioms: \n" + sb);
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    /**
     * equivalent entity axioms with more than two entities are broken up by RDF syntaxes. Ensure
     * they are still recognized as correct roundtripping
     */
    public void applyEquivalentsRoundtrip(Set<OWLAxiom> axioms1, Set<OWLAxiom> axioms2,
        OWLDocumentFormat destination) {
        if (!axioms1.equals(axioms2)) {
            // remove axioms that differ only because of n-ary equivalence
            // axioms
            // http://www.w3.org/TR/owl2-mapping-to-rdf/#Axioms_that_are_Translated_to_Multiple_Triples
            for (OWLAxiom ax : new ArrayList<>(axioms1)) {
                if (ax instanceof OWLEquivalentClassesAxiom) {
                    OWLEquivalentClassesAxiom ax2 = (OWLEquivalentClassesAxiom) ax;
                    if (ax2.getOperandsAsList().size() > 2) {
                        Collection<OWLEquivalentClassesAxiom> pairs = ax2.splitToAnnotatedPairs();
                        if (removeIfContainsAll(axioms2, pairs, destination)) {
                            axioms1.remove(ax);
                            axioms2.removeAll(pairs);
                        }
                    }
                } else if (ax instanceof OWLEquivalentDataPropertiesAxiom) {
                    OWLEquivalentDataPropertiesAxiom ax2 = (OWLEquivalentDataPropertiesAxiom) ax;
                    if (ax2.getOperandsAsList().size() > 2) {
                        Collection<OWLEquivalentDataPropertiesAxiom> pairs =
                            ax2.splitToAnnotatedPairs();
                        if (removeIfContainsAll(axioms2, pairs, destination)) {
                            axioms1.remove(ax);
                            axioms2.removeAll(pairs);
                        }
                    }
                } else if (ax instanceof OWLEquivalentObjectPropertiesAxiom) {
                    OWLEquivalentObjectPropertiesAxiom ax2 =
                        (OWLEquivalentObjectPropertiesAxiom) ax;
                    if (ax2.getOperandsAsList().size() > 2) {
                        Collection<OWLEquivalentObjectPropertiesAxiom> pairs =
                            ax2.splitToAnnotatedPairs();
                        if (removeIfContainsAll(axioms2, pairs, destination)) {
                            axioms1.remove(ax);
                            axioms2.removeAll(pairs);
                        }
                    }
                }
            }
        }
        if (!axioms1.equals(axioms2) && destination instanceof RDFJsonLDDocumentFormat) {
            // other axioms can have their annotations changed to string type
            Set<OWLAxiom> reannotated1 = new HashSet<>();
            axioms1.forEach(a -> reannotated1.add(reannotate(a)));
            axioms1.clear();
            axioms1.addAll(reannotated1);
            Set<OWLAxiom> reannotated2 = new HashSet<>();
            axioms2.forEach(a -> reannotated2.add(reannotate(a)));
            axioms2.clear();
            axioms2.addAll(reannotated2);
        }
    }

    private boolean removeIfContainsAll(Collection<OWLAxiom> axioms,
        Collection<? extends OWLAxiom> others, OWLDocumentFormat destination) {
        if (axioms.containsAll(others)) {
            axioms.removeAll(others);
            return true;
        }
        // some syntaxes attach xsd:string to annotation values that did not
        // have it previously
        if (!(destination instanceof RDFJsonLDDocumentFormat)) {
            return false;
        }
        Set<OWLAxiom> toRemove = new HashSet<>();
        for (OWLAxiom ax : others) {
            OWLAxiom reannotated = reannotate(ax);
            toRemove.add(reannotated);
        }
        axioms.removeAll(toRemove);
        return true;
    }

    protected OWLAxiom reannotate(OWLAxiom ax) {
        return ax.getAxiomWithoutAnnotations().getAnnotatedAxiom(reannotate(ax.annotations()));
    }

    public static <S> List<S> set(S... s) {
        return asList(Stream.of(s).distinct());
    }

    private static Set<OWLAnnotation> reannotate(Stream<OWLAnnotation> anns) {
        OWLDatatype stringType = df.getOWLDatatype(OWL2Datatype.XSD_STRING);
        Set<OWLAnnotation> toReturn = new HashSet<>();
        anns.forEach(a -> {
            Optional<OWLLiteral> asLiteral = a.getValue().asLiteral();
            if (asLiteral.isPresent() && asLiteral.get().isRDFPlainLiteral()) {
                OWLAnnotation replacement = df.getOWLAnnotation(a.getProperty(),
                    df.getOWLLiteral(asLiteral.get().getLiteral(), stringType));
                toReturn.add(replacement);
            } else {
                toReturn.add(a);
            }
        });
        return toReturn;
    }

    private static String topOfStackTrace() {
        StackTraceElement[] elements = new RuntimeException().getStackTrace();
        return elements[1] + "\n" + elements[2] + '\n' + elements[3];
    }

    public static boolean verifyErrorIsDueToBlankNodesId(Set<OWLAxiom> leftOnly,
        Set<OWLAxiom> rightOnly) {
        Set<String> leftOnlyStrings = new HashSet<>();
        Set<String> rightOnlyStrings = new HashSet<>();
        for (OWLAxiom ax : leftOnly) {
            leftOnlyStrings.add(ax.toString().replaceAll("_:anon-ind-[0-9]+", BLANK)
                .replaceAll("_:genid[0-9]+", BLANK));
        }
        for (OWLAxiom ax : rightOnly) {
            rightOnlyStrings.add(ax.toString().replaceAll("_:anon-ind-[0-9]+", BLANK)
                .replaceAll("_:genid[0-9]+", BLANK));
        }
        return rightOnlyStrings.equals(leftOnlyStrings);
    }

    /**
     * ignore declarations of builtins and of named individuals - named individuals do not /need/ a
     * declaration, but adding one is not an error.
     *
     * @param parse true if the axiom belongs to the parsed ones, false for the input
     * @return true if the axiom can be ignored
     */
    public boolean isIgnorableAxiom(OWLAxiom ax, boolean parse) {
        if (ax instanceof OWLDeclarationAxiom) {
            if (parse) {
                // all extra declarations in the parsed ontology are fine
                return true;
            }
            OWLDeclarationAxiom d = (OWLDeclarationAxiom) ax;
            // declarations of builtin and named individuals can be ignored
            return d.getEntity().isBuiltIn() || d.getEntity().isOWLNamedIndividual();
        }
        return false;
    }

    public OWLOntology getOWLOntology() {
        try {
            return m.createOntology(IRI.getNextDocumentIRI(uriBase));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    public OWLOntology getOWLOntology(IRI iri) {
        try {
            return m.createOntology(iri);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    public OWLOntology getOWLOntology(OWLOntologyID iri) {
        try {
            return m.createOntology(iri);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    public OWLOntology getAnonymousOWLOntology() {
        try {
            return m.createOntology();
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    public OWLOntology roundTripOntology(OWLOntology ont) {
        return roundTripOntology(ont, new RDFXMLDocumentFormat());
    }

    /**
     * Saves the specified ontology in the specified format and reloads it. Calling this method from
     * a test will cause the test to fail if the ontology could not be stored, could not be
     * reloaded, or was reloaded and the reloaded version is not equal (in terms of ontology URI and
     * axioms) with the original.
     *
     * @param ont The ontology to round trip.
     * @param format The format to use when doing the round trip.
     */
    public OWLOntology roundTripOntology(OWLOntology ont, OWLDocumentFormat format) {
        try {
            StringDocumentTarget target = new StringDocumentTarget();
            OWLDocumentFormat fromFormat = ont.getFormat();
            if (fromFormat.isPrefixOWLDocumentFormat() && format.isPrefixOWLDocumentFormat()) {
                PrefixDocumentFormat fromPrefixFormat = fromFormat.asPrefixOWLDocumentFormat();
                PrefixDocumentFormat toPrefixFormat = format.asPrefixOWLDocumentFormat();
                toPrefixFormat.copyPrefixesFrom(fromPrefixFormat);
                toPrefixFormat.setDefaultPrefix(null);
            }
            format.setAddMissingTypes(true);
            if (logger.isTraceEnabled()) {
                StringDocumentTarget targetForDebug = new StringDocumentTarget();
                ont.saveOntology(format, targetForDebug);
                logger.trace(targetForDebug.toString());
            }
            ont.saveOntology(format, target);
            handleSaved(target, format);
            OWLOntology ont2 = setupManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(target.toString(), "string:ontology", format, null),
                new OWLOntologyLoaderConfiguration().setReportStackTraces(true));
            if (logger.isTraceEnabled()) {
                logger.trace("TestBase.roundTripOntology() ontology parsed");
                ont2.axioms().forEach(ax -> logger.trace(ax.toString()));
            }
            equal(ont, ont2);
            return ont2;
        } catch (OWLRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new OWLRuntimeException(e);
        }
    }

    // @Test
    public void checkVerify() {
        OWLDataProperty t = df.getOWLDataProperty("urn:test#", "t");
        Set<OWLAxiom> ax1 = new HashSet<>();
        ax1.add(df.getOWLDataPropertyAssertionAxiom(t, df.getOWLAnonymousIndividual(),
            df.getOWLLiteral("test1")));
        ax1.add(df.getOWLDataPropertyAssertionAxiom(t, df.getOWLAnonymousIndividual(),
            df.getOWLLiteral("test2")));
        Set<OWLAxiom> ax2 = new HashSet<>();
        ax2.add(df.getOWLDataPropertyAssertionAxiom(t, df.getOWLAnonymousIndividual(),
            df.getOWLLiteral("test1")));
        ax2.add(df.getOWLDataPropertyAssertionAxiom(t, df.getOWLAnonymousIndividual(),
            df.getOWLLiteral("test2")));
        assertFalse(ax1.equals(ax2));
        assertTrue(verifyErrorIsDueToBlankNodesId(ax1, ax2));
    }

    @SuppressWarnings("unused")
    protected void handleSaved(StringDocumentTarget target, OWLDocumentFormat format) {
        // System.out.println(target.toString());
    }

    public OWLOntology loadOntology(String fileName) {
        return loadOntology(fileName, m);
    }

    public OWLOntology loadOntology(String fileName, OWLOntologyManager manager) {
        try {
            URL url = getClass().getResource('/' + fileName);
            return manager.loadOntologyFromOntologyDocument(
                new IRIDocumentSource(IRI.create(url), null, null),
                new OWLOntologyLoaderConfiguration().setReportStackTraces(true));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(String input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        } catch (OWLRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(String input, IRI i, OWLDocumentFormat f) {
        StringDocumentSource documentSource = new StringDocumentSource(input, i, f, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(String input, OWLDocumentFormat f) {
        StringDocumentSource documentSource =
            new StringDocumentSource(input, IRI.generateDocumentIRI(), f, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(String input, OWLDocumentFormat f,
        OWLOntologyLoaderConfiguration c) {
        StringDocumentSource documentSource =
            new StringDocumentSource(input, IRI.generateDocumentIRI(), f, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource, c);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(StringDocumentSource input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(input);
        } catch (OWLRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(StringDocumentTarget input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(StringDocumentTarget input, OWLDocumentFormat f) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input.toString(), "string:ontology", f, null));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyStrict(StringDocumentTarget o) {
        return loadOntologyWithConfig(o, new OWLOntologyLoaderConfiguration().setStrict(true));
    }

    protected OWLOntology loadOntologyStrict(String s, OWLDocumentFormat f) {
        return loadOntologyWithConfig(new StringDocumentSource(s, "string:ontology", f, null),
            new OWLOntologyLoaderConfiguration().setStrict(true));
    }

    protected OWLOntology loadOntologyWithConfig(StringDocumentTarget o,
        OWLOntologyLoaderConfiguration c) {
        return loadOntologyWithConfig(new StringDocumentSource(o), c);
    }

    protected OWLOntology loadOntologyWithConfig(StringDocumentSource o,
        OWLOntologyLoaderConfiguration c) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(o, c);
        } catch (OWLRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected StringDocumentTarget saveOntology(OWLOntology o) {
        return saveOntology(o, o.getNonnullFormat());
    }

    protected StringDocumentTarget saveOntology(OWLOntology o, OWLDocumentFormat format) {
        try {
            StringDocumentTarget t = new StringDocumentTarget();
            o.saveOntology(format, t);
            return t;
        } catch (OWLRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology roundTrip(OWLOntology o, OWLDocumentFormat format) {
        return loadOntologyFromString(saveOntology(o, format), format);
    }

    protected OWLOntology roundTrip(OWLOntology o, OWLDocumentFormat format,
        OWLOntologyLoaderConfiguration c) {
        return loadOntologyWithConfig(saveOntology(o, format), c);
    }

    protected OWLOntology roundTrip(OWLOntology o) {
        return loadOntologyFromString(saveOntology(o));
    }

    protected interface AxiomBuilder {
        List<OWLAxiom> build();
    }
}
