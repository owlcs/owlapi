package org.semanticweb.owl.io;

import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.vocab.NamespaceOWLOntologyFormat;

import java.net.URI;
import java.util.*;
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
 * Date: 02-Jan-2007<br><br>
 */
public class RDFXMLOntologyFormat extends NamespaceOWLOntologyFormat {

    private Map<URI, Set<OWLAnnotation>> annotationURI2Annotation;

    private Set<URI> annotationURIs;

    private long numberOfTriplesProcessedDuringLoading;

    public RDFXMLOntologyFormat() {
        annotationURI2Annotation = new HashMap<URI, Set<OWLAnnotation>>();
        annotationURIs = new HashSet<URI>();
    }


    /**
     * Returns the number of triples that where processed when the
     * ontology was loaded.
     * @return The number of triples parsed when loading from a file,
     * If the ontology hasn't been loaded from
     * a file (because it was created programmatically) then the return
     * value will be zero.
     */
    public long getNumberOfTriplesProcessedDuringLoading() {
        return numberOfTriplesProcessedDuringLoading;
    }


    public void setNumberOfTriplesProcessedDuringLoading(long numberOfTriplesProcessedDuringLoading) {
        this.numberOfTriplesProcessedDuringLoading = numberOfTriplesProcessedDuringLoading;
    }


    public String toString() {
        return "RDF/XML";
    }


    public Set<URI> getAnnotationURIs() {
        return Collections.unmodifiableSet(annotationURIs);
    }

    /**
     * This method and the functionality that it provides are merely a stopgap
     * until the OWL 1.1 specification is fixed.  Use at your own risk! It will
     * be removed when the spec is fixed!
     */
    public void addAnnotationURI(URI uri) {
        annotationURIs.add(uri);
        if(annotationURI2Annotation.get(uri) == null) {
            annotationURI2Annotation.put(uri, new HashSet<OWLAnnotation>());
        }
    }


    /**
     * This method and the functionality that it provides are merely a stopgap
     * until the OWL 1.1 specification is fixed.  Use at your own risk! It will
     * be removed when the spec is fixed!
     */
    public void addAnnotationURIAnnotation(URI uri, OWLAnnotation anno) {
        addAnnotationURI(uri);
        Set<OWLAnnotation> annos = annotationURI2Annotation.get(uri);
        if(annos == null) {
            annos = new HashSet<OWLAnnotation>();
            annotationURI2Annotation.put(uri, annos);
        }
        annos.add(anno);
    }


    /**
     * This method and the functionality that it provides are merely a stopgap
     * until the OWL 1.1 specification is fixed.  Use at your own risk! It will
     * be removed when the spec is fixed!
     */
    public void removeAnnotationURIAnnotation(URI uri, OWLAnnotation anno) {
        Set<OWLAnnotation> annos = annotationURI2Annotation.get(uri);
        if(annos != null) {
            annos.remove(anno);
        }
    }


    /**
     * This method and the functionality that it provides are merely a stopgap
     * until the OWL 1.1 specification is fixed.  Use at your own risk! It will
     * be removed when the spec is fixed!
     */
    public void clearAnnotationURIAnnotations() {
        annotationURI2Annotation.clear();
    }


    /**
     *
     * This method and the functionality that it provides are merely a stopgap
     * until the OWL 1.1 specification is fixed.  Use at your own risk! It will
     * be removed when the spec is fixed!
     */
    public Map<URI, Set<OWLAnnotation>> getAnnotationURIAnnotations() {
        return Collections.unmodifiableMap(annotationURI2Annotation);
    }
}
