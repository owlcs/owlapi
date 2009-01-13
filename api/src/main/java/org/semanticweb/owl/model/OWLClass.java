package org.semanticweb.owl.model;

import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group
 * Date: 24-Oct-2006
 *
 * Represents a named class in OWL.
 */
public interface OWLClass extends OWLDescription, OWLEntity, OWLNamedObject {

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and return the descriptions corresponding to super classes of this class.
     * @param ontology The ontology to be examined
     * @return A <code>Set</code> of <code>OWLDescription</code>s that represent
     * the superclasses of this class, which have been asserted in the specified
     * ontology.
     */
    Set<OWLDescription> getSuperClasses(OWLOntology ontology);


    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the description corresponding to the asserted super classes
     * of this class.
     * @param ontologies The set of ontologies to be examined.
     * @return A set of <code>OWLDescription</code>s that represent the super classes
     * of this class
     */
    Set<OWLDescription> getSuperClasses(Set<OWLOntology> ontologies);


    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of this class
     * in the specified ontology.
     * @param ontology The ontology which should be examined for subclass axioms.
     * @return A <code>Set</code> of <code>OWLDescription</code>s that represet the
     * asserted subclasses of this class.
     */
    Set<OWLDescription> getSubClasses(OWLOntology ontology);

    /**
     * Gets the classes which have been <i>asserted</i> to be subclasses of this class
     * in the specified ontologies.
     * @param ontologies The ontologies which should be examined for subclass axioms.
     * @return A <code>Set</code> of <code>OWLDescription</code>s that represet the
     * asserted subclasses of this class.
     */
    Set<OWLDescription> getSubClasses(Set<OWLOntology> ontologies);

    /**
     * A convenience method that examines the axioms in the specified ontology
     * and returns the descriptions corresponding to equivalent classes of this
     * class.
     * @param ontology The ontology to be examined for axioms
     * @return A <code>Set</code> of <code>OWLDescription</code>s that represent
     * the equivalent classes of this class, that have been asserted in the specified
     * ontology.
     */
    Set<OWLDescription> getEquivalentClasses(OWLOntology ontology);

    /**
     * A convenience method that examines the axioms in the specified ontologies
     * and returns the descriptions corresponding to equivalent classes of this
     * class.
     * @param ontologies The ontologies to be examined for axioms
     * @return A <code>Set</code> of <code>OWLDescription</code>s that represent
     * the equivalent classes of this class, that have been asserted in the specified
     * ontologies.
     */
    Set<OWLDescription> getEquivalentClasses(Set<OWLOntology> ontologies);

    /**
     * Gets the classes which have been asserted to be disjoint with this class by
     * axioms in the specified ontology.
     * @param ontology The ontology to search for disjoint class axioms
     * @return A <code>Set</code> of <code>OWLDescription</code>s that represent
     * the disjoint classes of this class.
     */
    Set<OWLDescription> getDisjointClasses(OWLOntology ontology);

    /**
     * Gets the classes which have been asserted to be disjoint with this class by
     * axioms in the specified ontologies.
     * @param ontologies The ontologies to search for disjoint class axioms
     * @return A <code>Set</code> of <code>OWLDescription</code>s that represent
     * the disjoint classes of this class.
     */
    Set<OWLDescription> getDisjointClasses(Set<OWLOntology> ontologies);


    /**
     * Gets the individuals that have been asserted to be an instance of this
     * class by axioms in the speficied ontology.
     * @param ontology The ontology to be examined for class assertion axioms that
     * assert an individual to be an instance of this class.
     * @return A <code>Set</code> of <code>OWLIndividual</code>s that represent
     * the individual that have been asserted to be an instance of this class.
     */
    Set<OWLIndividual> getIndividuals(OWLOntology ontology);


    /**
     * Gets the individuals that have been asserted to be an instance of this
     * class by axioms in the speficied ontologies.
     * @param ontologies The ontologies to be examined for class assertion axioms that
     * assert an individual to be an instance of this class.
     * @return A <code>Set</code> of <code>OWLIndividual</code>s that represent
     * the individual that have been asserted to be an instance of this class.
     */
    Set<OWLIndividual> getIndividuals(Set<OWLOntology> ontologies);

    /**
     * Determines if this class has at least one equivalent class in the specified
     * ontology.
     * @param ontology The ontology to examine for axioms.
     */
    boolean isDefined(OWLOntology ontology);


    /**
     * Determines if this class has at least one equvialent class specified
     * by an equvialent classes axiom in one of the ontologies.
     * @param ontologies The ontologies to be examined for equivalent classes axioms
     */
    boolean isDefined(Set<OWLOntology> ontologies);
    
}
