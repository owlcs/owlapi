package com.clarkparsia.owlapi.modularity.locality;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 * @author Evren Sirin
 */
public interface LocalityEvaluator {

    /**
     * Tests whether a given axiom is local with respect to a given signature.
     * @param axiom the axiom to test
     * @param signature the signature to test against
     * @return <code>true</code> if the axiom is local w.r.t. the signature; <code>false</code> otherwise
     */
    public boolean isLocal(OWLAxiom axiom, Set<? extends OWLEntity> signature);

}