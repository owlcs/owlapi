package org.semanticweb.owlapi.util;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.util.AnnotationWalkingControl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;

/**
 * Created by ses on 8/15/15.
 */
@SuppressWarnings("javadoc")
public class OWLObjectWalkerTest extends TestBase {

    private OWLAnnotation world;
    private OWLAnnotation cruelWorld;
    private OWLAnnotationProperty ap;
    private OWLAnnotation goodbye;
    private OWLAnnotation hello;

    @Before
    public void setUp() {
        ap = df.getOWLAnnotationProperty(iri("ap"));
        cruelWorld = df.getOWLAnnotation(ap, df.getOWLLiteral("cruel world"));
        goodbye = df.getOWLAnnotation(ap, df.getOWLLiteral("goodbye"), singleton(cruelWorld));
        world = df.getOWLAnnotation(ap, df.getOWLLiteral("world"));
        hello = df.getOWLAnnotation(ap, df.getOWLLiteral("hello"), singleton(world));
    }

    @Test
    public void testWalkAnnotations() {
        OWLOntology o = getOwlOntology();
        List<OWLAnnotation> emptyAnnotationList = Collections.emptyList();
        checkWalkWithFlags(o, DONT_WALK_ANNOTATIONS, emptyAnnotationList);
        checkWalkWithFlags(o, WALK_ONTOLOGY_ANNOTATIONS_ONLY, Arrays.asList(hello));
        checkWalkWithFlags(o, WALK_ANNOTATIONS, Arrays.asList(hello, world, goodbye, cruelWorld));
    }

    private static void checkWalkWithFlags(OWLOntology o, AnnotationWalkingControl walkFlag,
        List<OWLAnnotation> expected) {
        final List<OWLAnnotation> visitedAnnotations = new ArrayList<>();
        OWLObjectVisitor visitor = new OWLObjectVisitor() {

            @Override
            public void visit(OWLAnnotation node) {
                visitedAnnotations.add(node);
            }
        };
        Set<? extends OWLObject> ontologySet = Collections.singleton(o);
        OWLObjectWalker<? extends OWLObject> walker;
        if (walkFlag == WALK_ONTOLOGY_ANNOTATIONS_ONLY) {
            walker = new OWLObjectWalker<>(ontologySet);
        } else {
            walker = new OWLObjectWalker<>(ontologySet, true, walkFlag);
        }
        walker.walkStructure(visitor);
        assertEquals(expected, visitedAnnotations);
    }

    private OWLOntology getOwlOntology() {
        OWLOntology o = getOWLOntology();
        m.applyChange(new AddOntologyAnnotation(o, hello));
        o.addAxiom(df.getOWLDeclarationAxiom(ap, singleton(goodbye)));
        return o;
    }
}
