package org.coode.owlapi.owlxml.renderer;

import org.semanticweb.owl.model.*;
import static org.semanticweb.owl.vocab.OWLXMLVocabulary.*;

import java.util.Set;
import java.util.TreeSet;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 12-Dec-2006<br><br>
 */
public class OWLXMLObjectRenderer implements OWLObjectVisitor {

    private OWLXMLWriter writer;

    private OWLOntology ontology;


    public OWLXMLObjectRenderer(OWLOntology ontology, OWLXMLWriter writer) {
        this.writer = writer;
        this.ontology = ontology;
    }


    public OWLXMLObjectRenderer(OWLXMLWriter writer) {
        this.writer = writer;
        this.ontology = null;
    }


    private void writeAnnotations(OWLAxiom axiom) {
        if (ontology != null) {
            for (OWLAnnotationAxiom ax : axiom.getAnnotationAxioms(ontology)) {
                ax.accept(this);
            }
        }
    }


    public void visit(OWLOntology ontology) {
        for (OWLImportsDeclaration decl : ontology.getImportsDeclarations()) {
            decl.accept(this);
        }
        for (OWLOntologyAnnotationAxiom ax : ontology.getAnnotations(ontology)) {
            ax.accept(this);
        }
        for (OWLAxiom ax : new TreeSet<OWLAxiom>(ontology.getAxioms())) {
            if (!(ax instanceof OWLAxiomAnnotationAxiom) && !(ax instanceof OWLOntologyAnnotationAxiom)) {
                ax.accept(this);
            }
        }
    }


    public void visit(OWLConstantAnnotation annotation) {
        writer.writeStartElement(ANNOTATION.getURI());
        writer.writeAnnotationURIAttribute(annotation.getAnnotationURI());
        writer.writeStartElement(CONSTANT.getURI());
        if (annotation.getAnnotationValue().isTyped()) {
            writer.writeDatatypeAttribute(((OWLTypedLiteral) annotation.getAnnotationValue()).getDataType().getURI());
        }
        writer.writeTextContent(annotation.getAnnotationValue().getString());
        writer.writeEndElement();
        writer.writeEndElement();
    }


