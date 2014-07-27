/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coode.owlapi.obo12.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br>
 * <br>
 * <p/>
 * Concrete implementations of this interface allow specific behaviour for
 * processing specific tag value pairs in an OBO file to be specified.
 * <p>
 * <h3>Tag-Value Pairs (From the OBO 1.4 Guide)</h3>
 * <p/>
 * Tag-value pairs consist of a tag name, an unescaped colon, the tag value, and
 * a newline:
 * <p/>
 * &lt;tag&gt;: &lt;value&gt; {&lt;trailing modifiers&gt;} ! &lt;comment&gt; The
 * tag name is always a string. The value is always a string, but the value
 * string may require special parsing depending on the tag with which it is
 * associated.
 * <p/>
 * In general, tag-value pairs occur on a single line. Multi-line values are
 * possible using escape characters (see escape characters).
 * <p/>
 * In general, each stanza type expects a particular set of pre-defined tags.
 * However, a stanza may contain any tag. If a parser does not recognize a tag
 * name for a particular stanza, no error will be generated. This allows new
 * experimental tags to be added without breaking existing parsers. See handling
 * unrecognized tags for specifics.
 * <p/>
 * <h3>Trailing Modifiers</h3>
 * <p/>
 * Any tag-value pair may be followed by a trailing modifier. Trailing modifiers
 * have been introduced into the OBO 1.2 Specification to allow the graceful
 * addition of new features to existing tags.
 * <p/>
 * A trailing modifier has the following structure:
 * <p/>
 * {&lt;name&gt;=&lt;value&gt;, &lt;name=value&gt;, &lt;name=value&gt;} That is,
 * trailing modifiers are lists of name-value pairs.
 * <p/>
 * Parser implementations may choose to decode and/or round-trip these trailing
 * modifiers. However, this is not required. A parser may choose to ignore or
 * strip away trailing modifiers.
 * <p/>
 * For this reason, trailing modifiers should only include information that is
 * optional or experimental.
 * <p/>
 * Trailing modifiers may also occur within dbxref definitions (see dbxref
 * formatting).
 * <p/>
 * </p>
 */
interface TagValueHandler {

    /**
     * Gets the name of the tag handled by this tag value handler
     * 
     * @return The name of the tag
     */
    @Nonnull
    String getTagName();

    /**
     * Handles a tag. This is called by the OBOConsumer during parsing to handle
     * tags that match the value returned by the {@link #getTagName()} method.
     * 
     * @param currentId
     *        The id of the current frame.
     * @param value
     *        The value of the tag
     * @param qualifierBlock
     *        qualifier block
     * @param comment
     *        The hidden comment. This is made up of any characters between !
     *        and the end of line.
     */
    void handle(String currentId, @Nonnull String value, String qualifierBlock,
            String comment);
}

abstract class AbstractTagValueHandler implements TagValueHandler {

    @Nonnull
    private String tag;
    @Nonnull
    private OBOConsumer consumer;

    public AbstractTagValueHandler(@Nonnull String tag,
            @Nonnull OBOConsumer consumer) {
        this.tag = tag;
        this.consumer = consumer;
    }

    @Override
    public String getTagName() {
        return tag;
    }

    public OWLOntologyManager getOWLOntologyManager() {
        return consumer.getOWLOntologyManager();
    }

    @Nonnull
    public OWLOntology getOntology() {
        return consumer.getOntology();
    }

    public void applyChange(@Nonnull OWLOntologyChange change) {
        consumer.getOWLOntologyManager().applyChange(change);
    }

    @Nonnull
    public OBOConsumer getConsumer() {
        return consumer;
    }

    public OWLDataFactory getDataFactory() {
        return consumer.getOWLOntologyManager().getOWLDataFactory();
    }

    @Nonnull
    public IRI getTagIRI(OBOVocabulary vocabulary) {
        return consumer.getIRIFromTagName(vocabulary.getName());
    }

