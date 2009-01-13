package org.semanticweb.owl.profiles;

import java.util.Collections;
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
 * Date: 16-Apr-2008<br><br>
 */
public abstract class ConstructNotAllowed<O> {

    private Set<ConstructNotAllowed> cause;

    private O construct;


    protected ConstructNotAllowed(O construct) {
        this.construct = construct;
    }


    protected ConstructNotAllowed(ConstructNotAllowed cause, O construct) {
        this.cause = Collections.singleton(cause);
        this.construct = construct;
    }


    protected ConstructNotAllowed(Set<ConstructNotAllowed> cause, O construct) {
        this.cause = new HashSet<ConstructNotAllowed>(cause);
        this.construct = construct;
    }


    /**
     * Gets the chained cause.
     * @return The cause of this construct not being allowed.  The
     * return value may be <code>null</code> if there is no cause.
     */
    public Set<ConstructNotAllowed> getCause() {
        return cause;
    }


    /**
     * Gets the construct that isn't allowed.
     * @return The construct that isn't allowed.
     */
    public O getConstruct() {
        return construct;
    }


    /**
     * Gets a short readable message as to why the construct
     * is not allowed.  By default this will return the simple
     * name of the implmenentation class.
     * @return The reason as to why this construct is not allowed.
     */
    public String getReason() {
        return getClass().getSimpleName();
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getReason());
        sb.append(": ");
        sb.append(getConstruct());
        if(getCause() != null) {
            sb.append(" [Caused by ");
            sb.append(getCause());
            sb.append("]");
        }
        return sb.toString();
    }
}
