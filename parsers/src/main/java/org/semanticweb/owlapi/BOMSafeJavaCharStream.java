/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLRuntimeException;

/** JavaCC generated JavaCharStream with added treatment for BOMs. */
public class BOMSafeJavaCharStream {

    /** Whether parser is static. */
    public static final boolean staticFlag = false;

    private static int hexval(char c) throws IOException {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
            case 'A':
                return 10;
            case 'b':
            case 'B':
                return 11;
            case 'c':
            case 'C':
                return 12;
            case 'd':
            case 'D':
                return 13;
            case 'e':
            case 'E':
                return 14;
            case 'f':
            case 'F':
                return 15;
            default:
                break;
        }
        throw new IOException(); // Should never come here
    }

    /** Position in buffer. */
    public int bufpos = -1;
    protected int bufsize;
    protected int available;
    protected int tokenBegin;
    protected int[] bufline;
    protected int[] bufcolumn;
    protected int column = 0;
    protected int line = 1;
    protected boolean prevCharIsCR = false;
    protected boolean prevCharIsLF = false;
    protected Reader inputStream;
    protected char[] nextCharBuf;
    protected char[] buffer;
    protected int maxNextCharInd = 0;
    protected int nextCharInd = -1;
    protected int inBuf = 0;
    protected int tabSize = 8;
    private boolean beginning = true;

    private void ExpandBuff(boolean wrapAround) {
        char[] newbuffer = new char[bufsize + 2048];
        int[] newbufline = new int[bufsize + 2048];
        int[] newbufcolumn = new int[bufsize + 2048];
        if (wrapAround) {
            System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize
                    - tokenBegin);
            System.arraycopy(buffer, 0, newbuffer, bufsize - tokenBegin, bufpos);
            buffer = newbuffer;
            System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize
                    - tokenBegin);
            System.arraycopy(bufline, 0, newbufline, bufsize - tokenBegin,
                    bufpos);
            bufline = newbufline;
            System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize
                    - tokenBegin);
            System.arraycopy(bufcolumn, 0, newbufcolumn, bufsize - tokenBegin,
                    bufpos);
            bufcolumn = newbufcolumn;
            bufpos += bufsize - tokenBegin;
        } else {
            System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize
                    - tokenBegin);
            buffer = newbuffer;
            System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize
                    - tokenBegin);
            bufline = newbufline;
            System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize
                    - tokenBegin);
            bufcolumn = newbufcolumn;
            bufpos -= tokenBegin;
        }
        available = bufsize += 2048;
        tokenBegin = 0;
    }

    private void FillBuff() throws IOException {
        int i;
        if (maxNextCharInd == 4096) {
            maxNextCharInd = nextCharInd = 0;
        }
        try {
            if ((i = inputStream.read(nextCharBuf, maxNextCharInd,
                    4096 - maxNextCharInd)) == -1) {
                inputStream.close();
                throw new IOException();
            } else {
                maxNextCharInd += i;
            }
            // this treats the BOM problem; when the parser is regenerated,
            // this needs to be applied again
            if (beginning && nextCharBuf[0] == '\uFEFF') {
                nextCharInd++;
                beginning = false;
            }
            return;
        } catch (IOException e) {
            if (bufpos != 0) {
                --bufpos;
                backup(0);
            } else {
                bufline[bufpos] = line;
                bufcolumn[bufpos] = column;
            }
            throw e;
        }
    }

    private char ReadByte() throws IOException {
        if (++nextCharInd >= maxNextCharInd) {
            FillBuff();
        }
        return nextCharBuf[nextCharInd];
    }

    /**
     * @return starting character for token.
     * @throws IOException
     *         IO exception
     */
    public char BeginToken() throws IOException {
        if (inBuf > 0) {
            --inBuf;
            if (++bufpos == bufsize) {
                bufpos = 0;
            }
            tokenBegin = bufpos;
            return buffer[bufpos];
        }
        tokenBegin = 0;
        bufpos = -1;
        return readChar();
    }

    private void AdjustBuffSize() {
        if (available == bufsize) {
            if (tokenBegin > 2048) {
                bufpos = 0;
                available = tokenBegin;
            } else {
                ExpandBuff(false);
            }
        } else if (available > tokenBegin) {
            available = bufsize;
        } else if (tokenBegin - available < 2048) {
            ExpandBuff(true);
        } else {
            available = tokenBegin;
        }
    }

    private void UpdateLineColumn(char c) {
        column++;
        if (prevCharIsLF) {
            prevCharIsLF = false;
            line += column = 1;
        } else if (prevCharIsCR) {
            prevCharIsCR = false;
            if (c == '\n') {
                prevCharIsLF = true;
            } else {
                line += column = 1;
            }
        }
        switch (c) {
            case '\r':
                prevCharIsCR = true;
                break;
            case '\n':
                prevCharIsLF = true;
                break;
            case '\t':
                column--;
                column += tabSize - column % tabSize;
                break;
            default:
                break;
        }
        bufline[bufpos] = line;
        bufcolumn[bufpos] = column;
    }

    /**
     * Read a character.
     * 
     * @return character
     * @throws IOException
     *         IO exception
     */
    public char readChar() throws IOException {
        if (inBuf > 0) {
            --inBuf;
            if (++bufpos == bufsize) {
                bufpos = 0;
            }
            return buffer[bufpos];
        }
        char c;
        if (++bufpos == available) {
            AdjustBuffSize();
        }
        if ((buffer[bufpos] = c = ReadByte()) == '\\') {
            UpdateLineColumn(c);
            int backSlashCnt = 1;
            for (;;) // Read all the backslashes
            {
                if (++bufpos == available) {
                    AdjustBuffSize();
                }
                try {
                    if ((buffer[bufpos] = c = ReadByte()) != '\\') {
                        UpdateLineColumn(c);
                        // found a non-backslash char.
                        if (c == 'u' && (backSlashCnt & 1) == 1) {
                            if (--bufpos < 0) {
                                bufpos = bufsize - 1;
                            }
                            break;
                        }
                        backup(backSlashCnt);
                        return '\\';
                    }
                } catch (IOException e) {
                    // We are returning one backslash so we should only backup
                    // (count-1)
                    if (backSlashCnt > 1) {
                        backup(backSlashCnt - 1);
                    }
                    return '\\';
                }
                UpdateLineColumn(c);
                backSlashCnt++;
            }
            // Here, we have seen an odd number of backslash's followed by a 'u'
            try {
                while ((c = ReadByte()) == 'u') {
                    ++column;
                }
                buffer[bufpos] = c = (char) (hexval(c) << 12
                        | hexval(ReadByte()) << 8 | hexval(ReadByte()) << 4 | hexval(ReadByte()));
                column += 4;
            } catch (IOException e) {
                throw new OWLRuntimeException(
                        "Invalid escape character at line " + line + " column "
                                + column + ".", e);
            }
            if (backSlashCnt == 1) {
                return c;
            } else {
                backup(backSlashCnt - 1);
                return '\\';
            }
        } else {
            UpdateLineColumn(c);
            return c;
        }
    }

    /** @return end column. */
    public int getEndColumn() {
        return bufcolumn[bufpos];
    }

    /** @return end line. */
    public int getEndLine() {
        return bufline[bufpos];
    }

    /** @return column of token start */
    public int getBeginColumn() {
        return bufcolumn[tokenBegin];
    }

    /** @return line number of token start */
    public int getBeginLine() {
        return bufline[tokenBegin];
    }

    /**
     * Retreat.
     * 
     * @param amount
     *        number of characters to back up
     */
    public void backup(int amount) {
        inBuf += amount;
        if ((bufpos -= amount) < 0) {
            bufpos += bufsize;
        }
    }

    /**
     * @param dstream
     *        stream
     * @param startline
     *        start line
     * @param startcolumn
     *        start column
     * @param buffersize
     *        buffer size
     */
    protected BOMSafeJavaCharStream(Reader dstream, int startline,
            int startcolumn, int buffersize) {
        inputStream = dstream;
        line = startline;
        column = startcolumn - 1;
        available = bufsize = buffersize;
        buffer = new char[buffersize];
        bufline = new int[buffersize];
        bufcolumn = new int[buffersize];
        nextCharBuf = new char[4096];
    }

    /**
     * @param dstream
     *        stream
     * @param startline
     *        start line
     * @param startcolumn
     *        start column
     */
    public BOMSafeJavaCharStream(Reader dstream, int startline, int startcolumn) {
        this(dstream, startline, startcolumn, 4096);
    }

    private void ReInit(Reader dstream, int startline, int startcolumn,
            int buffersize) {
        inputStream = dstream;
        line = startline;
        column = startcolumn - 1;
        if (buffer == null || buffersize != buffer.length) {
            available = bufsize = buffersize;
            buffer = new char[buffersize];
            bufline = new int[buffersize];
            bufcolumn = new int[buffersize];
            nextCharBuf = new char[4096];
        }
        prevCharIsLF = prevCharIsCR = false;
        tokenBegin = inBuf = maxNextCharInd = 0;
        nextCharInd = bufpos = -1;
    }

    /**
     * Reinitialise.
     * 
     * @param dstream
     *        stream
     * @param startline
     *        start line
     * @param startcolumn
     *        start column
     */
    public void ReInit(Reader dstream, int startline, int startcolumn) {
        ReInit(dstream, startline, startcolumn, 4096);
    }

    /**
     * @param dstream
     *        stream
     * @param encoding
     *        encoding
     * @param startline
     *        start line
     * @param startcolumn
     *        start column
     * @param buffersize
     *        buffer size
     * @throws UnsupportedEncodingException
     *         if encoding unsupported
     */
    protected BOMSafeJavaCharStream(InputStream dstream,
            @Nullable String encoding, int startline, int startcolumn,
            int buffersize) throws UnsupportedEncodingException {
        this(encoding == null ? new InputStreamReader(dstream, "UTF-8")
                : new InputStreamReader(dstream, encoding), startline,
                startcolumn, buffersize);
    }

    /**
     * @param dstream
     *        stream
     * @param encoding
     *        encoding
     * @param startline
     *        start line
     * @param startcolumn
     *        start column
     * @throws UnsupportedEncodingException
     *         if encoding unsupported
     */
    public BOMSafeJavaCharStream(InputStream dstream, String encoding,
            int startline, int startcolumn) throws UnsupportedEncodingException {
        this(dstream, encoding, startline, startcolumn, 4096);
    }

    private void ReInit(InputStream dstream, @Nullable String encoding,
            int startline, int startcolumn, int buffersize)
            throws UnsupportedEncodingException {
        ReInit(encoding == null ? new InputStreamReader(dstream, "UTF-8")
                : new InputStreamReader(dstream, encoding), startline,
                startcolumn, buffersize);
    }

    /**
     * Reinitialise.
     * 
     * @param dstream
     *        stream
     * @param encoding
     *        encoding
     * @param startline
     *        start line
     * @param startcolumn
     *        start column
     * @throws UnsupportedEncodingException
     *         for unsupported encoding
     */
    public void ReInit(InputStream dstream, String encoding, int startline,
            int startcolumn) throws UnsupportedEncodingException {
        ReInit(dstream, encoding, startline, startcolumn, 4096);
    }

    /** @return token image as String */
    @Nonnull
    public String GetImage() {
        if (bufpos >= tokenBegin) {
            return new String(buffer, tokenBegin, bufpos - tokenBegin + 1);
        } else {
            return new String(buffer, tokenBegin, bufsize - tokenBegin)
                    + new String(buffer, 0, bufpos + 1);
        }
    }
}
