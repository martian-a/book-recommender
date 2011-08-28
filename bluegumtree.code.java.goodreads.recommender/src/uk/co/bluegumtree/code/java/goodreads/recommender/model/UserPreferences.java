package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import uk.co.bluegumtree.code.java.goodreads.recommender.controller.GoodreadsRecommender;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsUser;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.XmlWriter;

public class UserPreferences {

	public static final String SAVE_DIRECTORY = "preferences/";

	private HashMap<Factor, Profile> profiles;
	private GoodreadsUser user;

	public UserPreferences(GoodreadsUser currUser) {

		this.profiles = new HashMap<Factor, Profile>();

		this.user = currUser;
		this.profile();
		this.save();
	}

	public int apply(GoodreadsBook currBook) {

		int grade = 0;

		for (Factor currFactor : Factor.values()) {

			if (currFactor != Factor.ALL) {

				try {

					// Determine whether this book passes the requirements of
					// the
					// current factor
					boolean result = this.profiles.get(currFactor).apply(currBook);

					// If a match, increment the grade by one.
					if (result) {
						grade++;
					}

				} catch (ReportNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return grade;
	}

	public int apply(GoodreadsBook currBook, Factor currFactor, Measure currMeasure) {

		int grade = 0;

		try {

			// Determine whether this book passes the requirements of the
			// current factor
			boolean result = this.profiles.get(currFactor).apply(currBook, currMeasure);

			// If a match, increment the grade by one.
			if (result) {
				grade++;
			}

		} catch (ReportNotFoundException e) {
			e.printStackTrace();
		}

		return grade;
	}

	public GoodreadsUser getUser() {
		return this.user;
	}

	public void profile() {

		System.out.println("Analysing the library of user " + this.user.getId() + ".\n\n");

		this.profiles.put(Factor.ORIGINALPUBLICATIONYEAR, new ProfileOriginalPublicationYear(this.user.getFeedback()));
		this.profiles.put(Factor.PUBLICATIONYEAR, new ProfilePublicationYear(this.user.getFeedback()));
		this.profiles.put(Factor.TITLELENGTH, new ProfileTitleLength(this.user.getFeedback()));
		this.profiles.put(Factor.TOTALPAGES, new ProfileTotalPages(this.user.getFeedback()));

	}

	public void save() {

		String currSaveLocation = Location.getSaveDir(GoodreadsRecommender.SAVE_DIRECTORY + this.user.getId() + File.separator + UserPreferences.SAVE_DIRECTORY);

		for (Factor currFactor : Factor.values()) {

			if (currFactor != Factor.ALL) {

				Profile currProfile = this.profiles.get(currFactor);
				for (Report currReport : currProfile.reports) {

					try {

						String filename = currFactor.getFilename() + "_" + currReport.getScope().toString() + ".xml";
						XmlWriter.write(currReport.toXml(), currSaveLocation + filename);

					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					} catch (InvalidFactorException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public String toString() {

		String out = "";

		out = out + (this.user.getLibrary().toStringBookList());

		for (Factor currFactor : Factor.values()) {
			if (currFactor != Factor.ALL) {
				out = out + this.profiles.get(currFactor).toString();
			}
		}

		return out;
	}

}
