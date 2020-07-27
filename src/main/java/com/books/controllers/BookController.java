package com.books.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.books.services.BookService;
import com.hazelcast.core.IMap;


@RestController
public class BookController {
	
	
	@Autowired
	BookService bookService;
	
	@Autowired
    private CacheManager cacheManager;
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
	 
	 
	     @PostMapping("/add/category")
	    public List<String> addCategory(@RequestBody String category){
	        LOGGER.info("[CONTROLLER] Add category: {}",category);
	        return bookService.addCategory(category);
	    }
	     
	     @PostMapping("/remove/category")
		    public List<String> removeCategory(@RequestBody String category){
		        LOGGER.info("[CONTROLLER] remove category: {}",category);
		        return bookService.removeCategory(category);
		    }
	     
	     @PostMapping("/login/admin")
	     public  ResponseEntity<Object> loginAdmin(@RequestParam String name, @RequestParam String password)
	     {
	    	 if(name.equals("admin") && password.equals("admin"))
	    	 {
	    		 return new ResponseEntity<Object>("Welcome admin", HttpStatus.OK);
	    	 }
	    	 return new ResponseEntity<Object>("try again", HttpStatus.BAD_REQUEST);
	     }
	     
	     
	     @PostMapping("/login/user")
	     public  ResponseEntity<Object> loginUser(@RequestParam String name, @RequestParam String password)
	     {
	    	 LOGGER.info("[CONTROLLER] login user: {} {}",name,password);
	    	 return bookService.loginUser(name,password);
	     }
	     
	     @PostMapping("/register/user")
	     public  ResponseEntity<Object> registerUser(@RequestParam String name, @RequestParam String password)
	     {
	    	 LOGGER.info("[CONTROLLER] register user: {} {}",name,password);
	    	 return bookService.registerUser(name,password);
	     }
	     
	     
	     
	     @PostMapping("/add/book/{category}")
	     public HashMap<String,ArrayList<String>> addBook(@PathVariable String category,@RequestBody String bookName)
	     {
	    	 LOGGER.info("[CONTROLLER] ADD Book: {} {}",category,bookName);
	    	 return bookService.addBook(category,bookName);
	     }
	     
	 
	     
	     @DeleteMapping("/book/{category}")
	     public void removeBook(@PathVariable String category,@RequestBody String bookName)
	     {
	    	 LOGGER.info("[CONTROLLER] remove Book: {} {}",category,bookName);
	    	  this.bookService.removeBook(category,bookName);
	     }
	     
	     @GetMapping("/book/{category}")
	     public List<String> getbooks(@PathVariable String category)
	     {
	    	 LOGGER.info("[CONTROLLER] get Books: {} ",category);
	    	 return bookService.getbooks(category);
	     }
	     
	     @GetMapping("/cache/{name}")
	     public String cache(@PathVariable("name") String cacheName) {
	         return ((IMap)this.cacheManager.getCache(cacheName).getNativeCache()).entrySet().toString();
	     }
	     
	     @GetMapping("/add/cart")
	     public Vector addCart(HttpSession session, @RequestParam String bookName)
	     {
	    	 
	     ShoppingCart cart = (ShoppingCart) session.getAttribute("ShoppingCart");

		        if (cart == null)
		        {
		            cart = new ShoppingCart();

		            session.setAttribute("ShoppingCart", cart);
		        }

		        cart.addItem(bookName);
		      return  cart.getItems();
	     }
	   
	 

}
