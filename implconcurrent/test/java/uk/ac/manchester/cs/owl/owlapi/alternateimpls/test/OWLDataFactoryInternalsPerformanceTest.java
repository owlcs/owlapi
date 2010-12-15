package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

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

import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryNoCache;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.InternalsCSR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.InternalsCWR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.InternalsFuture;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.InternalsFutureSmart;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.InternalsLSR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.InternalsLWR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.InternalsNoCache;

public class OWLDataFactoryInternalsPerformanceTest extends TestCase {
	private final static OWLDataFactory factory = new DataFactoryNoCache();
	private final InternalsTester tester = new InternalsTester();

	public void testBaseline() {
		OWLDataFactoryInternals i = new InternalsNoCache(factory);
		tester.run(i);
	}

	public void testDefault() {
		OWLDataFactoryInternals i = new OWLDataFactoryInternalsImpl(factory);
		tester.run(i);
	}

	public void testFastLock() {
		OWLDataFactoryInternals i = new InternalsFuture(factory);
		tester.run(i);
	}

	public void testLockWeakReferences() {
		OWLDataFactoryInternals i = new InternalsLWR(factory);
		tester.run(i);
	}
	public void testLockStrongReferences() {
		OWLDataFactoryInternals i = new InternalsLSR(factory);
		tester.run(i);
	}

	public void testConcurrentHashMapsStrongRefs() {
		OWLDataFactoryInternals i = new InternalsCSR(factory);
		tester.run(i);
	}

	public void testConcurrentHashMapsWeakRefs() {
		OWLDataFactoryInternals i = new InternalsCWR(factory);
		tester.run(i);
	}

	public void testFutureSmart() {
		OWLDataFactoryInternals i = new InternalsFutureSmart(factory);
		tester.run(i);
	}

	@Override
	protected void tearDown() throws Exception {
		System.gc();
	}

	public static void main(String[] args) {
		OWLDataFactoryInternalsPerformanceTest t = new OWLDataFactoryInternalsPerformanceTest();
		t.testBaseline();
		t.testDefault();
		t.testFastLock();
		t.testLockWeakReferences();
		t.testLockStrongReferences();
		t.testConcurrentHashMapsStrongRefs();
		t.testConcurrentHashMapsWeakRefs();
		t.testFutureSmart();
		long start = System.currentTimeMillis();
		t.testBaseline();
		System.out.println("baseline\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testDefault();
		System.out.println("default:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testFastLock();
		System.out.println("FUT:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		start = System.currentTimeMillis();
		t.testLockWeakReferences();
		System.out.println("LWR:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testLockStrongReferences();
		System.out.println("LSR:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testConcurrentHashMapsStrongRefs();
		System.out.println("CSR:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testConcurrentHashMapsWeakRefs();
		System.out.println("CWR:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testFutureSmart();
		System.out.println("FUTS:\t" + (System.currentTimeMillis() - start));
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() waiting for you to capture the memory snapshot");
//		while(true) {
//			
//		}
	}
}
