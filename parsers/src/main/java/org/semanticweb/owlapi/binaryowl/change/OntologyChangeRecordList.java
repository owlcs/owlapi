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
import org.semanticweb.owlapi.binaryowl.chunk.SkipSetting;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.change.OWLOntologyChangeRecord;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 11/05/2012
 * <p>
 *     Represents a list of {@link OWLOntologyChangeRecord}s with a timestamp and metadata.
 * </p>
 */
public class OntologyChangeRecordList implements TimeStampedMetadataChunk {
    
    public static final short VERSION = 1;
    
    public static final int CHUNK_TYPE_MARKER = ChunkUtil.toInt("ochr");

    private long timestamp;
    
    private BinaryOWLMetadata metadata;
    
    private List<OWLOntologyChangeRecord> changeRecords;
    
    public OntologyChangeRecordList(List<OWLOntologyChange> changes, long timestamp, BinaryOWLMetadata metadata) {
        this(timestamp, metadata, convertToChangeRecordList(changes));
    }

    private static List<OWLOntologyChangeRecord> convertToChangeRecordList(List<OWLOntologyChange> changes) {
        List<OWLOntologyChangeRecord> records = new ArrayList<OWLOntologyChangeRecord>(changes.size() + 1);
        for(OWLOntologyChange change : changes) {
            records.add(change.getChangeRecord());
        }
        return records;
    }

    public OntologyChangeRecordList(long timestamp, BinaryOWLMetadata metadata, List<OWLOntologyChangeRecord> changeRecords) {
        this.timestamp = timestamp;
        this.metadata = metadata;
        this.changeRecords = Collections.unmodifiableList(new ArrayList<OWLOntologyChangeRecord>(changeRecords));
    }
    
    public OntologyChangeRecordList(DataInput dataInput, OWLDataFactory dataFactory, SkipSetting skipSetting) throws IOException, BinaryOWLParseException {
        read(dataInput, dataFactory, skipSetting);
    }

    public int getChunkType() {
        return CHUNK_TYPE_MARKER;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BinaryOWLMetadata getMetadata() {
        return metadata;
    }

    public List<OWLOntologyChangeRecord> getChangeRecords() {
        return changeRecords;
    }

    public List<OntologyChangeRecordRun> getRuns() {
        List<OntologyChangeRecordRun> result = new ArrayList<OntologyChangeRecordRun>();
        if (!changeRecords.isEmpty()) {
            OWLOntologyID currentRunId = null;
            List<OWLOntologyChangeData> runInfoList = new ArrayList<OWLOntologyChangeData>();
            for(OWLOntologyChangeRecord record : changeRecords) {
                if(currentRunId == null) {
                    // First run
                    currentRunId = record.getOntologyID();
                }
                else if(!currentRunId.equals(record.getOntologyID())) {
                    // Store current run
                    result.add(new OntologyChangeRecordRun(currentRunId, new ArrayList<OWLOntologyChangeData>(runInfoList)));
                    // Reset for fresh run
                    currentRunId = record.getOntologyID();
                    runInfoList.clear();
                }
                // Add to current run
                runInfoList.add(record.getData());
            }
            result.add(new OntologyChangeRecordRun(currentRunId, new ArrayList<OWLOntologyChangeData>(runInfoList)));
        }
        return result;
    }
    
    public void write(DataOutputStream os) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream bufferedDataOutputStream = new DataOutputStream(buffer);

        // Record format version
        bufferedDataOutputStream.writeShort(VERSION);

        // Timestamp
        bufferedDataOutputStream.writeLong(timestamp);
        
        // Metadata:  Size and Data
        ByteArrayOutputStream metadataBuffer = new ByteArrayOutputStream();
        DataOutputStream metadataDataOutputStream = new DataOutputStream(metadataBuffer);
        metadata.write(metadataDataOutputStream);
        bufferedDataOutputStream.writeInt(metadataBuffer.size());
        metadataBuffer.writeTo(bufferedDataOutputStream);

        // Split into runs - saves us repeatedly storing the same ontology id.
        List<OntologyChangeRecordRun> runs = getRuns();
        bufferedDataOutputStream.writeInt(runs.size());
        for(OntologyChangeRecordRun run : runs) {
            run.write(bufferedDataOutputStream);
        }
        bufferedDataOutputStream.flush();

        // Size, Type, Data
        final int bufferSize = buffer.size();
        os.writeInt(bufferSize);
        os.writeInt(CHUNK_TYPE_MARKER);
        buffer.writeTo(os);
    }

