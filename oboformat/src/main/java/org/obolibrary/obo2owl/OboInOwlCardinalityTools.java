package org.obolibrary.obo2owl;

import static org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.*;

import javax.annotation.Nullable;

import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tools for checking and fixing cardinality constrains for OBO ontologies in
 * OWL.
 */
public final class OboInOwlCardinalityTools {

    protected static final Logger LOGGER = LoggerFactory.getLogger(OboInOwlCardinalityTools.class);

    private OboInOwlCardinalityTools() {}

    /**
     * Functor for resolving conflicts for an annotation property and its
     * cardinality constraint.
     */
    public interface AnnotationCardinalityConfictHandler {

        /**
         * Resolve a conflict for a given annotation property and axioms. The
         * result is either a list of resolved axioms or an exception thrown by
         * this method.
         * 
         * @param entity
         *        entity
         * @param property
         *        property
         * @param axioms
         *        axioms
         * @return list of resolved axioms
         */
        List<OWLAnnotationAssertionAxiom> handleConflict(OWLEntity entity, OWLAnnotationProperty property,
            Collection<OWLAnnotationAssertionAxiom> axioms);

        /**
         * Resolve a conflict for a given annotation property and ontology
         * annotations. The result is either a list of resolved annotations or
         * an exception thrown by this method.
         * 
         * @param property
         *        property
         * @param ontologyAnnotations
         *        ontologyAnnotations
         * @return list of resolved annotations
         * @throws AnnotationCardinalityException
         *         AnnotationCardinalityException
         */
        List<OWLAnnotation> handleConflict(OWLAnnotationProperty property,
            Collection<OWLAnnotation> ontologyAnnotations) throws AnnotationCardinalityException;
    }

    /**
     * Functor for reporting conflicts for an annotation property and its
     * cardinality constraint.
     */
    public interface AnnotationCardinalityReporter {

        /**
         * Report a conflict for a given annotation property and axioms.
         * 
         * @param entity
         *        entity
         * @param property
         *        property
         * @param axioms
         *        axioms
         */
        void reportConflict(OWLEntity entity, OWLAnnotationProperty property,
            Collection<OWLAnnotationAssertionAxiom> axioms);

        /**
         * Report a conflict for a given annotation property and ontology
         * annotations.
         * 
         * @param property
         *        property
         * @param ontologyAnnotations
         *        ontologyAnnotations
         */
        void reportConflict(OWLAnnotationProperty property, Collection<OWLAnnotation> ontologyAnnotations);
    }

    /**
     * Exception indication a non-resolvable conflict for an annotation property
     * and its cardinality constraint.
     */
    public static class AnnotationCardinalityException extends OWLRuntimeException {

        /**
         * Create a new Exception.
         * 
         * @param message
         *        message
         * @param cause
         *        cause
         */
        public AnnotationCardinalityException(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * Create a new Exception.
         * 
         * @param message
         *        message
         */
        public AnnotationCardinalityException(String message) {
            super(message);
        }
    }

    /**
     * Check the annotations for cardinality violations. Try to resolve
     * conflicts with the given handler.
     * 
     * @param ontology
     *        the target ontology
     * @param reporter
     *        reporter
     * @param handler
     *        the conflict handler
     * @throws AnnotationCardinalityException
     *         throws exception in case a conflict cannot be resolved by the
     *         handler
     * @see Frame#check() for implementation in OBO
     */
    public static void checkAnnotationCardinality(OWLOntology ontology,
        @Nullable AnnotationCardinalityReporter reporter, @Nullable AnnotationCardinalityConfictHandler handler)
            throws AnnotationCardinalityException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        Set<OWLAnnotationProperty> headerProperties = getProperties(factory, TAG_ONTOLOGY, TAG_FORMAT_VERSION, TAG_DATE,
            TAG_DEFAULT_NAMESPACE, TAG_SAVED_BY, TAG_AUTO_GENERATED_BY);
        checkOntologyAnnotations(headerProperties, ontology, reporter, handler, manager);
        Set<OWLAnnotationProperty> properties = getProperties(factory, TAG_IS_ANONYMOUS, TAG_NAME, TAG_NAMESPACE,
            TAG_DEF, TAG_COMMENT, TAG_DOMAIN, TAG_RANGE, TAG_IS_ANTI_SYMMETRIC, TAG_IS_CYCLIC, TAG_IS_REFLEXIVE,
            TAG_IS_SYMMETRIC, TAG_IS_TRANSITIVE, TAG_IS_FUNCTIONAL, TAG_IS_INVERSE_FUNCTIONAL, TAG_IS_OBSELETE,
            TAG_CREATED_BY, TAG_CREATION_DATE);
        ontology.classesInSignature(INCLUDED).forEach(c -> checkOwlEntity(c, properties, ontology, reporter, handler));
        ontology.objectPropertiesInSignature(INCLUDED).forEach(p -> checkOwlEntity(p, properties, ontology, reporter,
            handler));
    }

    private static Set<OWLAnnotationProperty> getProperties(OWLDataFactory factory, OboFormatTag... tags) {
        Set<OWLAnnotationProperty> set = new HashSet<>();
        for (OboFormatTag tag : tags) {
            set.add(factory.getOWLAnnotationProperty(OWLAPIObo2Owl.trTagToIRI(tag.getTag())));
        }
        return set;
    }

