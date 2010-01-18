package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.NNF;
import org.semanticweb.owlapi.util.OWLEntityCollector;

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
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLAxiomImpl extends OWLObjectImpl implements OWLAxiom {

    private OWLAxiom nnf;

    private Set<OWLAnnotation> annotations;

    public OWLAxiomImpl(OWLDataFactory dataFactory, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory);
        if (!annotations.isEmpty()) {
            this.annotations = Collections.unmodifiableSortedSet(new TreeSet<OWLAnnotation>(annotations));
        } else {
            this.annotations = Collections.emptySet();
        }
    }

    public boolean isAnnotated() {
        return !annotations.isEmpty();
    }

    public Set<OWLAnnotation> getAnnotations() {
        return annotations;
    }

    public Set<OWLAnnotation> getAnnotations(OWLAnnotationProperty annotationProperty) {
        if(annotations.isEmpty()) {
            return annotations;
        }
        else {
            Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
            for(OWLAnnotation anno : annotations) {
                if(anno.getProperty().equals(annotationProperty)) {
                    result.add(anno);
                }
            }
            return result;
        }
    }

    /**
     * Determines if another axiom is equal to this axiom not taking into consideration the annotations on the axiom
     *
     * @param axiom The axiom to test if equal
     * @return <code>true</code> if <code>axiom</code> without annotations is equal to this axiom without annotations
     *         otherwise <code>false</code>.
     */
    public boolean equalsIgnoreAnnotations(OWLAxiom axiom) {
        return this.getAxiomWithoutAnnotations().equals(axiom.getAxiomWithoutAnnotations());
    }

    /**
     * Determines if this axiom is one of the specified types
     * @param axiomTypes The axiom types to check for
     * @return <code>true</code> if this axiom is one of the specified types, otherwise <code>false</code>
     * @since 3.0
     */
    public boolean isOfType(AxiomType... axiomTypes) {
        for(AxiomType type : axiomTypes) {
            if(getAxiomType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if this axiom is one of the specified types
     * @param types The axiom types to check for
     * @return <code>true</code> if this axioms is one of the specified types, otherwise <code>false</code>
     * @since 3.0
     */
    public boolean isOfType(Set<AxiomType> types) {
        return types.contains(getAxiomType());
    }

    /**
     * A convenience method for implementation that returns a set containing the annotations on this axiom plus the
     * annoations in the specified set.
     * @param annos The annotations to add to the annotations on this axiom
     * @return The annotations
     */
    protected Set<OWLAnnotation> mergeAnnos(Set<OWLAnnotation> annos) {
        Set<OWLAnnotation> merged = new HashSet<OWLAnnotation>(annos);
        merged.addAll(getAnnotations());
        return annos;
    }


    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OWLAxiom)) {
            return false;
        }
        OWLAxiom other = (OWLAxiom) obj;
        return annotations.equals(other.getAnnotations());
    }


    public Set<OWLEntity> getReferencedEntities() {
        OWLEntityCollector collector = new OWLEntityCollector();
        this.accept(collector);
        return collector.getObjects();
    }


    public OWLAxiom getNNF() {
        if (nnf == null) {
            NNF con = new NNF(getOWLDataFactory());
            nnf = accept(con);
        }
        return nnf;
    }
}
