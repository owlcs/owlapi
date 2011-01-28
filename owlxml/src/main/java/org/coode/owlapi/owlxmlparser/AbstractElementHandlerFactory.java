package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br><br>
 */
public abstract class AbstractElementHandlerFactory implements OWLElementHandlerFactory {

    private String elementName;

    public AbstractElementHandlerFactory(OWLXMLVocabulary v) {
        this.elementName = v.getShortName();
    }


    protected AbstractElementHandlerFactory(String elementName) {
        this.elementName = elementName;
    }


    public String getElementName() {
        return elementName;
    }


}
