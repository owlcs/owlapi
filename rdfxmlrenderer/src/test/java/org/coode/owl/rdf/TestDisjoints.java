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
import java.util.HashSet;
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
 * <p/>
 * Test cases for rendering of disjoint axioms.  The OWL 1.1 specification makes it
 * possible to specify that a set of classes are mutually disjoint.  Unfortunately,
 * this must be represented in RDF as a set of pairwise disjoint statements. In otherwords,
 * DisjointClasses(A, B, C) must be represented as DisjointWith(A, B), DisjointWith(A, C)
 * DisjointWith(B, C).  ~This test case ensure that these axioms are serialsed correctly.
 */
public class TestDisjoints extends TestCase {

    private OWLOntologyManager man;


    protected void setUp() throws Exception {
        super.setUp();
        man = new OWLOntologyManagerImpl();
        OWLParserFactoryRegistry.getInstance().registerParserFactory(new RDFXMLParserFactory());
        man.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        man.addOntologyFactory(new ParsableOWLOntologyFactory());
        man.addOntologyStorer(new RDFXMLOntologyStorer());
    }

    // The test case below is redundant now that the RDF mapping for OWL 2 contains
    // an "AllDisjoint" mapping.

//    public void testDisjoints() throws Exception {
//        OWLOntology ontA = man.createOntology(TestUtils.createURI());
//        OWLClass clsA = man.getOWLDataFactory().getOWLClass(TestUtils.createURI());
//        OWLClass clsB = man.getOWLDataFactory().getOWLClass(TestUtils.createURI());
//        OWLClass clsC = man.getOWLDataFactory().getOWLClass(TestUtils.createURI());
//        OWLObjectProperty prop = man.getOWLDataFactory().getOWLObjectProperty(TestUtils.createURI());
//        OWLClassExpression clsD = man.getOWLDataFactory().getOWLObjectSomeValuesFrom(prop, man.getOWLDataFactory().getOWLThing());
//        Set<OWLClassExpression> expressions = new HashSet<OWLClassExpression>();
//        expressions.add(clsA);
//        expressions.add(clsB);
//        expressions.add(clsC);
//        expressions.add(clsD);
//        OWLAxiom ax = man.getOWLDataFactory().getOWLDisjointClassesAxiom(expressions);
//        man.applyChange(new AddAxiom(ontA, ax));
//        File tempFile = File.createTempFile("Ontology", ".owl");
//        man.saveOntology(ontA, tempFile.toURI());
//        OWLOntology ontB = man.loadOntologyFromPhysicalURI(tempFile.toURI());
//        Set<OWLAxiom> pairwiseAxioms = new HashSet<OWLAxiom>();
//        List<OWLClassExpression> expressionsList = new ArrayList<OWLClassExpression>(expressions);
//        for(int i = 0; i < expressions.size(); i++) {
//            for(int j = i; j < expressions.size(); j++) {
//                if(i != j) {
//                    System.out.println(i + " -- " + j);
//                    pairwiseAxioms.add(man.getOWLDataFactory().getOWLDisjointClassesAxiom(CollectionFactory.createSet(expressionsList.get(i), expressionsList.get(j))));
//                }
//            }
//        }
//        assertTrue(ontB.getAxioms().containsAll(pairwiseAxioms));
//    }

    public void testAnonDisjoints() throws Exception {
        OWLOntology ontA = man.createOntology(TestUtils.createURI());
        OWLClass clsA = man.getOWLDataFactory().getOWLClass(TestUtils.createURI());
        OWLClass clsB = man.getOWLDataFactory().getOWLClass(TestUtils.createURI());
        OWLObjectProperty prop = man.getOWLDataFactory().getOWLObjectProperty(TestUtils.createURI());
        OWLClassExpression descA = man.getOWLDataFactory().getOWLObjectSomeValuesFrom(prop, clsA);
        OWLClassExpression descB = man.getOWLDataFactory().getOWLObjectSomeValuesFrom(prop, clsB);
        Set<OWLClassExpression> classExpressions = new HashSet<OWLClassExpression>();
        classExpressions.add(descA);
        classExpressions.add(descB);
        OWLAxiom ax = man.getOWLDataFactory().getOWLDisjointClassesAxiom(classExpressions);
        man.applyChange(new AddAxiom(ontA, ax));
        File tempFile = File.createTempFile("Ontology", ".owl");
        man.saveOntology(ontA, tempFile.toURI());
        OWLOntology ontB = man.loadOntologyFromPhysicalURI(tempFile.toURI());
        assertTrue(ontB.getAxioms().contains(ax));
    }

}
