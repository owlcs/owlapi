package uk.ac.manchester.cs.owl.inference.dig11;
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
 * Date: 21-Nov-2006<br><br>
 */
public interface Vocab {

    public static final String DEFCONCEPT = "defconcept";

    public static final String DEFROLE = "defrole";

    public static final String DEFINDIVIDUAL = "defindividual";

    public static final String TOP = "top";

    public static final String BOTTOM = "bottom";

    public static final String CATOM = "catom";

    public static final String RATOM = "ratom";

    public static final String INDIVIDUAL = "individual";

    public static final String NAME = "name";

    public static final String AND = "and";

    public static final String OR = "or";

    public static final String NOT = "not";

    public static final String SOME = "some";

    public static final String ALL = "all";

    public static final String ATMOST = "atmost";

    public static final String ATLEST = "atleast";

    public static final String ISET = "iset";

    public static final String INVERSE = "inverse";

    public static final String DISJOINT = "disjoint";

    public static final String EQUALC = "equalc";

    public static final String EQUALR = "equalr";

    public static final String IMPLIESC = "impliesc";

    public static final String IMPLIESR = "impliesr";

    public static final String FUNCTIONAL = "functional";

    public static final String TRANSITIVE = "transitive";

    public static final String DOMAIN = "domain";

    public static final String RANGE = "range";

    public static final String RELATED = "related";

    public static final String VALUE = "value";

    public static final String INSTANCEOF = "instanceof";

    public static final String ASKS = "asks";

    public static final String TELLS = "tells";

    public static final String ALL_CONCEPT_NAMES = "allConceptNames";

    public static final String ALL_ROLE_NAMES = "allRoleNames";

    public static final String ALL_INDIVIDUALS = "allIndividuals";

    public static final String SATISFIABLE = "satisfiable";

    public static final String SUBSUMES = "subsumes";

    public static final String PARENTS = "parents";

    public static final String CHILDREN = "children";

    public static final String ANCESTORS = "ancestors";

    public static final String DESCENDANTS = "descendants";

    public static final String EQUIVALENT = "equivalents";

    public static final String R_PARENTS = "rparents";

    public static final String R_CHILDREN = "rchildren";

    public static final String R_ANCESTORS = "rancestors";

    public static final String R_DESCENDANTS = "rdescendants";

    public static final String INSTANCES = "instances";

    public static final String TYPES = "types";

    public static final String ROLE_FILLERS = "rolefillers";

    public static final String VALUES = "values";

    public static final String RELATED_INDIVIDUALS = "relatedIndividuals";

    public static final String TOLD_VALUES = "toldValues";

    public static final String INSTANCE = "instance";

    public static final String UNIQUE_NAME_ASSUMPTION = "uniqueNameAssumption";


    public static final String GET_IDENTIFIER = "getIdentifier";

    public static final String NEW_KNOWLEDGE_BASE = "newKB";

    public static final String CLEAR_KNOWLEDGE_BASE = "clearKB";

    public static final String RELEASE_KNOWLEDGE_BASE = "releaseKB";

    public static final String ERROR = "error";

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    public static final String CONCEPT_SET = "conceptSet";

    public static final String ROLE_SET = "roleSet";

    public static final String INDIVIDUAL_SET = "individualSet";

    public static final String INDIVIDUAL_PAIR_SET = "individualPairSet";

    public static final String INDIVIDUAL_PAIR = "individualPair";


    public static final String SYNONYMS = "synonyms";
}
