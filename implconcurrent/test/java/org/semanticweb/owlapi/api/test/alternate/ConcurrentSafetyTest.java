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

package org.semanticweb.owlapi.api.test.alternate;

import junit.framework.TestCase;

import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory.DataFactoryCSR;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.MultiThreadChecker;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.TestMultithreadCallBack;
import uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.Tester;
@SuppressWarnings("javadoc")
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
			 new DataFactoryCSR()
};//,new OWLDataFactoryImpl() };

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
