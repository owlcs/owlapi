package org.semanticweb.owlapi.api.test.baseclasses;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;

@SuppressWarnings("javadoc")
public class CheckTestCase {

    @Test
    public void checkVerify() {
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        OWLDataProperty t = df.getOWLDataProperty(IRI.create("urn:test#t"));
        Set<OWLAxiom> ax1 = new HashSet<OWLAxiom>();
        ax1.add(df.getOWLDataPropertyAssertionAxiom(t,
                df.getOWLAnonymousIndividual(), df.getOWLLiteral("test1")));
        ax1.add(df.getOWLDataPropertyAssertionAxiom(t,
                df.getOWLAnonymousIndividual(), df.getOWLLiteral("test2")));
        Set<OWLAxiom> ax2 = new HashSet<OWLAxiom>();
        ax2.add(df.getOWLDataPropertyAssertionAxiom(t,
                df.getOWLAnonymousIndividual(), df.getOWLLiteral("test1")));
        ax2.add(df.getOWLDataPropertyAssertionAxiom(t,
                df.getOWLAnonymousIndividual(), df.getOWLLiteral("test2")));
        assertFalse(ax1.equals(ax2));
        assertTrue(AbstractOWLAPITestCase.verifyErrorIsDueToBlankNodesId(ax1,
                ax2));
    }
}
