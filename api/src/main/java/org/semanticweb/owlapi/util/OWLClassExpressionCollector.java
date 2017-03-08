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

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Collects all of the nested class expression that are used in some OWLObject. For example, given
 * SubClassOf(ObjectUnionOf(D C) ObjectSomeValuesFrom(R F)) the collector could be used to obtain
 * ObjectUnionOf(D C), D, C, ObjectSomeValuesFrom(R F), F
 *
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
public class OWLClassExpressionCollector extends AbstractCollectorEx<OWLClassExpression> {

    /**
     * The default collection is a set
     */
    public OWLClassExpressionCollector() {
        super(new HashSet<>());
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLOntology ontology) {
        ontology.logicalAxioms().forEach(ax -> ax.accept(this));
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLClass ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectIntersectionOf ce) {
        objects.add(ce);
        return super.visit(ce);
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectUnionOf ce) {
        objects.add(ce);
        return super.visit(ce);
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectComplementOf ce) {
        objects.add(ce);
        return super.visit(ce);
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectSomeValuesFrom ce) {
        objects.add(ce);
        return super.visit(ce);
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectAllValuesFrom ce) {
        objects.add(ce);
        return super.visit(ce);
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectHasValue ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectMinCardinality ce) {
        objects.add(ce);
        return super.visit(ce);
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectExactCardinality ce) {
        objects.add(ce);
        return super.visit(ce);
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectMaxCardinality ce) {
        objects.add(ce);
        return super.visit(ce);
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectHasSelf ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLObjectOneOf ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLDataSomeValuesFrom ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLDataAllValuesFrom ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLDataHasValue ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLDataMinCardinality ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLDataExactCardinality ce) {
        objects.add(ce);
        return objects;
    }

    @Override
    public Collection<OWLClassExpression> visit(OWLDataMaxCardinality ce) {
        objects.add(ce);
        return objects;
    }
}
