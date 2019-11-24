/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi6.tutorialowled2011;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.stream.Stream;

import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataHasValue;
import org.semanticweb.owlapi6.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi6.model.OWLDataMinCardinality;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi6.model.OWLObjectHasSelf;
import org.semanticweb.owlapi6.model.OWLObjectHasValue;
import org.semanticweb.owlapi6.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi6.model.OWLObjectInverseOf;
import org.semanticweb.owlapi6.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi6.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi6.model.OWLObjectOneOf;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLPropertyExpression;
import org.semanticweb.owlapi6.model.OWLQuantifiedDataRestriction;
import org.semanticweb.owlapi6.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;

/**
 * A renderer that provides an HTML version of the ontology.
 * 
 * @author Sean Bechhofer, The University Of Manchester, Information Management Group
 * @since 2.0.0
 */
public class OWLTutorialSyntaxObjectRenderer implements OWLObjectVisitor {

    private static final String ENDSPAN = "</span>";
    private final Writer writer;
    private int pos;
    int lastNewLinePos;
    private static final boolean TABLES = true;
    private static final int TABLE_COLUMNS = 3;

    public String labelFor(OWLEntity entity) {
        return entity.getIRI().getFragment();
    }

    public OWLTutorialSyntaxObjectRenderer(Writer writer) {
        this.writer = writer;
    }

