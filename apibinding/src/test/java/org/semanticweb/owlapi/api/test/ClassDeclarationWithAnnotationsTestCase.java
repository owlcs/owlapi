package org.semanticweb.owlapi.api.test;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class ClassDeclarationWithAnnotationsTestCase extends AbstractAnnotatedAxiomRoundTrippingTestCase {

    @Override
    protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
        OWLEntity ent = getOWLClass("A");
        return getFactory().getOWLDeclarationAxiom(ent);
    }

    @Override
    protected boolean isIgnoreDeclarationAxioms(OWLOntologyFormat format) {
        return false;
    }

    @Override
    public void testManchesterOWLSyntax() throws Exception {
        // Can't represent annotated declarations in Manchester Syntax
//        super.testManchesterOWLSyntax();
    }
}
