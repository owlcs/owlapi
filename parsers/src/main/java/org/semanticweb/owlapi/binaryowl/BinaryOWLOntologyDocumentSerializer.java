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

package org.semanticweb.owlapi.binaryowl;

import org.semanticweb.owlapi.binaryowl.change.OntologyChangeDataList;
import org.semanticweb.owlapi.binaryowl.chunk.BinaryOWLMetadataChunk;
import org.semanticweb.owlapi.binaryowl.chunk.ChunkUtil;
import org.semanticweb.owlapi.binaryowl.lookup.IRILookupTable;
import org.semanticweb.owlapi.binaryowl.lookup.LiteralLookupTable;
import org.semanticweb.owlapi.binaryowl.lookup.LookupTable;
import org.semanticweb.owlapi.binaryowl.owlobject.BinaryOWLImportsDeclarationSet;
import org.semanticweb.owlapi.binaryowl.owlobject.BinaryOWLOntologyID;
import org.semanticweb.owlapi.binaryowl.owlobject.SerializerBase;
import org.semanticweb.owlapi.model.*;

import java.io.*;
import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 30/04/2012
 */
public class BinaryOWLOntologyDocumentSerializer extends SerializerBase {

    public static final byte CHUNK_FOLLOWS_MARKER = 33;

    private static final long EXPECTED_VERSION = 1;

//    public OWLOntology read(DataInputStream dis) throws IOException, OWLOntologyCreationException, BinaryOWLParseException {
//        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//        OWLOntology ontology = manager.createOntology();
//        read(dis, ontology);
//        return ontology;
//    }

    public BinaryOWLOntologyDocumentFormat read(DataInputStream dis, OWLOntology ontology) throws IOException, BinaryOWLParseException, UnloadableImportException {
        return read(dis, ontology, new OWLOntologyLoaderConfiguration());
    }

    public BinaryOWLOntologyDocumentFormat read(DataInputStream dis, OWLOntology ontology, OWLOntologyLoaderConfiguration loaderConfiguration) throws IOException, BinaryOWLParseException, UnloadableImportException {

        BinaryOWLOntologyDocumentPreamble preamble = new BinaryOWLOntologyDocumentPreamble(dis);
        int fileFormatVersion = preamble.getFileFormatVersion();
        if (fileFormatVersion != EXPECTED_VERSION) {
            throw new BinaryOWLParseException("Cannot parse file format version: " + fileFormatVersion + " (expected version: " + EXPECTED_VERSION + ")");
        }

        OWLDataFactory df = ontology.getOWLOntologyManager().getOWLDataFactory();

        // Metadata
        BinaryOWLMetadataChunk chunk = new BinaryOWLMetadataChunk(dis, df);
        BinaryOWLMetadata metadata = chunk.getMetadata();


        // Ontology ID
        BinaryOWLOntologyID serializer = new BinaryOWLOntologyID(dis, df);
        OWLOntologyID ontologyID = serializer.getOntologyID();

        // Imported ontologies
        BinaryOWLImportsDeclarationSet importsDeclarationSet = new BinaryOWLImportsDeclarationSet(dis, df);
        Set<OWLImportsDeclaration> importsDeclarations = importsDeclarationSet.getImportsDeclarations();

        // IRI Table
        IRILookupTable iriLookupTable = new IRILookupTable(dis);

        // Literal Table
        LiteralLookupTable literalLookupTable = new LiteralLookupTable(iriLookupTable, dis, df);


        LookupTable lookupTable = new LookupTable(iriLookupTable, literalLookupTable);

        // Ontology Annotations
        Set<OWLAnnotation> annotations = readTypeMarkedObjectSet(lookupTable, dis, df);


        // Axiom tables - axioms by type
        List<Set<OWLAxiom>> axioms = new ArrayList<Set<OWLAxiom>>();
        for (int i = 0; i < AxiomType.AXIOM_TYPES.size(); i++) {
            Set<OWLAxiom> axiomsOfType = readTypeMarkedObjectSet(lookupTable, dis, df);
            axioms.add(axiomsOfType);
        }


        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        manager.applyChange(new SetOntologyID(ontology, ontologyID));
        for (OWLImportsDeclaration decl : importsDeclarations) {
            manager.applyChange(new AddImport(ontology, decl));
        }
        for (OWLImportsDeclaration decl : importsDeclarations) {
            manager.makeLoadImportRequest(decl, loaderConfiguration);
        }
        for (OWLAnnotation annotation : annotations) {
            manager.applyChange(new AddOntologyAnnotation(ontology, annotation));
        }
        for (Set<OWLAxiom> axiomSet : axioms) {
            manager.addAxioms(ontology, axiomSet);
        }

        // Read any changes that have been appended to the end of the file.
        readOntologyChanges(dis, df, new ApplyOntologyChangesHandler(ontology));

        return new BinaryOWLOntologyDocumentFormat(metadata);
    }

