package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNaryDataRange;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 17-Jan-2009
 */
public abstract class OWLNaryDataRangeImpl extends OWLObjectImpl implements OWLNaryDataRange {

    private Set<OWLDataRange> operands;

    protected OWLNaryDataRangeImpl(OWLDataFactory dataFactory, Set<? extends OWLDataRange> operands) {
        super(dataFactory);
        this.operands = Collections.unmodifiableSet(new TreeSet<OWLDataRange>(operands));
    }

    public Set<OWLDataRange> getOperands() {
        return operands;
    }

    public boolean isTopDatatype() {
        return false;
    }

    public boolean isDatatype() {
        return false;
    }

    public OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not a datatype");
    }
}
