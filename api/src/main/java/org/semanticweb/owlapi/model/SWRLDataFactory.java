package org.semanticweb.owlapi.model;

import java.util.List;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * An interface to a factory that can create SWRL objects
 */
public interface SWRLDataFactory {

    /**
     * Gets a SWRL rule which is named with an IRI
     * @param iri The rule identifier/name
     * @param body The atoms that make up the body of the rule
     * @param head The atoms that make up the head of the rule
     * @return The rule with the specified IRI, body and head
     */
    SWRLRule getSWRLRule(IRI iri, Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head);


    /**
     * Gets a SWRL rule which is anonymous (the main node in the rule doesn't have an identifier in RDF)
     * @param nodeID The anonymous node ID
     * @param body The atoms that make up the body of the rule
     * @param head The atoms that make up the head of the rule
     * @return The rule with the specified node ID, body and head
     */
    SWRLRule getSWRLRule(NodeID nodeID, Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head);


    /**
     * Gets an anonymous SWRL Rule
     * @param body The atoms that make up the body
     * @param head The atoms that make up the head
     * @return An anonymous rule with the specified body and head
     */
    SWRLRule getSWRLRule(Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head);

     /**
     * Gets an anonymous SWRL Rule
     * @param body The atoms that make up the body
     * @param head The atoms that make up the head
     * @param annotations The annotations for the rule (may be an empty set)
     * @return An anonymous rule with the specified body and head
     */
    SWRLRule getSWRLRule(Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head, Set<OWLAnnotation> annotations);


    /**
     * Gets a SWRL atom where the predicate is a class expression i.e.  C(x) where C is a class expression and
     * x is either an individual or a variable for an individual
     *
     * @param predicate The class expression that represents the predicate of the atom
     * @param arg  The argument (x)
     * @return The class atom with the specified class expression predicate and the specified argument.
     */
    SWRLClassAtom getSWRLClassAtom(OWLClassExpression predicate, SWRLIArgument arg);


    /**
     * Gets a SWRL atom where the predicate is a data range, i.e.  D(x) where D is an OWL data range and
     * x is either a literal or variable for a literal
     *
     * @param predicate The data range that represents the predicate of the atom
     * @param arg The argument (x)
     * @return An atom with the specified data range predicate and the specified argument
     */
    SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange predicate, SWRLDArgument arg);


    /**
     * Gets a SWRL object property atom, i.e. P(x, y) where P is an OWL object
     * property (expression) and x and y are are either individuals or variables for individuals.
     * @param property The property (P) representing the atom predicate
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     * @return A SWRLObjectPropertyAtom that has the specified predicate and the specified arguments
     */
    SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property,
                                                     SWRLIArgument arg0,
                                                     SWRLIArgument arg1);


     /**
     * Gets a SWRL data property atom, i.e. R(x, y) where R is an OWL data
     * property (expression) and x and y are are either literals or variables for literals
     * @param property The property (P) that represents the atom predicate
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     * @return A SWRLDataPropertyAtom that has the specified predicate and the specified arguments
     */
    SWRLDataPropertyAtom getSWRLDataPropertyAtom(OWLDataPropertyExpression property,
                                                       SWRLIArgument arg0,
                                                       SWRLDArgument arg1);


    /**
     * Creates a SWRL Built-In atom.  Builtins have predicates that are identified by IRIs.  SWRL provides a core
     * set of builtins, which are described in the OWL API by the {@link org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary}.
     *
     * @param builtInIRI The builtin predicate IRI
     * @param args       A non-empty set of SWRL Arguments.
     * @throws IllegalArgumentException if the list of arguments is empty
     * @return A SWRLBuiltInAtom whose predicate is identified by the specified builtInIRI and that has the specified
     * arguments
     */
    SWRLBuiltInAtom getSWRLBuiltInAtom(IRI builtInIRI, List<SWRLDArgument> args);


    /**
     * Gets a SWRLVariable.
     * @param var The id (IRI) of the variable
     * @return A SWRLVariable that has the name specified by the IRI
     */
    SWRLVariable getSWRLVariable(IRI var);

    /**
     * Gets a SWRLIndividualArgument, which is used to wrap and OWLIndividual as an argument for an atom
     * @param individual The individual that is the object argument
     * @return A SWRLIndividualArgument that wraps the specified individual
     */
    SWRLIndividualArgument getSWRLIndividualArgument(OWLIndividual individual);

    /**
     * Gets a SWRLLiteralArgument, which is used to wrap an OWLLiteral to provide an argument for an atom
     * @param literal The constant that is the object argument
     * @return A SWRLLiteralArgument that wraps the specified literal
     */
    SWRLLiteralArgument getSWRLLiteralArgument(OWLLiteral literal);

    SWRLSameIndividualAtom getSWRLSameIndividualAtom(SWRLIArgument arg0, SWRLIArgument arg1);

    SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(SWRLIArgument arg0, SWRLIArgument arg1);
}
