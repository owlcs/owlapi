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

import java.io.*;
import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 06/04/2012
 */
public class IRILookupTable {

    public static final int NOT_INDEXED_MARKER = -8;

    private Map<String, Integer> startIndex = new LinkedHashMap<String, Integer>();

    private Map<IRI, Integer> iri2IndexMap = new LinkedHashMap<IRI, Integer>();

    private IRI [] iriTable;
    
    private OWLClass [] clsTable;
    
    private OWLObjectProperty objectPropertyTable [];
    
    private OWLDataProperty dataPropertyTable [];
    
    private OWLAnnotationProperty annotationPropertyTable [];
    
    private OWLNamedIndividual individualTable [];
    
    private OWLDatatype datatypeTable [];

    
    public IRILookupTable(OWLOntology ontology) {
        processSignatureSubset(ontology.getClassesInSignature());
        processSignatureSubset(ontology.getObjectPropertiesInSignature());
        processSignatureSubset(ontology.getDataPropertiesInSignature());
        processSignatureSubset(ontology.getAnnotationPropertiesInSignature());
        processSignatureSubset(ontology.getIndividualsInSignature());
        processSignatureSubset(ontology.getDatatypesInSignature());
    }

    public IRILookupTable(DataInput dis) throws IOException {
        read(dis);
    }

    public IRILookupTable() {
    }

    private void processSignatureSubset(Set<? extends OWLEntity> signature) {
        for (OWLEntity entity : signature) {
            processEntity(entity);
        }
    }

    private void processEntity(OWLEntity entity) {
        IRI iri = entity.getIRI();
        processIRI(iri);
    }

    private void processIRI(IRI iri) {
        if (!iri2IndexMap.containsKey(iri)) {
            int iriIndex = iri2IndexMap.size();
            iri2IndexMap.put(iri, iriIndex);
        }
        String start = iri.getStart();
        if (!startIndex.containsKey(start)) {
            startIndex.put(start, startIndex.size());
        }
    }

    private int get(OWLEntity e) {
        return iri2IndexMap.get(e.getIRI());
    }

    private int get(IRI iri) {
        return getIndex(iri);
    }

    private IRI get(int index) {
        return iriTable [index];
    }

    private int getIndex(IRI iri) {
        Integer i = iri2IndexMap.get(iri);
        if (i == null) {
            return -1;
        }
        else {
            return i;
        }
    }

    public void write(DataOutput os) throws IOException {
        os.writeInt(startIndex.size());
        for (String start : startIndex.keySet()) {
            os.writeUTF(start);
        }
        os.writeInt(iri2IndexMap.size());
        for (IRI iri : iri2IndexMap.keySet()) {
            int si = startIndex.get(iri.getStart());
            os.writeInt(si);
            String fragment = iri.getFragment();
            if (fragment == null) {
                os.writeUTF("");
            }
            else {
                os.writeUTF(fragment);//bytes = fragment.getBytes();
            }
        }
    }

    private void read(DataInput is) throws IOException {
        int startIndexSize = is.readInt();
        List<String> startIndexes = new ArrayList<String>(startIndexSize);
        for (int i = 0; i < startIndexSize; i++) {
            String s = is.readUTF();
            startIndexes.add(s);
        }

        int size = is.readInt();
        if(size == 0) {
            return;
        }
        iriTable = new IRI [size];
        for (int i = 0; i < size; i++) {
            int startIndex = is.readInt();
            String start = startIndexes.get(startIndex);
            String s = is.readUTF();//new String(bytes);
            IRI iri = IRI.create(start, s);
            iriTable[i] = iri;
        }
        clsTable = new OWLClass[size];
        annotationPropertyTable = new OWLAnnotationProperty[size];
        datatypeTable = new OWLDatatype[size];
    }
    
    public IRI readIRI(DataInput dis) throws IOException {
        int index = readIndex(dis);
        
        if(index == NOT_INDEXED_MARKER) {
            return readNonIndexedIRI(dis);
        }
        else {
            return iriTable[index];
        }
    }

    private IRI readNonIndexedIRI(DataInput dis) throws IOException {
        byte startMarker = dis.readByte();
        String start;
        if(startMarker == 0) {
            start = null;
        }
        else {
            start = dis.readUTF();
        }
        byte fragmentMarker = dis.readByte();
        String fragment;
        if(fragmentMarker == 0) {
            fragment = null;
        }
        else {
            fragment = dis.readUTF();
        }
        return IRI.create(start, fragment);
    }

