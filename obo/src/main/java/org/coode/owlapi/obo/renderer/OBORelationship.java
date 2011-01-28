package org.coode.owlapi.obo.renderer;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Nick Drummond<br>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 18, 2008<br><br>
 */
public class OBORelationship {

    private OWLObjectProperty property;

    private int minCardinality = -1;
    private int maxCardinality = -1;
    private int cardinality = -1;

    private OWLEntity filler;

    public OBORelationship(OWLObjectProperty property, OWLNamedIndividual filler) {
        this.property = property;
        this.filler = filler;
    }

    public OBORelationship(OWLObjectProperty property, OWLClass filler) {
        this.property = property;
        this.filler = filler;
    }

    public OWLObjectProperty getProperty() {
        return property;
    }


    public int getMinCardinality() {
        return minCardinality;
    }


    public OWLEntity getFiller() {
        return filler;
    }


    public int getMaxCardinality() {
        return maxCardinality;
    }


    public void setMaxCardinality(int maxCardinality) {
        this.maxCardinality = maxCardinality;
    }


    public void setMinCardinality(int minCardinality) {
        this.minCardinality = minCardinality;
    }


    public int getCardinality() {
        return cardinality;
    }


    public void setCardinality(int cardinality) {
        this.cardinality = cardinality;
    }
}
