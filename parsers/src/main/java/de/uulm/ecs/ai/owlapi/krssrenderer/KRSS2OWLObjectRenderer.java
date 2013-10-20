/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, Ulm University
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, Ulm University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.uulm.ecs.ai.owlapi.krssrenderer;

import static de.uulm.ecs.ai.owlapi.krssrenderer.KRSS2Vocabulary.*;
import static org.semanticweb.owlapi.search.Searcher.find;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.search.Searcher;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;

/** @author Olaf Noppens */
public class KRSS2OWLObjectRenderer extends OWLObjectVisitorAdapter {
    private final OWLOntology ontology;
    private final Writer writer;

    /** @param ontology
     * @param writer */
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

    private void write(OWLClassExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    private void write(OWLIndividual ind) {
        writeSpace();
        ind.accept(this);
    }

    private void write(OWLPropertyExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    private void write(OWLDataRange obj) {
        writeSpace();
        obj.accept(this);
    }

    private final void flatten(Iterable<OWLClassExpression> classExpressions) {
        List<OWLClassExpression> list = new ArrayList<OWLClassExpression>();
        for (OWLClassExpression c : classExpressions) {
            list.add(c);
        }
        if (list.isEmpty()) {
            return;
        }
        final OWLClassExpression desc = list.iterator().next();
        if (list.size() == 1) {
            write(desc);
            return;
        }
        list.remove(0);
        writeOpenBracket();
        write(AND);
        write(desc);
        flatten(list);
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLOntology onto) {
        for (final OWLClass eachClass : onto.getClassesInSignature()) {
            final boolean primitive = !find().in(onto).isDefined(eachClass);
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                Searcher<OWLClassExpression> superClasses = find(OWLClassExpression.class)
                        .sup().classes(eachClass).in(onto);
                flatten(superClasses);
                writeCloseBracket(); // ==> end definition of primitive-concept
                writeln();
                Searcher<OWLClassExpression> classes = find(OWLClassExpression.class)
                        .in(onto).equivalent().classes(eachClass);
                for (OWLClassExpression classExpression : classes) {
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
                Searcher<OWLClassExpression> classes = find(OWLClassExpression.class)
                        .in(onto).equivalent().classes(eachClass);
                if (classes.isEmpty()) {
                    // ?
                    writeCloseBracket();
                    writeln();
                } else if (classes.size() == 1) {
                    write(classes.iterator().next());
                    writeCloseBracket();
                    writeln();
                } else {
                    Iterator<OWLClassExpression> iter = classes.iterator();
                    write(iter.next());
                    writeCloseBracket();
                    writeln();
                    while (iter.hasNext()) {
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
        for (final OWLClassAxiom axiom : onto.getGeneralClassAxioms()) {
            axiom.accept(this);
        }
        for (final OWLObjectProperty property : onto.getObjectPropertiesInSignature()) {
            writeOpenBracket();
            write(DEFINE_PRIMITIVE_ROLE);
            write(property);
            if (find().in(onto).isTransitive(property)) {
                writeAttribute(TRANSITIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (find().in(ontology).isSymmetric(property)) {
                writeAttribute(SYMMETRIC_ATTR);
                writeSpace();
                write(TRUE);
            }
            Searcher<OWLClassExpression> domains = find(OWLClassExpression.class)
                    .in(onto).domains(property);
            if (!domains.isEmpty()) {
                writeAttribute(DOMAIN);
                flatten(domains);
            }
            Searcher<OWLClassExpression> ranges = find(OWLClassExpression.class).in(onto)
                    .ranges(property);
            if (!ranges.isEmpty()) {
                writeAttribute(RANGE_ATTR);
                flatten(ranges);
            }
            Searcher<OWLObjectPropertyExpression> superProperties = find(
                    OWLObjectPropertyExpression.class).in(onto).sup()
                    .propertiesOf(property);
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

    @Override
    public final void visit(OWLSubClassOfAxiom axiom) {
        writeOpenBracket();
        write(IMPLIES);
        write(axiom.getSubClass());
        write(axiom.getSuperClass());
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLDisjointClassesAxiom axiom) {
        writeOpenBracket();
        for (final OWLClassExpression desc : axiom.getClassExpressions()) {
            write(desc);
        }
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLObjectPropertyAssertionAxiom axiom) {
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getObject());
        write(axiom.getProperty());
        writeln();
    }

    @Override
    public final void visit(OWLClassAssertionAxiom axiom) {
        write(INSTANCE);
        write(axiom.getIndividual());
        write(axiom.getClassExpression());
        writeln();
    }

    @Override
    public final void visit(OWLClass desc) {
        write(desc.getIRI());
    }

    @Override
    public final void visit(OWLObjectIntersectionOf desc) {
        writeOpenBracket();
        write(AND);
        for (final OWLClassExpression des : desc.getOperands()) {
            write(des);
        }
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLObjectUnionOf desc) {
        writeOpenBracket();
        write(OR);
        for (OWLClassExpression des : desc.getOperands()) {
            write(des);
        }
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLObjectComplementOf desc) {
        writeOpenBracket();
        write(NOT);
        write(desc.getOperand());
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLObjectSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLObjectAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public final void visit(OWLDataSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLDataAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public final void visit(OWLObjectProperty property) {
        write(property.getIRI());
    }

    @Override
    public final void visit(OWLObjectInverseOf property) {
        writeOpenBracket();
        write(INVERSE);
        writeSpace();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public final void visit(OWLDataProperty property) {
        write(property.getIRI());
    }

    @Override
    public final void visit(OWLNamedIndividual individual) {
        write(individual.getIRI());
    }
}