    private static void checkOntologyAnnotations(Set<OWLAnnotationProperty> properties, OWLOntology ontology,
        @Nullable AnnotationCardinalityReporter reporter, @Nullable AnnotationCardinalityConfictHandler handler,
        OWLOntologyManager manager) throws AnnotationCardinalityException {
        Set<OWLAnnotation> annotations = asSet(ontology.annotations());
        Map<OWLAnnotationProperty, Set<OWLAnnotation>> groupedAnnotations = new HashMap<>();
        for (OWLAnnotation annotation : annotations) {
            OWLAnnotationProperty current = annotation.getProperty();
            if (properties.contains(current)) {
                Set<OWLAnnotation> set = groupedAnnotations.get(current);
                if (set == null) {
                    groupedAnnotations.put(current, Collections.singleton(annotation));
                } else if (set.size() == 1) {
                    set = new HashSet<>(set);
                    set.add(annotation);
                    groupedAnnotations.put(current, set);
                } else {
                    set.add(annotation);
                }
            }
        }
        // check cardinality constraint
        for (OWLAnnotationProperty property : groupedAnnotations.keySet()) {
            Set<OWLAnnotation> group = groupedAnnotations.get(property);
            if (group.size() > 1) {
                if (reporter != null) {
                    // report conflict
                    reporter.reportConflict(property, group);
                }
                if (handler != null) {
                    // handle conflict
                    // if conflict is not resolvable, throws exception
                    List<OWLAnnotation> changed = handler.handleConflict(property, group);
                    group.forEach(a -> manager.applyChange(new RemoveOntologyAnnotation(ontology, a)));
                    changed.forEach(a -> manager.applyChange(new AddOntologyAnnotation(ontology, a)));
                }
            }
        }
    }

    private static void checkOwlEntity(OWLEntity owlClass, Set<OWLAnnotationProperty> properties, OWLOntology ontology,
        @Nullable AnnotationCardinalityReporter reporter, @Nullable AnnotationCardinalityConfictHandler handler) {
        Map<OWLAnnotationProperty, Set<OWLAnnotationAssertionAxiom>> groupedAxioms = new HashMap<>();
        for (OWLAnnotationAssertionAxiom axiom : asSet(ontology.annotationAssertionAxioms(owlClass.getIRI()))) {
            OWLAnnotationProperty current = axiom.getProperty();
            if (properties.contains(current)) {
                Set<OWLAnnotationAssertionAxiom> set = groupedAxioms.get(current);
                if (set == null) {
                    groupedAxioms.put(current, Collections.singleton(axiom));
                } else if (set.size() == 1) {
                    set = new HashSet<>(set);
                    set.add(axiom);
                    groupedAxioms.put(current, set);
                } else {
                    set.add(axiom);
                }
            }
        }
        // check cardinality constraint
        for (OWLAnnotationProperty property : groupedAxioms.keySet()) {
            Set<OWLAnnotationAssertionAxiom> group = groupedAxioms.get(property);
            if (group.size() > 1) {
                if (reporter != null) {
                    // report conflict
                    reporter.reportConflict(owlClass, property, group);
                }
                if (handler != null) {
                    // handle conflict
                    // if conflict is not resolvable, throws exception
                    List<OWLAnnotationAssertionAxiom> changed = handler.handleConflict(owlClass, property, group);
                    ontology.remove(group);
                    ontology.add(changed);
                }
            }
        }
    }

    /**
     * Check the annotations for cardinality violations. Try to resolve
     * conflicts with the default handler.
     * 
     * @param ontology
     *        the target ontology
     * @throws AnnotationCardinalityException
     *         throws exception in case a conflict cannot be resolved by the
     *         handler
     * @see #DEFAULT_HANDLER
     */
    public static void checkAnnotationCardinality(OWLOntology ontology) throws AnnotationCardinalityException {
        checkAnnotationCardinality(ontology, null, DEFAULT_HANDLER);
    }

    /**
     * Check the annotations for cardinality violations. Only report violations
     * via the given reporter
     * 
     * @param ontology
     *        the target ontology
     * @param reporter
     *        used to report violations
     */
    public static void checkAnnotationCardinality(OWLOntology ontology, AnnotationCardinalityReporter reporter) {
        try {
            checkAnnotationCardinality(ontology, reporter, null);
        } catch (AnnotationCardinalityException e) {
            // this will not happen as no handler is registered
            LOGGER.error("Cardinality exception during report: This isn't supposed to happen.", e);
        }
    }

    /** Default handler. */
    public static final AnnotationCardinalityConfictHandler DEFAULT_HANDLER = new AnnotationCardinalityConfictHandler() {

        @Override
        public List<OWLAnnotationAssertionAxiom> handleConflict(OWLEntity entity, OWLAnnotationProperty property,
            Collection<OWLAnnotationAssertionAxiom> axioms) {
            if (axioms.size() > 1) {
                String tag = OWLAPIOwl2Obo.owlObjectToTag(property);
                if (tag == null) {
                    tag = property.getIRI().toString();
                }
                // take the first one in the collection
                // (may be random)
                LOGGER.info("Fixing multiple {} tags for entity: {}", tag, entity.getIRI());
                return listOfFirst(axioms);
            }
            throw new AnnotationCardinalityException("Could not resolve conflict for property: " + property);
        }

        @Override
        public List<OWLAnnotation> handleConflict(OWLAnnotationProperty property,
            Collection<OWLAnnotation> ontologyAnnotations) throws AnnotationCardinalityException {
            if (ontologyAnnotations.size() > 1) {
                String tag = OWLAPIOwl2Obo.owlObjectToTag(property);
                if (tag == null) {
                    tag = property.getIRI().toString();
                }
                // take the first one in the collection
                // (may be random)
                LOGGER.info("Fixing multiple ontolgy annotations with, tag: {}", tag);
                return listOfFirst(ontologyAnnotations);
            }
            throw new AnnotationCardinalityException("Could not resolve conflict for property: " + property);
        }
    };

    static <T> List<T> listOfFirst(Collection<T> t) {
        return Collections.singletonList(t.iterator().next());
    }
}
