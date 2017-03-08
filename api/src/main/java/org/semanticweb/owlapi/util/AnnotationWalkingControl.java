package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.HasAnnotations;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Control flag for whether to walk annotations
 */
public enum AnnotationWalkingControl {
    /**
     * Do not walk any annotations
     */
    DONT_WALK_ANNOTATIONS,
    /**
     * Only walk ontology annotations (previous behaviour)
     */
    WALK_ONTOLOGY_ANNOTATIONS_ONLY {
        @Override
        public <T extends OWLObject> void walk(StructureWalker<T> walker, OWLObject o) {
            if (o instanceof OWLOntology) {
                ((OWLOntology) o).annotations().forEach(a -> a.accept(walker));
            }
        }
    },
    /**
     * Walk all annotations
     */
    WALK_ANNOTATIONS {
        @Override
        public <T extends OWLObject> void walk(StructureWalker<T> walker, OWLObject o) {
            if (o instanceof HasAnnotations) {
                ((HasAnnotations) o).annotations().forEach(a -> a.accept(walker));
            }
        }
    };

    /**
     * Visit annotations on the object, if the setting allows for the annotations to be visited.
     *
     * @param walker walker to use to visit annotations
     * @param o object containing annotations
     */
    @SuppressWarnings("unused")
    public <T extends OWLObject> void walk(StructureWalker<T> walker, OWLObject o) {
        // default implementation
    }
}
