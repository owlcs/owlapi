package org.semanticweb.owlapi6.apitest;

import java.util.List;

import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.apitest.baseclasses.DF;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.SWRLAtom;
import org.semanticweb.owlapi6.model.SWRLRule;

class Builder extends DF {
//@formatter:off
    private static final SWRLAtom v1 = SWRLBuiltInAtom(iri("urn:test#", "v1"), SWRL.var3, SWRL.var4);
    private static final SWRLAtom v2 = SWRLBuiltInAtom(iri("urn:test#", "v2"), SWRL.var5, SWRL.var6);
    private static final List<SWRLAtom> body2 = l(v1, SWRLClassAtom(CLASSES.C, SWRL.var2),
        SWRLDataRangeAtom(DATATYPES.DT, SWRL.var1), SWRLBuiltInAtom(IRIS.iri, SWRL.var1),
        SWRLDifferentIndividualsAtom(SWRL.var2, SWRLIndividualArgument(INDIVIDUALS.I)),
        SWRLSameIndividualAtom(SWRL.var2, SWRLIndividualArgument(INDIVIDUALS.IND_IRI)),
        SWRLBuiltInAtom(IRIS.iri, SWRL.var1));
    private static final List<SWRLAtom> head2 = l(v2,
        SWRLDataPropertyAtom(DATAPROPS.DP, SWRL.var2, SWRLLiteralArgument(LITERALS.LIT_FALSE)),
        SWRLObjectPropertyAtom(OBJPROPS.OP, SWRL.var2, SWRL.var2));

