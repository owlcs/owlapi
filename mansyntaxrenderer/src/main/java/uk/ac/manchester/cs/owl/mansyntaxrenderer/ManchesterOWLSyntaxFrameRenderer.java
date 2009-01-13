package uk.ac.manchester.cs.owl.mansyntaxrenderer;

import org.coode.manchesterowlsyntax.ManchesterOWLSyntax;
import static org.coode.manchesterowlsyntax.ManchesterOWLSyntax.*;
import org.coode.xml.OWLOntologyNamespaceManager;
import org.semanticweb.owl.io.OWLRendererException;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.CollectionFactory;
import org.semanticweb.owl.util.OWLEntityCollector;
import org.semanticweb.owl.util.ShortFormProvider;

import java.io.Writer;
import java.net.URI;
import java.util.*;
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
 */
public class ManchesterOWLSyntaxFrameRenderer extends ManchesterOWLSyntaxObjectRenderer implements OWLEntityVisitor {

    private OWLOntologyNamespaceManager nsm;

    private OWLOntology ontology;


    public ManchesterOWLSyntaxFrameRenderer(OWLOntologyManager owlOntologyManager, OWLOntology ontology,
                                            Writer writer) {
        super(writer);
        this.ontology = ontology;
        nsm = new OWLOntologyNamespaceManager(owlOntologyManager, ontology);
        setShortFormProvider(new ShortFormProvider() {
            public String getShortForm(OWLEntity entity) {
                return nsm.getQName(entity.getURI().toString());
            }


            public void dispose() {
            }
        });
    }


    private OWLOntology getOntology() {
        return this.ontology;
    }


    public OWLOntologyNamespaceManager getNamespaceManager() {
        return nsm;
    }


    public void writeOntology() throws OWLRendererException {
        writePrefixMap();
        writeNewLine();
        write(ONTOLOGY.toString());
        write(":");
        writeSpace();
        writeFullURI(getOntology().getURI().toString());
        writeNewLine();
        for (OWLImportsDeclaration decl : getOntology().getImportsDeclarations()) {
            write(IMPORT.toString());
            write(":");
            writeSpace();
            writeFullURI(decl.getImportedOntologyURI().toString());
            writeNewLine();
        }
        writeNewLine();
        Set<OWLAnnotation> annos = new HashSet<OWLAnnotation>();
        for(OWLOntologyAnnotationAxiom anno : getOntology().getAnnotations(getOntology())) {
            annos.add(anno.getAnnotation());
        }
        writeSection(ANNOTATIONS, annos, ",", true);

        for (OWLObjectProperty prop : getOntology().getReferencedObjectProperties()) {
            write(prop);
            writeNewLine();
            writeNewLine();
        }
        for (OWLDataProperty prop : getOntology().getReferencedDataProperties()) {
            write(prop);
            writeNewLine();
            writeNewLine();
        }
        for (OWLClass cls : getOntology().getReferencedClasses()) {
            write(cls);
            writeNewLine();
            writeNewLine();
        }
        for (OWLIndividual ind : getOntology().getReferencedIndividuals()) {
            write(ind);
            writeNewLine();
            writeNewLine();
        }
        // Nary disjoint classes axioms
        for(OWLDisjointClassesAxiom ax : getOntology().getAxioms(AxiomType.DISJOINT_CLASSES)) {
            if(ax.getDescriptions().size() > 2) {
                writeSection(DISJOINT_CLASSES, ax.getDescriptions(), ",", true);
            }
        }
        flush();
    }


    public void writePrefixMap() {
        Map<String, String> prefixMap = new HashMap<String, String>();
        for (String prefix : nsm.getPrefixes()) {
            prefixMap.put(prefix, nsm.getNamespaceForPrefix(prefix));
            write(NAMESPACE.toString());
            write(":");
            writeSpace();
            write(prefix);
            writeSpace();
            writeFullURI(nsm.getNamespaceForPrefix(prefix));
            writeNewLine();
        }
        if (!prefixMap.isEmpty()) {
            writeNewLine();
            writeNewLine();
        }
    }


    public void writeFullURI(String uri) {
        write("<");
        write(uri);
        write(">");
    }


    protected void write(URI uri) {
        String qname = nsm.getQName(uri.toString());
        if (qname != null) {
            super.write(qname);
        }
        else {
            writeFullURI(uri.toString());
        }
    }


