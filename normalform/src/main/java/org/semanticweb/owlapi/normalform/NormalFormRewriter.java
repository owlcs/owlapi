package org.semanticweb.owlapi.normalform;

import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Sep-2007<br><br>
 */
public interface NormalFormRewriter {

    boolean isInNormalForm(OWLClassExpression classExpression);

    OWLClassExpression convertToNormalForm(OWLClassExpression classExpression);
}
