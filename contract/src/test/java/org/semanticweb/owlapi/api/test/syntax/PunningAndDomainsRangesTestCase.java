package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class PunningAndDomainsRangesTestCase extends TestBase {

    private static final String URN_TEST = "urn:test#";

    @Test
    void shouldKeepDomainsInFSS() throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(iri(URN_TEST, "ontology"));
        OWLAnnotationProperty p1 = df.getOWLAnnotationProperty(iri("urn:property#", "p"));
        OWLDataProperty p2 = df.getOWLDataProperty(iri("urn:property#", "p"));
        o.addAxiom(df.getOWLAnnotationPropertyRangeAxiom(p1, OWL2Datatype.RDFS_LITERAL.getIRI()));
        o.addAxiom(df.getOWLDataPropertyRangeAxiom(p2, OWL2Datatype.RDFS_LITERAL.getDatatype(df)));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
    }

    @Test
    void shouldSupportPunningClassesAndPropertiesInManchesterSyntax()
        throws OWLOntologyCreationException {
        OWLClass b = df.getOWLClass(iri(URN_TEST, "B"));
        OWLClass a = df.getOWLClass(iri(URN_TEST, "A"));
        OWLOntology o = m.createOntology();
        o.addAxiom(df.getOWLDeclarationAxiom(df.getOWLObjectProperty(iri(URN_TEST, "B"))));
        o.addAxiom(df.getOWLDeclarationAxiom(b));
        o.addAxiom(df.getOWLDeclarationAxiom(a));
        ManchesterOWLSyntaxParserImpl parser =
            (ManchesterOWLSyntaxParserImpl) OWLManager.createManchesterParser();
        parser.getPrefixManager().setDefaultPrefix(URN_TEST);
        parser.setDefaultOntology(o);
        parser.setStringToParse(":A or :B");
        OWLClassExpression parseClassExpression = parser.parseClassExpression();
        assertEquals(parseClassExpression, df.getOWLObjectUnionOf(a, b));
    }
}
