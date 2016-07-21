package com.demo.dataset.interfaces;

import java.util.LinkedHashSet;
import java.util.List;

import com.demo.dataset.model.User;

public interface Extractor {
	public LinkedHashSet<User> friendsList(User user);
	public LinkedHashSet<User> friendsListSuggestedNoSorted(User user);
	public List<User> friendsListSuggestedSorted(User user);
}
