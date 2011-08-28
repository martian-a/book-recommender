package co.uk.bluegumtree.code.java.api.goodreads;

import java.util.HashSet;

public class GoodreadsCommunity {

	private HashSet<GoodreadsUser> users;

	public GoodreadsCommunity() {
		this.users = new HashSet<GoodreadsUser>();
	}

	public void addUser(GoodreadsUser currUser) {
		this.users.add(currUser);
	}

	public boolean contains(GoodreadsUser currUser) {
		return this.contains(currUser.getId());
	}

	public boolean contains(Long currId) {
		for (GoodreadsUser currUser : this.users) {
			if (currUser.getId().equals(currId)) {
				return true;
			}
		}
		return false;
	}

	public GoodreadsUser getUser(Long currId) throws UserNotFoundException {
		for (GoodreadsUser currUser : this.users) {
			if (currUser.getId().equals(currId)) {
				return currUser;
			}
		}
		throw new UserNotFoundException(currId);
	}

	public HashSet<GoodreadsUser> getUsers() {
		return this.users;
	}

	public int size() {
		return this.users.size();
	}
}
