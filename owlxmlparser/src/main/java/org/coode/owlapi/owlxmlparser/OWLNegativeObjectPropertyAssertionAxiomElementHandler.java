package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLNegativeObjectPropertyAssertionAxiomElementHandler extends AbstractOWLObjectPropertyAssertionAxiomElementHandler {

    public OWLNegativeObjectPropertyAssertionAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	protected OWLAxiom createAxiom() throws OWLXMLParserException {
        return getOWLDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(getProperty(), getSubject(), getObject(), getAnnotations());
    }
}
