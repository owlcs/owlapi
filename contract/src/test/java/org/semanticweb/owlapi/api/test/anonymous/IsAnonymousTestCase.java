package org.semanticweb.owlapi.api.test.anonymous;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

class IsAnonymousTestCase extends TestBase {

    @Test
    void shouldCheckAnonymous() {
        IRI itest = iri("urn:test:", "i");
        IRI j = iri("urn:test:", "j");
        OWLClass c3 = df.getOWLClass(itest);
        OWLClass c1 = df.getOWLClass(iri("urn:test:", "c1"));
        OWLClass c2 = df.getOWLClass(iri("urn:test:", "c2"));
        OWLDataProperty dp = df.getOWLDataProperty(itest);
        OWLDataProperty dp1test = df.getOWLDataProperty(iri("urn:test:", "dp1"));
        OWLObjectProperty op = df.getOWLObjectProperty(itest);
        OWLObjectProperty op1test = df.getOWLObjectProperty(iri("urn:test:", "op1"));
        OWLObjectProperty op2test = df.getOWLObjectProperty(iri("urn:test:", "op2"));
        OWLNamedIndividual ind = df.getOWLNamedIndividual(itest);
        OWLNamedIndividual ind1 = df.getOWLNamedIndividual(iri("urn:test:", "ind1"));
        OWLAnnotation label = df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral("label"));
        OWLLiteral literal = df.getOWLLiteral("literal");
        SWRLClassAtom sc =
            df.getSWRLClassAtom(c3, df.getSWRLIndividualArgument(df.getOWLNamedIndividual(j)));
        SWRLIndividualArgument sind = df.getSWRLIndividualArgument(ind);
        SWRLIndividualArgument sind1 = df.getSWRLIndividualArgument(ind1);
        SWRLLiteralArgument sl = df.getSWRLLiteralArgument(literal);
        OWLDatatype itype = df.getIntegerOWLDatatype();
        OWLDatatype dtype = df.getDoubleOWLDatatype();
        assertFalse(new OWLOntologyID(itest, itest).isAnonymous());
        OWLOntology o = create(itest);
        assertFalse(o.isAnonymous());
        OWLOntology o1 = createAnon();
        assertTrue(o1.isAnonymous());
        // entities
        assertFalse(c3.isAnonymous());
        assertFalse(dp.isAnonymous());
        assertFalse(op.isAnonymous());
        assertFalse(df.getOWLDatatype(itest).isAnonymous());
        assertFalse(ind.isAnonymous());
        assertFalse(df.getOWLAnnotationProperty(itest).isAnonymous());
        assertFalse(df.getOWLThing().isAnonymous());
        assertFalse(df.getOWLNothing().isAnonymous());
        // convenience properties
        assertFalse(df.getRDFSLabel().isAnonymous());
        assertFalse(df.getRDFSComment().isAnonymous());
        assertFalse(df.getOWLTopObjectProperty().isAnonymous());
        assertFalse(df.getOWLTopDataProperty().isAnonymous());
        assertFalse(df.getTopDatatype().isAnonymous());
        assertFalse(df.getOWLBottomObjectProperty().isAnonymous());
        assertFalse(df.getOWLBottomDataProperty().isAnonymous());
        assertFalse(df.getRDFSSeeAlso().isAnonymous());
        assertFalse(df.getRDFSIsDefinedBy().isAnonymous());
        assertFalse(df.getOWLVersionInfo().isAnonymous());
        assertFalse(df.getOWLBackwardCompatibleWith().isAnonymous());
        assertFalse(df.getOWLIncompatibleWith().isAnonymous());
        assertFalse(df.getOWLDeprecated().isAnonymous());
        // convenience datatype methods
        assertFalse(df.getRDFPlainLiteral().isAnonymous());
        assertFalse(itype.isAnonymous());
        assertFalse(df.getFloatOWLDatatype().isAnonymous());
        assertFalse(dtype.isAnonymous());
        assertFalse(df.getBooleanOWLDatatype().isAnonymous());
        assertFalse(df.getOWLDatatype(OWL2Datatype.XSD_STRING.getIRI()).isAnonymous());
        assertTrue(itest.isAnonymous());
        assertTrue(label.isAnonymous());
        assertTrue(
            df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral("comment")).isAnonymous());
        assertTrue(df.getSWRLVariable(iri("urn:swrl:", "variable")).isAnonymous());
        assertTrue(df.getSWRLVariable(itest).isAnonymous());
        assertTrue(new OWLOntologyID().isAnonymous());
        assertTrue(df.getOWLAnonymousIndividual().isAnonymous());
        assertTrue(literal.isAnonymous());
        assertTrue(df.getDeprecatedOWLAnnotationAssertionAxiom(itest).isAnonymous());
        assertTrue(df.getOWLAnnotation(df.getRDFSComment(), literal).isAnonymous());
        assertTrue(sc.isAnonymous());
        assertTrue(df.getOWLDeclarationAxiom(c3).isAnonymous());
        assertTrue(df.getOWLDatatypeDefinitionAxiom(itype, dtype).isAnonymous());
        assertTrue(df.getOWLDataComplementOf(itype).isAnonymous());
        assertTrue(df.getOWLDataAllValuesFrom(dp, itype).isAnonymous());
        assertTrue(df.getOWLDataSomeValuesFrom(dp, itype).isAnonymous());
        assertTrue(
            df.getOWLDisjointUnionAxiom(c3, new HashSet<OWLClassExpression>(Arrays.asList(c1, c2)))
                .isAnonymous());
        assertTrue(
            df.getOWLSubPropertyChainOfAxiom(Arrays.asList(op1test, op2test), op).isAnonymous());
        assertTrue(
            df.getSWRLRule(Collections.singleton(sc), Collections.singleton(sc)).isAnonymous());
        assertTrue(df.getSWRLDataRangeAtom(itype, sl).isAnonymous());
        assertTrue(df.getSWRLObjectPropertyAtom(op, sind, sind1).isAnonymous());
        assertTrue(df.getSWRLDataPropertyAtom(dp, sind, sl).isAnonymous());
        assertTrue(df.getSWRLBuiltInAtom(itest, Arrays.asList(sl)).isAnonymous());
        assertTrue(sind.isAnonymous());
        assertTrue(sl.isAnonymous());
        assertTrue(df.getSWRLSameIndividualAtom(sind1, sind).isAnonymous());
        assertTrue(df.getSWRLDifferentIndividualsAtom(sind1, sind).isAnonymous());
        assertTrue(df.getOWLAnnotationAssertionAxiom(itest, label).isAnonymous());
        assertTrue(df.getOWLClassAssertionAxiom(c3, ind).isAnonymous());
        assertTrue(df.getOWLDataPropertyAssertionAxiom(dp, ind, 2D).isAnonymous());
        assertTrue(df.getOWLNegativeDataPropertyAssertionAxiom(dp, ind, literal).isAnonymous());
        assertTrue(df.getOWLObjectPropertyAssertionAxiom(op, ind, ind1).isAnonymous());
        assertTrue(df.getOWLNegativeObjectPropertyAssertionAxiom(op, ind, ind1).isAnonymous());
        assertTrue(df.getOWLSameIndividualAxiom(ind, ind1).isAnonymous());
        assertTrue(df.getOWLDifferentIndividualsAxiom(ind, ind1).isAnonymous());
        assertTrue(df.getOWLDataExactCardinality(1, dp, itype).isAnonymous());
        assertTrue(df.getOWLDataMaxCardinality(1, dp).isAnonymous());
        assertTrue(df.getOWLDataMinCardinality(1, dp).isAnonymous());
        assertTrue(df.getOWLObjectExactCardinality(1, op).isAnonymous());
        assertTrue(df.getOWLObjectMinCardinality(1, op).isAnonymous());
        assertTrue(df.getOWLObjectMaxCardinality(1, op).isAnonymous());
        assertTrue(df.getOWLDisjointClassesAxiom(c3, c1).isAnonymous());
        assertTrue(df.getOWLDisjointObjectPropertiesAxiom(op, op1test).isAnonymous());
        assertTrue(df.getOWLDisjointDataPropertiesAxiom(dp, dp1test).isAnonymous());
        assertTrue(df.getOWLEquivalentClassesAxiom(c3, c1).isAnonymous());
        assertTrue(df.getOWLEquivalentObjectPropertiesAxiom(op, op1test).isAnonymous());
        assertTrue(df.getOWLEquivalentDataPropertiesAxiom(dp, dp1test).isAnonymous());
        assertTrue(df.getOWLFunctionalObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLInverseFunctionalObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLReflexiveObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLIrreflexiveObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLSymmetricObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLAsymmetricObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLTransitiveObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLFunctionalDataPropertyAxiom(dp).isAnonymous());
        assertTrue(df.getOWLDatatypeRestriction(itype, OWLFacet.LENGTH, literal).isAnonymous());
        assertTrue(df.getOWLDatatypeMinInclusiveRestriction(2D).isAnonymous());
        assertTrue(df.getOWLDatatypeMaxInclusiveRestriction(3D).isAnonymous());
        assertTrue(df.getOWLDatatypeMinMaxInclusiveRestriction(2D, 3D).isAnonymous());
        assertTrue(df.getOWLFacetRestriction(OWLFacet.LENGTH, 1).isAnonymous());
        assertTrue(df.getOWLDatatypeMinExclusiveRestriction(1).isAnonymous());
        assertTrue(df.getOWLDatatypeMaxExclusiveRestriction(1).isAnonymous());
        assertTrue(df.getOWLDatatypeMinMaxExclusiveRestriction(1, 2).isAnonymous());
        assertTrue(df.getOWLObjectPropertyDomainAxiom(op, c3).isAnonymous());
        assertTrue(df.getOWLDataPropertyDomainAxiom(dp, c3).isAnonymous());
        assertTrue(df.getOWLAnnotationPropertyDomainAxiom(df.getRDFSLabel(), itest).isAnonymous());
        assertTrue(df.getOWLObjectPropertyRangeAxiom(op, c3).isAnonymous());
        assertTrue(df.getOWLDataPropertyRangeAxiom(dp, itype).isAnonymous());
        assertTrue(df.getOWLAnnotationPropertyRangeAxiom(df.getRDFSLabel(), itest).isAnonymous());
        assertTrue(df.getOWLDataIntersectionOf(itype, dtype).isAnonymous());
        assertTrue(df.getOWLObjectIntersectionOf(c3, c1).isAnonymous());
        assertTrue(df.getOWLDataUnionOf(itype, dtype).isAnonymous());
        assertTrue(df.getOWLObjectUnionOf(c3, c1).isAnonymous());
        assertTrue(df.getOWLSubClassOfAxiom(c3, c1).isAnonymous());
        assertTrue(df.getOWLSubObjectPropertyOfAxiom(op, op1test).isAnonymous());
        assertTrue(df.getOWLSubDataPropertyOfAxiom(dp, dp1test).isAnonymous());
        assertTrue(df.getOWLSubAnnotationPropertyOfAxiom(df.getRDFSLabel(), df.getRDFSLabel())
            .isAnonymous());
        assertTrue(df.getOWLObjectComplementOf(c3).isAnonymous());
        assertTrue(df.getOWLDataOneOf(literal).isAnonymous());
        assertTrue(df.getOWLObjectOneOf(ind).isAnonymous());
        assertTrue(df.getOWLObjectAllValuesFrom(op, c3).isAnonymous());
        assertTrue(df.getOWLObjectSomeValuesFrom(op, c3).isAnonymous());
        assertTrue(df.getOWLHasKeyAxiom(c3, dp).isAnonymous());
        assertTrue(df.getOWLInverseObjectPropertiesAxiom(op, op).isAnonymous());
        assertTrue(df.getOWLObjectHasValue(op, ind).isAnonymous());
        assertTrue(df.getOWLDataHasValue(dp, literal).isAnonymous());
        assertTrue(df.getOWLObjectInverseOf(op).isAnonymous());
        assertTrue(df.getOWLObjectHasSelf(op).isAnonymous());
    }
}
