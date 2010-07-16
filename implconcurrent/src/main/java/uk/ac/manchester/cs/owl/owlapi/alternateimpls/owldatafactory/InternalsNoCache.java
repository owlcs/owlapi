package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;

public class InternalsNoCache implements
		OWLDataFactoryInternals {
	private final OWLDataFactory factory;

	public InternalsNoCache(OWLDataFactory f) {
		factory = f;
	}

	public void purge() {
	}

	public OWLClass getOWLClass(IRI iri) {
		return new OWLClassImpl(factory, iri);
	}

	public OWLObjectProperty getOWLObjectProperty(IRI iri) {
		return new OWLObjectPropertyImpl(factory, iri);
	}

	public OWLDataProperty getOWLDataProperty(IRI iri) {
		return new OWLDataPropertyImpl(factory, iri);
	}

	public OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
		return new OWLNamedIndividualImpl(factory, iri);
	}

	public OWLDatatype getOWLDatatype(IRI iri) {
		return new OWLDatatypeImpl(factory, iri);
	}

	public OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
		return new OWLAnnotationPropertyImpl(factory, iri);
	}
}