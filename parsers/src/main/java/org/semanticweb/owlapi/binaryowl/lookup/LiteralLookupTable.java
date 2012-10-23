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

package org.semanticweb.owlapi.binaryowl.lookup;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import java.io.*;
import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 06/04/2012
 */
public class LiteralLookupTable {


    private boolean useInterning = false;

    private static final byte INTERNING_NOT_USED_MARKER = 0;

    private static final byte INTERNING_USED_MARKER = 1;
    

    public static final String UTF_8 = "UTF-8";
    
    public static final byte NOT_INDEXED_MARKER = -2;

    private static final byte RDF_PLAIN_LITERAL_MARKER = 0;

    private static final byte XSD_STRING_MARKER = 1;

    private static final byte XSD_BOOLEAN_MARKER = 2;

    private static final byte OTHER_DATATYPE_MARKER = 3;

    private static final byte LANG_MARKER = 1;

    private static final byte NO_LANG_MARKER = 0;

    private Map<OWLLiteral, Integer> indexMap = new LinkedHashMap<OWLLiteral, Integer>();

    private List<OWLLiteral> tableList = new ArrayList<OWLLiteral>(0);

    private IRILookupTable iriLookupTable;

    private static final OWLDatatype RDF_PLAIN_LITERAL_DATATYPE = new OWLDatatypeImpl(OWL2Datatype.RDF_PLAIN_LITERAL.getIRI());

    private static final OWLDatatype XSD_STRING_DATATYPE = new OWLDatatypeImpl(OWL2Datatype.XSD_STRING.getIRI());

    private static final OWLDatatype XSD_BOOLEAN_DATATYPE = new OWLDatatypeImpl(OWL2Datatype.XSD_BOOLEAN.getIRI());



    private static final OWLLiteral BOOLEAN_TRUE = new OWLLiteralImplNoCompression("true", null, XSD_BOOLEAN_DATATYPE);

    private static final OWLLiteral BOOLEAN_FALSE = new OWLLiteralImplNoCompression("false", null, XSD_BOOLEAN_DATATYPE);



    public LiteralLookupTable(OWLOntology ontology, IRILookupTable lookupTable) {
        this.iriLookupTable = lookupTable;
        if (useInterning) {
            internLiterals(ontology);
        }
    }

