package org.semanticweb.owlapi.util;

import java.io.Serializable;
import java.util.Comparator;

import org.semanticweb.owlapi.model.OWLEntity;

/** Comparator that uses IRI ordering to order entities. */
public class OWLEntityIRIComparator implements Comparator<OWLEntity>,
        Serializable {

    private static final long serialVersionUID = 30406L;

    @Override
    public int compare(OWLEntity o1, OWLEntity o2) {
        return o1.getIRI().compareTo(o2.getIRI());
    }
}