    private void read(DataInput dataInput, OWLDataFactory dataFactory, SkipSetting skipSetting) throws IOException, BinaryOWLParseException {
        // Size
        int chunkSize = dataInput.readInt();
        // Marker
        int chunkTypeMarker = dataInput.readInt();
        if(chunkTypeMarker != CHUNK_TYPE_MARKER) {
            throw new BinaryOWLParseException("Expected chunk type 0x" + Integer.toHexString(CHUNK_TYPE_MARKER) + " but encountered 0x" + Integer.toHexString(chunkTypeMarker));
        }

        // Record format version
        short version = dataInput.readShort();
        // For the moment we can only handle version 1 stuff
        if(version != VERSION) {
            throw new BinaryOWLParseException("Invalid version specifier.  Found 0x" + Integer.toHexString(version) + " but expected 0x" + Integer.toHexString(VERSION));
        }

        // Actual data
        timestamp = dataInput.readLong();

        int metadataSize = dataInput.readInt();
        if(skipSetting.isSkipMetadata()) {
            dataInput.skipBytes(metadataSize);
            metadata = new BinaryOWLMetadata();
        }
        else {
            metadata = new BinaryOWLMetadata(dataInput, dataFactory);
        }
        if(skipSetting.isSkipData()) {
            int bytesToSkip = chunkSize - 4 - metadataSize;
            dataInput.skipBytes(bytesToSkip);
            changeRecords = Collections.emptyList();
        }
        else {
            changeRecords = Collections.unmodifiableList(readRecords(dataInput, dataFactory));
        }
    }

    private static List<OWLOntologyChangeRecord> readRecords(DataInput dataInput, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        int numberOfRuns = dataInput.readInt();
        List<OWLOntologyChangeRecord> records = new ArrayList<OWLOntologyChangeRecord>(numberOfRuns + 1);
        for(int i = 0; i < numberOfRuns; i++) {
            OntologyChangeRecordRun run = new OntologyChangeRecordRun(dataInput, dataFactory);
            for(OWLOntologyChangeData info : run.getInfoList()) {
                records.add(new OWLOntologyChangeRecord(run.getOntologyID(), info));
            }
        }
        return records;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OntologyChangeRecordList(");
        sb.append(timestamp);
        sb.append(" ");
        sb.append(metadata);
        sb.append(" ");
        for(OWLOntologyChangeRecord record : changeRecords) {
            sb.append(" ");
            sb.append(record);
            sb.append(" ");
        }
        sb.append(")");
        return sb.toString();
    }



    //    private static void skipToTimestamp(DataInput dataInput) throws IOException {
//        // Size (4 bytes) + Type (4 bytes) + Timestamp (8 bytes)
//        final int bytesToSkip = 8;
//        long skipped = dataInput.skip(bytesToSkip);
//        if(skipped != bytesToSkip) {
//            throwSkipFailure(bytesToSkip, skipped);
//        }
//    }
//
//    public static long readTimeStamp(DataInput dataInput) throws IOException {
//        skipToTimestamp(dataInput);
//        return dataInput.readLong();
//    }
//    
//    private static void skipToMetadata(DataInput dataInput) throws IOException {
//        skipToTimestamp(dataInput);
//        // Time stamp is 8 bytes
//        final int bytesToSkip = 8;
//        long skipped = dataInput.skip(bytesToSkip);
//        if(skipped != bytesToSkip) {
//            throwSkipFailure(bytesToSkip, skipped);
//        }
//    }
//    
//    public static BinaryOWLMetadata readMetadata(DataInput dataInput, OWLDataFactory dataFactory) throws IOException {
//        skipToTimestamp(dataInput);
//        long skippedBytes = dataInput.skip(4);
//        if(skippedBytes != 4) {
//            throwSkipFailure(4, skippedBytes);
//        }
//        return new BinaryOWLMetadata(dataInput, dataFactory);
//    }
//
//    private List<OWLOntologyChangeRecord> skipToData(DataInput dataInput) throws IOException {
//        skipToMetadata(dataInput);
//        int metadataSize = dataInput.readInt();
//        long skippedBytes = dataInput.skip(metadataSize);
//        if(skippedBytes != metadataSize) {
//            throwSkipFailure(metadataSize, skippedBytes);
//        }
//        
//    }




//    private static void throwSkipFailure(long bytesToSkip, long skipped) throws IOException {
//        throw new IOException("Tried to skip " + bytesToSkip + " bytes, but only skipped " + skipped + " bytes");
//    }
//    
}