    private void internLiterals(OWLOntology ontology) {
        for (OWLAnnotationAssertionAxiom ax : ontology.getAxioms(AxiomType.ANNOTATION_ASSERTION)) {
            OWLAnnotationValue value = ax.getValue();
            if (value instanceof OWLLiteral) {
                int newIndex = indexMap.size();
                OWLLiteral litValue = (OWLLiteral) value;
                Integer prev = indexMap.put(litValue, newIndex);
                if (prev != null) {
                    indexMap.put(litValue, prev);
                }
            }
        }
        for (OWLDataPropertyAssertionAxiom ax : ontology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION)) {
            int newIndex = indexMap.size();
            OWLLiteral object = ax.getObject();
            Integer prev = indexMap.put(object, newIndex);
            if (prev != null) {
                indexMap.put(object, prev);
            }
        }
    }

    public LiteralLookupTable(IRILookupTable iriLookupTable) {
        this.iriLookupTable = iriLookupTable;
    }
    
    public LiteralLookupTable(IRILookupTable iriLookupTable, DataInput dis, OWLDataFactory df) throws IOException {
        this(iriLookupTable);
        read(dis, df);
    }

    public LiteralLookupTable() {
        this(new IRILookupTable());
    }

    public OWLLiteral getLiteral(int index) {
        return tableList.get(index);
    }

    public int getIndex(OWLLiteral literal) {
        if(!useInterning) {
            return -1;
        }
        Integer i = indexMap.get(literal);
        if (i != null) {
            return i;
        }
        else {
            return -1;
        }
    }

    public void write(DataOutputStream os) throws IOException {
        if(useInterning) {
            os.writeByte(INTERNING_USED_MARKER);
            os.writeInt(indexMap.size());
            for (OWLLiteral literal : indexMap.keySet()) {
                writeRawLiteral(os, literal);
            }
        }
        else {
            os.writeByte(INTERNING_NOT_USED_MARKER);
        }

    }

    private void read(DataInput is, OWLDataFactory df) throws IOException {
        int interningMarker = is.readByte();
        if(interningMarker == INTERNING_USED_MARKER) {
            useInterning = true;
        }
        else if(interningMarker == INTERNING_NOT_USED_MARKER) {
            useInterning = false;
        }
        else {
            throw new IOException("Unexpected literal interning marker: " + interningMarker);
        }

        if (useInterning) {
            int size = is.readInt();
            tableList = new ArrayList<OWLLiteral>(size + 2);
            for (int i = 0; i < size; i++) {
                OWLLiteral literal = readRawLiteral(is, df);
                tableList.add(literal);
            }
        }
    }

    public OWLLiteral readLiteral(DataInput is, OWLDataFactory df) throws IOException {
        if (useInterning) {
            int index = is.readInt();
            if(index == NOT_INDEXED_MARKER) {
                return readRawLiteral(is, df);
            }
            else {
                return tableList.get(index);
            }
        }
        else {
            return readRawLiteral(is, df);
        }
    }

    private OWLLiteral readRawLiteral(DataInput is, OWLDataFactory df) throws IOException {
        int typeMarker = is.readByte();
        if (typeMarker == RDF_PLAIN_LITERAL_MARKER) {
            int langMarker = is.readByte();
            if (langMarker == LANG_MARKER) {
                String lang = is.readUTF();
                byte[] literalBytes = readBytes(is);
//                return new OWLLiteralImplNoCompression(literalBytes, lang, RDF_PLAIN_LITERAL_DATATYPE);
                return new OWLLiteralImplNoCompression(new String(literalBytes), lang, RDF_PLAIN_LITERAL_DATATYPE);
            }
            else if (langMarker == NO_LANG_MARKER) {
                byte[] literalBytes = readBytes(is);
//                return new OWLLiteralImplNoCompression(literalBytes, null, RDF_PLAIN_LITERAL_DATATYPE);
                return new OWLLiteralImplNoCompression(new String(literalBytes), null, RDF_PLAIN_LITERAL_DATATYPE);
            }
            else {
                throw new IOException("Unknown lang marker: " + langMarker);
            }
        }
        else if(typeMarker == XSD_STRING_MARKER) {
            byte[] literalBytes = readBytes(is);
//            return new OWLLiteralImplNoCompression(literalBytes, null, XSD_STRING_DATATYPE);
            return new OWLLiteralImplNoCompression(new String(literalBytes), null, XSD_STRING_DATATYPE);
        }
        else if(typeMarker == XSD_BOOLEAN_MARKER) {
            if(is.readBoolean()) {
                return BOOLEAN_TRUE;
            }
            else {
                return BOOLEAN_FALSE;
            }
        }
        else if (typeMarker == OTHER_DATATYPE_MARKER) {
            OWLDatatype datatype = iriLookupTable.readDataypeIRI(is);
            byte[] literalBytes = readBytes(is);
            return new OWLLiteralImplNoCompression(new String(literalBytes), null, datatype);
        }
        else {
            throw new RuntimeException("Unknown type marker: " + typeMarker);
        }


    }


    public void writeLiteral(DataOutput os, OWLLiteral literal) throws IOException {
        if(useInterning) {
            int index = getIndex(literal);
            if(index == -1) {
                os.writeInt(NOT_INDEXED_MARKER);
                writeRawLiteral(os, literal);
            }
            else {
                os.writeInt(index);
            }
        }
        else {
            writeRawLiteral(os, literal);
        }

    }

    private void writeRawLiteral(DataOutput os, OWLLiteral literal) throws IOException {
        if(literal.getDatatype().equals(XSD_BOOLEAN_DATATYPE)) {
            os.write(XSD_BOOLEAN_MARKER);
            os.writeBoolean(literal.parseBoolean());
            return;
        }
        else if (literal.isRDFPlainLiteral()) {
            os.write(RDF_PLAIN_LITERAL_MARKER);
            if (literal.hasLang()) {
                os.write(LANG_MARKER);
                writeString(literal.getLang(), os);
            }
            else {
                os.write(NO_LANG_MARKER);
            }
        }
        else if(literal.getDatatype().equals(XSD_STRING_DATATYPE)) {
            os.write(XSD_STRING_MARKER);
        }
        else {
            os.write(OTHER_DATATYPE_MARKER);
            iriLookupTable.writeIRI(literal.getDatatype().getIRI(), os);
        }

        byte[] literalBytes;
        if (literal instanceof OWLLiteralImplNoCompression) {
            literalBytes = ((OWLLiteralImplNoCompression) literal).getLiteral().getBytes();
        }
        else {
            literalBytes = literal.getLiteral().getBytes(UTF_8);
        }
        writeBytes(literalBytes, os);



    }

    private void writeString(String s, DataOutput os) throws IOException {
        os.writeUTF(s);
    }


    private void writeBytes(byte[] bytes, DataOutput os) throws IOException {
        os.writeShort(bytes.length);
        os.write(bytes);
    }


    private byte[] readBytes(DataInput is) throws IOException {
        int length = is.readShort();
        byte[] bytes = new byte[length];
        is.readFully(bytes);
        return bytes;
    }

}
