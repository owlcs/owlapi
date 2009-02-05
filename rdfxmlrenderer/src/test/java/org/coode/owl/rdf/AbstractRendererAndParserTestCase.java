package org.coode.owl.rdf;

import junit.framework.TestCase;
import org.coode.owl.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owl.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owl.io.OWLParserFactoryRegistry;
import org.semanticweb.owl.model.*;
import uk.ac.manchester.cs.owl.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.ParsableOWLOntologyFactory;

import java.io.File;
import java.net.URI;
import java.util.Set;
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
 * Date: 09-May-2007<br><br>
 */
public abstract class AbstractRendererAndParserTestCase extends TestCase {

    private OWLOntologyManager man;


    protected void setUp() throws Exception {
        super.setUp();
        man = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        OWLParserFactoryRegistry.getInstance().registerParserFactory(new RDFXMLParserFactory());
        man.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        man.addOntologyFactory(new ParsableOWLOntologyFactory());
        man.addOntologyStorer(new RDFXMLOntologyStorer());

    }

    public OWLClass createClass() {
        return man.getOWLDataFactory().getOWLClass(TestUtils.createURI());
    }

    public OWLObjectProperty createObjectProperty() {
        return man.getOWLDataFactory().getObjectProperty(TestUtils.createURI());
    }

    public OWLDataProperty createDataProperty() {
        return man.getOWLDataFactory().getDataProperty(TestUtils.createURI());
    }

    public OWLIndividual createIndividual() {
        return man.getOWLDataFactory().getIndividual(TestUtils.createURI());
    }


    public OWLOntologyManager getManager() {
        return man;
    }


    protected OWLDataFactory getDataFactory() {
        return man.getOWLDataFactory();
    }

    public void testSaveAndReload() throws Exception {
        OWLOntology ontA = man.createOntology(URI.create("http://rdfxmltests/ontology"));
        for (OWLAxiom ax : getAxioms()) {
            man.applyChange(new AddAxiom(ontA, ax));
        }
//        OWLOntologyAnnotationAxiom anno = getDataFactory().getOWLOntologyAnnotationAxiom(ontA, getDataFactory().getCommentAnnotation(getDescription()));
//        man.applyChange(new AddAxiom(ontA, anno));
        File tempFile = File.createTempFile("Ontology", ".owl");
        man.saveOntology(ontA, tempFile.toURI());
        man.removeOntology(ontA);
        OWLOntology ontB = man.loadOntologyFromPhysicalURI(tempFile.toURI());
        assertTrue(ontB.getAxioms().containsAll(ontA.getAxioms()));
    }

    protected abstract Set<OWLAxiom> getAxioms();

    protected abstract String getDescription();

}
