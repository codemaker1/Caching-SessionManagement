package com.books.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

	
	 private static final Logger LOGGER = LoggerFactory.getLogger(BookRepository.class);
	      
	 private static ArrayList<String> categories = new ArrayList<String>();
	 
	 private static HashMap<String,ArrayList<String>> inventory = new HashMap<String, ArrayList<String>>();
	 private static HashMap<String,String> userDetials = new HashMap<String, String>();
	 
	 static {
		 
		 categories.add("Romance");
		 categories.add("Horror");
		 categories.add("Action");
		 inventory.put("Romance", new ArrayList<String>(Arrays.asList(new String[] {"notebook","the wedding date"})));
		 inventory.put("Action",  new ArrayList<String>(Arrays.asList(new String[] {"Caching fire","the order"})));
		 inventory.put("Horror",  new ArrayList<String>(Arrays.asList(new String[] {"Dracula","IT"})));
		 userDetials.put("ishu","1234");
	 }
	 
	 
	 public ArrayList<String> addCategory(String category)
	 {
		 LOGGER.info("[Repository] add category: {}",category);
		if(!categories.contains(category))
		{
			categories.add(category);
		}
		return categories;
	 }


	public List<String> removeCategory(String category) {
		 LOGGER.info("[Repository] remove category: {}",category);
		 
		 if(categories.contains(category))
			{
				categories.remove(category);
			}
		
		return categories;
	}
	
	
	public HashMap<String,ArrayList<String>> addBook(String category, String bookName)
	{
	                if(inventory.containsKey(category))
	                {
	                	ArrayList<String> list = inventory.get(category);
	                	list.add(bookName);
	                	inventory.put(category, list);
	                }
	                else
	                {
	                	inventory.put(category,   new ArrayList<String>(Arrays.asList(new String[] {bookName})));
	                	categories.add(category);
	                }
	                return inventory;
	}


	public void removeBook(String category, String bookName) {
		if(inventory.containsKey(category))
        {
        	ArrayList<String> list = inventory.get(category);
        	list.remove(bookName);
        	inventory.put(category, list);
        }
	}


	public ResponseEntity<Object> loginUser(String name, String password) {
		if(userDetials.containsKey(name) && userDetials.get(name).equals(password))
		{
			return new ResponseEntity<Object>("Welcome user", HttpStatus.OK);
		}
		return new ResponseEntity<Object>("register yourself", HttpStatus.BAD_REQUEST);
	}


	public ResponseEntity<Object> registerUser(String name, String password) {
		if(!userDetials.containsKey(name))
		{
			userDetials.put(name, password);
			return new ResponseEntity<Object>("thank you for registerng", HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Already registered", HttpStatus.FORBIDDEN);
	}


	public List<String> getbooks(String category) {
		if(inventory.containsKey(category))
        {
        	ArrayList<String> list = inventory.get(category);
        	return list;
        }
		return null;
	}
}
