package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.vocab.Namespaces;

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

    private Set<IRI> ignoredImports = new HashSet<IRI>();
    
    public OWLOntologyLoaderConfiguration() {
        ignoredImports.add(IRI.create(Namespaces.OWL.toString()));
        ignoredImports.add(IRI.create(Namespaces.RDF.toString()));
        ignoredImports.add(IRI.create(Namespaces.RDFS.toString()));
        ignoredImports.add(IRI.create(Namespaces.SWRL.toString()));
        ignoredImports.add(IRI.create(Namespaces.SWRLB.toString()));
        ignoredImports.add(IRI.create(Namespaces.XML.toString()));
        ignoredImports.add(IRI.create(Namespaces.XSD.toString()));
    }



    public MissingOntologyHeaderStrategy getMissingOntologyHeaderStrategy() {
        return missingOntologyHeaderStrategy;
    }

    public OWLOntologyLoaderConfiguration setMissingOntologyHeaderStrategy(MissingOntologyHeaderStrategy missingOntologyHeaderStrategy) {
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
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
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
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
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.strict = strict;
        return copy;
    }

    public boolean isIgnoredImport(IRI iri) {
        return ignoredImports.contains(iri);
    }

    public Set<IRI> getIgnoredImports() {
        return new HashSet<IRI>(ignoredImports);
    }

    public OWLOntologyLoaderConfiguration addIgnoredImport(IRI iri) {
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.ignoredImports.add(iri);
        return configuration;
    }

    public OWLOntologyLoaderConfiguration removeIgnoredImport(IRI iri) {
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.ignoredImports.remove(iri);
        return configuration;
    }

    public OWLOntologyLoaderConfiguration clearIgnoredImports() {
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.ignoredImports.clear();
        return configuration;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Internally copies this configuaration object
     * @return The copied configuration
     */
    private OWLOntologyLoaderConfiguration copyConfiguration() {
        OWLOntologyLoaderConfiguration copy = new OWLOntologyLoaderConfiguration();
        copy.loadAnnotations = this.loadAnnotations;
        copy.ignoredImports.clear();
        copy.ignoredImports.addAll(this.ignoredImports);
        return copy;
    }





}
