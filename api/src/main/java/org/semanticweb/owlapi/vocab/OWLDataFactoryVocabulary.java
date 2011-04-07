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

package org.semanticweb.owlapi.vocab;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

/**
 * This class keeps hold of often used constants; if the implementation of
 * OWLDataFactory and all acommpanying impl classes needs changing, this class
 * must be modified (the factory field needs to refer to the new implementation)
 */
public class OWLDataFactoryVocabulary {
	private static final OWLDataFactory factory = OWLDataFactoryImpl
			.getInstance();
	/**
	 * the built in owl:Thing class, which has a URI of
	 * &lt;http://www.w3.org/2002/07/owl#Thing&gt;
	 */
	public static final OWLClass OWLThing = factory.getOWLThing();
	/**
	 * the built in owl:Nothing class, which has a URI of
	 * &lt;http://www.w3.org/2002/07/owl#Nothing&gt;
	 */
	public static final OWLClass OWLNothing = factory.getOWLNothing();
	public static final OWLObjectProperty OWLTopObjectProperty = factory
			.getOWLTopObjectProperty();
	public static final OWLDataProperty OWLTopDataProperty = factory.getOWLTopDataProperty();
	public static final OWLObjectProperty OWLBottomObjectProperty = factory
			.getOWLBottomObjectProperty();
	public static final OWLDataProperty OWLBottomDataProperty = factory.getOWLBottomDataProperty();
	public static final OWLDatatype TopDatatype = factory.getTopDatatype();
}
