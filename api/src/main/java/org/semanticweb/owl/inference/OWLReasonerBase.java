package org.semanticweb.owl.inference;

import org.semanticweb.owl.model.*;

import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Oct-2006<br><br>
 *
 * The base interface for OWL reasoner.  This interface allows the
 * ontology that should be reasoned over to be set.
 */
public interface OWLReasonerBase {

    /**
     * Loads the specified ontologies.  The reasoner will then
     * take into consideration the logical axioms in each ontology.
     * Note that this methods does <b>not<b> load any ontologies
     * in the imports closure - <i>all</i> imports must be loaded
     * explicitly.
     * @param ontologies The ontolgies to be loaded.
     */
    void loadOntologies(Set<OWLOntology> ontologies) throws OWLReasonerException;


    /**
     * Determines if the reasoner has classified the ontology.
     * @return <code>true</code> if the ontology has been classified,
     * or <code>false</code> if the ontology has not been classified.
     */
    boolean isClassified() throws OWLReasonerException;


    /**
     * Asks the reasoner to classify the ontology.  If the ontology
     * has been classified then this method may not have any effect.
     */
    void classify() throws OWLReasonerException;


    /**
     * Determines if the types of individuals have been computed by this
     * reasoner (if supported).
     * @return <code>true</code> is the types of individuals have been computed,
     * otherwise <code>false</code>.
     * @throws OWLReasonerException
     */
    boolean isRealised() throws OWLReasonerException;


    /**
     * Asks the reasoner to compute the types of individuals (if supported).  If
     * realisation is not supported then this method will have no effect.
     * @throws OWLReasonerException
     */
    void realise() throws OWLReasonerException;

    /**
     * Determines if the specified class is defined in the reasoner.
     * If a class is defined then the reasoner "knows" about it, if
     * a class is not defined then the reasoner doesn't know about it.
     * @param cls The class to check for.
     * @return <code>true</code> if the class is defined in the
     * reasoner, or <code>false</code> if the class is not defined
     * in the reasoner.
     * @throws OWLReasonerException
     */
    boolean isDefined(OWLClass cls) throws OWLReasonerException;

    /**
     * Determines if the specified property is defined in the reasoner.
     * If a property is defined then the reasoner "knows" about it, if
     * a property is not defined then the reasoner doesn't know about it.
     * @param prop The property to check for.
     * @return <code>true</code> if the property is defined in the
     * reasoner, or <code>false</code> if the property is not defined
     * in the reasoner.
     * @throws OWLReasonerException
     */
    boolean isDefined(OWLObjectProperty prop) throws OWLReasonerException;

    /**
     * Determines if the specified property is defined in the reasoner.
     * If a property is defined then the reasoner "knows" about it, if
     * a property is not defined then the reasoner doesn't know about it.
     * @param prop The property to check for.
     * @return <code>true</code> if the property is defined in the
     * reasoner, or <code>false</code> if the property is not defined
     * in the reasoner.
     * @throws OWLReasonerException
     */
    boolean isDefined(OWLDataProperty prop) throws OWLReasonerException;


    /**
     * Determines if the specified individual is defined in the reasoner.
     * If an individual is defined then the reasoner "knows" about it, if
     * a individual is not defined then the reasoner doesn't know about it.
     * @param ind The individual to check for.
     * @return <code>true</code> if the individual is defined in the
     * reasoner, or <code>false</code> if the individual is not defined
     * in the reasoner.
     * @throws OWLReasonerException
     */
    boolean isDefined(OWLIndividual ind) throws OWLReasonerException;

    /**
     * Gets the ontologies that have been loaded into this reasoner.
     */
    Set<OWLOntology> getLoadedOntologies();


    /**
     * Unloads the specified ontologies from this reasoner.
     * @param ontologies The ontologies to be unloaded.
     */
    void unloadOntologies(Set<OWLOntology> ontologies) throws OWLReasonerException;


    /**
     * Clears the reasoner.  All loaded ontologies will be unloaded.
     */
    void clearOntologies() throws OWLReasonerException;


    /**
     * Disposes of and cleans up any resources used by this reasoner.
     */
    void dispose() throws OWLReasonerException;

}
