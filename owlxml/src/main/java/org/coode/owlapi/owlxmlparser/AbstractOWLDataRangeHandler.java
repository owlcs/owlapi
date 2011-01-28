package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public abstract class AbstractOWLDataRangeHandler extends AbstractOWLElementHandler<OWLDataRange> {

    private OWLDataRange dataRange;

    protected AbstractOWLDataRangeHandler(OWLXMLParserHandler handler) {
        super(handler);
    }


    public void setDataRange(OWLDataRange dataRange) {
        this.dataRange = dataRange;
    }


    final public OWLDataRange getOWLObject() {
        return dataRange;
    }


    final public void endElement() throws OWLParserException, UnloadableImportException {
        endDataRangeElement();
        getParentHandler().handleChild(this);
    }

    protected abstract void endDataRangeElement() throws OWLXMLParserException;

}
