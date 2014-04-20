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
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
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
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
public class MaximumModalDepthFinder implements OWLObjectVisitorEx<Integer> {

    private static final Integer _1 = Integer.valueOf(1);
    private static final Integer _0 = Integer.valueOf(0);

    @Override
    public Integer visit(@Nonnull IRI iri) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDatatype node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectProperty property) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLAnonymousIndividual individual) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLClassAtom node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectInverseOf property) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLDataRangeAtom node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLAnnotation node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataOneOf node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataProperty property) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLObjectPropertyAtom node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataIntersectionOf node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLNamedIndividual individual) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataUnionOf node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLSubClassOfAxiom axiom) {
        int subClassModalDepth = axiom.getSubClass().accept(this).intValue();
        int superClassModalDepth = axiom.getSuperClass().accept(this)
                .intValue();
        return Integer.valueOf(Math.max(subClassModalDepth,
                superClassModalDepth));
    }

    @Override
    public Integer visit(@Nonnull OWLAnnotationPropertyRangeAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLOntology ontology) {
        int max = 0;
        for (OWLAxiom axiom : ontology.getLogicalAxioms()) {
            int depth = axiom.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return Integer.valueOf(max);
    }

    @Override
    public Integer visit(@Nonnull OWLDatatypeRestriction node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLBuiltInAtom node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLAnnotationProperty property) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLClass ce) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLVariable node) {
        return _0;
    }

    @Nullable
    @Override
    public Integer visit(@Nonnull OWLLiteral node) {
        return null;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectIntersectionOf ce) {
        int max = 0;
        for (OWLClassExpression op : ce.getOperands()) {
            int depth = op.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return Integer.valueOf(max);
    }

    @Override
    public Integer visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLIndividualArgument node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectUnionOf ce) {
        int max = 0;
        for (OWLClassExpression op : ce.getOperands()) {
            int depth = op.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return Integer.valueOf(max);
    }

    @Override
    public Integer visit(@Nonnull OWLFacetRestriction node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLLiteralArgument node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectComplementOf ce) {
        return ce.getOperand().accept(this);
    }

    @Override
    public Integer visit(@Nonnull SWRLSameIndividualAtom node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLDifferentIndividualsAtom node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectAllValuesFrom ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(@Nonnull OWLDisjointClassesAxiom axiom) {
        int max = 0;
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            int depth = ce.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return Integer.valueOf(max);
    }

    @Override
    public Integer visit(@Nonnull OWLObjectHasValue ce) {
        return _1;
    }

    @Override
    public Integer visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Integer visit(@Nonnull OWLObjectMinCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Integer visit(@Nonnull OWLObjectExactCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectMaxCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(@Nonnull OWLObjectHasSelf ce) {
        return _1;
    }

    @Override
    public Integer visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectOneOf ce) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataSomeValuesFrom ce) {
        return _1;
    }

    @Override
    public Integer visit(@Nonnull OWLDataAllValuesFrom ce) {
        return _1;
    }

    @Override
    public Integer visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataHasValue ce) {
        return _1;
    }

    @Override
    public Integer visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataMinCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataExactCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(@Nonnull OWLDataMaxCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDisjointUnionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDeclarationAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLClassAssertionAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    @Override
    public Integer visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
        int max = 0;
        for (OWLClassExpression ce : axiom.getClassExpressions()) {
            int depth = ce.accept(this).intValue();
            if (depth > max) {
                max = depth;
            }
        }
        return Integer.valueOf(max);
    }

    @Override
    public Integer visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLSameIndividualAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLSubPropertyChainOfAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLHasKeyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDatatypeDefinitionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLRule rule) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLSubAnnotationPropertyOfAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLAnnotationPropertyDomainAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull OWLDataComplementOf node) {
        return _0;
    }

    @Override
    public Integer visit(@Nonnull SWRLDataPropertyAtom node) {
        return _0;
    }
}
