package org.obolibrary.macro;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.coode.owlapi.manchesterowlsyntax.OntologyAxiomPair;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.*;

/**
 * @author cjm
 *
 * TODO - allow use of prefixes
 */
public class MacroExpansionVisitor {

    private static final Logger log = Logger.getLogger(MacroExpansionVisitor.class
            .getName());
	
	private OWLOntology inputOntology;
	private OWLOntologyManager manager;
	
	private Visitor visitor;
	private ManchesterSyntaxTool manchesterSyntaxTool;
	
	public MacroExpansionVisitor(OWLOntology inputOntology) {
		super();
		this.inputOntology = inputOntology;
		visitor = new Visitor(inputOntology);
		manchesterSyntaxTool = new ManchesterSyntaxTool(inputOntology);
		manager = inputOntology.getOWLOntologyManager();
	}

	public OWLOntology expandAll() {
		Set<OWLAxiom> newAxioms = new HashSet<OWLAxiom>();
		Set<OWLAxiom> rmAxioms = new HashSet<OWLAxiom>();
		
		for (OWLAxiom ax : inputOntology.getAxioms()) {
			
			OWLAxiom exAx = ax;
			if (ax instanceof OWLSubClassOfAxiom) {
				exAx = visitor.visit((OWLSubClassOfAxiom)ax);
			}
			else if (ax instanceof OWLEquivalentClassesAxiom) {
				exAx = visitor.visit((OWLEquivalentClassesAxiom)ax);
			}
			else if (ax instanceof OWLClassAssertionAxiom) {
				exAx = visitor.visit((OWLClassAssertionAxiom)ax);
			}
			else if(ax instanceof OWLAnnotationAssertionAxiom){
			 	for(OWLAxiom expandedAx: expand((OWLAnnotationAssertionAxiom)ax)){
			 		//output(expandedAx);
					if (!ax.equals(expandedAx)) {
						newAxioms.add(expandedAx);
						rmAxioms.add(ax);
					}
			 	}
			}
			/*else if(ax instanceof OWLDeclarationAxiom) {
				exAx = vistor.visit((OWLDeclarationAxiom) ax);
			}*/
			
			//output(exAx);
			if (!ax.equals(exAx)) {
				newAxioms.add(exAx);
				rmAxioms.add(ax);
			}
		}
		manager.addAxioms(inputOntology, newAxioms);
		manager.removeAxioms(inputOntology, rmAxioms);
		return inputOntology;
	}
	
	private Set<OWLAxiom> expand(OWLAnnotationAssertionAxiom ax){
		
		OWLAnnotationProperty prop = ax.getProperty();
		
		String expandTo = visitor.expandAssertionToMap.get(prop.getIRI());
		HashSet<OWLAxiom> setAx = new HashSet<OWLAxiom>();
		
		if(expandTo != null){
			
			// when expanding assertions, the axiom is an annotation assertion,
			// and the value may be not be explicitly declared. If it is not,
			// we assume it is a class
			IRI axValIRI = (IRI) ax.getValue();
			OWLClass axValClass = visitor.dataFactory.getOWLClass(axValIRI);
			if (inputOntology.getDeclarationAxioms(axValClass).size() == 0) {
				OWLDeclarationAxiom newAx = visitor.dataFactory.getOWLDeclarationAxiom(axValClass);
				manager.addAxiom(inputOntology, newAx);
				// we need to sync the MST entity checker with the new ontology plus declarations;
				// we do this by creating a new MST - this is not particularly efficient, a better
				// way might be to first scan the ontology for all annotation axioms that will be expanded,
				// then add the declarations at this point
				manchesterSyntaxTool = new ManchesterSyntaxTool(inputOntology);
			}
			
            if (log.isLoggable(Level.WARNING)) {
                log.log(Level.WARNING, "Template to Expand" + expandTo);
            }
			
			expandTo = expandTo.replaceAll("\\?X", manchesterSyntaxTool.getId((IRI)ax.getSubject()));
			expandTo = expandTo.replaceAll("\\?Y", manchesterSyntaxTool.getId(axValIRI));

            if (log.isLoggable(Level.WARNING)) {
                log.log(Level.WARNING, "Expanding " + expandTo);
            }
			
			try{
				Set<OntologyAxiomPair> setAxp =  manchesterSyntaxTool.parseManchesterExpressionFrames(expandTo);
				
				for(OntologyAxiomPair axp: setAxp){
					setAx.add(axp.getAxiom());
				}
				
			}catch(Exception ex){
                log.log(Level.SEVERE, ex.getMessage(), ex);
			}
			//TODO: 
		}
		return setAx;
	}
	
	private class Visitor extends AbstractMacroExpansionVisitor {
		
		Visitor(OWLOntology inputOntology) {
			super(inputOntology, MacroExpansionVisitor.log);
		}

		@Override
		OWLClassExpression expandOWLObjSomeVal(OWLClassExpression filler, OWLObjectPropertyExpression p) {
			return expandObject(filler, p);
		}

		@Override
		OWLClassExpression expandOWLObjHasVal(OWLObjectHasValue desc, OWLIndividual filler,
				OWLObjectPropertyExpression p) {
			OWLClassExpression result = expandObject(filler, p);
			if (result != null) {
				result = dataFactory.getOWLObjectSomeValuesFrom(desc.getProperty(), result);
			}
			return result;
		}
		
		OWLClassExpression expandObject(Object filler, OWLObjectPropertyExpression p) {
			OWLClassExpression result = null;
			IRI iri = ((OWLObjectProperty)p).getIRI();
			IRI templateVal = null;
			if (expandExpressionMap.containsKey(iri)) {
				System.out.println("svf "+p+" "+filler);
				if (filler instanceof OWLObjectOneOf) {
					Set<OWLIndividual> inds = ((OWLObjectOneOf)filler).getIndividuals();
					if (inds.size() == 1) {
						System.out.println("**svf "+p+" "+inds.iterator().next());
						OWLIndividual ind = inds.iterator().next();
						if (ind instanceof OWLNamedIndividual) {
							templateVal = ((OWLNamedObject)ind).getIRI();
						}
					}
					
				}
				if (filler instanceof OWLNamedObject) {
					 templateVal =  ((OWLNamedObject)filler).getIRI();
				}
				if (templateVal != null) {
					System.out.println("TEMPLATEVAL: "+templateVal.toString());

					String tStr = expandExpressionMap.get(iri);
					
					System.out.println("t: "+tStr);
					String exStr = tStr.replaceAll("\\?Y", manchesterSyntaxTool.getId( templateVal));
					System.out.println("R: "+exStr);

					try {
						result = manchesterSyntaxTool.parseManchesterExpression(exStr);
					} catch (ParserException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
					}
				}
			}
			return result;
		}
	}

	/**
	 * Call this method to clear internal references.
	 */
	public void dispose() {
		manchesterSyntaxTool.dispose();
	}
}
