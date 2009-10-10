package com.clarkparsia.owlapi.explanation;

import org.semanticweb.owlapi.model.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/*
* Copyright (C) 2007, Clark & Parsia
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
 * <p>Title: SatisfiabilityConverter</p>
 * <p/>
 * <p>Description: Converts an axiom into a concept such that the axiom is entailed iff
 * the concept is unsatisfiable. Designed as a helper function to generate explanations
 * for arbitrary axioms.</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p/>
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 *
 * @author Evren Sirin
 */
public class SatisfiabilityConverter {

    private class AxiomConverter implements OWLAxiomVisitor {

        private OWLClassExpression result;


        private OWLObjectIntersectionOf and(OWLClassExpression desc1, OWLClassExpression desc2) {
            return factory.getOWLObjectIntersectionOf(set(desc1, desc2));
        }


        private OWLObjectIntersectionOf and(Set<OWLClassExpression> set) {
            return factory.getOWLObjectIntersectionOf(set);
        }


        OWLClassExpression getResult() {
            return result;
        }


        private OWLObjectComplementOf not(OWLClassExpression desc) {
            return factory.getOWLObjectComplementOf(desc);
        }


        private OWLObjectOneOf oneOf(OWLIndividual ind) {
            return factory.getOWLObjectOneOf(Collections.singleton(ind));
        }


        private OWLObjectUnionOf or(OWLClassExpression desc1, OWLClassExpression desc2) {
            return factory.getOWLObjectUnionOf(set(desc1, desc2));
        }


        void reset() {
            result = null;
        }


        private <T> Set<T> set(T desc1, T desc2) {
            Set<T> set = new HashSet<T>();
            set.add(desc1);
            set.add(desc2);

            return set;
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            OWLIndividual ind = axiom.getIndividual();
            OWLClassExpression c = axiom.getClassExpression();

            result = and(oneOf(ind), not(c));
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            OWLClassExpression sub = oneOf(axiom.getSubject());
            OWLClassExpression sup = factory.getOWLDataHasValue(axiom.getProperty(), axiom.getObject());
            OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(sub, sup);
            ax.accept(this);
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            OWLClassExpression sub = factory.getOWLDataSomeValuesFrom(axiom.getProperty(), factory.getTopDatatype());
            result = and(sub, not(axiom.getDomain()));
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            result = factory.getOWLDataSomeValuesFrom(axiom.getProperty(),
                    factory.getOWLDataComplementOf(axiom.getRange()));
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLDeclarationAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            Set<OWLClassExpression> nominals = new HashSet<OWLClassExpression>();
            for (OWLIndividual ind : axiom.getIndividuals()) {
                nominals.add(oneOf(ind));
            }
            result = factory.getOWLObjectIntersectionOf(nominals);
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            result = and(axiom.getClassExpressions());
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            Iterator<OWLClassExpression> classes = axiom.getClassExpressions().iterator();
            OWLClassExpression c1 = classes.next();
            OWLClassExpression c2 = classes.next();

            if (classes.hasNext())
                logger.warning("EquivalentClassesAxiom with more than two elements not supported!");

            // apply simplification for the cases where either concept is owl:Thing or owlapi:Nothin
            if (c1.isOWLNothing())
                result = c2;
            else if (c2.isOWLNothing())
                result = c1;
            else if (c1.isOWLThing())
                result = not(c2);
            else if (c2.isOWLThing())
                result = not(c1);
            else
                result = or(and(c1, not(c2)), and(not(c1), c2));
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLImportsDeclaration axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            OWLClassExpression sub = oneOf(axiom.getSubject());
            OWLClassExpression sup = factory.getOWLDataHasValue(axiom.getProperty(), axiom.getObject());
            factory.getOWLSubClassOfAxiom(sub, not(sup)).accept(this);
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            OWLClassExpression sub = oneOf(axiom.getSubject());
            OWLClassExpression sup = factory.getOWLObjectHasValue(axiom.getProperty(), axiom.getObject());
            factory.getOWLSubClassOfAxiom(sub, not(sup)).accept(this);
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            OWLClassExpression sub = oneOf(axiom.getSubject());
            OWLClassExpression sup = factory.getOWLObjectHasValue(axiom.getProperty(), axiom.getObject());
            OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(sub, sup);
            ax.accept(this);
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            result = and(factory.getOWLObjectSomeValuesFrom(axiom.getProperty(), factory.getOWLThing()),
                    not(axiom.getDomain()));
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            result = factory.getOWLObjectSomeValuesFrom(axiom.getProperty(), not(axiom.getRange()));
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }

        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            Set<OWLClassExpression> nominals = new HashSet<OWLClassExpression>();
            for (OWLIndividual ind : axiom.getIndividuals()) {
                nominals.add(not(oneOf(ind)));
            }
            result = and(nominals);
        }


        public void visit(OWLSubClassOfAxiom axiom) {
            OWLClassExpression sub = axiom.getSubClass();
            OWLClassExpression sup = axiom.getSuperClass();

            if (sup.isOWLNothing())
                result = sub;
            else if (sub.isOWLThing())
                result = not(sup);
            else
                result = and(sub, not(sup));
        }


        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(SWRLRule rule) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + rule);
        }

        public void visit(OWLHasKeyAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            throw new OWLRuntimeException("Not implemented: Cannot generate explanation for " + axiom);
        }
    }


    private static final Logger logger = Logger.getLogger(SatisfiabilityConverter.class.getName());

    private AxiomConverter converter;

    private OWLDataFactory factory;


    public SatisfiabilityConverter(OWLDataFactory factory) {
        this.factory = factory;

        converter = new AxiomConverter();
    }


    public OWLClassExpression convert(OWLAxiom axiom) {
        converter.reset();

        axiom.accept(converter);

        OWLClassExpression result = converter.getResult();

        if (result == null)
            throw new RuntimeException("Not supported yet");

        return result;
    }
}
