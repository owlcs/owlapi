/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2020, Marc Robin Nolte
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.modularity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Abstract class for convenience implementation of {@link ModuleExtractor}s able of precomputing
 * global axioms and tautologies.
 *
 * @author Marc Robin Nolte
 *
 */
public abstract class AbstractModuleExtractor implements ModuleExtractor {

    /**
     * The axiom base of this {@link AbstractModuleExtractor}.
     */
    private final @Nonnull Set<OWLAxiom> axiomBase;

    /**
     * The axioms that are guaranteed to be contained in every module calculated by this
     * {@link AbstractModuleExtractor}. Will be calculated when first calling
     * {@link AbstractModuleExtractor#globals()}.
     */
    private @Nonnull Optional<Set<OWLAxiom>> globals = Optional.empty();

    /**
     * The axioms that are guaranteed to be contained in no module calculated by this
     * {@link AbstractModuleExtractor}. Will be calculated when first calling
     * {@link AbstractModuleExtractor#tautologies()}.
     */
    private @Nonnull Optional<Set<OWLAxiom>> tautologies = Optional.empty();

    /**
     * Creates a new {@link AbstractModuleExtractor}.
     *
     * @param axiomBase the axiom base of the new {@link AbstractModuleExtractor}
     */
    protected AbstractModuleExtractor(Stream<OWLAxiom> axiomBase) {
        this.axiomBase = axiomBase.filter(OWLAxiom::isLogicalAxiom).collect(Collectors.toSet());
    }

    @Override
    public final @Nonnull Stream<OWLAxiom> axiomBase() {
        return axiomBase.stream();
    }

    @Override
    public boolean containsAxiom(OWLAxiom axiom) {
        return axiomBase.contains(axiom);
    }

    @Override
    public final boolean everyModuleContains(OWLAxiom axiom) {
        if (globals.isPresent()) {
            return globals.get().contains(axiom);
        }
        return ModuleExtractor.super.everyModuleContains(axiom);
    }

    @Override
    public final @Nonnull Stream<OWLAxiom> globals() {
        if (!globals.isPresent()) {
            globals = Optional.of(ModuleExtractor.super.globals().collect(Collectors.toSet()));
        }
        return globals.get().stream();
    }

    @Override
    public final boolean noModuleContains(OWLAxiom axiom) {
        if (tautologies.isPresent()) {
            return tautologies.get().contains(axiom);
        }
        return ModuleExtractor.super.noModuleContains(axiom);
    }

    @Override
    public final @Nonnull Stream<OWLAxiom> tautologies() {
        if (!tautologies.isPresent()) {
            tautologies =
                Optional.of(ModuleExtractor.super.tautologies().collect(Collectors.toSet()));
        }
        return tautologies.get().stream();
    }
}
