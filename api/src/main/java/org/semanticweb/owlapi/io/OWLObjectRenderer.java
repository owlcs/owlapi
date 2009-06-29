package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 08-Oct-2007<br><br>
 *
 * A general purpose interface, implementations of which can be used
 * to renderer OWL objects (e.g. class expressions, axioms etc.) for
 * presentation in user interfaces, colsole writing etc. etc.  Many
 * ontology renderers may also choose to implement this interface.
 */
public interface OWLObjectRenderer {

    /**
     * Sets the short form provider, which determines the short form
     * that should be used for entities.
     * @param shortFormProvider The short form provider to be used.
     */
    void setShortFormProvider(ShortFormProvider shortFormProvider);

    /**
     * Renders the specified object.
     * @param object The object to be rendered.
     * @return A string that represents the rendering
     * of the object.
     */
    String render(OWLObject object);
}
