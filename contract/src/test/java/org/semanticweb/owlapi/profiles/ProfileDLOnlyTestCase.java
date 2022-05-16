package org.semanticweb.owlapi.profiles;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.api.test.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;

@RunWith(Parameterized.class)
public class ProfileDLOnlyTestCase extends ProfileBase {

    private final String premise;

    public ProfileDLOnlyTestCase(String premise) {
        this.premise = premise;
    }

    @Parameters
    public static List<String> getData() {
        return Arrays.asList(TestFiles.profileDLTestCases);
    }

    @Test
    public void testDLOnly() {
        test(premise.startsWith("<") ? new RDFXMLDocumentFormat() : new FunctionalSyntaxDocumentFormat(), premise,
            false, false, false, true);
    }
}
