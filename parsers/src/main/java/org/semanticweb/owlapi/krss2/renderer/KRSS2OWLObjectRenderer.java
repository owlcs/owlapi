/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Ulm University
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.krss2.renderer;

import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.*;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.search.Filters;

/** @author Olaf Noppens */
public class KRSS2OWLObjectRenderer implements OWLObjectVisitor {

    @Nonnull
    private final Writer writer;

    /**
     * @param writer
     *        writer
     */
    public KRSS2OWLObjectRenderer(@Nonnull Writer writer) {
        this.writer = checkNotNull(writer);
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
            throw new OWLRuntimeException(e);
        }
    }

    private void write(@Nonnull IRI iri) {
        try {
            writer.write(iri.toString());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void writeAttribute(KRSS2Vocabulary v) {
        try {
            writeSpace();
            writer.write(":");
            writer.write(v.toString());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(KRSS2Vocabulary v) {
        try {
            writer.write(v.toString());
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void writeSpace() {
        try {
            writer.write(" ");
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void writeln() {
        try {
            writer.write("\n");
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
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

    private void flatten(Iterator<OWLClassExpression> c) {
        if (!c.hasNext()) {
            return;
        }
        OWLClassExpression desc = c.next();
        if (!c.hasNext()) {
            write(desc);
            return;
        }
        writeOpenBracket();
        write(AND);
        write(desc);
        flatten(c);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLOntology ontology) {
        for (OWLClass eachClass : asList(ontology.classesInSignature())) {
            boolean primitive = !isDefined(eachClass, ontology);
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                Stream<OWLClassExpression> superClasses = sup(ontology.axioms(
                        Filters.subClassWithSub, eachClass, INCLUDED),
                        OWLClassExpression.class);
                flatten(superClasses.iterator());
                writeCloseBracket(); // ==> end definition of primitive-concept
                writeln();
                Collection<OWLClassExpression> classes = asList(equivalent(
                        ontology.equivalentClassesAxioms(eachClass),
                        OWLClassExpression.class));
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
                Collection<OWLClassExpression> classes = asList(equivalent(
                        ontology.equivalentClassesAxioms(eachClass),
                        OWLClassExpression.class));
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
        ontology.generalClassAxioms().forEach(a -> a.accept(this));
        for (OWLObjectProperty property : asList(ontology
                .objectPropertiesInSignature())) {
            writeOpenBracket();
            write(DEFINE_PRIMITIVE_ROLE);
            write(property);
            if (isTransitive(property, ontology)) {
                writeAttribute(TRANSITIVE_ATTR);
                writeSpace();
                write(TRUE);
            }
            if (isSymmetric(property, ontology)) {
                writeAttribute(SYMMETRIC_ATTR);
                writeSpace();
                write(TRUE);
            }
            Stream<OWLClassExpression> domains = domain(ontology
                    .objectPropertyDomainAxioms(property));
            Iterator<OWLClassExpression> i = domains.iterator();
            if (i.hasNext()) {
                writeAttribute(DOMAIN);
                flatten(i);
            }
            Stream<OWLClassExpression> ranges = range(ontology
                    .objectPropertyRangeAxioms(property));
            i = ranges.iterator();
            if (i.hasNext()) {
                writeAttribute(RANGE_ATTR);
                flatten(i);
            }
            Stream<OWLObjectPropertyExpression> superProperties = sup(
                    ontology.axioms(Filters.subObjectPropertyWithSub, property,
                            INCLUDED), OWLObjectPropertyExpression.class);
            Iterator<OWLObjectPropertyExpression> it = superProperties
                    .iterator();
            if (it.hasNext()) {
                writeAttribute(PARENTS_ATTR);
                writeOpenBracket();
                while (it.hasNext()) {
                    write(it.next());
                }
                writeCloseBracket();
            }
            writeCloseBracket();
        }
        try {
            writer.flush();
        } catch (IOException io) {
            throw new OWLRuntimeException(io);
        }
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        writeOpenBracket();
        write(IMPLIES);
        write(axiom.getSubClass());
        write(axiom.getSuperClass());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        writeOpenBracket();
        axiom.classExpressions().forEach(desc -> write(desc));
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getObject());
        write(axiom.getProperty());
        writeln();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        write(INSTANCE);
        write(axiom.getIndividual());
        write(axiom.getClassExpression());
        writeln();
    }

    @Override
    public void visit(OWLClass ce) {
        write(ce.getIRI());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        writeOpenBracket();
        write(AND);
        ce.operands().forEach(des -> write(des));
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        writeOpenBracket();
        write(OR);
        ce.operands().forEach(des -> write(des));
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        writeOpenBracket();
        write(NOT);
        write(ce.getOperand());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeOpenBracket();
        write(SOME);
        write(ce.getProperty());
        write(ce.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeOpenBracket();
        write(ALL);
        write(ce.getProperty());
        write(ce.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeOpenBracket();
        write(AT_LEAST);
        write(ce.getCardinality());
        write(ce.getProperty());
        if (ce.isQualified()) {
            write(ce.getFiller());
        }
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeOpenBracket();
        write(EXACTLY);
        write(ce.getCardinality());
        write(ce.getProperty());
        if (ce.isQualified()) {
            write(ce.getFiller());
        }
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writeOpenBracket();
        write(AT_MOST);
        write(ce.getCardinality());
        write(ce.getProperty());
        if (ce.isQualified()) {
            write(ce.getFiller());
        }
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeOpenBracket();
        write(SOME);
        write(ce.getProperty());
        write(ce.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeOpenBracket();
        write(ALL);
        write(ce.getProperty());
        write(ce.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeOpenBracket();
        write(AT_LEAST);
        write(ce.getCardinality());
        write(ce.getProperty());
        if (ce.isQualified()) {
            write(ce.getFiller());
        }
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeOpenBracket();
        write(EXACTLY);
        write(ce.getCardinality());
        write(ce.getProperty());
        if (ce.isQualified()) {
            write(ce.getFiller());
        }
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeOpenBracket();
        write(AT_MOST);
        write(ce.getCardinality());
        write(ce.getProperty());
        if (ce.isQualified()) {
            write(ce.getFiller());
        }
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectProperty property) {
        write(property.getIRI());
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        writeOpenBracket();
        write(INVERSE);
        writeSpace();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataProperty property) {
        write(property.getIRI());
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        write(individual.getIRI());
    }
}
