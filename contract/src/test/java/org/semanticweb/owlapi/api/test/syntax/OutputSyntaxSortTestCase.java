package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

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
                OWLOntology o = loadFrom(new StringDocumentSource(input, IRI.generateDocumentIRI(),
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
