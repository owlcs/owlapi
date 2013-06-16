package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.Namespaces;

class DeprecatedVocabulary {
    private static final String OWL = Namespaces.OWL.toString();
    /** http://www.w3.org/2002/07/owl#OntologyProperty **/
    static IRI OWL_ONTOLOGY_PROPERTY = IRI.create(OWL, "OntologyProperty");
    /** http://www.w3.org/2002/07/owl#AntisymmetricProperty **/
    static IRI OWL_ANTI_SYMMETRIC_PROPERTY = IRI.create(OWL, "AntisymmetricProperty");
    /** http://www.w3.org/2002/07/owl#DataRestriction **/
    static IRI OWL_DATA_RESTRICTION = IRI.create(OWL, "DataRestriction");
    /** http://www.w3.org/2002/07/owl#ObjectRestriction **/
    static IRI OWL_OBJECT_RESTRICTION = IRI.create(OWL, "ObjectRestriction");
    /** http://www.w3.org/2002/07/owl#SelfRestriction **/
    static IRI OWL_SELF_RESTRICTION = IRI.create(OWL, "SelfRestriction");
    /** http://www.w3.org/2002/07/owl#declaredAs **/
    static IRI OWL_DECLARED_AS = IRI.create(OWL, "declaredAs");
    /** http://www.w3.org/2002/07/owl#NegativeObjectPropertyAssertion **/
    static IRI OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION = IRI.create(OWL,
            "NegativeObjectPropertyAssertion");
    /** http://www.w3.org/2002/07/owl#NegativeDataPropertyAssertion **/
    static IRI OWL_NEGATIVE_DATA_PROPERTY_ASSERTION = IRI.create(OWL,
            "NegativeDataPropertyAssertion");
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#subject **/
    static IRI RDF_SUBJECT = IRI.create(Namespaces.RDF.toString(), "subject");
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate **/
    static IRI RDF_PREDICATE = IRI.create(Namespaces.RDF.toString(), "predicate");
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#object **/
    static IRI RDF_OBJECT = IRI.create(Namespaces.RDF.toString(), "object");
    /** http://www.w3.org/2002/07/owl#subject **/
    static IRI OWL_SUBJECT = IRI.create(OWL, "subject");
    /** http://www.w3.org/2002/07/owl#predicate **/
    static IRI OWL_PREDICATE = IRI.create(OWL, "predicate");
    /** http://www.w3.org/2002/07/owl#object **/
    static IRI OWL_OBJECT = IRI.create(OWL, "object");
    /** http://www.w3.org/2002/07/owl#objectPropertyDomain **/
    static IRI OWL_OBJECT_PROPERTY_DOMAIN = IRI.create(OWL, "objectPropertyDomain");
    /** http://www.w3.org/2002/07/owl#dataPropertyDomain **/
    static IRI OWL_DATA_PROPERTY_DOMAIN = IRI.create(OWL, "dataPropertyDomain");
    /** http://www.w3.org/2002/07/owl#dataPropertyRange **/
    static IRI OWL_DATA_PROPERTY_RANGE = IRI.create(OWL, "dataPropertyRange");
    /** http://www.w3.org/2002/07/owl#objectPropertyRange **/
    static IRI OWL_OBJECT_PROPERTY_RANGE = IRI.create(OWL, "objectPropertyRange");
    /** http://www.w3.org/2002/07/owl#subObjectPropertyOf **/
    static IRI OWL_SUB_OBJECT_PROPERTY_OF = IRI.create(OWL, "subObjectPropertyOf");
    /** http://www.w3.org/2002/07/owl#subDataPropertyOf **/
    static IRI OWL_SUB_DATA_PROPERTY_OF = IRI.create(OWL, "subDataPropertyOf");
    /** http://www.w3.org/2002/07/owl#disjointDataProperties **/
    static IRI OWL_DISJOINT_DATA_PROPERTIES = IRI.create(OWL, "disjointDataProperties");
    /** http://www.w3.org/2002/07/owl#disjointObjectProperties **/
    static IRI OWL_DISJOINT_OBJECT_PROPERTIES = IRI.create(OWL,
            "disjointObjectProperties");
    /** http://www.w3.org/2002/07/owl#equivalentDataProperty **/
    static IRI OWL_EQUIVALENT_DATA_PROPERTIES = IRI.create(OWL, "equivalentDataProperty");
    /** http://www.w3.org/2002/07/owl#equivalentObjectProperty **/
    static IRI OWL_EQUIVALENT_OBJECT_PROPERTIES = IRI.create(OWL,
            "equivalentObjectProperty");
    /** http://www.w3.org/2002/07/owl#FunctionalDataProperty **/
    static IRI OWL_FUNCTIONAL_DATA_PROPERTY = IRI.create(OWL, "FunctionalDataProperty");
    /** http://www.w3.org/2002/07/owl#FunctionalObjectProperty **/
    static IRI OWL_FUNCTIONAL_OBJECT_PROPERTY = IRI.create(OWL,
            "FunctionalObjectProperty");
    /** http://www.w3.org/2002/07/owl#propertyChain **/
    static IRI OWL_PROPERTY_CHAIN = IRI.create(OWL, "propertyChain");
}
