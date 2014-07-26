/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coode.owlapi.obo12.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br>
 * <br>
 */
@SuppressWarnings("javadoc")
public enum OBOVocabulary {
    DATA_VERSION("data-version"),
    VERSION("version"),
    DATE("date", DublinCoreVocabulary.DATE.getIRI()),
    SAVED_BY("saved-by"),
    AUTO_GENERATED_BY("auto-generated-by"),
    ONTOLOGY("ontology"),
    SUBSETDEF("subsetdef", OBOPrefix.OBO_IN_OWL, "SubsetProperty"),
    IMPORT("import"),
    SYNONYM_TYPE_DEF(
            "synonymtypedef",
            OBOPrefix.OBO_IN_OWL,
            "SynonymTypeProperty"),
    SYNONYM_TYPE("synonym-type", OBOPrefix.OBO_IN_OWL, "hasSynonymType"),
    ID_SPACE("id_space"),
    DEFAULT_RELATIONSHIP_ID_PREFIX("default-relationship-id-prefix"),
    ID_MAPPING("id-mapping"),
    REMARK("remark"),
    ID("id"),
    NAME("name", OWLRDFVocabulary.RDFS_LABEL.getIRI()),
    FORMAT_VERSION("format-version"),
    TYPEDEF("Typedef"),
    ALT_ID("alt_id", OBOPrefix.OBO_IN_OWL, "hasAlternativeId"),
    SHORT_HAND("shorthand", OBOPrefix.OBO_IN_OWL, "shorthand"),
    ALT_NAME("alt_name"),
    NAMESPACE("namespace"),
    DEFAULT_NAMESPACE("default-namespace"),
    DEF("def"),
    COMMENT("comment", OWLRDFVocabulary.RDFS_COMMENT.getIRI()),
    SUBSET("subset", OBOPrefix.OBO_IN_OWL, "inSubset"),
    SYNONYM("synonym"),
    HAS_SCOPE("hasScope", OBOPrefix.OBO_IN_OWL),
    RELATED_SYNONYM("relatedSynonym", OBOPrefix.OBO_IN_OWL, "hasRelatedSynonym"),
    EXACT_SYNONYM("exactSynonym", OBOPrefix.OBO_IN_OWL, "hasExactSynonym"),
    BROAD_SYNONYM("broadSynonym", OBOPrefix.OBO_IN_OWL, "hasBroadSynonym"),
    NARROW_SYNONYM("narrowSynonym", OBOPrefix.OBO_IN_OWL, "hasNarrowSynonym"),
    XREF("xref", OBOPrefix.OBO_IN_OWL),
    XREF_ANALOGUE("xref_analogue"),
    XREF_UNKNOWN("xref_unk"),
    IS_A("is_a"),
    IS_OBSOLETE("is_obsolete", OWLRDFVocabulary.OWL_DEPRECATED.getIRI()),
    PART_OF("part_of"),
    RELATIONSHIP("relationship"),
    REPLACED_BY("replaced_by"),
    CONSIDER("consider", OBOPrefix.OBO_IN_OWL, "consider"),
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

    /**
     * @deprecated Use {@link #OBO_IRI_BASE}
     */
    @Deprecated
    public static final String ONTOLOGY_URI_BASE = "http://purl.org/obo/owl";
    /**
     * @deprecated Use {@link #OBO_IRI_BASE}
     */
    @Deprecated
    public static final String ANNOTATION_URI_BASE = OBOPrefix.OBO_IN_OWL
            .getPrefix();
    public static final String OBO_IRI_BASE = OBOPrefix.OBO.getPrefix();
    public static final String LEGACY_OBO_IRI_BASE = "http://purl.org/obo/owl/";//
    /**
     * The pattern for OBO IDs. Specified at <a
     * href="http://www.obofoundry.org/id-policy.shtml"
     * >http://www.obofoundry.org/id-policy.shtml</a>
     */
    public static final Pattern OBO_ID_PATTERN = Pattern
            .compile("(([^:]+):)?(.+)");
    private static final String bases = Pattern.quote(OBO_IRI_BASE) + "|"
            + Pattern.quote(ONTOLOGY_URI_BASE + "/") + "|"
            + Pattern.quote(LEGACY_OBO_IRI_BASE) + "|"
            + Pattern.quote(ANNOTATION_URI_BASE);
    public static final Pattern OBO_IRI_PATTERN = Pattern.compile("(" + bases
            + ")" + "(([^\\_]*)\\_)?([A-Za-z0-9\\_\\-]*)");
    private static final IDSpaceManager DEFAULT_ID_SPACE_MANAGER = new IDSpaceManager() {

        @Override
        public void setIRIPrefix(String idPrefix, String iriPrefix) {
            throw new RuntimeException(
                    "The default id space manager must not be used for custom prefixes.");
        }
    };

    /**
     * Converts OBO Ids to IRIs. The conversion is defined at <a
     * href="http://www.obofoundry.org/id-policy.shtml"
     * >http://www.obofoundry.org/id-policy.shtml</a>
     * 
     * @param oboId
     *        The Id to convert
     * @return The IRI of the converted Id
     */
    public static IRI ID2IRI(@Nonnull String oboId) {
        return ID2IRI(oboId, DEFAULT_ID_SPACE_MANAGER);
    }

