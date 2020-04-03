package uk.ac.manchester.cs.owl.owlapi;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.*;

class FindLiterals implements OWLObjectVisitorEx<Boolean> {
    private final OWLLiteral value;

    public FindLiterals(OWLLiteral value) {
        this.value = value;
    }

    @Override
    public Boolean visit(OWLAnnotation node) {
        if (node.getValue().equals(value)) {
            return Boolean.TRUE;
        }
        return visitStreamAndSpares(() -> node.getAnnotations().stream(), node.getValue());
    }

    @Override
    public Boolean visit(OWLLiteral node) {
        return Boolean.valueOf(node.equals(value));
    }

    @Override
    public Boolean visit(OWLDeclarationAxiom axiom) {
        return visitAnnotations(axiom);
    }

    protected Boolean visitAnnotations(HasAnnotations a) {
        return visitStream(a.getAnnotations().stream());
    }

    protected Boolean visitStream(Stream<? extends OWLObject> stream) {
        return Boolean.valueOf(stream.anyMatch(x -> x.accept(this).booleanValue()));
    }

    protected Boolean visitStreamAndSpares(Supplier<Stream<? extends OWLObject>> stream,
        OWLObject... objs) {
        for (OWLObject o : objs) {
            if (o.accept(this).booleanValue()) {
                return Boolean.TRUE;
            }
        }
        return visitStream(stream.get());
    }

    @Override
    public Boolean visit(OWLDatatypeDefinitionAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLAnnotationAssertionAxiom axiom) {
        return visitStreamAndSpares(() -> axiom.getAnnotations().stream(), axiom.getValue());
    }

