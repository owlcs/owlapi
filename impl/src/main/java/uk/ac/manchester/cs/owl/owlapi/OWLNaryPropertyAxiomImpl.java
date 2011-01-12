package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNaryPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLNaryPropertyAxiomImpl<P extends OWLPropertyExpression> extends OWLPropertyAxiomImpl implements OWLNaryPropertyAxiom<P> {

    private Set<P> properties;


    public OWLNaryPropertyAxiomImpl(OWLDataFactory dataFactory, Set<? extends P> properties, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.properties = new TreeSet<P>(properties);
    }


    public Set<P> getProperties() {
        return CollectionFactory.getCopyOnRequestSet(properties);
    }

    public Set<P> getPropertiesMinus(P property) {
        Set<P> props = new TreeSet<P>(properties);
        props.remove(property);
        return props;
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLNaryPropertyAxiom)) {
                return false;
            }
            return ((OWLNaryPropertyAxiom<?>) obj).getProperties().equals(properties);
        }
        return false;
    }


    @Override
	final protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(properties, ((OWLNaryPropertyAxiom<?>) object).getProperties());
    }
}
