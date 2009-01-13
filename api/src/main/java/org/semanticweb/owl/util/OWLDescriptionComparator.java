package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
 * Date: 15-Jun-2007<br><br>
 *
 * It's sometimes useful (for pretty printing etc.) to be able to order
 * class descriptions.  This comparator provides an implementation that
 * can be used to order class descriptions.
 */
public class OWLDescriptionComparator implements Comparator<OWLDescription> {

    /*
        The basic idea behind the comparator is to first compare descriptions at
        a coarse grained level (Named classes, Object restrictions, Data restrictions,
        intersections, unions, complement of, oneOf -- see CoarseGrainedDescriptionComparator
        for details.  If the objects being compared fall into the same categories then
        a more fine grained comparator is used.
    */

    private CoarseGrainedDescriptionComparator descriptionComparator;

    public OWLDescriptionComparator(ShortFormProvider shortFormProvider) {
        descriptionComparator = new CoarseGrainedDescriptionComparator(shortFormProvider);
    }


    public int compare(OWLDescription o1, OWLDescription o2) {
        return descriptionComparator.compare(o1, o2);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Comparator for property expressions
    //

    /**
     * Compares two property expressions.  Object property expression have a higher precedence
     * than data properties.
     */
    public static class OWLPropertyExpressionComparator extends AbstractOWLObjectComparator<OWLPropertyExpression> implements OWLPropertyExpressionVisitor {

        private int lastValue;


        public OWLPropertyExpressionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }


        public int compareObjects(OWLPropertyExpression o1, OWLPropertyExpression o2) {
            // Object first then data
            o1.accept(this);
            int i1 = lastValue;
            o2.accept(this);
            int i2 = lastValue;
            int delta = i2 - i1;
            if(delta == 0) {
                if(o1.isAnonymous()) {
                    // Two Object property expressions
                    return compare(((OWLObjectPropertyInverse) o1).getInverse(),
                                   ((OWLObjectPropertyInverse) o2).getInverse());
                }
                else {
                    // Straight alpha comparison
                    String s1 = getShortForm((OWLProperty) o1);
                    String s2 = getShortForm((OWLProperty) o2);
                    return s1.compareTo(s2);
                }
            }
            else {
                return delta;
            }
        }


        public void visit(OWLDataProperty property) {
            lastValue = 0;
        }


        public void visit(OWLObjectProperty property) {
            lastValue = 1;
        }


        public void visit(OWLObjectPropertyInverse property) {
            lastValue = 2;
        }
    }


    /**
     * Compares two restrictions.  The restrictions are compared by property and then by
     * type
     */
    private abstract static class OWLRestrictionComparator<R extends OWLRestriction> extends AbstractOWLDescriptionComparator<R> {

        public static final int OBJECT_SOME = 0;

        public static final int OBJECT_MIN = 1;

        public static final int OBJECT_MAX = 2;

        public static final int OBJECT_EXACT = 3;

        public static final int OBJECT_SELF = 4;

        public static final int OBJECT_VALUE = 5;

        public static final int OBJECT_ALL = 6;

        public static final int DATA_SOME = 7;

        public static final int DATA_MIN = 8;

        public static final int DATA_MAX = 9;

        public static final int DATA_EXACT = 10;

        public static final int DATA_VALUE = 11;

        public static final int DATA_ALL = 12;

        private int typeIndex;

        private OWLPropertyExpressionComparator propertyComparator;

        public OWLRestrictionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
            propertyComparator = new OWLPropertyExpressionComparator(shortFormProvider);
        }


        protected int compareObjects(R o1, R o2) {
            // Compare properties
            OWLPropertyExpression prop1 = o1.getProperty();
            OWLPropertyExpression prop2 = o2.getProperty();
            int delta = propertyComparator.compare(prop1, prop2);
            if(delta != 0) {
                return delta;
            }
            // Compare types
            o1.accept(this);
            int type1 = typeIndex;
            o2.accept(this);
            int type2 = typeIndex;

            return type1 - type2;
        }


        public void visit(OWLDataAllRestriction desc) {
            typeIndex = DATA_ALL;
        }


