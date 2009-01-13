package org.semanticweb.owl.model;

import uk.ac.manchester.cs.owl.OWLDataFactoryImpl;
import junit.framework.TestCase;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Apr-2007<br><br>
 */
public class StandardAnnotationsTestCase extends TestCase {

    private OWLDataFactory dataFactory;

    protected void setUp() throws Exception {
        dataFactory = new OWLDataFactoryImpl();
    }


    public void testLabelAnnotations() {
        OWLConstantAnnotation anno1 = dataFactory.getOWLLabelAnnotation("Test");
        assertTrue(anno1.isLabel());
        OWLConstantAnnotation anno2 = dataFactory.getOWLConstantAnnotation(OWLRDFVocabulary.RDFS_LABEL.getURI(), dataFactory.getOWLTypedLiteral("Test"));
        assertTrue(anno2.isLabel());
    }

    public void testCommentAnnotations() {
        OWLConstantAnnotation anno2 = dataFactory.getOWLConstantAnnotation(OWLRDFVocabulary.RDFS_COMMENT.getURI(), dataFactory.getOWLTypedLiteral("Test"));
        assertTrue(anno2.isComment());
    }


}

