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
package org.semanticweb.owlapitools.builders;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;

/**
 * Base builder class, providing annotations storage.
 * 
 * @author ignazio
 * @param <T>
 *        built type
 * @param <B>
 *        builder type
 */
public abstract class BaseBuilder<T extends OWLObject, B> implements Builder<T> {

    @Nonnull
    protected final OWLDataFactory df;
    @Nonnull
    protected final Set<OWLAnnotation> annotations = new HashSet<>();
    @Nonnull
    private final OWLProfile profile = new OWL2DLProfile();

    /**
     * @param df
     *        data factory
     */
    @Inject
    protected BaseBuilder(OWLDataFactory df) {
        this.df = checkNotNull(df);
    }

    /**
     * @param arg
     *        annotation
     * @return builder
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public B withAnnotation(OWLAnnotation arg) {
        annotations.add(arg);
        return (B) this;
    }

    /**
     * @param arg
     *        annotations
     * @return builder
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public B withAnnotations(@Nonnull Collection<OWLAnnotation> arg) {
        annotations.addAll(arg);
        return (B) this;
    }

    /**
     * @param arg
     *        annotations
     * @return builder
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public B withAnnotations(@Nonnull Stream<OWLAnnotation> arg) {
        arg.forEach(a -> annotations.add(a));
        return (B) this;
    }

    @Override
    public abstract T buildObject();

    @Nonnull
    @Override
    public List<OWLOntologyChange> applyChanges(@Nonnull OWLOntology o) {
        T object = buildObject();
        if (!(object instanceof OWLAxiom)) {
            return Collections.emptyList();
        }
        // create and apply the new change
        AddAxiom change = new AddAxiom(o, (OWLAxiom) object);
        o.getOWLOntologyManager().applyChange(change);
        List<OWLOntologyChange> changes = new ArrayList<>();
        // check conformity to the profile
        OWLProfileReport report = profile.checkOntology(o);
        // collect all changes to fix the ontology
        report.getViolations().forEach(v -> changes.addAll(v.repair()));
        // fix the ontology
        o.getOWLOntologyManager().applyChanges(changes);
        // return all applied changes for reference
        changes.add(change);
        return changes;
    }
}
