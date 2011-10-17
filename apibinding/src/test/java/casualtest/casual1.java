package casualtest;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class casual1 {
	public static void main(String[] args) throws Exception{
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLOntology o=m.createOntology();
		OWLDataFactory f=m.getOWLDataFactory();
		m.addAxiom(o, f.getOWLClassAssertionAxiom(f.getOWLObjectSomeValuesFrom(f.getOWLObjectProperty(IRI.create("http://test/p")), f.getOWLThing()), f.getOWLAnonymousIndividual()));
		StringDocumentTarget t=new StringDocumentTarget();
		m.saveOntology(o, t);
		System.out.println(t);
		m.removeOntology(o);
		o=m.loadOntologyFromOntologyDocument(new StringDocumentSource(t.toString()));
		for(OWLAxiom ax: o.getAxioms()) {
			String s=ax.toString();
			System.out.println("casual1.main() "+s);
		}
//		OWLClass a1=f.getOWLClass(IRI.create("urn:test:a1"));
//		OWLClass z=f.getOWLClass(IRI.create("urn:test:z"));
//		OWLClass c=f.getOWLClass(IRI.create("urn:test:c"));
//		OWLClass a2=f.getOWLClass(IRI.create("urn:test:a2"));
//		OWLObjectProperty r=f.getOWLObjectProperty(IRI.create("urn:test:R"));
//		m.addAxiom(o, f.getOWLSubClassOfAxiom(a1, z));
//		m.addAxiom(o, f.getOWLEquivalentClassesAxiom(a2, f.getOWLObjectAllValuesFrom(r, c)));
//		m.addAxiom(o, f.getOWLObjectPropertyDomainAxiom(r, z));
//		Set<OWLEntity> sig1=new HashSet<OWLEntity>(Arrays.asList(new OWLEntity[] {a1, z}));
//		Set<OWLEntity> sig2=new HashSet<OWLEntity>(Arrays.asList(new OWLEntity[] {a2, r, c}));
//
//		SyntacticLocalityModuleExtractor extractor=new SyntacticLocalityModuleExtractor(m, o, ModuleType.STAR);
//		Set<OWLAxiom> extracted1 = extractor.extract(sig1);
//		Set<OWLAxiom> extracted2 = extractor.extract(sig2);
//		System.out.println("casual1.main() "+extracted1);
//		System.out.println("casual1.main() "+extracted2);

	}
}
