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

import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.isDefined;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;

/**
 * A {@code KRSSObjectRenderer} renderes an OWLOntology in the original KRSS
 * syntax. Note that only a subset of OWL can be expressed in KRSS. <br>
 * <b>Abbreviations</b>
 * <table summary="abbreviations">
 * <tr>
 * <td>CN</td>
 * <td>concept name</td>
 * </tr>
 * <tr>
 * <td>C,D,E</td>
 * <td>concept expression</td>
 * </tr>
 * <tr>
 * <td>RN</td>
 * <td>role name</td>
 * </tr>
 * <tr>
 * <td>R, R1, R2,...</td>
 * <td>role expressions, i.e. role name or inverse role</td>
 * </tr>
 * </table>
 * <br>
 * <b>KRSS concept language</b>
 * <table summary="krss concept language">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLClassExpression</td>
 * </tr>
 * <tr>
 * <td>(at-least n R C)</td>
 * <td>(OWLObjectMinCardinality R n C)</td>
 * </tr>
 * <tr>
 * <td>(at-most n R C)</td>
 * <td>(OWLObjectMaxCardinality R n C)</td>
 * </tr>
 * <tr>
 * <td>(exactly n R C)</td>
 * <td>(OWLObjectExactCardinality R n C)</td>
 * </tr>
 * <tr>
 * <td>(some R C)</td>
 * <td>(OWLObjectSomeValuesFrom R C)</td>
 * </tr>
 * <tr>
 * <td>(all R C)</td>
 * <td>(OWLObjectAllValuesFrom R C)</td>
 * </tr>
 * <tr>
 * <td>(not C)</td>
 * <td>(OWLObjectComplementOf C)</td>
 * </tr>
 * <tr>
 * <td>(and C D E)</td>
 * <td>(OWLObjectIntersectionOf C D E)</td>
 * </tr>
 * <tr>
 * <td>(or C D E)</td>
 * <td>(OWLObjectUnionOf C D E)</td>
 * </tr>
 * </table>
 * <br>
 * <b>KRSS role language</b>
 * <table summary="krss role language">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLObjectPropertyExpression</td>
 * </tr>
 * <tr>
 * <td>(inv R)</td>
 * <td>(OWLInverseObjectPropertiesAxiom R)</td>
 * </tr>
 * </table>
 * <br>
 * Each referenced class, object property as well as individual is defined using
 * <i>define-concept</i> resp. <i>define-primitive-concept</i>,
 * <i>define-role</i> and <i>define-individual</i>. In addition, axioms are
 * translated as follows. <br>
 * <table summary="remarks">
 * <tr>
 * <td>OWLAxiom</td>
 * <td>KRSS syntax</td>
 * <td>Remarks</td>
 * </tr>
 * <tr>
 * <td>OWLEquivalentClasses</td>
 * <td>(define-concept C D)</td>
 * <td><i>OWLEquivalentClasses C D1 D2...Dn</i> will be translated to:<br>
 * (define-concept C (and D1 D2...Dn)) <br>
 * Only applicable if there is no OWLSubClassOf axiom. <br>
 * </td>
 * </tr>
 * <tr>
 * <td>OWLDisjointClassesAxiom</td>
 * <td>(disjoint C D)</td>
 * <td>multiple pairwise disjoint statements are added in case of more than 2
 * disjoint expressions</td>
 * </tr>
 * <tr>
 * <td>OWLSubClassOf</td>
 * <td>(define-primitive-concept C D)</td>
 * <td>Multiple OWLSubClassOf axioms for C will be combined: <br>
 * (define-primitive-concept C (and D1...Dn)) <br>
 * Only applicable if there is no OWLEquivalentClasses axiom.<br>
 * KRSS does not allow both define-concept C and define-primitive-concept C.
 * GCIs not supported in KRSS (see KRSS2)</td>
 * </tr>
 * <tr>
 * <td>OWLEquivalentObjectPropertiesAxiom</td>
 * <td>(define-role R S)</td>
 * <td>Only applicable if the is no OWLSubObjectPropertyOf for R and the number
 * of the involved properties must be two</td>
 * </tr>
 * <tr>
 * <td>OWLObjectPropertyDomainAxiom</td>
 * <td>(domain P D)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>OWLObjectPropertyRangeAxiom</td>
 * <td>(range P D)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>OWLSubObjectPropertyOf</td>
 * <td>(define-primitive-role R S)</td>
 * <td>Only applicable if the is no OWLEquivalentObjectPropertiesAxiom for R and
 * only one OWLSubObjectPropertyOf axiom for a given property is allowed. If
 * there are more one is randomly chosen.</td>
 * </tr>
 * <tr>
 * <td>OWLTransitiveObjectPropertyAxiom</td>
 * <td>(transitive P)</td>
 * </tr>
 * <tr>
 * <td>OWLClassAssertionAxiom</td>
 * <td>(instance i D)</td>
 * </tr>
 * <tr>
 * <td>OWLDifferentIndividualsAxiom</td>
 * <td>(distinct i1 i2)</td>
 * <td><i>OWLDifferentIndividualsAxiom i1 i2 ... in</i> will be splitted into:
 * <br>
 * { (distinct i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k}</td>
 * </tr>
 * <tr>
 * <td>OWLObjectPropertyAssertionAxiom</td>
 * <td>(related i1 P i2)</td>
 * <td>i1: subject, i2: object</td>
 * </tr>
 * <tr>
 * <td>OWLSameIndividualsAxiom</td>
 * <td>(equal i1 i2)</td>
 * <td><i>OWLSameIndividual i1 i2 ...i(n-1)</i> in will be splitted into:<br>
 * { (equal i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k} <br>
 * </td>
 * </tr>
 * </table>
 * 
 * @author Olaf Noppens, Ulm University, Institute of Artificial Intelligence
 */
