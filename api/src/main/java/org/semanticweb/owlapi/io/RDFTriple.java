/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.io;

import java.io.Serializable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics
 *         Group, Date: 21/12/2010
 * @since 3.2
 */
public class RDFTriple implements Serializable {

    private static final long serialVersionUID = 30406L;
    private final RDFResource subject;
    private final RDFResource predicate;
    private final RDFNode object;

    /**
     * @param subject
     *        the subject
     * @param predicate
     *        the predicate
     * @param object
     *        the object
     */
    public RDFTriple(RDFResource subject, RDFResource predicate, RDFNode object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    /**
     * @param subject
     *        the subject
     * @param subjectAnon
     *        whether the subject is anonymous
     * @param predicate
     *        the predicate
     * @param predicateAnon
     *        whether the predicate is anon
     * @param object
     *        the object
     * @param objectAnon
     *        whether the object is anonymous
     */
    public RDFTriple(IRI subject, boolean subjectAnon, IRI predicate,
            boolean predicateAnon, IRI object, boolean objectAnon) {
        this.subject = new RDFResource(subject, subjectAnon);
        this.predicate = new RDFResource(predicate, predicateAnon);
        this.object = new RDFResource(object, objectAnon);
    }

    /**
     * @param subject
     *        the subject
     * @param subjectAnon
     *        whether the subject is anonymous
     * @param predicate
     *        the predicate
     * @param predicateAnon
     *        whether the predicate is anon
     * @param object
     *        the object
     */
    public RDFTriple(IRI subject, boolean subjectAnon, IRI predicate,
            boolean predicateAnon, OWLLiteral object) {
        this.subject = new RDFResource(subject, subjectAnon);
        this.predicate = new RDFResource(predicate, predicateAnon);
        this.object = new RDFLiteral(object);
    }

    /** @return the subject */
    public RDFResource getSubject() {
        return subject;
    }

    /** @return the predicate */
    public RDFResource getPredicate() {
        return predicate;
    }

    /** @return the object */
    public RDFNode getObject() {
        return object;
    }

    @Override
    public int hashCode() {
        return subject.hashCode() * 37 + predicate.hashCode() * 17
                + object.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RDFTriple)) {
            return false;
        }
        RDFTriple other = (RDFTriple) o;
        return subject.equals(other.subject)
                && predicate.equals(other.predicate)
                && object.equals(other.object);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(subject.toString());
        sb.append(" ");
        sb.append(predicate.toString());
        sb.append(" ");
        sb.append(object.toString());
        sb.append(".");
        return sb.toString();
    }
}
