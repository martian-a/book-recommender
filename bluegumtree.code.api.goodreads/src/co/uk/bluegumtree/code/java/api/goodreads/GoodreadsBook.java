package co.uk.bluegumtree.code.java.api.goodreads;

import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import co.uk.bluegumtree.code.java.util.Table;
import co.uk.bluegumtree.code.java.util.XmlWriter;
import co.uk.bluegumtree.code.java.util.Table.Align;

public class GoodreadsBook extends GoodreadsObject {

	public static final String dataDirectory = "books";

	private Long id;
	private String isbn10;
	private String isbn13;
	private String title;
	private Integer originalPublicationYear;
	private Integer originalPublicationMonth;
	private Integer publicationYear;
	private Language language;
	private Language originalLanguage;
	private Integer totalPages;
	private GoodreadsFeedback reviews;
	private HashSet<GoodreadsAuthor> authors;

	public GoodreadsBook(long currBookId) {
		this.id = currBookId;
		this.isbn10 = null;
		this.isbn13 = null;
		this.title = null;
		this.originalPublicationYear = null;
		this.originalPublicationMonth = null;
		this.publicationYear = null;
		this.language = null;
		this.originalLanguage = null;
		this.totalPages = null;
		this.reviews = new GoodreadsFeedback();
	}
	
	public void setAuthors(HashSet<GoodreadsAuthor> currAuthors) {
		this.authors = currAuthors;
	}
	
	public HashSet<GoodreadsAuthor> getAuthors() {
		return this.authors;
	}

	public void addReview(GoodreadsReview currReview) {
		this.reviews.addReview(currReview);
	}

	public boolean equals(GoodreadsBook anotherBook) {
		if (this.id.equals(anotherBook.getId())) {
			return true;
		}
		return false;
	}

	public int getAverageRating() throws RatingNotFoundException {
		return this.reviews.getAverageRating();
	}

	public Long getId() {
		return this.id;
	}

	public String getIsbn10() {
		return this.isbn10;
	}

	public String getIsbn13() {
		return this.isbn13;
	}

	public Language getLanguage() {
		return this.language;
	}

	public Language getOriginalLanguage() {
		return this.originalLanguage;
	}

	public Integer getOriginalPublicationMonth() {
		return this.originalPublicationMonth;
	}

	public Integer getOriginalPublicationYear() {
		return this.originalPublicationYear;
	}

	public Integer getPublicationYear() {
		return this.publicationYear;
	}

	public GoodreadsReview getReview(GoodreadsUser currUser) throws ReviewNotFoundException {
		return this.reviews.getReview(this, currUser);
	}

	public GoodreadsReview getReview(Long currId) throws ReviewNotFoundException {
		return this.reviews.getReview(currId);
	}

