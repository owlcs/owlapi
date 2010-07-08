package org.semanticweb.owlapi.api.test.alternate;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;

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
public class OWLTypedConstantTestCase extends AbstractOWLDataFactoryTest {
	public void testCreation() throws Exception {
		OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
		OWLLiteral conA = getFactory().getOWLLiteral("3", dt);
		assertNotNull(conA);
	}

	public void testEqualsPositive() throws Exception {
		OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
		OWLLiteral conA = getFactory().getOWLLiteral("3", dt);
		OWLLiteral conB = getFactory().getOWLLiteral("3", dt);
		assertEquals(conA, conB);
	}

	public void testEqualsNegative() throws Exception {
		// Different datatypes - same literal
		OWLDatatype dtA = getFactory().getOWLDatatype(createIRI());
		OWLLiteral conA = getFactory().getOWLLiteral("3", dtA);
		OWLDatatype dtB = getFactory().getOWLDatatype(createIRI());
		OWLLiteral conB = getFactory().getOWLLiteral("3", dtB);
		assertNotEquals(conA, conB);
		// Different literals - same datatype
		OWLDatatype dtC = getFactory().getOWLDatatype(createIRI());
		OWLLiteral conC = getFactory().getOWLLiteral("3", dtC);
		OWLLiteral conD = getFactory().getOWLLiteral("4", dtC);
		assertNotEquals(conC, conD);
	}

	public void testHashCode() throws Exception {
		OWLDatatype dt = getFactory().getOWLDatatype(createIRI());
		OWLLiteral conA = getFactory().getOWLLiteral("3", dt);
		OWLLiteral conB = getFactory().getOWLLiteral("3", dt);
		assertEquals(conA.hashCode(), conB.hashCode());
	}
}
