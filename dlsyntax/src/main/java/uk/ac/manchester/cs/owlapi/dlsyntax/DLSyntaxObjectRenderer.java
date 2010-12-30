package uk.ac.manchester.cs.owlapi.dlsyntax;

import static uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntax.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.IRIShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Feb-2008<br><br>
 * <p/>
 * Renders objects in unicode DL syntax
 */
public class DLSyntaxObjectRenderer implements OWLObjectRenderer, OWLObjectVisitor {

    private ShortFormProvider shortFormProvider;

    private IRIShortFormProvider iriShortFormProvider;

    private StringBuilder buffer;

    private OWLObject focusedObject;

    public DLSyntaxObjectRenderer() {
        this.shortFormProvider = new SimpleShortFormProvider();
        this.iriShortFormProvider = new SimpleIRIShortFormProvider();
        this.buffer = new StringBuilder();
    }


    public void setFocusedObject(OWLObject focusedObject) {
        this.focusedObject = focusedObject;
    }

    public boolean isFocusedObject(OWLObject obj) {
        if (focusedObject == null) {
            return false;
        }
        return focusedObject.equals(obj);
    }


    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

    public String render(OWLObject object) {
        buffer = new StringBuilder();
        object.accept(this);
        return buffer.toString();
    }


    public void visit(OWLOntology ontology) {
        for (OWLAxiom ax : new TreeSet<OWLAxiom>(ontology.getLogicalAxioms())) {
            ax.accept(this);
            write("\n");
        }
    }

    protected void write(String s) {
        buffer.append(s);
    }

    protected String renderEntity(OWLEntity entity) {
        return shortFormProvider.getShortForm(entity);
    }

    protected void writeEntity(OWLEntity entity) {
        write(renderEntity(entity));
    }

    protected void write(DLSyntax keyword) {
        write(keyword.toString());
    }

    protected void write(int i) {
        write(Integer.toString(i));
    }

    protected void writeNested(OWLObject object) {
        if (isBracketedIfNested(object)) {
            write("(");
        }
        object.accept(this);
        if (isBracketedIfNested(object)) {
            write(")");
        }
    }


    protected boolean isBracketedIfNested(OWLObject object) {
//        if(object instanceof OWLObjectComplementOf) {
//            if(!((OWLObjectComplementOf) object).getOperand().isAnonymous()) {
//                return false;
//            }
//        }
//        return object instanceof OWLClassExpression && !((OWLClassExpression) object).isClassExpressionLiteral();
        return !(object instanceof OWLEntity);
    }

    private void writeObject(OWLObject object, boolean nest) {
        if (nest) {
            writeNested(object);
        } else {
            object.accept(this);
        }
    }

    protected void write(Collection<? extends OWLObject> objects, DLSyntax delim, boolean nest) {
        if (objects.size() == 2) {
            Iterator<? extends OWLObject> it = objects.iterator();
            OWLObject o1 = it.next();
            OWLObject o2 = it.next();
            if (isFocusedObject(o1) || !isFocusedObject(o2)) {
                writeObject(o1, nest);
                writeSpace();
                write(delim);
                writeSpace();
                writeObject(o2, nest);
            } else {
                writeObject(o2, nest);
                writeSpace();
                write(delim);
                writeSpace();
                writeObject(o1, nest);
            }
        } else {
            for (Iterator<? extends OWLObject> it = objects.iterator(); it.hasNext();) {
                OWLObject o = it.next();
                writeObject(o, nest);
                if (it.hasNext()) {
                    writeSpace();
                    write(delim);
                    writeSpace();
                }
            }
        }

    }

//    protected void write(Collection<? extends OWLObject> objects, DLSyntax keyword, boolean nest) {
//        write(objects, keyword, nest);
//    }


    public void visit(OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperClass().accept(this);
    }

    private void writePropertyAssertion(OWLPropertyAssertionAxiom ax) {
        if (ax instanceof OWLNegativeObjectPropertyAssertionAxiom || ax instanceof OWLNegativeDataPropertyAssertionAxiom) {
            write(NOT);
        }
        ax.getProperty().accept(this);
        write("(");
        ax.getSubject().accept(this);
        write(", ");
        ax.getObject().accept(this);
        write(")");
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        write(NOT);
        writePropertyAssertion(axiom);

    }

