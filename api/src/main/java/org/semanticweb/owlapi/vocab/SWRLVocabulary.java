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
package org.semanticweb.owlapi.vocab;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public enum SWRLVocabulary implements HasShortForm, HasIRI, HasPrefixedName {
//@formatter:off
    /** IMP */                          IMP                     ("Imp"), 
    /** INDIVIDUAL_PROPERTY_ATOM */     INDIVIDUAL_PROPERTY_ATOM("IndividualPropertyAtom"), 
    /** DATAVALUED_PROPERTY_ATOM */     DATAVALUED_PROPERTY_ATOM("DatavaluedPropertyAtom"), 
    /** CLASS_ATOM */                   CLASS_ATOM              ("ClassAtom"), 
    /** DATA_RANGE_ATOM */              DATA_RANGE_ATOM         ("DataRangeAtom"), 
    /** VARIABLE */                     VARIABLE                ("Variable"), 
    /** ATOM_LIST */                    ATOM_LIST               ("AtomList"), 
    /** SAME_INDIVIDUAL_ATOM */         SAME_INDIVIDUAL_ATOM    ("SameIndividualAtom"), 
    /** DIFFERENT_INDIVIDUALS_ATOM */   DIFFERENT_INDIVIDUALS_ATOM("DifferentIndividualsAtom"), 
    /** BUILT_IN_ATOM */                BUILT_IN_ATOM           ("BuiltinAtom"), 
    /** HEAD */                         HEAD                    ("head"), 
    /** BODY */                         BODY                    ("body"), 
    /** CLASS_PREDICATE */              CLASS_PREDICATE         ("classPredicate"), 
    /** DATA_RANGE */                   DATA_RANGE              ("dataRange"), 
    /** PROPERTY_PREDICATE */           PROPERTY_PREDICATE      ("propertyPredicate"), 
    /** BUILT_IN */                     BUILT_IN                ("builtin"), 
    /** BUILT_IN_CLASS */               BUILT_IN_CLASS          ("Builtin"), 
    /** ARGUMENTS */                    ARGUMENTS               ("arguments"), 
    /** ARGUMENT_1 */                   ARGUMENT_1              ("argument1"), 
    /** ARGUMENT_2 */                   ARGUMENT_2              ("argument2");
//@formatter:on
    @Nonnull
    private final String shortName;
    @Nonnull
    private final IRI iri;
    @Nonnull
    private final String prefixedName;

    SWRLVocabulary(@Nonnull String name) {
        shortName = name;
        prefixedName = Namespaces.SWRL.getPrefixName() + ':' + name;
        iri = IRI.create(Namespaces.SWRL.toString(), name);
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public String getShortForm() {
        return shortName;
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }
}
