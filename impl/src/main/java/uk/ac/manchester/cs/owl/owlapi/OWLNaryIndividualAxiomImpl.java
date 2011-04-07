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

package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNaryIndividualAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLNaryIndividualAxiomImpl extends OWLIndividualAxiomImpl implements OWLNaryIndividualAxiom {

    private Set<OWLIndividual> individuals;


    public OWLNaryIndividualAxiomImpl(OWLDataFactory dataFactory, Set<? extends OWLIndividual> individuals, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.individuals = new TreeSet<OWLIndividual>(individuals);
    }


    public Set<OWLIndividual> getIndividuals() {
        return CollectionFactory.getCopyOnRequestSet(individuals);
    }

    public List<OWLIndividual> getIndividualsAsList() {
        return new ArrayList<OWLIndividual>(individuals);
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLNaryIndividualAxiom)) {
                return false;
            }
            return ((OWLNaryIndividualAxiom) obj).getIndividuals().equals(individuals);
        }
        return false;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(individuals, ((OWLNaryIndividualAxiom) object).getIndividuals());
    }
}
