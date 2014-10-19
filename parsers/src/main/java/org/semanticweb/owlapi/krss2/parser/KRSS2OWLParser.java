/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Ulm University
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.krss2.parser;

import java.io.IOException;
import java.io.Reader;

import org.semanticweb.owlapi.formats.KRSS2DocumentFormat;
import org.semanticweb.owlapi.formats.KRSS2DocumentFormatFactory;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.DocumentSources;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyInputSourceException;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * The KRSS2OWLParser differs from the
 * {@link org.semanticweb.owlapi.krss1.parser.KRSSOWLParser KRSSOWLParser} that
 * it supports an extended KRSS vocabulary available in many reasoning systems.
 * For instance, CGIs can be added with help of (implies subclass superclass),
 * range, domain, inverse, functinal attribute can be provided for roles. Note
 * that DatatypeProperties are not supported within KRSS2. <br>
 * <b>Abbreviations</b>
 * <table summary="Abbreviations">
 * <tr>
 * <td>CN</td>
 * <td>concept name</td>
 * </tr>
 * <tr>
 * <td>C,D,E</td>
 * <td>concept expression</td>
 * </tr>
 * <tr>
 * <td>RN</td>
 * <td>role name</td>
 * </tr>
 * <tr>
 * <td>R, R1, R2,...</td>
 * <td>role expressions, i.e. role name or inverse role</td>
 * </tr>
 * </table>
 * <br>
 * <b>KRSS concept language</b>
 * <table summary="KRSS concept language">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLDescription</td>
 * </tr>
 * <tr>
 * <td>(at-least n R C)</td>
 * <td>(OWLObjectMinCardinalityRestriction R n C)</td>
 * </tr>
 * <tr>
 * <td>(at-most n R C)</td>
 * <td>(OWLObjectMaxCardinalityRestriction R n C)</td>
 * </tr>
 * <tr>
 * <td>(exactly n R C)</td>
 * <td>(OWLObjectExactCardinalityRestriction R n C)</td>
 * </tr>
 * <tr>
 * <td>(some R C)</td>
 * <td>(OWLObjectSomeRestriction R C)</td>
 * </tr>
 * <tr>
 * <td>(all R C)</td>
 * <td>(OWLObjectAllRestriction R C)</td>
 * </tr>
 * <tr>
 * <td>(not C)</td>
 * <td>(OWLObjectComplementOf C)</td>
 * </tr>
 * <tr>
 * <td>(and C D E)</td>
 * <td>(OWLObjectIntersectionOf C D E)</td>
 * </tr>
 * <tr>
 * <td>(or C D E)</td>
 * <td>(OWLObjectUnionOf C D E)</td>
 * </tr>
 * </table>
 * <br>
 * <b>KRSS role language</b>
 * <table summary="KRSS role language">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLObjectPropertyExpression</td>
 * </tr>
 * <tr>
 * <td>(inv R)</td>
 * <td>(OWLInverseObjectPropertiesAxiom R)</td>
 * </tr>
 * </table>
 * <br>
 * <table summary="remarks">
 * <tr>
 * <td>KRSS2</td>
 * <td>OWLAxiom</td>
 * <td>Remarks</td>
 * </tr>
 * <tr>
 * <td>(define-primitive-concept CN C)</td>
 * <td>(OWLSubClassOfAxiom CN C)</td>
 * <td>If C is not given owl:Thing will be used instead.</td>
 * </tr>
 * <tr>
 * <td>(define-concept CN C)</td>
 * <td>(OWLEquivalentClassesAxiom CN C)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(disjoint C D)</td>
 * <td>(OWLDisjointClassesAxiom C D)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(equivalent C D)</td>
 * <td>(OWLEquivalentClassesAxion C D)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(implies C D)</td>
 * <td>(OWLSubclassOf C D)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(define-role RN RN2)</td>
 * <td>(OWLEquivalentObjectPropertiesAxiom RN RN2)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(define-primitive-role RN :right-identity RN1)</td>
 * <td>(OWLObjectPropertyChainSubPropertyAxiom (RN RN1) RN)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(define-primitive-role RN :left-identity RN1)</td>
 * <td>(OWLObjectPropertyChainSubPropertyAxiom (RN1 RN) RN)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(define-primitive-role RN RN1)</td>
 * <td>(OWLSubObjectPropertyAxiom RN RN1)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(define-primitive-role RN :parents (RN1 RN2 ...RNn))</td>
 * <td>(OWLSubObjectPropertyAxiom RN RN1)<br>
 * (OWLSubObjectPropertyAxiom RN RN2)<br>
 * (OWLSubObjectPropertyAxiom RN RNn)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(define-primitive-role RN :domain (C D ...E) :range (C D ...E)
 * :transitive t :symmetric t :reflexive t :inverse RN1)</td>
 * <td></td>
 * <td>Corresponding axioms for domain and range as well as transitive,
 * symmetric, reflexive and inverse will be added.</td>
 * </tr>
 * <tr>
 * <td>(disjoint-roles R R1)</td>
 * <td>(OWLDisjointObjectPropertiesAxiom R R1)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(implies-role R R)</td>
 * <td>(OWLSubObjectPropertyAxiom R R1)</td>
 * <td>(OWLInverseObjectPropertiesAxiom R R1)</td>
 * </tr>
 * <tr>
 * <td>(inverse RN RN1)</td>
 * <td></td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(roles-equivalent R R1)</td>
 * <td>(OWLEquivalentObjectPropertiesAxiom R R1)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(role-inclusion (compose RN RN1) RN2</td>
 * <td>(OWLObjectPropertyChainSubPropertyAxiom (RN RN1) RN2)</td>
 * <td>RN1 can also be (compose RN3 ...).</td>
 * </tr>
 * <tr>
 * <td>(transitive RN)</td>
 * <td>(OWLTransitiveObjectPropertyAxiom RN)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(range RN C)</td>
 * <td>(OWLObjectPropertyRangeAxiom RN C)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(instance i C)</td>
 * <td>(OWLClassAssertionAxiom i C)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(related i R i2)</td>
 * <td>(OWLObjectPropertyAssertionAxiom i R i2)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(equal i1 i2)</td>
 * <td>(OWLSameIndividualsAxiom i1 i2)</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>(distinct i1 i2)</td>
 * <td>(OWLDifferentIndividualsAxiom i1 i2)</td>
 * <td></td>
 * </tr>
 * </table>
 * 
 * @author Olaf Noppens, Ulm University, Institute of Artificial Intelligence
 */
public class KRSS2OWLParser extends AbstractOWLParser {

    private static final long serialVersionUID = 40000L;

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new KRSS2DocumentFormatFactory();
    }

    @Override
    public OWLDocumentFormat parse(OWLOntologyDocumentSource source,
            OWLOntology ontology, OWLOntologyLoaderConfiguration config) {
        try (Reader r = DocumentSources.wrapInputAsReader(source, config)) {
            KRSS2DocumentFormat format = new KRSS2DocumentFormat();
            KRSS2Parser parser = new KRSS2Parser(r);
            parser.setOntology(ontology);
            parser.parse();
            return format;
        } catch (ParseException | OWLOntologyInputSourceException | IOException e) {
            throw new KRSS2OWLParserException(e);
        }
    }
}
