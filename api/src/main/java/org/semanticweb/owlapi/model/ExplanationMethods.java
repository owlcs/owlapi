import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owl.explanation.api.*;
import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;

public class ExplanationMethods {

	public static Set<Explanation<OWLAxiom>> getExplanationForAxioms(OWLReasonerFactory rf, OWLOntology ont, OWLAxiom entailment){
		
		OWLReasoner r = rf.createReasoner(ont);
		
		if (r.isConsistent()) {
		
			// Create the explanation generator factory which uses reasoners provided by the specified
			// reasoner factory
			ExplanationGeneratorFactory<OWLAxiom> genFac = ExplanationManager.createExplanationGeneratorFactory(rf);
			
			// Now create the actual explanation generator for our ontology
			ExplanationGenerator<OWLAxiom> gen = genFac.createExplanationGenerator(ont);
			
			// Get our explanations.
			Set<Explanation<OWLAxiom>> expl = gen.getExplanations(entailment);
			
			return expl;
		}
		else {
			return null;
		}
		
	}
	
	public static Set<Explanation<OWLAxiom>> getExplanationInconsistent(OWLReasonerFactory rf, OWLOntology ont , OWLDataFactory df){
		OWLReasoner r = rf.createReasoner(ont);
		
		if(r.isConsistent()) {
			return null;
		}
		
		else {
			
			ExplanationGeneratorFactory<OWLAxiom> genFac = new InconsistentOntologyExplanationGeneratorFactory(rf,1000L);
			
			ExplanationGenerator<OWLAxiom> gen = genFac.createExplanationGenerator(ont);// get the inconsistent axiom explanation

			Set<Explanation<OWLAxiom>> expl = gen.getExplanations(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLNothing()));//getting the explanation
		    
			return expl;
		}
	}
	
	public static Set<Explanation<OWLAxiom>> getExplanationSubClassSimple(OWLReasonerFactory rf, OWLOntology ont , OWLDataFactory df){
		// Create the explanation generator factory which uses reasoners provided by the specified
		// reasoner factory
		OWLReasoner r = rf.createReasoner(ont);
		
		if (r.isConsistent()) {
			ExplanationGeneratorFactory<OWLAxiom> genFac = ExplanationManager.createExplanationGeneratorFactory(rf);
			
			// Now create the actual explanation generator for our ontology
			ExplanationGenerator<OWLAxiom> gen = genFac.createExplanationGenerator(ont);
			
			System.out.println();
			System.out.println(" A subClassOf B");
			System.out.println();
			
			Set<Explanation<OWLAxiom>> expl = new HashSet<Explanation<OWLAxiom>>();;
			
			Set<OWLClass> classes = ont.getClassesInSignature();//get all the classes in the ontology
			
			Iterator<OWLClass> i = classes.iterator();//iterator
			
			while (i.hasNext()) { 
	        
				OWLClass cl = i.next();//getting the owlclass
				
				NodeSet<OWLClass> superclasses = r.getSuperClasses(cl, false); // get all the superclass for the specified class
				
				Iterator<Node<OWLClass>> n = superclasses.iterator();//getting the superclasses
				
				while (n.hasNext()) { 
				
					Iterator<OWLClass> n1 = n.next().iterator();//iterator
					
					while(n1.hasNext()) {
					
						OWLClass cl1 = n1.next();//one of the superclass
						
						OWLAxiom entailment = df.getOWLSubClassOfAxiom(cl,cl1) ; // Get a reference to the axiom that represents the entailment that we want explanation for
						
						Set<Explanation<OWLAxiom>> expl1 = gen.getExplanations(entailment);//get explanations
						
						Iterator<Explanation<OWLAxiom>> i1 = expl1.iterator();
					    
						while(i1.hasNext()) {
					    
							Explanation<OWLAxiom> ex = i1.next();//printing the explanation
					    	
							expl.add(ex);
					    
						}
						
					}	
					
				}
				
			}

			return expl;
		
		}
		else {
			return null;
		}
			
	}
	
	
	public static Set<Explanation<OWLAxiom>> getExplanationSubClassComplex(OWLReasonerFactory rf, OWLOntology ont , OWLDataFactory df){
		// Create the explanation generator factory which uses reasoners provided by the specified
		// reasoner factory
		OWLReasoner r = rf.createReasoner(ont);
		
		if (r.isConsistent()) {
			ExplanationGeneratorFactory<OWLAxiom> genFac = ExplanationManager.createExplanationGeneratorFactory(rf);
			
			// Now create the actual explanation generator for our ontology
			ExplanationGenerator<OWLAxiom> gen = genFac.createExplanationGenerator(ont);
			
			System.out.println();
			System.out.println("A and B a simple class or B a complex class");
			System.out.println();
			
			Set<Explanation<OWLAxiom>> expl = new HashSet<Explanation<OWLAxiom>>();;
			
			Set<OWLAxiom> entailment = ont.getAxioms() ; // Get a reference to the axiom that represents the entailment that we want explanation for

			Iterator<OWLAxiom> i3 = entailment.iterator();
			
			while(i3.hasNext()) {
			
				OWLAxiom ax = i3.next();
				
				if(ax.getAxiomType().toString().compareTo("SubClassOf")==0) {//selecting the subclassof axioms for explanation
				
					Set<Explanation<OWLAxiom>> expl1 = gen.getExplanations(ax);//getting the explanations 
					
					Iterator<Explanation<OWLAxiom>> i4 = expl1.iterator();
				    
					while(i4.hasNext()) {
				    
						Explanation<OWLAxiom> ex = i4.next();
				    	
						expl.add(ex);
				    
					}
				
				}
				
			}
			
			return expl;

		}
		else {
			return null;
		}
		
		
		
	}
	
	// public static void printExplanations(Set<Explanation<OWLAxiom>> expl) {
	// 	Iterator<Explanation<OWLAxiom>> i1 = expl.iterator();
	//     while(i1.hasNext()) {
	//     	Explanation<OWLAxiom> ex = i1.next();//printing the explanation
	//     	System.out.println("Explaination "+ex.getAxioms());//printing the explanation
	//     	System.out.println();
	//     }
	// }
	
	
}
