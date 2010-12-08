package org.semanticweb.owlapi.util;

import java.util.List;

import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Dec-2006<br><br>
 *
 * Incorporates a listener into an <code>OWLOntologyChangeFilter</code>.  This listener listens to and
 * processes axiom added and axiom removed changes.  To filter out axioms of interest, override the
 * appropriate <code>visit</code> method - see <code>OWLOntologyChangeFilter</code> for more details.
 */
public class FilteringOWLOntologyChangeListener extends OWLOntologyChangeFilter implements OWLOntologyChangeListener {

    final public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
        processChanges(changes);
    }
}
