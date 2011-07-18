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
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 29-Nov-2009
 */
public class RenameEntityTestCase extends AbstractOWLAPITestCase {

    public void testRenameClass() throws Exception {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsAIRI1 = getOWLClass("ClsA1");
        OWLClass clsAIRI2 = getOWLClass("ClsA2");
        OWLClass clsB = getOWLClass("ClsB");
        OWLClass clsC = getOWLClass("ClsC");
        OWLObjectPropertyExpression propA = getOWLObjectProperty("propA");
        OWLDataPropertyExpression propB = getOWLDataProperty("propA");
        OWLIndividual indA = getOWLIndividual("indA");
        OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");


        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(getFactory().getOWLSubClassOfAxiom(clsAIRI1, clsB));
        axioms1.add(getFactory().getOWLEquivalentClassesAxiom(clsAIRI1, clsC));
        axioms1.add(getFactory().getOWLDisjointClassesAxiom(clsAIRI1, clsC));
        axioms1.add(getFactory().getOWLObjectPropertyDomainAxiom(propA, clsAIRI1));
        axioms1.add(getFactory().getOWLObjectPropertyRangeAxiom(propA, clsAIRI1));
        axioms1.add(getFactory().getOWLDataPropertyDomainAxiom(propB, clsAIRI1));
        axioms1.add(getFactory().getOWLClassAssertionAxiom(clsAIRI1, indA));
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, clsAIRI1.getIRI(), getFactory().getOWLLiteral("X")));
        getManager().addAxioms(ont, axioms1);

        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLSubClassOfAxiom(clsAIRI2, clsB));
        axioms2.add(getFactory().getOWLEquivalentClassesAxiom(clsAIRI2, clsC));
        axioms2.add(getFactory().getOWLDisjointClassesAxiom(clsAIRI2, clsC));
        axioms2.add(getFactory().getOWLObjectPropertyDomainAxiom(propA, clsAIRI2));
        axioms2.add(getFactory().getOWLObjectPropertyRangeAxiom(propA, clsAIRI2));
        axioms2.add(getFactory().getOWLDataPropertyDomainAxiom(propB, clsAIRI2));
        axioms2.add(getFactory().getOWLClassAssertionAxiom(clsAIRI2, indA));
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, clsAIRI2.getIRI(), getFactory().getOWLLiteral("X")));

        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(), Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(clsAIRI1, clsAIRI2.getIRI());
        getManager().applyChanges(changes);

        assertEquals(ont.getAxioms(), axioms2);

        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(clsAIRI2.getIRI(), clsAIRI1.getIRI());
        getManager().applyChanges(changes2);

        assertEquals(ont.getAxioms(), axioms1);
    }

    public void testRenameObjectProperty() throws Exception {

        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = getOWLClass("ClsA");
        OWLObjectProperty propA = getOWLObjectProperty("propA");
        OWLObjectProperty propA2 = getOWLObjectProperty("propA2");
        OWLObjectPropertyExpression propB = getOWLObjectProperty("propB").getInverseProperty();
        OWLIndividual indA = getOWLIndividual("indA");
        OWLIndividual indB = getOWLIndividual("indB");
        OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");


        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(getFactory().getOWLSubObjectPropertyOfAxiom(propA, propB));
        axioms1.add(getFactory().getOWLEquivalentObjectPropertiesAxiom(propA, propB));
        axioms1.add(getFactory().getOWLDisjointObjectPropertiesAxiom(propA, propB));
        axioms1.add(getFactory().getOWLObjectPropertyDomainAxiom(propA, clsA));
        axioms1.add(getFactory().getOWLObjectPropertyRangeAxiom(propA, clsA));
        axioms1.add(getFactory().getOWLFunctionalObjectPropertyAxiom(propA));
        axioms1.add(getFactory().getOWLInverseFunctionalObjectPropertyAxiom(propA));
        axioms1.add(getFactory().getOWLSymmetricObjectPropertyAxiom(propA));
        axioms1.add(getFactory().getOWLAsymmetricObjectPropertyAxiom(propA));
        axioms1.add(getFactory().getOWLTransitiveObjectPropertyAxiom(propA));
        axioms1.add(getFactory().getOWLReflexiveObjectPropertyAxiom(propA));
        axioms1.add(getFactory().getOWLIrreflexiveObjectPropertyAxiom(propA));
        axioms1.add(getFactory().getOWLObjectPropertyAssertionAxiom(propA, indA, indB));
        axioms1.add(getFactory().getOWLNegativeObjectPropertyAssertionAxiom(propA, indA, indB));
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA.getIRI(), getFactory().getOWLLiteral("X")));
        getManager().addAxioms(ont, axioms1);

        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLSubObjectPropertyOfAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLEquivalentObjectPropertiesAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLDisjointObjectPropertiesAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLObjectPropertyDomainAxiom(propA2, clsA));
        axioms2.add(getFactory().getOWLObjectPropertyRangeAxiom(propA2, clsA));
        axioms2.add(getFactory().getOWLFunctionalObjectPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLInverseFunctionalObjectPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLSymmetricObjectPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLAsymmetricObjectPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLTransitiveObjectPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLReflexiveObjectPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLIrreflexiveObjectPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLObjectPropertyAssertionAxiom(propA2, indA, indB));
        axioms2.add(getFactory().getOWLNegativeObjectPropertyAssertionAxiom(propA2, indA, indB));
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA2.getIRI(), getFactory().getOWLLiteral("X")));


        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(), Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(propA, propA2.getIRI());
        getManager().applyChanges(changes);

        assertEquals(ont.getAxioms(), axioms2);

        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(propA2.getIRI(), propA.getIRI());
        getManager().applyChanges(changes2);

        assertEquals(ont.getAxioms(), axioms1);
    }

    public void testRenameDataProperty() throws Exception {

        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = getOWLClass("ClsA");
        OWLDataProperty propA = getOWLDataProperty("propA");
        OWLDataProperty propA2 = getOWLDataProperty("propA2");
        OWLDataPropertyExpression propB = getOWLDataProperty("propB");
        OWLIndividual indA = getOWLIndividual("indA");
        OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");


        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(getFactory().getOWLSubDataPropertyOfAxiom(propA, propB));
        axioms1.add(getFactory().getOWLEquivalentDataPropertiesAxiom(propA, propB));
        axioms1.add(getFactory().getOWLDisjointDataPropertiesAxiom(propA, propB));
        axioms1.add(getFactory().getOWLDataPropertyDomainAxiom(propA, clsA));
        axioms1.add(getFactory().getOWLDataPropertyRangeAxiom(propA, getFactory().getTopDatatype()));
        axioms1.add(getFactory().getOWLFunctionalDataPropertyAxiom(propA));
        axioms1.add(getFactory().getOWLDataPropertyAssertionAxiom(propA, indA, getFactory().getOWLLiteral(33)));
        axioms1.add(getFactory().getOWLNegativeDataPropertyAssertionAxiom(propA, indA, getFactory().getOWLLiteral(44)));
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA.getIRI(), getFactory().getOWLLiteral("X")));
        getManager().addAxioms(ont, axioms1);

        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLSubDataPropertyOfAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLEquivalentDataPropertiesAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLDisjointDataPropertiesAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLDataPropertyDomainAxiom(propA2, clsA));
        axioms2.add(getFactory().getOWLDataPropertyRangeAxiom(propA2, getFactory().getTopDatatype()));
        axioms2.add(getFactory().getOWLFunctionalDataPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLDataPropertyAssertionAxiom(propA2, indA, getFactory().getOWLLiteral(33)));
        axioms2.add(getFactory().getOWLNegativeDataPropertyAssertionAxiom(propA2, indA, getFactory().getOWLLiteral(44)));
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA2.getIRI(), getFactory().getOWLLiteral("X")));


        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(), Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(propA, propA2.getIRI());
        getManager().applyChanges(changes);

        assertEquals(ont.getAxioms(), axioms2);

        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(propA2.getIRI(), propA.getIRI());
        getManager().applyChanges(changes2);

        assertEquals(ont.getAxioms(), axioms1);
    }

    public void testRenameIndividual() throws Exception {
        OWLOntology ont = getOWLOntology("testont");
        OWLClass clsA = getOWLClass("ClsA");
        OWLDataProperty propA = getOWLDataProperty("propA");
        OWLObjectProperty propB = getOWLObjectProperty("propB");
        OWLNamedIndividual indA = getOWLIndividual("indA");
        OWLNamedIndividual indB = getOWLIndividual("indA");
        OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");

        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(getFactory().getOWLClassAssertionAxiom(clsA, indA));
        axioms1.add(getFactory().getOWLDataPropertyAssertionAxiom(propA, indA, getFactory().getOWLLiteral(33)));
        axioms1.add(getFactory().getOWLNegativeDataPropertyAssertionAxiom(propA, indA, getFactory().getOWLLiteral(44)));
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA.getIRI(), getFactory().getOWLLiteral("X")));
        axioms1.add(getFactory().getOWLObjectPropertyAssertionAxiom(propB, indA, indB));
        axioms1.add(getFactory().getOWLNegativeObjectPropertyAssertionAxiom(propB, indA, indB));

        getManager().addAxioms(ont, axioms1);


        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLClassAssertionAxiom(clsA, indB));
        axioms2.add(getFactory().getOWLDataPropertyAssertionAxiom(propA, indB, getFactory().getOWLLiteral(33)));
        axioms2.add(getFactory().getOWLNegativeDataPropertyAssertionAxiom(propA, indB, getFactory().getOWLLiteral(44)));
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA.getIRI(), getFactory().getOWLLiteral("X")));
        axioms2.add(getFactory().getOWLObjectPropertyAssertionAxiom(propB, indB, indB));
        axioms2.add(getFactory().getOWLNegativeObjectPropertyAssertionAxiom(propB, indB, indB));


        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(), Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(indA, indB.getIRI());
        getManager().applyChanges(changes);

        assertEquals(ont.getAxioms(), axioms2);

        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(indB.getIRI(), indA.getIRI());
        getManager().applyChanges(changes2);

        assertEquals(ont.getAxioms(), axioms1);

    }

    public void testRenameDatatype() throws Exception {
        OWLOntology ont = getOWLOntology("testont");

        OWLDatatype dtA = getOWLDatatype("DtA");
        OWLDatatype dtB = getOWLDatatype("DtB");
        OWLDatatype dtC = getOWLDatatype("DtC");

        OWLDataRange rng1 = getFactory().getOWLDataIntersectionOf(dtA, dtB);
        OWLDataRange rng1R = getFactory().getOWLDataIntersectionOf(dtC, dtB);

        OWLDataRange rng2 = getFactory().getOWLDataUnionOf(dtA, dtB);
        OWLDataRange rng2R = getFactory().getOWLDataUnionOf(dtC, dtB);

        OWLDataRange rng3 = getFactory().getOWLDataComplementOf(dtA);
        OWLDataRange rng3R = getFactory().getOWLDataComplementOf(dtC);


        OWLDataPropertyExpression propB = getOWLDataProperty("propA");


        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(getFactory().getOWLDataPropertyRangeAxiom(propB, rng1));
        axioms1.add(getFactory().getOWLDataPropertyRangeAxiom(propB, rng2));
        axioms1.add(getFactory().getOWLDataPropertyRangeAxiom(propB, rng3));
        getManager().addAxioms(ont, axioms1);

        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLDataPropertyRangeAxiom(propB, rng1R));
        axioms2.add(getFactory().getOWLDataPropertyRangeAxiom(propB, rng2R));
        axioms2.add(getFactory().getOWLDataPropertyRangeAxiom(propB, rng3R));


        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(), Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(dtA, dtC.getIRI());
        getManager().applyChanges(changes);

        assertEquals(ont.getAxioms(), axioms2);

        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(dtC.getIRI(), dtA.getIRI());
        getManager().applyChanges(changes2);

        assertEquals(ont.getAxioms(), axioms1);
    }


    public void testRenameAnnotationProperty() throws Exception {

        OWLOntology ont = getOWLOntology("testont");

        OWLNamedIndividual indA = getOWLIndividual("indA");
        OWLNamedIndividual indB = getOWLIndividual("indB");
        OWLAnnotationProperty annoProp = getOWLAnnotationProperty("annoProp");
        OWLAnnotationProperty annoPropR = getOWLAnnotationProperty("annoPropR");
        OWLAnnotationProperty annoProp2 = getOWLAnnotationProperty("annoProp2");


        Set<OWLAxiom> axioms1 = new HashSet<OWLAxiom>();
        axioms1.add(getFactory().getOWLDeclarationAxiom(annoProp));
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, indA.getIRI(), indB.getIRI()));
        axioms1.add(getFactory().getOWLSubAnnotationPropertyOfAxiom(annoProp, annoProp2));
        axioms1.add(getFactory().getOWLAnnotationPropertyRangeAxiom(annoProp, indA.getIRI()));
        axioms1.add(getFactory().getOWLAnnotationPropertyDomainAxiom(annoProp, indA.getIRI()));
        getManager().addAxioms(ont, axioms1);

        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLDeclarationAxiom(annoPropR));
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoPropR, indA.getIRI(), indB.getIRI()));
        axioms2.add(getFactory().getOWLSubAnnotationPropertyOfAxiom(annoPropR, annoProp2));
        axioms2.add(getFactory().getOWLAnnotationPropertyRangeAxiom(annoPropR, indA.getIRI()));
        axioms2.add(getFactory().getOWLAnnotationPropertyDomainAxiom(annoPropR, indA.getIRI()));


        OWLEntityRenamer entityRenamer = new OWLEntityRenamer(getManager(), Collections.singleton(ont));
        List<OWLOntologyChange> changes = entityRenamer.changeIRI(annoProp, annoPropR.getIRI());
        getManager().applyChanges(changes);

        assertEquals(ont.getAxioms(), axioms2);

        List<OWLOntologyChange> changes2 = entityRenamer.changeIRI(annoPropR.getIRI(), annoProp.getIRI());
        getManager().applyChanges(changes2);

        assertEquals(ont.getAxioms(), axioms1);
    }


}
