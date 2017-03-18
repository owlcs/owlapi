package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDFS_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDF_LANG_STRING;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.RDF_PLAIN_LITERAL;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_BOOLEAN;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_DOUBLE;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_FLOAT;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_INTEGER;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.XSD_STRING;

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

    //@formatter:off
    /**owl:Thing.*/             public static final OWLClass                OWL_THING                   = new OWLClassImpl                  (OWLRDFVocabulary.OWL_THING.getIRI());
    /**owl:Nothing.*/           public static final OWLClass                OWL_NOTHING                 = new OWLClassImpl                  (OWLRDFVocabulary.OWL_NOTHING.getIRI());
    /**Top object propery.*/    public static final OWLObjectProperty       OWL_TOP_OBJECT_PROPERTY     = new OWLObjectPropertyImpl         (OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getIRI());
    /**Bottom object propery.*/ public static final OWLObjectProperty       OWL_BOTTOM_OBJECT_PROPERTY  = new OWLObjectPropertyImpl         (OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getIRI());
    /**Top data propery.*/      public static final OWLDataProperty         OWL_TOP_DATA_PROPERTY       = new OWLDataPropertyImpl           (OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getIRI());
    /**Bottom data propery.*/   public static final OWLDataProperty         OWL_BOTTOM_DATA_PROPERTY    = new OWLDataPropertyImpl           (OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getIRI());
    /**Top datatype.*/          public static final OWLDatatype             RDFSLITERAL                 = new OWL2DatatypeImpl              (RDFS_LITERAL);
    protected static final OWLAnnotationProperty   RDFS_LABEL                  = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_LABEL.getIRI());
    protected static final OWLAnnotationProperty   RDFS_COMMENT                = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_COMMENT.getIRI());
    protected static final OWLAnnotationProperty   RDFS_SEE_ALSO               = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_SEE_ALSO.getIRI());
    protected static final OWLAnnotationProperty   RDFS_IS_DEFINED_BY          = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.RDFS_IS_DEFINED_BY.getIRI());
    protected static final OWLAnnotationProperty   OWL_BACKWARD_COMPATIBLE_WITH= new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_BACKWARD_COMPATIBLE_WITH.getIRI());
    protected static final OWLAnnotationProperty   OWL_INCOMPATIBLE_WITH       = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_INCOMPATIBLE_WITH.getIRI());
    protected static final OWLAnnotationProperty   OWL_VERSION_INFO            = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_VERSION_INFO.getIRI());
    protected static final OWLAnnotationProperty   OWL_DEPRECATED              = new OWLAnnotationPropertyImpl     (OWLRDFVocabulary.OWL_DEPRECATED.getIRI());
    protected static final OWLDatatype             PLAIN                       = new OWL2DatatypeImpl              (RDF_PLAIN_LITERAL);
    protected static final OWLDatatype             LANGSTRING                  = new OWL2DatatypeImpl              (RDF_LANG_STRING);
    protected static final OWLDatatype             XSDBOOLEAN                  = new OWL2DatatypeImpl              (XSD_BOOLEAN);
    protected static final OWLDatatype             XSDDOUBLE                   = new OWL2DatatypeImpl              (XSD_DOUBLE);
    protected static final OWLDatatype             XSDFLOAT                    = new OWL2DatatypeImpl              (XSD_FLOAT);
    protected static final OWLDatatype             XSDINTEGER                  = new OWL2DatatypeImpl              (XSD_INTEGER);
    protected static final OWLDatatype             XSDSTRING                   = new OWL2DatatypeImpl              (XSD_STRING);
    protected static final OWLLiteral              TRUELITERAL                 = new OWLLiteralImplBoolean         (true);
    protected static final OWLLiteral              FALSELITERAL                = new OWLLiteralImplBoolean         (false);
    // according to some W3C test, this needs to be different from 0.0; Java floats disagree
    protected static final OWLLiteral              negativeFloatZero           = new OWLLiteralImplNoCompression   ("-0.0", "", XSDFLOAT);
  //@formatter:on

    private InternalizedEntities() {}
}
