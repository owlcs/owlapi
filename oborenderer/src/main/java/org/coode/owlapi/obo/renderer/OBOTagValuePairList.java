package org.coode.owlapi.obo.renderer;

import org.coode.owlapi.obo.parser.OBOVocabulary;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi.util.IRIShortFormProvider;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
/*
* Copyright (C) 2008, University of Manchester
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
 * Author: Nick Drummond<br>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 19, 2008<br><br>
 * <p/>
 * An ordered rendering of the Tag Value Pairs that also supports:
 * - default values
 * - unknown tags (which are rendered at the end of the known tags)
 * - extraction of TVPs from annotations
 */
public class OBOTagValuePairList {


    private Map<String, Set<String>> knownTVPs = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> unknownTVPs = new HashMap<String, Set<String>>();

    private List<OBOVocabulary> vocab;

    private IRIShortFormProvider iriSFP;

    private Map<IRI, String> defaults = new HashMap<IRI, String>();

    private Writer writer;


    /**
     * @param knownVocab the set of tags that are known by this generator
     */
    public OBOTagValuePairList(List<OBOVocabulary> knownVocab) {
        this.vocab = knownVocab;
        iriSFP = new SimpleIRIShortFormProvider();
    }


    public void visit(OWLAnnotation annot) {
        addPair(annot.getProperty().getIRI(), ((OWLLiteral) annot.getValue()).getLiteral());
    }


    public void addPair(OBOVocabulary tag, String value) {
        addPair(tag.getIRI(), value);
    }


    public void addPair(IRI tag, String value) {
        boolean found = false;
        for (OBOVocabulary obo : vocab) {
            if (tag.equals(obo.getIRI())) {
                addPair(obo.getName(), value, knownTVPs);
                found = true;
                break;
            }
        }
        if (!found) {
            final String name = iriSFP.getShortForm(tag);
            addPair(name, value, unknownTVPs);
        }
    }


    public void setPair(OBOVocabulary key, String value) {
        knownTVPs.remove(key.getName());
        addPair(key.getIRI(), value);
    }


    public void setDefault(OBOVocabulary tag, String value) {
        defaults.put(tag.getIRI(), value);
    }


    public void setDefault(IRI tag, String value) {
        defaults.put(tag, value);
    }


    public Set<String> getValues(OBOVocabulary key) {
        Set<String> values = knownTVPs.get(key.getName());
        if (values == null) {
            values = Collections.EMPTY_SET;
        }
        return values;
    }


    private void addPair(String tag, String value, Map<String, Set<String>> map) {
        Set<String> set = map.get(tag);
        if (set == null) {
            set = new HashSet<String>(1);
            map.put(tag, set);
        }
        set.add(value);
    }


    public void write(Writer writer) {
        this.writer = writer;

        // write tags out in order
        for (OBOVocabulary tag : vocab) {
            Set<String> values = knownTVPs.get(tag.getName());
            if (values == null) {
                String def = defaults.get(tag.getIRI());
                if (def != null) {
                    values = Collections.singleton(def);
                }
            }
            if (values != null) {
                for (String value : values) {
                    writeTagValuePair(tag, value);
                }
            }
        }

        // write additional tags in no specified order
        for (String unknownTag : unknownTVPs.keySet()) {
            for (String value : unknownTVPs.get(unknownTag)) {
                writeTagValuePair(unknownTag, value);
            }
        }
    }


    private void writeTagValuePair(OBOVocabulary key, String value) {
        writeTagValuePair(key.getName(), value);
    }


    private void writeTagValuePair(String key, String value) {
        if (key != null && value != null) {
            write(key);
            write(": ");
            write(value);
            writeNewLine();
        }
    }


    private void writeNewLine() {
        write("\n");
    }


    private void write(String s) {
        try {
            writer.write(s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
