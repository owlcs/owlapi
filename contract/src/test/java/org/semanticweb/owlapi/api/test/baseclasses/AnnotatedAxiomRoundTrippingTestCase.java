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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;

import com.google.common.collect.Sets;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public abstract class AnnotatedAxiomRoundTrippingTestCase extends
    AxiomsRoundTrippingBase {

    private static OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
    private static OWLLiteral lit = Literal("Test", "");
    private static OWLAnnotation anno1 = Annotation(prop, lit);
    private static OWLAnnotationProperty prop2 = AnnotationProperty(iri(
        "prop2"));
    private static OWLAnnotation anno2 = Annotation(prop2, lit);
    private static Set<OWLAnnotation> annos = Sets.newHashSet(anno1, anno2);

    public AnnotatedAxiomRoundTrippingTestCase(
        Function<Set<OWLAnnotation>, OWLAxiom> f) {
        super(() -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            OWLAxiom ax = f.apply(annos);
            axioms.add(ax.getAnnotatedAxiom(annos));
            axioms.add(Declaration(prop));
            axioms.add(Declaration(prop2));
            axioms.add(ax.getAnnotatedAxiom(singleton(anno1)));
            axioms.add(ax.getAnnotatedAxiom(singleton(anno2)));
            return axioms;
        } );
    }
}
