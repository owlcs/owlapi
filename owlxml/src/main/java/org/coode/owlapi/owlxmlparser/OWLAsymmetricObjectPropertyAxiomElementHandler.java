package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.OWLAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public class OWLAsymmetricObjectPropertyAxiomElementHandler extends AbstractOWLObjectPropertyCharacteristicAxiomElementHandler {

    public OWLAsymmetricObjectPropertyAxiomElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    @Override
	protected OWLAxiom createPropertyCharacteristicAxiom() {
        return getOWLDataFactory().getOWLAsymmetricObjectPropertyAxiom(getProperty());
    }
}
