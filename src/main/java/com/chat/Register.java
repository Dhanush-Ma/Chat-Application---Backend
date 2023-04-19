package com.chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;



@WebServlet("/register")
public class Register extends HttpServlet{

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
	   PrintWriter out = res.getWriter();
	   res.addHeader("Access-Control-Allow-Origin", "*");
	   res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		
		String password = req.getParameter("password");

		if(password.length() < 6) {
			   res.setStatus(res.SC_BAD_REQUEST);
			   res.getWriter().write("Password should be atleat 6 characters!"); // set error message as response body
			   return;
		}
		System.out.println("here");
		String username = req.getParameter("username");
		
		// JDBC Connectivity to NoSQL Database (MongoDB)
		MongoClient mongo = new MongoClient("localhost", 27017);
		//Creating a database Name - ChatApp
		MongoDatabase db = mongo.getDatabase("ChatApp");
		
		MongoCollection userCollection = db.getCollection("Users");
		
		Document existingUser = (Document) userCollection.find(eq("username", username)).first();
		if(existingUser == null) {
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			
			Document user = new Document();
			user.append("firstName", firstName);
			user.append("lastName", lastName);
			user.append("username", username);
			user.append("password", password);
			
			userCollection.insertOne(user);
			res.setStatus(200);
			res.getWriter().write(user.toJson().toString()); // set error message as response body
			
	   }else {
			//handle invalid user - user already registered!
		   res.setStatus(res.SC_CONFLICT);
		   res.getWriter().write("Username is already registered!"); // set error message as response body
		  
	   }
				
}
}
