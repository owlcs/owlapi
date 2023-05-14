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
package org.semanticweb.owlapi.apitest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.utility.DelegatingObjectVisitorEx;

class DelegatingObjectVisitorExTestCase extends TestBase {

    @Test
    void testAssertion() {
        OWLObjectVisitorEx<Object> test = mock(OWLObjectVisitorEx.class);
        DelegatingObjectVisitorEx<Object> testsubject = new DelegatingObjectVisitorEx<>(test);
        dataproperties(test, testsubject);
        datatype(test, testsubject);
        objectproperties(test, testsubject);
        swrl(test, testsubject);
        individuals(test, testsubject);
        annotations(test, testsubject);
        classes(test, testsubject);
        objectRestrictions(test, testsubject);
        misc(test, testsubject);
    }

    protected void misc(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(OWLHasKeyAxiom.class));
        verify(test).visit(any(OWLHasKeyAxiom.class));
        testsubject.visit(mock(OWLFacetRestriction.class));
        verify(test).visit(any(OWLFacetRestriction.class));
        testsubject.visit(mock(OWLLiteral.class));
        verify(test).visit(any(OWLLiteral.class));
        testsubject.visit(mock(IRI.class));
        verify(test).visit(any(IRI.class));
        testsubject.visit(mock(OWLOntology.class));
        verify(test).visit(any(OWLOntology.class));
        testsubject.visit(mock(OWLDeclarationAxiom.class));
        verify(test).visit(any(OWLDeclarationAxiom.class));
        testsubject.visit(mock(OWLClassAssertionAxiom.class));
        verify(test).visit(any(OWLClassAssertionAxiom.class));
    }

    protected void classes(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(OWLClass.class));
        verify(test).visit(any(OWLClass.class));
        testsubject.visit(mock(OWLEquivalentClassesAxiom.class));
        verify(test).visit(any(OWLEquivalentClassesAxiom.class));
        testsubject.visit(mock(OWLDisjointUnionAxiom.class));
        verify(test).visit(any(OWLDisjointUnionAxiom.class));
        testsubject.visit(mock(OWLSubPropertyChainOfAxiom.class));
        verify(test).visit(any(OWLSubPropertyChainOfAxiom.class));
        testsubject.visit(mock(OWLDisjointClassesAxiom.class));
        verify(test).visit(any(OWLDisjointClassesAxiom.class));
        testsubject.visit(mock(OWLSubClassOfAxiom.class));
        verify(test).visit(any(OWLSubClassOfAxiom.class));
    }

    protected void objectRestrictions(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(OWLObjectOneOf.class));
        verify(test).visit(any(OWLObjectOneOf.class));
        testsubject.visit(mock(OWLObjectHasSelf.class));
        verify(test).visit(any(OWLObjectHasSelf.class));
        testsubject.visit(mock(OWLObjectMaxCardinality.class));
        verify(test).visit(any(OWLObjectMaxCardinality.class));
        testsubject.visit(mock(OWLObjectSomeValuesFrom.class));
        verify(test).visit(any(OWLObjectSomeValuesFrom.class));
        testsubject.visit(mock(OWLObjectComplementOf.class));
        verify(test).visit(any(OWLObjectComplementOf.class));
        testsubject.visit(mock(OWLObjectUnionOf.class));
        verify(test).visit(any(OWLObjectUnionOf.class));
        testsubject.visit(mock(OWLObjectIntersectionOf.class));
        verify(test).visit(any(OWLObjectIntersectionOf.class));
        testsubject.visit(mock(OWLObjectExactCardinality.class));
        verify(test).visit(any(OWLObjectExactCardinality.class));
        testsubject.visit(mock(OWLObjectMinCardinality.class));
        verify(test).visit(any(OWLObjectMinCardinality.class));
        testsubject.visit(mock(OWLObjectHasValue.class));
        verify(test).visit(any(OWLObjectHasValue.class));
        testsubject.visit(mock(OWLObjectAllValuesFrom.class));
        verify(test).visit(any(OWLObjectAllValuesFrom.class));
        testsubject.visit(mock(OWLObjectInverseOf.class));
        verify(test).visit(any(OWLObjectInverseOf.class));
    }

    protected void annotations(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(OWLAnnotationPropertyRangeAxiom.class));
        verify(test).visit(any(OWLAnnotationPropertyRangeAxiom.class));
        testsubject.visit(mock(OWLAnnotationPropertyDomainAxiom.class));
        verify(test).visit(any(OWLAnnotationPropertyDomainAxiom.class));
        testsubject.visit(mock(OWLSubAnnotationPropertyOfAxiom.class));
        verify(test).visit(any(OWLSubAnnotationPropertyOfAxiom.class));
        testsubject.visit(mock(OWLAnnotationAssertionAxiom.class));
        verify(test).visit(any(OWLAnnotationAssertionAxiom.class));
        testsubject.visit(mock(OWLAnnotationProperty.class));
        verify(test).visit(any(OWLAnnotationProperty.class));
        testsubject.visit(mock(OWLAnnotation.class));
        verify(test).visit(any(OWLAnnotation.class));
    }

    protected void objectproperties(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(OWLSymmetricObjectPropertyAxiom.class));
        verify(test).visit(any(OWLSymmetricObjectPropertyAxiom.class));
        testsubject.visit(mock(OWLInverseObjectPropertiesAxiom.class));
        verify(test).visit(any(OWLInverseObjectPropertiesAxiom.class));
        testsubject.visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        verify(test).visit(any(OWLTransitiveObjectPropertyAxiom.class));
        testsubject.visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        verify(test).visit(any(OWLIrreflexiveObjectPropertyAxiom.class));
        testsubject.visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        verify(test).visit(any(OWLInverseFunctionalObjectPropertyAxiom.class));
        testsubject.visit(mock(OWLObjectPropertyDomainAxiom.class));
        verify(test).visit(any(OWLObjectPropertyDomainAxiom.class));
        testsubject.visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        verify(test).visit(any(OWLEquivalentObjectPropertiesAxiom.class));
        testsubject.visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        verify(test).visit(any(OWLNegativeObjectPropertyAssertionAxiom.class));
        testsubject.visit(mock(OWLAsymmetricObjectPropertyAxiom.class));
        verify(test).visit(any(OWLAsymmetricObjectPropertyAxiom.class));
        testsubject.visit(mock(OWLReflexiveObjectPropertyAxiom.class));
        verify(test).visit(any(OWLReflexiveObjectPropertyAxiom.class));
        testsubject.visit(mock(OWLObjectPropertyRangeAxiom.class));
        verify(test).visit(any(OWLObjectPropertyRangeAxiom.class));
        testsubject.visit(mock(OWLObjectPropertyAssertionAxiom.class));
        verify(test).visit(any(OWLObjectPropertyAssertionAxiom.class));
        testsubject.visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        verify(test).visit(any(OWLFunctionalObjectPropertyAxiom.class));
        testsubject.visit(mock(OWLSubObjectPropertyOfAxiom.class));
        verify(test).visit(any(OWLSubObjectPropertyOfAxiom.class));
        testsubject.visit(mock(OWLDisjointObjectPropertiesAxiom.class));
        verify(test).visit(any(OWLDisjointObjectPropertiesAxiom.class));
        testsubject.visit(mock(OWLObjectProperty.class));
        verify(test).visit(any(OWLObjectProperty.class));
    }

    protected void individuals(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(OWLSameIndividualAxiom.class));
        verify(test).visit(any(OWLSameIndividualAxiom.class));
        testsubject.visit(mock(OWLDifferentIndividualsAxiom.class));
        verify(test).visit(any(OWLDifferentIndividualsAxiom.class));
        testsubject.visit(mock(OWLNamedIndividual.class));
        verify(test).visit(any(OWLNamedIndividual.class));
        testsubject.visit(mock(OWLAnonymousIndividual.class));
        verify(test).visit(any(OWLAnonymousIndividual.class));
    }

    protected void dataproperties(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        verify(test).visit(any(OWLEquivalentDataPropertiesAxiom.class));
        testsubject.visit(mock(OWLDataPropertyAssertionAxiom.class));
        verify(test).visit(any(OWLDataPropertyAssertionAxiom.class));
        testsubject.visit(mock(OWLDataPropertyRangeAxiom.class));
        verify(test).visit(any(OWLDataPropertyRangeAxiom.class));
        testsubject.visit(mock(OWLFunctionalDataPropertyAxiom.class));
        verify(test).visit(any(OWLFunctionalDataPropertyAxiom.class));
        testsubject.visit(mock(OWLSubDataPropertyOfAxiom.class));
        verify(test).visit(any(OWLSubDataPropertyOfAxiom.class));
        testsubject.visit(mock(OWLDataPropertyDomainAxiom.class));
        verify(test).visit(any(OWLDataPropertyDomainAxiom.class));
        testsubject.visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        verify(test).visit(any(OWLNegativeDataPropertyAssertionAxiom.class));
        testsubject.visit(mock(OWLDisjointDataPropertiesAxiom.class));
        verify(test).visit(any(OWLDisjointDataPropertiesAxiom.class));
        testsubject.visit(mock(OWLDataAllValuesFrom.class));
        verify(test).visit(any(OWLDataAllValuesFrom.class));
        testsubject.visit(mock(OWLDataSomeValuesFrom.class));
        verify(test).visit(any(OWLDataSomeValuesFrom.class));
        testsubject.visit(mock(OWLDataMaxCardinality.class));
        verify(test).visit(any(OWLDataMaxCardinality.class));
        testsubject.visit(mock(OWLDataExactCardinality.class));
        verify(test).visit(any(OWLDataExactCardinality.class));
        testsubject.visit(mock(OWLDataMinCardinality.class));
        verify(test).visit(any(OWLDataMinCardinality.class));
        testsubject.visit(mock(OWLDataHasValue.class));
        verify(test).visit(any(OWLDataHasValue.class));
        testsubject.visit(mock(OWLDataUnionOf.class));
        verify(test).visit(any(OWLDataUnionOf.class));
        testsubject.visit(mock(OWLDataIntersectionOf.class));
        verify(test).visit(any(OWLDataIntersectionOf.class));
        testsubject.visit(mock(OWLDataOneOf.class));
        verify(test).visit(any(OWLDataOneOf.class));
        testsubject.visit(mock(OWLDataComplementOf.class));
        verify(test).visit(any(OWLDataComplementOf.class));
        testsubject.visit(mock(OWLDataProperty.class));
        verify(test).visit(any(OWLDataProperty.class));
    }

    protected void datatype(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(OWLDatatypeDefinitionAxiom.class));
        verify(test).visit(any(OWLDatatypeDefinitionAxiom.class));
        testsubject.visit(mock(OWLDatatypeRestriction.class));
        verify(test).visit(any(OWLDatatypeRestriction.class));
        testsubject.visit(mock(OWLDatatype.class));
        verify(test).visit(any(OWLDatatype.class));
    }

    protected void swrl(OWLObjectVisitorEx<Object> test,
        DelegatingObjectVisitorEx<Object> testsubject) {
        testsubject.visit(mock(SWRLRule.class));
        verify(test).visit(any(SWRLRule.class));
        testsubject.visit(mock(SWRLVariable.class));
        verify(test).visit(any(SWRLVariable.class));
        testsubject.visit(mock(SWRLIndividualArgument.class));
        verify(test).visit(any(SWRLIndividualArgument.class));
        testsubject.visit(mock(SWRLLiteralArgument.class));
        verify(test).visit(any(SWRLLiteralArgument.class));
        testsubject.visit(mock(SWRLSameIndividualAtom.class));
        verify(test).visit(any(SWRLSameIndividualAtom.class));
        testsubject.visit(mock(SWRLDifferentIndividualsAtom.class));
        verify(test).visit(any(SWRLDifferentIndividualsAtom.class));
        testsubject.visit(mock(SWRLClassAtom.class));
        verify(test).visit(any(SWRLClassAtom.class));
        testsubject.visit(mock(SWRLDataRangeAtom.class));
        verify(test).visit(any(SWRLDataRangeAtom.class));
        testsubject.visit(mock(SWRLObjectPropertyAtom.class));
        verify(test).visit(any(SWRLObjectPropertyAtom.class));
        testsubject.visit(mock(SWRLDataPropertyAtom.class));
        verify(test).visit(any(SWRLDataPropertyAtom.class));
        testsubject.visit(mock(SWRLBuiltInAtom.class));
        verify(test).visit(any(SWRLBuiltInAtom.class));
    }
}
