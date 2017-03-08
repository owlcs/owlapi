package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.BUILT_IN_AP_IRIS;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_NOTHING;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_THING;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LITERAL;

import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;

class FoundIRIs {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoundIRIs.class);
    /**
     * IRIs that had a type triple to owl:Ontology
     */
    protected final Set<IRI> ontologyIRIs = createSet();
    protected final ArrayListMultimap<IRI, Class<?>> guessedDeclarations =
        ArrayListMultimap.create();
    // The set of IRIs that are either explicitly typed
    // an an owl:Class, or are inferred to be an owl:Class
    // because they are used in some triple whose predicate
    // has the domain or range of owl:Class
    private final Set<IRI> classIRIs = createSet();
    private final Set<IRI> objectPropertyIRIs = createSet();
    private final Set<IRI> dataPropertyIRIs = createSet();
    /**
     * Same as classExpressionIRIs but for rdf properties things neither typed as a data or object
     * property - bad!
     */
    private final Set<IRI> propertyIRIs = createSet();
    /**
     * Set of IRIs that are typed by non-system types and also owl:Thing
     */
    private final Set<IRI> individualIRIs = createSet();
    private final Set<IRI> annPropertyIRIs = createSet();
    /**
     * The annotation iris.
     */
    private final Set<IRI> annotationIRIs = createSet();
    /**
     * IRIs that had a type triple to rdfs:Datatange
     */
    private final Set<IRI> dataRangeIRIs = createSet();
    /**
     * IRIs that had a type triple to owl:Restriction
     */
    private final Set<IRI> restrictionIRIs = createSet();
    private final boolean strict;
    /**
     * The IRI of the first reource that is typed as an ontology
     */
    @Nullable
    protected IRI firstOntologyIRI;

    public FoundIRIs(boolean strict) {
        this.strict = strict;
        BUILT_IN_AP_IRIS.forEach(annPropertyIRIs::add);
        dataRangeIRIs.add(RDFS_LITERAL.getIRI());
        Stream.of(OWL2Datatype.values()).forEach(v -> dataRangeIRIs.add(v.getIRI()));
        if (!strict) {
            Stream.of(XSDVocabulary.values()).forEach(v -> dataRangeIRIs.add(v.getIRI()));
        }
        classIRIs.add(OWL_THING.getIRI());
        classIRIs.add(OWL_NOTHING.getIRI());
        objectPropertyIRIs.add(OWL_TOP_OBJECT_PROPERTY.getIRI());
        objectPropertyIRIs.add(OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
        dataPropertyIRIs.add(OWL_TOP_DATA_PROPERTY.getIRI());
        dataPropertyIRIs.add(OWL_BOTTOM_DATA_PROPERTY.getIRI());
    }

    /**
     * Imports closure changed. NOTE: This method only gets called when the ontology being parsed
     * adds a direct import. This is enough for resolving the imports closure.
     *
     * @param o ontology
     */
    public void importsClosureChanged(OWLOntology o) {
        o.annotationPropertiesInSignature(INCLUDED).forEach(e -> annPropertyIRIs.add(e.getIRI()));
        o.dataPropertiesInSignature(INCLUDED).forEach(e -> dataPropertyIRIs.add(e.getIRI()));
        o.objectPropertiesInSignature(INCLUDED).forEach(e -> objectPropertyIRIs.add(e.getIRI()));
        o.classesInSignature(INCLUDED).forEach(e -> classIRIs.add(e.getIRI()));
        o.datatypesInSignature(INCLUDED).forEach(e -> dataRangeIRIs.add(e.getIRI()));
        o.individualsInSignature(INCLUDED).forEach(e -> individualIRIs.add(e.getIRI()));
    }

    protected boolean isIndividual(IRI iri) {
        return individualIRIs.contains(iri);
    }

    protected void addRDFProperty(IRI iri) {
        propertyIRIs.add(iri);
    }

    protected boolean isRDFProperty(IRI iri) {
        return propertyIRIs.contains(iri);
    }

    /**
     * Adds the class expression.
     *
     * @param iri the iri
     * @param explicitlyTyped the explicitly typed
     */
    public void addClassExpression(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLClass.class, explicitlyTyped);
        addType(iri, classIRIs, explicitlyTyped);
    }

    private void updateGuesses(IRI iri, Class<?> class1, boolean explicitlyTyped) {
        if (explicitlyTyped && guessedDeclarations.containsKey(iri)) {
            // if an explicitly typed declaration has been added and there was a
            // guess for its type, replace it
            // Do not replace all guesses, as these might be due to punning
            guessedDeclarations.remove(iri, class1);
        }
        if (!explicitlyTyped) {
            guessedDeclarations.put(iri, class1);
        }
    }

    protected boolean isClassExpression(IRI iri) {
        return classIRIs.contains(iri);
    }

    /**
     * Adds the object property.
     *
     * @param iri the iri
     * @param explicitlyTyped the explicitly typed
     */
    public void addObjectProperty(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLObjectProperty.class, explicitlyTyped);
        addType(iri, objectPropertyIRIs, explicitlyTyped);
    }

    /**
     * Adds the data property.
     *
     * @param iri the iri
     * @param explicitlyTyped the explicitly typed
     */
    public void addDataProperty(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLDataProperty.class, explicitlyTyped);
        addType(iri, dataPropertyIRIs, explicitlyTyped);
    }

    /**
     * Adds the annotation property.
     *
     * @param iri the iri
     * @param explicitlyTyped the explicitly typed
     */
    protected void addAnnotationProperty(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLAnnotationProperty.class, explicitlyTyped);
        addType(iri, annPropertyIRIs, explicitlyTyped);
    }

    /**
     * Adds the data range.
     *
     * @param iri the iri
     * @param explicitlyTyped the explicitly typed
     */
    public void addDataRange(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLDataRange.class, explicitlyTyped);
        addType(iri, dataRangeIRIs, explicitlyTyped);
    }

    /**
     * Adds the owl named individual.
     *
     * @param iri the iri
     * @param explicitlyTyped the explicitly type
     */
    protected void addOWLNamedIndividual(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLNamedIndividual.class, explicitlyTyped);
        addType(iri, individualIRIs, explicitlyTyped);
    }

    /**
     * Adds the owl restriction.
     *
     * @param iri the iri
     * @param explicitlyTyped the explicitly typed
     */
    protected void addOWLRestriction(IRI iri, boolean explicitlyTyped) {
        updateGuesses(iri, OWLClassExpression.class, explicitlyTyped);
        addType(iri, restrictionIRIs, explicitlyTyped);
    }

    private void addType(IRI iri, Set<IRI> types, boolean explicitlyTyped) {
        if (strict && !explicitlyTyped) {
            LOGGER.warn("STRICT: Not adding implicit type iri={} types={}", iri, types);
            return;
        }
        types.add(iri);
    }

    protected boolean isRestriction(IRI iri) {
        return restrictionIRIs.contains(iri);
    }

    protected void addAnnotationIRI(IRI iri) {
        annotationIRIs.add(iri);
    }

    protected boolean isAnnotation(IRI iri) {
        return annotationIRIs.contains(iri);
    }

    /**
     * Determines if a given IRI is currently an object property IRI and not a data property IRI and
     * not an annotation property IRI. Note that this method is only guaranteed to return the same
     * value once all triples in the imports closure of the RDF graph being parsed have been parsed.
     *
     * @param iri The IRI to check.
     * @return {@code true} if the IRI is an object property IRI and not a data property IRI and not
     * an annotation property IRI. Otherwise, {@code false}.
     */
    protected boolean isObjectPropertyOnly(@Nullable IRI iri) {
        return iri != null && !isDP(iri) && !isAP(iri) && isOP(iri);
    }

    protected boolean isOP(IRI iri) {
        return objectPropertyIRIs.contains(iri);
    }

    /**
     * Determines if a given IRI is currently a data property IRI and not an object property IRI and
     * not an annotation property IRI. Note that this method is only guaranteed to return the same
     * value once all triples in the imports closure of the RDF graph being parsed have been parsed.
     *
     * @param iri The IRI to check.
     * @return {@code true} if the IRI is a data property IRI and not an object property IRI and not
     * an annotation property IRI. Otherwise, {@code false}.
     */
    protected boolean isDataPropertyOnly(@Nullable IRI iri) {
        return iri != null && !isOP(iri) && !isAP(iri) && isDP(iri);
    }

    protected boolean isDP(IRI iri) {
        return dataPropertyIRIs.contains(iri);
    }

    /**
     * Determines if a given IRI is currently an annotation property IRI and not a data property IRI
     * and not an object property IRI. Note that this method is only guaranteed to return the same
     * value once all triples in the imports closure of the RDF graph being parsed have been parsed.
     *
     * @param iri The IRI to check.
     * @return {@code true} if the IRI is an annotation property IRI and not a data property IRI and
     * not an object property IRI. Otherwise, {@code false}.
     */
    protected boolean isAnnotationPropertyOnly(@Nullable IRI iri) {
        return iri != null && !isOP(iri) && !isDP(iri) && isAP(iri);
    }

    protected boolean isAP(IRI iri) {
        return annPropertyIRIs.contains(iri);
    }

    protected boolean isOntology(IRI iri) {
        return ontologyIRIs.contains(iri);
    }

    protected void clear() {
        classIRIs.clear();
        objectPropertyIRIs.clear();
        dataPropertyIRIs.clear();
        dataRangeIRIs.clear();
        restrictionIRIs.clear();
        guessedDeclarations.clear();
    }

    protected boolean isObjectPropertyStrict(IRI node) {
        return isObjectPropertyOnly(node);
    }

    protected boolean isOpLax(@Nullable IRI node) {
        if (node == null) {
            return false;
        }
        return isOP(node);
    }

    protected boolean isDataPropertyStrict(IRI node) {
        return isDataPropertyOnly(node);
    }

    protected boolean isDPLax(IRI node) {
        return isDP(node);
    }

    protected boolean isDataRangeStrict(@Nullable IRI node) {
        return node != null && isDataRange(node) && !isClassExpression(node);
    }

    protected boolean isDataRange(IRI iri) {
        return dataRangeIRIs.contains(iri);
    }

    protected boolean isDrLax(IRI node) {
        return isDataRange(node);
    }

    protected boolean isClassExpressionStrict(IRI node) {
        return isClassExpression(node) && !isDataRange(node);
    }

    protected boolean bothDataRange(IRI s, IRI o) {
        return isDataRange(s) && isDataRange(o);
    }

    protected boolean bothCe(IRI s, IRI o) {
        return isClassExpression(s) && isClassExpression(o);
    }

    protected void inferTypes(IRI s, IRI o) {
        if (isClassExpression(o)) {
            addClassExpression(s, false);
        } else if (isDataRange(o)) {
            addDataRange(s, false);
        } else if (isClassExpression(s)) {
            addClassExpression(o, false);
        } else if (isDataRange(s)) {
            addDataRange(o, false);
        }
    }

    protected boolean bothClassOrDataRange(IRI s, IRI o) {
        return bothCe(s, o) || bothDataRange(s, o);
    }

    protected boolean isApLax(IRI iri) {
        return isAP(iri);
    }

    protected void addOntology(IRI iri) {
        if (ontologyIRIs.isEmpty()) {
            firstOntologyIRI = iri;
        }
        ontologyIRIs.add(iri);
    }

    protected Set<IRI> getOntologies() {
        return ontologyIRIs;
    }

    protected boolean isTyped(IRI s, IRI o) {
        return isClassExpression(s) && isClassExpression(o);
    }

    protected boolean streamSubproperty(IRI s, IRI o) {
        if (isOpLax(o)) {
            addObjectProperty(s, false);
        } else if (isDPLax(o)) {
            addDataProperty(o, false);
        } else if (isApLax(o)) {
            addAnnotationProperty(s, false);
        } else if (isOpLax(s)) {
            addObjectProperty(o, false);
        } else if (isDPLax(s)) {
            addDataProperty(o, false);
        } else if (isApLax(s)) {
            addAnnotationProperty(o, false);
        }
        return false;
    }

    protected boolean canStreamIntersection(IRI s, IRI o) {
        if (isClassExpression(s)) {
            addClassExpression(o, false);
        } else if (isClassExpression(o)) {
            addClassExpression(s, false);
        } else if (isDataRange(s)) {
            addDataRange(o, false);
        } else if (isDataRange(o)) {
            addDataRange(s, false);
        }
        return false;
    }
}
