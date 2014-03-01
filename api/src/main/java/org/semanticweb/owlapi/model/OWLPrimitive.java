package org.semanticweb.owlapi.model;

/**
 * A common interface for: {@link org.semanticweb.owlapi.model.OWLClass},
 * {@link org.semanticweb.owlapi.model.OWLObjectProperty},
 * {@link org.semanticweb.owlapi.model.OWLDataProperty},
 * {@link org.semanticweb.owlapi.model.OWLAnnotationProperty},
 * {@link org.semanticweb.owlapi.model.OWLDatatype},
 * {@link org.semanticweb.owlapi.model.OWLAnonymousIndividual},
 * {@link org.semanticweb.owlapi.model.OWLLiteral},
 * {@link org.semanticweb.owlapi.model.IRI}. i.e. the basic "leaf" objects that
 * are used in axioms, class expressions an annotations.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface OWLPrimitive extends OWLObject {}
