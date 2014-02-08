package org.semanticweb.owlapitools.builders;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapitools.profiles.OWL2DLProfile;
import org.semanticweb.owlapitools.profiles.OWLProfile;
import org.semanticweb.owlapitools.profiles.OWLProfileReport;
import org.semanticweb.owlapitools.profiles.OWLProfileViolation;

/** Base builder class, providing annotations storage
 * 
 * @author ignazio
 * @param <T>
 *            built type
 * @param <Type>
 *            builder type */
public abstract class BaseBuilder<T extends OWLObject, Type> implements
        Builder<T> {
    protected final OWLDataFactory df;
    protected Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
    private OWLProfile profile = new OWL2DLProfile();

    /** @param df
     *            data factory */
    protected BaseBuilder(OWLDataFactory df) {
        this.df = checkNotNull(df);
    }

    /** @param arg
     *            annotation
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withAnnotation(OWLAnnotation arg) {
        annotations.add(arg);
        return (Type) this;
    }

    /** @param arg
     *            annotation
     * @return builder */
    @SuppressWarnings("unchecked")
    public Type withAnnotations(Collection<OWLAnnotation> arg) {
        annotations.addAll(arg);
        return (Type) this;
    }

    @Override
    public abstract T buildObject();

    @Override
    public final List<OWLOntologyChange<?>> buildChanges(OWLOntology o) {
        T object = buildObject();
        if (!(object instanceof OWLAxiom)) {
            return Collections.emptyList();
        }
        // create and apply the new change
        AddAxiom change = new AddAxiom(o, (OWLAxiom) object);
        o.getOWLOntologyManager().applyChange(change);
        List<OWLOntologyChange<?>> changes = new ArrayList<OWLOntologyChange<?>>();
        // check conformity to the profile
        OWLProfileReport report = profile.checkOntology(o);
        for (OWLProfileViolation<?> v : report.getViolations()) {
            // collect all changes to fix the ontology
            changes.addAll(v.repair());
        }
        // fix the ontology
        o.getOWLOntologyManager().applyChanges(changes);
        // return all applied changes for reference
        changes.add(change);
        return changes;
    }
}
