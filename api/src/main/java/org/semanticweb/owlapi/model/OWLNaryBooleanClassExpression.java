package org.semanticweb.owlapi.model;

import java.util.List;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 */
public interface OWLNaryBooleanClassExpression extends OWLBooleanClassExpression {

    Set<OWLClassExpression> getOperands();

    /**
     * Gets the class expressions returned by {@link #getOperands()} as a list of class expressions.
     * @return The class expressions as a list.
     */
    List<OWLClassExpression> getOperandsAsList();
}
