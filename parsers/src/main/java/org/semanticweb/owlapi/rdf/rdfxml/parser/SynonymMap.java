package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.util.CollectionFactory.createMap;
import static org.semanticweb.owlapi.vocab.Namespaces.OWL;
import static org.semanticweb.owlapi.vocab.Namespaces.OWL11;
import static org.semanticweb.owlapi.vocab.Namespaces.OWL2;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_SOURCE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANNOTATED_TARGET;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ANTI_SYMMETRIC_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ASYMMETRIC_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_COMPLEMENT_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DATA_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISJOINT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_DISJOINT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_DATA_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_EQUIVALENT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_FUNCTIONAL_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_FUNCTIONAL_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NEGATIVE_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT_PROPERTY_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_OBJECT_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_ON_CLASS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_PREDICATE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_PROPERTY_DISJOINT_WITH;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_RESTRICTION;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SUBJECT;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SUB_DATA_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_SUB_OBJECT_PROPERTY_OF;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DATATYPE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_DOMAIN;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_RANGE;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_SUB_PROPERTY_OF;

import java.util.Map;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

class SynonymMap {

    private final Map<IRI, IRI> synonymMap = createMap();
    private final boolean strict;

    public SynonymMap(boolean strict) {
        this.strict = strict;
        // We can load legacy ontologies by providing synonyms for built in
        // vocabulary (e.g. DAML+OIL -> OWL)
        synonymMap.clear();
        // Legacy protege-owlapi representation of QCRs
        synonymMap.put(IRI.create(OWL.getPrefixIRI(), "valuesFrom"), OWL_ON_CLASS.getIRI());
        if (!strict) {
            OWLRDFVocabulary.DAML_COMPATIBILITY
                .forEach(x -> synonymMap.put(x.getDAMLIRI(), x.getIRI()));
            addIntermediateOWLSpecVocabulary();
        }
    }

    protected IRI getSynonym(IRI original) {
        if (!strict) {
            IRI synonymIRI = synonymMap.get(original);
            if (synonymIRI != null) {
                return synonymIRI;
            }
        }
        return original;
    }

    /**
     * There may be some ontologies floating about that use early versions of the OWL 1.1
     * vocabulary. We can map early versions of the vocabulary to the current OWL 1.1 vocabulary.
     */
    private void addIntermediateOWLSpecVocabulary() {
        Stream.of(OWLRDFVocabulary.values()).forEach(this::addLegacyMapping);
        Stream.of(OWLFacet.values()).forEach(v -> Stream.of(OWL, OWL11, OWL2)
            .forEach(p -> synonymMap.put(IRI.create(p.getPrefixIRI(), v.getShortForm()),
                v.getIRI())));
        synonymMap.put(OWL_NEGATIVE_DATA_PROPERTY_ASSERTION.getIRI(),
            OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        synonymMap.put(OWL_NEGATIVE_OBJECT_PROPERTY_ASSERTION.getIRI(),
            OWL_NEGATIVE_PROPERTY_ASSERTION.getIRI());
        // Intermediate OWL 2 spec
        synonymMap.put(OWL_SUBJECT.getIRI(), OWL_ANNOTATED_SOURCE.getIRI());
        synonymMap.put(OWL_PREDICATE.getIRI(), OWL_ANNOTATED_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT.getIRI(), OWL_ANNOTATED_TARGET.getIRI());
        // Preliminary OWL 1.1 Vocab
        synonymMap.put(IRI.create(OWL.getPrefixIRI(), "cardinalityType"), OWL_ON_CLASS.getIRI());
        synonymMap.put(IRI.create(OWL.getPrefixIRI(), "dataComplementOf"),
            OWL_COMPLEMENT_OF.getIRI());
        synonymMap.put(OWL_ANTI_SYMMETRIC_PROPERTY.getIRI(), OWL_ASYMMETRIC_PROPERTY.getIRI());
        synonymMap.put(OWL_FUNCTIONAL_DATA_PROPERTY.getIRI(), OWL_FUNCTIONAL_PROPERTY.getIRI());
        synonymMap.put(OWL_FUNCTIONAL_OBJECT_PROPERTY.getIRI(), OWL_FUNCTIONAL_PROPERTY.getIRI());
        synonymMap.put(OWL_SUB_DATA_PROPERTY_OF.getIRI(), RDFS_SUB_PROPERTY_OF.getIRI());
        synonymMap.put(OWL_SUB_OBJECT_PROPERTY_OF.getIRI(), RDFS_SUB_PROPERTY_OF.getIRI());
        synonymMap.put(OWL_OBJECT_PROPERTY_RANGE.getIRI(), RDFS_RANGE.getIRI());
        synonymMap.put(OWL_DATA_PROPERTY_RANGE.getIRI(), RDFS_RANGE.getIRI());
        synonymMap.put(OWL_OBJECT_PROPERTY_DOMAIN.getIRI(), RDFS_DOMAIN.getIRI());
        synonymMap.put(OWL_DATA_PROPERTY_DOMAIN.getIRI(), RDFS_DOMAIN.getIRI());
        synonymMap.put(OWL_DISJOINT_DATA_PROPERTIES.getIRI(), OWL_PROPERTY_DISJOINT_WITH.getIRI());
        synonymMap.put(OWL_DISJOINT_OBJECT_PROPERTIES.getIRI(),
            OWL_PROPERTY_DISJOINT_WITH.getIRI());
        synonymMap.put(OWL_EQUIVALENT_DATA_PROPERTIES.getIRI(), OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(OWL_EQUIVALENT_OBJECT_PROPERTIES.getIRI(), OWL_EQUIVALENT_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT_RESTRICTION.getIRI(), OWL_RESTRICTION.getIRI());
        synonymMap.put(OWL_DATA_RESTRICTION.getIRI(), OWL_RESTRICTION.getIRI());
        synonymMap.put(OWL_DATA_RANGE.getIRI(), RDFS_DATATYPE.getIRI());
        synonymMap.put(OWL_SUBJECT.getIRI(), OWL_ANNOTATED_SOURCE.getIRI());
        synonymMap.put(OWL_PREDICATE.getIRI(), OWL_ANNOTATED_PROPERTY.getIRI());
        synonymMap.put(OWL_OBJECT.getIRI(), OWL_ANNOTATED_TARGET.getIRI());
    }

    private void addLegacyMapping(OWLRDFVocabulary v) {
        // Map OWL11 to OWL
        // Map OWL2 to OWL
        synonymMap.put(IRI.create(OWL2.getPrefixIRI(), v.getShortForm()), v.getIRI());
        synonymMap.put(IRI.create(OWL11.getPrefixIRI(), v.getShortForm()), v.getIRI());
    }
}
