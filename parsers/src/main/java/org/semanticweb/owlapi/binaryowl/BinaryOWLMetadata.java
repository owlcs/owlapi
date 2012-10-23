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

import org.semanticweb.owlapi.binaryowl.lookup.LookupTable;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 08/05/2012
 * <p>
 *     A structure for holding key value pairs that can be serialized in a binary format.  The values may take the form
 *     of primitive types:  string, int, long, boolean, double, byte array, or may also be instances of {@OWLObject}.
 * </p>
 */
public class BinaryOWLMetadata {

    /**
     * A list of the different kinds of attribute values that we can store and the bytes that mark them.
     */
    private static enum TypeMarker {

        STRING(1),

        INTEGER(2),

        LONG(3),

        BOOLEAN(4),

        DOUBLE(5),

        BYTE_ARRAY(6),

        OWL_OBJECT_LIST(7);

        private byte marker;

        private static TypeMarker [] values = values();

        private TypeMarker(int marker) {
            this.marker = (byte) marker;
        }

        public byte getMarker() {
            return marker;
        }

        public static TypeMarker getTypeMarker(byte marker) {
            return values[((int)(marker)) - 1];
        }
    }


    private StringMap stringAttributes = new StringMap();

    private IntegerMap intAttributes = new IntegerMap();
    
    private LongMap longAttributes = new LongMap();
    
    private BooleanMap booleanAttributes = new BooleanMap();
    
    private DoubleMap doubleAttributes = new DoubleMap();

    private ByteArrayMap byteArrayAttributes = new ByteArrayMap();

    private OWLObjectListMap owlObjectAttributes = new OWLObjectListMap();
    
    private EnumMap<TypeMarker, AttributeMap<?>> attributeMaps = new EnumMap<TypeMarker, AttributeMap<?>>(TypeMarker.class);

    public BinaryOWLMetadata() {
        attributeMaps.put(TypeMarker.STRING, stringAttributes);
        attributeMaps.put(TypeMarker.INTEGER, intAttributes);
        attributeMaps.put(TypeMarker.LONG, longAttributes);
        attributeMaps.put(TypeMarker.BOOLEAN, booleanAttributes);
        attributeMaps.put(TypeMarker.DOUBLE, doubleAttributes);
        attributeMaps.put(TypeMarker.BYTE_ARRAY, byteArrayAttributes);
        attributeMaps.put(TypeMarker.OWL_OBJECT_LIST, owlObjectAttributes);
    }


    public BinaryOWLMetadata(DataInput dataInput, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        this();
        read(dataInput, dataFactory);
    }

