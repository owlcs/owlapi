package uk.ac.manchester.cs.owl.owlapi;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRangeVisitor;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDatatypeRestrictionImpl extends OWLObjectImpl implements OWLDatatypeRestriction {

    private OWLDatatype datatype;

    private Set<OWLFacetRestriction> facetRestrictions;


    public OWLDatatypeRestrictionImpl(OWLDataFactory dataFactory, OWLDatatype datatype, Set<OWLFacetRestriction> facetRestrictions) {
        super(dataFactory);
        this.datatype = datatype;
        this.facetRestrictions = new HashSet<OWLFacetRestriction>(facetRestrictions);
    }

    public DataRangeType getDataRangeType() {
        return DataRangeType.DATATYPE_RESTRICTION;
    }

    public boolean isDatatype() {
        return false;
    }


    public boolean isTopDatatype() {
        return false;
    }


    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not a data type!");
    }


    public OWLDatatype getDatatype() {
        return datatype;
    }


    /**
     * Gets the facet restrictions on this data range
     * @return A <code>Set</code> of facet restrictions that apply to
     *         this data range
     */
    public Set<OWLFacetRestriction> getFacetRestrictions() {
        return CollectionFactory.getCopyOnRequestSet(facetRestrictions);
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDatatypeRestriction)) {
                return false;
            }
            OWLDatatypeRestriction other = (OWLDatatypeRestriction) obj;
            return other.getDatatype().equals(datatype) && other.getFacetRestrictions().equals(facetRestrictions);
        }
        return false;
    }


    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLDataRangeVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLDataRangeVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLDatatypeRestriction other = (OWLDatatypeRestriction) object;
        int diff = datatype.compareTo(other.getDatatype());
        if (diff != 0) {
            return diff;
        }
        return compareSets(facetRestrictions, other.getFacetRestrictions());
    }
}
