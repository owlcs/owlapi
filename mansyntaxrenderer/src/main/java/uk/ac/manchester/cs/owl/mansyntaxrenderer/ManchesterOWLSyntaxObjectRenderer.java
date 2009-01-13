package uk.ac.manchester.cs.owl.mansyntaxrenderer;

import org.coode.manchesterowlsyntax.ManchesterOWLSyntax;
import static org.coode.manchesterowlsyntax.ManchesterOWLSyntax.*;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.XSDVocabulary;

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


    public ManchesterOWLSyntaxObjectRenderer(Writer writer) {
        super(writer);
    }

    protected List<? extends OWLObject> sort(Collection<? extends OWLObject> objects) {
        List<? extends OWLObject> sortedDescriptions = new ArrayList<OWLObject>(objects);
        Collections.sort(sortedDescriptions);
        return sortedDescriptions;
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
//                if (lastWasNamed && desc instanceof OWLRestriction) {
//                    write("", THAT, " ");
//                }
//                else {
                write("", AND, " ");
//                }
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
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write("(");
        }
        restriction.getFiller().accept(this);
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write(")");
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
    // Class descriptions
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
                write("", OR, " ");
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


    public void visit(OWLObjectAllRestriction desc) {
        writeRestriction(desc, ONLY);
    }


    public void visit(OWLObjectValueRestriction desc) {
        writeRestriction(desc);
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        writeRestriction(desc, MIN);
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        writeRestriction(desc, EXACTLY);
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        writeRestriction(desc, MAX);
    }


    public void visit(OWLObjectSelfRestriction desc) {
        desc.getProperty().accept(this);
        write(SOME);
        write(SELF);
    }


    public void visit(OWLObjectOneOf desc) {
        write("{");
        write(desc.getIndividuals(), ONE_OF_DELIMETER, false);
        write("}");
    }


    public void visit(OWLDataSomeRestriction desc) {
        writeRestriction(desc, SOME);
    }


    public void visit(OWLDataAllRestriction desc) {
        writeRestriction(desc, ONLY);
    }


    public void visit(OWLDataValueRestriction desc) {
        writeRestriction(desc);
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        writeRestriction(desc, MIN);
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        writeRestriction(desc, EXACTLY);
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
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


    public void visit(OWLIndividual individual) {
        write(getShortFormProvider().getShortForm(individual));
    }


    public void visit(OWLDatatype datatype) {
        write(datatype.getURI().getFragment());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLDataComplementOf node) {
        write(NOT);
        node.getDataRange().accept(this);
    }


    public void visit(OWLDataOneOf node) {
        write("{");
        write(node.getValues(), ONE_OF_DELIMETER, false);
        write("}");
    }


    public void visit(OWLDataRangeRestriction node) {
        node.getDataRange().accept(this);
        write("[");
        write(node.getFacetRestrictions(), FACET_RESTRICTION_SEPARATOR, false);
        write("]");
    }


    public void visit(OWLTypedLiteral node) {
        if (node.getDataType().getURI().equals(XSDVocabulary.DOUBLE.getURI())) {
            write(node.getString());
        } else if (node.getDataType().getURI().equals(XSDVocabulary.FLOAT.getURI())) {
            write(node.getString());
            write("f");
        } else if (node.getDataType().getURI().equals(XSDVocabulary.INTEGER.getURI())) {
            write(node.getString());
        } else {
            write("\"");
            pushTab(getIndent());
//            write(node.getString(), wrap ? LINE_LENGTH : Integer.MAX_VALUE);
            write(node.getString());
            popTab();
            write("\"^^");
            write(node.getDataType().getURI());
        }
    }


    public void visit(OWLRDFTextLiteral node) {
        write("\"");
        pushTab(getIndent());
        write(node.getString());
//        write(node.getString(), wrap ? LINE_LENGTH : Integer.MAX_VALUE);
        popTab();
        write("\"");
        write("@");
        write(node.getLang());
    }


    public void visit(OWLDataRangeFacetRestriction node) {
        write(node.getFacet().getSymbolicForm());
        writeSpace();
        node.getFacetValue().accept(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Property expression stuff
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLObjectPropertyInverse property) {
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


    public void visit(OWLObjectAnnotation annotation) {
        write(annotation.getAnnotationURI());
        writeSpace();
        annotation.getAnnotationValue().accept(this);
    }


    public void visit(OWLConstantAnnotation annotation) {
        write(annotation.getAnnotationURI());
        writeSpace();
        annotation.getAnnotationValue().accept(this);
    }

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

    public void visit(OWLSubClassAxiom axiom) {
        setAxiomWriting();
        setUseTabbing(false);
        writeFrameKeyword(CLASS);
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


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writePropertyCharacteristic(ANTI_SYMMETRIC);
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
        writeCommaSeparatedList(axiom.getDescriptions());
        restore();
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getProperty());
        writeSectionKeyword(DOMAIN);
        axiom.getDomain().accept(this);
        restore();
    }


    public void visit(OWLImportsDeclaration axiom) {
        setAxiomWriting();
        writeFrameKeyword(ONTOLOGY);
        write("<");
        write(axiom.getSubject().getURI().toString());
        write(">");
        writeSectionKeyword(IMPORT);
        write("<");
        write(axiom.getImportedOntologyURI().toString());
        write(">");
        restore();
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
        setAxiomWriting();
        restore();
    }


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


    public void visit(OWLObjectSubPropertyAxiom axiom) {
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
        writeCommaSeparatedList(axiom.getDescriptions());
        restore();
    }

    private void writeFrameType(OWLObject object) {
        setAxiomWriting();
        if (object instanceof OWLOntology) {
            writeFrameKeyword(ONTOLOGY);
            write("<");
            write(((OWLOntology) object).getURI().toString());
            write(">");
        } else {
            if (object instanceof OWLClassExpression) {
                writeFrameKeyword(CLASS);
            } else if (object instanceof OWLObjectPropertyExpression) {
                writeFrameKeyword(OBJECT_PROPERTY);
            } else if (object instanceof OWLDataPropertyExpression) {
                writeFrameKeyword(DATA_PROPERTY);
            } else if (object instanceof OWLIndividual) {
                writeFrameKeyword(INDIVIDUAL);
            }
        }
        object.accept(this);
    }

    public void visit(OWLDeclarationAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getEntity());
        restore();
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSubject());
        writeSectionKeyword(ANNOTATIONS);
        axiom.getAnnotation().accept(this);
        restore();
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getSubject());
        writeSectionKeyword(ANNOTATIONS);
        axiom.getAnnotation().accept(this);
        restore();
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
        axiom.getDescription().accept(this);
        restore();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(EQUIVALENT_CLASSES);
        writeCommaSeparatedList(axiom.getDescriptions());
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


    public void visit(OWLDataSubPropertyAxiom axiom) {
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


    public void visit(OWLSameIndividualsAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(SAME_INDIVIDUAL);
        writeCommaSeparatedList(axiom.getIndividuals());
        restore();
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
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

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Ontology
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLOntology ontology) {

    }
}
