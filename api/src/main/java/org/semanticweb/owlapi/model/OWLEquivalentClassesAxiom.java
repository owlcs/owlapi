/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.model;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents an <a href= "http://www.w3.org/TR/owl2-syntax/#Equivalent_Classes" >
 * EquivalentClasses</a> axiom in the OWL 2 Specification.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLEquivalentClassesAxiom extends OWLNaryClassAxiom {

    @Override
    @SuppressWarnings("unchecked")
    OWLEquivalentClassesAxiom getAxiomWithoutAnnotations();

    @Override
    default OWLObjectType type() {
        return OWLObjectType.EQUIVALENT_CLASSES;
    }

    /**
     * Determines if this equivalent classes axiom contains at least one named class (excluding
     * owl:Thing or owl:Nothing).
     *
     * @return {@code true} if the axiom contains at least one named class otherwise {@code false}.
     * Note that this method will return {@code false} if the only named classes are owl:Thing or
     * owl:Nothing.
     */
    boolean containsNamedEquivalentClass();

    /**
     * Gets the named classes (excluding owl:Thing and owl:Nothing) that are in this equivalent
     * classes axiom.
     *
     * @return A set of classes that represents the named classes that are specified to be
     * equivalent to some other class (expression), excluding the built in classes owl:Thing and
     * owl:Nothing
     * @deprecated use the stream method
     */
    @Deprecated
    default Set<OWLClass> getNamedClasses() {
        return asSet(namedClasses());
    }

    /**
     * Gets the named classes (excluding owl:Thing and owl:Nothing) that are in this equivalent
     * classes axiom.
     *
     * @return A set of classes that represents the named classes that are specified to be
     * equivalent to some other class (expression), excluding the built in classes owl:Thing and
     * owl:Nothing
     */
    Stream<OWLClass> namedClasses();

    /**
     * Determines if this class axiom makes a class expression equivalent to nothing.
     *
     * @return {@code true} if this axiom contains owl:Nothing as an equivalent class.
     */
    boolean containsOWLNothing();

    /**
     * Determines if this class axiom makes a class expression equivalent to thing.
     *
     * @return {@code true} if this axioms contains owl:Thing as an equivalent class.
     */
    boolean containsOWLThing();

    @Override
    Collection<OWLEquivalentClassesAxiom> asPairwiseAxioms();

    @Override
    Collection<OWLEquivalentClassesAxiom> splitToAnnotatedPairs();

    @Override
    default void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default AxiomType<?> getAxiomType() {
        return AxiomType.EQUIVALENT_CLASSES;
    }
}
