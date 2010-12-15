package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 08-Jun-2009
 */
public class RelativeURITestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        OWLOntology ont = getOWLOntology("Ont");
        OWLClass cls = getFactory().getOWLClass(IRI.create(ont.getOntologyID().getOntologyIRI() + "/Office"));
        Set<OWLAxiom> axs = new HashSet<OWLAxiom>();
        axs.add(getFactory().getOWLDeclarationAxiom(cls));
        return axs;

    }
}
