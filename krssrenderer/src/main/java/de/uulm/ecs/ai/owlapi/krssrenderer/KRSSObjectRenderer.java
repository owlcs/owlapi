package de.uulm.ecs.ai.owlapi.krssrenderer;

import static de.uulm.ecs.ai.owlapi.krssrenderer.KRSSVocabulary.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/*
 * Copyright (C) 2008, Ulm University
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
 * A <code>KRSSObjectRenderer</code> renderes an OWLOntology in the original KRSS syntax.
 * Note that only a subset of OWL can be expressed in KRSS.
 * <p/>
 * <b>Abbreviations</b>
 * <table bordercolor="#000200" border="1">
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
 * <p/>
 * <b>KRSS concept language</b>
 * <table bordercolor="#000200" border="1">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLClassExpression</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(at-least n R C)</td>
 * <td>(OWLObjectMinCardinality R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(at-most n R C)</td>
 * <td>(OWLObjectMaxCardinality R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(exactly n R C)</td>
 * <td>(OWLObjectExactCardinality R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(some R C)</td>
 * <td>(OWLObjectSomeValuesFrom R C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(all R C)</td>
 * <td>(OWLObjectAllValuesFrom R C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(not C)</td>
 * <td>(OWLObjectComplementOf C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(and C D E)</td>
 * <td>(OWLObjectIntersectionOf C D E)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(or C D E)</td>
 * <td>(OWLObjectUnionOf C D E)</td>
 * </tr>
 * <p/>
 * <p/>
 * </table>
 * <p/>
 * <b>KRSS role language</b>
 * <table bordercolor="#000200" border="1">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLObjectPropertyExpression</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(inv R)</td>
 * <td>(OWLInverseObjectPropertiesAxiom R)</td>
 * </tr>
 * <p/>
 * </table>
 * <p/>
 * <p/>
 * Each referenced class, object property as well as individual is
 * defined using <i>define-concept</i> resp. <i>define-primitive-concept</i>,
 * <i>define-role</i> and <i>define-individual</i>. In addition, axioms are
 * translated as follows.
 * <p/>
 * <table bordercolor="#000200" border="1">
 * <th>OWLAxiom</th>
 * <th>KRSS syntax</th>
 * <th>Remarks</th>
 * <tr>
 * <td>OWLEquivalentClasses</td>
 * <td>(define-concept C D)</td>
 * <td><i>OWLEquivalentClasses C D1 D2...Dn</i> will be translated to:<br>
 * (define-concept C (and D1 D2...Dn))
 * <p/>
 * Only applicable if there is no OWLSubClassOf axiom. <p>
 * </td>
 * </tr>
 * <tr>
 * <td>OWLDisjointClassesAxiom</td>
 * <td>(disjoint C D)</td>
 * <td>multiple pairwise disjoint statements are added in case of more than 2 disjoint expressions</td>
 * </tr>
 * <tr>
 * <td>OWLSubClassOf</td>
 * <td>(define-primitive-concept C D)</td>
 * <td>Multiple OWLSubClassOf axioms for C will be combined: <br>
 * (define-primitive-concept C (and D1...Dn))
 * <p/>
 * Only applicable if there is no OWLEquivalentClasses axiom.<br>
 * KRSS does not allow both define-concept C and define-primitive-concept C.
 * GCIs not supported in KRSS (see KRSS2)
 * </td>
 * </tr>
 * <p/>
 * <tr>
 * <td>OWLEquivalentObjectPropertiesAxiom</td>
 * <td>(define-role R S)</td>
 * <td>Only applicable if the is no OWLSubObjectPropertyOf for R
 * and the number of the involved properties must be two</td>
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
 * <td>Only applicable if the is no OWLEquivalentObjectPropertiesAxiom for R
 * and only one OWLSubObjectPropertyOf axiom for a given property is
 * allowed. If there are more one is randomly chosen.</td>
 * </tr>
 * <tr>
 * <td>OWLTransitiveObjectPropertyAxiom</td>
 * <td>(transitive P)</td>
 * </td></td>
 * </tr>
 * <tr>
 * <td>OWLClassAssertionAxiom</td>
 * <td>(instance i D)</td>
 * </td></td>
 * </tr>
 * <tr>
 * <td>OWLDifferentIndividualsAxiom</td>
 * <td>(distinct i1 i2)</td>
 * <td><i>OWLDifferentIndividualsAxiom i1 i2 ... in</i> will be splitted into:<br>
 * { (distinct i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k}  <br>
 * </td>
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
 * { (equal i(j) i(j+k)) | 1 &lt;= j &lt;=n, j&lt;k&lt;=n, j=|=k}  <br>
 * </td>
 * </tr>
 * </table>
 *
 * Author: Olaf Noppens<br>
 * Ulm University<br>
 * Institute of Artificial Intelligence<br>
 */
