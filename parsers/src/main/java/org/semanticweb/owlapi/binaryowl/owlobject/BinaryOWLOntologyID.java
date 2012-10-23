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
 * Copyright 2011, The University of Manchester
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

package org.semanticweb.owlapi.binaryowl.owlobject;

import org.semanticweb.owlapi.binaryowl.BinaryOWLParseException;
import org.semanticweb.owlapi.binaryowl.lookup.LookupTable;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyID;

import java.io.*;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 30/04/2012
 */
public class BinaryOWLOntologyID {

    private static final int ANONYMOUS_ONTOLOGY_MARKER = 0;
    
    private static final int ONTOLOGY_IRI_NO_VERSION_IRI_MARKER = 1;
    
    private static final int ONTOLOGY_IRI_PLUS_VERSION_IRI_MARKER = 2;
    
    
    private OWLOntologyID ontologyID;

    public BinaryOWLOntologyID(OWLOntologyID ontologyID) {
        this.ontologyID = ontologyID;
    }
    
    public BinaryOWLOntologyID(DataInput dataInput, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        read(dataInput, dataFactory);
    }

    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    public void write(DataOutput dataOutput) throws IOException {
        if(ontologyID.isAnonymous()) {
            dataOutput.writeByte(ANONYMOUS_ONTOLOGY_MARKER);
        }
        else {
            if(ontologyID.getVersionIRI() == null) {
                dataOutput.writeByte(ONTOLOGY_IRI_NO_VERSION_IRI_MARKER);
                IRISerializer iri = new IRISerializer();
                iri.write(ontologyID.getOntologyIRI(), LookupTable.emptyLookupTable(), dataOutput);
            }
            else {
                dataOutput.writeByte(ONTOLOGY_IRI_PLUS_VERSION_IRI_MARKER);
                IRISerializer iri = new IRISerializer();
                iri.write(ontologyID.getOntologyIRI(), LookupTable.emptyLookupTable(), dataOutput);
                iri.write(ontologyID.getVersionIRI(), LookupTable.emptyLookupTable(), dataOutput);
            }
        }
    }
    
    private void read(DataInput dataInput, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        byte marker = dataInput.readByte();
        if(marker == ANONYMOUS_ONTOLOGY_MARKER) {
            ontologyID = new OWLOntologyID();
        }
        else {
            if(marker == 1) {
                IRISerializer serializer = new IRISerializer();
                IRI ontologyIRI = serializer.readObject(LookupTable.emptyLookupTable(), dataInput, dataFactory);
                ontologyID = new OWLOntologyID(ontologyIRI);
            }
            else if(marker == 2) {
                IRISerializer serializer = new IRISerializer();
                IRI ontologyIRI = serializer.readObject(LookupTable.emptyLookupTable(), dataInput, dataFactory);
                IRI versionIRI = serializer.readObject(LookupTable.emptyLookupTable(), dataInput, dataFactory);
                ontologyID = new OWLOntologyID(ontologyIRI, versionIRI);
            }
            else {
                throw new BinaryOWLParseException("Unexpected OntologyID marker: " + marker);
            }
        }
    }
    
    
}
