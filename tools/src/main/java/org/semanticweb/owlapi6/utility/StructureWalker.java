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
package org.semanticweb.owlapi6.utility;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataRange;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;

/**
 * Structure walker for object walkers.
 *
 * @param <O> type to visit
 */
public class StructureWalker<O extends OWLObject> implements OWLObjectVisitor {

    protected final OWLObjectWalker<O> walkerCallback;
    protected final Set<OWLObject> visited = new HashSet<>();
    protected final AnnotationWalkingControl annotationWalkFlag;

    /**
     * @param owlObjectWalker callback object walker
     */
    public StructureWalker(OWLObjectWalker<O> owlObjectWalker) {
        this(owlObjectWalker, AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY);
    }

    /**
     * @param owlObjectWalker callback object walker
     * @param annotationWalkFlag control flag for annotation walking
     */
    public StructureWalker(OWLObjectWalker<O> owlObjectWalker,
        AnnotationWalkingControl annotationWalkFlag) {
        this.walkerCallback = owlObjectWalker;
        this.annotationWalkFlag = annotationWalkFlag;
    }

    protected void process(OWLObject object) {
        if (object instanceof OWLAxiom) {
            walkerCallback.setAxiom((OWLAxiom) object);
        }
        if (object instanceof OWLAnnotation) {
            walkerCallback.setAnnotation((OWLAnnotation) object);
        }
        if (!walkerCallback.visitDuplicates) {
            if (visited.add(object)) {
                walkerCallback.passToVisitor(object);
            }
        } else {
            walkerCallback.passToVisitor(object);
        }
        annotationWalkFlag.walk(this, object);
    }

    @Override
    public void visit(IRI iri) {
        process(iri);
    }

    @Override
    public void visit(OWLOntology ontology) {
        walkerCallback.ontology = ontology;
        walkerCallback.setAxiom(null);
        process(ontology);
        ontology.axioms().forEach(a -> a.accept(this));
    }

    @Override
    public void doDefault(OWLObject object) {
        if (object instanceof OWLClassExpression) {
            OWLClassExpression ce = (OWLClassExpression) object;
            walkerCallback.pushClassExpression(ce);
            process(ce);
            ce.componentStream().forEach(this::accept);
            walkerCallback.popClassExpression();
            return;
        }
        if (object instanceof OWLDataRange) {
            OWLDataRange ce = (OWLDataRange) object;
            walkerCallback.pushDataRange(ce);
            process(ce);
            ce.componentStream().forEach(this::accept);
            walkerCallback.popDataRange();
            return;
        }
        process(object);
        object.componentStream().forEach(this::accept);
    }

    @SuppressWarnings("unchecked")
    private void accept(Object o) {
        if (o instanceof Collection) {
            ((Collection<OWLObject>) o).forEach(this::accept);
        } else if (o instanceof OWLObject) {
            ((OWLObject) o).accept(this);
        }
    }
}
