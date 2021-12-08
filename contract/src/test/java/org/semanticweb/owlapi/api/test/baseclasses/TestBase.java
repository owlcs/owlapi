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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OntologyConfigurator;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
@Timeout(value = 1000, unit = TimeUnit.SECONDS)
public abstract class TestBase extends DF {

    @TempDir
    protected File folder;
    private static final String BLANK = "blank";
    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected static final OWLDataFactory df = OWLManager.getOWLDataFactory();
    protected static OntologyConfigurator masterConfigurator;
    protected static final File RESOURCES = resources();
    protected OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
    protected OWLOntologyManager m = setupManager();
    protected OWLOntologyManager m1 = setupManager();
    protected OWLAnnotationProperty seeAlso = df.getRDFSSeeAlso();
    protected OWLDatatype intType = df.getIntegerOWLDatatype();
    protected OWLDatatype stringType = df.getStringOWLDatatype();


    private static final File resources() {
        try {
            return new File(TestBase.class.getResource("/owlapi.properties").toURI())
                .getParentFile();
        } catch (URISyntaxException ex) {
            throw new OWLRuntimeException("NO RESOURCE FOLDER ACCESSIBLE", ex);
        }
    }

    protected static List<OWLDocumentFormat> formats() {
        return l(new RDFXMLDocumentFormat(), new RioRDFXMLDocumentFormat(),
            new RDFJsonDocumentFormat(), new OWLXMLDocumentFormat(),
            new FunctionalSyntaxDocumentFormat(), new TurtleDocumentFormat(),
            new RioTurtleDocumentFormat(), new ManchesterSyntaxDocumentFormat(),
            new TrigDocumentFormat(), new RDFJsonLDDocumentFormat(), new NTriplesDocumentFormat(),
            new NQuadsDocumentFormat());
    }

    protected static Stream<OWLDocumentFormat> formatsSkip(Class<?> typeToSkip) {
        return formats().stream().filter(format -> !typeToSkip.isInstance(format));
    }

    protected static void assertThrowsWithMessage(String message,
        Class<? extends Throwable> expectedException, Executable r) {
        assertThrows(expectedException, r, message);
    }

    protected static void assertThrowsWithCauseMessage(Class<?> wrapper, Class<?> expectedException,
        @Nullable String message, Executable r) {
        assertThrowsWithCausePredicate(wrapper, expectedException,
            ex -> assertTrue(message == null || ex.getMessage().contains(message)), r);
    }

    protected static void assertThrowsWithPredicate(Class<?> expectedException,
        Consumer<Throwable> p, Executable r) {
        try {
            r.execute();
        } catch (Throwable ex) {
            assertEquals(expectedException, ex.getClass());
            p.accept(ex);
        }
    }

    protected static void assertThrowsWithCausePredicate(Class<?> wrapper,
        Class<?> expectedException, Consumer<Throwable> p, Executable r) {
        try {
            r.execute();
        } catch (Throwable ex) {
            assertEquals(wrapper, ex.getClass());
            assertNotNull(ex.getCause());
            assertEquals(expectedException, ex.getCause().getClass());
            p.accept(ex.getCause());
        }
    }

    protected static void assertThrowsWithCause(Class<?> wrapper, Class<?> expectedException,
        Executable r) {
        assertThrowsWithCauseMessage(wrapper, expectedException, null, r);
    }

    @BeforeAll
    static void setupManagers() {
        masterConfigurator = new OntologyConfigurator();
    }

    protected static OWLOntologyManager setupManager() {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        man.setOntologyConfigurator(masterConfigurator);
        return man;
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
        return ax.isOfType(AxiomType.DECLARATION) && ax.annotationsAsList().isEmpty();
    }

    protected static String str(Stream<? extends Object> stream) {
        return stream.map(Object::toString).sorted().distinct()
            .map(obj -> obj.replace(" ", "\n ").replace("(", "(\n"))
            .collect(Collectors.joining("\n"));
    }

