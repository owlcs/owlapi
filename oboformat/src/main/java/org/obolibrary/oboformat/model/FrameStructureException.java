package org.obolibrary.oboformat.model;

/** The Class FrameStructureException. */
public class FrameStructureException extends DocumentStructureException {

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
    public FrameStructureException(Frame frame, String msg) {
        super(msg + " in frame:" + frame);
    }
}
