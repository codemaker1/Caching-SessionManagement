package com.books.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.books.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

	public List<String> addCategory(String category) {
		 LOGGER.info("[SERVICE] Add Category: {}", category);
		return bookRepository.addCategory(category);
	}

	public List<String> removeCategory(String category) {
		LOGGER.info("[SERVICE] remove Category: {}", category);
		return bookRepository.removeCategory(category);
	}

	@CachePut( cacheNames = "books",key = "#category")
	public HashMap<String, ArrayList<String>> addBook(String category, String bookName) {
		LOGGER.info("[Service] ADD Book: {} {}",category,bookName);
		return bookRepository.addBook(category,bookName);
	}
 
	@CacheEvict(cacheNames = "books",key = "#category")
	public void removeBook(String category, String bookName) {
		LOGGER.info("[Service] remove Book: {} {}",category,bookName);
		this.bookRepository.removeBook(category,bookName);
	}
  
	@Cacheable("user")
	public ResponseEntity<Object> loginUser(String name, String password) {
		LOGGER.info("[Service] login user: {} {}",name,password);
		return bookRepository.loginUser(name,password);
	}

	public ResponseEntity<Object> registerUser(String name, String password) {
		LOGGER.info("[Service] register user: {} {}",name,password);
		return bookRepository.registerUser(name,password);
	}
 
	@Cacheable("books")
	public List<String> getbooks(String category) {
		LOGGER.info("[Service] get books: {}",category);
		return bookRepository.getbooks(category);
	}

}
