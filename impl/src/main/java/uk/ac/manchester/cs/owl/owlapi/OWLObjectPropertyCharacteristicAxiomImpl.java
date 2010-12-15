package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLObjectPropertyCharacteristicAxiomImpl extends OWLPropertyAxiomImpl implements OWLObjectPropertyCharacteristicAxiom {

    private OWLObjectPropertyExpression property;


    public OWLObjectPropertyCharacteristicAxiomImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression property, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.property = property;
    }


    public OWLObjectPropertyExpression getProperty() {
        return property;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLObjectPropertyCharacteristicAxiom)) {
                return false;
            }
            return ((OWLObjectPropertyCharacteristicAxiom) obj).getProperty().equals(property);
        }
        return false;
    }


    @Override
	final protected int compareObjectOfSameType(OWLObject object) {
        return property.compareTo(((OWLObjectPropertyCharacteristicAxiom) object).getProperty());
    }
}
