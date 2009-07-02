package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public interface SWRLObjectVisitor {

    void visit(SWRLRule node);

    void visit(SWRLClassAtom node);

    void visit(SWRLDataRangeAtom node);

    void visit(SWRLObjectPropertyAtom node);

    void visit(SWRLDataValuedPropertyAtom node);

    void visit(SWRLBuiltInAtom node);

    void visit(SWRLLiteralVariable node);

    void visit(SWRLIndividualVariable node);

    void visit(SWRLIndividualArgument node);

    void visit(SWRLLiteralArgument node);

    void visit(SWRLSameAsAtom node);

    void visit(SWRLDifferentFromAtom node);
}
