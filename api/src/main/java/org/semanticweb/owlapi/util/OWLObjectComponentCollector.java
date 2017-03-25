/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.util;

import java.util.Collection;
import java.util.HashSet;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
// XXX all special cases in this class look a lot like bugs.
public class OWLObjectComponentCollector extends AbstractCollectorEx<OWLObject> {

    /**
     * Default constructor
     */
    public OWLObjectComponentCollector() {
        super(new HashSet<>());
    }

    /**
     * A convenience method that obtains the components of an OWL object. Note that by definition,
     * the components of the object include the object itself.
     *
     * @param object The object whose components are to be obtained.
     * @return The component of the specified object.
     */
    public Collection<OWLObject> getComponents(OWLObject object) {
        return object.accept(this);
    }

    @Override
    public Collection<OWLObject> doDefault(OWLObject object) {
        objects.add(object);
        return super.doDefault(object);
    }

    @Override
    public Collection<OWLObject> visit(OWLOntology ontology) {
        ontology.axioms().forEach(a -> a.accept(this));
        return super.visit(ontology);
    }

    @Override
    public Collection<OWLObject> visit(OWLLiteral l) {
        objects.add(l);
        l.getDatatype().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLObject> visit(OWLAnnotation l) {
        return objects;
    }

    @Override
    public Collection<OWLObject> visit(OWLAnnotationAssertionAxiom l) {
        objects.add(l);
        l.getSubject().accept(this);
        l.getAnnotation().accept(this);
        return objects;
    }

    @Override
    public Collection<OWLObject> visit(SWRLVariable node) {
        objects.add(node);
        return objects;
    }

    @Override
    public Collection<OWLObject> visit(SWRLBuiltInAtom node) {
        objects.add(node);
        node.allArguments().forEach(a -> a.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLObject> visit(OWLDataSomeValuesFrom ce) {
        objects.add(ce);
        return ce.getProperty().accept(this);
    }

    @Override
    public Collection<OWLObject> visit(OWLDataAllValuesFrom ce) {
        objects.add(ce);
        return ce.getProperty().accept(this);
    }

    @Override
    public Collection<OWLObject> visit(OWLDataHasValue ce) {
        objects.add(ce);
        return ce.getProperty().accept(this);
    }

    @Override
    public Collection<OWLObject> visit(OWLDataMinCardinality ce) {
        objects.add(ce);
        return ce.getProperty().accept(this);
    }

    @Override
    public Collection<OWLObject> visit(OWLDataMaxCardinality ce) {
        objects.add(ce);
        return ce.getProperty().accept(this);
    }

    @Override
    public Collection<OWLObject> visit(OWLDataExactCardinality ce) {
        objects.add(ce);
        return ce.getProperty().accept(this);
    }

    @Override
    public Collection<OWLObject> visit(OWLDataPropertyDomainAxiom ce) {
        objects.add(ce);
        return ce.getProperty().accept(this);
    }

    @Override
    public Collection<OWLObject> visit(SWRLDifferentIndividualsAtom node) {
        objects.add(node);
        node.allArguments().forEach(a -> a.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLObject> visit(SWRLSameIndividualAtom node) {
        objects.add(node);
        node.allArguments().forEach(a -> a.accept(this));
        return objects;
    }
}
