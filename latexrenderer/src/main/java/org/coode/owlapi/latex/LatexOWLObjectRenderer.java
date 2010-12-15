package org.coode.owlapi.latex;

import java.io.StringWriter;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2007<br><br>
 */
public class LatexOWLObjectRenderer implements OWLObjectRenderer {

    private OWLDataFactory dataFactory;

    private ShortFormProvider shortFormProvider;

    public LatexOWLObjectRenderer(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }


    public String render(OWLObject object) {
        StringWriter writer = new StringWriter();
        LatexWriter latexWriter = new LatexWriter(writer);
        LatexObjectVisitor visitor = new LatexObjectVisitor(latexWriter, dataFactory);

        object.accept(visitor);
        return writer.getBuffer().toString();
    }


    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

}
