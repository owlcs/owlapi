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
package org.semanticweb.owlapi.util;

import java.io.Serializable;

import org.semanticweb.owlapi.model.OWLEntity;

/**
 * A very simple short form provider which is intended to provide human readable
 * display names for entities. The following strategy is used: 1) If the entity
 * URI has a fragment then that fragment is returned e.g. http://an.other.com#A
 * would have a short form of "A". 2) If the entity URI does not have a fragment
 * then the last segment of the URI path is used e.g. http://an.other.com/A/B
 * would have a short form of "B". 3) If the entity URI does not have a path
 * then the full URI is returned as a string.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class SimpleShortFormProvider implements ShortFormProvider, Serializable {

    private static final long serialVersionUID = 40000L;
    private final SimpleIRIShortFormProvider uriShortFormProvider = new SimpleIRIShortFormProvider();

    @Override
    public String getShortForm(OWLEntity entity) {
        return uriShortFormProvider.getShortForm(entity.getIRI());
    }

    @Override
    public void dispose() {
        // Nothing to do here
    }
}
