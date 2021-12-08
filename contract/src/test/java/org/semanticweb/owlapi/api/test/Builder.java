package org.semanticweb.owlapi.api.test;

import static org.semanticweb.owlapi.api.test.baseclasses.DF.iri;

import java.util.List;

import org.semanticweb.owlapi.api.test.baseclasses.DF;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLRule;

class Builder {

    private static final String SWRL = "urn:swrl:var#";
    private static final String NS = DF.OWLAPI_TEST;
    private static final OWLAnnotationProperty ap = DF.AnnotationProperty(iri(NS, "ann"));
    private static final OWLLiteral plainlit = DF.Literal("string", "en");
    private static final IRI iri = DF.iri(NS, "iri");
    private static final List<OWLAnnotation> as = DF.l(DF.Annotation(ap, "test"));
    private static final SWRLAtom v1 = DF.SWRLBuiltInAtom(iri(SWRL, "v1"),
        DF.SWRLVariable(SWRL, "var3"), DF.SWRLVariable(SWRL, "var4"));
    private static final SWRLAtom v2 = DF.SWRLBuiltInAtom(iri(SWRL, "v2"),
        DF.SWRLVariable(SWRL, "var5"), DF.SWRLVariable(SWRL, "var6"));
    private static final List<SWRLAtom> body = DF.l(v1);
    private static final List<SWRLAtom> head = DF.l(v2);
    private static final SWRLDArgument var1 = DF.SWRLVariable(SWRL, "var1");
    private static final SWRLIArgument var2 = DF.SWRLVariable(SWRL, "var2");
    private static final List<SWRLAtom> body2 = DF.l(v1, DF.SWRLClassAtom(DF.C, var2),
        DF.SWRLDataRangeAtom(DF.DT, var1), DF.SWRLBuiltInAtom(iri, var1),
        DF.SWRLDifferentIndividualsAtom(var2, DF.SWRLIndividualArgument(DF.I)),
        DF.SWRLSameIndividualAtom(var2, DF.SWRLIndividualArgument(DF.NamedIndividual(iri))),
        DF.SWRLBuiltInAtom(iri, var1));
    private static final List<SWRLAtom> head2 =
        DF.l(v2, DF.SWRLDataPropertyAtom(DF.DP, var2, DF.SWRLLiteralArgument(DF.LIT_FALSE)),
            DF.SWRLObjectPropertyAtom(DF.OP, var2, var2));
    private final OWLOntologyManager m = getManager();

    // no parsers and storers injected
    private static OWLOntologyManager getManager() {
        OWLOntologyManager instance = OWLManager.createOWLOntologyManager();
        instance.getOntologyParsers().clear();
        instance.getOntologyStorers().clear();
        return instance;
    }

    public SWRLRule bigRule() {
        return DF.SWRLRule(as, body2, head2);
    }

    public OWLHasKeyAxiom hasKey() {
        return DF.HasKey(as, DF.C, DF.ObjectProperty(iri), DF.OP, DF.DP);
    }

    public OWLSymmetricObjectPropertyAxiom symm() {
        return DF.SymmetricObjectProperty(as, DF.OP);
    }

    public OWLTransitiveObjectPropertyAxiom trans() {
        return DF.TransitiveObjectProperty(as, DF.OP);
    }

    public SWRLRule rule() {
        return DF.SWRLRule(body, head);
    }

    public OWLSubObjectPropertyOfAxiom subObject() {
        return DF.SubObjectPropertyOf(as, DF.OP, DF.TopObjectProperty());
    }

    public OWLSubDataPropertyOfAxiom subData() {
        return DF.SubDataPropertyOf(DF.DP, DF.TopDataProperty());
    }

    public OWLSubClassOfAxiom subClass() {
        return DF.SubClassOf(as, DF.C, DF.OWLThing());
    }

    public OWLSubAnnotationPropertyOfAxiom subAnn() {
        return DF.SubAnnotationPropertyOf(as, ap, DF.RDFSLabel());
    }

    public OWLSameIndividualAxiom same() {
        return DF.SameIndividual(as, DF.I, DF.NamedIndividual(iri));
    }

    public OWLReflexiveObjectPropertyAxiom ref() {
        return DF.ReflexiveObjectProperty(as, DF.OP);
    }

    public OWLSubPropertyChainOfAxiom chain() {
        return DF.SubPropertyChainOf(as, DF.l(DF.ObjectProperty(iri), DF.OP), DF.OP);
    }

    public OWLObjectPropertyRangeAxiom oRange() {
        return DF.ObjectPropertyRange(as, DF.OP, DF.C);
    }

    public OWLObjectPropertyDomainAxiom oDom() {
        return DF.ObjectPropertyDomain(as, DF.OP, DF.C);
    }

    public OWLObjectPropertyAssertionAxiom opaInv() {
        return DF.ObjectPropertyAssertion(as, DF.ObjectInverseOf(DF.OP), DF.I, DF.I);
    }

    public OWLObjectPropertyAssertionAxiom opaInvj() {
        return DF.ObjectPropertyAssertion(as, DF.ObjectInverseOf(DF.OP), DF.I, DF.J);
    }

