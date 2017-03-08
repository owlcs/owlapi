package org.semanticweb.owlapitools.decomposition;

import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectVisitor;

/**
 * helper class to set signature and locality class
 */
public class SigAccessor implements OWLObjectVisitor {

    LocalityChecker localityChecker;

    /**
     * @param c locality checker
     */
    public SigAccessor(LocalityChecker c) {
        localityChecker = c;
    }

    /**
     * @param expr data range to check
     * @return true iff EXPR is a top datatype or a built-in datatype;
     */
    public boolean isTopOrBuiltInDataType(OWLDataRange expr) {
        return expr.isTopDatatype() || expr.isOWLDatatype() && expr.asOWLDatatype().isBuiltIn();
    }

    /**
     * @return true iff roles are treated as TOPs
     */
    public boolean topRLocal() {
        return localityChecker.getSignature().topRLocal();
    }

    protected Signature getSignature() {
        return localityChecker.getSignature();
    }
}
