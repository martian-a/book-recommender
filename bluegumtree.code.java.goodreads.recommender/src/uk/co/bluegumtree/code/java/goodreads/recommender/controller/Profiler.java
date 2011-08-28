package uk.co.bluegumtree.code.java.goodreads.recommender.controller;

import java.util.HashSet;

import uk.co.bluegumtree.code.java.goodreads.recommender.model.PreferencesNotFoundException;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.UserPreferences;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsUser;

public class Profiler {

	HashSet<GoodreadsUser> users;
	HashSet<UserPreferences> profiles;

	private Profiler() {
		this.users = new HashSet<GoodreadsUser>();
		this.profiles = new HashSet<UserPreferences>();
	}

	public Profiler(GoodreadsUser currUser) {
		this();
		this.users.add(currUser);
	}

	public Profiler(HashSet<GoodreadsUser> currUsers) {
		this();
		this.users = currUsers;
	}

	public UserPreferences getPreferences(GoodreadsUser currUser) throws PreferencesNotFoundException {
		for (UserPreferences currProfile : this.profiles) {
			if (currProfile.getUser() == currUser) {
				return currProfile;
			}
		}
		throw new PreferencesNotFoundException();
	}

	public HashSet<GoodreadsUser> getUsers() {
		return this.users;
	}

	public void profile() {
		for (GoodreadsUser currUser : this.users) {
			this.profile(currUser);
		}
	}

	public UserPreferences profile(GoodreadsUser currUser) {
		UserPreferences currProfile = new UserPreferences(currUser);
		this.profiles.add(currProfile);
		return currProfile;
	}
}
