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

import static org.semanticweb.owlapi.krss2.renderer.KRSS2Vocabulary.IMPLIES;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.ALL;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.AND;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.AT_LEAST;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.AT_MOST;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.DEFINE_CONCEPT;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.DEFINE_PRIMITIVE_CONCEPT;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.DEFINE_PRIMITIVE_ROLE;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.DEFINE_ROLE;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.DISJOINT;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.DISTINCT;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.DOMAIN;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.EQUAL;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.EXACTLY;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.INSTANCE;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.INVERSE;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.NOT;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.OR;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.RANGE;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.RELATED;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.SOME;
import static org.semanticweb.owlapi.krss2.renderer.KRSSVocabulary.TRANSITIVE;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.isDefined;
import static org.semanticweb.owlapi.search.Searcher.equivalent;
import static org.semanticweb.owlapi.search.Searcher.sup;
import static org.semanticweb.owlapi.util.CollectionFactory.sortOptionally;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.pairs;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.search.Filters;
import org.semanticweb.owlapi.util.OWLAPIStreamUtils.Pair;

/**
 * A {@code KRSSObjectRenderer} renderes an OWLOntology in the original KRSS syntax. Note that only
 * a subset of OWL can be expressed in KRSS. <br>
 * <b>Abbreviations</b>
 * <table summary="Abbreviations">
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
 * <table summary="KRSS concept language">
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
 * <table summary="KRSS role language">
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
 * <i>define-concept</i> resp. <i>define-primitive-concept</i>, <i>define-role</i> and
 * <i>define-individual</i>. In addition, axioms are translated as follows. <br>
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
 * <td>multiple pairwise disjoint statements are added in case of more than 2 disjoint
 * expressions</td>
 * </tr>
 * <tr>
 * <td>OWLSubClassOf</td>
 * <td>(define-primitive-concept C D)</td>
 * <td>Multiple OWLSubClassOf axioms for C will be combined: <br>
 * (define-primitive-concept C (and D1...Dn)) <br>
 * Only applicable if there is no OWLEquivalentClasses axiom.<br>
 * KRSS does not allow both define-concept C and define-primitive-concept C. GCIs not supported in
 * KRSS (see KRSS2)</td>
 * </tr>
 * <tr>
 * <td>OWLEquivalentObjectPropertiesAxiom</td>
 * <td>(define-role R S)</td>
 * <td>Only applicable if the is no OWLSubObjectPropertyOf for R and the number of the involved
 * properties must be two</td>
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
 * <td>Only applicable if the is no OWLEquivalentObjectPropertiesAxiom for R and only one
 * OWLSubObjectPropertyOf axiom for a given property is allowed. If there are more one is randomly
 * chosen.</td>
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
 * <td><i>OWLDifferentIndividualsAxiom i1 i2 ... in</i> will be splitted into: <br>
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
public class KRSSObjectRenderer implements OWLObjectVisitor {

    protected static final String CLOSE_BRACKET = ")";
    protected static final String NEWLINE = "\n";
    protected static final String OPEN_BRACKET = "(";
    private int lastNewLinePos = 0;
    protected final OWLOntology ont;
    private int pos = 0;
    protected final PrintWriter writer;

    /**
     * @param ontology ontology
     * @param writer writer
     */
    public KRSSObjectRenderer(OWLOntology ontology, Writer writer) {
        ont = checkNotNull(ontology);
        this.writer = new PrintWriter(writer);
    }

    protected void flatten(List<OWLClassExpression> descs) {
        int size = descs.size();
        if (size == 0) {
            return;
        }
        write(descs.get(0));
        if (size == 1) {
            return;
        }
        writeList(descs.subList(1, size), AND);
    }

    protected void flattenProperties(List<OWLObjectPropertyExpression> props,
        @Nullable KRSSVocabulary junctor) {
        int size = props.size();
        if (size == 0) {
            return;
        }
        if (size == 1) {
            write(props.iterator().next());
            return;
        }
        if (junctor != null) {
            writeList(props, junctor);
        } else {
            writeList(props, size);
        }
    }

    protected int getIndent() {
        return pos - lastNewLinePos - 1;
    }

    protected <T extends OWLObject> void round(Pair<T> v, KRSS keyword) {
        writeOpenBracket();
        write(keyword);
        write(v.i);
        write(v.j);
        writeCloseBracket();
        writeln();
    }

    @Override
    public void visit(IRI iri) {
        write(iri.toString());
    }

    @Override
    public void visit(OWLClass ce) {
        ce.getIRI().accept(this);
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
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(ALL, ce);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeCardinality(EXACTLY, ce);
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeCardinality(AT_MOST, ce);
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeCardinality(AT_LEAST, ce);
    }

    @Override
    public void visit(OWLDataProperty property) {
        property.getIRI().accept(this);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(SOME, ce);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        pairs(axiom.individuals()).forEach(v -> round(v, DISTINCT));
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        pairs(axiom.classExpressions()).forEach(v -> round(v, DISJOINT));
    }

    @Override
    public void visit(OWLLiteral node) {
        write(node.getLiteral());
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        individual.getIRI().accept(this);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(ALL, ce);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        writeOpenBracket();
        write(NOT);
        write(ce.getOperand());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeCardinality(EXACTLY, ce);
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        writeList(asList(ce.operands()), AND);
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
    public void visit(OWLObjectMaxCardinality ce) {
        writeCardinality(AT_MOST, ce);
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeCardinality(AT_LEAST, ce);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        property.getIRI().accept(this);
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
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeOpenBracket();
        write(DOMAIN);
        write(axiom.getProperty());
        write(axiom.getDomain());
        writeCloseBracket();
        writeln();
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
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(SOME, ce);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        writeList(asList(ce.operands()), OR);
    }

    @Override
    public void visit(OWLOntology ontology) {
        List<OWLClass> classes = asList(ontology.classesInSignature());
        classes.remove(ontology.getOWLOntologyManager().getOWLDataFactory().getOWLThing());
        classes.remove(ontology.getOWLOntologyManager().getOWLDataFactory().getOWLNothing());
        sortOptionally(classes);
        for (OWLClass eachClass : classes) {
            boolean primitive = !isDefined(eachClass, ontology);
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                flatten(asList(
                    sup(ontology.subClassAxiomsForSubClass(eachClass), OWLClassExpression.class)));
                writeCloseBracket();
                writeln();
            } else {
                writeOpenBracket();
                write(DEFINE_CONCEPT);
                write(eachClass);
                flatten(asList(equivalent(ontology.equivalentClassesAxioms(eachClass))));
                writeCloseBracket();
                writeln();
            }
        }
        for (OWLObjectProperty property : sortOptionally(ontology.objectPropertiesInSignature())) {
            writeOpenBracket();
            Stream<OWLObjectPropertyExpression> pStream =
                equivalent(ontology.equivalentObjectPropertiesAxioms(property));
            Collection<OWLObjectPropertyExpression> properties = asList(pStream);
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
                Iterator<OWLObjectPropertyExpression> i =
                    sup(ontology.axioms(Filters.subObjectPropertyWithSub, property, INCLUDED),
                        OWLObjectPropertyExpression.class).iterator();
                if (i.hasNext()) {
                    write(i.next());
                }
            }
            writeCloseBracket();
            writeln();
        }
        ontology.axioms().forEach(a -> a.accept(this));
        writer.flush();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        pairs(axiom.individuals()).forEach(v -> round(v, EQUAL));
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
    public void visit(OWLSubClassOfAxiom axiom) {
        writeOpenBracket();
        write(IMPLIES);
        write(axiom.getSubClass());
        write(axiom.getSuperClass());
        writeCloseBracket();
    }

    protected void write(int i) {
        write(" " + i);
    }

    protected void write(KRSS v) {
        write(v.toString());
    }

    protected void write(OWLObject obj) {
        writeSpace();
        obj.accept(this);
    }

    protected void write(String s) {
        int newLineIndex = s.indexOf('\n');
        if (newLineIndex != -1) {
            lastNewLinePos = pos + newLineIndex;
        }
        pos += s.length();
        writer.write(s);
    }

    protected <T extends OWLPropertyRange> void writeCardinality(KRSSVocabulary v,
        OWLCardinalityRestriction<T> c) {
        writeOpenBracket();
        write(v);
        write(c.getCardinality());
        write(c.getProperty());
        if (c.isQualified()) {
            write(c.getFiller());
        }
        writeCloseBracket();
    }

    protected void writeCloseBracket() {
        write(CLOSE_BRACKET);
    }

    protected void writeIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            writeSpace();
        }
    }

    protected void writeList(List<? extends OWLObject> l, int size) {
        write(l.get(0));
        int indent = getIndent();
        for (int i = 1; i < size; i++) {
            writeln();
            writeIndent(indent);
            write(l.get(i));
        }
    }

    protected void writeList(List<? extends OWLObject> l, KRSSVocabulary v) {
        writeOpenBracket();
        write(v);
        int size = l.size();
        if (size > 0) {
            int indent = getIndent();
            write(l.get(0));
            for (int i = 1; i < size; i++) {
                writeln();
                writeIndent(indent);
                write(l.get(i));
            }
        }
        writeCloseBracket();
    }

    protected void writeln() {
        write(NEWLINE);
    }

    protected void writeOpenBracket() {
        write(OPEN_BRACKET);
    }

    protected <T extends OWLPropertyRange> void writeRestriction(KRSSVocabulary v,
        OWLQuantifiedRestriction<T> c) {
        writeOpenBracket();
        write(v);
        write(c.getProperty());
        write(c.getFiller());
        writeCloseBracket();
    }

    protected void writeSpace() {
        write(" ");
    }
    
}
