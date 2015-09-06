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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 * @param <O>
 *        the returned type
 */
public class OWLObjectWalker<O extends OWLObject> {

    protected OWLOntology ontology;
    private final Collection<O> objects;
    protected @Nullable OWLObjectVisitor visitor;
    private @Nullable OWLObjectVisitorEx<?> visitorEx;
    protected final boolean visitDuplicates;
    protected OWLAxiom ax;
    protected OWLAnnotation annotation;
    private final List<OWLClassExpression> classExpressionPath = new ArrayList<>();
    private final List<OWLDataRange> dataRangePath = new ArrayList<>();
    private @Nonnull StructureWalker<O> walker;

    /**
     * @param objects
     *        objects to visit
     */
    public OWLObjectWalker(Collection<O> objects) {
        this(objects.stream(), true, AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY);
    }

    /**
     * @param objects
     *        objects to visit
     */
    public OWLObjectWalker(Stream<O> objects) {
        this(objects, true, AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY);
    }

    /**
     * @param walkDuplicates
     *        true if duplicates should be visited
     * @param objects
     *        objects to visit
     */
    public OWLObjectWalker(Collection<O> objects, boolean walkDuplicates) {
        this(objects.stream(), walkDuplicates, AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY);
    }

    /**
     * @param walkDuplicates
     *        true if duplicates should be visited
     * @param objects
     *        objects to visit
     */
    public OWLObjectWalker(Stream<O> objects, boolean walkDuplicates) {
        this(objects, walkDuplicates, AnnotationWalkingControl.WALK_ONTOLOGY_ANNOTATIONS_ONLY);
    }

    protected @Nullable Object passToVisitor(OWLObject o) {
        if (visitor != null) {
            o.accept(visitor);
            return null;
        }
        return o.accept(verifyNotNull(visitorEx));
    }

    protected void setVisitor(OWLObjectVisitorEx<?> visitor) {
        visitorEx = visitor;
        this.visitor = null;
    }

    protected void setVisitor(OWLObjectVisitor visitor) {
        this.visitor = visitor;
        visitorEx = null;
    }

    /**
     * @param walkDuplicates
     *        true if duplicates should be visited
     * @param objects
     *        objects to visit
     * @param walkFlag
     *        control which annotations to visit
     */
    public OWLObjectWalker(Stream<O> objects, boolean walkDuplicates, AnnotationWalkingControl walkFlag) {
        this.objects = asList(checkNotNull(objects, "objects cannot be null"));
        this.visitDuplicates = walkDuplicates;
        this.walker = new StructureWalker<>(this, walkFlag);
    }

    /**
     * @param visitDuplicates
     *        true if duplicates should be visited
     * @param objects
     *        the set of objects to visit
     * @param walkFlag
     *        control which annotations to visit
     */
    public OWLObjectWalker(Collection<O> objects, boolean visitDuplicates, AnnotationWalkingControl walkFlag) {
        this(objects.stream(), visitDuplicates, walkFlag);
    }

    /**
     * @param walker
     *        the structure walker to use with this object walker
     */
    public void setStructureWalker(StructureWalker<O> walker) {
        this.walker = walker;
    }

    /**
     * @param v
     *        visitor to use over the objects
     */
    public void walkStructure(OWLObjectVisitorEx<?> v) {
        setVisitor(checkNotNull(v, "v cannot be null"));
        objects.forEach(o -> o.accept(walker));
    }

    /**
     * @param v
     *        visitor to use over the objects
     */
    public void walkStructure(OWLObjectVisitor v) {
        setVisitor(checkNotNull(v, "v cannot be null"));
        objects.forEach(o -> o.accept(walker));
    }

    /**
     * Gets the last ontology to be visited.
     * 
     * @return The last ontology to be visited
     */
    public @Nullable OWLOntology getOntology() {
        return ontology;
    }

    /**
     * Gets the last axiom to be visited.
     * 
     * @return The last axiom to be visited, or {@code null} if an axiom has not
     *         be visited
     */
    public OWLAxiom getAxiom() {
        return verifyNotNull(ax, "No current axiom; is the walker being used outside of an ontology visit?");
    }

    /**
     * Gets the last annotation to be visited.
     * 
     * @return The last annotation to be visited (may be {@code null})
     */
    public OWLAnnotation getAnnotation() {
        return annotation;
    }

    /**
     * Gets the current class expression path. The current class expression path
     * is a list of class expressions that represents the containing expressions
     * for the current class expressions. The first item in the path (list) is
     * the root class expression that was visited. For i between 0 and
     * pathLength, the item at index i+1 is a direct sub-expression of the item
     * at index i. The last item in the path is the current class expression
     * being visited.
     * 
     * @return A list of class expressions that represents the path of class
     *         expressions, with the root of the class expression being the
     *         first element in the list.
     */
    public List<OWLClassExpression> getClassExpressionPath() {
        return new ArrayList<>(classExpressionPath);
    }

    /**
     * Determines if a particular class expression is the first (or root) class
     * expression in the current class expression path.
     * 
     * @param classExpression
     *        The class expression
     * @return {@code true} if the specified class expression is the first class
     *         expression in the current class expression path, otherwise
     *         {@code false} ({@code false} if the path is empty)
     */
    public boolean isFirstClassExpressionInPath(OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        return !classExpressionPath.isEmpty() && classExpressionPath.get(0).equals(classExpression);
    }

    /**
     * Pushes a class expression onto the class expression path.
     * 
     * @param ce
     *        The class expression to be pushed onto the path
     */
    protected void pushClassExpression(OWLClassExpression ce) {
        checkNotNull(ce, "ce cannot be null");
        classExpressionPath.add(ce);
    }

    /**
     * Pops a class expression from the class expression path. If the path is
     * empty then this method has no effect.
     */
    protected void popClassExpression() {
        if (!classExpressionPath.isEmpty()) {
            classExpressionPath.remove(classExpressionPath.size() - 1);
        }
    }

    /**
     * Gets the current data range path. The current data range path is a list
     * of data ranges that represents the containing expressions for the current
     * data ranges. The first item in the path (list) is the root data range
     * that was visited. For i between 0 and pathLength, the item at index i+1
     * is a direct sub-expression of the item at index i. The last item in the
     * path is the current data range being visited.
     * 
     * @return A list of data ranges that represents the path of data ranges,
     *         with the root of the data range being the first element in the
     *         list.
     */
    public List<OWLDataRange> getDataRangePath() {
        return new ArrayList<>(dataRangePath);
    }

    /**
     * Pushes a data range on to the data range path.
     * 
     * @param dr
     *        The data range to be pushed onto the path
     */
    protected void pushDataRange(OWLDataRange dr) {
        checkNotNull(dr, "dr cannot be null");
        dataRangePath.add(dr);
    }

    /**
     * Pops a data range from the data range expression path. If the path is
     * empty then this method has no effect.
     */
    protected void popDataRange() {
        if (!dataRangePath.isEmpty()) {
            dataRangePath.remove(dataRangePath.size() - 1);
        }
    }

    /**
     * Allow the structure walker to set the current axiom.
     * 
     * @param axiom
     *        the axiom to set
     */
    public void setAxiom(OWLAxiom axiom) {
        ax = axiom;
    }

    /**
     * Allow the structure walker to set the current annotation.
     * 
     * @param node
     *        the annotation to set
     */
    public void setAnnotation(OWLAnnotation node) {
        annotation = node;
    }
}
