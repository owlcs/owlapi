package uk.ac.manchester.cs.owl.inference.dig11;

import org.semanticweb.owl.model.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
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
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21-Nov-2006<br><br>
 */
public class DIGQueryResponseImpl implements DIGQueryResponse {

    private Element element;

    private OWLDataFactory factory;


    public DIGQueryResponseImpl(OWLDataFactory kb) {
        this.factory = kb;
    }


    public void setElement(Element element) {
        this.element = element;
    }


    public String getID() {
        return element.getAttribute("id");
    }


    public Set<Set<OWLClass>> getConceptSets() throws DIGReasonerException {
        NodeList synonymsList = element.getElementsByTagName(Vocab.SYNONYMS);
        Set<Set<OWLClass>> conceptSet = new HashSet<Set<OWLClass>>(synonymsList.getLength());
        for (int i = 0; i < synonymsList.getLength(); i++) {
            final NodeList catomList = ((Element) synonymsList.item(i)).getElementsByTagName(Vocab.CATOM);
            Set<OWLClass> equivalents = new HashSet<OWLClass>();
            for (int j = 0; j < catomList.getLength(); j++) {
                String name = ((Element) catomList.item(j)).getAttribute("name");
                final OWLClass aClass = factory.getOWLClass(URI.create(name));
                if (aClass != null) {
                    equivalents.add(aClass);
                }
            }
            if (((Element) synonymsList.item(i)).getElementsByTagName(Vocab.TOP).getLength() != 0) {
                equivalents.add(factory.getOWLThing());
            }
            conceptSet.add(equivalents);
        }

        return conceptSet;
    }


    public Set<Set<OWLObjectProperty>> getRoleSets() throws DIGReasonerException {
        NodeList synonymsList = element.getElementsByTagName(Vocab.SYNONYMS);
        Set<Set<OWLObjectProperty>> conceptSet = new HashSet<Set<OWLObjectProperty>>(synonymsList.getLength());
        for (int i = 0; i < synonymsList.getLength(); i++) {
            final NodeList catomList = ((Element) synonymsList.item(i)).getElementsByTagName(Vocab.RATOM);
            Set<OWLObjectProperty> equivalents = new HashSet<OWLObjectProperty>();
            for (int j = 0; j < catomList.getLength(); j++) {
                String name = ((Element) catomList.item(j)).getAttribute("name");
                final OWLObjectProperty prop = factory.getOWLObjectProperty(URI.create(name));
                if (prop != null) {
                    equivalents.add(prop);
                }
            }
            conceptSet.add(equivalents);
        }

        return conceptSet;
    }


    public Set<OWLIndividual> getIndividuals() throws DIGReasonerException {
        Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();
        NodeList individualElementList = element.getElementsByTagName(Vocab.INDIVIDUAL);
        for (int i = 0; i < individualElementList.getLength(); i++) {
            final Element individualElement = (Element) individualElementList.item(i);
            final OWLIndividual curInd = factory.getOWLIndividual(URI.create(individualElement.getAttribute("name")));
            if (curInd != null) {
                individuals.add(curInd);
            }
        }
        return individuals;
    }


    public boolean getBoolean() {
        String val = element.getTagName();
        boolean b = true;
        if (val.equals("false")) {
            b = false;
        }
        return b;
    }
}