    public OWLObjectPropertyAssertionAxiom opa() {
        return DF.ObjectPropertyAssertion(as, DF.OP, DF.I, DF.I);
    }

    public OWLNegativeObjectPropertyAssertionAxiom nop() {
        return DF.NegativeObjectPropertyAssertion(as, DF.OP, DF.I, DF.I);
    }

    public OWLNegativeDataPropertyAssertionAxiom ndp() {
        return DF.NegativeDataPropertyAssertion(as, DF.DP, DF.I, DF.LIT_FALSE);
    }

    public OWLIrreflexiveObjectPropertyAxiom irr() {
        return DF.IrreflexiveObjectProperty(as, DF.OP);
    }

    public OWLInverseObjectPropertiesAxiom iop() {
        return DF.InverseObjectProperties(as, DF.OP, DF.OP);
    }

    public OWLInverseFunctionalObjectPropertyAxiom ifp() {
        return DF.InverseFunctionalObjectProperty(as, DF.OP);
    }

    public OWLFunctionalObjectPropertyAxiom fop() {
        return DF.FunctionalObjectProperty(as, DF.OP);
    }

    public OWLFunctionalDataPropertyAxiom fdp() {
        return DF.FunctionalDataProperty(as, DF.DP);
    }

    public OWLEquivalentObjectPropertiesAxiom eOp() {
        return DF.EquivalentObjectProperties(as, DF.ObjectProperty(iri), DF.OP);
    }

    public OWLEquivalentDataPropertiesAxiom eDp() {
        return DF.EquivalentDataProperties(as, DF.DataProperty(iri), DF.DP);
    }

    public OWLEquivalentClassesAxiom ec() {
        return DF.EquivalentClasses(as, DF.Class(iri), DF.C);
    }

    public OWLDisjointUnionAxiom du() {
        return DF.DisjointUnion(as, DF.C, DF.Class(iri), DF.C);
    }

    public OWLDisjointObjectPropertiesAxiom dOp() {
        return DF.DisjointObjectProperties(as, DF.ObjectProperty(iri), DF.OP);
    }

    public OWLDisjointDataPropertiesAxiom dDp() {
        return DF.DisjointDataProperties(as, DF.DataProperty(iri), DF.DP);
    }

    public OWLDisjointClassesAxiom dc() {
        return DF.DisjointClasses(DF.C, DF.Class(iri));
    }

    public OWLDifferentIndividualsAxiom assDi() {
        return DF.DifferentIndividuals(DF.I, DF.NamedIndividual(iri));
    }

    public OWLDeclarationAxiom decI() {
        return DF.Declaration(as, DF.I);
    }

    public OWLDeclarationAxiom decAp() {
        return DF.Declaration(as, ap);
    }

    public OWLDeclarationAxiom decDt() {
        return DF.Declaration(as, DF.DT);
    }

    public OWLDeclarationAxiom decDp() {
        return DF.Declaration(as, DF.DP);
    }

    public OWLDeclarationAxiom decOp() {
        return DF.Declaration(as, DF.OP);
    }

    public OWLDeclarationAxiom decC() {
        return DF.Declaration(as, DF.C);
    }

    public OWLDatatypeDefinitionAxiom dDef() {
        return DF.DatatypeDefinition(as, DF.DT, DF.Double());
    }

    public OWLDataPropertyRangeAxiom dRange() {
        return DF.DataPropertyRange(as, DF.DP, DF.DT);
    }

    public OWLDataPropertyDomainAxiom dDom() {
        return DF.DataPropertyDomain(as, DF.DP, DF.C);
    }

    public OWLDataPropertyAssertionAxiom assDPlain() {
        return DF.DataPropertyAssertion(as, DF.DP, DF.I, plainlit);
    }

    public OWLDataPropertyAssertionAxiom assD() {
        return DF.DataPropertyAssertion(as, DF.DP, DF.I, DF.LIT_FALSE);
    }

    public OWLDataPropertyRangeAxiom dRangeRestrict() {
        return DF.DataPropertyRange(as, DF.DP, DF.DatatypeMinMaxExclusiveRestriction(5.0D, 6.0D));
    }

    public OWLDataPropertyRangeAxiom dNot() {
        return DF.DataPropertyRange(as, DF.DP, DF.DataComplementOf(DF.DataOneOf(DF.LIT_FALSE)));
    }

    public OWLDataPropertyRangeAxiom dOneOf() {
        return DF.DataPropertyRange(as, DF.DP, DF.DataOneOf(DF.LIT_FALSE));
    }

    public OWLClassAssertionAxiom assDEq() {
        return DF.ClassAssertion(as, DF.DataExactCardinality(1, DF.DP, DF.DT), DF.I);
    }

    public OWLClassAssertionAxiom assDMax() {
        return DF.ClassAssertion(as, DF.DataMaxCardinality(1, DF.DP, DF.DT), DF.I);
    }

    public OWLClassAssertionAxiom assDMin() {
        return DF.ClassAssertion(as, DF.DataMinCardinality(1, DF.DP, DF.DT), DF.I);
    }

