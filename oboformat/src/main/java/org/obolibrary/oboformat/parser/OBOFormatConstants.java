package org.obolibrary.oboformat.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

/**
 * OBOformat constants.
 */
public class OBOFormatConstants {

    private static final Map<String, OboFormatTag> TAGSTABLE = Stream.of(OboFormatTag.values())
        .collect(Collectors.toConcurrentMap(OboFormatTag::getTag, Function.identity()));

    private static int map(String s, ToIntFunction<OboFormatTag> f) {
        OboFormatTag t = TAGSTABLE.get(s);
        if (t == null) {
            return 10000;
        }
        return f.applyAsInt(t);
    }

    /**
     * Header priority comparator.
     */
    public static Comparator<String> headerPriority = (a, b) -> Integer
        .compare(map(a, OboFormatTag::headerPriority), map(b, OboFormatTag::headerPriority));
    /**
     * Tag priority comparator.
     */
    public static Comparator<String> tagPriority = (a, b) -> Integer
        .compare(map(a, OboFormatTag::tagPriority), map(b, OboFormatTag::tagPriority));
    /**
     * typedef priority comparator.
     */
    public static Comparator<String> typeDefPriority = (a, b) -> Integer
        .compare(map(a, OboFormatTag::typeDefPriority), map(b, OboFormatTag::typeDefPriority));
    /**
     * tags
     */
    public static final Set<String> TAGS = TAGSTABLE.keySet();

    /**
     * @param tag tag
     * @return oboformat tag
     */
    @Nullable
    public static OboFormatTag getTag(String tag) {
        return TAGSTABLE.get(tag);
    }

    /**
     * @return Date format for OboFormatTag.TAG_DATE
     */
    public static final DateFormat headerDateFormat() {
        return new SimpleDateFormat("dd:MM:yyyy HH:mm");
    }