    private OWLTutorialSyntaxObjectRenderer write(String s) {
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
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer write(int i) {
        try {
            String s = " " + i + ' ';
            pos += s.length();
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer write(IRI iri) {
        write("<").write(iri.toQuotedString()).write(">");
        return this;
    }

    public void header() {
        write(
            "<html>\n<head>\n<style>\nbody { font-family: sans-serif; }\n.key { color: grey; font-size: 75%; }\n.op { color: grey; }\n.cl { color: #800; }\n.pr { color: #080; }\n.in { color: #008; }\n.box { border: solid 1px grey; padding: 10px; margin: 10px; }\ntable { width: 100%; }\ntd { padding-left: 10px; padding-right: 10px; width: "
                + 100 / TABLE_COLUMNS + "%;}\n</style>\n<body>\n");
    }

    public void footer() {
        write("</body>\n</html>\n");
    }

    private <T extends OWLObject> OWLTutorialSyntaxObjectRenderer writeCollection(
        Stream<T> objects) {
        if (TABLES) {
            return writeTable(objects);
        }
        return writeList(objects);
    }

    private <T extends OWLObject> OWLTutorialSyntaxObjectRenderer writeTable(Stream<T> objects) {
        writeTableStart();
        int count = 0;
        for (Iterator<T> it = objects.iterator(); it.hasNext();) {
            if (count % TABLE_COLUMNS == 0) {
                if (count > 0) {
                    writeTableRowEnd();
                }
                writeTableRowStart();
            }
            writeTableCellStart();
            it.next().accept(this);
            writeTableCellEnd();
            count++;
        }
        writeTableRowEnd();
        writeTableEnd();
        return this;
    }

    private <T extends OWLObject> OWLTutorialSyntaxObjectRenderer writeList(Stream<T> objects) {
        writeListStart();
        for (Iterator<T> it = objects.iterator(); it.hasNext();) {
            writeListItemStart();
            it.next().accept(this);
            writeListItemEnd();
        }
        writeListEnd();
        return this;
    }

    @Override
    public void visit(OWLOntology ontology) {
        header();
        write("<h1>").write(ontology.getOntologyID().toString()).write("</h1>\n");
        write("<div><div class='box'>\n");
        ontology.importsDeclarations()
            .forEach(d -> write("Imports: ").write(d.getIRI()).write("\n"));
        write("<h2>Classes</h2>\n").writeCollection(ontology.classesInSignature());
        write("</div>\n<div class='box'>\n<h2>Properties</h2>\n")
            .writeCollection(ontology.objectPropertiesInSignature())
            .writeCollection(ontology.dataPropertiesInSignature());
        write("</div>\n<div class='box'>\n<h2>Individuals</h2>\n")
            .writeCollection(ontology.individualsInSignature()).write("</div>");
        write("<div><div class='box'><h2>Axioms</h2>\n");
        writeListStart();
        ontology.axioms().forEach(ax -> {
            writeListItemStart();
            ax.accept(this);
            writeListEnd();
        });
        writeListEnd();
        write("</div>");
        footer();
    }

    public OWLTutorialSyntaxObjectRenderer write(String str, OWLObject o) {
        write(str).write("(");
        o.accept(this);
        write(")");
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer write(Stream<? extends OWLObject> stream,
        String separator) {
        Iterator<? extends OWLObject> objects = stream.iterator();
        while (objects.hasNext()) {
            objects.next().accept(this);
            if (objects.hasNext()) {
                writeSpace();
                write(separator);
                writeSpace();
            }
        }
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer write(Stream<? extends OWLObject> objects) {
        return write(objects, "");
    }

    public OWLTutorialSyntaxObjectRenderer writeOpenBracket() {
        return write("(");
    }

    public OWLTutorialSyntaxObjectRenderer writeCloseBracket() {
        return write(")");
    }

    public OWLTutorialSyntaxObjectRenderer writeSpace() {
        return write(" ");
    }

    public OWLTutorialSyntaxObjectRenderer writeAnnotations(OWLAxiom ax) {
        return this;
    }

    public OWLTutorialSyntaxObjectRenderer writeListStart() {
        return write("<ul>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeListEnd() {
        return write("</ul>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableStart() {
        return write("<table>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableEnd() {
        return write("</table>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableRowStart() {
        return write("<tr>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableRowEnd() {
        return write("</tr>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableCellStart() {
        return write("<td>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeTableCellEnd() {
        return write("</td>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeListItemStart() {
        return write("<li>\n");
    }

    public OWLTutorialSyntaxObjectRenderer writeListItemEnd() {
        return write("</li>\n");
    }

    private OWLTutorialSyntaxObjectRenderer writePropertyCharacteristic(String str,
        OWLPropertyExpression prop) {
        write(keyword(str));
        writeSpace();
        prop.accept(this);
        return this;
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("asymmetric", axiom.getProperty());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        write(keyword(":")).writeSpace();
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(keyword("domain"));
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(keyword("range"));
        writeSpace();
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        writeSpace();
        write(keyword("subProperty"));
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        write(axiom.individuals(), keyword("!="));
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        write(axiom.classExpressions(), keyword("|"));
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        write(axiom.properties(), keyword("|"));
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        write(keyword("disjoint")).write(axiom.properties(), keyword("|"));
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept(this);
        writeSpace().write(keyword("==")).writeSpace().write(axiom.classExpressions(),
            keyword("|"));
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        write(axiom.classExpressions(), keyword("=="));
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        write(axiom.properties(), keyword("=="));
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        write(axiom.properties(), keyword("=="));
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writePropertyCharacteristic("functional", axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("functional", axiom.getProperty());
    }

    public void visit(OWLImportsDeclaration axiom) {
        write(keyword("imports")).write(axiom.getIRI());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("inversefunctional", axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        writeSpace().write(keyword("inverse")).writeSpace();
        axiom.getSecondProperty().accept(this);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("Irreflexive", axiom.getProperty());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace().write(keyword("notvalue"));
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace().write(keyword("notvalue"));
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        write("chain").writeOpenBracket().write(axiom.getPropertyChain().stream())
            .writeCloseBracket();
        writeSpace().write(keyword("subProperty")).writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace().write(keyword("domain")).writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace().write(keyword("range")).writeSpace();
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        writeSpace().write(keyword("subProperty")).writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("reflexive", axiom.getProperty());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        write(axiom.individuals(), keyword("="));
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        writeSpace().write(keyword("subClass")).writeSpace();
        axiom.getSuperClass().accept(this);
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("symmetric", axiom.getProperty());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("transitive", axiom.getProperty());
    }

    @Override
    public void visit(OWLClass ce) {
        write("<span class='cl'>" + labelFor(ce) + ENDSPAN);
    }

    private OWLTutorialSyntaxObjectRenderer writeRestriction(String str,
        OWLCardinalityRestriction<?> restriction, OWLPropertyExpression property) {
        write(str).writeOpenBracket().write(restriction.getCardinality()).writeSpace();
        property.accept(this);
        if (restriction.isQualified()) {
            writeSpace();
            restriction.getFiller().accept(this);
        }
        writeCloseBracket();
        return this;
    }

    private OWLTutorialSyntaxObjectRenderer writeRestriction(String str,
        OWLQuantifiedDataRestriction restriction) {
        return writeRestriction(str, restriction.getProperty(), restriction.getFiller());
    }

    private OWLTutorialSyntaxObjectRenderer writeRestriction(String str,
        OWLQuantifiedObjectRestriction restriction) {
        return writeRestriction(str, restriction.getProperty(), restriction.getFiller());
    }

    private OWLTutorialSyntaxObjectRenderer writeRestriction(String str, OWLPropertyExpression prop,
        OWLObject filler) {
        write(str).writeOpenBracket();
        prop.accept(this);
        writeSpace();
        filler.accept(this);
        writeCloseBracket();
        return this;
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(operator("only"), ce);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeRestriction("exact", ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeRestriction("atmost", ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeRestriction("atleast", ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(operator("some"), ce);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        writeRestriction("has-value", ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(operator("only"), ce);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write(operator("not"), ce.getOperand());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeRestriction("exact", ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        writeOpenBracket().write(ce.operands(), keyword("and")).writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writeRestriction("atmost", ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeRestriction("atleast", ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        write(operator("one-of")).writeOpenBracket().write(ce.individuals()).writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        write("self", ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(operator("some"), ce);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        writeOpenBracket().write(ce.operands(), " or ").writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        writeRestriction("hasValue", ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        write(operator("not"), node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        write(operator("one-of")).write("(").write(node.operands()).write(")");
    }

    @Override
    public void visit(OWLDatatype node) {
        write("Datatype").writeOpenBracket().write(node.getIRI()).writeCloseBracket();
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        write("DatatypeRestriction").writeOpenBracket();
        node.getDatatype().accept(this);
        node.facetRestrictions().forEach(r -> {
            writeSpace();
            r.accept(this);
        });
        writeCloseBracket();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getIRI()).writeSpace();
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(OWLLiteral node) {
        write("\"").write(node.getLiteral()).write("\"");
        if (node.hasLang()) {
            write("@").write(node.getLang());
        } else {
            write("^^").write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        write("<span class='pr'>" + labelFor(property) + ENDSPAN);
    }

    @Override
    public void visit(OWLObjectProperty property) {
        write("<span class='pr'>" + labelFor(property) + ENDSPAN);
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        write("inv").writeOpenBracket();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        write("<span class='in'>" + labelFor(individual) + ENDSPAN);
    }

    public String keyword(String str) {
        return "<span class='key'>" + str + ENDSPAN;
    }

    public String operator(String str) {
        return "<span class='op'>" + str + ENDSPAN;
    }
}
