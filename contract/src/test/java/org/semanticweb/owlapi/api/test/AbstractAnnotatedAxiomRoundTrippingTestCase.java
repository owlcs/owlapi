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

package org.semanticweb.owlapi.api.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-May-2009
 */
public abstract class AbstractAnnotatedAxiomRoundTrippingTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	final protected Set<? extends OWLAxiom> createAxioms() {
        OWLAnnotationProperty prop = getOWLAnnotationProperty("prop");
        OWLLiteral lit = getFactory().getOWLLiteral("Test", "");
        OWLAnnotation anno1 = getFactory().getOWLAnnotation(prop, lit);
        OWLAnnotationProperty prop2 = getOWLAnnotationProperty("prop2");
        OWLAnnotation anno2 = getFactory().getOWLAnnotation(prop2, lit);

        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        // Add two annotations per axiom
        annos.add(anno1);
        annos.add(anno2);
        OWLAxiom ax = getMainAxiom(annos);
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(ax.getAnnotatedAxiom(annos));
        axioms.add(getFactory().getOWLDeclarationAxiom(prop));
        axioms.add(getFactory().getOWLDeclarationAxiom(prop2));

        axioms.add(ax.getAnnotatedAxiom(Collections.singleton(anno1)));
        axioms.add(ax.getAnnotatedAxiom(Collections.singleton(anno2)));

//        Set<OWLAxiom> declarations = getDeclarationsToAdd(ax);
//        axioms.addAll(declarations);
        return axioms;
    }

//    protected Set<OWLAxiom> getDeclarationsToAdd(OWLAxiom ax) {
//        Set<OWLAxiom> declarations = new HashSet<OWLAxiom>();
//        for(OWLEntity ent : ax.getSignature()) {
//            declarations.add(getFactory().getOWLDeclarationAxiom(ent));
//        }
//        return declarations;
//    }

    protected abstract OWLAxiom getMainAxiom(Set<OWLAnnotation> annos);
}
