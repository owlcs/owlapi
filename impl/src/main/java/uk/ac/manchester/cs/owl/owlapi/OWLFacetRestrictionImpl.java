package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.vocab.OWLFacet;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Jan-2007<br><br>
 */
public class OWLFacetRestrictionImpl extends OWLObjectImpl implements OWLFacetRestriction {

    private OWLFacet facet;

    private OWLLiteral facetValue;


    public OWLFacetRestrictionImpl(OWLDataFactory dataFactory, OWLFacet facet, OWLLiteral facetValue) {
        super(dataFactory);
        this.facet = facet;
        this.facetValue = facetValue;
    }


    /**
     * Gets the restricting facet for this facet restriction
     */
    public OWLFacet getFacet() {
        return facet;
    }


    /**
     * Gets the corresponding facet value for this facet restriction
     */
    public OWLLiteral getFacetValue() {
        return facetValue;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLFacetRestriction other = (OWLFacetRestriction) object;
        int diff = facet.compareTo(other.getFacet());
        if (diff != 0) {
            return diff;
        }
        return facetValue.compareTo(other.getFacetValue());
    }
}
