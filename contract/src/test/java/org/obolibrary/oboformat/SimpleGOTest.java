package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

@SuppressWarnings("javadoc")
public class SimpleGOTest extends OboFormatTestBasics {

    @Test
    public void testParseOBOFile() throws Exception {
        OBODoc obodoc = parseOBOFile("simplego.obo");
        assertEquals(3, obodoc.getTermFrames().size());
        assertEquals(5, obodoc.getTypedefFrames().size());
        checkFrame(obodoc, "GO:0018901",
                "2,4-dichlorophenoxyacetic acid metabolic process",
                "biological_process");
        checkFrame(obodoc, "GO:0055124", "premature neural plate formation",
                "biological_process");
        checkFrame(obodoc, "GO:0055125", "Nic96 complex", "cellular_component");
        checkFrame(obodoc, "has_part", "has_part", "gene_ontology");
        checkFrame(obodoc, "negatively_regulates", "negatively_regulates",
                "gene_ontology");
        checkFrame(obodoc, "part_of", "part_of", "gene_ontology");
        checkFrame(obodoc, "positively_regulates", "positively_regulates",
                "gene_ontology");
        checkFrame(obodoc, "regulates", "regulates", "gene_ontology");
    }

    private void
            checkFrame(OBODoc doc, String id, String name, String namespace) {
        Frame frame = doc.getTermFrame(id);
        if (frame == null) {
            frame = doc.getTypedefFrame(id);
        }
        assertNotNull(frame);
        assertEquals(name, frame.getTagValue(OboFormatTag.TAG_NAME));
        assertEquals(namespace, frame.getTagValue(OboFormatTag.TAG_NAMESPACE));
    }
}
