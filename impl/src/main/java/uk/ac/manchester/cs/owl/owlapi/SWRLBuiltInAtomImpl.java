package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLBuiltInAtomImpl extends SWRLAtomImpl implements SWRLBuiltInAtom {

    private List<SWRLDArgument> args;


    public SWRLBuiltInAtomImpl(OWLDataFactory dataFactory, IRI predicate,
                               List<SWRLDArgument> args) {
        super(dataFactory, predicate);
        this.args = new ArrayList<SWRLDArgument>(args);
    }

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
        return Collections.unmodifiableList(args);
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


    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if (!(obj instanceof SWRLBuiltInAtom)) {
            return false;
        }
        SWRLBuiltInAtom other = (SWRLBuiltInAtom) obj;
        return other.getPredicate().equals(getPredicate()) && other.getArguments().equals(getArguments());
    }


    protected int compareObjectOfSameType(OWLObject object) {
        SWRLBuiltInAtom other = (SWRLBuiltInAtom) object;
        int diff = getPredicate().compareTo(other.getPredicate());
        if(diff != 0) {
            return diff;
        }
        List<SWRLDArgument> otherArgs = other.getArguments();
        int i = 0;
        while(i < args.size() && i < otherArgs.size()) {
            diff = args.get(i).compareTo(otherArgs.get(i));
            if(diff != 0) {
                return diff;
            }
            i++;
        }
        return args.size() - otherArgs.size();
    }
}
