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
package org.semanticweb.owlapi.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * A configurable search.
 * 
 * @param <T>
 *        the generic type
 * @author ignazio
 */
public class Searcher<T> {

    public static Collection<OWLLiteral> values(
            Collection<OWLDataPropertyAssertionAxiom> axioms,
            OWLDataPropertyExpression p) {
        Set<OWLLiteral> literals = new HashSet<OWLLiteral>();
        for (OWLDataPropertyAssertionAxiom ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                literals.add(ax.getObject());
            }
        }
        return literals;
    }

    public static Collection<OWLIndividual> values(
            Collection<OWLObjectPropertyAssertionAxiom> axioms,
            OWLObjectPropertyExpression p) {
        Set<OWLIndividual> objects = new HashSet<OWLIndividual>();
        for (OWLObjectPropertyAssertionAxiom ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                objects.add(ax.getObject());
            }
        }
        return objects;
    }

    public static Collection<OWLLiteral> negValues(
            Collection<OWLNegativeDataPropertyAssertionAxiom> axioms,
            OWLDataPropertyExpression p) {
        Set<OWLLiteral> literals = new HashSet<OWLLiteral>();
        for (OWLNegativeDataPropertyAssertionAxiom ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                literals.add(ax.getObject());
            }
        }
        return literals;
    }

    public static Collection<OWLIndividual> negValues(
            Collection<OWLNegativeObjectPropertyAssertionAxiom> axioms,
            OWLObjectPropertyExpression p) {
        Set<OWLIndividual> objects = new HashSet<OWLIndividual>();
        for (OWLNegativeObjectPropertyAssertionAxiom ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                objects.add(ax.getObject());
            }
        }
        return objects;
    }

    public static Collection<OWLClassExpression> types(
            Collection<OWLClassAssertionAxiom> axioms) {
        Set<OWLClassExpression> objects = new HashSet<OWLClassExpression>();
        for (OWLClassAssertionAxiom ax : axioms) {
            objects.add(ax.getClassExpression());
        }
        return objects;
    }

    public static Collection<OWLIndividual> instances(
            Collection<OWLClassAssertionAxiom> axioms) {
        Set<OWLIndividual> objects = new HashSet<OWLIndividual>();
        for (OWLClassAssertionAxiom ax : axioms) {
            objects.add(ax.getIndividual());
        }
        return objects;
    }

    public static Collection<OWLObjectPropertyExpression> inverse(
            Collection<OWLInverseObjectPropertiesAxiom> axioms,
            OWLObjectPropertyExpression p) {
        List<OWLObjectPropertyExpression> toReturn = new ArrayList<OWLObjectPropertyExpression>();
        for (OWLInverseObjectPropertiesAxiom ax : axioms) {
            if (ax.getFirstProperty().equals(p)) {
                toReturn.add(ax.getSecondProperty());
            } else {
                toReturn.add(ax.getFirstProperty());
            }
        }
        return toReturn;
    }

    public static Collection<OWLAnnotationValue> values(
            Collection<OWLAnnotation> axioms) {
        return values(axioms, null);
    }

    public static Collection<OWLAnnotationValue> values(
            Collection<OWLAnnotation> axioms, OWLAnnotationProperty p) {
        Set<OWLAnnotationValue> toReturn = new HashSet<OWLAnnotationValue>();
        for (OWLAnnotation ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                toReturn.add(ax.getValue());
            }
        }
        return toReturn;
    }

    public static Collection<OWLAnnotation> annotations(
            Collection<? extends OWLAxiom> axioms) {
        return annotations(axioms, null);
    }

    public static Collection<OWLAnnotation> annotations(
            Collection<? extends OWLAxiom> axioms, OWLAnnotationProperty p) {
        Set<OWLAnnotation> toReturn = new HashSet<OWLAnnotation>();
        for (OWLAxiom ax : axioms) {
            Set<OWLAnnotation> c = annotations(ax, p);
            toReturn.addAll(c);
        }
        return toReturn;
    }

    public static Set<OWLAnnotation> annotations(OWLAxiom axiom,
            OWLAnnotationProperty p) {
        if (axiom instanceof OWLAnnotationAssertionAxiom) {
            if (p == null
                    || ((OWLAnnotationAssertionAxiom) axiom).getProperty()
                            .equals(p)) {
                return Collections
                        .singleton(((OWLAnnotationAssertionAxiom) axiom)
                                .getAnnotation());
            }
            return Collections.emptySet();
        }
        if (p != null) {
            return axiom.getAnnotations(p);
        }
        return axiom.getAnnotations();
    }

    public static <C extends OWLObject> Collection<C> equivalent(
            Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) equivalent(axioms, OWLObject.class);
    }

    public static <C extends OWLObject> Collection<C> equivalent(
            Collection<? extends OWLAxiom> axioms, Class<C> type) {
        Set<C> toReturn = new HashSet<C>();
        for (OWLAxiom ax : axioms) {
            Set<C> c = equivalent(ax);
            toReturn.addAll(c);
        }
        return toReturn;
    }

    public static <C extends OWLObject> Set<C> equivalent(OWLAxiom axiom) {
        return axiom.accept(new EquivalentVisitor<C>(true));
    }

    public static <C extends OWLObject> Collection<C> different(
            Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) different(axioms, OWLObject.class);
    }

    public static <C extends OWLObject> Collection<C> different(
            Collection<? extends OWLAxiom> axioms, Class<C> type) {
        Set<C> toReturn = new HashSet<C>();
        for (OWLAxiom ax : axioms) {
            Set<C> c = different(ax);
            toReturn.addAll(c);
        }
        return toReturn;
    }

    public static <C extends OWLObject> Set<C> different(OWLAxiom axiom) {
        return axiom.accept(new EquivalentVisitor<C>(false));
    }

    public static <C extends OWLObject> Collection<C> sub(
            Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) sub(axioms, OWLObject.class);
    }

    public static <C> Collection<C> sub(Collection<? extends OWLAxiom> axioms,
            Class<C> type) {
        List<C> toReturn = new ArrayList<C>();
        for (OWLAxiom ax : axioms) {
            C c = sub(ax);
            if (c != null) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }

    public static <C extends OWLObject> C sub(OWLAxiom axiom) {
        return axiom.accept(new SupSubVisitor<C>(false));
    }

    public static <C> Collection<C> sup(Collection<? extends OWLAxiom> axioms,
            Class<C> type) {
        List<C> toReturn = new ArrayList<C>();
        for (OWLAxiom ax : axioms) {
            C c = sup(ax);
            if (c != null) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }

    public static <C extends OWLObject> Collection<C> sup(
            Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) sup(axioms, OWLObject.class);
    }

    public static <C extends OWLObject> C sup(OWLAxiom axiom) {
        return axiom.accept(new SupSubVisitor<C>(true));
    }

    public static <C extends OWLObject> Collection<C> domain(
            Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) domain(axioms, OWLObject.class);
    }

    public static <C> Collection<C> domain(
            Collection<? extends OWLAxiom> axioms, Class<C> type) {
        List<C> toReturn = new ArrayList<C>();
        for (OWLAxiom ax : axioms) {
            C c = domain(ax);
            if (c != null) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }

    public static <C extends OWLObject> C domain(OWLAxiom axiom) {
        return axiom.accept(new DomainRangeVisitor<C>(false));
    }

    public static <C extends OWLObject> Collection<C> range(
            Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) range(axioms, OWLObject.class);
    }

    public static <C> Collection<C> range(
            Collection<? extends OWLAxiom> axioms, Class<C> type) {
        List<C> toReturn = new ArrayList<C>();
        for (OWLAxiom ax : axioms) {
            C c = range(ax);
            if (c != null) {
                toReturn.add(c);
            }
        }
        return toReturn;
    }

    public static <C extends OWLObject> C range(OWLAxiom axiom) {
        return axiom.accept(new DomainRangeVisitor<C>(true));
    }

    /**
     * Checks if is transitive.
     * 
     * @param e
     *        the e
     * @return true for transitive properties
     */
    public static boolean isTransitive(OWLOntology o, OWLObjectProperty e) {
        return !o.getTransitiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if is symmetric.
     * 
     * @param e
     *        the e
     * @return true for symmetric properties
     */
    public static boolean isSymmetric(OWLOntology o, OWLObjectProperty e) {
        return !o.getSymmetricObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if is asymmetric.
     * 
     * @param e
     *        the e
     * @return true for asymmetric properties
     */
    public static boolean isAsymmetric(OWLOntology o, OWLObjectProperty e) {
        return !o.getAsymmetricObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if is reflexive.
     * 
     * @param e
     *        the e
     * @return true for reflexive properties
     */
    public static boolean isReflexive(OWLOntology o, OWLObjectProperty e) {
        return !o.getReflexiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if is irreflexive.
     * 
     * @param e
     *        the e
     * @return true for irreflexive properties
     */
    public static boolean isIrreflexive(OWLOntology o, OWLObjectProperty e) {
        return !o.getIrreflexiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if is inverse functional.
     * 
     * @param e
     *        the e
     * @return true for inverse functional properties
     */
    public static boolean
            isInverseFunctional(OWLOntology o, OWLObjectProperty e) {
        return !o.getInverseFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if is functional.
     * 
     * @param e
     *        the e
     * @return true for functional object properties
     */
    public static boolean isFunctional(OWLOntology o, OWLObjectProperty e) {
        return !o.getFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if is functional.
     * 
     * @param e
     *        the e
     * @return true for functional data properties
     */
    public static boolean isFunctional(OWLOntology o, OWLDataProperty e) {
        return !o.getFunctionalDataPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if is defined.
     * 
     * @param c
     *        the c
     * @return true for defined classes
     */
    public static boolean isDefined(OWLOntology o, OWLClass c) {
        return !o.getEquivalentClassesAxioms(c).isEmpty();
    }
}
