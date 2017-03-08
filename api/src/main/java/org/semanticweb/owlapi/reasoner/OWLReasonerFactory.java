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
package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * An OWLReasonerFactory is a point for creating instances of
 * {@link org.semanticweb.owlapi.reasoner.OWLReasoner} objects. A reasoner reasons over a set of
 * ontologies that is defined by an ontology and its imports closure.
 *
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @see org.semanticweb.owlapi.reasoner.OWLReasoner
 * @since 3.0.0
 */
public interface OWLReasonerFactory {

    /**
     * Gets the name of the reasoner created by this factory.
     *
     * @return A string that represents the name of the reasoner created by this factory.
     */
    String getReasonerName();

    /**
     * Creates an OWLReasoner that reasons over the imports closure of the specified ontology. The
     * reasoner will obtain the imports closure from the ontology manager associated with
     * {@code ontology}. The reasoner will listen for ontology changes to the ontologies that it is
     * reasoning over and will always answer queries with respect to the changed ontologies (see
     * {@link org.semanticweb.owlapi.reasoner.OWLReasoner} for further details).
     *
     * @param ontology The ontology whose imports closure will be reasoned over by the reasoner that
     * is created.
     * @return The reasoner that reasons over the imports closure of the specified ontology. Note
     * that calling this method multiple times with the same manager and ontology will return
     * <b>fresh</b> instances of OWLReasoner.
     * @throws NullPointerException if the {@code manager} or {@code ontology} are {@code null}.
     */
    OWLReasoner createNonBufferingReasoner(OWLOntology ontology);

    /**
     * Creates a buffering reasoner that reasons over the imports closure of the specified ontology.
     * The reasoner will obtain the imports closure from the ontology manager associated with
     * {@code ontology}. The reasoner will listen for ontology changes to the ontologies it is
     * reasoning over but will only answer queries with respect to the changed ontologies when the
     * {@link OWLReasoner#flush()} method is called (see
     * {@link org.semanticweb.owlapi.reasoner.OWLReasoner} for further details).
     *
     * @param ontology The ontology whose imports closure will be reasoned over by the reasoner that
     * is created.
     * @return The reasoner that reasons over the imports closure of the specified ontology. Note
     * that calling this method multiple times with the same manager and ontology will return
     * <b>fresh</b> instances of OWLReasoner.
     * @throws NullPointerException if the {@code manager} or {@code ontology} are {@code null}.
     */
    OWLReasoner createReasoner(OWLOntology ontology);

    /**
     * Creates an OWLReasoner that reasons over the imports closure of the specified ontology. The
     * reasoner will obtain the imports closure from the ontology manager associated with
     * {@code ontology}. The reasoner will listen for ontology changes to the ontologies that it is
     * reasoning over and will always answer queries with respect to the changed ontologies (see
     * {@link org.semanticweb.owlapi.reasoner.OWLReasoner} for further details).
     *
     * @param ontology The ontology whose imports closure will be reasoned over by the reasoner that
     * is created.
     * @param config A configuration object that can be used to customise the setup of the reasoner
     * that will be created by calling this method.
     * @return The reasoner that reasons over the imports closure of the specified ontology. Note
     * that calling this method multiple times with the same manager and ontology will return
     * <b>fresh</b> instances of OWLReasoner.
     * @throws IllegalConfigurationException if the configuration object is inappropriate for
     * reasoners created by this factory, or if there is an illegal setting on the configuration.
     * @throws NullPointerException if any of {@code manager}, {@code ontology} or {@code config}
     * are {@code null}.
     */
    OWLReasoner createNonBufferingReasoner(OWLOntology ontology, OWLReasonerConfiguration config);

    /**
     * Creates a buffering reasoner that reasons over the imports closure of the specified ontology.
     * The reasoner will obtain the imports closure from the ontology manager associated with
     * {@code ontology}. The reasoner will listen for ontology changes to the ontologies it is
     * reasoning over but will only answer queries with respect to the changed ontologies when the
     * {@link OWLReasoner#flush()} method is called (see
     * {@link org.semanticweb.owlapi.reasoner.OWLReasoner} for further details).
     *
     * @param ontology The ontology whose imports closure will be reasoned over by the reasoner that
     * is created.
     * @param config A configuration object that can be used to customise the setup of the reasoner
     * that will be created by calling this method.
     * @return The reasoner that reasons over the imports closure of the specified ontology. Note
     * that calling this method multiple times with the same manager and ontology will return
     * <b>fresh</b> instances of OWLReasoner.
     * @throws IllegalConfigurationException if the configuration object is inappropriate for
     * reasoners created by this factory, or if there is an illegal setting on the configuration.
     * @throws NullPointerException if any of {@code manager}, {@code ontology} or {@code config}
     * are {@code null}.
     */
    OWLReasoner createReasoner(OWLOntology ontology, OWLReasonerConfiguration config);
}
