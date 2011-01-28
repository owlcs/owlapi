package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 07-Sep-2008<br><br>
 */
public class OWLAsymmetricObjectPropertyElementHandler extends AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    public OWLAsymmetricObjectPropertyElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

    @Override
	protected OWLAxiom createPropertyCharacteristicAxiom() {
        return getOWLDataFactory().getOWLAsymmetricObjectPropertyAxiom(getProperty(), getAnnotations());
    }
}
