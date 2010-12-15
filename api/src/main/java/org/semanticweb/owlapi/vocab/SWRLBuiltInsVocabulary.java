package org.semanticweb.owlapi.vocab;

import java.net.URI;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLPredicate;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public enum SWRLBuiltInsVocabulary implements SWRLPredicate {

    EQUAL("equal", 2),
    NOT_EQUAL("notEqual", 2),
    LESS_THAN("lessThan", 2),
    LESS_THAN_OR_EQUAL("lessThanOrEqual", 2),
    GREATER_THAN("greaterThan", 2),
    GREATER_THAN_OR_EQUAL("greaterThanOrEqual", 2),
    ADD("add", -1),
    SUBTRACT("subtract", 3),
    MULTIPLY("multiply", -1),
    DIVIDE("divide", 3),
    INTEGER_DIVIDE("integerDivide", 3),
    MOD("mod", 3),
    POW("pow", 3),
    UNARY_MINUS("unaryMinus", 2),
    UNARY_PLUS("unaryPlus", 2),
    ABS("abs", 2),
    CEILING("ceiling", 2),
    FLOOR("floor", 2),
    ROUND("round", 2),
    ROUND_HALF_TO_EVEN("roundHalfToEven", 2, 3),
    SIN("sin", 2),
    COS("cos", 2),
    TAN("tan", 2),
    BOOLEAN_NOT("booleanNot", 2),
    STRING_EQUALS_IGNORE_CASE("stringEqualIgnoreCase", 2),
    STRING_CONCAT("stringConcat", -1),
    SUBSTRING("substring", 3),
    STRING_LENGTH("stringLength", 2),
    NORMALIZE_SPACE("normalizeSpace", 2),
    UPPER_CASE("upperCase", 2),
    LOWER_CASE("lowerCase", 2),
    TRANSLATE("translate", 4),
    CONTAINS("contains", 2),
    CONTAINS_IGNORE_CASE("containsIgnoreCase", 2),
    STARTS_WITH("startsWith", 2),
    ENDS_WITH("endsWith", 2),
    SUBSTRING_BEFORE("substringBefore", 3),
    SUBSTRING_AFTER("substringAfter", 3),
    MATCHES("matches", 2),
    REPLACE("replace", 4),
    TOKENIZE("tokenize", 3),
    YEAR_MONTH_DURATION("yearMonthDuration", 5),
    DAY_TIME_DURATION("dayTimeDuration", 5),
    DATE_TIME("dateTime", 5),
    DATE("date", 5),
    TIME("time", 5),
    ADD_YEAR_MONTH_DURATIONS("addYearMonthDurations", -1),
    SUBTRACT_YEAR_MONTH_DURATIONS("subtractYearMonthDurations", 3),
    MULTIPLY_YEAR_MONTH_DURATIONS("multiplyYearMonthDurations", 3),
    DIVIDE_YEAR_MONTH_DURATIONS("divideYearMonthDurations", 3),
    ADD_DAY_TIME_DURATIONS("addDayTimeDurations", -1),
    SUBTRACT_DAY_TIME_DURATIONS("subtractDayTimeDurations", 3),
    MULTIPLY_DAY_TIME_DURATIONS("multiplyDayTimeDurations", 3),
    DIVIDE_DAY_TIME_DURATIONS("divideDayTimeDurations", 3),
    SUBTRACT_DATES("subtractDates", 3),
    SUBTRACT_TIMES("subtractTimes", 3),
    ADD_DAY_TIME_DURATION_TO_DATE_TIME("addDayTimeDurationToDateTime", 3),
    SUBTRACT_YEAR_MONTH_DURATION_FROM_DATE_TIME("subtractYearMonthDurationFromDateTime", 3),
    SUBTRACT_DAY_TIME_DURATION_FROM_DATE_TIME("subtractDayTimeDurationFromDateTime", 3),
    ADD_YEAR_MONTH_DURATION_TO_DATE("addYearMonthDurationToDate", 3),
    ADD_DAY_TIME_DURATION_TO_DATE("addDayTimeDurationToDate", 3),
    SUBTRACT_YEAR_MONTH_DURATION_FROM_DATE("subtractYearMonthDurationFromDate", 3),
    SUBTRACT_DAY_TIME_DURATION_FROM_DATE("subtractDayTimeDurationFromDate", 3),
    ADD_DAY_TIME_DURATION_FROM_TIME("addDayTimeDurationToTime", 3),
    SUBTRACT_DAY_TIME_DURATION_FROM_TIME("subtractDayTimeDurationFromTime", 3),
    SUBTRACT_DATE_TIMES_YIELDING_YEAR_MONTH_DURATION("subtractDateTimesYieldingYearMonthDuration", 3),
    SUBTRACT_DATE_TIMES_YIELDING_DAY_TIME_DURATION("subtractDateTimesYieldingDayTimeDuration", 3),
    RESOLVE_URI("resolveURI", 3),
    ANY_URI("anyURI", 7);

    private String shortName;

    private IRI iri;

    // Arity of the predicate (-1 if infinite)
    private int minArity;

    private int maxArity;

    SWRLBuiltInsVocabulary(String name, int arity) {
        this(name, arity, arity);
    }

    SWRLBuiltInsVocabulary(String name, int minArity, int maxArity) {
        this.shortName = name;
        this.iri = IRI.create(Namespaces.SWRLB + name);
        this.minArity = minArity;
        this.maxArity = maxArity;
    }


    public String getShortName() {
        return shortName;
    }

    public IRI getIRI() {
        return iri;
    }

    public URI getURI() {
        return iri.toURI();
    }

    /**
     * Gets the minimum arity of this built in
     * @return The minimum arity of this built in
     */
    public int getMinArity() {
        return minArity;
    }

    /**
     * Gets the maximum arity of this built in.
     * @return The maximum arity of the built in or -1 if the arity is infinite
     */
    public int getMaxArity() {
        return maxArity;
    }

    /**
     * Returns the minimum arity of this built in.
     * @return The minimum arity of this built in.
     * @deprecated Use getMinArity and getMaxArity instead
     */
    @Deprecated
    public int getArity() {
        return minArity;
    }


    /**
     * Gets a builtin vocabulary value for a given IRI
     * @param iri The IRI
     * @return The builtin vocabulary having the specified IRI, or <code>null</code> if there is no builtin
     * vocabulary with the specified IRI
     */
    public static SWRLBuiltInsVocabulary getBuiltIn(IRI iri) {
        for(SWRLBuiltInsVocabulary v : values()) {
            if(v.iri.equals(iri)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Gets a builtin vocabulary value for a given URI
     * @param uri The URI
     * @return The builtin vocabulary having the specified URI, or <code>null</code> if there is no builtin
     * vocabulary with the specified URI
     */
    public static SWRLBuiltInsVocabulary getBuiltIn(URI uri) {
        for(SWRLBuiltInsVocabulary v : values()) {
            if(v.getURI().equals(uri)) {
                return v;
            }
        }
        return null;
    }
}
