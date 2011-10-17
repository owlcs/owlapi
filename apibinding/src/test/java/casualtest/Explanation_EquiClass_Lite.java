package casualtest;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOWLObjectRendererImpl;
@SuppressWarnings("javadoc")
public class Explanation_EquiClass_Lite {
	private static final String file = "file:///Users/ignazio/Downloads/abd.muse.massey.ac.nz.owl";
	private static OWLObjectRenderer renderer = new ManchesterOWLSyntaxOWLObjectRendererImpl(); //new DLSyntaxObjectRenderer();

	public static void main(String[] args) throws Exception {
		System.out.print("Reading file " + file + "...");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntology(IRI.create(file));
		OWLDataFactory factory = manager.getOWLDataFactory();
		System.out.println("Ontology loaded");
		OWLAnnotationProperty label = factory.getRDFSLabel();
		for (OWLAxiom e : ontology.getAxioms()) {
			Set<OWLAnnotation> labels = e.getAnnotations(label);
			if (!labels.isEmpty()) {
				System.out.println("\n***" + e.getAxiomType() + ":");
				System.out.println("Annotation: " + labels);
				System.out.println(renderer.render(e));
			}
		}
	}
}