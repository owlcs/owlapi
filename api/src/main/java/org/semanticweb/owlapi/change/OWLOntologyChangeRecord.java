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

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;

/**
 * Associates an {@link OWLOntologyID} with ontology-less change data.<br>
 * An {@link OWLOntologyChangeRecord} captures information about an
 * {@link OWLOntologyChange} in a way that does not require a reference to an
 * {@link OWLOntology} object. It does this by referencing an
 * {@link OWLOntologyID} instead of referencing an {@link OWLOntology}. The
 * primary reason for doing this is so that changes can be serialized and logged
 * more easily. It should be kept in mind that {@link OWLOntologyChangeRecord}
 * objects can represent changes for which there might be no in memory
 * representation of a specific {@link OWLOntology}. This is also true if an
 * {@link OWLOntology} object has its {@link OWLOntologyID} changed.<br>
 * An {@link OWLOntologyChange} object contains two important pieces of
 * information:
 * <ol>
 * <li>The {@link OWLOntologyID} that identifies the ontology that the change
 * pertains to.</li>
 * <li>The {@link OWLOntologyChangeData} that describes the change specific
 * data. For each kind of {@link OWLOntologyChange} there is a corresponding
 * {@link OWLOntologyChangeData} class which captures the essential details that
 * pertain to the change. The reason for this separation is that it allows
 * change information to be captured where the context of the change (the
 * ontology) is known via some other mechanism.</li>
 * </ol>
 * {@code OWLOntologyChangeRecord} objects are immutable.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 03/05/2012
 * @since 3.4.3
 */
public final class OWLOntologyChangeRecord implements Serializable {

    private static final long serialVersionUID = 30406L;
    private final OWLOntologyID ontologyID;
    private final OWLOntologyChangeData data;

    /** Default constructor for serialization purposes only. */
    private OWLOntologyChangeRecord() {
        ontologyID = null;
        data = null;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// Constructors and Factory methods
    // ////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructs an {@link OWLOntologyChangeRecord} object which holds
     * information about a change to a particular ontology identified by an
     * {@link OWLOntologyID} object and also change details specified by the
     * {@link OWLOntologyChangeData} object.
     * 
     * @param ontologyID
     *        The {@link OWLOntologyID} which identifies the ontology that the
     *        change was applied to. Not {@code null}.
     * @param data
     *        The {@link OWLOntologyChangeData} that describes the particular
     *        details of the change. Not {@code null}.
     * @throws NullPointerException
     *         if {@code ontologyID} is {@code null} or if {@code recordInfo} is
     *         {@code null}.
     */
    public OWLOntologyChangeRecord(OWLOntologyID ontologyID,
            OWLOntologyChangeData data) {
        if (ontologyID == null) {
            throw new NullPointerException("ontologyID must not be null");
        }
        if (data == null) {
            throw new NullPointerException("data must not be null");
        }
        this.ontologyID = ontologyID;
        this.data = data;
    }

    /**
     * A convenience method that creates an {@link OWLOntologyChangeRecord} by
     * deriving data from an {@link OWLOntologyChange} object.
     * 
     * @param change
     *        The {@link OWLOntologyChange} object. Not {@code null}.
     * @return instance of OntologychangeRecord
     * @throws NullPointerException
     *         if {@code change} is {@code null}.
     */
    public static OWLOntologyChangeRecord createFromOWLOntologyChange(
            OWLOntologyChange change) {
        if (change == null) {
            throw new NullPointerException("change must not be null");
        }
        OWLOntologyID ontologyId = change.getOntology().getOntologyID();
        OWLOntologyChangeData data = change.getChangeData();
        return new OWLOntologyChangeRecord(ontologyId, data);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////
    // //// Interface methods.
    // ////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the {@link OWLOntologyID} that identifies the ontology associated
     * with this change record.
     * 
     * @return The {@link OWLOntologyID}. Not {@code null}.
     */
    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    /**
     * Gets the {@link OWLOntologyChangeData} which is associated with this
     * {@link OWLOntologyChangeRecord}.
     * 
     * @return The {@link OWLOntologyChangeData}. Not {@code null}.
     */
    public OWLOntologyChangeData getData() {
        return data;
    }

    /**
     * Creates an {@link OWLOntologyChange} from the {@link OWLOntologyID} and
     * {@link OWLOntologyChangeData} associated with this
     * {@link OWLOntologyChangeRecord} object. The {@link OWLOntology} that is
     * the target of the resulting {@link OWLOntologyChange} is derived from an
     * {@link OWLOntologyManager}. The manager <i>must</i> contain an ontology
     * that has an {@link OWLOntologyID} which is equal to the
     * {@link OWLOntologyID} associated with this
     * {@link OWLOntologyChangeRecord} object.
     * 
     * @param manager
     *        The manager which will be used to obtain a reference to an
     *        {@link OWLOntology} object having the same {@link OWLOntologyID}
     *        as the {@link OWLOntologyID} associated with this
     *        {@link OWLOntologyChangeRecord}. Not {@code null}.
     * @return The {@link OWLOntologyChange} object that is derived from this
     *         record's {@link OWLOntologyID} and {@link OWLOntologyChangeData}.
     *         The specific concrete subclass of the returned
     *         {@link OWLOntologyChange} will depend upon the specific concrete
     *         subclass of the {@link OWLOntologyChangeData} associated with
     *         this {@link OWLOntologyChangeRecord}.
     * @throws UnknownOWLOntologyException
     *         if the specified manager does not contain an ontology which has
     *         an {@link OWLOntologyID} equal to the {@link OWLOntologyID}
     *         associated with this {@link OWLOntologyChangeRecord}.
     * @throws NullPointerException
     *         if {@code manager} is {@code null}.
     */
    public OWLOntologyChange createOntologyChange(OWLOntologyManager manager)
            throws UnknownOWLOntologyException {
        if (manager == null) {
            throw new NullPointerException("manager must not be null");
        }
        OWLOntology ontology = manager.getOntology(ontologyID);
        if (ontology == null) {
            throw new UnknownOWLOntologyException(ontologyID);
        }
        return data.createOntologyChange(ontology);
    }

    @Override
    /**
     * {@code obj} is equal to this object if it is an {@link OWLOntologyChangeRecord} and its associated
     * {@link OWLOntologyID} and {@link OWLOntologyChangeData} are equal to this objects associated
     * {@link OWLOntologyID} and {@link OWLOntologyChangeData}.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLOntologyChangeRecord)) {
            return false;
        }
        OWLOntologyChangeRecord other = (OWLOntologyChangeRecord) obj;
        return ontologyID.equals(other.ontologyID) && data.equals(other.data);
    }

    @Override
    public int hashCode() {
        return "OWLOntologyChangeRecord".hashCode() + ontologyID.hashCode()
                + data.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OWLOntologyChangeRecord(");
        sb.append(ontologyID);
        sb.append(" ");
        sb.append(data);
        sb.append(")");
        return sb.toString();
    }
}
