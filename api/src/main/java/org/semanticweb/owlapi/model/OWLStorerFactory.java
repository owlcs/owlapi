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

import javax.annotation.Nonnull;
import javax.inject.Provider;

/**
 * Factory for ontology storers. Provide a priority to allow for sorting of
 * ontology storers. Default implementations will have priorities starting at 0
 * and moving up by increments of one. This allows the introduction of more
 * storers at any position in the list: e.g., to insert a new storer in the
 * second position, it is sufficient for it to pick a priority value strictly
 * between 0 and 1. Storers can be provided by adding a Guice module to the
 * injector used for binding, or set directly on the manager after, or in place
 * of, injection.
 */
public interface OWLStorerFactory extends Serializable, Provider<OWLStorer> {

    /**
     * Create new storer.
     * 
     * @return new storer
     */
    @Nonnull
    OWLStorer createStorer();

    /**
     * @return format factory for the format parsed by this storer
     */
    @Nonnull
    OWLDocumentFormatFactory getFormatFactory();
}