    protected boolean equal(OWLOntology ont1, OWLOntology ont2) {
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
                        String message = getClass().getSimpleName()
                            + " roundTripOntology() Failing to match axioms: \n" + sb
                            + topOfStackTrace();
                        logger.trace(message);
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
     * 
     * @param axioms1 first set
     * @param axioms2 second set
     * @param format format
     */
    protected void applyEquivalentsRoundtrip(Set<OWLAxiom> axioms1, Set<OWLAxiom> axioms2,
        OWLDocumentFormat format) {
        if (!axioms1.equals(axioms2)) {
            // remove axioms that differ only because of n-ary equivalence
            // axioms
            // http://www.w3.org/TR/owl2-mapping-to-rdf/#Axioms_that_are_Translated_to_Multiple_Triples
            for (OWLAxiom ax : new ArrayList<>(axioms1)) {
                if (ax instanceof OWLEquivalentClassesAxiom) {
                    OWLEquivalentClassesAxiom ax2 = (OWLEquivalentClassesAxiom) ax;
                    if (ax2.getOperandsAsList().size() > 2) {
                        Collection<OWLEquivalentClassesAxiom> pairs = ax2.splitToAnnotatedPairs();
                        if (removeIfContainsAll(axioms2, pairs, format)) {
                            axioms1.remove(ax);
                            axioms2.removeAll(pairs);
                        }
                    }
                } else if (ax instanceof OWLEquivalentDataPropertiesAxiom) {
                    OWLEquivalentDataPropertiesAxiom ax2 = (OWLEquivalentDataPropertiesAxiom) ax;
                    if (ax2.getOperandsAsList().size() > 2) {
                        Collection<OWLEquivalentDataPropertiesAxiom> pairs =
                            ax2.splitToAnnotatedPairs();
                        if (removeIfContainsAll(axioms2, pairs, format)) {
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
                        if (removeIfContainsAll(axioms2, pairs, format)) {
                            axioms1.remove(ax);
                            axioms2.removeAll(pairs);
                        }
                    }
                }
            }
        }
        if (!axioms1.equals(axioms2) && format instanceof RDFJsonLDDocumentFormat) {
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

    /**
     * @param <S> type
     * @param values array
     * @return list of distinct elements
     */
    @SafeVarargs
    public static <S> Set<S> set(S... values) {
        if (values.length == 0) {
            return Collections.emptySet();
        }
        return asUnorderedSet(Stream.of(values).distinct());
    }

    /**
     * @param <S> type
     * @param witness witness type
     * @param values array
     * @return list of distinct elements
     */
    @SafeVarargs
    public static <S> Set<S> set(Class<S> witness, S... values) {
        return asUnorderedSet(Stream.of(values).distinct());
    }

    /**
     * @param <S> type
     * @param value array
     * @return list of distinct elements
     */
    public static <S> Set<S> set(S value) {
        return Collections.singleton(value);
    }

    private static Set<OWLAnnotation> reannotate(Stream<OWLAnnotation> anns) {
        OWLDatatype stringType = Datatype(OWL2Datatype.XSD_STRING.getIRI());
        Set<OWLAnnotation> toReturn = new HashSet<>();
        anns.forEach(a -> {
            Optional<OWLLiteral> asLiteral = a.getValue().asLiteral();
            if (asLiteral.isPresent() && asLiteral.get().isRDFPlainLiteral()) {
                OWLAnnotation replacement =
                    Annotation(a.getProperty(), Literal(asLiteral.get().getLiteral(), stringType));
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

    static boolean verifyErrorIsDueToBlankNodesId(Set<OWLAxiom> leftOnly, Set<OWLAxiom> rightOnly) {
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
     * @param ax axiom
     * @param parse true if the axiom belongs to the parsed ones, false for the input
     * @return true if the axiom can be ignored
     */
    public boolean isIgnorableAxiom(OWLAxiom ax, boolean parse) {
        if (ax instanceof OWLDeclarationAxiom) {
            if (parse) {
                // all extra declarations in the parsed ontology are fine
                return true;
            }
            OWLEntity entity = ((OWLDeclarationAxiom) ax).getEntity();
            // declarations of builtin and named individuals can be ignored
            return entity.isBuiltIn() || entity.isOWLNamedIndividual();
        }
        return false;
    }

    protected OWLOntology create() {
        try {
            return m.createOntology(IRI.getNextDocumentIRI(uriBase));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology create(String ontID) {
        try {
            return m.createOntology(IRI.getNextDocumentIRI(ontID));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology create(IRI iri) {
        try {
            return m.createOntology(iri);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology create(OWLOntologyID iri) {
        try {
            return m.createOntology(iri);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology createAnon() {
        try {
            return m.createOntology();
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology roundTripOntology(OWLOntology ont) {
        return roundTripOntology(ont, new RDFXMLDocumentFormat());
    }

    /*
     * Saves the specified ontology in the specified format and reloads it. Calling this method from
     * a test will cause the test to fail if the ontology could not be stored, could not be
     * reloaded, or was reloaded and the reloaded version is not equal (in terms of ontology URI and
     * axioms) with the original.
     *
     * @param ont The ontology to round trip.
     * 
     * @param format The format to use when doing the round trip.
     */
    protected OWLOntology roundTripOntology(OWLOntology ont, OWLDocumentFormat format) {
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
        } catch (OWLException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Test
    void checkVerify() {
        Set<OWLAxiom> ax1 = new HashSet<>();
        ax1.add(DataPropertyAssertion(DPT, AnonymousIndividual(), Literal("test1")));
        ax1.add(DataPropertyAssertion(DPT, AnonymousIndividual(), Literal("test2")));
        Set<OWLAxiom> ax2 = new HashSet<>();
        ax2.add(DataPropertyAssertion(DPT, AnonymousIndividual(), Literal("test1")));
        ax2.add(DataPropertyAssertion(DPT, AnonymousIndividual(), Literal("test2")));
        assertFalse(ax1.equals(ax2));
        assertTrue(verifyErrorIsDueToBlankNodesId(ax1, ax2));
    }

    @SuppressWarnings("unused")
    protected void handleSaved(StringDocumentTarget target, OWLDocumentFormat format) {
        // System.out.println(target.toString());
    }

    protected OWLOntology load(String fileName) {
        return load(fileName, m);
    }

    protected OWLOntology load(String fileName, OWLOntologyManager manager) {
        try {
            URL url = getClass().getResource('/' + fileName);
            return manager.loadOntologyFromOntologyDocument(
                new IRIDocumentSource(IRI.create(url), null, null),
                new OWLOntologyLoaderConfiguration().setReportStackTraces(true));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(InputStream input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(input);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected OWLOntology loadFrom(OWLOntologyDocumentSource input) {
        return loadFrom(input, setupManager());
    }

    @Nonnull
    protected OWLOntology loadFrom(OWLOntologyDocumentSource input, OWLOntologyManager manager) {
        try {
            return manager.loadOntologyFromOntologyDocument(input);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected OWLOntology loadFrom(OWLOntologyDocumentSource input,
        OWLOntologyLoaderConfiguration conf) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(input, conf);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }


    @Nonnull
    protected OWLOntology loadFrom(File input) {
        return loadFrom(input, setupManager());
    }

    @Nonnull
    protected OWLOntology loadFrom(File input, OWLOntologyManager manager) {
        try {
            return manager.loadOntologyFromOntologyDocument(input);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Nonnull
    protected OWLOntology loadFrom(File input, OWLDocumentFormat format,
        OWLOntologyManager manager) {
        try {
            return manager.loadOntologyFromOntologyDocument(new FileDocumentSource(input, format));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(String input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        } catch (OWLException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(IRI input) {
        return loadFrom(input, setupManager());
    }

    protected OWLOntology loadFrom(IRI input, OWLOntologyManager manager) {
        try {
            return manager.loadOntology(input);
        } catch (OWLException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(String input, IRI iri, OWLDocumentFormat format) {
        StringDocumentSource documentSource = new StringDocumentSource(input, iri, format, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(String input, OWLDocumentFormat format) {
        StringDocumentSource documentSource =
            new StringDocumentSource(input, IRI.generateDocumentIRI(), format, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(String input, OWLDocumentFormat format,
        OWLOntologyLoaderConfiguration conf) {
        StringDocumentSource documentSource =
            new StringDocumentSource(input, IRI.generateDocumentIRI(), format, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource, conf);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(StringDocumentSource input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(input);
        } catch (OWLException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(StringDocumentTarget input) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadFrom(StringDocumentTarget input, OWLDocumentFormat format) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input.toString(), "string:ontology", format, null));
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology loadStrict(StringDocumentTarget o) {
        return loadWithConfig(o, new OWLOntologyLoaderConfiguration().setStrict(true));
    }

    protected OWLOntology loadStrict(String content, OWLDocumentFormat format) {
        return loadWithConfig(new StringDocumentSource(content, "string:ontology", format, null),
            new OWLOntologyLoaderConfiguration().setStrict(true));
    }

    protected OWLOntology loadWithConfig(StringDocumentTarget o,
        OWLOntologyLoaderConfiguration conf) {
        return loadWithConfig(new StringDocumentSource(o), conf);
    }

    protected OWLOntology loadWithConfig(StringDocumentSource o,
        OWLOntologyLoaderConfiguration conf) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(o, conf);
        } catch (OWLException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected StringDocumentTarget saveOntology(OWLOntology o) {
        return saveOntology(o, o.getNonnullFormat());
    }

    protected StringDocumentTarget saveOntology(OWLOntology o, OWLDocumentFormat format) {
        try {
            StringDocumentTarget target = new StringDocumentTarget();
            o.saveOntology(format, target);
            return target;
        } catch (OWLException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected void saveOntology(OWLOntology o, IRI destination) {
        try {
            o.saveOntology(destination);
        } catch (OWLException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    protected OWLOntology roundTrip(OWLOntology o, OWLDocumentFormat format) {
        return loadFrom(saveOntology(o, format), format);
    }

    protected OWLOntology roundTrip(OWLOntology o, OWLDocumentFormat format,
        OWLOntologyLoaderConfiguration conf) {
        return loadWithConfig(saveOntology(o, format), conf);
    }

    protected OWLOntology roundTrip(OWLOntology o) {
        return loadFrom(saveOntology(o));
    }

    protected interface AxiomBuilder {
        List<OWLAxiom> build();
    }

    protected OWLOntology o(OWLAxiom... a) {
        return o(l(a));
    }

    protected OWLOntology o(OWLAxiom a) {
        return o(l(a));
    }

    protected OWLOntology o(Collection<OWLAxiom> a) {
        return o(a, createAnon());
    }

    protected OWLOntology o(IRI iri, OWLAxiom... a) {
        return o(iri, l(a));
    }

    protected OWLOntology o(IRI iri, OWLAxiom a) {
        return o(iri, l(a));
    }

    protected OWLOntology o(IRI iri, Collection<OWLAxiom> a) {
        return o(a, create(iri));
    }

    protected OWLOntology o(Collection<OWLAxiom> a, OWLOntology ont) {
        ont.addAxioms(a);
        ont.unsortedSignature()
            .filter(entity -> !entity.isBuiltIn() && !ont.isDeclared(entity, Imports.INCLUDED))
            .forEach(entity -> ont.getOWLOntologyManager().addAxiom(ont, Declaration(entity)));
        return ont;
    }

    protected String str(Collection<?> expected) {
        return expected.stream().map(Object::toString).sorted().distinct()
            .collect(Collectors.joining("\n"));
    }
}
