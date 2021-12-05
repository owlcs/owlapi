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
package org.semanticweb.owlapi.api.test.searcher;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Boolean;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.*;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.search.Filters;

class SearcherTestCase extends TestBase {

    private static final String URN_TEST = "urn:test#";

    @Test
    void shouldSearch() {
        // given
        OWLOntology o = createAnon();
        OWLAxiom ax = SubClassOf(C, D);
        o.getOWLOntologyManager().addAxiom(o, ax);
        assertTrue(o.getAxioms(AxiomType.SUBCLASS_OF).contains(ax));
        assertTrue(o.getAxioms(C, EXCLUDED).contains(ax));
    }

    @Test
    void shouldSearchObjectProperties() {
        // given
        OWLOntology o = createAnon();
        OWLAxiom ax = SubObjectPropertyOf(c, d);
        OWLAxiom ax2 = ObjectPropertyDomain(c, X);
        OWLAxiom ax3 = ObjectPropertyRange(c, Y);
        OWLAxiom ax4 = EquivalentObjectProperties(c, e);
        OWLAxiom ax5 = SubObjectPropertyOf(c, df.getOWLObjectInverseOf(f));
        OWLAxiom ax6 = EquivalentObjectProperties(e, df.getOWLObjectInverseOf(f));
        o.getOWLOntologyManager().addAxioms(o, set(ax, ax2, ax3, ax4, ax5, ax6));
        assertTrue(o.getAxioms(AxiomType.SUB_OBJECT_PROPERTY).contains(ax));
        Collection<OWLAxiom> axioms =
            o.filterAxioms(Filters.subObjectPropertyWithSuper, d, INCLUDED);
        assertTrue(sub(axioms).contains(c));
        axioms = o.filterAxioms(Filters.subObjectPropertyWithSub, c, INCLUDED);
        assertTrue(sup(axioms).contains(d));
        assertTrue(domain(o.getObjectPropertyDomainAxioms(c)).contains(X));
        assertTrue(equivalent(o.getEquivalentObjectPropertiesAxioms(c)).contains(e));
        assertTrue(equivalent(o.getEquivalentObjectPropertiesAxioms(e))
            .contains(df.getOWLObjectInverseOf(f)));
        EntitySearcher.getSuperProperties(c, o).forEach(q -> assertTrue(checkMethod(q)));
    }

    protected boolean checkMethod(OWLObject q) {
        return q instanceof OWLObjectPropertyExpression;
    }

    @Test
    void shouldSearchDataProperties() {
        // given
        OWLOntology o = createAnon();
        OWLAxiom ax = SubDataPropertyOf(DP, DQ);
        OWLClass x = Class(iri(URN_TEST, "x"));
        OWLAxiom ax2 = DataPropertyDomain(DP, x);
        OWLAxiom ax3 = DataPropertyRange(DP, Boolean());
        OWLAxiom ax4 = EquivalentDataProperties(DP, DR);
        o.getOWLOntologyManager().addAxioms(o, set(ax, ax2, ax3, ax4));
        assertTrue(o.getAxioms(AxiomType.SUB_DATA_PROPERTY).contains(ax));
        Collection<OWLAxiom> axioms =
            o.filterAxioms(Filters.subDataPropertyWithSuper, DQ, INCLUDED);
        assertTrue(sub(axioms).contains(DP));
        axioms = o.filterAxioms(Filters.subDataPropertyWithSub, DP, INCLUDED);
        assertTrue(sup(axioms).contains(DQ));
        assertTrue(domain(o.getDataPropertyDomainAxioms(DP)).contains(x));
        assertTrue(range(o.getDataPropertyRangeAxioms(DP)).contains(Boolean()));
        assertTrue(equivalent(o.getEquivalentDataPropertiesAxioms(DP)).contains(DR));
    }
}