    public static final SWRLRule bigRule                            = SWRLRule(as, body2, head2);
    public static final OWLHasKeyAxiom hasKey                       = HasKey(as, CLASSES.C, OBJPROPS.OP_IRI, OBJPROPS.OP, DATAPROPS.DP);
    public static final OWLSymmetricObjectPropertyAxiom symm        = SymmetricObjectProperty(as, OBJPROPS.OP);
    public static final OWLTransitiveObjectPropertyAxiom trans      = TransitiveObjectProperty(as, OBJPROPS.OP);
    public static final SWRLRule rule                               = SWRLRule(l(v1), l(v2));
    public static final OWLSubObjectPropertyOfAxiom subObject       = SubObjectPropertyOf(as, OBJPROPS.OP, TopObjectProperty());
    public static final OWLSubDataPropertyOfAxiom subData           = SubDataPropertyOf(DATAPROPS.DP, TopDataProperty());
    public static final OWLSubClassOfAxiom subClass                 = SubClassOf(as, CLASSES.C, OWLThing());
    public static final OWLSubAnnotationPropertyOfAxiom subAnn      = SubAnnotationPropertyOf(as, ANNPROPS.ap, RDFSLabel());
    public static final OWLSameIndividualAxiom same                 = SameIndividual(as, INDIVIDUALS.I, INDIVIDUALS.IND_IRI);
    public static final OWLReflexiveObjectPropertyAxiom ref         = ReflexiveObjectProperty(as, OBJPROPS.OP);
    public static final OWLSubPropertyChainOfAxiom chain            = SubPropertyChainOf(as, l(OBJPROPS.OP_IRI, OBJPROPS.OP), OBJPROPS.OP);
    public static final OWLObjectPropertyRangeAxiom oRange          = ObjectPropertyRange(as, OBJPROPS.OP, CLASSES.C);
    public static final OWLObjectPropertyDomainAxiom oDom           = ObjectPropertyDomain(as, OBJPROPS.OP, CLASSES.C);
    public static final OWLObjectPropertyAssertionAxiom opaInv      = ObjectPropertyAssertion(as, ObjectInverseOf(OBJPROPS.OP), INDIVIDUALS.I, INDIVIDUALS.I);
    public static final OWLObjectPropertyAssertionAxiom opaInvj     = ObjectPropertyAssertion(as, ObjectInverseOf(OBJPROPS.OP), INDIVIDUALS.I, INDIVIDUALS.J);
    public static final OWLObjectPropertyAssertionAxiom opa         = ObjectPropertyAssertion(as, OBJPROPS.OP, INDIVIDUALS.I, INDIVIDUALS.I);
    public static final OWLNegativeObjectPropertyAssertionAxiom nop = NegativeObjectPropertyAssertion(as, OBJPROPS.OP, INDIVIDUALS.I, INDIVIDUALS.I);
    public static final OWLNegativeDataPropertyAssertionAxiom ndp   = NegativeDataPropertyAssertion(as, DATAPROPS.DP, INDIVIDUALS.I, LITERALS.LIT_FALSE);
    public static final OWLIrreflexiveObjectPropertyAxiom irr       = IrreflexiveObjectProperty(as, OBJPROPS.OP);
    public static final OWLInverseObjectPropertiesAxiom iop         = InverseObjectProperties(as, OBJPROPS.OP, OBJPROPS.OP);
    public static final OWLInverseFunctionalObjectPropertyAxiom ifp = InverseFunctionalObjectProperty(as, OBJPROPS.OP);
    public static final OWLFunctionalObjectPropertyAxiom fop        = FunctionalObjectProperty(as, OBJPROPS.OP);
    public static final OWLFunctionalDataPropertyAxiom fdp          = FunctionalDataProperty(as, DATAPROPS.DP);
    public static final OWLEquivalentObjectPropertiesAxiom eOp      = EquivalentObjectProperties(as, OBJPROPS.OP_IRI, OBJPROPS.OP);
    public static final OWLEquivalentDataPropertiesAxiom eDp        = EquivalentDataProperties(as, DATAPROPS.DP_IRI, DATAPROPS.DP);
    public static final OWLEquivalentClassesAxiom ec                = EquivalentClasses(as, CLASSES.C_IRI, CLASSES.C);
    public static final OWLDisjointUnionAxiom du                    = DisjointUnion(as, CLASSES.C, CLASSES.C_IRI, CLASSES.C);
    public static final OWLDisjointObjectPropertiesAxiom dOp        = DisjointObjectProperties(as, OBJPROPS.OP_IRI, OBJPROPS.OP);
    public static final OWLDisjointDataPropertiesAxiom dDp          = DisjointDataProperties(as, DATAPROPS.DP_IRI, DATAPROPS.DP);
    public static final OWLDisjointClassesAxiom dc                  = DisjointClasses(CLASSES.C, CLASSES.C_IRI);
    public static final OWLDifferentIndividualsAxiom assDi          = DifferentIndividuals(INDIVIDUALS.I, INDIVIDUALS.IND_IRI);
    public static final OWLDeclarationAxiom decI                    = Declaration(as, INDIVIDUALS.I);
    public static final OWLDeclarationAxiom decAp                   = Declaration(as, ANNPROPS.ap);
    public static final OWLDeclarationAxiom decDt                   = Declaration(as, DATATYPES.DT);
    public static final OWLDeclarationAxiom decDp                   = Declaration(as, DATAPROPS.DP);
    public static final OWLDeclarationAxiom decOp                   = Declaration(as, OBJPROPS.OP);
    public static final OWLDeclarationAxiom decC                    = Declaration(as, CLASSES.C);
    public static final OWLDatatypeDefinitionAxiom dDef             = DatatypeDefinition(as, DATATYPES.DT, Double());
    public static final OWLDataPropertyRangeAxiom dRange            = DataPropertyRange(as, DATAPROPS.DP, DATATYPES.DT);
    public static final OWLDataPropertyDomainAxiom dDom             = DataPropertyDomain(as, DATAPROPS.DP, CLASSES.C);
    public static final OWLDataPropertyAssertionAxiom assDPlain     = DataPropertyAssertion(as, DATAPROPS.DP, INDIVIDUALS.I, LITERALS.plainlit);
    public static final OWLDataPropertyAssertionAxiom assD          = DataPropertyAssertion(as, DATAPROPS.DP, INDIVIDUALS.I, LITERALS.LIT_FALSE);
    public static final OWLDataPropertyRangeAxiom dRangeRestrict    = DataPropertyRange(as, DATAPROPS.DP, DatatypeMinMaxExclusiveRestriction(5.0D, 6.0D));
    public static final OWLDataPropertyRangeAxiom dNot              = DataPropertyRange(as, DATAPROPS.DP, DataComplementOf(DataOneOf(LITERALS.LIT_FALSE)));
    public static final OWLDataPropertyRangeAxiom dOneOf            = DataPropertyRange(as, DATAPROPS.DP, DataOneOf(LITERALS.LIT_FALSE));
    public static final OWLClassAssertionAxiom assDEq               = ClassAssertion(as, DataExactCardinality(1, DATAPROPS.DP, DATATYPES.DT), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assDMax              = ClassAssertion(as, DataMaxCardinality(1, DATAPROPS.DP, DATATYPES.DT), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assDMin              = ClassAssertion(as, DataMinCardinality(1, DATAPROPS.DP, DATATYPES.DT), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assDHas              = ClassAssertion(as, DataHasValue(DATAPROPS.DP, LITERALS.LIT_FALSE), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assDAll              = ClassAssertion(as, DataAllValuesFrom(DATAPROPS.DP, DATATYPES.DT), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assDSome             = ClassAssertion(as, DataSomeValuesFrom(DATAPROPS.DP, DATATYPES.DT), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assOneOf             = ClassAssertion(as, ObjectOneOf(INDIVIDUALS.I), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assHasSelf           = ClassAssertion(as, ObjectHasSelf(OBJPROPS.OP), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assEq                = ClassAssertion(as, ObjectExactCardinality(1, OBJPROPS.OP, CLASSES.C), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assMax               = ClassAssertion(as, ObjectMaxCardinality(1, OBJPROPS.OP, CLASSES.C), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assMin               = ClassAssertion(as, ObjectMinCardinality(1, OBJPROPS.OP, CLASSES.C), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assMinTop            = ClassAssertion(as, ObjectMinCardinality(1, OBJPROPS.OP, OWLThing()), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assHas               = ClassAssertion(as, ObjectHasValue(OBJPROPS.OP, INDIVIDUALS.I), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assAll               = ClassAssertion(as, ObjectAllValuesFrom(OBJPROPS.OP, CLASSES.C), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assSome              = ClassAssertion(as, ObjectSomeValuesFrom(OBJPROPS.OP, CLASSES.C), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assNotAnon           = ClassAssertion(as, notC, AnonymousIndividual("id"));
    public static final OWLClassAssertionAxiom assNot               = ClassAssertion(as, notC, INDIVIDUALS.I);
    public static final OWLDataPropertyRangeAxiom dRangeOr          = DataPropertyRange(as, DATAPROPS.DP, DataUnionOf(DATATYPES.DT, DataOneOf(LITERALS.LIT_FALSE)));
    public static final OWLDataPropertyRangeAxiom dRangeAnd         = DataPropertyRange(as, DATAPROPS.DP, DataIntersectionOf(DATATYPES.DT, DataOneOf(LITERALS.LIT_FALSE)));
    public static final OWLClassAssertionAxiom assOr                = ClassAssertion(as, ObjectUnionOf(CLASSES.C_IRI, CLASSES.C), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom assAnd               = ClassAssertion(as, ObjectIntersectionOf(CLASSES.C_IRI, CLASSES.C), INDIVIDUALS.I);
    public static final OWLClassAssertionAxiom ass                  = ClassAssertion(as, CLASSES.C, INDIVIDUALS.I);
    public static final OWLAnnotationPropertyRangeAxiom annRange    = AnnotationPropertyRange(as, ANNPROPS.ap, IRIS.iri);
    public static final OWLAnnotationPropertyDomainAxiom annDom     = AnnotationPropertyDomain(as, ANNPROPS.ap, IRIS.iri);
    public static final OWLAsymmetricObjectPropertyAxiom asymm      = AsymmetricObjectProperty(as, OBJPROPS.OP);
    public static final OWLAnnotationAssertionAxiom ann             = AnnotationAssertion(as, ANNPROPS.ap, IRIS.iri, LITERALS.LIT_FALSE);
//@formatter:on
    public static final OWLOntology onto = o();

    private static OWLOntology o() {
        try {
            return OWLManager.createOWLOntologyManager().createOntology(IRIS.iriTest);
        } catch (OWLOntologyCreationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<OWLAxiom> all =
        l(ann, asymm, annDom, annRange, ass, assAnd, assOr, dRangeAnd, dRangeOr, assNot, assNotAnon,
            assSome, assAll, assHas, assMin, assMax, assEq, assHasSelf, assOneOf, assDSome, assDAll,
            assDHas, assDMin, assDMax, assDEq, dOneOf, dNot, dRangeRestrict, assD, assDPlain, dDom,
            dRange, dDef, decC, decOp, decDp, decDt, decAp, decI, assDi, dc, dDp, dOp, du, ec, eDp,
            eOp, fdp, fop, ifp, iop, irr, ndp, nop, opa, opaInv, opaInvj, oDom, oRange, chain, ref,
            same, subAnn, subClass, subData, subObject, rule, symm, trans, hasKey, bigRule);

    private Builder() {
        // no instances
    }
}
