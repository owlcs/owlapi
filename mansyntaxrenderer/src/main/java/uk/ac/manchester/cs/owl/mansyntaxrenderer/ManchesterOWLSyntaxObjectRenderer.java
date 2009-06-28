package uk.ac.manchester.cs.owl.mansyntaxrenderer;

import org.coode.manchesterowlsyntax.ManchesterOWLSyntax;
import static org.coode.manchesterowlsyntax.ManchesterOWLSyntax.*;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.ShortFormProvider;

import java.io.Writer;
import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Apr-2007<br><br>
 */
public class ManchesterOWLSyntaxObjectRenderer extends AbstractRenderer implements OWLObjectVisitor {

    public static final int LINE_LENGTH = 70;


    public ManchesterOWLSyntaxObjectRenderer(Writer writer, ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
    }

    protected List<? extends OWLObject> sort(Collection<? extends OWLObject> objects) {
        List<? extends OWLObject> sortedObjects = new ArrayList<OWLObject>(objects);
        Collections.sort(sortedObjects);
        return sortedObjects;
    }


    protected void write(Set<? extends OWLObject> objects, ManchesterOWLSyntax delimeter, boolean newline) {
        int tab = getIndent();
        pushTab(tab);
        for (Iterator<? extends OWLObject> it = sort(objects).iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                if (newline && isUseWrapping()) {
                    writeNewLine();
                }
                write(delimeter);
            }
        }
        popTab();
    }

    protected void writeCommaSeparatedList(Set<? extends OWLObject> objects) {
        for (Iterator<OWLObject> it = new TreeSet<OWLObject>(objects).iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(", ");
            }
        }
    }

    protected void write(Set<? extends OWLClassExpression> objects, boolean newline) {
        boolean lastWasNamed = false;
        boolean first = true;

        for (Iterator<? extends OWLObject> it = sort(objects).iterator(); it.hasNext();) {
            OWLObject desc = it.next();
            if (!first) {
                if (newline && isUseWrapping()) {
                    writeNewLine();
                }
                write(" ", AND, " ");
            }

            first = false;
            if (desc instanceof OWLAnonymousClassExpression) {
                write("(");
            }
            desc.accept(this);
            if (desc instanceof OWLAnonymousClassExpression) {
                write(")");
            }

            lastWasNamed = desc instanceof OWLClass;
        }
    }


    private void writeRestriction(OWLQuantifiedRestriction restriction, ManchesterOWLSyntax keyword) {
        restriction.getProperty().accept(this);
        write(keyword);
        boolean conjunctionOrDisjunction = false;
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            if (restriction.getFiller() instanceof OWLObjectIntersectionOf || restriction.getFiller() instanceof OWLObjectUnionOf) {
                conjunctionOrDisjunction = true;
                incrementTab(4);
                writeNewLine();
            }
            write("(");
        }
        restriction.getFiller().accept(this);
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write(")");
            if (conjunctionOrDisjunction) {
                popTab();
            }
        }
    }


    private void writeRestriction(OWLValueRestriction restriction) {
        restriction.getProperty().accept(this);
        write(VALUE);
        restriction.getValue().accept(this);
    }


    private void writeRestriction(OWLCardinalityRestriction restriction, ManchesterOWLSyntax keyword) {
        restriction.getProperty().accept(this);
        write(keyword);
        write(Integer.toString(restriction.getCardinality()));
//        if(restriction.isQualified()) {
        writeSpace();
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write("(");
        }
        restriction.getFiller().accept(this);
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write(")");
        }
