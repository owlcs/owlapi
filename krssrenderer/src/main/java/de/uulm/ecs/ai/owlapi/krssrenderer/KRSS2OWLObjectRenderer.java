package de.uulm.ecs.ai.owlapi.krssrenderer;

import static de.uulm.ecs.ai.owlapi.krssrenderer.KRSS2Vocabulary.*;
import org.semanticweb.owlapi.model.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;
/*
 * Copyright (C) 2007, Ulm University
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
 * @author Olaf Noppens
 */
public class KRSS2OWLObjectRenderer implements OWLObjectVisitor {
    private final OWLOntology ontology;
    private final Writer writer;

    public KRSS2OWLObjectRenderer(OWLOntology ontology, Writer writer) {
        this.ontology = ontology;
        this.writer = writer;
    }

    private void writeOpenBracket() {
        try {
            writer.write("(");
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void writeCloseBracket() {
        try {
            writer.write(")");
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(int i) {
        try {
            writer.write(" " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(IRI iri) {
        try {
            writer.write(iri.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAttribute(KRSS2Vocabulary v) {
        try {
            writeSpace();
            writer.write(":");
            writer.write(v.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(KRSS2Vocabulary v) {
        try {
            writer.write(v.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSpace() {
        try {
            writer.write(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeln() {
        try {
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public final void write(OWLClassExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    public final void write(OWLIndividual ind) {
        writeSpace();
        ind.accept(this);
    }

    public final void write(OWLPropertyExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    public final void write(OWLDataRange obj) {
        writeSpace();
        obj.accept(this);
    }


    private final void flatten(Set<OWLClassExpression> classExpressions) {
        if (classExpressions.isEmpty()) return;
        final OWLClassExpression desc = classExpressions.iterator().next();
        if (classExpressions.size() == 1) {
            write(desc);
            return;
        }
        classExpressions.remove(desc);
        writeOpenBracket();
        write(AND);
        write(desc);
        flatten(classExpressions);
        writeCloseBracket();
    }

    public final void visit(OWLOntology ontology) {
        for (final OWLClass eachClass : ontology.getClassesInSignature()) {
            final boolean primitive = !eachClass.isDefined(ontology);//!eachClass.getSuperClasses(ontology).isEmpty();
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                Set<OWLClassExpression> superClasses = eachClass.getSuperClasses(ontology);
                if (superClasses.size() == 1) {
                    write(superClasses.iterator().next());
                } else {
                    flatten(superClasses);
                }
                writeCloseBracket(); //==> end definition of primitive-concept
                writeln();
                for (OWLClassExpression classExpression : eachClass.getEquivalentClasses(ontology)) {
                    writeOpenBracket();
                    write(eachClass);
                    write(EQUIVALENT);
                    writeSpace();
                    classExpression.accept(this);
                    writeCloseBracket();
                    writeln();
                }
            } else {
                writeOpenBracket();
                write(DEFINE_CONCEPT);
                write(eachClass);
                Set<OWLClassExpression> equivalentClasses = eachClass.getEquivalentClasses(ontology);
                if (equivalentClasses.isEmpty()) {
                    //?
                    writeCloseBracket();
                    writeln();
                } else if (equivalentClasses.size() == 1) {
                    write(equivalentClasses.iterator().next());
                    writeCloseBracket();
                    writeln();
                } else {
                    Iterator<OWLClassExpression> iter = equivalentClasses.iterator();
                    write(iter.next());
                    writeCloseBracket();
                    writeln();
                    for (; iter.hasNext();) {
                        writeOpenBracket();
                        write(EQUIVALENT);
                        write(eachClass);
                        writeSpace();
                        iter.next().accept(this);
                        writeCloseBracket();
                        writeln();
                    }
                }

            }
        }
        for (final OWLClassAxiom axiom : ontology.getGeneralClassAxioms()) {
            axiom.accept(this);
        }

        for (final OWLObjectProperty property : ontology.getObjectPropertiesInSignature()) {
            writeOpenBracket();
            write(DEFINE_PRIMITIVE_ROLE);
            write(property);

            if (property.isTransitive(ontology)) {
                writeAttribute(TRANSITIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (property.isSymmetric(ontology)) {
                writeAttribute(SYMMETRIC_ATTR);
                writeSpace();
                write(TRUE);
            }
            final Set<OWLClassExpression> domains = property.getDomains(ontology);
            if (!domains.isEmpty()) {
                writeAttribute(DOMAIN);
                flatten(domains);
            }
            final Set<OWLClassExpression> ranges = property.getDomains(ontology);
            if (!ranges.isEmpty()) {
                writeAttribute(RANGE_ATTR);
                flatten(ranges);
            }
            final Set<OWLObjectPropertyExpression> superProperties = property.getSuperProperties(ontology);
            if (!superProperties.isEmpty()) {
                writeAttribute(PARENTS_ATTR);
                writeOpenBracket();
                for (OWLObjectPropertyExpression express : superProperties) {
                    write(express);
                }
                writeCloseBracket();
            }

            writeCloseBracket();
        }
        try {
            writer.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public final void visit(OWLSubClassOfAxiom axiom) {
        writeOpenBracket();
        write(IMPLIES);
        write(axiom.getSubClass());
        write(axiom.getSuperClass());
        writeCloseBracket();
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }

    public final void visit(OWLDisjointClassesAxiom axiom) {
        writeOpenBracket();
        for (final OWLClassExpression desc : axiom.getClassExpressions()) {
            write(desc);
        }
        writeCloseBracket();
    }

    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }

    public void visit(OWLImportsDeclaration axiom) {
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
    }

    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
    }

    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
    }

    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
    }

    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
    }

    public void visit(OWLObjectPropertyRangeAxiom axiom) {
    }

    public final void visit(OWLObjectPropertyAssertionAxiom axiom) {
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getObject());
        write(axiom.getProperty());
        writeln();
    }

    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
    }

    public void visit(OWLDisjointUnionAxiom axiom) {
    }

    public void visit(OWLDeclarationAxiom axiom) {
    }

    public void visit(OWLAnnotationAssertionAxiom axiom) {
    }

    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }

    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
    }

    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
    }

    public final void visit(OWLClassAssertionAxiom axiom) {
        write(INSTANCE);
        write(axiom.getIndividual());
        write(axiom.getClassExpression());
        writeln();
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
    }

    public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSubDataPropertyOfAxiom axiom) {
    }

    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSameIndividualAxiom axiom) {
    }

    public void visit(OWLSubPropertyChainOfAxiom axiom) {
    }

    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
    }

    public void visit(SWRLRule rule) {
    }

    public void visit(SWRLClassAtom node) {
    }

    public void visit(SWRLDataRangeAtom node) {
    }

    public void visit(SWRLObjectPropertyAtom node) {
    }

    public void visit(SWRLDataPropertyAtom node) {
    }

    public void visit(SWRLBuiltInAtom node) {
    }

    public void visit(SWRLVariable node) {
    }

    public void visit(SWRLIndividualArgument node) {
    }

    public void visit(SWRLLiteralArgument node) {
    }

    public void visit(SWRLSameIndividualAtom node) {
    }

    public void visit(SWRLDifferentIndividualsAtom node) {
    }

    public final void visit(OWLClass desc) {
        write(desc.getIRI());
    }

    public final void visit(OWLObjectIntersectionOf desc) {
        writeOpenBracket();
        write(AND);
        for (final OWLClassExpression des : desc.getOperands()) {
            write(des);
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectUnionOf desc) {
        writeOpenBracket();
        write(OR);
        for (OWLClassExpression des : desc.getOperands()) {
            write(des);
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectComplementOf desc) {
        writeOpenBracket();
        write(NOT);
        write(desc.getOperand());
        writeCloseBracket();
    }

    public final void visit(OWLObjectSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public final void visit(OWLObjectAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLObjectHasValue desc) {

    }

    public final void visit(OWLObjectMinCardinality desc) {
        writeOpenBracket();
        write(AT_LEAST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectExactCardinality desc) {
        writeOpenBracket();
        write(EXACTLY);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectMaxCardinality desc) {
        writeOpenBracket();
        write(AT_MOST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public void visit(OWLObjectHasSelf desc) {
    }

    public void visit(OWLObjectOneOf desc) {
    }

    public final void visit(OWLDataSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public final void visit(OWLDataAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLDataHasValue desc) {
    }

    public final void visit(OWLDataMinCardinality desc) {
        writeOpenBracket();
        write(AT_LEAST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLDataExactCardinality desc) {
        writeOpenBracket();
        write(EXACTLY);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLDataMaxCardinality desc) {
        writeOpenBracket();
        write(AT_MOST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public void visit(OWLDatatype node) {

    }

    public void visit(OWLDataComplementOf node) {

    }

    public void visit(OWLDataOneOf node) {

    }

    public void visit(OWLDatatypeRestriction node) {

    }

    public void visit(OWLTypedLiteral node) {

    }

    public void visit(OWLStringLiteral node) {

    }

    public void visit(OWLFacetRestriction node) {

    }

    public final void visit(OWLObjectProperty property) {
        write(property.getIRI());
    }

    public final void visit(OWLObjectInverseOf property) {
        writeOpenBracket();
        write(INVERSE);
        writeSpace();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    public final void visit(OWLDataProperty property) {
        write(property.getIRI());
    }

    public final void visit(OWLNamedIndividual individual) {
        write(individual.getIRI());
    }

    public void visit(OWLHasKeyAxiom axiom) {
    }

    public void visit(OWLDataIntersectionOf node) {
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
    }


    public void visit(OWLDataUnionOf node) {
    }

    public void visit(OWLAnnotationProperty property) {
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
    }

    public void visit(OWLAnonymousIndividual individual) {
    }

    public void visit(IRI iri) {
    }

    public void visit(OWLAnnotation node) {
    }
}
