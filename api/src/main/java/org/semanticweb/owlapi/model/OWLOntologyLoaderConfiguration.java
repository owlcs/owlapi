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
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.vocab.Namespaces;

/**
 * A configuration object that specifies options and hints to objects that load
 * OWLOntologies. Every {@code OWLOntologyLoaderConfiguration} is immutable.
 * Changing a setting results in the creation of a new
 * {@code OWLOntologyLoaderConfiguration} with that setting. For example,
 * 
 * <pre>
 * OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
 * config = config.setLoadAnnotationAxioms(false);
 * </pre>
 * 
 * creates an {@code OWLOntologyLoaderConfiguration} object with the load
 * annotation axioms set to {@code false}.
 * 
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.2.0
 */
public class OWLOntologyLoaderConfiguration implements Serializable {

    private static final long serialVersionUID = 40000L;

    /**
     * what action to take if the ontology header is missing.
     */
    public enum MissingOntologyHeaderStrategy {
        /** Include triples. */
        INCLUDE_GRAPH,
        /** Keep import structure. */
        IMPORT_GRAPH
    }

    /** True if http compression should be used. */
    private boolean acceptHTTPCompression = true;
    /** Timeout for connections. */
    private int connectionTimeout = 20000;
    /** True if redirects should be followed across protocols. */
    private boolean followRedirects = true;
    /** Set of imports to ignore. */
    @Nonnull
    private final Set<IRI> ignoredImports = new HashSet<>();
    /** True if annotations should be loaded, false if skipped. */
    private boolean loadAnnotations = true;
    /** Missing imports handling strategy. */
    @Nonnull
    private MissingImportHandlingStrategy missingImportHandlingStrategy = MissingImportHandlingStrategy.THROW_EXCEPTION;
    /** Default missing ontology strategy. */
    @Nonnull
    private MissingOntologyHeaderStrategy missingOntologyHeaderStrategy = MissingOntologyHeaderStrategy.INCLUDE_GRAPH;
    /** Flag to enable stack traces on parsing exceptions. */
    private boolean reportStackTraces = true;
    /**
     * Number of retries to attempt when retrieving an ontology form a remote
     * URL. Defaults to 5.
     */
    private int retriesToAttempt = 5;
    /** True if strict parsing should be used. */
    private boolean strict = false;
    /** True if Dublin Core. */
    private boolean treatDublinCoreAsBuiltIn = true;
    /**sort configuration for priority collections*/
    private PriorityCollectionSorting priorityCollectionSorting=PriorityCollectionSorting.ON_SET_INJECTION_ONLY;

    /**
     * Set the priorty collection sorting option.
     * 
     * @param sorting the sorting option to be used.
     * @return An {@code OWLOntologyLoaderConfiguration} with the new sorting option set.
     */
    public OWLOntologyLoaderConfiguration setPriorityCollectionSorting(
            PriorityCollectionSorting sorting) {
        if(priorityCollectionSorting==sorting) {
            return this;
        }
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.priorityCollectionSorting=sorting;
        return configuration;
    }

    /**
     * @return The {@code PriorityCollectionSorting} for this configuration. It determines how parsers, storers and mappers are ordered.
     * Default is {@link PriorityCollectionSorting#ON_SET_INJECTION_ONLY}
     */
    public PriorityCollectionSorting getPriorityCollectionSorting() {
        return priorityCollectionSorting;
    }

    /**
     * Adds an ontology document IRI to the list of ontology imports that will
     * be ignored during ontology loading.
     * 
     * @param ontologyDocumentIRI
     *        The ontology document IRI that will be ignored if it is
     *        encountered as an imported ontology during loading.
     * @return An {@code OWLOntologyLoaderConfiguration} with the ignored
     *         ontology document IRI set.
     */
    public OWLOntologyLoaderConfiguration addIgnoredImport(
            IRI ontologyDocumentIRI) {
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.ignoredImports.add(ontologyDocumentIRI);
        return configuration;
    }

    /**
     * Clears all ontology document IRIs from the list of ignored ontology
     * document IRIs.
     * 
     * @return An {@code OWLOntologyLoaderConfiguration} with the list of
     *         ignored ontology document IRIs set to be empty.
     */
    public OWLOntologyLoaderConfiguration clearIgnoredImports() {
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.ignoredImports.clear();
        return configuration;
    }

