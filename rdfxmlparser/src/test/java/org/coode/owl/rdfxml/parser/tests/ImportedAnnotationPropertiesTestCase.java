package org.coode.owl.rdfxml.parser.tests;

import junit.framework.TestCase;
import org.coode.owl.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;

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
        man = new OWLOntologyManagerImpl();
        OWLParserFactoryRegistry.getInstance().registerParserFactory(new RDFXMLParserFactory());
        ParsableOWLOntologyFactory factory = new ParsableOWLOntologyFactory();
        man.addOntologyFactory(factory);

    }

    public void testImportedAnnotationProperties() throws Exception {
        IRI ontURI = IRI.create("http://www.semanticweb.org/ontologies/importAnnotationProperties.owlapi");
        man.addIRIMapper(new SimpleIRIMapper(URI.create("http://www.semanticweb.org/ontologies/annotationProperties.owlapi"),
                getClass().getResource("/owlapi/AnnotationURIsTest.owlapi").toURI()));
        man.addIRIMapper(new SimpleIRIMapper(ontURI,
                getClass().getResource("/owlapi/ImportAnnotationPropertiesTest.owlapi").toURI()));

        OWLOntology ontA = man.loadOntology(ontURI);
        OWLIndividual ind = man.getOWLDataFactory().getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/ontologies/importAnnotationProperties.owlapi#A"));
        assertTrue(ontA.getReferencedIndividuals().contains(ind));
        assertTrue(ontA.getAnnotationURIs().contains(URI.create("http://www.semanticweb.org/owlapi/annotationprops#myProp")));
    }
}
