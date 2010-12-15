package org.coode.owlapi.latex;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
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
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
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
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 15-Jun-2006<br><br>
 */
@SuppressWarnings("unused")
public class LatexObjectVisitor implements OWLObjectVisitor {

    public static final String AND = "\\ensuremath{\\sqcap}";

    public static final String OR = "\\ensuremath{\\sqcup}";

    public static final String NOT = "\\ensuremath{\\lnot}";

    public static final String ALL = "\\ensuremath{\\forall}";

    public static final String SOME = "\\ensuremath{\\exists}";

    public static final String MIN = "\\ensuremath{\\geq}";

    public static final String MAX = "\\ensuremath{\\leq}";

    public static final String EQUAL = "\\ensuremath{=}";

    public static final String SUBCLASS = "\\ensuremath{\\sqsubseteq}";

    public static final String EQUIV = "\\ensuremath{\\equiv}";

    public static final String NOT_EQUIV = "\\ensuremath{\\not\\equiv}";

    public static final String TOP = "\\ensuremath{\\top}";

    public static final String BOTTOM = "\\ensuremath{\\bot}";

    public static final String SELF = "\\ensuremath{\\Self}";

    public static final String CIRC = "\\ensuremath{\\circ}";


    private OWLObject subject;

    private LatexWriter writer;

    private boolean prettyPrint = true;

    private OWLDataFactory df;

    private ShortFormProvider shortFormProvider;


    public LatexObjectVisitor(LatexWriter writer, OWLDataFactory df) {
        this.writer = writer;
        this.df = df;
        shortFormProvider = new SimpleShortFormProvider();
        subject = df.getOWLThing();
    }


    public void setSubject(OWLObject subject) {
        this.subject = subject;
    }


    public void setShortFormProvider(ShortFormProvider shortFormProvder) {
        this.shortFormProvider = shortFormProvder;
    }


    private void writeSpace() {
        writer.writeSpace();
    }


    private void write(Object o) {
        writer.write(o);
    }


    private void writeOpenBrace() {
        writer.writeOpenBrace();
    }


    private void writeCloseBrace() {
        writer.writeCloseBrace();
    }


    public boolean isPrettyPrint() {
        return prettyPrint;
    }


    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }
