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
package org.semanticweb.owlapi.apitest.searcher;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.domain;
import static org.semanticweb.owlapi.search.Searcher.equivalent;
import static org.semanticweb.owlapi.search.Searcher.range;
import static org.semanticweb.owlapi.search.Searcher.sub;
import static org.semanticweb.owlapi.search.Searcher.sup;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.contains;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.search.Searcher;

class SearcherTestCase extends TestBase {

    @Test
    void shouldSearch() {
        // given
        OWLOntology o = createAnon();
        OWLAxiom ax = SubClassOf(CLASSES.C, CLASSES.D);
        o.addAxiom(ax);
        assertTrue(contains(o.axioms(AxiomType.SUBCLASS_OF), ax));
        assertTrue(contains(o.axioms(CLASSES.C), ax));
    }

    @Test
    void shouldSearchObjectProperties() {
        // given
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(OBJPROPS.c, OBJPROPS.d);
        OWLOntology o = o(ax, ObjectPropertyDomain(OBJPROPS.c, CLASSES.X),
            ObjectPropertyRange(OBJPROPS.c, CLASSES.Y),
            EquivalentObjectProperties(OBJPROPS.c, OBJPROPS.e),
            SubObjectPropertyOf(OBJPROPS.c, ObjectInverseOf(OBJPROPS.f)),
            EquivalentObjectProperties(OBJPROPS.e, ObjectInverseOf(OBJPROPS.f)));
        assertTrue(contains(o.axioms(AxiomType.SUB_OBJECT_PROPERTY), ax));
        assertTrue(contains(sup(o.axioms(Filters.subObjectPropertyWithSub, OBJPROPS.c, INCLUDED)),
            OBJPROPS.d));
        assertTrue(contains(sub(o.axioms(Filters.subObjectPropertyWithSuper, OBJPROPS.d, INCLUDED)),
            OBJPROPS.c));
        assertTrue(contains(domain(o.objectPropertyDomainAxioms(OBJPROPS.c)), CLASSES.X));
        assertTrue(
            contains(equivalent(o.equivalentObjectPropertiesAxioms(OBJPROPS.c)), OBJPROPS.e));
        assertTrue(contains(equivalent(o.equivalentObjectPropertiesAxioms(OBJPROPS.e)),
            ObjectInverseOf(OBJPROPS.f)));
        Searcher.getSuperProperties(OBJPROPS.c, o).forEach(q -> assertTrue(checkMethod(q)));
    }

    @Test
    void shouldSearchDataProperties() {
        // given
        OWLAxiom ax = SubDataPropertyOf(DATAPROPS.DP, DATAPROPS.DQ);
        OWLOntology o = o(ax, DataPropertyDomain(DATAPROPS.DP, CLASSES.A),
            DataPropertyRange(DATAPROPS.DP, Boolean()),
            EquivalentDataProperties(DATAPROPS.DP, DATAPROPS.DR));
        assertTrue(contains(o.axioms(AxiomType.SUB_DATA_PROPERTY), ax));
        assertTrue(contains(sup(o.axioms(Filters.subDataPropertyWithSub, DATAPROPS.DP, INCLUDED)),
            DATAPROPS.DQ));
        assertTrue(contains(sub(o.axioms(Filters.subDataPropertyWithSuper, DATAPROPS.DQ, INCLUDED)),
            DATAPROPS.DP));
        assertTrue(contains(domain(o.dataPropertyDomainAxioms(DATAPROPS.DP)), CLASSES.A));
        assertTrue(contains(range(o.dataPropertyRangeAxioms(DATAPROPS.DP)), Boolean()));
        assertTrue(
            contains(equivalent(o.equivalentDataPropertiesAxioms(DATAPROPS.DP)), DATAPROPS.DR));
    }

    protected boolean checkMethod(OWLObject q) {
        return q instanceof OWLObjectPropertyExpression;
    }
}
