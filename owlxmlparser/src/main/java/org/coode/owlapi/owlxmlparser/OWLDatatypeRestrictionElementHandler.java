package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Apr-2007<br><br>
 */
public class OWLDatatypeRestrictionElementHandler extends AbstractOWLDataRangeHandler {

    private OWLDatatype restrictedDataRange;

    private Set<OWLFacetRestriction> facetRestrictions;

    public OWLDatatypeRestrictionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        facetRestrictions = new HashSet<OWLFacetRestriction>();
    }


    @Override
	protected void endDataRangeElement() {
        setDataRange(getOWLDataFactory().getOWLDatatypeRestriction(restrictedDataRange, facetRestrictions));
    }


    @Override
	public void handleChild(AbstractOWLDataRangeHandler handler) {
        OWLDataRange dr = handler.getOWLObject();
        if (dr.isDatatype()) {
            restrictedDataRange = dr.asOWLDatatype();
        }
    }


    @Override
	public void handleChild(OWLDatatypeFacetRestrictionElementHandler handler) {
        facetRestrictions.add(handler.getOWLObject());
    }
}
