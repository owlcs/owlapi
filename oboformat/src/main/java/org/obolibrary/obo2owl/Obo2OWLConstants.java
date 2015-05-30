package org.obolibrary.obo2owl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;

import com.google.common.collect.Maps;

/**
 * @author Shahid Manzoor
 */
public class Obo2OWLConstants {

    /** Default iri. */
    public static final @Nonnull String DEFAULT_IRI_PREFIX = "http://purl.obolibrary.org/obo/";
    /** OIO vocabulary prefix. */
    public static final @Nonnull String OIOVOCAB_IRI_PREFIX = "http://www.geneontology.org/formats/oboInOwl#";
    /** IRI for the 'has obsolescence reason' annotation property */
    public static final @Nonnull IRI IRI_IAO_0000231 = IRI.create(DEFAULT_IRI_PREFIX + "IAO_0000231");
    /** IRI for the 'terms merged' individual */
    public static final @Nonnull IRI IRI_IAO_0000227 = IRI.create(DEFAULT_IRI_PREFIX + "IAO_0000227");

    /**
     * @param d
     *        date to format
     * @return formatted string
     */
    public static synchronized String format(Date d) {
        return FORMATTER.format(d);
    }

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /** OBO to OWL vocabulary. */
    public enum Obo2OWLVocabulary implements HasIRI {
        //@formatter:off
        /**IRI_IAO_0000424. */    IRI_IAO_0000424(DEFAULT_IRI_PREFIX, "IAO_0000424", "expand expression to", OboFormatTag.TAG_EXPAND_EXPRESSION_TO.getTag()),
        /**IRI_IAO_0000425. */    IRI_IAO_0000425(DEFAULT_IRI_PREFIX, "IAO_0000425", "expand assertion to", OboFormatTag.TAG_EXPAND_ASSERTION_TO.getTag()),
        /**IRI_IAO_0000115. */    IRI_IAO_0000115(DEFAULT_IRI_PREFIX, "IAO_0000115", "definition", OboFormatTag.TAG_DEF.getTag()),
        //                      IRI_IAO_0000118(DEFAULT_IRI_PREFIX, "IAO_0000118", "alternative term", OboFormatTag.TAG_SYNONYM.getTag()),
        /**IRI_IAO_0000427. */    IRI_IAO_0000427(DEFAULT_IRI_PREFIX, "IAO_0000427", "antisymmetric property", OboFormatTag.TAG_IS_ANTI_SYMMETRIC.getTag()),
        /**IRI_IAO_0100001. */    IRI_IAO_0100001(DEFAULT_IRI_PREFIX, "IAO_0100001", "term replaced by", OboFormatTag.TAG_REPLACED_BY.getTag()),
        /**IRI_OIO_shorthand. */  IRI_OIO_shorthand(OIOVOCAB_IRI_PREFIX, "shorthand", "shorthand", "shorthand"),
        /**IRI_OIO_consider. */   IRI_OIO_consider(OIOVOCAB_IRI_PREFIX, "consider", "consider", OboFormatTag.TAG_CONSIDER.getTag()),
        /**IRI_OIO_hasOBOFormatVersion. */        IRI_OIO_hasOBOFormatVersion(OIOVOCAB_IRI_PREFIX, "hasOBOFormatVersion", "has_obo_format_version", OboFormatTag.TAG_FORMAT_VERSION.getTag()),
        /**IRI_OIO_treatXrefsAsIsA. */            IRI_OIO_treatXrefsAsIsA(OIOVOCAB_IRI_PREFIX, "treat-xrefs-as-is_a", "treat-xrefs-as-is_a", OboFormatTag.TAG_TREAT_XREFS_AS_IS_A.getTag()),
        /**IRI_OIO_treatXrefsAsHasSubClass. */    IRI_OIO_treatXrefsAsHasSubClass(OIOVOCAB_IRI_PREFIX, "treat-xrefs-as-has-subclass", "treat-xrefs-as-has-subclass", OboFormatTag.TAG_TREAT_XREFS_AS_HAS_SUBCLASS.getTag()),
        /**IRI_OIO_treatXrefsAsRelationship. */   IRI_OIO_treatXrefsAsRelationship(OIOVOCAB_IRI_PREFIX, "treat-xrefs-as-relationship", "treat-xrefs-as-relationship", OboFormatTag.TAG_TREAT_XREFS_AS_RELATIONSHIP.getTag()),
        /**IRI_OIO_treatXrefsAsGenusDifferentia. */           IRI_OIO_treatXrefsAsGenusDifferentia(OIOVOCAB_IRI_PREFIX, "treat-xrefs-as-genus-differentia", "treat-xrefs-as-genus-differentia", OboFormatTag.TAG_TREAT_XREFS_AS_GENUS_DIFFERENTIA.getTag()),
        /**IRI_OIO_treatXrefsAsReverseGenusDifferentia. */    IRI_OIO_treatXrefsAsReverseGenusDifferentia(OIOVOCAB_IRI_PREFIX, "treat-xrefs-as-reverse-genus-differentia", "treat-xrefs-as-reverse-genus-differentia", OboFormatTag.TAG_TREAT_XREFS_AS_REVERSE_GENUS_DIFFERENTIA.getTag()),
        /**IRI_OIO_treatXrefsAsEquivalent. */                 IRI_OIO_treatXrefsAsEquivalent(OIOVOCAB_IRI_PREFIX, "treat-xrefs-as-equivalent", "treat-xrefs-as-equivalent", OboFormatTag.TAG_TREAT_XREFS_AS_EQUIVALENT.getTag()),
        /**IRI_OIO_hasOboNamespace. */    IRI_OIO_hasOboNamespace(OIOVOCAB_IRI_PREFIX, "hasOBONamespace", "has_obo_namespace", OboFormatTag.TAG_NAMESPACE.getTag()),
        /**IRI_OIO_hasDbXref. */          IRI_OIO_hasDbXref(OIOVOCAB_IRI_PREFIX, "hasDbXref", "database_cross_reference", OboFormatTag.TAG_XREF.getTag()),
        /**hasAlternativeId. */           hasAlternativeId(OIOVOCAB_IRI_PREFIX, "hasAlternativeId", "has_alternative_id", OboFormatTag.TAG_ALT_ID.getTag()),
        /**IRI_OIO_inSubset. */           IRI_OIO_inSubset(OIOVOCAB_IRI_PREFIX, "inSubset", "in_subset", OboFormatTag.TAG_SUBSET.getTag()),
        /**IRI_OIO_hasScope. */           IRI_OIO_hasScope(OIOVOCAB_IRI_PREFIX, "hasScope", "has_scope", OboFormatTag.TAG_SCOPE.getTag()),
        /**IRI_OIO_hasBroadSynonym. */    IRI_OIO_hasBroadSynonym(OIOVOCAB_IRI_PREFIX, "hasBroadSynonym", "has_broad_synonym", OboFormatTag.TAG_BROAD.getTag()),
        /**IRI_OIO_hasNarrowSynonym. */   IRI_OIO_hasNarrowSynonym(OIOVOCAB_IRI_PREFIX, "hasNarrowSynonym", "has_narrow_synonym", OboFormatTag.TAG_NARROW.getTag()),
        /**IRI_OIO_hasExactSynonym. */    IRI_OIO_hasExactSynonym(OIOVOCAB_IRI_PREFIX, "hasExactSynonym", "has_exact_synonym", OboFormatTag.TAG_EXACT.getTag()),
        /**IRI_OIO_hasRelatedSynonym. */  IRI_OIO_hasRelatedSynonym(OIOVOCAB_IRI_PREFIX, "hasRelatedSynonym", "has_related_synonym", OboFormatTag.TAG_RELATED.getTag()),
        /**hasSynonymType. */             hasSynonymType(OIOVOCAB_IRI_PREFIX, "hasSynonymType", "has_synonym_type", OboFormatTag.TAG_HAS_SYNONYM_TYPE.getTag()),
        /**IRI_OIO_Subset. */             IRI_OIO_Subset(OIOVOCAB_IRI_PREFIX, "SubsetProperty", "subset_property", OboFormatTag.TAG_SUBSETDEF.getTag()),
        /**IRI_OIO_SynonymType. */        IRI_OIO_SynonymType(OIOVOCAB_IRI_PREFIX, "SynonymTypeProperty", "synonym_type_property", OboFormatTag.TAG_SYNONYMTYPEDEF.getTag()),
        /**IRI_OIO_NamespaceIdRule. */    IRI_OIO_NamespaceIdRule(OIOVOCAB_IRI_PREFIX, "NamespaceIdRule", "namespace-id-rule", OboFormatTag.TAG_NAMESPACE_ID_RULE.getTag()),
        /**IRI_OIO_LogicalDefinitionViewRelation. */          IRI_OIO_LogicalDefinitionViewRelation(OIOVOCAB_IRI_PREFIX, "logical-definition-view-relation", "logical-definition-view-relation", OboFormatTag.TAG_LOGICAL_DEFINITION_VIEW_RELATION.getTag());
        //@formatter:on
        final @Nonnull IRI iri;
        final @Nonnull String namespace;
        final @Nonnull String shortName;
        final @Nonnull String label;
        final @Nonnull String mappedTag;