    /**
     * Gets an IRI for a tag name. This is a helper method, which ultimately
     * calls {@link OBOConsumer#getIRIFromTagName(String)}.
     * 
     * @param tagName
     *        The tag name.
     * @return The IRI corresponding to the tag name.
     */
    @Nonnull
    public IRI getTagIRI(String tagName) {
        return consumer.getIRIFromTagName(tagName);
    }

    @Nonnull
    public IRI getIRIFromOBOId(String id) {
        return consumer.getIRIFromOBOId(id);
    }

    // public IRI getIRIFromSymbolicId(String symbolicId) {
    // return consumer.getIRIFromSymbolicId(symbolicId);
    // }
    /**
     * Gets an {@link OWLAnnotation} for a tag value pair.
     * 
     * @param tagName
     *        The tag name.
     * @param value
     *        The tag value. Note that the tag value is un-escaped and stripped
     *        of double quotes if they exist.
     * @return An {@link OWLAnnotation} that is formed by converting the tagName
     *         to an IRI and then to an {@link OWLAnnotationProperty} and the
     *         value to an {@link OWLLiteral}.
     */
    @Nonnull
    public OWLAnnotation getAnnotationForTagValuePair(String tagName,
            String value) {
        IRI tagIRI = getTagIRI(tagName);
        OWLDataFactory df = getDataFactory();
        OWLAnnotationProperty annotationProperty = df
                .getOWLAnnotationProperty(tagIRI);
        String unescapedString = getUnquotedString(value);
        OWLLiteral annotationValue = df.getOWLLiteral(unescapedString);
        return df.getOWLAnnotation(annotationProperty, annotationValue);
    }

    @Nonnull
    public OWLClass getClassFromId(String s) {
        return getDataFactory().getOWLClass(getIRIFromOBOId(s));
    }

    @Nonnull
    public OWLClass getCurrentClass() {
        return getDataFactory().getOWLClass(
                getIRIFromOBOId(consumer.getCurrentId()));
    }

    @Nonnull
    protected OWLClass getOWLClass(String id) {
        return getDataFactory().getOWLClass(getIRIFromOBOId(id));
    }

    @Nonnull
    protected OWLObjectProperty getOWLObjectProperty(String id) {
        return getDataFactory().getOWLObjectProperty(getIRIFromOBOId(id));
    }

    @SuppressWarnings("null")
    @Nonnull
    protected String getUnquotedString(String value) {
        String unquotedString;
        if (value.startsWith("\"") && value.endsWith("\"")) {
            unquotedString = value.substring(1, value.length() - 1);
        } else {
            unquotedString = value;
        }
        return unquotedString;
    }

    @Nonnull
    protected OWLClassExpression getOWLClassOrRestriction(String termList) {
        StringTokenizer tok = new StringTokenizer(termList, " ", false);
        String id0 = null;
        String id1 = null;
        id0 = tok.nextToken();
        if (tok.hasMoreTokens()) {
            id1 = tok.nextToken();
        }
        if (id1 == null) {
            return getDataFactory().getOWLClass(getIRIFromOBOId(id0));
        } else {
            IRI propertyIRI = getConsumer()
                    .getRelationIRIFromSymbolicIdOrOBOId(id0);
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(
                    propertyIRI);
            OWLClass filler = getDataFactory()
                    .getOWLClass(getIRIFromOBOId(id1));
            return getDataFactory().getOWLObjectSomeValuesFrom(prop, filler);
        }
    }

    @Nonnull
    protected OWLLiteral getBooleanConstant(boolean b) {
        return getDataFactory().getOWLLiteral(b);
    }

    protected void addAnnotation(String id, String uriID,
            @Nonnull OWLLiteral value) {
        IRI subject = getIRIFromOBOId(id);
        OWLAnnotationProperty annotationProperty = getDataFactory()
                .getOWLAnnotationProperty(getIRIFromOBOId(uriID));
        OWLAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(
                annotationProperty, subject, value);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}

class AltIdTagValueHandler extends AbstractTagValueHandler {

