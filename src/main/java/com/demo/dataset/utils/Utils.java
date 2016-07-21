package com.demo.dataset.utils;

import org.codehaus.jackson.JsonNode;

import com.demo.dataset.configuration.DataSetConfiguration;
import com.demo.dataset.model.User;

public class Utils {

	public static User userFrom(JsonNode node){
		if (node == null) return null;
		User user = new User();
		user.setId(node.get(DataSetConfiguration.ID_JSON_FIELD).asInt());
		user.setName(node.get(DataSetConfiguration.NAME_JSON_FIELD).asText());
		user.setCity(node.get(DataSetConfiguration.CITY_JSON_FIELD).asText());
		return user;
	}
}
