package org.semanticweb.owlapi6.api.test.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi6.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
public class PunningAndDomainsRangesTestCase extends TestBase {

    @Test
    public void shouldKeepDomainsInFSS()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(df.getIRI("urn:test#", "ontology"));
        OWLAnnotationProperty p1 = df.getOWLAnnotationProperty(df.getIRI("urn:property#", "p"));
        OWLDataProperty p2 = df.getOWLDataProperty(df.getIRI("urn:property#", "p"));
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
        OWLClass b = df.getOWLClass(df.getIRI("urn:test#", "B"));
        OWLClass a = df.getOWLClass(df.getIRI("urn:test#", "A"));
        OWLOntology o = m.createOntology();
        m.addAxiom(o,
            df.getOWLDeclarationAxiom(df.getOWLObjectProperty(df.getIRI("urn:test#", "B"))));
        m.addAxiom(o, df.getOWLDeclarationAxiom(b));
        m.addAxiom(o, df.getOWLDeclarationAxiom(a));
        ManchesterOWLSyntaxParserImpl parser =
            (ManchesterOWLSyntaxParserImpl) OWLManager.createManchesterParser();
        parser.getPrefixManager().withDefaultPrefix("urn:test#");
        parser.setDefaultOntology(o);
        parser.setStringToParse(":A or :B");
        OWLClassExpression parseClassExpression = parser.parseClassExpression();
        assertEquals(parseClassExpression, df.getOWLObjectUnionOf(a, b));
    }
}