    public AltIdTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.ALT_ID.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        IRI subject = getConsumer().getCurrentEntity().getIRI();
        IRI annotationPropertyIRI = OBOVocabulary.ALT_ID.getIRI();
        OWLAnnotationProperty property = getDataFactory()
                .getOWLAnnotationProperty(annotationPropertyIRI);
        IRI object = getIRIFromOBOId(value);
        OWLAnnotationAssertionAxiom ax = getDataFactory()
                .getOWLAnnotationAssertionAxiom(property, subject, object);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}

class AsymmetricHandler extends AbstractTagValueHandler {

    public AsymmetricHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.IS_ASYMMETRIC.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        if (Boolean.parseBoolean(value)) {
            OWLObjectProperty prop = getOWLObjectProperty(currentId);
            OWLAxiom ax = getDataFactory().getOWLAsymmetricObjectPropertyAxiom(
                    prop);
            applyChange(new AddAxiom(getOntology(), ax));
        } else {
            addAnnotation(currentId, OBOVocabulary.IS_ASYMMETRIC.getName(),
                    getBooleanConstant(false));
        }
    }
}

class DataVersionTagValueHandler extends AbstractTagValueHandler {

    public DataVersionTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.DATA_VERSION.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        getConsumer().setDataVersionTagValue(value);
    }
}

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Sep-2008<br>
 * <br>
 * <p/>
 * OBO Namespaces are NOT like XML Namespaces. They are NOT used to form
 * abbreviations for IRIs. The description below, taken from the OBOEdit manual,
 * explains their provenance.
 * <p/>
 * <h3>OBO Namespaces and Ontology Name (OBO Syntax and Semantics Document:
 * Section 4.3)</h3>
 * <p/>
 * Note that OBO namespaces are not the same as OWL namespaces - the analog of
 * OWL namespaces are OBO ID spaces. OBO namespaces are semantics-free
 * properties of a frame that allow partitioning of an ontology into
 * sub-ontologies. For example, the GO is partitioned into 3 ontologies (3 OBO
 * namespaces, 1 OWL namespace).
 * <p/>
 * Every frame must have exactly one namespace. However, these do not need to be
 * explicitly assigned. After parsing an OBO Document, any frame without a
 * namespace is assigned the default-namespace, from the OBO Document header. If
 * this is not specified, the Parser assigns a namespace arbitrarily. It is
 * recommended this is equivalent to the URL or file path from which the
 * document was retrieved.
 * <p/>
 * Every OBODoc should have an "ontology" tag specified in the header. If this
 * is not specified, then the parser should supply a default value. This value
 * should be derived from the URL of the source of the ontology (typically using
 * http or file schemes).
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <h3>OBO Namespaces (From the OBOEdit Manual)</h3>
 * <p/>
 * Namespaces
 * <p/>
 * OBO files are designed to be easily merged and separated. Most tools that use
 * OBO files can load many OBO files at once. If several ontologies have been
 * loaded together and saved into a single file, it would be impossible to know
 * which terms came from which file unless the origin of each term is indicated
 * somehow. Namespaces are used to solve this problem by indicating a
 * "logical ontology" to which every term, relation, instance OR relationship
 * belongs, i.e., each entity is tagged with a Namespace that indicates which
 * ontology it is part of.
 * <p/>
 * Namespaces are user-definable. Every ontology object belongs to a single
 * namespace. When terms from many ontologies have been loaded together,
 * namespaces are used to break the merged ontology back into separate files.
 */
class DefaultNamespaceTagValueHandler extends AbstractTagValueHandler {

    public DefaultNamespaceTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.DEFAULT_NAMESPACE.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        // Just register the namespace with the consumer and add it as an
        // annotation to the ontology
        getConsumer().setDefaultNamespaceTagValue(value);
        // Add an annotation to the ontology
        OWLAnnotation annotation = getAnnotationForTagValuePair(
                OBOVocabulary.DEFAULT_NAMESPACE.getName(), value);
        applyChange(new AddOntologyAnnotation(getOntology(), annotation));
    }
}

class DefTagValueHandler extends AbstractTagValueHandler {

    private static final Pattern PATTERN = Pattern
            .compile("\"([^\"]*)\"\\s*(\\[([^\\]]*)\\])?\\s*");
    private static final int QUOTED_STRING_CONTENT_GROUP = 1;
    private static final int XREF_GROUP = 3;

