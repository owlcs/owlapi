package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
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

public class PunningAndDomainsRangesTestCase extends TestBase {

    @Test
    public void shouldKeepDomainsInFSS()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(df.getIRI("urn:test#", "ontology"));
        OWLAnnotationProperty p1 = df.getOWLAnnotationProperty(df.getIRI("urn:property#", "p"));
        OWLDataProperty p2 = df.getOWLDataProperty(df.getIRI("urn:property#", "p"));
        o.addAxiom(df.getOWLAnnotationPropertyRangeAxiom(p1, OWL2Datatype.RDFS_LITERAL.getIRI()));
        o.addAxiom(df.getOWLDataPropertyRangeAxiom(p2, OWL2Datatype.RDFS_LITERAL.getDatatype(df)));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
    }

    @Test
    public void shouldSupportPunningClassesAndPropertiesInManchesterSyntax()
        throws OWLOntologyCreationException {
        OWLClass b = df.getOWLClass(df.getIRI("urn:test#", "B"));
        OWLClass a = df.getOWLClass(df.getIRI("urn:test#", "A"));
        OWLOntology o = m.createOntology();
        o.addAxiom(df.getOWLDeclarationAxiom(df.getOWLObjectProperty(df.getIRI("urn:test#", "B"))));
        o.addAxiom(df.getOWLDeclarationAxiom(b));
        o.addAxiom(df.getOWLDeclarationAxiom(a));
        ManchesterOWLSyntaxParserImpl parser =
            (ManchesterOWLSyntaxParserImpl) OWLManager.createManchesterParser();
        parser.getPrefixManager().withDefaultPrefix("urn:test#");
        parser.setDefaultOntology(o);
        parser.setStringToParse(":A or :B");
        OWLClassExpression parseClassExpression = parser.parseClassExpression();
        assertEquals(parseClassExpression, df.getOWLObjectUnionOf(a, b));
    }
}
