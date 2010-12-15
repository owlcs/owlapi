package org.coode.owlapi.obo.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public enum OBOVocabulary {

    DATA_VERSION("data-version"),
    VERSION("version"),
    DATE("date"),
    SAVED_BY("saved-by"),
    AUTO_GENERATED_BY("auto-generated-by"),
    SUBSETDEF("subsetdef"),
    IMPORT("import"),
    SYNONYM_TYPE_DEF("synonymtypedef"),
    ID_SPACE("id_space"),
    DEFAULT_RELATIONSHIP_ID_PREFIX("default-relationship-id-prefix"),
    ID_MAPPING("id-mapping"),
    REMARK("remark"),
    ID("id"),
    NAME("name"),
    FORMAT_VERSION("format-version"),
    TYPEDEF("Typedef"),
    ALT_ID("alt_id"),
    ALT_NAME("alt_name"),
    NAMESPACE("namespace"),
    DEFAULT_NAMESPACE("default-namespace"),
    DEF("def"),
    COMMENT("comment"),
    SUBSET("subset"),
    SYNONYM("synonym"),
    RELATED_SYNONYM("related_synonym"),
    EXACT_SYNONYM("exact_synonym"),
    BROAD_SYNONYM("broad_synonym"),
    NARROW_SYNONYM("narrow_synonym"),
    XREF("xref"),
    XREF_ANALOGUE("xref_analogue"),
    XREF_UNKNOWN("xref_unk"),
    IS_A("is_a"),
    PART_OF("part_of"),
    RELATIONSHIP("relationship"),
    IS_OBSOLETE("is_obsolete"),
    REPLACED_BY("replaced_by"),
    CONSIDER("consider"),
    USE_TERM("use_term"),
    DOMAIN("domain"),
    RANGE("range"),
    IS_CYCLIC("is_cyclic"),
    IS_TRANSITIVE("is_transitive"),
    IS_SYMMETRIC("is_symmetric"),
    IS_ASYMMETRIC("is_asymmetric"),
    IS_REFLEXIVE("is_reflexive"),
    INVERSE("inverse"),
    TRANSITIVE_OVER("transitive_over"),
    INTERSECTION_OF("intersection_of"),
    UNION_OF("union_of"),
    DISJOINT_FROM("disjoint_from"),
    TERM("Term"),
    BUILTIN("builtin"),
    IS_METADATA_TAG("is_metadata_tag"),
    CARDINALITY("cardinality"),
    MAX_CARDINALITY("maxCardinality"),
    MIN_CARDINALITY("minCardinality"),
    INSTANCE("Instance"),
    INSTANCE_OF("instance_of"),
    PROPERTY_VALUE("property_value"),
    IS_ANONYMOUS("is_anonymous");


    public static final String ONTOLOGY_URI_BASE = "http://purl.org/obo/owlapi";

    
    public static final String ANNOTATION_URI_BASE = "http://www.geneontology.org/formats/oboInOwl";


    private static final List<OBOVocabulary> headerTags =
            Arrays.asList(FORMAT_VERSION, DATA_VERSION, DATE, SAVED_BY,
                          AUTO_GENERATED_BY, SUBSETDEF, IMPORT, SYNONYM_TYPE_DEF,
                          ID_SPACE, DEFAULT_RELATIONSHIP_ID_PREFIX, ID_MAPPING,
                          REMARK);


    private static final List<OBOVocabulary> termStanzaTags =
            Arrays.asList(ID, NAME, NAMESPACE, ALT_ID,
                          DEF, COMMENT, SUBSET, SYNONYM, XREF,
                          IS_A, INTERSECTION_OF, UNION_OF,
                          DISJOINT_FROM, RELATIONSHIP,
                          IS_OBSOLETE, REPLACED_BY, CONSIDER);


    private static final List<OBOVocabulary> typeDefStanzaTags =
            Arrays.asList(ID, NAME, NAMESPACE, ALT_ID,
                          DEF, COMMENT, SUBSET, SYNONYM, XREF,
                          DOMAIN, RANGE, IS_ASYMMETRIC,
                          IS_CYCLIC, IS_REFLEXIVE, IS_SYMMETRIC,
                          IS_TRANSITIVE, IS_A, INVERSE, TRANSITIVE_OVER,
                          RELATIONSHIP, IS_METADATA_TAG,
                          IS_OBSOLETE, REPLACED_BY, CONSIDER);


    private static final List<OBOVocabulary> instanceStanzaTags =
            Arrays.asList(ID, NAME, NAMESPACE, ALT_ID,
                          DEF, COMMENT, SYNONYM, XREF,
                          INSTANCE_OF, PROPERTY_VALUE,
                          IS_OBSOLETE, REPLACED_BY, CONSIDER);


    OBOVocabulary(String name) {
        this.name = name;
        iri = IRI.create(ANNOTATION_URI_BASE + "#" + name);
    }

    private String name;

    private IRI iri;


    public String getName() {
        return name;
    }


    public IRI getIRI() {
        return iri;
    }


    @Override
	public String toString() {
        return name;
    }


    public static List<OBOVocabulary> getHeaderTags() {
        return new ArrayList<OBOVocabulary>(headerTags);
    }


    public static List<OBOVocabulary> getTermStanzaTags() {
        return new ArrayList<OBOVocabulary>(termStanzaTags);
    }


    public static List<OBOVocabulary> getTypeDefStanzaTags() {
        return new ArrayList<OBOVocabulary>(typeDefStanzaTags);
    }


    public static List<OBOVocabulary> getInstanceStanzaTags() {
        return new ArrayList<OBOVocabulary>(instanceStanzaTags);
    }
}
