package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLObject;
import org.semanticweb.owl.model.SWRLAtomObject;
import org.semanticweb.owl.model.SWRLUnaryAtom;

import java.util.Collection;
import java.util.Collections;
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
public abstract class SWRLUnaryAtomImpl<P, A extends SWRLAtomObject> extends SWRLAtomImpl<P> implements SWRLUnaryAtom<P, A> {

    private A arg;

    public SWRLUnaryAtomImpl(OWLDataFactory dataFactory, P predicate, A arg) {
        super(dataFactory, predicate);
        this.arg = arg;
    }


    public A getArgument() {
        return arg;
    }


    public Collection<? extends SWRLAtomObject> getAllArguments() {
        return Collections.singleton(arg);
    }


    final protected int compareObjectOfSameType(OWLObject object) {
        return arg.compareTo(((SWRLUnaryAtom) object).getArgument());
    }
}
