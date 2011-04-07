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

package org.coode.owlapi.obo.renderer;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

/**
 * Author: Nick Drummond<br>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 18, 2008<br><br>
 */
public class OBORelationshipGenerator extends OWLClassExpressionVisitorAdapter {

    private Set<OBORelationship> relationships = new HashSet<OBORelationship>();

    private OBOExceptionHandler eHandler;

    private OWLClass cls;


    public OBORelationshipGenerator(OBOExceptionHandler eHandler) {
        this.eHandler = eHandler;
    }


    public void setClass(OWLClass cls) {
        this.cls = cls;
        clear();
    }


    public void clear() {
        relationships.clear();
    }


    public Set<OBORelationship> getOBORelationships() {
        return new HashSet<OBORelationship>(relationships);
    }


    @Override
	public void visit(OWLObjectSomeValuesFrom desc) {
        getRelationship(desc);
    }


    @Override
	public void visit(OWLObjectMinCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setMinCardinality(desc.getCardinality());
        }
    }


    @Override
	public void visit(OWLObjectExactCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setCardinality(desc.getCardinality());
        }
    }


    @Override
	public void visit(OWLObjectMaxCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setMaxCardinality(desc.getCardinality());
        }
    }

    // TODO error handling for un-translatable class expressions

    private OBORelationship getRelationship(OWLObjectCardinalityRestriction desc) {
        if (desc.isAnonymous() && !desc.getFiller().isAnonymous()) {
            final OWLObjectProperty p = desc.getProperty().asOWLObjectProperty();
            final OWLClass f = desc.getFiller().asOWLClass();

            for (OBORelationship rel : relationships) {
                if (rel.getProperty().equals(p) && rel.getFiller().equals(f)) {
                    return rel;
                }
            }
            final OBORelationship newRel = new OBORelationship(p, f);
            relationships.add(newRel);
            return newRel;
        }

        eHandler.addException(new OBOStorageException(cls, desc, "Anonymous filler of some restriction cannot be converted to OBO"));
        return null;
    }

    private OBORelationship getRelationship(OWLQuantifiedObjectRestriction desc) {
        if (desc.isAnonymous() && !desc.getFiller().isAnonymous()) {
            final OWLObjectProperty p = desc.getProperty().asOWLObjectProperty();
            final OWLClass f = desc.getFiller().asOWLClass();

            for (OBORelationship rel : relationships) {
                if (rel.getProperty().equals(p) && rel.getFiller().equals(f)) {
                    return rel;
                }
            }
            final OBORelationship newRel = new OBORelationship(p, f);
            relationships.add(newRel);
            return newRel;
        }

        eHandler.addException(new OBOStorageException(cls, desc, "Anonymous filler of some restriction cannot be converted to OBO"));
        return null;
    }
}
