package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 17-May-2009
 */
public abstract class AbstractIRIElementHandler extends AbstractOWLElementHandler<IRI> {

    public AbstractIRIElementHandler(OWLXMLParserHandler handler) {
        super(handler);
    }

}
