package uk.ac.manchester.cs.bhig.util;

import org.coode.xml.XMLWriter;
import org.coode.xml.XMLWriterImpl;
import org.coode.xml.XMLWriterNamespaceManager;

import java.io.*;
import java.util.Map;
import java.util.IdentityHashMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 08-Oct-2009
 */
public class OmniGraffleWriter<N> {

    private Tree<N> tree;
    private static final String PLIST_ELEMENT_NAME = "plist";
    private static final String DICT_ELEMENT_NAME = "dict";
    private static final String KEY_ELEMENT_NAME = "key";
    private static final String GRAPHICS_LIST = "GraphicsList";
    private static final String ARRAY = "array";

    private Map<Object, Integer> nodeIDMap = new IdentityHashMap<Object, Integer>();

    private Map<LineHolder, Integer> lineIDMap = new HashMap<LineHolder, Integer>();

    private int counter = 0;

    public OmniGraffleWriter(Tree<N> tree) {
        this.tree = tree;
    }

    public void write(Writer writer) throws IOException {
        XMLWriterNamespaceManager nsm = new XMLWriterNamespaceManager("");
        XMLWriter xmlWriter = new XMLWriterImpl(writer, nsm);
        xmlWriter.writeStartElement(PLIST_ELEMENT_NAME);
        xmlWriter.writeStartElement(DICT_ELEMENT_NAME);
        xmlWriter.writeStartElement(KEY_ELEMENT_NAME);
        xmlWriter.writeTextContent(GRAPHICS_LIST);
        xmlWriter.writeEndElement();
        xmlWriter.writeStartElement(ARRAY);
        writeTreeElement(tree, xmlWriter);
        xmlWriter.writeEndElement();
        xmlWriter.writeEndElement();
        xmlWriter.writeEndElement();
    }

    private void writeTreeElement(Tree<N> tree, XMLWriter writer) throws IOException {
        writeKeyValue(null, getNodeValuesMap(tree), writer);
        for (Tree<N> child : tree.getChildren()) {
            writeTreeElement(child, writer);
            writeKeyValue(null, getLineValuesMap(tree, child), writer);
//            writeKeyValue(null, getLineLabelValuesMap(tree, child), writer);
        }
    }

