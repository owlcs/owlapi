package uk.ac.manchester.cs.owl.mansyntaxrenderer;

import org.semanticweb.owl.io.OWLObjectRenderer;
import org.semanticweb.owl.model.OWLObject;
import org.semanticweb.owl.util.ShortFormProvider;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2007<br><br>
 *
 * An implementation of the OWLObjectRenderer interface.  (Renders
 * standalone class descriptions and axioms in the manchester syntax).
 */
public class ManchesterOWLSyntaxOWLObjectRendererImpl implements OWLObjectRenderer {

    private ManchesterOWLSyntaxObjectRenderer ren;

    private WriterDelegate writerDelegate;

    public ManchesterOWLSyntaxOWLObjectRendererImpl() {
        writerDelegate = new WriterDelegate();
        ren = new ManchesterOWLSyntaxObjectRenderer(writerDelegate);
    }


    public String render(OWLObject object) {
        writerDelegate.reset();
        object.accept(ren);
        return writerDelegate.toString();
    }


    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        ren.setShortFormProvider(shortFormProvider);
    }

    private class WriterDelegate extends Writer {

        private StringWriter delegate;

        private void reset() {
            delegate = new StringWriter();
        }


        public String toString() {
            return delegate.getBuffer().toString();
        }


        public void close() throws IOException {
            delegate.close();
        }


        public void flush() throws IOException {
            delegate.flush();
        }


        public void write(char cbuf[], int off, int len) throws IOException {
            delegate.write(cbuf, off, len);
        }
    }
}
