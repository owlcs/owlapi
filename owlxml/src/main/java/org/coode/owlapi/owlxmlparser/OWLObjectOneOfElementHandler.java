package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLIndividual;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLObjectOneOfElementHandler extends AbstractClassExpressionElementHandler {

    private Set<OWLIndividual> individuals;

    public OWLObjectOneOfElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        individuals = new HashSet<OWLIndividual>();
    }


    @Override
	public void handleChild(OWLIndividualElementHandler handler) {
        individuals.add(handler.getOWLObject());
    }


    @Override
	protected void endClassExpressionElement() throws OWLXMLParserException {
        if (individuals.size() < 1) {
            throw new OWLXMLParserElementNotFoundException(getLineNumber(), getColumnNumber(), "Expected at least one individual in object oneOf");
        }
        setClassExpression(getOWLDataFactory().getOWLObjectOneOf(individuals));
    }
}
