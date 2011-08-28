package co.uk.bluegumtree.code.java.api.goodreads;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.uk.bluegumtree.code.java.util.XmlReader;

public class GoodreadsImage extends GoodreadsObject {

	protected String size;
	protected URL url;

	public GoodreadsImage(Node currNode) {

		this.size = XmlReader.readString(currNode, "@size");
		try {
			this.url = new URL(XmlReader.readString(currNode, "."));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public GoodreadsImage(String currUrl, String currSize) throws MalformedURLException {
		super();
		this.url = new URL(currUrl);
		this.size = currSize;
	}

	public String getSize() {
		return this.size;
	}

	public URL getUrl() {
		return this.url;
	}

	public static ArrayList<GoodreadsImage> fromXml(NodeList asXml) {

		ArrayList<GoodreadsImage> images = new ArrayList<GoodreadsImage>();

		int totalNodes = asXml.getLength();
		for (int i = 0; i < totalNodes; i++) {
			Node currNode = asXml.item(i);
			images.add(new GoodreadsImage(currNode));
		}

		return images;
	}
}
