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
package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;
import org.semanticweb.owlapi.util.QNameShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * A renderer that provides an HTML version of the ontology.
 * 
 * @author Sean Bechhofer, The University Of Manchester, Information Management
 *         Group
 * @since 2.0.0
 */
@SuppressWarnings({ "javadoc", })
public class OWLTutorialSyntaxObjectRenderer extends OWLObjectVisitorAdapter {

    @Nonnull
    private final ShortFormProvider shortForms;
    private final Writer writer;
    private int pos;
    int lastNewLinePos;
    private static final boolean TABLES = true;
    private static final int TABLE_COLUMNS = 3;

    @Nonnull
    public String labelFor(@Nonnull OWLEntity entity) {
        return shortForms.getShortForm(entity);
    }

    public OWLTutorialSyntaxObjectRenderer(Writer writer) {
        this.writer = writer;
        shortForms = new QNameShortFormProvider();
    }

    private void write(@Nonnull String s) {
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

    private void write(int i) {
        try {
            String s = " " + i + ' ';
            pos += s.length();
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(@Nonnull IRI iri) {
        write("<");
        write(iri.toQuotedString());
        write(">");
    }

    public void header() {
        write("<html>\n");
        write("<head>\n");
        write("<style>\n");
        write("body { font-family: sans-serif; }\n");
        write(".key { color: grey; font-size: 75%; }\n");
        write(".op { color: grey; }\n");
        write(".cl { color: #800; }\n");
        write(".pr { color: #080; }\n");
        write(".in { color: #008; }\n");
        write(".box { border: solid 1px grey; padding: 10px; margin: 10px; }\n");
        int width = 100 / TABLE_COLUMNS;
        write("table { width: 100%; }\n");
        write("td { padding-left: 10px; padding-right: 10px; width: " + width + "%;}\n");
        write("</style>\n");
        write("<body>\n");
    }

    public void footer() {
        write("</body>\n");
        write("</html>\n");
    }

    private <T extends OWLObject> void writeCollection(@Nonnull Collection<T> objects) {
        if (TABLES) {
            writeTable(objects);
        } else {
            writeList(objects);
        }
    }

    private <T extends OWLObject> void writeTable(@Nonnull Collection<T> objects) {
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
    }

    private <T extends OWLObject> void writeList(@Nonnull Collection<T> objects) {
        writeListStart();
        for (Iterator<T> it = objects.iterator(); it.hasNext();) {
            writeListItemStart();
            it.next().accept(this);
            writeListItemEnd();
        }
        writeListEnd();
    }

    @Override
    public void visit(@Nonnull OWLOntology ontology) {
        header();
        write("<h1>");
        write(ontology.getOntologyID().toString());
        write("</h1>\n");
        write("<div>");
        write("<div class='box'>\n");
        for (OWLImportsDeclaration decl : ontology.getImportsDeclarations()) {
            write("Imports: ");
            write(decl.getIRI().toString());
            write("\n");
        }
        write("<h2>Classes</h2>\n");
        writeCollection(ontology.getClassesInSignature());
        write("</div>\n");
        write("<div class='box'>\n");
        write("<h2>Properties</h2>\n");
        writeCollection(ontology.getObjectPropertiesInSignature());
        writeCollection(ontology.getDataPropertiesInSignature());
        write("</div>\n");
        write("<div class='box'>\n");
        write("<h2>Individuals</h2>\n");
        writeCollection(ontology.getIndividualsInSignature());
        write("</div>");
        write("<div>");
        write("<div class='box'>");
        write("<h2>Axioms</h2>\n");
        writeListStart();
        for (OWLAxiom ax : ontology.getAxioms()) {
            writeListItemStart();
            ax.accept(this);
            writeListEnd();
        }
        writeListEnd();
        write("</div>");
        footer();
    }

    public void write(@Nonnull String str, @Nonnull OWLObject o) {
        write(str);
        write("(");
        o.accept(this);
        write(")");
    }

    private void write(@Nonnull Collection<? extends OWLObject> objects, @Nonnull String separator) {
        for (Iterator<? extends OWLObject> it = objects.iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                writeSpace();
                write(separator);
                writeSpace();
            }
        }
    }

    private void write(@Nonnull Collection<? extends OWLObject> objects) {
        write(objects, "");
    }

    public void writeOpenBracket() {
        write("(");
    }

    public void writeCloseBracket() {
        write(")");
    }

    public void writeSpace() {
        write(" ");
    }

    public void writeAnnotations(@SuppressWarnings("unused") OWLAxiom ax) {
        // TODO: IMPLEMENT
    }

    public void writeListStart() {
        write("<ul>\n");
    }

    public void writeListEnd() {
        write("</ul>\n");
    }

    public void writeTableStart() {
        write("<table>\n");
    }

    public void writeTableEnd() {
        write("</table>\n");
    }

    public void writeTableRowStart() {
        write("<tr>\n");
    }

    public void writeTableRowEnd() {
        write("</tr>\n");
    }

    public void writeTableCellStart() {
        write("<td>\n");
    }

    public void writeTableCellEnd() {
        write("</td>\n");
    }

    public void writeListItemStart() {
        write("<li>\n");
    }

    public void writeListItemEnd() {
        write("</li>\n");
    }

    @SuppressWarnings("unused")
    public void writePropertyCharacteristic(String str, OWLAxiom ax, @Nonnull OWLPropertyExpression prop) {
        write(keyword(str));
        writeSpace();
        prop.accept(this);
    }

    @Override
    public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("asymmetric", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
        axiom.getIndividual().accept(this);
        write(keyword(":"));
        writeSpace();
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(keyword("domain"));
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(keyword("range"));
        writeSpace();
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        writeSpace();
        write(keyword("subProperty"));
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {}

    @Override
    public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        write(axiom.getIndividuals(), keyword("!="));
    }

    @Override
    public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        write(axiom.getClassExpressions(), keyword("|"));
    }

    @Override
    public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        write(axiom.getProperties(), keyword("|"));
    }

