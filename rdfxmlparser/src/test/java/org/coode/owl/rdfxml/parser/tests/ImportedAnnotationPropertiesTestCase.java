package org.coode.owl.rdfxml.parser.tests;

import junit.framework.TestCase;
import org.coode.owl.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owl.io.OWLParserFactoryRegistry;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.util.SimpleURIMapper;
import uk.ac.manchester.cs.owl.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.ParsableOWLOntologyFactory;

import java.net.URI;
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
 * Date: 31-Jul-2007<br><br>
 */
public class ImportedAnnotationPropertiesTestCase extends TestCase {

    private OWLOntologyManager man;


    protected void setUp() throws Exception {
        super.setUp();
        // Use the reference implementation
        man = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        OWLParserFactoryRegistry.getInstance().registerParserFactory(new RDFXMLParserFactory());
        ParsableOWLOntologyFactory factory = new ParsableOWLOntologyFactory();
        man.addOntologyFactory(factory);

    }

    public void testImportedAnnotationProperties() throws Exception {
        URI ontURI = URI.create("http://www.semanticweb.org/ontologies/importAnnotationProperties.owl");
        man.addURIMapper(new SimpleURIMapper(URI.create("http://www.semanticweb.org/ontologies/annotationProperties.owl"),
                getClass().getResource("/owlapi/AnnotationURIsTest.owl").toURI()));
        man.addURIMapper(new SimpleURIMapper(ontURI,
                getClass().getResource("/owlapi/ImportAnnotationPropertiesTest.owl").toURI()));

        OWLOntology ontA = man.loadOntology(ontURI);
        OWLIndividual ind = man.getOWLDataFactory().getIndividual(URI.create("http://www.semanticweb.org/ontologies/importAnnotationProperties.owl#A"));
        assertTrue(ontA.getReferencedIndividuals().contains(ind));
        assertTrue(ontA.getAnnotationURIs().contains(URI.create("http://www.semanticweb.org/owl/annotationprops#myProp")));
    }
}