    public DefTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.DEF.getName(), consumer);
    }

    @SuppressWarnings("null")
    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        Matcher matcher = PATTERN.matcher(value);
        OWLDataFactory df = getDataFactory();
        String annotationValue;
        Set<OWLAnnotation> xrefAnnotations = Collections.emptySet();
        if (matcher.matches()) {
            annotationValue = matcher.group(QUOTED_STRING_CONTENT_GROUP);
            xrefAnnotations = getXRefAnnotations(matcher);
        } else {
            annotationValue = getUnquotedString(value);
        }
        IRI propertyIRI = getTagIRI(getTagName());
        OWLAnnotationProperty property = df
                .getOWLAnnotationProperty(propertyIRI);
        OWLEntity currentEntity = getConsumer().getCurrentEntity();
        OWLLiteral literal = df.getOWLLiteral(annotationValue);
        OWLAnnotationAssertionAxiom ax = df.getOWLAnnotationAssertionAxiom(
                property, currentEntity.getIRI(), literal, xrefAnnotations);
        applyChange(new AddAxiom(getOntology(), ax));
    }

    @Nonnull
    private Set<OWLAnnotation> getXRefAnnotations(Matcher matcher) {
        Set<OWLAnnotation> annotations = new HashSet<>();
        String xrefs = matcher.group(XREF_GROUP);
        if (xrefs != null) {
            StringTokenizer tokenizer = new StringTokenizer(xrefs, ",");
            while (tokenizer.hasMoreTokens()) {
                String xrefValue = tokenizer.nextToken();
                assert xrefValue != null;
                OWLAnnotation xrefAnnotation = getConsumer().parseXRef(
                        xrefValue);
                annotations.add(xrefAnnotation);
            }
        }
        return annotations;
    }
}

class DisjointFromHandler extends AbstractTagValueHandler {

    public DisjointFromHandler(@Nonnull OBOConsumer consumer) {
        super("disjoint_from", consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        OWLAxiom ax = getDataFactory().getOWLDisjointClassesAxiom(
                CollectionFactory.createSet(getCurrentClass(),
                        getOWLClass(value)));
        applyChange(new AddAxiom(getOntology(), ax));
    }
}

class DomainHandler extends AbstractTagValueHandler {

    public DomainHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.DOMAIN.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        OWLObjectProperty prop = getOWLObjectProperty(getConsumer()
                .getCurrentId());
        OWLClass cls = getOWLClass(value);
        applyChange(new AddAxiom(getOntology(), getDataFactory()
                .getOWLObjectPropertyDomainAxiom(prop, cls)));
    }
}

class IDSpaceTagValueHandler extends AbstractTagValueHandler {

    private static final Pattern PATTERN = Pattern
            .compile("([^\\s]*)\\s+([^\\s]*)");
    private static final int ID_PREFIX_GROUP = 1;
    private static final int IRI_PREFIX_GROUP = 2;

    public IDSpaceTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.ID_SPACE.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        Matcher matcher = PATTERN.matcher(value);
        if (matcher.matches()) {
            String idPrefix = matcher.group(ID_PREFIX_GROUP);
            String iriPrefix = matcher.group(IRI_PREFIX_GROUP);
            getConsumer().registerIdSpace(idPrefix, iriPrefix);
        }
    }
}

class IDTagValueHandler extends AbstractTagValueHandler {

    public IDTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.ID.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        getConsumer().setCurrentId(value);
        final OWLEntity entity = getConsumer().getCurrentEntity();
        if (entity != null) {
            applyChange(new AddAxiom(getOntology(), getDataFactory()
                    .getOWLDeclarationAxiom(entity)));
        }
    }
}

class IntersectionOfHandler extends AbstractTagValueHandler {

    public IntersectionOfHandler(@Nonnull OBOConsumer consumer) {
        super("intersection_of", consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        getConsumer().addIntersectionOfOperand(getOWLClassOrRestriction(value));
    }
}

class InverseHandler extends AbstractTagValueHandler {

