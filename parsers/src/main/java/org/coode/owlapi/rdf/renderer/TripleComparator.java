package org.coode.owlapi.rdf.renderer;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.coode.owlapi.rdf.model.RDFLiteralNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.semanticweb.owlapi.model.IRI;

/** Triple comparator */
public class TripleComparator implements Comparator<RDFTriple>, Serializable {
    private static final long serialVersionUID = 30406L;
    private static final List<IRI> orderedURIs = Arrays.asList(RDF_TYPE.getIRI(),
            RDFS_LABEL.getIRI(), OWL_EQUIVALENT_CLASS.getIRI(),
            RDFS_SUBCLASS_OF.getIRI(), OWL_DISJOINT_WITH.getIRI(),
            OWL_ON_PROPERTY.getIRI(), OWL_DATA_RANGE.getIRI(), OWL_ON_CLASS.getIRI());

    private int getIndex(IRI iri) {
        int index = orderedURIs.indexOf(iri);
        if (index == -1) {
            index = orderedURIs.size();
        }
        return index;
    }

    private int compareResources(RDFResourceNode a, RDFResourceNode b) {
        int diff = 0;
        if (!a.equals(b)) {
            if (!a.isAnonymous()) {
                if (!b.isAnonymous()) {
                    diff = a.getIRI().compareTo(b.getIRI());
                } else {
                    diff = -1;
                }
            } else {
                if (!b.isAnonymous()) {
                    diff = 1;
                } else {
                    diff = 0;
                }
            }
        }
        return diff;
    }

    @Override
    public int compare(RDFTriple o1, RDFTriple o2) {
        // compare by predicate, then subject, then object
        int diff = getIndex(o1.getProperty().getIRI())
                - getIndex(o2.getProperty().getIRI());
        if (diff == 0) {
            diff = compareResources(o1.getSubject(), o2.getSubject());
        }
        if (diff == 0) {
            // same predicate = both resource objects or both literal objects
            if (!o1.getObject().isLiteral()) {
                // Resource
                if (!o2.getObject().isLiteral()) {
                    // Resource
                    diff = compareResources((RDFResourceNode) o1.getObject(),
                            (RDFResourceNode) o2.getObject());
                } else {
                    // Literal
                    // Literals first?
                    diff = 1;
                }
            } else {
                // Literal
                if (!o2.getObject().isLiteral()) {
                    // Resource
                    diff = -1;
                } else {
                    // Literal
                    RDFLiteralNode lit1 = (RDFLiteralNode) o1.getObject();
                    RDFLiteralNode lit2 = (RDFLiteralNode) o2.getObject();
                    diff = compareLiterals(lit1, lit2);
                }
            }
        }
        return diff;
    }

    private int compareLiterals(RDFLiteralNode lit1, RDFLiteralNode lit2) {
        int diff = 0;
        if (lit1.isTyped()) {
            if (lit2.isTyped()) {
                diff = lit1.getLiteral().compareTo(lit2.getLiteral());
                if (diff == 0) {
                    diff = lit1.getDatatype().compareTo(lit2.getDatatype());
                }
            } else {
                diff = -1;
            }
        } else {
            if (lit2.isTyped()) {
                diff = 1;
            } else {
                if (lit1.getLang() != null) {
                    if (lit2.getLang() != null) {
                        diff = lit1.getLang().compareTo(lit2.getLang());
                    }
                } else {
                    diff = -1;
                }
                if (diff == 0) {
                    diff = lit1.getLiteral().compareTo(lit2.getLiteral());
                }
            }
        }
        return diff;
    }
}
