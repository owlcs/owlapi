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

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;
import org.semanticweb.owlapi.api.test.anonymous.AnonymousIndividualsNormaliser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.formats.RDFDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.concurrent.NoOpReadWriteLock;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
@SuppressWarnings({ "javadoc", "null" })
public abstract class TestBase {

    @Nonnull protected static final File RESOURCES = resources();

    private static final File resources() {
        try {
            return new File(TestBase.class.getResource("/owlapi.properties").toURI()).getParentFile();
        } catch (URISyntaxException e) {
            throw new OWLRuntimeException("NO RESOURCE FOLDER ACCESSIBLE", e);
        }
    }

    protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);
    @Nonnull @Rule public TemporaryFolder folder = new TemporaryFolder();
    @Rule public ExpectedException expectedException = ExpectedException.none();
    @Nonnull @Rule public Timeout timeout = new Timeout(1000000);
    @Nonnull protected OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
    protected static @Nonnull OWLDataFactory df;
    protected static @Nonnull OWLOntologyManager masterManager;
    @Nonnull protected OWLOntologyManager m;
    @Nonnull protected OWLOntologyManager m1;

    @BeforeClass
    public static void setupManagers() {
        masterManager = OWLManager.createOWLOntologyManager();
        df = masterManager.getOWLDataFactory();
    }

    @Before
    public void setupManagersClean() {
        m = setupManager();
        m1 = setupManager();
    }

    protected static OWLOntologyManager setupManager() {
        OWLOntologyManager manager = new OWLOntologyManagerImpl(df, new NoOpReadWriteLock());
        manager.getOntologyFactories().set(new OWLOntologyFactoryImpl(new OWLOntologyBuilder() {

            @Override
            public OWLOntology createOWLOntology(OWLOntologyManager om, OWLOntologyID id) {
                return new OWLOntologyImpl(om, id);
            }
        }));
        manager.getOntologyParsers().set(masterManager.getOntologyParsers());
        manager.getOntologyStorers().set(masterManager.getOntologyStorers());
        manager.getIRIMappers().set(masterManager.getIRIMappers());
        return manager;
    }

    @Nonnull
    protected <T> Optional<T> of(T t) {
        return Optional.fromNullable(t);
    }

    @Nonnull
    protected Optional<IRI> absent() {
        return Optional.absent();
    }

    @Nonnull
    protected <S> Set<S> singleton(S s) {
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
        return ax.isOfType(AxiomType.DECLARATION) && ax.getAnnotations().isEmpty();
    }

    public boolean equal(@Nonnull OWLOntology ont1, @Nonnull OWLOntology ont2) {
        if (!ont1.isAnonymous() && !ont2.isAnonymous()) {
            assertEquals("Ontologies supposed to be the same", ont1.getOntologyID(), ont2.getOntologyID());
        }
        assertEquals("Annotations supposed to be the same", ont1.getAnnotations(), ont2.getAnnotations());
        Set<OWLAxiom> axioms1;
        Set<OWLAxiom> axioms2;
        // This isn't great - we normalise axioms by changing the ids of
        // individuals. This relies on the fact that
        // we iterate over objects in the same order for the same set of axioms!
        axioms1 = new AnonymousIndividualsNormaliser(df).getNormalisedAxioms(ont1.getAxioms());
        axioms2 = new AnonymousIndividualsNormaliser(df).getNormalisedAxioms(ont2.getAxioms());
        OWLDocumentFormat ontologyFormat = ont2.getOWLOntologyManager().getOntologyFormat(ont2);
        applyEquivalentsRoundtrip(axioms1, axioms2, ontologyFormat);
        if (ontologyFormat instanceof ManchesterSyntaxDocumentFormat) {
            // drop GCIs from the expected axioms, they won't be there
            Iterator<OWLAxiom> it = axioms1.iterator();
            while (it.hasNext()) {
                OWLAxiom next = it.next();
                if (next instanceof OWLSubClassOfAxiom) {
                    if (((OWLSubClassOfAxiom) next).getSubClass().isAnonymous() && ((OWLSubClassOfAxiom) next)
                        .getSuperClass().isAnonymous()) {
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
                        sb.append("Rem axiom: ");
                        sb.append(ax);
                        sb.append('\n');
                        counter++;
                    }
                }
            }
            for (OWLAxiom ax : b) {
                if (!a.contains(ax)) {
                    if (!isIgnorableAxiom(ax, true)) {
                        rightOnly.add(ax);
                        sb.append("Add axiom: ");
                        sb.append(ax);
                        sb.append('\n');
                        counter++;
                    }
                }
            }
            if (counter > 0) {
                // a test fails on OpenJDK implementations because of ordering
                // testing here if blank node ids are the only difference
                boolean fixed = !verifyErrorIsDueToBlankNodesId(leftOnly, rightOnly);
                if (fixed) {
                    String x = getClass().getSimpleName() + " roundTripOntology() Failing to match axioms: \n" + sb;
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
     * equivalent entity axioms with more than two entities are broken up by RDF
     * syntaxes. Ensure they are still recognized as correct roundtripping
     */
    public void applyEquivalentsRoundtrip(Set<OWLAxiom> axioms1, Set<OWLAxiom> axioms2, OWLDocumentFormat destination) {
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
                    OWLEquivalentObjectPropertiesAxiom ax2 = (OWLEquivalentObjectPropertiesAxiom) ax;
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

    private boolean removeIfContainsAll(Collection<OWLAxiom> axioms, Collection<? extends OWLAxiom> others,
        OWLDocumentFormat destination) {
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
            OWLAxiom reannotated = ax.getAxiomWithoutAnnotations().getAnnotatedAxiom(reannotate(ax.getAnnotations()));
            toRemove.add(reannotated);
        }
        axioms.removeAll(toRemove);
        return true;
    }

    private Set<OWLAnnotation> reannotate(Set<OWLAnnotation> anns) {
        OWLDatatype stringType = df.getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI());
        Set<OWLAnnotation> toReturn = new HashSet<>();
        for (OWLAnnotation a : anns) {
            Optional<OWLLiteral> asLiteral = a.getValue().asLiteral();
            if (asLiteral.isPresent() && asLiteral.get().isRDFPlainLiteral()) {
                OWLAnnotation replacement = df.getOWLAnnotation(a.getProperty(), df.getOWLLiteral(asLiteral.get()
                    .getLiteral(), stringType));
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
    public static boolean verifyErrorIsDueToBlankNodesId(@Nonnull Set<OWLAxiom> leftOnly,
        @Nonnull Set<OWLAxiom> rightOnly) {
        Set<String> leftOnlyStrings = new HashSet<>();
        Set<String> rightOnlyStrings = new HashSet<>();
        for (OWLAxiom ax : leftOnly) {
            leftOnlyStrings.add(ax.toString().replaceAll("_:anon-ind-[0-9]+", "blank").replaceAll("_:genid[0-9]+",
                "blank"));
        }
        for (OWLAxiom ax : rightOnly) {
            rightOnlyStrings.add(ax.toString().replaceAll("_:anon-ind-[0-9]+", "blank").replaceAll("_:genid[0-9]+",
                "blank"));
        }
        return rightOnlyStrings.equals(leftOnlyStrings);
    }

    /**
     * ignore declarations of builtins and of named individuals - named
     * individuals do not /need/ a declaration, but addiong one is not an error.
     * 
     * @param parse
     *        true if the axiom belongs to the parsed ones, false for the input
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

    @Nonnull private final String uriBase = "http://www.semanticweb.org/owlapi/test";

    @Nonnull
    public OWLOntology getOWLOntology(String name) {
        try {
            IRI iri = IRI(uriBase + '/' + name);
            if (m.contains(iri)) {
                return m.getOntology(iri);
            } else {
                return m.createOntology(iri);
            }
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public OWLOntology loadOntology(String fileName) {
        try {
            URL url = getClass().getResource('/' + fileName);
            return m.loadOntologyFromOntologyDocument(new IRIDocumentSource(IRI.create(url), null, null),
                new OWLOntologyLoaderConfiguration().setReportStackTraces(true));
        } catch (OWLOntologyCreationException e) {
            fail(e.getMessage());
            throw new OWLRuntimeException(e);
        }
    }

    @Nonnull
    public IRI iri(String name) {
        return IRI(uriBase + '#' + name);
    }

    public void addAxiom(@Nonnull OWLOntology ont, @Nonnull OWLAxiom ax) {
        m.addAxiom(ont, ax);
    }

    public void roundTripOntology(@Nonnull OWLOntology ont) throws OWLOntologyStorageException,
        OWLOntologyCreationException {
        roundTripOntology(ont, new RDFXMLDocumentFormat());
    }

    /**
     * Saves the specified ontology in the specified format and reloads it.
     * Calling this method from a test will cause the test to fail if the
     * ontology could not be stored, could not be reloaded, or was reloaded and
     * the reloaded version is not equal (in terms of ontology URI and axioms)
     * with the original.
     * 
     * @param ont
     *        The ontology to be round tripped.
     * @param format
     *        The format to use when doing the round trip.
     */
    public OWLOntology roundTripOntology(@Nonnull OWLOntology ont, @Nonnull OWLDocumentFormat format)
        throws OWLOntologyStorageException, OWLOntologyCreationException {
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
        OWLOntology ont2 = setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(target.toString(),
            OWLOntologyDocumentSourceBase.getNextDocumentIRI("string:ontology"), format, null),
            new OWLOntologyLoaderConfiguration().setReportStackTraces(true));
        if (logger.isTraceEnabled()) {
            logger.trace("TestBase.roundTripOntology() ontology parsed");
            Set<OWLAxiom> axioms = ont2.getAxioms();
            for (OWLAxiom ax : axioms) {
                logger.trace(ax.toString());
            }
        }
        equal(ont, ont2);
        return ont2;
    }

    // @Test
    public void checkVerify() {
        OWLDataProperty t = df.getOWLDataProperty(IRI.create("urn:test#t"));
        Set<OWLAxiom> ax1 = new HashSet<>();
        ax1.add(df.getOWLDataPropertyAssertionAxiom(t, df.getOWLAnonymousIndividual(), df.getOWLLiteral("test1")));
        ax1.add(df.getOWLDataPropertyAssertionAxiom(t, df.getOWLAnonymousIndividual(), df.getOWLLiteral("test2")));
        Set<OWLAxiom> ax2 = new HashSet<>();
        ax2.add(df.getOWLDataPropertyAssertionAxiom(t, df.getOWLAnonymousIndividual(), df.getOWLLiteral("test1")));
        ax2.add(df.getOWLDataPropertyAssertionAxiom(t, df.getOWLAnonymousIndividual(), df.getOWLLiteral("test2")));
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

    @Nonnull
    protected OWLOntology loadOntologyFromString(@Nonnull String input) throws OWLOntologyCreationException {
        return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input));
    }

    @Nonnull
    protected OWLOntology loadOntologyFromString(@Nonnull String input, @Nonnull IRI i, @Nonnull OWLDocumentFormat f) {
        StringDocumentSource documentSource = new StringDocumentSource(input, i, f, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }
    @Nonnull
    protected OWLOntology loadOntologyFromString(@Nonnull String input, @Nonnull OWLDocumentFormat f) {
        StringDocumentSource documentSource = new StringDocumentSource(input, IRI.generateDocumentIRI(), f, null);
        try {
            return setupManager().loadOntologyFromOntologyDocument(documentSource);
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Nonnull
    protected OWLOntology loadOntologyFromString(@Nonnull StringDocumentSource input)
        throws OWLOntologyCreationException {
        return setupManager().loadOntologyFromOntologyDocument(input);
    }

    @Nonnull
    protected OWLOntology loadOntologyFromString(@Nonnull StringDocumentTarget input)
        throws OWLOntologyCreationException {
        return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input));
    }

    @Nonnull
    protected OWLOntology loadOntologyFromString(@Nonnull StringDocumentTarget input, OWLDocumentFormat f)
        throws OWLOntologyCreationException {
        return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(input.toString(),
            OWLOntologyDocumentSourceBase.getNextDocumentIRI("string:ontology"), f, null));
    }

    @Nonnull
    protected OWLOntology loadOntologyStrict(@Nonnull StringDocumentTarget o) throws OWLOntologyCreationException {
        return loadOntologyWithConfig(o, new OWLOntologyLoaderConfiguration().setStrict(true));
    }

    @Nonnull
    protected OWLOntology loadOntologyWithConfig(@Nonnull StringDocumentTarget o,
        @Nonnull OWLOntologyLoaderConfiguration c) throws OWLOntologyCreationException {
        return setupManager().loadOntologyFromOntologyDocument(new StringDocumentSource(o), c);
    }

    @Nonnull
    protected StringDocumentTarget saveOntology(@Nonnull OWLOntology o) throws OWLOntologyStorageException {
        return saveOntology(o, o.getOWLOntologyManager().getOntologyFormat(o));
    }

    @Nonnull
    protected StringDocumentTarget saveOntology(@Nonnull OWLOntology o, @Nonnull OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        StringDocumentTarget t = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o, format, t);
        return t;
    }

    @Nonnull
    protected OWLOntology roundTrip(@Nonnull OWLOntology o, @Nonnull OWLDocumentFormat format)
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        return loadOntologyFromString(saveOntology(o, format), format);
    }

    @Nonnull
    protected OWLOntology roundTrip(@Nonnull OWLOntology o, @Nonnull OWLDocumentFormat format,
        @Nonnull OWLOntologyLoaderConfiguration c) throws OWLOntologyCreationException, OWLOntologyStorageException {
        return loadOntologyWithConfig(saveOntology(o, format), c);
    }

    @Nonnull
    protected OWLOntology roundTrip(@Nonnull OWLOntology o) throws OWLOntologyCreationException,
        OWLOntologyStorageException {
        return loadOntologyFromString(saveOntology(o));
    }
}
