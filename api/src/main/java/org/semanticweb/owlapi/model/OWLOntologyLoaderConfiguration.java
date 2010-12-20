package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.vocab.BuiltInVocabulary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15/12/2010
 * <p>
 * A configuration object that specifies options and hints to objects that load OWLOntologies.  Every
 * <code>OWLOntologyLoaderConfiguration</code> is immutable.  Changing a setting results in the creation
 * of a new <code>OWLOntologyLoaderConfiguration</code> with that setting.  For example,
 * <pre>
 * OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
 * config = config.setLoadAnnotationAxioms(false);
 * </pre>
 * creates an <code>OWLOntologyLoaderConfiguration</code> object with the load annotation axioms set to <code>false</code>.
 */
public final class OWLOntologyLoaderConfiguration {

    public enum MissingOntologyHeaderStrategy {

        INCLUDE_GRAPH,

        IMPORT_GRAPH
    }


    public static final boolean DEFAULT_LOAD_ANNOTATIONS_FLAG_VALUE = true;

    public static final MissingOntologyHeaderStrategy DEFAULT_MISSING_ONTOLOGY_HEADER_STRATEGY = MissingOntologyHeaderStrategy.INCLUDE_GRAPH;





    private boolean loadAnnotations = DEFAULT_LOAD_ANNOTATIONS_FLAG_VALUE;

    private MissingOntologyHeaderStrategy missingOntologyHeaderStrategy = DEFAULT_MISSING_ONTOLOGY_HEADER_STRATEGY;


    private boolean strict = false;

    
    public OWLOntologyLoaderConfiguration() {
    }


    public MissingOntologyHeaderStrategy getMissingOntologyHeaderStrategy() {
        return missingOntologyHeaderStrategy;
    }

    public OWLOntologyLoaderConfiguration setMissingOntologyHeaderStrategy(MissingOntologyHeaderStrategy missingOntologyHeaderStrategy) {
        OWLOntologyLoaderConfiguration copy = copy();
        copy.missingOntologyHeaderStrategy = missingOntologyHeaderStrategy;
        return copy;
    }

    /**
     * Specifies whether or not annotation axioms (instances of <code>OWLAnnotationAxiom</code>) should be loaded or
     * whether they should be discarded on
     * loading.  By default, the loading of annotation axioms is enabled.
     * @param b <code>true</code> if annotation axioms should be loaded, or <code>false</code> if annotation
     * axioms should not be loaded and should be discarded on loading.
     * @return An <code>OWLOntologyLoaderConfiguration</code> object with the option set.
     */
    public OWLOntologyLoaderConfiguration setLoadAnnotationAxioms(boolean b) {
        OWLOntologyLoaderConfiguration copy = copy();
        copy.loadAnnotations = b;
        return copy;
    }

    /**
     * Determines whether or not annotation axioms (instances of <code>OWLAnnotationAxiom</code>) should be loaded.
     * By default, the loading of annotation axioms is enabled.
     * @return <code>true</code> if annotation assertions will be loaded, or <code>false</code> if annotation
     * assertions will not be loaded because they will be discarded on loading.
     */
    public boolean isLoadAnnotationAxioms() {
         return loadAnnotations;
     }

    public boolean isStrict() {
        return strict;
    }

    public OWLOntologyLoaderConfiguration setStrict(boolean strict) {
        OWLOntologyLoaderConfiguration copy = copy();
        copy.strict = strict;
        return copy;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Internally copies this configuaration object
     * @return The copied configuration
     */
    private OWLOntologyLoaderConfiguration copy() {
        OWLOntologyLoaderConfiguration copy = new OWLOntologyLoaderConfiguration();
        copy.loadAnnotations = this.loadAnnotations;
        return copy;
    }





}
