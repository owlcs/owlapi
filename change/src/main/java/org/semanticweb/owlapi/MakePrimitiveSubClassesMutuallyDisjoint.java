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
 * Copyright 2011, The University of Manchester
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

package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Jul-2007<br><br>
 * <p/>
 * For a given class, this composite change makes its told primitive subclasses mutually disjoint.
 * For example, if B, C, and D are primitive subclasses of A then this composite change will make
 * B, C, and D mutually disjoint.
 * <p/>
 * More formally, for a given class, A, and a set of ontologies, S, this method will obtain a
 * set of classes, G, where all classes in G are named and primitive.  Moreover, for any class, B in G,
 * some ontology O in S will contain an axiom, SubClassOf(B, A). All classes in G will be made
 * mutually disjoint by creating axiom(s) in a target ontology T.
 * <p/>
 * This composite change supports a common design pattern where primitive subclasses of a class
 * are made mutually disjoint.
 */
public class MakePrimitiveSubClassesMutuallyDisjoint extends AbstractCompositeOntologyChange {

    private OWLClass cls;

    private Set<OWLOntology> ontologies;

    private OWLOntology targetOntology;

    private boolean usePairwiseDisjointAxioms;

    private List<OWLOntologyChange> changes;


    public MakePrimitiveSubClassesMutuallyDisjoint(OWLDataFactory dataFactory, OWLClass cls, Set<OWLOntology> ontologies, OWLOntology targetOntology) {
        this(dataFactory, cls, ontologies, targetOntology, false);
    }


    public MakePrimitiveSubClassesMutuallyDisjoint(OWLDataFactory dataFactory, OWLClass cls, Set<OWLOntology> ontologies, OWLOntology targetOntology, boolean usePairwiseDisjointAxioms) {
        super(dataFactory);
        this.cls = cls;
        this.ontologies = ontologies;
        this.targetOntology = targetOntology;
        this.usePairwiseDisjointAxioms = usePairwiseDisjointAxioms;
        generateChanges();
    }


    private void generateChanges() {
        changes = new ArrayList<OWLOntologyChange>();
        Set<OWLClass> subclasses = new HashSet<OWLClass>();
        for (OWLOntology ont : ontologies) {
            for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSuperClass(cls)) {
                OWLClassExpression subCls = ax.getSubClass();
                if (!subCls.isAnonymous()) {
                    if (!subCls.asOWLClass().isDefined(ontologies)) {
                        subclasses.add(subCls.asOWLClass());
                    }
                }
            }
        }
        MakeClassesMutuallyDisjoint makeClassesMutuallyDisjoint = new MakeClassesMutuallyDisjoint(getDataFactory(), subclasses, usePairwiseDisjointAxioms, targetOntology);
        changes.addAll(makeClassesMutuallyDisjoint.getChanges());
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }
}
