package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Collections;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLPredicate;
import org.semanticweb.owlapi.model.SWRLUnaryAtom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public abstract class SWRLUnaryAtomImpl<A extends SWRLArgument> extends SWRLAtomImpl implements SWRLUnaryAtom<A> {

    private A arg;

    public SWRLUnaryAtomImpl(OWLDataFactory dataFactory, SWRLPredicate predicate, A arg) {
        super(dataFactory, predicate);
        this.arg = arg;
    }


    public A getArgument() {
        return arg;
    }


    public Collection<SWRLArgument> getAllArguments() {
        return Collections.singleton((SWRLArgument) arg);
    }
}
