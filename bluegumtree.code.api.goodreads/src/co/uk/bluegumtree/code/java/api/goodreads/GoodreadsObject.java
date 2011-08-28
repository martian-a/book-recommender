package co.uk.bluegumtree.code.java.api.goodreads;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.XmlReadException;
import co.uk.bluegumtree.code.java.util.XmlReader;

public abstract class GoodreadsObject {

	public static final String dataDirectory = "goodreads/db/";

	protected ArrayList<Document> xml;

	public String getFilename() throws GoodreadsLoadException {
		return "";
	}

	public ArrayList<File> getFiles() throws GoodreadsLoadException {

		ArrayList<File> files = new ArrayList<File>();
		int i = 0;
		while (true) {

			File currFile = new File(Location.getSaveDir(dataDirectory + this.getFilename() + "_" + i + ".xml"));

			if (currFile.exists()) {
				files.add(currFile);
				i++;
			} else {
				return files;
			}
		}
	}

	public ArrayList<Document> getXml() throws DOMException, ParserConfigurationException {
		return this.xml;
	}

	public void load() throws GoodreadsLoadException {

		ArrayList<File> files = this.getFiles();

		for (File currFile : files) {

			Document currXml;
			try {

				currXml = XmlReader.read(currFile);
				this.load(currXml);

			} catch (XmlReadException e) {
				e.printStackTrace();
				throw new GoodreadsLoadException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new GoodreadsLoadException(e.getMessage());
			}
		}
	}

	public void load(Document currXml) throws GoodreadsLoadException {

		if (currXml == null) {
			throw new GoodreadsLoadException("Load failed: attempted to set XML to a null value.");
		}

		this.xml = new ArrayList<Document>();
		this.xml.add(currXml);
	}

	public void log(String message) {
		System.out.println(message);
	}
}
