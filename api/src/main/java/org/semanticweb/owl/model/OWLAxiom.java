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
 * <p/>
 * An OWL ontology contains a set of axioms.  These axioms can be annotation axioms,
 * declaration axioms, imports axioms or logical axioms
 */
public interface OWLAxiom extends OWLObject, OWLAnnotationSubject {

    void accept(OWLAxiomVisitor visitor);

    <O> O accept(OWLAxiomVisitorEx<O> visitor);

    Set<OWLAnnotation> getAnnotations();

    /**
     * Determines if this axiom is a logical axiom. Logical axioms are defined to be
     * axioms other than both declaration axioms (including imports declarations) and annotation
     * axioms.
     *
     * @return <code>true</code> if the axiom is a logical axiom, <code>false</code>
     *         if the axiom is not a logical axiom.
     */
    boolean isLogicalAxiom();


    /**
     * A convenience method that obtains the entities that this axiom
     * references.
     *
     * @return A set of <code>OWLEntity</code> objects.
     */
    Set<OWLEntity> getReferencedEntities();


    /**
     * Gets the axiom type for this axiom.
     *
     * @return The axiom type that corresponds to the type of this axiom.
     */
    AxiomType getAxiomType();


    /**
     * Gets this axioms in negation normal form.  i.e. any class descriptions involved in this
     * axiom are converted into negation normal form.
     *
     * @return The axiom in negation normal form.
     */
    OWLAxiom getNNF();

}
