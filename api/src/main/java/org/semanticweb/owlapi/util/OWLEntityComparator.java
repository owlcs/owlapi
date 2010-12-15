package org.semanticweb.owlapi.util;

import java.util.Comparator;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jun-2007<br><br>
 * <p/>
 * A <code>Comparator</code> which compares entities.  Entities are compared first
 * by their type (in the following order: Class, Object property, Data property, Individual,
 * Datatype) then by their short form (using the specified short form provider).
 */
@SuppressWarnings("unused")
public class OWLEntityComparator implements Comparator<OWLEntity>, OWLEntityVisitor {

    int lastValue;

    public static final int OWL_CLASS_INDEX = 0;

    public static final int OWL_OBJECT_PROPERTY_INDEX = 1;

    public static final int OWL_DATA_PROPERTY_INDEX = 2;

    public static final int OWL_INDIVIDUAL_INDEX = 3;

    public static final int OWL_DATATYPE_INDEX = 4;

    private ShortFormProvider shortFormProvider;


    /**
     * Constructs an entity comparator which uses the specified short form
     * provider
     */
    public OWLEntityComparator(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }


    public int compare(OWLEntity o1, OWLEntity o2) {
        o1.accept(this);
        int i1 = lastValue;
        o2.accept(this);
        int i2 = lastValue;
        int delta = i1 - i2;
        if (delta != 0) {
            return delta;
        }
        String s1 = getShortForm(o1);
        String s2 = getShortForm(o2);
        return s1.compareTo(s2);
    }


    private String getShortForm(OWLEntity entity) {
        return shortFormProvider.getShortForm(entity);
    }


    public void visit(OWLClass cls) {
        lastValue = 0;
    }


    public void visit(OWLObjectProperty property) {
        lastValue = 1;
    }


    public void visit(OWLDataProperty property) {
        lastValue = 2;
    }


    public void visit(OWLNamedIndividual individual) {
        lastValue = 3;
    }

    public void visit(OWLAnnotationProperty property) {
        lastValue = 4;
    }

    public void visit(OWLDatatype datatype) {
        lastValue = 5;
    }
}
