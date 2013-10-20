package org.coode.owlapi.rdf.renderer;

import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;

/** Comparator for triples */
public class TripleComparator implements Comparator<RDFTriple>, Serializable {
    private static final long serialVersionUID = 40000L;
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

    @Override
    public int compare(RDFTriple o1, RDFTriple o2) {
        int diff = getIndex(o1.getPredicate().getIRI())
                - getIndex(o2.getPredicate().getIRI());
        if (diff == 0) {
            // Compare by subject, then predicate, then object
            if (!o1.getSubject().isAnonymous()) {
                if (!o2.getSubject().isAnonymous()) {
                    diff = o1.getSubject().getIRI().compareTo(o2.getSubject().getIRI());
                } else {
                    diff = -1;
                }
            } else {
                if (!o2.getSubject().isAnonymous()) {
                    diff = 1;
                } else {
                    diff = 0;
                }
            }
            if (diff == 0) {
                diff = o2.getPredicate().getIRI().compareTo(o2.getPredicate().getIRI());
                if (diff == 0) {
                    if (!o1.getObject().isLiteral()) {
                        // Resource
                        if (!o2.getObject().isLiteral()) {
                            // Resource
                            if (!o1.getObject().isAnonymous()) {
                                if (!o2.getObject().isAnonymous()) {
                                    diff = o1.getObject().getIRI()
                                            .compareTo(o2.getObject().getIRI());
                                } else {
                                    diff = -1;
                                }
                            } else {
                                if (!o2.getObject().isAnonymous()) {
                                    diff = 1;
                                } else {
                                    diff = -1;
                                }
                            }
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
                            RDFLiteral lit1 = (RDFLiteral) o1.getObject();
                            RDFLiteral lit2 = (RDFLiteral) o2.getObject();
                            if (!lit1.isPlainLiteral()) {
                                if (!lit2.isPlainLiteral()) {
                                    diff = lit1.getLexicalValue().compareTo(
                                            lit2.getLexicalValue());
                                    if (diff == 0) {
                                        diff = lit1.getDatatype().compareTo(
                                                lit2.getDatatype());
                                    }
                                } else {
                                    diff = -1;
                                }
                            } else {
                                if (!lit2.isPlainLiteral()) {
                                    diff = 1;
                                } else {
                                    if (lit1.hasLang()) {
                                        if (lit2.hasLang()) {
                                            diff = lit1.getLang().compareTo(
                                                    lit2.getLang());
                                        }
                                    } else {
                                        diff = -1;
                                    }
                                    if (diff == 0) {
                                        diff = lit1.getLexicalValue().compareTo(
                                                lit2.getLexicalValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return diff;
    }
}
