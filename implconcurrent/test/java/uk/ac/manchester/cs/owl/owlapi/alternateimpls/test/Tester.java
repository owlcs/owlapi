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

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;

public class Tester {
		public static final int _10 = 10;
		public static final int _10000 = 10000;
		protected List<IRI> iriClasses = new ArrayList<IRI>();
		protected List<IRI> iriDataproperties = new ArrayList<IRI>();
		protected List<IRI> iriObjectProperties = new ArrayList<IRI>();
		protected List<IRI> iriIndividuals = new ArrayList<IRI>();
		protected List<IRI> iriDatatypes = new ArrayList<IRI>();
		protected List<IRI> iriAnnotations = new ArrayList<IRI>();

		public Tester() {
			init();
		}

		private void init() {
			String uriClasses = "urn:test:classes#A";
			add(iriClasses, uriClasses);
			String objects = "urn:test:objectproperties#B";
			add(iriObjectProperties, objects);
			String datas = "urn:test:dataproperties#C";
			add(iriDataproperties, datas);
			String inds = "urn:test:individuals#D";
			add(iriIndividuals, inds);
			String datatypes = "urn:test:datatypes#E";
			add(iriDatatypes, datatypes);
			String annotations = "urn:test:annotations#F";
			add(iriAnnotations, annotations);
		}

		public void run(OWLDataFactory toTest) {
			for (int i = 0; i < _10; i++) {
				for (IRI iri : iriClasses) {
					singleRunClasses(toTest, iri);
				}
				for (IRI iri : iriObjectProperties) {
					singleRunObjectProp(toTest, iri);
				}
				for (IRI iri : iriDataproperties) {
					singleRunDataprop(toTest, iri);
				}
				for (IRI iri : iriIndividuals) {
					singleRunIndividuals(toTest, iri);
				}
				for (IRI iri : iriDatatypes) {
					singleRunDatatype(toTest, iri);
				}
				for (IRI iri : iriAnnotations) {
					singleRunAnnotations(toTest, iri);
				}
			}
		}

		private void add(List<IRI> l, String s) {
			for (int i = 0; i < _10000; i++) {
				l.add(IRI.create(s + i));
			}
		}

		private void singleRunClasses(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLClass(iri);
		}

		private void singleRunObjectProp(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLObjectProperty(iri);
		}

		private void singleRunDataprop(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLDataProperty(iri);
		}

		private void singleRunDatatype(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLDatatype(iri);
		}

		private void singleRunIndividuals(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLNamedIndividual(iri);
		}

		private void singleRunAnnotations(OWLDataFactory toTest, IRI iri) {
			toTest.getOWLAnnotationProperty(iri);
		}
	}