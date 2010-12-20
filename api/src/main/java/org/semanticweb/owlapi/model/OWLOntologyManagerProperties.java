package org.semanticweb.owlapi.model;

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
