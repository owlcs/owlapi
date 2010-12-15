package org.coode.owlapi.owlxmlparser;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03-Apr-2007<br><br>
 */
public class OWLSubObjectPropertyChainElementHandler extends AbstractOWLElementHandler<List<OWLObjectPropertyExpression>> {

    private List<OWLObjectPropertyExpression> propertyList;


    public OWLSubObjectPropertyChainElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        propertyList = new ArrayList<OWLObjectPropertyExpression>();
    }


    public void endElement() throws OWLParserException, UnloadableImportException {
        getParentHandler().handleChild(this);
    }


    public List<OWLObjectPropertyExpression> getOWLObject() {
        return propertyList;
    }


    @Override
	public void handleChild(AbstractOWLObjectPropertyElementHandler handler) {
        propertyList.add(handler.getOWLObject());
    }
}
