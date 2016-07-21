package com.demo.dataset.handlers;

import org.junit.Test;

import com.demo.dataset.handlers.DataSetHandlerByFile;
import com.demo.dataset.model.User;
import com.demo.dataset.utils.DataSetGenerator;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.List;

public class DataSetHandlerByFileTest {
	
    @Test
    public void friendsList_Test(){
    	DataSetHandlerByFile dataset = new DataSetHandlerByFile();

    	dataset.setFileName("/jsonTestByExample.json");
		
    	String solutionActual = dataset.friendsList(DataSetGenerator.createFakeUser(2)).toString();
    	String solutionExpected_friendsList = "[Id: 1, name: Alice, city: Dublin, Id: 3, name: Charlie, city: London, Id: 4, name: Davina, city: Belfast]";
    	
    	assertEquals("Unexpected value: " + solutionActual, solutionExpected_friendsList, solutionActual);

    }
    
    @Test
    public void friendsListSuggestedNoSorted_Test(){
    	DataSetHandlerByFile dataset = new DataSetHandlerByFile();

    	dataset.setFileName("/jsonTestByExample.json");
    	
    	Integer idKey = 2;
    	
    	User user = DataSetGenerator.createFakeUser(idKey);
    	
    	LinkedHashSet<User> friendsList = dataset.friendsList(user);
    	
    	LinkedHashSet<User> solutionActual = dataset.friendsListSuggestedNoSorted(user);
    	
    	String solutionExpected_SuggestedNoSorted = "[Id: 5, name: Attilio, city: Reggio Calabria]";
    	
    	for (User u : solutionActual) {
    		assertFalse(friendsList.contains(u));
    	}
    	
    	assertEquals("Unexpected value: " + solutionActual.toString(), solutionExpected_SuggestedNoSorted, solutionActual.toString());

    }
    
    @Test
    public void friendsListSuggestedSortedNoFriends_Test(){
    	DataSetHandlerByFile dataset = new DataSetHandlerByFile();

    	dataset.setFileName("/jsonTestByExample.json");
		
    	Integer idKey = 1;
    	
    	User user = DataSetGenerator.createFakeUser(idKey);
    	
    	LinkedHashSet<User> friendsList = dataset.friendsList(user);
    	
    	List<User> solutionActual = dataset.friendsListSuggestedSorted(user);
    	
    	String solutionExpected_SuggestedSorted = "[]";
    	
    	for (User u : solutionActual) {
    		assertFalse(friendsList.contains(u));
    	}
    	
    	assertEquals("Unexpected value: " + solutionActual.toString(), solutionExpected_SuggestedSorted, solutionActual.toString());
    }
    
    @Test
    public void friendsListSuggestedSorted_Test(){
    	DataSetHandlerByFile dataset = new DataSetHandlerByFile();

    	dataset.setFileName("/jsonTestByExample.json");
		
    	Integer idKey = 6;
    	
    	User user = DataSetGenerator.createFakeUser(idKey);
    	
    	LinkedHashSet<User> friendsList = dataset.friendsList(user);
    	
    	List<User> solutionActualSorted = dataset.friendsListSuggestedSorted(user);
    	
    	String solutionExpected_SuggestedSorted = "[Id: 2, name: Bob, city: Dublin, Id: 5, name: Attilio, city: Reggio Calabria]";
    	
    	for (User u : solutionActualSorted) {
    		assertFalse(friendsList.contains(u));
    	}
    	
    	assertEquals("Unexpected value: " + solutionActualSorted.toString(), solutionExpected_SuggestedSorted, solutionActualSorted.toString());
    	
     }
}
