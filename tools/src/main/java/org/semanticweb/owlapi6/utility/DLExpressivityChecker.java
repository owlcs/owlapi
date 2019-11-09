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
package org.semanticweb.owlapi6.utility;

import static org.semanticweb.owlapi6.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utility.Construct.ATOMIC_NEGATION;
import static org.semanticweb.owlapi6.utility.Construct.CONCEPT_COMPLEX_NEGATION;
import static org.semanticweb.owlapi6.utility.Construct.CONCEPT_INTERSECTION;
import static org.semanticweb.owlapi6.utility.Construct.CONCEPT_UNION;
import static org.semanticweb.owlapi6.utility.Construct.D;
import static org.semanticweb.owlapi6.utility.Construct.F;
import static org.semanticweb.owlapi6.utility.Construct.FULL_EXISTENTIAL;
import static org.semanticweb.owlapi6.utility.Construct.LIMITED_EXISTENTIAL;
import static org.semanticweb.owlapi6.utility.Construct.N;
import static org.semanticweb.owlapi6.utility.Construct.NOMINALS;
import static org.semanticweb.owlapi6.utility.Construct.Q;
import static org.semanticweb.owlapi6.utility.Construct.ROLE_COMPLEX;
import static org.semanticweb.owlapi6.utility.Construct.ROLE_DOMAIN_RANGE;
import static org.semanticweb.owlapi6.utility.Construct.ROLE_HIERARCHY;
import static org.semanticweb.owlapi6.utility.Construct.ROLE_INVERSE;
import static org.semanticweb.owlapi6.utility.Construct.ROLE_REFLEXIVITY_CHAINS;
import static org.semanticweb.owlapi6.utility.Construct.ROLE_TRANSITIVE;
import static org.semanticweb.owlapi6.utility.Construct.UNIVERSAL_RESTRICTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.semanticweb.owlapi6.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectComplementOf;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectType;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLPropertyRange;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class DLExpressivityChecker implements OWLObjectVisitor {

    /**
     * @return Collection of Languages that include all constructs used in the ontology. Each
     *         language returned allows for all constructs found and has no sublanguages that also
     *         allow for all constructs found. E.g., if FL is returned, FL0 and FLMNUS cannot be
     *         returned.
     */
    public Collection<Languages> expressibleInLanguages() {
        return Arrays.stream(Languages.values()).filter(this::minimal).collect(Collectors.toList());
    }

    /**
     * @param l language to check
     * @return true if l is minimal, i.e., all sublanguages of l cannot represent all the constructs
     *         found, but l can.
     */
    public boolean minimal(Languages l) {
        if (!l.components.containsAll(getOrderedConstructs())) {
            // not minimal because it does not cover the constructs found
            return false;
        }
        return Arrays.stream(Languages.values()).filter(p -> p.isSubLanguageOf(l))
            .noneMatch(this::minimal);
    }

    /**
     * @param l language to check
     * @return true if l is sufficient to express the ontology, i.e., if all constructs found in the
     *         ontology are included in the language
     */
    public boolean isWithin(Languages l) {
        return l.components.containsAll(getOrderedConstructs());
    }

    /**
     * @param c construct to check
     * @return true if the matched constructs contain c.
     */
    public boolean has(Construct c) {
        return getOrderedConstructs().contains(c);
    }

    private Set<Construct> constructs;
    private final List<OWLOntology> ontologies;

    /**
     * @param ontologies ontologies
     */
    public DLExpressivityChecker(Collection<OWLOntology> ontologies) {
        this.ontologies = new ArrayList<>(ontologies);
    }

    /**
     * @param ontologies ontologies
     */
    public DLExpressivityChecker(Stream<OWLOntology> ontologies) {
        this.ontologies = asList(ontologies);
    }

    private static boolean isTop(OWLClassExpression classExpression) {
        return classExpression.isOWLThing();
    }

    /**
     * @return ordered constructs
     */
    public List<Construct> getConstructs() {
        return new ArrayList<>(getOrderedConstructs());
    }

    /**
     * @return DL name
     */
    public String getDescriptionLogicName() {
        return getOrderedConstructs().stream().map(Object::toString).collect(Collectors.joining());
    }

    private void accept(OWLObject o) {
        o.accept(this);
    }

    private Set<Construct> getOrderedConstructs() {
        if (constructs == null) {
            constructs = new TreeSet<>();
            ontologies.stream().flatMap(OWLOntology::logicalAxioms).forEach(this::accept);
        }
        Construct.trim(constructs);
        return constructs;
    }

    private void addConstruct(Construct c) {
        if (constructs == null) {
            constructs = new TreeSet<>();
        }
        // Rr+I = R + I
        if (c == ROLE_INVERSE && constructs.contains(ROLE_REFLEXIVITY_CHAINS)) {
            constructs.add(c);
            constructs.remove(ROLE_REFLEXIVITY_CHAINS);
            constructs.add(ROLE_COMPLEX);
        } else if (c == ROLE_REFLEXIVITY_CHAINS && constructs.contains(ROLE_INVERSE)) {
            constructs.add(ROLE_COMPLEX);
        } else {
            constructs.add(c);
        }
    }

    private boolean isAtomic(OWLClassExpression classExpression) {
        if (classExpression.isAnonymous()) {
            return false;
        }
        return ontologies.stream()
            .noneMatch(ont -> ont.axioms((OWLClass) classExpression, EXCLUDED).count() > 0);
    }

    private void checkCardinality(
        OWLCardinalityRestriction<? extends OWLPropertyRange> restriction) {
        if (restriction.isQualified()) {
            addConstruct(Q);
        } else {
            addConstruct(N);
        }
        accept(restriction.getFiller());
        accept(restriction.getProperty());
    }

    private static EnumMap<OWLObjectType, List<Construct>> constructMap = constructMap();

    private static List<Construct> l(Construct... c) {
        return Arrays.asList(c);
    }

    private static EnumMap<OWLObjectType, List<Construct>> constructMap() {
        EnumMap<OWLObjectType, List<Construct>> map = new EnumMap<>(OWLObjectType.class);
        map.put(OWLObjectType.INVERSE_OBJECT, l(ROLE_INVERSE));
        map.put(OWLObjectType.DATA_PROPERTY, l(D));
        map.put(OWLObjectType.NOT_DATA, l(D));
        map.put(OWLObjectType.ONEOF_DATA, l(D));
        map.put(OWLObjectType.DATATYPE_RESTRICTION, l(D));
        map.put(OWLObjectType.LITERAL, l(D));
        map.put(OWLObjectType.FACET_RESTRICTION, l(D));
        map.put(OWLObjectType.AND_OBJECT, l(CONCEPT_INTERSECTION));
        map.put(OWLObjectType.OR_OBJECT, l(CONCEPT_UNION));
        map.put(OWLObjectType.FORALL_DATA, l(UNIVERSAL_RESTRICTION));
        map.put(OWLObjectType.FORALL_OBJECT, l(UNIVERSAL_RESTRICTION));
        map.put(OWLObjectType.HASVALUE_OBJECT, l(NOMINALS, FULL_EXISTENTIAL));
        map.put(OWLObjectType.HASSELF_OBJECT, l(ROLE_COMPLEX));
        map.put(OWLObjectType.ONEOF_OBJECT, l(CONCEPT_UNION, NOMINALS));
        map.put(OWLObjectType.SOME_DATA, l(FULL_EXISTENTIAL));
        map.put(OWLObjectType.HASVALUE_DATA, l(D));
        map.put(OWLObjectType.ASYMMETRIC, l(ROLE_COMPLEX));
        map.put(OWLObjectType.REFLEXIVE, l(ROLE_REFLEXIVITY_CHAINS));
        map.put(OWLObjectType.DISJOINT_CLASSES, l(CONCEPT_COMPLEX_NEGATION));
        map.put(OWLObjectType.DATA_DOMAIN, l(ROLE_DOMAIN_RANGE, D));
        map.put(OWLObjectType.OBJECT_DOMAIN, l(ROLE_DOMAIN_RANGE));
        map.put(OWLObjectType.EQUIVALENT_OBJECT, l(ROLE_HIERARCHY));
        map.put(OWLObjectType.DIFFERENT_INDIVIDUALS,
            l(CONCEPT_UNION, NOMINALS, CONCEPT_COMPLEX_NEGATION));
        map.put(OWLObjectType.DISJOINT_DATA, l(D));
        map.put(OWLObjectType.DISJOINT_OBJECT, l(ROLE_COMPLEX));
        map.put(OWLObjectType.OBJECT_RANGE, l(ROLE_DOMAIN_RANGE));
        map.put(OWLObjectType.FUNCTIONAL_OBJECT, l(F));
        map.put(OWLObjectType.SUB_OBJECT, l(ROLE_HIERARCHY));
        map.put(OWLObjectType.DISJOINT_UNION, l(CONCEPT_UNION, CONCEPT_COMPLEX_NEGATION));
        map.put(OWLObjectType.SYMMETRIC, l(ROLE_INVERSE));
        map.put(OWLObjectType.DATA_RANGE, l(ROLE_DOMAIN_RANGE, D));
        map.put(OWLObjectType.FUNCTIONAL_DATA, l(F, D));
        map.put(OWLObjectType.EQUIVALENT_DATA, l(ROLE_HIERARCHY, D));
        map.put(OWLObjectType.DATA_ASSERTION, l(D));
        map.put(OWLObjectType.TRANSITIVE, l(ROLE_TRANSITIVE));
        map.put(OWLObjectType.IRREFLEXIVE, l(ROLE_COMPLEX));
        map.put(OWLObjectType.SUB_DATA, l(ROLE_HIERARCHY, D));
        map.put(OWLObjectType.INVERSE_FUNCTIONAL, l(ROLE_INVERSE, F));
        map.put(OWLObjectType.SAME_INDIVIDUAL, l(NOMINALS));
        map.put(OWLObjectType.SUB_PROPERTY_CHAIN, l(ROLE_REFLEXIVITY_CHAINS));
        map.put(OWLObjectType.INVERSE, l(ROLE_INVERSE));
        return map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doDefault(OWLObject object) {
        if (object instanceof OWLCardinalityRestriction) {
            checkCardinality((OWLCardinalityRestriction<? extends OWLPropertyRange>) object);
            return;
        }
        constructMap.getOrDefault(object.type(), Collections.emptyList())
            .forEach(this::addConstruct);
        object.componentStream().forEach(this::iterate);
    }

    @SuppressWarnings("unchecked")
    protected void iterate(Object o) {
        if (o instanceof Collection) {
            ((Collection<? extends OWLObject>) o).forEach(this::accept);
        } else if (o instanceof OWLObject) {
            accept((OWLObject) o);
        }
    }

    // class expressions
    @Override
    public void visit(OWLObjectComplementOf ce) {
        if (isAtomic(ce)) {
            addConstruct(ATOMIC_NEGATION);
        } else {
            addConstruct(CONCEPT_COMPLEX_NEGATION);
        }
        accept(ce.getOperand());
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        if (isTop(ce.getFiller())) {
            addConstruct(LIMITED_EXISTENTIAL);
        } else {
            addConstruct(FULL_EXISTENTIAL);
        }
        accept(ce.getProperty());
        accept(ce.getFiller());
    }
}
