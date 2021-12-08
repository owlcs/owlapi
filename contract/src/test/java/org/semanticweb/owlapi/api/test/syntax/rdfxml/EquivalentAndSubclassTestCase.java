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
package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * Tests the loading of a single ontology multiple times, using the same ontologyIRI in the
 * {@link OWLOntologyID} as that used in the actual ontology that is being imported.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
class EquivalentAndSubclassTestCase extends TestBase {

    @Test
    void testRoundtrip() {
        // given
        OWLOntology o =
            loadFrom(TestFiles.equivalentAndSubclasses, new ManchesterSyntaxDocumentFormat());
        relax(o);
        OWLOntology o2 = roundTrip(o, new RDFXMLDocumentFormat());
        equal(o, o2);
    }

    static void relax(OWLOntology ontology) {
        Set<OWLAxiom> newAxioms = new HashSet<>();
        ontology.axioms(AxiomType.EQUIVALENT_CLASSES).forEach(ax -> {
            ax.operands().forEach(operand -> {
                // we only relax in cases where the equivalence is between one
                // named and one anon expression
                if (!operand.isAnonymous()) {
                    OWLClass cClass = (OWLClass) operand;
                    // ax = EquivalentClasses(x y1 y2 ...)
                    for (OWLClassExpression expression : ax.getClassExpressionsMinus(cClass)) {
                        // limited structural reasoning:
                        // return (P some Z), if:
                        // - y is of the form (P some Z)
                        // - y is of the form ((P some Z) and ...),
                        // or any level of nesting
                        for (OWLObjectSomeValuesFrom svf : getSomeValuesFromAncestor(expression)) {
                            newAxioms.add(SubClassOf(cClass, svf));
                        }
                        for (OWLClass zClass : getNamedAncestors(expression)) {
                            newAxioms.add(SubClassOf(cClass, zClass));
                        }
                    }
                }
            });
        });
        // remove redundant axiom
        ontology.addAxioms(newAxioms);
    }

    private static Set<OWLObjectSomeValuesFrom> getSomeValuesFromAncestor(OWLClassExpression ex) {
        Set<OWLObjectSomeValuesFrom> svfs = new HashSet<>();
        if (ex instanceof OWLObjectSomeValuesFrom) {
            OWLObjectSomeValuesFrom svf = (OWLObjectSomeValuesFrom) ex;
            svfs.add(svf);
        } else if (ex instanceof OWLObjectCardinalityRestriction) {
            OWLObjectCardinalityRestriction ocr = (OWLObjectCardinalityRestriction) ex;
            OWLClassExpression filler = ocr.getFiller();
            OWLObjectPropertyExpression p = ocr.getProperty();
            if (ocr.getCardinality() > 0) {
                svfs.add(ObjectSomeValuesFrom(p, filler));
            }
        } else if (ex instanceof OWLObjectIntersectionOf) {
            ((OWLObjectIntersectionOf) ex).operands()
                .forEach(op -> svfs.addAll(getSomeValuesFromAncestor(op)));
        }
        return svfs;
    }

    private static Set<OWLClass> getNamedAncestors(OWLClassExpression expression) {
        Set<OWLClass> cs = new HashSet<>();
        if (!expression.isAnonymous()) {
            cs.add(expression.asOWLClass());
        } else if (expression instanceof OWLObjectIntersectionOf) {
            ((OWLObjectIntersectionOf) expression).operands()
                .forEach(op -> cs.addAll(getNamedAncestors(op)));
        }
        return cs;
    }
}
