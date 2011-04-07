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

package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLBuiltInAtomImpl extends SWRLAtomImpl implements SWRLBuiltInAtom {

    private List<SWRLDArgument> args;


    public SWRLBuiltInAtomImpl(OWLDataFactory dataFactory, IRI predicate, List<SWRLDArgument> args) {
        super(dataFactory, predicate);
        this.args = new ArrayList<SWRLDArgument>(args);
    }

    @Override
	public IRI getPredicate() {
        return (IRI) super.getPredicate();
    }

    /**
     * Determines if the predicate of this atom is is a core builtin.
     * @return <code>true</code> if this is a core builtin, otherwise <code>false</code>
     */
    public boolean isCoreBuiltIn() {
        return SWRLBuiltInsVocabulary.getBuiltIn(getPredicate().toURI()) != null;
    }

    public List<SWRLDArgument> getArguments() {
        return new ArrayList<SWRLDArgument>(args);
    }


    public Collection<SWRLArgument> getAllArguments() {
        return new ArrayList<SWRLArgument>(args);
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(SWRLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    @Override
	public boolean equals(Object obj) {
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
