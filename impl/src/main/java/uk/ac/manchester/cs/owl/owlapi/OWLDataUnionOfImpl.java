package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRangeVisitor;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 17-Jan-2009
 */
public class OWLDataUnionOfImpl extends OWLNaryDataRangeImpl implements OWLDataUnionOf {

    public OWLDataUnionOfImpl(OWLDataFactory dataFactory, Set<? extends OWLDataRange> operands) {
        super(dataFactory, operands);
    }

    public DataRangeType getDataRangeType() {
        return DataRangeType.DATA_UNION_OF;
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLDataUnionOf other = (OWLDataUnionOf) object;
        return compareSets(getOperands(), other.getOperands());
    }

    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLDataUnionOf)) {
            return false;
        }
        OWLDataUnionOf other = (OWLDataUnionOf) obj;
        return this.getOperands().equals(other.getOperands());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLDataVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLDataVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLDataRangeVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLDataRangeVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
