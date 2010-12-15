package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLBinaryAtom;
import org.semanticweb.owlapi.model.SWRLPredicate;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public abstract class SWRLBinaryAtomImpl<A extends SWRLArgument, B extends SWRLArgument> extends SWRLAtomImpl implements SWRLBinaryAtom<A, B> {

    private A arg0;

    private B arg1;

    protected SWRLBinaryAtomImpl(OWLDataFactory dataFactory, SWRLPredicate predicate, A arg0, B arg1) {
        super(dataFactory, predicate);
        this.arg0 = arg0;
        this.arg1 = arg1;
    }


    public Collection<SWRLArgument> getAllArguments() {
        List<SWRLArgument> objs = new ArrayList<SWRLArgument>();
        objs.add(arg0);
        objs.add(arg1);
        return objs;
    }


    public A getFirstArgument() {
        return arg0;
    }


    public B getSecondArgument() {
        return arg1;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        SWRLBinaryAtom other = (SWRLBinaryAtom) object;
        int diff = ((OWLObject) getPredicate()).compareTo((OWLObject) other.getPredicate());
        if (diff != 0) {
            return diff;
        }
        diff = arg0.compareTo(other.getFirstArgument());
        if (diff != 0) {
            return diff;
        }
        return arg1.compareTo(other.getSecondArgument());
    }
}
