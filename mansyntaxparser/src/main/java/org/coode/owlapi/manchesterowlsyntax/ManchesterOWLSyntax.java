package org.coode.owlapi.manchesterowlsyntax;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Apr-2007<br><br>
 * <p/>
 * The vocabulary that the Manchester OWL Syntax uses
 */
public enum ManchesterOWLSyntax {

    ONTOLOGY("Ontology", false, false, false, false, false),

    IMPORT("Import", false, true, true, false, false),

    PREFIX("Prefix", false, false, false, false, false),

    CLASS("Class", true, true, false, false, false),

    OBJECT_PROPERTY("ObjectProperty", true, true, false, false, false),

    DATA_PROPERTY("DataProperty", true, true, false, false, false),

    INDIVIDUAL("Individual", true, true, false, false, false),

    DATATYPE("Datatype", true, true, false, false, false),

    ANNOTATION_PROPERTY("AnnotationProperty", true, true, false, false, false),

    SOME("some", false, false, false, true, false),

    ONLY("only", false, false, false, true, false),

    ONLYSOME("onlysome", false, false, false, true, false),

    MIN("min", false, false, false, true, false),

    MAX("max", false, false, false, true, false),

    EXACTLY("exactly", false, false, false, true, false),

    VALUE("value", false, false, false, true, false),

    AND("and", false, false, false, false, true),

    THAT("that", false, false, false, false, true),

    OR("or", false, false, false, false, true),

    NOT("not", false, false, false, false, true),

    INVERSE("inverse", false, false, true, false, false),

    SELF("Self", false, false, false, true, false),

    FACET_RESTRICTION_SEPARATOR(",", false, false, false, false, false),

    SUBCLASS_OF("SubClassOf", false, true, true, false, false),

    SUPERCLASS_OF("SuperClassOf", false, true, true, false, false),

    EQUIVALENT_TO("EquivalentTo", false, true, true, false, false),

    EQUIVALENT_CLASSES("EquivalentClasses", false, true, true, false, false),

    EQUIVALENT_PROPERTIES("EquivalentProperties", false, true, true, false, false),

    DISJOINT_WITH("DisjointWith", false, true, true, false, false),

    INDIVIDUALS("Individuals", false, true, true, false, false),

    DISJOINT_CLASSES("DisjointClasses", true, true, true, false, false),

    DISJOINT_PROPERTIES("DisjointProperties", true, false, true, false, false),

    DISJOINT_UNION_OF("DisjointUnionOf", false, true, true, false, false),

    FACTS("Facts", false, true, false, false, false),

    SAME_AS("SameAs", false, true, true, false, false),

    SAME_INDIVIDUAL("SameIndividual", false, true, true, false, false),

    DIFFERENT_FROM("DifferentFrom", false, true, true, false, false),

    DIFFERENT_INDIVIDUALS("DifferentIndividuals", true, true, true, false, false),

    MIN_INCLUSIVE_FACET(">=", false, false, false, false, false),

    MAX_INCLUSIVE_FACET("<=", false, false, false, false, false),

    MIN_EXCLUSIVE_FACET(">", false, false, false, false, false),

    MAX_EXCLUSIVE_FACET("<", false, false, false, false, false),

    ONE_OF_DELIMETER(",", false, false, false, false, false),

    TYPES("Types", false, true, true, false, false),

    TYPE("Type", false, true, true, false, false),

    ANNOTATIONS("Annotations", false, true, false, false, false),

    COMMA(",", false, false, false, false, false),

    DOMAIN("Domain", false, true, true, false, false),

    RANGE("Range", false, true, true, false, false),

    CHARACTERISTICS("Characteristics", false, true, false, false, false),

    FUNCTIONAL("Functional", false, false, true, false, false),

    INVERSE_FUNCTIONAL("InverseFunctional", false, false, true, false, false),

    SYMMETRIC("Symmetric", false, false, true, false, false),

    TRANSITIVE("Transitive", false, false, true, false, false),

    REFLEXIVE("Reflexive", false, false, true, false, false),

    IRREFLEXIVE("Irreflexive", false, false, true, false, false),

    /**
     * For legacy reasons
     */
    ANTI_SYMMETRIC("AntiSymmetric", false, false, true, false, false),

    ASYMMETRIC("Asymmetric", false, false, true, false, false),

    INVERSE_OF("InverseOf", false, true, true, false, false),

    INVERSES("Inverses", false, true, false, false, false),

    SUB_PROPERTY_OF("SubPropertyOf", false, true, true, false, false),

    SUPER_PROPERTY_OF("SuperPropertyOf", false, true, true, false, false),

    SUB_PROPERTY_CHAIN("SubPropertyChain", false, true, true, false, false),

    HAS_KEY("HasKey", false, true, false, false, false),

    RULE("Rule", true, true, false, false, false);

    private boolean frameKeyword;

    private boolean sectionKeyword;

    private boolean axiomKeyword;

    private boolean classExpressionQuantiferKeyword;

    private boolean classExpressionConnectiveKeyword;

    private String rendering;

    private ManchesterOWLSyntax(String rendering, boolean frameKeyword, boolean sectionKeyword, boolean axiomKeyword, boolean classExpressionQuantifierKeyword, boolean classExpressionConnectiveKeyword) {
        this.rendering = rendering;
        this.frameKeyword = frameKeyword;
        this.sectionKeyword = sectionKeyword;
        this.axiomKeyword = axiomKeyword;
        this.classExpressionConnectiveKeyword = classExpressionConnectiveKeyword;
        this.classExpressionQuantiferKeyword = classExpressionQuantifierKeyword;
    }

    public boolean isFrameKeyword() {
        return frameKeyword;
    }


    public boolean isSectionKeyword() {
        return sectionKeyword;
    }


    public boolean isAxiomKeyword() {
        return axiomKeyword;
    }


    public boolean isClassExpressionConnectiveKeyword() {
        return classExpressionConnectiveKeyword;
    }


    public boolean isClassExpressionQuantiferKeyword() {
        return classExpressionQuantiferKeyword;
    }


    @Override
	public String toString() {
        return rendering;
    }
}
