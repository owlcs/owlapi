package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleRenderer;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Jan-2008<br><br>
 * A utility class which can be used by implementations to provide
 * a toString rendering of OWL API objects.  The idea is that this
 * is pluggable.
 */
public class ToStringRenderer {

	private static ToStringRenderer instance;

    private OWLObjectRenderer renderer;

    private ToStringRenderer() {
        renderer = new SimpleRenderer();
    }

    public static synchronized ToStringRenderer getInstance() {
        if (instance == null) {
            instance = new ToStringRenderer();
        }
        return instance;
    }

    /**Deprecated: this method returns internal mutable state, it is not safe to use in a multithreaded environment. Use ToStringRenderer::getInstance().setShortFormProvider() to set the ShortFormProvider instead.
     * @return the current OWLObjectRenderer */
    @Deprecated
    public synchronized OWLObjectRenderer getRenderer() {
        return renderer;
    }
    
    public synchronized void setShortFormProvider(ShortFormProvider provider) {
    	renderer.setShortFormProvider(provider);
    }

    public synchronized void setRenderer(OWLObjectRenderer renderer) {
        this.renderer = renderer;
    }

    public synchronized String getRendering(OWLObject object) {
        return renderer.render(object);
    }
}
