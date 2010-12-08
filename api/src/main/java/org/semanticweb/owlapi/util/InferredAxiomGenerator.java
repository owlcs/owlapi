package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 27-Jul-2007<br><br>
 *
 * Given a reasoner, implementations of this interface generate axioms that
 * represent inferred information.  For example, an implementation might generate
 * the necessary subclass axioms that represent the inferred class subsumption
 * hierarchy within the reasoner.  It is assumed that axioms generated reflect
 * the inferences drawn from the ontologies which were loaded into the reasoner.c
 */
public interface InferredAxiomGenerator<A extends OWLAxiom> {

    Set<A> createAxioms(OWLOntologyManager manager, OWLReasoner reasoner);

    String getLabel();
}
