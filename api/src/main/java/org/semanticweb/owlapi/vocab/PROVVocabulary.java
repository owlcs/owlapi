package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.*;

import static org.semanticweb.owlapi.model.EntityType.*;

/**
 * @author Alex To, The University Of Sydney, Falcuty of Engineering & Information Technologies
 * @since 5.1.0
 */
public enum PROVVocabulary implements HasShortForm, HasIRI, HasPrefixedName {

    // Prov Vocab http://www.w3.org/ns/prov#

    ACTIVITY_CLASS(Namespaces.PROV, "Activity", CLASS),

    AGENT_CLASS(Namespaces.PROV, "Agent", CLASS),

    ORGANIZATION(Namespaces.PROV, "Organization", CLASS),

    PERSON(Namespaces.PROV, "Person", CLASS),

    SOFTWARE_AGENT(Namespaces.PROV, "SoftwareAgent", CLASS),

    ENTITY_CLASS(Namespaces.PROV, "Entity", CLASS),

    BUNDLE(Namespaces.PROV, "Bundle", CLASS),

    COLLECTION(Namespaces.PROV, "Collection", CLASS),

    EMPTY_COLLECTION(Namespaces.PROV, "EmptyCollection", CLASS),

    PLAN(Namespaces.PROV, "Plan", CLASS),

    INFLUENCE(Namespaces.PROV, "Influence", CLASS),

    ACTIVITY_INFLUENCE(Namespaces.PROV, "ActivityInfluence", CLASS),

    COMMUNICATION(Namespaces.PROV, "Communication", CLASS),

    GENERATION(Namespaces.PROV, "Generation", CLASS),

    INVALIDATION(Namespaces.PROV, "Invalidation", CLASS),

    AGENT_INFLUENCE(Namespaces.PROV, "AgentInfluence", CLASS),

    ASSOCIATION(Namespaces.PROV, "Association", CLASS),

    ATTRIBUTION(Namespaces.PROV, "Attribution", CLASS),

    DELEGATION(Namespaces.PROV, "Delegation", CLASS),

    ENTITY_INFLUENCE(Namespaces.PROV, "EntityInfluence", CLASS),

    DERIVATION(Namespaces.PROV, "Derivation", CLASS),

    PRIMARY_SOURCE(Namespaces.PROV, "PrimarySource", CLASS),

    QUOTATION(Namespaces.PROV, "Quotation", CLASS),

    REVISION(Namespaces.PROV, "Revision", CLASS),

    END(Namespaces.PROV, "End", CLASS),

    START(Namespaces.PROV, "Start", CLASS),

    USAGE(Namespaces.PROV, "Usage", CLASS),

    INSTANTANEOUS_EVENT(Namespaces.PROV, "InstantaneousEvent", CLASS),

    LOCATION(Namespaces.PROV, "Location", CLASS),

    ROLE(Namespaces.PROV, "Role", CLASS),

    ALTERNATE_OF(Namespaces.PROV, "alternateOf", OBJECT_PROPERTY),

    AT_LOCATION(Namespaces.PROV, "atLocation", OBJECT_PROPERTY),

    HAD_ACTIVITY(Namespaces.PROV, "hadActivity", OBJECT_PROPERTY),

    HAD_GENERATION(Namespaces.PROV, "hadGeneration", OBJECT_PROPERTY),

    HAD_PLAN(Namespaces.PROV, "hadPlan", OBJECT_PROPERTY),

    HAD_ROLE(Namespaces.PROV, "hadRole", OBJECT_PROPERTY),

    HAD_USAGE(Namespaces.PROV, "hadUsage", OBJECT_PROPERTY),

    INFLUENCED(Namespaces.PROV, "influenced", OBJECT_PROPERTY),

    GENERATED(Namespaces.PROV, "generated", OBJECT_PROPERTY),

    INVALIDATED(Namespaces.PROV, "invalidated", OBJECT_PROPERTY),

    INFLUENCER(Namespaces.PROV, "influencer", OBJECT_PROPERTY),

    ACTIVITY_PROPERTY(Namespaces.PROV, "activity", OBJECT_PROPERTY),

    AGENT_PROPERTY(Namespaces.PROV, "agent", OBJECT_PROPERTY),

    ENTITY_PROPERTY(Namespaces.PROV, "entity", OBJECT_PROPERTY),

    QUALIFIED_INFLUENCE(Namespaces.PROV, "qualifiedInfluence", OBJECT_PROPERTY),

    QUALIFIED_ASSOCIATION(Namespaces.PROV, "qualifiedAssociation", OBJECT_PROPERTY),

    QUALIFIED_ATTRIBUTION(Namespaces.PROV, "qualifiedAttribution", OBJECT_PROPERTY),

