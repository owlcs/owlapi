/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.InternalsNoCache;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryNoCache;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.InternalsCSR;

@SuppressWarnings("javadoc")
public class OWLDataFactoryInternalsPerformanceTest extends TestCase {
	private final static OWLDataFactory factory = new DataFactoryNoCache();
	private final InternalsTester tester = new InternalsTester();

	public void testBaseline() {
		OWLDataFactoryInternals i = new InternalsNoCache(factory, false);
		tester.run(i);
	}

	public void testDefault() {
		OWLDataFactoryInternals i = new OWLDataFactoryInternalsImpl(factory, false);
		tester.run(i);
	}



	public void testConcurrentHashMapsStrongRefs() {
		OWLDataFactoryInternals i = new InternalsCSR(factory, false);
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

	t.testConcurrentHashMapsStrongRefs();

		long start = System.currentTimeMillis();
		t.testBaseline();
		System.out.println("baseline\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testDefault();
		System.out.println("default:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		System.out.println("FUT:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		start = System.currentTimeMillis();

		System.out.println("LWR:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		System.out.println("LSR:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		t.testConcurrentHashMapsStrongRefs();
		System.out.println("CSR:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		System.out.println("CWR:\t" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		System.out.println("FUTS:\t" + (System.currentTimeMillis() - start));
		System.out
				.println("OWLDataFactoryInternalsPerformanceTest.main() waiting for you to capture the memory snapshot");
//		while(true) {
//
//		}
	}
}
