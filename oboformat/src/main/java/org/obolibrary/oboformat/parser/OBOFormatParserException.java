package org.obolibrary.oboformat.parser;

import javax.annotation.Nullable;

/**
 * The Class OBOFormatParserException.
 */
public class OBOFormatParserException extends OBOFormatException {

    private final int lineNo;
    @Nullable
    private final String line;

    /**
     * @param message the message
     * @param e the cause
     * @param lineNo the line no
     * @param line the line
     */
    public OBOFormatParserException(String message, Throwable e, int lineNo,
        @Nullable String line) {
        super(message, e);
        this.lineNo = lineNo;
        this.line = line;
    }

    /**
     * @param message the message
     * @param lineNo the line no
     * @param line the line
     */
    public OBOFormatParserException(String message, int lineNo, @Nullable String line) {
        super(message);
        this.lineNo = lineNo;
        this.line = line;
    }

    /**
     * @param e the cause
     * @param lineNo the line no
     * @param line the line
     */
    public OBOFormatParserException(Throwable e, int lineNo, @Nullable String line) {
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
    @Nullable
    public String getLine() {
        return line;
    }

    @Override
    public String getMessage() {
        return "LINENO: " + lineNo + " - " + super.getMessage() + "\nLINE: " + line;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
