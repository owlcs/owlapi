package casualtest;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.semanticweb.owlapi.model.OWLDataFactory;
@SuppressWarnings("javadoc")
public class NullChecksTestBuilder {
	public void testNullValuesOnDataFactory() {
		Map<String, String> classnames=new HashMap<String, String>();
		for (Method m : OWLDataFactory.class.getDeclaredMethods()) {
			if (m.getName().startsWith("getOWL")) {
				final Type[] genericParameterTypes = m.getGenericParameterTypes();
				for (int i = 0; i < genericParameterTypes.length; i++) {
					System.out.print(
							"public void test"+m.getName()+i+"_"+genericParameterTypes.length+"(){\ntry{\nf."
					+ m.getName() + "(");
					for (int j = 0; j < genericParameterTypes.length; j++) {
						if (i == j) {
							System.out.print("null");
						} else {
							String string = genericParameterTypes[j].toString();
							final String className = string.replace("class ", "").replace("interface ","");
							string = className.replace("java.lang.", "").replace("org.semanticweb.owlapi.model.","").replace("org.semanticweb.owlapi.vocab.","").toLowerCase(Locale.ENGLISH);
							classnames.put(string, className);
							System.out.print(string);
						}
						if (j < genericParameterTypes.length - 1) {
							System.out.print(",");
						}
					}
					System.out.println(");\n}catch(IllegalArgumentException ex){return;}fail(\"Exception expected!\");}");
				}
			}
		}
		for(Map.Entry<String, String> e:classnames.entrySet()) {
			System.out.println(e.getValue() +" "+e.getKey()+" = ");
		}
	}
}
