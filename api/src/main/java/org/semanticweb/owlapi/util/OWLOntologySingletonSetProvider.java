package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;

import java.util.Collections;
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
 * An ontology set provider which provides a singleton set - i.e. a set containing
 * just one ontology.
 */
public class OWLOntologySingletonSetProvider implements OWLOntologySetProvider {

    private Set<OWLOntology> ontologySingletonSet;


    /**
     * Constructs an <code>OWLOntologySingletonSetProvider</code> which provides a singleton
     * set contain the specified ontology.
     * @param ontology The one and only ontology which should be contained in the sets provided
     *                 by this provider.
     */
    public OWLOntologySingletonSetProvider(OWLOntology ontology) {
        ontologySingletonSet = Collections.singleton(ontology);
    }


    public Set<OWLOntology> getOntologies() {
        return ontologySingletonSet;
    }
}