    private Map<String, Object> getNodeValuesMap(Tree tree) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Bounds", "{{10, 10}, {10, 10}}");
        map.put("Class", "ShapedGraphic");
        map.put("FitText", "YES");
        map.put("Flow", "Resize");
        map.put("ID", getID(tree));
        map.put("Shape", "Rectangle");
        Map<String, Object> textMap = new HashMap<String, Object>();
        textMap.put("Text", tree.getUserObject().toString());
        map.put("Text", textMap);
        map.put("Wrap", "NO");
        return map;
    }

    private Map<String, Object> getLineLabelValuesMap(Tree parent, Tree child) {
//                <dict>
//                    <key>Bounds</key>
//                    <string>{{10, 10}, {10, 10}}</string>
//                    <key>Class</key>
//                    <string>ShapedGraphic</string>
//                    <key>FitText</key>
//                    <string>YES</string>
//                    <key>ID</key>
//                    <integer>5</integer>
//                    <key>Line</key>
//                    <dict>
//                        <key>ID</key>
//                        <integer>4</integer>
//                        <key>Position</key>
//                        <real>0.5</real>
//                        <key>RotationType</key>
//                        <integer>0</integer>
//                    </dict>
//                    <key>Shape</key>
//                    <string>Rectangle</string>
//                    <key>Text</key>
//                    <dict>
//                        <key>Text</key>
//                        <string>{XYZ}</string>
//                    </dict>
//                </dict>


        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Bounds", "{{10, 10}, {10, 10}}");
        map.put("Class", "ShapedGraphic");
        map.put("FitText", "YES");
        map.put("ID", getID(tree.getUserObject()));
        Map<String, Object> lineMap = new HashMap<String, Object>();
        lineMap.put("ID", getLineID(parent, child));
        lineMap.put("Position", 0.5);
        lineMap.put("RotationType", "0");
        map.put("Shape", "Rectangle");
        map.put("Line", lineMap);
        Map<String, Object> textMap = new HashMap<String, Object>();
        textMap.put("Text", parent.getEdge(child).toString());
        map.put("Text", textMap);
        map.put("Wrap", "NO");
        return map;
    }

    private Map<String, Object> getLineValuesMap(Tree parent, Tree child) {
//        <dict>
//                    <key>Class</key>
//                    <string>LineGraphic</string>
//                    <key>Head</key>
//                    <dict>
//                        <key>ID</key>
//                        <integer>3</integer>
//                    </dict>
//                    <key>ID</key>
//                    <integer>4</integer>
//                    <key>Tail</key>
//                    <dict>
//                        <key>ID</key>
//                        <integer>2</integer>
//                    </dict>
//                </dict>
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("Class", "LineGraphic");
        map.put("ID", getLineID(parent, child));
        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("ID", getID(child));
        map.put("Head", headMap);
        Map<String, Object> tailMap = new HashMap<String, Object>();
        tailMap.put("ID", getID(parent));
        map.put("Tail", tailMap);
        return map;

    }

    private int getID(Object obj) {
        Integer id = nodeIDMap.get(obj);
        if (id == null) {
            id = counter;
            counter++;
            nodeIDMap.put(obj, id);
        }
        return id;
    }

    private int getLineID(Tree parent, Tree child) {
        LineHolder holder = new LineHolder(parent, child);
        Integer id = lineIDMap.get(holder);
        if (id == null) {
            id = counter;
            counter++;
            lineIDMap.put(holder, id);
        }
        return id;
    }


    private void writeKeyValue(String key, Object value, XMLWriter writer) throws IOException {
        if (key != null) {
            writer.writeStartElement(KEY_ELEMENT_NAME);
            writer.writeTextContent(key);
            writer.writeEndElement();
        }
        if (value instanceof String) {
            writer.writeStartElement("string");
            writer.writeTextContent(value.toString());
            writer.writeEndElement();
        }
        else if (value instanceof Integer) {
            writer.writeStartElement("integer");
            writer.writeTextContent(value.toString());
            writer.writeEndElement();
        }
        else if (value instanceof Float || value instanceof Double) {
            writer.writeStartElement("real");
            writer.writeTextContent(value.toString());
            writer.writeEndElement();
        }
        else if (value instanceof Map) {
            Map<String, Object> values = (Map<String, Object>) value;
            writer.writeStartElement(DICT_ELEMENT_NAME);
            for (String k : values.keySet()) {
                writeKeyValue(k, values.get(k), writer);
            }
            writer.writeEndElement();
        }

    }


    private class LineHolder<N> {

        private Tree<N> parent;

        private Tree<N> child;

        private LineHolder(Tree<N> parent, Tree<N> child) {
            this.parent = parent;
            this.child = child;
        }


        @Override
        public int hashCode() {
            return parent.hashCode() * 37 + child.hashCode();
        }


        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LineHolder)) {
                return false;
            }
            LineHolder other = (LineHolder) obj;
            return other.parent.equals(this.parent) && other.child.equals(this.child);
        }
    }

    public static void main(String[] args) {
        try {
            MutableTree<String> root = new MutableTree<String>("Root");
            constructDummy(root, 0);
            OmniGraffleWriter writer = new OmniGraffleWriter(root);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("/tmp/test2.graffle"));
            writer.write(outputStreamWriter);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int depth = 10;
    private static void constructDummy(MutableTree tree, int curDepth) {
        curDepth++;
        for (int i = 0; i < 3; i++) {
            MutableTree<String> child0 = new MutableTree<String>("Child" + i);
            tree.addChild(child0, "Edge" + i);
            if(curDepth < depth) {
                constructDummy(child0, curDepth);
            }
        }
    }
}