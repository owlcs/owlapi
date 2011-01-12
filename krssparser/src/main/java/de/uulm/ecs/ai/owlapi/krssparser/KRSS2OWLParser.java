package de.uulm.ecs.ai.owlapi.krssparser;

import java.io.IOException;

import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;

/*
* Copyright (C) 2008, Ulm University
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
 * The KRSS2OWLParser differs from the {@link org.coode.owl.krssparser.KRSSOWLParser KRSSOWLParser} that
 * it supports an extended KRSS vocabulary available in many reasoning systems. For instance, CGIs can
 * be added with help of (implies subclass superclass), range, domain, inverse, functinal attribute can be
 * provided for roles.
 * Note that DatatypeProperties are not supported within KRSS2.
 * <p/>
 * <b>Abbreviations</b>
 * <table bordercolor="#000200" border="1">
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
 * <p/>
 * <b>KRSS concept language</b>
 * <table bordercolor="#000200" border="1">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLDescription</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(at-least n R C)</td>
 * <td>(OWLObjectMinCardinalityRestriction R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(at-most n R C)</td>
 * <td>(OWLObjectMaxCardinalityRestriction R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(exactly n R C)</td>
 * <td>(OWLObjectExactCardinalityRestriction R n C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(some R C)</td>
 * <td>(OWLObjectSomeRestriction R C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(all R C)</td>
 * <td>(OWLObjectAllRestriction R C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(not C)</td>
 * <td>(OWLObjectComplementOf C)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(and C D E)</td>
 * <td>(OWLObjectIntersectionOf C D E)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(or C D E)</td>
 * <td>(OWLObjectUnionOf C D E)</td>
 * </tr>
 * <p/>
 * <p/>
 * </table>
 * <p/>
 * <b>KRSS role language</b>
 * <table bordercolor="#000200" border="1">
 * <tr>
 * <td>KRSS</td>
 * <td>OWLObjectPropertyExpression</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(inv R)</td>
 * <td>(OWLInverseObjectPropertiesAxiom R)</td>
 * </tr>
 * <p/>
 * </table>
 * <p/>
 * <table bordercolor="#000200" border="1">
 * <th>KRSS2</th>
 * <th>OWLAxiom</th>
 * <th>Remarks</th>
 * <p/>
 * <tr>
 * <td>(define-primitive-concept CN C)</td>
 * <td>(OWLSubClassOfAxiom CN C)</td>
 * <td>If C is not given owl:Thing will be used instead.</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(define-concept CN C)</td>
 * <td>(OWLEquivalentClassesAxiom CN C)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(disjoint C D)</td>
 * <td>(OWLDisjointClassesAxiom C D)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(equivalent C D)</td>
 * <td>(OWLEquivalentClassesAxion C D)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(implies C D)</td>
 * <td>(OWLSubclassOf C D)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(define-role RN RN2)</td>
 * <td>(OWLEquivalentObjectPropertiesAxiom RN RN2)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(define-primitive-role RN :right-identity RN1)</td>
 * <td>(OWLObjectPropertyChainSubPropertyAxiom (RN RN1) RN)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(define-primitive-role RN :left-identity RN1)</td>
 * <td>(OWLObjectPropertyChainSubPropertyAxiom (RN1 RN) RN)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(define-primitive-role RN RN1)</td>
 * <td>(OWLSubObjectPropertyAxiom RN RN1)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(define-primitive-role RN :parents (RN1 RN2 ...RNn))</td>
 * <td>(OWLSubObjectPropertyAxiom RN RN1)<br>(OWLSubObjectPropertyAxiom RN RN2)<br>(OWLSubObjectPropertyAxiom RN RNn)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(define-primitive-role RN :domain (C D ...E) :range (C D ...E) :transitive t :symmetric t :reflexive t :inverse RN1)</td>
 * <td></td>
 * <td>Corresponding axioms for domain and range as well as transitive, symmetric, reflexive and inverse will be added.
 * </td>
 * </tr>
 * <p/>
 * <p/>
 * <tr>
 * <td>(disjoint-roles R R1)</td>
 * <td>(OWLDisjointObjectPropertiesAxiom R R1)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(implies-role R R)</td>
 * <td>(OWLSubObjectPropertyAxiom R R1)</td>
 * <td>(OWLInverseObjectPropertiesAxiom R R1)</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(inverse RN RN1)</td>
 * <td></td>
 * <td></td>
 * </tr>
 * <p/>
 * <p/>
 * <tr>
 * <td>(roles-equivalent R R1)</td>
 * <td>(OWLEquivalentObjectPropertiesAxiom R R1)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(role-inclusion (compose RN RN1) RN2</td>
 * <td>(OWLObjectPropertyChainSubPropertyAxiom (RN RN1) RN2)</td>
 * <td>RN1 can also be (compose RN3 ...).</td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(transitive RN)</td>
 * <td>(OWLTransitiveObjectPropertyAxiom RN)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(range RN C)</td>
 * <td>(OWLObjectPropertyRangeAxiom RN C)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(instance i C)</td>
 * <td>(OWLClassAssertionAxiom i C)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(related i R i2)</td>
 * <td>(OWLObjectPropertyAssertionAxiom i R i2)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <p/>
 * <tr>
 * <td>(equal i1 i2)</td>
 * <td>(OWLSameIndividualsAxiom i1 i2)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <tr>
 * <td>(distinct i1 i2)</td>
 * <td>(OWLDifferentIndividualsAxiom i1 i2)</td>
 * <td></td>
 * </tr>
 * <p/>
 * <p/>
 * </table>
 *
 * Author: Olaf Noppens<br>
 * Ulm University<br>
 * Institute of Artificial Intelligence<br>
 */
public class KRSS2OWLParser extends AbstractOWLParser {


    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException, UnloadableImportException {
        return parse(documentSource, ontology, new OWLOntologyLoaderConfiguration());
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
        try {
            KRSS2OntologyFormat format = new KRSS2OntologyFormat();
            KRSS2Parser parser;
            if (documentSource.isReaderAvailable()) {
                parser = new KRSS2Parser(documentSource.getReader());
            } else if (documentSource.isInputStreamAvailable()) {
                parser = new KRSS2Parser(documentSource.getInputStream());
            } else {
                parser = new KRSS2Parser(getInputStream(documentSource.getDocumentIRI()));
            }
            parser.setOntology(ontology, getOWLOntologyManager().getOWLDataFactory());
            parser.parse();
            return format;
        }
        catch (ParseException e) {
            throw new KRSS2OWLParserException(e);
        }
    }
}
