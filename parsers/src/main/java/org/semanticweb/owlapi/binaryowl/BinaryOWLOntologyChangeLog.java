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

import org.semanticweb.owlapi.binaryowl.change.OntologyChangeRecordList;
import org.semanticweb.owlapi.binaryowl.chunk.SkipSetting;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyChange;

import java.io.*;
import java.util.List;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 11/05/2012
 */
public class BinaryOWLOntologyChangeLog {


    public void appendChanges(List<OWLOntologyChange> changeList, long timestamp, BinaryOWLMetadata changeListMetadata, File file) throws IOException {
        final BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file, true));
        this.appendChanges(changeList, timestamp, changeListMetadata, os);
        os.close();
    }
    
    public void appendChanges(List<OWLOntologyChange> changeList, long timestamp, BinaryOWLMetadata changeListMetadata, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeByte(BinaryOWLOntologyDocumentSerializer.CHUNK_FOLLOWS_MARKER);
        OntologyChangeRecordList changeRecordList = new OntologyChangeRecordList(changeList, timestamp, changeListMetadata);
        changeRecordList.write(new DataOutputStream(os));
    }

    public void readChanges(InputStream inputStream, OWLDataFactory dataFactory, BinaryOWLChangeLogHandler handler) throws IOException, BinaryOWLParseException {
        readChanges(inputStream, dataFactory, handler, SkipSetting.SKIP_NONE);
    }

    public void readChanges(InputStream inputStream, OWLDataFactory dataFactory, BinaryOWLChangeLogHandler handler, SkipSetting skipSetting) throws IOException, BinaryOWLParseException {
        CountingInputStream countingInputStream = new CountingInputStream(inputStream);
        DataInputStream dataInputStream = new DataInputStream(countingInputStream);
        int marker = inputStream.read();
        while(marker == BinaryOWLOntologyDocumentSerializer.CHUNK_FOLLOWS_MARKER) {
            long filePosition = countingInputStream.getPosition();
            OntologyChangeRecordList changeRecordList = new OntologyChangeRecordList(dataInputStream, dataFactory, skipSetting);
            handler.handleChangesRead(changeRecordList, skipSetting, filePosition);
            marker = dataInputStream.read();
        }
    }


    
    private class CountingInputStream extends InputStream {


        private long position;

        private InputStream delegate;

        protected CountingInputStream(InputStream delegate) {
            this.delegate = delegate;
        }

        public long getPosition() {
            return position;
        }

        @Override
        public int read() throws IOException {
            int read = delegate.read();
            if(read != -1) {
                position++;
            }
            return read;
        }

        @Override
        public int read(byte[] b) throws IOException {
            int read = super.read(b);
            if(read != -1) {
                position += read;
            }
            return read;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int read = super.read(b, off, len);
            if(read != -1) {
                position += read;
            }
            return read;
        }

        @Override
        public long skip(long n) throws IOException {
            long skipped = super.skip(n);
            position += skipped;
            return skipped;
        }

    }



}
