package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.NNF;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLAxiomImpl extends OWLObjectImpl implements OWLAxiom {

    private OWLAxiom nnf;

    private final Set<OWLAnnotation> annotations;

    public OWLAxiomImpl(OWLDataFactory dataFactory, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory);
        if (!annotations.isEmpty()) {
            this.annotations = CollectionFactory.getCopyOnRequestSet(new TreeSet<OWLAnnotation>(annotations));
        }
        else {
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
        if (annotations.isEmpty()) {
            return annotations;
        }
        else {
            Set<OWLAnnotation> result = new HashSet<OWLAnnotation>();
            for (OWLAnnotation anno : annotations) {
                if (anno.getProperty().equals(annotationProperty)) {
                    result.add(anno);
                }
            }
            return result;
        }
    }

    /**
     * Determines if another axiom is equal to this axiom not taking into consideration the annotations on the axiom
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
    public boolean isOfType(AxiomType<?>... axiomTypes) {
        for (AxiomType<?> type : axiomTypes) {
            if (getAxiomType().equals(type)) {
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
    public boolean isOfType(Set<AxiomType<?>> types) {
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
        return merged;
    }


    @Override
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
    	return getSignature();
    }


    public OWLAxiom getNNF() {
        if (nnf == null) {
            NNF con = new NNF(getOWLDataFactory());
            nnf = accept(con);
        }
        return nnf;
    }
}
