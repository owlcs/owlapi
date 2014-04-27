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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

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
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
public class NNF implements OWLClassExpressionVisitorEx<OWLClassExpression>,
        OWLDataVisitorEx<OWLDataRange>, OWLAxiomVisitorEx<OWLAxiom> {

    private boolean negated;
    private final OWLDataFactory dataFactory;

    /**
     * @param dataFactory
     *        datafactory to use
     */
    public NNF(@Nonnull OWLDataFactory dataFactory) {
        this.dataFactory = checkNotNull(dataFactory,
                "dataFactory cannot be null");
    }

    /** reset the negation. */
    public void reset() {
        negated = false;
    }

    @Nonnull
    private OWLClassExpression getNegation(
            @Nonnull OWLClassExpression classExpression) {
        return dataFactory.getOWLObjectComplementOf(classExpression);
    }

    @Override
    public OWLClassExpression visit(OWLClass desc) {
        if (negated) {
            if (desc.isOWLNothing()) {
                return dataFactory.getOWLThing();
            } else if (desc.isOWLThing()) {
                return dataFactory.getOWLNothing();
            } else {
                return getNegation(desc);
            }
        } else {
            return desc;
        }
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public OWLClassExpression visit(OWLObjectSomeValuesFrom desc) {
        OWLClassExpression filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLObjectAllValuesFrom(desc.getProperty(),
                    filler);
        } else {
            return dataFactory.getOWLObjectSomeValuesFrom(desc.getProperty(),
                    filler);
        }
    }

    @Override
    public OWLClassExpression visit(OWLObjectAllValuesFrom desc) {
        OWLClassExpression filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLObjectSomeValuesFrom(desc.getProperty(),
                    filler);
        } else {
            return dataFactory.getOWLObjectAllValuesFrom(desc.getProperty(),
                    filler);
        }
    }

    @Override
    public OWLClassExpression visit(OWLObjectHasValue desc) {
        return desc.asSomeValuesFrom().accept(this);
    }

    @Nonnull
    @Override
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
            nnf = dataFactory.getOWLObjectMaxCardinality(card,
                    desc.getProperty(), filler);
        } else {
            nnf = dataFactory.getOWLObjectMinCardinality(card,
                    desc.getProperty(), filler);
        }
        negated = neg;
        return nnf;
    }

    @Override
    public OWLClassExpression visit(OWLObjectExactCardinality desc) {
        return desc.asIntersectionOfMinMax().accept(this);
    }

    @Override
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
            nnf = dataFactory.getOWLObjectMinCardinality(card,
                    desc.getProperty(), filler);
        } else {
            nnf = dataFactory.getOWLObjectMaxCardinality(card,
                    desc.getProperty(), filler);
        }
        negated = neg;
        return nnf;
    }

    @Override
    public OWLClassExpression visit(OWLObjectHasSelf desc) {
        if (negated) {
            return getNegation(desc);
        } else {
            return desc;
        }
    }

    @Override
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

    @Override
    public OWLClassExpression visit(OWLDataSomeValuesFrom desc) {
        OWLDataRange filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLDataAllValuesFrom(desc.getProperty(),
                    filler);
        } else {
            return dataFactory.getOWLDataSomeValuesFrom(desc.getProperty(),
                    filler);
        }
    }

    @Override
    public OWLClassExpression visit(OWLDataAllValuesFrom desc) {
        OWLDataRange filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLDataSomeValuesFrom(desc.getProperty(),
                    filler);
        } else {
            return dataFactory.getOWLDataAllValuesFrom(desc.getProperty(),
                    filler);
        }
    }

    @Override
    public OWLClassExpression visit(OWLDataHasValue desc) {
        return desc.asSomeValuesFrom().accept(this);
    }

    @Override
    public OWLClassExpression visit(OWLDataExactCardinality desc) {
        return desc.asIntersectionOfMinMax().accept(this);
    }

    @Override
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
            nnf = dataFactory.getOWLDataMinCardinality(card,
                    desc.getProperty(), filler);
        } else {
            nnf = dataFactory.getOWLDataMaxCardinality(card,
                    desc.getProperty(), filler);
        }
        negated = neg;
        return nnf;
    }

    @Override
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
            nnf = dataFactory.getOWLDataMaxCardinality(card,
                    desc.getProperty(), filler);
        } else {
            nnf = dataFactory.getOWLDataMinCardinality(card,
                    desc.getProperty(), filler);
        }
        negated = neg;
        return nnf;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public OWLDataRange visit(OWLDatatype node) {
        if (negated) {
            return dataFactory.getOWLDataComplementOf(node);
        } else {
            return node;
        }
    }

    @Override
    public OWLDataRange visit(OWLDataComplementOf node) {
        if (negated) {
            return node.getDataRange();
        } else {
            return node;
        }
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public OWLAxiom visit(@SuppressWarnings("unused") OWLHasKeyAxiom axiom) {
        return null;
    }

    @Override
    public OWLDataRange visit(OWLDatatypeRestriction node) {
        if (negated) {
            return dataFactory.getOWLDataComplementOf(node);
        } else {
            return node;
        }
    }

    @Override
    public OWLDataRange visit(@SuppressWarnings("unused") OWLLiteral node) {
        return null;
    }

    @Override
    public OWLDataRange visit(
            @SuppressWarnings("unused") OWLFacetRestriction node) {
        return null;
    }

    // Conversion of non-class expressions to NNF
    @Override
    public OWLAxiom visit(OWLSubClassOfAxiom axiom) {
        return dataFactory.getOWLSubClassOfAxiom(
                axiom.getSubClass().accept(this),
                axiom.getSuperClass().accept(this));
    }

    @Override
    public OWLAxiom visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDisjointClassesAxiom(ops);
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyDomainAxiom axiom) {
        return dataFactory.getOWLDataPropertyDomainAxiom(axiom.getProperty(),
                axiom.getDomain().accept(this));
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyDomainAxiom axiom) {
        return dataFactory.getOWLObjectPropertyDomainAxiom(axiom.getProperty(),
                axiom.getDomain().accept(this));
    }

    @Override
    public OWLAxiom visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDifferentIndividualsAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDisjointDataPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyRangeAxiom axiom) {
        return dataFactory.getOWLObjectPropertyRangeAxiom(axiom.getProperty(),
                axiom.getRange().accept(this));
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSubObjectPropertyOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            descs.add(op.accept(this));
        }
        return dataFactory.getOWLDisjointUnionAxiom(axiom.getOWLClass(), descs);
    }

    @Override
    public OWLAxiom visit(OWLDeclarationAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLAnnotationAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyRangeAxiom axiom) {
        return dataFactory.getOWLDataPropertyRangeAxiom(axiom.getProperty(),
                axiom.getRange().accept(this));
    }

    @Override
    public OWLAxiom visit(OWLFunctionalDataPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLClassAssertionAxiom axiom) {
        if (axiom.getClassExpression().isAnonymous()) {
            return dataFactory.getOWLClassAssertionAxiom(axiom
                    .getClassExpression().accept(this), axiom.getIndividual());
        } else {
            return axiom;
        }
    }

    @Override
    public OWLAxiom visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLEquivalentClassesAxiom(ops);
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSubDataPropertyOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSameIndividualAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSubPropertyChainOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLInverseObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(SWRLRule rule) {
        return rule;
    }

    @Override
    public OWLAxiom visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLDatatypeDefinitionAxiom axiom) {
        return axiom;
    }
}
