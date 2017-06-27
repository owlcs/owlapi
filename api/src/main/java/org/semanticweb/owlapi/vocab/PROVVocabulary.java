package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.*;

import static org.semanticweb.owlapi.model.EntityType.*;

/**
 * @author Alex To, The University Of Sydney, School of Engineering & Information Technologies
 * @since 5.1.0
 */
public enum PROVVocabulary implements HasShortForm, HasIRI, HasPrefixedName {

    // Prov Vocab http://www.w3.org/ns/prov#

    PROV_ACTIVITY(Namespaces.PROV, "Activity", CLASS),

    PROV_AGENT(Namespaces.PROV, "Agent", CLASS),

    PROV_ORGANIZATION(Namespaces.PROV, "Organization", CLASS),

    PROV_PERSON(Namespaces.PROV, "Person", CLASS),

    PROV_SOFTWARE_AGENT(Namespaces.PROV, "SoftwareAgent", CLASS),

    PROV_ENTITY(Namespaces.PROV, "Entity", CLASS),

    PROV_BUNDLE(Namespaces.PROV, "Bundle", CLASS),

    PROV_COLLECTION(Namespaces.PROV, "Collection", CLASS),

    PROV_EMPTY_COLLECTION(Namespaces.PROV, "EmptyCollection", CLASS),

    PROV_PLAN(Namespaces.PROV, "Plan", CLASS),

    PROV_INFLUENCE(Namespaces.PROV, "Influence", CLASS),

    PROV_ACTIVITY_INFLUENCE(Namespaces.PROV, "ActivityInfluence", CLASS),

    PROV_COMMUNICATION(Namespaces.PROV, "Communication", CLASS),

    PROV_GENERATION(Namespaces.PROV, "Generation", CLASS),

    PROV_INVALIDATION(Namespaces.PROV, "Invalidation", CLASS),

    PROV_AGENT_INFLUENCE(Namespaces.PROV, "AgentInfluence", CLASS),

    PROV_ASSOCIATION(Namespaces.PROV, "Association", CLASS),

    PROV_ATTRIBUTION(Namespaces.PROV, "Attribution", CLASS),

    PROV_DELEGATION(Namespaces.PROV, "Delegation", CLASS),

    PROV_ENTITY_INFLUENCE(Namespaces.PROV, "EntityInfluence", CLASS),

    PROV_DERIVATION(Namespaces.PROV, "Derivation", CLASS),

    PROV_PRIMARY_SOURCE(Namespaces.PROV, "PrimarySource", CLASS),

    PROV_QUOTATION(Namespaces.PROV, "Quotation", CLASS),

    PROV_REVISION(Namespaces.PROV, "Revision", CLASS),

    PROV_END(Namespaces.PROV, "End", CLASS),

    PROV_START(Namespaces.PROV, "Start", CLASS),

    PROV_USAGE(Namespaces.PROV, "Usage", CLASS),

    PROV_INSTANTANEOUS_EVENT(Namespaces.PROV, "InstantaneousEvent", CLASS),

    PROV_LOCATION(Namespaces.PROV, "Location", CLASS),

    PROV_ROLE(Namespaces.PROV, "Role", CLASS),

    PROV_ALTERNATE_OF(Namespaces.PROV, "alternateOf", OBJECT_PROPERTY),

    PROV_AT_LOCATION(Namespaces.PROV, "atLocation", OBJECT_PROPERTY);



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