        Obo2OWLVocabulary(String namespce, String shortName, String label, String mappedTag) {
            iri = IRI.create(namespce + shortName);
            this.shortName = shortName;
            namespace = namespce;
            this.label = label;
            this.mappedTag = mappedTag;
        }

        /**
         * @return short name
         */
        public String getShortName() {
            return shortName;
        }

        /**
         * @return namespace
         */
        public String getNamespace() {
            return namespace;
        }

        @Override
        public IRI getIRI() {
            return iri;
        }

        /**
         * @return label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @return mapped tag
         */
        public String getMappedTag() {
            return mappedTag;
        }

        /**
         * @param e
         *        entity to check
         * @return true if e has the same iri as the enum value, false if e is
         *         null or has a different iri
         */
        public boolean sameIRI(@Nullable OWLEntity e) {
            // if a null value is passed in, then no match
            if (e == null) {
                return false;
            }
            return iri.equals(e.getIRI());
        }
    }

    private static final Map<String, Obo2OWLVocabulary> TAGSTOVOCAB = Maps
            .uniqueIndex(Arrays.asList(Obo2OWLVocabulary.values()), v -> v.mappedTag);

    /**
     * @param tag
     *        tag
     * @return obj for tag
     */
    public static @Nullable Obo2OWLVocabulary getVocabularyObj(String tag) {
        return TAGSTOVOCAB.get(tag);
    }
}
