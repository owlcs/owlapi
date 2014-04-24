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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
 * A collection of static search utilities.
 * 
 * @author ignazio
 */
public class Searcher {

    /**
     * Retrieve literals from a collection of assertions.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional property to match. Null means all.
     * @return literals
     */
    @Nonnull
    public static Collection<OWLLiteral> values(
            @Nonnull Collection<OWLDataPropertyAssertionAxiom> axioms,
            @Nullable OWLDataPropertyExpression p) {
        Set<OWLLiteral> literals = new HashSet<OWLLiteral>();
        for (OWLDataPropertyAssertionAxiom ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                literals.add(ax.getObject());
            }
        }
        return literals;
    }

    /**
     * Retrieve objects from a collection of assertions.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional property to match. Null means all.
     * @return objects
     */
    @Nonnull
    public static Collection<OWLIndividual> values(
            @Nonnull Collection<OWLObjectPropertyAssertionAxiom> axioms,
            @Nullable OWLObjectPropertyExpression p) {
        Set<OWLIndividual> objects = new HashSet<OWLIndividual>();
        for (OWLObjectPropertyAssertionAxiom ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                objects.add(ax.getObject());
            }
        }
        return objects;
    }

    /**
     * Retrieve literals from a collection of negative assertions.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional property to match. Null means all.
     * @return literals
     */
    @Nonnull
    public static Collection<OWLLiteral> negValues(
            @Nonnull Collection<OWLNegativeDataPropertyAssertionAxiom> axioms,
            @Nullable OWLDataPropertyExpression p) {
        Set<OWLLiteral> literals = new HashSet<OWLLiteral>();
        for (OWLNegativeDataPropertyAssertionAxiom ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                literals.add(ax.getObject());
            }
        }
        return literals;
    }

    /**
     * Retrieve objects from a collection of negative assertions.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional property to match. Null means all.
     * @return objects
     */
    @Nonnull
    public static
            Collection<OWLIndividual>
            negValues(
                    @Nonnull Collection<OWLNegativeObjectPropertyAssertionAxiom> axioms,
                    @Nullable OWLObjectPropertyExpression p) {
        Set<OWLIndividual> objects = new HashSet<OWLIndividual>();
        for (OWLNegativeObjectPropertyAssertionAxiom ax : axioms) {
            if (p == null || ax.getProperty().equals(p)) {
                objects.add(ax.getObject());
            }
        }
        return objects;
    }

    /**
     * Retrieve classes from class assertions.
     * 
     * @param axioms
     *        axioms
     * @return classes
     */
    @Nonnull
    public static Collection<OWLClassExpression> types(
            @Nonnull Collection<OWLClassAssertionAxiom> axioms) {
        Set<OWLClassExpression> objects = new HashSet<OWLClassExpression>();
        for (OWLClassAssertionAxiom ax : axioms) {
            objects.add(ax.getClassExpression());
        }
        return objects;
    }

    /**
     * Retrieve individuals from class assertions.
     * 
     * @param axioms
     *        axioms
     * @return individuals
     */
    @Nonnull
    public static Collection<OWLIndividual> instances(
            @Nonnull Collection<OWLClassAssertionAxiom> axioms) {
        Set<OWLIndividual> objects = new HashSet<OWLIndividual>();
        for (OWLClassAssertionAxiom ax : axioms) {
            objects.add(ax.getIndividual());
        }
        return objects;
    }

    /**
     * Retrieve inverses from a collection of inverse axioms.
     * 
     * @param axioms
     *        axioms to check
     * @param p
     *        property to match; not returned in the set
     * @return inverses of p
     */
    @Nonnull
    public static Collection<OWLObjectPropertyExpression> inverse(
            @Nonnull Collection<OWLInverseObjectPropertiesAxiom> axioms,
            @Nonnull OWLObjectPropertyExpression p) {
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

    /**
     * Retrieve annotation values from annotations
     * 
     * @param annotations
     *        annotations
     * @return annotation values
     */
    @Nonnull
    public static Collection<OWLAnnotationValue> values(
            @Nonnull Collection<OWLAnnotation> annotations) {
        return values(annotations, null);
    }

    /**
     * Retrieve annotation values from annotations
     * 
     * @param annotations
     *        annotations
     * @param p
     *        optional annotation property to filter. Null means all.
     * @return annotation values
     */
    @Nonnull
    public static Collection<OWLAnnotationValue> values(
            @Nonnull Collection<OWLAnnotation> annotations,
            @Nullable OWLAnnotationProperty p) {
        Set<OWLAnnotationValue> toReturn = new HashSet<OWLAnnotationValue>();
        for (OWLAnnotation ax : annotations) {
            if (p == null || ax.getProperty().equals(p)) {
                toReturn.add(ax.getValue());
            }
        }
        return toReturn;
    }

    /**
     * Retrieve annotations from a collection of axioms. For regular axioms,
     * their annotations are retrieved; for annotation assertion axioms, their
     * asserted annotation is retrieved as well.
     * 
     * @param axioms
     *        axioms
     * @return annotations
     */
    @Nonnull
    public static Collection<OWLAnnotation> annotations(
            @Nonnull Collection<? extends OWLAxiom> axioms) {
        return annotations(axioms, null);
    }

    /**
     * Retrieve annotations from a collection of axioms. For regular axioms,
     * their annotations are retrieved; for annotation assertion axioms, their
     * asserted annotation is retrieved as well.
     * 
     * @param axioms
     *        axioms
     * @param p
     *        optional annotation property to filter. Null means all.
     * @return annotations
     */
    @Nonnull
    public static Collection<OWLAnnotation> annotations(
            @Nonnull Collection<? extends OWLAxiom> axioms,
            @Nullable OWLAnnotationProperty p) {
        Set<OWLAnnotation> toReturn = new HashSet<OWLAnnotation>();
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            Set<OWLAnnotation> c = annotations(ax, p);
            toReturn.addAll(c);
        }
        return toReturn;
    }

    /**
     * Retrieve annotations from an axiom. For regular axioms, their annotations
     * are retrieved; for annotation assertion axioms, their asserted annotation
     * is retrieved as well.
     * 
     * @param axiom
     *        axiom
     * @param p
     *        optional annotation property to filter. Null means all.
     * @return annotations
     */
    @Nonnull
    public static Set<OWLAnnotation> annotations(@Nonnull OWLAxiom axiom,
            @Nullable OWLAnnotationProperty p) {
        Set<OWLAnnotation> set = new HashSet<OWLAnnotation>();
        if (axiom instanceof OWLAnnotationAssertionAxiom) {
            if (p == null
                    || ((OWLAnnotationAssertionAxiom) axiom).getProperty()
                            .equals(p)) {
                set.add(((OWLAnnotationAssertionAxiom) axiom).getAnnotation());
            }
        }
        if (p != null) {
            set.addAll(axiom.getAnnotations(p));
        } else {
            set.addAll(axiom.getAnnotations());
        }
        return set;
    }

    /**
     * Retrieve equivalent entities from axioms, including individuals from
     * sameAs axioms. A mixture of axiom types can be passed in, as long as the
     * entity type they contain is compatible with the return type for the
     * collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return equivalent entities
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Collection<C> equivalent(
            @Nonnull Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) equivalent(axioms, OWLObject.class);
    }

    /**
     * Retrieve equivalent entities from axioms, including individuals from
     * sameAs axioms. A mixture of axiom types can be passed in, as long as the
     * entity type they contain is compatible with the return type for the
     * collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return equivalent entities
     */
    @Nonnull
    public static <C extends OWLObject> Collection<C> equivalent(
            @Nonnull Collection<? extends OWLAxiom> axioms,
            @SuppressWarnings("unused") @Nonnull Class<C> type) {
        Set<C> toReturn = new HashSet<C>();
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            Set<C> c = equivalent(ax);
            toReturn.addAll(c);
        }
        return toReturn;
    }

    /**
     * Retrieve equivalent entities from an axiom, including individuals from
     * sameAs axioms.
     * 
     * @param axiom
     *        axiom
     * @param <C>
     *        type contained in the returned collection
     * @return equivalent entities
     */
    @Nonnull
    public static <C extends OWLObject> Set<C> equivalent(
            @Nonnull OWLAxiom axiom) {
        return axiom.accept(new EquivalentVisitor<C>(true));
    }

    /**
     * Retrieve disjoint entities from axioms, including individuals from
     * differentFrom axioms. A mixture of axiom types can be passed in, as long
     * as the entity type they contain is compatible with the return type for
     * the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return disjoint entities
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Collection<C> different(
            @Nonnull Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) different(axioms, OWLObject.class);
    }

    /**
     * Retrieve disjoint entities from axioms, including individuals from
     * differentFrom axioms. A mixture of axiom types can be passed in, as long
     * as the entity type they contain is compatible with the return type for
     * the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return disjoint entities
     */
    @Nonnull
    public static <C extends OWLObject> Collection<C> different(
            @Nonnull Collection<? extends OWLAxiom> axioms,
            @SuppressWarnings("unused") @Nonnull Class<C> type) {
        Set<C> toReturn = new HashSet<C>();
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            Set<C> c = different(ax);
            toReturn.addAll(c);
        }
        return toReturn;
    }

    /**
     * Retrieve disjoint entities from an axiom, including individuals from
     * differentFrom axioms.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return disjoint entities
     */
    @Nonnull
    public static <C extends OWLObject> Set<C>
            different(@Nonnull OWLAxiom axiom) {
        return axiom.accept(new EquivalentVisitor<C>(false));
    }

    /**
     * Retrieve the sub part of axioms, i.e., subclass or subproperty. A mixture
     * of axiom types can be passed in, as long as the entity type they contain
     * is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return sub expressions
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Collection<C> sub(
            @Nonnull Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) sub(axioms, OWLObject.class);
    }

    /**
     * Retrieve the sub part of axioms, i.e., subclass or subproperty. A mixture
     * of axiom types can be passed in, as long as the entity type they contain
     * is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return sub expressions
     */
    @Nonnull
    public static <C extends OWLObject> Collection<C> sub(
            @Nonnull Collection<? extends OWLAxiom> axioms,
            @SuppressWarnings("unused") @Nonnull Class<C> type) {
        List<C> toReturn = new ArrayList<C>();
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            C c = sub(ax);
            toReturn.add(c);
        }
        return toReturn;
    }

    /**
     * Retrieve the sub part of an axiom, i.e., subclass or subproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return sub expressions
     */
    @Nonnull
    public static <C extends OWLObject> C sub(@Nonnull OWLAxiom axiom) {
        return axiom.accept(new SupSubVisitor<C>(false));
    }

    /**
     * Retrieve the super part of axioms, i.e., superclass or superproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return sub expressions
     */
    @Nonnull
    public static <C extends OWLObject> Collection<C> sup(
            @Nonnull Collection<? extends OWLAxiom> axioms,
            @SuppressWarnings("unused") @Nonnull Class<C> type) {
        List<C> toReturn = new ArrayList<C>();
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            C c = sup(ax);
            toReturn.add(c);
        }
        return toReturn;
    }

    /**
     * Retrieve the super part of axioms, i.e., superclass or superproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return sub expressions
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Collection<C> sup(
            @Nonnull Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) sup(axioms, OWLObject.class);
    }

    /**
     * Retrieve the super part of an axiom, i.e., superclass or superproperty. A
     * mixture of axiom types can be passed in, as long as the entity type they
     * contain is compatible with the return type for the collection.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return sub expressions
     */
    @Nonnull
    public static <C extends OWLObject> C sup(@Nonnull OWLAxiom axiom) {
        return axiom.accept(new SupSubVisitor<C>(true));
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return sub expressions
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Collection<C> domain(
            @Nonnull Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) domain(axioms, OWLObject.class);
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return sub expressions
     */
    @Nonnull
    public static <C extends OWLObject> Collection<C> domain(
            @Nonnull Collection<? extends OWLAxiom> axioms,
            @SuppressWarnings("unused") @Nonnull Class<C> type) {
        List<C> toReturn = new ArrayList<C>();
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            C c = domain(ax);
            toReturn.add(c);
        }
        return toReturn;
    }

    /**
     * Retrieve the domains from domain axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return sub expressions
     */
    @Nonnull
    public static <C extends OWLObject> C domain(@Nonnull OWLAxiom axiom) {
        return axiom.accept(new DomainVisitor<C>());
    }

    /**
     * Retrieve the ranges from range axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @return sub expressions
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <C extends OWLObject> Collection<C> range(
            @Nonnull Collection<? extends OWLAxiom> axioms) {
        return (Collection<C>) range(axioms, OWLObject.class);
    }

    /**
     * Retrieve the ranges from range axioms. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axioms
     *        axioms
     * @param type
     *        type contained in the returned collection
     * @return sub expressions
     */
    @Nonnull
    public static <C extends OWLObject> Collection<C> range(
            @Nonnull Collection<? extends OWLAxiom> axioms,
            @SuppressWarnings("unused") @Nonnull Class<C> type) {
        List<C> toReturn = new ArrayList<C>();
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            C c = range(ax);
            toReturn.add(c);
        }
        return toReturn;
    }

    /**
     * Retrieve the ranges from a range axiom. A mixture of axiom types can be
     * passed in.
     * 
     * @param <C>
     *        returned type
     * @param axiom
     *        axiom
     * @return sub expressions
     */
    @Nonnull
    public static <C extends OWLObject> C range(@Nonnull OWLAxiom axiom) {
        return axiom.accept(new RangeVisitor<C>());
    }

    /**
     * Checks if e is declared transitive in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for transitive properties
     */
    public static boolean isTransitive(@Nonnull OWLOntology o,
            @Nonnull OWLObjectProperty e) {
        return !o.getTransitiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared symmetric in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for symmetric properties
     */
    public static boolean isSymmetric(@Nonnull OWLOntology o,
            @Nonnull OWLObjectProperty e) {
        return !o.getSymmetricObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared asymmetric in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for asymmetric properties
     */
    public static boolean isAsymmetric(@Nonnull OWLOntology o,
            @Nonnull OWLObjectProperty e) {
        return !o.getAsymmetricObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared reflexive in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for reflexive properties
     */
    public static boolean isReflexive(@Nonnull OWLOntology o,
            @Nonnull OWLObjectProperty e) {
        return !o.getReflexiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared irreflexive in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for irreflexive properties
     */
    public static boolean isIrreflexive(@Nonnull OWLOntology o,
            @Nonnull OWLObjectProperty e) {
        return !o.getIrreflexiveObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared inverse functional in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for inverse functional properties
     */
    public static boolean isInverseFunctional(@Nonnull OWLOntology o,
            @Nonnull OWLObjectProperty e) {
        return !o.getInverseFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared functional in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for functional object properties
     */
    public static boolean isFunctional(@Nonnull OWLOntology o,
            @Nonnull OWLObjectProperty e) {
        return !o.getFunctionalObjectPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if e is declared functional in o.
     * 
     * @param o
     *        ontology
     * @param e
     *        property
     * @return true for functional data properties
     */
    public static boolean isFunctional(@Nonnull OWLOntology o,
            @Nonnull OWLDataProperty e) {
        return !o.getFunctionalDataPropertyAxioms(e).isEmpty();
    }

    /**
     * Checks if c is defined (is included in equivalent axioms) in o.
     * 
     * @param o
     *        ontology
     * @param c
     *        class
     * @return true for defined classes
     */
    public static boolean
            isDefined(@Nonnull OWLOntology o, @Nonnull OWLClass c) {
        return !o.getEquivalentClassesAxioms(c).isEmpty();
    }
}
