package org.semanticweb.owlapi.api.test.alternate;

import junit.framework.TestCase;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class SimpleIRIShortFormProviderTestCase extends TestCase {
	public void testFragmentShortForm() {
		IRI iri = IRI.create("http://owl.cs.manchester.ac.uk/ontology/x#A");
		SimpleIRIShortFormProvider sfp = new SimpleIRIShortFormProvider();
		String shortForm = sfp.getShortForm(iri);
		assertEquals(shortForm, "A");
	}

	public void testLastPathShortForm() {
		IRI iri = IRI.create("http://owl.cs.manchester.ac.uk/ontology/x");
		SimpleIRIShortFormProvider sfp = new SimpleIRIShortFormProvider();
		String shortForm = sfp.getShortForm(iri);
		assertEquals(shortForm, "x");
	}

	public void testEmptyPathShortForm() {
		IRI iri = IRI.create("http://owl.cs.manchester.ac.uk/");
		SimpleIRIShortFormProvider sfp = new SimpleIRIShortFormProvider();
		String shortForm = sfp.getShortForm(iri);
		assertEquals("<http://owl.cs.manchester.ac.uk/>", shortForm);
	}
}