    public InverseHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.INVERSE.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        OWLAxiom ax = getDataFactory().getOWLInverseObjectPropertiesAxiom(
                getOWLObjectProperty(currentId), getOWLObjectProperty(value));
        applyChange(new AddAxiom(getOntology(), ax));
    }
}

class IsATagValueHandler extends AbstractTagValueHandler {

    public IsATagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.IS_A.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        if (getConsumer().isTerm()) {
            // We simply add a subclass axiom
            applyChange(new AddAxiom(getOntology(), getDataFactory()
                    .getOWLSubClassOfAxiom(getClassFromId(currentId),
                            getClassFromId(value))));
        } else if (getConsumer().isTypedef()) {
            // We simply add a sub property axiom
            applyChange(new AddAxiom(getOntology(), getDataFactory()
                    .getOWLSubObjectPropertyOfAxiom(
                            getOWLObjectProperty(currentId),
                            getOWLObjectProperty(value))));
        }
    }
}

class IsObsoleteTagValueHandler extends AbstractTagValueHandler {

    public IsObsoleteTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.IS_OBSOLETE.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        OWLDataFactory df = getDataFactory();
        OWLAnnotationProperty deprecatedProperty = df.getOWLDeprecated();
        OWLLiteral annotationValue = df.getOWLLiteral(true);
        IRI subject = getIRIFromOBOId(currentId);
        OWLAnnotationAssertionAxiom ax = df.getOWLAnnotationAssertionAxiom(
                deprecatedProperty, subject, annotationValue);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}

class NameTagValueHandler extends AbstractTagValueHandler {

    public NameTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.NAME.getName(), consumer);
    }

    @Override
    public void handle(String currentId, @Nonnull String value,
            String qualifierBlock, String comment) {
        // This is an annotation - but add as a label
        OWLEntity ent;
        if (getConsumer().isTerm()) {
            ent = getDataFactory().getOWLClass(getIRIFromOBOId(currentId));
        } else if (getConsumer().isTypedef()) {
            ent = getDataFactory().getOWLObjectProperty(
                    getIRIFromOBOId(currentId));
        } else {
            ent = getDataFactory().getOWLNamedIndividual(
                    getIRIFromOBOId(currentId));
        }
        OWLLiteral con = getDataFactory().getOWLLiteral(value);
        OWLAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(
                getDataFactory().getOWLAnnotationProperty(
                        OWLRDFVocabulary.RDFS_LABEL.getIRI()), ent.getIRI(),
                con);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}

class OntologyTagValueHandler extends AbstractTagValueHandler {

    public OntologyTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.ONTOLOGY.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        getConsumer().setOntologyTagValue(value);
    }
}

class PartOfTagValueHandler extends AbstractTagValueHandler {

    public PartOfTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.RELATIONSHIP.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        int index = value.indexOf(' ');
        String propLocalName = value.substring(0, index);
        String val = value.substring(index + 1, value.length());
        OWLDataFactory df = getDataFactory();
        OWLObjectProperty prop = df
                .getOWLObjectProperty(getIRIFromOBOId(propLocalName));
        OWLClass filler = getClassFromId(val);
        OWLClassExpression desc = df.getOWLObjectSomeValuesFrom(prop, filler);
        OWLAxiom ax = df.getOWLSubClassOfAxiom(getCurrentClass(), desc);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}

class RawFrameHandler implements OBOParserHandler {

    private String currentFrameType;
    private List<OBOTagValuePair> currentTagValuePairs = new ArrayList<>();
    private OBOFrame headerFrame;
    private List<OBOFrame> typeDefFrames = new ArrayList<>();
    private List<OBOFrame> nonTypeDefFrames = new ArrayList<>();

    @Override
    public void startHeader() {
        currentTagValuePairs.clear();
    }

    @Override
    public void endHeader() {
        headerFrame = new OBOFrame(currentTagValuePairs);
    }

    @Override
    public void startFrame(String frameType) {
        currentFrameType = frameType;
        currentTagValuePairs.clear();
    }

    @Override
    public void endFrame() {
        storeCurrentFrame();
    }

