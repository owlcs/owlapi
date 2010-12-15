package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 15-Jan-2009
 */
public interface OWLNaryDataRange extends OWLDataRange {

    Set<OWLDataRange> getOperands();
}
