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

/**
 * A configuration builder that specifies all available options in the OWL API.
 * Can be used to build OWLOntologyLoaderConfiguration and
 * OWLOntologyWriterConfiguration objects
 * 
 * @author Ignazio
 * @since 5.0.0
 */
public class OntologyConfigurator implements Serializable {

    /** True if http compression should be used. */
    private boolean acceptHTTPCompression = true;
    /** Timeout for connections. */
    private int connectionTimeout = 20000;
    /** True if redirects should be followed across protocols. */
    private boolean followRedirects = true;
    /** Set of imports to ignore. */
    @Nonnull private final Set<IRI> ignoredImports = new HashSet<>();
    /** True if annotations should be loaded, false if skipped. */
    private boolean loadAnnotations = true;
    /** Missing imports handling strategy. */
    @Nonnull private MissingImportHandlingStrategy missingImportHandlingStrategy = MissingImportHandlingStrategy.THROW_EXCEPTION;
    /** Default missing ontology strategy. */
    @Nonnull private MissingOntologyHeaderStrategy missingOntologyHeaderStrategy = MissingOntologyHeaderStrategy.INCLUDE_GRAPH;
    /** Flag to enable stack traces on parsing exceptions. */
    private boolean reportStackTraces = true;
    /**
     * Number of retries to attempt when retrieving an ontology form a remote
     * URL. Defaults to 5.
     */
    private int retriesToAttempt = 5;
    /** True if strict parsing should be used. */
    private boolean parseWithStrictConfiguration = false;
    /** True if Dublin Core. */
    private boolean treatDublinCoreAsBuiltIn = true;
    /** sort configuration for priority collections */
    private PriorityCollectionSorting priorityCollectionSorting = PriorityCollectionSorting.ON_SET_INJECTION_ONLY;
    // Save options
    /**
     * True if ids for blank nodes should always be written (axioms and
     * anonymous individuals only).
     */
    private boolean saveIds = false;
    /**
     * True if all anonymous individuals should have their ids remapped after
     * parsing.
     */
    private boolean remapIds = true;
    /** True if entities should be used for namespace abbreviations. */
    private boolean useNamespaceEntities = false;
    /** True if indenting should be used when writing out a file. */
    private boolean indenting = true;
    /**
     * Size of indentation between levels. Only used if indenting is set to
     * true.
     */
    private int indentSize = 4;
    private boolean labelsAsBanner = false;

