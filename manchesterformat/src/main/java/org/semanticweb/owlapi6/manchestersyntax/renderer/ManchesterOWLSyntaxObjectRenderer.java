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
package org.semanticweb.owlapi6.manchestersyntax.renderer;

import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.AND;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.ANNOTATIONS;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.ANNOTATION_PROPERTY;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.ASYMMETRIC;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.CLASS;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.COMMA;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.DATA_PROPERTY;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.DIFFERENT_FROM;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.DISJOINT_CLASSES;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.DISJOINT_PROPERTIES;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.DISJOINT_UNION_OF;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.DISJOINT_WITH;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.DOMAIN;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.EQUIVALENT_CLASSES;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.EQUIVALENT_PROPERTIES;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.EQUIVALENT_TO;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.EXACTLY;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.FACET_RESTRICTION_SEPARATOR;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.FUNCTIONAL;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.HAS_KEY;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.INDIVIDUAL;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.INVERSE;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.INVERSE_FUNCTIONAL;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.INVERSE_OF;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.IRREFLEXIVE;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.MAX;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.MIN;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.NOT;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.ONE_OF_DELIMETER;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.ONLY;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.ONTOLOGY;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.OR;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.RANGE;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.REFLEXIVE;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.SAME_AS;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.SAME_INDIVIDUAL;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.SELF;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.SOME;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.SUBCLASS_OF;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.SUB_PROPERTY_OF;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.SYMMETRIC;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.TRANSITIVE;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.TYPE;
import static org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax.VALUE;

