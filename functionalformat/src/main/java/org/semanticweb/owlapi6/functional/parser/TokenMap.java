package org.semanticweb.owlapi6.functional.parser;

import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATIONASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATIONPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATIONPROPERTYDOMAIN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ANNOTATIONPROPERTYRANGE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ASYMMETRICOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.BUILTINATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.CLASS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.CLASSASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.CLASSATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAALLVALUESFROM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATACOMPLEMENTOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAEXACTCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAHASVALUE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAINTERSECTIONOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAMAXCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAMINCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAONEOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROP;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROPERTYASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROPERTYATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROPERTYDOMAIN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAPROPERTYRANGE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATARANGEATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATASOMEVALUESFROM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATATYPE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATATYPEDEFINITION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATATYPERESTRICTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DATAUNIONOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DECLARATION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DIFFERENTINDIVIDUALS;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DIFFERENTINDIVIDUALSATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DISJOINTCLASSES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DISJOINTDATAPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DISJOINTOBJECTPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DISJOINTUNION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.DLSAFERULE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EQUIVALENTCLASSES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EQUIVALENTDATAPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.EQUIVALENTOBJECTPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.FUNCTIONALDATAPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.FUNCTIONALOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.HASKEY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.INVERSEFUNCTIONALOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.INVERSEOBJECTPROPERTIES;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.IRREFLEXIVEOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NAMEDINDIVIDUAL;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NEGATIVEDATAPROPERTYASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.NEGATIVEOBJECTPROPERTYASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTALLVALUESFROM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTCOMPLEMENTOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTEXACTCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTHASSELF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTHASVALUE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTINTERSECTIONOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTINVERSEOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTMAXCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTMINCARDINALITY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTONEOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROP;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROPERTYASSERTION;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROPERTYATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROPERTYDOMAIN;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTPROPERTYRANGE;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTSOMEVALUESFROM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.OBJECTUNIONOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.ONTOLOGY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.REFLEXIVEOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SAMEINDIVIDUAL;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SAMEINDIVIDUALATOM;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBANNOTATIONPROPERTYOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBCLASSOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBDATAPROPERTYOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SUBOBJECTPROPERTYOF;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.SYMMETRICOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.TRANSITIVEOBJECTPROPERTY;
import static org.semanticweb.owlapi6.functional.parser.OWLFunctionalSyntaxParserConstants.VARIABLE;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.semanticweb.owlapi6.model.OWLObjectType;

/**
 * Map between string tokens and indexes as defined in
 * OWLFunctionalSyntaxParserConstants. This will get updated automatically every
 * time the parser is updated.
 */
public class TokenMap {

    private static Integer DEFAULT = Integer.valueOf(OWLFunctionalSyntaxParserConstants.PN_LOCAL);
    private static final Map<String, Integer> makeTokenMap = makeTokenMap();
    private static final String[] index = reverse(makeTokenMap);

    private TokenMap() {}