    private OBOFrame storeCurrentFrame() {
        OBOFrame frame = new OBOFrame(currentFrameType, currentTagValuePairs);
        if (frame.isTypeDefFrame()) {
            typeDefFrames.add(frame);
        } else {
            nonTypeDefFrames.add(frame);
        }
        return frame;
    }

    @Override
    public void handleTagValue(String tag, String value, String qualifierBlock,
            String comment) {
        OBOTagValuePair tvp = new OBOTagValuePair(tag, value, qualifierBlock,
                comment);
        currentTagValuePairs.add(tvp);
    }

    /** @return the header frame */
    public OBOFrame getHeaderFrame() {
        return headerFrame;
    }

    /** @return the typedef frames */
    public List<OBOFrame> getTypeDefFrames() {
        return typeDefFrames;
    }

    /** @return the non typedef frames */
    public List<OBOFrame> getNonTypeDefFrames() {
        return nonTypeDefFrames;
    }
}

class ReflexiveHandler extends AbstractTagValueHandler {

    public ReflexiveHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.IS_REFLEXIVE.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        if (Boolean.parseBoolean(value)) {
            OWLObjectProperty prop = getOWLObjectProperty(currentId);
            OWLAxiom ax = getDataFactory().getOWLReflexiveObjectPropertyAxiom(
                    prop);
            applyChange(new AddAxiom(getOntology(), ax));
        } else {
            addAnnotation(currentId, OBOVocabulary.IS_REFLEXIVE.getName(),
                    getBooleanConstant(false));
        }
    }
}

class RelationshipTagValueHandler extends AbstractTagValueHandler {

    private Pattern tagValuePattern = Pattern
            .compile("([^\\s]*)\\s*([^\\s]*)\\s*(\\{([^\\}]*)\\})?");

    public RelationshipTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.RELATIONSHIP.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        Matcher matcher = tagValuePattern.matcher(value);
        if (matcher.matches()) {
            IRI propIRI = getConsumer().getRelationIRIFromSymbolicIdOrOBOId(
                    matcher.group(1));
            IRI fillerIRI = getIRIFromOBOId(matcher.group(2));
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(
                    propIRI);
            OWLClass filler = getDataFactory().getOWLClass(fillerIRI);
            OWLClassExpression restriction = getDataFactory()
                    .getOWLObjectSomeValuesFrom(prop, filler);
            OWLClass subCls = getDataFactory().getOWLClass(
                    getIRIFromOBOId(currentId));
            applyChange(new AddAxiom(getOntology(), getDataFactory()
                    .getOWLSubClassOfAxiom(subCls, restriction)));
            applyChange(new AddAxiom(getOntology(), getDataFactory()
                    .getOWLDeclarationAxiom(prop)));
        }
    }
}

class SymmetricTagValueHandler extends AbstractTagValueHandler {

    public SymmetricTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.IS_SYMMETRIC.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        if (Boolean.parseBoolean(value)) {
            OWLObjectProperty prop = getOWLObjectProperty(currentId);
            OWLAxiom ax = getDataFactory().getOWLSymmetricObjectPropertyAxiom(
                    prop);
            applyChange(new AddAxiom(getOntology(), ax));
        } else {
            addAnnotation(currentId, OBOVocabulary.IS_SYMMETRIC.getName(),
                    getBooleanConstant(false));
        }
    }
}

class SynonymTagValueHandler extends AbstractTagValueHandler {

    @Nonnull
    private static final String TAG_NAME = OBOVocabulary.SYNONYM.toString();
    // synonym: "synonym" (EXACT|BROAD|NARROW|RELATED) TYPE? XRefList
    private static final Pattern valuePattern = Pattern
            .compile("\"([^\"]*)\"\\s*([^\\s]*)\\s*([^\\[\\s]+)?\\s*\\[([^\\]]*)\\]");
    private static final int VALUE_GROUP = 1;
    private static final int SCOPE_GROUP = 2;
    private static final int SYNONYM_TYPE_GROUP = 3;
    private static final int XREF_GROUP = 4;
    @Nonnull
    public static final IRI SYNONYM_TYPE_IRI = OBOVocabulary.SYNONYM_TYPE
            .getIRI();
    public static final IRI XREF_IRI = OBOVocabulary.XREF.getIRI();

