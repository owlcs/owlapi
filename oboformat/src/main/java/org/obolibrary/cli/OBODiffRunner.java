package org.obolibrary.cli;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.obolibrary.cli.OBORunnerConfiguration.ExpandMacrosModeOptions;
import org.obolibrary.macro.MacroExpansionGCIVisitor;
import org.obolibrary.macro.MacroExpansionVisitor;
import org.obolibrary.obo2owl.Obo2Owl;
import org.obolibrary.obo2owl.Owl2Obo;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * command line access to obo2owl
 */
public class OBODiffRunner {

    private static Logger logger = Logger.getLogger(OBODiffRunner.class.getName());


	public static void main(String[] args) throws Exception {

		OBORunnerConfiguration config = OBORunnerConfigCLIReader.readConfig(args);
		if (config.showHelp.getValue()) {
			usage();
			System.exit(0);
		}


		String buildDir = config.buildDir.getValue();
		if (config.ontsToDownload.getValue().size() > 0 && buildDir == null) {
            logger.log(Level.SEVERE, "must specify dir with -b DIR");
			System.exit(1);
		}

		if (config.outFile.isEmpty() && config.outputdir.isEmpty()) {
            logger.log(Level.SEVERE,
                    "must specify at least one fo the following: outFile OR outputdir");
			usage();
			System.exit(1);
		}

		// If the -b option is set, then build ALL ontologies in the specified directory
		if (buildDir != null) {
            buildAllOboOwlFiles(buildDir, config, logger, null);
		}
        // XXX a manager needs to be injected
        runConversion(config, logger, null);
	}

    protected static void runConversion(OBORunnerConfiguration config, Logger logger,
            OWLOntologyManager manager) throws Exception {

		String outFile = config.outFile.getValue();
		List<OBODoc> obodocs = new Vector<OBODoc>();
		for (String iri : config.paths.getValue()) {
			iri = getURI(iri);
			if (config.isOboToOwl.getValue()) {
				//showMemory();
				OBOFormatParser p = new OBOFormatParser();
				p.setFollowImports(config.followImports.getValue());
				OBODoc obodoc = p.parseURL(iri);
				obodocs.add(obodoc);
			}
			else {
				OWLOntology ontology = manager.loadOntologyFromOntologyDocument(IRI.create(iri));

				String version = config.version.getValue();
				if(version != null){
					addVersion(ontology, version, manager);
				}

                Owl2Obo bridge = new Owl2Obo(manager);
				bridge.setStrictConversion(config.strictConversion.getValue());
				OBODoc doc = bridge.convert(ontology);
				obodocs.add(doc);
			}

		}
		if (obodocs.size() > 2) {
			System.err.println("Max 2 docs!");
		}
		OBODocDiffer dd = new OBODocDiffer();
		OBODoc obodoc1 = obodocs.get(0);
		OBODoc obodoc2 = obodocs.get(1);
		List<Diff> diffs = dd.getDiffs(obodoc1 , obodoc2);
		for (Diff diff : diffs) {
			System.out.println("MDiff="+diff);
		}


	}

