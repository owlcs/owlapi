package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import org.semanticweb.owlapi.model.OWLObject;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 15-Jul-2009
 */
public class RendererEvent {

    private ManchesterOWLSyntaxFrameRenderer frameRenderer;

    private OWLObject frameSubject;

    public RendererEvent(ManchesterOWLSyntaxFrameRenderer frameRenderer, OWLObject frameSubject) {
        this.frameSubject = frameSubject;
        this.frameRenderer = frameRenderer;
    }

    public ManchesterOWLSyntaxFrameRenderer getFrameRenderer() {
        return frameRenderer;
    }

    public void writeComment(String comment) {
        frameRenderer.writeComment(comment, false);
    }

    public void writeCommentOnNewLine(String comment) {
        frameRenderer.writeComment(comment, true);
    }

    public OWLObject getFrameSubject() {
        return frameSubject;
    }
}
