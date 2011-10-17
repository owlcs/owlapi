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


package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

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
/**
 * @author ignazio
 * no cache used
 */
public class InternalsNoCache implements OWLDataFactoryInternals {
    private final OWLDataFactory factory;

    /**
     * @param f the factory to refer to
     */
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