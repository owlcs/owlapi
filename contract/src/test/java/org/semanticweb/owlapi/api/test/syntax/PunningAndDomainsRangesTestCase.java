package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxParserImpl;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class PunningAndDomainsRangesTestCase extends TestBase {

    @Test
    void shouldKeepDomainsInFSS() {
        OWLOntology o = create("urn:testontology");
        OWLAnnotationProperty ann = AnnotationProperty(DP.getIRI());
        o.addAxiom(AnnotationPropertyRange(ann, OWL2Datatype.RDFS_LITERAL.getIRI()));
        o.addAxiom(DataPropertyRange(DP, Datatype(OWL2Datatype.RDFS_LITERAL.getIRI())));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
    }

    @Test
    void shouldSupportPunningClassesAndPropertiesInManchesterSyntax() {
        OWLOntology o = createAnon();
        o.addAxiom(Declaration(ObjectProperty(B.getIRI())));
        o.addAxiom(Declaration(B));
        o.addAxiom(Declaration(A));
        ManchesterOWLSyntaxParserImpl parser =
            (ManchesterOWLSyntaxParserImpl) OWLManager.createManchesterParser();
        parser.getPrefixManager().setDefaultPrefix(A.getIRI().getNamespace());
        parser.setDefaultOntology(o);
        parser.setStringToParse(":A or :B");
        OWLClassExpression parseClassExpression = parser.parseClassExpression();
        assertEquals(parseClassExpression, ObjectUnionOf(A, B));
    }
}
