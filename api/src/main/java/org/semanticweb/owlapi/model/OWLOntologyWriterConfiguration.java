/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.model;

import java.io.Serializable;

/**
 * A configuration object that specifies options for output.
 *
 * @author Ignazio
 * @since 5.0.0
 */
public class OWLOntologyWriterConfiguration implements Serializable {

    private boolean saveIds = false;
    private boolean remapIds = true;
    private boolean useNamespaceEntities = false;
    private boolean indenting = true;
    private boolean labelsAsBanner = false;
    private boolean bannersEnabled = true;
    private int indentSize = 4;

    private OWLOntologyWriterConfiguration copy() {
        OWLOntologyWriterConfiguration toReturn = new OWLOntologyWriterConfiguration();
        toReturn.indenting = indenting;
        toReturn.labelsAsBanner = labelsAsBanner;
        toReturn.useNamespaceEntities = useNamespaceEntities;
        toReturn.remapIds = remapIds;
        toReturn.saveIds = saveIds;
        return toReturn;
    }

    /**
     * @return should output banners
     */
    public boolean shouldUseBanners() {
        return bannersEnabled;
    }

    /**
     * @return true if all anonymous individuals should have their ids persisted
     */
    public boolean shouldSaveIdsForAllAnonymousIndividuals() {
        return saveIds;
    }

    /**
     * @return true if all anonymous individuals should have their ids remapped upon reading
     */
    public boolean shouldRemapAllAnonymousIndividualsIds() {
        return remapIds;
    }

    /**
     * @return use namespace entities
     */
    public boolean isUseNamespaceEntities() {
        return useNamespaceEntities;
    }

    /**
     * @return indenting
     */
    public boolean isIndenting() {
        return indenting;
    }

    /**
     * @return indent size
     */
    public int getIndentSize() {
        return indentSize;
    }

    /**
     * @return true if labels should be used for banner comments
     */
    public boolean isLabelsAsBanner() {
        return labelsAsBanner;
    }

    /**
     * @param banners True if banner comments should be enabled.
     * @return new config object
     */
    public OWLOntologyWriterConfiguration withBannersEnabled(boolean banners) {
        if (bannersEnabled == banners) {
            return this;
        }
        OWLOntologyWriterConfiguration copy = copy();
        copy.bannersEnabled = banners;
        return copy;
    }

    /**
     * @param b true if all anonymous individuals should have their ids persisted
     * @return new config object
     */
    public OWLOntologyWriterConfiguration withSaveIdsForAllAnonymousIndividuals(boolean b) {
        if (saveIds == b) {
            return this;
        }
        OWLOntologyWriterConfiguration copy = copy();
        copy.saveIds = b;
        return copy;
    }

    /**
     * @param b true if all anonymous individuals should have their ids remapped after parsing
     * @return new config object
     */
    public OWLOntologyWriterConfiguration withRemapAllAnonymousIndividualsIds(boolean b) {
        if (remapIds == b) {
            return this;
        }
        OWLOntologyWriterConfiguration copy = copy();
        copy.remapIds = b;
        return copy;
    }

    /**
     * @param useEntities useNamespaceEntities
     * @return new config object
     */
    public OWLOntologyWriterConfiguration withUseNamespaceEntities(boolean useEntities) {
        if (useNamespaceEntities == useEntities) {
            return this;
        }
        OWLOntologyWriterConfiguration copy = copy();
        copy.useNamespaceEntities = useEntities;
        return copy;
    }

    /**
     * @param indent indent
     * @return new config object
     */
    public OWLOntologyWriterConfiguration withIndenting(boolean indent) {
        if (indenting == indent) {
            return this;
        }
        OWLOntologyWriterConfiguration copy = copy();
        copy.indenting = indent;
        return copy;
    }

    /**
     * @param indent indent size
     * @return new config object
     */
    public OWLOntologyWriterConfiguration withIndentSize(int indent) {
        if (indentSize == indent) {
            return this;
        }
        OWLOntologyWriterConfiguration copy = copy();
        copy.indentSize = indent;
        return copy;
    }

    /**
     * @param label true if labels should be used for banner comments
     * @return new config object
     */
    public OWLOntologyWriterConfiguration withLabelsAsBanner(boolean label) {
        if (labelsAsBanner == label) {
            return this;
        }
        OWLOntologyWriterConfiguration copy = copy();
        copy.labelsAsBanner = label;
        return copy;
    }
}
