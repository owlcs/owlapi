package org.semanticweb.owl.inference;

import org.semanticweb.owl.model.OWLOntologyManager;
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
 * Date: 20-Jun-2007<br><br>
 *
 * Represents a reasoner creation point.
 */
public interface OWLReasonerFactory {

    /**
     * Gets the name of the reasoner that is created by this factory.
     * @return A string that represents the name of the reasoner.
     */
    String getReasonerName();


    /**
     * Creates a reasoner that uses the specified manager.
     * @param manager The manager to be used (may be used by the
     * reasoner to obtain a data factory etc.)
     * @return A new reasoner.
     */
    OWLReasoner createReasoner(OWLOntologyManager manager);
}
