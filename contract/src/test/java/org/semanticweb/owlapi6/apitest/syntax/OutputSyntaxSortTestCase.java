package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
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
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;

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
