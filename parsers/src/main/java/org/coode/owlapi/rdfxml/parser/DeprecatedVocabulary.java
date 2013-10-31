package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.Namespaces;

class DeprecatedVocabulary {
    //@formatter:off
    private static final String OWL = Namespaces.OWL.toString();
    /** http://www.w3.org/2002/07/owl#OntologyProperty **/
    public static final IRI OWL_ONTOLOGY_PROPERTY = IRI.create(OWL, "OntologyProperty");
    /** http://www.w3.org/2002/07/owl#AntisymmetricProperty **/
    public static final IRI OWL_ANTI_SYMMETRIC_PROPERTY = IRI.create(OWL, "AntisymmetricProperty");
    /** http://www.w3.org/2002/07/owl#DataRestriction **/
    public static final IRI OWL_DATA_RESTRICTION = IRI.create(OWL, "DataRestriction");
    /** http://www.w3.org/2002/07/owl#ObjectRestriction **/
    public static final IRI OWL_OBJECT_RESTRICTION = IRI.create(OWL, "ObjectRestriction");
    /** http://www.w3.org/2002/07/owl#SelfRestriction **/
    public static final IRI OWL_SELF_RESTRICTION = IRI.create(OWL, "SelfRestriction");
    /** http://www.w3.org/2002/07/owl#declaredAs **/
    public static final IRI OWL_DECLARED_AS = IRI.create(OWL, "declaredAs");
    /** http://www.w3.org/2002/07/owl#NegativeObjectPropertyAssertion **/
    public static final IRI OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION = IRI.create(OWL, "NegativeObjectPropertyAssertion");
    /** http://www.w3.org/2002/07/owl#NegativeDataPropertyAssertion **/
    public static final IRI OWL_NEGATIVE_DATA_PROPERTY_ASSERTION = IRI.create(OWL, "NegativeDataPropertyAssertion");
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#subject **/
    public static final IRI RDF_SUBJECT = IRI.create(Namespaces.RDF.toString(), "subject");
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate **/
    public static final IRI RDF_PREDICATE = IRI.create(Namespaces.RDF.toString(), "predicate");
    /** http://www.w3.org/1999/02/22-rdf-syntax-ns#object **/
    public static final IRI RDF_OBJECT = IRI.create(Namespaces.RDF.toString(), "object");
    /** http://www.w3.org/2002/07/owl#subject **/
    public static final IRI OWL_SUBJECT = IRI.create(OWL, "subject");
    /** http://www.w3.org/2002/07/owl#predicate **/
    public static final IRI OWL_PREDICATE = IRI.create(OWL, "predicate");
    /** http://www.w3.org/2002/07/owl#object **/
    public static final IRI OWL_OBJECT = IRI.create(OWL, "object");
    /** http://www.w3.org/2002/07/owl#objectPropertyDomain **/
    public static final IRI OWL_OBJECT_PROPERTY_DOMAIN = IRI.create(OWL, "objectPropertyDomain");
    /** http://www.w3.org/2002/07/owl#dataPropertyDomain **/
    public static final IRI OWL_DATA_PROPERTY_DOMAIN = IRI.create(OWL, "dataPropertyDomain");
    /** http://www.w3.org/2002/07/owl#dataPropertyRange **/
    public static final IRI OWL_DATA_PROPERTY_RANGE = IRI.create(OWL, "dataPropertyRange");
    /** http://www.w3.org/2002/07/owl#objectPropertyRange **/
    public static final IRI OWL_OBJECT_PROPERTY_RANGE = IRI.create(OWL, "objectPropertyRange");
    /** http://www.w3.org/2002/07/owl#subObjectPropertyOf **/
    public static final IRI OWL_SUB_OBJECT_PROPERTY_OF = IRI.create(OWL, "subObjectPropertyOf");
    /** http://www.w3.org/2002/07/owl#subDataPropertyOf **/
    public static final IRI OWL_SUB_DATA_PROPERTY_OF = IRI.create(OWL, "subDataPropertyOf");
    /** http://www.w3.org/2002/07/owl#disjointDataProperties **/
    public static final IRI OWL_DISJOINT_DATA_PROPERTIES = IRI.create(OWL, "disjointDataProperties");
    /** http://www.w3.org/2002/07/owl#disjointObjectProperties **/
    public static final IRI OWL_DISJOINT_OBJECT_PROPERTIES = IRI.create(OWL, "disjointObjectProperties");
    /** http://www.w3.org/2002/07/owl#equivalentDataProperty **/
    public static final IRI OWL_EQUIVALENT_DATA_PROPERTIES = IRI.create(OWL, "equivalentDataProperty");
    /** http://www.w3.org/2002/07/owl#equivalentObjectProperty **/
    public static final IRI OWL_EQUIVALENT_OBJECT_PROPERTIES = IRI.create(OWL, "equivalentObjectProperty");
    /** http://www.w3.org/2002/07/owl#FunctionalDataProperty **/
    public static final IRI OWL_FUNCTIONAL_DATA_PROPERTY = IRI.create(OWL, "FunctionalDataProperty");
    /** http://www.w3.org/2002/07/owl#FunctionalObjectProperty **/
    public static final IRI OWL_FUNCTIONAL_OBJECT_PROPERTY = IRI.create(OWL, "FunctionalObjectProperty");
    /** http://www.w3.org/2002/07/owl#propertyChain **/
    public static final IRI OWL_PROPERTY_CHAIN = IRI.create(OWL, "propertyChain");
    //@formatter:on
}
