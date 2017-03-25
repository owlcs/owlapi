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

import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.ADD_MISSING_TYPES;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.BANNED_PARSERS;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.BANNERS_ENABLED;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.CONNECTION_TIMEOUT;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.ENTITY_EXPANSION_LIMIT;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.INDENTING;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.INDENT_SIZE;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.LABELS_AS_BANNER;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.LOAD_ANNOTATIONS;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.MISSING_IMPORT_HANDLING_STRATEGY;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.MISSING_ONTOLOGY_HEADER_STRATEGY;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.PARSE_WITH_STRICT_CONFIGURATION;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.PRIORITY_COLLECTION_SORTING;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.REMAP_IDS;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.REPORT_STACK_TRACES;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.RETRIES_TO_ATTEMPT;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.SAVE_IDS;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.TREAT_DUBLINCORE_AS_BUILTIN;
import static org.semanticweb.owlapi.model.parameters.ConfigurationOptions.USE_NAMESPACE_ENTITIES;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.parameters.ConfigurationOptions;

/**
 * A configuration builder that specifies all available options in the OWL API. Can be used to build
 * OWLOntologyLoaderConfiguration and OWLOntologyWriterConfiguration objects
 *
 * @author Ignazio
 * @since 5.0.0
 */
public class OntologyConfigurator implements Serializable {

    /** Set of imports to ignore. */
    private final Set<IRI> ignoredImports = new HashSet<>();
    /** Local override map. */
    private EnumMap<ConfigurationOptions, Object> overrides =
        new EnumMap<>(ConfigurationOptions.class);

    /**
     * @param expansion entity expansion limit.
     * @return An {@code OntologyConfigurator} with the new option set.
     */
    public OntologyConfigurator withEntityExpansionLimit(long expansion) {
        overrides.put(ENTITY_EXPANSION_LIMIT, Long.valueOf(expansion));
        return this;
    }

    /**
     * @return entity expansion limit.
     */
    public String getEntityExpansionLimit() {
        return ENTITY_EXPANSION_LIMIT.getValue(Long.class, overrides).toString();
    }

    /**
     * @param ban list of parser factory class names that should be skipped when attempting ontology
     *        parsing. The list is space separated.
     * @return An {@code OntologyConfigurator} with the new option set.
     */
    public OntologyConfigurator withBannedParsers(String ban) {
        overrides.put(BANNED_PARSERS, ban);
        return this;
    }

    /**
     * @return list of parser factory class names that should be skipped when attempting ontology
     *         parsing. The list is space separated.
     */
    public String getBannedParsers() {
        return BANNED_PARSERS.getValue(String.class, overrides);
    }

    /** @return the priorty collection sorting option */
    public PriorityCollectionSorting getPriorityCollectionSorting() {
        return PRIORITY_COLLECTION_SORTING.getValue(PriorityCollectionSorting.class, overrides);
    }

    /**
     * Set the priorty collection sorting option.
     *
     * @param sorting the sorting option to be used.
     * @return An {@code OntologyConfigurator} with the new sorting option set.
     */
    public OntologyConfigurator setPriorityCollectionSorting(PriorityCollectionSorting sorting) {
        overrides.put(PRIORITY_COLLECTION_SORTING, sorting);
        return this;
    }

    /**
     * @param iri import to check.
     * @return true if the import is ignored.
     */
    public boolean isImportIgnored(IRI iri) {
        return ignoredImports.contains(iri);
    }

    /**
     * Adds an ontology document IRI to the list of ontology imports that will be ignored during
     * ontology loading.
     *
     * @param ontologyDocumentIRI The ontology document IRI that will be ignored if it is
     *        encountered as an imported ontology during loading.
     * @return An {@code OWLOntologyLoaderConfiguration} with the ignored ontology document IRI set.
     */
    public OntologyConfigurator addIgnoredImport(IRI ontologyDocumentIRI) {
        ignoredImports.add(ontologyDocumentIRI);
        return this;
    }

    /**
     * Clears all ontology document IRIs from the list of ignored ontology document IRIs.
     *
     * @return An {@code OWLOntologyLoaderConfiguration} with the list of ignored ontology document
     *         IRIs set to be empty.
     */
    public OntologyConfigurator clearIgnoredImports() {
        ignoredImports.clear();
        return this;
    }

