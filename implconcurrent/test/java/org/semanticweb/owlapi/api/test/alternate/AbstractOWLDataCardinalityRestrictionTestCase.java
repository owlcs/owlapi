package org.semanticweb.owlapi.api.test.alternate;

import org.semanticweb.owlapi.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLRestriction;

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
public abstract class AbstractOWLDataCardinalityRestrictionTestCase extends
		AbstractOWLRestrictionTestCase<OWLDataProperty> {
	protected abstract OWLDataCardinalityRestriction createRestriction(
			OWLDataProperty prop, int cardinality) throws Exception;

	protected abstract OWLDataCardinalityRestriction createRestriction(
			OWLDataProperty prop, int cardinality, OWLDataRange dataRange)
			throws Exception;

	@Override
	protected OWLRestriction createRestriction(OWLDataProperty prop)
			throws Exception {
		return createRestriction(prop, 3);
	}

	@Override
	protected OWLDataProperty createProperty() throws OWLException {
		return createOWLDataProperty();
	}

	@Override
	public void testCreation() throws Exception {
		OWLDataProperty prop = getFactory().getOWLDataProperty(createIRI());
		int cardinality = 3;
		OWLDataCardinalityRestriction restA = createRestriction(prop,
				cardinality);
		assertNotNull(restA);
		OWLDataRange dataRange = getFactory().getOWLDatatype(createIRI());
		OWLDataCardinalityRestriction restB = createRestriction(prop,
				cardinality, dataRange);
		assertNotNull(restB);
	}

	@Override
	public void testEqualsPositive() throws Exception {
		OWLDataProperty prop = getFactory().getOWLDataProperty(createIRI());
		int cardinality = 3;
		OWLDataCardinalityRestriction restA = createRestriction(prop,
				cardinality);
		OWLDataCardinalityRestriction restB = createRestriction(prop,
				cardinality);
		assertEquals(restA, restB);
		OWLDataRange dataRange = getFactory().getOWLDatatype(createIRI());
		OWLDataCardinalityRestriction restC = createRestriction(prop,
				cardinality, dataRange);
		OWLDataCardinalityRestriction restD = createRestriction(prop,
				cardinality, dataRange);
		assertEquals(restC, restD);
	}

	@Override
	public void testEqualsNegative() throws Exception {
		OWLDataProperty prop = getFactory().getOWLDataProperty(createIRI());
		// Different cardinality
		OWLDataCardinalityRestriction restA = createRestriction(prop, 3);
		OWLDataCardinalityRestriction restB = createRestriction(prop, 4);
		assertNotEquals(restA, restB);
		// Different property
		OWLDataCardinalityRestriction restC = createRestriction(getFactory()
				.getOWLDataProperty(createIRI()), 3);
		OWLDataCardinalityRestriction restD = createRestriction(getFactory()
				.getOWLDataProperty(createIRI()), 3);
		assertNotEquals(restC, restD);
		// Different filler
		OWLDataCardinalityRestriction restE = createRestriction(prop, 3,
				getFactory().getOWLDatatype(createIRI()));
		OWLDataCardinalityRestriction restF = createRestriction(prop, 3,
				getFactory().getOWLDatatype(createIRI()));
		assertNotEquals(restE, restF);
	}

	@Override
	public void testHashCode() throws Exception {
		OWLDataProperty prop = getFactory().getOWLDataProperty(createIRI());
		int cardinality = 3;
		OWLDataRange dataRange = getFactory().getOWLDatatype(createIRI());
		OWLDataCardinalityRestriction restA = createRestriction(prop,
				cardinality, dataRange);
		OWLDataCardinalityRestriction restB = createRestriction(prop,
				cardinality, dataRange);
		assertEquals(restA, restB);
	}
}
