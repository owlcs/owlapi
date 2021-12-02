package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class PunningAndDomainsRangesTestCase extends TestBase {

    @Test
    void shouldKeepDomainsInFSS() {
        OWLOntology o = getOWLOntology(IRI.create("urn:testontology"));
        OWLAnnotationProperty ann = df.getOWLAnnotationProperty(DP.getIRI());
        m.addAxiom(o,
            df.getOWLAnnotationPropertyRangeAxiom(ann, OWL2Datatype.RDFS_LITERAL.getIRI()));
        m.addAxiom(o,
            df.getOWLDataPropertyRangeAxiom(DP, OWL2Datatype.RDFS_LITERAL.getDatatype(df)));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
    }

    @Test
    void shouldSupportPunningClassesAndPropertiesInManchesterSyntax() {
        OWLOntology o = o(df.getOWLDeclarationAxiom(df.getOWLObjectProperty(B.getIRI())),
            df.getOWLDeclarationAxiom(B), df.getOWLDeclarationAxiom(A));
        ManchesterOWLSyntaxParserImpl parser =
            (ManchesterOWLSyntaxParserImpl) OWLManager.createManchesterParser();
        parser.getPrefixManager().setDefaultPrefix(A.getIRI().getNamespace());
        parser.setDefaultOntology(o);
        parser.setStringToParse(":A or :B");
        OWLClassExpression parseClassExpression = parser.parseClassExpression();
        assertEquals(parseClassExpression, df.getOWLObjectUnionOf(A, B));
    }
}
