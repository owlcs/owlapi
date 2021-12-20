package org.semanticweb.owlapi6.profilestest;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;

class ProfileELDLOnlyTestCase extends ProfileBase {

    static Stream<String> data() {
        return Stream.of(TestFiles.profileELDLTestCases);
    }

    @ParameterizedTest
    @MethodSource("data")
    void testELDLONLY(String premise) {
        test(premise.startsWith("<rdf:RDF") ? new RDFXMLDocumentFormat()
            : new FunctionalSyntaxDocumentFormat(), premise, true, false, false, true);
    }
}
