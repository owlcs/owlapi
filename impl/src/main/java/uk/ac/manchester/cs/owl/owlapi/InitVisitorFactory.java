package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

public class InitVisitorFactory {

    @SuppressWarnings("unchecked")
    public static class InitVisitor<K extends OWLObject> extends OWLAxiomVisitorExAdapter<K> {
        private final boolean sub;
        private final boolean named;

        public InitVisitor(boolean sub, boolean named) {
            this.sub = sub;
            this.named = named;
        }

        @Override
        public K visit(OWLSubClassOfAxiom axiom) {
            OWLClassExpression c = sub ? axiom.getSubClass() : axiom.getSuperClass();
            if (named && c.isAnonymous()) {
                return null;
            }
            return (K) c;
        }

        @Override
        public K visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLDataPropertyDomainAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLObjectPropertyDomainAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLObjectPropertyRangeAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLObjectPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (sub) {
                return (K) axiom.getSubProperty();
            }
            else {
                return (K) axiom.getSuperProperty();
            }
        }

        @Override
        public K visit(OWLAnnotationAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLSymmetricObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLDataPropertyRangeAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLFunctionalDataPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLClassAssertionAxiom axiom) {
            OWLClassExpression c = axiom.getClassExpression();
            if (named && c.isAnonymous()) {
                return null;
            }
            return (K) c;
        }

        @Override
        public K visit(OWLDataPropertyAssertionAxiom axiom) {
            return (K) axiom.getSubject();
        }

        @Override
        public K visit(OWLTransitiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLSubDataPropertyOfAxiom axiom) {
            if (sub) {
                return (K) axiom.getSubProperty();
            }
            else {
                return (K) axiom.getSuperProperty();
            }
        }