    public Set<OWLAxiom> write(OWLClass cls) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(CLASS, cls));
        writeSection(EQUIVALENT_TO, cls.getEquivalentClasses(getOntology()), ",", true);
        axioms.addAll(getOntology().getEquivalentClassesAxioms(cls));
        Set<OWLDescription> superclasses = cls.getSuperClasses(getOntology());
        if (!superclasses.isEmpty()) {
            writeSection(SUBCLASS_OF, superclasses, ",", true);
            axioms.addAll(getOntology().getSubClassAxiomsForLHS(cls));
        }
        Set<OWLAxiom> pairwiseDisjointClassesAxioms = new HashSet<OWLAxiom>();
        Set<OWLDescription> disjointClasses = new HashSet<OWLDescription>();
        for(OWLDisjointClassesAxiom ax : getOntology().getDisjointClassesAxioms(cls)) {
            if (ax.getDescriptions().size() <= 2) {
                pairwiseDisjointClassesAxioms.add(ax);
                disjointClasses.addAll(ax.getDescriptions());
            }
        }
        disjointClasses.remove(cls);
        axioms.addAll(pairwiseDisjointClassesAxioms);
        writeSection(DISJOINT_WITH, disjointClasses, ",", true);
        writeEntitySectionEnd();
        return axioms;
    }


    private void writeEntitySectionEnd() {
        popTab();
        writeNewLine();
        writeNewLine();
    }


    public Set<OWLAxiom> write(OWLObjectProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(OBJECT_PROPERTY, property));
        List<String> characteristics = new ArrayList<String>();
        if (property.isFunctional(getOntology())) {
            characteristics.add(FUNCTIONAL.toString());
            axioms.add(getOntology().getFunctionalObjectPropertyAxiom(property));
        }
        if (property.isInverseFunctional(getOntology())) {
            characteristics.add(INVERSE_FUNCTIONAL.toString());
            axioms.add(getOntology().getInverseFunctionalObjectPropertyAxiom(property));
        }
        if (property.isSymmetric(getOntology())) {
            characteristics.add(SYMMETRIC.toString());
            axioms.add(getOntology().getSymmetricObjectPropertyAxiom(property));
        }
        if (property.isTransitive(getOntology())) {
            characteristics.add(TRANSITIVE.toString());
            axioms.add(getOntology().getTransitiveObjectPropertyAxiom(property));
        }
        if (property.isReflexive(getOntology())) {
            characteristics.add(REFLEXIVE.toString());
            axioms.add(getOntology().getReflexiveObjectPropertyAxiom(property));
        }
        if (property.isIrreflexive(getOntology())) {
            characteristics.add(IRREFLEXIVE.toString());
            axioms.add(getOntology().getIrreflexiveObjectPropertyAxiom(property));
        }
        if (property.isAntiSymmetric(getOntology())) {
            characteristics.add(ANTI_SYMMETRIC.toString());
            axioms.add(getOntology().getAntiSymmetricObjectPropertyAxiom(property));
        }
        writeSection(CHARACTERISTICS, new LinkedHashSet(characteristics), ",", true);
        writeSection(DOMAIN, property.getDomains(getOntology()), ",", true);
        axioms.addAll(getOntology().getObjectPropertyDomainAxioms(property));
        writeSection(RANGE, property.getRanges(getOntology()), ",", true);
        axioms.addAll(getOntology().getObjectPropertyRangeAxioms(property));
        writeSection(INVERSE_OF, property.getInverses(getOntology()), ",", true);
        axioms.addAll(getOntology().getInverseObjectPropertyAxioms(property));
        writeSection(SUB_PROPERTY_OF, property.getSuperProperties(getOntology()), ",", true);
        axioms.addAll(getOntology().getObjectSubPropertyAxiomsForLHS(property));
        writeSection(EQUIVALENT_TO, property.getEquivalentProperties(getOntology()), ",", true);
        axioms.addAll(getOntology().getEquivalentObjectPropertiesAxioms(property));
        writeSection(DISJOINT_WITH, property.getDisjointProperties(getOntology()), ",", true);
        axioms.addAll(getOntology().getDisjointObjectPropertiesAxiom(property));
        for(OWLObjectPropertyChainSubPropertyAxiom ax : getOntology().getPropertyChainSubPropertyAxioms()) {
            if(ax.getSuperProperty().equals(property)) {
                writeSection(SUB_PROPERTY_CHAIN, new LinkedHashSet<OWLObjectPropertyExpression>(ax.getPropertyChain()), " o ", false);
                axioms.add(ax);
            }
        }
        writeEntitySectionEnd();
        return axioms;
    }


    public Set<OWLAxiom> write(OWLDataProperty property) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(DATA_PROPERTY, property));
        List<String> characteristics = new ArrayList<String>();
        if (property.isFunctional(getOntology())) {
            characteristics.add(FUNCTIONAL.toString());
            axioms.add(getOntology().getFunctionalDataPropertyAxiom(property));
        }
        writeSection(CHARACTERISTICS, new LinkedHashSet(characteristics), ",", true);
        writeSection(DOMAIN, property.getDomains(getOntology()), ",", true);
        axioms.addAll(getOntology().getDataPropertyDomainAxioms(property));
        writeSection(RANGE, property.getRanges(getOntology()), ",", true);
        axioms.addAll(getOntology().getDataPropertyRangeAxiom(property));
        writeSection(SUB_PROPERTY_OF, property.getSuperProperties(getOntology()), ",", true);
        axioms.addAll(getOntology().getDataSubPropertyAxiomsForLHS(property));
        writeSection(EQUIVALENT_TO, property.getEquivalentProperties(getOntology()), ",", true);
        axioms.addAll(getOntology().getEquivalentDataPropertiesAxiom(property));
        writeSection(DISJOINT_WITH, property.getDisjointProperties(getOntology()), ",", true);
        axioms.addAll(getOntology().getDisjointDataPropertiesAxiom(property));
        writeEntitySectionEnd();
        return axioms;
    }


    public Set<OWLAxiom> write(OWLIndividual individual) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.addAll(writeEntityStart(INDIVIDUAL, individual));
        writeSection(TYPES, individual.getTypes(getOntology()), ",", true);
        axioms.addAll(getOntology().getClassAssertionAxioms(individual));
        // Facts - messy!
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectMap = individual.getObjectPropertyValues(getOntology());
        Map<OWLDataPropertyExpression, Set<OWLConstant>> dataMap = individual.getDataPropertyValues(getOntology());
        // Negative facts
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> negObjectMap = individual.getNegativeObjectPropertyValues(
                getOntology());
        Map<OWLDataPropertyExpression, Set<OWLConstant>> negDataMap = individual.getNegativeDataPropertyValues(
                getOntology());

        if (!objectMap.isEmpty() || !dataMap.isEmpty() || !negObjectMap.isEmpty() || !negDataMap.isEmpty()) {
            writeSection(FACTS);
            incrementTab(4);
            writeNewLine();
            writeFacts(objectMap, false);
            if (!objectMap.isEmpty() && (!dataMap.isEmpty() || !negObjectMap.isEmpty() || !negDataMap.isEmpty())) {
                write(",");
                writeNewLine();
            }
            writeFacts(dataMap, false);
            if (!dataMap.isEmpty() && (!negObjectMap.isEmpty() || !negDataMap.isEmpty())) {
                write(",");
                writeNewLine();
            }
            writeFacts(negObjectMap, true);
            if (!negDataMap.isEmpty() && !negObjectMap.isEmpty()) {
                write(",");
                writeNewLine();
            }
            writeFacts(negDataMap, true);
            popTab();
            writeNewLine();
            writeNewLine();
        }
        axioms.addAll(getOntology().getNegativeDataPropertyAssertionAxioms(individual));
        axioms.addAll(getOntology().getNegativeObjectPropertyAssertionAxioms(individual));
        axioms.addAll(getOntology().getObjectPropertyAssertionAxioms(individual));
        axioms.addAll(getOntology().getDataPropertyAssertionAxioms(individual));
        writeSection(SAME_AS, individual.getSameIndividuals(getOntology()), ",", true);
        axioms.addAll(getOntology().getSameIndividualAxioms(individual));
        writeSection(DIFFERENT_FROM, individual.getDifferentIndividuals(getOntology()), ",", true);
        axioms.addAll(getOntology().getDifferentIndividualAxioms(individual));
        writeEntitySectionEnd();
        return axioms;
    }


    private <K extends OWLObject, V extends OWLObject> void writeFacts(Map<K, Set<V>> relationshipsMap,
                                                                       boolean negative) {
        for (Iterator<K> propIt = relationshipsMap.keySet().iterator(); propIt.hasNext();) {
            K prop = propIt.next();
            for (Iterator<? extends OWLObject> it = relationshipsMap.get(prop).iterator(); it.hasNext();) {
                if (negative) {
                    write(NOT);
                    writeSpace();
                }
                prop.accept(this);
                writeSpace();
                writeSpace();
                OWLObject ind = it.next();
                ind.accept(this);
                if (it.hasNext()) {
                    write(",");
                    writeNewLine();
                }
            }
            if (propIt.hasNext()) {
                write(",");
                writeNewLine();
            }
        }
    }


    private Set<OWLEntityAnnotationAxiom> writeEntityStart(ManchesterOWLSyntax keyword, OWLEntity entity) {
        writeSection(keyword);
        entity.accept(this);
        writeNewLine();
        incrementTab(4);
        writeNewLine();
        return writeAnnotations(entity);
    }


    public Set<OWLEntityAnnotationAxiom> writeAnnotations(OWLEntity entity) {
        writeSection(ANNOTATIONS, entity.getAnnotations(getOntology()), ",", true);
        return getOntology().getEntityAnnotationAxioms(entity);
    }


    public void writeSection(ManchesterOWLSyntax keyword) {
        write("", keyword, "");
        write(":");
        writeSpace();
    }


    public void writeSection(ManchesterOWLSyntax keyword, Set<? extends Object> content, String delimeter,
                             boolean newline) {

        if (!content.isEmpty()) {
            writeSection(keyword);
            incrementTab(4);
            writeNewLine();
            for (Iterator<? extends Object> it = content.iterator(); it.hasNext();) {
                Object obj = it.next();
                if (obj instanceof OWLObject) {
                    ((OWLObject) obj).accept(this);
                }
                else {
                    write(obj.toString());
                }
                if (it.hasNext()) {
                    write(delimeter);
                    if (newline) {
                        writeNewLine();
                    }
                }
            }
            popTab();
            writeNewLine();
            writeNewLine();
        }
    }


    private class EntityDependency implements Comparator<OWLEntity> {


        private OWLOntology ont;

        private Set<Set<OWLEntity>> cyclicDependencies;

        public EntityDependency(OWLOntology ont) {
            this.ont = ont;
            cyclicDependencies = new HashSet<Set<OWLEntity>>();
        }


        public Set<Set<OWLEntity>> getCyclicDependencies() {
            return cyclicDependencies;
        }


        public int compare(OWLEntity o1, OWLEntity o2) {
            Set<? extends OWLAxiom> axioms1 = Collections.emptySet();
            if(o1.isOWLClass()) {
                axioms1 = ont.getAxioms(o1.asOWLClass());
            }
            else if(o1.isOWLObjectProperty()) {
                axioms1 = ont.getAxioms(o1.asOWLObjectProperty());
            }
            else if(o1.isOWLDataProperty()) {
                axioms1 = ont.getAxioms(o1.asOWLDataProperty());
            }
            else if(o1.isOWLIndividual()) {
                axioms1 = ont.getAxioms(o1.asOWLIndividual());
            }
            OWLEntityCollector collector1 = new OWLEntityCollector();
            for (OWLAxiom ax1 : axioms1) {
                ax1.accept(collector1);
            }
            Set<OWLEntity> dependents1 = collector1.getObjects();
            dependents1.remove(o1);

            Set<? extends OWLAxiom> axioms2 = Collections.emptySet();
            if(o1.isOWLClass()) {
                axioms2 = ont.getAxioms(o2.asOWLClass());
            }
            else if(o1.isOWLObjectProperty()) {
                axioms2 = ont.getAxioms(o2.asOWLObjectProperty());
            }
            else if(o1.isOWLDataProperty()) {
                axioms2 = ont.getAxioms(o2.asOWLDataProperty());
            }
            else if(o1.isOWLIndividual()) {
                axioms2 = ont.getAxioms(o2.asOWLIndividual());
            }
            OWLEntityCollector collector2 = new OWLEntityCollector();
            for (OWLAxiom ax2 : axioms2) {
                ax2.accept(collector2);
            }
            Set<OWLEntity> dependents2 = collector2.getObjects();
            dependents2.remove(o2);
            if(dependents1.contains(o2)) {
                if(dependents2.contains(o1)) {
                    // Cyclic dependency!
                    cyclicDependencies.add(CollectionFactory.createSet(o1, o2));
                    return 0;
                }
                // o1 is dependent on o2
                return -1;
            }
            else if(dependents2.contains(o1)) {
                // o2 is dependent on o1
                return 1;
            }
            // Not comparable - order doesn't matter
            return -1;
        }
    }
}
