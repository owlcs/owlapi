package org.obolibrary.oboformat.parser;

/** The Class OBOFormatParserException. */
public class OBOFormatParserException extends OBOFormatException {

    // generated
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7346016707770104873L;
    /** The line no. */
    private final int lineNo;
    /** The line. */
    private final String line;

    /**
     * Instantiates a new oBO format parser exception.
     * 
     * @param message
     *        the message
     * @param e
     *        the cause
     * @param lineNo
     *        the line no
     * @param line
     *        the line
     */
    public OBOFormatParserException(String message, Throwable e, int lineNo,
            String line) {
        super(message, e);
        this.lineNo = lineNo;
        this.line = line;
    }

    /**
     * Instantiates a new oBO format parser exception.
     * 
     * @param message
     *        the message
     * @param lineNo
     *        the line no
     * @param line
     *        the line
     */
    public OBOFormatParserException(String message, int lineNo, String line) {
        super(message);
        this.lineNo = lineNo;
        this.line = line;
    }

    /**
     * Instantiates a new oBO format parser exception.
     * 
     * @param e
     *        the e
     * @param lineNo
     *        the line no
     * @param line
     *        the line
     */
    public OBOFormatParserException(Throwable e, int lineNo, String line) {
        super(e);
        this.lineNo = lineNo;
        this.line = line;
    }

    /**
     * Gets the line no.
     * 
     * @return the lineNo
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Gets the line.
     * 
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
