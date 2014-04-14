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
package org.semanticweb.owlapi.functional.renderer;

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.EscapeUtils;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

/**
 * The Class OWLObjectRenderer.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class FunctionalSyntaxObjectRenderer implements OWLObjectVisitor {

    private PrefixManager prefixManager;
    protected OWLOntology ontology;
    private Writer writer;
    private boolean writeEntitiesAsURIs = true;
    private OWLObject focusedObject;

    /**
     * @param ontology
     *        the ontology
     * @param writer
     *        the writer
     */
    public FunctionalSyntaxObjectRenderer(OWLOntology ontology, Writer writer) {
        this.ontology = ontology;
        this.writer = writer;
        prefixManager = new DefaultPrefixManager();
        OWLOntologyFormat ontologyFormat = ontology.getOWLOntologyManager()
                .getOntologyFormat(ontology);
        if (ontologyFormat instanceof PrefixOWLOntologyFormat) {
            PrefixOWLOntologyFormat prefixFormat = (PrefixOWLOntologyFormat) ontologyFormat;
            for (String prefixName : prefixFormat.getPrefixNames()) {
                String prefix = prefixFormat.getPrefix(prefixName);
                prefixManager.setPrefix(prefixName, prefix);
            }
        }
        if (!ontology.isAnonymous()) {
            String defPrefix = ontology.getOntologyID().getOntologyIRI() + "#";
            prefixManager.setDefaultPrefix(defPrefix);
        }
        focusedObject = ontology.getOWLOntologyManager().getOWLDataFactory()
                .getOWLThing();
    }

    /**
     * @param prefixManager
     *        the new prefix manager
     */
    public void setPrefixManager(PrefixManager prefixManager) {
        this.prefixManager = prefixManager;
    }

    /**
     * @param focusedObject
     *        the new focused object
     */
    public void setFocusedObject(OWLObject focusedObject) {
        this.focusedObject = focusedObject;
    }

    /**
     * @param prefix
     *        the prefix
     * @param namespace
     *        the namespace
     */
    public void writePrefix(String prefix, String namespace) {
        write("Prefix");
        writeOpenBracket();
        write(prefix);
        write("=");
        write("<");
        write(namespace);
        write(">");
        writeCloseBracket();
        write("\n");
    }

    /** Write prefixes. */
    public void writePrefixes() {
        for (String prefix : prefixManager.getPrefixName2PrefixMap().keySet()) {
            writePrefix(prefix, prefixManager.getPrefix(prefix));
        }
    }

    private void write(OWLXMLVocabulary v) {
        write(v.getShortForm());
    }

    private void write(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void write(IRI iri) {
        String qname = prefixManager.getPrefixIRI(iri);
        if (qname != null) {
            write(qname);
        } else {
            writeFullIRI(iri);
        }
    }

    private void writeFullIRI(IRI iri) {
        write("<");
        write(iri.getNamespace());
        if (iri.getFragment() != null) {
            write(iri.getFragment());
        }
        write(">");
    }

    @Override
    public void visit(OWLOntology ontology1) {
        writePrefixes();
        write("\n\n");
        write(ONTOLOGY);
        write("(");
        if (!ontology1.isAnonymous()) {
            writeFullIRI(ontology1.getOntologyID().getOntologyIRI());
            if (ontology1.getOntologyID().getVersionIRI() != null) {
                write("\n");
                writeFullIRI(ontology1.getOntologyID().getVersionIRI());
            }
            write("\n");
        }
        for (OWLImportsDeclaration decl : ontology1.getImportsDeclarations()) {
            write(IMPORT);
            write("(");
            writeFullIRI(decl.getIRI());
            write(")\n");
        }
        for (OWLAnnotation ontologyAnnotation : ontology1.getAnnotations()) {
            ontologyAnnotation.accept(this);
            write("\n");
        }
        write("\n");
        Set<OWLAxiom> writtenAxioms = new HashSet<OWLAxiom>();
        List<OWLEntity> signature = new ArrayList<OWLEntity>(
                ontology1.getSignature());
        Collections.sort(signature);
        for (OWLEntity ent : signature) {
            writeDeclarations(ent, writtenAxioms);
        }
        for (OWLEntity ent : signature) {
            writeAxioms(ent, writtenAxioms);
        }
        for (OWLAxiom ax : ontology1.getAxioms()) {
            if (!writtenAxioms.contains(ax)) {
                ax.accept(this);
                write("\n");
            }
        }
        write(")");
        flush();
    }

    /**
     * Writes out the axioms that define the specified entity.
     * 
     * @param entity
     *        The entity
     * @return The set of axioms that was written out
     */
    public Set<OWLAxiom> writeAxioms(OWLEntity entity) {
        Set<OWLAxiom> writtenAxioms = new HashSet<OWLAxiom>();
        return writeAxioms(entity, writtenAxioms);
    }

    private Set<OWLAxiom> writeAxioms(OWLEntity entity,
            Set<OWLAxiom> alreadyWrittenAxioms) {
        Set<OWLAxiom> writtenAxioms = new HashSet<OWLAxiom>();
        setFocusedObject(entity);
        writtenAxioms.addAll(writeDeclarations(entity, alreadyWrittenAxioms));
        writtenAxioms.addAll(writeAnnotations(entity));
        List<OWLAxiom> axs = new ArrayList<OWLAxiom>();
        axs.addAll(entity
                .accept(new OWLEntityVisitorEx<Set<? extends OWLAxiom>>() {

                    @Override
                    public Set<? extends OWLAxiom> visit(OWLClass cls) {
                        return ontology.getAxioms(cls, Imports.EXCLUDED);
                    }

                    @Override
                    public Set<? extends OWLAxiom> visit(
                            OWLObjectProperty property) {
                        return ontology.getAxioms(property, Imports.EXCLUDED);
                    }

                    @Override
                    public Set<? extends OWLAxiom> visit(
                            OWLDataProperty property) {
                        return ontology.getAxioms(property, Imports.EXCLUDED);
                    }

                    @Override
                    public Set<? extends OWLAxiom> visit(
                            OWLNamedIndividual individual) {
                        return ontology.getAxioms(individual, Imports.EXCLUDED);
                    }

                    @Override
                    public Set<? extends OWLAxiom> visit(OWLDatatype datatype) {
                        return ontology.getAxioms(datatype, Imports.EXCLUDED);
                    }

                    @Override
                    public Set<? extends OWLAxiom> visit(
                            OWLAnnotationProperty property) {
                        return ontology.getAxioms(property, Imports.EXCLUDED);
                    }
                }));
        Collections.sort(axs);
        for (OWLAxiom ax : axs) {
            if (alreadyWrittenAxioms.contains(ax)) {
                continue;
            }
            if (ax.getAxiomType().equals(AxiomType.DIFFERENT_INDIVIDUALS)) {
                continue;
            }
            if (ax.getAxiomType().equals(AxiomType.DISJOINT_CLASSES)
                    && ((OWLDisjointClassesAxiom) ax).getClassExpressions()
                            .size() > 2) {
                continue;
            }
            ax.accept(this);
            writtenAxioms.add(ax);
            write("\n");
        }
        alreadyWrittenAxioms.addAll(writtenAxioms);
        return writtenAxioms;
    }

    /**
     * Writes out the declaration axioms for the specified entity.
     * 
     * @param entity
     *        The entity
     * @return The axioms that were written out
     */
    public Set<OWLAxiom> writeDeclarations(OWLEntity entity) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        for (OWLAxiom ax : ontology.getDeclarationAxioms(entity)) {
            ax.accept(this);
            axioms.add(ax);
            write("\n");
        }
        return axioms;
    }

    private Set<OWLAxiom> writeDeclarations(OWLEntity entity,
            Set<OWLAxiom> alreadyWrittenAxioms) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        for (OWLAxiom ax : ontology.getDeclarationAxioms(entity)) {
            if (!alreadyWrittenAxioms.contains(ax)) {
                ax.accept(this);
                axioms.add(ax);
                write("\n");
            }
        }
        alreadyWrittenAxioms.addAll(axioms);
        return axioms;
    }

    /**
     * Writes of the annotation for the specified entity.
     * 
     * @param entity
     *        The entity
     * @return The set of axioms that were written out
     */
    public Set<OWLAxiom> writeAnnotations(OWLEntity entity) {
        Set<OWLAxiom> annotationAssertions = new HashSet<OWLAxiom>();
        for (OWLAnnotationAxiom ax : ontology
                .getAnnotationAssertionAxioms(entity.getIRI())) {
            ax.accept(this);
            annotationAssertions.add(ax);
            write("\n");
        }
        return annotationAssertions;
    }

    /**
     * Write.
     * 
     * @param v
     *        the v
     * @param o
     *        the o
     */
    public void write(OWLXMLVocabulary v, OWLObject o) {
        write(v);
        write("(");
        o.accept(this);
        write(")");
    }

    private void write(Collection<? extends OWLObject> objects) {
        if (objects.size() > 2) {
            for (Iterator<? extends OWLObject> it = objects.iterator(); it
                    .hasNext();) {
                it.next().accept(this);
                if (it.hasNext()) {
                    write(" ");
                }
            }
        } else if (objects.size() == 2) {
            Iterator<? extends OWLObject> it = objects.iterator();
            OWLObject objA = it.next();
            OWLObject objB = it.next();
            OWLObject lhs, rhs;
            if (objA.equals(focusedObject)) {
                lhs = objA;
                rhs = objB;
            } else {
                lhs = objB;
                rhs = objA;
            }
            lhs.accept(this);
            writeSpace();
            rhs.accept(this);
        } else if (objects.size() == 1) {
            objects.iterator().next().accept(this);
        }
    }

    private void write(List<? extends OWLObject> objects) {
        if (objects.size() > 1) {
            for (Iterator<? extends OWLObject> it = objects.iterator(); it
                    .hasNext();) {
                it.next().accept(this);
                if (it.hasNext()) {
                    write(" ");
                }
            }
        } else if (objects.size() == 1) {
            objects.iterator().next().accept(this);
        }
    }

    /** Write open bracket. */
    public void writeOpenBracket() {
        write("(");
    }

    /** Write close bracket. */
    public void writeCloseBracket() {
        write(")");
    }

    /** Write space. */
    public void writeSpace() {
        write(" ");
    }

    /**
     * Write.
     * 
     * @param annotation
     *        the annotation
     */
    public void write(OWLAnnotation annotation) {}

    /**
     * Write annotations.
     * 
     * @param ax
     *        the ax
     */
    public void writeAnnotations(OWLAxiom ax) {
        for (OWLAnnotation anno : ax.getAnnotations()) {
            anno.accept(this);
            write(" ");
        }
    }

    /**
     * Write axiom start.
     * 
     * @param v
     *        the v
     * @param axiom
     *        the axiom
     */
    public void writeAxiomStart(OWLXMLVocabulary v, OWLAxiom axiom) {
        write(v);
        writeOpenBracket();
        writeAnnotations(axiom);
    }

    /** Write axiom end. */
    public void writeAxiomEnd() {
        write(")");
    }

    /**
     * Write property characteristic.
     * 
     * @param v
     *        the v
     * @param ax
     *        the ax
     * @param prop
     *        the prop
     */
    public void writePropertyCharacteristic(OWLXMLVocabulary v, OWLAxiom ax,
            OWLPropertyExpression prop) {
        writeAxiomStart(v, ax);
        prop.accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(ASYMMETRIC_OBJECT_PROPERTY, axiom,
                axiom.getProperty());
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        writeAxiomStart(CLASS_ASSERTION, axiom);
        axiom.getClassExpression().accept(this);
        writeSpace();
        axiom.getIndividual().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writeAxiomStart(DATA_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_DATA_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        writeAxiomStart(DECLARATION, axiom);
        writeEntitiesAsURIs = false;
        axiom.getEntity().accept(this);
        writeEntitiesAsURIs = true;
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        Set<OWLIndividual> individuals = axiom.getIndividuals();
        if (individuals.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DIFFERENT_INDIVIDUALS, axiom);
        write(individuals);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> classExpressions = axiom.getClassExpressions();
        if (classExpressions.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_CLASSES, axiom);
        write(classExpressions);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> properties = axiom.getProperties();
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_DATA_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> properties = axiom.getProperties();
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(DISJOINT_OBJECT_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        writeAxiomStart(DISJOINT_UNION, axiom);
        axiom.getOWLClass().accept(this);
        writeSpace();
        write(axiom.getClassExpressions());
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        writeAxiomStart(ANNOTATION_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getValue().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> classExpressions = axiom.getClassExpressions();
        if (classExpressions.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_CLASSES, axiom);
        write(classExpressions);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        Set<OWLDataPropertyExpression> properties = axiom.getProperties();
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_DATA_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        Set<OWLObjectPropertyExpression> properties = axiom.getProperties();
        if (properties.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(EQUIVALENT_OBJECT_PROPERTIES, axiom);
        write(properties);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_DATA_PROPERTY, axiom,
                axiom.getProperty());
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(FUNCTIONAL_OBJECT_PROPERTY, axiom,
                axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiom,
                axiom.getProperty());
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writeAxiomStart(INVERSE_OBJECT_PROPERTIES, axiom);
        axiom.getFirstProperty().accept(this);
        writeSpace();
        axiom.getSecondProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(IRREFLEXIVE_OBJECT_PROPERTY, axiom,
                axiom.getProperty());
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_DATA_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_ASSERTION, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getSubject().accept(this);
        writeSpace();
        axiom.getObject().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        write(OBJECT_PROPERTY_CHAIN);
        writeOpenBracket();
        for (Iterator<OWLObjectPropertyExpression> it = axiom
                .getPropertyChain().iterator(); it.hasNext();) {
            it.next().accept(this);
            if (it.hasNext()) {
                write(" ");
            }
        }
        writeCloseBracket();
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writeAxiomStart(OBJECT_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        writeSpace();
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_OBJECT_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        writeSpace();
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(REFLEXIVE_OBJECT_PROPERTY, axiom,
                axiom.getProperty());
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        Set<OWLIndividual> individuals = axiom.getIndividuals();
        if (individuals.size() < 2) {
            // TODO log
            return;
        }
        writeAxiomStart(SAME_INDIVIDUAL, axiom);
        write(individuals);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        writeAxiomStart(SUB_CLASS_OF, axiom);
        axiom.getSubClass().accept(this);
        writeSpace();
        axiom.getSuperClass().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(SYMMETRIC_OBJECT_PROPERTY, axiom,
                axiom.getProperty());
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writePropertyCharacteristic(TRANSITIVE_OBJECT_PROPERTY, axiom,
                axiom.getProperty());
    }

    @Override
    public void visit(OWLClass desc) {
        if (!writeEntitiesAsURIs) {
            write(CLASS);
            writeOpenBracket();
        }
        desc.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    private <F extends OWLPropertyRange> void writeRestriction(
            OWLXMLVocabulary v, OWLCardinalityRestriction<F> restriction,
            OWLPropertyExpression p) {
        write(v);
        writeOpenBracket();
        write(Integer.toString(restriction.getCardinality()));
        writeSpace();
        p.accept(this);
        if (restriction.isQualified()) {
            writeSpace();
            restriction.getFiller().accept(this);
        }
        writeCloseBracket();
    }

    private void writeRestriction(OWLXMLVocabulary v,
            OWLQuantifiedDataRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(OWLXMLVocabulary v,
            OWLQuantifiedObjectRestriction restriction) {
        writeRestriction(v, restriction.getProperty(), restriction.getFiller());
    }

    private void writeRestriction(OWLXMLVocabulary v,
            OWLPropertyExpression prop, OWLObject filler) {
        write(v);
        writeOpenBracket();
        prop.accept(this);
        writeSpace();
        filler.accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataAllValuesFrom desc) {
        writeRestriction(DATA_ALL_VALUES_FROM, desc);
    }

    @Override
    public void visit(OWLDataExactCardinality desc) {
        writeRestriction(DATA_EXACT_CARDINALITY, desc, desc.getProperty());
    }

    @Override
    public void visit(OWLDataMaxCardinality desc) {
        writeRestriction(DATA_MAX_CARDINALITY, desc, desc.getProperty());
    }

    @Override
    public void visit(OWLDataMinCardinality desc) {
        writeRestriction(DATA_MIN_CARDINALITY, desc, desc.getProperty());
    }

    @Override
    public void visit(OWLDataSomeValuesFrom desc) {
        writeRestriction(DATA_SOME_VALUES_FROM, desc);
    }

    @Override
    public void visit(OWLDataHasValue desc) {
        writeRestriction(DATA_HAS_VALUE, desc.getProperty(), desc.getFiller());
    }

    @Override
    public void visit(OWLObjectAllValuesFrom desc) {
        writeRestriction(OBJECT_ALL_VALUES_FROM, desc);
    }

    @Override
    public void visit(OWLObjectComplementOf desc) {
        write(OBJECT_COMPLEMENT_OF, desc.getOperand());
    }

    @Override
    public void visit(OWLObjectExactCardinality desc) {
        writeRestriction(OBJECT_EXACT_CARDINALITY, desc, desc.getProperty());
    }

    @Override
    public void visit(OWLObjectIntersectionOf desc) {
        write(OBJECT_INTERSECTION_OF);
        writeOpenBracket();
        write(desc.getOperands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectMaxCardinality desc) {
        writeRestriction(OBJECT_MAX_CARDINALITY, desc, desc.getProperty());
    }

    @Override
    public void visit(OWLObjectMinCardinality desc) {
        writeRestriction(OBJECT_MIN_CARDINALITY, desc, desc.getProperty());
    }

    @Override
    public void visit(OWLObjectOneOf desc) {
        write(OBJECT_ONE_OF);
        writeOpenBracket();
        write(desc.getIndividuals());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectHasSelf desc) {
        write(OBJECT_HAS_SELF, desc.getProperty());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom desc) {
        writeRestriction(OBJECT_SOME_VALUES_FROM, desc);
    }

    @Override
    public void visit(OWLObjectUnionOf desc) {
        write(OBJECT_UNION_OF);
        writeOpenBracket();
        write(desc.getOperands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLObjectHasValue desc) {
        writeRestriction(OBJECT_HAS_VALUE, desc.getProperty(), desc.getFiller());
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        write(DATA_COMPLEMENT_OF, node.getDataRange());
    }

    @Override
    public void visit(OWLDataOneOf node) {
        write(DATA_ONE_OF);
        write("(");
        write(node.getValues());
        write(")");
    }

    @Override
    public void visit(OWLDatatype node) {
        if (!writeEntitiesAsURIs) {
            write(DATATYPE);
            writeOpenBracket();
        }
        node.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        write(DATATYPE_RESTRICTION);
        writeOpenBracket();
        node.getDatatype().accept(this);
        for (OWLFacetRestriction restriction : node.getFacetRestrictions()) {
            writeSpace();
            restriction.accept(this);
        }
        writeCloseBracket();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        write(node.getFacet().getIRI());
        writeSpace();
        node.getFacetValue().accept(this);
    }

    @Override
    public void visit(OWLLiteral node) {
        write("\"");
        write(EscapeUtils.escapeString(node.getLiteral()));
        write("\"");
        if (node.hasLang()) {
            write("@");
            write(node.getLang());
        } else if (!node.isRDFPlainLiteral()) {
            write("^^");
            write(node.getDatatype().getIRI());
        }
    }

    @Override
    public void visit(OWLDataProperty property) {
        if (!writeEntitiesAsURIs) {
            write(DATA_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLObjectProperty property) {
        if (!writeEntitiesAsURIs) {
            write(OBJECT_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        write(OBJECT_INVERSE_OF);
        writeOpenBracket();
        property.getInverse().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        if (!writeEntitiesAsURIs) {
            write(NAMED_INDIVIDUAL);
            writeOpenBracket();
        }
        individual.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        writeAxiomStart(HAS_KEY, axiom);
        axiom.getClassExpression().accept(this);
        write(" ");
        write("(");
        for (Iterator<? extends OWLPropertyExpression> it = axiom
                .getObjectPropertyExpressions().iterator(); it.hasNext();) {
            OWLPropertyExpression prop = it.next();
            prop.accept(this);
            if (it.hasNext()) {
                write(" ");
            }
        }
        write(") (");
        for (Iterator<? extends OWLPropertyExpression> it = axiom
                .getDataPropertyExpressions().iterator(); it.hasNext();) {
            OWLPropertyExpression prop = it.next();
            prop.accept(this);
            if (it.hasNext()) {
                write(" ");
            }
        }
        write(")");
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_DOMAIN, axiom);
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getDomain().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        writeAxiomStart(ANNOTATION_PROPERTY_RANGE, axiom);
        axiom.getProperty().accept(this);
        write(" ");
        axiom.getRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        writeAxiomStart(SUB_ANNOTATION_PROPERTY_OF, axiom);
        axiom.getSubProperty().accept(this);
        write(" ");
        axiom.getSuperProperty().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        write(DATA_INTERSECTION_OF);
        writeOpenBracket();
        write(node.getOperands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        write(DATA_UNION_OF);
        writeOpenBracket();
        write(node.getOperands());
        writeCloseBracket();
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        if (!writeEntitiesAsURIs) {
            write(ANNOTATION_PROPERTY);
            writeOpenBracket();
        }
        property.getIRI().accept(this);
        if (!writeEntitiesAsURIs) {
            writeCloseBracket();
        }
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        write(individual.getID().toString());
    }

    @Override
    public void visit(IRI iri) {
        write(iri);
    }

    @Override
    public void visit(OWLAnnotation node) {
        write(ANNOTATION);
        write("(");
        for (OWLAnnotation anno : node.getAnnotations()) {
            anno.accept(this);
            write(" ");
        }
        node.getProperty().accept(this);
        write(" ");
        node.getValue().accept(this);
        write(")");
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        writeAxiomStart(DATATYPE_DEFINITION, axiom);
        axiom.getDatatype().accept(this);
        writeSpace();
        axiom.getDataRange().accept(this);
        writeAxiomEnd();
    }

    @Override
    public void visit(SWRLRule rule) {
        writeAxiomStart(DL_SAFE_RULE, rule);
        write(BODY);
        writeOpenBracket();
        write(rule.getBody());
        writeCloseBracket();
        write(HEAD);
        writeOpenBracket();
        write(rule.getHead());
        writeCloseBracket();
        writeAxiomEnd();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }

    @Override
    public void visit(SWRLClassAtom node) {
        write(CLASS_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        write(DATA_RANGE_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        write(OBJECT_PROPERTY_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        write(DATA_PROPERTY_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        write(BUILT_IN_ATOM);
        writeOpenBracket();
        node.getPredicate().accept(this);
        writeSpace();
        write(node.getArguments());
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLVariable node) {
        write(VARIABLE);
        writeOpenBracket();
        node.getIRI().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        write(DIFFERENT_INDIVIDUALS_ATOM);
        writeOpenBracket();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        write(SAME_INDIVIDUAL_ATOM);
        writeOpenBracket();
        node.getFirstArgument().accept(this);
        writeSpace();
        node.getSecondArgument().accept(this);
        writeCloseBracket();
    }
}
