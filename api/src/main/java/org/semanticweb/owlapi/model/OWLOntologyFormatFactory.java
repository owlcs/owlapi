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
package org.semanticweb.owlapi.model;

import java.io.Serializable;

import javax.inject.Provider;

/**
 * Factory for ontology formats.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public interface OWLOntologyFormatFactory extends Serializable,
        Provider<OWLOntologyFormat>, MIMETypeAware {

    /**
     * Create a new format. Note that ontology formats currently carry prefix
     * information for the ontology, so an instance cannot, in general, be
     * reused for multiple parsings.
     * 
     * @return new storer
     */
    OWLOntologyFormat createFormat();

    /**
     * Returns the key for the OWLOntologyFormat that this class is a factory
     * for without necessarily creating an instance of the OWLOntologyFormat.
     * 
     * @return The key for the OWLOntologyFormat.
     */
    String getKey();

    /**
     * Returns true if this format can be represented using textual characters.
     * Returns false if this format must be dealt with using binary methods.
     * 
     * @return True if this format is textual, and false if it is a binary
     *         format.
     */
    boolean isTextual();
}
