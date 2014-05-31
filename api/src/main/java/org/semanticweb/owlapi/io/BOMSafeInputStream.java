package org.semanticweb.owlapi.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * A class to wrap any input stream and strip off BOM markers from XML and non
 * XML formats alike
 * 
 * @deprecated this class was introduced in 3.5.1 as a stopgap, to allow for
 *             handling of BOM prepended streams without introducing a
 *             dependency on Apache Commons, which has better clases for this.
 *             Version 4 and successive will use Apache Commons.
 */
@Deprecated
public class BOMSafeInputStream extends InputStream {

    private InputStream delegate;
    private InputStream firstline;

    // Bytes Encoding Form
    // 00 00 FE FF | UTF-32, big-endian
    // FF FE 00 00 | UTF-32, little-endian
    // FE FF |UTF-16, big-endian
    // FF FE |UTF-16, little-endian
    // EF BB BF |UTF-8
    /**
     * @param d
     *        delegate stream with the actual data
     * @throws IOException
     *         if no data can be read from the delegate
     */
    public BOMSafeInputStream(InputStream d) throws IOException {
        this.delegate = d;
        int[] row = new int[10];
        int end = 0;
        int c = delegate.read();
        while (c > -1 && end < 10) {
            row[end] = c;
            end++;
            if (end < 10) {
                // only read another char if there is space in the temp array
                c = delegate.read();
            }
        }
        int begin = 0;
        // only look to skip BOMs if there are more than four bytes in the
        // stream.
        // Hard to put an ontology in three bytes anyway.
        if (end > 4) {
            if (row[0] == 0x0 && row[1] == 0x0 && row[2] == 0xFE
                    && row[3] == 0xFF) {
                begin = 4;
            } else if (row[0] == 0xFF && row[1] == 0xFE && row[2] == 0x0
                    && row[3] == 0x0) {
                begin = 4;
            } else if (row[0] == 0xFE && row[1] == 0xFF) {
                begin = 2;
            } else if (row[0] == 0xFF && row[1] == 0xFE) {
                begin = 2;
            } else if (row[0] == 0xEF && row[1] == 0xBB && row[2] == 0xBF) {
                begin = 3;
            }
        }
        // only move the actual characters that do not belong to a BOM to the
        // first line input stream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i : Arrays.copyOfRange(row, begin, end)) {
            out.write(i);
        }
        firstline = new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public int read() throws IOException {
        int i = firstline.read();
        if (i >= 0) {
            // System.out.println("BOMSafeInputStream.read() " + (char) i);
            return i;
        }
        i = delegate.read();
        // System.out.println("BOMSafeInputStream.read() " + (char) i);
        return i;
    }

    @Override
    public int available() throws IOException {
        return firstline.available() + delegate.available();
    }

    @Override
    public void close() throws IOException {
        delegate.close();
        // not bothering to try and catch an exception from the delegate in
        // order to close firstline, since it does not keep any external
        // resources open and uses very little memory itself.
        firstline.close();
    }
}
