package org.obolibrary.oboformat.model;

import javax.annotation.Nonnull;

/** The Class FrameStructureException. */
public class FrameStructureException extends DocumentStructureException {

    // generated
    private static final long serialVersionUID = 40000L;

    /**
     * Instantiates a new frame structure exception.
     * 
     * @param msg
     *        the msg
     */
    public FrameStructureException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new frame structure exception.
     * 
     * @param frame
     *        the frame
     * @param msg
     *        the msg
     */
    public FrameStructureException(@Nonnull Frame frame, String msg) {
        super(msg + " in frame:" + frame);
    }
}
