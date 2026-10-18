/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2020, Marc Robin Nolte
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.modularity.locality;
/*
 * Following com.clarkparsia.owlapi.modularity.locality.SyntacticLocalityEvaluator, and
 * modified for a more modern, thread safe and maintainable implementation. Original Copyright is:
 *
 * This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
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
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * Evaluates syntactic bottom and top locality with respect to an entity signature.
 *
 * <p>This is a <em>sound but deliberately incomplete</em> approximation of semantic
 * locality: {@code isLocal(axiom, signature) == true} implies that the axiom is
 * semantically local (under the usual OWL 2 Direct Semantics), but a semantically
 * local axiom may still return {@code false}. In particular, unfamiliar data
 * ranges and unsupported logical axiom forms are conservatively non-local.
 * Such false negatives may enlarge an extracted module but must not make it
 * incorrect.</p>
 *
 * <p>Named classes and roles outside the signature are interpreted as empty
 * for {@link #BOTTOM} and as universal for {@link #TOP}. The built-in entities
 * {@code owl:Thing}, {@code owl:Nothing}, and the built-in top/bottom object and
 * data properties always retain their fixed meanings, even if omitted from the
 * signature. Individuals and datatypes are never replaced.</p>
 *
 * <p>All evaluation state is local to the invocation. Enum instances are
 * immutable and can be called concurrently, provided the supplied signature
 * collection is not mutated concurrently while it is copied.</p>
 *
 * @author Marc Robin Nolte (modified version)
 */
public enum SyntacticLocalityEvaluator implements LocalityEvaluator {
    /** Syntactic locality for empty interpretations of absent class and role names. */
    BOTTOM,
    /** Syntactic locality for universal interpretations of absent class and role names. */
    TOP;

    /**
     * Reports whether the data range is syntactically a built-in datatype.
     *
     * <p>Retained for compatibility with the earlier implementation. Being a
     * built-in datatype establishes nonemptiness, but not an arbitrary lower
     * cardinality bound (for example, {@code xsd:boolean} has two values).</p>
     *
     * @param dataRange the range to examine
     * @return {@code true} for the top datatype or another built-in datatype
     */
    protected static boolean isTopOrBuiltInDatatype(OWLDataRange dataRange) {
        if (!dataRange.isOWLDatatype()) {
            return false;
        }
        OWLDatatype datatype = dataRange.asOWLDatatype();
        return datatype.isTopDatatype() || datatype.isBuiltIn();
    }

    /**
     * Reports whether a datatype is known to have an infinite value space.
     *
     * @param dataRange the range to examine
     * @return {@code true} for built-in infinite datatypes, including rdfs:Literal
     */
    static boolean isTopOrBuiltInInfiniteDatatype(OWLDataRange dataRange) {
        if (!dataRange.isOWLDatatype()) {
            return false;
        }
        OWLDatatype datatype = dataRange.asOWLDatatype();
        return datatype.isTopDatatype()
                || datatype.isBuiltIn() && !datatype.getBuiltInDatatype().isFinite();
    }

    /**
     * Checks syntactic locality of an axiom.
     *
     * @param axiom the axiom to check
     * @param signature the entities that must retain their interpretation
     * @return {@code true} only if locality is established by a sound syntactic rule
     * @throws NullPointerException if an argument is {@code null}
     */
    @Override
    public boolean isLocal(OWLAxiom axiom, Collection<OWLEntity> signature) {
        Objects.requireNonNull(axiom, "axiom cannot be null");
        Objects.requireNonNull(signature, "signature cannot be null");
        if (!axiom.isLogicalAxiom()) {
            // Annotation and declaration axioms do not constrain Direct Semantics.
            return true;
        }
        ExpressionClassifier classifier = new ExpressionClassifier(this, new HashSet<>(signature));
        AxiomClassifier visitor = new AxiomClassifier(classifier);
        axiom.accept(visitor);
        return visitor.local;
    }

    /**
     * Pure recursive checks for sufficient top/bottom equivalence conditions.
     * Every positive conclusion is sound; unrecognised cases return false.
     */
    private static final class ExpressionClassifier {
        private final SyntacticLocalityEvaluator locality;
        private final Set<OWLEntity> signature;

        ExpressionClassifier(SyntacticLocalityEvaluator locality, Set<OWLEntity> signature) {
            this.locality = locality;
            this.signature = signature;
        }

