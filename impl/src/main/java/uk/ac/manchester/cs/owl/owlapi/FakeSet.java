package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
 * Date: 27-Jun-2007<br><br>
 *
 * A set which is really backed by a list.  This is used in the reference implementation
 * of OWLOntology (OWLOntologyImpl) because it improves performance with respect to speed
 * and memory consumption.  This class should be used with CAUTION - in particular, the
 * user must know that there aren't duplicates when adding elements.
 *
 * Changed back to hash set - this class should proabably be deprecated because although
 * loading is very fast, removal of axioms is slowed down.
 */
class FakeSet<O> extends HashSet<O> implements Set<O> {

    public FakeSet() {
    }


    public FakeSet(Collection<? extends O> c) {
        super(c);
    }


    public FakeSet(int initialCapacity) {
        super(initialCapacity);
    }


    public boolean contains(Object elem) {
        return super.contains(elem);
    }
}
