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

package org.semanticweb.owlapi.binaryowl.chunk;

import org.semanticweb.owlapi.binaryowl.lookup.LookupTable;
import org.semanticweb.owlapi.model.OWLDataFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 30/04/2012
 */
public class ChunkUtil {
    
    
    public static int toInt(char ch0, char ch1, char ch2, char ch3) {
        return (int) ch0 << 24 | ch1 << 16 | ch2 << 8 | ch3;
    }

    public static int toInt(String chars) {
        if(chars.length() != 4) {
            throw new RuntimeException("chars should be 4 characters long");
        }
        return toInt(chars.charAt(0), chars.charAt(1), chars.charAt(2), chars.charAt(3));
    }

    public static int skipChunk(DataInput dataInput) throws IOException {
        int chunkSize = dataInput.readInt();
        // Skip over chunk type
        int chunkType = dataInput.readInt();
        // Skip over data
        dataInput.skipBytes(chunkSize);
        return chunkType;
    }
    
    

    
}
