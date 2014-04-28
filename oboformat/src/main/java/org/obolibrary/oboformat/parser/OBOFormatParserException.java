package org.obolibrary.oboformat.parser;

import javax.annotation.Nonnull;

/** The Class OBOFormatParserException. */
public class OBOFormatParserException extends OBOFormatException {

    private static final long serialVersionUID = 40000L;
    private final int lineNo;
    private final String line;

    /**
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
     * @param e
     *        the cause
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

    /** @return the lineNo */
    public int getLineNo() {
        return lineNo;
    }

    /** @return the line */
    public String getLine() {
        return line;
    }

    @Nonnull
    @Override
    public String getMessage() {
        return "LINENO: " + lineNo + " - " + super.getMessage() + "\nLINE: "
                + line;
    }

    @Nonnull
    @Override
    public String toString() {
        return getMessage();
    }
}
