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
    @Nonnull protected static final OWLClass                OWL_THING                   = new OWLClassImpl                  (OWLRDFVocabulary.OWL_THING.getIRI());
    @Nonnull protected static final OWLClass                OWL_NOTHING                 = new OWLClassImpl                  (OWLRDFVocabulary.OWL_NOTHING.getIRI());
    @Nonnull protected static final OWLAnnotationProperty   RDFS_LABEL                  = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_LABEL.getIRI());
    @Nonnull protected static final OWLAnnotationProperty   RDFS_COMMENT                = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    @Nonnull protected static final OWLAnnotationProperty   RDFS_SEE_ALSO               = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_SEE_ALSO.getIRI());
    @Nonnull protected static final OWLAnnotationProperty   RDFS_IS_DEFINED_BY          = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_IS_DEFINED_BY.getIRI());
    @Nonnull protected static final OWLAnnotationProperty   OWL_BACKWARD_COMPATIBLE_WITH= new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
    @Nonnull protected static final OWLAnnotationProperty   OWL_INCOMPATIBLE_WITH       = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());
    @Nonnull protected static final OWLAnnotationProperty   OWL_VERSION_INFO            = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_VERSION_INFO.getIRI());
    @Nonnull protected static final OWLAnnotationProperty   OWL_DEPRECATED              = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    @Nonnull protected static final OWLObjectProperty       OWL_TOP_OBJECT_PROPERTY     = new OWLObjectPropertyImpl         (OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    @Nonnull protected static final OWLObjectProperty       OWL_BOTTOM_OBJECT_PROPERTY  = new OWLObjectPropertyImpl         (OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    @Nonnull protected static final OWLDataProperty         OWL_TOP_DATA_PROPERTY       = new OWLDataPropertyImpl           (OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    @Nonnull protected static final OWLDataProperty         OWL_BOTTOM_DATA_PROPERTY    = new OWLDataPropertyImpl           (OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    @Nonnull protected static final OWLDatatype             PLAIN                       = new OWL2DatatypeImpl              (RDF_PLAIN_LITERAL);
    @Nonnull protected static final OWLDatatype             LANGSTRING                  = new OWL2DatatypeImpl              (RDF_LANG_STRING);
    @Nonnull protected static final OWLDatatype             XSDBOOLEAN                  = new OWL2DatatypeImpl              (XSD_BOOLEAN);
    @Nonnull protected static final OWLDatatype             XSDDOUBLE                   = new OWL2DatatypeImpl              (XSD_DOUBLE);
    @Nonnull protected static final OWLDatatype             XSDFLOAT                    = new OWL2DatatypeImpl              (XSD_FLOAT);
    @Nonnull protected static final OWLDatatype             XSDINTEGER                  = new OWL2DatatypeImpl              (XSD_INTEGER);
    @Nonnull protected static final OWLDatatype             XSDSTRING                   = new OWL2DatatypeImpl              (XSD_STRING);
    @Nonnull protected static final OWLDatatype             RDFSLITERAL                 = new OWL2DatatypeImpl              (RDFS_LITERAL);
    @Nonnull protected static final OWLLiteral              TRUELITERAL                 = new OWLLiteralImplBoolean         (true);
    @Nonnull protected static final OWLLiteral              FALSELITERAL                = new OWLLiteralImplBoolean         (false);
  //@formatter:on

    private InternalizedEntities() {}
}
