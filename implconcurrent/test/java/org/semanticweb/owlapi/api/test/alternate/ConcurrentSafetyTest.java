package org.semanticweb.owlapi.api.test.alternate;

/*
 * Copyright (C) 2010, University of Manchester
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

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apibinding.configurables.ThreadSafeOWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryCSR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryCWR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryFuture;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryLSR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryLWR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.MultiThreadChecker;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.TestMultithreadCallBack;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.Tester;

public class ConcurrentSafetyTest extends TestCase {
	private class CallBack1 implements TestMultithreadCallBack {
		private final OWLDataFactory f;
		private final Tester t;

		public CallBack1(OWLDataFactory factory, Tester t) {
			f = factory;
			this.t = t;
		}

		public void execute() throws Exception {
			t.run(f);
		}

		public String getId() {
			return "test for " + f.getClass().getSimpleName();
		}
	}

	private Tester tester = new Tester();
	private OWLDataFactory[] factories = new OWLDataFactory[] {
			 new DataFactoryCSR(),
			new DataFactoryCWR(), new DataFactoryFuture(),
			new DataFactoryLSR(), new DataFactoryLWR()};//,new OWLDataFactoryImpl() };

	public void testSafeImplementation() {
		for (OWLDataFactory d : factories) {
			actualrun(d);
			d.purge();
		}
	}

	private void actualrun(OWLDataFactory d) {
		MultiThreadChecker checker = new MultiThreadChecker();
		checker.check(new CallBack1(d, tester));
		System.out.println(checker.getTrace());
		assertTrue(checker.getTrace(), checker.isSuccessful());
	}
}
