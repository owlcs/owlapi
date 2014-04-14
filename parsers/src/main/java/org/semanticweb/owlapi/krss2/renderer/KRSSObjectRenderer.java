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
import static org.semanticweb.owlapi.model.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.Searcher.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
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
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.search.Filters;
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
 * <td><i>OWLDifferentIndividualsAxiom i1 i2 ... in</i> will be splitted into:<br>
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

    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String NEWLINE = "\n";
    protected final OWLOntology ontology;
    protected final Writer writer;
    private int pos = 0;
    private int lastNewLinePos = 0;

    /**
     * @param ontology
     *        ontology
     * @param writer
     *        writer
     */
    public KRSSObjectRenderer(OWLOntology ontology, Writer writer) {
        this.ontology = ontology;
        this.writer = new PrintWriter(writer);
    }

    protected <T extends OWLObject> List<T> sort(Collection<T> objects) {
        List<T> sortedDescriptions = new ArrayList<T>(objects);
        Collections.sort(sortedDescriptions);
        return sortedDescriptions;
    }

    protected <T extends OWLObject> List<T> sort(Iterable<T> objects) {
        List<T> sortedDescriptions = new ArrayList<T>();
        for (T t : objects) {
            sortedDescriptions.add(t);
        }
        Collections.sort(sortedDescriptions);
        return sortedDescriptions;
    }

    protected final void writeOpenBracket() {
        write(OPEN_BRACKET);
    }

    protected final void writeCloseBracket() {
        write(CLOSE_BRACKET);
    }

    protected final void write(int i) {
        write(" " + i);
    }

    protected final void write(IRI iri) {
        write(iri.toString());
    }

    protected void write(KRSSVocabulary v) {
        write(v.toString());
    }

    protected final void writeSpace() {
        write(" ");
    }

    protected final void write(String s) {
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

    protected final int getIndent() {
        return pos - lastNewLinePos - 1;
    }

    protected final void writeIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            writeSpace();
        }
    }

    protected final void writeln() {
        write(NEWLINE);
    }

    protected final void write(OWLClassExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    protected final void write(OWLIndividual ind) {
        writeSpace();
        ind.accept(this);
    }

    protected final void write(OWLPropertyExpression obj) {
        writeSpace();
        obj.accept(this);
    }

    protected final void write(OWLDataRange obj) {
        writeSpace();
        obj.accept(this);
    }

    protected void flattenProperties(
            Iterable<OWLObjectPropertyExpression> properties,
            KRSSVocabulary junctor) {
        List<OWLObjectPropertyExpression> props = sort(properties);
        final int size = props.size();
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
        final int indent = getIndent();
        for (int i = 1; i < size; i++) {
            writeln();
            writeIndent(indent);
            write(props.get(i));
        }
        if (junctor != null) {
            writeCloseBracket();
        }
    }

    protected void flatten(Iterable<OWLClassExpression> description,
            KRSSVocabulary junctor) {
        List<OWLClassExpression> descs = sort(description);
        final int size = descs.size();
        if (size == 0) {
            return;
        }
        write(descs.get(0));
        if (size == 1) {
            return;
        }
        writeOpenBracket();
        write(junctor);
        final int indent = getIndent();
        for (int i = 1; i < size; i++) {
            writeln();
            writeIndent(indent);
            write(descs.get(i));
        }
        writeCloseBracket();
    }

    @Override
    public void visit(OWLOntology ontology1) {
        Set<OWLClass> classes = ontology1.getClassesInSignature();
        classes.remove(ontology1.getOWLOntologyManager().getOWLDataFactory()
                .getOWLThing());
        classes.remove(ontology1.getOWLOntologyManager().getOWLDataFactory()
                .getOWLNothing());
        for (final OWLClass eachClass : sort(classes)) {
            final boolean primitive = !isDefined(ontology1, eachClass);
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                Iterable<OWLClassExpression> supclasses = sup(
                        ontology1.getSubClassAxiomsForSubClass(eachClass),
                        OWLClassExpression.class);
                flatten(supclasses, KRSSVocabulary.AND);
                writeCloseBracket();
                writeln();
            } else {
                writeOpenBracket();
                write(DEFINE_CONCEPT);
                write(eachClass);
                Iterable<OWLClassExpression> equivalentClasses = equivalent(ontology1
                        .getEquivalentClassesAxioms(eachClass));
                flatten(equivalentClasses, KRSSVocabulary.AND);
                writeCloseBracket();
                writeln();
            }
        }
        for (final OWLObjectProperty property : sort(ontology1
                .getObjectPropertiesInSignature())) {
            writeOpenBracket();
            Collection<OWLObjectPropertyExpression> properties = equivalent(ontology1
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
                Collection<OWLAxiom> axioms = ontology1.filterAxioms(
                        Filters.subObjectPropertyWithSub, property, INCLUDED);
                properties = sup(axioms, OWLObjectPropertyExpression.class);
                if (!properties.isEmpty()) {
                    write(properties.iterator().next());
                }
            }
            writeCloseBracket();
            writeln();
        }
        for (final OWLAxiom axiom : ontology1.getAxioms()) {
            axiom.accept(this);
        }
        try {
            writer.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
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
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeOpenBracket();
        write(DOMAIN);
        write(axiom.getProperty());
        write(axiom.getDomain());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
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
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeOpenBracket();
        write(RANGE);
        write(axiom.getProperty());
        write(axiom.getRange());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writeOpenBracket();
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getProperty());
        write(axiom.getObject());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        writeOpenBracket();
        write(INSTANCE);
        write(axiom.getIndividual());
        write(axiom.getClassExpression());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writeOpenBracket();
        write(TRANSITIVE);
        write(axiom.getProperty());
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        final List<OWLIndividual> individuals = sort(axiom.getIndividuals());
        final int size = individuals.size();
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
    public void visit(OWLClass desc) {
        write(desc.getIRI());
    }

    @Override
    public void visit(OWLObjectIntersectionOf desc) {
        writeOpenBracket();
        write(AND);
        final List<OWLClassExpression> operands = sort(desc.getOperands());
        final int size = operands.size();
        if (size > 0) {
            final int indent = getIndent();
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
    public void visit(OWLObjectUnionOf desc) {
        writeOpenBracket();
        write(OR);
        final List<OWLClassExpression> operands = sort(desc.getOperands());
        final int size = operands.size();
        if (size > 0) {
            final int indent = getIndent();
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
    public void visit(OWLObjectComplementOf desc) {
        writeOpenBracket();
        write(NOT);
        write(desc.getOperand());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectMinCardinality desc) {
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
    public void visit(OWLObjectExactCardinality desc) {
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
    public void visit(OWLObjectMaxCardinality desc) {
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
    public void visit(OWLDataSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataMinCardinality desc) {
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
    public void visit(OWLDataExactCardinality desc) {
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
    public void visit(OWLDataMaxCardinality desc) {
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
    public void visit(OWLLiteral node) {
        write(node.getLiteral());
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
