package org.obolibrary.oboformat;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;

/**
 * This was inspired by a cross product ontology file which has no header, but
 * an empty line as first entry. Original url:
 * http://www.geneontology.org/scratch
 * /xps/biological_process_xp_plant_anatomy.obo
 */
@SuppressWarnings("javadoc")
public class EmptyLinesTest extends OboFormatTestBasics {

    @Test
    public void testEmptyFirstLine() {
        OBODoc obodoc = parseOBOFile("empty_lines.obo");
        Collection<Frame> frames = obodoc.getTermFrames();
        assertEquals(1, frames.size());
        assertEquals("GO:0009555", frames.iterator().next().getId());
    }
}
