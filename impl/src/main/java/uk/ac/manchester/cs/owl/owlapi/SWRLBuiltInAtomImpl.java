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
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class SWRLBuiltInAtomImpl extends SWRLAtomImpl implements SWRLBuiltInAtom {

    private final @Nonnull List<SWRLDArgument> args;

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.RULE_OBJECT_TYPE_INDEX_BASE + 5;
    }

    /**
     * @param predicate
     *        predicate
     * @param args
     *        builtin argument
     */
    public SWRLBuiltInAtomImpl(IRI predicate, List<SWRLDArgument> args) {
        super(predicate);
        this.args = new ArrayList<>(checkNotNull(args, "args cannot be null"));
    }

    @Override
    public IRI getPredicate() {
        return (IRI) super.getPredicate();
    }

    @Override
    public boolean isCoreBuiltIn() {
        return SWRLBuiltInsVocabulary.getBuiltIn(getPredicate()) != null;
    }

    @Override
    public Stream<SWRLDArgument> arguments() {
        return args.stream();
    }

    @Override
    public Collection<SWRLArgument> getAllArguments() {
        return new ArrayList<>(args);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SWRLBuiltInAtom)) {
            return false;
        }
        SWRLBuiltInAtom other = (SWRLBuiltInAtom) obj;
        return other.getPredicate().equals(getPredicate()) && other.getArguments().equals(getArguments());
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        SWRLBuiltInAtom other = (SWRLBuiltInAtom) object;
        int diff = getPredicate().compareTo(other.getPredicate());
        if (diff != 0) {
            return diff;
        }
        List<SWRLDArgument> otherArgs = other.getArguments();
        int i = 0;
        while (i < args.size() && i < otherArgs.size()) {
            diff = args.get(i).compareTo(otherArgs.get(i));
            if (diff != 0) {
                return diff;
            }
            i++;
        }
        return args.size() - otherArgs.size();
    }
}
