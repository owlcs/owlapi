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

import static java.util.stream.Collectors.toSet;
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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import org.junit.jupiter.api.BeforeEach;
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
import org.semanticweb.owlapi.formats.RDFDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TrigDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
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
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.util.OWLAPIPreconditions;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
@Timeout(value = 1000, unit = TimeUnit.SECONDS)
public abstract class TestBase {
    protected static final String uriBase = "http://www.semanticweb.org/owlapi/test";
    public static final String OWLAPI_TEST = uriBase + "#";

    @TempDir
    public File folder;
    private static final String BLANK = "blank";
    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected static OWLDataFactory df;

    public static final String OBO = "http://purl.obolibrary.org/obo/";

    public static final OWLClass A = Class(iri("A"));
    public static final OWLClass B = Class(iri("B"));
    public static final OWLClass C = Class(iri("C"));
    public static final OWLClass D = Class(iri("D"));
    public static final OWLClass E = Class(iri("E"));
    public static final OWLClass F = Class(iri("F"));
    public static final OWLClass G = Class(iri("G"));
    public static final OWLClass K = Class(iri("K"));
    public static final OWLClass X = Class(iri("X"));
    public static final OWLClass Y = Class(iri("Y"));

    public static final OWLClass C1 = Class(iri(OBO, "TEST_1"));
    public static final OWLClass C2 = Class(iri(OBO, "TEST_2"));
    public static final OWLClass C3 = Class(iri(OBO, "TEST_3"));
    public static final OWLClass C4 = Class(iri(OBO, "TEST_4"));
    public static final OWLClass C5 = Class(iri(OBO, "TEST_5"));

    public static final OWLObjectProperty c = ObjectProperty(iri("c"));
    public static final OWLObjectProperty d = ObjectProperty(iri("d"));
    public static final OWLObjectProperty e = ObjectProperty(iri("e"));
    public static final OWLObjectProperty f = ObjectProperty(iri("f"));
    public static final OWLObjectProperty P = ObjectProperty(iri("p"));
    public static final OWLObjectProperty Q = ObjectProperty(iri("q"));
    public static final OWLObjectProperty R = ObjectProperty(iri("r"));
    public static final OWLObjectProperty S = ObjectProperty(iri("s"));
    public static final OWLObjectProperty t = ObjectProperty(iri("t"));
    public static final OWLObjectProperty u = ObjectProperty(iri("u"));
    public static final OWLObjectProperty w = ObjectProperty(iri("w"));
    public static final OWLObjectProperty z = ObjectProperty(iri("z"));


    public static final OWLDataProperty DP = DataProperty(iri("p"));
    public static final OWLDataProperty DQ = DataProperty(iri("q"));
    public static final OWLDataProperty DR = DataProperty(iri("r"));
    public static final OWLDataProperty DS = DataProperty(iri("s"));

    public static final OWLDataProperty DPROP = DataProperty(iri("prop"));
    public static final OWLObjectProperty PROP = ObjectProperty(iri("prop"));
    public static final OWLDataProperty DPP = DataProperty(iri("dp"));
    public static final OWLDataProperty dp1 = DataProperty(iri("dp1"));
    public static final OWLDataProperty dp2 = DataProperty(iri("dp2"));
    public static final OWLDataProperty dp3 = DataProperty(iri("dp3"));
    public static final OWLObjectProperty op1 = ObjectProperty(iri("op1"));
    public static final OWLObjectProperty op2 = ObjectProperty(iri("op2"));

    public static final OWLNamedIndividual I = NamedIndividual(iri("i"));
    public static final OWLNamedIndividual J = NamedIndividual(iri("j"));
    public static final OWLNamedIndividual k = NamedIndividual(iri("k"));
    public static final OWLNamedIndividual l = NamedIndividual(iri("l"));

