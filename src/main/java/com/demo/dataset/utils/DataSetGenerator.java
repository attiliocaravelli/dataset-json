package com.demo.dataset.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;

import com.demo.dataset.configuration.DataSetConfiguration;
import com.demo.dataset.model.User;

public class DataSetGenerator {
	
	public static void generateDataSet(int size) {
		if (size <= 0) { throw new IllegalArgumentException(); }
		
		ObjectMapper mapper = new ObjectMapper();
		User [] users = new User[size];
		fillUsers(users);
		//Object to JSON in file
		try {
			mapper.writeValue(new File(DataSetConfiguration.OUTPUT_FILE_NAME), users);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static User createFakeUser(int indexToDifferentiateUser) {
		User user = new User();
		user.setId(indexToDifferentiateUser);
		user.setName("Attilio " + indexToDifferentiateUser);
		user.setCity(selectFakeCityBy(indexToDifferentiateUser));
		return user;
	}
	
	private static void fillUsers(User[] users) {
		for (int i = 0; i < users.length; i++) {
			User user = createFakeUser(i+1);
			user.setFriends(new ArrayList<Integer>());
			fillFriendsList(users.length, user);
			users[i] = user;
		}
	}
	
	private static void fillFriendsList(int userLength, User user) {
		
		ArrayList<Integer> friends = new ArrayList<>();
		int sizeOfFriendsList = randInt(1, userLength);
		
		for (int i = 0; i < sizeOfFriendsList; i++) {
			int randomFriendId = randInt(1, userLength);
			while (friends.contains(randomFriendId)) randomFriendId = randInt(1, userLength); 
			friends.add(randomFriendId);
		}
		
		user.setFriends(friends);
	}
	
	private static String selectFakeCityBy(int counter) {
		switch (choice(counter, 5)) {
		case 0: return "Dublin";
		case 1: return "Galway";
		case 2: return "Limerick";
		case 3: return "Cork";
		case 4: return "Rome";
		}
		return null;
	}
	
	private static int choice(int counter, int max) {
	    return counter % max;
	}
	
	/**
	 * Returns a psuedo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimim value
	 * @param max Maximim value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
