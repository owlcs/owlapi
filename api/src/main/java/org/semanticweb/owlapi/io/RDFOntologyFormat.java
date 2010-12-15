package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

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