    /**
     * Set the priorty collection sorting option.
     * 
     * @param sorting
     *        the sorting option to be used.
     * @return An {@code OWLOntologyLoaderConfiguration} with the new sorting
     *         option set.
     */
    public OntologyConfigurator setPriorityCollectionSorting(PriorityCollectionSorting sorting) {
        priorityCollectionSorting = sorting;
        return this;
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
    public OntologyConfigurator addIgnoredImport(IRI ontologyDocumentIRI) {
        ignoredImports.add(ontologyDocumentIRI);
        return this;
    }

    /**
     * Clears all ontology document IRIs from the list of ignored ontology
     * document IRIs.
     * 
     * @return An {@code OWLOntologyLoaderConfiguration} with the list of
     *         ignored ontology document IRIs set to be empty.
     */
    public OntologyConfigurator clearIgnoredImports() {
        ignoredImports.clear();
        return this;
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
    public OntologyConfigurator removeIgnoredImport(IRI ontologyDocumentIRI) {
        ignoredImports.remove(ontologyDocumentIRI);
        return this;
    }

    /**
     * @param b
     *        true if HTTP compression should be accepted
     * @return a copy of this configuration with accepting HTTP compression set
     *         to the new value
     */
    public OntologyConfigurator setAcceptingHTTPCompression(boolean b) {
        acceptHTTPCompression = b;
        return this;
    }

    /**
     * @param l
     *        new timeout Note: the timeout is an int and represents
     *        milliseconds. This is necessary for use in {@code URLConnection}
     * @return A {@code OWLOntologyLoaderConfiguration} with the connection
     *         timeout set to the new value.
     */
    public OntologyConfigurator setConnectionTimeout(int l) {
        connectionTimeout = l;
        return this;
    }

    /**
     * @param value
     *        true if redirects should be followed across protocols, false
     *        otherwise.
     * @return a copy of the current object with followRedirects set to the new
     *         value.
     */
    public OntologyConfigurator setFollowRedirects(boolean value) {
        followRedirects = value;
        return this;
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
    public OntologyConfigurator setLoadAnnotationAxioms(boolean b) {
        loadAnnotations = b;
        return this;
    }

    /**
     * Sets the strategy that is used for missing imports handling. See
     * {@link MissingImportHandlingStrategy} for the strategies and their
     * descriptions.
     * 
     * @param strategy
     *        The strategy to be used.
     * @return An {@code OWLOntologyLoaderConfiguration} object with the
     *         strategy set.
     * @since 3.3
     */
    public OntologyConfigurator setMissingImportHandlingStrategy(MissingImportHandlingStrategy strategy) {
        missingImportHandlingStrategy = strategy;
        return this;
    }

    /**
     * @param strategy
     *        new value
     * @return a copy of this configuration object with a different strategy
     */
    public OntologyConfigurator setMissingOntologyHeaderStrategy(MissingOntologyHeaderStrategy strategy) {
        missingOntologyHeaderStrategy = strategy;
        return this;
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
    public OntologyConfigurator setReportStackTraces(boolean b) {
        reportStackTraces = b;
        return this;
    }

    /**
     * @param retries
     *        new value of retries to attempt
     * @return copy of this configuration with modified retries attempts.
     */
    public OntologyConfigurator setRetriesToAttempt(int retries) {
        retriesToAttempt = retries;
        return this;
    }

    /**
     * @param strict
     *        new value for strict
     * @return copy of the configuration with new strict value
     */
    public OntologyConfigurator setStrict(boolean strict) {
        parseWithStrictConfiguration = strict;
        return this;
    }

    /**
     * @param value
     *        true if Dublin Core vocabulary should be treated as built in.
     * @return a copy of the current object with treatDublinCoreAsBuiltIn set to
     *         the new value.
     */
    public OntologyConfigurator setTreatDublinCoreAsBuiltIn(boolean value) {
        treatDublinCoreAsBuiltIn = value;
        return this;
    }

    /**
     * @return a new OWLOntologyLoaderConfiguration from the builder current
     *         settings
     */
    public OWLOntologyLoaderConfiguration buildLoaderConfiguration() {
        return new OWLOntologyLoaderConfiguration().setAcceptingHTTPCompression(acceptHTTPCompression)
            .setConnectionTimeout(connectionTimeout).setFollowRedirects(followRedirects).setLoadAnnotationAxioms(
                loadAnnotations).setMissingImportHandlingStrategy(missingImportHandlingStrategy)
            .setMissingOntologyHeaderStrategy(missingOntologyHeaderStrategy).setPriorityCollectionSorting(
                priorityCollectionSorting).setReportStackTraces(reportStackTraces).setRetriesToAttempt(retriesToAttempt)
            .setStrict(parseWithStrictConfiguration).setTreatDublinCoreAsBuiltIn(treatDublinCoreAsBuiltIn);
    }

    /**
     * @param b
     *        True if ids for blank nodes should always be written (axioms and
     *        anonymous individuals only).
     * @return new config object
     */
    public OntologyConfigurator withSaveIdsForAllAnonymousIndividuals(boolean b) {
        saveIds = b;
        return this;
    }

    /**
     * @param b
     *        True if all anonymous individuals should have their ids remapped
     *        after parsing.
     * @return new config object
     */
    public OntologyConfigurator withRemapAllAnonymousIndividualsIds(boolean b) {
        remapIds = b;
        return this;
    }

    /**
     * @param useEntities
     *        True if entities should be used for namespace abbreviations.
     * @return new config object
     */
    public OntologyConfigurator withUseNamespaceEntities(boolean useEntities) {
        useNamespaceEntities = useEntities;
        return this;
    }

    /**
     * @param indent
     *        True if indenting should be used when writing out a file.
     * @return new config object
     */
    public OntologyConfigurator withIndenting(boolean indent) {
        indenting = indent;
        return this;
    }

    /**
     * @param indent
     *        Size of indentation between levels. Only used if indenting is set
     *        to true.
     * @return new config object
     */
    public OntologyConfigurator withIndentSize(int indent) {
        indentSize = indent;
        return this;
    }

    /**
     * @param label
     *        True if {@code rdfs:labels} should be used for banner comments.
     * @return new config object
     */
    public OntologyConfigurator withLabelsAsBanner(boolean label) {
        labelsAsBanner = label;
        return this;
    }

    /**
     * @return a new OWLOntologyWriterConfiguration from the builder current
     *         settings
     */
    public OWLOntologyWriterConfiguration buildWriterConfiguration() {
        return new OWLOntologyWriterConfiguration().withIndenting(indenting).withIndentSize(indentSize)
            .withLabelsAsBanner(labelsAsBanner).withRemapAllAnonymousIndividualsIds(remapIds)
            .withSaveIdsForAllAnonymousIndividuals(saveIds).withUseNamespaceEntities(useNamespaceEntities);
    }
}
