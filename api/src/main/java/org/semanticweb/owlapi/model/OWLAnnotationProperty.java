package org.semanticweb.owlapi.model;/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 13-Jan-2009
 * </p>
 * Represents an <a href="http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Annotation_Properties">AnnotationProperty</a>
 * in the OWL 2 specification.
 */
public interface OWLAnnotationProperty extends OWLEntity {

    /**
     * Determines if this annotation property has an IRI corresponding to <code>rdfs:comment</code>.
     * @return <code>true</code> if the IRI of this annotation property is
     * <code>rdfs:comment</code>, where <code>rdfs:</code> expands to the usual prefix, otherwise <code>false</code>.
     */
    boolean isComment();

    /**
     * Determines if this annotation property has an IRI corresponding to <code>rdfs:label</code>.
     * @return <code>true</code> if the IRI of this annotation property is
     * <code>rdfs:label</code>, where <code>rdfs:</code> expands to the usual prefix, otherwise <code>false</code>.
     */
    boolean isLabel();

    /**
     * Determines if this annotation property has an IRI corresponding to <code>owl:deprecated</code>. An annotation
     * along the <code>owl:deprecated</code> property which has a value of <code>"true"^^xsd:boolean</code> can be
     * used to deprecate IRIs. (See <a href="Section 5.5">http://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Annotation_Properties</a>
     * of the OWL 2 specification.
     * @return <code>true</code> if the IRI of this annotation property is
     * <code>owl:deprecated</code>, where <code>owl:</code> expands to the usual prefix, otherwise <code>false</code>.
     */
    boolean isDeprecated();

    /**
     * Determines if this property is a built in annotation property.  i.e. one of the following
     * <ul>
     * <li>rdfs:label</li>
     * <li>rdfs:comment</li>
     * <li>rdfs:seeAlso</li>
     * <li>rdfs:isDefinedBy</li>
     * <li>owl:deprecated</li>
     * <li>owl:priorVersion</li>
     * <li>owl:backwardCompatibleWith</li>
     * <li>owl:incompatibleWith</li>
     * </ul>
     *
     * @return <code>true</code> if the property is a built in annotation property, otherwise <code>false</code>
     */
    boolean isBuiltIn();
}
