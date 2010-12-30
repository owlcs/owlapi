package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLUnaryPropertyAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLUnaryPropertyAxiomImpl<P extends OWLPropertyExpression> extends OWLPropertyAxiomImpl implements OWLUnaryPropertyAxiom<P> {

    private P property;


    public OWLUnaryPropertyAxiomImpl(OWLDataFactory dataFactory, P property, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.property = property;
    }


    public P getProperty() {
        return property;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLUnaryPropertyAxiom)) {
                return false;
            }
            return ((OWLUnaryPropertyAxiom<?>) obj).getProperty().equals(property);
        }
        return false;
    }
}
