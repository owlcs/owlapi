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
package org.semanticweb.owlapi.model.providers;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * An interface to a factory that can create SWRL objects.
 *
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics Group
 * @since 2.0.0
 */
public interface SWRLProvider {

    /**
     * Gets an anonymous SWRL Rule.
     *
     * @param body The atoms that make up the body
     * @param head The atoms that make up the head
     * @return An anonymous rule with the specified body and head
     */
    SWRLRule getSWRLRule(Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head);

    /**
     * Gets an anonymous SWRL Rule.
     *
     * @param body The atoms that make up the body
     * @param head The atoms that make up the head
     * @param annotations The annotations for the rule (may be an empty set)
     * @return An anonymous rule with the specified body and head
     */
    SWRLRule getSWRLRule(Collection<? extends SWRLAtom> body, Collection<? extends SWRLAtom> head,
        Collection<OWLAnnotation> annotations);

    /**
     * Gets a SWRL atom where the predicate is a class expression i.e. C(x) where C is a class
     * expression and x is either an individual or a variable for an individual
     *
     * @param predicate The class expression that represents the predicate of the atom
     * @param arg The argument (x)
     * @return The class atom with the specified class expression predicate and the specified
     * argument.
     */
    SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIArgument arg);

    /**
     * Gets a SWRL atom where the predicate is a data range, i.e. D(x) where D is an OWL data range
     * and x is either a literal or variable for a literal
     *
     * @param predicate The data range that represents the predicate of the atom
     * @param arg The argument (x)
     * @return An atom with the specified data range predicate and the specified argument
     */
    SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange predicate, SWRLDArgument arg);

    /**
     * Gets a SWRL atom where the predicate is a data range, i.e. D(x) where D is an OWL data range
     * and x is either a literal or variable for a literal
     *
     * @param predicate The data range that represents the predicate of the atom
     * @param arg The argument (x)
     * @return An atom with the specified data range predicate and the specified argument
     */
    SWRLDataRangeAtom getSWRLDataRangeAtom(OWL2Datatype predicate, SWRLDArgument arg);

    /**
     * Gets a SWRL object property atom, i.e. P(x, y) where P is an OWL object property (expression)
     * and x and y are are either individuals or variables for individuals.
     *
     * @param property The property (P) representing the atom predicate
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     * @return A SWRLObjectPropertyAtom that has the specified predicate and the specified arguments
     */
    SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property,
        SWRLIArgument arg0, SWRLIArgument arg1);

    /**
     * Gets a SWRL data property atom, i.e. R(x, y) where R is an OWL data property (expression) and
     * x and y are are either literals or variables for literals
     *
     * @param property The property (P) that represents the atom predicate
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     * @return A SWRLDataPropertyAtom that has the specified predicate and the specified arguments
     */
    SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property,
        SWRLIArgument arg0, SWRLDArgument arg1);

    /**
     * Creates a SWRL Built-In atom. Builtins have predicates that are identified by IRIs. SWRL
     * provides a core set of builtins, which are described in the OWL API by the
     * {@link org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary}.
     *
     * @param builtInIRI The builtin predicate IRI
     * @param args A non-empty set of SWRL Arguments.
     * @return A SWRLBuiltInAtom whose predicate is identified by the specified builtInIRI and that
     * has the specified arguments
     * @throws IllegalArgumentException if the list of arguments is empty
     */
    SWRLBuiltInAtom getSWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args);

    /**
     * Gets a SWRLVariable.
     *
     * @param var The id (IRI) of the variable
     * @return A SWRLVariable that has the name specified by the IRI
     */
    SWRLVariable getSWRLVariable(IRI var);

    /**
     * Gets a SWRLVariable.
     *
     * @param iri The IRI of the variable
     * @return A SWRLVariable that has the name specified by the IRI
     */
    default SWRLVariable getSWRLVariable(String iri) {
        return getSWRLVariable(IRI.create(iri));
    }

    /**
     * Gets a SWRLVariable.
     *
     * @param namespace The IRI namespace
     * @param remainder optional remainder or local name
     * @return A SWRLVariable that has the name specified by the IRI
     */
    default SWRLVariable getSWRLVariable(String namespace, @Nullable String remainder) {
        return getSWRLVariable(IRI.create(namespace, remainder));
    }

    /**
     * Gets a SWRLVariable with the same IRI as the input.
     *
     * @param var an HasIRI instance. The variable will have the same IRI
     * @return A SWRLVariable that has the name specified by the IRI
     */
    default SWRLVariable getSWRLVariable(HasIRI var) {
        return getSWRLVariable(var.getIRI());
    }

    /**
     * Gets a SWRLIndividualArgument, which is used to wrap and OWLIndividual as an argument for an
     * atom.
     *
     * @param individual The individual that is the object argument
     * @return A SWRLIndividualArgument that wraps the specified individual
     */
    SWRLIndividualArgument getSWRLIndividualArgument(OWLIndividual individual);

    /**
     * Gets a SWRLLiteralArgument, which is used to wrap an OWLLiteral to provide an argument for an
     * atom.
     *
     * @param literal The constant that is the object argument
     * @return A SWRLLiteralArgument that wraps the specified literal
     */
    SWRLLiteralArgument getSWRLLiteralArgument(OWLLiteral literal);

    /**
     * @param arg0 first individual
     * @param arg1 second individual
     * @return a sameindividual atom
     */
    SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument arg0, SWRLIArgument arg1);

    /**
     * @param arg0 first individual
     * @param arg1 second individual
     * @return a differentindividual atom
     */
    SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument arg0,
        SWRLIArgument arg1);
}
