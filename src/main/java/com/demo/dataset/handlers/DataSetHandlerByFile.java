package com.demo.dataset.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;

import com.carrotsearch.sizeof.RamUsageEstimator;
import com.demo.dataset.DataSetApplication;
import com.demo.dataset.configuration.DataSetConfiguration;
import com.demo.dataset.interfaces.Extractor;
import com.demo.dataset.model.User;
import com.demo.dataset.utils.Utils;

public class DataSetHandlerByFile implements Extractor{

	private String fileName = null;


	public DataSetHandlerByFile() {

	}

	@Override
	public LinkedHashSet<User> friendsList(User user) {
		long startTime = System.currentTimeMillis();

		LinkedHashSet<User> friendsList = new LinkedHashSet<>();
		Integer idUser = user.getId();
		
		long maxSizeUserNode = fillFriendsDetailedList(user, friendsList);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;

		System.out.println("The friends list by file has been found in: " + elapsedTime + " ms");

		if (DataSetConfiguration.PRINT_DEBUG) {
			System.out.println("ID User: " + idUser + " max size of the user node in memory by file searching: " + maxSizeUserNode + " bytes");
			System.out.println("n. " + friendsList.size() + " of friends found");
			if (DataSetConfiguration.PRINT_DEBUG_LIST)
				System.out.println("List of friends found: " + friendsList.toString());
			System.out.println();
		}
		return friendsList;
	}

	@Override
	public LinkedHashSet<User> friendsListSuggestedNoSorted(User user) {
		long startTime = System.currentTimeMillis();

		LinkedHashSet<User> alreadyFriends = friendsList(user);
			
		LinkedHashSet<User> friendsListSuggestedNoSorted = new LinkedHashSet<>();
		
		for (User alreadyFriend : alreadyFriends) {
			LinkedHashSet<User> friendsSuggested = friendsList(alreadyFriend);
			for (User friendSuggested : friendsSuggested) {
				boolean isDifferentUser = friendSuggested.compareTo(user) != 0;
				boolean isAlreadyFriend = alreadyFriends.contains(friendSuggested);
				if (isDifferentUser && !isAlreadyFriend) 
					friendsListSuggestedNoSorted.add(friendSuggested);
			}
		}
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;

		System.out.println("The friends suggested list no sorted by file has been found in: " + elapsedTime + " ms");

		return friendsListSuggestedNoSorted;
	}

	@Override
	public List<User> friendsListSuggestedSorted(User user) {
		long startTime = System.currentTimeMillis();

		LinkedHashSet<User> friendsListSuggestedNoSorted = friendsListSuggestedNoSorted(user);
		
		List<User> friendsListSuggestedSorted = new ArrayList<>(friendsListSuggestedNoSorted);
		Collections.sort(friendsListSuggestedSorted, User.Comparators.CITY);
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;

		System.out.println("The friends suggested list sorted by file has been found in: " + elapsedTime + " ms");

		return friendsListSuggestedSorted;
	}

	private JsonNode findUserById(Integer idUser) {
		if (fileName == null) return null;
		// read the json file
		InputStream is = DataSetApplication.class.getClass().getResourceAsStream(fileName);
		Reader reader = new InputStreamReader(is);
		JsonFactory f = new MappingJsonFactory();
		JsonParser jp = null;
		JsonNode userNode = null;

		try {
			jp = f.createJsonParser(reader);

			if (jp.nextToken() == JsonToken.START_ARRAY) {
				// For each of the records in the array

				while (jp.nextToken() != JsonToken.END_ARRAY) {

					userNode = jp.readValueAsTree();		
					JsonNode idUserNode = userNode.get(DataSetConfiguration.ID_JSON_FIELD);
					// And now we have random access to everything in the object
					Integer idUserTmp = idUserNode.asInt();

					if (idUserTmp.compareTo(idUser) == 0) break;
				}
			} 

		} catch (JsonParseException x) {

		} catch (JsonProcessingException y) {

		}catch (IOException e) {
			e.printStackTrace();
		} 
		
		return userNode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private JsonNode getFriends(Integer idUser) {
		JsonNode userNode = findUserById(idUser);
		if (userNode == null) return null;
		final JsonNode jsonFriends=userNode.get(DataSetConfiguration.FRIENDS_JSON_FIELD);
		return jsonFriends;
	}

	private long fillFriendsDetailedList(User user, LinkedHashSet<User> friendsList) {
		long maxSizeUserNode = 0;
		JsonNode userNodeFriends = getFriends(user.getId());
		for (Iterator<JsonNode> x=userNodeFriends.getElements(); x.hasNext(); ) {
			JsonNode jsonFriendSuggestedByIdToTheUserGiven=(JsonNode)x.next();
			if (jsonFriendSuggestedByIdToTheUserGiven.asInt()==user.getId()) continue;
			JsonNode jsonFriendSuggestedToTheUserGiven=findUserById(jsonFriendSuggestedByIdToTheUserGiven.asInt());
			maxSizeUserNode = Math.max(maxSizeUserNode, RamUsageEstimator.sizeOf((jsonFriendSuggestedToTheUserGiven)));
			friendsList.add(Utils.userFrom(jsonFriendSuggestedToTheUserGiven));
		}
		return maxSizeUserNode;
	}
}
