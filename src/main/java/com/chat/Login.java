package com.chat;


import static com.mongodb.client.model.Filters.eq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


@WebServlet("/login")
public class Login extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		   PrintWriter out = res.getWriter();
		   res.addHeader("Access-Control-Allow-Origin", "*");
		   res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
			
			String password = req.getParameter("password");
			String username = req.getParameter("username");
			
			// JDBC Connectivity to NoSQL Database (MongoDB)
			MongoClient mongo = new MongoClient("localhost", 27017);
			//Creating a database Name - ChatApp
			MongoDatabase db = mongo.getDatabase("ChatApp");
			
			MongoCollection userCollection = db.getCollection("Users");
			
			Document existingUser = (Document) userCollection.find(eq("username", username)).first();
			if(existingUser != null) {
				
				System.out.println(existingUser.getString("password"));
				if(existingUser.getString("password").equals(password)) {
					res.setStatus(200);
					res.getWriter().write(existingUser.toJson().toString()); // set error message as response body
				}else {
					   res.setStatus(res.SC_UNAUTHORIZED);
					   res.getWriter().write("Invalid Password!");	
				}
				
				
				
		   }else {
				//handle invalid user - user already registered!
			   res.setStatus(res.SC_CONFLICT);
			   res.getWriter().write("Username is not registered! Try Sigining in!"); // set error message as response body
			  
		   }
	}
}
