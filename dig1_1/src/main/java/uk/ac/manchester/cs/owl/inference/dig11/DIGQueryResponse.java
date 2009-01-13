package uk.ac.manchester.cs.owl.inference.dig11;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;

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
 * Date: 21-Nov-2006<br><br>
 */
public interface DIGQueryResponse {

    /**
     * Gets the ID of the query that the reponse corresponds to
     */
    public String getID();


    /**
     * If the query resulted in a concept set response type
     * for example a query for super concepts then
     * this method can be used to get the concepts.
     * @return A <code>Set</code> of <code>OWLClass</code>s
     */
    public Set<Set<OWLClass>> getConceptSets() throws DIGReasonerException;


    /**
     * If the query resulted in an object property set response type
     * for example a query for super properties then
     * this method can be used to get the concepts.
     * @return A <code>Set</code> of <code>OWLObjectProperty</code>s
     */
    public Set<Set<OWLObjectProperty>> getRoleSets() throws DIGReasonerException;


    /**
     * If the query resulted in an individual set response type
     * then this method may be used to obtain the individuals
     * in the response.
     * @return A <code>Set</code> of <code>OWLIndividual</code>s
     */
    public Set<OWLIndividual> getIndividuals() throws DIGReasonerException;


    /**
     * If the query resultied in a boolean response,
     * for example asking if a concept was satisfiable, then
     * this method may be used to get the boolean result.
     */
    public boolean getBoolean();
}