	public GoodreadsFeedback getReviews() {
		return this.reviews;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getTotalPages() {
		return this.totalPages;
	}

	public int getTotalReviews() {
		return this.reviews.size();
	}

	public void merge(GoodreadsBook currBook) throws InvalidIdException {
		if (!this.id.equals(currBook.getId())) {
			throw new InvalidIdException("Book IDs do not match.");
		}

		if (this.isbn10 == null) {
			this.setIsbn10(currBook.getIsbn10());
		}

		if (this.isbn13 == null) {
			this.setIsbn13(currBook.getIsbn13());
		}

		if (this.title == null) {
			this.setTitle(currBook.getTitle());
		}

		if (this.originalPublicationYear == null) {
			this.setOriginalPublicationYear(currBook.getOriginalPublicationYear());
		}

		if (this.originalPublicationMonth == null) {
			this.setOriginalPublicationMonth(currBook.getOriginalPublicationMonth());
		}

		if (this.publicationYear == null) {
			this.setPublicationYear(currBook.getPublicationYear());
		}

		if (this.language == null) {
			this.setLanguage(currBook.getLanguage());
		}

		if (this.originalLanguage == null) {
			this.setOriginalLanguage(currBook.getOriginalLanguage());
		}

		if (this.totalPages == null) {
			this.setTotalPages(currBook.getTotalPages());
		}

		this.reviews.addReviews(currBook.getReviews());
	}

	public void setIsbn10(String currIsbn) {
		this.isbn10 = currIsbn;
	}

	public void setIsbn13(String currIsbn) {
		this.isbn13 = currIsbn;
	}

	public void setLanguage(Language currLanguage) {
		this.language = currLanguage;
	}

	public void setLanguage(String currLanguage) {
		try {
			this.setLanguage(Language.getLanguage(currLanguage));
		} catch (InvalidLanguageException e) {
			e.printStackTrace();
		}
	}

	public void setOriginalLanguage(Integer currLanguage) {
		try {
			this.setOriginalLanguage(Language.getLanguage(currLanguage));
		} catch (InvalidLanguageException e) {
			e.printStackTrace();
		}
	}

	public void setOriginalLanguage(Language currLanguage) {
		this.originalLanguage = currLanguage;
	}

	public void setOriginalPublicationMonth(Integer currMonth) {
		this.originalPublicationMonth = currMonth;
	}

	public void setOriginalPublicationYear(Integer currYear) {
		this.originalPublicationYear = currYear;
	}

	public void setPublicationYear(Integer currYear) {
		this.publicationYear = currYear;
	}

	public void setReviews(GoodreadsFeedback currReviews) {
		this.reviews = currReviews;
	}

	public void setTitle(String currTitle) {
		this.title = currTitle;
	}

	public void setTotalPages(Integer currPages) {
		this.totalPages = currPages;
	}

	@Override
	public String toString() {

		ArrayList<String[]> list = new ArrayList<String[]>();

		String[] idRow = { "ID", String.valueOf(this.getId()) };
		list.add(idRow);

		String[] titleRow = { "Title", this.getTitle() };
		list.add(titleRow);

		String[] oMonthRow = { "Original Month of Publication", String.valueOf(this.getOriginalPublicationMonth()) };
		list.add(oMonthRow);

		String[] oYearRow = { "Original Year of Publication", String.valueOf(this.getOriginalPublicationYear()) };
		list.add(oYearRow);

		String[] yearRow = { "Year of Publication", String.valueOf(this.getPublicationYear()) };
		list.add(yearRow);

		String[] pagesRow = { "Total Pages", String.valueOf(this.getTotalPages()) };
		list.add(pagesRow);

		String[] oLanguageRow = { "Original Language", String.valueOf(this.getOriginalLanguage()) };
		list.add(oLanguageRow);

		String[] languageRow = { "Language", String.valueOf(this.getLanguage()) };
		list.add(languageRow);

		String[] isbn10Row = { "ISBN (10)", String.valueOf(this.getIsbn10()) };
		list.add(isbn10Row);

		String[] isbn13Row = { "ISBN (13)", String.valueOf(this.getIsbn13()) };
		list.add(isbn13Row);

		Table table = new Table(list, 0, 1);
		Align[] formatting = { Align.LEFT, Align.LEFT };
		table.format(formatting);

		return table.toString();
	}
	
	public Document toXml() throws ParserConfigurationException {

		// Create an XML document
		Document currDocument = XmlWriter.createXmlDocument();
		Element rootElement = currDocument.getDocumentElement();
		
		this.toXml(currDocument, rootElement);
		
		return currDocument;
	}
	
	public Element toXml(Document currDocument, Element docElement) throws ParserConfigurationException {
					
		// Create the root element, <book />
		Element rootElement = XmlWriter.toElement(currDocument, "book");
				
		// Append the root element to the document
		docElement.appendChild(rootElement);
		
		rootElement.setAttribute("id", this.getId().toString());
				
		Element currElement = null;
		Element parentElement = null;
		
		currElement = XmlWriter.append(currDocument, rootElement, "isbn", this.isbn10, false);
		if (currElement != null) {
			currElement.setAttribute("version", "10");
		}
		
		currElement = XmlWriter.append(currDocument, rootElement, "isbn", this.isbn13, false);
		if (currElement != null) {
			currElement.setAttribute("version", "13");
		}
		
		XmlWriter.append(currDocument, rootElement, "title", this.title, false);
		
		parentElement = XmlWriter.append(currDocument, rootElement, "published");
		parentElement.setAttribute("original", "true");
		
		XmlWriter.append(currDocument, parentElement, "year", this.originalPublicationYear, false);
		XmlWriter.append(currDocument, parentElement, "month", this.originalPublicationMonth, false);
	
		parentElement = XmlWriter.append(currDocument, rootElement, "published");
		
		XmlWriter.append(currDocument, parentElement, "year", this.publicationYear, false);
		// XmlWriter.append(currDocument, parentElement, "month", this.publicationMonth.toString(), false);
		
		XmlWriter.append(currDocument, rootElement, "language", this.language, false);
		
		currElement = XmlWriter.append(currDocument, rootElement, "language", this.originalLanguage, false);
		if (currElement != null) {
			currElement.setAttribute("original", "true");
		}
		
		XmlWriter.append(currDocument, rootElement, "pages", this.totalPages, false);
		
		return rootElement;
	}
}