    public static final OWLNamedIndividual indA = NamedIndividual(iri("a"));
    public static final OWLNamedIndividual indB = NamedIndividual(iri("b"));
    public static final OWLNamedIndividual indC = NamedIndividual(iri("c"));
    public static final OWLObjectComplementOf notC = ObjectComplementOf(C);
    public static final OWLObjectComplementOf notB = ObjectComplementOf(B);
    public static final OWLObjectComplementOf notA = ObjectComplementOf(A);
    public static final OWLNamedIndividual i = NamedIndividual(iri("I"));
    public static final OWLAnnotationProperty AP = AnnotationProperty(iri("propA"));
    public static final OWLAnnotationProperty propP = AnnotationProperty(iri("propP"));
    public static final OWLAnnotationProperty propQ = AnnotationProperty(iri("propQ"));
    public static final OWLAnnotationProperty propR = AnnotationProperty(iri("propR"));
    public static final OWLDataProperty PD = DataProperty(iri("propD"));
    public static final OWLDatatype DT = Datatype(iri("DT"));
    public static final OWLDatatype DTA = Datatype(iri("DtA"));
    public static final OWLDatatype DTB = Datatype(iri("DtB"));
    public static final OWLDatatype DTC = Datatype(iri("DtC"));
    public static final OWLAnnotationProperty areaTotal =
        AnnotationProperty(IRI("http://dbpedia.org/ontology/", "areaTotal"));
    public static final IRI southAfrica = IRI("http://dbpedia.org/resource/", "South_Africa");
    public static final OWLLiteral oneMillionth = Literal("1.0E-7", OWL2Datatype.XSD_DOUBLE);

    protected OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
    protected static final File RESOURCES = resources();
    protected OWLOntologyManager m;
    protected OWLOntologyManager m1;

