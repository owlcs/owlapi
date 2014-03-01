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

import java.util.HashSet;
import java.util.Set;

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
 *         Informatics Group, Date: 15/12/2010
 */
public final class OWLOntologyLoaderConfiguration {

    /** strategies to cope with missing headers. */
    public enum MissingOntologyHeaderStrategy {
        /** include triples. */
        INCLUDE_GRAPH,
        /** keep import structure. */
        IMPORT_GRAPH
    }

    /** default annotation value. */
    public static final boolean DEFAULT_LOAD_ANNOTATIONS_FLAG_VALUE = true;
    /** default missing ontology strategy. */
    public static final MissingOntologyHeaderStrategy DEFAULT_MISSING_ONTOLOGY_HEADER_STRATEGY = MissingOntologyHeaderStrategy.INCLUDE_GRAPH;
    private boolean loadAnnotations = DEFAULT_LOAD_ANNOTATIONS_FLAG_VALUE;
    private MissingOntologyHeaderStrategy missingOntologyHeaderStrategy = DEFAULT_MISSING_ONTOLOGY_HEADER_STRATEGY;
    private boolean strict = false;
    /** flag to enable stack traces on parsing exceptions. */
    private boolean reportStackTraces = false;
    private boolean followRedirects = true;
    private MissingImportHandlingStrategy missingImportHandlingStrategy = MissingImportHandlingStrategy.THROW_EXCEPTION;
    private final Set<IRI> ignoredImports = new HashSet<IRI>();

    /** default constructor */
    public OWLOntologyLoaderConfiguration() {
        ignoredImports.add(IRI.create(stripHash(Namespaces.OWL.toString())));
        ignoredImports.add(IRI.create(stripHash(Namespaces.RDF.toString())));
        ignoredImports.add(IRI.create(stripHash(Namespaces.RDFS.toString())));
        ignoredImports.add(IRI.create(stripHash(Namespaces.SWRL.toString())));
        ignoredImports.add(IRI.create(stripHash(Namespaces.SWRLB.toString())));
        ignoredImports.add(IRI.create(Namespaces.XML.toString()));
        ignoredImports.add(IRI.create(Namespaces.XSD.toString()));
    }

    /**
     * Removes a trailing # character (if one exists) from a string.
     * 
     * @param s
     *        The string, possibly ending with a # character.
     * @return The string s minus the # character.
     */
    private static String stripHash(String s) {
        if (!s.endsWith("#")) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    /** @return the ontology header strategy */
    public MissingOntologyHeaderStrategy getMissingOntologyHeaderStrategy() {
        return missingOntologyHeaderStrategy;
    }

    /**
     * @param missingOntologyHeaderStrategy
     *        new value
     * @return a copy of this configuration object with a different strategy
     */
    public OWLOntologyLoaderConfiguration setMissingOntologyHeaderStrategy(
            MissingOntologyHeaderStrategy missingOntologyHeaderStrategy) {
        // do not make copies if setting the same value
        if (missingOntologyHeaderStrategy == this.missingOntologyHeaderStrategy) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.missingOntologyHeaderStrategy = missingOntologyHeaderStrategy;
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
     * @param value
     *        true if redirects should be followed across protocols, false
     *        otherwise.
     * @return a copy of the current object with followRedirects set to the new
     *         value.
     */
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
    public OWLOntologyLoaderConfiguration setMissingImportHandlingStrategy(
            MissingImportHandlingStrategy missingImportHandlingStrategy) {
        // do not make copies if setting the same value
        if (this.missingImportHandlingStrategy == missingImportHandlingStrategy) {
            return this;
        }
        OWLOntologyLoaderConfiguration copy = copyConfiguration();
        copy.missingImportHandlingStrategy = missingImportHandlingStrategy;
        return copy;
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

    /**
     * @param b
     *        new value for missing imports
     * @deprecated Do not use because this method mutates the
     *             OWLOntologyLoaderConfiguration instance that it is called on.
     *             Use
     *             {@link #setMissingImportHandlingStrategy(MissingImportHandlingStrategy)}
     * @since 3.2.4
     */
    @Deprecated
    public void setSilentMissingImportsHandling(boolean b) {
        if (b) {
            missingImportHandlingStrategy = MissingImportHandlingStrategy.SILENT;
        } else {
            missingImportHandlingStrategy = MissingImportHandlingStrategy.THROW_EXCEPTION;
        }
    }

    /**
     * @return true if a missing import does not raise an error.
     * @deprecated Use {@link #getMissingImportHandlingStrategy()}.
     * @since 3.2.4
     */
    @Deprecated
    public boolean isSilentMissingImportsHandling() {
        return missingImportHandlingStrategy == MissingImportHandlingStrategy.SILENT;
    }

    /** @return true if parsing should be strict */
    public boolean isStrict() {
        return strict;
    }

    /**
     * @param strict
     *        new value for strict
     * @return copy of the configuration with new strict value
     */
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
     * @param iri
     *        iri to check
     * @return true if iri should be ignored
     */
    public boolean isIgnoredImport(IRI iri) {
        return ignoredImports.contains(iri);
    }

    /**
     * Gets the list of ontology document IRIs that are ignored during ontology
     * loading if they are encountered as imported ontologies.
     * 
     * @return A set of IRIs that represent ontology document IRI to be ignored
     *         during ontology loading.
     */
    public Set<IRI> getIgnoredImports() {
        return new HashSet<IRI>(ignoredImports);
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
     * Removes an ontology document IRI from the list of ontology imports that
     * will be ignored during ontology loading.
     * 
     * @param ontologyDocumentIRI
     *        The ontology document IRI that would be ignored if it is
     *        encountered as an imported ontology during loading.
     * @return An {@code OWLOntologyLoaderConfiguration} with the ignored
     *         ontology document IRI removed.
     */
    public OWLOntologyLoaderConfiguration removeIgnoredImport(
            IRI ontologyDocumentIRI) {
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.ignoredImports.remove(ontologyDocumentIRI);
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
     * Set the value for the report stack traces flag. If true, parsing
     * exceptions will have the full stack trace for the source exceptions.
     * Default is false.
     * 
     * @param b
     *        the new value for the flag
     * @return A {@code OWLOntologyLoaderConfiguration} with the report flag set
     *         to the new value.
     */
    public OWLOntologyLoaderConfiguration setReportStackTraces(boolean b) {
        if (b == reportStackTraces) {
            return this;
        }
        OWLOntologyLoaderConfiguration configuration = copyConfiguration();
        configuration.reportStackTraces = b;
        return configuration;
    }

    /** @return value for the report stack trace flag. */
    public boolean isReportStackTrace() {
        return reportStackTraces;
    }

    /**
     * Internally copies this configuaration object.
     * 
     * @return The copied configuration
     */
    private OWLOntologyLoaderConfiguration copyConfiguration() {
        OWLOntologyLoaderConfiguration copy = new OWLOntologyLoaderConfiguration();
        copy.loadAnnotations = loadAnnotations;
        copy.ignoredImports.clear();
        copy.ignoredImports.addAll(ignoredImports);
        copy.strict = strict;
        copy.missingImportHandlingStrategy = missingImportHandlingStrategy;
        copy.missingOntologyHeaderStrategy = missingOntologyHeaderStrategy;
        copy.followRedirects = followRedirects;
        copy.reportStackTraces = reportStackTraces;
        return copy;
    }
}
