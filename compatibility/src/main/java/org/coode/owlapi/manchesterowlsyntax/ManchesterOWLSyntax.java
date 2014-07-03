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
package org.coode.owlapi.manchesterowlsyntax;

import javax.annotation.Nonnull;

/**
 * The vocabulary that the Manchester OWL Syntax uses.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 25-Apr-2007
 * @deprecated use
 *             {@link org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax}
 */
@Deprecated
public enum ManchesterOWLSyntax {





    //@formatter:off
    
//    public static final String VALUE_PARTITION = "ValuePartition:";
//    public static final String INSTANCES = "Instances:";
    
    /** VALUE_PARTITION             */   VALUE_PARTITION             (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.VALUE_PARTITION             ),
    /** DASH                        */   DASH                        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DASH                        ),
    /** OPEN                        */   OPEN                        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.OPEN                        ),
    /** CLOSE                       */   CLOSE                       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.CLOSE                       ),
    /** OPENBRACE                   */   OPENBRACE                   (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.OPENBRACE                   ),
    /** CLOSEBRACE                  */   CLOSEBRACE                  (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.CLOSEBRACE                  ),
    /** OPENBRACKET                 */   OPENBRACKET                 (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.OPENBRACKET                 ),
    /** CLOSEBRACKET                */   CLOSEBRACKET                (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.CLOSEBRACKET                ),
    /** ONTOLOGY                    */   ONTOLOGY                    (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.ONTOLOGY                    ),
    /** IMPORT                      */   IMPORT                      (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.IMPORT                      ),
    /** PREFIX                      */   PREFIX                      (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.PREFIX                      ),
    /** CLASS                       */   CLASS                       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.CLASS                       ),
    /** OBJECT_PROPERTY             */   OBJECT_PROPERTY             (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.OBJECT_PROPERTY             ),
    /** CHAIN_IMPLY                 */   CHAIN_IMPLY                 (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.CHAIN_IMPLY                 ),
    /** CHAIN_CONNECT               */   CHAIN_CONNECT               (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.CHAIN_CONNECT               ),
    /** DATA_PROPERTY               */   DATA_PROPERTY               (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DATA_PROPERTY               ),
    /** INDIVIDUAL                  */   INDIVIDUAL                  (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.INDIVIDUAL                  ),
    /** DATATYPE                    */   DATATYPE                    (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DATATYPE                    ),
    /** ANNOTATION_PROPERTY         */   ANNOTATION_PROPERTY         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.ANNOTATION_PROPERTY         ),
    /** SOME                        */   SOME                        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SOME                        ),
    /** ONLY                        */   ONLY                        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.ONLY                        ),
    /** ONLYSOME                    */   ONLYSOME                    (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.ONLYSOME                    ),
    /** MIN                         */   MIN                         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.MIN                         ),
    /** MAX                         */   MAX                         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.MAX                         ),
    /** EXACTLY                     */   EXACTLY                     (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.EXACTLY                     ),
    /** VALUE                       */   VALUE                       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.VALUE                       ),
    /** AND                         */   AND                         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.AND                         ),
    /** OR                          */   OR                          (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.OR                          ),
    /** NOT                         */   NOT                         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.NOT                         ),
    /** INVERSE                     */   INVERSE                     (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.INVERSE                     ),
    /** INV                         */   INV                         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.INV                         ),
    /** SELF                        */   SELF                        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SELF                        ),
    /** THAT                        */   THAT                        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.THAT                        ),
    /** FACET_RESTRICTION_SEPARATOR */   FACET_RESTRICTION_SEPARATOR (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.FACET_RESTRICTION_SEPARATOR ),
    /** SUBCLASS_OF                 */   SUBCLASS_OF                 (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SUBCLASS_OF          ),
    /** SUPERCLASS_OF               */   SUPERCLASS_OF               (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SUPERCLASS_OF        ),
    /** EQUIVALENT_TO               */   EQUIVALENT_TO               (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.EQUIVALENT_TO        ),
    /** EQUIVALENT_CLASSES          */   EQUIVALENT_CLASSES          (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.EQUIVALENT_CLASSES   ),
    /** EQUIVALENT_PROPERTIES       */   EQUIVALENT_PROPERTIES       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.EQUIVALENT_PROPERTIES),
    /** DISJOINT_WITH               */   DISJOINT_WITH               (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DISJOINT_WITH        ),
    /** INDIVIDUALS                 */   INDIVIDUALS                 (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.INDIVIDUALS          ),
    /** DISJOINT_CLASSES            */   DISJOINT_CLASSES            (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DISJOINT_CLASSES     ),
    /** DISJOINT_PROPERTIES         */   DISJOINT_PROPERTIES         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DISJOINT_PROPERTIES  ),
    /** DISJOINT_UNION_OF           */   DISJOINT_UNION_OF           (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DISJOINT_UNION_OF    ),
    /** FACTS                       */   FACTS                       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.FACTS                ),
    /** SAME_AS                     */   SAME_AS                     (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SAME_AS              ),
    /** SAME_INDIVIDUAL             */   SAME_INDIVIDUAL             (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SAME_INDIVIDUAL      ),
    /** DIFFERENT_FROM              */   DIFFERENT_FROM              (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DIFFERENT_FROM       ),
    /** DIFFERENT_INDIVIDUALS       */   DIFFERENT_INDIVIDUALS       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DIFFERENT_INDIVIDUALS),
    /** MIN_INCLUSIVE_FACET         */   MIN_INCLUSIVE_FACET         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.MIN_INCLUSIVE_FACET  ),
    /** MAX_INCLUSIVE_FACET         */   MAX_INCLUSIVE_FACET         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.MAX_INCLUSIVE_FACET  ),
    /** MIN_EXCLUSIVE_FACET         */   MIN_EXCLUSIVE_FACET         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.MIN_EXCLUSIVE_FACET  ),
    /** MAX_EXCLUSIVE_FACET         */   MAX_EXCLUSIVE_FACET         (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.MAX_EXCLUSIVE_FACET  ),
    /** ONE_OF_DELIMETER            */   ONE_OF_DELIMETER            (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.ONE_OF_DELIMETER     ),
    /** TYPES                       */   TYPES                       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.TYPES                ),
    /** TYPE                        */   TYPE                        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.TYPE                 ),
    /** ANNOTATIONS                 */   ANNOTATIONS                 (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.ANNOTATIONS          ),
    /** COMMA                       */   COMMA                       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.COMMA                ),
    /** DOMAIN                      */   DOMAIN                      (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.DOMAIN               ),
    /** RANGE                       */   RANGE                       (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.RANGE                ),
    /** CHARACTERISTICS             */   CHARACTERISTICS             (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.CHARACTERISTICS      ),
    /** FUNCTIONAL                  */   FUNCTIONAL                  (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.FUNCTIONAL           ),
    /** INVERSE_FUNCTIONAL          */   INVERSE_FUNCTIONAL          (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.INVERSE_FUNCTIONAL   ),
    /** SYMMETRIC                   */   SYMMETRIC                   (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SYMMETRIC            ),
    /** TRANSITIVE                  */   TRANSITIVE                  (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.TRANSITIVE           ),
    /** REFLEXIVE                   */   REFLEXIVE                   (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.REFLEXIVE            ),
    /** IRREFLEXIVE                 */   IRREFLEXIVE                 (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.IRREFLEXIVE          ),
    /** LITERAL_TRUE                */   LITERAL_TRUE                (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.LITERAL_TRUE         ),
    /** LITERAL_FALSE               */   LITERAL_FALSE               (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.LITERAL_FALSE        ),
    /** LITERAL_INTEGER             */   LITERAL_INTEGER             (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.LITERAL_INTEGER      ),
    /** LITERAL_FLOAT               */   LITERAL_FLOAT               (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.LITERAL_FLOAT        ),
    /** LITERAL_DOUBLE              */   LITERAL_DOUBLE              (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.LITERAL_DOUBLE       ),
    /** LITERAL_LITERAL             */   LITERAL_LITERAL             (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.LITERAL_LITERAL      ),
    /** LITERAL_LIT_DATATYPE        */   LITERAL_LIT_DATATYPE        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.LITERAL_LIT_DATATYPE ),
    /** LITERAL_LIT_LANG            */   LITERAL_LIT_LANG            (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.LITERAL_LIT_LANG     ),
    /** For legacy reasons. */                                 
    /**ANTI_SYMMETRIC              */    ANTI_SYMMETRIC              (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.ANTI_SYMMETRIC      ),
    /**ASYMMETRIC                  */    ASYMMETRIC                  (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.ASYMMETRIC          ),
    /**INVERSE_OF                  */    INVERSE_OF                  (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.INVERSE_OF          ),
    /**INVERSES                    */    INVERSES                    (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.INVERSES            ),
    /**SUB_PROPERTY_OF             */    SUB_PROPERTY_OF             (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SUB_PROPERTY_OF     ),
    /**SUPER_PROPERTY_OF           */    SUPER_PROPERTY_OF           (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SUPER_PROPERTY_OF   ),
    /**SUB_PROPERTY_CHAIN          */    SUB_PROPERTY_CHAIN          (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.SUB_PROPERTY_CHAIN  ),
    /**HAS_KEY                     */    HAS_KEY                     (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.HAS_KEY             ),
    /**RULE                        */    RULE                        (org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax.RULE                );

    //@formatter:on
    org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax legacy;

    private ManchesterOWLSyntax(
            org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntax legacy) {
        this.legacy = legacy;
    }

    /** @return frame keyword */
    public boolean isFrameKeyword() {
        return legacy.isFrameKeyword();
    }

    /** @return section keyword */
    public boolean isSectionKeyword() {
        return legacy.isSectionKeyword();
    }

    /** @return axiom keyword */
    public boolean isAxiomKeyword() {
        return legacy.isAxiomKeyword();
    }

    /** @return class conective */
    public boolean isClassExpressionConnectiveKeyword() {
        return legacy.isClassExpressionConnectiveKeyword();
    }

    /** @return class quantifier */
    public boolean isClassExpressionQuantiferKeyword() {
        return legacy.isClassExpressionQuantiferKeyword();
    }

    @Override
    public String toString() {
        return legacy.toString();
    }

    /** @return keyword */
    public String keyword() {
        return legacy.keyword();
    }

    /**
     * @param s
     *        s
     * @return true if matches keyword
     */
    public boolean matches(String s) {
        return legacy.matches(s);
    }

    /**
     * @param s
     *        s
     * @return true if either form matches
     */
    public boolean matchesEitherForm(String s) {
        return legacy.matchesEitherForm(s);
    }

    /**
     * for keywords which match two tokens.
     * 
     * @param s
     *        s
     * @param v
     *        v
     * @return true if matches
     */
    public boolean matches(@Nonnull String s, @Nonnull String v) {
        return legacy.matches(s, v);
    }

    /**
     * @param rendering
     *        rendering
     * @return manchester owl syntax object
     */
    public static ManchesterOWLSyntax parse(String rendering) {
        for (ManchesterOWLSyntax m : values()) {
            if (m.matches(rendering)) {
                return m;
            }
        }
        return null;
    }
}
