package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 23-Jul-2009
 */
public class OWLClassLiteralCollector extends OWLObjectWalker<OWLObject> {

	// XXX stateful visitor...
    private Set<OWLClass> pos = new HashSet<OWLClass>();

    private Set<OWLClass> neg = new HashSet<OWLClass>();

    private boolean processed = false;

    public OWLClassLiteralCollector(Set<OWLObject> objects) {
        super(objects);
    }

    public OWLClassLiteralCollector(Set<OWLObject> objects, boolean visitDuplicates) {
        super(objects, visitDuplicates);
    }

    private void process() {
        if(!processed) {
            processed = true;
            walkStructure(new OWLClassLiteralCollectorVisitor());
        }
    }

    public Set<OWLClass> getPositiveLiterals() {
        process();
        return new HashSet<OWLClass>(pos);
    }

    public Set<OWLClass> getNegativeLiterals() {
        process();
        return new HashSet<OWLClass>(neg);
    }

    private class OWLClassLiteralCollectorVisitor extends OWLObjectVisitorExAdapter<Object> {

        @Override
		public Object visit(OWLClass desc) {
            List<OWLClassExpression> path = getClassExpressionPath();
            if(path.size() > 1) {
                OWLClassExpression prev = path.get(path.size() - 2);
                if(prev instanceof OWLObjectComplementOf) {
                    neg.add(desc);
                }
                else {
                    pos.add(desc);
                }
            }
            else {
                pos.add(desc);
            }
            return null;
        }
    }
}
