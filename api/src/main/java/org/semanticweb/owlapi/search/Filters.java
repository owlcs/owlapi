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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/**
 * Collection of filters for use in searching through ontology axioms.
 *
 * @author ignazio
 * @since 4.0.0
 */
public class Filters {

    /**
     * filter returning subannotation axioms where the super property matches the input key.
     */
    public static final OWLAxiomSearchFilter subAnnotationWithSuper = new AxiomFilter<>(
        AxiomType.SUB_ANNOTATION_PROPERTY_OF, OWLSubAnnotationPropertyOfAxiom::getSuperProperty);
    /**
     * filter returning subannotation axioms where the sub property matches the input key.
     */
    public static final OWLAxiomSearchFilter subAnnotationWithSub = new AxiomFilter<>(
        AxiomType.SUB_ANNOTATION_PROPERTY_OF, OWLSubAnnotationPropertyOfAxiom::getSubProperty);
    /**
     * filter returning subclass axioms where the super class matches the input key.
     */
    public static final OWLAxiomSearchFilter subClassWithSuper =
        new AxiomFilter<>(AxiomType.SUBCLASS_OF, OWLSubClassOfAxiom::getSuperClass);
    /**
     * filter returning subclass axioms where the sub class matches the input key.
     */
    public static final OWLAxiomSearchFilter subClassWithSub =
        new AxiomFilter<>(AxiomType.SUBCLASS_OF, OWLSubClassOfAxiom::getSubClass);
    /**
     * filter returning sub object property axioms where the super property matches the input key.
     */
    public static final OWLAxiomSearchFilter subObjectPropertyWithSuper = new AxiomFilter<>(
        AxiomType.SUB_OBJECT_PROPERTY, OWLSubObjectPropertyOfAxiom::getSuperProperty);
    /**
     * filter returning sub object property axioms where the sub property matches the input key.
     */
    public static final OWLAxiomSearchFilter subObjectPropertyWithSub = new AxiomFilter<>(
        AxiomType.SUB_OBJECT_PROPERTY, OWLSubObjectPropertyOfAxiom::getSubProperty);
    /**
     * filter returning sub data property axioms where the super property matches the input key.
     */
    public static final OWLAxiomSearchFilter subDataPropertyWithSuper =
        new AxiomFilter<>(AxiomType.SUB_DATA_PROPERTY, OWLSubDataPropertyOfAxiom::getSuperProperty);
    /**
     * filter returning sub data property axioms where the sub property matches the input key.
     */
    public static final OWLAxiomSearchFilter subDataPropertyWithSub =
        new AxiomFilter<>(AxiomType.SUB_DATA_PROPERTY, OWLSubDataPropertyOfAxiom::getSubProperty);
    /**
     * filter returning datatype definition axioms where the datatype matches the input key.
     */
    public static final OWLAxiomSearchFilter datatypeDefFilter =
        new AxiomFilter<>(AxiomType.DATATYPE_DEFINITION, OWLDatatypeDefinitionAxiom::getDatatype);
    /**
     * filter returning annotation property range axioms where the property matches the input key.
     */
    public static final OWLAxiomSearchFilter apRangeFilter = new AxiomFilter<>(
        AxiomType.ANNOTATION_PROPERTY_RANGE, OWLAnnotationPropertyRangeAxiom::getProperty);
    /**
     * filter returning annotation property domain axioms where the property matches the input key.
     */
    public static final OWLAxiomSearchFilter apDomainFilter = new AxiomFilter<>(
        AxiomType.ANNOTATION_PROPERTY_DOMAIN, OWLAnnotationPropertyDomainAxiom::getProperty);
    /**
     * filter returning annotation assertions where the subject matches the input key.
     *
     * @deprecated use the OWLOntology:getAnnotationAssertionAxioms() in place of this filter as it
     *             is much faster, thanks to indexing.
     */
    @Deprecated
    public static final OWLAxiomSearchFilter annotations =
        new AxiomFilter<>(AxiomType.ANNOTATION_ASSERTION, OWLAnnotationAssertionAxiom::getSubject);
    /**
     * filter returning all axioms included in TBox or RBox. No assertions, nonlogical axioms or
     * SWRL rules.
     */
    public static final OWLAxiomSearchFilter axiomsFromTBoxAndRBox =
        new AxiomFilter<OWLAxiom>(AxiomType.TBoxAndRBoxAxiomTypes, a -> a) {

            @Override
            public boolean pass(OWLAxiom axiom, Object key) {
                // for this filter, accept all axioms
                return true;
            }
        };
    /**
     * filter returning all axioms not in TBox or RBox; therefore, ABox axioms, nonlogical axioms
     * and SWRL rules.
     */
    public static final OWLAxiomSearchFilter axiomsNotInTBoxOrRBox =
        new AxiomFilter<OWLAxiom>(AxiomType.AXIOM_TYPES, a -> a) {

            @Override
            public boolean pass(@Nonnull OWLAxiom axiom, Object key) {
                // for this filter, only accept the axioms whose types are not in
                // tbox or rbox
                return !AxiomType.TBoxAndRBoxAxiomTypes.contains(axiom.getAxiomType());
            }
        };

    private Filters() {}

    @FunctionalInterface
    private interface Filter<A extends OWLAxiom> {

        Object filter(A axiom);
    }

    /**
     * @param <A> axiom type
     * @author ignazio
     */
    public static class AxiomFilter<A extends OWLAxiom> implements OWLAxiomSearchFilter {

        private final Collection<AxiomType<?>> types;
        private final Filter<A> filter;

        /**
         * @param type axiom type to filter on
         * @param f filter lambda
         */
        public AxiomFilter(AxiomType<?> type, Filter<A> f) {
            types = Collections.singletonList(type);
            filter = f;
        }

        /**
         * @param types axiom types to filter on
         * @param f filter lambda
         */
        public AxiomFilter(Collection<AxiomType<?>> types, Filter<A> f) {
            this.types = types;
            filter = f;
        }

        /**
         * @param f filter lambda
         * @param types axiom types to filter on
         */
        public AxiomFilter(Filter<A> f, AxiomType<?>... types) {
            this.types = Arrays.asList(types);
            filter = f;
        }

        @Override
        public Iterable<AxiomType<?>> getAxiomTypes() {
            return types;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean pass(OWLAxiom axiom, Object key) {
            return axiomValue((A) axiom).equals(key);
        }

        /**
         * Override this method to select what part of the axiom should be compared with the input
         * key.
         *
         * @param axiom axiom to check
         * @return Object to compare to the input key
         */
        protected Object axiomValue(A axiom) {
            return filter.filter(axiom);
        }
    }
}