//        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Class expressions
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLClass desc) {
        write(getShortFormProvider().getShortForm(desc));
    }


    public void visit(OWLObjectIntersectionOf desc) {
        write(desc.getOperands(), true);
    }


    public void visit(OWLObjectUnionOf desc) {
        boolean first = true;
        for (Iterator<? extends OWLClassExpression> it = desc.getOperands().iterator(); it.hasNext();) {
            OWLClassExpression op = it.next();
            if (!first) {
                if (isUseWrapping()) {
                    writeNewLine();
                }
                write(" ", OR, " ");
            }

            first = false;
            if (op.isAnonymous()) {
                write("(");
            }
            op.accept(this);
            if (op.isAnonymous()) {
                write(")");
            }

        }
    }


    public void visit(OWLObjectComplementOf desc) {
        write("", NOT, desc.isAnonymous() ? " " : "");
        if (desc.isAnonymous()) {
            write("(");
        }
        desc.getOperand().accept(this);
        if (desc.isAnonymous()) {
            write(")");
        }
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        writeRestriction(desc, SOME);
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        writeRestriction(desc, ONLY);
    }


    public void visit(OWLObjectHasValue desc) {
        writeRestriction(desc);
    }


    public void visit(OWLObjectMinCardinality desc) {
        writeRestriction(desc, MIN);
    }


    public void visit(OWLObjectExactCardinality desc) {
        writeRestriction(desc, EXACTLY);
    }


    public void visit(OWLObjectMaxCardinality desc) {
        writeRestriction(desc, MAX);
    }


    public void visit(OWLObjectHasSelf desc) {
        desc.getProperty().accept(this);
        write(SOME);
        write(SELF);
    }


    public void visit(OWLObjectOneOf desc) {
        write("{");
        write(desc.getIndividuals(), ONE_OF_DELIMETER, false);
        write("}");
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        writeRestriction(desc, SOME);
    }


    public void visit(OWLDataAllValuesFrom desc) {
        writeRestriction(desc, ONLY);
    }


    public void visit(OWLDataHasValue desc) {
        writeRestriction(desc);
    }


    public void visit(OWLDataMinCardinality desc) {
        writeRestriction(desc, MIN);
    }


    public void visit(OWLDataExactCardinality desc) {
        writeRestriction(desc, EXACTLY);
    }


    public void visit(OWLDataMaxCardinality desc) {
        writeRestriction(desc, MAX);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Entities stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLObjectProperty property) {
        write(getShortFormProvider().getShortForm(property));
    }


    public void visit(OWLDataProperty property) {
        write(getShortFormProvider().getShortForm(property));
    }


    public void visit(OWLNamedIndividual individual) {
        write(getShortFormProvider().getShortForm(individual));
    }

    public void visit(OWLAnnotationProperty property) {
        write(getShortFormProvider().getShortForm(property));
    }

    public void visit(OWLDatatype datatype) {
        write(getShortFormProvider().getShortForm(datatype));
    }

    public void visit(OWLAnonymousIndividual individual) {
        write(individual.toString());
    }

    public void visit(IRI iri) {
        write(iri.toURI());
    }

    public void visit(OWLAnnotation node) {
        node.getProperty().accept(this);
        writeSpace();
        node.getValue().accept(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLDataComplementOf node) {
        write(NOT);
        if(node.getDataRange().isDatatype()) {
            node.getDataRange().accept(this);
        }
        else {
            write("(");
            node.getDataRange().accept(this);
            write(")");
        }

    }


    public void visit(OWLDataOneOf node) {
        write("{");
        write(node.getValues(), ONE_OF_DELIMETER, false);
        write("}");
    }


    public void visit(OWLDataIntersectionOf node) {
        write("(");
        write(node.getOperands(), AND, false);
        write(")");
    }

    public void visit(OWLDataUnionOf node) {
        write("(");
        write(node.getOperands(), OR, false);
        write(")");
    }

    public void visit(OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        write("[");
        write(node.getFacetRestrictions(), FACET_RESTRICTION_SEPARATOR, false);
        write("]");
    }


    public void visit(OWLTypedLiteral node) {
        if (node.getDatatype().isDouble()) {
            write(node.getLiteral());
        }
        else if (node.getDatatype().isFloat()) {
            write(node.getLiteral());
            write("f");
        }
        else if (node.getDatatype().isInteger()) {
            write(node.getLiteral());
        }
        else if (node.getDatatype().isString()) {
            writeLiteral(node.getLiteral());
        }
        else {
            pushTab(getIndent());
            writeLiteral(node.getLiteral());
            popTab();
            write("^^");
            write(node.getDatatype().getURI());
        }
    }


    public void visit(OWLStringLiteral node) {
        pushTab(getIndent());
        writeLiteral(node.getLiteral());
        if (node.getLang() != null) {
            write("@");
            write(node.getLang());
        }
        popTab();
    }

    private void writeLiteral(String literal) {
        write("\"");
        if (literal.indexOf("\"") == -1 && literal.indexOf("\\") != -1) {
            write(literal);
        }
        else {

            literal = literal.replace("\\", "\\\\");
            literal = literal.replace("\"", "\\\"");
            write(literal);
        }
        write("\"");
//        if(literal.indexOf('\"') != -1) {
//            write("\"\"\"");
//            write(literal);
//            write("\"\"\"");
//        }
//        else {
//            write("\"");
//            write(literal);
//            write("\"");
//        }
    }


    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getSymbolicForm());
        writeSpace();
        node.getFacetValue().accept(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Property expression stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLObjectInverseOf property) {
        write(INVERSE);
        write("(");
        property.getInverse().accept(this);
        write(")");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Annotation stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Stand alone axiom representation
    //
    // We render each axiom as a one line frame
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private boolean wrapSave;

    private boolean tabSave;

    private void setAxiomWriting() {
        wrapSave = isUseWrapping();
        tabSave = isUseTabbing();
        setUseWrapping(false);
        setUseTabbing(false);
    }

    private void restore() {
        setUseTabbing(tabSave);
        setUseWrapping(wrapSave);
    }

    public void visit(OWLSubClassOfAxiom axiom) {
        setAxiomWriting();
        setUseTabbing(false);
//        writeFrameKeyword(CLASS);
        axiom.getSubClass().accept(this);
        writeSectionKeyword(SUBCLASS_OF);
        axiom.getSuperClass().accept(this);
        restore();
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSubject());
        writeSectionKeyword(FACTS);
        write(NOT);
        write("(");
        axiom.getSubject().accept(this);
        write(" ");
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getObject().accept(this);
        write(")");
        restore();
    }


    private void writePropertyCharacteristic(ManchesterOWLSyntax characteristic) {
        writeSectionKeyword(CHARACTERISTICS);
        write(characteristic);
        restore();
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(ASYMMETRIC);
        restore();
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(REFLEXIVE);
        restore();
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(DISJOINT_CLASSES);
        writeCommaSeparatedList(axiom.getClassExpressions());
        restore();
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(DOMAIN);
        axiom.getDomain().accept(this);
        restore();
    }