    /**
     * OBOformat tags.
     */
    public enum OboFormatTag {
        //@formatter:off
        /**TAG_FORMAT_VERSION.                   */ TAG_FORMAT_VERSION                              ("format-version",      0),
        // moved from pos 5 to emulate OBO-Edit behavior
        /**TAG_ONTOLOGY.                         */ TAG_ONTOLOGY                                    ("ontology",            85),
        /**TAG_DATA_VERSION.                     */ TAG_DATA_VERSION                                ("data-version",        10),
        /**TAG_DATE.                             */ TAG_DATE                                        ("date",                15),
        /**TAG_SAVED_BY.                         */ TAG_SAVED_BY                                    ("saved-by",            20),
        /**TAG_AUTO_GENERATED_BY.                */ TAG_AUTO_GENERATED_BY                           ("auto-generated-by",   25),
        // moved from pos 30 to emulate OBO-Edit behavior
        /**TAG_IMPORT.                           */ TAG_IMPORT                                      ("import",              80),
        /**TAG_SUBSETDEF.                        */ TAG_SUBSETDEF                                   ("subsetdef",           35),
        /**TAG_SYNONYMTYPEDEF.                   */ TAG_SYNONYMTYPEDEF                              ("synonymtypedef",      40),
        /**TAG_DEFAULT_NAMESPACE.                */ TAG_DEFAULT_NAMESPACE                           ("default-namespace",   45),
        /**TAG_IDSPACE.                          */ TAG_IDSPACE                                     ("idspace",             50),
        /**TAG_TREAT_XREFS_AS_EQUIVALENT.        */ TAG_TREAT_XREFS_AS_EQUIVALENT                   ("treat-xrefs-as-equivalent",           55),
        /**XREFS_AS_REVERSE_GENUS_DIFFERENTIA.   */ TAG_TREAT_XREFS_AS_REVERSE_GENUS_DIFFERENTIA    ("treat-xrefs-as-reverse-genus-differentia"),
        /**TAG_TREAT_XREFS_AS_GENUS_DIFFERENTIA. */ TAG_TREAT_XREFS_AS_GENUS_DIFFERENTIA            ("treat-xrefs-as-genus-differentia",    60),
        /**TAG_TREAT_XREFS_AS_RELATIONSHIP.      */ TAG_TREAT_XREFS_AS_RELATIONSHIP                 ("treat-xrefs-as-relationship",         65),
        /**TAG_TREAT_XREFS_AS_IS_A.              */ TAG_TREAT_XREFS_AS_IS_A                         ("treat-xrefs-as-is_a",                 70),
        /**TAG_TREAT_XREFS_AS_HAS_SUBCLASS. */ TAG_TREAT_XREFS_AS_HAS_SUBCLASS( "treat-xrefs-as-has-subclass"),
        /**TAG_OWL_AXIOMS.                       */ TAG_OWL_AXIOMS                                  ("owl-axioms",              110),
        /**TAG_REMARK.                           */ TAG_REMARK                                      ("remark",                  75),
        /**TAG_ID.                               */ TAG_ID                                          ("id",                      10000,  5,      5),
        /**TAG_NAME.                             */ TAG_NAME                                        ("name",                    10000,  15,     15),
        /**TAG_NAMESPACE.                        */ TAG_NAMESPACE                                   ("namespace",               10000,  20,     20),
        /**TAG_ALT_ID.                           */ TAG_ALT_ID                                      ("alt_id",                  10000,  25,     25),
        /**TAG_DEF.                              */ TAG_DEF                                         ("def",                     10000,  30,     30),
        /**TAG_COMMENT.                          */ TAG_COMMENT                                     ("comment",                 10000,  35,     35),
        /**TAG_SUBSET.                           */ TAG_SUBSET                                      ("subset",                  10000,  40,     40),
        /**TAG_SYNONYM.                          */ TAG_SYNONYM                                     ("synonym",                 10000,  45,     45),
        /**TAG_XREF.                             */ TAG_XREF                                        ("xref",                    10000,  50,     50),
        /**TAG_BUILTIN.                          */ TAG_BUILTIN                                     ("builtin",                 10000,  55,     70),
        /**TAG_PROPERTY_VALUE.                   */ TAG_PROPERTY_VALUE                              ("property_value",          100,    98,     55),
        /**TAG_IS_A.                             */ TAG_IS_A                                        ("is_a",                    10000,  65,     115),
        /**TAG_INTERSECTION_OF.                  */ TAG_INTERSECTION_OF                             ("intersection_of",         10000,  70,     120),
        /**TAG_UNION_OF.                         */ TAG_UNION_OF                                    ("union_of",                10000,  80,     125),
        /**TAG_EQUIVALENT_TO.                    */ TAG_EQUIVALENT_TO                               ("equivalent_to",           10000,  85,     130),
        /**TAG_DISJOINT_FROM.                    */ TAG_DISJOINT_FROM                               ("disjoint_from",           10000,  90,     135),
        /**TAG_RELATIONSHIP.                     */ TAG_RELATIONSHIP                                ("relationship",            10000,  95,     165),
        /**TAG_CREATED_BY.                       */ TAG_CREATED_BY                                  ("created_by",              10000,  130,    191),
        /**TAG_CREATION_DATE.                    */ TAG_CREATION_DATE                               ("creation_date",           10000,  140,    192),
        /**TAG_IS_OBSELETE.                      */ TAG_IS_OBSELETE                                 ("is_obsolete",             10000,  110,    169),
        /**TAG_REPLACED_BY.                      */ TAG_REPLACED_BY                                 ("replaced_by",             10000,  115,    185),
        /**TAG_IS_ANONYMOUS.                     */ TAG_IS_ANONYMOUS                                ("is_anonymous",            10000,  10,     10),
        /**TAG_DOMAIN.                           */ TAG_DOMAIN                                      ("domain",                  10000,  10000,  60),
        /**TAG_RANGE.                            */ TAG_RANGE                                       ("range",                   10000,  10000,  65),
        /**TAG_IS_ANTI_SYMMETRIC.                */ TAG_IS_ANTI_SYMMETRIC                           ("is_anti_symmetric",       10000,  10000,  75),
        /**TAG_IS_CYCLIC.                        */ TAG_IS_CYCLIC                                   ("is_cyclic",               10000,  10000,  80),
        /**TAG_IS_REFLEXIVE.                     */ TAG_IS_REFLEXIVE                                ("is_reflexive",            10000,  10000,  85),
        /**TAG_IS_SYMMETRIC.                     */ TAG_IS_SYMMETRIC                                ("is_symmetric",            10000,  10000,  90),
        /**TAG_IS_TRANSITIVE.                    */ TAG_IS_TRANSITIVE                               ("is_transitive",           10000,  10000,  100),
        /**TAG_IS_FUNCTIONAL.                    */ TAG_IS_FUNCTIONAL                               ("is_functional",           10000,  10000,  105),
        /**TAG_IS_INVERSE_FUNCTIONAL.            */ TAG_IS_INVERSE_FUNCTIONAL                       ("is_inverse_functional",   10000,  10000,  110),
        /**TAG_TRANSITIVE_OVER.                  */ TAG_TRANSITIVE_OVER                             ("transitive_over",         10000,  10000,  145),
        /**TAG_HOLDS_OVER_CHAIN.                 */ TAG_HOLDS_OVER_CHAIN                            ("holds_over_chain",        10000,  60,     71),
        /**TAG_EQUIVALENT_TO_CHAIN.              */ TAG_EQUIVALENT_TO_CHAIN                         ("equivalent_to_chain",     10000,  10000,  155),
        /**TAG_DISJOINT_OVER.                    */ TAG_DISJOINT_OVER                               ("disjoint_over",           10000,  10000,  160),
        /**TAG_EXPAND_ASSERTION_TO.              */ TAG_EXPAND_ASSERTION_TO                         ("expand_assertion_to",     10000,  10000,  195),
        /**TAG_EXPAND_EXPRESSION_TO.             */ TAG_EXPAND_EXPRESSION_TO                        ("expand_expression_to",    10000,  10000,  200),
        /**TAG_IS_CLASS_LEVEL_TAG.               */ TAG_IS_CLASS_LEVEL_TAG                          ("is_class_level",          10000,  10000,  210),
        /**TAG_IS_METADATA_TAG.                  */ TAG_IS_METADATA_TAG                             ("is_metadata_tag",         10000,  10000,  205),
        /**TAG_CONSIDER.                         */ TAG_CONSIDER                                    ("consider",                10000,  120,    190),
        /**TAG_INVERSE_OF.                       */ TAG_INVERSE_OF                                  ("inverse_of",              10000,  10000,  140),
        /**TAG_IS_ASYMMETRIC. */ TAG_IS_ASYMMETRIC("is_asymmetric"),
        /**TAG_NAMESPACE_ID_RULE.                */ TAG_NAMESPACE_ID_RULE                           ("namespace-id-rule",       46),
        /**TAG_LOGICAL_DEFINITION_VIEW_RELATION. */ TAG_LOGICAL_DEFINITION_VIEW_RELATION("logical-definition-view-relation"),
        //@formatter:on
        // these are keywords, not tags, but we keep them here for convenience
        /**
         * Scope.
         */
        TAG_SCOPE("scope"),
        /**
         * Implicit, in synonymtypedef.
         */
        TAG_HAS_SYNONYM_TYPE("has_synonym_type"),
        /** implicit, in synonym. */
        /**
         * Broad.
         */
        TAG_BROAD("BROAD"),
        /**
         * Narrow.
         */
        TAG_NARROW("NARROW"),
        /**
         * Exact.
         */
        TAG_EXACT("EXACT"),
        /**
         * Related.
         */
        TAG_RELATED("RELATED");

