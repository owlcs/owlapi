package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Jun-2009
 */
public class DatatypeDefinitionTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLDatatype datatype = getFactory().getOWLDatatype(IRI.create("http://www.ont.com/myont/mydatatype"));
        OWLDataRange dr = getFactory().getOWLDataComplementOf(getFactory().getIntegerOWLDatatype());
        axioms.add(getFactory().getOWLDatatypeDefinitionAxiom(datatype, dr));
        axioms.add(getFactory().getOWLDeclarationAxiom(datatype));
        return axioms;
    }
}
