package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.vocab.OWL2Datatype.*;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Entities that are commonly used in implementations.
 * 
 * @author ignazio
 */
public class InternalizedEntities {

    @Nonnull
    static final OWLClass OWL_THING = new OWLClassImpl(
            OWLRDFVocabulary.OWL_THING.getIRI());
    @Nonnull
    static final OWLClass OWL_NOTHING = new OWLClassImpl(
            OWLRDFVocabulary.OWL_NOTHING.getIRI());
    @Nonnull
    static final OWLAnnotationProperty RDFS_LABEL = new OWLAnnotationPropertyImpl(
            OWLRDFVocabulary.RDFS_LABEL.getIRI());
    @Nonnull
    static final OWLAnnotationProperty RDFS_COMMENT = new OWLAnnotationPropertyImpl(
            OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    @Nonnull
    static final OWLAnnotationProperty RDFS_SEE_ALSO = new OWLAnnotationPropertyImpl(
            OWLRDFVocabulary.RDFS_SEE_ALSO.getIRI());
    @Nonnull
    static final OWLAnnotationProperty RDFS_IS_DEFINED_BY = new OWLAnnotationPropertyImpl(
            OWLRDFVocabulary.RDFS_IS_DEFINED_BY.getIRI());
    @Nonnull
    static final OWLAnnotationProperty OWL_BACKWARD_COMPATIBLE_WITH = new OWLAnnotationPropertyImpl(
            OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
    @Nonnull
    static final OWLAnnotationProperty OWL_INCOMPATIBLE_WITH = new OWLAnnotationPropertyImpl(
            OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());
    @Nonnull
    static final OWLAnnotationProperty OWL_VERSION_INFO = new OWLAnnotationPropertyImpl(
            OWLRDFVocabulary.OWL_VERSION_INFO.getIRI());
    @Nonnull
    static final OWLAnnotationProperty OWL_DEPRECATED = new OWLAnnotationPropertyImpl(
            OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    @Nonnull
    static final OWLObjectProperty OWL_TOP_OBJECT_PROPERTY = new OWLObjectPropertyImpl(
            OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    @Nonnull
    static final OWLObjectProperty OWL_BOTTOM_OBJECT_PROPERTY = new OWLObjectPropertyImpl(
            OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    @Nonnull
    static final OWLDataProperty OWL_TOP_DATA_PROPERTY = new OWLDataPropertyImpl(
            OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    @Nonnull
    static final OWLDataProperty OWL_BOTTOM_DATA_PROPERTY = new OWLDataPropertyImpl(
            OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    @Nonnull
    static final OWLDatatype PLAIN = new OWL2DatatypeImpl(RDF_PLAIN_LITERAL);
    @Nonnull
    static final OWLDatatype XSDBOOLEAN = new OWL2DatatypeImpl(XSD_BOOLEAN);
    @Nonnull
    static final OWLDatatype XSDDOUBLE = new OWL2DatatypeImpl(XSD_DOUBLE);
    @Nonnull
    static final OWLDatatype XSDFLOAT = new OWL2DatatypeImpl(XSD_FLOAT);
    @Nonnull
    static final OWLDatatype XSDINTEGER = new OWL2DatatypeImpl(XSD_INTEGER);
    @Nonnull
    static final OWLDatatype XSDSTRING = new OWL2DatatypeImpl(XSD_STRING);
    @Nonnull
    static final OWLDatatype RDFSLITERAL = new OWL2DatatypeImpl(RDFS_LITERAL);
    @Nonnull
    static final OWLLiteral TRUELITERAL = new OWLLiteralImplBoolean(true);
    @Nonnull
    static final OWLLiteral FALSELITERAL = new OWLLiteralImplBoolean(false);
}
