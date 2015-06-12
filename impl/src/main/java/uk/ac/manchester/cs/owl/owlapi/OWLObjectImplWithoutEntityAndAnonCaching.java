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
package uk.ac.manchester.cs.owl.owlapi;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLObjectImplWithoutEntityAndAnonCaching implements OWLObject,
    HasIncrementalSignatureGenerationSupport, Serializable {

    private static final long serialVersionUID = 40000L;
    /** a convenience reference for an empty annotation set, saves on typing. */
    @Nonnull
    protected static final Set<OWLAnnotation> NO_ANNOTATIONS = CollectionFactory.emptySet();
    static final OWLObjectTypeIndexProvider OWLOBJECT_TYPEINDEX_PROVIDER = new OWLObjectTypeIndexProvider();
    protected int hashCode = 0;
    @Nonnull
    protected static final OWLClass OWL_THING = new OWLClassImpl(OWLRDFVocabulary.OWL_THING.getIRI());

    @SuppressWarnings("unused")
    private static void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

    @Nonnull
    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        Set<OWLAnonymousIndividual> result = new HashSet<>();
        addAnonymousIndividualsToSet(result);
        return result;
    }

    @Nonnull
    @Override
    public Set<OWLEntity> getSignature() {
        Set<OWLEntity> result = new HashSet<>();
        addSignatureEntitiesToSet(result);
        return result;
    }

    protected static void addEntitiesFromAnnotationsToSet(Collection<OWLAnnotation> annotations,
        Set<OWLEntity> entities) {
        for (OWLAnnotation annotation : annotations) {
            if (annotation instanceof OWLAnnotationImpl) {
                OWLAnnotationImpl owlAnnotation = (OWLAnnotationImpl) annotation;
                owlAnnotation.addSignatureEntitiesToSet(entities);
            } else {
                entities.addAll(annotation.getSignature());
            }
        }
    }

    protected static void addAnonymousIndividualsFromAnnotationsToSet(Collection<OWLAnnotation> annotations,
        Set<OWLAnonymousIndividual> anons) {
        for (OWLAnnotation annotation : annotations) {
            if (annotation instanceof OWLAnnotationImpl) {
                OWLAnnotationImpl owlAnnotation = (OWLAnnotationImpl) annotation;
                owlAnnotation.addAnonymousIndividualsToSet(anons);
            } else {
                anons.addAll(annotation.getAnonymousIndividuals());
            }
        }
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity) {
        return getSignature().contains(owlEntity);
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        Set<OWLClass> result = new HashSet<>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLClass()) {
                result.add(ent.asOWLClass());
            }
        }
        return result;
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        Set<OWLDataProperty> result = new HashSet<>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLDataProperty()) {
                result.add(ent.asOWLDataProperty());
            }
        }
        return result;
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        Set<OWLObjectProperty> result = new HashSet<>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLObjectProperty()) {
                result.add(ent.asOWLObjectProperty());
            }
        }
        return result;
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        Set<OWLNamedIndividual> result = new HashSet<>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLNamedIndividual()) {
                result.add(ent.asOWLNamedIndividual());
            }
        }
        return result;
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        Set<OWLDatatype> result = new HashSet<>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLDatatype()) {
                result.add(ent.asOWLDatatype());
            }
        }
        return result;
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        Set<OWLAnnotationProperty> result = new HashSet<>();
        for (OWLEntity ent : getSignature()) {
            if (ent.isOWLAnnotationProperty()) {
                result.add(ent.asOWLAnnotationProperty());
            }
        }
        return result;
    }

    @Override
    public Set<OWLClassExpression> getNestedClassExpressions() {
        OWLClassExpressionCollector collector = new OWLClassExpressionCollector();
        return accept(collector);
    }

    protected void addSignatureEntitiesToSetForValue(Set<OWLEntity> entities, HasSignature canHasSignature) {
        if (canHasSignature instanceof HasIncrementalSignatureGenerationSupport) {
            HasIncrementalSignatureGenerationSupport hasIncrementalSignatureGenerationSupport = (HasIncrementalSignatureGenerationSupport) canHasSignature;
            hasIncrementalSignatureGenerationSupport.addSignatureEntitiesToSet(entities);
        } else {
            entities.addAll(canHasSignature.getSignature());
        }
    }

    protected void addAnonymousIndividualsToSetForValue(Set<OWLAnonymousIndividual> anons,
        HasAnonymousIndividuals canHasAnons) {
        if (canHasAnons instanceof HasIncrementalSignatureGenerationSupport) {
            HasIncrementalSignatureGenerationSupport hasIncrementalSignatureGenerationSupport = (HasIncrementalSignatureGenerationSupport) canHasAnons;
            hasIncrementalSignatureGenerationSupport.addAnonymousIndividualsToSet(anons);
        } else {
            anons.addAll(canHasAnons.getAnonymousIndividuals());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof OWLObject;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = HashCode.hashCode(this);
        }
        return hashCode;
    }

    protected abstract int index();

    @Override
    public int compareTo(OWLObject o) {
        int thisTypeIndex = index();
        int otherTypeIndex = 0;
        if (o instanceof OWLObjectImplWithoutEntityAndAnonCaching) {
            otherTypeIndex = ((OWLObjectImplWithoutEntityAndAnonCaching) o).index();
        } else {
            otherTypeIndex = OWLOBJECT_TYPEINDEX_PROVIDER.getTypeIndex(o);
        }
        int diff = thisTypeIndex - otherTypeIndex;
        if (diff != 0) {
            return diff;
        }
        // Objects are the same type
        diff = compareObjectOfSameType(o);
        if (diff != 0) {
            return diff;
        }
        if (this instanceof OWLAxiom) {
            diff = compareLists(new ArrayList<>(((OWLAxiom) this).getAnnotations()), new ArrayList<>(((OWLAxiom) o)
                .getAnnotations()));
        }
        return diff;
    }

    protected int compareAnnotations(List<OWLAnnotation> l1, List<OWLAnnotation> l2) {
        int i = 0;
        for (; i < l1.size() && i < l2.size(); i++) {
            int diff = l1.get(i).compareTo(l2.get(i));
            if (diff != 0) {
                return diff;
            }
        }
        if (i < l2.size()) {
            // l1 is shorter and a sublist of l2
            return -1;
        }
        if (i < l1.size()) {
            // l2 is shorter and a sublist of l1
            return 1;
        }
        // lists are identical
        return 0;
    }

    protected abstract int compareObjectOfSameType(@Nonnull OWLObject object);

    @Override
    @Nonnull
    public String toString() {
        return ToStringRenderer.getInstance().getRendering(this);
    }

    @Override
    public boolean isTopEntity() {
        return false;
    }

    @Override
    public boolean isBottomEntity() {
        return false;
    }

    protected static int compareSets(Collection<? extends OWLObject> set1, Collection<? extends OWLObject> set2) {
        SortedSet<? extends OWLObject> ss1;
        if (set1 instanceof SortedSet) {
            ss1 = (SortedSet<? extends OWLObject>) set1;
        } else {
            ss1 = new TreeSet<>(set1);
        }
        SortedSet<? extends OWLObject> ss2;
        if (set2 instanceof SortedSet) {
            ss2 = (SortedSet<? extends OWLObject>) set2;
        } else {
            ss2 = new TreeSet<>(set2);
        }
        int i = 0;
        Iterator<? extends OWLObject> thisIt = ss1.iterator();
        Iterator<? extends OWLObject> otherIt = ss2.iterator();
        while (i < ss1.size() && i < ss2.size()) {
            OWLObject o1 = thisIt.next();
            OWLObject o2 = otherIt.next();
            int diff = o1.compareTo(o2);
            if (diff != 0) {
                return diff;
            }
            i++;
        }
        return ss1.size() - ss2.size();
    }

    protected static int compareLists(List<? extends OWLObject> list1, List<? extends OWLObject> list2) {
        int i = 0;
        int size = list1.size() < list2.size() ? list1.size() : list2.size();
        while (i < size) {
            OWLObject o1 = list1.get(i);
            OWLObject o2 = list2.get(i);
            int diff = o1.compareTo(o2);
            if (diff != 0) {
                return diff;
            }
            i++;
        }
        return list1.size() - list2.size();
    }
}
