package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.vocab.OWL2Datatype.*;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Entities that are commonly used in implementations.
 * 
 * @author ignazio
 */
public class InternalizedEntities {

//@formatter:off
    protected static final @Nonnull OWLClass                OWL_THING                   = new OWLClassImpl                  (OWLRDFVocabulary.OWL_THING.getIRI());
    protected static final @Nonnull OWLClass                OWL_NOTHING                 = new OWLClassImpl                  (OWLRDFVocabulary.OWL_NOTHING.getIRI());
    protected static final @Nonnull OWLAnnotationProperty   RDFS_LABEL                  = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_LABEL.getIRI());
    protected static final @Nonnull OWLAnnotationProperty   RDFS_COMMENT                = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    protected static final @Nonnull OWLAnnotationProperty   RDFS_SEE_ALSO               = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_SEE_ALSO.getIRI());
    protected static final @Nonnull OWLAnnotationProperty   RDFS_IS_DEFINED_BY          = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_IS_DEFINED_BY.getIRI());
    protected static final @Nonnull OWLAnnotationProperty   OWL_BACKWARD_COMPATIBLE_WITH= new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
    protected static final @Nonnull OWLAnnotationProperty   OWL_INCOMPATIBLE_WITH       = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());
    protected static final @Nonnull OWLAnnotationProperty   OWL_VERSION_INFO            = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_VERSION_INFO.getIRI());
    protected static final @Nonnull OWLAnnotationProperty   OWL_DEPRECATED              = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    protected static final @Nonnull OWLObjectProperty       OWL_TOP_OBJECT_PROPERTY     = new OWLObjectPropertyImpl         (OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    protected static final @Nonnull OWLObjectProperty       OWL_BOTTOM_OBJECT_PROPERTY  = new OWLObjectPropertyImpl         (OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    protected static final @Nonnull OWLDataProperty         OWL_TOP_DATA_PROPERTY       = new OWLDataPropertyImpl           (OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    protected static final @Nonnull OWLDataProperty         OWL_BOTTOM_DATA_PROPERTY    = new OWLDataPropertyImpl           (OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    protected static final @Nonnull OWLDatatype             PLAIN                       = new OWL2DatatypeImpl              (RDF_PLAIN_LITERAL);
    protected static final @Nonnull OWLDatatype             LANGSTRING                       = new OWL2DatatypeImpl              (RDF_LANG_STRING);
    protected static final @Nonnull OWLDatatype             XSDBOOLEAN                  = new OWL2DatatypeImpl              (XSD_BOOLEAN);
    protected static final @Nonnull OWLDatatype             XSDDOUBLE                   = new OWL2DatatypeImpl              (XSD_DOUBLE);
    protected static final @Nonnull OWLDatatype             XSDFLOAT                    = new OWL2DatatypeImpl              (XSD_FLOAT);
    protected static final @Nonnull OWLDatatype             XSDINTEGER                  = new OWL2DatatypeImpl              (XSD_INTEGER);
    protected static final @Nonnull OWLDatatype             XSDSTRING                   = new OWL2DatatypeImpl              (XSD_STRING);
    protected static final @Nonnull OWLDatatype             RDFSLITERAL                 = new OWL2DatatypeImpl              (RDFS_LITERAL);
    protected static final @Nonnull OWLLiteral              TRUELITERAL                 = new OWLLiteralImplBoolean         (true);
    protected static final @Nonnull OWLLiteral              FALSELITERAL                = new OWLLiteralImplBoolean         (false);
  //@formatter:on
}
