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
import org.semanticweb.owlapi.binaryowl.OWLObjectBinaryType;
import org.semanticweb.owlapi.binaryowl.lookup.LookupTable;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 01/05/2012
 */
public class SerializerBase {

    protected void writeTypedMarkedObjectSet(Set<? extends OWLObject> objects, LookupTable lookupTable, DataOutput dataOutput) throws IOException {
        final int size = objects.size();
        writeVariableLengthUnsignedInt(size, dataOutput);
        for(OWLObject object : objects) {
            writeTypeMarkedObject(object, lookupTable, dataOutput);
        }
    }


    @SuppressWarnings("unchecked")
    protected <O extends OWLObject> Set<O> readTypeMarkedObjectSet(LookupTable lookupTable, DataInput bos, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        int length = readVariableLengthUnsignedInt(bos);
        if(length == 0) {
            return Collections.emptySet();
        }
        else {
            Set<O> result = new ListBackedSet<O>(length + 2);
            for(int i = 0; i < length; i++) {
                OWLObject object = readTypeMarkedObject(lookupTable, bos, dataFactory);
                result.add((O) object);
            }
            return result;
        }
    }


    @SuppressWarnings("unchecked")
    protected void writeTypeMarkedObject(OWLObject o, LookupTable lookupTable, DataOutput dataOutput) throws IOException  {
        OWLObjectBinaryType.write(o, dataOutput, lookupTable);
    }

    @SuppressWarnings("unchecked")
    protected <O extends OWLObject> O readTypeMarkedObject(LookupTable lookupTable, DataInput dataInput, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException  {
        return OWLObjectBinaryType.read(dataInput, lookupTable, dataFactory);
    }


    /**
     * Every collection in the file is stored as a set.  Therefore, when we read it in, we don't have to check for
     * duplicates.  Further more, most consumers of read in sets just iterate over them - they don't call contains()
     * or add().  Therefore, we can optimise this and have a set which is really a list.
     * @param <O>
     */
    private class ListBackedSet<O> extends ArrayList<O> implements Set<O> {

        private ListBackedSet(int initialCapacity) {
            super(initialCapacity);
        }
    }

    
    
    
    private static void writeVariableLengthUnsignedInt(int i, DataOutput dos) throws IOException {
        if(i < 0) {
            throw new RuntimeException("Cannot write int < 0");
        }
        else if(i == 0) {
            dos.writeByte(0);
        }
        else if(i < Byte.MAX_VALUE) {
            dos.writeByte(i);
        }
        else if(i < Short.MAX_VALUE) {
            dos.writeByte(-2);
            dos.writeShort(i);
        }
        else {
            dos.writeByte(-4);
            dos.writeInt(i);
        }
    }

    private static int readVariableLengthUnsignedInt(DataInput dataInput) throws IOException {
        byte size = dataInput.readByte();
        if(size == 0) {
            return 0;
        }
        else if(size == -2) {
            return dataInput.readShort();
        }
        else if(size == -4) {
            return dataInput.readInt();
        }
        else if(size < Byte.MAX_VALUE) {
            return size;
        }
        else {
            throw new RuntimeException();
        }
    }

}
