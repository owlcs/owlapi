package org.semanticweb.owlapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi.util.AnnotationWalkingControl.DONT_WALK_ANNOTATIONS;
import static org.semanticweb.owlapi.util.AnnotationWalkingControl.WALK_ANNOTATIONS;
import static org.semanticweb.owlapi.util.AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Created by ses on 8/15/15.
 */
class OWLObjectWalkerTest extends TestBase {

    private OWLAnnotation world;
    private OWLAnnotation cruelWorld;
    private OWLAnnotation goodbye;
    private OWLAnnotation hello;

    private static void checkWalkWithFlags(OWLOntology o, AnnotationWalkingControl walkFlag,
        List<OWLAnnotation> expected) {
        final List<OWLAnnotation> visitedAnnotations = new ArrayList<>();
        OWLObjectVisitor visitor = new OWLObjectVisitor() {

            @Override
            public void visit(OWLAnnotation node) {
                visitedAnnotations.add(node);
            }
        };
        Set<? extends OWLObject> ontologySet = set(o);
        OWLObjectWalker<? extends OWLObject> walker;
        if (walkFlag == WALK_ONTOLOGY_ANNOTATIONS_ONLY) {
            walker = new OWLObjectWalker<>(ontologySet);
        } else {
            walker = new OWLObjectWalker<>(ontologySet, true, walkFlag);
        }
        walker.walkStructure(visitor);
        assertEquals(expected, visitedAnnotations);
    }

    @BeforeEach
    void setUp() {
        cruelWorld = Annotation(AP, Literal("cruel world"));
        goodbye = Annotation(cruelWorld, AP, Literal("goodbye"));
        world = Annotation(AP, Literal("world"));
        hello = Annotation(world, AP, Literal("hello"));
    }

    @Test
    void testWalkAnnotations() {
        OWLOntology o = getOwlOntology();
        checkWalkWithFlags(o, DONT_WALK_ANNOTATIONS, l());
        checkWalkWithFlags(o, WALK_ONTOLOGY_ANNOTATIONS_ONLY, l(hello));
        checkWalkWithFlags(o, WALK_ANNOTATIONS, l(hello, world, goodbye, cruelWorld));
    }

    private OWLOntology getOwlOntology() {
        OWLOntology o = create("foo");
        o.applyChange(new AddOntologyAnnotation(o, hello));
        o.addAxiom(Declaration(goodbye, AP));
        return o;
    }
}
