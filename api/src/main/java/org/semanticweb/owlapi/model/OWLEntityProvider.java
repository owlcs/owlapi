package org.semanticweb.owlapi.model;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface OWLEntityProvider extends OWLClassProvider,
        OWLObjectPropertyProvider, OWLDataPropertyProvider,
        OWLNamedIndividualProvider, OWLDatatypeProvider,
        OWLAnnotationPropertyProvider {}
