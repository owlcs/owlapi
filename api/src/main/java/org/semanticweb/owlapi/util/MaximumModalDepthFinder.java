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
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Jun-2010
 */
public class MaximumModalDepthFinder implements OWLObjectVisitorEx<Integer> {


    


    public Integer visit(IRI iri) {
        return 0;
    }

    public Integer visit(OWLDatatype node) {
        return 0;
    }

    public Integer visit(OWLObjectProperty property) {
        return 0;
    }

    public Integer visit(OWLAnonymousIndividual individual) {
        return 0;
    }

    public Integer visit(SWRLClassAtom node) {
        return 0;
    }

    public Integer visit(OWLObjectInverseOf property) {
        return 0;
    }

    public Integer visit(SWRLDataRangeAtom node) {
        return 0;
    }

    public Integer visit(OWLAnnotation node) {
        return 0;
    }

    public Integer visit(OWLDataOneOf node) {
        return 0;
    }

    public Integer visit(OWLDataProperty property) {
        return 0;
    }

    public Integer visit(SWRLObjectPropertyAtom node) {
        return 0;
    }

    public Integer visit(OWLDataIntersectionOf node) {
        return 0;
    }

    public Integer visit(OWLNamedIndividual individual) {
        return 0;
    }

    public Integer visit(OWLDataUnionOf node) {
        return 0;
    }

    public Integer visit(OWLSubClassOfAxiom axiom) {
        int subClassModalDepth = axiom.getSubClass().accept(this);
        int superClassModalDepth = axiom.getSuperClass().accept(this);
        return Math.max(subClassModalDepth, superClassModalDepth);
    }

    public Integer visit(OWLAnnotationPropertyRangeAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLOntology ontology) {
        int max = 0;
        for(OWLAxiom axiom : ontology.getLogicalAxioms()) {
            int depth = axiom.accept(this);
            if(depth > max) {
                max = depth;
            }
        }
        return max;
    }

    public Integer visit(OWLDatatypeRestriction node) {
        return 0;
    }

    public Integer visit(SWRLBuiltInAtom node) {
        return 0;
    }

    public Integer visit(OWLAnnotationProperty property) {
        return 0;
    }

    public Integer visit(OWLClass ce) {
        return 0;
    }

    public Integer visit(SWRLVariable node) {
        return 0;
    }

    public Integer visit(OWLLiteral node) {
        return null;
    }

    public Integer visit(OWLObjectIntersectionOf ce) {
        int max = 0;
        for(OWLClassExpression op : ce.getOperands()) {
            int depth = op.accept(this);
            if(depth > max) {
                max = depth;
            }
        }
        return max;
    }

    public Integer visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        return 0;
    }

    public Integer visit(SWRLIndividualArgument node) {
        return 0;
    }

    public Integer visit(OWLObjectUnionOf ce) {
        int max = 0;
        for(OWLClassExpression op : ce.getOperands()) {
            int depth = op.accept(this);
            if(depth > max) {
                max = depth;
            }
        }
        return max;
    }

    public Integer visit(OWLFacetRestriction node) {
        return 0;
    }

    public Integer visit(SWRLLiteralArgument node) {
        return 0;
    }

    public Integer visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLObjectComplementOf ce) {
        return ce.getOperand().accept(this);
    }

    public Integer visit(SWRLSameIndividualAtom node) {
        return 0;
    }

    public Integer visit(OWLObjectSomeValuesFrom ce) {
        return 1 + ce.getFiller().accept(this);
    }

    public Integer visit(OWLReflexiveObjectPropertyAxiom axiom) {
        return 0;
    }

    public Integer visit(SWRLDifferentIndividualsAtom node) {
        return 0;
    }

    public Integer visit(OWLObjectAllValuesFrom ce) {
        return 1 + ce.getFiller().accept(this);
    }

    public Integer visit(OWLDisjointClassesAxiom axiom) {
        int max = 0;
        for(OWLClassExpression ce : axiom.getClassExpressions()) {
            int depth = ce.accept(this);
            if(depth > max) {
                max = depth;
            }
        }
        return max;
    }

    public Integer visit(OWLObjectHasValue ce) {
        return 1;
    }

    public Integer visit(OWLDataPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    public Integer visit(OWLObjectMinCardinality ce) {
        return 1 + ce.getFiller().accept(this);
    }

    public Integer visit(OWLObjectPropertyDomainAxiom axiom) {
        return axiom.getDomain().accept(this);
    }

    public Integer visit(OWLObjectExactCardinality ce) {
        return 1 + ce.getFiller().accept(this);
    }

    public Integer visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLObjectMaxCardinality ce) {
        return 1 + ce.getFiller().accept(this);
    }

    public Integer visit(OWLObjectHasSelf ce) {
        return 1;
    }

    public Integer visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLObjectOneOf ce) {
        return 0;
    }

    public Integer visit(OWLDifferentIndividualsAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDataSomeValuesFrom ce) {
        return 1;
    }

    public Integer visit(OWLDataAllValuesFrom ce) {
        return 1;
    }

    public Integer visit(OWLDisjointDataPropertiesAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDataHasValue ce) {
        return 1;
    }

    public Integer visit(OWLDisjointObjectPropertiesAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDataMinCardinality ce) {
        return 1;
    }

    public Integer visit(OWLObjectPropertyRangeAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDataExactCardinality ce) {
        return 1;
    }

    public Integer visit(OWLDataMaxCardinality ce) {
        return 1;
    }

    public Integer visit(OWLObjectPropertyAssertionAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLFunctionalObjectPropertyAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLSubObjectPropertyOfAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDisjointUnionAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDeclarationAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLAnnotationAssertionAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLSymmetricObjectPropertyAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDataPropertyRangeAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLFunctionalDataPropertyAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLEquivalentDataPropertiesAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLClassAssertionAxiom axiom) {
        return axiom.getClassExpression().accept(this);
    }

    public Integer visit(OWLEquivalentClassesAxiom axiom) {
        int max = 0;
        for(OWLClassExpression ce : axiom.getClassExpressions()) {
            int depth = ce.accept(this);
            if(depth > max) {
                max = depth;
            }
        }
        return max;
    }

    public Integer visit(OWLDataPropertyAssertionAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLTransitiveObjectPropertyAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLSubDataPropertyOfAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLSameIndividualAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLSubPropertyChainOfAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLInverseObjectPropertiesAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLHasKeyAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDatatypeDefinitionAxiom axiom) {
        return 0;
    }

    public Integer visit(SWRLRule rule) {
        return 0;
    }

    public Integer visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLAnnotationPropertyDomainAxiom axiom) {
        return 0;
    }

    public Integer visit(OWLDataComplementOf node) {
        return 0;
    }

    public Integer visit(SWRLDataPropertyAtom node) {
        return 0;
    }
}
