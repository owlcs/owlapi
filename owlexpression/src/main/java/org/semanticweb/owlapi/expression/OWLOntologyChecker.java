package org.semanticweb.owlapi.expression;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 15-Feb-2009
 */
public interface OWLOntologyChecker {

    OWLOntology getOntology(String name);

}
