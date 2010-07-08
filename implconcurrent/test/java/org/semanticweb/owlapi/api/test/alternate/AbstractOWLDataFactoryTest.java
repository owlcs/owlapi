package org.semanticweb.owlapi.api.test.alternate;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group Date: 25-Oct-2006
 * <p/>
 * The base for test cases that need a data factory.
 */
public abstract class AbstractOWLDataFactoryTest extends AbstractOWLAPITestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}

	public static IRI createIRI() {
		return TestUtils.createIRI();
	}

	public abstract void testCreation() throws Exception;

	public abstract void testEqualsPositive() throws Exception;

	public abstract void testEqualsNegative() throws Exception;

	public abstract void testHashCode() throws Exception;

	public static void assertNotEquals(Object objA, Object objB) {
		assertFalse(objA.equals(objB));
	}

	protected OWLObjectProperty createOWLObjectProperty() throws Exception {
		return getFactory().getOWLObjectProperty(createIRI());
	}

	protected OWLClass createOWLClass() throws Exception {
		return getFactory().getOWLClass(createIRI());
	}

	protected OWLIndividual createOWLIndividual() throws Exception {
		return getFactory().getOWLNamedIndividual(createIRI());
	}

	protected OWLDataProperty createOWLDataProperty() throws OWLException {
		return getFactory().getOWLDataProperty(createIRI());
	}

	protected OWLDatatype createOWLDatatype() throws OWLException {
		return getFactory().getOWLDatatype(createIRI());
	}

	protected OWLLiteral createOWLLiteral() throws OWLException {
		return getFactory().getOWLLiteral("Test" + System.currentTimeMillis(),
				createOWLDatatype());
	}
}
