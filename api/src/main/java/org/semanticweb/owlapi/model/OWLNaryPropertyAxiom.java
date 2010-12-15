package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 */
public interface OWLNaryPropertyAxiom<P extends OWLPropertyExpression> extends OWLPropertyAxiom {

    /**
     * Gets all of the properties that appear in this axiom
     */
    public Set<P> getProperties();

    Set<P> getPropertiesMinus(P property);
}