        /**
         * Term frames.
         */
        public static final EnumSet<OboFormatTag> TERM_FRAMES = EnumSet.of(TAG_INTERSECTION_OF,
            TAG_UNION_OF, TAG_EQUIVALENT_TO, TAG_DISJOINT_FROM, TAG_RELATIONSHIP, TAG_IS_A);
        /**
         * Typedef frames.
         */
        public static final EnumSet<OboFormatTag> TYPEDEF_FRAMES =
            EnumSet.of(TAG_INTERSECTION_OF, TAG_UNION_OF, TAG_EQUIVALENT_TO, TAG_DISJOINT_FROM,
                TAG_INVERSE_OF, TAG_TRANSITIVE_OVER, TAG_DISJOINT_OVER, TAG_IS_A);
        private final String tag;
        private final int headerTagsPriority;
        private final int tagsPriority;
        private final int typeDefTagsPriority;

        OboFormatTag(String tag) {
            this(tag, 10000, 10000, 10000);
        }

        OboFormatTag(String tag, int header) {
            this(tag, header, 10000, 10000);
        }

        OboFormatTag(String tag, int header, int priority, int typedef) {
            this.tag = tag;
            headerTagsPriority = header;
            tagsPriority = priority;
            typeDefTagsPriority = typedef;
        }

        /**
         * @return header priority
         */
        public int headerPriority() {
            return headerTagsPriority;
        }

        /**
         * @return tag priority
         */
        public int tagPriority() {
            return tagsPriority;
        }

        /**
         * @return typedef priority
         */
        public int typeDefPriority() {
            return typeDefTagsPriority;
        }

        /**
         * @return tag
         */
        public String getTag() {
            return tag;
        }

        @Override
        public String toString() {
            return tag;
        }
    }
}
