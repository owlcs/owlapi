package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;

import org.coode.owlapi.owlxmlparser.*;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParser;
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
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
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
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlxmlparserTest {

    @Test
    public void shouldTestAbbreviatedIRIElementHandler() throws Exception {
        AbbreviatedIRIElementHandler testSubject0 = new AbbreviatedIRIElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        boolean result0 = testSubject0.isTextContentPossible();
        IRI result1 = testSubject0.getOWLObject();
        Object result2 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractClassExpressionElementHandler()
            throws Exception {
        AbstractClassExpressionElementHandler testSubject0 = new AbstractClassExpressionElementHandler(
                Utils.mockHandler()) {

            @Override
            protected void endClassExpressionElement()
                    throws OWLXMLParserException {}
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractClassExpressionFillerRestriction()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractClassExpressionOperandAxiomElementHandler()
            throws Exception {
        AbstractClassExpressionOperandAxiomElementHandler testSubject0 = new AbstractClassExpressionOperandAxiomElementHandler(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractDataCardinalityRestrictionElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractDataRangeFillerRestrictionElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractDataRestrictionElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractElementHandlerFactory() throws Exception {
        AbstractElementHandlerFactory testSubject0 = new AbstractElementHandlerFactory(
                OWLXMLVocabulary.COMMENT) {

            @Override
            public OWLElementHandler<?> createHandler(
                    OWLXMLParserHandler handler) {
                return null;
            }
        };
        String result0 = testSubject0.getElementName();
        String result1 = testSubject0.toString();
        OWLElementHandler<?> result2 = testSubject0.createHandler(Utils
                .mockHandler());
    }

    @Test
    public void shouldTestAbstractIRIElementHandler() throws Exception {
        AbstractIRIElementHandler testSubject0 = new AbstractIRIElementHandler(
                Utils.mockHandler()) {

            @Override
            public void endElement() throws OWLParserException,
                    UnloadableImportException {}

            @Override
            public IRI getOWLObject() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        OWLOntologyLoaderConfiguration result0 = testSubject0
                .getConfiguration();
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
        IRI result3 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result4 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result5 = testSubject0.toString();
        testSubject0.endElement();
        Object result6 = testSubject0.getOWLObject();
    }

    @Test
    public void shouldTestAbstractNaryBooleanClassExpressionElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractObjectRestrictionElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOperandAxiomElementHandler() throws Exception {
        AbstractOperandAxiomElementHandler<OWLAxiom> testSubject0 = new AbstractOperandAxiomElementHandler<OWLAxiom>(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLAssertionAxiomElementHandler()
            throws Exception {
        AbstractOWLAssertionAxiomElementHandler<OWLObjectPropertyExpression, OWLObject> testSubject0 = new AbstractOWLAssertionAxiomElementHandler<OWLObjectPropertyExpression, OWLObject>(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        OWLObject result1 = testSubject0.getObject();
        testSubject0.setSubject(mock(OWLIndividual.class));
        OWLIndividual result2 = testSubject0.getSubject();
        testSubject0.setObject(mock(OWLObject.class));
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result6 = testSubject0
                .getConfiguration();
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
        IRI result9 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result10 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLAxiomElementHandler() throws Exception {
        AbstractOWLAxiomElementHandler testSubject0 = new AbstractOWLAxiomElementHandler(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLDataPropertyAssertionAxiomElementHandler()
            throws Exception {
        AbstractOWLDataPropertyAssertionAxiomElementHandler testSubject0 = new AbstractOWLDataPropertyAssertionAxiomElementHandler(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.setProperty(mock(OWLDataPropertyExpression.class));
        OWLDataPropertyExpression result0 = testSubject0.getProperty();
        OWLObject result1 = testSubject0.getObject();
        testSubject0.setSubject(mock(OWLIndividual.class));
        OWLIndividual result2 = testSubject0.getSubject();
        testSubject0.setObject(mock(OWLLiteral.class));
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result6 = testSubject0
                .getConfiguration();
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
        IRI result9 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result10 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLDataPropertyOperandAxiomElementHandler()
            throws Exception {
        AbstractOWLDataPropertyOperandAxiomElementHandler testSubject0 = new AbstractOWLDataPropertyOperandAxiomElementHandler(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLDataRangeHandler() throws Exception {
        AbstractOWLDataRangeHandler testSubject0 = new AbstractOWLDataRangeHandler(
                Utils.mockHandler()) {

            @Override
            protected void endDataRangeElement() throws OWLXMLParserException {}
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setDataRange(mock(OWLDataRange.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLElementHandler() throws Exception {
        AbstractOWLElementHandler<Object> testSubject0 = new AbstractOWLElementHandler<Object>(
                Utils.mockHandler()) {

            @Override
            public void endElement() throws OWLParserException,
                    UnloadableImportException {}

            @Override
            public Object getOWLObject() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        OWLOntologyLoaderConfiguration result0 = testSubject0
                .getConfiguration();
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
        IRI result3 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result4 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result5 = testSubject0.toString();
        testSubject0.endElement();
        Object result6 = testSubject0.getOWLObject();
    }

    @Test
    public void shouldTestAbstractOWLIndividualOperandAxiomElementHandler()
            throws Exception {
        AbstractOWLIndividualOperandAxiomElementHandler testSubject0 = new AbstractOWLIndividualOperandAxiomElementHandler(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLObjectCardinalityElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void
            shouldTestAbstractOWLObjectPropertyAssertionAxiomElementHandler()
                    throws Exception {
        AbstractOWLObjectPropertyAssertionAxiomElementHandler testSubject0 = new AbstractOWLObjectPropertyAssertionAxiomElementHandler(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        OWLObject result1 = testSubject0.getObject();
        testSubject0.setSubject(mock(OWLIndividual.class));
        OWLIndividual result2 = testSubject0.getSubject();
        testSubject0.setObject(mock(OWLIndividual.class));
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result6 = testSubject0
                .getConfiguration();
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
        IRI result9 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result10 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result11 = testSubject0.toString();
    }

    @Test
    public
            void
            shouldTestAbstractOWLObjectPropertyCharacteristicAxiomElementHandler()
                    throws Exception {
        AbstractOWLObjectPropertyCharacteristicAxiomElementHandler testSubject0 = new AbstractOWLObjectPropertyCharacteristicAxiomElementHandler(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createPropertyCharacteristicAxiom()
                    throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLObjectPropertyElementHandler()
            throws Exception {
        AbstractOWLObjectPropertyElementHandler testSubject0 = new AbstractOWLObjectPropertyElementHandler(
                Utils.mockHandler()) {

            @Override
            protected void endObjectPropertyElement()
                    throws OWLXMLParserException {}
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLObjectPropertyExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractOWLObjectPropertyOperandAxiomElementHandler()
            throws Exception {
        AbstractOWLObjectPropertyOperandAxiomElementHandler testSubject0 = new AbstractOWLObjectPropertyOperandAxiomElementHandler(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createAxiom() throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void
            shouldTestAbstractOWLPropertyCharacteristicAxiomElementHandler()
                    throws Exception {
        AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLAxiom> testSubject0 = new AbstractOWLPropertyCharacteristicAxiomElementHandler<OWLAxiom>(
                Utils.mockHandler()) {

            @Override
            protected OWLAxiom createPropertyCharacteristicAxiom()
                    throws OWLXMLParserException {
                return null;
            }
        };
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.setProperty(mock(OWLAxiom.class));
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractRestrictionElementHandler() throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestIRIElementHandler() throws Exception {
        IRIElementHandler testSubject0 = new IRIElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        boolean result0 = testSubject0.isTextContentPossible();
        IRI result1 = testSubject0.getOWLObject();
        Object result2 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestLegacyEntityAnnotationElementHandler()
            throws Exception {
        LegacyEntityAnnotationElementHandler testSubject0 = new LegacyEntityAnnotationElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAnnotationAssertionElementHandler()
            throws Exception {
        OWLAnnotationAssertionElementHandler testSubject0 = new OWLAnnotationAssertionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAnnotationElementHandler() throws Exception {
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
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAnnotationPropertyDomainElementHandler()
            throws Exception {
        OWLAnnotationPropertyDomainElementHandler testSubject0 = new OWLAnnotationPropertyDomainElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAnnotationPropertyElementHandler()
            throws Exception {
        OWLAnnotationPropertyElementHandler testSubject0 = new OWLAnnotationPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLAnnotationProperty result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAnnotationPropertyRangeElementHandler()
            throws Exception {
        OWLAnnotationPropertyRangeElementHandler testSubject0 = new OWLAnnotationPropertyRangeElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAbstractIRIHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAnonymousIndividualElementHandler()
            throws Exception {
        OWLAnonymousIndividualElementHandler testSubject0 = new OWLAnonymousIndividualElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLAnonymousIndividual result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAsymmetricObjectPropertyAxiomElementHandler()
            throws Exception {
        OWLAsymmetricObjectPropertyAxiomElementHandler testSubject0 = new OWLAsymmetricObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAsymmetricObjectPropertyElementHandler()
            throws Exception {
        OWLAsymmetricObjectPropertyElementHandler testSubject0 = new OWLAsymmetricObjectPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLClassAssertionAxiomElementHandler()
            throws Exception {
        OWLClassAssertionAxiomElementHandler testSubject0 = new OWLClassAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLClassElementHandler() throws Exception {
        OWLClassElementHandler testSubject0 = new OWLClassElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.endClassExpressionElement();
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataAllValuesFromElementHandler() throws Exception {
        OWLDataAllValuesFromElementHandler testSubject0 = new OWLDataAllValuesFromElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataComplementOfElementHandler() throws Exception {
        OWLDataComplementOfElementHandler testSubject0 = new OWLDataComplementOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setDataRange(mock(OWLDataRange.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataExactCardinalityElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataHasValueElementHandler() throws Exception {
        OWLDataHasValueElementHandler testSubject0 = new OWLDataHasValueElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataIntersectionOfElementHandler()
            throws Exception {
        OWLDataIntersectionOfElementHandler testSubject0 = new OWLDataIntersectionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setDataRange(mock(OWLDataRange.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataMaxCardinalityElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataMinCardinalityElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataOneOfElementHandler() throws Exception {
        OWLDataOneOfElementHandler testSubject0 = new OWLDataOneOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setDataRange(mock(OWLDataRange.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataPropertyAssertionAxiomElementHandler()
            throws Exception {
        OWLDataPropertyAssertionAxiomElementHandler testSubject0 = new OWLDataPropertyAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.setProperty(mock(OWLDataPropertyExpression.class));
        OWLDataPropertyExpression result0 = testSubject0.getProperty();
        OWLObject result1 = testSubject0.getObject();
        testSubject0.setSubject(mock(OWLIndividual.class));
        OWLIndividual result2 = testSubject0.getSubject();
        testSubject0.setObject(mock(OWLLiteral.class));
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result6 = testSubject0
                .getConfiguration();
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
        IRI result9 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result10 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataPropertyDomainAxiomElementHandler()
            throws Exception {
        OWLDataPropertyDomainAxiomElementHandler testSubject0 = new OWLDataPropertyDomainAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataPropertyElementHandler() throws Exception {
        OWLDataPropertyElementHandler testSubject0 = new OWLDataPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLDataPropertyExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataPropertyRangeAxiomElementHandler()
            throws Exception {
        OWLDataPropertyRangeAxiomElementHandler testSubject0 = new OWLDataPropertyRangeAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataRestrictionElementHandler() throws Exception {
        OWLDataRestrictionElementHandler testSubject0 = new OWLDataRestrictionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setDataRange(mock(OWLDataRange.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataSomeValuesFromElementHandler()
            throws Exception {
        OWLDataSomeValuesFromElementHandler testSubject0 = new OWLDataSomeValuesFromElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDatatypeDefinitionElementHandler()
            throws Exception {
        OWLDatatypeDefinitionElementHandler testSubject0 = new OWLDatatypeDefinitionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDatatypeElementHandler() throws Exception {
        OWLDatatypeElementHandler testSubject0 = new OWLDatatypeElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setDataRange(mock(OWLDataRange.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDatatypeFacetRestrictionElementHandler()
            throws Exception {
        OWLDatatypeFacetRestrictionElementHandler testSubject0 = new OWLDatatypeFacetRestrictionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLFacetRestriction result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.attribute("IRI", "");
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDatatypeRestrictionElementHandler()
            throws Exception {
        OWLDatatypeRestrictionElementHandler testSubject0 = new OWLDatatypeRestrictionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.handleChild(Utils.mockDatatypeFacetRestrictionHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setDataRange(mock(OWLDataRange.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataUnionOfElementHandler() throws Exception {
        OWLDataUnionOfElementHandler testSubject0 = new OWLDataUnionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataRangeHandler());
        testSubject0.endElement();
        OWLDataRange result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setDataRange(mock(OWLDataRange.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDeclarationAxiomElementHandler() throws Exception {
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
        Set<OWLAnnotation> result0 = testSubject0.getEntityAnnotations();
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result2 = testSubject0.getOWLObject();
        OWLAxiom result3 = testSubject0.getOWLObject();
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result4 = testSubject0
                .getConfiguration();
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
        IRI result7 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result8 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result9 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDifferentIndividualsAxiomElementHandler()
            throws Exception {
        OWLDifferentIndividualsAxiomElementHandler testSubject0 = new OWLDifferentIndividualsAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDisjointClassesAxiomElementHandler()
            throws Exception {
        OWLDisjointClassesAxiomElementHandler testSubject0 = new OWLDisjointClassesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDisjointDataPropertiesAxiomElementHandler()
            throws Exception {
        OWLDisjointDataPropertiesAxiomElementHandler testSubject0 = new OWLDisjointDataPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDisjointObjectPropertiesAxiomElementHandler()
            throws Exception {
        OWLDisjointObjectPropertiesAxiomElementHandler testSubject0 = new OWLDisjointObjectPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDisjointUnionElementHandler() throws Exception {
        OWLDisjointUnionElementHandler testSubject0 = new OWLDisjointUnionElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLElementHandler() throws Exception {
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
    public void shouldTestInterfaceOWLElementHandlerFactory() throws Exception {
        OWLElementHandlerFactory testSubject0 = mock(OWLElementHandlerFactory.class);
        String result0 = testSubject0.getElementName();
        OWLElementHandler<?> result1 = testSubject0.createHandler(Utils
                .mockHandler());
    }

    @Test
    public void shouldTestOWLEquivalentClassesAxiomElementHandler()
            throws Exception {
        OWLEquivalentClassesAxiomElementHandler testSubject0 = new OWLEquivalentClassesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEquivalentDataPropertiesAxiomElementHandler()
            throws Exception {
        OWLEquivalentDataPropertiesAxiomElementHandler testSubject0 = new OWLEquivalentDataPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEquivalentObjectPropertiesAxiomElementHandler()
            throws Exception {
        OWLEquivalentObjectPropertiesAxiomElementHandler testSubject0 = new OWLEquivalentObjectPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLFunctionalDataPropertyAxiomElementHandler()
            throws Exception {
        OWLFunctionalDataPropertyAxiomElementHandler testSubject0 = new OWLFunctionalDataPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.setProperty(mock(OWLDataPropertyExpression.class));
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLFunctionalObjectPropertyAxiomElementHandler()
            throws Exception {
        OWLFunctionalObjectPropertyAxiomElementHandler testSubject0 = new OWLFunctionalObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLHasKeyElementHandler() throws Exception {
        OWLHasKeyElementHandler testSubject0 = new OWLHasKeyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLImportsHandler() throws Exception {
        OWLImportsHandler testSubject0 = new OWLImportsHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        boolean result0 = testSubject0.isTextContentPossible();
        OWLOntology result1 = testSubject0.getOWLObject();
        Object result2 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLIndividualElementHandler() throws Exception {
        OWLIndividualElementHandler testSubject0 = new OWLIndividualElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        OWLNamedIndividual result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void
            shouldTestOWLInverseFunctionalObjectPropertyAxiomElementHandler()
                    throws Exception {
        OWLInverseFunctionalObjectPropertyAxiomElementHandler testSubject0 = new OWLInverseFunctionalObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLInverseObjectPropertiesAxiomElementHandler()
            throws Exception {
        OWLInverseObjectPropertiesAxiomElementHandler testSubject0 = new OWLInverseObjectPropertiesAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLInverseObjectPropertyElementHandler()
            throws Exception {
        OWLInverseObjectPropertyElementHandler testSubject0 = new OWLInverseObjectPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLObjectPropertyExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLIrreflexiveObjectPropertyAxiomElementHandler()
            throws Exception {
        OWLIrreflexiveObjectPropertyAxiomElementHandler testSubject0 = new OWLIrreflexiveObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLLiteralElementHandler() throws Exception {
        OWLLiteralElementHandler testSubject0 = new OWLLiteralElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.endElement();
        boolean result0 = testSubject0.isTextContentPossible();
        OWLLiteral result1 = testSubject0.getOWLObject();
        Object result2 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLNegativeDataPropertyAssertionAxiomElementHandler()
            throws Exception {
        OWLNegativeDataPropertyAssertionAxiomElementHandler testSubject0 = new OWLNegativeDataPropertyAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        testSubject0.handleChild(Utils.mockLiteralHandler());
        testSubject0.setProperty(mock(OWLDataPropertyExpression.class));
        OWLDataPropertyExpression result0 = testSubject0.getProperty();
        OWLObject result1 = testSubject0.getObject();
        testSubject0.setSubject(mock(OWLIndividual.class));
        OWLIndividual result2 = testSubject0.getSubject();
        testSubject0.setObject(mock(OWLLiteral.class));
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result6 = testSubject0
                .getConfiguration();
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
        IRI result9 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result10 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result11 = testSubject0.toString();
    }

    @Test
    public void
            shouldTestOWLNegativeObjectPropertyAssertionAxiomElementHandler()
                    throws Exception {
        OWLNegativeObjectPropertyAssertionAxiomElementHandler testSubject0 = new OWLNegativeObjectPropertyAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        OWLObject result1 = testSubject0.getObject();
        testSubject0.setSubject(mock(OWLIndividual.class));
        OWLIndividual result2 = testSubject0.getSubject();
        testSubject0.setObject(mock(OWLIndividual.class));
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result6 = testSubject0
                .getConfiguration();
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
        IRI result9 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result10 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectAllValuesFromElementHandler()
            throws Exception {
        OWLObjectAllValuesFromElementHandler testSubject0 = new OWLObjectAllValuesFromElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectComplementOfElementHandler()
            throws Exception {
        OWLObjectComplementOfElementHandler testSubject0 = new OWLObjectComplementOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectExactCardinalityElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectExistsSelfElementHandler() throws Exception {
        OWLObjectExistsSelfElementHandler testSubject0 = new OWLObjectExistsSelfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectHasValueElementHandler() throws Exception {
        OWLObjectHasValueElementHandler testSubject0 = new OWLObjectHasValueElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectIntersectionOfElementHandler()
            throws Exception {
        OWLObjectIntersectionOfElementHandler testSubject0 = new OWLObjectIntersectionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectMaxCardinalityElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectMinCardinalityElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectOneOfElementHandler() throws Exception {
        OWLObjectOneOfElementHandler testSubject0 = new OWLObjectOneOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectPropertyAssertionAxiomElementHandler()
            throws Exception {
        OWLObjectPropertyAssertionAxiomElementHandler testSubject0 = new OWLObjectPropertyAssertionAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        OWLObject result1 = testSubject0.getObject();
        testSubject0.setSubject(mock(OWLIndividual.class));
        OWLIndividual result2 = testSubject0.getSubject();
        testSubject0.setObject(mock(OWLIndividual.class));
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result4 = testSubject0.getOWLObject();
        OWLAxiom result5 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result6 = testSubject0
                .getConfiguration();
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
        IRI result9 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result10 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectPropertyDomainElementHandler()
            throws Exception {
        OWLObjectPropertyDomainElementHandler testSubject0 = new OWLObjectPropertyDomainElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectPropertyElementHandler() throws Exception {
        OWLObjectPropertyElementHandler testSubject0 = new OWLObjectPropertyElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.endElement();
        OWLObjectPropertyExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectPropertyRangeAxiomElementHandler()
            throws Exception {
        OWLObjectPropertyRangeAxiomElementHandler testSubject0 = new OWLObjectPropertyRangeAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectSomeValuesFromElementHandler()
            throws Exception {
        OWLObjectSomeValuesFromElementHandler testSubject0 = new OWLObjectSomeValuesFromElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectUnionOfElementHandler() throws Exception {
        OWLObjectUnionOfElementHandler testSubject0 = new OWLObjectUnionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockClassHandler());
        testSubject0.endElement();
        OWLClassExpression result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyHandler() throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLReflexiveObjectPropertyAxiomElementHandler()
            throws Exception {
        OWLReflexiveObjectPropertyAxiomElementHandler testSubject0 = new OWLReflexiveObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLSameIndividualsAxiomElementHandler()
            throws Exception {
        OWLSameIndividualsAxiomElementHandler testSubject0 = new OWLSameIndividualsAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        testSubject0.handleChild(Utils.mockAnonymousIndividualHandler());
        testSubject0.startElement("");
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLSubAnnotationPropertyOfElementHandler()
            throws Exception {
        OWLSubAnnotationPropertyOfElementHandler testSubject0 = new OWLSubAnnotationPropertyOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockAnnotationPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLSubClassAxiomElementHandler() throws Exception {
        OWLSubClassAxiomElementHandler testSubject0 = new OWLSubClassAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.startElement("");
        testSubject0.handleChild(Utils.mockClassHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLSubDataPropertyOfAxiomElementHandler()
            throws Exception {
        OWLSubDataPropertyOfAxiomElementHandler testSubject0 = new OWLSubDataPropertyOfAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockDataPropertyHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLSubObjectPropertyChainElementHandler()
            throws Exception {
        OWLSubObjectPropertyChainElementHandler testSubject0 = new OWLSubObjectPropertyChainElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        List<OWLObjectPropertyExpression> result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLSubObjectPropertyOfAxiomElementHandler()
            throws Exception {
        OWLSubObjectPropertyOfAxiomElementHandler testSubject0 = new OWLSubObjectPropertyOfAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.handleChild(Utils.mockSubObjectPropertyChainHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLSymmetricObjectPropertyAxiomElementHandler()
            throws Exception {
        OWLSymmetricObjectPropertyAxiomElementHandler testSubject0 = new OWLSymmetricObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLTransitiveObjectPropertyAxiomElementHandler()
            throws Exception {
        OWLTransitiveObjectPropertyAxiomElementHandler testSubject0 = new OWLTransitiveObjectPropertyAxiomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockObjectPropertyHandler());
        testSubject0.setProperty(Utils.mockObjectProperty());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLUnionOfElementHandler() throws Exception {
        OWLUnionOfElementHandler testSubject0 = new OWLUnionOfElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockClassHandler());
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    public void shouldTestOWLXMLParser() throws Exception {
        OWLXMLParser testSubject0 = new OWLXMLParser();
        OWLOntologyFormat result1 = testSubject0.parse(
                new StringDocumentSource(""), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLXMLParserAttributeNotFoundException()
            throws Exception {
        OWLXMLParserAttributeNotFoundException testSubject0 = new OWLXMLParserAttributeNotFoundException(
                0, 0, "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLParserElementNotFoundException()
            throws Exception {
        OWLXMLParserElementNotFoundException testSubject0 = new OWLXMLParserElementNotFoundException(
                0, 0, "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLParserException() throws Exception {
        OWLXMLParserException testSubject0 = new OWLXMLParserException("", 0, 0);
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLParserFactory() throws Exception {
        OWLXMLParserFactory testSubject0 = new OWLXMLParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLAtomElementHandler() throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
        testSubject0.endElement();
    }

    @Test
    public void shouldTestSWRLAtomListElementHandler() throws Exception {
        SWRLAtomListElementHandler testSubject0 = new SWRLAtomListElementHandler(
                Utils.mockHandler()) {};
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        List<SWRLAtom> result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockSWRLAtomHandler());
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLBuiltInAtomElementHandler() throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLClassAtomElementHandler() throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLDataPropertyAtomElementHandler() throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLDifferentIndividualsAtomElementHandler()
            throws Exception {
        SWRLDifferentIndividualsAtomElementHandler testSubject0 = new SWRLDifferentIndividualsAtomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLObjectPropertyAtomElementHandler()
            throws Exception {
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
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLRuleElementHandler() throws Exception {
        SWRLRuleElementHandler testSubject0 = new SWRLRuleElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChild(Utils.mockSWRLAtomListHandler());
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        testSubject0.startElement("");
        testSubject0.endElement();
        Object result1 = testSubject0.getOWLObject();
        OWLAxiom result2 = testSubject0.getOWLObject();
        testSubject0.handleChild(Utils.mockAnnotationHandler());
        testSubject0.setAxiom(mock(OWLAxiom.class));
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .getConfiguration();
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
        IRI result6 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result7 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLSameIndividualAtomElementHandler()
            throws Exception {
        SWRLSameIndividualAtomElementHandler testSubject0 = new SWRLSameIndividualAtomElementHandler(
                Utils.mockHandler());
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        testSubject0.handleChild(Utils.mockSWRLVariableHandler());
        testSubject0.handleChild(Utils.mockIndividualHandler());
        SWRLAtom result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.setAtom(mock(SWRLAtom.class));
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.attribute("IRI", "");
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestSWRLVariableElementHandler() throws Exception {
        SWRLVariableElementHandler testSubject0 = new SWRLVariableElementHandler(
                Utils.mockHandler()) {};
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.endElement();
        SWRLVariable result0 = testSubject0.getOWLObject();
        Object result1 = testSubject0.getOWLObject();
        testSubject0.attribute("IRI", "");
        OWLOntologyLoaderConfiguration result2 = testSubject0
                .getConfiguration();
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
        IRI result5 = testSubject0.getIRIFromAttribute("IRI", "");
        IRI result6 = testSubject0.getIRIFromElement("IRI", "");
        testSubject0.setParentHandler(Utils.mockElementHandler());
        testSubject0.handleChars(new char[5], 0, 0);
        String result7 = testSubject0.toString();
    }

    @Test
    public void shouldTestTranslatedOWLOntologyChangeException()
            throws Exception {
        TranslatedOWLOntologyChangeException testSubject0 = new TranslatedOWLOntologyChangeException(
                mock(OWLOntologyChangeException.class));
        OWLOntologyChangeException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.toString();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedOWLParserException() throws Exception {
        TranslatedOWLParserException testSubject0 = new TranslatedOWLParserException(
                mock(OWLParserException.class));
        OWLParserException result0 = testSubject0.getParserException();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.toString();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedUnloadableImportException()
            throws Exception {
        TranslatedUnloadableImportException testSubject0 = new TranslatedUnloadableImportException(
                mock(UnloadableImportException.class));
        UnloadableImportException result0 = testSubject0
                .getUnloadableImportException();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.toString();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }
}
