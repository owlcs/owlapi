package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.api.test.TestFiles;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@RunWith(Parameterized.class)
public class OutputSyntaxSortTestCase extends TestBase {

    private final OWLDocumentFormat format;

    public OutputSyntaxSortTestCase(OWLDocumentFormat format) {
        this.format = format;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> getData() {
        return Arrays.<Object[]> asList(new Object[] { new ManchesterSyntaxDocumentFormat() },
            new Object[] { new FunctionalSyntaxDocumentFormat() }, new Object[] { new TurtleDocumentFormat() },
            new Object[] { new RDFXMLDocumentFormat() }, new Object[] { new OWLXMLDocumentFormat() });
    }

    @Test
    public void shouldOutputAllInSameOrder() throws OWLOntologyStorageException, OWLOntologyCreationException {
        masterConfigurator.withRemapAllAnonymousIndividualsIds(false);
        try {
            List<OWLOntology> ontologies = new ArrayList<>();
            List<String> set = new ArrayList<>();
            for (String s : TestFiles.inputSorting) {
                OWLOntology o = loadOntologyFromString(
                    new StringDocumentSource(s, "uri:owlapi:ontology", new FunctionalSyntaxDocumentFormat(), null));
                set.add(saveOntology(o, format).toString());
                ontologies.add(o);
            }
            for (int i = 0; i < ontologies.size() - 1; i++) {
                equal(ontologies.get(i), ontologies.get(i + 1));
            }
            for (int i = 0; i < set.size() - 1; i++) {
                assertEquals(format.getKey() + " " + new ComparisonFailure("", set.get(i), set.get(i + 1)).getMessage(),
                    set.get(i), set.get(i + 1));
            }
        } finally {
            masterConfigurator.withRemapAllAnonymousIndividualsIds(true);
        }
    }
}
