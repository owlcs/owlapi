package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

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
public abstract class OWLOntologyFormat {

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

    /**
     * Determines if this format is an instance of a format that uses prefixes to shorted IRIs
     * @return <code>true</code> if this format is an instance of {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat}
     * other wise <code>false</code>.
     */
    public boolean isPrefixOWLOntologyFormat() {
        return this instanceof PrefixOWLOntologyFormat;
    }

    /**
     * If this format is an instance of {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat} then this method
     * will obtain it as a {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat}
     * @return This format as a more specific {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat}.
     * @throws ClassCastException if this format is not an instance of {@link org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat}
     */
    public PrefixOWLOntologyFormat asPrefixOWLOntologyFormat() {
        return (PrefixOWLOntologyFormat) this;
    }

    public boolean equals(Object obj) {
        return obj.getClass().equals(getClass());
    }


    public int hashCode() {
        return getClass().hashCode();
    }
}

