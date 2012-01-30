package casualtest;

import java.util.Collections;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;

public class RecursiveAnnotationTest {
	public static void main(String[] args) throws Exception{
	String s="<?xml version=\"1.0\"?>\n" +
			"\n" +
			"\n" +
			"<!DOCTYPE rdf:RDF [\n" +
			"    <!ENTITY OCRe2 \"http://purl.org/net/OCRe/\" >\n" +
			"    <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n" +
			"    <!ENTITY obo \"http://purl.obolibrary.org/obo/\" >\n" +
			"    <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n" +
			"    <!ENTITY OCRe \"http://purl.org/net/OCRe/OCRe.owl#\" >\n" +
			"    <!ENTITY snap \"http://www.ifomis.org/bfo/1.1/snap#\" >\n" +
			"    <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n" +
			"    <!ENTITY OCRe_ext \"http://purl.org/net/OCRe/OCRe_ext.owl#\" >\n" +
			"\n" +
			"    <!ENTITY HSDB_OCRe \"http://purl.org/net/OCRe/HSDB_OCRe.owl#\" >\n" +
			"    <!ENTITY study_protocol2 \"http://purl.org/net/OCRe/study_protocol#\" >\n" +
			"    <!ENTITY statistics \"http://purl.org/net/OCRe/statistics.owl#\" >\n" +
			"    <!ENTITY fma3 \"http://sig.biostr.washington.edu/fma3.0#\" >\n" +
			"    <!ENTITY study_design \"http://purl.org/net/OCRe/study_design.owl#\" >\n" +
			"    <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n" +
			"    <!ENTITY study_protocol \"http://purl.org/net/OCRe/study_protocol.owl#\" >\n" +
			"    <!ENTITY oboInOwl \"http://www.geneontology.org/formats/oboInOwl#\" >\n" +
			"    <!ENTITY protege \"http://protege.stanford.edu/plugins/owl/protege#\" >\n" +
			"\n" +
			"    <!ENTITY export_annotations_def \"http://purl.org/net/OCRe/export_annotations_def.owl#\" >\n" +
			"]>\n" +
			"\n" +
			"\n" +
			"<rdf:RDF xmlns=\"&OCRe2;HSDB_OCRe.owl#\"\n" +
			"     xml:base=\"&OCRe2;HSDB_OCRe.owl\"\n" +
			"     xmlns:OCRe_ext=\"&OCRe2;OCRe_ext.owl#\"\n" +
			"     xmlns:protege=\"http://protege.stanford.edu/plugins/owl/protege#\"\n" +
			"     xmlns:OCRe2=\"http://purl.org/net/OCRe/\"\n" +
			"     xmlns:fma3=\"http://sig.biostr.washington.edu/fma3.0#\"\n" +
			"     xmlns:snap=\"http://www.ifomis.org/bfo/1.1/snap#\"\n" +
			"     xmlns:OCRe=\"&OCRe2;OCRe.owl#\"\n" +
			"     xmlns:study_protocol2=\"&OCRe2;study_protocol#\"\n" +
			"     xmlns:HSDB_OCRe=\"&OCRe2;HSDB_OCRe.owl#\"\n" +
			"     xmlns:statistics=\"&OCRe2;statistics.owl#\"\n" +
			"     xmlns:study_design=\"&OCRe2;study_design.owl#\"\n" +
			"     xmlns:obo=\"http://purl.obolibrary.org/obo/\"\n" +
			"     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" +
			"     xmlns:export_annotations_def=\"&OCRe2;export_annotations_def.owl#\"\n" +
			"     xmlns:study_protocol=\"&OCRe2;study_protocol.owl#\"\n" +
			"     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n" +
			"     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" +
			"     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
			"     xmlns:oboInOwl=\"http://www.geneontology.org/formats/oboInOwl#\">\n" +
			"    <owl:Ontology rdf:about=\"&OCRe2;HSDB_OCRe.owl\"/>\n"+
			"<owl:AnnotationProperty rdf:about=\"http://purl.org/net/OCRe/export_annotations_def.owl#OCRE520413\"/>\n"+
			"<owl:AnnotationProperty rdf:about=\"http://purl.org/net/OCRe/export_annotations_def.owl#OCRE863610\"/>\n"+

			"    <owl:Class rdf:about=\"&OCRe2;OCRe.owl#OCRE400076\">\n" +

//			"        <rdfs:label rdf:datatype=\"&xsd;string\">Person</rdfs:label>\n" +
//			"        <rdfs:subClassOf rdf:resource=\"&OCRe2;OCRe.owl#OCRE400064\"/>\n" +
//			"\n" +
//			"        <rdfs:subClassOf rdf:resource=\"&OCRe2;OCRe_ext.owl#OCRE546280\"/>\n" +
//			"        <owl:disjointWith rdf:resource=\"&OCRe2;OCRe.owl#OCRE449000\"/>\n" +
//			"        <owl:disjointWith rdf:resource=\"&OCRe2;OCRe.owl#OCRE584000\"/>\n" +
//			"        <owl:disjointWith rdf:resource=\"&OCRe2;OCRe.owl#OCRE740000\"/>\n" +
//			"        <owl:disjointWith rdf:resource=\"&OCRe2;OCRe.owl#OCRE831288\"/>\n" +
//			"        <owl:disjointWith rdf:resource=\"&OCRe2;OCRe.owl#OCRE832477\"/>\n" +
//			"        <owl:disjointWith rdf:resource=\"&OCRe2;OCRe.owl#OCRE992000\"/>\n" +
//			"        <owl:disjointWith rdf:resource=\"&owl;Nothing\"/>\n" +
//			"        <obo:IAO_0000115 rdf:datatype=\"&xsd;string\">A human being</obo:IAO_0000115>\n" +
//			"\n" +
//			"        <export_annotations_def:OCRE520413 rdf:resource=\"&OCRe2;OCRe.owl#OCRE900064\"/>\n" +
//			"        <export_annotations_def:OCRE520413 rdf:resource=\"&OCRe2;OCRe.owl#OCRE900225\"/>\n" +
//			"        <export_annotations_def:OCRE520413 rdf:resource=\"&OCRe2;OCRe.owl#OCRE900226\"/>\n" +
			"        <export_annotations_def:OCRE520413 rdf:resource=\"&OCRe2;OCRe.owl#OCRE901003\"/>\n" +
//			"        <export_annotations_def:OCRE520413 rdf:resource=\"&OCRe2;OCRe.owl#OCRE901005\"/>\n" +
			"    </owl:Class>\n" +
			"    <owl:Axiom>\n" +
			"        <export_annotations_def:OCRE863610 rdf:datatype=\"&xsd;int\">3</export_annotations_def:OCRE863610>\n" +
			"\n" +
			"        <owl:annotatedSource rdf:resource=\"&OCRe2;OCRe.owl#OCRE400076\"/>\n" +
			"        <owl:annotatedTarget rdf:resource=\"&OCRe2;OCRe.owl#OCRE901003\"/>\n" +
			"        <owl:annotatedProperty rdf:resource=\"&OCRe2;export_annotations_def.owl#OCRE520413\"/>\n" +
			"    </owl:Axiom>\n" +
			""+

			"</rdf:RDF>";

		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLOntology o=m.loadOntologyFromOntologyDocument(new StringDocumentSource(s));
		OWL2DLProfile profile=new OWL2DLProfile();
		OWLProfileReport checkOntology = profile.checkOntology(o);
		for(OWLProfileViolation v:checkOntology.getViolations()) {
			System.out.println("RecursiveAnnotationTest.main() "+v);
		}
		OWLDataFactory f=m.getOWLDataFactory();
		//OWLOntology o=m.createOntology(IRI.create("urn:test:ontology"));
		OWLClass c=f.getOWLClass(IRI.create("urn:class:c"));
		OWLAnnotationProperty p1=f.getOWLAnnotationProperty(IRI.create("urn:a1level:annproperty1"));
		OWLAnnotationValue v1=f.getOWLLiteral("v1");
		OWLAnnotationValue v2=f.getOWLLiteral("v2");
		OWLAnnotationProperty p2=f.getOWLAnnotationProperty(IRI.create("urn:a2level:annproperty2"));
		OWLAxiom ax=f.getOWLSubClassOfAxiom(c, f.getOWLThing());
		OWLAnnotationAssertionAxiom ann1=f.getOWLAnnotationAssertionAxiom(p1, c.getIRI(), v1, Collections.singleton(f.getOWLAnnotation(p2, v2)));
		m.addAxiom(o, ax);
		m.addAxiom(o, ann1);
		for(OWLAxiom ax1: o.getAxioms()) {
			System.out.println("RecursiveAnnotationTest.main() 1\t"+ax1);
		}
		final StringDocumentTarget documentTarget = new StringDocumentTarget();
		m.saveOntology(o, documentTarget);
		m.removeOntology(o);
		o=m.loadOntologyFromOntologyDocument(new StringDocumentSource(documentTarget.toString()));
		profile=new OWL2DLProfile();
		checkOntology = profile.checkOntology(o);
		for(OWLProfileViolation v:checkOntology.getViolations()) {
			System.out.println("RecursiveAnnotationTest.main() "+v);
		}

		for(OWLAxiom ax1: o.getAxioms()) {
			System.out.println("RecursiveAnnotationTest.main() "+ax1);
		}
		m.saveOntology(o, new SystemOutDocumentTarget());

	}
}
