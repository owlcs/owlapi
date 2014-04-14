package org.obolibrary.obo2owl;

import static org.semanticweb.owlapi.model.Imports.INCLUDED;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tools for checking and fixing cardinality constrains for OBO ontologies in
 * OWL.
 */
public class OboInOwlCardinalityTools {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(OboInOwlCardinalityTools.class);

    /**
     * Functor for resolving conflicts for an annotation property and its
     * cardinality constraint.
     */
    public static interface AnnotationCardinalityConfictHandler {

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
         * @throws AnnotationCardinalityException
         *         AnnotationCardinalityException
         */
        public List<OWLAnnotationAssertionAxiom> handleConflict(
                OWLEntity entity, OWLAnnotationProperty property,
                Collection<OWLAnnotationAssertionAxiom> axioms)
                throws AnnotationCardinalityException;

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
        public List<OWLAnnotation> handleConflict(
                OWLAnnotationProperty property,
                Collection<OWLAnnotation> ontologyAnnotations)
                throws AnnotationCardinalityException;
    }

    /**
     * Functor for reporting conflicts for an annotation property and its
     * cardinality constraint.
     */
    public static interface AnnotationCardinalityReporter {

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
        public void reportConflict(OWLEntity entity,
                OWLAnnotationProperty property,
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
        public void reportConflict(OWLAnnotationProperty property,
                Collection<OWLAnnotation> ontologyAnnotations);
    }

    /**
     * Exception indication a non-resolvable conflict for an annotation property
     * and its cardinality constraint.
     */
    public static class AnnotationCardinalityException extends Exception {

        // generated
        private static final long serialVersionUID = 40000L;

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
            AnnotationCardinalityReporter reporter,
            AnnotationCardinalityConfictHandler handler)
            throws AnnotationCardinalityException {
        final OWLOntologyManager manager = ontology.getOWLOntologyManager();
        final OWLDataFactory factory = manager.getOWLDataFactory();
        Set<OWLAnnotationProperty> headerProperties = getProperties(factory,
                OboFormatTag.TAG_ONTOLOGY, OboFormatTag.TAG_FORMAT_VERSION,
                OboFormatTag.TAG_DATE, OboFormatTag.TAG_DEFAULT_NAMESPACE,
                OboFormatTag.TAG_SAVED_BY, OboFormatTag.TAG_AUTO_GENERATED_BY);
        checkOntologyAnnotations(headerProperties, ontology, reporter, handler,
                manager);
        Set<OWLAnnotationProperty> properties = getProperties(factory,
                OboFormatTag.TAG_IS_ANONYMOUS, OboFormatTag.TAG_NAME,
                OboFormatTag.TAG_NAMESPACE, OboFormatTag.TAG_DEF,
                OboFormatTag.TAG_COMMENT, OboFormatTag.TAG_DOMAIN,
                OboFormatTag.TAG_RANGE, OboFormatTag.TAG_IS_ANTI_SYMMETRIC,
                OboFormatTag.TAG_IS_CYCLIC, OboFormatTag.TAG_IS_REFLEXIVE,
                OboFormatTag.TAG_IS_SYMMETRIC, OboFormatTag.TAG_IS_TRANSITIVE,
                OboFormatTag.TAG_IS_FUNCTIONAL,
                OboFormatTag.TAG_IS_INVERSE_FUNCTIONAL,
                OboFormatTag.TAG_IS_OBSELETE, OboFormatTag.TAG_CREATED_BY,
                OboFormatTag.TAG_CREATION_DATE);
        for (OWLClass owlClass : ontology.getClassesInSignature(INCLUDED)) {
            checkOwlEntity(owlClass, properties, ontology, reporter, handler,
                    manager);
        }
        for (OWLObjectProperty owlProperty : ontology
                .getObjectPropertiesInSignature(INCLUDED)) {
            checkOwlEntity(owlProperty, properties, ontology, reporter,
                    handler, manager);
        }
    }

    private static Set<OWLAnnotationProperty> getProperties(
            OWLDataFactory factory, OboFormatTag... tags) {
        Set<OWLAnnotationProperty> set = new HashSet<OWLAnnotationProperty>();
        for (OboFormatTag tag : tags) {
            IRI iri = OWLAPIObo2Owl.trTagToIRI(tag.getTag());
            if (iri != null) {
                OWLAnnotationProperty property = factory
                        .getOWLAnnotationProperty(iri);
                set.add(property);
            }
        }
        return set;
    }

