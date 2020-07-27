package com.books.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/add/cart")
public class AddToShoppingCartServlet  extends HttpServlet {
	
	    @Override
	    public void doGet(HttpServletRequest request,
	        HttpServletResponse response)
	        throws IOException, ServletException
	    {

	// First get the item values from the request.
	        String bookName = request.getParameter("bookName");

	        HttpSession session = request.getSession();

	// Get the cart.
	        ShoppingCart cart = (ShoppingCart) session.
	            getAttribute("ShoppingCart");

	// If there is no shopping cart, create one.
	        if (cart == null)
	        {
	            cart = new ShoppingCart();

	            session.setAttribute("ShoppingCart", cart);
	        }

	        cart.addItem(bookName);

	// Now display the cart and allow the user to check out or order more items.
	     //   response.sendRedirect(response.encodeRedirectURL(
	      //      "/shoppingcart/ShowCartAfterAdd.jsp"));
	    }
	}


