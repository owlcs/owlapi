package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 * <p/>
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Equivalent_Classes">EquivalentClasses</a> axiom in the OWL 2 Specification.
 */
public interface OWLEquivalentClassesAxiom extends OWLNaryClassAxiom {

    /**
     * Determines if this equivalent classes axiom contains at least one
     * named class (excluding owl:Thing or owl:Nothing).
     *
     * @return <code>true</code> if the axiom contains at least one named class
     *         otherwise <code>false</code>. Note that this method will return <code>false</code>
     *         if the only named classes are owl:Thing or owl:Nothing.
     */
    boolean containsNamedEquivalentClass();

    /**
     * Gets the named classes (excluding owl:Thing and owl:Nothing) that are in this equivalent classes axiom.
     *
     * @return A set of classes that represents the named classes that are specified to be
     *         equivalent to some other class (expression), excluding the built in classes owl:Thing
     *         and owl:Nothing
     */
    Set<OWLClass> getNamedClasses();

    /**
     * Determines if this class axiom makes a class expression equivalent to nothing.
     *
     * @return <code>true</code> if this axiom contains owl:Nothing as an equivalent
     *         class.
     */
    boolean containsOWLNothing();


    /**
     * Determines if this class axiom makes a class expression equivalent to thing.
     *
     * @return <code>true</code> if this axioms contains owl:Thing as an equivalent class.
     */
    boolean containsOWLThing();

    Set<OWLEquivalentClassesAxiom> asPairwiseAxioms();

    OWLEquivalentClassesAxiom getAxiomWithoutAnnotations();
}
