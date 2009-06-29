package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.NodeID;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 17-Jan-2009
 */
public class NodeIDImpl implements NodeID {

    private static long counter = System.nanoTime();

    private String id;

    public NodeIDImpl(String id) {
        if (id.startsWith("_:")) {
            this.id = id;
        }
        else {
            this.id = "_:" + id;
        }
    }

    public NodeIDImpl() {
        this(Long.toString(++counter));
    }

    public String toString() {
        return id;
    }

    public int compareTo(NodeID o) {
        return toString().compareTo(o.toString());
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof NodeID)) {
            return false;
        }
        NodeID other = (NodeID) obj;
        return id.equals(other.getID());
    }

    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Gets the string representation of the node ID.  This will begin with _:
     * @return The string representation of the node ID.
     */
    public String getID() {
        return id;
    }
}