//
//     private void beginTabbing() {
//        write("\\begin{tabbing}\n");
//    }
//
//    private void endTabbing() {
//        write("\\end{tabbing}\n");
//    }


    public void visit(OWLObjectIntersectionOf node) {
        for (Iterator<OWLClassExpression> it = node.getOperands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(AND);
                writeSpace();
            }
        }
    }


    public void visit(OWLDataAllValuesFrom node) {

    }


    public void visit(OWLDataExactCardinality desc) {
        write(EQUAL);
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMaxCardinality desc) {
        write(MAX);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataMinCardinality desc) {
        write(MIN);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
    }


    public void visit(OWLDataSomeValuesFrom node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        node.getFiller().accept(this);
    }


    public void visit(OWLDataHasValue node) {
    }


    public void visit(OWLObjectAllValuesFrom node) {
        write(ALL);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeNested(node.getFiller());
    }


    public void visit(OWLObjectExactCardinality desc) {
        write(EQUAL);
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectMaxCardinality desc) {
        write(MAX);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectMinCardinality desc) {
        write(MIN);
        writeSpace();
        write(desc.getCardinality());
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        writeNested(desc.getFiller());
    }


    public void visit(OWLObjectSomeValuesFrom node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeNested(node.getFiller());
    }


    public void visit(OWLObjectHasValue node) {
        write(SOME);
        writeSpace();
        node.getProperty().accept(this);
        writeSpace();
        writeOpenBrace();
        node.getValue().accept(this);
        writeCloseBrace();
    }


    public void visit(OWLObjectComplementOf node) {
        write(NOT);
        writeNested(node.getOperand());
    }


    public void visit(OWLObjectUnionOf node) {
        for (Iterator<OWLClassExpression> it = node.getOperands().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(OR);
                writeSpace();
            }
        }
    }


    public void visit(OWLClass node) {
        write(escapeName(shortFormProvider.getShortForm(node)));
    }


    public void visit(OWLObjectOneOf node) {
        for (Iterator<OWLIndividual> it = node.getIndividuals().iterator(); it.hasNext();) {
            writeOpenBrace();
            it.next().accept(this);
            writeCloseBrace();
            if (it.hasNext()) {
                writeSpace();
                write(OR);
                writeSpace();
            }
        }
    }


    public void visit(OWLDataProperty entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }


    public void visit(OWLObjectProperty entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }


    public void visit(OWLNamedIndividual entity) {
        write(escapeName(shortFormProvider.getShortForm(entity)));
    }


    public void visit(OWLObjectHasSelf desc) {
        write(SOME);
        writeSpace();
        desc.getProperty().accept(this);
        writeSpace();
        write(SELF);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() != 2) {
            for (OWLClassExpression left : axiom.getClassExpressions()) {
                for (OWLClassExpression right : axiom.getClassExpressions()) {
                    if (left != right) {
                        if (left.equals(subject)) {
                            left.accept(this);
                            writeSpace();
                            write(SUBCLASS);
                            writeSpace();
                            write(NOT);
                            writeSpace();
                            right.accept(this);
                        }
                        else {
                            right.accept(this);
                            writeSpace();
                            write(SUBCLASS);
                            writeSpace();
                            write(NOT);
                            writeSpace();
                            left.accept(this);
                        }
                        writer.writeNewLine();
                    }
                }
            }
        }
        else {
            Iterator<OWLClassExpression> it = axiom.getClassExpressions().iterator();
            OWLClassExpression descA = it.next();
            OWLClassExpression descB = it.next();
            OWLClassExpression lhs;
            OWLClassExpression rhs;
            if (descA.equals(subject)) {
                lhs = descA;
                rhs = descB;
            }
            else {
                lhs = descB;
                rhs = descA;
            }
            lhs.accept(this);
            writeSpace();
            write(SUBCLASS);
            writeSpace();
            write(NOT);
            writeSpace();
            rhs.accept(this);
        }
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() > 2) {
            Set<Set<OWLClassExpression>> rendered = new HashSet<Set<OWLClassExpression>>();
            for (OWLClassExpression left : axiom.getClassExpressions()) {
                for (OWLClassExpression right : axiom.getClassExpressions()) {
                    if (left != right) {
                        Set<OWLClassExpression> cur = CollectionFactory.createSet(left, right);
                        if (!rendered.contains(cur)) {
                            rendered.add(cur);
                            left.accept(this);
                            writeSpace();
                            write(EQUIV);
                            writeSpace();
                            right.accept(this);
                        }

                    }
                }
            }
        }
        else if (axiom.getClassExpressions().size() == 2) {
            Iterator<OWLClassExpression> it = axiom.getClassExpressions().iterator();
            OWLClassExpression descA = it.next();
            OWLClassExpression descB = it.next();
            OWLClassExpression lhs;
            OWLClassExpression rhs;
            if (subject.equals(descA)) {
                lhs = descA;
                rhs = descB;
            }
            else {
                lhs = descB;
                rhs = descA;
            }
            lhs.accept(this);
            writeSpace();
            write(EQUIV);
            writeSpace();
            rhs.accept(this);
        }
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        this.setPrettyPrint(false);
        axiom.getSubClass().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperClass().accept(this);
        writeSpace();
        this.setPrettyPrint(true);
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        writeSpace();
        write(":");
        writeSpace();
        axiom.getClassExpression().accept(this);
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
    }


    public void visit(OWLDeclarationAxiom axiom) {
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        for (Iterator<OWLIndividual> it = axiom.getIndividuals().iterator(); it.hasNext();) {
            write("\\{");
            it.next().accept(this);
            write("\\}");
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        for (Iterator<OWLDataPropertyExpression> it = axiom.getProperties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        for (Iterator<OWLDataPropertyExpression> it = axiom.getProperties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(NOT_EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getProperties().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLDataMaxCardinality(1, axiom.getProperty()).accept(this);
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLObjectMaxCardinality(1, axiom.getProperty()).accept(this);
    }


    public void visit(OWLImportsDeclaration axiom) {
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        OWLObjectPropertyExpression prop = df.getOWLObjectInverseOf(axiom.getProperty());
        df.getOWLObjectMaxCardinality(1, prop).accept(this);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        write(axiom.getFirstProperty());
        writeSpace();
        write(EQUIV);
        writeSpace();
        write(axiom.getSecondProperty());
        write("\\ensuremath{^-}");
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        write(NOT);
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        write(NOT);
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
        write("(");
        axiom.getSubject().accept(this);
        write(", ");
        axiom.getObject().accept(this);
        write(")");
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getPropertyChain().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(CIRC);
                writeSpace();
            }
        }
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        df.getOWLObjectSomeValuesFrom(axiom.getProperty(), df.getOWLThing()).accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getDomain().accept(this);
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        df.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange()).accept(this);
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty();
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        for (Iterator<OWLIndividual> it = axiom.getIndividuals().iterator(); it.hasNext();) {
            write("\\{");
            it.next().accept(this);
            write("\\}");
            if (it.hasNext()) {
                writeSpace();
                write(EQUIV);
                writeSpace();
            }
        }
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(EQUIV);
        writeSpace();
        axiom.getProperty().accept(this);
        write("\\ensuremath{^-}");
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }


    public void visit(SWRLRule rule) {
    }

    public void visit(SWRLVariable node) {
    }

    private void writeNested(OWLClassExpression classExpression) {
        openBracket(classExpression);
        classExpression.accept(this);
        closeBracket(classExpression);
    }


    private void openBracket(OWLClassExpression classExpression) {
        if (LatexBracketChecker.requiresBracket(classExpression)) {
            write("(");
        }
    }


    private void closeBracket(OWLClassExpression classExpression) {
        if (LatexBracketChecker.requiresBracket(classExpression)) {
            write(")");
        }
    }


    private String escapeName(String name) {
        return name.replace("_", "\\_");
    }


    public void visit(OWLOntology ontology) {
    }


    public void visit(OWLObjectInverseOf property) {
        property.getInverse().accept(this);
        write("\\ensuremath{^-}");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLDataComplementOf node) {
    }


    public void visit(OWLDataOneOf node) {
    }


    public void visit(OWLFacetRestriction node) {
    }


    public void visit(OWLDatatypeRestriction node) {
    }


    public void visit(OWLDatatype node) {
    }


    public void visit(OWLLiteral node) {
    }


    //////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(SWRLLiteralArgument node) {
    }


    public void visit(SWRLIndividualArgument node) {
    }


    public void visit(SWRLBuiltInAtom node) {
    }


    public void visit(SWRLClassAtom node) {
    }


    public void visit(SWRLDataRangeAtom node) {
    }


    public void visit(SWRLDataPropertyAtom node) {
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
    }


    public void visit(SWRLObjectPropertyAtom node) {
    }


    public void visit(SWRLSameIndividualAtom node) {
    }

    public void visit(OWLAnnotationProperty property) {
    }

    public void visit(OWLAnnotation annotation) {
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
    }

    public void visit(OWLAnnotationValue value) {
    }

    public void visit(OWLHasKeyAxiom axiom) {
    }

    public void visit(OWLDataIntersectionOf node) {
    }

    public void visit(OWLDataUnionOf node) {
    }

    public void visit(OWLAnonymousIndividual individual) {
    }

    public void visit(IRI iri) {
    }
}
