package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.coode.xml.XMLWriterPreferences;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 10-Dec-2009
 */
public class IRISubstringTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);
        IRI iriA = IRI.create("http://owlapi.sourceforge.net/properties#propA");
        IRI iriB = IRI.create("http://owlapi.sourceforge.net/properties2#propB");
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLClass clsA = getOWLClass("A");
        OWLAnnotationProperty propA = getFactory().getOWLAnnotationProperty(iriA);
        OWLAnnotationProperty propB = getFactory().getOWLAnnotationProperty(iriB);
        axioms.add(getFactory().getOWLDeclarationAxiom(clsA));
        axioms.add(getFactory().getOWLAnnotationAssertionAxiom(propA, clsA.getIRI(), getFactory().getOWLLiteral("value1")));
        axioms.add(getFactory().getOWLAnnotationAssertionAxiom(propB, clsA.getIRI(), getFactory().getOWLLiteral("value2")));
        return axioms;
    }
}
