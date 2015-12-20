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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.List;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
public class NNF implements OWLAxiomVisitorEx<OWLAxiom> {

    protected boolean negated;
    protected final OWLDataFactory df;
    private final OWLClassExpressionVisitorEx<OWLClassExpression> classVisitor;
    protected final OWLDataRangeVisitorEx<OWLDataRange> dataVisitor;

    /**
     * @param datafactory
     *        datafactory to use
     */
    public NNF(OWLDataFactory datafactory) {
        df = checkNotNull(datafactory, "dataFactory cannot be null");
        dataVisitor = new OWLDataRangeVisitorEx<OWLDataRange>() {

            @Override
            public OWLDataRange visit(OWLDatatype node) {
                if (negated) {
                    return df.getOWLDataComplementOf(node);
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
                if (node.values().count() == 1) {
                    if (negated) {
                        return df.getOWLDataComplementOf(node);
                    } else {
                        return node;
                    }
                } else {
                    // Encode as a data union of and return result
                    List<OWLDataOneOf> oneOfs = asList(node.values().map(df::getOWLDataOneOf));
                    return df.getOWLDataUnionOf(oneOfs).accept(this);
                }
            }

            @Override
            public OWLDataRange visit(OWLDataIntersectionOf node) {
                List<OWLDataRange> ops = asList(node.operands().map(p -> p.accept(this)));
                if (negated) {
                    return df.getOWLDataUnionOf(ops);
                } else {
                    return df.getOWLDataIntersectionOf(ops);
                }
            }

            @Override
            public OWLDataRange visit(OWLDataUnionOf node) {
                List<OWLDataRange> ops = asList(node.operands().map(p -> p.accept(this)));
                if (negated) {
                    // Flip to an intersection
                    return df.getOWLDataIntersectionOf(ops);
                } else {
                    return df.getOWLDataUnionOf(ops);
                }
            }

            @Override
            public OWLDataRange visit(OWLDatatypeRestriction node) {
                if (negated) {
                    return df.getOWLDataComplementOf(node);
                } else {
                    return node;
                }
            }
        };
        classVisitor = new OWLClassExpressionVisitorEx<OWLClassExpression>() {

            private OWLClassExpression getNegation(OWLClassExpression classExpression) {
                return df.getOWLObjectComplementOf(classExpression);
            }

            @Override
            public OWLClassExpression visit(OWLClass ce) {
                if (negated) {
                    if (ce.isOWLNothing()) {
                        return df.getOWLThing();
                    }
                    if (ce.isOWLThing()) {
                        return df.getOWLNothing();
                    }
                    return getNegation(ce);
                }
                return ce;
            }

            @Override
            public OWLClassExpression visit(OWLObjectIntersectionOf ce) {
                List<OWLClassExpression> ops = asList(ce.operands().map(p -> p.accept(this)));
                if (negated) {
                    return df.getOWLObjectUnionOf(ops);
                } else {
                    return df.getOWLObjectIntersectionOf(ops);
                }
            }

            @Override
            public OWLClassExpression visit(OWLObjectUnionOf ce) {
                List<OWLClassExpression> ops = asList(ce.operands().map(p -> p.accept(this)));
                if (negated) {
                    // Flip to an intersection
                    return df.getOWLObjectIntersectionOf(ops);
                } else {
                    return df.getOWLObjectUnionOf(ops);
                }
            }

            @Override
            public OWLClassExpression visit(OWLObjectComplementOf ce) {
                if (negated) {
                    // Cancels out.
                    // Save and then restore.
                    boolean neg = negated;
                    negated = false;
                    OWLClassExpression negDesc = ce.getOperand().accept(this);
                    negated = neg;
                    return negDesc;
                } else {
                    // Save and then restore
                    boolean neg = negated;
                    negated = true;
                    OWLClassExpression negDesc = ce.getOperand().accept(this);
                    negated = neg;
                    return negDesc;
                }
            }

            @Override
            public OWLClassExpression visit(OWLObjectSomeValuesFrom ce) {
                OWLClassExpression filler = ce.getFiller().accept(this);
                if (negated) {
                    return df.getOWLObjectAllValuesFrom(ce.getProperty(), filler);
                } else {
                    return df.getOWLObjectSomeValuesFrom(ce.getProperty(), filler);
                }
            }

            @Override
            public OWLClassExpression visit(OWLObjectAllValuesFrom ce) {
                OWLClassExpression filler = ce.getFiller().accept(this);
                if (negated) {
                    return df.getOWLObjectSomeValuesFrom(ce.getProperty(), filler);
                } else {
                    return df.getOWLObjectAllValuesFrom(ce.getProperty(), filler);
                }
            }

            @Override
            public OWLClassExpression visit(OWLObjectHasValue ce) {
                return ce.asSomeValuesFrom().accept(this);
            }

            @Override
            public OWLClassExpression visit(OWLObjectMinCardinality ce) {
                boolean neg = negated;
                int card = ce.getCardinality();
                if (negated) {
                    card = ce.getCardinality() - 1;
                    if (card < 0) {
                        card = 0;
                    }
                }
                negated = false;
                OWLClassExpression filler = ce.getFiller().accept(this);
                OWLClassExpression nnf = null;
                if (neg) {
                    nnf = df.getOWLObjectMaxCardinality(card, ce.getProperty(), filler);
                } else {
                    nnf = df.getOWLObjectMinCardinality(card, ce.getProperty(), filler);
                }
                negated = neg;
                return nnf;
            }

            @Override
            public OWLClassExpression visit(OWLObjectExactCardinality ce) {
                return ce.asIntersectionOfMinMax().accept(this);
            }

            @Override
            public OWLClassExpression visit(OWLObjectMaxCardinality ce) {
                boolean neg = negated;
                int card = ce.getCardinality();
                if (negated) {
                    card = ce.getCardinality() + 1;
                }
                negated = false;
                OWLClassExpression filler = ce.getFiller().accept(this);
                OWLClassExpression nnf = null;
                if (neg) {
                    nnf = df.getOWLObjectMinCardinality(card, ce.getProperty(), filler);
                } else {
                    nnf = df.getOWLObjectMaxCardinality(card, ce.getProperty(), filler);
                }
                negated = neg;
                return nnf;
            }

            @Override
            public OWLClassExpression visit(OWLObjectHasSelf ce) {
                if (negated) {
                    return getNegation(ce);
                } else {
                    return ce;
                }
            }

            @Override
            public OWLClassExpression visit(OWLObjectOneOf ce) {
                if (ce.individuals().count() == 1) {
                    if (negated) {
                        return getNegation(ce);
                    } else {
                        return ce;
                    }
                } else {
                    return ce.asObjectUnionOf().accept(this);
                }
            }

            @Override
            public OWLClassExpression visit(OWLDataSomeValuesFrom ce) {
                OWLDataRange filler = ce.getFiller().accept(dataVisitor);
                if (negated) {
                    return df.getOWLDataAllValuesFrom(ce.getProperty(), filler);
                } else {
                    return df.getOWLDataSomeValuesFrom(ce.getProperty(), filler);
                }
            }

            @Override
            public OWLClassExpression visit(OWLDataAllValuesFrom ce) {
                OWLDataRange filler = ce.getFiller().accept(dataVisitor);
                if (negated) {
                    return df.getOWLDataSomeValuesFrom(ce.getProperty(), filler);
                } else {
                    return df.getOWLDataAllValuesFrom(ce.getProperty(), filler);
                }
            }

            @Override
            public OWLClassExpression visit(OWLDataHasValue ce) {
                return ce.asSomeValuesFrom().accept(this);
            }

            @Override
            public OWLClassExpression visit(OWLDataExactCardinality ce) {
                return ce.asIntersectionOfMinMax().accept(this);
            }

            @Override
            public OWLClassExpression visit(OWLDataMaxCardinality ce) {
                boolean neg = negated;
                int card = ce.getCardinality();
                if (negated) {
                    card = ce.getCardinality() + 1;
                }
                negated = false;
                OWLDataRange filler = ce.getFiller().accept(dataVisitor);
                OWLClassExpression nnf = null;
                if (neg) {
                    nnf = df.getOWLDataMinCardinality(card, ce.getProperty(), filler);
                } else {
                    nnf = df.getOWLDataMaxCardinality(card, ce.getProperty(), filler);
                }
                negated = neg;
                return nnf;
            }

            @Override
            public OWLClassExpression visit(OWLDataMinCardinality ce) {
                boolean neg = negated;
                int card = ce.getCardinality();
                if (negated) {
                    card = ce.getCardinality() - 1;
                    if (card < 0) {
                        card = 0;
                    }
                }
                negated = false;
                OWLDataRange filler = ce.getFiller().accept(dataVisitor);
                OWLClassExpression nnf = null;
                if (neg) {
                    nnf = df.getOWLDataMaxCardinality(card, ce.getProperty(), filler);
                } else {
                    nnf = df.getOWLDataMinCardinality(card, ce.getProperty(), filler);
                }
                negated = neg;
                return nnf;
            }
        };
    }

    /**
     * @return class visitor
     */
    public OWLClassExpressionVisitorEx<OWLClassExpression> getClassVisitor() {
        return classVisitor;
    }

    /** reset the negation. */
    public void reset() {
        negated = false;
    }

    @Override
    public OWLAxiom visit(OWLHasKeyAxiom axiom) {
        return axiom;
    }

    // Conversion of non-class expressions to NNF
    @Override
    public OWLAxiom visit(OWLSubClassOfAxiom axiom) {
        return df.getOWLSubClassOfAxiom(axiom.getSubClass().accept(classVisitor),
            axiom.getSuperClass().accept(classVisitor));
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
        List<OWLClassExpression> ops = asList(axiom.classExpressions().map(p -> p.accept(classVisitor)));
        return df.getOWLDisjointClassesAxiom(ops);
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyDomainAxiom axiom) {
        return df.getOWLDataPropertyDomainAxiom(axiom.getProperty(), axiom.getDomain().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyDomainAxiom axiom) {
        return df.getOWLObjectPropertyDomainAxiom(axiom.getProperty(), axiom.getDomain().accept(classVisitor));
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
        return df.getOWLObjectPropertyRangeAxiom(axiom.getProperty(), axiom.getRange().accept(classVisitor));
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
        List<OWLClassExpression> ops = asList(axiom.classExpressions().map(p -> p.accept(classVisitor)));
        return df.getOWLDisjointUnionAxiom(axiom.getOWLClass(), ops);
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
        return df.getOWLDataPropertyRangeAxiom(axiom.getProperty(), axiom.getRange().accept(dataVisitor));
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
            return df.getOWLClassAssertionAxiom(axiom.getClassExpression().accept(classVisitor),
                axiom.getIndividual());
        } else {
            return axiom;
        }
    }

    @Override
    public OWLAxiom visit(OWLEquivalentClassesAxiom axiom) {
        List<OWLClassExpression> ops = asList(axiom.classExpressions().map(p -> p.accept(classVisitor)));
        return df.getOWLEquivalentClassesAxiom(ops);
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