public class KRSSObjectRenderer extends OWLObjectVisitorAdapter {

    @Nonnull
    private static final String OPEN_BRACKET = "(";
    @Nonnull
    private static final String CLOSE_BRACKET = ")";
    @Nonnull
    private static final String NEWLINE = "\n";
    @Nonnull
    protected final OWLOntology ont;
    @Nonnull
    protected final Writer writer;
    private int pos = 0;
    private int lastNewLinePos = 0;

    /**
     * @param ontology
     *        ontology
     * @param writer
     *        writer
     */
    public KRSSObjectRenderer(@Nonnull OWLOntology ontology, @Nonnull Writer writer) {
        ont = checkNotNull(ontology);
        this.writer = new PrintWriter(writer);
    }

    @Nonnull
    protected static <T extends OWLObject> List<T> sort(@Nonnull Collection<T> objects) {
        return CollectionFactory.sortOptionally(objects);
    }

    @Nonnull
    protected static <T extends OWLObject> List<T> sort(@Nonnull Iterable<T> objects) {
        Collection<T> sortedDescriptions = new ArrayList<>();
        for (T t : objects) {
            sortedDescriptions.add(t);
        }
        return CollectionFactory.sortOptionally(sortedDescriptions);
    }

    protected void writeOpenBracket() {
        write(OPEN_BRACKET);
    }

    protected void writeCloseBracket() {
        write(CLOSE_BRACKET);
    }

    protected void write(int i) {
        write(" " + i);
    }

    protected void write(@Nonnull IRI iri) {
        write(iri.toString());
    }

    protected void write(@Nonnull KRSSVocabulary v) {
        write(v.toString());
    }

    protected void writeSpace() {
        write(" ");
    }

    protected void write(@Nonnull String s) {
        try {
            int newLineIndex = s.indexOf('\n');
            if (newLineIndex != -1) {
                lastNewLinePos = pos + newLineIndex;
            }
            pos += s.length();
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected int getIndent() {
        return pos - lastNewLinePos - 1;
    }

    protected void writeIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            writeSpace();
        }
    }

    protected void writeln() {
        write(NEWLINE);
    }

