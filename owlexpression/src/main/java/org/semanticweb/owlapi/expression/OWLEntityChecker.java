package org.semanticweb.owlapi.expression;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Sep-2007<br><br>
 */
public interface OWLEntityChecker {

    OWLClass getOWLClass(String name);

    OWLObjectProperty getOWLObjectProperty(String name);

    OWLDataProperty getOWLDataProperty(String name);

    OWLNamedIndividual getOWLIndividual(String name);

    OWLDatatype getOWLDatatype(String name);

    OWLAnnotationProperty getOWLAnnotationProperty(String name);
}
