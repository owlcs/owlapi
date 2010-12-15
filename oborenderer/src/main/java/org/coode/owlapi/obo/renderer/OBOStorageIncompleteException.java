package org.coode.owlapi.obo.renderer;

import java.util.ArrayList;
import java.util.List;

import org.semanticweb.owlapi.io.OWLRendererException;

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Apr 9, 2009<br><br>
 */
public class OBOStorageIncompleteException extends OWLRendererException {

    private List<OBOStorageException> exceptions;


    public OBOStorageIncompleteException(List<OBOStorageException> exceptions) {
        super("Warning: OBO storage incomplete (" + exceptions.size() + " errors)");
        this.exceptions = exceptions;
    }


    @Override
	public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());
        for (OBOStorageException e : exceptions) {
            sb.append("\n");
            sb.append(e.getMessage());
        }
        return sb.toString();
    }


    public List<OBOStorageException> getCauses() {
        return new ArrayList<OBOStorageException>(exceptions);
    }
}
