package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings("javadoc")
public class ManchesterOWLSyntaxParserTestCase {
    public static final String NS = "http://protege.org/ontologies/Test.owl";
    OWLClass a;
    OWLDataProperty p;
    OWLDatatype date_time;
    OWLDataFactory factory;

    @Before
    public void setUp() {
        factory = OWLManager.getOWLDataFactory();
        a = factory.getOWLClass(IRI.create(NS + "#A"));
        p = factory.getOWLDataProperty(IRI.create(NS + "#p"));
        date_time = factory.getOWLDatatype(XSDVocabulary.DATE_TIME.getIRI());
    }

    @Test
    public void shouldParseCorrectly() throws ParserException,
    OWLOntologyCreationException {
        // given
        String text1 = "'GWAS study' and  has_publication_date some dateTime[< \"2009-01-01T00:00:00+00:00\"^^dateTime]";
        OWLClassExpression expected = factory.getOWLObjectIntersectionOf(a, factory
                .getOWLDataSomeValuesFrom(p, factory.getOWLDatatypeRestriction(date_time,
                        OWLFacet.MAX_EXCLUSIVE,
                        factory.getOWLLiteral("2009-01-01T00:00:00+00:00", date_time))));
        // ontology creation including labels - this is the input ontology
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology o = manager.createOntology();
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(a));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(p));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(date_time));
        manager.addAxiom(o, annotation(a, "'GWAS study'"));
        manager.addAxiom(o, annotation(p, "has_publication_date"));
        manager.addAxiom(o, annotation(date_time, "dateTime"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(factory.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(), manager);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                manager.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                factory, text1);
        final ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(
                shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals(expected, dsvf);
    }

    public OWLAxiom annotation(OWLEntity e, String s) {
        return factory.getOWLAnnotationAssertionAxiom(e.getIRI(), factory
                .getOWLAnnotation(factory.getRDFSLabel(), factory.getOWLLiteral(s)));
    }
}
