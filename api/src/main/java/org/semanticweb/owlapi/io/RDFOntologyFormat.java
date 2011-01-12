package org.semanticweb.owlapi.io;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
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
     * Determines if untyped entities should automatically be typed (declared) during rendering.  (This is a hint to an RDF
     * renderer - the reference implementation will respect this).
     * The render will check with the {@link #isMissingType(org.semanticweb.owlapi.model.OWLEntity, org.semanticweb.owlapi.model.OWLOntology)}
     * method to determine if it needs to add a type.
     * @return <code>true</code> if untyped entities should automatically be typed during rendering,
     *         otherwise <code>false</code>.
     */
    public boolean isAddMissingTypes() {
        return addMissingTypes;
    }

    /**
     * Determines if a declaration axiom (type triple) needs to be added to the specified ontology for the given entity.
     * @param entity The entity
     * @param ontology The ontology.
     * @return <code>false</code> if the entity is built in. <code>false</code> if the ontology doesn't contain
     *         the entity in its signature. <code>false</code> if the entity is already declared in the imports closure
     *         of the ontology. <code>false</code> if the transitive imports does not contain the ontology but the entity
     *         is contained in the signature of one of the imported ontologies, <code>true</code> if none of the previous conditions
     *         are met.
     */
    public static boolean isMissingType(OWLEntity entity, OWLOntology ontology) {
        // We don't need to declare built in entities
        if (entity.isBuiltIn()) {
            return false;
        }
        // If the ontology doesn't contain the entity in its signature then it shouldn't declare it
        if (!ontology.containsEntityInSignature(entity)) {
            return false;
        }
        if (ontology.isDeclared(entity, true)) {
            return false;
        }
        Set<OWLOntology> transitiveImports = ontology.getImports();
        if (!transitiveImports.contains(ontology)) {
            // See if the entity should be declared in an imported ontology
            for (OWLOntology importedOntology : transitiveImports) {
                if (importedOntology.containsEntityInSignature(entity)) {
                    // Leave it for that ontology to declare the entity
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines if untyped entities should automatically be typed during rendering.  By default this is true.
     * @param addMissingTypes <code>true</code> if untyped entities should automatically be typed during rendering,
     * otherwise <code>false</code>.
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
