package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * Represents a <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Classes">Class</a> in the OWL 2 specification.
 */
public interface OWLClass extends OWLClassExpression, OWLLogicalEntity, OWLNamedObject {

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and return the class expressions corresponding to super classes of this class.
     * @param ontology The ontology to be examined
     * @return A <code>Set</code> of <code>OWLClassExpression</code>s that represent
     *         the superclasses of this class, which have been asserted in the specified
     *         ontology.
     */
    Set<OWLClassExpression> getSuperClasses(OWLOntology ontology);

    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the class expression corresponding to the asserted super classes
     * of this class.
     * @param ontologies The set of ontologies to be examined.
     * @return A set of <code>OWLClassExpressions</code>s that represent the super classes
     *         of this class
     */
    Set<OWLClassExpression> getSuperClasses(Set<OWLOntology> ontologies);


    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of this class
     * in the specified ontology.
     * @param ontology The ontology which should be examined for subclass axioms.
     * @return A <code>Set</code> of <code>OWLClassExpression</code>s that represet the
     *         asserted subclasses of this class.
     */
    Set<OWLClassExpression> getSubClasses(OWLOntology ontology);

    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of this class
     * in the specified ontologies.
     * @param ontologies The ontologies which should be examined for subclass axioms.
     * @return A <code>Set</code> of <code>OWLClassExpression</code>s that represet the
     *         asserted subclasses of this class.
     */
    Set<OWLClassExpression> getSubClasses(Set<OWLOntology> ontologies);

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and returns the class expressions corresponding to equivalent classes of this
     * class.
     * @param ontology The ontology to be examined for axioms
     * @return A <code>Set</code> of <code>OWLClassExpression</code>s that represent
     *         the equivalent classes of this class, that have been asserted in the specified
     *         ontology.
     */
    Set<OWLClassExpression> getEquivalentClasses(OWLOntology ontology);

    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the class expressions corresponding to equivalent classes of this
     * class.
     * @param ontologies The ontologies to be examined for axioms
     * @return A <code>Set</code> of <code>OWLClassExpression</code>s that represent
     *         the equivalent classes of this class, that have been asserted in the specified
     *         ontologies.
     */
    Set<OWLClassExpression> getEquivalentClasses(Set<OWLOntology> ontologies);

    /**
     * Gets the classes which have been asserted to be disjoint with this class by
     * axioms in the specified ontology.
     * @param ontology The ontology to search for disjoint class axioms
     * @return A <code>Set</code> of <code>OWLClassExpression</code>s that represent
     *         the disjoint classes of this class.
     */
    Set<OWLClassExpression> getDisjointClasses(OWLOntology ontology);

    /**
     * Gets the classes which have been asserted to be disjoint with this class by
     * axioms in the specified ontologies.
     * @param ontologies The ontologies to search for disjoint class axioms
     * @return A <code>Set</code> of <code>OWLClassExpression</code>s that represent
     *         the disjoint classes of this class.
     */
    Set<OWLClassExpression> getDisjointClasses(Set<OWLOntology> ontologies);


    /**
     * Gets the individuals that have been asserted to be an instance of this
     * class by axioms in the specified ontology.
     * @param ontology The ontology to be examined for class assertion axioms that
     * assert an individual to be an instance of this class.
     * @return A <code>Set</code> of <code>OWLIndividual</code>s that represent
     *         the individual that have been asserted to be an instance of this class.
     */
    Set<OWLIndividual> getIndividuals(OWLOntology ontology);


    /**
     * Gets the individuals that have been asserted to be an instance of this
     * class by axioms in the speficied ontologies.
     * @param ontologies The ontologies to be examined for class assertion axioms that
     * assert an individual to be an instance of this class.
     * @return A <code>Set</code> of <code>OWLIndividual</code>s that represent
     *         the individual that have been asserted to be an instance of this class.
     */
    Set<OWLIndividual> getIndividuals(Set<OWLOntology> ontologies);

    /**
     * Determines if this class is a top level class in an {@link org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom}
     * in the specified ontology.
     * @param ontology The ontology to examine for axioms.
     * @return <code>true</code> if <code>ontology</code> contains an <code>EquivalentClassesAxiom</code> where this
     *         class is a top level class in the axiom, other wise <code>false</code>.
     */
    boolean isDefined(OWLOntology ontology);


    /**
     * Determines if this class is a top level class in an {@link org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom}
     * in at least one of the specified ontologies.
     * @param ontologies The ontologies to examine for axioms.
     * @return <code>true</code> if one or more of <code>ontologies</code> contains an <code>EquivalentClassesAxiom</code> where this
     *         class is a top level class in the axiom, other wise <code>false</code>.
     */
    boolean isDefined(Set<OWLOntology> ontologies);

}