    public OWLOntologyID readOntologyIDOnly(DataInputStream dataInputStream, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        BinaryOWLOntologyDocumentPreamble preamble = new BinaryOWLOntologyDocumentPreamble(dataInputStream);
        // Skip over metadata
        ChunkUtil.skipChunk(dataInputStream);
        BinaryOWLOntologyID serializer = new BinaryOWLOntologyID(dataInputStream, dataFactory);
        return serializer.getOntologyID();
    }

    public void write(OWLOntology ontology, DataOutputStream dos, BinaryOWLMetadata documentMetadata) throws IOException {
        BinaryOWLOntologyDocumentPreamble preamble = new BinaryOWLOntologyDocumentPreamble();
        preamble.write(dos);

        // Metadata
        BinaryOWLMetadataChunk metadataChunk = new BinaryOWLMetadataChunk(documentMetadata);
        metadataChunk.write(dos);

        // Ontology ID
        BinaryOWLOntologyID serializer = new BinaryOWLOntologyID(ontology.getOntologyID());
        serializer.write(dos);

        // Imports
        BinaryOWLImportsDeclarationSet importsDeclarationSet = new BinaryOWLImportsDeclarationSet(ontology.getImportsDeclarations());
        importsDeclarationSet.write(dos);

        // IRI Table
        IRILookupTable iriLookupTable = new IRILookupTable(ontology);
        iriLookupTable.write(dos);

        // Literal Table
        LiteralLookupTable literalLookupTable = new LiteralLookupTable(ontology, iriLookupTable);
        literalLookupTable.write(dos);

        LookupTable lookupTable = new LookupTable(iriLookupTable, literalLookupTable);

        // Ontology Annotations
        writeTypedMarkedObjectSet(ontology.getAnnotations(), lookupTable, dos);

        // Axiom tables - axioms by type
        for (AxiomType axiomType : AxiomType.AXIOM_TYPES) {
            writeTypedMarkedObjectSet(ontology.getAxioms(axiomType), lookupTable, dos);
        }

        dos.flush();
    }

    public void write(OWLOntology ontology, DataOutputStream dos) throws IOException {
        write(ontology, dos, new BinaryOWLMetadata());
    }

    public void readOntologyChanges(DataInputStream dataInputStream, OWLDataFactory dataFactory, BinaryOWLOntologyDocumentAppendedChangeHandler changeHandler) throws IOException, BinaryOWLParseException {
        byte chunkFollowsMarker = (byte) dataInputStream.read();
        while (chunkFollowsMarker != -1) {
            OntologyChangeDataList list = new OntologyChangeDataList(dataInputStream, dataFactory);
            changeHandler.handleChanges(list);
            chunkFollowsMarker = (byte) dataInputStream.read();
        }
    }

    public void appendOntologyChanges(File file, OntologyChangeDataList changeRecords) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, true);
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(fos));
        appendOntologyChanges(dos, changeRecords);
        dos.close();
    }

    public void appendOntologyChanges(DataOutputStream dos, OntologyChangeDataList changeRecords) throws IOException {
        dos.writeByte(BinaryOWLOntologyDocumentSerializer.CHUNK_FOLLOWS_MARKER);
        changeRecords.write(dos);
    }

}
