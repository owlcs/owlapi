package org.semanticweb.owl.model;

import org.semanticweb.owl.vocab.SWRLBuiltInsVocabulary;

import java.util.List;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2007<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public interface SWRLBuiltInAtom extends SWRLAtom<SWRLBuiltInsVocabulary> {

    List<SWRLAtomDObject> getArguments();
}
