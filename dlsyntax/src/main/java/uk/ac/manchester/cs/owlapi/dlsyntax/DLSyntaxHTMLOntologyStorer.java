package uk.ac.manchester.cs.owlapi.dlsyntax;

import java.io.PrintWriter;
import java.util.Set;

import org.coode.string.EscapeUtils;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Feb-2008<br><br>
 */
@SuppressWarnings("unused")
public class DLSyntaxHTMLOntologyStorer extends DLSyntaxOntologyStorerBase {

    private ShortFormProvider sfp;


    public DLSyntaxHTMLOntologyStorer() {
        sfp = new SimpleShortFormProvider();
    }


    public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
        return ontologyFormat.equals(new DLSyntaxHTMLOntologyFormat());
    }


    @Override
	protected String getRendering(final OWLEntity subject, OWLAxiom axiom) {
        DLSyntaxObjectRenderer ren = new DLSyntaxObjectRenderer() {

            @Override
			protected String renderEntity(OWLEntity entity) {
                if (!entity.equals(subject)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<a href=\"#");
                    sb.append(sfp.getShortForm(entity));
                    sb.append("\">");
                    sb.append(sfp.getShortForm(entity));
                    sb.append("</a>");
                    return sb.toString();
                }
                else {
                    return sfp.getShortForm(entity);
                }
            }


            @Override
			protected void write(DLSyntax keyword) {
                super.write(EscapeUtils.escapeXML(keyword.toString()));
            }
        };
        ren.setFocusedObject(subject);
        ren.setShortFormProvider(sfp);
        String rendering = ren.render(axiom);
        return rendering;
    }


    @Override
	protected void beginWritingOntology(OWLOntology ontology, PrintWriter writer) {
        writer.println("<html>");

        writer.println("<body>");
        writer.print("<h1>");
        writer.print("Ontology: ");
        writer.print(ontology.getOntologyID().toString());
        writer.println("</h1>");
    }

    protected void writeEntity(OWLEntity entity, PrintWriter writer) {

    }

    @Override
	protected void endWritingOntology(OWLOntology ontology, PrintWriter writer) {
        writer.println("</body>");
        writer.println("</html>");
    }


    @Override
	protected void beginWritingAxiom(OWLAxiom axiom, PrintWriter writer) {
        writer.println("<div class=\"axiombox\"> ");
    }


    @Override
	protected void endWritingAxiom(OWLAxiom axiom, PrintWriter writer) {
        writer.println(" </div>");
    }


    @Override
	protected void beginWritingAxioms(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.print("<h2><a name=\"");
        writer.print(sfp.getShortForm(subject));
        writer.print("\">");
        writer.print(subject.toString());
        writer.println("</a></h2>");
        writer.println("<div class=\"entitybox\">");
    }


    @Override
	protected void endWritingAxioms(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("</div>");
    }


    @Override
	protected void beginWritingGeneralAxioms(Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("<div>");
    }


    @Override
	protected void endWritingGeneralAxioms(Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("</div>");
    }


    @Override
	protected void beginWritingUsage(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("<div class=\"usage\" style=\"margin-left: 60px; size: tiny\">");
        writer.print("<h3>Usages (" + axioms.size() + ")</h3>");

    }


    @Override
	protected void endWritingUsage(OWLEntity subject, Set<? extends OWLAxiom> axioms, PrintWriter writer) {
        writer.println("</div>");
    }
}
