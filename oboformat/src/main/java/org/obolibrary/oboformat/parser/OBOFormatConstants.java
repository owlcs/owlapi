package org.obolibrary.oboformat.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Set;

import javax.annotation.Nonnull;

/** oboformat constants */
public class OBOFormatConstants {

    /** oboformat tags */
    public enum OboFormatTag {



        //@formatter:off
        /**TAG_FORMAT_VERSION*/ TAG_FORMAT_VERSION("format-version"),
        /**TAG_ONTOLOGY*/ TAG_ONTOLOGY( "ontology"),
        /**TAG_DATA_VERSION*/ TAG_DATA_VERSION( "data-version"),
        /**TAG_DATE*/ TAG_DATE( "date"),
        /**TAG_SAVED_BY*/ TAG_SAVED_BY( "saved-by"),
        /**TAG_AUTO_GENERATED_BY*/ TAG_AUTO_GENERATED_BY( "auto-generated-by"),
        /**TAG_IMPORT*/ TAG_IMPORT( "import"),
        /**TAG_SUBSETDEF*/ TAG_SUBSETDEF( "subsetdef"),
        /**TAG_SYNONYMTYPEDEF*/ TAG_SYNONYMTYPEDEF( "synonymtypedef"),
        /**TAG_DEFAULT_NAMESPACE*/ TAG_DEFAULT_NAMESPACE( "default-namespace"),
        /**TAG_IDSPACE*/ TAG_IDSPACE( "idspace"),
        /**TAG_TREAT_XREFS_AS_EQUIVALENT*/ TAG_TREAT_XREFS_AS_EQUIVALENT( "treat-xrefs-as-equivalent"),
        /**TAG_TREAT_XREFS_AS_REVERSE_GENUS_DIFFERENTIA*/ TAG_TREAT_XREFS_AS_REVERSE_GENUS_DIFFERENTIA( "treat-xrefs-as-reverse-genus-differentia"),
        /**TAG_TREAT_XREFS_AS_GENUS_DIFFERENTIA*/ TAG_TREAT_XREFS_AS_GENUS_DIFFERENTIA( "treat-xrefs-as-genus-differentia"),
        /**TAG_TREAT_XREFS_AS_RELATIONSHIP*/ TAG_TREAT_XREFS_AS_RELATIONSHIP( "treat-xrefs-as-relationship"),
        /**TAG_TREAT_XREFS_AS_IS_A*/ TAG_TREAT_XREFS_AS_IS_A( "treat-xrefs-as-is_a"),
        /**TAG_TREAT_XREFS_AS_HAS_SUBCLASS*/ TAG_TREAT_XREFS_AS_HAS_SUBCLASS( "treat-xrefs-as-has-subclass"),
        /**TAG_OWL_AXIOMS*/ TAG_OWL_AXIOMS( "owl-axioms"),
        /**TAG_REMARK*/ TAG_REMARK( "remark"),
        /**TAG_ID*/ TAG_ID( "id"),
        /**TAG_NAME*/ TAG_NAME( "name"),
        /**TAG_NAMESPACE*/ TAG_NAMESPACE( "namespace"),
        /**TAG_ALT_ID*/ TAG_ALT_ID( "alt_id"),
        /**TAG_DEF*/ TAG_DEF( "def"),
        /**TAG_COMMENT*/ TAG_COMMENT( "comment"),
        /**TAG_SUBSET*/ TAG_SUBSET( "subset"),
        /**TAG_SYNONYM*/ TAG_SYNONYM( "synonym"),
        /**TAG_XREF*/ TAG_XREF( "xref"),
        /**TAG_BUILTIN*/ TAG_BUILTIN( "builtin"),
        /**TAG_PROPERTY_VALUE*/ TAG_PROPERTY_VALUE( "property_value"),
        /**TAG_IS_A*/ TAG_IS_A( "is_a"),
        /**TAG_INTERSECTION_OF*/ TAG_INTERSECTION_OF( "intersection_of"),
        /**TAG_UNION_OF*/ TAG_UNION_OF( "union_of"),
        /**TAG_EQUIVALENT_TO*/ TAG_EQUIVALENT_TO( "equivalent_to"),
        /**TAG_DISJOINT_FROM*/ TAG_DISJOINT_FROM( "disjoint_from"),
        /**TAG_RELATIONSHIP*/ TAG_RELATIONSHIP( "relationship"),
        /**TAG_CREATED_BY*/ TAG_CREATED_BY( "created_by"),
        /**TAG_CREATION_DATE*/ TAG_CREATION_DATE( "creation_date"),
        /**TAG_IS_OBSELETE*/ TAG_IS_OBSELETE( "is_obsolete"),
        /**TAG_REPLACED_BY*/ TAG_REPLACED_BY( "replaced_by"),
        /**TAG_IS_ANONYMOUS*/ TAG_IS_ANONYMOUS( "is_anonymous"),
        /**TAG_DOMAIN*/ TAG_DOMAIN( "domain"),
        /**TAG_RANGE*/ TAG_RANGE( "range"),
        /**TAG_IS_ANTI_SYMMETRIC*/ TAG_IS_ANTI_SYMMETRIC( "is_anti_symmetric"),
        /**TAG_IS_CYCLIC*/ TAG_IS_CYCLIC( "is_cyclic"),
        /**TAG_IS_REFLEXIVE*/ TAG_IS_REFLEXIVE( "is_reflexive"),
        /**TAG_IS_SYMMETRIC*/ TAG_IS_SYMMETRIC( "is_symmetric"),
        /**TAG_IS_TRANSITIVE*/ TAG_IS_TRANSITIVE( "is_transitive"),
        /**TAG_IS_FUNCTIONAL*/ TAG_IS_FUNCTIONAL( "is_functional"),
        /**TAG_IS_INVERSE_FUNCTIONAL*/ TAG_IS_INVERSE_FUNCTIONAL( "is_inverse_functional"),
        /**TAG_TRANSITIVE_OVER*/ TAG_TRANSITIVE_OVER( "transitive_over"),
        /**TAG_HOLDS_OVER_CHAIN*/ TAG_HOLDS_OVER_CHAIN( "holds_over_chain"),
        /**TAG_EQUIVALENT_TO_CHAIN*/ TAG_EQUIVALENT_TO_CHAIN( "equivalent_to_chain"),
        /**TAG_DISJOINT_OVER*/ TAG_DISJOINT_OVER( "disjoint_over"),
        /**TAG_EXPAND_ASSERTION_TO*/ TAG_EXPAND_ASSERTION_TO( "expand_assertion_to"),
        /**TAG_EXPAND_EXPRESSION_TO*/ TAG_EXPAND_EXPRESSION_TO( "expand_expression_to"),
        /**TAG_IS_CLASS_LEVEL_TAG*/ TAG_IS_CLASS_LEVEL_TAG( "is_class_level"),
        /**TAG_IS_METADATA_TAG*/ TAG_IS_METADATA_TAG("is_metadata_tag"),
        /**TAG_CONSIDER*/ TAG_CONSIDER("consider"),
        /**TAG_INVERSE_OF*/ TAG_INVERSE_OF("inverse_of"),
        /**TAG_IS_ASYMMETRIC*/ TAG_IS_ASYMMETRIC("is_asymmetric"),
        /**TAG_NAMESPACE_ID_RULE*/ TAG_NAMESPACE_ID_RULE("namespace-id-rule"),
        /**TAG_LOGICAL_DEFINITION_VIEW_RELATION*/ TAG_LOGICAL_DEFINITION_VIEW_RELATION("logical-definition-view-relation"),
        
