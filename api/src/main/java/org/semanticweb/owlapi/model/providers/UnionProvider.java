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
package org.semanticweb.owlapi.model.providers;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkIterableNotNull;

import java.util.Collection;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * Object and datatype union provider.
 */
public interface UnionProvider {

    /**
     * @param dataRanges data ranges for union. Cannot be null or contain nulls.
     * @return an OWLDataUnionOf on the specified dataranges
     */
    default OWLDataUnionOf getOWLDataUnionOf(Collection<? extends OWLDataRange> dataRanges) {
        checkIterableNotNull(dataRanges, "dataRanges cannot be null", true);
        return getOWLDataUnionOf(dataRanges.stream());
    }

    /**
     * @param dataRanges data ranges for union. Cannot be null or contain nulls.
     * @return an OWLDataUnionOf on the specified dataranges
     */
    OWLDataUnionOf getOWLDataUnionOf(Stream<? extends OWLDataRange> dataRanges);

    /**
     * @param dataRanges data ranges for union. Cannot be null or contain nulls.
     * @return an OWLDataUnionOf on the specified dataranges
     */
    default OWLDataUnionOf getOWLDataUnionOf(OWLDataRange... dataRanges) {
        checkIterableNotNull(dataRanges, "dataRanges cannot be null", true);
        return getOWLDataUnionOf(CollectionFactory.createSet(dataRanges));
    }

    /**
     * @param operands class expressions for union
     * @return a class union over the specified arguments
     */
    OWLObjectUnionOf getOWLObjectUnionOf(Stream<? extends OWLClassExpression> operands);

    /**
     * @param operands class expressions for union
     * @return a class union over the specified arguments
     */
    default OWLObjectUnionOf getOWLObjectUnionOf(
        Collection<? extends OWLClassExpression> operands) {
        return getOWLObjectUnionOf(operands.stream());
    }

    /**
     * @param operands class expressions for union
     * @return a class union over the specified arguments
     */
    default OWLObjectUnionOf getOWLObjectUnionOf(OWLClassExpression... operands) {
        checkIterableNotNull(operands, "operands cannot be null", true);
        return getOWLObjectUnionOf(CollectionFactory.createSet(operands));
    }
}