    private static void checkOntologyAnnotations(
            Set<OWLAnnotationProperty> properties, OWLOntology ontology,
            AnnotationCardinalityReporter reporter,
            AnnotationCardinalityConfictHandler handler,
            final OWLOntologyManager manager)
            throws AnnotationCardinalityException {
        Set<OWLAnnotation> annotations = ontology.getAnnotations();
        Map<OWLAnnotationProperty, Set<OWLAnnotation>> groupedAnnotations = new HashMap<OWLAnnotationProperty, Set<OWLAnnotation>>();
        for (OWLAnnotation annotation : annotations) {
            OWLAnnotationProperty current = annotation.getProperty();
            if (properties.contains(current)) {
                Set<OWLAnnotation> set = groupedAnnotations.get(current);
                if (set == null) {
                    groupedAnnotations.put(current,
                            Collections.singleton(annotation));
                } else if (set.size() == 1) {
                    set = new HashSet<OWLAnnotation>(set);
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
                    List<OWLAnnotation> changed = handler.handleConflict(
                            property, group);
                    for (OWLAnnotation annotation : group) {
                        manager.applyChange(new RemoveOntologyAnnotation(
                                ontology, annotation));
                    }
                    for (OWLAnnotation annotation : changed) {
                        manager.applyChange(new AddOntologyAnnotation(ontology,
                                annotation));
                    }
                }
            }
        }
    }

    private static void checkOwlEntity(OWLEntity owlClass,
            final Set<OWLAnnotationProperty> properties, OWLOntology ontology,
            AnnotationCardinalityReporter reporter,
            AnnotationCardinalityConfictHandler handler,
            final OWLOntologyManager manager)
            throws AnnotationCardinalityException {
        Set<OWLAnnotationAssertionAxiom> axioms = ontology
                .getAnnotationAssertionAxioms(owlClass.getIRI());
        Map<OWLAnnotationProperty, Set<OWLAnnotationAssertionAxiom>> groupedAxioms = new HashMap<OWLAnnotationProperty, Set<OWLAnnotationAssertionAxiom>>();
        for (OWLAnnotationAssertionAxiom axiom : axioms) {
            final OWLAnnotationProperty current = axiom.getProperty();
            if (properties.contains(current)) {
                Set<OWLAnnotationAssertionAxiom> set = groupedAxioms
                        .get(current);
                if (set == null) {
                    groupedAxioms.put(current, Collections.singleton(axiom));
                } else if (set.size() == 1) {
                    set = new HashSet<OWLAnnotationAssertionAxiom>(set);
                    set.add(axiom);
                    groupedAxioms.put(current, set);
                } else {
                    set.add(axiom);
                }
            }
        }
        // check cardinality constraint
        for (OWLAnnotationProperty property : groupedAxioms.keySet()) {
            Set<OWLAnnotationAssertionAxiom> group = groupedAxioms
                    .get(property);
            if (group.size() > 1) {
                if (reporter != null) {
                    // report conflict
                    reporter.reportConflict(owlClass, property, group);
                }
                if (handler != null) {
                    // handle conflict
                    // if conflict is not resolvable, throws exception
                    List<OWLAnnotationAssertionAxiom> changed = handler
                            .handleConflict(owlClass, property, group);
                    for (OWLAnnotationAssertionAxiom axiom : group) {
                        manager.removeAxiom(ontology, axiom);
                    }
                    for (OWLAnnotationAssertionAxiom axiom : changed) {
                        manager.addAxiom(ontology, axiom);
                    }
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
    public static void checkAnnotationCardinality(OWLOntology ontology)
            throws AnnotationCardinalityException {
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
    public static void checkAnnotationCardinality(OWLOntology ontology,
            AnnotationCardinalityReporter reporter) {
        try {
            checkAnnotationCardinality(ontology, reporter, null);
        } catch (AnnotationCardinalityException e) {
            // this will not happen as no handler is registered
            LOGGER.error(
                    "Cardinality exception during report: This isn't supposed to happen.",
                    e);
        }
    }

    /** default handler */
    public static final AnnotationCardinalityConfictHandler DEFAULT_HANDLER = new AnnotationCardinalityConfictHandler() {

        @Override
        public List<OWLAnnotationAssertionAxiom> handleConflict(
                OWLEntity entity, OWLAnnotationProperty property,
                Collection<OWLAnnotationAssertionAxiom> annotations)
                throws AnnotationCardinalityException {
            if (annotations.size() > 1) {
                String tag = OWLAPIOwl2Obo.owlObjectToTag(property);
                if (tag == null) {
                    tag = property.getIRI().toString();
                }
                // take the first one in the collection
                // (may be random)
                LOGGER.info("Fixing multiple {} tags for entity: {}", tag,
                        entity.getIRI());
                return Collections.singletonList(annotations.iterator().next());
            }
            throw new AnnotationCardinalityException(
                    "Could not resolve conflict for property: " + property);
        }

        @Override
        public List<OWLAnnotation> handleConflict(
                OWLAnnotationProperty property,
                Collection<OWLAnnotation> ontologyAnnotations)
                throws AnnotationCardinalityException {
            if (ontologyAnnotations.size() > 1) {
                String tag = OWLAPIOwl2Obo.owlObjectToTag(property);
                if (tag == null) {
                    tag = property.getIRI().toString();
                }
                // take the first one in the collection
                // (may be random)
                LOGGER.info(
                        "Fixing multiple ontolgy annotations with, tag: {}",
                        tag);
                return Collections.singletonList(ontologyAnnotations.iterator()
                        .next());
            }
            throw new AnnotationCardinalityException(
                    "Could not resolve conflict for property: " + property);
        }
    };
}
