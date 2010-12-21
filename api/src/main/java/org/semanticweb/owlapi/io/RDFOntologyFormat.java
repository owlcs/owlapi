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

    @Override
    public RDFParserMetaData getOntologyLoaderMetaData() {
        return (RDFParserMetaData) super.getOntologyLoaderMetaData();
    }

    public void setOntologyLoaderMetaData(RDFParserMetaData loaderMetaData) {
        super.setOntologyLoaderMetaData(loaderMetaData);
    }
}