        boolean objectBottom(OWLObjectPropertyExpression property) {
            OWLEntity name = property.getNamedProperty();
            // Inverses of the universal and empty roles remain universal and empty.
            if (name.isBottomEntity()) {
                return true;
            }
            return !name.isTopEntity() && locality == BOTTOM && !signature.contains(name);
        }

        boolean objectTop(OWLObjectPropertyExpression property) {
            OWLEntity name = property.getNamedProperty();
            if (name.isTopEntity()) {
                return true;
            }
            return !name.isBottomEntity() && locality == TOP && !signature.contains(name);
        }

        boolean dataBottom(OWLDataPropertyExpression property) {
            OWLEntity name = property.asOWLDataProperty();
            if (name.isBottomEntity()) {
                return true;
            }
            return !name.isTopEntity() && locality == BOTTOM && !signature.contains(name);
        }

        boolean dataTop(OWLDataPropertyExpression property) {
            OWLEntity name = property.asOWLDataProperty();
            if (name.isTopEntity()) {
                return true;
            }
            return !name.isBottomEntity() && locality == TOP && !signature.contains(name);
        }

        /** Sufficient conditions for a class expression to denote the empty set. */
        boolean bottom(OWLClassExpression ce) {
            if (ce instanceof OWLClass) {
                OWLClass c = (OWLClass) ce;
                return c.isOWLNothing() || !c.isOWLThing()
                        && locality == BOTTOM && !signature.contains(c);
            }
            if (ce instanceof OWLObjectComplementOf) {
                return top(((OWLObjectComplementOf) ce).getOperand());
            }
            if (ce instanceof OWLObjectIntersectionOf) {
                // An intersection is empty if at least one conjunct is empty.
                return ((OWLObjectIntersectionOf) ce).operands().anyMatch(this::bottom);
            }
            if (ce instanceof OWLObjectUnionOf) {
                // A union is empty only if every disjunct is empty.
                return ((OWLObjectUnionOf) ce).operands().allMatch(this::bottom);
            }
            if (ce instanceof OWLObjectOneOf) {
                // Named/anonymous individuals are not replaced by the locality mode.
                return ((OWLObjectOneOf) ce).individuals().count() == 0;
            }
            if (ce instanceof OWLObjectSomeValuesFrom) {
                OWLObjectSomeValuesFrom c = (OWLObjectSomeValuesFrom) ce;
                return objectBottom(c.getProperty()) || bottom(c.getFiller());
            }
            if (ce instanceof OWLObjectAllValuesFrom) {
                OWLObjectAllValuesFrom c = (OWLObjectAllValuesFrom) ce;
                return objectTop(c.getProperty()) && bottom(c.getFiller());
            }
            if (ce instanceof OWLObjectHasSelf) {
                return objectBottom(((OWLObjectHasSelf) ce).getProperty());
            }
            if (ce instanceof OWLObjectHasValue) {
                return objectBottom(((OWLObjectHasValue) ce).getProperty());
            }
            if (ce instanceof OWLObjectMinCardinality) {
                OWLObjectMinCardinality c = (OWLObjectMinCardinality) ce;
                return c.getCardinality() > 0
                        && (objectBottom(c.getProperty()) || bottom(c.getFiller()));
            }
            if (ce instanceof OWLObjectMaxCardinality) {
                OWLObjectMaxCardinality c = (OWLObjectMaxCardinality) ce;
                // With the universal role and a universal filler, every element
                // has at least one qualifying successor (itself). Only <= 0 is
                // guaranteed empty; <= 1 is satisfiable in a singleton domain.
                return c.getCardinality() == 0
                        && objectTop(c.getProperty()) && top(c.getFiller());
            }
            if (ce instanceof OWLObjectExactCardinality) {
                OWLObjectExactCardinality c = (OWLObjectExactCardinality) ce;
                if (c.getCardinality() == 0) {
                    return objectTop(c.getProperty()) && top(c.getFiller());
                }
                // Positive exact cardinality entails positive minimum cardinality.
                return objectBottom(c.getProperty()) || bottom(c.getFiller());
            }
            if (ce instanceof OWLDataSomeValuesFrom) {
                OWLDataSomeValuesFrom c = (OWLDataSomeValuesFrom) ce;
                return dataBottom(c.getProperty()) || DataRangeFacts.empty(c.getFiller());
            }
            if (ce instanceof OWLDataAllValuesFrom) {
                OWLDataAllValuesFrom c = (OWLDataAllValuesFrom) ce;
                // NOT simply '!isTopDatatype()': a complex data range may still
                // denote all data values (e.g. D union not D).
                return dataTop(c.getProperty()) && DataRangeFacts.proper(c.getFiller());
            }
            if (ce instanceof OWLDataHasValue) {
                return dataBottom(((OWLDataHasValue) ce).getProperty());
            }
            if (ce instanceof OWLDataMinCardinality) {
                OWLDataMinCardinality c = (OWLDataMinCardinality) ce;
                return c.getCardinality() > 0
                        && (dataBottom(c.getProperty())
                        || DataRangeFacts.upperBound(c.getFiller()) < c.getCardinality());
            }
            if (ce instanceof OWLDataMaxCardinality) {
                OWLDataMaxCardinality c = (OWLDataMaxCardinality) ce;
                return dataTop(c.getProperty())
                        && DataRangeFacts.atLeast(c.getFiller(), ((long) c.getCardinality()) + 1);
            }
            if (ce instanceof OWLDataExactCardinality) {
                OWLDataExactCardinality c = (OWLDataExactCardinality) ce;
                int n = c.getCardinality();
                // Exact n is impossible when the available data range has fewer
                // than n values, or when a universal data role has more than n.
                return (n > 0 && (dataBottom(c.getProperty())
                        || DataRangeFacts.upperBound(c.getFiller()) < n))
                        || (dataTop(c.getProperty())
                        && DataRangeFacts.atLeast(c.getFiller(), ((long) n) + 1));
            }
            return false;
        }

