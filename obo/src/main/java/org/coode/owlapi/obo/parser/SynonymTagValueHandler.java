package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 03/02/2011
 */
public class SynonymTagValueHandler extends AbstractTagValueHandler {

    private static final String TAG_NAME = OBOVocabulary.SYNONYM.toString();

    private static final Pattern valuePattern = Pattern.compile("\"([^\"]*)\"\\s*([^\\s]*)\\s*\\[([^\\]]*)\\]");

    private static final int VALUE_GROUP = 1;

    private static final int TYPE_GROUP = 2;

    private static final int XREF_GROUP = 3;

    public static final IRI SYNONYM_TYPE_IRI = OBOVocabulary.SYNONYM_TYPE.getIRI();

    public static final IRI XREF_IRI = OBOVocabulary.XREF.getIRI();

    public SynonymTagValueHandler(OBOConsumer consumer) {
        super(TAG_NAME, consumer);
    }

    public void handle(String id, String value, String comment) {
        Matcher matcher = valuePattern.matcher(value);
        if(matcher.matches()) {
            IRI synonymIRI = getTagIRI(TAG_NAME);
            OWLDataFactory df = getDataFactory();
            OWLAnnotationProperty property = df.getOWLAnnotationProperty(synonymIRI);
            Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
            OWLAnnotation typeAnnotation = getSynonymTypeAnnotation(matcher);
            annotations.add(typeAnnotation);
            annotations.addAll(getXRefAnnotations(matcher, df));

            OWLEntity subject = getConsumer().getCurrentEntity();
            String synonym = matcher.group(VALUE_GROUP);
            OWLLiteral synonymLiteral = df.getOWLLiteral(synonym, "");
            OWLAnnotationAssertionAxiom annoAssertion = df.getOWLAnnotationAssertionAxiom(property, subject.getIRI(), synonymLiteral, annotations);
            applyChange(new AddAxiom(getOntology(), annoAssertion));
        }
    }

    private Set<OWLAnnotation> getXRefAnnotations(Matcher matcher, OWLDataFactory df) {
        Set<OWLAnnotation> annotations = new HashSet<OWLAnnotation>();
        String xrefs = matcher.group(XREF_GROUP);
        if (xrefs != null) {
            StringTokenizer tokenizer = new StringTokenizer(xrefs, ",");
            while(tokenizer.hasMoreTokens()) {
                String xref = tokenizer.nextToken();
                IRI xrefIRI = getTagIRI(xref);
                OWLAnnotationProperty xrefProperty = df.getOWLAnnotationProperty(XREF_IRI);
                OWLAnnotation xrefAnnotation = df.getOWLAnnotation(xrefProperty, xrefIRI);
                annotations.add(xrefAnnotation);
            }
        }
        return annotations;
    }

    private OWLAnnotation getSynonymTypeAnnotation(Matcher matcher) {
        OWLDataFactory df = getDataFactory();
        String synonymType = matcher.group(TYPE_GROUP);
        return df.getOWLAnnotation(df.getOWLAnnotationProperty(SYNONYM_TYPE_IRI), df.getOWLLiteral(synonymType));
    }
}
