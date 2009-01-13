package org.semanticweb.owl.model;

import java.util.HashMap;
import java.util.Map;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 02-Jan-2007<br><br>
 *
 * Represents the concrete representation format of
 * an ontology.  The equality of an ontology format
 * is defined by the equals and hashCode method (not
 * its identity).
 */
public class OWLOntologyFormat {

    private Map<Object, Object> paramaterMap;

    private Map<Object, Object> getParameterMap() {
        if(paramaterMap == null) {
            paramaterMap = new HashMap<Object, Object>();
        }
        return paramaterMap;
    }

    public void setParameter(Object key, Object value) {
        getParameterMap().put(key, value);
    }

    public Object getParameter(Object key, Object defaultValue) {
        Object val = getParameterMap().get(key);
        if(val != null) {
            return val;
        }
        else {
            return defaultValue;
        }
    }


    public boolean equals(Object obj) {
        return obj.getClass().equals(getClass());
    }


    public int hashCode() {
        return getClass().hashCode();
    }
}

