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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;
import org.semanticweb.owlapi.util.OWLEntityCollector;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Oct-2006<br>
 * <br>
 */
public abstract class OWLObjectImpl implements OWLObject {
	private final OWLDataFactory dataFactory;
	private int hashCode = 0;
	private Set<OWLEntity> signature;

	public OWLObjectImpl(OWLDataFactory dataFactory) {
		this.dataFactory = dataFactory;
	}

	public OWLDataFactory getOWLDataFactory() {
		return dataFactory;
	}

	public Set<OWLEntity> getSignature() {
		if (signature == null) {
			Set<OWLEntity> sig = new HashSet<OWLEntity>();
			List<OWLAnonymousIndividual> anons = new ArrayList<OWLAnonymousIndividual>();
			OWLEntityCollector collector = new OWLEntityCollector(sig, anons);
			accept(collector);
			signature = sig;
		}
		return CollectionFactory.getCopyOnRequestSet(signature);
	}

	public Set<OWLClass> getClassesInSignature() {
		Set<OWLClass> result = new HashSet<OWLClass>();
		for (OWLEntity ent : getSignature()) {
			if (ent.isOWLClass()) {
				result.add(ent.asOWLClass());
			}
		}
		return result;
	}

	public Set<OWLDataProperty> getDataPropertiesInSignature() {
		Set<OWLDataProperty> result = new HashSet<OWLDataProperty>();
		for (OWLEntity ent : getSignature()) {
			if (ent.isOWLDataProperty()) {
				result.add(ent.asOWLDataProperty());
			}
		}
		return result;
	}

	public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
		Set<OWLObjectProperty> result = new HashSet<OWLObjectProperty>();
		for (OWLEntity ent : getSignature()) {
			if (ent.isOWLObjectProperty()) {
				result.add(ent.asOWLObjectProperty());
			}
		}
		return result;
	}

	public Set<OWLNamedIndividual> getIndividualsInSignature() {
		Set<OWLNamedIndividual> result = new HashSet<OWLNamedIndividual>();
		for (OWLEntity ent : getSignature()) {
			if (ent.isOWLNamedIndividual()) {
				result.add(ent.asOWLNamedIndividual());
			}
		}
		return result;
	}

	/**
	 * A convenience method that obtains the datatypes that are in the signature
	 * of this object
	 * 
	 * @return A set containing the datatypes that are in the signature of this
	 *         object.
	 */
	public Set<OWLDatatype> getDatatypesInSignature() {
		Set<OWLDatatype> result = new HashSet<OWLDatatype>();
		for (OWLEntity ent : getSignature()) {
			if (ent.isOWLDatatype()) {
				result.add(ent.asOWLDatatype());
			}
		}
		return result;
	}

	public Set<OWLClassExpression> getNestedClassExpressions() {
		OWLClassExpressionCollector collector = new OWLClassExpressionCollector();
		return this.accept(collector);
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this || obj != null && obj instanceof OWLObject;
	}

	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = HashCode.hashCode(this);
		}
		return hashCode;
	}

	final public int compareTo(OWLObject o) {
		//        if (o instanceof OWLAxiom && this instanceof OWLAxiom) {
		//            OWLObject thisSubj = subjectProvider.getSubject((OWLAxiom) this);
		//            OWLObject otherSubj = subjectProvider.getSubject((OWLAxiom) o);
		//            int axDiff = thisSubj.compareTo(otherSubj);
		//            if (axDiff != 0) {
		//                return axDiff;
		//            }
		//        }
		OWLObjectTypeIndexProvider typeIndexProvider = new OWLObjectTypeIndexProvider();
		int thisTypeIndex = typeIndexProvider.getTypeIndex(this);
		int otherTypeIndex = typeIndexProvider.getTypeIndex(o);
		int diff = thisTypeIndex - otherTypeIndex;
		if (diff == 0) {
			// Objects are the same type
			return compareObjectOfSameType(o);
		} else {
			return diff;
		}
	}

	protected abstract int compareObjectOfSameType(OWLObject object);

	@Override
	public String toString() {
		return ToStringRenderer.getInstance().getRendering(this);
	}

	public boolean isTopEntity() {
		return false;
	}

	public boolean isBottomEntity() {
		return false;
	}

	protected static int compareSets(Set<? extends OWLObject> set1,
			Set<? extends OWLObject> set2) {
		SortedSet<? extends OWLObject> ss1;
		if (set1 instanceof SortedSet) {
			ss1 = (SortedSet<? extends OWLObject>) set1;
		} else {
			ss1 = new TreeSet<OWLObject>(set1);
		}
		SortedSet<? extends OWLObject> ss2;
		if (set2 instanceof SortedSet) {
			ss2 = (SortedSet<? extends OWLObject>) set2;
		} else {
			ss2 = new TreeSet<OWLObject>(set2);
		}
		int i = 0;
		Iterator<? extends OWLObject> thisIt = ss1.iterator();
		Iterator<? extends OWLObject> otherIt = ss2.iterator();
		while (i < ss1.size() && i < ss2.size()) {
			OWLObject o1 = thisIt.next();
			OWLObject o2 = otherIt.next();
			int diff = o1.compareTo(o2);
			if (diff != 0) {
				return diff;
			}
			i++;
		}
		return ss1.size() - ss2.size();
	}
}
