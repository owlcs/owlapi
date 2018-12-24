package org.semanticweb.owlapi6.manchestersyntax.renderer;

import java.io.StringWriter;

import org.semanticweb.owlapi6.annotations.Renders;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.io.OWLObjectRenderer;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;

/**
 * Adapter for running as OWLObjectRenderer from ToStringRenderer.
 */
@Renders(ManchesterSyntaxDocumentFormat.class)
public class ManchesterObjectRenderer implements OWLObjectRenderer {
    private PrefixManager pm;

    /**
     * Default constructor.
     */
    public ManchesterObjectRenderer() {
        pm = new PrefixManagerImpl();
    }

    @Override
    public String render(OWLObject object) {
        StringWriter w = new StringWriter();
        ManchesterOWLSyntaxObjectRenderer r = new ManchesterOWLSyntaxObjectRenderer(w, pm);
        object.accept(r);
        return w.toString();
    }

    @Override
    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        // need a prefix manager
        throw new UnsupportedOperationException(
            "ManchesterObjectRenderer needs a prefix manager, not a short form provider");
    }

    @Override
    public void setPrefixManager(PrefixManager shortFormProvider) {
        pm = shortFormProvider;
    }
}
