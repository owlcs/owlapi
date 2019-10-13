/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.model;

import org.semanticweb.owlapi.model.parameters.ChangeApplied;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.5
 */
@FunctionalInterface
public interface HasApplyChange {

    /**
     * A convenience method that applies just one change to an ontology. When this method is used
     * through an {@code OWLOntologyManager} implementation, the instance used should be the one
     * that the ontology returns through the {@code getOWLOntologyManager()} call. The reason is
     * that some changes, e.g., change of ontology id and change of imports directives, affect data
     * that only that manager instance knows about, such as the imports closure; changes of ontology
     * id through the wrong manager will make the ontology unreachable through its new id in the
     * manager associated with the ontology. Configuration for loading and saving parameters is also
     * held by the manager, if not explicitly specified for the ontology. While the change might be
     * successful, other bugs might be made apparent later.
     *
     * @param change The change to be applied
     * @return ChangeApplied.SUCCESSFULLY if the change is applied with success,
     *         ChangeApplied.UNSUCCESSFULLY otherwise.
     * @throws OWLOntologyChangeException If the change could not be applied. See subclasses of
     *         ontology change exception for more specific details.
     * @throws OWLOntologyRenameException If one or more of the changes is an instance of
     *         {@link org.semanticweb.owlapi.model.SetOntologyID} where the new
     *         {@link org.semanticweb.owlapi.model.OWLOntologyID} already belongs to an ontology
     *         managed by this manager.
     */
    ChangeApplied applyChange(OWLOntologyChange change);
}
