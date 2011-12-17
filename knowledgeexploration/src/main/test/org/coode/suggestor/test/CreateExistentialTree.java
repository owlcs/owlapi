/*
 * Date: Dec 17, 2007
 *
 * code made available under Mozilla Public License (http://www.mozilla.org/MPL/MPL-1.1.html)
 *
 * copyright 2007, The University of Manchester
 *
 * Author: Nick Drummond
 * http://www.cs.man.ac.uk/~drummond/
 * Bio Health Informatics Group
 * The University Of Manchester
 */
package org.coode.suggestor.test;

import org.coode.suggestor.api.FillerSuggestor;
import org.coode.suggestor.api.PropertySuggestor;
import org.coode.suggestor.impl.SuggestorFactory;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import java.util.*;

public class CreateExistentialTree extends AbstractSuggestorTest {

    private final Set<Node<OWLClass>> visited = new HashSet<Node<OWLClass>>();

    @Override
    protected OWLOntology createOntology() throws OWLOntologyCreationException {
        return mngr.loadOntologyFromOntologyDocument(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));
    }

    public void testCreateTree() throws Exception{

        OWLOntology ont = createOntology();


        OWLReasoner r = ((OWLReasonerFactory) Class.forName(DEFAULT_REASONER_FACTORY).newInstance()).createNonBufferingReasoner(ont);


        SuggestorFactory fac = new SuggestorFactory(r);
       PropertySuggestor ps = fac.getPropertySuggestor();
       FillerSuggestor fs = fac.getFillerSuggestor();

        long start = System.currentTimeMillis();

        for (int i=0; i<20; i++){
            visited.clear();
            printClass(r.getTopClassNode(), 0,ps,r,fs);
        }

        long end = System.currentTimeMillis();
        System.out.println("Complete in " + (end - start) + "ms");
    }

    private void printClass(Node<OWLClass> cNode, int indent, PropertySuggestor ps, OWLReasoner r, FillerSuggestor fs) {
        print(cNode, indent);
        if (visited.add(cNode)){
            final OWLClassExpression c = cNode.getRepresentativeElement();
            for (Node<OWLObjectPropertyExpression> p : ps.getCurrentObjectProperties(c, true)){
                printProperty(c, p, indent+3, fs);
            }
            for (Node<OWLClass> sub : r.getSubClasses(c, true)){
                if (!sub.isBottomNode()){
                    printClass(sub, indent+1,ps, r,fs);
                }
            }
        }
    }

    private void printProperty(OWLClassExpression c, Node<OWLObjectPropertyExpression> p, int indent, FillerSuggestor fs) {
        print(p, indent);
        for (Node<OWLClass> f : fs.getCurrentNamedFillers(c, p.getRepresentativeElement(), true)){
            print(f, indent+1);
        }
    }

    private void print(Node<? extends OWLObject> node, int indent) {
//        System.out.println();
//        for (int i=0; i<indent; i++){
//            System.out.print("    ");
//        }
//        boolean started = false;
//        final Set<OWLObject> entities = new TreeSet<OWLObject>(node.getEntities());
//        for (OWLObject o : entities){
//            if (started){
//                System.out.print(" == ");
//            }
//            else{
//                started = true;
//            }
//            if (o instanceof OWLEntity){
//                System.out.print(((OWLEntity)o).getIRI().getFragment());
//            }
//            else{
//                System.out.print(o);
//            }
//        }
    }
}
