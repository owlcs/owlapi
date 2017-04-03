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

import java.util.stream.Stream;

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
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
public class NNF implements OWLAxiomVisitorEx<OWLAxiom> {

    protected final OWLDataFactory df;
    protected final OWLClassExpressionVisitorEx<OWLClassExpression> classVisitor =
        new ClassVisitor();
    protected final OWLDataRangeVisitorEx<OWLDataRange> dataVisitor = new DataVisitor();
    protected boolean negated;

    /**
     * @param datafactory datafactory to use
     */
    public NNF(OWLDataFactory datafactory) {
        df = checkNotNull(datafactory, "dataFactory cannot be null");
    }

    /**
     * @return class visitor
     */
    public OWLClassExpressionVisitorEx<OWLClassExpression> getClassVisitor() {
        return classVisitor;
    }

    /**
     * reset the negation.
     */
    public void reset() {
        negated = false;
    }

    @Override
    public OWLAxiom doDefault(OWLObject object) {
        return (OWLAxiom) object;
    }

    // Conversion of non-class expressions to NNF
    @Override
    public OWLAxiom visit(OWLSubClassOfAxiom axiom) {
        return df.getOWLSubClassOfAxiom(axiom.getSubClass().accept(classVisitor),
            axiom.getSuperClass().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(OWLDisjointClassesAxiom axiom) {
        return df
            .getOWLDisjointClassesAxiom(axiom.classExpressions().map(p -> p.accept(classVisitor)));
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyDomainAxiom axiom) {
        return df.getOWLDataPropertyDomainAxiom(axiom.getProperty(),
            axiom.getDomain().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyDomainAxiom axiom) {
        return df.getOWLObjectPropertyDomainAxiom(axiom.getProperty(),
            axiom.getDomain().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(OWLObjectPropertyRangeAxiom axiom) {
        return df.getOWLObjectPropertyRangeAxiom(axiom.getProperty(),
            axiom.getRange().accept(classVisitor));
    }

    @Override
    public OWLAxiom visit(OWLDisjointUnionAxiom axiom) {
        return df.getOWLDisjointUnionAxiom(axiom.getOWLClass(),
            asList(axiom.classExpressions().map(p -> p.accept(classVisitor))));
    }

    @Override
    public OWLAxiom visit(OWLDataPropertyRangeAxiom axiom) {
        return df.getOWLDataPropertyRangeAxiom(axiom.getProperty(),
            axiom.getRange().accept(dataVisitor));
    }

    @Override
    public OWLAxiom visit(OWLClassAssertionAxiom axiom) {
        if (axiom.getClassExpression().isAnonymous()) {
            return df.getOWLClassAssertionAxiom(axiom.getClassExpression().accept(classVisitor),
                axiom.getIndividual());
        }
        return axiom;
    }

    @Override
    public OWLAxiom visit(OWLEquivalentClassesAxiom axiom) {
        return df.getOWLEquivalentClassesAxiom(
            axiom.classExpressions().map(p -> p.accept(classVisitor)));
    }

    class DataVisitor implements OWLDataRangeVisitorEx<OWLDataRange> {

        @Override
        public OWLDataRange visit(OWLDatatype node) {
            if (negated) {
                return df.getOWLDataComplementOf(node);
            }
            return node;
        }

        @Override
        public OWLDataRange visit(OWLDataComplementOf node) {
            if (negated) {
                return node.getDataRange();
            }
            return node;
        }

        @Override
        public OWLDataRange visit(OWLDataOneOf node) {
            if (node.values().count() == 1) {
                if (negated) {
                    return df.getOWLDataComplementOf(node);
                }
                return node;
            }
            return df.getOWLDataUnionOf(node.values().map(df::getOWLDataOneOf)).accept(this);
        }

        @Override
        public OWLDataRange visit(OWLDataIntersectionOf node) {
            Stream<OWLDataRange> ops = node.operands().map(p -> p.accept(this));
            if (negated) {
                return df.getOWLDataUnionOf(ops);
            }
            return df.getOWLDataIntersectionOf(ops);
        }

        @Override
        public OWLDataRange visit(OWLDataUnionOf node) {
            Stream<OWLDataRange> ops = node.operands().map(p -> p.accept(this));
            if (negated) {
                // Flip to an intersection
                return df.getOWLDataIntersectionOf(ops);
            }
            return df.getOWLDataUnionOf(ops);
        }

        @Override
        public OWLDataRange visit(OWLDatatypeRestriction node) {
            if (negated) {
                return df.getOWLDataComplementOf(node);
            }
            return node;
        }
    }

    class ClassVisitor implements OWLClassExpressionVisitorEx<OWLClassExpression> {

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
            Stream<OWLClassExpression> ops = ce.operands().map(p -> p.accept(this));
            if (negated) {
                return df.getOWLObjectUnionOf(ops);
            }
            return df.getOWLObjectIntersectionOf(ops);
        }

        @Override
        public OWLClassExpression visit(OWLObjectUnionOf ce) {
            Stream<OWLClassExpression> ops = ce.operands().map(p -> p.accept(this));
            if (negated) {
                // Flip to an intersection
                return df.getOWLObjectIntersectionOf(ops);
            }
            return df.getOWLObjectUnionOf(ops);
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
            }
            // Save and then restore
            boolean neg = negated;
            negated = true;
            OWLClassExpression negDesc = ce.getOperand().accept(this);
            negated = neg;
            return negDesc;
        }

        @Override
        public OWLClassExpression visit(OWLObjectSomeValuesFrom ce) {
            OWLClassExpression filler = ce.getFiller().accept(this);
            if (negated) {
                return df.getOWLObjectAllValuesFrom(ce.getProperty(), filler);
            }
            return df.getOWLObjectSomeValuesFrom(ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(OWLObjectAllValuesFrom ce) {
            OWLClassExpression filler = ce.getFiller().accept(this);
            if (negated) {
                return df.getOWLObjectSomeValuesFrom(ce.getProperty(), filler);
            }
            return df.getOWLObjectAllValuesFrom(ce.getProperty(), filler);
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
            negated = neg;
            if (neg) {
                return df.getOWLObjectMaxCardinality(card, ce.getProperty(), filler);
            }
            return df.getOWLObjectMinCardinality(card, ce.getProperty(), filler);
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
            negated = neg;
            if (neg) {
                return df.getOWLObjectMinCardinality(card, ce.getProperty(), filler);
            }
            return df.getOWLObjectMaxCardinality(card, ce.getProperty(), filler);
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
            }
            return ce.asObjectUnionOf().accept(this);
        }

        @Override
        public OWLClassExpression visit(OWLDataSomeValuesFrom ce) {
            OWLDataRange filler = ce.getFiller().accept(dataVisitor);
            if (negated) {
                return df.getOWLDataAllValuesFrom(ce.getProperty(), filler);
            }
            return df.getOWLDataSomeValuesFrom(ce.getProperty(), filler);
        }

        @Override
        public OWLClassExpression visit(OWLDataAllValuesFrom ce) {
            OWLDataRange filler = ce.getFiller().accept(dataVisitor);
            if (negated) {
                return df.getOWLDataSomeValuesFrom(ce.getProperty(), filler);
            }
            return df.getOWLDataAllValuesFrom(ce.getProperty(), filler);
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
            negated = neg;
            if (neg) {
                return df.getOWLDataMinCardinality(card, ce.getProperty(), filler);
            }
            return df.getOWLDataMaxCardinality(card, ce.getProperty(), filler);
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
            negated = neg;
            if (neg) {
                return df.getOWLDataMaxCardinality(card, ce.getProperty(), filler);
            }
            return df.getOWLDataMinCardinality(card, ce.getProperty(), filler);
        }
    }
}
