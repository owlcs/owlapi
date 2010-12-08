package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import java.io.File;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apibinding.configurables.BoxExtractor;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class BoxExtractorTest {
	public static void main(String[] args) throws Exception{
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLOntology o=m.loadOntologyFromOntologyDocument(new File("../ReasonersTest/materializedOntologies/allobi.owl"));
		
		Set<OWLAxiom> tbox=o.getTBoxAxioms(true);
		Set<OWLAxiom> testtbox=o.accept(new BoxExtractor(AxiomType.TBoxAxiomTypes, true));
		if(tbox.equals(testtbox)) {
			System.out.println("BoxExtractorTest.main() extraction equivalent");
		}else {
			System.out.println("BoxExtractorTest.main() extractions differ");
		}
		System.out.println("BoxExtractorTest.main() "+tbox.toString().replace(",", "\n"));
	}
}
