package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 12-Jan-2010
 * </p>
 * A marker interface for an axiom that can be represented by a set of SubClassOf axioms that is equivalent to this axiom.
 */
public interface OWLSubClassOfAxiomSetShortCut {

    Set<OWLSubClassOfAxiom> asOWLSubClassOfAxioms();
}
