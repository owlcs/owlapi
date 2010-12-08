package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 *
 * Specifies that an IRI that is used for a datatype is also used for a class IRI
 */
public class DatatypeIRIAlsoUsedAsClassIRI extends OWLProfileViolation implements OWL2DLProfileViolation {

    private IRI iri;

    public DatatypeIRIAlsoUsedAsClassIRI(OWLOntology ontology, OWLAxiom axiom, IRI iri) {
        super(ontology, axiom);
        this.iri = iri;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public IRI getIRI() {
        return iri;
    }
}