    public SynonymTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(TAG_NAME, consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        Matcher matcher = valuePattern.matcher(value);
        if (matcher.matches()) {
            OWLDataFactory df = getDataFactory();
            OWLAnnotationProperty property = getSynonymAnnotationProperty(matcher);
            Set<OWLAnnotation> annotations = new HashSet<>();
            annotations.addAll(getSynonymTypeAnnotations(matcher));
            annotations.addAll(getXRefAnnotations(matcher));
            OWLEntity subject = getConsumer().getCurrentEntity();
            String synonym = matcher.group(VALUE_GROUP);
            assert synonym != null;
            OWLLiteral synonymLiteral = df.getOWLLiteral(synonym);
            OWLAnnotationAssertionAxiom annoAssertion = df
                    .getOWLAnnotationAssertionAxiom(property, subject.getIRI(),
                            synonymLiteral, annotations);
            applyChange(new AddAxiom(getOntology(), annoAssertion));
        }
    }

    private Set<OWLAnnotation> getSynonymTypeAnnotations(Matcher matcher) {
        if (matcher.group(SYNONYM_TYPE_GROUP) != null) {
            OWLAnnotation typeAnnotation = getSynonymTypeAnnotation(matcher);
            return Collections.singleton(typeAnnotation);
        } else {
            return Collections.emptySet();
        }
    }

    private Set<OWLAnnotation> getXRefAnnotations(Matcher matcher) {
        Set<OWLAnnotation> annotations = new HashSet<>();
        String xrefs = matcher.group(XREF_GROUP);
        if (xrefs != null) {
            StringTokenizer tokenizer = new StringTokenizer(xrefs, ",");
            while (tokenizer.hasMoreTokens()) {
                String xref = tokenizer.nextToken();
                assert xref != null;
                OWLAnnotation xrefAnnotation = getConsumer().parseXRef(xref);
                annotations.add(xrefAnnotation);
            }
        }
        return annotations;
    }

    @Nonnull
    private OWLAnnotationProperty getSynonymAnnotationProperty(Matcher matcher) {
        String synonymScope = matcher.group(SCOPE_GROUP);
        IRI annotationPropertyIRI;
        if (SynonymScope.BROAD.name().equals(synonymScope)) {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.BROAD_SYNONYM);
        } else if (SynonymScope.EXACT.name().equals(synonymScope)) {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.EXACT_SYNONYM);
        } else if (SynonymScope.NARROW.name().equals(synonymScope)) {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.NARROW_SYNONYM);
        } else if (SynonymScope.RELATED.name().equals(synonymScope)) {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.RELATED_SYNONYM);
        } else {
            annotationPropertyIRI = getTagIRI(OBOVocabulary.SYNONYM);
        }
        return getDataFactory().getOWLAnnotationProperty(annotationPropertyIRI);
    }

    private OWLAnnotation getSynonymTypeAnnotation(Matcher matcher) {
        OWLDataFactory df = getDataFactory();
        String synonymType = matcher.group(SYNONYM_TYPE_GROUP);
        assert synonymType != null;
        return df.getOWLAnnotation(
                df.getOWLAnnotationProperty(SYNONYM_TYPE_IRI),
                df.getOWLLiteral(synonymType));
    }
}

class SynonymTypeDefTagHandler extends AbstractTagValueHandler {

    private static final Pattern PATTERN = Pattern
            .compile("([^\\s]*)\\s+\"([^\"]*)\"(\\s*([^\\s]*)\\s*)?");
    private static final int ID_GROUP = 1;
    private static final int NAME_GROUP = 2;

