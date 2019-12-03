package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
public class PunningAndDomainsRangesTestCase extends TestBase {

    private static final String URN_TEST = "urn:test#";

    @Test
    public void shouldKeepDomainsInFSS()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(IRI.create(URN_TEST, "ontology"));
        OWLAnnotationProperty p1 = df.getOWLAnnotationProperty(IRI.create("urn:property#", "p"));
        OWLDataProperty p2 = df.getOWLDataProperty(IRI.create("urn:property#", "p"));
        m.addAxiom(o,
            df.getOWLAnnotationPropertyRangeAxiom(p1, OWL2Datatype.RDFS_LITERAL.getIRI()));
        m.addAxiom(o,
            df.getOWLDataPropertyRangeAxiom(p2, OWL2Datatype.RDFS_LITERAL.getDatatype(df)));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
    }

    @Test
    public void shouldSupportPunningClassesAndPropertiesInManchesterSyntax()
        throws OWLOntologyCreationException {
        OWLClass b = df.getOWLClass(IRI.create(URN_TEST, "B"));
        OWLClass a = df.getOWLClass(IRI.create(URN_TEST, "A"));
        OWLOntology o = m.createOntology();
        m.addAxiom(o,
            df.getOWLDeclarationAxiom(df.getOWLObjectProperty(IRI.create(URN_TEST, "B"))));
        m.addAxiom(o, df.getOWLDeclarationAxiom(b));
        m.addAxiom(o, df.getOWLDeclarationAxiom(a));
        ManchesterOWLSyntaxParserImpl parser = (ManchesterOWLSyntaxParserImpl) OWLManager
            .createManchesterParser();
        parser.getPrefixManager().setDefaultPrefix(URN_TEST);
        parser.setDefaultOntology(o);
        parser.setStringToParse(":A or :B");
        OWLClassExpression parseClassExpression = parser.parseClassExpression();
        assertEquals(parseClassExpression, df.getOWLObjectUnionOf(a, b));
    }
}
