/* OBOParser.java */
/* Generated By:JavaCC: Do not edit this line. OBOParser.java */
package org.coode.owlapi.obo12.parser;

@SuppressWarnings("all")
public class OBOParser implements OBOParserConstants {

    private OBOParserHandler handler;

    public void setHandler(OBOParserHandler handler) {
        this.handler = handler;
    }

    final public void parse() throws ParseException {
        Header();
        label_1:
        while (true) {
            Stanza();
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case OPEN_SQUARE_BRACKET: {
                    ;
                    break;
                }
                default:
                    jj_la1[0] = jj_gen;
                    break label_1;
            }
        }
        jj_consume_token(0);
    }

    final public void Header() throws ParseException {
        handler.startHeader();
        label_2:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case TAG_NAME: {
                    ;
                    break;
                }
                default:
                    jj_la1[1] = jj_gen;
                    break label_2;
            }
            TagValuePair();
        }
        handler.endHeader();
    }

    final public void Stanza() throws ParseException {
        Token t;
        jj_consume_token(OPEN_SQUARE_BRACKET);
        t = jj_consume_token(STANZA_TYPE);
        handler.startFrame(t.image);
        jj_consume_token(CLOSE_SQUARE_BRACKET);
        label_3:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case TAG_NAME: {
                    ;
                    break;
                }
                default:
                    jj_la1[2] = jj_gen;
                    break label_3;
            }
            TagValuePair();
        }
        handler.endFrame();
    }

    final public void TagValuePair() throws ParseException {
        Token tagToken = null;
        Token valToken = null;
        String qualifierBlock = "";
        String comment = "";
        Token t;
        StringBuilder sb = new StringBuilder();
        tagToken = jj_consume_token(TAG_NAME);
        label_4:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case QUOTED_STRING:
                case STRING:
                case TAG_VALUE_WS: {
                    ;
                    break;
                }
                default:
                    jj_la1[3] = jj_gen;
                    break label_4;
            }
            switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
                case QUOTED_STRING: {
                    t = jj_consume_token(QUOTED_STRING);
                    sb.append(t.image);
                    break;
                }
                case STRING: {
                    t = jj_consume_token(STRING);
                    sb.append(t.image);
                    break;
                }
                case TAG_VALUE_WS: {
                    t = jj_consume_token(TAG_VALUE_WS);
                    sb.append(t.image);
                    break;
                }
                default:
                    jj_la1[4] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case COMMENT: {
                comment = Comment();
                break;
            }
            default:
                jj_la1[5] = jj_gen;
                ;
        }
        String name = tagToken.image.trim();
        String val = sb.toString().trim();
        // This a real mess.  The OBO format allows qualifier blocks at the end of lines.  However, things that look
        // like qualifier blocks can appear in the middle of values for comment tags.
        if (val.endsWith("}")) {
            int qualifierStart = val.lastIndexOf("{");
            if (qualifierStart != -1) {
                qualifierBlock = val.substring(qualifierStart);
                val = val.substring(0, qualifierStart).trim();
            }
        }
        handler.handleTagValue(name, val, qualifierBlock, comment);
    }

    final public String Comment() throws ParseException {
        Token t;
        t = jj_consume_token(COMMENT);
        return t.image;
    }

    /**
     * Generated Token Manager.
     */
    public OBOParserTokenManager token_source;
    JavaCharStream jj_input_stream;
    /**
     * Current token.
     */
    public Token token;
    /**
     * Next token.
     */
    public Token jj_nt;
    private int jj_ntk;
    private int jj_gen;
    final private int[] jj_la1 = new int[6];
    static private int[] jj_la1_0;

    static {
        jj_la1_init_0();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{0x8, 0x40, 0x40, 0x3200, 0x3200, 0x10000,};
    }

    /**
     * Constructor.
     */
    public OBOParser(Provider stream) {
        jj_input_stream = new JavaCharStream(stream, 1, 1);
        token_source = new OBOParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 6; i++) {
            jj_la1[i] = -1;
        }
    }

    /**
     * Constructor.
     */
    public OBOParser(String dsl) throws ParseException, TokenMgrException {
        this(new StringProvider(dsl));
    }

    public void ReInit(String s) {
        ReInit(new StringProvider(s));
    }

    /**
     * Reinitialise.
     */
    public void ReInit(Provider stream) {
        if (jj_input_stream == null) {
            jj_input_stream = new JavaCharStream(stream, 1, 1);
        } else {
            jj_input_stream.ReInit(stream, 1, 1);
        }
        if (token_source == null) {
            token_source = new OBOParserTokenManager(jj_input_stream);
        }

        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 6; i++) {
            jj_la1[i] = -1;
        }
    }

    /**
     * Constructor with generated Token Manager.
     */
    public OBOParser(OBOParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 6; i++) {
            jj_la1[i] = -1;
        }
    }

    /**
     * Reinitialise.
     */
    public void ReInit(OBOParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 6; i++) {
            jj_la1[i] = -1;
        }
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) {
            token = token.next;
        } else {
            token = token.next = token_source.getNextToken();
        }
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }


    /**
     * Get the next Token.
     */
    final public Token getNextToken() {
        if (token.next != null) {
            token = token.next;
        } else {
            token = token.next = token_source.getNextToken();
        }
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /**
     * Get the specific Token.
     */
    final public Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) {
                t = t.next;
            } else {
                t = t.next = token_source.getNextToken();
            }
        }
        return t;
    }

    private int jj_ntk_f() {
        if ((jj_nt = token.next) == null) {
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        } else {
            return (jj_ntk = jj_nt.kind);
        }
    }

    private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    private int[] jj_expentry;
    private int jj_kind = -1;

    /**
     * Generate ParseException.
     */
    public ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[19];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 6; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1 << j)) != 0) {
                        la1tokens[j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 19; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, tokenImage, token_source == null ? null
            : OBOParserTokenManager.lexStateNames[token_source.curLexState]);
    }

    private int trace_indent = 0;
    private boolean trace_enabled;

    /**
     * Trace enabled.
     */
    final public boolean trace_enabled() {
        return trace_enabled;
    }

    /**
     * Enable tracing.
     */
    final public void enable_tracing() {
    }

    /**
     * Disable tracing.
     */
    final public void disable_tracing() {
    }

}
