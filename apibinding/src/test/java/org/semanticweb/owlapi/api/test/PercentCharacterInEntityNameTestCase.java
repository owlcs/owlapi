package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-Sep-2009
 */
public class PercentCharacterInEntityNameTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLClass(IRI.create("http://www.test.com/ontology#Class%37A"))));
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLObjectProperty(IRI.create("http://www.test.com/ontology#prop%37A"))));
        return axioms;
    }
}