    private static Map<String, Integer> makeTokenMap() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        for (Field f : OWLFunctionalSyntaxParserConstants.class.getDeclaredFields()) {
            if (f.getType().equals(int.class)) {
                try {
                    int indexValue = f.getInt(null);
                    String key = OWLFunctionalSyntaxParserConstants.tokenImage[indexValue];
                    if (key.charAt(0) == '"' && key.charAt(key.length() - 1) == '"') {
                        key = key.substring(1, key.length() - 1);
                    }
                    if (key.codePoints().allMatch(Character::isLetter)) {
                        map.put(key, Integer.valueOf(indexValue));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return map;
    }

    private static String[] reverse(Map<String, Integer> map) {
        String[] reverse = new String[map.values().stream().mapToInt(Integer::intValue).max().orElse(-1) + 1];
        Arrays.fill(reverse, "");
        map.forEach((a, b) -> reverse[b.intValue()] = a);
        return reverse;
    }

    /**
     * @param s
     *        String to match
     * @return index of the corresponding token in
     *         OWLFunctionalSyntaxParserConstants.tokenImage if one found, index
     *         of {@code <PN_LOCAL>} otherwise
     */
    public static int tokenIndex(String s) {
        return makeTokenMap.getOrDefault(s, DEFAULT).intValue();
    }

    /**
     * @param in
     *        index to match
     * @return String corresponding to index of the corresponding token in
     *         OWLFunctionalSyntaxParserConstants.tokenImage if one found, index
     *         of {@code <PN_LOCAL>} otherwise
     */
    public static String indexToken(int in) {
        if (in >= index.length || in < 0) {
            return "";
        }
        return index[in];
    }

    private static EnumMap<OWLObjectType, Integer> typeTokens = typeTokens();

    /**
     * @param t
     *        type
     * @return token, or empty string if no type is matched
     */
    public static String tokenForType(OWLObjectType t) {
        Integer i = typeTokens.get(t);
        if (i == null) {
            return "";
        }
        return indexToken(i.intValue());
    }

    private static EnumMap<OWLObjectType, Integer> typeTokens() {
        EnumMap<OWLObjectType, Integer> map = new EnumMap<>(OWLObjectType.class);
        map.put(OWLObjectType.ONTOLOGY, Integer.valueOf(ONTOLOGY));
        map.put(OWLObjectType.ASYMMETRIC, Integer.valueOf(ASYMMETRICOBJECTPROPERTY));
        map.put(OWLObjectType.CLASS_ASSERTION, Integer.valueOf(CLASSASSERTION));
        map.put(OWLObjectType.DATA_ASSERTION, Integer.valueOf(DATAPROPERTYASSERTION));
        map.put(OWLObjectType.DATA_DOMAIN, Integer.valueOf(DATAPROPERTYDOMAIN));
        map.put(OWLObjectType.DATA_RANGE, Integer.valueOf(DATAPROPERTYRANGE));
        map.put(OWLObjectType.SUB_DATA, Integer.valueOf(SUBDATAPROPERTYOF));
        map.put(OWLObjectType.DECLARATION, Integer.valueOf(DECLARATION));
        map.put(OWLObjectType.DIFFERENT_INDIVIDUALS, Integer.valueOf(DIFFERENTINDIVIDUALS));
        map.put(OWLObjectType.DISJOINT_CLASSES, Integer.valueOf(DISJOINTCLASSES));
        map.put(OWLObjectType.DISJOINT_DATA, Integer.valueOf(DISJOINTDATAPROPERTIES));
        map.put(OWLObjectType.DISJOINT_OBJECT, Integer.valueOf(DISJOINTOBJECTPROPERTIES));
        map.put(OWLObjectType.DISJOINT_UNION, Integer.valueOf(DISJOINTUNION));
        map.put(OWLObjectType.ANNOTATION_ASSERTION, Integer.valueOf(ANNOTATIONASSERTION));
        map.put(OWLObjectType.EQUIVALENT_CLASSES, Integer.valueOf(EQUIVALENTCLASSES));
        map.put(OWLObjectType.EQUIVALENT_DATA, Integer.valueOf(EQUIVALENTDATAPROPERTIES));
        map.put(OWLObjectType.EQUIVALENT_OBJECT, Integer.valueOf(EQUIVALENTOBJECTPROPERTIES));
        map.put(OWLObjectType.FUNCTIONAL_DATA, Integer.valueOf(FUNCTIONALDATAPROPERTY));
        map.put(OWLObjectType.FUNCTIONAL_OBJECT, Integer.valueOf(FUNCTIONALOBJECTPROPERTY));
        map.put(OWLObjectType.INVERSE_FUNCTIONAL, Integer.valueOf(INVERSEFUNCTIONALOBJECTPROPERTY));
        map.put(OWLObjectType.INVERSE, Integer.valueOf(INVERSEOBJECTPROPERTIES));
        map.put(OWLObjectType.IRREFLEXIVE, Integer.valueOf(IRREFLEXIVEOBJECTPROPERTY));
        map.put(OWLObjectType.NEGATIVE_DATA_ASSERTION, Integer.valueOf(NEGATIVEDATAPROPERTYASSERTION));
        map.put(OWLObjectType.NEGATIVE_OBJECT_ASSERTION, Integer.valueOf(NEGATIVEOBJECTPROPERTYASSERTION));
        map.put(OWLObjectType.OBJECT_ASSERTION, Integer.valueOf(OBJECTPROPERTYASSERTION));
        map.put(OWLObjectType.OBJECT_DOMAIN, Integer.valueOf(OBJECTPROPERTYDOMAIN));
        map.put(OWLObjectType.OBJECT_RANGE, Integer.valueOf(OBJECTPROPERTYRANGE));
        map.put(OWLObjectType.SUB_OBJECT, Integer.valueOf(SUBOBJECTPROPERTYOF));
        map.put(OWLObjectType.REFLEXIVE, Integer.valueOf(REFLEXIVEOBJECTPROPERTY));
        map.put(OWLObjectType.SAME_INDIVIDUAL, Integer.valueOf(SAMEINDIVIDUAL));
        map.put(OWLObjectType.SUB_CLASS, Integer.valueOf(SUBCLASSOF));
        map.put(OWLObjectType.SYMMETRIC, Integer.valueOf(SYMMETRICOBJECTPROPERTY));
        map.put(OWLObjectType.TRANSITIVE, Integer.valueOf(TRANSITIVEOBJECTPROPERTY));
        map.put(OWLObjectType.CLASS, Integer.valueOf(CLASS));
        map.put(OWLObjectType.FORALL_DATA, Integer.valueOf(DATAALLVALUESFROM));
        map.put(OWLObjectType.EXACT_DATA, Integer.valueOf(DATAEXACTCARDINALITY));
        map.put(OWLObjectType.MAX_DATA, Integer.valueOf(DATAMAXCARDINALITY));
        map.put(OWLObjectType.MIN_DATA, Integer.valueOf(DATAMINCARDINALITY));
        map.put(OWLObjectType.SOME_DATA, Integer.valueOf(DATASOMEVALUESFROM));
        map.put(OWLObjectType.HASVALUE_DATA, Integer.valueOf(DATAHASVALUE));
        map.put(OWLObjectType.FORALL_OBJECT, Integer.valueOf(OBJECTALLVALUESFROM));
        map.put(OWLObjectType.NOT_OBJECT, Integer.valueOf(OBJECTCOMPLEMENTOF));
        map.put(OWLObjectType.EXACT_OBJECT, Integer.valueOf(OBJECTEXACTCARDINALITY));
        map.put(OWLObjectType.AND_OBJECT, Integer.valueOf(OBJECTINTERSECTIONOF));
        map.put(OWLObjectType.MAX_OBJECT, Integer.valueOf(OBJECTMAXCARDINALITY));
        map.put(OWLObjectType.MIN_OBJECT, Integer.valueOf(OBJECTMINCARDINALITY));
        map.put(OWLObjectType.ONEOF_OBJECT, Integer.valueOf(OBJECTONEOF));
        map.put(OWLObjectType.HASSELF_OBJECT, Integer.valueOf(OBJECTHASSELF));
        map.put(OWLObjectType.SOME_OBJECT, Integer.valueOf(OBJECTSOMEVALUESFROM));
        map.put(OWLObjectType.OR_OBJECT, Integer.valueOf(OBJECTUNIONOF));
        map.put(OWLObjectType.HASVALUE_OBJECT, Integer.valueOf(OBJECTHASVALUE));
        map.put(OWLObjectType.NOT_DATA, Integer.valueOf(DATACOMPLEMENTOF));
        map.put(OWLObjectType.ONEOF_DATA, Integer.valueOf(DATAONEOF));
        map.put(OWLObjectType.DATATYPE, Integer.valueOf(DATATYPE));
        map.put(OWLObjectType.DATATYPE_RESTRICTION, Integer.valueOf(DATATYPERESTRICTION));
        map.put(OWLObjectType.DATA_PROPERTY, Integer.valueOf(DATAPROP));
        map.put(OWLObjectType.OBJECT_PROPERTY, Integer.valueOf(OBJECTPROP));
        map.put(OWLObjectType.INVERSE_OBJECT, Integer.valueOf(OBJECTINVERSEOF));
        map.put(OWLObjectType.NAMED_INDIVIDUAL, Integer.valueOf(NAMEDINDIVIDUAL));
        map.put(OWLObjectType.HASKEY, Integer.valueOf(HASKEY));
        map.put(OWLObjectType.ANNOTATION_DOMAIN, Integer.valueOf(ANNOTATIONPROPERTYDOMAIN));
        map.put(OWLObjectType.ANNOTATION_RANGE, Integer.valueOf(ANNOTATIONPROPERTYRANGE));
        map.put(OWLObjectType.SUB_ANNOTATION, Integer.valueOf(SUBANNOTATIONPROPERTYOF));
        map.put(OWLObjectType.AND_DATA, Integer.valueOf(DATAINTERSECTIONOF));
        map.put(OWLObjectType.OR_DATA, Integer.valueOf(DATAUNIONOF));
        map.put(OWLObjectType.ANNOTATION_PROPERTY, Integer.valueOf(ANNOTATIONPROPERTY));
        map.put(OWLObjectType.ANNOTATION, Integer.valueOf(ANNOTATION));
        map.put(OWLObjectType.DATATYPE_DEFINITION, Integer.valueOf(DATATYPEDEFINITION));
        map.put(OWLObjectType.SWRL_RULE, Integer.valueOf(DLSAFERULE));
        map.put(OWLObjectType.SWRL_CLASS, Integer.valueOf(CLASSATOM));
        map.put(OWLObjectType.SWRL_DATA_RANGE, Integer.valueOf(DATARANGEATOM));
        map.put(OWLObjectType.SWRL_OBJECT_PROPERTY, Integer.valueOf(OBJECTPROPERTYATOM));
        map.put(OWLObjectType.SWRL_DATA_PROPERTY, Integer.valueOf(DATAPROPERTYATOM));
        map.put(OWLObjectType.SWRL_BUILTIN, Integer.valueOf(BUILTINATOM));
        map.put(OWLObjectType.SWRL_VARIABLE, Integer.valueOf(VARIABLE));
        map.put(OWLObjectType.SWRL_DIFFERENT_INDIVIDUAL, Integer.valueOf(DIFFERENTINDIVIDUALSATOM));
        map.put(OWLObjectType.SWRL_SAME_INDIVIDUAL, Integer.valueOf(SAMEINDIVIDUALATOM));
        return map;
    }
}