    @Override
    public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        write(keyword("disjoint"));
        write(axiom.getProperties(), keyword("|"));
    }

    @Override
    public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        axiom.getOWLClass().accept(this);
        writeSpace();
        write(keyword("=="));
        writeSpace();
        write(axiom.getClassExpressions(), keyword("|"));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        write(axiom.getClassExpressions(), keyword("=="));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        write(axiom.getProperties(), keyword("=="));
    }

    @Override
    public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        write(axiom.getProperties(), keyword("=="));
    }

    @Override
    public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        writePropertyCharacteristic("functional", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("functional", axiom, axiom.getProperty());
    }

    public void visit(@Nonnull OWLImportsDeclaration axiom) {
        write(keyword("imports"));
        write(axiom.getIRI());
    }

    @Override
    public void visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("inversefunctional", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        axiom.getFirstProperty().accept(this);
        writeSpace();
        write(keyword("inverse"));
        writeSpace();
        axiom.getSecondProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("Irreflexive", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace();
        write(keyword("notvalue"));
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace();
        write(keyword("notvalue"));
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        write("chain");
        writeOpenBracket();
        write(axiom.getPropertyChain());
        writeCloseBracket();
        writeSpace();
        write(keyword("subProperty"));
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(keyword("domain"));
        writeSpace();
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        axiom.getProperty().accept(this);
        writeSpace();
        write(keyword("range"));
        writeSpace();
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        axiom.getSubProperty().accept(this);
        writeSpace();
        write(keyword("subProperty"));
        writeSpace();
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("reflexive", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
        write(axiom.getIndividuals(), keyword("="));
    }

    @Override
    public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        writeSpace();
        write(keyword("subClass"));
        writeSpace();
        axiom.getSuperClass().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("symmetric", axiom, axiom.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic("transitive", axiom, axiom.getProperty());
    }

    @Override
    public void visit(OWLClass ce) {
        write("<span class='cl'>" + labelFor(ce) + "</span>");
    }

    private void writeRestriction(@Nonnull String str, @Nonnull OWLCardinalityRestriction<?> restriction,
        @Nonnull OWLPropertyExpression property) {
        write(str);
        writeOpenBracket();
        write(restriction.getCardinality());
        writeSpace();
        property.accept(this);
        if (restriction.isQualified()) {
            writeSpace();
            restriction.getFiller().accept(this);
        }
        writeCloseBracket();
    }

    private void writeRestriction(@Nonnull String str, @Nonnull OWLQuantifiedDataRestriction restriction) {
        writeRestriction(str, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(@Nonnull String str, @Nonnull OWLQuantifiedObjectRestriction restriction) {
        writeRestriction(str, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(@Nonnull String str, @Nonnull OWLPropertyExpression prop, @Nonnull OWLObject filler) {
        write(str);
        writeOpenBracket();
        prop.accept(this);
        writeSpace();
        filler.accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(operator("only"), ce);
    }

    @Override
    public void visit(@Nonnull OWLDataExactCardinality ce) {
        writeRestriction("exact", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLDataMaxCardinality ce) {
        writeRestriction("atmost", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLDataMinCardinality ce) {
        writeRestriction("atleast", ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(operator("some"), ce);
    }

    @Override
    public void visit(@Nonnull OWLDataHasValue ce) {
        writeRestriction("has-value", ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(operator("only"), ce);
    }

    @Override
    public void visit(@Nonnull OWLObjectComplementOf ce) {
        write(operator("not"), ce.getOperand());
    }

    @Override
    public void visit(@Nonnull OWLObjectExactCardinality ce) {
        writeRestriction("exact", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectIntersectionOf ce) {
        writeOpenBracket();
        write(ce.getOperands(), keyword("and"));
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectMaxCardinality ce) {
        writeRestriction("atmost", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectMinCardinality ce) {
        writeRestriction("atleast", ce, ce.getProperty());
    }

    @Override
    public void visit(@Nonnull OWLObjectOneOf ce) {
        write(operator("one-of"));
        writeOpenBracket();
        write(ce.getIndividuals());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasSelf ce) {
        write("self", ce.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(operator("some"), ce);
    }

    @Override
    public void visit(@Nonnull OWLObjectUnionOf ce) {
        writeOpenBracket();
        write(ce.getOperands(), " or ");
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLObjectHasValue ce) {
        writeRestriction("hasValue", ce.getProperty(), ce.getFiller());
    }

    @Override
    public void visit(@Nonnull OWLDataComplementOf node) {
        write(operator("not"), node.getDataRange());
    }

    @Override
    public void visit(@Nonnull OWLDataOneOf node) {
        write(operator("one-of"));
        write("(");
        write(node.getValues());
        write(")");
    }

    @Override
    public void visit(@Nonnull OWLDatatype node) {
        write("Datatype");
        writeOpenBracket();
        write(node.getIRI());
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLDatatypeRestriction node) {
        write("DatatypeRestriction");
        writeOpenBracket();
        node.getDatatype().accept(this);
        for (OWLFacetRestriction restriction : node.getFacetRestrictions()) {
            writeSpace();
            restriction.accept(this);
        }
        writeCloseBracket();
    }

    @Override
    public void visit(@Nonnull OWLFacetRestriction node) {
        write(node.getFacet().getIRI());
        writeSpace();
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(@Nonnull OWLLiteral node) {
        write("\"");
        write(node.getLiteral());
        write("\"");
        if (node.hasLang()) {
            write("@");
            write(node.getLang());
        } else {
            write("^^");
            write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        write("<span class='pr'>" + labelFor(property) + "</span>");
    }

    @Override
    public void visit(OWLObjectProperty property) {
        write("<span class='pr'>" + labelFor(property) + "</span>");
    }

    @Override
    public void visit(@Nonnull OWLObjectInverseOf property) {
        write("inv");
        writeOpenBracket();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        write("<span class='in'>" + labelFor(individual) + "</span>");
    }

    @Override
    public void visit(SWRLRule rule) {}

    @Nonnull
    public String keyword(String str) {
        return "<span class='key'>" + str + "</span>";
    }

    @Nonnull
    public String operator(String str) {
        return "<span class='op'>" + str + "</span>";
    }
}
