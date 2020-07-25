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
package org.semanticweb.owlapi.profiles;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 */
public class OWLProfileReport {

    private final OWLProfile profile;
    private final List<OWLProfileViolation> violations;

    /**
     * @param profile    the profile used
     * @param violations the set of violations
     */
    public OWLProfileReport(OWLProfile profile, Collection<OWLProfileViolation> violations) {
        this.profile = profile;
        this.violations = new ArrayList<>(violations);
    }

    /**
     * @return the profile used
     */
    public OWLProfile getProfile() {
        return profile;
    }

    /**
     * @return true if there are no violations
     */
    public boolean isInProfile() {
        return violations.isEmpty();
    }

    /**
     * @return the violations found
     */
    public List<OWLProfileViolation> getViolations() {
        return violations;
    }

    /**
     * @param o ontology of interest
     * @return the violations found, filtered by ontology
     */
    public List<OWLProfileViolation> getViolations(OWLOntology o) {
        return asList(violations.stream().filter(v -> Objects.equals(v.getOntology(), o)));
    }

    /**
     * @param e entity of interest
     * @return the violations found, filtered by entity
     */
    public List<OWLProfileViolation> getViolations(OWLEntity e) {
        return asList(violations.stream().filter(v -> Objects.equals(v.getExpression(), e)));
    }

    /**
     * @param ax axiom of interest
     * @return the violations found, filtered by axiom
     */
    public List<OWLProfileViolation> getViolations(OWLAxiom ax) {
        return asList(violations.stream().filter(v -> Objects.equals(v.getAxiom(), ax)));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(profile.getName());
        sb.append(" Profile Report: ");
        if (isInProfile()) {
            sb.append("[Ontology and imports closure in profile]\n");
        } else {
            sb.append(
                "Ontology and imports closure NOT in profile. The following violations are present:\n");
        }
        violations.forEach(v -> sb.append(v).append('\n'));
        return sb.toString();
    }
}
