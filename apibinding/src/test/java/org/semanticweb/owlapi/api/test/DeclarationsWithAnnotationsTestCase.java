package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

import java.util.Collections;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class DeclarationsWithAnnotationsTestCase extends AbstractAnnotatedAxiomRoundTrippingTestCase {

    @Override
    protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
        OWLEntity ent = getOWLClass("A");
        return getFactory().getOWLDeclarationAxiom(ent);
    }
}
