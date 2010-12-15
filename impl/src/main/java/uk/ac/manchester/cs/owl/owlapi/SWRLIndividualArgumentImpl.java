package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLIndividualArgumentImpl extends OWLObjectImpl implements SWRLIndividualArgument {

    private OWLIndividual individual;


    public SWRLIndividualArgumentImpl(OWLDataFactory dataFactory, OWLIndividual individual) {
        super(dataFactory);
        this.individual = individual;
    }


    public OWLIndividual getIndividual() {
        return individual;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(SWRLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SWRLIndividualArgument)) {
            return false;
        }
        SWRLIndividualArgument other = (SWRLIndividualArgument) obj;
        return other.getIndividual().equals(getIndividual());
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return individual.compareTo(((SWRLIndividualArgument) object).getIndividual());
    }
}