        public void visit(OWLDataExactCardinalityRestriction desc) {
            typeIndex = DATA_EXACT;
        }


        public void visit(OWLDataMaxCardinalityRestriction desc) {
            typeIndex = DATA_MAX;
        }


        public void visit(OWLDataMinCardinalityRestriction desc) {
            typeIndex = DATA_MIN;
        }


        public void visit(OWLDataSomeRestriction desc) {
            typeIndex = DATA_SOME;
        }


        public void visit(OWLDataValueRestriction desc) {
            typeIndex = DATA_VALUE;
        }


        public void visit(OWLObjectAllRestriction desc) {
            typeIndex = OBJECT_ALL;
        }


        public void visit(OWLObjectExactCardinalityRestriction desc) {
            typeIndex = OBJECT_EXACT;
        }


        public void visit(OWLObjectMaxCardinalityRestriction desc) {
            typeIndex = OBJECT_MAX;
        }


        public void visit(OWLObjectMinCardinalityRestriction desc) {
            typeIndex = OBJECT_MIN;
        }


        public void visit(OWLObjectSelfRestriction desc) {
            typeIndex = OBJECT_SELF;
        }


        public void visit(OWLObjectSomeRestriction desc) {
            typeIndex = OBJECT_SOME;
        }


        public void visit(OWLObjectValueRestriction desc) {
            typeIndex = OBJECT_VALUE;
        }
    }

    private static class OWLDataRangeComparator extends AbstractOWLObjectComparator<OWLDataRange> implements OWLDataVisitor {

        public OWLDataRangeComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }


        protected int compareObjects(OWLDataRange o1, OWLDataRange o2) {
            return 0;
        }


        public void visit(OWLDataComplementOf node) {
        }


        public void visit(OWLDataOneOf node) {
        }


        public void visit(OWLDataRangeFacetRestriction node) {
        }


        public void visit(OWLDataRangeRestriction node) {
        }


        public void visit(OWLDataType node) {
        }


        public void visit(OWLTypedConstant node) {
        }


        public void visit(OWLUntypedConstant node) {
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class OWLObjectComplementOfComparator extends AbstractOWLDescriptionComparator<OWLObjectComplementOf> {

        public OWLObjectComplementOfComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }


        protected int compareObjects(OWLObjectComplementOf o1, OWLObjectComplementOf o2) {
            CoarseGrainedDescriptionComparator descriptionComparator = new CoarseGrainedDescriptionComparator(getShortFormProvider());
            return descriptionComparator.compare(o1.getOperand(), o2.getOperand());
        }
    }

    private static class NaryBooleanDescriptionComparator extends AbstractOWLDescriptionComparator<OWLNaryBooleanDescription> {

        private CoarseGrainedDescriptionComparator descriptionComparator;


        public NaryBooleanDescriptionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }


        protected int compareObjects(OWLNaryBooleanDescription o1, OWLNaryBooleanDescription o2) {
            CoarseGrainedDescriptionComparator descriptionComparator = new CoarseGrainedDescriptionComparator(getShortFormProvider());
            List<OWLDescription> descsA = new ArrayList<OWLDescription>(o1.getOperands());
            List<OWLDescription> descsB = new ArrayList<OWLDescription>(o2.getOperands());
            Collections.sort(descsA, descriptionComparator);
            Collections.sort(descsB, descriptionComparator);
            int maxIndex = descsA.size() > descsB.size() ? descsB.size() : descsA.size();
            for(int i = 0; i < maxIndex; i++) {
                OWLDescription descA = descsA.get(i);
                OWLDescription descB = descsB.get(i);
                int delta = descriptionComparator.compare(descA, descB);
                if(delta != 0) {
                    return delta;
                }
            }
            return descsA.size() - descsB.size();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class OWLSelfRestictionComparator extends OWLRestrictionComparator<OWLObjectSelfRestriction> {

        public OWLSelfRestictionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class OWLObjectOneOfComparator extends AbstractOWLDescriptionComparator<OWLObjectOneOf> {

        private OWLEntityComparator entityComparator;

        public OWLObjectOneOfComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
            entityComparator = new OWLEntityComparator(shortFormProvider);
        }


        protected int compareObjects(OWLObjectOneOf o1, OWLObjectOneOf o2) {
            List<OWLIndividual> indsA = new ArrayList<OWLIndividual>(o1.getIndividuals());
            List<OWLIndividual> indsB = new ArrayList<OWLIndividual>(o2.getIndividuals());
            Collections.sort(indsA, entityComparator);
            Collections.sort(indsB, entityComparator);
            int maxIndex = indsA.size() > indsB.size() ? indsB.size() : indsA.size();
            for(int i = 0; i < maxIndex; i++) {
                OWLIndividual descA = indsA.get(i);
                OWLIndividual descB = indsB.get(i);
                int delta = entityComparator.compare(descA, descB);
                if(delta != 0) {
                    return delta;
                }
            }
            return indsA.size() - indsB.size();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class OWLObjectQuantifiedRestrictionComparator extends OWLRestrictionComparator<OWLQuantifiedRestriction<OWLObjectPropertyExpression, OWLDescription>> {

        public OWLObjectQuantifiedRestrictionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }


        protected int compareObjects(OWLQuantifiedRestriction<OWLObjectPropertyExpression, OWLDescription> o1,
                                     OWLQuantifiedRestriction<OWLObjectPropertyExpression, OWLDescription> o2) {
            int delta = super.compareObjects(o1, o2);
            if(delta != 0) {
                return delta;
            }
            // Properties and types are the same
            // Compare fillers
            CoarseGrainedDescriptionComparator fillerComparator = new CoarseGrainedDescriptionComparator(getShortFormProvider());
            return fillerComparator.compare(o1.getFiller(), o2.getFiller());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class OWLObjectCardinalityRestrictionComparator extends OWLObjectQuantifiedRestrictionComparator {

        public OWLObjectCardinalityRestrictionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }


        public int compareObjects(OWLObjectCardinalityRestriction o1, OWLObjectCardinalityRestriction o2) {
            int delta = super.compare(o1, o2);
            if(delta != 0) {
                return 0;
            }
            return o1.getCardinality() - o2.getCardinality();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class OWLDataQuantifiedRestrictionComparator extends OWLRestrictionComparator<OWLQuantifiedRestriction<OWLDataPropertyExpression, OWLDataRange>> {

        private OWLDataRangeComparator fillerComparator;

        public OWLDataQuantifiedRestrictionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
            fillerComparator = new OWLDataRangeComparator(shortFormProvider);
        }


        protected int compareObjects(OWLQuantifiedRestriction<OWLDataPropertyExpression, OWLDataRange> o1,
                                     OWLQuantifiedRestriction<OWLDataPropertyExpression, OWLDataRange> o2) {
            int delta = super.compareObjects(o1, o2);
            if(delta != 0) {
                return delta;
            }
            // Properties and types are the same
            // Compare fillers
            return fillerComparator.compare(o1.getFiller(), o2.getFiller());
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static class OWLDataCardinalityRestrictionComparator extends OWLDataQuantifiedRestrictionComparator {

        public OWLDataCardinalityRestrictionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }


        public int compareObjects(OWLDataCardinalityRestriction o1, OWLDataCardinalityRestriction o2) {
            int delta = super.compare(o1, o2);
            if(delta != 0) {
                return 0;
            }
            return o1.getCardinality() - o2.getCardinality();
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Compares two descriptions using the coarse grained categories of
     * OWLClass, OWLObjectRestriction, OWLDataRestriction, OWLBooleanDescription, OWLObjectOneOf
     */
    public static class CoarseGrainedDescriptionComparator extends AbstractOWLDescriptionComparator<OWLDescription> {

        public static int OWL_CLASS = 0;

        public static int OWL_OBJECT_RESTRICTION = 1;

        public static int OWL_DATA_RESTRICTION = 2;

        public static int OWL_OBJECT_INTERSECTION_OF = 3;

        public static int OWL_OBJECT_UNION_OF = 4;

        public static int OWL_OBJECT_COMPLEMENT_OF = 5;

        public static int OWL_OBJECT_ONE_OF = 6;

        private OWLEntityComparator entityComparator;

        private OWLDataQuantifiedRestrictionComparator dataQuantifiedRestrictionComparator;

        private OWLDataCardinalityRestrictionComparator dataCardinalityRestrictionComparator;

        private OWLObjectCardinalityRestrictionComparator objectCardinalityRestrictionComparator;

        private OWLObjectQuantifiedRestrictionComparator objectQuantifiedRestrictionComparator;

        private OWLObjectComplementOfComparator objectComplementOfComparator;

        private NaryBooleanDescriptionComparator naryBooleanDescriptionComparator;

        private OWLSelfRestictionComparator selfRestictionComparator;

        private OWLObjectOneOfComparator objectOneOfComparator;

        private int lastValue;



        public CoarseGrainedDescriptionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
            entityComparator = new OWLEntityComparator(shortFormProvider);
            dataCardinalityRestrictionComparator = new OWLDataCardinalityRestrictionComparator(shortFormProvider);
            dataQuantifiedRestrictionComparator = new OWLDataQuantifiedRestrictionComparator(shortFormProvider);
            objectCardinalityRestrictionComparator = new OWLObjectCardinalityRestrictionComparator(shortFormProvider);
            objectQuantifiedRestrictionComparator = new OWLObjectQuantifiedRestrictionComparator(shortFormProvider);
            objectComplementOfComparator = new OWLObjectComplementOfComparator(shortFormProvider);
            naryBooleanDescriptionComparator = new NaryBooleanDescriptionComparator(shortFormProvider);
            selfRestictionComparator = new OWLSelfRestictionComparator(shortFormProvider);
            objectOneOfComparator = new OWLObjectOneOfComparator(shortFormProvider);
        }


        protected int compareObjects(OWLDescription o1, OWLDescription o2) {
            o1.accept(this);
            int i1 = lastValue;
            o2.accept(this);
            int i2 = lastValue;
            return i1 - i2;
        }

        public void visit(OWLClass desc) {
            lastValue = OWL_CLASS;
            setFineGrainedComparator(entityComparator);
        }


        public void visit(OWLDataAllRestriction desc) {
            lastValue = OWL_DATA_RESTRICTION;
            setFineGrainedComparator(dataQuantifiedRestrictionComparator);
        }


        public void visit(OWLDataExactCardinalityRestriction desc) {
            lastValue = OWL_DATA_RESTRICTION;
            setFineGrainedComparator(dataCardinalityRestrictionComparator);
        }


        public void visit(OWLDataMaxCardinalityRestriction desc) {
            lastValue = OWL_DATA_RESTRICTION;
            setFineGrainedComparator(dataCardinalityRestrictionComparator);
        }


        public void visit(OWLDataMinCardinalityRestriction desc) {
            lastValue = OWL_DATA_RESTRICTION;
            setFineGrainedComparator(dataCardinalityRestrictionComparator);
        }


        public void visit(OWLDataSomeRestriction desc) {
            lastValue = OWL_DATA_RESTRICTION;
            setFineGrainedComparator(dataQuantifiedRestrictionComparator);
        }


        public void visit(OWLDataValueRestriction desc) {
            lastValue = OWL_DATA_RESTRICTION;
            setFineGrainedComparator(dataQuantifiedRestrictionComparator);
        }


        public void visit(OWLObjectAllRestriction desc) {
            lastValue = OWL_OBJECT_RESTRICTION;
            setFineGrainedComparator(objectQuantifiedRestrictionComparator);
        }


        public void visit(OWLObjectComplementOf desc) {
            lastValue = OWL_OBJECT_COMPLEMENT_OF;
            setFineGrainedComparator(objectComplementOfComparator);
        }


        public void visit(OWLObjectExactCardinalityRestriction desc) {
            lastValue = OWL_OBJECT_RESTRICTION;
            setFineGrainedComparator(objectCardinalityRestrictionComparator);
        }


        public void visit(OWLObjectIntersectionOf desc) {
            lastValue = OWL_OBJECT_INTERSECTION_OF;
            setFineGrainedComparator(naryBooleanDescriptionComparator);
        }


        public void visit(OWLObjectMaxCardinalityRestriction desc) {
            lastValue = OWL_OBJECT_RESTRICTION;
            setFineGrainedComparator(objectCardinalityRestrictionComparator);
        }


        public void visit(OWLObjectMinCardinalityRestriction desc) {
            lastValue = OWL_OBJECT_RESTRICTION;
            setFineGrainedComparator(objectCardinalityRestrictionComparator);
        }


        public void visit(OWLObjectOneOf desc) {
            lastValue = OWL_OBJECT_ONE_OF;
            setFineGrainedComparator(objectOneOfComparator);

        }


        public void visit(OWLObjectSelfRestriction desc) {
            lastValue = OWL_OBJECT_RESTRICTION;
            setFineGrainedComparator(selfRestictionComparator);
        }


        public void visit(OWLObjectSomeRestriction desc) {
            lastValue = OWL_OBJECT_RESTRICTION;
            setFineGrainedComparator(objectQuantifiedRestrictionComparator);
        }


        public void visit(OWLObjectUnionOf desc) {
            lastValue = OWL_OBJECT_UNION_OF;
            setFineGrainedComparator(naryBooleanDescriptionComparator);
        }


        public void visit(OWLObjectValueRestriction desc) {
            lastValue = OWL_OBJECT_RESTRICTION;
            setFineGrainedComparator(objectQuantifiedRestrictionComparator);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private static abstract class AbstractOWLObjectComparator<O extends OWLObject> implements Comparator<O> {

        private ShortFormProvider shortFormProvider;

        private Comparator<OWLObject> fineGrainedComparator;


        protected AbstractOWLObjectComparator(ShortFormProvider shortFormProvider) {
            this.shortFormProvider = shortFormProvider;
        }


        public void setFineGrainedComparator(Comparator fineGrainedComparator) {
            this.fineGrainedComparator = fineGrainedComparator;
        }

        protected abstract int compareObjects(O o1, O o2);


        public int compare(O o1, O o2) {
            fineGrainedComparator = null;
            int delta = compareObjects(o1, o2);
            if(delta == 0 && fineGrainedComparator != null) {
                return fineGrainedComparator.compare(o1, o2);
            }
            else {
                return delta;
            }
        }


        protected String getShortForm(OWLEntity entity) {
            return shortFormProvider.getShortForm(entity);
        }


        public ShortFormProvider getShortFormProvider() {
            return shortFormProvider;
        }


        protected Comparator<? extends OWLObject> getFineGrainedComparator() {
            return fineGrainedComparator;
        }
    }


    private static abstract class AbstractOWLDescriptionComparator<O extends OWLDescription> extends AbstractOWLObjectComparator<O> implements OWLDescriptionVisitor {

        protected AbstractOWLDescriptionComparator(ShortFormProvider shortFormProvider) {
            super(shortFormProvider);
        }


        public void visit(OWLClass desc) {
        }


        public void visit(OWLDataAllRestriction desc) {
        }


        public void visit(OWLDataExactCardinalityRestriction desc) {
        }


        public void visit(OWLDataMaxCardinalityRestriction desc) {
        }


        public void visit(OWLDataMinCardinalityRestriction desc) {
        }


        public void visit(OWLDataSomeRestriction desc) {
        }


        public void visit(OWLDataValueRestriction desc) {
        }


        public void visit(OWLObjectAllRestriction desc) {
        }


        public void visit(OWLObjectComplementOf desc) {
        }


        public void visit(OWLObjectExactCardinalityRestriction desc) {
        }


        public void visit(OWLObjectIntersectionOf desc) {
        }


        public void visit(OWLObjectMaxCardinalityRestriction desc) {
        }


        public void visit(OWLObjectMinCardinalityRestriction desc) {
        }


        public void visit(OWLObjectOneOf desc) {
        }


        public void visit(OWLObjectSelfRestriction desc) {
        }


        public void visit(OWLObjectSomeRestriction desc) {
        }


        public void visit(OWLObjectUnionOf desc) {
        }


        public void visit(OWLObjectValueRestriction desc) {
        }
    }
}
