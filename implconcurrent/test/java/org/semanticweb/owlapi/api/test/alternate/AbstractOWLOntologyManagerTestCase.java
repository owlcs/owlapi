package org.semanticweb.owlapi.api.test.alternate;

import org.semanticweb.owlapi.model.OWLOntologyManager;

/*
 * Copyright (C) 2007, University of Manchester
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
 * Bio-Health Informatics Group<br>
 * Date: 16-Mar-2007<br>
 * <br>
 */
public abstract class AbstractOWLOntologyManagerTestCase extends
		AbstractOWLDataFactoryTest {
	public static final String SYSTEM_PARAM_NAME = "OntologyManagerFactory";
	private OWLOntologyManager manager;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		String factoryName = System.getProperty(SYSTEM_PARAM_NAME);
		if (factoryName == null) {
			throw new RuntimeException("System property '" + SYSTEM_PARAM_NAME
					+ " must be set in order to run the tests");
		}
		Class cls = Class.forName(factoryName);
		OWLOntologyManagerFactory factory = (OWLOntologyManagerFactory) cls
				.newInstance();
		manager = factory.createOWLOntologyManager(getFactory());
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return manager;
	}

	@Override
	public void testCreation() throws Exception {
		// Ignore
	}

	@Override
	public void testEqualsPositive() throws Exception {
		// Ignore
	}

	@Override
	public void testEqualsNegative() throws Exception {
		// Ignore
	}

	@Override
	public void testHashCode() throws Exception {
		// Ignore
	}
}
