package org.semanticweb.owl.io;

import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyFormat;
import org.semanticweb.owl.model.OWLOntologyManager;

import java.net.URI;
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
 * Date: 14-Nov-2006<br><br>
 */
public interface OWLParser {

    /**
     * Sets the <code>OWLOntologyManager</code> which should be used to load
     * imports etc.
     * @param owlOntologyManager
     */
    void setOWLOntologyManager(OWLOntologyManager owlOntologyManager);

    /**
     * Parses the ontology that has a concrete representation which is pointed
     * to by the specified physical URI.  Implementors of this method should
     * load any imported ontologies with the loadImports method on OWLOntologyManager.
     * @param physicalURI
     * @param ontology The ontology that the concrete representation should be
     * parsed into.
     * @return An <code>OWLOntologyFormat</code> which describes the concrete representation
     * format which was parsed to obtain the ontology. This will never be <code>null</code>.
     * @throws OWLParserException if there was a problem parsing the ontology.
     */
    OWLOntologyFormat parse(URI physicalURI, OWLOntology ontology) throws OWLOntologyCreationException;


    /**
     * Parses the ontology that has a concrete representation which is pointed to
     * by the specified input source. Implementors of this method should
     * load any imported ontologies with the makeImportsLoadRequest method on OWLOntologyManager.
     * @param inputSource The input source which points the concrete representation.  If
     * the input source can provider a <code>Reader</code> then the ontology is parsed
     * from the <code>Reader</code>.  If the input source cannot provide a reader then
     * it is parsed from the <code>InputStream</code>.  If the input source cannot provide
     * an <code>InputStream</code> then it is parsed from the physical URI.
     * @param ontology The ontology which the representation will be parsed into
     * @return An <code>OWLOntologyFormat</code> which describes the concrete representation
     * format which was parsed to obtain the ontology.
     * @throws OWLParserException
     */
    OWLOntologyFormat parse(OWLOntologyInputSource inputSource, OWLOntology ontology) throws OWLOntologyCreationException;
}
