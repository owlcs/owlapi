package org.semanticweb.owlapi.api.test.anonymous;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;
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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

public class IsAnonymousTestCase extends TestBase {

    @Test
    public void shouldCheckAnonymous() throws OWLOntologyCreationException {
        IRI i = IRI.create("urn:test:i");
        IRI j = IRI.create("urn:test:j");
        IRI k = IRI.create("urn:test:k");
        OWLClass c = df.getOWLClass(i);
        OWLClass c1 = df.getOWLClass(IRI.create("urn:test:c1"));
        OWLClass c2 = df.getOWLClass(IRI.create("urn:test:c2"));
        OWLDataProperty dp = df.getOWLDataProperty(i);
        OWLDataProperty dp1 = df.getOWLDataProperty(IRI.create("urn:test:dp1"));
        OWLObjectProperty op = df.getOWLObjectProperty(i);
        OWLObjectProperty op1 = df.getOWLObjectProperty(IRI.create("urn:test:op1"));
        OWLObjectProperty op2 = df.getOWLObjectProperty(IRI.create("urn:test:op2"));
        OWLNamedIndividual ind = df.getOWLNamedIndividual(i);
        OWLNamedIndividual ind1 = df.getOWLNamedIndividual(IRI.create("urn:test:ind1"));
        OWLAnnotation label = df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral("label"));
        OWLLiteral l = df.getOWLLiteral("literal");
        SWRLClassAtom sc =
            df.getSWRLClassAtom(c, df.getSWRLIndividualArgument(df.getOWLNamedIndividual(j)));
        SWRLIndividualArgument sind = df.getSWRLIndividualArgument(ind);
        SWRLIndividualArgument sind1 = df.getSWRLIndividualArgument(ind1);
        SWRLLiteralArgument sl = df.getSWRLLiteralArgument(l);
        OWLDatatype itype = df.getIntegerOWLDatatype();
        OWLDatatype dtype = df.getDoubleOWLDatatype();
        assertFalse(new OWLOntologyID(i, i).isAnonymous());
        OWLOntology o = m.createOntology(i);
        assertFalse(o.isAnonymous());
        OWLOntology o1 = m.createOntology();
        assertTrue(o1.isAnonymous());
        // entities
        assertFalse(c.isAnonymous());
        assertFalse(dp.isAnonymous());
        assertFalse(op.isAnonymous());
        assertFalse(df.getOWLDatatype(i).isAnonymous());
        assertFalse(ind.isAnonymous());
        assertFalse(df.getOWLAnnotationProperty(i).isAnonymous());
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
        assertTrue(i.isAnonymous());
        assertTrue(label.isAnonymous());
        assertTrue(
            df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral("comment")).isAnonymous());
        assertTrue(df.getSWRLVariable(IRI.create("urn:swrl:variable")).isAnonymous());
        assertTrue(df.getSWRLVariable(i).isAnonymous());
        assertTrue(new OWLOntologyID().isAnonymous());
        assertTrue(df.getOWLAnonymousIndividual().isAnonymous());
        assertTrue(l.isAnonymous());
        assertTrue(df.getDeprecatedOWLAnnotationAssertionAxiom(i).isAnonymous());
        assertTrue(df.getOWLAnnotation(df.getRDFSComment(), l).isAnonymous());
        assertTrue(sc.isAnonymous());
        assertTrue(df.getOWLDeclarationAxiom(c).isAnonymous());
        assertTrue(df.getOWLDatatypeDefinitionAxiom(itype, dtype).isAnonymous());
        assertTrue(df.getOWLDataComplementOf(itype).isAnonymous());
        assertTrue(df.getOWLDataAllValuesFrom(dp, itype).isAnonymous());
        assertTrue(df.getOWLDataSomeValuesFrom(dp, itype).isAnonymous());
        assertTrue(
            df.getOWLDisjointUnionAxiom(c, new HashSet<OWLClassExpression>(Arrays.asList(c1, c2)))
                .isAnonymous());
        assertTrue(df.getOWLSubPropertyChainOfAxiom(Arrays.asList(op1, op2), op).isAnonymous());
        assertTrue(
            df.getSWRLRule(Collections.singleton(sc), Collections.singleton(sc)).isAnonymous());
        assertTrue(df.getSWRLDataRangeAtom(itype, sl).isAnonymous());
        assertTrue(df.getSWRLObjectPropertyAtom(op, sind, sind1).isAnonymous());
        assertTrue(df.getSWRLDataPropertyAtom(dp, sind, sl).isAnonymous());
        assertTrue(df.getSWRLBuiltInAtom(i, Arrays.asList(sl)).isAnonymous());
        assertTrue(sind.isAnonymous());
        assertTrue(sl.isAnonymous());
        assertTrue(df.getSWRLSameIndividualAtom(sind1, sind).isAnonymous());
        assertTrue(df.getSWRLDifferentIndividualsAtom(sind1, sind).isAnonymous());
        assertTrue(df.getOWLAnnotationAssertionAxiom(i, label).isAnonymous());
        assertTrue(df.getOWLClassAssertionAxiom(c, ind).isAnonymous());
        assertTrue(df.getOWLDataPropertyAssertionAxiom(dp, ind, 2D).isAnonymous());
        assertTrue(df.getOWLNegativeDataPropertyAssertionAxiom(dp, ind, l).isAnonymous());
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
        assertTrue(df.getOWLDisjointClassesAxiom(c, c1).isAnonymous());
        assertTrue(df.getOWLDisjointObjectPropertiesAxiom(op, op1).isAnonymous());
        assertTrue(df.getOWLDisjointDataPropertiesAxiom(dp, dp1).isAnonymous());
        assertTrue(df.getOWLEquivalentClassesAxiom(c, c1).isAnonymous());
        assertTrue(df.getOWLEquivalentObjectPropertiesAxiom(op, op1).isAnonymous());
        assertTrue(df.getOWLEquivalentDataPropertiesAxiom(dp, dp1).isAnonymous());
        assertTrue(df.getOWLFunctionalObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLInverseFunctionalObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLReflexiveObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLIrreflexiveObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLSymmetricObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLAsymmetricObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLTransitiveObjectPropertyAxiom(op).isAnonymous());
        assertTrue(df.getOWLFunctionalDataPropertyAxiom(dp).isAnonymous());
        assertTrue(df.getOWLDatatypeRestriction(itype, OWLFacet.LENGTH, l).isAnonymous());
        assertTrue(df.getOWLDatatypeMinInclusiveRestriction(2D).isAnonymous());
        assertTrue(df.getOWLDatatypeMaxInclusiveRestriction(3D).isAnonymous());
        assertTrue(df.getOWLDatatypeMinMaxInclusiveRestriction(2D, 3D).isAnonymous());
        assertTrue(df.getOWLFacetRestriction(OWLFacet.LENGTH, 1).isAnonymous());
        assertTrue(df.getOWLDatatypeMinExclusiveRestriction(1).isAnonymous());
        assertTrue(df.getOWLDatatypeMaxExclusiveRestriction(1).isAnonymous());
        assertTrue(df.getOWLDatatypeMinMaxExclusiveRestriction(1, 2).isAnonymous());
        assertTrue(df.getOWLObjectPropertyDomainAxiom(op, c).isAnonymous());
        assertTrue(df.getOWLDataPropertyDomainAxiom(dp, c).isAnonymous());
        assertTrue(df.getOWLAnnotationPropertyDomainAxiom(df.getRDFSLabel(), i).isAnonymous());
        assertTrue(df.getOWLObjectPropertyRangeAxiom(op, c).isAnonymous());
        assertTrue(df.getOWLDataPropertyRangeAxiom(dp, itype).isAnonymous());
        assertTrue(df.getOWLAnnotationPropertyRangeAxiom(df.getRDFSLabel(), i).isAnonymous());
        assertTrue(df.getOWLDataIntersectionOf(itype, dtype).isAnonymous());
        assertTrue(df.getOWLObjectIntersectionOf(c, c1).isAnonymous());
        assertTrue(df.getOWLDataUnionOf(itype, dtype).isAnonymous());
        assertTrue(df.getOWLObjectUnionOf(c, c1).isAnonymous());
        assertTrue(df.getOWLSubClassOfAxiom(c, c1).isAnonymous());
        assertTrue(df.getOWLSubObjectPropertyOfAxiom(op, op1).isAnonymous());
        assertTrue(df.getOWLSubDataPropertyOfAxiom(dp, dp1).isAnonymous());
        assertTrue(df.getOWLSubAnnotationPropertyOfAxiom(df.getRDFSLabel(), df.getRDFSLabel())
            .isAnonymous());
        assertTrue(df.getOWLObjectComplementOf(c).isAnonymous());
        assertTrue(df.getOWLDataOneOf(l).isAnonymous());
        assertTrue(df.getOWLObjectOneOf(ind).isAnonymous());
        assertTrue(df.getOWLObjectAllValuesFrom(op, c).isAnonymous());
        assertTrue(df.getOWLObjectSomeValuesFrom(op, c).isAnonymous());
        assertTrue(df.getOWLHasKeyAxiom(c, dp).isAnonymous());
        assertTrue(df.getOWLInverseObjectPropertiesAxiom(op, op).isAnonymous());
        assertTrue(df.getOWLObjectHasValue(op, ind).isAnonymous());
        assertTrue(df.getOWLDataHasValue(dp, l).isAnonymous());
        assertTrue(df.getOWLObjectInverseOf(op).isAnonymous());
        assertTrue(df.getOWLObjectHasSelf(op).isAnonymous());
    }
}
