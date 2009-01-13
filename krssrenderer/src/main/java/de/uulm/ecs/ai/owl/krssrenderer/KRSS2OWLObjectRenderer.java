package de.uulm.ecs.ai.owl.krssrenderer;

import static de.uulm.ecs.ai.owl.krssrenderer.KRSS2Vocabulary.*;
import org.semanticweb.owl.model.*;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
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

    private void write(URI uri) {
        try {
            writer.write(uri.toString());
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


    public final void write(OWLDescription obj) {
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


    private final void flatten(Set<OWLDescription> descriptions) {
        if (descriptions.isEmpty()) return;
        final OWLDescription desc = descriptions.iterator().next();
        if (descriptions.size() == 1) {
            write(desc);
            return;
        }
        descriptions.remove(desc);
        writeOpenBracket();
        write(AND);
        write(desc);
        flatten(descriptions);
        writeCloseBracket();
    }

    public final void visit(OWLOntology ontology) {
        for (final OWLClass eachClass : ontology.getReferencedClasses()) {
            final boolean primitive = !eachClass.isDefined(ontology);//!eachClass.getSuperClasses(ontology).isEmpty();
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                Set<OWLDescription> superClasses = eachClass.getSuperClasses(ontology);
                if (superClasses.size() == 1) {
                    write(superClasses.iterator().next());
                } else {
                    flatten(superClasses);
                }
                writeCloseBracket(); //==> end definition of primitive-concept
                writeln();
                for (OWLDescription description : eachClass.getEquivalentClasses(ontology)) {
                    writeOpenBracket();
                    write(eachClass);
                    write(EQUIVALENT);
                    writeSpace();
                    description.accept(this);
                    writeCloseBracket();
                    writeln();
                }
            } else {
                writeOpenBracket();
                write(DEFINE_CONCEPT);
                write(eachClass);
                Set<OWLDescription> equivalentClasses = eachClass.getEquivalentClasses(ontology);
                if (equivalentClasses.isEmpty()) {
                    //?
                    writeCloseBracket();
                    writeln();
                } else if (equivalentClasses.size() == 1) {
                    write(equivalentClasses.iterator().next());
                    writeCloseBracket();
                    writeln();
                } else {
                    Iterator<OWLDescription> iter = equivalentClasses.iterator();
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

        for (final OWLObjectProperty property : ontology.getReferencedObjectProperties()) {
            writeOpenBracket();
            write(DEFINE_PRIMITIVE_ROLE);
            write(property);

            if (property.isTransitive(ontology)) {
                writeAttribute(TRANSITIVE);
                writeSpace();
                write(TRUE);
            }
            if (property.isSymmetric(ontology)) {
                writeAttribute(SYMMETRIC);
                writeSpace();
                write(TRUE);
            }
            final Set<OWLDescription> domains = property.getDomains(ontology);
            if (!domains.isEmpty()) {
                writeAttribute(DOMAIN);
                flatten(domains);
            }
            final Set<OWLDescription> ranges = property.getDomains(ontology);
            if (!ranges.isEmpty()) {
                writeAttribute(RANGE);
                flatten(ranges);
            }
            final Set<OWLObjectPropertyExpression> superProperties = property.getSuperProperties(ontology);
            if (!superProperties.isEmpty()) {
                writeAttribute(PARENTS);
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

    public final void visit(OWLSubClassAxiom axiom) {
        writeOpenBracket();
        write(IMPLIES);
        write(axiom.getSubClass());
        write(axiom.getSuperClass());
        writeCloseBracket();
    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }

    public final void visit(OWLDisjointClassesAxiom axiom) {
        writeOpenBracket();
        for (final OWLDescription desc : axiom.getDescriptions()) {
            write(desc);
        }
        writeCloseBracket();
    }

    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }

    public void visit(OWLImportsDeclaration axiom) {
    }

    public void visit(OWLAxiomAnnotationAxiom axiom) {
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

    public void visit(OWLObjectSubPropertyAxiom axiom) {
    }

    public void visit(OWLDisjointUnionAxiom axiom) {
    }

    public void visit(OWLDeclarationAxiom axiom) {
    }

    public void visit(OWLEntityAnnotationAxiom axiom) {
    }

    public void visit(OWLOntologyAnnotationAxiom axiom) {
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
        write(axiom.getDescription());
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

    public void visit(OWLDataSubPropertyAxiom axiom) {
    }

    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSameIndividualsAxiom axiom) {
    }

    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
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

    public void visit(SWRLDataValuedPropertyAtom node) {
    }

    public void visit(SWRLBuiltInAtom node) {
    }

    public void visit(SWRLAtomDVariable node) {
    }

    public void visit(SWRLAtomIVariable node) {
    }

    public void visit(SWRLAtomIndividualObject node) {
    }

    public void visit(SWRLAtomConstantObject node) {
    }

    public void visit(SWRLSameAsAtom node) {
    }

    public void visit(SWRLDifferentFromAtom node) {
    }

    public final void visit(OWLClass desc) {
        write(desc.getURI());
    }

    public final void visit(OWLObjectIntersectionOf desc) {
        writeOpenBracket();
        write(AND);
        for (final OWLDescription des : desc.getOperands()) {
            write(des);
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectUnionOf desc) {
        writeOpenBracket();
        write(OR);
        for (OWLDescription des : desc.getOperands()) {
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

    public final void visit(OWLObjectSomeRestriction desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public final void visit(OWLObjectAllRestriction desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLObjectValueRestriction desc) {

    }

    public final void visit(OWLObjectMinCardinalityRestriction desc) {
        writeOpenBracket();
        write(AT_LEAST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectExactCardinalityRestriction desc) {
        writeOpenBracket();
        write(EXACTLY);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLObjectMaxCardinalityRestriction desc) {
        writeOpenBracket();
        write(AT_MOST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public void visit(OWLObjectSelfRestriction desc) {
    }

    public void visit(OWLObjectOneOf desc) {
    }

    public final void visit(OWLDataSomeRestriction desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public final void visit(OWLDataAllRestriction desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLDataValueRestriction desc) {
    }

    public final void visit(OWLDataMinCardinalityRestriction desc) {
        writeOpenBracket();
        write(AT_LEAST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLDataExactCardinalityRestriction desc) {
        writeOpenBracket();
        write(EXACTLY);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public final void visit(OWLDataMaxCardinalityRestriction desc) {
        writeOpenBracket();
        write(AT_MOST);
        write(desc.getCardinality());
        write(desc.getProperty());
        if (desc.isQualified()) {
            write(desc.getFiller());
        }
        writeCloseBracket();
    }

    public void visit(OWLDataType node) {

    }

    public void visit(OWLDataComplementOf node) {

    }

    public void visit(OWLDataOneOf node) {

    }

    public void visit(OWLDataRangeRestriction node) {

    }

    public void visit(OWLTypedConstant node) {

    }

    public void visit(OWLUntypedConstant node) {

    }

    public void visit(OWLDataRangeFacetRestriction node) {

    }

    public final void visit(OWLObjectProperty property) {
        write(property.getURI());
    }

    public final void visit(OWLObjectPropertyInverse property) {
        writeOpenBracket();
        write(INVERSE);
        writeSpace();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    public final void visit(OWLDataProperty property) {
        write(property.getURI());
    }

    public final void visit(OWLIndividual individual) {
        write(individual.getURI());
    }

    public void visit(OWLObjectAnnotation annotation) {
    }

    public void visit(OWLConstantAnnotation annotation) {
    }
}