    QUALIFIED_COMMUNICATION(Namespaces.PROV, "qualifiedCommunication", OBJECT_PROPERTY),

    QUALIFIED_DELEGATION(Namespaces.PROV, "qualifiedDelegation", OBJECT_PROPERTY),

    QUALIFIED_DERIVATION(Namespaces.PROV, "qualifiedDerivation", OBJECT_PROPERTY),

    QUALIFIED_END(Namespaces.PROV, "qualifiedEnd", OBJECT_PROPERTY),

    QUALIFIED_GENERATION(Namespaces.PROV, "qualifiedGeneration", OBJECT_PROPERTY),

    QUALIFIED_INVALIDATION(Namespaces.PROV, "qualifiedInvalidation", OBJECT_PROPERTY),

    QUALIFIED_PRIMARY_SOURCE(Namespaces.PROV, "qualifiedPrimarySource", OBJECT_PROPERTY),

    QUALIFIED_QUOTATION(Namespaces.PROV, "qualifiedQuotation", OBJECT_PROPERTY),

    QUALIFIED_REVISION(Namespaces.PROV, "qualifiedRevision", OBJECT_PROPERTY),

    QUALIFIED_START(Namespaces.PROV, "qualifiedStart", OBJECT_PROPERTY),

    QUALIFIED_USAGE(Namespaces.PROV, "qualifiedUsage", OBJECT_PROPERTY),

    WAS_INFLUENCED_BY(Namespaces.PROV, "wasInfluencedBy", OBJECT_PROPERTY),

    ACTED_ON_BEHALF_OF(Namespaces.PROV, "actedOnBehalfOf", OBJECT_PROPERTY),

    HAD_MEMBER(Namespaces.PROV, "hadMember", OBJECT_PROPERTY),

    USED(Namespaces.PROV, "used", OBJECT_PROPERTY),

    WAS_ASSOCIATED_WITH(Namespaces.PROV, "wasAssociatedWith", OBJECT_PROPERTY),

    WAS_ATTRIBUTED_TO(Namespaces.PROV, "wasAttributedTo", OBJECT_PROPERTY),

    WAS_DERIVED_FROM(Namespaces.PROV, "wasDerivedFrom", OBJECT_PROPERTY),

    HAD_PRIMARY_SOURCE(Namespaces.PROV, "hadPrimarySource", OBJECT_PROPERTY),

    WAS_QUOTED_FROM(Namespaces.PROV, "wasQuotedFrom", OBJECT_PROPERTY),

    WAS_REVISION_OF(Namespaces.PROV, "wasRevisionOf", OBJECT_PROPERTY),

    WAS_ENDED_BY(Namespaces.PROV, "wasEndedBy", OBJECT_PROPERTY),

    WAS_GENERATED_BY(Namespaces.PROV, "wasGeneratedBy", OBJECT_PROPERTY),

    WAS_INFORMED_BY(Namespaces.PROV, "wasInformedBy", OBJECT_PROPERTY),

    WAS_INVALIDATED_BY(Namespaces.PROV, "wasInvalidatedBy", OBJECT_PROPERTY),

    WAS_STARTED_BY(Namespaces.PROV, "wasStartedBy", OBJECT_PROPERTY),

    AT_TIME(Namespaces.PROV, "atTime", DATA_PROPERTY),

    ENDED_AT_TIME(Namespaces.PROV, "endedAtTime", DATA_PROPERTY),

    GENERATED_AT_TIME(Namespaces.PROV, "generatedAtTime", DATA_PROPERTY),

    INVALIDATED_AT_TIME(Namespaces.PROV, "invalidatedAtTime", DATA_PROPERTY),

    STARTED_AT_TIME(Namespaces.PROV, "startedAtTime", DATA_PROPERTY),

    VALUE(Namespaces.PROV, "value", DATA_PROPERTY);

    private final IRI iri;
    private final Namespaces namespace;
    private final String shortName;
    private final String prefixedName;
    private final EntityType<?> entityType;

    PROVVocabulary(Namespaces namespace, String shortName, EntityType<?> entityType) {
        this.namespace = namespace;
        this.shortName = shortName;
        this.prefixedName = namespace.getPrefixName() + ':' + shortName;
        this.iri = IRI.create(namespace.toString(), shortName);
        this.entityType = entityType;
    }

    @Override
    public IRI getIRI() {
        return this.iri;
    }

    @Override
    public String getPrefixedName() {
        return this.prefixedName;
    }

    @Override
    public String getShortForm() {
        return this.shortName;
    }

    public Namespaces getNamespace() {
        return this.namespace;
    }

    public EntityType<?> getEntityType() {
        return this.entityType;
    }
}
