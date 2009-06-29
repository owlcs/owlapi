package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;

import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 27-Apr-2007<br><br>
 * <p/>
 * An <code>OWLOntologySetProvider</code> which provides a set of ontologies
 * which correspond to the imports closure of a given ontology.  Note that
 * the set of provided ontologies will be updated if the imports closure gets
 * updated.
 */
public class OWLOntologyImportsClosureSetProvider implements OWLOntologySetProvider {

    private OWLOntologyManager manager;

    private OWLOntology rootOntology;


    /**
     * Constructs an <code>OWLOntologySetProvider</code> which provides a set containing the imports
     * closure of a given ontology.
     * @param manager      The manager which should be used to determine the imports closure.
     * @param rootOntology The ontology which is the "root" of the imports closure.
     */
    public OWLOntologyImportsClosureSetProvider(OWLOntologyManager manager, OWLOntology rootOntology) {
        this.manager = manager;
        this.rootOntology = rootOntology;
    }


    public Set<OWLOntology> getOntologies() {
        return manager.getImportsClosure(rootOntology);
    }
}
