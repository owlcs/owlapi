package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousClassExpression;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.util.NNF;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLAnonymousClassExpressionImpl extends OWLClassExpressionImpl implements OWLAnonymousClassExpression {

    public OWLAnonymousClassExpressionImpl(OWLDataFactory dataFactory) {
        super(dataFactory);
    }


    public boolean isAnonymous() {
        return true;
    }


    public boolean isOWLThing() {
        return false;
    }


    public boolean isOWLNothing() {
        return false;
    }


    public OWLClassExpression getNNF() {
        NNF nnf = new NNF(getOWLDataFactory());
        return accept(nnf);
    }

    public OWLClassExpression getComplementNNF() {
        NNF nnf = new NNF(getOWLDataFactory());
        return getOWLDataFactory().getOWLObjectComplementOf(this).accept(nnf);
    }

    /**
     * Gets the object complement of this class expression.
     * @return A class expression that is the complement of this class expression.
     */
    public OWLClassExpression getObjectComplementOf() {
        return getOWLDataFactory().getOWLObjectComplementOf(this);
    }

    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not an OWLClass.  This method should only be called if the isAnonymous method returns false!");
    }


    public Set<OWLClassExpression> asConjunctSet() {
        return Collections.singleton((OWLClassExpression) this);
    }

    public boolean containsConjunct(OWLClassExpression ce) {
        return ce.equals(this);
    }

    public Set<OWLClassExpression> asDisjunctSet() {
        return Collections.singleton((OWLClassExpression) this);
    }
}