    protected void write(@Nonnull OWLClassExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    protected void write(@Nonnull OWLIndividual ind) {
        writeSpace();
        ind.accept(this);
    }

    protected void write(@Nonnull OWLPropertyExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    protected void write(@Nonnull OWLDataRange obj) {
        writeSpace();
        obj.accept(this);
    }

    protected void flattenProperties(@Nonnull Iterable<OWLObjectPropertyExpression> properties,
        @Nullable KRSSVocabulary junctor) {
        List<OWLObjectPropertyExpression> props = sort(properties);
        int size = props.size();
        if (size == 0) {
            return;
        }
        if (size == 1) {
            write(properties.iterator().next());
            return;
        }
        if (junctor != null) {
            writeOpenBracket();
            write(junctor);
        }
        write(props.get(0));
        int indent = getIndent();
        for (int i = 1; i < size; i++) {
            writeln();
            writeIndent(indent);
            write(props.get(i));
        }
        if (junctor != null) {
            writeCloseBracket();
        }
    }

    protected void flatten(@Nonnull Iterable<OWLClassExpression> description, @Nonnull KRSSVocabulary junctor) {
        List<OWLClassExpression> descs = sort(description);
        int size = descs.size();
        if (size == 0) {
            return;
        }
        write(descs.get(0));
        if (size == 1) {
            return;
        }
        writeOpenBracket();
        write(junctor);
        int indent = getIndent();
        for (int i = 1; i < size; i++) {
            writeln();
            writeIndent(indent);
            write(descs.get(i));
        }
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLOntology ontology) {
        Set<OWLClass> classes = ontology.getClassesInSignature();
        classes.remove(ontology.getOWLOntologyManager().getOWLDataFactory().getOWLThing());
        classes.remove(ontology.getOWLOntologyManager().getOWLDataFactory().getOWLNothing());
        for (OWLClass eachClass : sort(classes)) {
            boolean primitive = !isDefined(eachClass, ontology);
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                Iterable<OWLClassExpression> supclasses = sup(ontology.getSubClassAxiomsForSubClass(eachClass),
                    OWLClassExpression.class);
                flatten(supclasses, AND);
                writeCloseBracket();
                writeln();
            } else {
                writeOpenBracket();
                write(DEFINE_CONCEPT);
                write(eachClass);
                Iterable<OWLClassExpression> equivalentClasses = equivalent(ontology.getEquivalentClassesAxioms(
                    eachClass));
                flatten(equivalentClasses, AND);
                writeCloseBracket();
                writeln();
            }
        }
        for (OWLObjectProperty property : sort(ontology.getObjectPropertiesInSignature())) {
            writeOpenBracket();
            Collection<OWLObjectPropertyExpression> properties = equivalent(ontology
                .getEquivalentObjectPropertiesAxioms(property));
            boolean isDefined = !properties.isEmpty();
            if (isDefined) {
                write(DEFINE_ROLE);
                write(property);
                // choose randomly one property (KRSS restriction)
                properties.remove(property);
                if (!properties.isEmpty()) {
                    write(properties.iterator().next());
                }
            } else {
                write(DEFINE_PRIMITIVE_ROLE);
                write(property);
                writeSpace();
                Collection<OWLAxiom> axioms = ontology.filterAxioms(Filters.subObjectPropertyWithSub, property,
                    INCLUDED);
                properties = sup(axioms, OWLObjectPropertyExpression.class);
                if (!properties.isEmpty()) {
                    write(properties.iterator().next());
                }
            }
            writeCloseBracket();
            writeln();
        }
        for (OWLAxiom axiom : ontology.getAxioms()) {
            axiom.accept(this);
        }
        try {
            writer.flush();
        } catch (IOException io) {
            throw new OWLRuntimeException(io);
        }
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> classes = sort(axiom.getClassExpressions());
        int size = classes.size();
        if (size <= 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(DISJOINT);
                write(classes.get(i));
                write(classes.get(j));
                writeCloseBracket();
                writeln();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        writeOpenBracket();
        write(DOMAIN);
        write(axiom.getProperty());
        write(axiom.getDomain());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        List<OWLIndividual> individuals = sort(axiom.getIndividuals());
        int size = individuals.size();
        if (size <= 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(DISTINCT);
                write(individuals.get(i));
                write(individuals.get(j));
                writeCloseBracket();
                writeln();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        writeOpenBracket();
        write(RANGE);
        write(axiom.getProperty());
        write(axiom.getRange());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        writeOpenBracket();
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getProperty());
        write(axiom.getObject());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        writeOpenBracket();
        write(INSTANCE);
        write(axiom.getIndividual());
        write(axiom.getClassExpression());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        writeOpenBracket();
        write(TRANSITIVE);
        write(axiom.getProperty());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        List<OWLIndividual> individuals = sort(axiom.getIndividuals());
        int size = individuals.size();
        if (size <= 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(EQUAL);
                write(individuals.get(i));
                write(individuals.get(j));
                writeCloseBracket();
                writeln();
            }
        }
    }

    @Override
    public void visit(@Nonnull OWLClass ce) {
        write(ce.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        writeOpenBracket();
        write(AND);
        List<OWLClassExpression> operands = sort(ce.getOperands());
        int size = operands.size();
        if (size > 0) {
            int indent = getIndent();
            write(operands.get(0));
            for (int i = 1; i < size; i++) {
                writeln();
                writeIndent(indent);
                write(operands.get(i));
            }
        }
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf ce) {
        writeOpenBracket();
        write(OR);
        List<OWLClassExpression> operands = sort(ce.getOperands());
        int size = operands.size();
        if (size > 0) {
            int indent = getIndent();
            write(operands.get(0));
            for (int i = 1; i < size; i++) {
                writeln();
                writeIndent(indent);
                write(operands.get(i));
            }
        }
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf ce) {
        writeOpenBracket();
        write(NOT);
        write(ce.getOperand());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        writeOpenBracket();
        write(SOME);
        write(ce.getProperty());
        write(ce.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectAllValuesFrom ce) {
        writeOpenBracket();
        write(ALL);
        write(ce.getProperty());
        write(ce.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality ce) {
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
    public void visit(@Nonnull OWLObjectExactCardinality ce) {
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
    public void visit(@Nonnull OWLObjectMaxCardinality ce) {
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
    public void visit(@Nonnull OWLDataSomeValuesFrom ce) {
        writeOpenBracket();
        write(SOME);
        write(ce.getProperty());
        write(ce.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDataAllValuesFrom ce) {
        writeOpenBracket();
        write(ALL);
        write(ce.getProperty());
        write(ce.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality ce) {
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
    public void visit(@Nonnull OWLDataExactCardinality ce) {
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
    public void visit(@Nonnull OWLDataMaxCardinality ce) {
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
    public void visit(@Nonnull OWLLiteral node) {
        write(node.getLiteral());
    }

    @Override
    public void visit(@Nonnull OWLObjectProperty property) {
        write(property.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        writeOpenBracket();
        write(INVERSE);
        writeSpace();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDataProperty property) {
        write(property.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLNamedIndividual individual) {
        write(individual.getIRI());
    }
}
