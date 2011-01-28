package org.coode.owlapi.latex;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 25-Aug-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class LatexWriter {

    private PrintWriter writer;

    public LatexWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void write(Object o) {
        writer.write(o.toString());
    }

    public void writeNewLine() {
        writer.write("\\\\\n");
    }

    public void writeSpace() {
        writer.write("~");
    }

    public void writeOpenBrace() {
        writer.write("\\{");
    }

    public void writeCloseBrace() {
        writer.write("\\}");
    }

    public void flush() {
        writer.flush();
    }
}
