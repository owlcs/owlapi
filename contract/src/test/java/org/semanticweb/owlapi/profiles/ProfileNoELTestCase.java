package org.semanticweb.owlapi.profiles;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;

class ProfileNoELTestCase extends ProfileBase {

    static Stream<String> data() {
        return Stream.of(TestFiles.profileNOELTestCases);
    }

    @ParameterizedTest
    @MethodSource("data")
    void testNoEL(String premise) {
        test(premise.startsWith("<rdf:RDF") ? new RDFXMLDocumentFormat()
            : new FunctionalSyntaxDocumentFormat(), premise, false, true, true, true);
    }
}
