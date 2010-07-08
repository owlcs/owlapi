package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

/**
 * This class keeps hold of often used constants; if the implementation of
 * OWLDataFactory and all acommpanying impl classes needs changing, this class
 * must be modified (the factory field needs to refer to the new implementation)
 */
public class OWLDataFactoryVocabulary {
	private static final OWLDataFactory factory = OWLDataFactoryImpl
			.getInstance();
	/**
	 * the built in owl:Thing class, which has a URI of
	 * &lt;http://www.w3.org/2002/07/owl#Thing&gt;
	 */
	public static final OWLClass OWLThing = factory.getOWLThing();
	/**
	 * the built in owl:Nothing class, which has a URI of
	 * &lt;http://www.w3.org/2002/07/owl#Nothing&gt;
	 */
	public static final OWLClass OWLNothing = factory.getOWLNothing();
	public static final OWLObjectProperty OWLTopObjectProperty = factory
			.getOWLTopObjectProperty();
	public static final OWLDataProperty OWLTopDataProperty = factory.getOWLTopDataProperty();
	public static final OWLObjectProperty OWLBottomObjectProperty = factory
			.getOWLBottomObjectProperty();
	public static final OWLDataProperty OWLBottomDataProperty = factory.getOWLBottomDataProperty();
	public static final OWLDatatype TopDatatype = factory.getTopDatatype();
}
