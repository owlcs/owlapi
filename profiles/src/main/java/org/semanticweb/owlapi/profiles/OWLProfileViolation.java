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

package org.semanticweb.owlapi.profiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 *
 * Describes a violation of an OWLProfile by one or more axioms.  Ultimately, there may
 * be part of an axiom(s) that violates the profile rather than the complete axiom.
 */
public class OWLProfileViolation {

    private OWLOntologyID ontologyID;

    private Map<OWLOntologyID, IRI> importsClosureMap = new HashMap<OWLOntologyID, IRI>();

    private OWLAxiom axiom;

    public OWLProfileViolation(OWLOntology ontology, OWLAxiom axiom) {
        this.axiom = axiom;
        this.ontologyID = ontology.getOntologyID();
        for(OWLOntology ont : ontology.getImportsClosure()) {
            importsClosureMap.put(ont.getOntologyID(), ont.getOWLOntologyManager().getOntologyDocumentIRI(ont));
        }
    }

    public OWLAxiom getAxiom() {
        return axiom;
    }

    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    public IRI getDocumentIRI(OWLOntologyID ontologyID) {
        return importsClosureMap.get(ontologyID);
    }

    public Set<OWLOntologyID> getImportsClosure() {
        return importsClosureMap.keySet();
    }

}
