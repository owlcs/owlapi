package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.Collection;
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
public abstract class SWRLBinaryAtomImpl<A extends SWRLArgument, B extends SWRLArgument> extends SWRLAtomImpl implements SWRLBinaryAtom<A, B> {

    private A arg0;

    private B arg1;

    protected SWRLBinaryAtomImpl(OWLDataFactory dataFactory, SWRLPredicate predicate, A arg0, B arg1) {
        super(dataFactory, predicate);
        this.arg0 = arg0;
        this.arg1 = arg1;
    }


    public Collection<SWRLArgument> getAllArguments() {
        List<SWRLArgument> objs = new ArrayList<SWRLArgument>();
        objs.add(arg0);
        objs.add(arg1);
        return objs;
    }


    public A getFirstArgument() {
        return arg0;
    }


    public B getSecondArgument() {
        return arg1;
    }


    protected int compareObjectOfSameType(OWLObject object) {
        SWRLBinaryAtom other = (SWRLBinaryAtom) object;
        int diff = ((OWLObject)getPredicate()).compareTo((OWLObject)other.getPredicate());
        if(diff != 0) {
            return diff;
        }
        diff = arg0.compareTo(other.getFirstArgument());
        if(diff != 0) {
            return diff;
        }
        return arg1.compareTo(other.getSecondArgument());
    }
}
