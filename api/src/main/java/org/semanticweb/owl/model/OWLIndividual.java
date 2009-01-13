package org.semanticweb.owl.model;

import java.util.Map;
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
 * Bio-Health Informatics Group<br>
 * Date: 25-Oct-2006<br><br>
 * <p/>
 * Represents a named or anonymous individual.
 */
public interface OWLIndividual extends OWLEntity, OWLNamedObject {

    /**
     * Determines if this object represents an anonymous individual.
     * @return <code>true</code> if this object represents an anonymous
     *         individual (<code>OWLAnonymousIndividual)</code> or <code>false</code>
     *         if this object represents a named individual (<code>OWLIndividual</code>)
     */
    boolean isAnonymous();


    /**
     * A convenience method, which gets the types of this individual, that
     * correspond to the types asserted with axioms in the specified ontology.
     * @param ontology The ontology that should be examined for class assertion
     * axioms in order to get the types for this individual.
     * @return A set of descriptions that correspond the asserted types of this
     * individual in the specified ontology.
     */
    public Set<OWLDescription> getTypes(OWLOntology ontology);


    /**
     * A convenience method that gets the types of this individual by
     * examining the specified ontologies.
     * @param ontologies The ontologies to be examined for class assertions
     * @return A set of descriptions that represent the types of this
     * individual as asserted in the specified ontologies.
     */
    public Set<OWLDescription> getTypes(Set<OWLOntology> ontologies);


    /**
     * Gets the object property values for this individual.
     * @return A map, which maps object properties to sets of individuals.
     */
    public Map<OWLObjectPropertyExpression, Set<OWLIndividual>> getObjectPropertyValues(OWLOntology ontology);


    /**
     * Gets the object property values that are explicitly asserted NOT to hold
     * for this individual
     * @param ontology The ontology that should be examined for axioms
     * @return A map containing the negative object property values
     */
    public Map<OWLObjectPropertyExpression, Set<OWLIndividual>> getNegativeObjectPropertyValues(OWLOntology ontology);

    /**
     * Gets the data property values for this individual
     */
    public Map<OWLDataPropertyExpression, Set<OWLConstant>> getDataPropertyValues(OWLOntology ontology);

    /**
     * Gets the data property values that are explicitly asserted NOT to hold
     * for this individual
     * @param ontology The ontology that should be examined for axioms
     * @return A map containing the negative data property values
     */
    public Map<OWLDataPropertyExpression, Set<OWLConstant>> getNegativeDataPropertyValues(OWLOntology ontology);



    /**
     * A convenience method that examines axioms in the specified ontology
     * to determine the individuals that are asserted to be the same as
     * this individual.
     * @return Individuals that have been asserted to be the same as this individual.
     */
    Set<OWLIndividual> getSameIndividuals(OWLOntology ontology);


    /**
     * A convenience method that examines axioms in the specified ontology
     * to determine the individuals that are asserted to be different
     * to this individual.
     * @param ontology
     * @return
     */
    Set<OWLIndividual> getDifferentIndividuals(OWLOntology ontology);
}
