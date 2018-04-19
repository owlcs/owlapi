package org;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OntoSig {
    public static void main(String[] args) throws OWLOntologyCreationException, Exception {
        System.in.read();
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        OWLDataFactory df = m.getOWLDataFactory();
        OWLOntology o = m.createOntology();
        String ns = "urn:test:ns#";
        for (int i = 0; i < 1000000; i++) {
            m.addAxiom(o, df.getOWLDeclarationAxiom(df.getOWLClass(IRI.create(ns + "c" + i))));
            m.addAxiom(o,
                df.getOWLDeclarationAxiom(df.getOWLDataProperty(IRI.create(ns + "dp" + i))));
            m.addAxiom(o,
                df.getOWLDeclarationAxiom(df.getOWLObjectProperty(IRI.create(ns + "op" + i))));
            m.addAxiom(o, df.getOWLDeclarationAxiom(df.getOWLDatatype(IRI.create(ns + "d" + i))));
            m.addAxiom(o,
                df.getOWLDeclarationAxiom(df.getOWLAnnotationProperty(IRI.create(ns + "ap" + i))));
            m.addAxiom(o,
                df.getOWLDeclarationAxiom(df.getOWLNamedIndividual(IRI.create(ns + "i" + i))));
        }
        long firstrun = 0;

        for (int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            o.getClassesInSignature();
            o.getDataPropertiesInSignature();
            o.getObjectPropertiesInSignature();
            o.getDatatypesInSignature();
            o.getIndividualsInSignature();
            o.getAnnotationPropertiesInSignature();
            o.getSignature();
            long elapsed = System.currentTimeMillis() - start;
            if (i == 0) {
                firstrun = elapsed;
            }
            System.out.println("OntoSig.main() " + elapsed);
        }
        System.out.println("OntoSig.main() first run: " + firstrun);
    }
}
