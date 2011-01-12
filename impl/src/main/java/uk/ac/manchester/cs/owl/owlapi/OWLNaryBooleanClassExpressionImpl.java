package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNaryBooleanClassExpression;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLNaryBooleanClassExpressionImpl extends OWLAnonymousClassExpressionImpl implements OWLNaryBooleanClassExpression {

    private Set<OWLClassExpression> operands;


    public OWLNaryBooleanClassExpressionImpl(OWLDataFactory dataFactory, Set<? extends OWLClassExpression> operands) {
        super(dataFactory);
        this.operands = new TreeSet<OWLClassExpression>(operands);
    }

    public List<OWLClassExpression> getOperandsAsList() {
        return new ArrayList<OWLClassExpression>(operands);
    }

    public Set<OWLClassExpression> getOperands() {
        return CollectionFactory.getCopyOnRequestSet(operands);
    }


    public boolean isClassExpressionLiteral() {
        return false;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLNaryBooleanClassExpression)) {
                return false;
            }
            return ((OWLNaryBooleanClassExpression) obj).getOperands().equals(operands);
        }
        return false;
    }


    @Override
	final protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(operands, ((OWLNaryBooleanClassExpression) object).getOperands());
    }
}
