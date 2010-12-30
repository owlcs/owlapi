package org.coode.owlapi.owlxml.renderer;

import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import java.util.ArrayList;
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
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 12-Dec-2006<br><br>
 */
public class OWLXMLObjectRenderer implements OWLObjectVisitor {

    private OWLXMLWriter writer;

    //private OWLOntology ontology;


    //public OWLXMLObjectRenderer(OWLOntology ontology, OWLXMLWriter writer) {
        //this.writer = writer;
        //this.ontology = ontology;
    //}


    public OWLXMLObjectRenderer(OWLXMLWriter writer) {
        this.writer = writer;
        //this.ontology = null;
    }

    private void writeAnnotations(OWLAxiom axiom) {
        for (OWLAnnotation anno : axiom.getAnnotations()) {
            anno.accept(this);
        }
    }

    public void visit(OWLOntology ontology) {
        for (OWLImportsDeclaration decl : ontology.getImportsDeclarations()) {
            writer.writeStartElement(IMPORT);
            writer.writeTextContent(decl.getURI().toString());
            writer.writeEndElement();
        }
        for (OWLAnnotation annotation : ontology.getAnnotations()) {
            annotation.accept(this);
        }

        List<OWLAxiom> axioms = new ArrayList<OWLAxiom>(ontology.getAxioms());
        Collections.sort(axioms);
        for (OWLAxiom ax : axioms) {
            ax.accept(this);
        }
    }

    public void visit(IRI iri) {
        writer.writeIRIElement(iri);
    }

    public void visit(OWLAnonymousIndividual individual) {
        writer.writeStartElement(ANONYMOUS_INDIVIDUAL);
        writer.writeNodeIDAttribute(individual.getID());
        writer.writeEndElement();
    }


    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        writer.writeStartElement(ASYMMETRIC_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }

