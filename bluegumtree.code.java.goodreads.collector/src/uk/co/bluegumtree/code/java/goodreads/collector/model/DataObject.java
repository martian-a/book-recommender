package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

import uk.co.bluegumtree.code.java.goodreads.collector.controller.GoodreadsDataCollector;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.XmlReadException;
import co.uk.bluegumtree.code.java.util.XmlReader;
import co.uk.bluegumtree.code.java.util.XmlWriter;

/**
 * A generic data object that contains attributes and methods common to all data sources.
 * 
 * @author Sheila Ellen Thomson
 *
 */
public abstract class DataObject {

	public static final String SAVE_DIRECTORY = "goodreads/db/organise/in/";

	protected ArrayList<Document> xml;

	public String getFilename() throws DataCollectorException {
		return Location.getSaveDir(SAVE_DIRECTORY);
	}

	/**
	 * Retrieves locally cached files associated with the current object.
	 * 
	 * @return
	 * locally cached files
	 * 
	 * @throws DataCollectorLoadException
	 */
	public ArrayList<File> getFiles() throws DataCollectorLoadException {

		ArrayList<File> files = new ArrayList<File>();
		int i = 0;
		while (true) {

			File currFile;
			try {
				String currFilename = this.getFilename() + "_" + i + ".xml";
				this.log("Attempting to load from " + currFilename);
				currFile = new File(currFilename);
			} catch (DataCollectorException e) {
				e.printStackTrace();
				throw new DataCollectorLoadException(e.getMessage());
			}

			if (currFile.exists()) {
				files.add(currFile);
				i++;
			} else {
				return files;
			}
		}
	}

	/**
	 * @return
	 * the XML response documents that contain the data used to popular the current instance of this object.
	 */
	public ArrayList<Document> getXml() {
		return this.xml;
	}

	/**
	 * Reads in locally cached XML response documents and stores them in this.xml
	 * @throws DataCollectorLoadException
	 */
	public void load() throws DataCollectorLoadException {

		this.resetXml();

		ArrayList<File> files = this.getFiles();

		if (files.size() < 1) {

			// If no XML has been loaded, force an update.
			try {

				// Download the latest XML
				this.update();

				// If there's still no XML, then there's nothing to load
				// data from.
				if (this.xml == null) {
					throw new DataCollectorLoadException("Update complete but object still has no XML.");
				}

			} catch (DataCollectorUpdateException e) {

				// Something went wrong during the update
				// so there's no XML to load data from
				throw new DataCollectorLoadException(e.getMessage());
			}
		}

		for (File currFile : files) {

			Document currXml;
			try {

				this.log("Attempting to read from " + currFile.getAbsolutePath() + "...");
				currXml = XmlReader.read(currFile);
				this.load(currXml);
				this.log("...load complete.");

			} catch (XmlReadException e) {
				e.printStackTrace();
				this.log("...load failed.");
			} catch (IOException e) {
				e.printStackTrace();
				this.log("...load failed.");
			}

		}

	}

	/**
	 * Replaces the contents of this.xml with the Document supplied.
	 * @param currXml
	 * the XML response to be loaded.
	 */
	public void load(Document currXml) {
		this.setXml(currXml);
	}

	/**
	 * If the current Mode is verbose, outputs the message supplied to screen,
	 * otherwise hides the message.
	 * 
	 * TODO: Implement a proper logging mechanism so that even if not displayed the messages are still recorded.
	 * 
	 * @param message
	 * the message to be displayed
	 */
	public void log(String message) {
		if (GoodreadsDataCollector.verbose) {
			System.out.println(message);
		}
	}

	/**
	 * Replaces any XML response documents currently loaded into this.xml with an empty collection object.
	 */
	public void resetXml() {
		this.xml = new ArrayList<Document>();
	}

	
	/**
	 * Saves the XML response documents loaded into this.xml.
	 * @throws DataCollectorSaveException
	 */
	public void save() throws DataCollectorSaveException {

		try {
			this.save(this.getFilename());
		} catch (DataCollectorException e) {
			e.printStackTrace();
			throw new DataCollectorSaveException(e.getMessage());
		}
	}

	/**
	 * Saves the XML response documents currently loaded into this.xml.
	 * 
	 * @param currSaveLocation
	 * the location that the files are to saved in
	 * 
	 * @throws DataCollectorSaveException
	 */
	public void save(String currSaveLocation) throws DataCollectorSaveException {

		ArrayList<Document> currXml = new ArrayList<Document>();
		try {
			currXml = this.getXml();
		} catch (DOMException e) {
			throw new DataCollectorSaveException(e.getMessage());
		}

		if (currXml == null) {
			return;
		}

		for (int i = 0; i < currXml.size(); i++) {

			File currSaveFile = new File(currSaveLocation + "_" + i + ".xml");

			try {
				System.out.println("Saving to " + currSaveFile.getCanonicalPath());
				boolean status = XmlWriter.write(currXml.get(i), currSaveFile);
				if (status == true) {
					System.out.println("Save complete");
				} else {
					System.out.println("Save failed");
				}
			} catch (DOMException e) {
				throw new DataCollectorSaveException(e.getMessage());
			} catch (IOException e) {
				throw new DataCollectorSaveException(e.getMessage());
			}
		}
	}

	/**
	 * Adds the supplied XML document to the current documents loaded in this.xml
	 * @param currXml
	 */
	public void setXml(Document currXml) {
		this.xml.add(currXml);
	}

	/**
	 * Removes any XML response documents currently loaded into this.xml
	 * 
	 * @throws DataCollectorUpdateException
	 */
	public void update() throws DataCollectorUpdateException {
		this.resetXml();
	}

}