        /** Sufficient conditions for a class expression to denote the whole domain. */
        boolean top(OWLClassExpression ce) {
            if (ce instanceof OWLClass) {
                OWLClass c = (OWLClass) ce;
                return c.isOWLThing() || !c.isOWLNothing()
                        && locality == TOP && !signature.contains(c);
            }
            if (ce instanceof OWLObjectComplementOf) {
                return bottom(((OWLObjectComplementOf) ce).getOperand());
            }
            if (ce instanceof OWLObjectIntersectionOf) {
                return ((OWLObjectIntersectionOf) ce).operands().allMatch(this::top);
            }
            if (ce instanceof OWLObjectUnionOf) {
                return ((OWLObjectUnionOf) ce).operands().anyMatch(this::top);
            }
            if (ce instanceof OWLObjectOneOf) {
                // Even a nonempty nominal is not the entire domain in all models.
                return false;
            }
            if (ce instanceof OWLObjectSomeValuesFrom) {
                OWLObjectSomeValuesFrom c = (OWLObjectSomeValuesFrom) ce;
                return objectTop(c.getProperty()) && top(c.getFiller());
            }
            if (ce instanceof OWLObjectAllValuesFrom) {
                OWLObjectAllValuesFrom c = (OWLObjectAllValuesFrom) ce;
                return objectBottom(c.getProperty()) || top(c.getFiller());
            }
            if (ce instanceof OWLObjectHasSelf) {
                return objectTop(((OWLObjectHasSelf) ce).getProperty());
            }
            if (ce instanceof OWLObjectHasValue) {
                return objectTop(((OWLObjectHasValue) ce).getProperty());
            }
            if (ce instanceof OWLObjectMinCardinality) {
                OWLObjectMinCardinality c = (OWLObjectMinCardinality) ce;
                // A nonempty domain guarantees ONE universal-role successor,
                // but not two: singleton domains provide the counterexample.
                return c.getCardinality() == 0 || c.getCardinality() == 1
                        && objectTop(c.getProperty()) && top(c.getFiller());
            }
            if (ce instanceof OWLObjectMaxCardinality) {
                OWLObjectMaxCardinality c = (OWLObjectMaxCardinality) ce;
                return objectBottom(c.getProperty()) || bottom(c.getFiller());
            }
            if (ce instanceof OWLObjectExactCardinality) {
                OWLObjectExactCardinality c = (OWLObjectExactCardinality) ce;
                // Exactly zero equals at most zero; positive exact restrictions
                // cannot be tautologies for arbitrary domain/role cardinalities.
                return c.getCardinality() == 0
                        && (objectBottom(c.getProperty()) || bottom(c.getFiller()));
            }
            if (ce instanceof OWLDataSomeValuesFrom) {
                OWLDataSomeValuesFrom c = (OWLDataSomeValuesFrom) ce;
                return dataTop(c.getProperty()) && DataRangeFacts.atLeast(c.getFiller(), 1);
            }
            if (ce instanceof OWLDataAllValuesFrom) {
                OWLDataAllValuesFrom c = (OWLDataAllValuesFrom) ce;
                return dataBottom(c.getProperty()) || DataRangeFacts.top(c.getFiller());
            }
            if (ce instanceof OWLDataHasValue) {
                return dataTop(((OWLDataHasValue) ce).getProperty());
            }
            if (ce instanceof OWLDataMinCardinality) {
                OWLDataMinCardinality c = (OWLDataMinCardinality) ce;
                return c.getCardinality() == 0 || dataTop(c.getProperty())
                        && DataRangeFacts.atLeast(c.getFiller(), c.getCardinality());
            }
            if (ce instanceof OWLDataMaxCardinality) {
                OWLDataMaxCardinality c = (OWLDataMaxCardinality) ce;
                return dataBottom(c.getProperty())
                        || DataRangeFacts.upperBound(c.getFiller()) <= c.getCardinality();
            }
            if (ce instanceof OWLDataExactCardinality) {
                OWLDataExactCardinality c = (OWLDataExactCardinality) ce;
                long n = c.getCardinality();
                if (n == 0 && dataBottom(c.getProperty())) {
                    return true;
                }
                // Under a universal data role, every individual has exactly
                // |D| values; proving both bounds proves exact cardinality.
                return dataTop(c.getProperty())
                        && DataRangeFacts.atLeast(c.getFiller(), n)
                        && DataRangeFacts.upperBound(c.getFiller()) <= n;
            }
            return false;
        }
    }

