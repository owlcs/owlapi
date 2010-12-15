package org.coode.owlapi.latex;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AxiomTypeProvider;
import org.semanticweb.owlapi.util.OWLEntityComparator;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 15-Jun-2007<br><br>
 */
public class LatexRenderer extends AbstractOWLRenderer {

    private ShortFormProvider shortFormProvider;

    public LatexRenderer(OWLOntologyManager owlOntologyManager) {
        super(owlOntologyManager);
        shortFormProvider = new SimpleShortFormProvider();
    }

    private void writeEntitySection(OWLEntity entity, LatexWriter w) {
        w.write("\\subsubsection*{");
        w.write(shortFormProvider.getShortForm(entity));
        w.write("}\n\n");
    }

    @Override
	public void render(OWLOntology ontology, Writer writer) throws OWLRendererException {
        try {


            LatexWriter w = new LatexWriter(writer);
            w.write("\\documentclass{article}\n");
            w.write("\\parskip 0pt\n");
            w.write("\\parindent 0pt\n");
            w.write("\\oddsidemargin 0cm\n");
            w.write("\\textwidth 19cm\n");

            w.write("\\begin{document}\n\n");

            LatexObjectVisitor renderer = new LatexObjectVisitor(w, getOWLOntologyManager().getOWLDataFactory());

            Collection<OWLClass> clses = sortEntities(ontology.getClassesInSignature());
            if (!clses.isEmpty()) {
                w.write("\\subsection*{Classes}\n\n");
            }
            for (OWLClass cls : clses) {
                writeEntitySection(cls, w);
                for (OWLAxiom ax : sortAxioms(ontology.getAxioms(cls))) {
                    renderer.setSubject(cls);
                    ax.accept(renderer);
                    w.write("\n\n");
                }
            }

            w.write("\\section*{Object properties}");

            for (OWLObjectProperty prop : sortEntities(ontology.getObjectPropertiesInSignature())) {
                writeEntitySection(prop, w);
                for (OWLAxiom ax : sortAxioms(ontology.getAxioms(prop))) {
                    ax.accept(renderer);
                    w.write("\n\n");
                }
            }

            w.write("\\section*{Data properties}");

            for (OWLDataProperty prop : sortEntities(ontology.getDataPropertiesInSignature())) {
                writeEntitySection(prop, w);
                for (OWLAxiom ax : sortAxioms(ontology.getAxioms(prop))) {
                    ax.accept(renderer);
                    w.write("\n\n");
                }
            }

            w.write("\\section*{Individuals}");

            for (OWLNamedIndividual ind : sortEntities(ontology.getIndividualsInSignature())) {
                writeEntitySection(ind, w);
                for (OWLAxiom ax : sortAxioms(ontology.getAxioms(ind))) {
                    ax.accept(renderer);
                    w.write("\n\n");
                }
            }

            writer.write(w.toString());
            writer.write("\\end{document}\n");
            writer.flush();
        }
        catch (IOException e) {
            throw new LatexRendererIOException(e);
        }
    }

    private <T extends OWLEntity> Collection<T> sortEntities(Set<T> entites) {
        List<T> list = new ArrayList<T>(entites);
        OWLEntityComparator entityComparator = new OWLEntityComparator(shortFormProvider);
        Collections.sort(list, entityComparator);
        return list;
    }

    private Collection<OWLAxiom> sortAxioms(Set<? extends OWLAxiom> axioms) {
        List<OWLAxiom> list = new ArrayList<OWLAxiom>(axioms);
        Collections.sort(list, new OWLAxiomComparator());
        return list;
    }


    private static class OWLAxiomComparator implements Comparator<OWLAxiom> {

        public int compare(OWLAxiom o1, OWLAxiom o2) {
            AxiomTypeProvider provider = new AxiomTypeProvider();
            int index1 = provider.getAxiomType(o1).getIndex();
            int index2 = provider.getAxiomType(o2).getIndex();
            return index1 - index2;
        }
    }
}
