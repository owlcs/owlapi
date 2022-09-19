package org.semanticweb.owlapi.profilestest;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;

class ProfileAllProfilesTestCase extends ProfileBase {

    static List<String> getData() {
        return Arrays.asList(TestFiles.profileAllTestCases);
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testAllProfiles(String premise) {
        test(premise.startsWith("<rdf:RDF") ? new RDFXMLDocumentFormat()
            : new FunctionalSyntaxDocumentFormat(), premise, true, true, true, true);
    }
}
