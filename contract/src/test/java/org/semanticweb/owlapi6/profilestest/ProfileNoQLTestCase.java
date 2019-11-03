package org.semanticweb.owlapi6.profilestest;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;

@RunWith(Parameterized.class)
public class ProfileNoQLTestCase extends ProfileBase {

    private final String premise;

    public ProfileNoQLTestCase(String premise) {
        this.premise = premise;
    }

    @Parameters
    public static List<String> getData() {
        return Arrays.asList(TestFiles.profileNOQLTestCases);
    }

    @Test
    public void testNoQL() {
        test(premise.startsWith("<rdf:RDF") ? new RDFXMLDocumentFormat() : new FunctionalSyntaxDocumentFormat(),
            premise, true, false, true, true);
    }
}
