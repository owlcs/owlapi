package org.coode.owl.rdf;

import junit.framework.TestCase;
import org.coode.owlapi.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.model.*;
import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import java.io.File;
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
        return man.getOWLDataFactory().getOWLClass(TestUtils.createIRI());
    }

    public OWLObjectProperty createObjectProperty() {
        return man.getOWLDataFactory().getOWLObjectProperty(TestUtils.createIRI());
    }

    public OWLDataProperty createDataProperty() {
        return man.getOWLDataFactory().getOWLDataProperty(TestUtils.createIRI());
    }

    public OWLIndividual createIndividual() {
        return man.getOWLDataFactory().getOWLNamedIndividual(TestUtils.createIRI());
    }


    public OWLOntologyManager getManager() {
        return man;
    }


    protected OWLDataFactory getDataFactory() {
        return man.getOWLDataFactory();
    }

    public void testSaveAndReload() throws Exception {
        OWLOntology ontA = man.createOntology(IRI.create("http://rdfxmltests/ontology"));
        for (OWLAxiom ax : getAxioms()) {
            man.applyChange(new AddAxiom(ontA, ax));
        }
//        OWLOntologyAnnotationAxiom anno = getDataFactory().getOWLOntologyAnnotationAxiom(ontA, getDataFactory().getCommentAnnotation(getClassExpression()));
//        man.applyChange(new AddAxiom(ontA, anno));
        File tempFile = File.createTempFile("Ontology", ".owlapi");
        man.saveOntology(ontA, IRI.create(tempFile.toURI()));
        man.removeOntology(ontA);
        OWLOntology ontB = man.loadOntologyFromOntologyDocument(IRI.create(tempFile.toURI()));
        assertTrue(ontB.getAxioms().containsAll(ontA.getAxioms()));
    }

    protected abstract Set<OWLAxiom> getAxioms();

    protected abstract String getClassExpression();

}
