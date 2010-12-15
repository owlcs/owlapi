package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLPropertyDomainAxiomImpl<P extends OWLPropertyExpression> extends OWLUnaryPropertyAxiomImpl<P> implements OWLPropertyDomainAxiom<P> {

    private OWLClassExpression domain;


    public OWLPropertyDomainAxiomImpl(OWLDataFactory dataFactory, P property, OWLClassExpression domain, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, property, annotations);
        this.domain = domain;
    }


    public OWLClassExpression getDomain() {
        return domain;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLPropertyDomainAxiom)) {
                return false;
            }
            return ((OWLPropertyDomainAxiom) obj).getDomain().equals(domain);
        }
        return false;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLPropertyDomainAxiom other = (OWLPropertyDomainAxiom) object;
        int diff = getProperty().compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return domain.compareTo(other.getDomain());
    }
}
