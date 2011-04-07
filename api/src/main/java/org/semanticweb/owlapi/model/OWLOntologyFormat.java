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

package org.semanticweb.owlapi.model;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;


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

    private OWLOntologyLoaderMetaData loaderMetaData = new NullLoaderMetaData();

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

    /**
     * If this format describes an ontology that was loaded from some ontology document (rather than created programmatically)
     * there may be some meta data about the loading process.  Subclasses of <code>OWLOntologyFormat</code> will provide
     * accessors etc. to details pertaining to the meta data about loading.
     * @return An object containing the meta data about loading.  Not <code>null</code>.
     */
    public OWLOntologyLoaderMetaData getOntologyLoaderMetaData() {
        return loaderMetaData;
    }

    /**
     * Sets the meta data for the ontology loader.
     * @param loaderMetaData The metadata. Must not be <code>null</code>.
     * @throws NullPointerException if the <code>loaderMetaData</code> is <code>null</code>.
     */
    public void setOntologyLoaderMetaData(OWLOntologyLoaderMetaData loaderMetaData) {
        this.loaderMetaData = loaderMetaData;
    }

    @Override
	public boolean equals(Object obj) {
        return obj != null && (obj == this || obj.getClass().equals(getClass()));
    }


    @Override
	public int hashCode() {
        return getClass().hashCode();
    }

    private static class NullLoaderMetaData implements OWLOntologyLoaderMetaData {

    }
}

