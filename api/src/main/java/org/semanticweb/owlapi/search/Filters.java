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

import java.util.Collection;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/**
 * Collection of filters for use in searching through ontology axioms.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public class Filters {

    private Filters() {}

    /**
     * @author ignazio
     * @param <A>
     *        axiom type
     */
    public abstract static class AxiomFilter<A extends OWLAxiom> implements OWLAxiomSearchFilter {

        private final @Nonnull Collection<AxiomType<?>> types;

        /**
         * @param type
         *        axiom type to filter on
         */
        public AxiomFilter(AxiomType<?> type) {
            types = CollectionFactory.<AxiomType<?>> list(type);
        }

        /**
         * @param types
         *        axiom types to filter on
         */
        public AxiomFilter(Collection<AxiomType<?>> types) {
            this.types = types;
        }

        /**
         * @param types
         *        axiom types to filter on
         */
        public AxiomFilter(AxiomType<?>... types) {
            this.types = CollectionFactory.list(types);
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
         * Override this method to select what part of the axiom should be
         * compared with the input key.
         * 
         * @param axiom
         *        axiom to check
         * @return Object to compare to the input key
         */
        protected abstract Object axiomValue(A axiom);
    }

    /**
     * filter returning subannotation axioms where the super property matches
     * the input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter subAnnotationWithSuper = new AxiomFilter<OWLSubAnnotationPropertyOfAxiom>(
            AxiomType.SUB_ANNOTATION_PROPERTY_OF) {

        @Override
        protected Object axiomValue(OWLSubAnnotationPropertyOfAxiom axiom) {
            return axiom.getSuperProperty();
        }
    };
    /**
     * filter returning subannotation axioms where the sub property matches the
     * input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter subAnnotationWithSub = new AxiomFilter<OWLSubAnnotationPropertyOfAxiom>(
            AxiomType.SUB_ANNOTATION_PROPERTY_OF) {

        @Override
        protected Object axiomValue(OWLSubAnnotationPropertyOfAxiom axiom) {
            return axiom.getSubProperty();
        }
    };
    /**
     * filter returning subclass axioms where the super class matches the input
     * key.
     */
    public static final @Nonnull OWLAxiomSearchFilter subClassWithSuper = new AxiomFilter<OWLSubClassOfAxiom>(
            AxiomType.SUBCLASS_OF) {

        @Override
        protected Object axiomValue(OWLSubClassOfAxiom axiom) {
            return axiom.getSuperClass();
        }
    };
    /**
     * filter returning subclass axioms where the sub class matches the input
     * key.
     */
    public static final @Nonnull OWLAxiomSearchFilter subClassWithSub = new AxiomFilter<OWLSubClassOfAxiom>(
            AxiomType.SUBCLASS_OF) {

        @Override
        protected Object axiomValue(OWLSubClassOfAxiom axiom) {
            return axiom.getSubClass();
        }
    };
    /**
     * filter returning sub object property axioms where the super property
     * matches the input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter subObjectPropertyWithSuper = new AxiomFilter<OWLSubObjectPropertyOfAxiom>(
            AxiomType.SUB_OBJECT_PROPERTY) {

        @Override
        protected Object axiomValue(OWLSubObjectPropertyOfAxiom axiom) {
            return axiom.getSuperProperty();
        }
    };
    /**
     * filter returning sub object property axioms where the sub property
     * matches the input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter subObjectPropertyWithSub = new AxiomFilter<OWLSubObjectPropertyOfAxiom>(
            AxiomType.SUB_OBJECT_PROPERTY) {

        @Override
        protected Object axiomValue(OWLSubObjectPropertyOfAxiom axiom) {
            return axiom.getSubProperty();
        }
    };
    /**
     * filter returning sub data property axioms where the super property
     * matches the input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter subDataPropertyWithSuper = new AxiomFilter<OWLSubDataPropertyOfAxiom>(
            AxiomType.SUB_DATA_PROPERTY) {

        @Override
        protected Object axiomValue(OWLSubDataPropertyOfAxiom axiom) {
            return axiom.getSuperProperty();
        }
    };
    /**
     * filter returning sub data property axioms where the sub property matches
     * the input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter subDataPropertyWithSub = new AxiomFilter<OWLSubDataPropertyOfAxiom>(
            AxiomType.SUB_DATA_PROPERTY) {

        @Override
        protected Object axiomValue(OWLSubDataPropertyOfAxiom axiom) {
            return axiom.getSubProperty();
        }
    };
    /**
     * filter returning datatype definition axioms where the datatype matches
     * the input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter datatypeDefFilter = new AxiomFilter<OWLDatatypeDefinitionAxiom>(
            AxiomType.DATATYPE_DEFINITION) {

        @Override
        protected Object axiomValue(OWLDatatypeDefinitionAxiom axiom) {
            return axiom.getDatatype();
        }
    };
    /**
     * filter returning annotation property range axioms where the property
     * matches the input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter apRangeFilter = new AxiomFilter<OWLAnnotationPropertyRangeAxiom>(
            AxiomType.ANNOTATION_PROPERTY_RANGE) {

        @Override
        protected Object axiomValue(OWLAnnotationPropertyRangeAxiom axiom) {
            return axiom.getProperty();
        }
    };
    /**
     * filter returning annotation property domain axioms where the property
     * matches the input key.
     */
    public static final @Nonnull OWLAxiomSearchFilter apDomainFilter = new AxiomFilter<OWLAnnotationPropertyDomainAxiom>(
            AxiomType.ANNOTATION_PROPERTY_DOMAIN) {

        @Override
        protected Object axiomValue(OWLAnnotationPropertyDomainAxiom axiom) {
            return axiom.getProperty();
        }
    };
    /**
     * filter returning annotation assertions where the subject matches the
     * input key.
     * 
     * @deprecated use the OWLOntology:getAnnotationAssertionAxioms() in place
     *             of this filter as it is much faster, thanks to indexing.
     */
    @Deprecated
    public static final @Nonnull OWLAxiomSearchFilter annotations = new AxiomFilter<OWLAnnotationAssertionAxiom>(
            AxiomType.ANNOTATION_ASSERTION) {

        @Override
        protected Object axiomValue(OWLAnnotationAssertionAxiom axiom) {
            return axiom.getSubject();
        }
    };
    /**
     * filter returning all axioms included in TBox or RBox. No assertions,
     * nonlogical axioms or SWRL rules.
     */
    public static final @Nonnull OWLAxiomSearchFilter axiomsFromTBoxAndRBox = new AxiomFilter<OWLAxiom>(
            AxiomType.TBoxAndRBoxAxiomTypes) {

        @Override
        protected Object axiomValue(OWLAxiom axiom) {
            return axiom;
        }

        @Override
        public boolean pass(OWLAxiom axiom, Object key) {
            // for this filter, accept all axioms
            return true;
        }
    };
    /**
     * filter returning all axioms not in TBox or RBox; therefore, ABox axioms,
     * nonlogical axioms and SWRL rules.
     */
    public static final OWLAxiomSearchFilter axiomsNotInTBoxOrRBox = new AxiomFilter<OWLAxiom>(AxiomType.AXIOM_TYPES) {

        @Override
        protected Object axiomValue(OWLAxiom axiom) {
            return axiom;
        }

        @Override
        public boolean pass(@Nonnull OWLAxiom axiom, Object key) {
            // for this filter, only accept the axioms whose types are not in
            // tbox or rbox
            return !AxiomType.TBoxAndRBoxAxiomTypes.contains(axiom.getAxiomType());
        }
    };
}
