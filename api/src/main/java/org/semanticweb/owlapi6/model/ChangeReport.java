package org.semanticweb.owlapi6.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.semanticweb.owlapi6.model.parameters.ChangeApplied;

/**
 * A class to hold detailed results on a set of changes. {@code changeEffect} has the overall result
 * of the transaction (changes can have been applied successfully, they can have failed and been
 * rolled back, or all changes might have been no-operation). {@code enactedChanges} has all the
 * changes that have been applied.
 *
 * @author ignazio
 */
public class ChangeReport {

    private final List<ChangeApplied> changeEffects;
    private final List<OWLOntologyChange> changes;

    /**
     * @param changeEffects the results of all attempted changes
     * @param changes the list of changes that were attempted
     */
    public ChangeReport(List<ChangeApplied> changeEffects, List<OWLOntologyChange> changes) {
        this.changeEffects = changeEffects;
        this.changes = changes;
    }

    /**
     * @return true if all changes were successful
     */
    public boolean allSuccessful() {
        return changeEffects.stream().allMatch(ChangeApplied.SUCCESSFULLY::equals);
    }

    /**
     * @return true if all changes were unsuccessful
     */
    public boolean allUnsuccessful() {
        return changeEffects.stream().allMatch(ChangeApplied.UNSUCCESSFULLY::equals);
    }

    /**
     * @return true if all changes were no operations
     */
    public boolean allNoOperation() {
        return changeEffects.stream().allMatch(ChangeApplied.NO_OPERATION::equals);
    }

    /**
     * @return the results of all attempted changes
     */
    public List<ChangeApplied> getChangeEffects() {
        return changeEffects;
    }

    /**
     * @return the changes
     */
    public List<OWLOntologyChange> getEnactedChanges() {
        return changes;
    }

    /**
     * @param c consumer to examine the pairs in the report
     */
    public void forEach(BiConsumer<ChangeApplied, OWLOntologyChange> c) {
        for (int i = 0; i < changeEffects.size(); i++) {
            c.accept(changeEffects.get(i), changes.get(i));
        }
    }

    /**
     * @param f function to transform the pairs in the report
     * @param <T> return type
     * @return list of transformation results
     */
    public <T> List<T> map(BiFunction<ChangeApplied, OWLOntologyChange, T> f) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < changeEffects.size(); i++) {
            list.add(f.apply(changeEffects.get(i), changes.get(i)));
        }
        return list;
    }
}