    /**
     * Internally copies this configuaration object.
     * 
     * @return The copied configuration
     */
    @Nonnull
    private OWLOntologyLoaderConfiguration copyConfiguration() {
        OWLOntologyLoaderConfiguration copy = new OWLOntologyLoaderConfiguration();
        copy.acceptHTTPCompression = acceptHTTPCompression;
        copy.connectionTimeout = connectionTimeout;
        copy.followRedirects = followRedirects;
        copy.ignoredImports.clear();
        copy.ignoredImports.addAll(ignoredImports);
        copy.loadAnnotations = loadAnnotations;
        copy.missingImportHandlingStrategy = missingImportHandlingStrategy;
        copy.missingOntologyHeaderStrategy = missingOntologyHeaderStrategy;
        copy.reportStackTraces = reportStackTraces;
        copy.retriesToAttempt = retriesToAttempt;
        copy.strict = strict;
        copy.treatDublinCoreAsBuiltIn = treatDublinCoreAsBuiltIn;
        return copy;
    }

    /** @return the connection timeout for this configuration */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Gets the strategy used for missing imports.
     * 
     * @return The strategy. See {@link MissingImportHandlingStrategy} for the
     *         strategies and their descriptions.
     * @since 3.3
     */
    public MissingImportHandlingStrategy getMissingImportHandlingStrategy() {
        return missingImportHandlingStrategy;
    }

    /** @return the ontology header strategy */
    public MissingOntologyHeaderStrategy getMissingOntologyHeaderStrategy() {
        return missingOntologyHeaderStrategy;
    }

    /**
     * @return number of retries to attempt when retrieving an ontology form a
     *         remote URL.
     */
    public int getRetriesToAttempt() {
        return retriesToAttempt;
    }

    /** @return true if http compression should be accepted. */
    public boolean isAcceptingHTTPCompression() {
        return acceptHTTPCompression;
    }

    /**
     * When loading an ontology, a parser might connect to a remote URL. If the
     * remote URL is a 302 redirect and the protocol is different, e.g., http to
     * https, the parser needs to decide whether to follow the redirect and
     * download the ontology from an alternate source, or stop with an
     * UnloadableOntologyError. By default this is true, meaning redirects will
     * be followed across protocols. If set to false, redirects will be followed
     * only within the same protocol (URLConnection limits this to five
     * redirects).
     * 
     * @return true if redirects should be followed when importing ontologies
     *         from remote URLs
     */
    public boolean isFollowRedirects() {
        return followRedirects;
    }

    /**
     * @param iri
     *        iri to check
     * @return true if iri should be ignored
     */
    public boolean isIgnoredImport(IRI iri) {
        return Namespaces.isDefaultIgnoredImport(iri)
                || ignoredImports.contains(iri);
    }

    /**
     * Determines whether or not annotation axioms (instances of
     * {@code OWLAnnotationAxiom}) should be loaded. By default, the loading of
     * annotation axioms is enabled.
     * 
     * @return {@code true} if annotation assertions will be loaded, or
     *         {@code false} if annotation assertions will not be loaded because
     *         they will be discarded on loading.
     */
    public boolean isLoadAnnotationAxioms() {
        return loadAnnotations;
    }

    /** @return value for the report stack trace flag. */
    public boolean isReportStackTrace() {
        return reportStackTraces;
    }

    /** @return true if parsing should be strict */
    public boolean isStrict() {
        return strict;
    }

    /**
     * Determines if the various parsers, for formats such as RDF based formats
     * that do not require strong typing, should treat Dublin Core Vocabulary as
     * built in vocabulary, so that Dublin Core metadata properties are
     * interpreted as annotation properties.
     * 
     * @return {@code true} if the Dublin Core Vocabulary should be treated as
     *         built in vocabulary and Dublin Core properties are interpreted as
     *         annotation properties, otherwise {@code false}. The defaut is
     *         {@code true}.
     */
    public boolean isTreatDublinCoreAsBuiltIn() {
        return treatDublinCoreAsBuiltIn;
    }

