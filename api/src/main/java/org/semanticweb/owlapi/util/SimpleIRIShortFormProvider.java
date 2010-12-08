package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.IRI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Dec-2007<br><br>
 *
 * A URI short form provider that generates short forms from
 * the URI fragment if there is one, followed by the URI last
 * path component if there is one.
 */
public class SimpleIRIShortFormProvider implements IRIShortFormProvider {


    public String getShortForm(IRI iri) {
        String rendering = iri.getFragment();
        if (rendering != null) {
            return rendering;
        }
        else {
            String s = iri.toString();
            int lastSlashIndex = s.lastIndexOf('/');
            if(lastSlashIndex != -1 && lastSlashIndex != s.length() - 1) {
                return s.substring(lastSlashIndex + 1);
            }
        }
        return iri.toQuotedString();
    }
}