    public void visit(OWLObjectAnnotation annotation) {
        writer.writeStartElement(ANNOTATION.getURI());
        writer.writeAnnotationURIAttribute(annotation.getAnnotationURI());
        annotation.getAnnotationValue().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        writer.writeStartElement(ASYMMETRIC_OBJECT_PROPERTY.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
//        writer.writeStartElement(ANNOTATION.getURI());
//        writer.writeAnnotationURIAttribute(axiom.getAnnotation().getAnnotationURI());
//        writeAnnotations(axiom);
        axiom.getAnnotation().accept(this);
//        writer.writeEndElement();
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        writer.writeStartElement(CLASS_ASSERTION.getURI());
        writeAnnotations(axiom);
        axiom.getDescription().accept(this);
        axiom.getIndividual().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_ASSERTION.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writeAnnotations(axiom);
        writer.writeStartElement(DATA_PROPERTY_DOMAIN.getURI());
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writeAnnotations(axiom);
        writer.writeStartElement(DATA_PROPERTY_RANGE.getURI());
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        writeAnnotations(axiom);
        writer.writeStartElement(SUB_DATA_PROPERTY_OF.getURI());
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDeclarationAxiom axiom) {
        writeAnnotations(axiom);
        writer.writeStartElement(DECLARATION.getURI());
        writeAnnotations(axiom);
        axiom.getEntity().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        writer.writeStartElement(DIFFERENT_INDIVIDUALS.getURI());
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        writer.writeEndElement();
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        writer.writeStartElement(DISJOINT_CLASSES.getURI());
        writeAnnotations(axiom);
        render(axiom.getDescriptions());
        writer.writeEndElement();
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_DATA_PROPERTIES.getURI());
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_OBJECT_PROPERTIES.getURI());
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        writer.writeStartElement(DISJOINT_UNION.getURI());
        writeAnnotations(axiom);
        axiom.getOWLClass().accept(this);
        writer.writeStartElement(UNION_OF.getURI());
        render(axiom.getDescriptions());
        writer.writeEndElement();
        writer.writeEndElement();
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        // Get written out with declarations
        // Not anymore!
        // This is not in the spec
        writer.writeStartElement(ENTITY_ANNOTATION.getURI());
        writeAnnotations(axiom);
        axiom.getSubject().accept(this);
        axiom.getAnnotation().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_CLASSES.getURI());
        writeAnnotations(axiom);
        render(axiom.getDescriptions());
        writer.writeEndElement();
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_DATA_PROPERTIES.getURI());
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_OBJECT_PROPERTIES.getURI());
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writer.writeStartElement(FUNCTIONAL_DATA_PROPERTY.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writer.writeStartElement(FUNCTIONAL_OBJECT_PROPERTY.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLImportsDeclaration axiom) {
        writer.writeStartElement(IMPORTS.getURI());
        writeAnnotations(axiom);
        writer.writeTextContent(axiom.getImportedOntologyURI().toString());
        writer.writeEndElement();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writer.writeStartElement(INVERSE_FUNCTIONAL_OBJECT_PROPERTY.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writer.writeStartElement(INVERSE_OBJECT_PROPERTIES.getURI());
        writeAnnotations(axiom);
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(IRREFLEXIVE_OBJECT_PROPERTY.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        writer.writeStartElement(NEGATIVE_DATA_PROPERTY_ASSERTION.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writer.writeStartElement(NEGATIVE_OBJECT_PROPERTY_ASSERTION.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_ASSERTION.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        writer.writeStartElement(SUB_OBJECT_PROPERTY_OF.getURI());
        writeAnnotations(axiom);
        writer.writeStartElement(SUB_OBJECT_PROPERTY_CHAIN.getURI());
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        writer.writeEndElement();
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_DOMAIN.getURI());
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_RANGE.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        writer.writeStartElement(SUB_OBJECT_PROPERTY_OF.getURI());
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(REFLEXIVE_OBJECT_PROPERTY.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        writer.writeStartElement(SAME_INDIVIDUALS.getURI());
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        writer.writeEndElement();
    }


    public void visit(OWLSubClassAxiom axiom) {
        writer.writeStartElement(SUB_CLASS_OF.getURI());
        writeAnnotations(axiom);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writer.writeStartElement(SYMMETRIC_OBJECT_PROPERTY.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(TRANSITIVE_OBJECT_PROPERTY.getURI());
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLClass desc) {
        writer.writeStartElement(CLASS.getURI());
        writer.writeNameAttribute(desc.getURI());
        writer.writeEndElement();
    }


    public void visit(OWLDataAllRestriction desc) {
        writer.writeStartElement(DATA_ALL_VALUES_FROM.getURI());
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
        writer.writeStartElement(DATA_EXACT_CARDINALITY.getURI());
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
        writer.writeStartElement(DATA_MAX_CARDINALITY.getURI());
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
        writer.writeStartElement(DATA_MIN_CARDINALITY.getURI());
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLDataSomeRestriction desc) {
        writer.writeStartElement(DATA_SOME_VALUES_FROM.getURI());
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataValueRestriction desc) {
        writer.writeStartElement(DATA_HAS_VALUE.getURI());
        desc.getProperty().accept(this);
        desc.getValue().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectAllRestriction desc) {
        writer.writeStartElement(OBJECT_ALL_VALUES_FROM.getURI());
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectComplementOf desc) {
        writer.writeStartElement(OBJECT_COMPLEMENT_OF.getURI());
        desc.getOperand().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {
        writer.writeStartElement(OBJECT_EXACT_CARDINALITY.getURI());
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLObjectIntersectionOf desc) {
        writer.writeStartElement(OBJECT_INTERSECTION_OF.getURI());
        render(desc.getOperands());
        writer.writeEndElement();
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
        writer.writeStartElement(OBJECT_MAX_CARDINALITY.getURI());
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        writer.writeStartElement(OBJECT_MIN_CARDINALITY.getURI());
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLObjectOneOf desc) {
        writer.writeStartElement(OBJECT_ONE_OF.getURI());
        render(desc.getIndividuals());
        writer.writeEndElement();
    }


    public void visit(OWLObjectSelfRestriction desc) {
        writer.writeStartElement(OBJECT_EXISTS_SELF.getURI());
        desc.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectSomeRestriction desc) {
        writer.writeStartElement(OBJECT_SOME_VALUES_FROM.getURI());
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectUnionOf desc) {
        writer.writeStartElement(OBJECT_UNION_OF.getURI());
        render(desc.getOperands());
        writer.writeEndElement();
    }


    public void visit(OWLObjectValueRestriction desc) {
        writer.writeStartElement(OBJECT_HAS_VALUE.getURI());
        desc.getProperty().accept(this);
        desc.getValue().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataComplementOf node) {
        writer.writeStartElement(DATA_COMPLEMENT_OF.getURI());
        node.getDataRange().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataOneOf node) {
        writer.writeStartElement(DATA_ONE_OF.getURI());
        render(node.getValues());
        writer.writeEndElement();
    }


    public void visit(OWLDataType node) {
        writer.writeStartElement(DATATYPE.getURI());
        writer.writeNameAttribute(node.getURI());
        writer.writeEndElement();
    }


    public void visit(OWLDataRangeRestriction node) {
        // TODO: Fix this when added to spec
        writer.writeStartElement(DATATYPE_RESTRICTION.getURI());
        node.getDataRange().accept(this);
        for (OWLDataRangeFacetRestriction restriction : node.getFacetRestrictions()) {
            restriction.accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLDataRangeFacetRestriction node) {
        // TODO: Fix this when added to spec
        writer.writeStartElement(DATATYPE_FACET_RESTRICTION.getURI());
        writer.writeFacetAttribute(node.getFacet().getURI());
        node.getFacetValue().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLTypedLiteral node) {
        writer.writeStartElement(CONSTANT.getURI());
        writer.writeDatatypeAttribute(node.getDataType().getURI());
        writer.writeTextContent(node.getString());
        writer.writeEndElement();
    }


    public void visit(OWLRDFTextLiteral node) {
        writer.writeStartElement(CONSTANT.getURI());
        // TODO: Add in lang when added to spec
//        writer.writeDatatypeAttribute(node.getLang());
        writer.writeTextContent(node.getString());
        writer.writeEndElement();
    }


    public void visit(OWLDataProperty property) {
        writer.writeStartElement(DATA_PROPERTY.getURI());
        writer.writeNameAttribute(property.getURI());
        writer.writeEndElement();
    }


    public void visit(OWLObjectProperty property) {
        writer.writeStartElement(OBJECT_PROPERTY.getURI());
        writer.writeNameAttribute(property.getURI());
        writer.writeEndElement();
    }


    public void visit(OWLObjectPropertyInverse property) {
        writer.writeStartElement(INVERSE_OBJECT_PROPERTY.getURI());
        property.getInverse().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLIndividual individual) {
        writer.writeStartElement(INDIVIDUAL.getURI());
        writer.writeNameAttribute(individual.getURI());
        writer.writeEndElement();
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
//        writer.writeStartElement(ANNOTATION.getURI());
//        writer.writeAnnotationURIAttribute(axiom.getAnnotation().getAnnotationURI());
        axiom.getAnnotation().accept(this);
//        writer.writeEndElement();
    }


    public void visit(SWRLRule rule) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLClassAtom node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLDataRangeAtom node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLObjectPropertyAtom node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLDataValuedPropertyAtom node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLBuiltInAtom node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLAtomDVariable node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLAtomIVariable node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLAtomIndividualObject node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLAtomConstantObject node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLDifferentFromAtom node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    public void visit(SWRLSameAsAtom node) {
        throw new OWLRuntimeException("NOT IMPLEMENTED!");
    }


    private void render(Set<? extends OWLObject> objects) {
        for (OWLObject obj : objects) {
            obj.accept(this);
        }
    }
}
