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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLBinaryAtom;
import org.semanticweb.owlapi.model.SWRLPredicate;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 * @param <A>
 *        atom type
 * @param <B>
 *        atom type
 */
public abstract class SWRLBinaryAtomImpl<A extends SWRLArgument, B extends SWRLArgument>
        extends SWRLAtomImpl implements SWRLBinaryAtom<A, B> {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private final A arg0;
    @Nonnull
    private final B arg1;

    protected SWRLBinaryAtomImpl(@Nonnull SWRLPredicate predicate,
            @Nonnull A arg0, @Nonnull B arg1) {
        super(predicate);
        this.arg0 = checkNotNull(arg0, "arg0 cannot be null");
        this.arg1 = checkNotNull(arg1, "arg1 cannot be null");
    }

    @Nonnull
    @Override
    public Collection<SWRLArgument> getAllArguments() {
        List<SWRLArgument> objs = new ArrayList<>();
        objs.add(arg0);
        objs.add(arg1);
        return objs;
    }

    @Override
    public A getFirstArgument() {
        return arg0;
    }

    @Override
    public B getSecondArgument() {
        return arg1;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        SWRLBinaryAtom<?, ?> other = (SWRLBinaryAtom<?, ?>) object;
        int diff = ((OWLObject) getPredicate()).compareTo((OWLObject) other
                .getPredicate());
        if (diff != 0) {
            return diff;
        }
        diff = arg0.compareTo(other.getFirstArgument());
        if (diff != 0) {
            return diff;
        }
        return arg1.compareTo(other.getSecondArgument());
    }
}
