package org.coode.owlapi.owlxmlparser;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectUnionOfElementHandler extends AbstractNaryBooleanClassExpressionElementHandler {

    public OWLObjectUnionOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	protected OWLClassExpression createClassExpression(Set<OWLClassExpression> operands) {
        return getOWLDataFactory().getOWLObjectUnionOf(operands);
    }
}
