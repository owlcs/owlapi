package org.semanticweb.owlapi6.reasoner.structural;

import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.reasoner.Node;
import org.semanticweb.owlapi6.reasoner.OWLReasoner;

/** Utility for structure dumping */
class SystemOutDump {
    static void dumpClassHierarchy(Node<OWLClass> cls, int level, boolean showBottomNode,
        OWLReasoner r) {
        if (!showBottomNode && cls.isBottomNode()) {
            return;
        }
        printIndent(level);
        OWLClass representative = cls.getRepresentativeElement();
        System.out.println(r.getEquivalentClasses(representative));
        for (Node<OWLClass> subCls : r.getSubClasses(representative, true)) {
            dumpClassHierarchy(subCls, level + 1, showBottomNode, r);
        }
    }

    static void dumpObjectPropertyHierarchy(Node<OWLObjectPropertyExpression> cls, int level,
        boolean showBottomNode, OWLReasoner r) {
        if (!showBottomNode && cls.isBottomNode()) {
            return;
        }
        printIndent(level);
        OWLObjectPropertyExpression representative = cls.getRepresentativeElement();
        System.out.println(r.getEquivalentObjectProperties(representative));
        for (Node<OWLObjectPropertyExpression> subProp : r.getSubObjectProperties(representative,
            true)) {
            dumpObjectPropertyHierarchy(subProp, level + 1, showBottomNode, r);
        }
    }

    static void dumpDataPropertyHierarchy(Node<OWLDataProperty> cls, int level,
        boolean showBottomNode, OWLReasoner r) {
        if (!showBottomNode && cls.isBottomNode()) {
            return;
        }
        printIndent(level);
        OWLDataProperty representative = cls.getRepresentativeElement();
        System.out.println(r.getEquivalentDataProperties(representative));
        for (Node<OWLDataProperty> subProp : r.getSubDataProperties(representative, true)) {
            dumpDataPropertyHierarchy(subProp, level + 1, showBottomNode, r);
        }
    }

    private static void printIndent(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
    }

    private SystemOutDump() {}
}
