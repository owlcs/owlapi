package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;

class OutputSyntaxSortTestCase extends TestBase {

    static Stream<OWLDocumentFormat> getData() {
        return Stream.of(new ManchesterSyntaxDocumentFormat(), new FunctionalSyntaxDocumentFormat(),
            new TurtleDocumentFormat(), new RDFXMLDocumentFormat(), new OWLXMLDocumentFormat());
    }

    @ParameterizedTest
    @MethodSource("getData")
    void shouldOutputAllInSameOrder(OWLDocumentFormat format) {
        masterConfigurator.withRemapAllAnonymousIndividualsIds(false);
        try {
            List<OWLOntology> ontologies = new ArrayList<>();
            List<String> set = new ArrayList<>();
            for (String input : TestFiles.inputSorting) {
                OWLOntology o = loadFrom(new StringDocumentSource(input, "uri:owlapi:ontology",
                    new FunctionalSyntaxDocumentFormat(), null));
                set.add(saveOntology(o, format).toString());
                ontologies.add(o);
            }
            for (int index = 0; index < ontologies.size() - 1; index++) {
                equal(ontologies.get(index), ontologies.get(index + 1));
            }
            for (int index = 0; index < set.size() - 1; index++) {
                assertEquals(set.get(index), set.get(index + 1));
            }
        } finally {
            masterConfigurator.withRemapAllAnonymousIndividualsIds(true);
        }
    }
}
