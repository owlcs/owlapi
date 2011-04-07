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

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Apr-2008<br><br>
 */
public class OWLOntologyManagerProperties {

    private boolean loadAnnotationAxioms = true;

    private boolean treatDublinCoreVocabularyAsBuiltInVocabulary = true;

    public OWLOntologyManagerProperties() {
        restoreDefaults();
    }

    /**
     * Restores the various properties to their default values.
     */
    public void restoreDefaults() {
        loadAnnotationAxioms = true;
        treatDublinCoreVocabularyAsBuiltInVocabulary = true;
    }

    /**
     * Determines if annotation axioms should be loaded or discarded.
     * @return <code>true</code> if annotation axioms should be loaded
     *         or <code>false</code> if annotation axioms should be ignored.
     */
    public boolean isLoadAnnotationAxioms() {
        return loadAnnotationAxioms;
    }


    /**
     * Specifies whether annotation axioms should be loaded or ignored.
     * @param loadAnnotationAxioms <code>true</code> if annotation axioms should be
     * loaded (default) or <code>false</code> if annotation axioms should be ignored.  Note that
     * this is merely a hint to parsers and loaders - a setting of <code>false</code>
     * does not guarentee that annotations won't be loaded.
     */
    public void setLoadAnnotationAxioms(boolean loadAnnotationAxioms) {
        this.loadAnnotationAxioms = loadAnnotationAxioms;
    }

    /**
     * Determines if the various parsers, for formats such as RDF based formats that do not require strong typing,
     * should treat Dublin Core Vocabulary as built in vocabulary, so that
     * Dublin Core metadata properties are interpreted as annotation properties.
     * @return <code>true</code> if the Dublin Core Vocabulary should be treated as built in vocabulary and
     *         Dublin Core properties are interpreted as annotation properties, otherwise <code>false</code>.  The
     *         defaut is <code>true</code>.
     */
    public boolean isTreatDublinCoreVocabularyAsBuiltInVocabulary() {
        return treatDublinCoreVocabularyAsBuiltInVocabulary;
    }

    /**
     * Specifies if the various parsers, for formats such as RDF based formats that do not require strong typing,
     * should treat Dublin Core Vocabulary as built in vocabulary, so that
     * Dublin Core metadata properties are interpreted as annotation properties.
     */
    public void setTreatDublinCoreVocabularyAsBuiltInVocabulary(boolean treatDublinCoreVocabularyAsBuiltInVocabulary) {
        this.treatDublinCoreVocabularyAsBuiltInVocabulary = treatDublinCoreVocabularyAsBuiltInVocabulary;
    }
}