    /**
     * Proven, conservative cardinality facts about OWL data ranges. These are
     * deliberately not a full datatype reasoner. In particular, unknown user
     * datatypes and facet restrictions receive no unwarranted lower bound.
     */
    private static final class DataRangeFacts {
        private static final String XSD_BOOLEAN = "http://www.w3.org/2001/XMLSchema#boolean";
        private static final long UNKNOWN_UPPER_BOUND = Long.MAX_VALUE;

        private DataRangeFacts() {}

        static boolean isBoolean(OWLDatatype datatype) {
            return XSD_BOOLEAN.equals(datatype.getIRI().toString());
        }

        static boolean top(OWLDataRange range) {
            if (range.isOWLDatatype()) {
                return range.asOWLDatatype().isTopDatatype();
            }
            if (range instanceof OWLDataComplementOf) {
                return empty(((OWLDataComplementOf) range).getDataRange());
            }
            if (range instanceof OWLDataIntersectionOf) {
                return ((OWLDataIntersectionOf) range).operands().allMatch(DataRangeFacts::top);
            }
            if (range instanceof OWLDataUnionOf) {
                return ((OWLDataUnionOf) range).operands().anyMatch(DataRangeFacts::top);
            }
            return false;
        }

        static boolean empty(OWLDataRange range) {
            if (range instanceof OWLDataComplementOf) {
                return top(((OWLDataComplementOf) range).getDataRange());
            }
            if (range instanceof OWLDataIntersectionOf) {
                return ((OWLDataIntersectionOf) range).operands().anyMatch(DataRangeFacts::empty);
            }
            if (range instanceof OWLDataUnionOf) {
                return ((OWLDataUnionOf) range).operands().allMatch(DataRangeFacts::empty);
            }
            if (range instanceof OWLDataOneOf) {
                return ((OWLDataOneOf) range).values().count() == 0;
            }
            return false;
        }

        static boolean proper(OWLDataRange range) {
            if (range.isOWLDatatype()) {
                OWLDatatype datatype = range.asOWLDatatype();
                // Every non-top OWL 2 built-in datatype excludes at least one
                // other data value. Do not make this claim for user datatypes.
                return datatype.isBuiltIn() && !datatype.isTopDatatype();
            }
            if (range instanceof OWLDataOneOf) {
                // A finite enumeration cannot cover the infinite OWL data domain.
                return true;
            }
            if (range instanceof OWLDatatypeRestriction) {
                return proper(((OWLDatatypeRestriction) range).getDatatype());
            }
            if (range instanceof OWLDataComplementOf) {
                return atLeast(((OWLDataComplementOf) range).getDataRange(), 1);
            }
            if (range instanceof OWLDataIntersectionOf) {
                return ((OWLDataIntersectionOf) range).operands().anyMatch(DataRangeFacts::proper);
            }
            // A union of proper subsets can still be the whole universe.
            return false;
        }

