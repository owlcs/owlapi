/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package com.clarkparsia.owlapi.explanation.util;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * A progress monitor for an explanation generator. The progress monitor should
 * be used with an {@code MultipleExpalanationGenerator}
 * 
 * @author Matthew Horridge, Clark &amp; Parsia, LLC, Bio-Health Informatics
 *         Group
 * @since 2.2.0
 */
public interface ExplanationProgressMonitor {

    /**
     * Determines if the process of generating explanations has been cancelled.
     * 
     * @return {@code true} if the process has been cancelled, or {@code false}
     *         if the process hasn't been cancelled.
     */
    boolean isCancelled();

    /**
     * Called when an explanation has been found.
     * 
     * @param axioms
     *        The axioms that constitute the explanation
     */
    void foundExplanation(@Nonnull Set<OWLAxiom> axioms);

    /** Called when all explanations have been found. */
    void foundAllExplanations();
}
