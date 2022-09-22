package org.semanticweb.owlapi.apitest.anonymous;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.vocab.OWLFacet;

class IsAnonymousTestCase extends TestBase {

    @Test
    void shouldCheckAnonymous() {
        IRI itest = INDIVIDUALS.I.getIRI();
        SWRLClassAtom sc = SWRLClassAtom(CLASSES.A, SWRLIndividualArgument(INDIVIDUALS.J));
        SWRLIndividualArgument sind = SWRLIndividualArgument(INDIVIDUALS.I);
        SWRLIndividualArgument sind1 = SWRLIndividualArgument(INDIVIDUALS.indA);
        SWRLLiteralArgument sl = SWRLLiteralArgument(LITERALS.literal);
        assertFalse(OntologyID(itest, itest).isAnonymous());
        OWLOntology o = create(itest);
        assertFalse(o.isAnonymous());
        OWLOntology o1 = createAnon();
        assertTrue(o1.isAnonymous());
        // entities
        assertFalse(CLASSES.A.isAnonymous());
        assertFalse(DATAPROPS.DP.isAnonymous());
        assertFalse(OBJPROPS.op1.isAnonymous());
        assertFalse(Datatype(itest).isAnonymous());
        assertFalse(INDIVIDUALS.I.isAnonymous());
        assertFalse(AnnotationProperty(itest).isAnonymous());
        assertFalse(OWLThing().isAnonymous());
        assertFalse(OWLNothing().isAnonymous());
        // convenience properties
        assertFalse(RDFSLabel().isAnonymous());
        assertFalse(RDFSComment().isAnonymous());
        assertFalse(TopObjectProperty().isAnonymous());
        assertFalse(TopDataProperty().isAnonymous());
        assertFalse(TopDatatype().isAnonymous());
        assertFalse(BottomObjectProperty().isAnonymous());
        assertFalse(BottomDataProperty().isAnonymous());
        assertFalse(RDFSSeeAlso().isAnonymous());
        assertFalse(RDFSIsDefinedBy().isAnonymous());
        assertFalse(VersionInfo().isAnonymous());
        assertFalse(BackwardCompatibleWith().isAnonymous());
        assertFalse(IncompatibleWith().isAnonymous());
        assertFalse(Deprecated().isAnonymous());
        // convenience datatype methods
        assertFalse(RDFPlainLiteral().isAnonymous());
        assertFalse(Integer().isAnonymous());
        assertFalse(Float().isAnonymous());
        assertFalse(Double().isAnonymous());
        assertFalse(Boolean().isAnonymous());
        assertFalse(String().isAnonymous());
        assertTrue(itest.isAnonymous());
        assertTrue(RDFSLabel("label").isAnonymous());
        assertTrue(RDFSComment("comment").isAnonymous());
        assertTrue(SWRLVariable(iri("urn:swrl:", "variable")).isAnonymous());
        assertTrue(SWRLVariable("?variable").isAnonymous());
        assertTrue(SWRLVariable(itest).isAnonymous());
        assertTrue(OntologyID().isAnonymous());
        assertTrue(AnonymousIndividual().isAnonymous());
        assertTrue(LITERALS.literal.isAnonymous());
        assertTrue(DeprecatedOWLAnnotationAssertion(itest).isAnonymous());
        assertTrue(Annotation(RDFSComment(), LITERALS.literal).isAnonymous());
        assertTrue(sc.isAnonymous());
        assertTrue(Declaration(CLASSES.A).isAnonymous());
        assertTrue(DatatypeDefinition(Integer(), Double()).isAnonymous());
        assertTrue(DataComplementOf(Integer()).isAnonymous());
        assertTrue(DataAllValuesFrom(DATAPROPS.DP, Integer()).isAnonymous());
        assertTrue(DataSomeValuesFrom(DATAPROPS.DP, Integer()).isAnonymous());
        assertTrue(DisjointUnion(CLASSES.A, CLASSES.C1, CLASSES.C2).isAnonymous());
        assertTrue(SubPropertyChainOf(l(OBJPROPS.op1, OBJPROPS.op2), OBJPROPS.P).isAnonymous());
        assertTrue(SWRLRule(l(sc), l(sc)).isAnonymous());
        assertTrue(SWRLDataRangeAtom(Integer(), sl).isAnonymous());
        assertTrue(SWRLObjectPropertyAtom(OBJPROPS.op1, sind, sind1).isAnonymous());
        assertTrue(SWRLDataPropertyAtom(DATAPROPS.DP, sind, sl).isAnonymous());
        assertTrue(SWRLBuiltInAtom(itest, l(sl)).isAnonymous());
        assertTrue(sind.isAnonymous());
        assertTrue(sl.isAnonymous());
        assertTrue(SWRLSameIndividualAtom(sind1, sind).isAnonymous());
        assertTrue(SWRLDifferentIndividualsAtom(sind1, sind).isAnonymous());
        assertTrue(AnnotationAssertion(RDFSLabel(), itest, LITERALS.literal).isAnonymous());
        assertTrue(ClassAssertion(CLASSES.A, INDIVIDUALS.I).isAnonymous());
        assertTrue(DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal(2D)).isAnonymous());
        assertTrue(NegativeDataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, LITERALS.literal)
            .isAnonymous());
        assertTrue(
            ObjectPropertyAssertion(OBJPROPS.op1, INDIVIDUALS.I, INDIVIDUALS.indA).isAnonymous());
        assertTrue(NegativeObjectPropertyAssertion(OBJPROPS.op1, INDIVIDUALS.I, INDIVIDUALS.indA)
            .isAnonymous());
        assertTrue(SameIndividual(INDIVIDUALS.I, INDIVIDUALS.indA).isAnonymous());
        assertTrue(DifferentIndividuals(INDIVIDUALS.I, INDIVIDUALS.indA).isAnonymous());
        assertTrue(DataExactCardinality(1, DATAPROPS.DP, Integer()).isAnonymous());
        assertTrue(DataMaxCardinality(1, DATAPROPS.DP).isAnonymous());
        assertTrue(DataMinCardinality(1, DATAPROPS.DP).isAnonymous());
        assertTrue(ObjectExactCardinality(1, OBJPROPS.op1, OWLThing()).isAnonymous());
        assertTrue(ObjectMinCardinality(1, OBJPROPS.op1, OWLThing()).isAnonymous());
        assertTrue(ObjectMaxCardinality(1, OBJPROPS.op1, OWLThing()).isAnonymous());
        assertTrue(DisjointClasses(CLASSES.A, CLASSES.C1).isAnonymous());
        assertTrue(DisjointObjectProperties(OBJPROPS.op2, OBJPROPS.op1).isAnonymous());
        assertTrue(DisjointDataProperties(DATAPROPS.DP, DATAPROPS.DPP).isAnonymous());
        assertTrue(EquivalentClasses(CLASSES.A, CLASSES.C1).isAnonymous());
        assertTrue(EquivalentObjectProperties(OBJPROPS.op2, OBJPROPS.op1).isAnonymous());
        assertTrue(EquivalentDataProperties(DATAPROPS.DP, DATAPROPS.DPP).isAnonymous());
        assertTrue(FunctionalObjectProperty(OBJPROPS.op1).isAnonymous());
        assertTrue(InverseFunctionalObjectProperty(OBJPROPS.op1).isAnonymous());
        assertTrue(ReflexiveObjectProperty(OBJPROPS.op1).isAnonymous());
        assertTrue(IrreflexiveObjectProperty(OBJPROPS.op1).isAnonymous());
        assertTrue(SymmetricObjectProperty(OBJPROPS.op1).isAnonymous());
        assertTrue(AsymmetricObjectProperty(OBJPROPS.op1).isAnonymous());
        assertTrue(TransitiveObjectProperty(OBJPROPS.op1).isAnonymous());
        assertTrue(FunctionalDataProperty(DATAPROPS.DP).isAnonymous());
        assertTrue(
            DatatypeRestriction(Integer(), FacetRestriction(OWLFacet.LENGTH, LITERALS.literal))
                .isAnonymous());
        assertTrue(DatatypeMinInclusiveRestriction(2D).isAnonymous());
        assertTrue(DatatypeMaxInclusiveRestriction(3D).isAnonymous());
        assertTrue(DatatypeMinMaxInclusiveRestriction(2D, 3D).isAnonymous());
        assertTrue(FacetRestriction(OWLFacet.LENGTH, LITERALS.LIT_ONE).isAnonymous());
        assertTrue(DatatypeMinExclusiveRestriction(1).isAnonymous());
        assertTrue(DatatypeMaxExclusiveRestriction(1).isAnonymous());
        assertTrue(DatatypeMinMaxExclusiveRestriction(1, 2).isAnonymous());
        assertTrue(ObjectPropertyDomain(OBJPROPS.op1, CLASSES.A).isAnonymous());
        assertTrue(DataPropertyDomain(DATAPROPS.DP, CLASSES.A).isAnonymous());
        assertTrue(AnnotationPropertyDomain(RDFSLabel(), itest).isAnonymous());
        assertTrue(ObjectPropertyRange(OBJPROPS.op1, CLASSES.A).isAnonymous());
        assertTrue(DataPropertyRange(DATAPROPS.DP, Integer()).isAnonymous());
        assertTrue(AnnotationPropertyRange(RDFSLabel(), itest).isAnonymous());
        assertTrue(DataIntersectionOf(Integer(), Double()).isAnonymous());
        assertTrue(ObjectIntersectionOf(CLASSES.A, CLASSES.C1).isAnonymous());
        assertTrue(DataUnionOf(Integer(), Double()).isAnonymous());
        assertTrue(ObjectUnionOf(CLASSES.A, CLASSES.C1).isAnonymous());
        assertTrue(SubClassOf(CLASSES.A, CLASSES.C1).isAnonymous());
        assertTrue(SubObjectPropertyOf(OBJPROPS.op2, OBJPROPS.op1).isAnonymous());
        assertTrue(SubDataPropertyOf(DATAPROPS.DP, DATAPROPS.DPP).isAnonymous());
        assertTrue(SubAnnotationPropertyOf(RDFSLabel(), RDFSLabel()).isAnonymous());
        assertTrue(notA.isAnonymous());
        assertTrue(DataOneOf(LITERALS.literal).isAnonymous());
        assertTrue(ObjectOneOf(INDIVIDUALS.I).isAnonymous());
        assertTrue(ObjectAllValuesFrom(OBJPROPS.op1, CLASSES.A).isAnonymous());
        assertTrue(ObjectSomeValuesFrom(OBJPROPS.op1, CLASSES.A).isAnonymous());
        assertTrue(HasKey(CLASSES.A, DATAPROPS.DP).isAnonymous());
        assertTrue(InverseObjectProperties(OBJPROPS.op1, OBJPROPS.op1).isAnonymous());
        assertTrue(ObjectHasValue(OBJPROPS.op1, INDIVIDUALS.I).isAnonymous());
        assertTrue(DataHasValue(DATAPROPS.DP, LITERALS.literal).isAnonymous());
        assertTrue(ObjectInverseOf(OBJPROPS.op1).isAnonymous());
        assertTrue(ObjectHasSelf(OBJPROPS.op1).isAnonymous());
    }
}
