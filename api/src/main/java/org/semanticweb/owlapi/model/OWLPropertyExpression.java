/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group Date: 24-Oct-2006 Represents a property or the
 * inverse of a property. */
public interface OWLPropertyExpression extends OWLObject {
    /** Determines if this property expression is anonymous.
     * 
     * @return <code>true</code> if the property expression is anonymous
     *         (because it is the inverse of a property). <code>false</code> if
     *         this property is a named object property or named data property. */
    boolean isAnonymous();

    @SuppressWarnings("javadoc")
    void accept(@Nonnull OWLPropertyExpressionVisitor visitor);

    @SuppressWarnings("javadoc")
    <O> O accept(@Nonnull OWLPropertyExpressionVisitorEx<O> visitor);

    /** @return true if this is a data property */
    boolean isDataPropertyExpression();

    /** @return true if this is an object property */
    boolean isObjectPropertyExpression();

    /** Determines if this is the owl:topObjectProperty
     * 
     * @return <code>true</code> if this property is the owl:topObjectProperty
     *         otherwise <code>false</code> */
    boolean isOWLTopObjectProperty();

    /** Determines if this is the owl:bottomObjectProperty
     * 
     * @return <code>true</code> if this property is the
     *         owl:bottomObjectProperty otherwise <code>false</code> */
    boolean isOWLBottomObjectProperty();

    /** Determines if this is the owl:topDataProperty
     * 
     * @return <code>true</code> if this property is the owl:topDataProperty
     *         otherwise <code>false</code> */
    boolean isOWLTopDataProperty();

    /** Determines if this is the owl:bottomDataProperty
     * 
     * @return <code>true</code> if this property is the owl:bottomDataProperty
     *         otherwise <code>false</code> */
    boolean isOWLBottomDataProperty();
}
