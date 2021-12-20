package org.semanticweb.owlapi6.profilestest;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;

class ProfileDLOnlyTestCase extends ProfileBase {

    static Stream<String> data() {
        return Stream.of(TestFiles.profileDLTestCases);
    }

    @ParameterizedTest
    @MethodSource("data")
    void testDLOnly(String premise) {
        test(premise.startsWith("<") ? new RDFXMLDocumentFormat()
            : new FunctionalSyntaxDocumentFormat(), premise, false, false, false, true);
    }
}
