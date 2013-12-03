package org.obolibrary.oboformat.parser;

public class OBOFormatDanglingReferenceException extends OBOFormatException {

	private static final long serialVersionUID = -7805725187214533880L;

	public OBOFormatDanglingReferenceException() {
	}

	public OBOFormatDanglingReferenceException(String message) {
		super(message);
	}

	public OBOFormatDanglingReferenceException(Throwable e) {
		super(e);
	}

	public OBOFormatDanglingReferenceException(String message, Throwable e) {
		super(message, e);
	}

}
