package org.coode.owlapi.obo.renderer;

import java.util.List;

/**
 * Author: Nick Drummond<br>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 19, 2008<br><br>
 */
public interface OBOExceptionHandler {

    public void addException(OBOStorageException exception);

    public List<OBOStorageException> getExceptions();

}
