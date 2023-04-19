package com.chat;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@WebServlet("/users")
public class GetUsers extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		   PrintWriter out = res.getWriter();
		   res.addHeader("Access-Control-Allow-Origin", "*");
		   res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
			
			String username = req.getParameter("query"); 
			String regexPattern = ".*" + username + ".*";

			
			// JDBC Connectivity to NoSQL Database (MongoDB)
			MongoClient mongo = new MongoClient("localhost", 27017);
			//Creating a database Name - ChatApp
			MongoDatabase db = mongo.getDatabase("ChatApp");
			
			ArrayList<String> result = new ArrayList<String>();
						
			MongoCollection<Document> userCollection = db.getCollection("Users");
			FindIterable<Document> users =  userCollection.find(new Document("username", new Document("$regex", regexPattern)));
			
			for (Document doc : users) {
			    result.add(doc.toJson());
			}
			
			System.out.println(result);
			res.setStatus(200);
			res.getWriter().write(result.toString()); // set error message as response body
					   	
	}

}
