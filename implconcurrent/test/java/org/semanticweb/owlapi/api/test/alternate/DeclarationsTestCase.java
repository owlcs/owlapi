package org.semanticweb.owlapi.api.test.alternate;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-Jan-2009
 */
public class DeclarationsTestCase extends AbstractFileRoundTrippingTestCase {
    public void testCorrectAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLClass(IRI.create("http://www.semanticweb.org/ontologies/declarations#Cls"))));
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLObjectProperty(IRI.create("http://www.semanticweb.org/ontologies/declarations#op"))));
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLDataProperty(IRI.create("http://www.semanticweb.org/ontologies/declarations#dp"))));
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLNamedIndividual(IRI.create("http://www.semanticweb.org/ontologies/declarations#ni"))));
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLAnnotationProperty(IRI.create("http://www.semanticweb.org/ontologies/declarations#ap"))));
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLDatatype(IRI.create("http://www.semanticweb.org/ontologies/declarations#dt"))));
        assertEquals(getOnt().getAxioms(), axioms);
    }

    @Override
	protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        System.out.println(target);
        super.handleSaved(target, format);
    }

    @Override
	protected String getFileName() {
        return "TestDeclarations.rdf";
    }
}
