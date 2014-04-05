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

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
public class MaximumModalDepthFinder implements OWLObjectVisitorEx<Integer> {

    private static final Integer _1 = Integer.valueOf(1);
    private static final Integer _0 = Integer.valueOf(0);

    @Override
    public Integer visit(IRI iri) {
        return _0;
    }

    @Override
    public Integer visit(OWLDatatype node) {
        return _0;
    }

    @Override
    public Integer visit(OWLObjectProperty property) {
        return _0;
    }

    @Override
    public Integer visit(OWLAnonymousIndividual individual) {
        return _0;
    }

    @Override
    public Integer visit(SWRLClassAtom node) {
        return _0;
    }

    @Override
    public Integer visit(OWLObjectInverseOf property) {
        return _0;
    }

    @Override
    public Integer visit(SWRLDataRangeAtom node) {
        return _0;
    }

    @Override
    public Integer visit(OWLAnnotation node) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataOneOf node) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataProperty property) {
        return _0;
    }

    @Override
    public Integer visit(SWRLObjectPropertyAtom node) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataIntersectionOf node) {
        return _0;
    }

    @Override
    public Integer visit(OWLNamedIndividual individual) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataUnionOf node) {
        return _0;
    }

    @Override
    public Integer visit(OWLSubClassOfAxiom axiom) {
        int subClassModalDepth = axiom.getSubClass().accept(this).intValue();
        int superClassModalDepth = axiom.getSuperClass().accept(this)
                .intValue();
        return Integer.valueOf(Math.max(subClassModalDepth,
                superClassModalDepth));
    }

    @Override
    public Integer visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLOntology ontology) {
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
    public Integer visit(OWLDatatypeRestriction node) {
        return _0;
    }

    @Override
    public Integer visit(SWRLBuiltInAtom node) {
        return _0;
    }

    @Override
    public Integer visit(OWLAnnotationProperty property) {
        return _0;
    }

    @Override
    public Integer visit(OWLClass ce) {
        return _0;
    }

    @Override
    public Integer visit(SWRLVariable node) {
        return _0;
    }

    @Override
    public Integer visit(OWLLiteral node) {
        return null;
    }

    @Override
    public Integer visit(OWLObjectIntersectionOf ce) {
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
    public Integer visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(SWRLIndividualArgument node) {
        return _0;
    }

    @Override
    public Integer visit(OWLObjectUnionOf ce) {
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
    public Integer visit(OWLFacetRestriction node) {
        return _0;
    }

    @Override
    public Integer visit(SWRLLiteralArgument node) {
        return _0;
    }

    @Override
    public Integer visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLObjectComplementOf ce) {
        return ce.getOperand().accept(this);
    }

    @Override
    public Integer visit(SWRLSameIndividualAtom node) {
        return _0;
    }

    @Override
    public Integer visit(OWLObjectSomeValuesFrom ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(SWRLDifferentIndividualsAtom node) {
        return _0;
    }

    @Override
    public Integer visit(OWLObjectAllValuesFrom ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLDisjointClassesAxiom axiom) {
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
    public Integer visit(OWLObjectHasValue ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Integer visit(OWLObjectMinCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    @Override
    public Integer visit(OWLObjectExactCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLObjectMaxCardinality ce) {
        return Integer.valueOf(1 + ce.getFiller().accept(this).intValue());
    }

    @Override
    public Integer visit(OWLObjectHasSelf ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLObjectOneOf ce) {
        return _0;
    }

    @Override
    public Integer visit(OWLDifferentIndividualsAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataSomeValuesFrom ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataAllValuesFrom ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDisjointDataPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataHasValue ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataMinCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLObjectPropertyRangeAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataExactCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLDataMaxCardinality ce) {
        return _1;
    }

    @Override
    public Integer visit(OWLObjectPropertyAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLSubObjectPropertyOfAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDisjointUnionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDeclarationAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLAnnotationAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataPropertyRangeAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLFunctionalDataPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLClassAssertionAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    @Override
    public Integer visit(OWLEquivalentClassesAxiom axiom) {
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
    public Integer visit(OWLDataPropertyAssertionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLSubDataPropertyOfAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLSameIndividualAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLSubPropertyChainOfAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLInverseObjectPropertiesAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLHasKeyAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDatatypeDefinitionAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(SWRLRule rule) {
        return _0;
    }

    @Override
    public Integer visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return _0;
    }

    @Override
    public Integer visit(OWLDataComplementOf node) {
        return _0;
    }

    @Override
    public Integer visit(SWRLDataPropertyAtom node) {
        return _0;
    }
}