    @Override
    public Boolean visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLSubClassOfAxiom axiom) {
        Boolean b = visitStream(Stream.of(axiom.getSubClass(), axiom.getSuperClass()));
        if (b.booleanValue()) {
            return b;
        }
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLDisjointClassesAxiom axiom) {
        Boolean b = visitStream(axiom.getClassExpressionsAsList().stream());
        if (b.booleanValue()) {
            return b;
        }
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLDataPropertyDomainAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLObjectPropertyDomainAxiom axiom) {
        return visitStreamAndSpares(() -> axiom.getAnnotations().stream(), axiom.getDomain());
    }

    @Override
    public Boolean visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return visitStreamAndSpares(() -> axiom.getAnnotations().stream(), axiom.getObject());
    }

    @Override
    public Boolean visit(OWLDifferentIndividualsAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLDisjointDataPropertiesAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLObjectPropertyRangeAxiom axiom) {
        return visitStreamAndSpares(() -> axiom.getAnnotations().stream(), axiom.getRange());
    }

    @Override
    public Boolean visit(OWLObjectPropertyAssertionAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLSubObjectPropertyOfAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLDisjointUnionAxiom axiom) {
        Boolean b = visitStream(axiom.getClassExpressions().stream());
        if (b.booleanValue()) {
            return Boolean.TRUE;
        }
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLDataPropertyRangeAxiom axiom) {
        return visitStreamAndSpares(() -> axiom.getAnnotations().stream(), axiom.getRange());
    }

    @Override
    public Boolean visit(OWLFunctionalDataPropertyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLClassAssertionAxiom axiom) {
        return visitStreamAndSpares(() -> axiom.getAnnotations().stream(),
            axiom.getClassExpression());
    }

    @Override
    public Boolean visit(OWLEquivalentClassesAxiom axiom) {
        Boolean b = visitStream(axiom.getClassExpressionsAsList().stream());
        if (b.booleanValue()) {
            return b;
        }
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLDataPropertyAssertionAxiom axiom) {
        return visitStreamAndSpares(() -> axiom.getAnnotations().stream(), axiom.getObject());
    }

    @Override
    public Boolean visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLSubDataPropertyOfAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLSameIndividualAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLSubPropertyChainOfAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLInverseObjectPropertiesAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(OWLHasKeyAxiom axiom) {
        return visitAnnotations(axiom);
    }

    @Override
    public Boolean visit(SWRLRule rule) {
        Boolean b = visitStream(rule.getHead().stream());
        if (b.booleanValue()) {
            return Boolean.TRUE;
        }
        b = visitStream(rule.getBody().stream());
        if (b.booleanValue()) {
            return Boolean.TRUE;
        }
        return visitAnnotations(rule);
    }

    @Override
    public Boolean visit(OWLClass ce) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLObjectIntersectionOf ce) {
        return visitStream(ce.getOperandsAsList().stream());
    }

    @Override
    public Boolean visit(OWLObjectUnionOf ce) {
        return visitStream(ce.getOperandsAsList().stream());
    }

    @Override
    public Boolean visit(OWLObjectComplementOf ce) {
        return ce.getOperand().accept(this);
    }

    @Override
    public Boolean visit(OWLObjectSomeValuesFrom ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLObjectAllValuesFrom ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLObjectHasValue ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLObjectMinCardinality ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLObjectExactCardinality ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLObjectMaxCardinality ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLObjectHasSelf ce) {
        return visitStream(ce.getNestedClassExpressions().stream());
    }

    @Override
    public Boolean visit(OWLObjectOneOf ce) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLDataSomeValuesFrom ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLDataAllValuesFrom ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLDataHasValue ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLDataMinCardinality ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLDataExactCardinality ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLDataMaxCardinality ce) {
        return ce.getFiller().accept(this);
    }

    @Override
    public Boolean visit(OWLDatatype node) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLDataComplementOf node) {
        return node.getDataRange().accept(this);
    }

    @Override
    public Boolean visit(OWLDataOneOf node) {
        return visitStream(node.getValues().stream());
    }

    @Override
    public Boolean visit(OWLDataIntersectionOf node) {
        return visitStream(node.getOperands().stream());
    }

    @Override
    public Boolean visit(OWLDataUnionOf node) {
        return visitStream(node.getOperands().stream());
    }

    @Override
    public Boolean visit(OWLDatatypeRestriction node) {
        return visitStreamAndSpares(() -> node.getFacetRestrictions().stream(), node.getDatatype());
    }

    @Override
    public Boolean visit(OWLFacetRestriction node) {
        return node.getFacetValue().accept(this);
    }

    @Override
    public Boolean visit(OWLObjectProperty property) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLObjectInverseOf property) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLDataProperty property) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLAnnotationProperty property) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLNamedIndividual individual) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(IRI iri) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(OWLAnonymousIndividual individual) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(SWRLClassAtom node) {
        Boolean b = node.getPredicate().accept(this);
        if (b.booleanValue()) {
            return Boolean.TRUE;
        }
        return node.getArgument().accept(this);
    }

    @Override
    public Boolean visit(SWRLDataRangeAtom node) {
        return visitStreamAndSpares(() -> node.getAllArguments().stream(),
            (OWLObject) node.getPredicate());
    }

    @Override
    public Boolean visit(SWRLObjectPropertyAtom node) {
        return visitStreamAndSpares(() -> node.getAllArguments().stream(), node.getPredicate());
    }

    @Override
    public Boolean visit(SWRLDataPropertyAtom node) {
        return visitStreamAndSpares(() -> node.getAllArguments().stream(), node.getPredicate());
    }

    @Override
    public Boolean visit(SWRLBuiltInAtom node) {
        return visitStreamAndSpares(() -> node.getAllArguments().stream(), node.getPredicate());
    }

    @Override
    public Boolean visit(SWRLVariable node) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(SWRLIndividualArgument node) {
        return Boolean.FALSE;
    }

    @Override
    public Boolean visit(SWRLLiteralArgument node) {
        return node.getLiteral().accept(this);
    }

    @Override
    public Boolean visit(SWRLSameIndividualAtom node) {
        if (node.getPredicate() instanceof OWLObject) {
            return visitStreamAndSpares(() -> node.getAllArguments().stream(),
                (OWLObject) node.getPredicate());
        }
        return visitStream(node.getAllArguments().stream());
    }

    @Override
    public Boolean visit(SWRLDifferentIndividualsAtom node) {
        if (node.getPredicate() instanceof OWLObject) {
            return visitStreamAndSpares(() -> node.getAllArguments().stream(),
                (OWLObject) node.getPredicate());
        }
        return visitStream(node.getAllArguments().stream());
    }

    @Override
    public Boolean visit(OWLOntology ontology) {
        return Boolean.FALSE;
    }
}