    public boolean isEmpty() {
        for(AttributeMap attributeValueMap : attributeMaps.values()) {
            if(!attributeValueMap.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Sets the value of an attribute.
     * @param nameValueMap A map which maps attribute names to attribute values.
     * @param name The name of the attribute.  Not null.
     * @param value The value of the attribute.  Not null.
     * @throws NullPointerException if name is null, or value is null.
     */
    private <O> void setValue(Map<String, O> nameValueMap, String name, O value) {
        checkForNull(name, value);
        nameValueMap.put(name, value);
    }

    /**
     * Checks the name and value of an attibute name/value pair to see if either of them are null.  If either is null
     * a {@link NullPointerException} is thrown.
     * @param name The name of the attribute to check.
     * @param value The value of the attribute to check.
     */
    private void checkForNull(String name, Object value) {
        if(name == null) {
            throw new NullPointerException("name must not be null");
        }
        if(value == null) {
            throw new NullPointerException("value must not be null");
        }
    }

    /**
     * Gets the value, or default value, of an attribute.
     * @param nameValueMap The name attribute value map which maps attribute names to attribute values.
     * @param attributeName The name of the attribute to retrieve.
     * @param defaultValue A default value, which will be returned if there is no set value for the specified attribute
     * name in the specified attribute name value map.
     * @param <O> The type of attribute value
     * @return The attribute value that is set for the specified name in the specified map, or the value specified by
     * the defaultValue parameter if no attribute with the specified name is set in the specified map.
     */
    private <O> O getValue(Map<String, O> nameValueMap, String attributeName, O defaultValue) {
        if(attributeName == null) {
            throw new NullPointerException("name must not be null");
        }
        if(nameValueMap == null) {
            return defaultValue;
        }
        O value = nameValueMap.get(attributeName);
        if(value == null) {
            return defaultValue;
        }
        return value;
    }

    private <O> void clearAttribute(Map<String, O> attributeValueMap, String attributeName) {
        attributeValueMap.remove(attributeName);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a copy of this metadata object.  Modifications to this object don't affect the copy, and vice versa.
     * @return A copy of this metadata object.
     */
    public BinaryOWLMetadata createCopy() {
        BinaryOWLMetadata copy = new BinaryOWLMetadata();
        copy.stringAttributes.putAll(stringAttributes);
        copy.intAttributes.putAll(intAttributes);
        copy.longAttributes.putAll(longAttributes);
        copy.doubleAttributes.putAll(doubleAttributes);
        copy.booleanAttributes.putAll(booleanAttributes);
        copy.byteArrayAttributes.putAll(byteArrayAttributes);
        for(String key : owlObjectAttributes.keySet()) {
            List<OWLObject> objectList = new ArrayList<OWLObject>(owlObjectAttributes.get(key));
            copy.owlObjectAttributes.put(key, objectList);
        }
        return copy;
    }
    
    /**
     * Removes all attributes in this metadata object.
     */
    public void removeAll() {
        for(Map<String, ?> attributeValueMap : attributeMaps.values()) {
            attributeValueMap.clear();
        }
    }

    /**
     * Removes all types (int, string, long etc.) of attributes with the specified name.
     * @param name The name.
     */
    public void removeAttribute(String name) {
        for(Map<String, ?> attributeValueMap : attributeMaps.values()) {
            clearAttribute(attributeValueMap, name);
        }
    }

    /**
     * Gets the string value of an attribute.
     * @param name The name of the attribute.  Not null.
     * @param defaultValue The default value for the attribute.  May be null.  This value will be returned if this
     * metadata object does not contain a string value for the specified attribute name.
     * @return Either the string value of the attribute with the specified name, or the value specified by the defaultValue
     * object if this metadata object does not contain a string value for the specified attribute name.
     * @throws NullPointerException if name is null.
     */
    public String getStringAttribute(String name, String defaultValue) {
        return getValue(stringAttributes, name, defaultValue);
    }

    /**
     * Sets the string value of an attribute.
     * @param name The name of the attribute.  Not null.
     * @param value The value of the attribute.  Not null.
     * @throws NullPointerException if the name parameter, or the value parameter is null.
     */
    public void setStringAttribute(String name, String value) {
        setValue(stringAttributes, name, value);
    }

    /**
     * Removes a string attribute with the specified name.
     * @param name The name of the attribute.  Not null.
     * @throws NullPointerException if the name parameter is null.
     */
    public void removeStringAttribute(String name) {
        clearAttribute(stringAttributes, name);
    }
    
    /**
     * Sets the integer value for the specified attribute name.
     * @param name The name of the attribute. Not null.
     * @param value The value of the attribute to set.
     * @throws NullPointerException if the name parameter is null.
     */
    public void setIntAttribute(String name, int value) {
        setValue(intAttributes, name, value);
    }

    /**
     * Gets the int value of an attribute.
     * @param name The name of the attribute.  Not null.
     * @param defaultValue The default value for the attribute.  May be null.  This value will be returned if this
     * metadata object does not contain an int value for the specified attribute name.
     * @return Either the int value of the attribute with the specified name, or the value specified by the defaultValue
     * object if this metadata object does not contain an int value for the specified attribute name.
     * @throws NullPointerException if name is null.
     */
    public Integer getIntAttribute(String name, Integer defaultValue) {
        return getValue(intAttributes, name, defaultValue);
    }


    /**
     * Removes an int attribute with the specified name.
     * @param name The name of the attribute.  Not null.
     * @throws NullPointerException if the name parameter is null.
     */
    public void removeIntAttribute(String name) {
        clearAttribute(intAttributes, name);
    }

    /**
     * Sets the long value for the specified attribute name.
     * @param name The name of the attribute. Not null.
     * @param value The value of the attribute to set.
     * @throws NullPointerException if the name parameter is null.
     */
    public void setLongAttribute(String name, long value) {
        setValue(longAttributes, name, value);
    }


    /**
     * Gets the long value of an attribute.
     * @param name The name of the attribute.  Not null.
     * @param defaultValue The default value for the attribute.  May be null.  This value will be returned if this
     * metadata object does not contain a long value for the specified attribute name.
     * @return Either the long value of the attribute with the specified name, or the value specified by the defaultValue
     * object if this metadata object does not contain a long value for the specified attribute name.
     * @throws NullPointerException if name is null.
     */
    public Long getLongAttribute(String name, Long defaultValue) {
        return getValue(longAttributes, name, defaultValue);
    }


    /**
     * Removes a long attribute with the specified name.
     * @param name The name of the attribute.  Not null.
     * @throws NullPointerException if the name parameter is null.
     */
    public void removeLongAttribute(String name) {
        clearAttribute(longAttributes, name);
    }

    /**
     * Sets the boolean value for the specified attribute name.
     * @param name The name of the attribute. Not null.
     * @param value The value of the attribute to set.
     * @throws NullPointerException if the name parameter is null.
     */
    public void setBooleanAttribute(String name, boolean value) {
        setValue(booleanAttributes, name, value);
    }

    /**
     * Gets the boolean value of an attribute.
     * @param name The name of the attribute.  Not null.
     * @param defaultValue The default value for the attribute.  May be null.  This value will be returned if this
     * metadata object does not contain a boolean value for the specified attribute name.
     * @return Either the boolean value of the attribute with the specified name, or the value specified by the defaultValue
     * object if this metadata object does not contain a boolean value for the specified attribute name.
     * @throws NullPointerException if name is null.
     */
    public Boolean getBooleanAttribute(String name, Boolean defaultValue) {
        return getValue(booleanAttributes, name, defaultValue);
    }


    /**
     * Removes a boolean attribute with the specified name.
     * @param name The name of the attribute.  Not null.
     * @throws NullPointerException if the name parameter is null.
     */
    public void removeBooleanAttribute(String name) {
        clearAttribute(booleanAttributes, name);
    }


    /**
     * Sets the double value for the specified attribute name.
     * @param name The name of the attribute. Not null.
     * @param value The value of the attribute to set.
     * @throws NullPointerException if the name parameter is null.
     */
    public void setDoubleAttribute(String name, double value) {
        setValue(doubleAttributes, name, value);
    }

    /**
     * Gets the double value of an attribute.
     * @param name The name of the attribute.  Not null.
     * @param defaultValue The default value for the attribute.  May be null.  This value will be returned if this
     * metadata object does not contain a double value for the specified attribute name.
     * @return Either the double value of the attribute with the specified name, or the value specified by the defaultValue
     * object if this metadata object does not contain a double value for the specified attribute name.
     * @throws NullPointerException if name is null.
     */
    public Double getDoubleAttribute(String name, Double defaultValue) {
        return getValue(doubleAttributes, name, defaultValue);
    }


    /**
     * Removes a double attribute with the specified name.
     * @param name The name of the attribute.  Not null.
     * @throws NullPointerException if the name parameter is null.
     */
    public void removeDoubleAttribute(String name) {
        clearAttribute(doubleAttributes, name);
    }


    /**
     * Sets the byte [] value for the specified attribute name.  This parameter will be copied so that modifications
     * to the passed in parameter are not reflected in the storage of the parameter in this metadata object.
     * @param name The name of the attribute. Not null.
     * @param value The value of the attribute to set.
     * @throws NullPointerException if the name parameter is null or if the value parameter is null.
     */
    public void setByteArrayAttribute(String name, byte [] value) {
        byte [] copy = new byte[value.length];
        System.arraycopy(value, 0, copy, 0, value.length);
        setValue(byteArrayAttributes, name, ByteBuffer.wrap(copy));
    }

    /**
     * Gets the byte [] value of an attribute.  This will be a copy of the actual byte [] stored in this metadata
     * object, hence modifications to the return value will not affect the stored value.
     * @param name The name of the attribute.  Not null.
     * @param defaultValue The default value for the attribute.  May be null.  This value will be returned if this
     * metadata object does not contain a byte [] value for the specified attribute name.
     * @return Either the byte [] value of the attribute with the specified name, or the value specified by the defaultValue
     * object if this metadata object does not contain a byte [] value for the specified attribute name.
     * @throws NullPointerException if name is null.
     */
    public byte [] getByteArrayAttribute(String name, byte [] defaultValue) {
        ByteBuffer buffer = getValue(byteArrayAttributes, name, null);
        if(buffer != null) {
            byte [] result = new byte[buffer.capacity()];
            System.arraycopy(buffer.array(), 0, result, 0, buffer.capacity());
            return result;
        }
        else {
            return defaultValue;
        }
    }


    /**
     * Removes a byte [] attribute with the specified name.
     * @param name The name of the attribute.  Not null.
     * @throws NullPointerException if the name parameter is null.
     */
    public void removeByteArrayAttribute(String name) {
        clearAttribute(byteArrayAttributes, name);
    }
    
    
    /**
     * Gets the {@link OWLObject} list value of an attribute.
     * @param name The name of the attribute.  Not null.
     * @param defaultValue The default value for the attribute.  May be null.  This value will be returned if this
     * metadata object does not contain an {@link OWLObject} list value for the specified attribute name.
     * @return Either the {@link OWLObject} list value of the attribute with the specified name, or the value specified by the defaultValue
     * object if this metadata object does not contain an {@link OWLObject} list value for the specified attribute name.
     * @throws NullPointerException if name is null.
     */
    public List<OWLObject> getOWLObjectListAttribute(String name, List<OWLObject> defaultValue) {
        List<OWLObject> result = getValue(owlObjectAttributes, name, defaultValue);
        return new ArrayList<OWLObject>(result);
    }

    /**
     * Sets the {@link OWLObject} list values for an attribute.
     * @param name The name of the attribute.  Not null.
     * @param value The value of the attribute.  Not null.
     * @throws NullPointerException if name is null or value is null.
     */
    public void setOWLObjectListAttribute(String name, List<OWLObject> value) {
        if(value == null) {
            throw new NullPointerException("value must not be null");
        }
        List<OWLObject> valueCopy = new ArrayList<OWLObject>(value);
        setValue(owlObjectAttributes, name, valueCopy);
    }


    /**
     * Removes an {@link OWLObject} list attribute with the specified name.
     * @param name The name of the attribute.  Not null.
     * @throws NullPointerException if the name parameter is null.
     */
    public void removeOWLObjectListAttribute(String name) {
        clearAttribute(owlObjectAttributes, name);
    }

    

    public void write(DataOutput dataOutput) throws IOException {
        byte mapCount = 0;

        for(AttributeMap<?> map : attributeMaps.values()) {
            if(!map.isEmpty()) {
                mapCount++;
            }
        }
        
        dataOutput.writeByte(mapCount);
        
        if(mapCount == 0) {
            return;
        }
        
        for(AttributeMap<?> map : attributeMaps.values()) {
            if(!map.isEmpty()) {
                dataOutput.writeByte(map.getTypeMarker().getMarker());
                map.write(dataOutput, LookupTable.emptyLookupTable());
            }
        }
    }



    private void read(DataInput dataInput, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
        byte mapCount = dataInput.readByte();
        if(mapCount == 0) {
            return;
        }
        for(int i = 0; i < mapCount; i++) {
            byte typeMarker = dataInput.readByte();
            AttributeMap<?> map = getMap(typeMarker);
            map.read(dataInput, LookupTable.emptyLookupTable(), dataFactory);
        }
        
    }
    
    
    private AttributeMap<?> getMap(byte typeMarker) {
        TypeMarker marker = TypeMarker.getTypeMarker(typeMarker);
        return attributeMaps.get(marker);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Metadata(");
        for(AttributeMap<?> map : attributeMaps.values()) {
            for(String attributeName : map.keySet()) {
                sb.append(" PropertyValue(");
                sb.append(attributeName);
                sb.append(": ");
                sb.append(map.get(attributeName));
                sb.append(") ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private abstract class AttributeMap<O> implements Map<String, O>{

        public static final int DEFAULT_INITIAL_SIZE = 2;
        
        private Map<String, O> delegate = null;

        protected AttributeMap() {
        }
        
        public abstract TypeMarker getTypeMarker();

        public void read(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
            short length = dataInput.readShort();
            for(int i = 0; i < length; i++) {
                String name = dataInput.readUTF();
                O value = readValue(dataInput, lookupTable, dataFactory);
                put(name, value);
            }
        }
        
        public void write(DataOutput dataOutput, LookupTable lookupTable) throws IOException {
            dataOutput.writeShort(size());
            for(String attributeName : keySet()) {
                dataOutput.writeUTF(attributeName);
                O value = get(attributeName);
                writeValue(value, dataOutput, lookupTable);
            }
        }
        
        protected abstract O readValue(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException;
        
        protected abstract void writeValue(O value, DataOutput dataOutput, LookupTable lookupTable) throws IOException;


        public int size() {
            return delegate == null ? 0 : delegate.size();
        }

        public boolean isEmpty() {
            return delegate == null ? true : delegate.isEmpty();
        }

        public boolean containsKey(Object key) {
            return delegate == null ? false : delegate.containsKey(key);
        }

        public boolean containsValue(Object value) {
            return delegate == null ? false : delegate.containsValue(value);
        }

        public O get(Object key) {
            return delegate == null ? null : delegate.get(key);
        }

        public O put(String key, O value) {
            getDelegate();
            return delegate.put(key, value);
        }

        private Map<String, O> getDelegate() {
            if(delegate == null) {
                delegate = createDelegate();
            }
            return delegate;
        }

        private LinkedHashMap<String, O> createDelegate() {
            return new LinkedHashMap<String, O>();
        }

        public O remove(Object key) {
            if(delegate == null) {
                return null;
            }
            return delegate.remove(key);
        }

        public void putAll(Map<? extends String, ? extends O> m) {
            getDelegate().putAll(m);
        }

        public void clear() {
            if(delegate != null) {
                delegate.clear();
            }
        }

        public Set<String> keySet() {
            return delegate == null ? Collections.<String>emptySet() : delegate.keySet();
        }

        public Collection<O> values() {
            return delegate == null ? Collections.<O>emptySet() : delegate.values();
        }

        public Set<Entry<String, O>> entrySet() {
            return delegate == null ? Collections.<Entry<String, O>>emptySet() : delegate.entrySet();
        }
    }
    
    

    
    private class StringMap extends AttributeMap<String> {

        @Override
        public TypeMarker getTypeMarker() {
            return TypeMarker.STRING;
        }

        @Override
        protected String readValue(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException {
            return dataInput.readUTF();
        }

        @Override
        protected void writeValue(String value, DataOutput dataOutput, LookupTable lookupTable) throws IOException {
            dataOutput.writeUTF(value);
        }
    }
    
    
    private class IntegerMap extends AttributeMap<Integer> {

        @Override
        public TypeMarker getTypeMarker() {
            return TypeMarker.INTEGER;
        }

        @Override
        protected Integer readValue(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException {
            return dataInput.readInt();
        }

        @Override
        public void writeValue(Integer value, DataOutput dataOutput, LookupTable lookupTable) throws IOException {
            dataOutput.writeInt(value);
        }
    }
    
    
    private class LongMap extends AttributeMap<Long> {

        @Override
        public TypeMarker getTypeMarker() {
            return TypeMarker.LONG;
        }

        @Override
        public Long readValue(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException {
            return dataInput.readLong();
        }

        @Override
        public void writeValue(Long value, DataOutput dataOutput, LookupTable lookupTable) throws IOException {
            dataOutput.writeLong(value);
        }
    }
    
    
    private class BooleanMap extends AttributeMap<Boolean> {

        @Override
        public TypeMarker getTypeMarker() {
            return TypeMarker.BOOLEAN;
        }

        @Override
        public Boolean readValue(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException {
            return dataInput.readBoolean();
        }

        @Override
        public void writeValue(Boolean value, DataOutput dataOutput, LookupTable lookupTable) throws IOException {
            dataOutput.writeBoolean(value);
        }
    }
    
    
    private class DoubleMap extends AttributeMap<Double> {

        @Override
        public TypeMarker getTypeMarker() {
            return TypeMarker.DOUBLE;
        }

        @Override
        public Double readValue(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException {
            return dataInput.readDouble();
        }

        @Override
        public void writeValue(Double value, DataOutput dataOutput, LookupTable lookupTable) throws IOException {
            dataOutput.writeDouble(value);
        }
    }
    
    private class OWLObjectListMap extends AttributeMap<List<OWLObject>> {

        @Override
        public TypeMarker getTypeMarker() {
            return TypeMarker.OWL_OBJECT_LIST;
        }

        @Override
        public List<OWLObject> readValue(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException, BinaryOWLParseException {
            int size = dataInput.readInt();
            if(size == 0) {
                return Collections.emptyList();
            }
            List<OWLObject> list = new ArrayList<OWLObject>(size + 2);
            for(int i = 0; i < size; i++) {
                OWLObject object = OWLObjectBinaryType.read(dataInput, lookupTable, dataFactory);
                list.add(object);
            }
            return list;
        }

        @Override
        public void writeValue(List<OWLObject> value, DataOutput dataOutput, LookupTable lookupTable) throws IOException {
            dataOutput.writeInt(value.size());
            for(OWLObject object : value) {
                OWLObjectBinaryType.write(object, dataOutput, lookupTable);
            }
        }
    }
    
    private class ByteArrayMap extends AttributeMap<ByteBuffer> {

        @Override
        public TypeMarker getTypeMarker() {
            return TypeMarker.BYTE_ARRAY;
        }

        @Override
        protected ByteBuffer readValue(DataInput dataInput, LookupTable lookupTable, OWLDataFactory dataFactory) throws IOException {
            int length = dataInput.readInt();
            ByteBuffer buffer = ByteBuffer.allocate(length);
            dataInput.readFully(buffer.array());
            return buffer;
        }

        @Override
        protected void writeValue(ByteBuffer value, DataOutput dataOutput, LookupTable lookupTable) throws IOException {
            int length = value.capacity();
            dataOutput.writeInt(length);
            dataOutput.write(value.array());
        }
    }
    
    
    
    
    private static BinaryOWLMetadata EMPTY_METADATA = new BinaryOWLMetadata() {
        @Override
        public void setStringAttribute(String name, String value) {
            throwUnmodifiableException();
        }

        @Override
        public void setIntAttribute(String name, int value) {
            throwUnmodifiableException();
        }

        @Override
        public void setLongAttribute(String name, long value) {
            throwUnmodifiableException();
        }

        @Override
        public void setBooleanAttribute(String name, boolean value) {
            throwUnmodifiableException();
        }

        @Override
        public void setDoubleAttribute(String name, double value) {
            throwUnmodifiableException();
        }

        @Override
        public void setOWLObjectListAttribute(String name, List<OWLObject> value) {
            throwUnmodifiableException();
        }
        
        private void throwUnmodifiableException() {
            throw new RuntimeException("Immutable metadata");
        }
    };
    
    
    public static BinaryOWLMetadata emptyMetadata() {
        return EMPTY_METADATA;
    }
}
