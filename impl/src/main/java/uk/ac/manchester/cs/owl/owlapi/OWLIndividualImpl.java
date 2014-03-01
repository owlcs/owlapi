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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 25-Oct-2006
 */
public abstract class OWLIndividualImpl extends OWLObjectImpl implements
        OWLIndividual {

    private static final long serialVersionUID = 30406L;

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            boolean isBuiltIn() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OWLIndividual;
    }

    @Override
    public Set<OWLClassExpression> getTypes(OWLOntology ontology) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLClassAssertionAxiom axiom : ontology
                .getClassAssertionAxioms(this)) {
            result.add(axiom.getClassExpression());
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> getTypes(Set<OWLOntology> ontologies) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getTypes(ont));
        }
        return result;
    }

    @Override
    public Set<OWLIndividual> getObjectPropertyValues(
            OWLObjectPropertyExpression property, OWLOntology ontology) {
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> map = getObjectPropertyValues(ontology);
        Set<OWLIndividual> vals = map.get(property);
        if (vals == null) {
            return Collections.emptySet();
        } else {
            return new HashSet<OWLIndividual>(vals);
        }
    }

    @Override
    public Set<OWLLiteral> getDataPropertyValues(
            OWLDataPropertyExpression property, OWLOntology ontology) {
        Set<OWLLiteral> result = new HashSet<OWLLiteral>();
        for (OWLDataPropertyAssertionAxiom ax : ontology
                .getDataPropertyAssertionAxioms(this)) {
            if (ax.getProperty().equals(property)) {
                result.add(ax.getObject());
            }
        }
        return result;
    }

    @Override
    public boolean hasObjectPropertyValue(OWLObjectPropertyExpression property,
            OWLIndividual individual, OWLOntology ontology) {
        for (OWLObjectPropertyAssertionAxiom ax : ontology
                .getObjectPropertyAssertionAxioms(this)) {
            if (ax.getProperty().equals(property)
                    && ax.getObject().equals(individual)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasDataPropertyValue(OWLDataPropertyExpression property,
            OWLLiteral value, OWLOntology ontology) {
        for (OWLDataPropertyAssertionAxiom ax : ontology
                .getDataPropertyAssertionAxioms(this)) {
            if (ax.getProperty().equals(property)
                    && ax.getObject().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<OWLObjectPropertyExpression, Set<OWLIndividual>>
            getObjectPropertyValues(OWLOntology ontology) {
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result = new HashMap<OWLObjectPropertyExpression, Set<OWLIndividual>>();
        for (OWLObjectPropertyAssertionAxiom ax : ontology
                .getObjectPropertyAssertionAxioms(this)) {
            Set<OWLIndividual> inds = result.get(ax.getProperty());
            if (inds == null) {
                inds = new TreeSet<OWLIndividual>();
                result.put(ax.getProperty(), inds);
            }
            inds.add(ax.getObject());
        }
        return result;
    }

    @Override
    public Map<OWLObjectPropertyExpression, Set<OWLIndividual>>
            getNegativeObjectPropertyValues(OWLOntology ontology) {
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result = new HashMap<OWLObjectPropertyExpression, Set<OWLIndividual>>();
        for (OWLNegativeObjectPropertyAssertionAxiom ax : ontology
                .getNegativeObjectPropertyAssertionAxioms(this)) {
            Set<OWLIndividual> inds = result.get(ax.getProperty());
            if (inds == null) {
                inds = new TreeSet<OWLIndividual>();
                result.put(ax.getProperty(), inds);
            }
            inds.add(ax.getObject());
        }
        return result;
    }

    @Override
    public boolean hasNegativeObjectPropertyValue(
            OWLObjectPropertyExpression property, OWLIndividual individual,
            OWLOntology ontology) {
        for (OWLNegativeObjectPropertyAssertionAxiom ax : ontology
                .getNegativeObjectPropertyAssertionAxioms(this)) {
            if (ax.getProperty().equals(property)
                    && ax.getObject().equals(individual)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<OWLDataPropertyExpression, Set<OWLLiteral>>
            getDataPropertyValues(OWLOntology ontology) {
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result = new HashMap<OWLDataPropertyExpression, Set<OWLLiteral>>();
        for (OWLDataPropertyAssertionAxiom ax : ontology
                .getDataPropertyAssertionAxioms(this)) {
            Set<OWLLiteral> vals = result.get(ax.getProperty());
            if (vals == null) {
                vals = new TreeSet<OWLLiteral>();
                result.put(ax.getProperty(), vals);
            }
            vals.add(ax.getObject());
        }
        return result;
    }

    @Override
    public Map<OWLDataPropertyExpression, Set<OWLLiteral>>
            getNegativeDataPropertyValues(OWLOntology ontology) {
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result = new HashMap<OWLDataPropertyExpression, Set<OWLLiteral>>();
        for (OWLNegativeDataPropertyAssertionAxiom ax : ontology
                .getNegativeDataPropertyAssertionAxioms(this)) {
            Set<OWLLiteral> inds = result.get(ax.getProperty());
            if (inds == null) {
                inds = new TreeSet<OWLLiteral>();
                result.put(ax.getProperty(), inds);
            }
            inds.add(ax.getObject());
        }
        return result;
    }

    @Override
    public boolean hasNegativeDataPropertyValue(
            OWLDataPropertyExpression property, OWLLiteral literal,
            OWLOntology ontology) {
        for (OWLNegativeDataPropertyAssertionAxiom ax : ontology
                .getNegativeDataPropertyAssertionAxioms(this)) {
            if (ax.getProperty().equals(property)
                    && ax.getObject().equals(literal)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<OWLIndividual> getSameIndividuals(OWLOntology ontology) {
        Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
        for (OWLSameIndividualAxiom ax : ontology.getSameIndividualAxioms(this)) {
            result.addAll(ax.getIndividuals());
        }
        result.remove(this);
        return result;
    }

    @Override
    public Set<OWLIndividual> getDifferentIndividuals(OWLOntology ontology) {
        Set<OWLIndividual> result = new TreeSet<OWLIndividual>();
        for (OWLDifferentIndividualsAxiom ax : ontology
                .getDifferentIndividualAxioms(this)) {
            result.addAll(ax.getIndividuals());
        }
        result.remove(this);
        return result;
    }

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not an OWLClass!");
    }

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            OWLDataProperty asOWLDataProperty() {
        throw new OWLRuntimeException("Not a data property!");
    }

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            OWLDatatype asOWLDatatype() {
        throw new OWLRuntimeException("Not a data type!");
    }

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            OWLObjectProperty asOWLObjectProperty() {
        throw new OWLRuntimeException("Not an object property");
    }

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            boolean isOWLClass() {
        return false;
    }

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            boolean isOWLDataProperty() {
        return false;
    }

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            boolean isOWLDatatype() {
        return false;
    }

    @SuppressWarnings("javadoc")
    // XXX not in the interface
            @Deprecated
            public
            boolean isOWLObjectProperty() {
        return false;
    }
}
