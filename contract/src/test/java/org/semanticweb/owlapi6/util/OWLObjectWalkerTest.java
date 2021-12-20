package org.semanticweb.owlapi6.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.semanticweb.owlapi6.utility.AnnotationWalkingControl.DONT_WALK_ANNOTATIONS;
import static org.semanticweb.owlapi6.utility.AnnotationWalkingControl.WALK_ANNOTATIONS;
import static org.semanticweb.owlapi6.utility.AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.AddOntologyAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.utility.AnnotationWalkingControl;
import org.semanticweb.owlapi6.utility.OWLObjectWalker;

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
        cruelWorld = Annotation(ANNPROPS.AP, Literal("cruel world"));
        goodbye = Annotation(ANNPROPS.AP, Literal("goodbye"), cruelWorld);
        world = Annotation(ANNPROPS.AP, Literal("world"));
        hello = Annotation(ANNPROPS.AP, Literal("hello"), world);
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
        o.addAxiom(Declaration(ANNPROPS.AP, goodbye));
        return o;
    }
}
