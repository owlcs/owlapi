package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLEntity;
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
 * Date: 18-Apr-2007<br><br>
 * <p/>
 * A very simple short form provider which is intended to provide
 * human readable display names for entities.  The following strategy
 * is used:
 * 1) If the entity URI has a fragment then that fragment
 * is returned e.g.  http://an.other.com#A would have a short form of
 * "A".
 * 2) If the entity URI does not have a fragment then the last
 * segment of the URI path is used e.g.  http://an.other.com/A/B would
 * have a short form of "B".
 * 3) If the entity URI does not have a path
 * then the full URI is returned as a string.
 */
public class SimpleShortFormProvider implements ShortFormProvider {

    private SimpleIRIShortFormProvider uriShortFormProvider = new SimpleIRIShortFormProvider();

    public String getShortForm(OWLEntity entity) {
        return uriShortFormProvider.getShortForm(entity.getIRI());
    }


    public void dispose() {
        // Nothing to do here
    }
}
