package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.List;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, clsAIRI1.getIRI(), getFactory().getOWLTypedLiteral("X")));
        getManager().addAxioms(ont, axioms1);

        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLSubClassOfAxiom(clsAIRI2, clsB));
        axioms2.add(getFactory().getOWLEquivalentClassesAxiom(clsAIRI2, clsC));
        axioms2.add(getFactory().getOWLDisjointClassesAxiom(clsAIRI2, clsC));
        axioms2.add(getFactory().getOWLObjectPropertyDomainAxiom(propA, clsAIRI2));
        axioms2.add(getFactory().getOWLObjectPropertyRangeAxiom(propA, clsAIRI2));
        axioms2.add(getFactory().getOWLDataPropertyDomainAxiom(propB, clsAIRI2));
        axioms2.add(getFactory().getOWLClassAssertionAxiom(clsAIRI2, indA));
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, clsAIRI2.getIRI(), getFactory().getOWLTypedLiteral("X")));

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
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA.getIRI(), getFactory().getOWLTypedLiteral("X")));
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
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA2.getIRI(), getFactory().getOWLTypedLiteral("X")));


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
        axioms1.add(getFactory().getOWLDataPropertyAssertionAxiom(propA, indA, getFactory().getOWLTypedLiteral(33)));
        axioms1.add(getFactory().getOWLNegativeDataPropertyAssertionAxiom(propA, indA, getFactory().getOWLTypedLiteral(44)));
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA.getIRI(), getFactory().getOWLTypedLiteral("X")));
        getManager().addAxioms(ont, axioms1);

        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLSubDataPropertyOfAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLEquivalentDataPropertiesAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLDisjointDataPropertiesAxiom(propA2, propB));
        axioms2.add(getFactory().getOWLDataPropertyDomainAxiom(propA2, clsA));
        axioms2.add(getFactory().getOWLDataPropertyRangeAxiom(propA2, getFactory().getTopDatatype()));
        axioms2.add(getFactory().getOWLFunctionalDataPropertyAxiom(propA2));
        axioms2.add(getFactory().getOWLDataPropertyAssertionAxiom(propA2, indA, getFactory().getOWLTypedLiteral(33)));
        axioms2.add(getFactory().getOWLNegativeDataPropertyAssertionAxiom(propA2, indA, getFactory().getOWLTypedLiteral(44)));
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA2.getIRI(), getFactory().getOWLTypedLiteral("X")));


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
        axioms1.add(getFactory().getOWLDataPropertyAssertionAxiom(propA, indA, getFactory().getOWLTypedLiteral(33)));
        axioms1.add(getFactory().getOWLNegativeDataPropertyAssertionAxiom(propA, indA, getFactory().getOWLTypedLiteral(44)));
        axioms1.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA.getIRI(), getFactory().getOWLTypedLiteral("X")));
        axioms1.add(getFactory().getOWLObjectPropertyAssertionAxiom(propB, indA, indB));
        axioms1.add(getFactory().getOWLNegativeObjectPropertyAssertionAxiom(propB, indA, indB));

        getManager().addAxioms(ont, axioms1);


        Set<OWLAxiom> axioms2 = new HashSet<OWLAxiom>();
        axioms2.add(getFactory().getOWLClassAssertionAxiom(clsA, indB));
        axioms2.add(getFactory().getOWLDataPropertyAssertionAxiom(propA, indB, getFactory().getOWLTypedLiteral(33)));
        axioms2.add(getFactory().getOWLNegativeDataPropertyAssertionAxiom(propA, indB, getFactory().getOWLTypedLiteral(44)));
        axioms2.add(getFactory().getOWLAnnotationAssertionAxiom(annoProp, propA.getIRI(), getFactory().getOWLTypedLiteral("X")));
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
}
