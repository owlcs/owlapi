package org.semanticweb.owlapi.api.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.util.OWLEntityCollector;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-May-2009
 */
public abstract class AbstractAnnotatedAxiomRoundTrippingTestCase extends AbstractAxiomsRoundTrippingTestCase {

    final protected Set<? extends OWLAxiom> createAxioms() {
        OWLAnnotationProperty prop = getOWLAnnotationProperty("prop");
        OWLLiteral lit = getFactory().getOWLLiteral("Test", "");
        OWLAnnotation anno1 = getFactory().getOWLAnnotation(prop, lit);
        OWLAnnotationProperty prop2 = getOWLAnnotationProperty("prop2");
        OWLAnnotation anno2 = getFactory().getOWLAnnotation(prop2, lit);

        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        // Add two annotations per axiom
        annos.add(anno1);
        annos.add(anno2);
        OWLAxiom ax = getMainAxiom(annos);
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(ax.getAnnotatedAxiom(annos));
        axioms.add(getFactory().getOWLDeclarationAxiom(prop));
        axioms.add(getFactory().getOWLDeclarationAxiom(prop2));

        axioms.add(ax.getAnnotatedAxiom(Collections.singleton(anno1)));
        axioms.add(ax.getAnnotatedAxiom(Collections.singleton(anno2)));

        Set<OWLAxiom> declarations = getDeclarationsToAdd(ax);
        axioms.addAll(declarations);
        return axioms;
    }

    protected Set<OWLAxiom> getDeclarationsToAdd(OWLAxiom ax) {
        Set<OWLAxiom> declarations = new HashSet<OWLAxiom>();
        for(OWLEntity ent : ax.getSignature()) {
            declarations.add(getFactory().getOWLDeclarationAxiom(ent));
        }
        return declarations;
    }

    protected abstract OWLAxiom getMainAxiom(Set<OWLAnnotation> annos);
}
