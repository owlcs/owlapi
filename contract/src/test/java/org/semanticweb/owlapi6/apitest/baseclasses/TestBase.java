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
package org.semanticweb.owlapi6.apitest.baseclasses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import java.io.File;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.function.ThrowingRunnable;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.apitest.anonymous.AnonymousIndividualsNormaliser;
import org.semanticweb.owlapi6.documents.IRIDocumentSource;
import org.semanticweb.owlapi6.documents.StreamDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
import org.semanticweb.owlapi6.rioformats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public abstract class TestBase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected interface AxiomBuilder {

        List<OWLAxiom> build();
    }

    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected final File RESOURCES = resources();

    protected <T> T get(Optional<T> t) {
        return t.get();
    }

    protected OWLOntology ontologyFromClasspathFile(String fileName) {
        return ontologyFromClasspathFile(fileName, config);
    }

    protected OWLOntology ontologyFromClasspathFile(String fileName,
        OntologyConfigurator configuration) {
        try {
            return m1.loadOntologyFromOntologyDocument(
                new StreamDocumentSource(getClass().getResourceAsStream('/' + fileName)),
                configuration);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private static final File resources() {
        try {
            return new File(TestBase.class.getResource("/owlapi.properties").toURI())
                .getParentFile();
        } catch (URISyntaxException e) {
            throw new OWLRuntimeException("NO RESOURCE FOLDER ACCESSIBLE", e);
        }
    }

    public static void assertThrowsWithMessage(String message, Class<? extends Throwable> c, ThrowingRunnable r) {
        assertTrue(assertThrows(c, r).getMessage().contains(message));
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    @Rule
    public Timeout timeout = new Timeout(1000000, TimeUnit.MILLISECONDS);
    protected OntologyConfigurator config = new OntologyConfigurator();
    protected static OWLDataFactory df;
    protected static OntologyConfigurator masterConfigurator;
    protected OWLOntologyManager m = setupManager();
    protected OWLOntologyManager m1 = setupManager();

    @BeforeClass
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
            assertEquals("Ontologies supposed to be the same", ont1.getOntologyID(),
                ont2.getOntologyID());
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
                    if (((OWLSubClassOfAxiom) next).getSubClass().isAnonymous()
                        && ((OWLSubClassOfAxiom) next).getSuperClass().isAnonymous()) {
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
                if (!b.contains(ax)) {
                    if (!isIgnorableAxiom(ax, false)) {
                        leftOnly.add(ax);
                        sb.append("Rem axiom: ").append(ax).append('\n');
                        counter++;
                    }
                }
            }
            for (OWLAxiom ax : b) {
                if (!a.contains(ax)) {
                    if (!isIgnorableAxiom(ax, true)) {
                        rightOnly.add(ax);
                        sb.append("Add axiom: ").append(ax).append('\n');
                        counter++;
                    }
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
        OWLAxiom reannotated =
            ax.getAxiomWithoutAnnotations().getAnnotatedAxiom(reannotate(ax.annotations()));
        return reannotated;
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
            leftOnlyStrings.add(ax.toString().replaceAll("_:anon-ind-[0-9]+", "blank")
                .replaceAll("_:genid[0-9]+", "blank"));
        }
        for (OWLAxiom ax : rightOnly) {
            rightOnlyStrings.add(ax.toString().replaceAll("_:anon-ind-[0-9]+", "blank")
                .replaceAll("_:genid[0-9]+", "blank"));
        }
        return rightOnlyStrings.equals(leftOnlyStrings);
    }

    /**
     * ignore declarations of builtins and of named individuals - named individuals do not /need/ a
     * declaration, but addiong one is not an error.
     * 
     * @param parse true if the axiom belongs to the parsed ones, false for the input
     * @return true if the axiom can be ignored
     */
    public boolean isIgnorableAxiom(OWLAxiom ax, boolean parse) {
        if (ax instanceof OWLDeclarationAxiom) {
            OWLDeclarationAxiom d = (OWLDeclarationAxiom) ax;
            if (parse) {
                // all extra declarations in the parsed ontology are fine
                return true;
            }
            // declarations of builtin and named individuals can be ignored
            return d.getEntity().isBuiltIn() || d.getEntity().isOWLNamedIndividual();
        }
        return false;
    }

    protected static final String uriBase = "http://www.semanticweb.org/owlapi/test";

    public OWLOntology getOWLOntology() {
        try {
            return m.createOntology(df.getNextDocumentIRI(uriBase));
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

    public static IRI iri(String name) {
        return IRI(uriBase + '#', name);
    }

    public OWLOntology roundTripOntology(OWLOntology ont)
        throws OWLOntologyStorageException, OWLOntologyCreationException {
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
    public OWLOntology roundTripOntology(OWLOntology ont, OWLDocumentFormat format)
        throws OWLOntologyStorageException, OWLOntologyCreationException {
        StringDocumentTarget target = new StringDocumentTarget();
        if (logger.isTraceEnabled()) {
            StringDocumentTarget targetForDebug = new StringDocumentTarget();
            ont.saveOntology(format, targetForDebug);
            logger.trace(targetForDebug.toString());
        }
        ont.saveOntology(format, target);
        // ont.axioms().forEach(System.out::println);
        // System.out.println("TestBase.roundTripOntology() " + target);
        OWLOntology ont2 = setupManager().loadOntologyFromOntologyDocument(
            new StringDocumentSource(target.toString(), "string:ontology", format, null),
            new OntologyConfigurator().setReportStackTraces(true));
        if (logger.isTraceEnabled()) {
            logger.trace("TestBase.roundTripOntology() ontology parsed");
            ont2.axioms().forEach(ax -> logger.trace(ax.toString()));
        }
        equal(ont, ont2);
        return ont2;
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

    public OWLOntology loadOntology(String fileName) {
        return loadOntology(fileName, m);
    }

    public OWLOntology loadOntology(String fileName, OWLOntologyManager manager) {
        try {
            URL url = getClass().getResource('/' + fileName);
            return manager.loadOntologyFromOntologyDocument(
                new IRIDocumentSource(df.getIRI(url).toString()),
                new OntologyConfigurator().setReportStackTraces(true));
        } catch (OWLOntologyCreationException e) {
            fail(e.getMessage());
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(String input, IRI i, OWLDocumentFormat f) {
        StringDocumentSource documentSource =
            new StringDocumentSource(input, i.toString(), f, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(String input, OWLDocumentFormat f) {
        StringDocumentSource documentSource =
            new StringDocumentSource(input, "uri:owlapi:ontology", f, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyFromString(StringDocumentSource input)
        throws OWLOntologyCreationException {
        return setupManager().loadOntologyFromOntologyDocument(input);
    }

    protected OWLOntology loadOntologyFromString(StringDocumentTarget input, OWLDocumentFormat f) {
        try {
            return setupManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input.toString(), "string:ontology", f, null));
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected OWLOntology loadOntologyStrict(StringDocumentTarget o, OWLDocumentFormat f)
        throws OWLOntologyCreationException {
        return loadOntologyWithConfig(o, f, new OntologyConfigurator().setStrict(true));
    }

    protected OWLOntology loadOntologyWithConfig(StringDocumentTarget o, OWLDocumentFormat f,
        OntologyConfigurator c) throws OWLOntologyCreationException {
        return loadOntologyWithConfig(new StringDocumentSource(o, f), c);
    }

    protected OWLOntology loadOntologyWithConfig(StringDocumentSource o, OntologyConfigurator c)
        throws OWLOntologyCreationException {
        return setupManager().loadOntologyFromOntologyDocument(o, c);
    }

    protected StringDocumentTarget saveOntology(OWLOntology o) throws OWLOntologyStorageException {
        return saveOntology(o, o.getNonnullFormat());
    }

    protected StringDocumentTarget saveOntology(OWLOntology o, OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        StringDocumentTarget t = new StringDocumentTarget();
        o.saveOntology(format, t);
        return t;
    }

    protected OWLOntology roundTrip(OWLOntology o) throws OWLOntologyStorageException {
        return loadOntologyFromString(saveOntology(o, o.getNonnullFormat()), o.getNonnullFormat());
    }

    protected OWLOntology roundTrip(OWLOntology o, OWLDocumentFormat format, OntologyConfigurator c)
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        return loadOntologyWithConfig(saveOntology(o, format), format, c);
    }

    protected OWLOntology roundTrip(OWLOntology o, OWLDocumentFormat f)
        throws OWLOntologyStorageException {
        StringDocumentTarget saveOntology = saveOntology(o, f);
        // System.out.println("TestBase.roundTrip() " + saveOntology);
        return loadOntologyFromString(saveOntology, f);
    }
}
