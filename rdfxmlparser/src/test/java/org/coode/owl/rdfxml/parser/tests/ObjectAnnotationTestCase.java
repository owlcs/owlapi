package org.coode.owl.rdfxml.parser.tests;

import junit.framework.TestCase;
import org.coode.owl.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owl.io.OWLParserFactoryRegistry;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.XSDVocabulary;
import uk.ac.manchester.cs.owl.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.ParsableOWLOntologyFactory;

import java.io.File;
import java.net.URI;
import java.net.URL;
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
 * Date: 13-Jul-2007<br><br>
 */
public class ObjectAnnotationTestCase extends TestCase {

    private OWLOntologyManager man;


    protected void setUp() throws Exception {
        super.setUp();
        // Use the reference implementation
        man = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        OWLParserFactoryRegistry.getInstance().registerParserFactory(new RDFXMLParserFactory());
        ParsableOWLOntologyFactory factory = new ParsableOWLOntologyFactory();
        man.addOntologyFactory(factory);

    }

    public void test() throws Exception {
        URL url = getClass().getResource("/owlapi/ObjectAnnotationTest.owl");
        File file = new File(url.toURI());
        OWLOntology ont = man.loadOntologyFromPhysicalURI(file.toURI());
        System.out.println("Loaded: " + ont);
        OWLIndividual ind = man.getOWLDataFactory().getOWLIndividual(URI.create("http://www.semanticweb.org/ontologies/2007/6/ObjectAnnotationTest.owl#a"));
        OWLAnnotation anno = ind.getAnnotations(ont).iterator().next();
        assertTrue(anno instanceof OWLObjectAnnotation);
                    man.removeOntology(ont.getURI());
    }

    public void testNeg() throws Exception {
        URL url = getClass().getResource("/owlapi/ObjectAnnotationNegativeTest.owl");
        File file = new File(url.toURI());
        OWLOntology ont = man.loadOntologyFromPhysicalURI(file.toURI());
        System.out.println("Loaded: " + ont);
        OWLIndividual ind = man.getOWLDataFactory().getOWLIndividual(URI.create("http://www.semanticweb.org/ontologies/2007/6/ObjectAnnotationTest.owl#a"));
        OWLAnnotation anno = ind.getAnnotations(ont).iterator().next();
        assertFalse(anno instanceof OWLObjectAnnotation);
        assertTrue(((OWLConstant) anno.getAnnotationValue()).isTyped());
        assertTrue(((OWLConstant) anno.getAnnotationValue()).asOWLTypedConstant().getDataType().getURI().equals(
                XSDVocabulary.ANY_URI.getURI()));
                    man.removeOntology(ont.getURI());
    }
}