    public SynonymTypeDefTagHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.SYNONYM_TYPE_DEF.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        Matcher matcher = PATTERN.matcher(value);
        if (matcher.matches()) {
            String id = matcher.group(ID_GROUP);
            IRI annotationPropertyIRI = getIRIFromOBOId(id);
            String name = matcher.group(NAME_GROUP);
            assert name != null;
            OWLDataFactory df = getDataFactory();
            OWLAnnotationProperty annotationProperty = df
                    .getOWLAnnotationProperty(annotationPropertyIRI);
            applyChange(new AddAxiom(getOntology(),
                    df.getOWLDeclarationAxiom(annotationProperty)));
            IRI subsetdefIRI = getTagIRI(OBOVocabulary.SUBSETDEF.getName());
            OWLAnnotationProperty subsetdefAnnotationProperty = df
                    .getOWLAnnotationProperty(subsetdefIRI);
            applyChange(new AddAxiom(getOntology(),
                    df.getOWLSubAnnotationPropertyOfAxiom(annotationProperty,
                            subsetdefAnnotationProperty)));
            OWLLiteral nameLiteral = df.getOWLLiteral(name);
            applyChange(new AddAxiom(getOntology(),
                    df.getOWLAnnotationAssertionAxiom(df.getRDFSLabel(),
                            annotationPropertyIRI, nameLiteral)));
        } else {
            OWLAnnotation annotation = getAnnotationForTagValuePair(
                    OBOVocabulary.SYNONYM_TYPE_DEF.getName(), value);
            applyChange(new AddOntologyAnnotation(getOntology(), annotation));
        }
        // ID QuotedString [Scope]
        // 18th April 2012
        // AnnotationProperty(T(ID))
        // SubAnnotationPropertyOf(T(ID) T(subsetdef))
        // AnnotationAssertion(T(name) T(ID) ID)
        // AnnotationAssertion(T(hasScope) T(ID) Scope)
    }
}

class TransitiveOverHandler extends AbstractTagValueHandler {

    public TransitiveOverHandler(@Nonnull OBOConsumer consumer) {
        super("is_transitive_over", consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        OWLObjectProperty first = getOWLObjectProperty(currentId);
        OWLObjectProperty second = getOWLObjectProperty(value);
        List<OWLObjectProperty> chain = new ArrayList<>();
        chain.add(first);
        chain.add(second);
        OWLAxiom ax = getDataFactory().getOWLSubPropertyChainOfAxiom(chain,
                first);
        applyChange(new AddAxiom(getOntology(), ax));
    }
}

class TransitiveTagValueHandler extends AbstractTagValueHandler {

    public TransitiveTagValueHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.IS_TRANSITIVE.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        if (Boolean.parseBoolean(value)) {
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(
                    getIRIFromOBOId(currentId));
            OWLAxiom ax = getDataFactory().getOWLTransitiveObjectPropertyAxiom(
                    prop);
            applyChange(new AddAxiom(getOntology(), ax));
        }
    }
}

class UnionOfHandler extends AbstractTagValueHandler {

    public UnionOfHandler(@Nonnull OBOConsumer consumer) {
        super("union_of", consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        getConsumer().addUnionOfOperand(getOWLClassOrRestriction(value));
    }
}

class XRefTagHandler extends AbstractTagValueHandler {

    public XRefTagHandler(@Nonnull OBOConsumer consumer) {
        super(OBOVocabulary.XREF.getName(), consumer);
    }

    @Override
    public void handle(String currentId, String value, String qualifierBlock,
            String comment) {
        if (currentId == null) {
            return;
        }
        OWLAnnotation xrefAnnotation = getConsumer().parseXRef(value);
        IRI subject = getIRIFromOBOId(currentId);
        OWLAnnotationAssertionAxiom ax = getDataFactory()
                .getOWLAnnotationAssertionAxiom(xrefAnnotation.getProperty(),
                        subject, xrefAnnotation.getValue());
        applyChange(new AddAxiom(getOntology(), ax));
        if (getConsumer().isTypedef()
                && xrefAnnotation.getValue() instanceof IRI) {
            IRI xrefIRI = (IRI) xrefAnnotation.getValue();
            String typedefId = getConsumer().getCurrentId();
            getConsumer().addSymbolicIdMapping(typedefId, xrefIRI);
        }
    }
}
