package org.obolibrary.gui;

import java.awt.BorderLayout;
import java.util.concurrent.BlockingQueue;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.obolibrary.gui.GuiTools.SizedJPanel;

/**
 * Panel for rendering the log in the GUI. This component relies on the assumption, that
 * all events are put in the logQueue. This is can be done with a registered logAppender.
 */
public class GuiLogPanel extends SizedJPanel {

	// Generated
	private static final long serialVersionUID = 3246873806276885090L;

	final JTextPane logTextPane;
	private final BlockingQueue<String> logQueue;
	private Thread thread = new Thread() {
		
		public void run() {
			while (true) {
				appendToLogPanel();
				// sleep for 10 ms to allow for any other activities
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	};
	
	/**
	 * Construct a new LogPanel using the given log message queue.
	 * 
	 * @param logQueue
	 */
	public GuiLogPanel(BlockingQueue<String> logQueue) {
		super();
		this.logQueue = logQueue;
		setLayout(new BorderLayout(5, 5));
		add(new JLabel("LOGS"), BorderLayout.NORTH);
		
		logTextPane = new JTextPane();
		logTextPane.setEditable(false);
		
		JScrollPane logScrollPane = new JScrollPane(logTextPane);
		logScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		logScrollPane.setWheelScrollingEnabled(true);
		
		add(logScrollPane, BorderLayout.CENTER);
		thread.start();
	}
	
	private void appendToLogPanel()
	{
		StyledDocument document = logTextPane.getStyledDocument();
		try {
			while (true) {
				// the take() is a blocking operation (sleep wait)
				// it only proceeds if there are objects to consume 
				String message = logQueue.take();
				document.insertString(document.getLength(), message, null);
				document.insertString(document.getLength(), "\n", null);
				logTextPane.setCaretPosition(document.getLength());
			}
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