    @SuppressWarnings("unused")
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(EXISTS);
        writeSpace();
        axiom.getProperty().accept(this);
        write(" .");
        write(SELF);
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> descs = new ArrayList<OWLClassExpression>(axiom.getClassExpressions());
        for (int i = 0; i < descs.size(); i++) {
            for (int j = i + 1; j < descs.size(); j++) {
                descs.get(i).accept(this);
                writeSpace();
                write(DISJOINT_WITH);
                writeSpace();
                descs.get(j).accept(this);
                if (j < descs.size() - 1) {
                    write(", ");
                }
            }
        }
//        write(axiom.getClassExpressions(), DISJOINT_WITH, true);
    }

    private void writeDomainAxiom(OWLPropertyDomainAxiom axiom) {
        write(EXISTS);
        writeSpace();
        axiom.getProperty().accept(this);
        writeRestrictionSeparator();
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        writeNested(axiom.getDomain());
    }


    private void writeRestrictionSeparator() {
        write(".");
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writeDomainAxiom(axiom);
    }

    @SuppressWarnings("unused")
    public void visit(OWLImportsDeclaration axiom) {

    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeDomainAxiom(axiom);
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        write(axiom.getProperties(), EQUIVALENT_TO, false);
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        write(NOT);
        writePropertyAssertion(axiom);
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        write(axiom.getIndividuals(), NOT_EQUAL, false);
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        write(axiom.getProperties(), DISJOINT_WITH, false);
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        write(axiom.getProperties(), DISJOINT_WITH, false);
    }

    private void writeRangeAxiom(OWLPropertyRangeAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(FORALL);
        writeSpace();
        axiom.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(axiom.getRange());
    }

    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeRangeAxiom(axiom);
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writePropertyAssertion(axiom);
    }

    private void writeFunctionalProperty(OWLPropertyExpression property) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(MAX);
        writeSpace();
        write(1);
        writeSpace();
        property.accept(this);
    }

    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writeFunctionalProperty(axiom.getProperty());
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept(this);
        write(EQUAL);
        write(axiom.getClassExpressions(), OR, false);
    }

    @SuppressWarnings("unused")
    public void visit(OWLDeclarationAxiom axiom) {
    }

    @SuppressWarnings("unused")
    public void visit(OWLAnnotationAssertionAxiom axiom) {
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(EQUIVALENT_TO);
        writeSpace();
        axiom.getProperty().accept(this);
        write(INVERSE);
    }


    private void writeSpace() {
        write(" ");
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writeRangeAxiom(axiom);
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writeFunctionalProperty(axiom.getProperty());
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        write(axiom.getProperties(), EQUIVALENT_TO, false);
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        if(axiom.getClassExpression().isAnonymous()) {
            write("(");
        }
        axiom.getClassExpression().accept(this);
        if(axiom.getClassExpression().isAnonymous()) {
            write(")");
        }
        write("(");
        axiom.getIndividual().accept(this);
        write(")");
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        write(axiom.getClassExpressions(), EQUIVALENT_TO, false);
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writePropertyAssertion(axiom);
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(IN);
        writeSpace();
        write("R");
        write("\u207A");
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(NOT);
        write(EXISTS);
        writeSpace();
        axiom.getProperty().accept(this);
        write(" .");
        write(SELF);
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        write(SUBCLASS);
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        write(TOP);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        write(MAX);
        writeSpace();
        write(1);
        writeSpace();
        axiom.getProperty().accept(this);
        write(INVERSE);
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        write(axiom.getIndividuals(), EQUAL, false);
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        write(axiom.getPropertyChain(), COMP, false);
        writeSpace();
        write(SUBCLASS);
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        OWLObject o1 = axiom.getFirstProperty();
        OWLObject o2 = axiom.getSecondProperty();

        OWLObject first, second;
        if (isFocusedObject(o1) || !isFocusedObject(o2)) {
            first = o1;
            second = o2;
        } else {
            first = o2;
            second = o1;
        }

        first.accept(this);
        writeSpace();
        write(EQUIVALENT_TO);
        writeSpace();
        second.accept(this);
        write(INVERSE);
    }


    public void visit(SWRLRule rule) {
        write(rule.getHead(), WEDGE, false);
        writeSpace();
        write(IMPLIES);
        writeSpace();
        write(rule.getBody(), WEDGE, false);

    }


    public void visit(OWLClass desc) {
        if (desc.isOWLThing()) {
            write(TOP);
        } else if (desc.isOWLNothing()) {
            write(BOTTOM);
        } else {
            writeEntity(desc);
        }
    }


    public void visit(OWLObjectIntersectionOf desc) {
        write(desc.getOperands(), AND, true);
    }


    public void visit(OWLObjectUnionOf desc) {
        write(desc.getOperands(), OR, true);
    }


    public void visit(OWLObjectComplementOf desc) {
        write(NOT);
        writeNested(desc.getOperand());
    }

    private void writeCardinalityRestriction(OWLDataCardinalityRestriction restriction, DLSyntax keyword) {
        write(keyword);
        writeSpace();
        write(restriction.getCardinality());
        writeSpace();
        restriction.getProperty().accept(this);
//        if (restriction.isQualified()) {
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
//        }
    }
    
    private void writeCardinalityRestriction(OWLObjectCardinalityRestriction restriction, DLSyntax keyword) {
        write(keyword);
        writeSpace();
        write(restriction.getCardinality());
        writeSpace();
        restriction.getProperty().accept(this);
//        if (restriction.isQualified()) {
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
//        }
    }

    private void writeQuantifiedRestriction(OWLQuantifiedDataRestriction restriction, DLSyntax keyword) {
        write(keyword);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }

    private void writeQuantifiedRestriction(OWLQuantifiedObjectRestriction restriction, DLSyntax keyword) {
        write(keyword);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        writeNested(restriction.getFiller());
    }
    
    public void visit(OWLObjectSomeValuesFrom desc) {
        writeQuantifiedRestriction(desc, EXISTS);

    }


    public void visit(OWLObjectAllValuesFrom desc) {
        writeQuantifiedRestriction(desc, FORALL);
    }


    private <R extends OWLPropertyRange, P extends OWLPropertyExpression<R, P>, V extends OWLObject> void writeValueRestriction(OWLHasValueRestriction<R, P, V> restriction) {
        write(EXISTS);
        writeSpace();
        restriction.getProperty().accept(this);
        writeRestrictionSeparator();
        write("{");
        restriction.getValue().accept(this);
        write("}");
    }

    public void visit(OWLObjectHasValue desc) {
        writeValueRestriction(desc);
    }


    public void visit(OWLObjectMinCardinality desc) {
        writeCardinalityRestriction(desc, MIN);
    }


    public void visit(OWLObjectExactCardinality desc) {
        writeCardinalityRestriction(desc, EQUAL);
    }


    public void visit(OWLObjectMaxCardinality desc) {
        writeCardinalityRestriction(desc, MAX);
    }


    public void visit(OWLObjectHasSelf desc) {
        write(EXISTS);
        writeSpace();
        desc.getProperty().accept(this);
        write(" .");
        write(SELF);
    }


    public void visit(OWLObjectOneOf desc) {
        for (Iterator<OWLIndividual> it = desc.getIndividuals().iterator(); it.hasNext();) {
            write("{");
            it.next().accept(this);
            write("}");
            if (it.hasNext()) {
                write(" ");
                write(OR);
                write(" ");
            }
        }

    }


    public void visit(OWLDataSomeValuesFrom desc) {
        writeQuantifiedRestriction(desc, EXISTS);
    }


    public void visit(OWLDataAllValuesFrom desc) {
        writeQuantifiedRestriction(desc, FORALL);
    }


    public void visit(OWLDataHasValue desc) {
        writeValueRestriction(desc);
    }


    public void visit(OWLDataMinCardinality desc) {
        writeCardinalityRestriction(desc, MIN);
    }


    public void visit(OWLDataExactCardinality desc) {
        writeCardinalityRestriction(desc, EQUAL);
    }


    public void visit(OWLDataMaxCardinality desc) {
        writeCardinalityRestriction(desc, MAX);
    }


    public void visit(OWLDatatype node) {
        write(shortFormProvider.getShortForm(node));
    }


    public void visit(OWLDataComplementOf node) {
        write(NOT);
        node.getDataRange().accept(this);

    }


    public void visit(OWLDataOneOf node) {
        for (Iterator<OWLLiteral> it = node.getValues().iterator(); it.hasNext();) {
            write("{");
            it.next().accept(this);
            write("}");
            if (it.hasNext()) {
                write(OR);
            }
        }
    }

    @SuppressWarnings("unused")
    public void visit(OWLDatatypeRestriction node) {

    }

    public void visit(OWLLiteral node) {
        write(node.getLiteral());
    }

    @SuppressWarnings("unused")
    public void visit(OWLFacetRestriction node) {
    }


    public void visit(OWLObjectProperty property) {
        writeEntity(property);
    }


    public void visit(OWLObjectInverseOf property) {
        property.getInverse().accept(this);
        write(INVERSE);
    }


    public void visit(OWLDataProperty property) {
        writeEntity(property);
    }


    public void visit(OWLNamedIndividual individual) {
        writeEntity(individual);
    }

    @SuppressWarnings("unused")
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
    }

    @SuppressWarnings("unused")
    public void visit(OWLHasKeyAxiom axiom) {
    }
    @SuppressWarnings("unused")
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
    }
    @SuppressWarnings("unused")
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
    }
    @SuppressWarnings("unused")
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
    }
    @SuppressWarnings("unused")
    public void visit(OWLDataIntersectionOf node) {
    }
    @SuppressWarnings("unused")
    public void visit(OWLDataUnionOf node) {
    }
    @SuppressWarnings("unused")
    public void visit(OWLAnnotationProperty property) {
    }
    @SuppressWarnings("unused")
    public void visit(OWLAnonymousIndividual individual) {
    }
    @SuppressWarnings("unused")
    public void visit(IRI iri) {
    }
    @SuppressWarnings("unused")
    public void visit(OWLAnnotation node) {
    }

    public void visit(SWRLClassAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getArgument().accept(this);
        write(")");
    }


    public void visit(SWRLDataRangeAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getArgument().accept(this);
        write(")");
    }


    public void visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }


    public void visit(SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }


    public void visit(SWRLBuiltInAtom node) {
        write(node.getPredicate().toString());
        write("(");
        write(node.getArguments(), COMMA, true);
        write(")");
    }


    public void visit(SWRLVariable node) {
        write("?");
        write(iriShortFormProvider.getShortForm(node.getIRI()));
    }

    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }


    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }


    public void visit(SWRLSameIndividualAtom node) {
        write("sameAs(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");

    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        write("differentFrom(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }
}
