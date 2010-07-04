package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

import java.util.Collections;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Jul-2010
 */
public class AnnotationPropertyDeclarationWithAnnotationsTestCase extends AbstractAnnotatedAxiomRoundTrippingTestCase {


    @Override
    protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
        OWLEntity ent = getOWLAnnotationProperty("propA");
        return getFactory().getOWLDeclarationAxiom(ent);
    }

    @Override
    protected Set<OWLAxiom> getDeclarationsToAdd(OWLAxiom ax) {
        return Collections.emptySet();
    }

    @Override
    protected boolean isIgnoreDeclarationAxioms(OWLOntologyFormat format) {
        return false;
    }
}
