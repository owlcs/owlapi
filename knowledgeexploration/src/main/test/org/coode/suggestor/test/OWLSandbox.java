/*
 * Date: Dec 17, 2007
 *
 * code made available under Mozilla Public License (http://www.mozilla.org/MPL/MPL-1.1.html)
 *
 * copyright 2007, The University of Manchester
 *
 * Author: Nick Drummond
 * http://www.cs.man.ac.uk/~drummond/
 * Bio Health Informatics Group
 * The University Of Manchester
 */
package org.coode.suggestor.test;

import junit.framework.TestCase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

public class OWLSandbox extends TestCase {


    public void testNNF(){
        OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
        OWLDataFactory df = mngr.getOWLDataFactory();

        OWLObjectProperty p = df.getOWLObjectProperty(IRI.create("http://example.com/p"));
        OWLClass c = df.getOWLClass(IRI.create("http://example.com/c"));

        OWLObjectComplementOf notPSomeNotD = df.getOWLObjectComplementOf(df.getOWLObjectSomeValuesFrom(p, df.getOWLObjectComplementOf(c)));

        System.out.println("notPSomeNotD = " + notPSomeNotD);

        System.out.println("notPSomeNotD.getNNF() = " + notPSomeNotD.getNNF());
    }
}