    private int readIndex(DataInput dataInput) throws IOException {
        byte size = dataInput.readByte();
        if(size == 0) {
            return 0;
        }
        else if(size == NOT_INDEXED_MARKER) {
            return NOT_INDEXED_MARKER;
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
    
    private void writeIndex(int i, DataOutput dos) throws IOException {
        if(i == NOT_INDEXED_MARKER) {
            dos.writeByte(i);
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



    public OWLClass readClassIRI(DataInput dis) throws IOException {
        if(iriTable == null) {
            IRI iri = readIRI(dis);
            return new OWLClassImpl(iri);
        }
        int index = readIndex(dis);
        if(index == NOT_INDEXED_MARKER) {
            IRI iri = readNonIndexedIRI(dis);
            return new OWLClassImpl(iri);
        }
        OWLClass cls = clsTable[index];
        if(cls == null) {
            cls = new OWLClassImpl(iriTable[index]);
            clsTable[index] = cls;
        }
        return cls;
    }
    
    public OWLObjectProperty readObjectPropertyIRI(DataInput dis) throws IOException {
        if(iriTable == null) {
            IRI iri = readIRI(dis);
            return new OWLObjectPropertyImpl(iri);
        }
        if(objectPropertyTable == null) {
            objectPropertyTable = new OWLObjectProperty [iriTable.length];
        }

        int index = readIndex(dis);
        if(index == NOT_INDEXED_MARKER) {
            IRI iri = readNonIndexedIRI(dis);
            return new OWLObjectPropertyImpl(iri);
        }
        OWLObjectProperty prop = objectPropertyTable[index];
        if(prop == null) {
            prop = new OWLObjectPropertyImpl(iriTable[index]);
            objectPropertyTable[index] = prop;
        }
        return prop;
    }

    public OWLDataProperty readDataPropertyIRI(DataInput dis) throws IOException {
        if(iriTable == null) {
            IRI iri = readIRI(dis);
            return new OWLDataPropertyImpl(iri);
        }
        if(dataPropertyTable == null) {
            dataPropertyTable = new OWLDataProperty[iriTable.length];
        }
        int index = readIndex(dis);
        if(index == NOT_INDEXED_MARKER) {
            IRI iri = readNonIndexedIRI(dis);
            return new OWLDataPropertyImpl(iri);
        }
        OWLDataProperty prop = dataPropertyTable[index];
        if(prop == null) {
            prop = new OWLDataPropertyImpl(iriTable[index]);
            dataPropertyTable[index] = prop;
        }
        return prop;
    }

    public OWLAnnotationProperty readAnnotationPropertyIRI(DataInput dis) throws IOException {
        if(iriTable == null) {
            IRI iri = readIRI(dis);
            return new OWLAnnotationPropertyImpl(iri);
        }
        int index = readIndex(dis);
        if(index == NOT_INDEXED_MARKER) {
            IRI iri = readNonIndexedIRI(dis);
            return new OWLAnnotationPropertyImpl(iri);
        }
        OWLAnnotationProperty prop = annotationPropertyTable[index];
        if(prop == null) {
            prop = new OWLAnnotationPropertyImpl(iriTable[index]);
            annotationPropertyTable[index] = prop;
        }
        return prop;
    }

    public OWLDatatype readDataypeIRI(DataInput dis) throws IOException {
        if(iriTable == null) {
            IRI iri = readIRI(dis);
            return new OWLDatatypeImpl(iri);
        }
        int index = readIndex(dis);
        if(index == NOT_INDEXED_MARKER) {
            IRI iri = readNonIndexedIRI(dis);
            return new OWLDatatypeImpl(iri);
        }
        OWLDatatype prop = datatypeTable[index];
        if(prop == null) {
            prop = new OWLDatatypeImpl(iriTable[index]);
            datatypeTable[index] = prop;
        }
        return prop;
    }

    public OWLNamedIndividual readIndividualIRI(DataInput dis) throws IOException {
        if(iriTable == null) {
            IRI iri = readIRI(dis);
            return new OWLNamedIndividualImpl(iri);
        }
        if(individualTable == null) {
            individualTable = new OWLNamedIndividual[iriTable.length];
        }

        int index = readIndex(dis);

        if(index == NOT_INDEXED_MARKER) {
            IRI iri = readNonIndexedIRI(dis);
            return new OWLNamedIndividualImpl(iri);
        }
        OWLNamedIndividual ind = individualTable[index];
        if(ind == null) {
            ind = new OWLNamedIndividualImpl(iriTable[index]);
            individualTable[index] = ind;
        }
        return ind;
    }
    
    public void writeIRI(IRI iri, DataOutput dataOutput) throws IOException {
        int index = getIndex(iri);
        if(index == -1) {
            writeIndex(NOT_INDEXED_MARKER, dataOutput);
            String start = iri.getStart();
            if(start == null) {
                dataOutput.writeByte(0);
            }
            else {
                dataOutput.writeByte(1);
                dataOutput.writeUTF(start);
            }
            String fragment = iri.getFragment();
            if(fragment == null) {
                dataOutput.writeByte(0);
            }
            else {
                dataOutput.writeByte(1);
                dataOutput.writeUTF(fragment);
            }
        }
        else {
            writeIndex(index, dataOutput);
        }
    }


}