    /**
     * Removes an ontology document IRI from the list of ontology imports that
     * will be ignored during ontology loading.
     * 
     * @param ontologyDocumentIRI
     *        The ontology document IRI that would be ignored if it is
     *        encountered as an imported ontology during loading.
     * @return An {@code OWLOntologyLoaderConfiguration} with the ignored
     *         ontology document IRI removed.
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration removeIgnoredImport(
            IRI ontologyDocumentIRI) {
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.ignoredImports.remove(ontologyDocumentIRI);
        return configuration;
    }

    /**
     * @param b
     *        true if HTTP compression should be accepted
     * @return a copy of this configuration with accepting HTTP compression set
     *         to the new value
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration
            setAcceptingHTTPCompression(boolean b) {
        // do not make copies if setting the same value
        if (acceptHTTPCompression == b) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.acceptHTTPCompression = b;
        return copy;
    }

    /**
     * @param l
     *        new timeout Note: the timeout is an int and represents
     *        milliseconds. This is necessary for use in {@code URLConnection}
     * @return A {@code OWLOntologyLoaderConfiguration} with the connection
     *         timeout set to the new value.
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration setConnectionTimeout(int l) {
        if (l == connectionTimeout) {
            return this;
        }
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.connectionTimeout = l;
        return configuration;
    }

    /**
     * @param value
     *        true if redirects should be followed across protocols, false
     *        otherwise.
     * @return a copy of the current object with followRedirects set to the new
     *         value.
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration setFollowRedirects(boolean value) {
        // as the objects are immutable, setting to the same value returns the
        // same object
        if (value == followRedirects) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.followRedirects = value;
        return copy;
    }

    /**
     * Specifies whether or not annotation axioms (instances of
     * {@code OWLAnnotationAxiom}) should be loaded or whether they should be
     * discarded on loading. By default, the loading of annotation axioms is
     * enabled.
     * 
     * @param b
     *        {@code true} if annotation axioms should be loaded, or
     *        {@code false} if annotation axioms should not be loaded and should
     *        be discarded on loading.
     * @return An {@code OWLOntologyLoaderConfiguration} object with the option
     *         set.
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration setLoadAnnotationAxioms(boolean b) {
        // do not make copies if setting the same value
        if (loadAnnotations == b) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.loadAnnotations = b;
        return copy;
    }

    /**
     * Sets the strategy that is used for missing imports handling. See
     * {@link MissingImportHandlingStrategy} for the strategies and their
     * descriptions.
     * 
     * @param missingImportHandlingStrategy
     *        The strategy to be used.
     * @return An {@code OWLOntologyLoaderConfiguration} object with the
     *         strategy set.
     * @since 3.3
     */
    @Nonnull
    public
            OWLOntologyLoaderConfiguration
            setMissingImportHandlingStrategy(
                    @Nonnull MissingImportHandlingStrategy missingImportHandlingStrategy) {
        // do not make copies if setting the same value
        if (this.missingImportHandlingStrategy == missingImportHandlingStrategy) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.missingImportHandlingStrategy = missingImportHandlingStrategy;
        return copy;
    }

    /**
     * @param missingOntologyHeaderStrategy
     *        new value
     * @return a copy of this configuration object with a different strategy
     */
    @Nonnull
    public
            OWLOntologyLoaderConfiguration
            setMissingOntologyHeaderStrategy(
                    @Nonnull MissingOntologyHeaderStrategy missingOntologyHeaderStrategy) {
        // do not make copies if setting the same value
        if (missingOntologyHeaderStrategy == this.missingOntologyHeaderStrategy) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.missingOntologyHeaderStrategy = missingOntologyHeaderStrategy;
        return copy;
    }

    /**
     * Set the value for the report stack traces flag. If true, parsing
     * exceptions will have the full stack trace for the source exceptions.
     * Default is false.
     * 
     * @param b
     *        the new value for the flag
     * @return A {@code OWLOntologyLoaderConfiguration} with the report flag set
     *         to the new value.
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration setReportStackTraces(boolean b) {
        if (b == reportStackTraces) {
            return this;
        }
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.reportStackTraces = b;
        return configuration;
    }

    /**
     * @param retries
     *        new value of retries to attempt
     * @return copy of this configuration with modified retries attempts.
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration setRetriesToAttempt(int retries) {
        // do not make copies if setting the same value
        if (retries == retriesToAttempt) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.retriesToAttempt = retries;
        return copy;
    }

    /**
     * @param strict
     *        new value for strict
     * @return copy of the configuration with new strict value
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration setStrict(boolean strict) {
        // do not make copies if setting the same value
        if (this.strict == strict) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.strict = strict;
        return copy;
    }

    /**
     * @param value
     *        true if Dublin Core vocabulary should be treated as built in.
     * @return a copy of the current object with treatDublinCoreAsBuiltIn set to
     *         the new value.
     */
    @Nonnull
    public OWLOntologyLoaderConfiguration setTreatDublinCoreAsBuiltIn(
            boolean value) {
        // as the objects are immutable, setting to the same value returns the
        // same object
        if (value == treatDublinCoreAsBuiltIn) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.treatDublinCoreAsBuiltIn = value;
        return copy;
    }
}