    public static IRI iri(String name) {
        return iri(OWLAPI_TEST, name);
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
        } catch (URISyntaxException ex) {
            throw new OWLRuntimeException("NO RESOURCE FOLDER ACCESSIBLE", ex);
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

    public static List<OWLDocumentFormat> formatsNoRio() {
        return Arrays.asList(new RDFXMLDocumentFormat(), new OWLXMLDocumentFormat(),
            new FunctionalSyntaxDocumentFormat(), new TurtleDocumentFormat(),
            new ManchesterSyntaxDocumentFormat());
    }

    public static Stream<OWLDocumentFormat> formatsSkip(Class<?> witness) {
        return formats().stream().filter(x -> !witness.isInstance(x));
    }

    public static void assertThrowsWithMessage(String message,
        Class<? extends Throwable> expectedException, Executable r) {
        assertThrows(expectedException, r, message);
    }

    public static void assertThrowsWithCauseMessage(Class<?> wrapper, Class<?> expectedClass,
        @Nullable String message, Executable r) {
        assertThrowsWithCausePredicate(wrapper, expectedClass,
            ex -> assertTrue(message == null || ex.getMessage().contains(message)), r);
    }

    public static void assertThrowsWithPredicate(Class<?> expectedClass, Consumer<Throwable> p,
        Executable r) {
        try {
            r.execute();
        } catch (Throwable ex) {
            assertEquals(expectedClass, ex.getClass());
            p.accept(ex);
        }
    }

    public static void assertThrowsWithCausePredicate(Class<?> wrapper, Class<?> expectedClass,
        Consumer<Throwable> p, Executable r) {
        try {
            r.execute();
        } catch (Throwable ex) {
            assertEquals(wrapper, ex.getClass());
            assertNotNull(ex.getCause());
            assertEquals(expectedClass, ex.getCause().getClass());
            p.accept(ex.getCause());
        }
    }

    public static void assertThrowsWithCause(Class<?> wrapper, Class<?> expectedClass,
        Executable r) {
        assertThrowsWithCauseMessage(wrapper, expectedClass, null, r);
    }

    @BeforeAll
    public static void setupManagers() {
        df = OWLManager.getOWLDataFactory();
    }

    protected static OWLOntologyManager setupManager() {
        return OWLManager.createOWLOntologyManager();
    }

    protected static OWLOntologyManager setupConcurrentManager() {
        return OWLManager.createConcurrentOWLOntologyManager();
    }

    protected static <S> Set<S> singleton(S s) {
        return Collections.singleton(s);
    }

    @BeforeEach
    void setupManagersClean() {
        m = setupManager();
        m1 = setupManager();
    }

    protected Optional<IRI> absent() {
        return Optional.empty();
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
        } catch (OWLOntologyCreationException | IOException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology ontologyFromClasspathFile(String fileName,
        OWLOntologyLoaderConfiguration configuration, @Nullable OWLDocumentFormat format) {
        if (format == null) {
            return ontologyFromClasspathFile(fileName, configuration);
        }
        URL resource = getClass().getResource('/' + fileName);
        try (InputStream resourceAsStream = new FileInputStream(new File(resource.toURI()))) {
            return m1.loadOntologyFromOntologyDocument(
                new StreamDocumentSource(resourceAsStream, IRI.create(resource), format, null),
                configuration);
        } catch (OWLOntologyCreationException | IOException | URISyntaxException ex) {
            throw new OWLRuntimeException(ex);
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
        return ax.isOfType(AxiomType.DECLARATION) && ax.getAnnotations().isEmpty();
    }

    private static String str(Stream<?> s) {
        return s.map(Object::toString).map(string -> string.replace(" ", "\n ").replace("(", "(\n"))
            .collect(Collectors.joining("\n"));
    }

    public boolean equal(OWLOntology ont1, OWLOntology ont2) {
        if (!ont1.isAnonymous() && !ont2.isAnonymous()) {
            assertEquals(ont1.getOntologyID(), ont2.getOntologyID(),
                "Ontologies supposed to be the same");
        }
        if (!Objects.equals(ont1.getAnnotations(), ont2.getAnnotations())) {
            assertEquals(str(ont1.getAnnotations().stream()), str(ont2.getAnnotations().stream()));
        }
        assertEquals(ont1.getAnnotations(), ont2.getAnnotations(),
            "Annotations supposed to be the same");
        Set<OWLAxiom> axioms1;
        Set<OWLAxiom> axioms2;
        // This isn't great - we normalise axioms by changing the ids of
        // individuals. This relies on the fact that
        // we iterate over objects in the same order for the same set of axioms!
        axioms1 = new AnonymousIndividualsNormaliser(df).getNormalisedAxioms(ont1.getAxioms());
        axioms2 = new AnonymousIndividualsNormaliser(df).getNormalisedAxioms(ont2.getAxioms());
        OWLDocumentFormat ontologyFormat = ont2.getOWLOntologyManager().getOntologyFormat(ont2);
        applyEquivalentsRoundtrip(axioms1, axioms2, ontologyFormat);
//        if (ontologyFormat instanceof ManchesterSyntaxDocumentFormat) {
//            // drop GCIs from the expected axioms, they won't be there
//            Iterator<OWLAxiom> it = axioms1.iterator();
//            while (it.hasNext()) {
//                OWLAxiom next = it.next();
//                if (next instanceof OWLSubClassOfAxiom) {
//                    OWLSubClassOfAxiom n = (OWLSubClassOfAxiom) next;
//                    if (n.getSubClass().isAnonymous() && n.getSuperClass().isAnonymous()) {
//                        it.remove();
//                    }
//                }
//            }
//        }
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
            if (counter > 0) {
                // a test fails on OpenJDK implementations because of ordering
                // testing here if blank node ids are the only difference
                boolean fixed = !verifyErrorIsDueToBlankNodesId(leftOnly, rightOnly);
                if (fixed) {
                    String x = getClass().getSimpleName()
                        + " roundTripOntology() Failing to match axioms: \n" + sb;
                    // System.out.println(x);
                    fail(x);
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        // assertEquals(axioms1, axioms2);
        return true;
    }

    /**
     * equivalent entity axioms with more than two entities are broken up by RDF syntaxes. Ensure
     * they are still recognized as correct roundtripping
     */
    void applyEquivalentsRoundtrip(Set<OWLAxiom> axioms1, Set<OWLAxiom> axioms2,
        OWLDocumentFormat destination) {
        if (!axioms1.equals(axioms2)) {
            // remove axioms that differ only because of n-ary equivalence
            // axioms
            // http://www.w3.org/TR/owl2-mapping-to-rdf/#Axioms_that_are_Translated_to_Multiple_Triples
            for (OWLAxiom ax : new ArrayList<>(axioms1)) {
                if (ax instanceof OWLEquivalentClassesAxiom) {
                    OWLEquivalentClassesAxiom ax2 = (OWLEquivalentClassesAxiom) ax;
                    if (ax2.getClassExpressions().size() > 2) {
                        Set<OWLEquivalentClassesAxiom> pairs = ax2.splitToAnnotatedPairs();
                        if (removeIfContainsAll(axioms2, pairs, destination)) {
                            axioms1.remove(ax);
                            axioms2.removeAll(pairs);
                        }
                    }
                } else if (ax instanceof OWLEquivalentDataPropertiesAxiom) {
                    OWLEquivalentDataPropertiesAxiom ax2 = (OWLEquivalentDataPropertiesAxiom) ax;
                    if (ax2.getProperties().size() > 2) {
                        Set<OWLEquivalentDataPropertiesAxiom> pairs = ax2.splitToAnnotatedPairs();
                        if (removeIfContainsAll(axioms2, pairs, destination)) {
                            axioms1.remove(ax);
                            axioms2.removeAll(pairs);
                        }
                    }
                } else if (ax instanceof OWLEquivalentObjectPropertiesAxiom) {
                    OWLEquivalentObjectPropertiesAxiom ax2 =
                        (OWLEquivalentObjectPropertiesAxiom) ax;
                    if (ax2.getProperties().size() > 2) {
                        Set<OWLEquivalentObjectPropertiesAxiom> pairs = ax2.splitToAnnotatedPairs();
                        if (removeIfContainsAll(axioms2, pairs, destination)) {
                            axioms1.remove(ax);
                            axioms2.removeAll(pairs);
                        }
                    }
                }
            }
        }
    }

    private static boolean removeIfContainsAll(Collection<OWLAxiom> axioms,
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
            OWLAxiom reannotated =
                ax.getAxiomWithoutAnnotations().getAnnotatedAxiom(reannotate(ax.getAnnotations()));
            toRemove.add(reannotated);
        }
        axioms.removeAll(toRemove);
        return true;
    }

    @SafeVarargs
    public static <S> Set<S> set(S... s) {
        return new HashSet<>(Arrays.asList(s));
    }

    private static Set<OWLAnnotation> reannotate(Set<OWLAnnotation> anns) {
        OWLDatatype stringType = df.getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI());
        Set<OWLAnnotation> toReturn = new HashSet<>();
        for (OWLAnnotation a : anns) {
            if (a.getValue().asLiteral().isPresent()
                && a.getValue().asLiteral().get().isRDFPlainLiteral()) {
                OWLAnnotation replacement = df.getOWLAnnotation(a.getProperty(),
                    df.getOWLLiteral(a.getValue().asLiteral().get().getLiteral(), stringType));
                toReturn.add(replacement);
            } else {
                toReturn.add(a);
            }
        }
        return toReturn;
    }

    /**
     * @param leftOnly
     * @param rightOnly
     * @return
     */
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
            OWLDeclarationAxiom decl = (OWLDeclarationAxiom) ax;
            // declarations of builtin and named individuals can be ignored
            return decl.getEntity().isBuiltIn() || decl.getEntity().isOWLNamedIndividual();
        }
        return false;
    }

    public OWLOntology create(String name) {
        return create(IRI(uriBase + '/' + name));
    }

    public OWLOntology create() {
        try {
            return m.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI(uriBase));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    public OWLOntology create(IRI iri) {
        try {
            return m.createOntology(iri);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    public OWLOntology create(OWLOntologyID iri) {
        try {
            return m.createOntology(iri);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    public OWLOntology createAnon() {
        try {
            return m.createOntology();
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    public OWLOntology loadOntology(String fileName) {
        return loadOntology(fileName, m);
    }

    public OWLOntology loadOntology(String fileName, OWLOntologyManager manager) {
        return loadOntology(IRI.create(getClass().getResource('/' + fileName)), manager);
    }

    public OWLOntology loadOntology(IRI iri, OWLOntologyManager manager) {
        try {
            return manager.loadOntology(iri);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected void addAxiom(OWLOntology ont, OWLAxiom ax) {
        m.addAxiom(ont, ax);
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
     * @param ont The ontology to be round tripped.
     * @param format The format to use when doing the round trip.
     */
    public OWLOntology roundTripOntology(OWLOntology ont, OWLDocumentFormat format) {
        try {
            StringDocumentTarget target = new StringDocumentTarget();
            OWLDocumentFormat fromFormat = m.getOntologyFormat(ont);
            if (fromFormat.isPrefixOWLOntologyFormat() && format.isPrefixOWLOntologyFormat()) {
                PrefixDocumentFormat fromPrefixFormat = fromFormat.asPrefixOWLOntologyFormat();
                PrefixDocumentFormat toPrefixFormat = format.asPrefixOWLOntologyFormat();
                toPrefixFormat.copyPrefixesFrom(fromPrefixFormat);
                toPrefixFormat.setDefaultPrefix(null);
            }
            boolean addMissingTypes = true;
            if (format instanceof RDFDocumentFormat) {
                format.setAddMissingTypes(addMissingTypes);
            }
            if (logger.isTraceEnabled()) {
                StringDocumentTarget targetForDebug = new StringDocumentTarget();
                m.saveOntology(ont, format, targetForDebug);
                logger.trace(targetForDebug.toString());
            }
            m.saveOntology(ont, format, target);
            handleSaved(target, format);
            OWLOntology ont2 = setupManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(target.toString(),
                    OWLOntologyDocumentSourceBase.getNextDocumentIRI("string:ontology"), format,
                    null), new OWLOntologyLoaderConfiguration().setReportStackTraces(true));
            if (logger.isTraceEnabled()) {
                logger.trace("TestBase.roundTripOntology() ontology parsed");
                Set<OWLAxiom> axioms = ont2.getAxioms();
                for (OWLAxiom ax : axioms) {
                    logger.trace(ax.toString());
                }
            }
            equal(ont, ont2);
            return ont2;
        } catch (OWLException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected void checkVerify() {
        OWLDataProperty tTest = df.getOWLDataProperty(iri("urn:test#", "t"));
        Set<OWLAxiom> ax1 = new HashSet<>();
        ax1.add(df.getOWLDataPropertyAssertionAxiom(tTest, df.getOWLAnonymousIndividual(),
            df.getOWLLiteral("test1")));
        ax1.add(df.getOWLDataPropertyAssertionAxiom(tTest, df.getOWLAnonymousIndividual(),
            df.getOWLLiteral("test2")));
        Set<OWLAxiom> ax2 = new HashSet<>();
        ax2.add(df.getOWLDataPropertyAssertionAxiom(tTest, df.getOWLAnonymousIndividual(),
            df.getOWLLiteral("test1")));
        ax2.add(df.getOWLDataPropertyAssertionAxiom(tTest, df.getOWLAnonymousIndividual(),
            df.getOWLLiteral("test2")));
        assertFalse(ax1.equals(ax2));
        assertTrue(verifyErrorIsDueToBlankNodesId(ax1, ax2));
    }

    @SuppressWarnings("unused")
    protected boolean isIgnoreDeclarationAxioms(OWLDocumentFormat format) {
        return true;
    }

    @SuppressWarnings("unused")
    protected void handleSaved(StringDocumentTarget target, OWLDocumentFormat format) {
        // System.out.println(target.toString());
    }

    protected OWLOntology loadOntologyFromString(String input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology createFile(IRI iri, File file) {
        try {
            OWLOntology a = create(iri);
            try (OutputStream out = new FileOutputStream(file)) {
                a.saveOntology(out);
            }
            return a;
        } catch (IOException | OWLOntologyStorageException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromSource(OWLOntologyDocumentSource input) {
        return loadOntologyFromSource(input, setupManager());
    }

    protected OWLOntology loadOntologyFromString(IRI input) {
        return loadOntologyFromString(input, setupManager());
    }

    protected OWLOntology loadOntologyFromString(IRI input, OWLOntologyManager manager) {
        return loadOntologyFromSource(new IRIDocumentSource(input), manager);
    }

    protected OWLOntology loadOntologyFromSource(OWLOntologyDocumentSource input,
        OWLOntologyManager manager) {
        try {
            return manager.loadOntologyFromOntologyDocument(input);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromSource(OWLOntologyDocumentSource input,
        OWLOntologyLoaderConfiguration conf) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(input, conf);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromFile(File input) {
        return loadOntologyFromFile(input, setupManager());
    }

    protected OWLOntology loadOntologyFromFile(File input, OWLOntologyManager manager) {
        try {
            return manager.loadOntologyFromOntologyDocument(input);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromFile(File input, OWLDocumentFormat format,
        OWLOntologyManager manager) {
        try {
            return manager.loadOntologyFromOntologyDocument(new FileDocumentSource(input, format));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFrom(InputStream input) {
        return loadOntologyFrom(input, setupManager());
    }

    protected OWLOntology loadOntologyFrom(InputStream input, OWLOntologyManager manager) {
        try {
            return manager.loadOntologyFromOntologyDocument(input);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromString(String input, IRI iri, OWLDocumentFormat format) {
        StringDocumentSource documentSource = new StringDocumentSource(input, iri, format, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromString(String input, OWLDocumentFormat format) {
        StringDocumentSource documentSource =
            new StringDocumentSource(input, IRI.generateDocumentIRI(), format, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromString(String input, OWLDocumentFormat format,
        OWLOntologyLoaderConfiguration conf) {
        StringDocumentSource documentSource =
            new StringDocumentSource(input, IRI.generateDocumentIRI(), format, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource, conf);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromString(StringDocumentSource input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(input);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromString(StringDocumentTarget input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyFromString(StringDocumentTarget input,
        OWLDocumentFormat format) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(
                input.toString(),
                OWLOntologyDocumentSourceBase.getNextDocumentIRI("string:ontology"), format, null));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyStrict(StringDocumentTarget o) {
        return loadOntologyWithConfig(o, new OWLOntologyLoaderConfiguration().setStrict(true));
    }

    protected OWLOntology loadOntologyStrict(StringDocumentTarget o, OWLDocumentFormat format) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(
                o.toString(), OWLOntologyDocumentSourceBase.getNextDocumentIRI("string:ontology"),
                format, null), new OWLOntologyLoaderConfiguration().setStrict(true));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyWithConfig(StringDocumentTarget o,
        OWLOntologyLoaderConfiguration conf) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(o),
                conf);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadOntologyWithConfig(StringDocumentSource o,
        OWLOntologyLoaderConfiguration conf) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(o, conf);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected StringDocumentTarget saveOntology(OWLOntology o) {
        return saveOntology(o, o.getOWLOntologyManager().getOntologyFormat(o));
    }

    protected StringDocumentTarget saveOntology(OWLOntology o, OWLDocumentFormat format) {
        StringDocumentTarget target = new StringDocumentTarget();
        try {
            o.getOWLOntologyManager().saveOntology(o, format, target);
        } catch (OWLOntologyStorageException ex) {
            throw new OWLRuntimeException(ex);
        }
        return target;
    }

    protected void saveOntology(OWLOntology o, OutputStream out) {
        try {
            o.getOWLOntologyManager().saveOntology(o, out);
        } catch (OWLOntologyStorageException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected void saveOntology(OWLOntology o, IRI iri) {
        try {
            o.getOWLOntologyManager().saveOntology(o, iri);
        } catch (OWLOntologyStorageException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology roundTrip(OWLOntology o, OWLDocumentFormat format) {
        return loadOntologyFromString(saveOntology(o, format), format);
    }

    protected OWLOntology roundTrip(OWLOntology o, OWLDocumentFormat format,
        OWLOntologyLoaderConfiguration conf) {
        return loadOntologyWithConfig(saveOntology(o, format), conf);
    }

    protected OWLOntology roundTrip(OWLOntology o) {
        return loadOntologyFromString(saveOntology(o));
    }

    /**
     * @param <T> type
     * @param s stream to turn to set. The stream is consumed by this operation.
     * @return set including all elements in the stream
     */
    public static <T> Set<T> asSet(Stream<T> s) {
        return s.collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * @param s stream to turn to set. The stream is consumed by this operation.
     * @param type force return type to be exactly T
     * @param <T> type of return collection
     * @return set including all elements in the stream
     */
    public static <T> Set<T> asSet(Stream<?> s, Class<T> type) {
        Set<T> set = new LinkedHashSet<>();
        s.map(type::cast).forEach(set::add);
        return set;
    }

    /**
     * @param <T> type
     * @param s stream to turn to set. The stream is consumed by this operation.
     * @return set including all elements in the stream
     */
    public static <T> Set<T> asUnorderedSet(Stream<T> s) {
        return s.collect(toSet());
    }

    /**
     * @param s stream to turn to set. The stream is consumed by this operation.
     * @param type force return type to be exactly T
     * @param <T> type of return collection
     * @return set including all elements in the stream
     */
    public static <T> Set<T> asUnorderedSet(Stream<?> s, Class<T> type) {
        return s.map(type::cast).collect(toSet());
    }

    /**
     * @param <T> type
     * @param s stream to turn to list. The stream is consumed by this operation.
     * @return list including all elements in the stream
     */
    public static <T> List<T> asList(Stream<T> s) {
        return s.collect(Collectors.toList());
    }

    /**
     * @param <T> type
     * @param s stream to turn to list. The stream is consumed by this operation.
     * @return list including all elements in the stream
     */
    public static <T> List<T> asList(Collection<T> s) {
        return new ArrayList<>(s);
    }

    /**
     * @param <T> type
     * @param s stream to turn to list. The stream is consumed by this operation.
     * @return list including all elements in the stream
     */
    public static <T> List<T> asListNullsForbidden(Stream<T> s) {
        return asList(s.map(OWLAPIPreconditions::checkNotNull));
    }

    /**
     * @param s stream to turn to list. The stream is consumed by this operation.
     * @param type force return type to be exactly T
     * @param <T> type of return collection
     * @return list including all elements in the stream
     */
    public static <T> List<T> asList(Stream<?> s, Class<T> type) {
        return asList(s.map(type::cast));
    }

    protected interface AxiomBuilder {
        List<OWLAxiom> build();
    }

    protected OWLOntology o(OWLAxiom... a) {
        return o(new HashSet<>(Arrays.asList(a)));
    }

    protected OWLOntology o(OWLAxiom a) {
        return o(Collections.singleton(a));
    }

    protected OWLOntology o(Set<OWLAxiom> a) {
        OWLOntology ont = createAnon();
        ont.getOWLOntologyManager().addAxioms(ont, a);
        ont.getSignature().stream()
            .filter(entity -> !entity.isBuiltIn() && !ont.isDeclared(entity, Imports.INCLUDED))
            .forEach(entity -> ont.getOWLOntologyManager().addAxiom(ont, Declaration(entity)));
        return ont;
    }
}
