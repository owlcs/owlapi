package org.semanticweb.owlapi.util;

import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2007<br><br>
 *
 * Converts the entity URI fragment or last path element if the
 * fragment is not present to Camel Case.  For example, if
 * the URI is http://another.com/pathA/pathB#has_part then this will
 * be converted to http://another.com/pathA/pathB#hasPart
 */
public class OWLEntityURIUnderscores2CamelBackConverterStrategy implements OWLEntityURIConverterStrategy {

    private Map<IRI, IRI> iriMap;


    public OWLEntityURIUnderscores2CamelBackConverterStrategy() {
        iriMap = new HashMap<IRI, IRI>();
    }


    public IRI getConvertedIRI(OWLEntity entity) {
        IRI convIRI = iriMap.get(entity.getIRI());
        if(convIRI == null) {
            convIRI = convert(entity.getIRI());
            iriMap.put(entity.getIRI(), convIRI);
        }
        return convIRI;
    }

    private static IRI convert(IRI iri) {
        String iriString = iri.toString();
        String fragment = iri.toURI().getFragment();
        if(fragment != null) {
            String base = iriString.substring(0, iriString.length() - fragment.length());
            String camelCaseFragment = toCamelCase(fragment);
            return IRI.create(base + camelCaseFragment);
        }
        String path = iri.toURI().getPath();
        if(path.length() > 0) {
            int index = path.lastIndexOf('/');
            String lastPathElement = path.substring(index + 1, path.length());
            String camelCaseElement = toCamelCase(lastPathElement);
            String base = iriString.substring(0, iriString.lastIndexOf('/') + 1);
            return IRI.create(base + camelCaseElement);
        }
        return iri;
    }



    private static String toCamelCase(String s) {
        StringBuilder sb = new StringBuilder();
        boolean nextIsUpperCase = false;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == '_') {
                nextIsUpperCase = true;
            }
            else {
                if(nextIsUpperCase) {
                    sb.append(Character.toUpperCase(ch));
                    nextIsUpperCase = false;
                }
                else {
                    sb.append(ch);
                }

            }
        }
        return sb.toString();
    }


}
