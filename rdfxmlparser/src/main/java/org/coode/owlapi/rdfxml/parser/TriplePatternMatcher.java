package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public interface TriplePatternMatcher {

    boolean matches(OWLRDFConsumer consumer, IRI mainNode);

    OWLObject createObject(OWLRDFConsumer consumer);
}
