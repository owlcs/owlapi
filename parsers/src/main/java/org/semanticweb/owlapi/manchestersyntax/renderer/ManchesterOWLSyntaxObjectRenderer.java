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
package org.semanticweb.owlapi.manchestersyntax.renderer;

import static org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntax;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class ManchesterOWLSyntaxObjectRenderer extends AbstractRenderer implements OWLObjectVisitor {

    /**
     * @param writer
     *        writer
     * @param entityShortFormProvider
     *        entityShortFormProvider
     */
    public ManchesterOWLSyntaxObjectRenderer(Writer writer, ShortFormProvider entityShortFormProvider) {
        super(writer, entityShortFormProvider);
    }

    protected static <T extends OWLObject> List<T> sort(Collection<T> objects) {
        return CollectionFactory.sortOptionally(objects);
    }

    protected static <T extends OWLObject> List<T> sort(Stream<T> objects) {
        return sort(asList(objects));
    }

    protected void write(List<? extends OWLObject> objects, ManchesterOWLSyntax delimeter, boolean newline) {
        int tab = getIndent();
        pushTab(tab);
        Iterator<? extends OWLObject> it = sort(objects).iterator();
        while (it.hasNext()) {
            it.next().accept(this);
            if (it.hasNext()) {
                if (newline && isUseWrapping()) {
                    writeNewLine();
                }
                write(delimeter);
            }
        }
        popTab();
    }

    protected void write(Stream<? extends OWLObject> objects, ManchesterOWLSyntax delimeter, boolean newline) {
        write(asList(objects), delimeter, newline);
    }

    protected void writeCommaSeparatedList(List<? extends OWLObject> objects) {
        for (Iterator<? extends OWLObject> it = objects.stream().sorted().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(", ");
            }
        }
    }

    protected void write(Stream<? extends OWLClassExpression> objects, boolean newline) {
        boolean first = true;
        Iterator<? extends OWLObject> it = sort(objects).iterator();
        while (it.hasNext()) {
            OWLObject desc = it.next();
            if (!first) {
                if (newline && isUseWrapping()) {
                    writeNewLine();
                }
                write(" ", AND, " ");
            }
            first = false;
            if (desc instanceof OWLAnonymousClassExpression) {
                write("(");
            }
            desc.accept(this);
            if (desc instanceof OWLAnonymousClassExpression) {
                write(")");
            }
        }
    }

    private void writeRestriction(OWLQuantifiedDataRestriction restriction, ManchesterOWLSyntax keyword) {
        restriction.getProperty().accept(this);
        write(keyword);
        restriction.getFiller().accept(this);
    }

    private void writeRestriction(OWLQuantifiedObjectRestriction restriction, ManchesterOWLSyntax keyword) {
        restriction.getProperty().accept(this);
        write(keyword);
        boolean conjunctionOrDisjunction = false;
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            if (restriction.getFiller() instanceof OWLObjectIntersectionOf
                || restriction.getFiller() instanceof OWLObjectUnionOf) {
                conjunctionOrDisjunction = true;
                incrementTab(4);
                writeNewLine();
            }
            write("(");
        }
        restriction.getFiller().accept(this);
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write(")");
            if (conjunctionOrDisjunction) {
                popTab();
            }
        }
    }

    private <V extends OWLObject> void writeRestriction(OWLHasValueRestriction<V> restriction,
        OWLPropertyExpression p) {
        p.accept(this);
        write(VALUE);
        restriction.getFiller().accept(this);
    }

    private <F extends OWLPropertyRange> void writeRestriction(OWLCardinalityRestriction<F> restriction,
        ManchesterOWLSyntax keyword, OWLPropertyExpression p) {
        p.accept(this);
        write(keyword);
        write(Integer.toString(restriction.getCardinality()));
        writeSpace();
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write("(");
        }
        restriction.getFiller().accept(this);
        if (restriction.getFiller() instanceof OWLAnonymousClassExpression) {
            write(")");
        }
    }

    // Class expressions
    @Override
    public void visit(OWLClass ce) {
        write(getShortFormProvider().getShortForm(ce));
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        write(ce.operands(), true);
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        boolean first = true;
        List<? extends OWLClassExpression> operands = sort(ce.operands());
        Iterator<? extends OWLClassExpression> it = operands.iterator();
        while (it.hasNext()) {
            OWLClassExpression op = it.next();
            if (!first) {
                write(" ", OR, " ");
            }
            first = false;
            if (op.isAnonymous()) {
                write("(");
            }
            op.accept(this);
            if (op.isAnonymous()) {
                write(")");
            }
        }
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write("", NOT, ce.isAnonymous() ? " " : "");
        if (ce.isAnonymous()) {
            write("(");
        }
        ce.getOperand().accept(this);
        if (ce.isAnonymous()) {
            write(")");
        }
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        writeRestriction(ce, SOME);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        writeRestriction(ce, ONLY);
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        writeRestriction(ce, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        writeRestriction(ce, MIN, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        writeRestriction(ce, EXACTLY, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        writeRestriction(ce, MAX, ce.getProperty());
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        ce.getProperty().accept(this);
        write(SOME);
        write(SELF);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        write("{");
        write(ce.individuals(), ONE_OF_DELIMETER, false);
        write("}");
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        writeRestriction(ce, SOME);
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        writeRestriction(ce, ONLY);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        writeRestriction(ce, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        writeRestriction(ce, MIN, ce.getProperty());
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        writeRestriction(ce, EXACTLY, ce.getProperty());
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        writeRestriction(ce, MAX, ce.getProperty());
    }

    // Entities stuff
    @Override
    public void visit(OWLObjectProperty property) {
        write(getShortFormProvider().getShortForm(property));
    }

    @Override
    public void visit(OWLDataProperty property) {
        write(getShortFormProvider().getShortForm(property));
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        write(getShortFormProvider().getShortForm(individual));
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        write(getShortFormProvider().getShortForm(property));
    }

    @Override
    public void visit(OWLDatatype node) {
        write(getShortFormProvider().getShortForm(node));
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        write(individual.toStringID());
    }

    @Override
    public void visit(IRI iri) {
        write(iri.toQuotedString());
    }

    @Override
    public void visit(OWLAnnotation node) {
        writeAnnotations(node.annotations().sorted().iterator());
        node.getProperty().accept(this);
        writeSpace();
        node.getValue().accept(this);
    }

    // Data stuff
    @Override
    public void visit(OWLDataComplementOf node) {
        write(NOT);
        if (node.getDataRange().isOWLDatatype()) {
            node.getDataRange().accept(this);
        } else {
            write("(");
            node.getDataRange().accept(this);
            write(")");
        }
    }

    @Override
    public void visit(OWLDataOneOf node) {
        write("{");
        write(node.values(), ONE_OF_DELIMETER, false);
        write("}");
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        write("(");
        write(node.operands(), AND, false);
        write(")");
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        write("(");
        write(node.operands(), OR, false);
        write(")");
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        node.getDatatype().accept(this);
        write("[");
        write(node.facetRestrictions(), FACET_RESTRICTION_SEPARATOR, false);
        write("]");
    }

    @Override
    public void visit(OWLLiteral node) {
        // xsd:decimal is the default datatype for literal forms like "33.3"
        // with no specified datatype
        if (XSDVocabulary.DECIMAL.getIRI().equals(node.getDatatype().getIRI())) {
            write(node.getLiteral());
        } else if (node.getDatatype().isFloat()) {
            write(node.getLiteral());
            write("f");
        } else if (node.getDatatype().isInteger()) {
            write(node.getLiteral());
        } else if (node.getDatatype().isBoolean()) {
            write(node.getLiteral());
        } else {
            pushTab(getIndent());
            writeLiteral(node.getLiteral());
            if (node.hasLang()) {
                write("@");
                write(node.getLang());
            } else if (!node.isRDFPlainLiteral()) {
                write("^^");
                node.getDatatype().accept(this);
            }
            popTab();
        }
    }

    private void writeLiteral(String literal) {
        write("\"");
        for (int i = 0; i < literal.length(); i++) {
            char ch = literal.charAt(i);
            if (ch == '"') {
                write('\\');
            } else if (ch == '\\') {
                write('\\');
            }
            write(ch);
        }
        write("\"");
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getSymbolicForm());
        writeSpace();
        node.getFacetValue().accept(this);
    }

    // Property expression stuff
    @Override
    public void visit(OWLObjectInverseOf property) {
        write(INVERSE);
        write("(");
        property.getInverse().accept(this);
        write(")");
    }

    // Annotation stuff
    // Stand alone axiom representation
    // We render each axiom as a one line frame
    private boolean wrapSave;
    private boolean tabSave;

    private void setAxiomWriting() {
        wrapSave = isUseWrapping();
        tabSave = isUseTabbing();
        setUseWrapping(false);
        setUseTabbing(false);
    }

    private void restore() {
        setUseTabbing(tabSave);
        setUseWrapping(wrapSave);
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        setAxiomWriting();
        axiom.getSubClass().accept(this);
        write(SUBCLASS_OF);
        axiom.getSuperClass().accept(this);
        restore();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        setAxiomWriting();
        write(NOT);
        write("(");
        axiom.getSubject().accept(this);
        write(" ");
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getObject().accept(this);
        write(")");
        restore();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeSectionKeyword(ASYMMETRIC);
        axiom.getProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeSectionKeyword(REFLEXIVE);
        axiom.getProperty().accept(this);
        restore();
    }

    private void writeBinaryOrNaryList(ManchesterOWLSyntax binaryKeyword, List<? extends OWLObject> objects,
        ManchesterOWLSyntax naryKeyword) {
        if (objects.size() == 2) {
            Iterator<? extends OWLObject> it = sort(objects).iterator();
            it.next().accept(this);
            write(binaryKeyword);
            it.next().accept(this);
        } else {
            writeSectionKeyword(naryKeyword);
            writeCommaSeparatedList(objects);
        }
    }

    private void writeBinaryOrNaryList(ManchesterOWLSyntax binaryKeyword, Stream<? extends OWLObject> objects,
        ManchesterOWLSyntax naryKeyword) {
        writeBinaryOrNaryList(binaryKeyword, asList(objects), naryKeyword);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(DISJOINT_WITH, axiom.classExpressions(), DISJOINT_CLASSES);
        restore();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        setAxiomWriting();
        axiom.getProperty().accept(this);
        write(DOMAIN);
        axiom.getDomain().accept(this);
        restore();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        setAxiomWriting();
        axiom.getProperty().accept(this);
        write(DOMAIN);
        axiom.getDomain().accept(this);
        restore();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(EQUIVALENT_TO, sort(axiom.properties()), EQUIVALENT_PROPERTIES);
        restore();
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        setAxiomWriting();
        write(NOT);
        write("(");
        axiom.getSubject().accept(this);
        write(" ");
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getObject().accept(this);
        write(")");
        restore();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(DIFFERENT_FROM, axiom.getIndividualsAsList(), DIFFERENT_INDIVIDUALS);
        restore();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(DISJOINT_WITH, sort(axiom.properties()), DISJOINT_PROPERTIES);
        restore();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(DISJOINT_WITH, sort(axiom.properties()), DISJOINT_PROPERTIES);
        restore();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        setAxiomWriting();
        axiom.getProperty().accept(this);
        write(RANGE);
        axiom.getRange().accept(this);
        restore();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        setAxiomWriting();
        axiom.getSubject().accept(this);
        write(" ");
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getObject().accept(this);
        restore();
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeSectionKeyword(FUNCTIONAL);
        axiom.getProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        setAxiomWriting();
        axiom.getSubProperty().accept(this);
        write(SUB_PROPERTY_OF);
        axiom.getSuperProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        setAxiomWriting();
        axiom.getOWLClass().accept(this);
        write(DISJOINT_UNION_OF);
        writeCommaSeparatedList(asList(axiom.classExpressions()));
        restore();
    }

    private void writeFrameType(OWLObject object) {
        setAxiomWriting();
        if (object instanceof OWLOntology) {
            writeFrameKeyword(ONTOLOGY);
            OWLOntology ont = (OWLOntology) object;
            if (!ont.isAnonymous()) {
                write("<");
                write(ont.getOntologyID().getOntologyIRI().get().toString());
                write(">");
            }
        } else {
            if (object instanceof OWLClassExpression) {
                writeFrameKeyword(CLASS);
            } else if (object instanceof OWLObjectPropertyExpression) {
                writeFrameKeyword(OBJECT_PROPERTY);
            } else if (object instanceof OWLDataPropertyExpression) {
                writeFrameKeyword(DATA_PROPERTY);
            } else if (object instanceof OWLIndividual) {
                writeFrameKeyword(INDIVIDUAL);
            } else if (object instanceof OWLAnnotationProperty) {
                writeFrameKeyword(ANNOTATION_PROPERTY);
            }
        }
        object.accept(this);
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        setAxiomWriting();
        writeFrameType(axiom.getEntity());
        restore();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        setAxiomWriting();
        axiom.getSubject().accept(this);
        write(" ");
        axiom.getAnnotation().accept(this);
        restore();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        setAxiomWriting();
        axiom.getProperty().accept(this);
        write(DOMAIN);
        axiom.getDomain().accept(this);
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        setAxiomWriting();
        axiom.getProperty().accept(this);
        write(RANGE);
        axiom.getRange().accept(this);
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        setAxiomWriting();
        axiom.getSubProperty().accept(this);
        write(SUB_PROPERTY_OF);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeSectionKeyword(SYMMETRIC);
        axiom.getProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        setAxiomWriting();
        axiom.getProperty().accept(this);
        writeSectionKeyword(RANGE);
        axiom.getRange().accept(this);
        restore();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        setAxiomWriting();
        writeSectionKeyword(FUNCTIONAL);
        axiom.getProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        setAxiomWriting();
        writeFrameKeyword(EQUIVALENT_PROPERTIES);
        writeCommaSeparatedList(sort(axiom.properties()));
        restore();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        setAxiomWriting();
        axiom.getIndividual().accept(this);
        write(TYPE);
        axiom.getClassExpression().accept(this);
        restore();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(EQUIVALENT_TO, axiom.classExpressions(), EQUIVALENT_CLASSES);
        restore();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        setAxiomWriting();
        axiom.getSubject().accept(this);
        write(" ");
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getObject().accept(this);
        restore();
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeSectionKeyword(TRANSITIVE);
        axiom.getProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeSectionKeyword(IRREFLEXIVE);
        axiom.getProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        setAxiomWriting();
        axiom.getSubProperty().accept(this);
        writeSectionKeyword(SUB_PROPERTY_OF);
        axiom.getSuperProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        setAxiomWriting();
        writeSectionKeyword(INVERSE_FUNCTIONAL);
        axiom.getProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        setAxiomWriting();
        writeBinaryOrNaryList(SAME_AS, axiom.getIndividualsAsList(), SAME_INDIVIDUAL);
        restore();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        setAxiomWriting();
        for (Iterator<OWLObjectPropertyExpression> it = axiom.getPropertyChain().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(" o ");
            }
        }
        write(SUB_PROPERTY_OF);
        axiom.getSuperProperty().accept(this);
        restore();
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        setAxiomWriting();
        axiom.getFirstProperty().accept(this);
        write(INVERSE_OF);
        axiom.getSecondProperty().accept(this);
        restore();
    }

    @Override
    public void visit(SWRLRule rule) {
        setAxiomWriting();
        for (Iterator<SWRLAtom> it = rule.body().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(", ");
            }
        }
        write(" -> ");
        for (Iterator<SWRLAtom> it = rule.head().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(", ");
            }
        }
        restore();
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        setAxiomWriting();
        axiom.getClassExpression().accept(this);
        write(HAS_KEY);
        write(axiom.objectPropertyExpressions(), COMMA, false);
        write(axiom.dataPropertyExpressions(), COMMA, false);
    }

    // SWRL
    @Override
    public void visit(SWRLClassAtom node) {
        if (node.getPredicate().isAnonymous()) {
            write("(");
        }
        node.getPredicate().accept(this);
        if (node.getPredicate().isAnonymous()) {
            write(")");
        }
        write("(");
        node.getArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        node.getPredicate().accept(this);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        SWRLBuiltInsVocabulary voc = SWRLBuiltInsVocabulary.getBuiltIn(node.getPredicate());
        if (voc != null) {
            write(voc.getPrefixedName());
        } else {
            write(node.getPredicate().toQuotedString());
        }
        write("(");
        for (Iterator<SWRLDArgument> it = node.arguments().sorted().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(", ");
            }
        }
        write(")");
    }

    @Override
    public void visit(SWRLVariable node) {
        write("?");
        // do not save the namespace if it's the conventional one
        if ("urn:swrl#".equals(node.getIRI().getNamespace())) {
            write(node.getIRI().prefixedBy(""));
        } else {
            write(node.getIRI().toQuotedString());
        }
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        write(SAME_AS);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        write(DIFFERENT_FROM);
        write("(");
        node.getFirstArgument().accept(this);
        write(", ");
        node.getSecondArgument().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {}

    protected void writeAnnotations(Iterator<OWLAnnotation> annoIt) {
        if (!annoIt.hasNext()) {
            return;
        }
        writeNewLine();
        write(ANNOTATIONS.toString());
        write(": ");
        pushTab(getIndent());
        for (; annoIt.hasNext();) {
            OWLAnnotation anno = annoIt.next();
            anno.accept(this);
            if (annoIt.hasNext()) {
                write(", ");
                writeNewLine();
            }
        }
        writeNewLine();
        writeNewLine();
        popTab();
    }

    // Ontology
    @Override
    public void visit(OWLOntology ontology) {}
}