    /**
     * Removes an ontology document IRI from the list of ontology imports that will be ignored
     * during ontology loading.
     *
     * @param ontologyDocumentIRI The ontology document IRI that would be ignored if it is
     *        encountered as an imported ontology during loading.
     * @return An {@code OWLOntologyLoaderConfiguration} with the ignored ontology document IRI
     *         removed.
     */
    public OntologyConfigurator removeIgnoredImport(IRI ontologyDocumentIRI) {
        ignoredImports.remove(ontologyDocumentIRI);
        return this;
    }

    /** @return the connection timeout */
    public int getConnectionTimeout() {
        return CONNECTION_TIMEOUT.getValue(Integer.class, overrides).intValue();
    }

    /**
     * @param l new timeout Note: the timeout is an int and represents milliseconds.
     * @return A {@code OWLOntologyLoaderConfiguration} with the connection timeout set to the new
     *         value.
     */
    public OntologyConfigurator setConnectionTimeout(int l) {
        overrides.put(CONNECTION_TIMEOUT, Integer.valueOf(l));
        return this;
    }

    /**
     * Specifies whether or not annotation axioms (instances of {@code OWLAnnotationAxiom}) should
     * be loaded or whether they should be discarded on loading. By default, the loading of
     * annotation axioms is enabled.
     *
     * @param b {@code true} if annotation axioms should be loaded, or {@code false} if annotation
     *        axioms should not be loaded and should be discarded on loading.
     * @return An {@code OWLOntologyLoaderConfiguration} object with the option set.
     */
    public OntologyConfigurator setLoadAnnotationAxioms(boolean b) {
        overrides.put(LOAD_ANNOTATIONS, Boolean.valueOf(b));
        return this;
    }

    /** @return load annotations */
    public boolean shouldLoadAnnotations() {
        return LOAD_ANNOTATIONS.getValue(Boolean.class, overrides).booleanValue();
    }

    /** @return missing import handling strategy */
    public MissingImportHandlingStrategy getMissingImportHandlingStrategy() {
        return MISSING_IMPORT_HANDLING_STRATEGY.getValue(MissingImportHandlingStrategy.class,
            overrides);
    }

    /**
     * Sets the strategy that is used for missing imports handling. See
     * {@link MissingImportHandlingStrategy} for the strategies and their descriptions.
     *
     * @param strategy The strategy to be used.
     * @return An {@code OWLOntologyLoaderConfiguration} object with the strategy set.
     * @since 3.3
     */
    public OntologyConfigurator setMissingImportHandlingStrategy(
        MissingImportHandlingStrategy strategy) {
        overrides.put(MISSING_IMPORT_HANDLING_STRATEGY, strategy);
        return this;
    }

    /** @return missing ontology header strategy */
    public MissingOntologyHeaderStrategy getMissingOntologyHeaderStrategy() {
        return MISSING_ONTOLOGY_HEADER_STRATEGY.getValue(MissingOntologyHeaderStrategy.class,
            overrides);
    }

    /**
     * @param strategy new value
     * @return a copy of this configuration object with a different strategy
     */
    public OntologyConfigurator setMissingOntologyHeaderStrategy(
        MissingOntologyHeaderStrategy strategy) {
        overrides.put(MISSING_ONTOLOGY_HEADER_STRATEGY, strategy);
        return this;
    }

    /**
     * Set the value for the report stack traces flag. If true, parsing exceptions will have the
     * full stack trace for the source exceptions. Default is false.
     *
     * @param b the new value for the flag
     * @return A {@code OWLOntologyLoaderConfiguration} with the report flag set to the new value.
     */
    public OntologyConfigurator setReportStackTraces(boolean b) {
        overrides.put(REPORT_STACK_TRACES, Boolean.valueOf(b));
        return this;
    }

    /** @return report stack traces */
    public boolean shouldReportStackTraces() {
        return REPORT_STACK_TRACES.getValue(Boolean.class, overrides).booleanValue();
    }

    /** @return value of retries to attempt */
    public int getRetriesToAttempt() {
        return RETRIES_TO_ATTEMPT.getValue(Integer.class, overrides).intValue();
    }

    /**
     * @param retries new value of retries to attempt
     * @return copy of this configuration with modified retries attempts.
     */
    public OntologyConfigurator setRetriesToAttempt(int retries) {
        overrides.put(RETRIES_TO_ATTEMPT, Integer.valueOf(retries));
        return this;
    }

    /**
     * @param strict new value for strict
     * @return copy of the configuration with new strict value
     */
    public OntologyConfigurator setStrict(boolean strict) {
        overrides.put(PARSE_WITH_STRICT_CONFIGURATION, Boolean.valueOf(strict));
        return this;
    }

