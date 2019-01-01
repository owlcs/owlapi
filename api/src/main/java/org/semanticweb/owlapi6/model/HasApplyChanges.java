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
package org.semanticweb.owlapi6.model;

import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi6.model.parameters.ChangeApplied;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.5
 */
public interface HasApplyChanges extends HasManager {

    /**
     * Apply ontology changes. Note: each change refers to the ontology it applies to, so that
     * ontology will handle the actual change application, even if the change is passed to a
     * different ontology for implementation. So, for example, a change to an ontology included in
     * the imports closure of a root ontology can be applied by an application which only refers to
     * the root ontology, without the need to navigate the imports closure.
     *
     * @param changes The changes to be applied.
     * @return ChangeReport containing the results of the changes.
     * @throws OWLOntologyChangeException If one or more of the changes could not be applied.
     */
    default ChangeReport applyChanges(OWLOntologyChange... changes) {
        return applyChanges(Arrays.stream(changes));
    }

    /**
     * Apply ontology changes. Note: each change refers to the ontology it applies to, so that
     * ontology will handle the actual change application, even if the change is passed to a
     * different ontology for implementation. So, for example, a change to an ontology included in
     * the imports closure of a root ontology can be applied by an application which only refers to
     * the root ontology, without the need to navigate the imports closure.
     *
     * @param changes The changes to be applied.
     * @return ChangeReport containing the results of the changes.
     * @throws OWLOntologyChangeException If one or more of the changes could not be applied.
     */
    default ChangeReport applyChanges(Collection<? extends OWLOntologyChange> changes) {
        if (changes.isEmpty()) {
            return new ChangeReport(Collections.emptyList(), Collections.emptyList());
        }
        try {
            getOWLOntologyManager().broadcastImpendingChanges(changes);
            boolean rollbackRequested = false;

            getOWLOntologyManager().fireBeginChanges(changes.size());
            List<ChangeApplied> results = new ArrayList<>();
            for (OWLOntologyChange change : changes) {
                // once rollback is requested by a failed change, do not carry
                // out any more changes
                if (!rollbackRequested) {
                    assert change != null;
                    ChangeApplied enactChangeApplication = change.getOntology().enactChange(change);
                    results.add(enactChangeApplication);
                    if (enactChangeApplication == ChangeApplied.UNSUCCESSFULLY) {
                        rollbackRequested = true;
                    }
                    getOWLOntologyManager().fireChangeApplied(change);
                }
            }
            ChangeReport report = new ChangeReport(results, new ArrayList<>(changes));
            if (rollbackRequested) {
                report = rollBackChanges(report);
            }
            getOWLOntologyManager().fireEndChanges();
            if (!rollbackRequested) {
                List<OWLOntologyChange> toBroadcast = new ArrayList<>();
                report.forEach((a, b) -> {
                    if (a == ChangeApplied.SUCCESSFULLY) {
                        toBroadcast.add(b);
                    }
                });
                getOWLOntologyManager().broadcastChanges(toBroadcast);
            }
            return report;
        } catch (OWLOntologyChangeVetoException e) {
            // Some listener blocked the changes.
            getOWLOntologyManager().broadcastOntologyChangesVetoed(changes, e);
            return new ChangeReport(asList(changes.stream().map(c -> ChangeApplied.UNSUCCESSFULLY)),
                new ArrayList<>(changes));
        }
    }

    /**
     * Apply ontology change without broadcasting the event and without allowing vetoers to
     * intervene. This method must be called on the ontology that is referred in the change object.
     * It should not be used by user application - it is meant to be used by implementations of
     * {@link #applyChanges} where the object is not the same object as the ontology referred in the
     * change (i.e., to apply a direct change to ontology X from ontology Y.
     *
     * @param change The change to be applied directly.
     * @return ChangeApplied containing the result of the change application.
     * @throws OWLOntologyChangeException If the change could not be applied.
     */
    ChangeApplied enactChange(OWLOntologyChange change);

    /**
     * Apply ontology changes. Note: each change refers to the ontology it applies to, so that
     * ontology will handle the actual change application, even if the change is passed to a
     * different ontology for implementation. So, for example, a change to an ontology included in
     * the imports closure of a root ontology can be applied by an application which only refers to
     * the root ontology, without the need to navigate the imports closure.
     *
     * @param changes The changes to be applied.
     * @return ChangeReport containing the results of the changes.
     * @throws OWLOntologyChangeException If one or more of the changes could not be applied.
     */
    default ChangeReport applyChanges(Stream<? extends OWLOntologyChange> changes) {
        return applyChanges(asList(changes));
    }

    /**
     * This method is to be called when a call to {@link #applyChanges(Collection)} encounters an
     * unsuccessful application and needs to roll back all changes that have been applied
     * successfully. Not for use by user applications.
     * 
     * @param report changes and application results to rollback
     * @return report containing the same changes as the input and the current application results
     */
    default ChangeReport rollBackChanges(ChangeReport report) {
        return new ChangeReport(report.map(this::rollbackChange), report.getEnactedChanges());
    }

    /**
     * This method is to be called to roll back a specific change. The change will be rolled back
     * only if the ChangeApplied parameter equals {@link ChangeApplied#SUCCESSFULLY}, as the method
     * is meant to roll back changes that have been successfully applied; other changes cannot be
     * rolled back. Not for use by user applications, this is meant to be called only by
     * {@link #rollBackChanges(ChangeReport)}.
     * 
     * @param r ChangeApplied related to the change parameter
     * @param change change to roll back
     * @return new value for the application result of the change
     */
    default ChangeApplied rollbackChange(ChangeApplied r, OWLOntologyChange change) {
        if (r == ChangeApplied.SUCCESSFULLY) {
            ChangeApplied rollbackResult = change.getOntology().enactChange(change.reverseChange());
            if (rollbackResult == ChangeApplied.UNSUCCESSFULLY) {
                // rollback could not complete, throw an exception
                throw new OWLRuntimeException("Rollback of changes unsuccessful: Change " + change
                    + " could not be rolled back");
            }
            return rollbackResult;
        }
        return r;
    }

}