    public OWLClassAssertionAxiom assDHas() {
        return DF.ClassAssertion(as, DF.DataHasValue(DF.DP, DF.LIT_FALSE), DF.I);
    }

    public OWLClassAssertionAxiom assDAll() {
        return DF.ClassAssertion(as, DF.DataAllValuesFrom(DF.DP, DF.DT), DF.I);
    }

    public OWLClassAssertionAxiom assDSome() {
        return DF.ClassAssertion(as, DF.DataSomeValuesFrom(DF.DP, DF.DT), DF.I);
    }

    public OWLClassAssertionAxiom assOneOf() {
        return DF.ClassAssertion(as, DF.ObjectOneOf(DF.I), DF.I);
    }

    public OWLClassAssertionAxiom assHasSelf() {
        return DF.ClassAssertion(as, DF.ObjectHasSelf(DF.OP), DF.I);
    }

    public OWLClassAssertionAxiom assEq() {
        return DF.ClassAssertion(as, DF.ObjectExactCardinality(1, DF.OP, DF.C), DF.I);
    }

    public OWLClassAssertionAxiom assMax() {
        return DF.ClassAssertion(as, DF.ObjectMaxCardinality(1, DF.OP, DF.C), DF.I);
    }

    public OWLClassAssertionAxiom assMin() {
        return DF.ClassAssertion(as, DF.ObjectMinCardinality(1, DF.OP, DF.C), DF.I);
    }

    public OWLClassAssertionAxiom assMinTop() {
        return DF.ClassAssertion(as, DF.ObjectMinCardinality(1, DF.OP, DF.OWLThing()), DF.I);
    }

    public OWLClassAssertionAxiom assHas() {
        return DF.ClassAssertion(as, DF.ObjectHasValue(DF.OP, DF.I), DF.I);
    }

    public OWLClassAssertionAxiom assAll() {
        return DF.ClassAssertion(as, DF.ObjectAllValuesFrom(DF.OP, DF.C), DF.I);
    }

    public OWLClassAssertionAxiom assSome() {
        return DF.ClassAssertion(as, DF.ObjectSomeValuesFrom(DF.OP, DF.C), DF.I);
    }

    public OWLClassAssertionAxiom assNotAnon() {
        return DF.ClassAssertion(as, DF.ObjectComplementOf(DF.C), DF.AnonymousIndividual("id"));
    }

    public OWLClassAssertionAxiom assNot() {
        return DF.ClassAssertion(as, DF.ObjectComplementOf(DF.C), DF.I);
    }

    public OWLDataPropertyRangeAxiom dRangeOr() {
        return DF.DataPropertyRange(as, DF.DP, DF.DataUnionOf(DF.DT, DF.DataOneOf(DF.LIT_FALSE)));
    }

    public OWLDataPropertyRangeAxiom dRangeAnd() {
        return DF.DataPropertyRange(as, DF.DP,
            DF.DataIntersectionOf(DF.DT, DF.DataOneOf(DF.LIT_FALSE)));
    }

    public OWLClassAssertionAxiom assOr() {
        return DF.ClassAssertion(as, DF.ObjectUnionOf(DF.Class(iri), DF.C), DF.I);
    }

    public OWLClassAssertionAxiom assAnd() {
        return DF.ClassAssertion(as, DF.ObjectIntersectionOf(DF.Class(iri), DF.C), DF.I);
    }

    public OWLClassAssertionAxiom ass() {
        return DF.ClassAssertion(as, DF.C, DF.I);
    }

    public OWLAnnotationPropertyRangeAxiom annRange() {
        return DF.AnnotationPropertyRange(as, ap, iri);
    }

    public OWLAnnotationPropertyDomainAxiom annDom() {
        return DF.AnnotationPropertyDomain(as, ap, iri);
    }

    public OWLAsymmetricObjectPropertyAxiom asymm() {
        return DF.AsymmetricObjectProperty(as, DF.OP);
    }

    public OWLAnnotationAssertionAxiom ann() {
        return DF.AnnotationAssertion(as, ap, iri, DF.LIT_FALSE);
    }

    public OWLOntology onto() {
        try {
            return m.createOntology(DF.iriTest);
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OWLAxiom> all() {
        return DF.l(ann(), asymm(), annDom(), annRange(), ass(), assAnd(), assOr(), dRangeAnd(),
            dRangeOr(), assNot(), assNotAnon(), assSome(), assAll(), assHas(), assMin(), assMax(),
            assEq(), assHasSelf(), assOneOf(), assDSome(), assDAll(), assDHas(), assDMin(),
            assDMax(), assDEq(), dOneOf(), dNot(), dRangeRestrict(), assD(), assDPlain(), dDom(),
            dRange(), dDef(), decC(), decOp(), decDp(), decDt(), decAp(), decI(), assDi(), dc(),
            dDp(), dOp(), du(), ec(), eDp(), eOp(), fdp(), fop(), ifp(), iop(), irr(), ndp(), nop(),
            opa(), opaInv(), opaInvj(), oDom(), oRange(), chain(), ref(), same(), subAnn(),
            subClass(), subData(), subObject(), rule(), symm(), trans(), hasKey(), bigRule());
    }
}