import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntax;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.IsAnonymous;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataExactCardinality;
import org.semanticweb.owlapi6.model.OWLDataHasValue;
import org.semanticweb.owlapi6.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi6.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi6.model.OWLDataMinCardinality;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLDataUnionOf;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
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
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLHasValueRestriction;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
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
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectUnionOf;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLPropertyExpression;
import org.semanticweb.owlapi6.model.OWLPropertyRange;
import org.semanticweb.owlapi6.model.OWLQuantifiedDataRestriction;
import org.semanticweb.owlapi6.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi6.model.SWRLClassAtom;
import org.semanticweb.owlapi6.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi6.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi6.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi6.model.SWRLIndividualArgument;
import org.semanticweb.owlapi6.model.SWRLLiteralArgument;
import org.semanticweb.owlapi6.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi6.model.SWRLRule;
import org.semanticweb.owlapi6.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi6.model.SWRLVariable;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owlapi6.vocab.XSDVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class ManchesterOWLSyntaxObjectRenderer extends AbstractRenderer implements OWLObjectVisitor {

    private boolean wrapSave;
    private boolean tabSave;

    /**
     * @param writer
     *        writer
     * @param entityShortFormProvider
     *        entityShortFormProvider
     */
    public ManchesterOWLSyntaxObjectRenderer(Writer writer, PrefixManager entityShortFormProvider) {
        super(writer, entityShortFormProvider);
    }

    protected ManchesterOWLSyntaxObjectRenderer accept(OWLObject o) {
        o.accept(this);
        return this;
    }

    private ManchesterOWLSyntaxObjectRenderer write(Collection<? extends OWLObject> objects,
        ManchesterOWLSyntax delimiter, boolean newline) {
        pushTab(getIndent());
        iterate(objects.iterator(), () -> divider(delimiter, newline));
        popTab();
        return this;
    }

    private ManchesterOWLSyntaxObjectRenderer divider(ManchesterOWLSyntax delimiter, boolean newline) {
        if (newline && isUseWrapping()) {
            writeNewLine();
        }
        write(delimiter);
        return this;
    }

    @Override
    protected ManchesterOWLSyntaxObjectRenderer write(@Nullable String s) {
        return (ManchesterOWLSyntaxObjectRenderer) super.write(s);
    }

    @Override
    protected ManchesterOWLSyntaxObjectRenderer writeFrameKeyword(ManchesterOWLSyntax keyword) {
        return (ManchesterOWLSyntaxObjectRenderer) super.writeFrameKeyword(keyword);
    }

    @Override
    protected ManchesterOWLSyntaxObjectRenderer write(ManchesterOWLSyntax keyword) {
        return (ManchesterOWLSyntaxObjectRenderer) super.write(keyword);
    }

    @Override
    protected ManchesterOWLSyntaxObjectRenderer writeSpace() {
        return (ManchesterOWLSyntaxObjectRenderer) super.writeSpace();
    }

    @Override
    protected ManchesterOWLSyntaxObjectRenderer writeSectionKeyword(ManchesterOWLSyntax keyword) {
        return (ManchesterOWLSyntaxObjectRenderer) super.writeSectionKeyword(keyword);
    }

    protected ManchesterOWLSyntaxObjectRenderer divider(String delimiter, boolean newline) {
        write(delimiter);
        if (newline) {
            writeNewLine();
        }
        return this;
    }

    protected <T extends OWLObject> ManchesterOWLSyntaxObjectRenderer iterate(Iterator<T> i) {
        return iterate(i, () -> write(", "));
    }

    private <T extends OWLObject> ManchesterOWLSyntaxObjectRenderer iterate(Iterator<T> i, Runnable separator) {
        while (i.hasNext()) {
            accept(i.next());
            if (i.hasNext()) {
                separator.run();
            }
        }
        return this;
    }

    protected <T> ManchesterOWLSyntaxObjectRenderer iterate(Iterator<T> i, Consumer<T> c, Runnable separator) {
        while (i.hasNext()) {
            c.accept(i.next());
            if (i.hasNext()) {
                separator.run();
            }
        }
        return this;
    }

    private ManchesterOWLSyntaxObjectRenderer writeRestriction(OWLQuantifiedDataRestriction restriction,
        ManchesterOWLSyntax keyword) {
        return pair(restriction.getProperty(), keyword, restriction.getFiller());
    }

    private ManchesterOWLSyntaxObjectRenderer writeRestriction(OWLQuantifiedObjectRestriction restriction,
        ManchesterOWLSyntax keyword) {
        accept(restriction.getProperty()).write(keyword);
        boolean conjunctionOrDisjunction = restriction.getFiller() instanceof OWLObjectIntersectionOf
            || restriction.getFiller() instanceof OWLObjectUnionOf;
        if (conjunctionOrDisjunction) {
            incrementTab(4);
            writeNewLine();
        }
        roundedAnon(restriction.getFiller());
        if (conjunctionOrDisjunction) {
            popTab();
        }
        return this;
    }

    private <V extends OWLObject> ManchesterOWLSyntaxObjectRenderer
        writeRestriction(OWLHasValueRestriction<V> restriction, OWLPropertyExpression p) {
        return pair(p, VALUE, restriction.getFiller());
    }

    private <F extends OWLPropertyRange> ManchesterOWLSyntaxObjectRenderer writeRestriction(
        OWLCardinalityRestriction<F> restriction, ManchesterOWLSyntax keyword, OWLPropertyExpression p) {
        return accept(p).write(keyword).write(Integer.toString(restriction.getCardinality())).writeSpace()
            .roundedAnon(restriction.getFiller());
    }

    private ManchesterOWLSyntaxObjectRenderer rounded(OWLObject o) {
        return write("(").accept(o).write(")");
    }

    private <T extends OWLObject & IsAnonymous> ManchesterOWLSyntaxObjectRenderer roundedAnon(T o) {
        return o.isAnonymous() ? rounded(o) : accept(o);
    }

    private ManchesterOWLSyntaxObjectRenderer setAxiomWriting() {
        wrapSave = isUseWrapping();
        tabSave = isUseTabbing();
        setUseWrapping(false);
        setUseTabbing(false);
        return this;
    }

    private ManchesterOWLSyntaxObjectRenderer restore() {
        setUseTabbing(tabSave);
        setUseWrapping(wrapSave);
        return this;
    }

    private ManchesterOWLSyntaxObjectRenderer writeBinaryOrNaryList(ManchesterOWLSyntax binaryKeyword,
        List<? extends OWLObject> objects, ManchesterOWLSyntax naryKeyword) {
        if (objects.size() == 2) {
            return pair(objects.get(0), binaryKeyword, objects.get(1));
        }
        writeSectionKeyword(naryKeyword);
        return iterate(objects.iterator());
    }

    private ManchesterOWLSyntaxObjectRenderer triplet(OWLObject o1, OWLObject o2, OWLObject o3) {
        return accept(o1).write(" ").accept(o2).write(" ").accept(o3);
    }

    private ManchesterOWLSyntaxObjectRenderer writeFrameType(OWLObject object) {
        setAxiomWriting();
        if (object instanceof OWLOntology) {
            writeFrameKeyword(ONTOLOGY);
            OWLOntology ont = (OWLOntology) object;
            if (ont.isNamed()) {
                write("<");
                ont.getOntologyID().getOntologyIRI().ifPresent(x -> write(x.toString()));
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
        return accept(object);
    }

    private ManchesterOWLSyntaxObjectRenderer section(ManchesterOWLSyntax v, OWLObject o) {
        return writeSectionKeyword(v).accept(o);
    }

    private ManchesterOWLSyntaxObjectRenderer section(OWLObject a, ManchesterOWLSyntax v, OWLObject o) {
        return accept(a).writeSectionKeyword(v).accept(o);
    }

    private ManchesterOWLSyntaxObjectRenderer pair(OWLObject a, ManchesterOWLSyntax v, OWLObject b) {
        return accept(a).write(v).accept(b);
    }

    private ManchesterOWLSyntaxObjectRenderer pair(OWLObject a, String v, OWLObject b) {
        return accept(a).write(v).accept(b);
    }

    private ManchesterOWLSyntaxObjectRenderer prefixedDouble(OWLObject a, OWLObject o1, OWLObject o2) {
        return accept(a).write("(").accept(o1).write(", ").accept(o2).write(")");
    }

    private ManchesterOWLSyntaxObjectRenderer prefixedDouble(ManchesterOWLSyntax a, OWLObject o1, OWLObject o2) {
        return write(a).write("(").accept(o1).write(", ").accept(o2).write(")");
    }

    protected ManchesterOWLSyntaxObjectRenderer writeAnnotations(Iterator<OWLAnnotation> annoIt) {
        if (!annoIt.hasNext()) {
            return this;
        }
        writeNewLine().write(ANNOTATIONS.toString()).write(": ");
        pushTab(getIndent());
        iterate(annoIt, () -> write(", \n")).writeNewLine().writeNewLine();
        popTab();
        return this;
    }

    @Override
    public void doDefault(OWLObject object) {
        if (object instanceof OWLEntity) {
            String iri = getPrefixManager().getPrefixIRIIgnoreQName(((OWLEntity) object).getIRI());
            if (iri == null || iri.isEmpty()) {
                iri = getPrefixManager().getShortForm((OWLEntity) object);
            }
            write(iri);
            return;
        }
        OWLObjectVisitor.super.doDefault(object);
    }

    // Class expressions
    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        iterate(ce.getOperandsAsList().iterator(), this::roundedAnon, () -> separate(AND));
    }

    protected void separate(ManchesterOWLSyntax x) {
        if (isUseWrapping()) {
            writeNewLine();
        }
        write(" ", x, " ");
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        iterate(ce.getOperandsAsList().iterator(), this::roundedAnon, () -> separate(OR));
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        write("", NOT, ce.isAnonymous() ? " " : "");
        roundedAnon(ce.getOperand());
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
        accept(ce.getProperty()).write(SOME).write(SELF);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        write("{").write(ce.getOperandsAsList(), ONE_OF_DELIMETER, false).write("}");
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
    public void visit(OWLAnonymousIndividual individual) {
        write(individual.toStringID());
    }

    @Override
    public void visit(IRI iri) {
        write(iri.toQuotedString());
    }

    @Override
    public void visit(OWLAnnotation node) {
        writeAnnotations(node.annotations().iterator()).pair(node.getProperty(), " ", node.getValue());
    }

    // Data stuff
    @Override
    public void visit(OWLDataComplementOf node) {
        write(NOT).roundedAnon(node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        write("{").write(node.getOperandsAsList(), ONE_OF_DELIMETER, false).write("}");
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        write("(").write(node.getOperandsAsList(), AND, false).write(")");
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        write("(").write(node.getOperandsAsList(), OR, false).write(")");
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        accept(node.getDatatype());
        write("[").write(node.facetRestrictionsAsList(), FACET_RESTRICTION_SEPARATOR, false).write("]");
    }

    @Override
    public void visit(OWLLiteral node) {
        // xsd:decimal is the default datatype for literal forms like "33.3"
        // with no specified datatype
        if (XSDVocabulary.DECIMAL.getIRI().equals(node.getDatatype().getIRI()) || node.getDatatype().isInteger()
            || node.getDatatype().isBoolean()) {
            write(node.getLiteral());
        } else if (node.getDatatype().isFloat()) {
            write(node.getLiteral()).write("f");
        } else {
            pushTab(getIndent());
            writeLiteral(node.getLiteral());
            if (node.hasLang()) {
                write("@").write(node.getLang());
            } else if (!node.isRDFPlainLiteral() && !OWL2Datatype.XSD_STRING.matches(node.getDatatype())) {
                write("^^").accept(node.getDatatype());
            }
            popTab();
        }
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getSymbolicForm()).writeSpace().accept(node.getFacetValue());
    }

    // Property expression stuff
    @Override
    public void visit(OWLObjectInverseOf property) {
        write(INVERSE).rounded(property.getInverse());
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        setAxiomWriting().pair(axiom.getSubClass(), SUBCLASS_OF, axiom.getSuperClass()).restore();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        setAxiomWriting().write(NOT).write("(").triplet(axiom.getSubject(), axiom.getProperty(), axiom.getObject())
            .write(")").restore();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        setAxiomWriting().section(ASYMMETRIC, axiom.getProperty()).restore();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        setAxiomWriting().section(REFLEXIVE, axiom.getProperty()).restore();
    }

    // Annotation stuff
    // Stand alone axiom representation
    // We render each axiom as a one line frame
    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        setAxiomWriting().writeBinaryOrNaryList(DISJOINT_WITH, axiom.getOperandsAsList(), DISJOINT_CLASSES).restore();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        setAxiomWriting().pair(axiom.getProperty(), DOMAIN, axiom.getDomain()).restore();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        setAxiomWriting().pair(axiom.getProperty(), DOMAIN, axiom.getDomain()).restore();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        setAxiomWriting().writeBinaryOrNaryList(EQUIVALENT_TO, axiom.getOperandsAsList(), EQUIVALENT_PROPERTIES)
            .restore();
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        setAxiomWriting().write(NOT).write("(").triplet(axiom.getSubject(), axiom.getProperty(), axiom.getObject())
            .write(")").restore();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        setAxiomWriting().writeBinaryOrNaryList(DIFFERENT_FROM, axiom.getOperandsAsList(), DIFFERENT_INDIVIDUALS)
            .restore();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        setAxiomWriting().writeBinaryOrNaryList(DISJOINT_WITH, axiom.getOperandsAsList(), DISJOINT_PROPERTIES)
            .restore();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        setAxiomWriting().writeBinaryOrNaryList(DISJOINT_WITH, axiom.getOperandsAsList(), DISJOINT_PROPERTIES)
            .restore();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        setAxiomWriting().pair(axiom.getProperty(), RANGE, axiom.getRange()).restore();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        setAxiomWriting().triplet(axiom.getSubject(), axiom.getProperty(), axiom.getObject()).restore();
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        setAxiomWriting().section(FUNCTIONAL, axiom.getProperty()).restore();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        setAxiomWriting().section(axiom.getSubProperty(), SUB_PROPERTY_OF, axiom.getSuperProperty()).restore();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        setAxiomWriting().accept(axiom.getOWLClass()).write(DISJOINT_UNION_OF)
            .iterate(axiom.classExpressions().iterator()).restore();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        setAxiomWriting().writeFrameType(axiom.getEntity()).restore();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        setAxiomWriting().pair(axiom.getSubject(), " ", axiom.getAnnotation()).restore();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        setAxiomWriting().accept(axiom.getProperty()).write(DOMAIN).accept(axiom.getDomain());
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        setAxiomWriting().accept(axiom.getProperty()).write(RANGE).accept(axiom.getRange());
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        setAxiomWriting().section(axiom.getSubProperty(), SUB_PROPERTY_OF, axiom.getSuperProperty());
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        setAxiomWriting().section(SYMMETRIC, axiom.getProperty()).restore();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        setAxiomWriting().accept(axiom.getProperty()).section(RANGE, axiom.getRange()).restore();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        setAxiomWriting().section(FUNCTIONAL, axiom.getProperty()).restore();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        setAxiomWriting().writeFrameKeyword(EQUIVALENT_PROPERTIES).iterate(axiom.properties().iterator()).restore();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        setAxiomWriting().pair(axiom.getIndividual(), TYPE, axiom.getClassExpression()).restore();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        setAxiomWriting().writeBinaryOrNaryList(EQUIVALENT_TO, axiom.getOperandsAsList(), EQUIVALENT_CLASSES).restore();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        setAxiomWriting().triplet(axiom.getSubject(), axiom.getProperty(), axiom.getObject()).restore();
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        setAxiomWriting().section(TRANSITIVE, axiom.getProperty()).restore();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        setAxiomWriting().section(IRREFLEXIVE, axiom.getProperty()).restore();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        setAxiomWriting().section(axiom.getSubProperty(), SUB_PROPERTY_OF, axiom.getSuperProperty()).restore();
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        setAxiomWriting().section(INVERSE_FUNCTIONAL, axiom.getProperty()).restore();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        setAxiomWriting().writeBinaryOrNaryList(SAME_AS, axiom.getOperandsAsList(), SAME_INDIVIDUAL).restore();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        setAxiomWriting().iterate(axiom.getPropertyChain().iterator(), () -> write(" o "))
            .section(SUB_PROPERTY_OF, axiom.getSuperProperty()).restore();
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        setAxiomWriting().pair(axiom.getFirstProperty(), INVERSE_OF, axiom.getSecondProperty()).restore();
    }

    @Override
    public void visit(SWRLRule rule) {
        setAxiomWriting().iterate(rule.body().iterator()).write(" -> ").iterate(rule.head().iterator()).restore();
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        setAxiomWriting().accept(axiom.getClassExpression()).write(HAS_KEY)
            .write(axiom.objectPropertyExpressionsAsList(), COMMA, false)
            .write(axiom.dataPropertyExpressionsAsList(), COMMA, false);
    }

    // SWRL
    @Override
    public void visit(SWRLClassAtom node) {
        roundedAnon(node.getPredicate()).rounded(node.getArgument());
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        accept(node.getPredicate()).rounded(node.getArgument());
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        prefixedDouble(node.getPredicate(), node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        prefixedDouble(node.getPredicate(), node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        SWRLBuiltInsVocabulary voc = SWRLBuiltInsVocabulary.getBuiltIn(node.getPredicate());
        if (voc != null) {
            write(voc.getPrefixedName());
        } else {
            write(node.getPredicate().toQuotedString());
        }
        write("(").iterate(node.arguments().iterator()).write(")");
    }

    @Override
    public void visit(SWRLVariable node) {
        write("?");
        // do not save the namespace if it's the conventional one
        if ("urn:swrl:var#".equals(node.getIRI().getNamespace()) || "urn:swrl#".equals(node.getIRI().getNamespace())) {
            write(node.getIRI().prefixedBy(""));
        } else {
            write(node.getIRI().toQuotedString());
        }
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        accept(node.getIndividual());
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        accept(node.getLiteral());
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        prefixedDouble(SAME_AS, node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        prefixedDouble(DIFFERENT_FROM, node.getFirstArgument(), node.getSecondArgument());
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        // XXX unsupported
    }

    // Ontology
    @Override
    public void visit(OWLOntology ontology) {
        // nothing to do here
    }
}
