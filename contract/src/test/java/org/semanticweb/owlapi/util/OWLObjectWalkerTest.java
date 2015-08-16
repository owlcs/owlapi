package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import static org.semanticweb.owlapi.util.StructureWalker.AnnotationWalkingControl.DONT_WALK_ANNOTATIONS;
import static org.semanticweb.owlapi.util.StructureWalker.AnnotationWalkingControl.WALK_ANNOTATIONS;
import static org.semanticweb.owlapi.util.StructureWalker.AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY;

/**
 * Created by ses on 8/15/15.
 */
public class OWLObjectWalkerTest extends TestBase {

    private OWLAnnotation world;
    private OWLAnnotation cruelWorld;
    private OWLAnnotationProperty ap;
    private OWLAnnotation goodbye;
    private OWLAnnotation hello;

    @Before
    public void setUp() throws Exception {
        this.ap = df.getOWLAnnotationProperty(iri("ap"));
        this.cruelWorld = df.getOWLAnnotation(ap, df.getOWLLiteral("cruel world"));
        this.goodbye = df.getOWLAnnotation(ap, df.getOWLLiteral("goodbye"), singleton(cruelWorld));
        this.world = df.getOWLAnnotation(ap, df.getOWLLiteral("world"));
        this.hello = df.getOWLAnnotation(ap, df.getOWLLiteral("hello"), singleton(world));
    }

    @Test
    public void testWalkAnnotations() throws Exception {
        OWLOntology o = getOwlOntology();
        List<OWLAnnotation> emptyAnnotationList = Collections.emptyList();
        checkWalkWithFlags(o, DONT_WALK_ANNOTATIONS, emptyAnnotationList);
        checkWalkWithFlags(o, WALK_ONTOLOGY_ANNOTATIONS_ONLY, Arrays.asList(hello));
        checkWalkWithFlags(o, WALK_ANNOTATIONS, Arrays.asList(hello, world, goodbye, cruelWorld));
    }

    private void checkWalkWithFlags(OWLOntology o, StructureWalker.AnnotationWalkingControl walkFlag, List<OWLAnnotation> expected) {
        final List<OWLAnnotation> visitedAnnotations = new ArrayList<>();

        OWLObjectVisitor visitor = new OWLObjectVisitorAdapter() {
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
            walker = new OWLObjectWalker<>(ontologySet, walkFlag);
        }
        walker.walkStructure(visitor);
        assertEquals(expected, visitedAnnotations);
    }

    @Nonnull
    private OWLOntology getOwlOntology() {
        OWLOntology o = getOWLOntology("foo");
        m.applyChange(new AddOntologyAnnotation(o, hello));
        addAxiom(o, df.getOWLDeclarationAxiom(ap, singleton(goodbye)));
        return o;
    }
}
