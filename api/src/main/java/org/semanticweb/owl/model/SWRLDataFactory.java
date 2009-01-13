package org.semanticweb.owl.model;

import org.semanticweb.owl.vocab.SWRLBuiltInsVocabulary;

import java.net.URI;
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
 */
public interface SWRLDataFactory {

    /**
     * Gets a SWRL rule which is named with a URI
     * @param uri The rule URI (the "name" of the rule)
     * @param antecendent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */
    SWRLRule getSWRLRule(URI uri, Set<? extends SWRLAtom> antecendent, Set<? extends SWRLAtom> consequent);


    /**
     * Gets a SWRL rule.
     * @param uri The id
     * @param anonymous specifies if the rule is anonymous, <code>true</code> if it
     * is anonymous, otherwise <code>false</code>.
     * @param antededent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */
    SWRLRule getSWRLRule(URI uri, boolean anonymous, Set<? extends SWRLAtom> antededent, Set<? extends SWRLAtom> consequent);


    /**
     * Gets a SWRL rule which is not named with a URI.
     * @param antecendent The atoms that make up the antecedent
     * @param consequent The atoms that make up the consequent
     */
    SWRLRule getSWRLRule(Set<? extends SWRLAtom> antecendent, Set<? extends SWRLAtom> consequent);


    /**
     * Gets a SWRL class atom, i.e.  C(x) where C is a class description and
     * x is either an individual id or an i-variable
     *
     * @param desc The class description
     * @param arg  The argument (x)
     */
    SWRLClassAtom getSWRLClassAtom(OWLDescription desc, SWRLAtomIObject arg);


    /**
     * Gets a SWRL data range atom, i.e.  D(x) where D is an OWL data range and
     * x is either a constant or a d-variable
     *
     * @param rng The class description
     * @param arg The argument (x)
     */
    SWRLDataRangeAtom getSWRLDataRangeAtom(OWLDataRange rng, SWRLAtomDObject arg);


    /**
     * Gets a SWRL object property atom, i.e. P(x, y) where P is an OWL object
     * property (expression) and x and y are are either an individual id or
     * an i-variable.
     * @param property The property (P)
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     */
    SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(OWLObjectPropertyExpression property,
                                                     SWRLAtomIObject arg0,
                                                     SWRLAtomIObject arg1);


     /**
     * Gets a SWRL data property atom, i.e. R(x, y) where R is an OWL data
     * property (expression) and x and y are are either a constant or
     * a d-variable.
     * @param property The property (P)
     * @param arg0 The first argument (x)
     * @param arg1 The second argument (y)
     */
    SWRLDataValuedPropertyAtom getSWRLDataValuedPropertyAtom(OWLDataPropertyExpression property,
                                                       SWRLAtomIObject arg0,
                                                       SWRLAtomDObject arg1);


    /**
     * Creates a SWRL Built-In atom.
     *
     * @param builtIn The SWRL builtIn (see SWRL W3 member submission)
     * @param args       A non-empty set of SWRL D-Objects
     */
    SWRLBuiltInAtom getSWRLBuiltInAtom(SWRLBuiltInsVocabulary builtIn,
                                       List<SWRLAtomDObject> args);


    /**
     * Gets a SWRL i-variable.  This is used in rule atoms where a SWRL
     * I object can be used.
     * @param var The id (URI) of the variable
     */
    SWRLAtomIVariable getSWRLAtomIVariable(URI var);

    /**
     * Gets a SWRL d-variable.  This is used in rule atoms where a SWRL
     * D object can be used.
     * @param var The id (URI) of the variable
     */
    SWRLAtomDVariable getSWRLAtomDVariable(URI var);


    /**
     * Gets a SWRL individual object.
     * @param individual The individual that is the object argument
     */
    SWRLAtomIndividualObject getSWRLAtomIndividualObject(OWLIndividual individual);

    /**
     * Gets a SWRL constant object.
     * @param constant The constant that is the object argument
     */
    SWRLAtomConstantObject getSWRLAtomConstantObject(OWLConstant constant);

    SWRLSameAsAtom getSWRLSameAsAtom(SWRLAtomIObject arg0, SWRLAtomIObject arg1);

    SWRLDifferentFromAtom getSWRLDifferentFromAtom(SWRLAtomIObject arg0, SWRLAtomIObject arg1);
}
