package org.semanticweb.owlapi.oboformat.parser;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;

public class OBOFormatParserTest {

    private static final String FILE_TEST_NAME1 = "example1.obo";

    @Test
    public void parseTestFile1() throws IOException {
        File oboFile =
            new File(this.getClass().getClassLoader().getResource(FILE_TEST_NAME1).getFile());
        assertNotNull(oboFile);
        OBOFormatParser parser = new OBOFormatParser();
        OBODoc oboDoc = parser.parse(oboFile);
        assertNotNull(oboDoc);
        oboDoc.check();
    }

    @Test
    public void should() throws IOException {
        String in = "format-version: 1.2\n" + "data-version: releases/2018-09-25\n"
            + "saved-by: cooperl\n"
            + "import: http://purl.obolibrary.org/obo/po/imports/ncbitaxon_import.owl\n"
            + "ontology: po\n" + "\n" + "[Term]\n" + "id: PO:0000001\n"
            + "name: plant embryo proper\n" + "namespace: plant_anatomy\n"
            + "synonym: \"embri&#243foro (Spanish, exact)\" EXACT Spanish [POC:Maria_Alejandra_Gandolfo {date=2011-09-14, lang=eng}]";
        new OBOFormatParser().parse(new StringReader(in));
    }

}
