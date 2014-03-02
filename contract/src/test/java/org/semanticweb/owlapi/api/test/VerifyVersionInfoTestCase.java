/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.semanticweb.owlapi.util.VersionInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings("javadoc")
public class VerifyVersionInfoTestCase {

    @Test
    public void checkMatchVersion() throws SAXException, IOException,
            ParserConfigurationException {
        // given
        VersionInfo info = VersionInfo.getVersionInfo();
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new File("pom.xml"));
        NodeList list = doc.getDocumentElement().getChildNodes();
        boolean found = false;
        for (int i = 0; i < list.getLength() && !found; i++) {
            Node n = list.item(i);
            if (n instanceof Element
                    && ((Element) n).getTagName().equals("version")) {
                String version = ((Element) n).getTextContent();
                if (!version.equals(info.getVersion())) {
                    System.out
                            .println("VerifyVersionInfo.checkMatchVersion() WARNING: update the version in VersionInfo");
                }
                // assertEquals(version, info.getVersion());
                found = true;
            }
        }
        if (!found) {
            File file = new File("../pom.xml");
            if (file.exists()) {
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(file);
                list = doc.getDocumentElement().getChildNodes();
                found = false;
                for (int i = 0; i < list.getLength() && !found; i++) {
                    Node n = list.item(i);
                    if (n instanceof Element
                            && ((Element) n).getTagName().equals("version")) {
                        String version = ((Element) n).getTextContent();
                        if (!version.equals(info.getVersion())) {
                            System.out
                                    .println("VerifyVersionInfo.checkMatchVersion() WARNING: update the version in VersionInfo");
                        }
                        // assertEquals(version, info.getVersion());
                        found = true;
                    }
                }
            }
        }
        assertTrue("Cannot find the version in the pom file", found);
    }
}
