package com.clarkparsia.owlapi.explanation.io;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import com.clarkparsia.owlapi.explanation.io.ExplanationRenderer;

/**
 * <p>Title: </p>
 * <p/>
 * <p>Description: </p>
 * <p/>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p/>
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 * @author Evren Sirin
 */
public class SilentExplanationRenderer implements ExplanationRenderer {

    public void endRendering() {
        // do nothing
    }


    public void render(OWLAxiom axiom, Set<Set<OWLAxiom>> explanations) throws OWLException, IOException {
        // do nothing
    }


    public void startRendering(Writer writer) {
        // do nothing
    }
}
