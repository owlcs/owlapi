package org.semanticweb.owlapitools.builders.test;

import java.util.Arrays;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIArgument;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import com.google.common.collect.Sets;

class BuildersTestUtil {

    @Nonnull
    static OWLDataFactory df = new OWLDataFactoryImpl();
    @Nonnull
    static OWLAnnotationProperty ap = df
            .getOWLAnnotationProperty("urn:test#ann");
    @Nonnull
    static OWLObjectProperty op = df.getOWLObjectProperty("urn:test#op");
    @Nonnull
    static OWLDataProperty dp = df.getOWLDataProperty("urn:test#dp");
    @Nonnull
    static OWLLiteral lit = df.getOWLLiteral(false);
    @Nonnull
    static IRI iri = IRI.create("urn:test#iri");
    @Nonnull
    static Set<OWLAnnotation> annotations = Sets.newHashSet(df
            .getOWLAnnotation(ap, df.getOWLLiteral("test")));
    @Nonnull
    static OWLClass ce = df.getOWLClass("urn:test#c");
    @Nonnull
    static OWLNamedIndividual i = df.getOWLNamedIndividual("urn:test#i");
    @Nonnull
    static OWLDatatype d = df.getBooleanOWLDatatype();
    @Nonnull
    static Set<OWLDataProperty> dps = Sets.newHashSet(
            df.getOWLDataProperty(iri), dp);
    @Nonnull
    static Set<OWLObjectProperty> ops = Sets.newHashSet(
            df.getOWLObjectProperty(iri), op);
    @Nonnull
    static Set<OWLClass> classes = Sets.newHashSet(df.getOWLClass(iri), ce);
    @Nonnull
    static Set<OWLNamedIndividual> inds = Sets.newHashSet(i,
            df.getOWLNamedIndividual(iri));
    @Nonnull
    static SWRLDArgument var1 = df.getSWRLVariable("var1");
    @Nonnull
    static SWRLIArgument var2 = df.getSWRLVariable("var2");
    @Nonnull
    static SWRLAtom v1 = df.getSWRLBuiltInAtom(
            IRI.create("v1"),
            Arrays.asList((SWRLDArgument) df.getSWRLVariable("var3"),
                    df.getSWRLVariable("var4")));
    @Nonnull
    static SWRLAtom v2 = df.getSWRLBuiltInAtom(
            IRI.create("v2"),
            Arrays.asList((SWRLDArgument) df.getSWRLVariable("var5"),
                    df.getSWRLVariable("var6")));
    @Nonnull
    static Set<SWRLAtom> body = Sets.newHashSet(v1);
    @Nonnull
    static Set<SWRLAtom> head = Sets.newHashSet(v2);
}
