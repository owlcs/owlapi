package org.semanticweb.owlapitools.builders.test;

import java.util.Arrays;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

import com.google.common.collect.Sets;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

class BuildersTestUtil {

    protected static @Nonnull OWLDataFactory df = new OWLDataFactoryImpl();
    protected static @Nonnull OWLAnnotationProperty ap = df.getOWLAnnotationProperty("urn:test#ann");
    protected static @Nonnull OWLObjectProperty op = df.getOWLObjectProperty("urn:test#op");
    protected static @Nonnull OWLDataProperty dp = df.getOWLDataProperty("urn:test#dp");
    protected static @Nonnull OWLLiteral lit = df.getOWLLiteral(false);
    protected static @Nonnull IRI iri = IRI.create("urn:test#iri");
    protected static @Nonnull Set<OWLAnnotation> annotations = Sets
            .newHashSet(df.getOWLAnnotation(ap, df.getOWLLiteral("test")));
    protected static @Nonnull OWLClass ce = df.getOWLClass("urn:test#c");
    protected static @Nonnull OWLNamedIndividual i = df.getOWLNamedIndividual("urn:test#i");
    protected static @Nonnull OWLDatatype d = df.getBooleanOWLDatatype();
    protected static @Nonnull Set<OWLDataProperty> dps = Sets.newHashSet(df.getOWLDataProperty(iri), dp);
    protected static @Nonnull Set<OWLObjectProperty> ops = Sets.newHashSet(df.getOWLObjectProperty(iri), op);
    protected static @Nonnull Set<OWLClass> classes = Sets.newHashSet(df.getOWLClass(iri), ce);
    protected static @Nonnull Set<OWLNamedIndividual> inds = Sets.newHashSet(i, df.getOWLNamedIndividual(iri));
    protected static @Nonnull SWRLDArgument var1 = df.getSWRLVariable("var1");
    protected static @Nonnull SWRLIArgument var2 = df.getSWRLVariable("var2");
    protected static @Nonnull SWRLAtom v1 = df.getSWRLBuiltInAtom(IRI.create("v1"),
            Arrays.asList((SWRLDArgument) df.getSWRLVariable("var3"), df.getSWRLVariable("var4")));
    protected static @Nonnull SWRLAtom v2 = df.getSWRLBuiltInAtom(IRI.create("v2"),
            Arrays.asList((SWRLDArgument) df.getSWRLVariable("var5"), df.getSWRLVariable("var6")));
    protected static @Nonnull Set<SWRLAtom> body = Sets.newHashSet(v1);
    protected static @Nonnull Set<SWRLAtom> head = Sets.newHashSet(v2);
}
