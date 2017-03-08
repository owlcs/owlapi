package org.semanticweb.owlapi.model;

import java.util.List;

import org.semanticweb.owlapi.model.parameters.ChangeApplied;

/**
 * A class to hold detailed results on a set of changes. {@code changeEffect} has the overall result
 * of the transaction (changes can have been applied successfully, they can have failed and been
 * rolled back, or all changes might have been no-operation). {@code enactedChanges} has all the
 * changes that have
 *
 * @author ignazio
 */
public class ChangeDetails {

    private final ChangeApplied changeEffect;
    private final List<? extends OWLOntologyChange> enactedChanges;

    /**
     * @param changeEffect the result of a change
     * @param enactedChanges the list of changes applied successfully
     */
    public ChangeDetails(ChangeApplied changeEffect,
        List<? extends OWLOntologyChange> enactedChanges) {
        this.changeEffect = changeEffect;
        this.enactedChanges = enactedChanges;
    }

    /**
     * @return the changeEffect
     */
    public ChangeApplied getChangeEffect() {
        return changeEffect;
    }

    /**
     * @return the enactedChanges
     */
    public List<? extends OWLOntologyChange> getEnactedChanges() {
        return enactedChanges;
    }
}
