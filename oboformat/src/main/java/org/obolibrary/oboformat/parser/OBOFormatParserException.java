package org.obolibrary.oboformat.parser;

public class OBOFormatParserException extends OBOFormatException {

	// generated
	private static final long serialVersionUID = 7346016707770104873L;
	
	private final int lineNo;
	private final String line;

	/**
	 * @param message
	 * @param e
	 * @param lineNo
	 * @param line 
	 */
	OBOFormatParserException(String message, Throwable e, int lineNo, String line) {
		super(message, e);
		this.lineNo = lineNo;
		this.line = line;
	}

	/**
	 * @param message
	 * @param lineNo
	 * @param line 
	 */
	OBOFormatParserException(String message, int lineNo, String line) {
		super(message);
		this.lineNo = lineNo;
		this.line = line;
	}

	/**
	 * @param e
	 * @param lineNo
	 * @param line 
	 */
	OBOFormatParserException(Throwable e, int lineNo, String line) {
		super(e);
		this.lineNo = lineNo;
		this.line = line;
	}

	/**
	 * @return the lineNo
	 */
	public int getLineNo() {
		return lineNo;
	}

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder("LINENO: ");
		sb.append(lineNo);
		sb.append(" - ");
		sb.append(super.getMessage());
		sb.append("\nLINE: ");
		sb.append(line);
		return sb.toString();
	}

	@Override
	public String toString() {
		return getMessage();
	}

}