    public void visit(OWLClassAssertionAxiom axiom) {
        writer.writeStartElement(CLASS_ASSERTION);
        writeAnnotations(axiom);
        axiom.getClassExpression().accept(this);
        axiom.getIndividual().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_DOMAIN);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        writer.writeStartElement(DATA_PROPERTY_RANGE);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_DATA_PROPERTY_OF);
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDeclarationAxiom axiom) {
        writer.writeStartElement(DECLARATION);
        writeAnnotations(axiom);
        axiom.getEntity().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        writer.writeStartElement(DIFFERENT_INDIVIDUALS);
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        writer.writeEndElement();
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        writer.writeStartElement(DISJOINT_CLASSES);
        writeAnnotations(axiom);
        render(axiom.getClassExpressions());
        writer.writeEndElement();
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_DATA_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        writer.writeStartElement(DISJOINT_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        writer.writeStartElement(DISJOINT_UNION);
        writeAnnotations(axiom);
        axiom.getOWLClass().accept(this);
        render(axiom.getClassExpressions());
        writer.writeEndElement();
    }


    public void visit(OWLAnnotationAssertionAxiom axiom) {
        writer.writeStartElement(ANNOTATION_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getValue().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_CLASSES);
        writeAnnotations(axiom);
        render(axiom.getClassExpressions());
        writer.writeEndElement();
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_DATA_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        writer.writeStartElement(EQUIVALENT_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        render(axiom.getProperties());
        writer.writeEndElement();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        writer.writeStartElement(FUNCTIONAL_DATA_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        writer.writeStartElement(FUNCTIONAL_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        writer.writeStartElement(INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        writer.writeStartElement(INVERSE_OBJECT_PROPERTIES);
        writeAnnotations(axiom);
        axiom.getFirstProperty().accept(this);
        axiom.getSecondProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(IRREFLEXIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        writer.writeStartElement(NEGATIVE_DATA_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        writer.writeStartElement(NEGATIVE_OBJECT_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_ASSERTION);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getSubject().accept(this);
        axiom.getObject().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        writer.writeStartElement(SUB_OBJECT_PROPERTY_OF);
        writeAnnotations(axiom);
        writer.writeStartElement(OBJECT_PROPERTY_CHAIN);
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        writer.writeEndElement();
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_DOMAIN);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        writer.writeStartElement(OBJECT_PROPERTY_RANGE);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_OBJECT_PROPERTY_OF);
        writeAnnotations(axiom);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(REFLEXIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLSameIndividualAxiom axiom) {
        writer.writeStartElement(SAME_INDIVIDUAL);
        writeAnnotations(axiom);
        render(axiom.getIndividuals());
        writer.writeEndElement();
    }


    public void visit(OWLSubClassOfAxiom axiom) {
        writer.writeStartElement(SUB_CLASS_OF);
        writeAnnotations(axiom);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        writer.writeStartElement(SYMMETRIC_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        writer.writeStartElement(TRANSITIVE_OBJECT_PROPERTY);
        writeAnnotations(axiom);
        axiom.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLClass desc) {
        writer.writeStartElement(CLASS);
        writer.writeIRIAttribute(desc.getIRI());
        writer.writeEndElement();
    }


    public void visit(OWLDataAllValuesFrom desc) {
        writer.writeStartElement(DATA_ALL_VALUES_FROM);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataExactCardinality desc) {
        writer.writeStartElement(DATA_EXACT_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLDataMaxCardinality desc) {
        writer.writeStartElement(DATA_MAX_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLDataMinCardinality desc) {
        writer.writeStartElement(DATA_MIN_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLDataSomeValuesFrom desc) {
        writer.writeStartElement(DATA_SOME_VALUES_FROM);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataHasValue desc) {
        writer.writeStartElement(DATA_HAS_VALUE);
        desc.getProperty().accept(this);
        desc.getValue().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectAllValuesFrom desc) {
        writer.writeStartElement(OBJECT_ALL_VALUES_FROM);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectComplementOf desc) {
        writer.writeStartElement(OBJECT_COMPLEMENT_OF);
        desc.getOperand().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectExactCardinality desc) {
        writer.writeStartElement(OBJECT_EXACT_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLObjectIntersectionOf desc) {
        writer.writeStartElement(OBJECT_INTERSECTION_OF);
        render(desc.getOperands());
        writer.writeEndElement();
    }


    public void visit(OWLObjectMaxCardinality desc) {
        writer.writeStartElement(OBJECT_MAX_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLObjectMinCardinality desc) {
        writer.writeStartElement(OBJECT_MIN_CARDINALITY);
        writer.writeCardinalityAttribute(desc.getCardinality());
        desc.getProperty().accept(this);
        if (desc.isQualified()) {
            desc.getFiller().accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(OWLObjectOneOf desc) {
        writer.writeStartElement(OBJECT_ONE_OF);
        render(desc.getIndividuals());
        writer.writeEndElement();
    }


    public void visit(OWLObjectHasSelf desc) {
        writer.writeStartElement(OBJECT_HAS_SELF);
        desc.getProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        writer.writeStartElement(OBJECT_SOME_VALUES_FROM);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLObjectUnionOf desc) {
        writer.writeStartElement(OBJECT_UNION_OF);
        render(desc.getOperands());
        writer.writeEndElement();
    }


    public void visit(OWLObjectHasValue desc) {
        writer.writeStartElement(OBJECT_HAS_VALUE);
        desc.getProperty().accept(this);
        desc.getValue().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataComplementOf node) {
        writer.writeStartElement(DATA_COMPLEMENT_OF);
        node.getDataRange().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDataOneOf node) {
        writer.writeStartElement(DATA_ONE_OF);
        render(node.getValues());
        writer.writeEndElement();
    }


    public void visit(OWLDatatype node) {
        writer.writeStartElement(DATATYPE);
        writer.writeIRIAttribute(node.getIRI());
        writer.writeEndElement();
    }


    public void visit(OWLDatatypeRestriction node) {
        writer.writeStartElement(DATATYPE_RESTRICTION);
        node.getDatatype().accept(this);
        render(node.getFacetRestrictions());
        writer.writeEndElement();
    }


    public void visit(OWLFacetRestriction node) {
        writer.writeStartElement(FACET_RESTRICTION);
        writer.writeFacetAttribute(node.getFacet());
        node.getFacetValue().accept(this);
        writer.writeEndElement();
    }

    public void visit(OWLLiteral node) {
        writer.writeStartElement(LITERAL);
        if (node.hasLang()) {
            writer.writeLangAttribute(node.getLang());
        }
        writer.writeDatatypeAttribute(node.getDatatype());
        writer.writeTextContent(node.getLiteral());
        writer.writeEndElement();
    }

    public void visit(OWLDataProperty property) {
        writer.writeStartElement(DATA_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }


    public void visit(OWLObjectProperty property) {
        writer.writeStartElement(OBJECT_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }


    public void visit(OWLObjectInverseOf property) {
        writer.writeStartElement(OBJECT_INVERSE_OF);
        property.getInverse().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLNamedIndividual individual) {
        writer.writeStartElement(NAMED_INDIVIDUAL);
        writer.writeIRIAttribute(individual.getIRI());
        writer.writeEndElement();
    }

    public void visit(OWLHasKeyAxiom axiom) {
        writer.writeStartElement(HAS_KEY);
        writeAnnotations(axiom);
        axiom.getClassExpression().accept(this);
        render(axiom.getObjectPropertyExpressions());
        render(axiom.getDataPropertyExpressions());
        writer.writeEndElement();
    }

    public void visit(OWLDataIntersectionOf node) {
        writer.writeStartElement(DATA_INTERSECTION_OF);
        render(node.getOperands());
        writer.writeEndElement();
    }

    public void visit(OWLDataUnionOf node) {
        writer.writeStartElement(DATA_UNION_OF);
        render(node.getOperands());
        writer.writeEndElement();
    }

    public void visit(OWLAnnotationProperty property) {
        writer.writeStartElement(ANNOTATION_PROPERTY);
        writer.writeIRIAttribute(property.getIRI());
        writer.writeEndElement();
    }

    public void visit(OWLAnnotation annotation) {
        writer.writeStartElement(ANNOTATION);
        for (OWLAnnotation anno : annotation.getAnnotations()) {
            anno.accept(this);
        }
        annotation.getProperty().accept(this);
        annotation.getValue().accept(this);
        writer.writeEndElement();
    }

    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        writer.writeStartElement(ANNOTATION_PROPERTY_DOMAIN);
        axiom.getProperty().accept(this);
        axiom.getDomain().accept(this);
        writer.writeEndElement();
    }

    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        writer.writeStartElement(ANNOTATION_PROPERTY_RANGE);
        axiom.getProperty().accept(this);
        axiom.getRange().accept(this);
        writer.writeEndElement();
    }

    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        writer.writeStartElement(SUB_ANNOTATION_PROPERTY_OF);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
        writer.writeEndElement();
    }


    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        writer.writeStartElement(DATATYPE_DEFINITION);
        axiom.getDatatype().accept(this);
        axiom.getDataRange().accept(this);
        writer.writeEndElement();
    }


    public void visit(SWRLRule rule) {
        writer.writeStartElement(DL_SAFE_RULE);
        writeAnnotations(rule);
        writer.writeStartElement(BODY);
        for (SWRLAtom atom : rule.getBody()) {
            atom.accept(this);
        }
        writer.writeEndElement();
        writer.writeStartElement(HEAD);
        for (SWRLAtom atom : rule.getHead()) {
            atom.accept(this);
        }
        writer.writeEndElement();
        writer.writeEndElement();
    }


    public void visit(SWRLClassAtom node) {
        writer.writeStartElement(CLASS_ATOM);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
        writer.writeEndElement();
    }


    public void visit(SWRLDataRangeAtom node) {
        writer.writeStartElement(DATA_RANGE_ATOM);
        node.getPredicate().accept(this);
        node.getArgument().accept(this);
        writer.writeEndElement();
    }


    public void visit(SWRLObjectPropertyAtom node) {
        writer.writeStartElement(OBJECT_PROPERTY_ATOM);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        writer.writeEndElement();
    }


    public void visit(SWRLDataPropertyAtom node) {
        writer.writeStartElement(DATA_PROPERTY_ATOM);
        node.getPredicate().accept(this);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        writer.writeEndElement();
    }


    public void visit(SWRLBuiltInAtom node) {
        writer.writeStartElement(BUILT_IN_ATOM);
        writer.writeIRIAttribute(node.getPredicate());
        for (SWRLDArgument arg : node.getArguments()) {
            arg.accept(this);
        }
        writer.writeEndElement();
    }


    public void visit(SWRLVariable node) {
        writer.writeStartElement(VARIABLE);
        writer.writeIRIAttribute(node.getIRI());
        writer.writeEndElement();
    }


    public void visit(SWRLIndividualArgument node) {
        node.getIndividual().accept(this);
    }


    public void visit(SWRLLiteralArgument node) {
        node.getLiteral().accept(this);
    }


    public void visit(SWRLDifferentIndividualsAtom node) {
        writer.writeStartElement(DIFFERENT_INDIVIDUALS_ATOM);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        writer.writeEndElement();
    }


    public void visit(SWRLSameIndividualAtom node) {
        writer.writeStartElement(SAME_INDIVIDUAL_ATOM);
        node.getFirstArgument().accept(this);
        node.getSecondArgument().accept(this);
        writer.writeEndElement();
    }


    private void render(Set<? extends OWLObject> objects) {
        for (OWLObject obj : objects) {
            obj.accept(this);
        }
    }
}