    /**
     * Converts OBO Ids to IRIs. The conversion is defined at <a
     * href="http://www.obofoundry.org/id-policy.shtml"
     * >http://www.obofoundry.org/id-policy.shtml</a>.
     * 
     * @param oboId
     *        The OBO Id to convert.
     * @param idSpaceManager
     *        An {@link IDSpaceManager} which can be used to customise the IRI
     *        prefixes used in the conversion.
     * @return The IRI of the converted Id.
     */
    @SuppressWarnings("null")
    public static IRI ID2IRI(@Nonnull String oboId,
            IDSpaceManager idSpaceManager) {
        Matcher matcher = OBO_ID_PATTERN.matcher(oboId);
        if (matcher.matches()) {
            String idSpace = matcher.group(2);
            String localId = matcher.group(3);
            StringBuilder sb = new StringBuilder();
            String iriPrefix = idSpaceManager.getIRIPrefix(idSpace);
            sb.append(iriPrefix);
            if (idSpace != null) {
                sb.append(idSpace);
                sb.append("_");
            }
            sb.append(localId);
            return IRI.create(sb.toString());
        } else {
            return IRI.create(oboId);
        }
    }

    // Format of Foundry-compliant URIs
    // FOUNDRY_OBO_URI ::= "http://purl.obolibrary.org/obo/" IDSPACE "_" LOCALID
    // Format of OBO legacy URIs
    // Those are found in documents that were natively authored using the OBO
    // format and which were converted using the NCBOoboInOwl script before this
    // policy was put in place.
    // LEGACY_OBO_URI ::= "http://purl.org/obo/owl/" IDSPACE "#" IDSPACE "_"
    // LOCALID
    public static String IRI2ID(IRI oboIRI) {
        Matcher matcher = OBO_IRI_PATTERN.matcher(oboIRI.toString());
        if (matcher.matches()) {
            String idSpace = matcher.group(3);
            String localId = matcher.group(4);
            StringBuilder sb = new StringBuilder();
            if (idSpace != null) {
                sb.append(idSpace);
                sb.append(":");
            }
            sb.append(localId);
            return sb.toString();
        } else {
            throw new RuntimeException("Not an OBO IRI");
        }
    }

    public static boolean isOBOIRI(IRI oboIRI) {
        return OBO_ID_PATTERN.matcher(oboIRI.toString()).matches();
    }

    private static final List<OBOVocabulary> headerTags = Arrays.asList(
            FORMAT_VERSION, DATA_VERSION, DATE, SAVED_BY, AUTO_GENERATED_BY,
            SUBSETDEF, IMPORT, SYNONYM_TYPE_DEF, ID_SPACE,
            DEFAULT_RELATIONSHIP_ID_PREFIX, ID_MAPPING, REMARK);
    private static final List<OBOVocabulary> termStanzaTags = Arrays.asList(ID,
            NAME, NAMESPACE, ALT_ID, DEF, COMMENT, SUBSET, SYNONYM, XREF, IS_A,
            INTERSECTION_OF, UNION_OF, DISJOINT_FROM, RELATIONSHIP,
            IS_OBSOLETE, REPLACED_BY, CONSIDER);
    private static final List<OBOVocabulary> typeDefStanzaTags = Arrays.asList(
            ID, NAME, NAMESPACE, ALT_ID, DEF, COMMENT, SUBSET, SYNONYM, XREF,
            DOMAIN, RANGE, IS_ASYMMETRIC, IS_CYCLIC, IS_REFLEXIVE,
            IS_SYMMETRIC, IS_TRANSITIVE, IS_A, INVERSE, TRANSITIVE_OVER,
            RELATIONSHIP, IS_METADATA_TAG, IS_OBSOLETE, REPLACED_BY, CONSIDER);
    private static final List<OBOVocabulary> instanceStanzaTags = Arrays
            .asList(ID, NAME, NAMESPACE, ALT_ID, DEF, COMMENT, SYNONYM, XREF,
                    INSTANCE_OF, PROPERTY_VALUE, IS_OBSOLETE, REPLACED_BY,
                    CONSIDER);

    OBOVocabulary(@Nonnull String name) {
        this.name = name;
        iri = IRI.create(OBOPrefix.OBO.getPrefix() + name);
    }

    OBOVocabulary(@Nonnull String name, @Nonnull OBOPrefix prefix) {
        this.name = name;
        iri = IRI.create(prefix.getPrefix() + name);
    }

    OBOVocabulary(@Nonnull String name, OBOPrefix prefix, String localName) {
        this.name = name;
        iri = IRI.create(prefix.getPrefix() + localName);
    }

    OBOVocabulary(@Nonnull String name, @Nonnull IRI iri) {
        this.name = name;
        this.iri = iri;
    }

    @Nonnull
    private String name;
    @Nonnull
    private IRI iri;

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public IRI getIRI() {
        return iri;
    }

    @Override
    @Nonnull
    public String toString() {
        return name;
    }

    public static List<OBOVocabulary> getHeaderTags() {
        return new ArrayList<>(headerTags);
    }

    public static List<OBOVocabulary> getTermStanzaTags() {
        return new ArrayList<>(termStanzaTags);
    }

    public static List<OBOVocabulary> getTypeDefStanzaTags() {
        return new ArrayList<>(typeDefStanzaTags);
    }

    public static List<OBOVocabulary> getInstanceStanzaTags() {
        return new ArrayList<>(instanceStanzaTags);
    }
}
