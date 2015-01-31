/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class HashCode implements OWLObjectVisitor, SWRLObjectVisitor {

    private int hashCode;
    private static final int MULT = 31;
    private static final int[] primes = { 37,// 37 41 43 47 53 59 61 67 71
            73,// 79 83 89 97 101 103 107 109 113
            127,// 131 137 139 149 151 157 163 167 173
            179,// 181 191 193 197 199 211 223 227 229
            233,// 239 241 251 257 263 269 271 277 281
            283,// 293 307 311 313 317 331 337 347 349
            353,// 359 367 373 379 383 389 397 401 409
            419,// 421 431 433 439 443 449 457 461 463
            467,// 479 487 491 499 503 509 521 523 541
            547,// 557 563 569 571 577 587 593 599 601
            607,// 613 617 619 631 641 643 647 653 659
            661,// 673 677 683 691 701 709 719 727 733
            739,// 743 751 757 761 769 773 787 797 809
            811,// 821 823 827 829 839 853 857 859 863
            877,// 881 883 887 907 911 919 929 937 941
            947,// 953 967 971 977 983 991 997 1009 1013
            1019,// 1021 1031 1033 1039 1049 1051 1061 1063 1069
            1087,// 1091 1093 1097 1103 1109 1117 1123 1129 1151
            1153,// 1163 1171 1181 1187 1193 1201 1213 1217 1223
            1229,// 1231 1237 1249 1259 1277 1279 1283 1289 1291
            1297,// 1301 1303 1307 1319 1321 1327 1361 1367 1373
            1381,// 1399 1409 1423 1427 1429 1433 1439 1447 1451
            1453,// 1459 1471 1481 1483 1487 1489 1493 1499 1511
            1523,// 1531 1543 1549 1553 1559 1567 1571 1579 1583
            1597,// 1601 1607 1609 1613 1619 1621 1627 1637 1657
            1663,// 1667 1669 1693 1697 1699 1709 1721 1723 1733
            1741,// 1747 1753 1759 1777 1783 1787 1789 1801 1811
            1823,// 1831 1847 1861 1867 1871 1873 1877 1879 1889
            1901,// 1907 1913 1931 1933 1949 1951 1973 1979 1987
            1993,// 1997 1999 2003 2011 2017 2027 2029 2039 2053
            2063,// 2069 2081 2083 2087 2089 2099 2111 2113 2129
            2131,// 2137 2141 2143 2153 2161 2179 2203 2207 2213
            2221,// 2237 2239 2243 2251 2267 2269 2273 2281 2287
            2293,// 2297 2309 2311 2333 2339 2341 2347 2351 2357
            2371,// 2377 2381 2383 2389 2393 2399 2411 2417 2423
            2437,// 2441 2447 2459 2467 2473 2477 2503 2521 2531
            2539,// 2543 2549 2551 2557 2579 2591 2593 2609 2617
            2621,// 2633 2647 2657 2659 2663 2671 2677 2683 2687
            2689,// 2693 2699 2707 2711 2713 2719 2729 2731 2741
            2749,// 2753 2767 2777 2789 2791 2797 2801 2803 2819
            2833,// 2837 2843 2851 2857 2861 2879 2887 2897 2903
            2909,// 2917 2927 2939 2953 2957 2963 2969 2971 2999
            3001,// 3011 3019 3023 3037 3041 3049 3061 3067 3079
            3083,// 3089 3109 3119 3121 3137 3163 3167 3169 3181
            3187,// 3191 3203 3209 3217 3221 3229 3251 3253 3257
            3259,// 3271 3299 3301 3307 3313 3319 3323 3329 3331
            3343,// 3347 3359 3361 3371 3373 3389 3391 3407 3413
            3433,// 3449 3457 3461 3463 3467 3469 3491 3499 3511
            3517,// 3527 3529 3533 3539 3541 3547 3557 3559 3571
            3581,// 3583 3593 3607 3613 3617 3623 3631 3637 3643
            3659,// 3671 3673 3677 3691 3697 3701 3709 3719 3727
            3733,// 3739 3761 3767 3769 3779 3793 3797 3803 3821
            3823,// 3833 3847 3851 3853 3863 3877 3881 3889 3907
            3911,// 3917 3919 3923 3929 3931 3943 3947 3967 3989
            4001,// 4003 4007 4013 4019 4021 4027 4049 4051 4057
            4073,// 4079 4091 4093 4099 4111 4127 4129 4133 4139
            4153,// 4157 4159 4177 4201 4211 4217 4219 4229 4231
            4241,// 4243 4253 4259 4261 4271 4273 4283 4289 4297
            4327,// 4337 4339 4349 4357 4363 4373 4391 4397 4409
            4421,// 4423 4441 4447 4451 4457 4463 4481 4483 4493
            4507,// 4513 4517 4519 4523 4547 4549 4561 4567 4583
            4591,// 4597 4603 4621 4637 4639 4643 4649 4651 4657
            4663,// 4673 4679 4691 4703 4721 4723 4729 4733 4751
            4759,// 4783 4787 4789 4793 4799 4801 4813 4817 4831
            4861,// 4871 4877 4889 4903 4909 4919 4931 4933 4937
            4943,// 4951 4957 4967 4969 4973 4987 4993 4999 5003
            5009,// 5011 5021 5023 5039 5051 5059 5077 5081 5087
            5099,// 5101 5107 5113 5119 5147 5153 5167 5171 5179
            5189,// 5197 5209 5227 5231 5233 5237 5261 5273 5279
            5281,// 5297 5303 5309 5323 5333 5347 5351 5381 5387
            5393,// 5399 5407 5413 5417 5419 5431 5437 5441 5443
            5449,// 5471 5477 5479 5483 5501 5503 5507 5519 5521
            5527,// 5531 5557 5563 5569 5573 5581 5591 5623 5639
            5641,// 5647 5651 5653 5657 5659 5669 5683 5689 5693
            5701,// 5711 5717 5737 5741 5743 5749 5779 5783 5791
            5801,// 5807 5813 5821 5827 5839 5843 5849 5851 5857
            5861,// 5867 5869 5879 5881 5897 5903 5923 5927 5939
            5953,// 5981 5987 6007 6011 6029 6037 6043 6047 6053
            6067,// 6073 6079 6089 6091 6101 6113 6121 6131 6133
            6143,// 6151 6163 6173 6197 6199 6203 6211 6217 6221
            6229,// 6247 6257 6263 6269 6271 6277 6287 6299 6301
            6311,// 6317 6323 6329 6337 6343 6353 6359 6361 6367
            6373,// 6379 6389 6397 6421 6427 6449 6451 6469 6473
            6481,// 6491 6521 6529 6547 6551 6553 6563 6569 6571
            6577,// 6581 6599 6607 6619 6637 6653 6659 6661 6673
            6679,// 6689 6691 6701 6703 6709 6719 6733 6737 6761
            6763,// 6779 6781 6791 6793 6803 6823 6827 6829 6833
            6841,// 6857 6863 6869 6871 6883 6899 6907 6911 6917
            6947,// 6949 6959 6961 6967 6971 6977 6983 6991 6997
            7001,// 7013 7019 7027 7039 7043 7057 7069 7079 7103
            7109,// 7121 7127 7129 7151 7159 7177 7187 7193 7207
            7211,// 7213 7219 7229 7237 7243 7247 7253 7283 7297
            7307,// 7309 7321 7331 7333 7349 7351 7369 7393 7411
            7417,// 7433 7451 7457 7459 7477 7481 7487 7489 7499
            7507,// 7517 7523 7529 7537 7541 7547 7549 7559 7561
            7573,// 7577 7583 7589 7591 7603 7607 7621 7639 7643
            7649,// 7669 7673 7681 7687 7691 7699 7703 7717 7723
            7727,// 7741 7753 7757 7759 7789 7793 7817 7823 7829
            7841,// 7853 7867 7873 7877 7879 7883 7901 7907 7919
    };

    /**
     * @param object
     *        the object to compute the hashcode for
     * @return the hashcode
     */
    public static int hashCode(@Nonnull OWLObject object) {
        checkNotNull(object, "object cannot be null");
        HashCode hashCode = new HashCode();
        object.accept(hashCode);
        return hashCode.hashCode;
    }

    @Override
    public void visit(OWLOntology ontology) {
        hashCode = ontology.getOntologyID().hashCode();
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        hashCode = primes[0];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        hashCode = primes[1];
        hashCode = hashCode * MULT + axiom.getIndividual().hashCode();
        hashCode = hashCode * MULT + axiom.getClassExpression().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        hashCode = primes[2];
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        hashCode = primes[3];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        hashCode = primes[4];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        hashCode = primes[5];
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {
        hashCode = primes[6];
        hashCode = hashCode * MULT + axiom.getEntity().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        hashCode = primes[7];
        hashCode = hashCode * MULT + axiom.getIndividuals().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        hashCode = primes[8];
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        hashCode = primes[9];
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        hashCode = primes[10];
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        hashCode = primes[11];
        hashCode = hashCode * MULT + axiom.getOWLClass().hashCode();
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {
        hashCode = primes[12];
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getValue().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        hashCode = primes[13];
        hashCode = hashCode * MULT + axiom.getClassExpressions().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        hashCode = primes[14];
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        hashCode = primes[15];
        hashCode = hashCode * MULT + axiom.getProperties().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        hashCode = primes[16];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        hashCode = primes[17];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        hashCode = primes[18];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        hashCode = primes[19];
        hashCode = hashCode * MULT + axiom.getFirstProperty().hashCode()
                + axiom.getSecondProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        hashCode = primes[20];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        hashCode = primes[21];
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        hashCode = primes[22];
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        hashCode = primes[23];
        hashCode = hashCode * MULT + axiom.getSubject().hashCode();
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getObject().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        hashCode = primes[24];
        hashCode = hashCode * MULT + axiom.getPropertyChain().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        hashCode = primes[25];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        hashCode = primes[26];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        hashCode = primes[27];
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        hashCode = primes[28];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        hashCode = primes[29];
        hashCode = hashCode * MULT + axiom.getIndividuals().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        hashCode = primes[30];
        hashCode = hashCode * MULT + axiom.getSubClass().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperClass().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        hashCode = primes[31];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        hashCode = primes[32];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getAnnotations().hashCode();
    }

    @Override
    public void visit(OWLClass ce) {
        hashCode = primes[33];
        hashCode = hashCode * MULT + ce.getIRI().hashCode();
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        hashCode = primes[34];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        hashCode = primes[35];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        hashCode = primes[36];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        hashCode = primes[37];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        hashCode = primes[38];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        hashCode = primes[39];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        hashCode = primes[40];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        hashCode = primes[41];
        hashCode = hashCode * MULT + ce.getOperand().hashCode();
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        hashCode = primes[42];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        hashCode = primes[43];
        hashCode = hashCode * MULT + ce.getOperands().hashCode();
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        hashCode = primes[44];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectMinCardinality ce) {
        hashCode = primes[45];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getCardinality();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        hashCode = primes[46];
        hashCode = hashCode * MULT + ce.getIndividuals().hashCode();
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        hashCode = primes[47];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        hashCode = primes[48];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        hashCode = primes[49];
        hashCode = hashCode * MULT + ce.getOperands().hashCode();
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        hashCode = primes[50];
        hashCode = hashCode * MULT + ce.getProperty().hashCode();
        hashCode = hashCode * MULT + ce.getFiller().hashCode();
    }

    @Override
    public void visit(OWLDataComplementOf node) {
        hashCode = primes[51];
        hashCode = hashCode * MULT + node.getDataRange().hashCode();
    }

    @Override
    public void visit(OWLDataOneOf node) {
        hashCode = primes[52];
        hashCode = hashCode * MULT + node.getValues().hashCode();
    }

    @Override
    public void visit(OWLDatatype node) {
        hashCode = primes[53];
        hashCode = hashCode * MULT + node.getIRI().hashCode();
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        hashCode = primes[54];
        hashCode = hashCode * MULT + node.getDatatype().hashCode();
        hashCode = hashCode * MULT + node.getFacetRestrictions().hashCode();
    }

    @Override
    public void visit(OWLDataProperty property) {
        hashCode = primes[55];
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(OWLObjectProperty property) {
        hashCode = primes[56];
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(OWLObjectInverseOf property) {
        hashCode = primes[57];
        hashCode = hashCode * MULT + property.getInverse().hashCode();
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        hashCode = primes[58];
        hashCode = hashCode * MULT + individual.getIRI().hashCode();
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        hashCode = primes[59];
        hashCode = hashCode * MULT + node.getFacet().hashCode();
        hashCode = hashCode * MULT + node.getFacetValue().hashCode();
    }

    @Override
    public void visit(OWLLiteral node) {
        hashCode = node.hashCode();
    }

    @Override
    public void visit(SWRLRule rule) {
        hashCode = primes[61];
        hashCode = hashCode * MULT + rule.getBody().hashCode();
        hashCode = hashCode * MULT + rule.getHead().hashCode();
    }

    @Override
    public void visit(SWRLClassAtom node) {
        hashCode = primes[62];
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLDataRangeAtom node) {
        hashCode = primes[63];
        hashCode = hashCode * MULT + node.getArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLObjectPropertyAtom node) {
        hashCode = primes[64];
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLDataPropertyAtom node) {
        hashCode = primes[65];
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLBuiltInAtom node) {
        hashCode = primes[66];
        hashCode = hashCode * MULT + node.getAllArguments().hashCode();
        hashCode = hashCode * MULT + node.getPredicate().hashCode();
    }

    @Override
    public void visit(SWRLVariable node) {
        hashCode = primes[67];
        hashCode = hashCode * MULT + node.getIRI().hashCode();
    }

    @Override
    public void visit(SWRLIndividualArgument node) {
        hashCode = primes[68];
        hashCode = hashCode * MULT + node.getIndividual().hashCode();
    }

    @Override
    public void visit(SWRLLiteralArgument node) {
        hashCode = primes[69];
        hashCode = hashCode * MULT + node.getLiteral().hashCode();
    }

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {
        hashCode = primes[70];
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }

    @Override
    public void visit(SWRLSameIndividualAtom node) {
        hashCode = primes[71];
        hashCode = hashCode * MULT + node.getFirstArgument().hashCode();
        hashCode = hashCode * MULT + node.getSecondArgument().hashCode();
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        hashCode = primes[72];
        hashCode = hashCode * MULT + axiom.getClassExpression().hashCode();
        hashCode = hashCode * MULT + axiom.getPropertyExpressions().hashCode();
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        hashCode = primes[73];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getDomain().hashCode();
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        hashCode = primes[74];
        hashCode = hashCode * MULT + axiom.getProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getRange().hashCode();
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        hashCode = primes[75];
        hashCode = hashCode * MULT + axiom.getSubProperty().hashCode();
        hashCode = hashCode * MULT + axiom.getSuperProperty().hashCode();
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        hashCode = primes[76];
        hashCode = hashCode * MULT + node.getOperands().hashCode();
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        hashCode = primes[77];
        hashCode = hashCode * MULT + node.getOperands().hashCode();
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        hashCode = primes[78];
        hashCode = hashCode * MULT + property.getIRI().hashCode();
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        hashCode = primes[79];
        hashCode = hashCode * MULT + individual.getID().hashCode();
    }

    @Override
    public void visit(IRI iri) {
        hashCode = primes[80];
        hashCode = hashCode * MULT + iri.toURI().hashCode();
    }

    @Override
    public void visit(OWLAnnotation node) {
        hashCode = primes[81];
        hashCode = hashCode * MULT + node.getProperty().hashCode();
        hashCode = hashCode * MULT + node.getValue().hashCode();
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        hashCode = primes[82];
        hashCode = hashCode * MULT + axiom.getDatatype().hashCode();
        hashCode = hashCode * MULT + axiom.getDataRange().hashCode();
    }
}
