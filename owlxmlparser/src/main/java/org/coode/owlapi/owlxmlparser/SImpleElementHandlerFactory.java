package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20-Dec-2006<br><br>
 */
public class SImpleElementHandlerFactory extends AbstractElementHandlerFactory  {

    private OWLElementHandler handler;

    public SImpleElementHandlerFactory(OWLXMLVocabulary name, OWLElementHandler handler) {
        super(name);
        this.handler = handler;
    }


    public OWLElementHandler createHandler(OWLXMLParserHandler handler) {
        return this.handler;
    }

}
