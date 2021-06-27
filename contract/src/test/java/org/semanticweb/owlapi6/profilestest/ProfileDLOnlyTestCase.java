package org.semanticweb.owlapi6.profilestest;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;

class ProfileDLOnlyTestCase extends ProfileBase {

    static List<String> data() {
        return Arrays.asList(TestFiles.profileDLTestCases);
    }

    @ParameterizedTest
    @MethodSource("data")
    void testDLOnly(String premise) {
        test(premise.startsWith("<") ? new RDFXMLDocumentFormat()
            : new FunctionalSyntaxDocumentFormat(), premise, false, false, false, true);
    }
}