	private static OWLOntology handleMacroExpansion(OBORunnerConfiguration config, OWLOntology ontology, 
 String gciFile, String outputFile, String ontologyId,
            OWLOntologyManager outputManager) throws OWLOntologyStorageException {

		if (!config.isExpandMacros.getValue()) {
			return ontology;
		}

		ExpandMacrosModeOptions option = config.expandMacrosMode.getValue();
		if (option == ExpandMacrosModeOptions.GCI) {
			// create GCI ontology
            MacroExpansionGCIVisitor mevGCI = new MacroExpansionGCIVisitor(ontology,
                    outputManager);
			OWLOntology gciOntology = mevGCI.createGCIOntology();

			if (gciOntology.isEmpty()) {
				// no macros expanded, do nothing
				return ontology;
			}

			// add import
			OWLOntologyManager manager = gciOntology.getOWLOntologyManager();
			OWLImportsDeclaration importDeclaration = manager.getOWLDataFactory()
			.getOWLImportsDeclaration(IRI.create(new File(outputFile).getName()));
			OWLOntologyChange change = new AddImport(gciOntology, importDeclaration);
			manager.applyChange(change);




			// create output file name
			File file = new File(gciFile);
			if (gciFile.equals(outputFile)) {
				String name = file.getName();
				String lowerCaseName = name.toLowerCase();
				int pos = lowerCaseName.lastIndexOf(".owl");
				if (pos > 0) {
					name = name.substring(0, pos) + "-aux" + name.substring(pos);
				} else {
					name += "-aux.owl";
				}
				gciFile = new File(file.getParentFile(), name).getAbsolutePath();
			}

			// set ontology ID
			OWLOntologyID id = new OWLOntologyID(IRI.create(ontologyId+"-aux"));
			change = new SetOntologyID(gciOntology, id);
			manager.applyChange(change);

			// write to file
			IRI gciIRI = IRI.create(new File(gciFile));
			OWLOntologyFormat format = config.format.getValue();
			logger.info("saving gci for " + ontologyId + " to " + gciIRI + " via " + format);
			manager.saveOntology(gciOntology, format, gciIRI);


		}
		else {
			MacroExpansionVisitor mev = 
				new MacroExpansionVisitor(ontology);
			ontology = mev.expandAll();	
		}
		return ontology;
	}

	private static void usage() {
		System.out.println("obolib-obo2owl [--to SYNTAX, --allowdangling --followimports --strictconversion] -o FILEPATH-URI OBO-FILE");
		System.out.println("obolib-obo2owl -b BUILDPATH-URI");
		System.out.println("\n");
		System.out.println("Converts obo files to OWL. If -b option is used, entire\n");
		System.out.println("obo repository is converted\n");
		System.out.println("\n");
		System.out.println("Example:\n");
		System.out.println(" obolib-obo2owl -o file://`pwd`/my.owl my.obo\n");
		System.out.println("Example:\n");
		System.out.println(" obolib-obo2owl -b file://`pwd`\n");
		System.out.println("Example:\n");
		System.out.println(" obolib-obo2owl -b file://`pwd` --download FBBT\n");

	}

	private static String getURI(String path){
		if(path.startsWith("http://") || path.startsWith("file:///")) {
            return  path;
        }

		File f = new File(path);

		return f.toURI().toString();

	}


	public static void showMemory() {
		System.gc();
		System.gc();
		System.gc();
		long tm = Runtime.getRuntime().totalMemory();
		long fm = Runtime.getRuntime().freeMemory();
		long mem = tm-fm;
		System.out.println("Memory total:"+tm+" free:"+fm+" diff:"+mem+" (bytes) diff:"+mem/1000000+" (mb)");
	}


