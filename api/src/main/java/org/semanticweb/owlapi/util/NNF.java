package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
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
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 06-Jun-2008<br><br>
 */
public class NNF implements OWLClassExpressionVisitorEx<OWLClassExpression>, OWLDataVisitorEx<OWLDataRange>, OWLAxiomVisitorEx<OWLAxiom> {

    private boolean negated;

    private OWLDataFactory dataFactory;


    public NNF(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }


    public void reset() {
        negated = false;
    }


    private OWLClassExpression getNegation(OWLClassExpression classExpression) {
        return dataFactory.getOWLObjectComplementOf(classExpression);
    }


    public OWLClassExpression visit(OWLClass desc) {
        if (negated) {
            if(desc.isOWLNothing()) {
                return dataFactory.getOWLThing();
            }
            else if(desc.isOWLThing()) {
                return dataFactory.getOWLNothing();
            }
            else {
                return getNegation(desc);    
            }

        } else {
            return desc;
        }
    }


    public OWLClassExpression visit(OWLObjectIntersectionOf desc) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : desc.getOperands()) {
            ops.add(op.accept(this));
        }
        if (negated) {
            return dataFactory.getOWLObjectUnionOf(ops);
        } else {
            return dataFactory.getOWLObjectIntersectionOf(ops);
        }
    }


    public OWLClassExpression visit(OWLObjectUnionOf desc) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : desc.getOperands()) {
            ops.add(op.accept(this));
        }
        if (negated) {
            // Flip to an intersection
            return dataFactory.getOWLObjectIntersectionOf(ops);
        } else {
            return dataFactory.getOWLObjectUnionOf(ops);
        }
    }


    public OWLClassExpression visit(OWLObjectComplementOf desc) {
        if (negated) {
            // Cancels out.
            // Save and then restore.
            boolean neg = negated;
            negated = false;
            OWLClassExpression negDesc = desc.getOperand().accept(this);
            negated = neg;
            return negDesc;
        } else {
            // Save and then restore
            boolean neg = negated;
            negated = true;
            OWLClassExpression negDesc = desc.getOperand().accept(this);
            negated = neg;
            return negDesc;
        }
    }


    public OWLClassExpression visit(OWLObjectSomeValuesFrom desc) {
        OWLClassExpression filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLObjectAllValuesFrom(desc.getProperty(), filler);
        } else {
            return dataFactory.getOWLObjectSomeValuesFrom(desc.getProperty(), filler);
        }
    }


    public OWLClassExpression visit(OWLObjectAllValuesFrom desc) {
        OWLClassExpression filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLObjectSomeValuesFrom(desc.getProperty(), filler);
        } else {
            return dataFactory.getOWLObjectAllValuesFrom(desc.getProperty(), filler);
        }
    }


    public OWLClassExpression visit(OWLObjectHasValue desc) {
        return desc.asSomeValuesFrom().accept(this);
    }


    public OWLClassExpression visit(OWLObjectMinCardinality desc) {
        boolean neg = negated;
        int card = desc.getCardinality();
        if (negated) {
            card = desc.getCardinality() - 1;
            if (card < 0) {
                card = 0;
            }
        }
        negated = false;
        OWLClassExpression filler = desc.getFiller().accept(this);
        OWLClassExpression nnf = null;
        if (neg) {
            nnf = dataFactory.getOWLObjectMaxCardinality(card, desc.getProperty(), filler);
        } else {
            nnf = dataFactory.getOWLObjectMinCardinality(card, desc.getProperty(), filler);
        }
        negated = neg;
        return nnf;
    }


    public OWLClassExpression visit(OWLObjectExactCardinality desc) {
        return desc.asIntersectionOfMinMax().accept(this);
    }


    public OWLClassExpression visit(OWLObjectMaxCardinality desc) {
        boolean neg = negated;
        int card = desc.getCardinality();
        if (negated) {
            card = desc.getCardinality() + 1;
        }
        negated = false;
        OWLClassExpression filler = desc.getFiller().accept(this);
        OWLClassExpression nnf = null;
        if (neg) {
            nnf = dataFactory.getOWLObjectMinCardinality(card, desc.getProperty(), filler);
        } else {
            nnf = dataFactory.getOWLObjectMaxCardinality(card, desc.getProperty(), filler);
        }
        negated = neg;
        return nnf;
    }


    public OWLClassExpression visit(OWLObjectHasSelf desc) {
        if (negated) {
            return getNegation(desc);
        } else {
            return desc;
        }
    }


    public OWLClassExpression visit(OWLObjectOneOf desc) {
        if (desc.getIndividuals().size() == 1) {
            if (negated) {
                return getNegation(desc);
            } else {
                return desc;
            }
        } else {
            return desc.asObjectUnionOf().accept(this);
        }
    }


    public OWLClassExpression visit(OWLDataSomeValuesFrom desc) {
        OWLDataRange filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLDataAllValuesFrom(desc.getProperty(), filler);
        } else {
            return dataFactory.getOWLDataSomeValuesFrom(desc.getProperty(), filler);
        }
    }


    public OWLClassExpression visit(OWLDataAllValuesFrom desc) {
        OWLDataRange filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLDataSomeValuesFrom(desc.getProperty(), filler);
        } else {
            return dataFactory.getOWLDataAllValuesFrom(desc.getProperty(), filler);
        }
    }


    public OWLClassExpression visit(OWLDataHasValue desc) {
        return desc.asSomeValuesFrom().accept(this);
    }


    public OWLClassExpression visit(OWLDataExactCardinality desc) {
        return desc.asIntersectionOfMinMax().accept(this);
    }

    public OWLClassExpression visit(OWLDataMaxCardinality desc) {
        boolean neg = negated;
        int card = desc.getCardinality();
        if (negated) {
            card = desc.getCardinality() + 1;
        }
        negated = false;
        OWLDataRange filler = desc.getFiller().accept(this);
        OWLClassExpression nnf = null;
        if (neg) {
            nnf = dataFactory.getOWLDataMinCardinality(card, desc.getProperty(), filler);
        } else {
            nnf = dataFactory.getOWLDataMaxCardinality(card, desc.getProperty(), filler);
        }
        negated = neg;
        return nnf;
    }


    public OWLClassExpression visit(OWLDataMinCardinality desc) {
        boolean neg = negated;
        int card = desc.getCardinality();
        if (negated) {
            card = desc.getCardinality() - 1;
            if (card < 0) {
                card = 0;
            }
        }
        negated = false;
        OWLDataRange filler = desc.getFiller().accept(this);
        OWLClassExpression nnf = null;
        if (neg) {
            nnf = dataFactory.getOWLDataMaxCardinality(card, desc.getProperty(), filler);
        } else {
            nnf = dataFactory.getOWLDataMinCardinality(card, desc.getProperty(), filler);
        }
        negated = neg;
        return nnf;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////


    public OWLDataRange visit(OWLDatatype node) {
        if (negated) {
            return dataFactory.getOWLDataComplementOf(node);
        } else {
            return node;
        }
    }


    public OWLDataRange visit(OWLDataComplementOf node) {
        if (negated) {
            return node.getDataRange();
        } else {
            return node;
        }
    }


    public OWLDataRange visit(OWLDataOneOf node) {
        if (node.getValues().size() == 1) {
            if (negated) {
                return dataFactory.getOWLDataComplementOf(node);
            } else {
                return node;
            }
        } else {
            // Encode as a data union of and return result
            Set<OWLDataOneOf> oneOfs = new HashSet<OWLDataOneOf>();
            for (OWLLiteral lit : node.getValues()) {
                oneOfs.add(dataFactory.getOWLDataOneOf(lit));
            }
            return dataFactory.getOWLDataUnionOf(oneOfs).accept(this);
        }

    }

    public OWLDataRange visit(OWLDataIntersectionOf node) {
        Set<OWLDataRange> ops = new HashSet<OWLDataRange>();
        for (OWLDataRange op : node.getOperands()) {
            ops.add(op.accept(this));
        }
        if (negated) {
            return dataFactory.getOWLDataUnionOf(ops);
        } else {
            return dataFactory.getOWLDataIntersectionOf(ops);
        }
    }

    public OWLDataRange visit(OWLDataUnionOf node) {
        Set<OWLDataRange> ops = new HashSet<OWLDataRange>();
        for (OWLDataRange op : node.getOperands()) {
            ops.add(op.accept(this));
        }
        if (negated) {
            // Flip to an intersection
            return dataFactory.getOWLDataIntersectionOf(ops);
        } else {
            return dataFactory.getOWLDataUnionOf(ops);
        }
    }
    @SuppressWarnings("unused")
    public OWLAxiom visit(OWLHasKeyAxiom axiom) {
        return null;
    }

    public OWLDataRange visit(OWLDatatypeRestriction node) {
        if (negated) {
            return dataFactory.getOWLDataComplementOf(node);
        } else {
            return node;
        }
    }
    @SuppressWarnings("unused")
    public OWLDataRange visit(OWLLiteral node) {
        return null;
    }
    @SuppressWarnings("unused")
    public OWLDataRange visit(OWLFacetRestriction node) {
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Conversion of non-class expressions to NNF
    //
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public OWLAxiom visit(OWLSubClassOfAxiom axiom) {
        return dataFactory.getOWLSubClassOfAxiom(axiom.getSubClass().accept(this),
                axiom.getSuperClass().accept(this));
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


    public OWLAxiom visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDisjointClassesAxiom(ops);
    }


    public OWLAxiom visit(OWLDataPropertyDomainAxiom axiom) {
        return dataFactory.getOWLDataPropertyDomainAxiom(axiom.getProperty(),
                axiom.getDomain().accept(this));
    }

    public OWLAxiom visit(OWLObjectPropertyDomainAxiom axiom) {
        return dataFactory.getOWLObjectPropertyDomainAxiom(axiom.getProperty(),
                axiom.getDomain().accept(this));
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


    public OWLAxiom visit(OWLObjectPropertyRangeAxiom axiom) {
        return dataFactory.getOWLObjectPropertyRangeAxiom(axiom.getProperty(),
                axiom.getRange().accept(this));
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


    public OWLAxiom visit(OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            descs.add(op.accept(this));
        }
        return dataFactory.getOWLDisjointUnionAxiom(axiom.getOWLClass(), descs);
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


    public OWLAxiom visit(OWLDataPropertyRangeAxiom axiom) {
        return dataFactory.getOWLDataPropertyRangeAxiom(axiom.getProperty(),
                axiom.getRange().accept(this));
    }


    public OWLAxiom visit(OWLFunctionalDataPropertyAxiom axiom) {
        return axiom;
    }


    public OWLAxiom visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return axiom;
    }


    public OWLAxiom visit(OWLClassAssertionAxiom axiom) {
        if (axiom.getClassExpression().isAnonymous()) {
            return dataFactory.getOWLClassAssertionAxiom(axiom.getClassExpression().accept(this), axiom.getIndividual());
        } else {
            return axiom;
        }
    }


    public OWLAxiom visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLEquivalentClassesAxiom(ops);
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
}