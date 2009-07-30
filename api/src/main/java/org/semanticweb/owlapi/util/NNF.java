package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2008, University of Manchester
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


    public OWLDataRange visit(OWLTypedLiteral node) {
        return null;
    }


    public OWLDataRange visit(OWLStringLiteral node) {
        return null;
    }


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