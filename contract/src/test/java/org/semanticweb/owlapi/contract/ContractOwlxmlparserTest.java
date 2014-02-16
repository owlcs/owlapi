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
package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.coode.owlapi.owlxmlparser.*;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.UnloadableImportException;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlxmlparserTest {
    @Test
    public void shouldTestAbbreviatedIRIElementHandler() throws OWLException {
        AbbreviatedIRIElementHandler testSubject0 = new AbbreviatedIRIElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        boolean result0 = testSubject0.isTextContentPossible();
        IRI result1 = testSubject0.getOWLObject();
        Object result2 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractClassExpressionElementHandler()
            throws OWLException {
        AbstractClassExpressionElementHandler testSubject0 = new AbstractClassExpressionElementHandler(
                Utils.mockHandler()) {
            @Override
            protected void endClassExpressionElement() {}
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractClassExpressionFillerRestriction()
            throws OWLException {
        AbstractClassExpressionFillerRestriction testSubject0 = new AbstractClassExpressionFillerRestriction(
                Utils.mockHandler()) {
            @Override
            protected OWLClassExpression createRestriction() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractClassExpressionOperandAxiomElementHandler()
            throws OWLException {
        AbstractClassExpressionOperandAxiomElementHandler testSubject0 = new AbstractClassExpressionOperandAxiomElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.endElement();
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractDataCardinalityRestrictionElementHandler()
            throws OWLException {
        AbstractDataCardinalityRestrictionElementHandler testSubject0 = new AbstractDataCardinalityRestrictionElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLClassExpression createRestriction() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractDataRangeFillerRestrictionElementHandler()
            throws OWLException {
        AbstractDataRangeFillerRestrictionElementHandler testSubject0 = new AbstractDataRangeFillerRestrictionElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLClassExpression createRestriction() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractDataRestrictionElementHandler()
            throws OWLException {
        AbstractDataRestrictionElementHandler<OWLObject> testSubject0 = new AbstractDataRestrictionElementHandler<OWLObject>(
                Utils.mockHandler()) {
            @Override
            protected OWLClassExpression createRestriction() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractIRIElementHandler() throws OWLException {
        AbstractIRIElementHandler testSubject0 = new AbstractIRIElementHandler(
                Utils.mockHandler()) {
            @Override
            public void endElement() throws OWLParserException,
                    UnloadableImportException {}

            @Override
            public IRI getOWLObject() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        boolean result1 = testSubject0.isTextContentPossible();
        String result2 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        testSubject0.endElement();
        Object result6 = testSubject0.getOWLObject();
    }

    @Test
    public void shouldTestAbstractNaryBooleanClassExpressionElementHandler()
            throws OWLException {
        AbstractNaryBooleanClassExpressionElementHandler testSubject0 = new AbstractNaryBooleanClassExpressionElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLClassExpression createClassExpression(
                    Set<OWLClassExpression> operands) {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractObjectRestrictionElementHandler()
            throws OWLException {
        AbstractObjectRestrictionElementHandler<OWLObject> testSubject0 = new AbstractObjectRestrictionElementHandler<OWLObject>(
                Utils.mockHandler()) {
            @Override
            protected OWLClassExpression createRestriction() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOperandAxiomElementHandler()
            throws OWLException {
        AbstractOperandAxiomElementHandler<OWLAxiom> testSubject0 = new AbstractOperandAxiomElementHandler<OWLAxiom>(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.endElement();
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLAssertionAxiomElementHandler()
            throws OWLException {
        AbstractOWLAssertionAxiomElementHandler<OWLObjectPropertyExpression, OWLObject> testSubject0 = new AbstractOWLAssertionAxiomElementHandler<OWLObjectPropertyExpression, OWLObject>(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result7 = testSubject0.isTextContentPossible();
        String result8 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.endElement();
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLAxiomElementHandler() throws OWLException {
        AbstractOWLAxiomElementHandler testSubject0 = new AbstractOWLAxiomElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.endElement();
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLDataPropertyAssertionAxiomElementHandler()
            throws OWLException {
        AbstractOWLDataPropertyAssertionAxiomElementHandler testSubject0 = new AbstractOWLDataPropertyAssertionAxiomElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result7 = testSubject0.isTextContentPossible();
        String result8 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLDataPropertyOperandAxiomElementHandler()
            throws OWLException {
        AbstractOWLDataPropertyOperandAxiomElementHandler testSubject0 = new AbstractOWLDataPropertyOperandAxiomElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.endElement();
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLDataRangeHandler() throws OWLException {
        AbstractOWLDataRangeHandler testSubject0 = new AbstractOWLDataRangeHandler(
                Utils.mockHandler()) {
            @Override
            protected void endDataRangeElement() {}
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLElementHandler() throws OWLException {
        AbstractOWLElementHandler<Object> testSubject0 = new AbstractOWLElementHandler<Object>(
                Utils.mockHandler()) {
            @Override
            public void endElement() throws OWLParserException,
                    UnloadableImportException {}

            @Override
            public Object getOWLObject() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        boolean result1 = testSubject0.isTextContentPossible();
        String result2 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        testSubject0.endElement();
        Object result6 = testSubject0.getOWLObject();
    }

    @Test
    public void shouldTestAbstractOWLIndividualOperandAxiomElementHandler()
            throws OWLException {
        AbstractOWLIndividualOperandAxiomElementHandler testSubject0 = new AbstractOWLIndividualOperandAxiomElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.endElement();
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLObjectCardinalityElementHandler()
            throws OWLException {
        AbstractOWLObjectCardinalityElementHandler testSubject0 = new AbstractOWLObjectCardinalityElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLClassExpression createCardinalityRestriction() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void
            shouldTestAbstractOWLObjectPropertyAssertionAxiomElementHandler()
                    throws OWLException {
        AbstractOWLObjectPropertyAssertionAxiomElementHandler testSubject0 = new AbstractOWLObjectPropertyAssertionAxiomElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result7 = testSubject0.isTextContentPossible();
        String result8 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.endElement();
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public
            void
            shouldTestAbstractOWLObjectPropertyCharacteristicAxiomElementHandler()
                    throws OWLException {
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler testSubject0 = new AbstractOWLObjectPropertyCharacteristicAxiomElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createPropertyCharacteristicAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLObjectPropertyElementHandler()
            throws OWLException {
        AbstractOWLObjectPropertyElementHandler testSubject0 = new AbstractOWLObjectPropertyElementHandler(
                Utils.mockHandler()) {
            @Override
            protected void endObjectPropertyElement() {}
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLObjectPropertyExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractOWLObjectPropertyOperandAxiomElementHandler()
            throws OWLException {
        AbstractOWLObjectPropertyOperandAxiomElementHandler testSubject0 = new AbstractOWLObjectPropertyOperandAxiomElementHandler(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void
            shouldTestAbstractOWLPropertyCharacteristicAxiomElementHandler()
                    throws OWLException {
        AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLAxiom> testSubject0 = new AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLAxiom>(
                Utils.mockHandler()) {
            @Override
            protected OWLAxiom createPropertyCharacteristicAxiom() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestAbstractRestrictionElementHandler()
            throws OWLException {
        AbstractRestrictionElementHandler<OWLObjectPropertyExpression, OWLClassExpression> testSubject0 = new AbstractRestrictionElementHandler<OWLObjectPropertyExpression, OWLClassExpression>(
                Utils.mockHandler()) {
            @Override
            protected OWLClassExpression createRestriction() {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestIRIElementHandler() throws OWLException {
        IRIElementHandler testSubject0 = new IRIElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        boolean result0 = testSubject0.isTextContentPossible();
        IRI result1 = testSubject0.getOWLObject();
        Object result2 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestLegacyEntityAnnotationElementHandler()
            throws OWLException {
        LegacyEntityAnnotationElementHandler testSubject0 = new LegacyEntityAnnotationElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLAnnotationAssertionElementHandler()
            throws OWLException {
        OWLAnnotationAssertionElementHandler testSubject0 = new OWLAnnotationAssertionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLAnnotationElementHandler() throws OWLException {
        OWLAnnotationElementHandler testSubject0 = new OWLAnnotationElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        boolean result0 = testSubject0.isTextContentPossible();
        Object result1 = testSubject0.getOWLObject();
        OWLAnnotation result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.attribute("IRI", "");
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLAnnotationPropertyDomainElementHandler()
            throws OWLException {
        OWLAnnotationPropertyDomainElementHandler testSubject0 = new OWLAnnotationPropertyDomainElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLAnnotationPropertyElementHandler()
            throws OWLException {
        OWLAnnotationPropertyElementHandler testSubject0 = new OWLAnnotationPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLAnnotationProperty result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLAnnotationPropertyRangeElementHandler()
            throws OWLException {
        OWLAnnotationPropertyRangeElementHandler testSubject0 = new OWLAnnotationPropertyRangeElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLAnonymousIndividualElementHandler()
            throws OWLException {
        OWLAnonymousIndividualElementHandler testSubject0 = new OWLAnonymousIndividualElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLAnonymousIndividual result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLAsymmetricObjectPropertyAxiomElementHandler()
            throws OWLException {
        OWLAsymmetricObjectPropertyAxiomElementHandler testSubject0 = new OWLAsymmetricObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLAsymmetricObjectPropertyElementHandler()
            throws OWLException {
        OWLAsymmetricObjectPropertyElementHandler testSubject0 = new OWLAsymmetricObjectPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLClassAssertionAxiomElementHandler()
            throws OWLException {
        OWLClassAssertionAxiomElementHandler testSubject0 = new OWLClassAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLClassElementHandler() throws OWLException {
        OWLClassElementHandler testSubject0 = new OWLClassElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.endClassExpressionElement();
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataAllValuesFromElementHandler()
            throws OWLException {
        OWLDataAllValuesFromElementHandler testSubject0 = new OWLDataAllValuesFromElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataComplementOfElementHandler()
            throws OWLException {
        OWLDataComplementOfElementHandler testSubject0 = new OWLDataComplementOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataExactCardinalityElementHandler()
            throws OWLException {
        OWLDataExactCardinalityElementHandler testSubject0 = new OWLDataExactCardinalityElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataHasValueElementHandler() throws OWLException {
        OWLDataHasValueElementHandler testSubject0 = new OWLDataHasValueElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataIntersectionOfElementHandler()
            throws OWLException {
        OWLDataIntersectionOfElementHandler testSubject0 = new OWLDataIntersectionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataMaxCardinalityElementHandler()
            throws OWLException {
        OWLDataMaxCardinalityElementHandler testSubject0 = new OWLDataMaxCardinalityElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataMinCardinalityElementHandler()
            throws OWLException {
        OWLDataMinCardinalityElementHandler testSubject0 = new OWLDataMinCardinalityElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataOneOfElementHandler() throws OWLException {
        OWLDataOneOfElementHandler testSubject0 = new OWLDataOneOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataPropertyAssertionAxiomElementHandler()
            throws OWLException {
        OWLDataPropertyAssertionAxiomElementHandler testSubject0 = new OWLDataPropertyAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result7 = testSubject0.isTextContentPossible();
        String result8 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataPropertyDomainAxiomElementHandler()
            throws OWLException {
        OWLDataPropertyDomainAxiomElementHandler testSubject0 = new OWLDataPropertyDomainAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataPropertyElementHandler() throws OWLException {
        OWLDataPropertyElementHandler testSubject0 = new OWLDataPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLDataPropertyExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataPropertyRangeAxiomElementHandler()
            throws OWLException {
        OWLDataPropertyRangeAxiomElementHandler testSubject0 = new OWLDataPropertyRangeAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataRestrictionElementHandler()
            throws OWLException {
        OWLDataRestrictionElementHandler testSubject0 = new OWLDataRestrictionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataSomeValuesFromElementHandler()
            throws OWLException {
        OWLDataSomeValuesFromElementHandler testSubject0 = new OWLDataSomeValuesFromElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDatatypeDefinitionElementHandler()
            throws OWLException {
        OWLDatatypeDefinitionElementHandler testSubject0 = new OWLDatatypeDefinitionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDatatypeElementHandler() throws OWLException {
        OWLDatatypeElementHandler testSubject0 = new OWLDatatypeElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDatatypeFacetRestrictionElementHandler()
            throws OWLException {
        OWLDatatypeFacetRestrictionElementHandler testSubject0 = new OWLDatatypeFacetRestrictionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLFacetRestriction result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDatatypeRestrictionElementHandler()
            throws OWLException {
        OWLDatatypeRestrictionElementHandler testSubject0 = new OWLDatatypeRestrictionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDataUnionOfElementHandler() throws OWLException {
        OWLDataUnionOfElementHandler testSubject0 = new OWLDataUnionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDeclarationAxiomElementHandler()
            throws OWLException {
        OWLDeclarationAxiomElementHandler testSubject0 = new OWLDeclarationAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        Object result2 = testSubject0.getOWLObject();
        OWLAxiom result3 = testSubject0.getOWLObject();
        boolean result5 = testSubject0.isTextContentPossible();
        String result6 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDifferentIndividualsAxiomElementHandler()
            throws OWLException {
        OWLDifferentIndividualsAxiomElementHandler testSubject0 = new OWLDifferentIndividualsAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDisjointClassesAxiomElementHandler()
            throws OWLException {
        OWLDisjointClassesAxiomElementHandler testSubject0 = new OWLDisjointClassesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDisjointDataPropertiesAxiomElementHandler()
            throws OWLException {
        OWLDisjointDataPropertiesAxiomElementHandler testSubject0 = new OWLDisjointDataPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDisjointObjectPropertiesAxiomElementHandler()
            throws OWLException {
        OWLDisjointObjectPropertiesAxiomElementHandler testSubject0 = new OWLDisjointObjectPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLDisjointUnionElementHandler() throws OWLException {
        OWLDisjointUnionElementHandler testSubject0 = new OWLDisjointUnionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestInterfaceOWLElementHandler() throws OWLException {
        OWLElementHandler<?> testSubject0 = Utils.mockElementHandler();
        testSubject0.startElement("");
        testSubject0.endElement();
        boolean result0 = testSubject0.isTextContentPossible();
        Object result1 = testSubject0.getOWLObject();
        String result2 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLEquivalentClassesAxiomElementHandler()
            throws OWLException {
        OWLEquivalentClassesAxiomElementHandler testSubject0 = new OWLEquivalentClassesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLEquivalentDataPropertiesAxiomElementHandler()
            throws OWLException {
        OWLEquivalentDataPropertiesAxiomElementHandler testSubject0 = new OWLEquivalentDataPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLEquivalentObjectPropertiesAxiomElementHandler()
            throws OWLException {
        OWLEquivalentObjectPropertiesAxiomElementHandler testSubject0 = new OWLEquivalentObjectPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLFunctionalDataPropertyAxiomElementHandler()
            throws OWLException {
        OWLFunctionalDataPropertyAxiomElementHandler testSubject0 = new OWLFunctionalDataPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLFunctionalObjectPropertyAxiomElementHandler()
            throws OWLException {
        OWLFunctionalObjectPropertyAxiomElementHandler testSubject0 = new OWLFunctionalObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLHasKeyElementHandler() throws OWLException {
        OWLHasKeyElementHandler testSubject0 = new OWLHasKeyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLImportsHandler() throws OWLException {
        OWLImportsHandler testSubject0 = new OWLImportsHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        boolean result0 = testSubject0.isTextContentPossible();
        OWLOntology result1 = testSubject0.getOWLObject();
        Object result2 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLIndividualElementHandler() throws OWLException {
        OWLIndividualElementHandler testSubject0 = new OWLIndividualElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLNamedIndividual result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void
            shouldTestOWLInverseFunctionalObjectPropertyAxiomElementHandler()
                    throws OWLException {
        OWLInverseFunctionalObjectPropertyAxiomElementHandler testSubject0 = new OWLInverseFunctionalObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLInverseObjectPropertiesAxiomElementHandler()
            throws OWLException {
        OWLInverseObjectPropertiesAxiomElementHandler testSubject0 = new OWLInverseObjectPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLInverseObjectPropertyElementHandler()
            throws OWLException {
        OWLInverseObjectPropertyElementHandler testSubject0 = new OWLInverseObjectPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLObjectPropertyExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLIrreflexiveObjectPropertyAxiomElementHandler()
            throws OWLException {
        OWLIrreflexiveObjectPropertyAxiomElementHandler testSubject0 = new OWLIrreflexiveObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLLiteralElementHandler() throws OWLException {
        OWLLiteralElementHandler testSubject0 = new OWLLiteralElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        boolean result0 = testSubject0.isTextContentPossible();
        OWLLiteral result1 = testSubject0.getOWLObject();
        Object result2 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLNegativeDataPropertyAssertionAxiomElementHandler()
            throws OWLException {
        OWLNegativeDataPropertyAssertionAxiomElementHandler testSubject0 = new OWLNegativeDataPropertyAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result7 = testSubject0.isTextContentPossible();
        String result8 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void
            shouldTestOWLNegativeObjectPropertyAssertionAxiomElementHandler()
                    throws OWLException {
        OWLNegativeObjectPropertyAssertionAxiomElementHandler testSubject0 = new OWLNegativeObjectPropertyAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result7 = testSubject0.isTextContentPossible();
        String result8 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectAllValuesFromElementHandler()
            throws OWLException {
        OWLObjectAllValuesFromElementHandler testSubject0 = new OWLObjectAllValuesFromElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectComplementOfElementHandler()
            throws OWLException {
        OWLObjectComplementOfElementHandler testSubject0 = new OWLObjectComplementOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectExactCardinalityElementHandler()
            throws OWLException {
        OWLObjectExactCardinalityElementHandler testSubject0 = new OWLObjectExactCardinalityElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectExistsSelfElementHandler()
            throws OWLException {
        OWLObjectExistsSelfElementHandler testSubject0 = new OWLObjectExistsSelfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectHasValueElementHandler() throws OWLException {
        OWLObjectHasValueElementHandler testSubject0 = new OWLObjectHasValueElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectIntersectionOfElementHandler()
            throws OWLException {
        OWLObjectIntersectionOfElementHandler testSubject0 = new OWLObjectIntersectionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectMaxCardinalityElementHandler()
            throws OWLException {
        OWLObjectMaxCardinalityElementHandler testSubject0 = new OWLObjectMaxCardinalityElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectMinCardinalityElementHandler()
            throws OWLException {
        OWLObjectMinCardinalityElementHandler testSubject0 = new OWLObjectMinCardinalityElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectOneOfElementHandler() throws OWLException {
        OWLObjectOneOfElementHandler testSubject0 = new OWLObjectOneOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectPropertyAssertionAxiomElementHandler()
            throws OWLException {
        OWLObjectPropertyAssertionAxiomElementHandler testSubject0 = new OWLObjectPropertyAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result7 = testSubject0.isTextContentPossible();
        String result8 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectPropertyDomainElementHandler()
            throws OWLException {
        OWLObjectPropertyDomainElementHandler testSubject0 = new OWLObjectPropertyDomainElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectPropertyElementHandler() throws OWLException {
        OWLObjectPropertyElementHandler testSubject0 = new OWLObjectPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.endElement();
        OWLObjectPropertyExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectPropertyRangeAxiomElementHandler()
            throws OWLException {
        OWLObjectPropertyRangeAxiomElementHandler testSubject0 = new OWLObjectPropertyRangeAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectSomeValuesFromElementHandler()
            throws OWLException {
        OWLObjectSomeValuesFromElementHandler testSubject0 = new OWLObjectSomeValuesFromElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLObjectUnionOfElementHandler() throws OWLException {
        OWLObjectUnionOfElementHandler testSubject0 = new OWLObjectUnionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLOntologyHandler() throws OWLException {
        OWLOntologyHandler testSubject0 = new OWLOntologyHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result0 = testSubject0.getOWLObject();
        OWLOntology result1 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLReflexiveObjectPropertyAxiomElementHandler()
            throws OWLException {
        OWLReflexiveObjectPropertyAxiomElementHandler testSubject0 = new OWLReflexiveObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLSameIndividualsAxiomElementHandler()
            throws OWLException {
        OWLSameIndividualsAxiomElementHandler testSubject0 = new OWLSameIndividualsAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLSubAnnotationPropertyOfElementHandler()
            throws OWLException {
        OWLSubAnnotationPropertyOfElementHandler testSubject0 = new OWLSubAnnotationPropertyOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLSubClassAxiomElementHandler() throws OWLException {
        OWLSubClassAxiomElementHandler testSubject0 = new OWLSubClassAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLSubDataPropertyOfAxiomElementHandler()
            throws OWLException {
        OWLSubDataPropertyOfAxiomElementHandler testSubject0 = new OWLSubDataPropertyOfAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLSubObjectPropertyChainElementHandler()
            throws OWLException {
        OWLSubObjectPropertyChainElementHandler testSubject0 = new OWLSubObjectPropertyChainElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        List<OWLObjectPropertyExpression> result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLSubObjectPropertyOfAxiomElementHandler()
            throws OWLException {
        OWLSubObjectPropertyOfAxiomElementHandler testSubject0 = new OWLSubObjectPropertyOfAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLSymmetricObjectPropertyAxiomElementHandler()
            throws OWLException {
        OWLSymmetricObjectPropertyAxiomElementHandler testSubject0 = new OWLSymmetricObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLTransitiveObjectPropertyAxiomElementHandler()
            throws OWLException {
        OWLTransitiveObjectPropertyAxiomElementHandler testSubject0 = new OWLTransitiveObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestOWLUnionOfElementHandler() throws OWLException {
        OWLUnionOfElementHandler testSubject0 = new OWLUnionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    public void shouldTestOWLXMLParser() throws OWLException,
            OWLOntologyChangeException, IOException {
        OWLXMLParser testSubject0 = new OWLXMLParser();
        OWLOntologyFormat result1 = testSubject0.parse(
                new StringDocumentSource(""), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
    }

    @Test
    public void shouldTestOWLXMLParserAttributeNotFoundException()
            throws OWLException {
        OWLXMLParserAttributeNotFoundException testSubject0 = new OWLXMLParserAttributeNotFoundException(
                mock(OWLXMLParserHandler.class), "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLParserElementNotFoundException()
            throws OWLException {
        OWLXMLParserElementNotFoundException testSubject0 = new OWLXMLParserElementNotFoundException(
                mock(OWLXMLParserHandler.class), "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLParserException() throws OWLException {
        OWLXMLParserException testSubject0 = new OWLXMLParserException(
                mock(OWLXMLParserHandler.class), "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestSWRLAtomElementHandler() throws OWLException {
        SWRLAtomElementHandler testSubject0 = new SWRLAtomElementHandler(
                Utils.mockHandler()) {
            @Override
            public void endElement() throws OWLParserException,
                    UnloadableImportException {}
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        testSubject0.endElement();
    }

    @Test
    public void shouldTestSWRLAtomListElementHandler() throws OWLException {
        SWRLAtomListElementHandler testSubject0 = new SWRLAtomListElementHandler(
                Utils.mockHandler()) {};
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        List<SWRLAtom> result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestSWRLBuiltInAtomElementHandler() throws OWLException {
        SWRLBuiltInAtomElementHandler testSubject0 = new SWRLBuiltInAtomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.attribute("IRI", "");
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestSWRLClassAtomElementHandler() throws OWLException {
        SWRLClassAtomElementHandler testSubject0 = new SWRLClassAtomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestSWRLDataPropertyAtomElementHandler()
            throws OWLException {
        SWRLDataPropertyAtomElementHandler testSubject0 = new SWRLDataPropertyAtomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestSWRLDifferentIndividualsAtomElementHandler()
            throws OWLException {
        SWRLDifferentIndividualsAtomElementHandler testSubject0 = new SWRLDifferentIndividualsAtomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestSWRLObjectPropertyAtomElementHandler()
            throws OWLException {
        SWRLObjectPropertyAtomElementHandler testSubject0 = new SWRLObjectPropertyAtomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestSWRLRuleElementHandler() throws OWLException {
        SWRLRuleElementHandler testSubject0 = new SWRLRuleElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        boolean result4 = testSubject0.isTextContentPossible();
        String result5 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestSWRLSameIndividualAtomElementHandler()
            throws OWLException {
        SWRLSameIndividualAtomElementHandler testSubject0 = new SWRLSameIndividualAtomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestSWRLVariableElementHandler() throws OWLException {
        SWRLVariableElementHandler testSubject0 = new SWRLVariableElementHandler(
                Utils.mockHandler()) {};
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        SWRLVariable result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        testSubject0.startElement("");
        boolean result3 = testSubject0.isTextContentPossible();
        String result4 = testSubject0.getText();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        testSubject0.handleChild(Utils.mockAxiomHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
    }

    @Test
    public void shouldTestTranslatedOWLOntologyChangeException()
            throws OWLException {
        TranslatedOWLOntologyChangeException testSubject0 = new TranslatedOWLOntologyChangeException(
                mock(OWLOntologyChangeException.class));
        OWLOntologyChangeException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedOWLParserException() throws OWLException {
        TranslatedOWLParserException testSubject0 = new TranslatedOWLParserException(
                mock(OWLParserException.class));
        OWLParserException result0 = testSubject0.getParserException();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedUnloadableImportException()
            throws OWLException {
        TranslatedUnloadableImportException testSubject0 = new TranslatedUnloadableImportException(
                mock(UnloadableImportException.class));
        UnloadableImportException result0 = testSubject0
                .getUnloadableImportException();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }
}
