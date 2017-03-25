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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.Collection;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.HasComponents;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * A utility class that visits all components of classes and axioms; this base class allows
 * subclasses to choose elements of interest and override handling of such elements.
 *
 * @param <E> type returned
 * @since 5.0.0
 */
public abstract class AbstractCollectorEx<E> implements OWLObjectVisitorEx<Collection<E>> {

    protected Collection<E> objects;

    /**
     * @param c collection to accumulate objects
     */
    public AbstractCollectorEx(Collection<E> c) {
        objects = checkNotNull(c, "c cannot be null");
    }

    @Override
    public Collection<E> doDefault(OWLObject object) {
        processStream(((HasComponents) object).components());
        return objects;
    }

    protected void processStream(Stream<?> s) {
        s.forEach(o -> {
            if (o instanceof OWLObject) {
                ((OWLObject) o).accept(this);
            } else if (o instanceof Stream) {
                processStream((Stream<?>) o);
            } else if (o instanceof Collection) {
                processStream(((Collection<?>) o).stream());
            }
        });
    }

    /**
     * @return collected objects
     */
    public Collection<E> getObjects() {
        return objects;
    }
}