        @Override
        public K visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return (K) axiom.getProperty();
        }

        @Override
        public K visit(OWLHasKeyAxiom axiom) {
            if (named && axiom.getClassExpression().isAnonymous()) {
                return null;
            }
            return (K) axiom.getClassExpression().asOWLClass();
        }
    }

    @SuppressWarnings("unchecked")
    public static class InitIndividualVisitor<K extends OWLObject> extends InitVisitor<K> {
        public InitIndividualVisitor(boolean sub, boolean named) {
            super(sub, named);
        }

        @Override
        public K visit(OWLClassAssertionAxiom axiom) {
            return (K) axiom.getIndividual();
        }
    }

    @SuppressWarnings("unchecked")
    public static class InitCollectionVisitor<K extends OWLObject> extends OWLAxiomVisitorExAdapter<Collection<K>> {
        private final boolean named;

        public InitCollectionVisitor(boolean named) {
            this.named = named;
        }

        @Override
        public Collection<K> visit(OWLDisjointClassesAxiom axiom) {
            List<OWLClassExpression> list = new ArrayList<OWLClassExpression>(axiom.getClassExpressions());
            if (named) {
                deleteAnonymousClasses(list);
            }
            return (Collection<K>) list;
        }

        private void deleteAnonymousClasses(List<OWLClassExpression> list) {
            for (int i = 0; i < list.size();) {
                if (list.get(i).isAnonymous()) {
                    list.remove(i);
                }
                else {
                    i++;
                }
            }
        }

        @Override
        public Collection<K> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }

        @Override
        public Collection<K> visit(OWLDifferentIndividualsAxiom axiom) {
            return (Collection<K>) axiom.getIndividuals();
        }

        @Override
        public Collection<K> visit(OWLDisjointDataPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }

        @Override
        public Collection<K> visit(OWLDisjointObjectPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }

        @Override
        public Collection<K> visit(OWLDisjointUnionAxiom axiom) {
            List<OWLClassExpression> list = new ArrayList<OWLClassExpression>(axiom.getClassExpressions());
            if (named) {
                deleteAnonymousClasses(list);
            }
            return (Collection<K>) list;
        }

        @Override
        public Collection<K> visit(OWLEquivalentDataPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }

        @Override
        public Collection<K> visit(OWLEquivalentClassesAxiom axiom) {
            List<OWLClassExpression> list = new ArrayList<OWLClassExpression>(axiom.getClassExpressions());
            if (named) {
                deleteAnonymousClasses(list);
            }
            return (Collection<K>) list;
        }

        @Override
        public Collection<K> visit(OWLSameIndividualAxiom axiom) {
            return (Collection<K>) axiom.getIndividuals();
        }

        @Override
        public Collection<K> visit(OWLInverseObjectPropertiesAxiom axiom) {
            return (Collection<K>) axiom.getProperties();
        }
    }

    @SuppressWarnings("unused")
    abstract static class OWLAxiomVisitorExAdapter<K> implements OWLAxiomVisitorEx<K> {
        public K visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            return null;
        }

        public K visit(OWLAnnotationPropertyDomainAxiom axiom) {
            return null;
        }

        public K visit(OWLAnnotationPropertyRangeAxiom axiom) {
            return null;
        }

        public K visit(OWLSubClassOfAxiom axiom) {
            return null;
        }

        public K visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return null;
        }

        public K visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            return null;
        }

        public K visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return null;
        }

        public K visit(OWLDisjointClassesAxiom axiom) {
            return null;
        }

        public K visit(OWLDataPropertyDomainAxiom axiom) {
            return null;
        }

        public K visit(OWLObjectPropertyDomainAxiom axiom) {
            return null;
        }

        public K visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            return null;
        }

        public K visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return null;
        }

        public K visit(OWLDifferentIndividualsAxiom axiom) {
            return null;
        }

        public K visit(OWLDisjointDataPropertiesAxiom axiom) {
            return null;
        }

        public K visit(OWLDisjointObjectPropertiesAxiom axiom) {
            return null;
        }

        public K visit(OWLObjectPropertyRangeAxiom axiom) {
            return null;
        }

        public K visit(OWLObjectPropertyAssertionAxiom axiom) {
            return null;
        }

        public K visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return null;
        }

        public K visit(OWLSubObjectPropertyOfAxiom axiom) {
            return null;
        }

        public K visit(OWLDisjointUnionAxiom axiom) {
            return null;
        }

        public K visit(OWLDeclarationAxiom axiom) {
            return null;
        }

        public K visit(OWLAnnotationAssertionAxiom axiom) {
            return null;
        }

        public K visit(OWLSymmetricObjectPropertyAxiom axiom) {
            return null;
        }

        public K visit(OWLDataPropertyRangeAxiom axiom) {
            return null;
        }

        public K visit(OWLFunctionalDataPropertyAxiom axiom) {
            return null;
        }

        public K visit(OWLEquivalentDataPropertiesAxiom axiom) {
            return null;
        }

        public K visit(OWLClassAssertionAxiom axiom) {
            return null;
        }

        public K visit(OWLEquivalentClassesAxiom axiom) {
            return null;
        }

        public K visit(OWLDataPropertyAssertionAxiom axiom) {
            return null;
        }

        public K visit(OWLTransitiveObjectPropertyAxiom axiom) {
            return null;
        }

        public K visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return null;
        }

        public K visit(OWLSubDataPropertyOfAxiom axiom) {
            return null;
        }

        public K visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return null;
        }

        public K visit(OWLSameIndividualAxiom axiom) {
            return null;
        }

        public K visit(OWLSubPropertyChainOfAxiom axiom) {
            return null;
        }

        public K visit(OWLInverseObjectPropertiesAxiom axiom) {
            return null;
        }

        public K visit(OWLHasKeyAxiom axiom) {
            return null;
        }

        public K visit(OWLDatatypeDefinitionAxiom axiom) {
            return null;
        }

        public K visit(SWRLRule rule) {
            return null;
        }
    }
}
