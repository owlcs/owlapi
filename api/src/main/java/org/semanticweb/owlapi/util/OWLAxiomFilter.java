package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Apr-2007<br><br>
 */
public interface OWLAxiomFilter {

    boolean passes(OWLAxiom axiom);
}
