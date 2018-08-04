package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.model.EntityType.CLASS;
import static org.semanticweb.owlapi.model.EntityType.DATA_PROPERTY;
import static org.semanticweb.owlapi.model.EntityType.OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.Namespaces.PROV;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Alex To, The University Of Sydney, Falcuty of Engineering and Information Technologies
 * @since 5.1.0
 */
public enum PROVVocabulary implements HasShortForm, HasIRI, HasPrefixedName {

    // Prov Vocab http://www.w3.org/ns/prov#
    //@formatter:off
    /** http://www.w3.org/ns/prov#Activity.                 */ ACTIVITY_CLASS           (PROV, "Activity", CLASS),
    /** http://www.w3.org/ns/prov#Agent.                    */ AGENT_CLASS              (PROV, "Agent", CLASS),
    /** http://www.w3.org/ns/prov#Organization.             */ ORGANIZATION             (PROV, "Organization", CLASS),
    /** http://www.w3.org/ns/prov#Person.                   */ PERSON                   (PROV, "Person", CLASS),
    /** http://www.w3.org/ns/prov#SoftwareAgent.            */ SOFTWARE_AGENT           (PROV, "SoftwareAgent", CLASS),
    /** http://www.w3.org/ns/prov#Entity.                   */ ENTITY_CLASS             (PROV, "Entity", CLASS),
    /** http://www.w3.org/ns/prov#Bundle.                   */ BUNDLE                   (PROV, "Bundle", CLASS),
    /** http://www.w3.org/ns/prov#Collection.               */ COLLECTION               (PROV, "Collection", CLASS),
    /** http://www.w3.org/ns/prov#EmptyCollection.          */ EMPTY_COLLECTION         (PROV, "EmptyCollection", CLASS),
    /** http://www.w3.org/ns/prov#Plan.                     */ PLAN                     (PROV, "Plan", CLASS),
    /** http://www.w3.org/ns/prov#Influence.                */ INFLUENCE                (PROV, "Influence", CLASS),
    /** http://www.w3.org/ns/prov#ActivityInfluence.        */ ACTIVITY_INFLUENCE       (PROV, "ActivityInfluence", CLASS),
    /** http://www.w3.org/ns/prov#Communication.            */ COMMUNICATION            (PROV, "Communication", CLASS),
    /** http://www.w3.org/ns/prov#Generation.               */ GENERATION               (PROV, "Generation", CLASS),
    /** http://www.w3.org/ns/prov#Invalidation.             */ INVALIDATION             (PROV, "Invalidation", CLASS),
    /** http://www.w3.org/ns/prov#AgentInfluence.           */ AGENT_INFLUENCE          (PROV, "AgentInfluence", CLASS),
    /** http://www.w3.org/ns/prov#Association.              */ ASSOCIATION              (PROV, "Association", CLASS),
    /** http://www.w3.org/ns/prov#Attribution.              */ ATTRIBUTION              (PROV, "Attribution", CLASS),
    /** http://www.w3.org/ns/prov#Delegation.               */ DELEGATION               (PROV, "Delegation", CLASS),
    /** http://www.w3.org/ns/prov#EntityInfluence.          */ ENTITY_INFLUENCE         (PROV, "EntityInfluence", CLASS),
    /** http://www.w3.org/ns/prov#Derivation.               */ DERIVATION               (PROV, "Derivation", CLASS),
    /** http://www.w3.org/ns/prov#PrimarySource.            */ PRIMARY_SOURCE           (PROV, "PrimarySource", CLASS),
    /** http://www.w3.org/ns/prov#Quotation.                */ QUOTATION                (PROV, "Quotation", CLASS),
    /** http://www.w3.org/ns/prov#Revision.                 */ REVISION                 (PROV, "Revision", CLASS),
    /** http://www.w3.org/ns/prov#End.                      */ END                      (PROV, "End", CLASS),
    /** http://www.w3.org/ns/prov#Start.                    */ START                    (PROV, "Start", CLASS),
    /** http://www.w3.org/ns/prov#Usage.                    */ USAGE                    (PROV, "Usage", CLASS),
    /** http://www.w3.org/ns/prov#InstantaneousEvent.       */ INSTANTANEOUS_EVENT      (PROV, "InstantaneousEvent", CLASS),
    /** http://www.w3.org/ns/prov#Location.                 */ LOCATION                 (PROV, "Location", CLASS),
    /** http://www.w3.org/ns/prov#Role.                     */ ROLE                     (PROV, "Role", CLASS),
    /** http://www.w3.org/ns/prov#alternateOf.              */ ALTERNATE_OF             (PROV, "alternateOf", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#atLocation.               */ AT_LOCATION              (PROV, "atLocation", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#hadActivity.              */ HAD_ACTIVITY             (PROV, "hadActivity", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#hadGeneration.            */ HAD_GENERATION           (PROV, "hadGeneration", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#hadPlan.                  */ HAD_PLAN                 (PROV, "hadPlan", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#hadRole.                  */ HAD_ROLE                 (PROV, "hadRole", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#hadUsage.                 */ HAD_USAGE                (PROV, "hadUsage", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#influenced.               */ INFLUENCED               (PROV, "influenced", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#generated.                */ GENERATED                (PROV, "generated", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#invalidated.              */ INVALIDATED              (PROV, "invalidated", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#influencer.               */ INFLUENCER               (PROV, "influencer", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#activity.                 */ ACTIVITY_PROPERTY        (PROV, "activity", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#agent.                    */ AGENT_PROPERTY           (PROV, "agent", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#entity.                   */ ENTITY_PROPERTY          (PROV, "entity", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedInfluence.       */ QUALIFIED_INFLUENCE      (PROV, "qualifiedInfluence", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedAssociation.     */ QUALIFIED_ASSOCIATION    (PROV, "qualifiedAssociation", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedAttribution.     */ QUALIFIED_ATTRIBUTION    (PROV, "qualifiedAttribution", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedCommunication.   */ QUALIFIED_COMMUNICATION  (PROV, "qualifiedCommunication", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedDelegation.      */ QUALIFIED_DELEGATION     (PROV, "qualifiedDelegation", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedDerivation.      */ QUALIFIED_DERIVATION     (PROV, "qualifiedDerivation", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedEnd.             */ QUALIFIED_END            (PROV, "qualifiedEnd", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedGeneration.      */ QUALIFIED_GENERATION     (PROV, "qualifiedGeneration", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedInvalidation.    */ QUALIFIED_INVALIDATION   (PROV, "qualifiedInvalidation", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedPrimarySource.   */ QUALIFIED_PRIMARY_SOURCE (PROV, "qualifiedPrimarySource", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedQuotation.       */ QUALIFIED_QUOTATION      (PROV, "qualifiedQuotation", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedRevision.        */ QUALIFIED_REVISION       (PROV, "qualifiedRevision", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedStart.           */ QUALIFIED_START          (PROV, "qualifiedStart", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#qualifiedUsage.           */ QUALIFIED_USAGE          (PROV, "qualifiedUsage", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasInfluencedBy.          */ WAS_INFLUENCED_BY        (PROV, "wasInfluencedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#actedOnBehalfOf.          */ ACTED_ON_BEHALF_OF       (PROV, "actedOnBehalfOf", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#hadMember.                */ HAD_MEMBER               (PROV, "hadMember", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#used.                     */ USED                     (PROV, "used", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasAssociatedWith.        */ WAS_ASSOCIATED_WITH      (PROV, "wasAssociatedWith", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasAttributedTo.          */ WAS_ATTRIBUTED_TO        (PROV, "wasAttributedTo", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasDerivedFrom.           */ WAS_DERIVED_FROM         (PROV, "wasDerivedFrom", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#hadPrimarySource.         */ HAD_PRIMARY_SOURCE       (PROV, "hadPrimarySource", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasQuotedFrom.            */ WAS_QUOTED_FROM          (PROV, "wasQuotedFrom", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasRevisionOf.            */ WAS_REVISION_OF          (PROV, "wasRevisionOf", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasEndedBy.               */ WAS_ENDED_BY             (PROV, "wasEndedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasGeneratedBy.           */ WAS_GENERATED_BY         (PROV, "wasGeneratedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasInformedBy.            */ WAS_INFORMED_BY          (PROV, "wasInformedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasInvalidatedBy.         */ WAS_INVALIDATED_BY       (PROV, "wasInvalidatedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#wasStartedBy.             */ WAS_STARTED_BY           (PROV, "wasStartedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/ns/prov#atTime.                   */ AT_TIME                  (PROV, "atTime", DATA_PROPERTY),
    /** http://www.w3.org/ns/prov#endedAtTime.              */ ENDED_AT_TIME            (PROV, "endedAtTime", DATA_PROPERTY),
    /** http://www.w3.org/ns/prov#generatedAtTime.          */ GENERATED_AT_TIME        (PROV, "generatedAtTime", DATA_PROPERTY),
    /** http://www.w3.org/ns/prov#invalidatedAtTime.        */ INVALIDATED_AT_TIME      (PROV, "invalidatedAtTime", DATA_PROPERTY),
    /** http://www.w3.org/ns/prov#startedAtTime.            */ STARTED_AT_TIME          (PROV, "startedAtTime", DATA_PROPERTY),
    /** http://www.w3.org/ns/prov#value.                    */ VALUE                    (PROV, "value", DATA_PROPERTY);
  //@formatter:off
    private final IRI iri;
    private final Namespaces namespace;
    private final String shortName;
    private final String prefixedName;
    private final EntityType<?> entityType;

    PROVVocabulary(Namespaces namespace, String shortName, EntityType<?> entityType) {
        this.namespace = namespace;
        this.shortName = shortName;
        prefixedName = namespace.getPrefixName() + ':' + shortName;
        iri = IRI.create(namespace.toString(), shortName);
        this.entityType = entityType;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }

    @Override
    public String getShortForm() {
        return shortName;
    }

    /**
     * @return namespace
     */
    public Namespaces getNamespace() {
        return namespace;
    }

    /**
     * @return entity type
     */
    public EntityType<?> getEntityType() {
        return entityType;
    }
}
