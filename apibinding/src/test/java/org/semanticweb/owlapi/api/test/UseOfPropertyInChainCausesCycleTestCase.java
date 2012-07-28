package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;

public class UseOfPropertyInChainCausesCycleTestCase {
    
    @Test
    public void shouldCauseViolations() throws OWLOntologyCreationException {
        OWLOntology o=OWLManager.createOWLOntologyManager().createOntology();
        OWLDataFactory f=OWLManager.getOWLDataFactory();
        
//        SubObjectPropertyOf( ObjectPropertyChain( a:hasFather a:hasBrother ) a:hasUncle )   The brother of someone's father is that person's uncle.
//        SubObjectPropertyOf( ObjectPropertyChain( a:hasChild a:hasUncle ) a:hasBrother )    The uncle of someone's child is that person's brother. 
        
        final OWLObjectProperty father = f.getOWLObjectProperty(IRI.create("urn:test:hasFather"));
        final OWLObjectProperty brother = f.getOWLObjectProperty(IRI.create("urn:test:hasBrother"));
        final OWLObjectProperty child = f.getOWLObjectProperty(IRI.create("urn:test:hasChild"));
        final OWLObjectProperty uncle = f.getOWLObjectProperty(IRI.create("urn:test:hasUncle"));

        o.getOWLOntologyManager().addAxiom(o, f.getOWLDeclarationAxiom(father  ));
        o.getOWLOntologyManager().addAxiom(o, f.getOWLDeclarationAxiom(brother ));
        o.getOWLOntologyManager().addAxiom(o, f.getOWLDeclarationAxiom(child ));
        o.getOWLOntologyManager().addAxiom(o, f.getOWLDeclarationAxiom(uncle ));

        final OWLSubPropertyChainOfAxiom brokenAxiom1 = f.getOWLSubPropertyChainOfAxiom(Arrays.asList(father, brother), uncle);
        final OWLSubPropertyChainOfAxiom brokenAxiom2 = f.getOWLSubPropertyChainOfAxiom(Arrays.asList(child, uncle), brother);
        
        OWLObjectPropertyManager manager=new OWLObjectPropertyManager(o.getOWLOntologyManager(), o);

        o.getOWLOntologyManager().addAxiom(o, brokenAxiom1);
        o.getOWLOntologyManager().addAxiom(o, brokenAxiom2);

        assertTrue(manager.isLessThan(brother, uncle));
        assertTrue(manager.isLessThan( uncle, brother));
        assertTrue(manager.isLessThan(brother, brother));
        assertTrue(manager.isLessThan(uncle, uncle));

        OWL2DLProfile profile=new OWL2DLProfile();
        final List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertTrue(!violations.isEmpty());
        for (OWLProfileViolation v: violations){
            assertTrue( v.getAxiom().equals(brokenAxiom1)||v.getAxiom().equals(brokenAxiom2));
        System.out.println(v);    
        }
    }
    @Test
    public void shouldNotCauseViolations() throws OWLOntologyCreationException {
        OWLOntology o=OWLManager.createOWLOntologyManager().createOntology();
        OWLDataFactory f=OWLManager.getOWLDataFactory();
        
        final OWLObjectProperty father = f.getOWLObjectProperty(IRI.create("urn:test:hasFather"));
        final OWLObjectProperty brother = f.getOWLObjectProperty(IRI.create("urn:test:hasBrother"));
        final OWLObjectProperty child = f.getOWLObjectProperty(IRI.create("urn:test:hasChild"));
        final OWLObjectProperty uncle = f.getOWLObjectProperty(IRI.create("urn:test:hasUncle"));

        o.getOWLOntologyManager().addAxiom(o, f.getOWLDeclarationAxiom(father  ));
        o.getOWLOntologyManager().addAxiom(o, f.getOWLDeclarationAxiom(brother ));
        o.getOWLOntologyManager().addAxiom(o, f.getOWLDeclarationAxiom(child ));
        o.getOWLOntologyManager().addAxiom(o, f.getOWLDeclarationAxiom(uncle ));

        final OWLSubPropertyChainOfAxiom brokenAxiom1 = f.getOWLSubPropertyChainOfAxiom(Arrays.asList(father, brother), uncle);
        
        OWLObjectPropertyManager manager=new OWLObjectPropertyManager(o.getOWLOntologyManager(), o);

        o.getOWLOntologyManager().addAxiom(o, brokenAxiom1);

        assertTrue(manager.isLessThan(brother, uncle));
     
        OWL2DLProfile profile=new OWL2DLProfile();
        final List<OWLProfileViolation> violations = profile.checkOntology(o).getViolations();
        assertTrue(violations.isEmpty());
        for (OWLProfileViolation v: violations){
            assertTrue( v.getAxiom().equals(brokenAxiom1));
        System.out.println(v);    
        }
    }
}