        static boolean atLeast(OWLDataRange range, long cardinality) {
            if (cardinality <= 0) {
                return true;
            }
            if (top(range)) {
                return true; // The OWL 2 data domain is infinite.
            }
            if (range.isOWLDatatype()) {
                OWLDatatype datatype = range.asOWLDatatype();
                if (!datatype.isBuiltIn()) {
                    return false;
                }
                if (isBoolean(datatype)) {
                    return cardinality <= 2;
                }
                return cardinality == 1 || !datatype.getBuiltInDatatype().isFinite();
            }
            if (range instanceof OWLDataOneOf) {
                // Lexically different literals can denote the SAME data value;
                // one value is guaranteed, more are not without a datatype check.
                return cardinality == 1 && ((OWLDataOneOf) range).values().findAny().isPresent();
            }
            if (range instanceof OWLDataUnionOf) {
                return ((OWLDataUnionOf) range).operands()
                        .anyMatch(operand -> atLeast(operand, cardinality));
            }
            if (range instanceof OWLDataComplementOf) {
                return cardinality == 1 && proper(((OWLDataComplementOf) range).getDataRange());
            }
            if (range instanceof OWLDataIntersectionOf) {
                List<OWLDataRange> operands = new ArrayList<>();
                ((OWLDataIntersectionOf) range).operands().forEach(operands::add);
                for (int i = 0; i < operands.size(); i++) {
                    OWLDataRange current = operands.get(i);
                    if (atLeast(current, cardinality)) {
                        boolean othersTop = true;
                        for (int j = 0; j < operands.size(); j++) {
                            if (j != i && !top(operands.get(j))) {
                                othersTop = false;
                                break;
                            }
                        }
                        if (othersTop) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        static long upperBound(OWLDataRange range) {
            if (empty(range)) {
                return 0;
            }
            if (range.isOWLDatatype()) {
                return isBoolean(range.asOWLDatatype()) ? 2 : UNKNOWN_UPPER_BOUND;
            }
            if (range instanceof OWLDataOneOf) {
                return ((OWLDataOneOf) range).values().count();
            }
            if (range instanceof OWLDatatypeRestriction) {
                // A restriction is a subset of its base datatype.
                return upperBound(((OWLDatatypeRestriction) range).getDatatype());
            }
            if (range instanceof OWLDataIntersectionOf) {
                return ((OWLDataIntersectionOf) range).operands()
                        .mapToLong(DataRangeFacts::upperBound).min()
                        .orElse(UNKNOWN_UPPER_BOUND);
            }
            if (range instanceof OWLDataUnionOf) {
                // The union has at most the sum of the component sizes.
                Iterator<OWLDataRange> it = ((OWLDataUnionOf) range).operands().iterator();
                long total = 0;
                while (it.hasNext()) {
                    long n = upperBound(it.next());
                    if (n == UNKNOWN_UPPER_BOUND || total > Long.MAX_VALUE - n) {
                        return UNKNOWN_UPPER_BOUND;
                    }
                    total += n;
                }
                return total;
            }
            return UNKNOWN_UPPER_BOUND;
        }
    }

    /**
     * Tests axioms using only sound positive conditions. Visitor methods not
     * implemented here retain the default {@code false} result; in particular,
     * no logical axiom is silently treated as local merely because it is unknown.
     */
    private static final class AxiomClassifier implements OWLAxiomVisitor {
        private final ExpressionClassifier e;
        private boolean local;

        AxiomClassifier(ExpressionClassifier e) {
            this.e = e;
            local = false;
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            local = e.bottom(axiom.getSubClass()) || e.top(axiom.getSuperClass());
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            List<OWLClassExpression> operands = new ArrayList<>();
            axiom.classExpressions().forEach(operands::add);
            // Every expression must be certified as bottom or every expression
            // certified as top. Recognising exact duplicates also preserves
            // trivial equivalence after OWLAPI normalisation.
            local = new HashSet<>(operands).size() <= 1
                    || operands.stream().allMatch(e::bottom)
                    || operands.stream().allMatch(e::top);
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            // Pairwise disjointness is trivial if at most one operand is nonempty.
            local = axiom.classExpressions().filter(c -> !e.bottom(c)).count() <= 1;
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            OWLClass lhs = axiom.getOWLClass();
            List<OWLClassExpression> rhs = new ArrayList<>();
            axiom.classExpressions().forEach(rhs::add);
            if (e.bottom(lhs)) {
                // Equivalent(lhs, Union(rhs)), with lhs = bottom, requires
                // all RHS classes to be bottom; then disjointness follows.
                local = rhs.stream().allMatch(e::bottom);
            } else if (e.top(lhs)) {
                // With lhs = top, exactly one RHS may be top and all others
                // must be bottom. In particular, finding ZERO top RHS is not
                // sufficient: their union would then be bottom.
                local = rhs.stream().filter(c -> e.top(c)).count() == 1
                        && rhs.stream().allMatch(c -> e.top(c) || e.bottom(c));
            }
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            local = e.top(axiom.getClassExpression());
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            local = e.objectBottom(axiom.getProperty()) || e.top(axiom.getDomain());
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            local = e.objectBottom(axiom.getProperty()) || e.top(axiom.getRange());
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {
            local = e.dataBottom(axiom.getProperty()) || e.top(axiom.getDomain());
        }

        @Override
        public void visit(OWLDataPropertyRangeAxiom axiom) {
            local = e.dataBottom(axiom.getProperty()) || DataRangeFacts.top(axiom.getRange());
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            local = e.objectBottom(axiom.getSubProperty())
                    || e.objectTop(axiom.getSuperProperty())
                    || axiom.getSubProperty().equals(axiom.getSuperProperty());
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            local = e.dataBottom(axiom.getSubProperty())
                    || e.dataTop(axiom.getSuperProperty())
                    || axiom.getSubProperty().equals(axiom.getSuperProperty());
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            // Empty chain relation when ANY link is empty. A universal RHS
            // contains every chain relation. Both make inclusion tautological.
            local = e.objectTop(axiom.getSuperProperty()) || axiom.getPropertyChain()
                    .stream().anyMatch(e::objectBottom);
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            List<OWLObjectPropertyExpression> properties = new ArrayList<>();
            axiom.properties().forEach(properties::add);
            local = new HashSet<>(properties).size() <= 1
                    || properties.stream().allMatch(e::objectBottom)
                    || properties.stream().allMatch(e::objectTop);
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            List<OWLDataPropertyExpression> properties = new ArrayList<>();
            axiom.properties().forEach(properties::add);
            local = new HashSet<>(properties).size() <= 1
                    || properties.stream().allMatch(e::dataBottom)
                    || properties.stream().allMatch(e::dataTop);
        }

        @Override
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            OWLObjectPropertyExpression left = axiom.getFirstProperty();
            OWLObjectPropertyExpression right = axiom.getSecondProperty();
            local = e.objectBottom(left) && e.objectBottom(right)
                    || e.objectTop(left) && e.objectTop(right)
                    || left.getInverseProperty().equals(right);
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            // IMPORTANT: use getNamedProperty() for inverse expressions.
            local = axiom.properties().filter(p -> !e.objectBottom(p)).count() <= 1;
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            local = axiom.properties().filter(p -> !e.dataBottom(p)).count() <= 1;
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            // Both the empty and universal object relations are symmetric.
            local = e.objectBottom(axiom.getProperty()) || e.objectTop(axiom.getProperty());
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            // Both the empty and universal object relations are transitive.
            local = e.objectBottom(axiom.getProperty()) || e.objectTop(axiom.getProperty());
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            local = e.objectBottom(axiom.getProperty());
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            local = e.objectBottom(axiom.getProperty());
        }

        @Override
        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            local = e.objectTop(axiom.getProperty());
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            local = e.objectBottom(axiom.getProperty());
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            local = e.objectBottom(axiom.getProperty());
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            local = e.dataBottom(axiom.getProperty());
        }

        @Override
        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            local = e.objectTop(axiom.getProperty());
        }

        @Override
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            local = e.objectBottom(axiom.getProperty());
        }

        @Override
        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            local = e.dataTop(axiom.getProperty());
        }

        @Override
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            local = e.dataBottom(axiom.getProperty());
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            // Keys impose nontrivial equality constraints on named individuals.
            // If the target class is provably empty, no pair of individuals can
            // satisfy the key premise, so the axiom is vacuously true.
            local = e.bottom(axiom.getClassExpression());
        }

        @Override
        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            // Datatypes are not replaced by locality transformations.
            // Conservatively keep definitions: they can constrain their values.
            local = false;
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            // The equality of two unrelated named individuals is not valid.
            // No separate postprocessing convention is assumed here.
            local = false;
        }

        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {
            local = false;
        }

        @Override
        public void visit(SWRLRule rule) {
            // SWRL is not covered by the OWL 2 DL locality calculus.
            local = false;
        }
    }
}