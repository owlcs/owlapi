package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Apr-2007<br><br>
 *
 * Inputs to algorithms etc. can require a set of ontologies.  This
 * interface provides a convenient lightweight access point for such
 * a set.
 */
public interface OWLOntologySetProvider {

    Set<OWLOntology> getOntologies();

}
