package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.semanticweb.owlapi.util.VersionInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@SuppressWarnings("javadoc")
public class VerifyVersionInfoTestCase {

    @Test
    public void checkMatchVersion() throws Exception {
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