	/**
	 * makes OWL from all selected ontologies.
	 * These are downloaded from the OBO metadata file
	 * 
	 * @param dir
	 * @param config
	 * @param logger 
	 * @throws IOException
	 */
    protected static void buildAllOboOwlFiles(String dir, OBORunnerConfiguration config,
            Logger logger, OWLOntologyManager manager) throws IOException {
		Map<String, String> ontmap = getOntDownloadMap();
		Vector<String> fails = new Vector<String>();
		Set<String> ontsToDownload = config.ontsToDownload.getValue();
		Set<String> omitOntsToDownload = config.omitOntsToDownload.getValue();

		for (String ont : ontmap.keySet()) {
			if (ontsToDownload.size() > 0 && !ontsToDownload.contains(ont)) {
                continue;
            }
			if (omitOntsToDownload.size() > 0 && omitOntsToDownload.contains(ont)) {
                continue;
            }
			if (ontmap.containsKey(ont)) {
				//if (ontmap.get("format"))
				try {
					String url = ontmap.get(ont);
					long initTime = System.nanoTime();
					String ontId = ont.toLowerCase();
					logger.info("converting: "+ont+" from: "+url+" using default ont:"+ontId);
					if (url == null) {
                        logger.log(Level.WARNING, "no url for " + ont);
						fails.add(ont);
						continue;
					}
                    Obo2Owl.convertURL(url, getURI(dir + "/" + ontId + ".owl"), ontId,
                            manager);
					long totalTime = System.nanoTime() - initTime;
					showMemory(); // useless

					logger.info("TIME_TO_CONVERT "+ont+" "+
							+ (totalTime / 1000000d) + " ms");

				}catch (Error e) {
                    logger.log(Level.WARNING, e.getMessage(), e);
					fails.add(ont);
				}catch (Exception e) {
                    logger.log(Level.WARNING, e.getMessage(), e);
					fails.add(ont);
				}
			}
			else {
				fails.add(ont);
                logger.log(Level.WARNING, "did not convert: " + ont);
			}
		}
		logger.info("DONE!");
		for (String fail : fails) {
            logger.log(Level.WARNING, "FAIL:" + fail);
        }
	}

	/**
	 * 
	 * find download URLs using obo foundry metadata file
	 * 
	 * @return mapping from ID-space (e.g. GO) to source download URL
	 * @throws IOException
	 */
	private static Map<String,String> getOntDownloadMap() throws IOException {
		return getOntDownloadMap(new URL("http://obo.cvs.sourceforge.net/viewvc/*checkout*/obo/obo/website/cgi-bin/ontologies.txt"));
	}

	private static Map<String,String> getOntDownloadMap(String fn) throws IOException {
		return getOntDownloadMap(new BufferedReader(new FileReader(fn)));
	}
	private static Map<String,String> getOntDownloadMap(URL url) throws IOException {
		return getOntDownloadMap(new BufferedReader(new InputStreamReader(url.openStream())));
	}

	private static Map<String,String> getOntDownloadMap(BufferedReader in) throws IOException {
		Map<String,String> urlmap = new HashMap<String,String>();
		String line;
		String ns = null;
		while ( true ) {
			line = in.readLine();
			if (line == null) {
                break;
            }
			if (line.length() == 0) {
				ns = null;
				continue;
			}
			String[] parts = line.split("\t");
			if (parts.length < 2) {
				continue;
			}
			String tag = parts[0];
			if (tag.equals("namespace")) {
				ns = parts[1];
			}
			else if (tag.equals("download")) {
				if (parts[1] != "") {
					urlmap.put(ns, parts[1]);
				}
			}
			else if (tag.equals("source")) {
				if (parts[1] != "" && !urlmap.containsKey(ns)) {
					urlmap.put(ns, parts[1]);
				}
			}
			else if (tag.equals("is_obsolete")) {
				if (urlmap.containsKey(ns)) {
                    urlmap.remove(ns);
                }
				ns = null;
			}
			else if (tag.equals("format")) {
				// danger or circularity, just for testing now
				//if (!parts[1].equals("obo"))
				//	urlmap.put(ns, "http://purl.org/obo/obo/"+ns+".obo");
				if (!parts[1].equals("obo")) {
                    urlmap.remove(ns);
                }
			}

		}
		return urlmap;

	}


	// TODO - document this
	private static void addVersion(OWLOntology ontology, String version, OWLOntologyManager manager){
		OWLDataFactory fac = manager.getOWLDataFactory();

		OWLAnnotationProperty ap = fac.getOWLAnnotationProperty( Obo2Owl.trTagToIRI(OboFormatTag.TAG_REMARK.getTag()));
		OWLAnnotation ann = fac.getOWLAnnotation(ap, fac.getOWLLiteral(version));

		OWLAxiom ax = fac.getOWLAnnotationAssertionAxiom(ontology.getOntologyID().getOntologyIRI(), ann);

		manager.applyChange(new AddAxiom(ontology, ax));


	}
}
