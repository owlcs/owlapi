package org.semanticweb.owlapi.api.test.alternate;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;

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
 */
public class OWLDatatypeTestCase extends AbstractOWLDataFactoryTest {
	@Override
	public void testCreation() throws Exception {
		OWLDatatype typeA = getFactory().getOWLDatatype(createIRI());
		assertNotNull(typeA);
	}

	@Override
	public void testEqualsPositive() throws Exception {
		IRI iri = createIRI();
		OWLDatatype typeA = getFactory().getOWLDatatype(iri);
		OWLDatatype typeB = getFactory().getOWLDatatype(iri);
		assertEquals(typeA, typeB);
	}

	@Override
	public void testEqualsNegative() throws Exception {
		OWLDatatype typeA = getFactory().getOWLDatatype(createIRI());
		OWLDatatype typeB = getFactory().getOWLDatatype(createIRI());
		assertNotEquals(typeA, typeB);
	}

	@Override
	public void testHashCode() throws Exception {
		IRI iri = createIRI();
		OWLDatatype typeA = getFactory().getOWLDatatype(iri);
		OWLDatatype typeB = getFactory().getOWLDatatype(iri);
		assertEquals(typeA.hashCode(), typeB.hashCode());
	}
}
