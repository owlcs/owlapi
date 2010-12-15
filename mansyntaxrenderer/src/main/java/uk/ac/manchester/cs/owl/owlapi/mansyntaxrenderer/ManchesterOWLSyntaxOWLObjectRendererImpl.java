package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2007<br><br>
 *
 * An implementation of the OWLObjectRenderer interface.  (Renders
 * standalone class class expressions and axioms in the manchester syntax).
 */
public class ManchesterOWLSyntaxOWLObjectRendererImpl implements OWLObjectRenderer {

    private ManchesterOWLSyntaxObjectRenderer ren;

    private WriterDelegate writerDelegate;


    public ManchesterOWLSyntaxOWLObjectRendererImpl() {
        writerDelegate = new WriterDelegate();
        ren = new ManchesterOWLSyntaxObjectRenderer(writerDelegate, new SimpleShortFormProvider());
    }


    public synchronized String render(OWLObject object) {
        writerDelegate.reset();
        object.accept(ren);
        return writerDelegate.toString();
    }


    public synchronized void setShortFormProvider(ShortFormProvider shortFormProvider) {
        ren = new ManchesterOWLSyntaxObjectRenderer(writerDelegate, shortFormProvider);
    }

    private static class WriterDelegate extends Writer {

        private StringWriter delegate;

        private void reset() {
            delegate = new StringWriter();
        }


        @Override
		public String toString() {
            return delegate.getBuffer().toString();
        }


        @Override
		public void close() throws IOException {
            delegate.close();
        }


        @Override
		public void flush() throws IOException {
            delegate.flush();
        }


        @Override
		public void write(char cbuf[], int off, int len) throws IOException {
            delegate.write(cbuf, off, len);
        }
    }
}
