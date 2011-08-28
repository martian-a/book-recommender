package co.uk.bluegumtree.code.java.api.goodreads;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import co.uk.bluegumtree.code.java.util.XmlReader;

public class GoodreadsAuthor extends GoodreadsObject {

	protected String name;
	protected Long id;
	protected URL url;

	public GoodreadsAuthor(Long currId) {
		this.id = currId;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public URL getUrl() {
		return this.url;
	}
	
	public void setName(String currName) {
		this.name = currName;
	}
	
	public void setUrl(URL currUrl) {
		this.url = currUrl;
	}
}
