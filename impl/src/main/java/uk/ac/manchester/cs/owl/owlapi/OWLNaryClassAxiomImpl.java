package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNaryClassAxiom;
import org.semanticweb.owlapi.model.OWLObject;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLNaryClassAxiomImpl extends OWLClassAxiomImpl implements OWLNaryClassAxiom {

    private Set<OWLClassExpression> classExpressions;


    public OWLNaryClassAxiomImpl(OWLDataFactory dataFactory, Set<? extends OWLClassExpression> classExpressions, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.classExpressions = Collections.unmodifiableSortedSet(new TreeSet<OWLClassExpression>(classExpressions));
    }


    public Set<OWLClassExpression> getClassExpressions() {
        return classExpressions;
    }

    public List<OWLClassExpression> getClassExpressionsAsList() {
        return new ArrayList<OWLClassExpression>(classExpressions);
    }

    public boolean contains(OWLClassExpression ce) {
        return classExpressions.contains(ce);
    }


    public Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... descs) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>(classExpressions);
        for (OWLClassExpression desc : descs) {
            result.remove(desc);
        }
        return result;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLNaryClassAxiom)) {
                return false;
            }
            return ((OWLNaryClassAxiom) obj).getClassExpressions().equals(classExpressions);
        }
        return false;
    }


    protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(classExpressions, ((OWLNaryClassAxiom) object).getClassExpressions());
    }
}