    /** @return true if parsing should be strict */
    public boolean shouldParseWithStrictConfiguration() {
        return PARSE_WITH_STRICT_CONFIGURATION.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param value true if Dublin Core vocabulary should be treated as built in.
     * @return a copy of the current object with treatDublinCoreAsBuiltIn set to the new value.
     */
    public OntologyConfigurator setTreatDublinCoreAsBuiltIn(boolean value) {
        overrides.put(TREAT_DUBLINCORE_AS_BUILTIN, Boolean.valueOf(value));
        return this;
    }

    /** @return true if Dublin Core vocabulary should be treated as built in. */
    public boolean shouldTreatDublinCoreAsBuiltin() {
        return TREAT_DUBLINCORE_AS_BUILTIN.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param b True if ids for blank nodes should always be written (axioms and anonymous
     *        individuals only).
     * @return new config object
     */
    public OntologyConfigurator withSaveIdsForAllAnonymousIndividuals(boolean b) {
        overrides.put(SAVE_IDS, Boolean.valueOf(b));
        return this;
    }

    /**
     * @return should save ids
     */
    public boolean shouldSaveIds() {
        return SAVE_IDS.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param b True if all anonymous individuals should have their ids remapped after parsing.
     * @return new config object
     */
    public OntologyConfigurator withRemapAllAnonymousIndividualsIds(boolean b) {
        overrides.put(REMAP_IDS, Boolean.valueOf(b));
        return this;
    }

    /**
     * @return should remap ids
     */
    public boolean shouldRemapIds() {
        return REMAP_IDS.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param useEntities True if entities should be used for namespace abbreviations.
     * @return new config object
     */
    public OntologyConfigurator withUseNamespaceEntities(boolean useEntities) {
        overrides.put(USE_NAMESPACE_ENTITIES, Boolean.valueOf(useEntities));
        return this;
    }

    /**
     * @return should use namespace entities
     */
    public boolean shouldUseNamespaceEntities() {
        return USE_NAMESPACE_ENTITIES.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param indent True if indenting should be used when writing out a file.
     * @return new config object
     */
    public OntologyConfigurator withIndenting(boolean indent) {
        overrides.put(INDENTING, Boolean.valueOf(indent));
        return this;
    }

    /**
     * @return should indent
     */
    public boolean shouldIndent() {
        return INDENTING.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param indent Size of indentation between levels. Only used if indenting is set to true.
     * @return new config object
     */
    public OntologyConfigurator withIndentSize(int indent) {
        overrides.put(INDENT_SIZE, Integer.valueOf(indent));
        return this;
    }

    /**
     * @return indent size
     */
    public int getIndentSize() {
        return INDENT_SIZE.getValue(Integer.class, overrides).intValue();
    }

    /**
     * @param label True if {@code rdfs:labels} should be used for banner comments.
     * @return new config object
     */
    public OntologyConfigurator withLabelsAsBanner(boolean label) {
        overrides.put(LABELS_AS_BANNER, Boolean.valueOf(label));
        return this;
    }

    /**
     * @return should use labels as banner
     */
    public boolean shouldUseLabelsAsBanner() {
        return LABELS_AS_BANNER.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param label True if banner comments should be enabled.
     * @return new config object
     */
    public OntologyConfigurator withBannersEnabled(boolean label) {
        overrides.put(BANNERS_ENABLED, Boolean.valueOf(label));
        return this;
    }

    /**
     * @return should output banners
     */
    public boolean shouldUseBanners() {
        return BANNERS_ENABLED.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * Determines if untyped entities should automatically be typed (declared) during rendering.
     * (This is a hint to an RDF renderer - the reference implementation will respect this).
     *
     * @return {@code true} if untyped entities should automatically be typed during rendering,
     *         otherwise {@code false}.
     */
    public boolean shouldAddMissingTypes() {
        return ADD_MISSING_TYPES.getValue(Boolean.class, overrides).booleanValue();
    }

    /**
     * @param addMissing true if untyped entities should automatically be typed (declared) during
     *        rendering. (This is a hint to an RDF renderer - the reference implementation will
     *        respect this).
     *
     * @return new config object
     */
    public OntologyConfigurator withAddMissingTypes(boolean addMissing) {
        overrides.put(ADD_MISSING_TYPES, Boolean.valueOf(addMissing));
        return this;
    }
}
