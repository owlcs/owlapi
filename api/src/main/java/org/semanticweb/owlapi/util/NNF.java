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
import javax.annotation.Nullable;

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
    @Nonnull
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
    private OWLClassExpression getNegation(@Nonnull OWLClassExpression classExpression) {
        return dataFactory.getOWLObjectComplementOf(classExpression);
    }

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLClass desc) {
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

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectIntersectionOf desc) {
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

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectUnionOf desc) {
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
    public OWLClassExpression visit(@Nonnull OWLObjectComplementOf desc) {
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

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectSomeValuesFrom desc) {
        OWLClassExpression filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLObjectAllValuesFrom(desc.getProperty(),
                    filler);
        } else {
            return dataFactory.getOWLObjectSomeValuesFrom(desc.getProperty(),
                    filler);
        }
    }

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectAllValuesFrom desc) {
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
    public OWLClassExpression visit(@Nonnull OWLObjectHasValue desc) {
        return desc.asSomeValuesFrom().accept(this);
    }

    @Nullable
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectMinCardinality desc) {
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
    public OWLClassExpression visit(@Nonnull OWLObjectExactCardinality desc) {
        return desc.asIntersectionOfMinMax().accept(this);
    }

    @Nullable
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectMaxCardinality desc) {
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

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectHasSelf desc) {
        if (negated) {
            return getNegation(desc);
        } else {
            return desc;
        }
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLObjectOneOf desc) {
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

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLDataSomeValuesFrom desc) {
        OWLDataRange filler = desc.getFiller().accept(this);
        if (negated) {
            return dataFactory.getOWLDataAllValuesFrom(desc.getProperty(),
                    filler);
        } else {
            return dataFactory.getOWLDataSomeValuesFrom(desc.getProperty(),
                    filler);
        }
    }

    @Nonnull
    @Override
    public OWLClassExpression visit(@Nonnull OWLDataAllValuesFrom desc) {
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
    public OWLClassExpression visit(@Nonnull OWLDataHasValue desc) {
        return desc.asSomeValuesFrom().accept(this);
    }

    @Override
    public OWLClassExpression visit(@Nonnull OWLDataExactCardinality desc) {
        return desc.asIntersectionOfMinMax().accept(this);
    }

    @Nullable
    @Override
    public OWLClassExpression visit(@Nonnull OWLDataMaxCardinality desc) {
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

    @Nullable
    @Override
    public OWLClassExpression visit(@Nonnull OWLDataMinCardinality desc) {
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
    @Nonnull
    @Override
    public OWLDataRange visit(@Nonnull OWLDatatype node) {
        if (negated) {
            return dataFactory.getOWLDataComplementOf(node);
        } else {
            return node;
        }
    }

    @Nonnull
    @Override
    public OWLDataRange visit(@Nonnull OWLDataComplementOf node) {
        if (negated) {
            return node.getDataRange();
        } else {
            return node;
        }
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataOneOf node) {
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

    @Nonnull
    @Override
    public OWLDataRange visit(@Nonnull OWLDataIntersectionOf node) {
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

    @Nonnull
    @Override
    public OWLDataRange visit(@Nonnull OWLDataUnionOf node) {
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

    @Nullable
    @Override
    public OWLAxiom visit(@Nonnull OWLHasKeyAxiom axiom) {
        return null;
    }

    @Nonnull
    @Override
    public OWLDataRange visit(@Nonnull OWLDatatypeRestriction node) {
        if (negated) {
            return dataFactory.getOWLDataComplementOf(node);
        } else {
            return node;
        }
    }

    @Nullable
    @Override
    public OWLDataRange visit(@Nonnull OWLLiteral node) {
        return null;
    }

    @Nullable
    @Override
    public OWLDataRange visit(@Nonnull OWLFacetRestriction node) {
        return null;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Conversion of non-class expressions to NNF
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////
    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLSubClassOfAxiom axiom) {
        return dataFactory.getOWLSubClassOfAxiom(
                axiom.getSubClass().accept(this),
                axiom.getSuperClass().accept(this));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDisjointClassesAxiom(ops);
    }

    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        return dataFactory.getOWLDataPropertyDomainAxiom(axiom.getProperty(),
                axiom.getDomain().accept(this));
    }

    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        return dataFactory.getOWLObjectPropertyDomainAxiom(axiom.getProperty(),
                axiom.getDomain().accept(this));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        return dataFactory.getOWLObjectPropertyRangeAxiom(axiom.getProperty(),
                axiom.getRange().accept(this));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        return axiom;
    }

    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            descs.add(op.accept(this));
        }
        return dataFactory.getOWLDisjointUnionAxiom(axiom.getOWLClass(), descs);
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDeclarationAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        return dataFactory.getOWLDataPropertyRangeAxiom(axiom.getProperty(),
                axiom.getRange().accept(this));
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        return axiom;
    }

    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLClassAssertionAxiom axiom) {
        if (axiom.getClassExpression().isAnonymous()) {
            return dataFactory.getOWLClassAssertionAxiom(axiom
                    .getClassExpression().accept(this), axiom.getIndividual());
        } else {
            return axiom;
        }
    }

    @Nonnull
    @Override
    public OWLAxiom visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : axiom.getClassExpressions()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLEquivalentClassesAxiom(ops);
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLSameIndividualAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull SWRLRule rule) {
        return rule;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        return axiom;
    }

    @Override
    public OWLAxiom visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        return axiom;
    }
}
