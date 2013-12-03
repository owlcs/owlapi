package org.obolibrary.macro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.obolibrary.obo2owl.Obo2OWLConstants.Obo2OWLVocabulary;
import org.semanticweb.owlapi.model.*;

/** Empty abstract visitor for macro expansion. This class allows to minimize the
 * code in the actual visitors, as they only need to overwrite the relevant
 * methods. */
abstract class AbstractMacroExpansionVisitor implements
        OWLClassExpressionVisitorEx<OWLClassExpression>, OWLDataVisitorEx<OWLDataRange>,
        OWLAxiomVisitorEx<OWLAxiom> {
    final Logger log;
    final OWLDataFactory dataFactory;
    final Map<IRI, String> expandAssertionToMap;
    final Map<IRI, String> expandExpressionMap;

    AbstractMacroExpansionVisitor(OWLOntology inputOntology, Logger log) {
        super();
        this.log = log;
        dataFactory = inputOntology.getOWLOntologyManager().getOWLDataFactory();
        expandExpressionMap = new HashMap<IRI, String>();
        expandAssertionToMap = new HashMap<IRI, String>();
        OWLAnnotationProperty expandExpressionAP = dataFactory
                .getOWLAnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0000424.getIRI());
        OWLAnnotationProperty expandAssertionAP = dataFactory
                .getOWLAnnotationProperty(Obo2OWLVocabulary.IRI_IAO_0000425.getIRI());
        for (OWLObjectProperty p : inputOntology.getObjectPropertiesInSignature()) {
            for (OWLAnnotation a : p.getAnnotations(inputOntology, expandExpressionAP)) {
                OWLAnnotationValue v = a.getValue();
                if (v instanceof OWLLiteral) {
                    String str = ((OWLLiteral) v).getLiteral();
                    if (log.isLoggable(Level.WARNING)) {
                        log.log(Level.WARNING, "mapping " + p + " to " + str);
                    }
                    expandExpressionMap.put(p.getIRI(), str);
                }
            }
        }
        for (OWLAnnotationProperty p : inputOntology.getAnnotationPropertiesInSignature()) {
            for (OWLAnnotation a : p.getAnnotations(inputOntology, expandAssertionAP)) {
                OWLAnnotationValue v = a.getValue();
                if (v instanceof OWLLiteral) {
                    String str = ((OWLLiteral) v).getLiteral();
                    if (log.isLoggable(Level.WARNING)) {
                        log.log(Level.WARNING, "assertion mapping " + p + " to " + str);
                    }
                    expandAssertionToMap.put(p.getIRI(), str);
                }
            }
        }
    }

    public OWLClassExpression visit(OWLObjectIntersectionOf desc) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : desc.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLObjectIntersectionOf(ops);
    }

    public OWLClassExpression visit(OWLObjectUnionOf desc) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : desc.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLObjectUnionOf(ops);
    }

    public OWLClassExpression visit(OWLObjectComplementOf desc) {
        return dataFactory.getOWLObjectComplementOf(desc.getOperand().accept(this));
    }

    public OWLClassExpression visit(OWLObjectSomeValuesFrom desc) {
        OWLClassExpression filler = desc.getFiller();
        OWLObjectPropertyExpression p = desc.getProperty();
        OWLClassExpression result = null;
        if (p instanceof OWLObjectProperty) {
            result = expandOWLObjSomeVal(filler, p);
        }
        if (result == null) {
            result = dataFactory.getOWLObjectSomeValuesFrom(desc.getProperty(),
                    filler.accept(this));
        }
        return result;
    }

    abstract OWLClassExpression expandOWLObjSomeVal(OWLClassExpression filler,
            OWLObjectPropertyExpression p);

    public OWLClassExpression visit(OWLObjectHasValue desc) {
        OWLClassExpression result = null;
        OWLIndividual filler = desc.getValue();
        OWLObjectPropertyExpression p = desc.getProperty();
        if (p instanceof OWLObjectProperty) {
            result = expandOWLObjHasVal(desc, filler, p);
        }
        if (result == null) {
            result = dataFactory.getOWLObjectHasValue(desc.getProperty(), filler);
        }
        return result;
    }

    abstract OWLClassExpression expandOWLObjHasVal(OWLObjectHasValue desc,
            OWLIndividual filler, OWLObjectPropertyExpression p);

    public OWLClassExpression visit(OWLObjectAllValuesFrom desc) {
        return desc.getFiller().accept(this);
    }

    public OWLClassExpression visit(OWLObjectMinCardinality desc) {
        OWLClassExpression filler = desc.getFiller().accept(this);
        return dataFactory.getOWLObjectMinCardinality(desc.getCardinality(),
                desc.getProperty(), filler);
    }

    public OWLClassExpression visit(OWLObjectExactCardinality desc) {
        return desc.asIntersectionOfMinMax().accept(this);
    }

    public OWLClassExpression visit(OWLObjectMaxCardinality desc) {
        OWLClassExpression filler = desc.getFiller().accept(this);
        return dataFactory.getOWLObjectMaxCardinality(desc.getCardinality(),
                desc.getProperty(), filler);
    }

    public OWLClassExpression visit(OWLDataSomeValuesFrom desc) {
        OWLDataRange filler = desc.getFiller().accept(this);
        return dataFactory.getOWLDataSomeValuesFrom(desc.getProperty(), filler);
    }

    public OWLClassExpression visit(OWLDataAllValuesFrom desc) {
        OWLDataRange filler = desc.getFiller().accept(this);
        return dataFactory.getOWLDataAllValuesFrom(desc.getProperty(), filler);
    }

    public OWLClassExpression visit(OWLDataHasValue desc) {
        return desc.asSomeValuesFrom().accept(this);
    }

    public OWLClassExpression visit(OWLDataExactCardinality desc) {
        return desc.asIntersectionOfMinMax().accept(this);
    }

    public OWLClassExpression visit(OWLDataMaxCardinality desc) {
        int card = desc.getCardinality();
        OWLDataRange filler = desc.getFiller().accept(this);
        return dataFactory.getOWLDataMaxCardinality(card, desc.getProperty(), filler);
    }

    public OWLClassExpression visit(OWLDataMinCardinality desc) {
        int card = desc.getCardinality();
        OWLDataRange filler = desc.getFiller().accept(this);
        return dataFactory.getOWLDataMinCardinality(card, desc.getProperty(), filler);
    }

    public OWLDataRange visit(OWLDataOneOf node) {
        // Encode as a data union of and return result
        Set<OWLDataOneOf> oneOfs = new HashSet<OWLDataOneOf>();
        for (OWLLiteral lit : node.getValues()) {
            oneOfs.add(dataFactory.getOWLDataOneOf(lit));
        }
        return dataFactory.getOWLDataUnionOf(oneOfs).accept(this);
    }

    public OWLDataRange visit(OWLDataIntersectionOf node) {
        Set<OWLDataRange> ops = new HashSet<OWLDataRange>();
        for (OWLDataRange op : node.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDataIntersectionOf(ops);
    }

    public OWLDataRange visit(OWLDataUnionOf node) {
        Set<OWLDataRange> ops = new HashSet<OWLDataRange>();
        for (OWLDataRange op : node.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDataUnionOf(ops);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Conversion of non-class expressions to MacroExpansionVisitor
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////
    public OWLAxiom visit(OWLSubClassOfAxiom axiom) {
        return dataFactory.getOWLSubClassOfAxiom(axiom.getSubClass().accept(this), axiom
                .getSuperClass().accept(this));
    }

    public OWLAxiom visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDisjointClassesAxiom(ops);
    }

    public OWLAxiom visit(OWLDataPropertyDomainAxiom axiom) {
        return dataFactory.getOWLDataPropertyDomainAxiom(axiom.getProperty(), axiom
                .getDomain().accept(this));
    }

    public OWLAxiom visit(OWLObjectPropertyDomainAxiom axiom) {
        return dataFactory.getOWLObjectPropertyDomainAxiom(axiom.getProperty(), axiom
                .getDomain().accept(this));
    }

    public OWLAxiom visit(OWLObjectPropertyRangeAxiom axiom) {
        return dataFactory.getOWLObjectPropertyRangeAxiom(axiom.getProperty(), axiom
                .getRange().accept(this));
    }

    public OWLAxiom visit(OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            descs.add(op.accept(this));
        }
        return dataFactory.getOWLDisjointUnionAxiom(axiom.getOWLClass(), descs);
    }

    public OWLAxiom visit(OWLDataPropertyRangeAxiom axiom) {
        return dataFactory.getOWLDataPropertyRangeAxiom(axiom.getProperty(), axiom
                .getRange().accept(this));
    }

    public OWLAxiom visit(OWLClassAssertionAxiom axiom) {
        if (axiom.getClassExpression().isAnonymous()) {
            return dataFactory.getOWLClassAssertionAxiom(axiom.getClassExpression()
                    .accept(this), axiom.getIndividual());
        }
        return axiom;
    }

    public OWLAxiom visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLEquivalentClassesAxiom(ops);
    }

    public OWLClassExpression visit(OWLClass desc) {
        return desc;
    }

    public OWLClassExpression visit(OWLObjectHasSelf desc) {
        return desc;
    }

    public OWLClassExpression visit(OWLObjectOneOf desc) {
        return desc;
    }

    public OWLDataRange visit(OWLDatatype node) {
        return node;
    }

    public OWLDataRange visit(OWLDataComplementOf node) {
        return node;
    }

    public OWLAxiom visit(OWLHasKeyAxiom axiom) {
        return axiom;
    }

    public OWLDataRange visit(OWLDatatypeRestriction node) {
        return node;
    }

    public OWLDataRange visit(OWLFacetRestriction node) {
        return null;
    }

    public OWLAxiom visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLDifferentIndividualsAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLDisjointDataPropertiesAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLObjectPropertyAssertionAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLSubObjectPropertyOfAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLDeclarationAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLAnnotationAssertionAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLFunctionalDataPropertyAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLDataPropertyAssertionAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLSubDataPropertyOfAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLSameIndividualAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLSubPropertyChainOfAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLInverseObjectPropertiesAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(SWRLRule rule) {
        return rule;
    }

    public OWLAxiom visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return axiom;
    }

    public OWLAxiom visit(OWLDatatypeDefinitionAxiom axiom) {
        return axiom;
    }

    public OWLDataRange visit(OWLLiteral node) {
        return null;
    }
}
