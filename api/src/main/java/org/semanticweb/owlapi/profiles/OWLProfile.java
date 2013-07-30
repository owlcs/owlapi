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

package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Apr-2008<br><br>
 *
 */
public interface OWLProfile {

    /** http://www.w3.org/ns/owl-profile/DL **/
    public static final IRI OWL2_DL = IRI.create("http://www.w3.org/ns/owl-profile/DL");
    
    /** http://www.w3.org/ns/owl-profile/EL **/
    public static final IRI OWL2_EL = IRI.create("http://www.w3.org/ns/owl-profile/EL");
    
    /** http://www.w3.org/ns/owl-profile/QL **/
    public static final IRI OWL2_QL = IRI.create("http://www.w3.org/ns/owl-profile/QL");
    
    /** http://www.w3.org/ns/owl-profile/RL **/
    public static final IRI OWL2_RL = IRI.create("http://www.w3.org/ns/owl-profile/RL");
    
    /** http://www.w3.org/ns/owl-profile/Full **/
    public static final IRI OWL2_FULL = IRI.create("http://www.w3.org/ns/owl-profile/Full");
    
    /**
     * Gets the name of the profile.
     * @return A string that represents the name of the profile
     */
    String getName();

    /**
     * The IRI that uniquely identifies this profile. 
     * 
     * If this profile is listed at http://www.w3.org/ns/owl-profile/ 
     * this IRI MUST match the IRI given in the list.
     * 
     * @return The IRI that identifies this profile.
     */
    IRI getIRI();
    

    /**
     * Checks an ontology and its import closure to see if it is within
     * this profile.
     * @param ontology The ontology to be checked.
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     * ontology is within this profile.
     */
    OWLProfileReport checkOntology(OWLOntology ontology);
    
    
}
