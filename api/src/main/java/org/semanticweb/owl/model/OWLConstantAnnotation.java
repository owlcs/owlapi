package org.semanticweb.owl.model;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 19-Dec-2006<br><br>
 *
 * Represents an annotation which has a constant (i.e. typed or untyped
 * literal) as its value.
 */
public interface OWLConstantAnnotation extends OWLAnnotation<OWLConstant> {

    /**
     * Determines if the specified annotation is a label annotation.
     * @return <code>true</code> if the annotation is a label annoation, and
     * can be cast to <code>OWLLabelAnnotation</code>, otherwise <code>false</code>.
     * Note that this method will return <code>false</code> for
     * annotations which aren't "syntactically" label annotations but which have
     * an rdfs:label URI (see the OWL 1.1 Functional specification for more
     * details).
     */
    boolean isLabel();

    /**
     * Determines if the specified annotation is a comment annotation.
     * @return <code>true</code> if the annotation is a comment annoation, and
     * can be cast to <code>OWLCommentAnnotation</code>, otherwise <code>false</code>.
     * Note that this method will return <code>false</code> for
     * annotations which aren't "syntactically" comment annotations but which have
     * an rdfs:comment URI (see the OWL 1.1 Functional specification for more
     * details).
     */
    boolean isComment();

}
