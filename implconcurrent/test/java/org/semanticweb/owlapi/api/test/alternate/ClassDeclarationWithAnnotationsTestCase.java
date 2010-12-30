package org.semanticweb.owlapi.api.test.alternate;

import java.util.Collections;
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
@SuppressWarnings("unused")
public class ClassDeclarationWithAnnotationsTestCase extends
		AbstractAnnotatedAxiomRoundTrippingTestCase {
	@Override
	protected OWLAxiom getMainAxiom(Set<OWLAnnotation> annos) {
		OWLEntity ent = getOWLClass("A");
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
