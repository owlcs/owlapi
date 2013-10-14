package org.obolibrary.gui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Wrapper for the native {@link FileDialog}. Allow convenient use of
 * the native Dialog. The wrapper sets also the system specific flags, for file
 * and folder dialogs.
 * 
 * Limitations:
 * <ul>
 *  <li>No API for multiple file selection (Fixed in Java7).</li>
 *  <li>Linux: Due to a bug, the non-native {@link JFileChooser} is used (Fixed in Java7).</li>
 *  <li>Windows: FileFilters are ignored in the reference implementation</li>
 * </ul>
 */
public abstract class SelectDialog {
	
    private final static Logger LOGGER = Logger.getLogger(SelectDialog.class.getName());
	public final static boolean LOAD = false;
	public final static boolean SAVE = true;
	
	/**
	 * Show the dialog to the user for selection. 
	 */
	public abstract void show();
	
	/**
	 * Retrieve the selected file/folder.
	 * 
	 * @return selected file/folder or null (if not available).
	 */
	public abstract File getSelected();
	
	/**
	 * Retrieve the canonical path of the selected folder.
	 * 
	 * @return canonical path of the selected file/folder or null (if not available)
	 */
	public String getSelectedCanonicalPath() {
		File selected = getSelected();
		return getCanonical(selected);
	}

