package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLPredicate;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public abstract class SWRLAtomImpl extends OWLObjectImpl implements SWRLAtom {

    SWRLPredicate predicate;

    protected SWRLAtomImpl(OWLDataFactory dataFactory, SWRLPredicate predicate) {
        super(dataFactory);
        this.predicate = predicate;
    }


    public SWRLPredicate getPredicate() {
        return predicate;
    }

}
