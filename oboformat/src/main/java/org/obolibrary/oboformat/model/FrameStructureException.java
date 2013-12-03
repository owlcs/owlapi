package org.obolibrary.oboformat.model;

public class FrameStructureException extends DocumentStructureException {
	
	// generated
	private static final long serialVersionUID = -461365284401308633L;
	
	public FrameStructureException(String msg) {
		super(msg);
	}

	public FrameStructureException(Frame frame, String msg) {
		super(msg + " in frame:" + frame.toString());
	}

}