        // these are keywords, not tags, but we keep them here for convenience
        /**scope*/ 
        TAG_SCOPE("scope"), 
        /** implicit, in synonymtypedef*/
        TAG_HAS_SYNONYM_TYPE("has_synonym_type"),
        /** implicit, in synonym*/
        /**broad*/
        TAG_BROAD("BROAD"), 
        /**narrow*/
        TAG_NARROW("NARROW"),
        /**exact*/
        TAG_EXACT("EXACT"),
        /**related*/
        TAG_RELATED("RELATED");
        private String tag;

        OboFormatTag(String tag) {
            this.tag = tag;
        }

        /** @return tag */
        public String getTag() {
            return tag;
        }

        @Override
        public String toString() {
            return tag;
        }
    }

    /** tags */
    @Nonnull
    public final static Set<String> TAGS;
    private static Hashtable<String, OboFormatTag> tagsTable;
    static {
        tagsTable = new Hashtable<String, OBOFormatConstants.OboFormatTag>();
        for (OboFormatTag tag : OboFormatTag.values()) {
            tagsTable.put(tag.getTag(), tag);
        }
        TAGS = tagsTable.keySet();
    }

    /** @param tag
     *            tag
     * @return oboformat tag */
    public static OboFormatTag getTag(String tag) {
        return tagsTable.get(tag);
    }

    /** Date format for OboFormatTag.TAG_DATE Use Thread local to ensure thread
     * safety, as {@link SimpleDateFormat} is not thread safe. */
    public static final ThreadLocal<DateFormat> headerDateFormat = new ThreadLocal<DateFormat>() {
        @Nonnull
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd:MM:yyyy HH:mm");
        }
    };
    /** UTF-8 default encoding */
    public static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";
}