@SuppressWarnings("unused")
public class KRSSObjectRenderer implements OWLObjectVisitor {
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String NEWLINE = "\n";

    protected final OWLOntology ontology;
    protected final Writer writer;
    protected final OWLOntologyManager manager;

    private int pos = 0;
    private int lastNewLinePos = 0;

    public KRSSObjectRenderer(OWLOntologyManager manager, OWLOntology ontology, Writer writer) {
        this.ontology = ontology;
        this.writer = new PrintWriter(writer);
        this.manager = manager;
    }

    protected <T extends OWLObject> List<T> sort(Collection<T>objects) {
        List<T> sortedDescriptions = new ArrayList<T>(objects);
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


    private void write(KRSSVocabulary v) {
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
        }
        catch (IOException e) {
            throw new RuntimeException(e);
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

    protected void flattenProperties(final Set<OWLObjectPropertyExpression> properties, KRSSVocabulary junctor) {
        if (properties.isEmpty()) return;
        if (properties.size() == 1) {
            write(properties.iterator().next());
            return;
        }

        if (junctor != null) {
            writeOpenBracket();
            write(junctor);
        }
        List<OWLObjectPropertyExpression> props = sort(properties);
        write(props.get(0));
        final int size = props.size();
        final int indent = getIndent();
        for (int i = 1; i < size; i++) {
            writeln();
            writeIndent(indent);
            write(props.get(i));
        }
        if (junctor != null)
            writeCloseBracket();
    }

    protected void flatten(final Set<OWLClassExpression> description, KRSSVocabulary junctor) {
        if (description.isEmpty()) return;
        if (description.size() == 1) {
            write(description.iterator().next());
            return;
        }
        writeOpenBracket();
        write(junctor);
        List<OWLClassExpression> descs = sort(description);
        write(descs.get(0));
        final int size = descs.size();
        final int indent = getIndent();
        for (int i = 1; i < size; i++) {
            writeln();
            writeIndent(indent);
            write(descs.get(i));
        }
        writeCloseBracket();
    }

    public void visit(OWLOntology ontology) {
        Set<OWLClass> classes = ontology.getClassesInSignature();
        classes.remove(manager.getOWLDataFactory().getOWLThing());
        classes.remove(manager.getOWLDataFactory().getOWLNothing());

        for (final OWLClass eachClass : sort(classes)) {
            final boolean primitive = !eachClass.isDefined(ontology);
            if (primitive) {
                writeOpenBracket();
                write(DEFINE_PRIMITIVE_CONCEPT);
                write(eachClass);
                writeSpace();
                flatten(eachClass.getSuperClasses(ontology), KRSSVocabulary.AND);
                writeCloseBracket();
                writeln();
            } else {
                writeOpenBracket();
                write(DEFINE_CONCEPT);
                write(eachClass);
                flatten(eachClass.getEquivalentClasses(ontology), KRSSVocabulary.AND);
                writeCloseBracket();
                writeln();
            }
        }

        for (final OWLObjectProperty property : sort(ontology.getObjectPropertiesInSignature())) {
            writeOpenBracket();
            Set<OWLObjectPropertyExpression> properties = property.getEquivalentProperties(ontology);
            boolean isDefined = !properties.isEmpty();

            if (isDefined) {
                write(DEFINE_ROLE);
                write(property);
                //choose randomly one property (KRSS restriction)
                properties.remove(property);
                if (!properties.isEmpty())
                    write(properties.iterator().next());
            } else {
                write(DEFINE_PRIMITIVE_ROLE);
                write(property);
                writeSpace();
                properties = property.getSuperProperties(ontology);
                if (!properties.isEmpty()) {
                    write(properties.iterator().next());
                }
            }
            writeCloseBracket();
            writeln();
        }

        for (final OWLAxiom axiom : ontology.getAxioms())
            axiom.accept(this);

        try {
            writer.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public void visit(OWLSubClassOfAxiom axiom) {

    }

    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
    }

    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLDisjointClassesAxiom axiom) {
        List<OWLClassExpression> classes = sort(axiom.getClassExpressions());
        int size = classes.size();
        if (size <= 1) return;
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(DISJOINT);
                write(classes.get(i));
                write(classes.get(j));
                writeCloseBracket();
                writeln();
            }
    }

    public void visit(OWLDataPropertyDomainAxiom axiom) {
    }

    public void visit(OWLImportsDeclaration axiom) {
    }

    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeOpenBracket();
        write(DOMAIN);
        write(axiom.getProperty());
        write(axiom.getDomain());
        writeCloseBracket();
        writeln();

    }

    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {

    }

    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLDifferentIndividualsAxiom axiom) {
        List<OWLIndividual> individuals = sort(axiom.getIndividuals());
        int size = individuals.size();
        if (size <= 1) return;
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(DISTINCT);
                write(individuals.get(i));
                write(individuals.get(j));
                writeCloseBracket();
                writeln();
            }
    }

    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
    }

    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {

    }

    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeOpenBracket();
        write(RANGE);
        write(axiom.getProperty());
        write(axiom.getRange());
        writeCloseBracket();
        writeln();
    }

    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writeOpenBracket();
        write(RELATED);
        write(axiom.getSubject());
        write(axiom.getProperty());
        write(axiom.getObject());
        writeCloseBracket();
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

    public void visit(OWLClassAssertionAxiom axiom) {
        writeOpenBracket();
        write(INSTANCE);
        write(axiom.getIndividual());
        write(axiom.getClassExpression());
        writeCloseBracket();
        writeln();
    }

    public void visit(OWLEquivalentClassesAxiom axiom) {
    }

    public void visit(OWLDataPropertyAssertionAxiom axiom) {
    }

    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writeOpenBracket();
        write(TRANSITIVE);
        write(axiom.getProperty());
        writeCloseBracket();
        writeln();
    }

    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSubDataPropertyOfAxiom axiom) {
    }

    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }

    public void visit(OWLSameIndividualAxiom axiom) {
        final List<OWLIndividual> individuals = sort(axiom.getIndividuals());
        final int size = individuals.size();
        if (size <= 1) return;
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++) {
                writeOpenBracket();
                write(EQUAL);
                write(individuals.get(i));
                write(individuals.get(j));
                writeCloseBracket();
                writeln();
            }
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



    public void visit(OWLClass desc) {
        write(desc.getIRI());
    }

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

    public void visit(OWLObjectComplementOf desc) {
        writeOpenBracket();
        write(NOT);
        write(desc.getOperand());
        writeCloseBracket();
    }

    public void visit(OWLObjectSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLObjectAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLObjectHasValue desc) {

    }

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

    public void visit(OWLObjectHasSelf desc) {
    }

    public void visit(OWLObjectOneOf desc) {
    }

    public void visit(OWLDataSomeValuesFrom desc) {
        writeOpenBracket();
        write(SOME);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLDataAllValuesFrom desc) {
        writeOpenBracket();
        write(ALL);
        write(desc.getProperty());
        write(desc.getFiller());
        writeCloseBracket();
    }

    public void visit(OWLDataHasValue desc) {
    }

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

    public void visit(OWLDatatype node) {
    }

    public void visit(OWLDataComplementOf node) {

    }

    public void visit(OWLDataOneOf node) {

    }

    public void visit(OWLDatatypeRestriction node) {

    }

    public void visit(OWLLiteral node) {
        write(node.getLiteral());
    }

    public void visit(OWLFacetRestriction node) {

    }

    public void visit(OWLObjectProperty property) {
        write(property.getIRI());
    }

    public void visit(OWLObjectInverseOf property) {
        writeOpenBracket();
        write(INVERSE);
        writeSpace();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    public void visit(OWLDataProperty property) {
        write(property.getIRI());
    }

    public void visit(OWLNamedIndividual individual) {
        write(individual.getIRI());
    }

    public void visit(OWLHasKeyAxiom axiom) {
    }

    public void visit(OWLDatatypeDefinitionAxiom axiom) {
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
    }

    public void visit(OWLDataIntersectionOf node) {
    }

    public void visit(OWLDataUnionOf node) {
    }

    public void visit(OWLAnnotationProperty property) {
    }

    public void visit(OWLAnonymousIndividual individual) {
    }

    public void visit(IRI iri) {
    }

    public void visit(OWLAnnotation node) {
    }
}