//    public void visit(OWLImportsDeclaration axiom) {
//        setAxiomWriting();
//        writeFrameKeyword(ONTOLOGY);
//        write("<");
//        write(axiom.getSubject().getIRI().toString());
//        write(">");
//        writeSectionKeyword(IMPORT);
//        write("<");
//        write(axiom.getIRI().toString());
//        write(">");
//        restore();
//    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(DOMAIN);
        axiom.getDomain().accept(this);
        restore();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(EQUIVALENT_OBJECT_PROPERTIES);
        writeCommaSeparatedList(axiom.getProperties());
        restore();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(FACTS);
        write(NOT);
        write("(");
        axiom.getSubject().accept(this);
        write(" ");
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getObject().accept(this);
        write(")");
        restore();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(DIFFERENT_INDIVIDUALS);
        writeCommaSeparatedList(axiom.getIndividuals());
        restore();
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(DISJOINT_DATA_PROPERTIES);
        writeCommaSeparatedList(axiom.getProperties());
        restore();
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(DISJOINT_OBJECT_PROPERTIES);
        writeCommaSeparatedList(axiom.getProperties());
        restore();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(RANGE);
        axiom.getRange().accept(this);
        restore();
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(FACTS);
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getObject().accept(this);
        restore();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(FUNCTIONAL);
        restore();
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSubProperty());
        writeSectionKeyword(SUB_PROPERTY_OF);
        axiom.getSuperProperty().accept(this);
        restore();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getOWLClass());
        writeSectionKeyword(DISJOINT_UNION_OF);
        writeCommaSeparatedList(axiom.getClassExpressions());
        restore();
    }

    private void writeFrameType(OWLObject object) {
        setAxiomWriting();
        if (object instanceof OWLOntology) {
            writeFrameKeyword(ONTOLOGY);
            OWLOntology ont = (OWLOntology) object;
            if (!ont.isAnonymous()) {
                write("<");
                write(ont.getOntologyID().getOntologyIRI().toString());
                write(">");
            }
        }
        else {
            if (object instanceof OWLClassExpression) {
                writeFrameKeyword(CLASS);
            }
            else if (object instanceof OWLObjectPropertyExpression) {
                writeFrameKeyword(OBJECT_PROPERTY);
            }
            else if (object instanceof OWLDataPropertyExpression) {
                writeFrameKeyword(DATA_PROPERTY);
            }
            else if (object instanceof OWLIndividual) {
                writeFrameKeyword(INDIVIDUAL);
            }
            else if (object instanceof OWLAnnotationProperty) {
                writeFrameKeyword(ANNOTATION_PROPERTY);
            }
        }
        object.accept(this);
    }

    public void visit(OWLDeclarationAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getEntity());
        restore();
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSubject());
        writeSectionKeyword(ANNOTATIONS);
        axiom.getAnnotation().accept(this);
        restore();
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(DOMAIN);
        axiom.getDomain().accept(this);
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(RANGE);
        axiom.getRange().accept(this);
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSubProperty());
        writeFrameKeyword(SUB_PROPERTY_OF);
        axiom.getSuperProperty().accept(this);
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(SYMMETRIC);
        restore();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(RANGE);
        axiom.getRange().accept(this);
        restore();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(FUNCTIONAL);
        restore();
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(EQUIVALENT_DATA_PROPERTIES);
        writeCommaSeparatedList(axiom.getProperties());
        restore();
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getIndividual());
        writeSectionKeyword(TYPES);
        axiom.getClassExpression().accept(this);
        restore();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        setAxiomWriting();
        if (axiom.getClassExpressions().size() == 2) {
            OWLClassExpression[] ces = axiom.getClassExpressions().toArray(new OWLClassExpression[2]);
            ces[0].accept(this);
            writeSpace();
            writeFrameKeyword(EQUIVALENT_CLASSES);
            writeSpace();
            ces[1].accept(this);
        }
        else {
            writeFrameKeyword(EQUIVALENT_CLASSES);
            writeCommaSeparatedList(axiom.getClassExpressions());
        }
        restore();
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSubject());
        writeSectionKeyword(FACTS);
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getObject().accept(this);
        restore();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(TRANSITIVE);
        restore();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(IRREFLEXIVE);
        restore();
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSubProperty());
        writeSectionKeyword(SUB_PROPERTY_OF);
        axiom.getSuperProperty().accept(this);
        restore();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(INVERSE_FUNCTIONAL);
        restore();
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(SAME_INDIVIDUAL);
        writeCommaSeparatedList(axiom.getIndividuals());
        restore();
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSuperProperty());
        writeSectionKeyword(SUB_PROPERTY_CHAIN);
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getPropertyChain().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(" o ");
            }
        }
        restore();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getFirstProperty());
        writeSectionKeyword(INVERSE_OF);
        axiom.getSecondProperty().accept(this);
        restore();
    }


    public void visit(SWRLRule rule) {
        setAxiomWriting();
        for (Iterator<SWRLAtom> it = rule.getBody().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(", ");
            }
        }
        write(" -> ");
        for (Iterator<SWRLAtom> it = rule.getHead().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(", ");
            }
        }
        restore();
    }

    public void visit(OWLHasKeyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getClassExpression());
        write(HAS_KEY);
        write(axiom.getObjectPropertyExpressions(), COMMA, false);
        write(axiom.getDataPropertyExpressions(), COMMA, false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // SWRL
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


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


    public void visit(SWRLDataValuedPropertyAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }


    public void visit(SWRLBuiltInAtom node) {
        write(node.getPredicate().getShortName());
        write("(");
        for (Iterator<SWRLAtomDObject> it = node.getArguments().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(", ");
            }
        }
        write(")");
    }


    public void visit(SWRLAtomDVariable node) {
        write("?");
        write(node.getURI().getFragment());
    }


    public void visit(SWRLAtomIVariable node) {
        write("?");
        write(node.getURI().getFragment());
    }


    public void visit(SWRLAtomIndividualObject node) {
        node.getIndividual().accept(this);
    }


    public void visit(SWRLAtomConstantObject node) {
        node.getConstant().accept(this);
    }


    public void visit(SWRLSameAsAtom node) {
        write(SAME_AS);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }


    public void visit(SWRLDifferentFromAtom node) {
        write(DIFFERENT_FROM);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Ontology
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLOntology ontology) {

    }
}
