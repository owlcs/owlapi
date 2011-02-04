package org.coode.owlapi.obo.parser;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03/02/2011
 */
public class XRefTagHandler extends AbstractTagValueHandler {

    public XRefTagHandler(OBOConsumer consumer) {
        super(OBOVocabulary.XREF.getName(), consumer);
    }

    public void handle(String id, String value, String comment) {
    }

    
}
