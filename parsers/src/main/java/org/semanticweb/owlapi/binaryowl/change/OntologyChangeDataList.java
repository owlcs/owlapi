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

package org.semanticweb.owlapi.binaryowl.change;

import org.semanticweb.owlapi.binaryowl.BinaryOWLMetadata;
import org.semanticweb.owlapi.binaryowl.BinaryOWLParseException;
import org.semanticweb.owlapi.binaryowl.chunk.ChunkUtil;
import org.semanticweb.owlapi.binaryowl.chunk.TimeStampedMetadataChunk;
import org.semanticweb.owlapi.binaryowl.lookup.LookupTable;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 27/04/2012
 */
public class OntologyChangeDataList implements TimeStampedMetadataChunk, Iterable<OWLOntologyChangeData> {

    public static final int CHUNK_TYPE = ChunkUtil.toInt("ochi");

    private long timestamp;

    private List<OWLOntologyChangeData> list;

    private BinaryOWLMetadata metadata;
    
    public OntologyChangeDataList(List<OWLOntologyChange> ontologyChanges, OWLOntologyID targetOntology, long timeStamp, BinaryOWLMetadata metadata) {
        List<OWLOntologyChangeData> infoList = new ArrayList<OWLOntologyChangeData>();
        for(OWLOntologyChange change : ontologyChanges) {
            if(change.getOntology().getOntologyID().equals(targetOntology)) {
                infoList.add(change.getChangeData());
            }
        }
        this.timestamp = timeStamp;
        this.metadata = metadata;
        this.list = Collections.unmodifiableList(infoList);
    }

    public OntologyChangeDataList(List<OWLOntologyChangeData> list, long timestamp, BinaryOWLMetadata metadata) {
        this.timestamp = timestamp;
        this.list = Collections.unmodifiableList(new ArrayList<OWLOntologyChangeData>(list));
        this.metadata = metadata;
    }

    public OntologyChangeDataList(List<OWLOntologyChangeData> list, long timestamp) {
        this(list, timestamp, BinaryOWLMetadata.emptyMetadata());
    }


    public OntologyChangeDataList(DataInputStream dataInputStream, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        read(dataInputStream, dataFactory);
    }



    public long getTimestamp() {
        return timestamp;
    }

    public BinaryOWLMetadata getMetadata() {
        return metadata;
    }

    public int getChunkType() {
        return CHUNK_TYPE;
    }

    public int size() {
        return list.size();
    }

    public Iterator<OWLOntologyChangeData> iterator() {
        return list.iterator();
    }
    
    public List<OWLOntologyChange> getOntologyChanges(OWLOntology ontology) {
        List<OWLOntologyChange> result = new ArrayList<OWLOntologyChange>();
        for(OWLOntologyChangeData info : list) {
            OWLOntologyChange change = info.createOntologyChange(ontology);
            result.add(change);
        }
        return result;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    private final LookupTable EMPTY_LOOKUP_TABLE = LookupTable.emptyLookupTable();

    public void write(DataOutputStream dos) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream buffer = new DataOutputStream(bos);
        buffer.writeLong(timestamp);
        writeMetadata(buffer);
        writeChangeRecordData(buffer);

        dos.writeInt(bos.size());
        dos.writeInt(CHUNK_TYPE);
        bos.writeTo(dos);
    }



    private void read(DataInputStream dis, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        int dataSize = dis.readInt();
        int chunkType = dis.readInt();
        if(chunkType != CHUNK_TYPE) {
            throw new BinaryOWLParseException("Expected change record chunk marker (0x" + Integer.toHexString(CHUNK_TYPE) + ") but found 0x" + Integer.toHexString(chunkType));
        }
        // Timestamp
        timestamp = dis.readLong();
        // Metadata
        metadata = readMetadata(dis, dataFactory);
        // Change list
        list = Collections.unmodifiableList(readChangeRecordData(dis, dataFactory));
    }

    private List<OWLOntologyChangeData> readChangeRecordData(DataInputStream dis, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        int recordSize = dis.readInt();
        int size = dis.readInt();
        List<OWLOntologyChangeData> list = new ArrayList<OWLOntologyChangeData>(size + 2);
        for(int i = 0; i < size; i++) {
            OWLOntologyChangeData representative = OntologyChangeRecordInfoType.read(dis, EMPTY_LOOKUP_TABLE, dataFactory);
            list.add(representative);
        }
        return list;
    }


    private BinaryOWLMetadata readMetadata(DataInputStream dis, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        int recordSize = dis.readInt();
        return new BinaryOWLMetadata(dis, dataFactory);

    }

    private void writeMetadata(DataOutputStream mainOutputStream) throws IOException {
        ByteArrayOutputStream metadataByteOutputStream = new ByteArrayOutputStream();
        DataOutputStream metaDataOutputStream = new DataOutputStream(metadataByteOutputStream);

        metadata.write(metaDataOutputStream);

        // Size of metadata in bytes
        int recordSize = metaDataOutputStream.size();
        mainOutputStream.writeInt(recordSize);
        // Actual metadata
        metadataByteOutputStream.writeTo(mainOutputStream);
    }



    @SuppressWarnings("unchecked")
    private void writeChangeRecordData(DataOutputStream mainOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream changeRecordDataOutputStream = new DataOutputStream(byteArrayOutputStream);
        // Change List
        changeRecordDataOutputStream.writeInt(list.size());
        for(OWLOntologyChangeData recordInfo : list) {
            OntologyChangeRecordInfoType.write(recordInfo, EMPTY_LOOKUP_TABLE, changeRecordDataOutputStream);
        }

        // Size of changes in bytes
        int size = changeRecordDataOutputStream.size();
        mainOutputStream.writeInt(size);
        // Actual changes
        byteArrayOutputStream.writeTo(mainOutputStream);
    }

}
