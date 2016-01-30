package uk.ac.manchester.cs.factplusplusad;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.util.OWLAPIStreamUtils;

/** class to hold the signature of a module */
class Signature {

    /** set to keep all the elements in signature */
    private final Set<OWLEntity> set = new HashSet<>();
    /** true if concept TOP-locality; false if concept BOTTOM-locality */
    private boolean topCLocality = false;
    /** true if role TOP-locality; false if role BOTTOM-locality */
    private boolean topRLocality = false;

    /** copy c'tor */
    Signature(Signature copy) {
        set.addAll(copy.set);
        topCLocality = copy.topCLocality;
        topRLocality = copy.topRLocality;
    }

    Signature() {
        super();
    }

    /**
     * @param sig
     *        signature elements
     */
    public Signature(Stream<OWLEntity> sig) {
        addAll(sig);
    }

    /**
     * @param p
     *        entity to add to signature
     * @return true if p was not in the signature already
     */
    public boolean add(OWLEntity p) {
        return set.add(p);
    }

    /**
     * @param p
     *        all entities to add
     */
    public void addAll(Stream<OWLEntity> p) {
        OWLAPIStreamUtils.add(set, p);
    }

    /** add set of named entities to signature */
    void add(Set<OWLEntity> aSet) {
        set.addAll(aSet);
    }

    /** add another signature to a given one */
    void add(Signature sig) {
        add(sig.set);
    }

    /** remove given element from a signature */
    void remove(OWLEntity p) {
        set.remove(p);
    }

    /** set new locality polarity */
    void setLocality(boolean top) {
        setLocality(top, top);
    }

    /**
     * @param topC
     *        new concept locality polarity
     * @param topR
     *        new role locality polarity
     */
    public void setLocality(boolean topC, boolean topR) {
        topCLocality = topC;
        topRLocality = topR;
    }

    void setSignature(Signature s) {
        setSignature(s.set.stream());
        topCLocality = s.topCLocality;
        topRLocality = s.topRLocality;
    }

    void setSignature(Stream<OWLEntity> s) {
        set.clear();
        addAll(s);
    }
    // comparison

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Signature) {
            return set.equals(((Signature) obj).set);
        }
        return false;
    }

    /** @return true iff signature contains given element */
    boolean contains(OWLEntity p) {
        return set.contains(p);
    }

    /** @return true iff signature contains given element */
    boolean contains(OWLObjectInverseOf p) {
        return set.contains(p.getNamedProperty());
    }

    /** @return size of the signature */
    int size() {
        return set.size();
    }

    /** clear the signature */
    void clear() {
        set.clear();
    }

    /** @return elements of signature */
    Stream<OWLEntity> getSignature() {
        return set.stream();
    }

    /** @return true iff concepts are treated as TOPs */
    public boolean topCLocal() {
        return topCLocality;
    }

    /** @return true iff concepts are treated as BOTTOMs */
    boolean botCLocal() {
        return !topCLocality;
    }

    /** @return true iff roles are treated as TOPs */
    public boolean topRLocal() {
        return topRLocality;
    }

    /** @return true iff roles are treated as BOTTOMs */
    boolean botRLocal() {
        return !topRLocality;
    }

    Set<OWLEntity> intersect(Signature s1, Signature s2) {
        Set<OWLEntity> ret = new HashSet<>(s1.set);
        ret.retainAll(s2.set);
        return ret;
    }
}
