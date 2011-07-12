package uk.ac.manchester.owl.owlapi.tutorialowled2011;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {
	public static void main(String[] args) throws Exception {
		System.out.println("Formatter.main() "+(Long.MAX_VALUE/(1000*86000*365)));
		
		
		Map<String, String> specials = new HashMap<String, String>();
		specials.put("public void test", "\\begin{beamerboxesrounded}{");
		specials.put("() throws Exception \\{", "}\n\\scriptsize");
		String[] keywords = new String[] { " class ", " void ", " extends ",
				"public", " static final", "return", "throws" };
		Pattern stringPattern = Pattern.compile("(\"[\\w\\.\\:\\s\\#/\\-]*\")");
		BufferedReader r = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(
								"../OWLAPI3/tutorial2011/uk/ac/manchester/owl/owlapi/tutorialowled2011/TutorialSnippets.java")));
		String line = r.readLine();
		while (line != null) {
			if (line.trim().length() == 0) {
				System.out.println("\\end{beamerboxesrounded}\n\n");
			} else {
				line = line.replace("{", "\\{").replace("}", "\\}")
						.replace("_", "\\_");
				if (line.trim().startsWith("//")) {
					System.out
							.println("\\codecomment{" + line.trim() + "}\\\\");
				} else {
					//regular code
					for(String s:specials.keySet()) {
						line=line.replace(s, specials.get(s));
					}
					for (String s : keywords) {
						line = line.replace(s, "\\codekeyword{" + s + "}");
					}
					line=line.replace("\t", "\\hspace{4mm}").replace("    ", "\\hspace{4mm}").replace("\\hspace{4mm}\\hspace{4mm}", "\\hspace{4mm}");
					Matcher match = stringPattern.matcher(line);
					List<String> strings = new ArrayList<String>();
					while (match.find()) {
						strings.add(match.group(1));
					}
					for (String s : strings) {
						line = line.replace(s, "\\codestring{" + s + "}");
					}
					if(!line.contains("beamerboxesrounded")) {
					System.out.print("\\coderegular{");
					System.out.print(line.trim());
					System.out.println("}\\\\");}else {
						System.out
								.println(line.trim());
					}
				}
			}
			line = r.readLine();
		}
	}
}
