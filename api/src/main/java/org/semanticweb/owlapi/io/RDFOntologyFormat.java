package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-Jun-2009
 */
public abstract class RDFOntologyFormat extends PrefixOWLOntologyFormat {

    private boolean addMissingTypes = true;

    private ParserMetaData parserMetaData = null;

    /**
     * An enumeration of
     */
    public enum OntologyHeaderStatus {

        /**
         * Specifies that during parsing, the ontology document did not contain any ontology headers
         */
        PARSED_ZERO_HEADERS,

        /**
         * Specifies that during parsing, the ontology document that the ontology was created from contained one header
         */
        PARSED_ONE_HEADER,

        /**
         * Specifies that during parsing, the ontology document that the ontology was created from contained multiple headers
         */
        PARSED_MULTIPLE_HEADERS
    }


    public static class ParserMetaData {

        private int tripleCount;

        private OntologyHeaderStatus headerStatus;

        public ParserMetaData(int tripleCount, OntologyHeaderStatus headerStatus) {
            this.tripleCount = tripleCount;
            this.headerStatus = headerStatus;
        }

        public int getTripleCount() {
            return tripleCount;
        }

        public OntologyHeaderStatus getHeaderState() {
            return headerStatus;
        }
    }

    /**
     * Gets the parser meta data for the ontology that this format relates to.
     * @return The parser metadata for the ontology that this format relates to.  If the ontology was not created
     *         by parsing an ontology document then this method will return <code>null</code>.
     */
    public ParserMetaData getParserMetaData() {
        return parserMetaData;
    }

    /**
     * Sets the parser meta data for the ontology that this format relates to.
     * @param parserMetaData The parser meta data.  May be <code>null</code> to indicate that no parser meta data
     *                       is available.
     */
    public void setParserMetaData(ParserMetaData parserMetaData) {
        this.parserMetaData = parserMetaData;
    }

    /**
     * Determines if untyped entities should automatically be typed during rendering.  (This is a hint to an RDF
     * renderer - the reference implementation will respect this).
     * @return <code>true</code> if untyped entities should automatically be typed during rendering,
     *         otherwise <code>false</code>.
     */
    public boolean isAddMissingTypes() {
        return addMissingTypes;
    }


    /**
     * Determines if untyped entities should automatically be typed during rendering.  By default this is true.
     * @param addMissingTypes <code>true</code> if untyped entities should automatically be typed during rendering,
     *                        otherwise <code>false</code>.
     */
    public void setAddMissingTypes(boolean addMissingTypes) {
        this.addMissingTypes = addMissingTypes;
    }
}
