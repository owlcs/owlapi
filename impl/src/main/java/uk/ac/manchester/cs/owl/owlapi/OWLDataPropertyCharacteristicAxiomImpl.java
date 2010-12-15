package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLDataPropertyCharacteristicAxiomImpl extends OWLPropertyAxiomImpl implements OWLDataPropertyCharacteristicAxiom {

    private OWLDataPropertyExpression property;


    public OWLDataPropertyCharacteristicAxiomImpl(OWLDataFactory dataFactory, OWLDataPropertyExpression property, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.property = property;
    }


    public OWLDataPropertyExpression getProperty() {
        return property;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDataPropertyCharacteristicAxiom)) {
                return false;
            }
            return ((OWLDataPropertyCharacteristicAxiom) obj).getProperty().equals(property);
        }
        return false;
    }
}
