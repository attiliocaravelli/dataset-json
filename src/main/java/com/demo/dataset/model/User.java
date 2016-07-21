package com.demo.dataset.model;

import java.util.ArrayList;
import java.util.Comparator;

public class User implements Comparable<User> {
	private Integer id;
	private String name;
	private String city;
	private ArrayList<Integer> friends;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public ArrayList<Integer> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<Integer> friends) {
		this.friends = friends;
	}
	
	@Override
    public String toString() {
        return "Id: " + id + ", name: " + name + ", city: " + city;
    }

	@Override
	public int compareTo(User o) {
		return Comparators.ID.compare(this, o);
	}
	
	@Override
	public boolean equals(Object o){
	    if(o == null) return false;
	    if(!(o instanceof User)) return false;

	    User other = (User) o;
	    return this.hashCode() == other.hashCode();
	}
	
	@Override
	public int hashCode(){
	    return id.intValue();
	}
	
	public static class Comparators {

        public static Comparator<User> NAME = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.name.compareTo(o2.name);
            }
        };
        public static Comparator<User> ID = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.id.compareTo(o2.id);
            }
        };
        public static Comparator<User> CITY = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
            	return o1.city.compareTo(o2.city);
            }
        };
    }
}
