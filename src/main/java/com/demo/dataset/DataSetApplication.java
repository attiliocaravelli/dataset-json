package com.demo.dataset;

import com.demo.dataset.handlers.DataSetHandlerByFile;
import com.demo.dataset.utils.DataSetGenerator;

public class DataSetApplication {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		if (args.length == 1) {
			demo(args[0]);
		}
		else System.out.println("Error in filename paramaters");

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Total task has been completed in: " + elapsedTime + " ms");
	}
	
	private static void demo(String filename){
		DataSetHandlerByFile dataset = new DataSetHandlerByFile();
		dataset.setFileName(filename);
		
		dataset.friendsList(DataSetGenerator.createFakeUser(2));
		dataset.friendsListSuggestedNoSorted(DataSetGenerator.createFakeUser(2));
		dataset.friendsListSuggestedSorted(DataSetGenerator.createFakeUser(2));
	}
	
}