	private static String getCanonical(File selected) {
		if (selected != null) {
			try {
				return selected.getCanonicalPath();
			} catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Unable to get canonical path for file: "
                        + selected.getAbsolutePath(), e);
			}
		}
		return null;
	}
	
	/**
	 * Create a new dialog, which selects a file from the file system.
	 * 
	 * @param frame the owner frame used to configure the dialog
	 * @param write write mode, see {@link #LOAD} and {@link #SAVE}.
	 * @param defaultFolder default folder the dialog 
	 * @param title title string for the dialog
	 * @param description filter description
	 * @param extensions suffixes for the filter, set to null to show all files
	 * @return dialog
	 */
	public static SelectDialog getFileSelector(final Frame frame, boolean write, String defaultFolder, String title, String description, String[] extensions) {
		if (isUnix()) {
			// due to a bug, which is fixed in JDK 7, the native AWT dialog 
			// does not use the correct renderer: meaning it looks very ugly.
			// http://bugs.sun.com/view_bug.do?bug_id=6913179
			// work around: use the built-in java swing version
			final JFileChooser fc;
			if (defaultFolder != null) {
				fc = new JFileChooser(defaultFolder);
			}
			else {
				fc = new JFileChooser();
			}
			int dialogType = JFileChooser.OPEN_DIALOG;
			if (write) {
				dialogType = JFileChooser.SAVE_DIALOG;
			}
			fc.setDialogType(dialogType);
			fc.setDialogTitle(title);
			if (extensions != null && extensions.length > 0) {
				fc.setFileFilter(new SuffixFileFilter(description, extensions));
			}
			return new SelectDialog() {
				File selected = null;
				
				@Override
				public void show() {
					selected = null;
					int returnVal = fc.showOpenDialog(frame);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						selected = fc.getSelectedFile();
					}
					
				}
				
				@Override
				public File getSelected() {
					return selected;
				}
			};
		}
		else {
			// try native
			int mode = FileDialog.LOAD;
			if (write) {
				mode = FileDialog.SAVE;
			}
			final FileDialog dialog = new FileDialog(frame, title, mode);
			dialog.setDirectory(defaultFolder);
			if (extensions != null && extensions.length > 0) {
				/*
				 * Extracted from the javadoc:
				 * 
				 * Filename filters do not function in Sun's reference
				 * implementation for Microsoft Windows.
				 */
				dialog.setFilenameFilter(new SuffixFilenameFilter(extensions));
			}
			
			return new SelectDialog() {
				File selected = null;
				
				@Override
				public void show() {
					selected = null;
					dialog.setVisible(true);
					String fileName = dialog.getFile();
					String dirName = dialog.getDirectory();
					if (fileName != null && dirName != null) {
						selected = new File(dirName, fileName);
					}
				}
				
				@Override
				public File getSelected() {
					return selected;
				}
			};
		}
	}
	
	/**
	 * Create a new dialog, which selects a folder from the file system.
	 * 
	 * @param frame the frame used to center the dialog
	 * @param defaultFolder default folder for the dialog 
	 * @param title title string for the dialog
	 * @return dialog
	 */
	public static SelectDialog getFolderSelector(final Frame frame, String defaultFolder, String title) {
		if (isUnix()) {
			final JFileChooser folderFC;
			if (defaultFolder != null) {
				folderFC = new JFileChooser(defaultFolder);
			}
			else {
				folderFC = new JFileChooser();
			}
			folderFC.setDialogTitle(title);
			folderFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			folderFC.setAcceptAllFileFilterUsed(false);
			return new SelectDialog() {
				File selected = null;

				@Override
				public void show() {
					selected = null;
					int returnVal = folderFC.showOpenDialog(frame);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						selected = folderFC.getSelectedFile();
					}
				}

				@Override
				public File getSelected() {
					return selected;
				}
			};
		}
		else {
			// try native file dialog
			// There are two problems here:
			// 1) Windows ignores the FilenameFilter
			// 2) MacOS needs a special flag to allow a FileDialog to select a folder
			
			final FileDialog dialog = new FileDialog(frame, title, FileDialog.LOAD);
			dialog.setDirectory(defaultFolder);
			dialog.setFilenameFilter(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					// only show directories
					return false;
				}
			});
			
			return new SelectDialog() {
				
				File selected = null;
				
				@Override
				public void show() {
					System.setProperty("apple.awt.fileDialogForDirectories", "true");
					try {
						selected = null;
						dialog.setVisible(true);
						String fileName = dialog.getFile();
						String dirName = dialog.getDirectory();
						if (fileName != null && dirName != null) {
							File file = new File(dirName, fileName);
							if (file.isDirectory()) {
								selected = file;
							}
							else if (file.isFile()) {
								selected = file.getParentFile();
							}
						}
						else if (dirName != null) {
							selected = new File(dirName);
						}
					} finally {
						System.setProperty("apple.awt.fileDialogForDirectories", "false");
					}
				}
				
				@Override
				public File getSelected() {
					return selected;
				}
			};
		}
	}
	
	
	/**
	 * {@link FileFilter} for a given list of suffixes. 
	 * If the list is empty, accept all files.
	 */
	private static class SuffixFileFilter extends FileFilter {
		
		private final String description; 
		private final Set<String> extensions;
		
		public SuffixFileFilter(String description, String...suffixes) {
			super();
			this.description = description;
			if (suffixes != null) {
				extensions = new HashSet<String>(Arrays.asList(suffixes));
			}
			else {
				extensions = null;
			}
		}

		public String getDescription() {
			return description;
		}
		
		public boolean accept(File f) {
			if (extensions == null) {
				return true;
			}
			if (f != null) {
	            if (f.isDirectory()) {
	                return true;
	            }
	            String fileName = f.getName();
	            int i = fileName.lastIndexOf('.');
	            if (i > 0 && i < fileName.length() - 1) {
	                String extension = fileName.substring(i + 1).toLowerCase();
	                return extensions.contains(extension);
	            }
	        }
	        return false;
		}
	};
	
	/**
	 * {@link FilenameFilter} for a given list of suffixes. 
	 * If the list is empty, accept all files.
	 */
	private static class SuffixFilenameFilter implements FilenameFilter {
		
		private final Set<String> extensions;
		
		public SuffixFilenameFilter(String...suffixes) {
			if (suffixes != null) {
				extensions = new HashSet<String>(Arrays.asList(suffixes));
			}
			else {
				extensions = null;
			}
		}
		
		public boolean accept(File dir, String fileName) {
			if (extensions == null) {
				return true;
			}
			if (fileName != null && fileName.length() > 0) {
				int i = fileName.lastIndexOf('.');
	            if (i > 0 && i < fileName.length() - 1) {
	                String extension = fileName.substring(i + 1).toLowerCase();
	                return extensions.contains(extension);
	            }
			}
			return false;
		}
	};
	
	private static boolean isUnix(){
		String os = System.getProperty("os.name").toLowerCase();
		//linux or unix
	    return os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0;
 
	}
}
