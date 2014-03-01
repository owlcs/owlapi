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
 * Copyright 2011, The University of Manchester
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
package org.semanticweb.owlapi.change;

import java.io.Serializable;
import java.util.Set;

import org.semanticweb.owlapi.model.HasSignature;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;

/**
 * Represents an abstraction of the essential non-ontology data required by a
 * particular kind of {@link OWLOntologyChange}. There is a concrete subclass of
 * this class for each concrete class of {@link OWLOntologyChange}. <br>
 * Instances of this class are immutable.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 27/04/2012
 * @see org.semanticweb.owlapi.model.OWLOntologyChange#getChangeData()
 * @since 3.5
 */
public abstract class OWLOntologyChangeData implements HasSignature,
        Serializable {

    private static final long serialVersionUID = 30406L;

    /** Default constructor for serialization purposes. */
    protected OWLOntologyChangeData() {}

    /**
     * Accepts a visit from an {@link OWLOntologyChangeDataVisitor}.
     * 
     * @param visitor
     *        The visitor
     * @param <R>
     *        The return type for visitor's visit methods.
     * @param <E>
     *        The exception type for exceptions thrown by the visitor's visit
     *        methods.
     * @return The object returned by the visitor's visit methods.
     * @throws E
     *         The exception thrown by the visitor's visit methods.
     */
    public abstract <R, E extends Exception> R accept(
            OWLOntologyChangeDataVisitor<R, E> visitor) throws E;

    /**
     * Creates an {@link OWLOntologyChange} object that pertains to the
     * specified {@code ontology}, which when applied to the specified ontology
     * enacts the change described by this info object.
     * 
     * @param ontology
     *        The {@link OWLOntology} that the change should apply to. Not
     *        {@code null}.
     * @return An {@link OWLOntologyChange} object that applies to
     *         {@code ontology} and changes {@code ontology} is a way that is
     *         consistent with this the information held in this
     *         {@link OWLOntologyChangeData} object.
     * @throws NullPointerException
     *         if {@code ontology} is {@code null}.
     */
    public abstract OWLOntologyChange
            createOntologyChange(OWLOntology ontology);

    /**
     * Gets the (possibly empty) signature of this change data.
     * 
     * @return A set of {@link OWLEntity} objects that represent the signature
     *         of this change data. Not {@code null}.
     * @since 3.5
     */
    @Override
    public abstract Set<OWLEntity> getSignature();
}
