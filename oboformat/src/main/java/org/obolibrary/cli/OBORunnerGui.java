package org.obolibrary.cli;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.obolibrary.gui.GuiMainFrame;

/**
 * GUI access for converter.
 */
public class OBORunnerGui extends OBORunner {

    private final static Logger logger = Logger.getLogger(OBORunnerGui.class.getName());
	
	// SimpleDateFormat is NOT thread safe
	// encapsulate as thread local
	private final static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	
	public static void main(String[] args) {
		
		final BlockingQueue<String> logQueue =  new ArrayBlockingQueue<String>(100000);
		
		
		OBORunnerConfiguration config = OBORunnerConfigCLIReader.readConfig(args);
		if (config.showHelp.getValue()) {
			System.out.println("GUI version of OBORunner. All parameters are set using the GUI.");
			System.exit(0);
		}
		
		

		// Start GUI
		new GuiMainFrameWorker(logQueue, config);
	}

	private static final class GuiMainFrameWorker extends GuiMainFrame {
		// generated
		private static final long serialVersionUID = -7439731894262579201L;
	
		private GuiMainFrameWorker(BlockingQueue<String> logQueue,
				OBORunnerConfiguration config) {
			super(logQueue, config);
		}
	
		@Override
		protected void executeConversion(final OBORunnerConfiguration config) {
			disableRunButton();
			// execute the conversion in a separate Thread, otherwise the GUI might be blocked.
			Thread t = new Thread() {

				@Override
				public void run() {
					try {
                        // XXX a manager needs to be injected
						String buildDir = config.buildDir.getValue();
						if (buildDir != null) {
                            buildAllOboOwlFiles(buildDir, config, logger, null);
						}
                        runConversion(config, logger, null, null);
						logger.info("Finished ontology conversion.");
						JOptionPane.showMessageDialog(GuiMainFrameWorker.this, "Finished ontology conversion.");
					} catch (Exception e) {
						String message = "Internal error: "+ e.getMessage();
                        logger.log(Level.SEVERE, message, e);
						JOptionPane.showMessageDialog(GuiMainFrameWorker.this, message, "Error", JOptionPane.ERROR_MESSAGE);
					} catch (Throwable e) {
						String message = "Internal error: "+ e.getMessage();
                        logger.log(Level.SEVERE, message, e);
						JOptionPane.showMessageDialog(GuiMainFrameWorker.this, message, "FatalError", JOptionPane.ERROR_MESSAGE);
					}
					finally {
						enableRunButton();
					}
				}
			};
			t.start();
		}
	}
}
