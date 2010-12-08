package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2007<br><br>
 * An interface for customisation of entity URI conversions.
 */
public interface OWLEntityURIConverterStrategy {

    IRI getConvertedIRI(OWLEntity entity);
}
