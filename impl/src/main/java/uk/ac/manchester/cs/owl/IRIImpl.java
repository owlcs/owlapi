package uk.ac.manchester.cs.owl;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import java.net.URI;
import java.util.Collections;
import java.util.Set;/*
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
 * Date: 14-Jan-2009
 */
public class IRIImpl implements IRI {

    private OWLDataFactory dataFactory;

    private URI uri;

    public IRIImpl(OWLDataFactory dataFactory, URI uri) {
        this.dataFactory = dataFactory;
        this.uri = uri;
    }

    public OWLClass toOWLClass() {
        return dataFactory.getOWLClass(uri);
    }

    public OWLDataProperty toOWLDataProperty() {
        return dataFactory.getOWLDataProperty(uri);
    }

    public OWLDatatype toOWLDatatype() {
        return dataFactory.getDatatype(uri);
    }

    public OWLNamedIndividual toOWLIndividual() {
        return dataFactory.getOWLIndividual(uri);
    }

    public OWLObjectProperty toOWLObjectProperty() {
        return dataFactory.getOWLObjectProperty(uri);
    }

    public URI toURI() {
        return uri;
    }

    public boolean isBuiltIn() {
        throw new OWLRuntimeException("NOT IMPLEMENTED");
    }

    public boolean isDisallowedVocabulary() {
        throw new OWLRuntimeException("NOT IMPLEMENTED");
    }

    public boolean isNothing() {
        return uri.equals(OWLRDFVocabulary.OWL_NOTHING.getURI());
    }

    public boolean isReservedVocabulary() {
        return OWLRDFVocabulary.BUILT_IN_VOCABULARY.contains(uri);
    }

    public boolean isSpecialTreatmentReservedVocabulary() {
        return false;
    }

    public boolean isThing() {
        return uri.equals(OWLRDFVocabulary.OWL_THING.getURI());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public Set<OWLClass> getClassesInSignature() {
        return Collections.emptySet();
    }

    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return Collections.emptySet();
    }

    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return Collections.emptySet();
    }

    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return Collections.emptySet();
    }

    public Set<OWLEntity> getSignature() {
        return Collections.emptySet();
    }

    public int compareTo(OWLObject o) {
        if (!(o instanceof IRI)) {
            return -1;
        }
        IRI other = (IRI) o;
        return uri.compareTo(other.toURI());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(uri);
        sb.append(">");
        return sb.toString();
    }
}
