package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRangeVisitor;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDataComplementOfImpl extends OWLObjectImpl implements OWLDataComplementOf {

    private OWLDataRange dataRange;


    public OWLDataComplementOfImpl(OWLDataFactory dataFactory, OWLDataRange dataRange) {
        super(dataFactory);
        this.dataRange = dataRange;
    }

    public DataRangeType getDataRangeType() {
        return DataRangeType.DATA_COMPLEMENT_OF;
    }

    public boolean isDatatype() {
        return false;
    }


    public boolean isTopDatatype() {
        return false;
    }


    public OWLDataRange getDataRange() {
        return dataRange;
    }


    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not a data type!");
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDataComplementOf)) {
                return false;
            }
            return ((OWLDataComplementOf) obj).getDataRange().equals(dataRange);
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
        OWLDataComplementOf other = (OWLDataComplementOf) object;
        return getDataRange().compareTo(other.getDataRange());
    }
}
