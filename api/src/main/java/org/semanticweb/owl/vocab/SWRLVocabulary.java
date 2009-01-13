package org.semanticweb.owl.vocab;

import java.net.URI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public enum SWRLVocabulary {

    IMP("Imp"),
    INDIVIDUAL_PROPERTY_ATOM("IndividualPropertyAtom"),
    DATAVALUED_PROPERTY_ATOM("DatavaluedPropertyAtom"),
    CLASS_ATOM("ClassAtom"),
    DATA_RANGE_ATOM("DataRangeAtom"),
    VARIABLE("Variable"),
    ATOM_LIST("AtomList"),
    SAME_INDIVIDUAL_ATOM("SameIndividualAtom"),
    DIFFERENT_INDIVIDUALS_ATOM("DifferentIndividualsAtom"),
    BUILT_IN_ATOM("BuiltinAtom"),
    HEAD("head"),
    BODY("body"),
    CLASS_PREDICATE("classPredicate"),
    DATA_RANGE("dataRange"),
    PROPERTY_PREDICATE("propertyPredicate"),
    BUILT_IN("builtin"),
    BUILT_IN_CLASS("Builtin"),
    ARGUMENTS("arguments"),
    ARGUMENT_1("argument1"),
    ARGUMENT_2("argument2");

    private String shortName;

    private URI uri;

    SWRLVocabulary(String name) {
        this.shortName = name;
        this.uri = URI.create(Namespaces.SWRL + name);
    }


    public String getShortName() {
        return shortName;
    }


    public URI getURI() {
        return uri;
    }
    
}
