package com.chat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Filters.eq;


@WebServlet("/user")
public class GetUser extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		   PrintWriter out = res.getWriter();
		   res.addHeader("Access-Control-Allow-Origin", "*");
		   res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
			
			String userID = req.getParameter("userID"); 
			System.out.println(userID);
			
			// JDBC Connectivity to NoSQL Database (MongoDB)
			MongoClient mongo = new MongoClient("localhost", 27017);
			//Creating a database Name - ChatApp
			MongoDatabase db = mongo.getDatabase("ChatApp");
			
			MongoCollection<Document> userCollection = db.getCollection("Users");
			Document currentUser = (Document) userCollection.find(eq("_id", new ObjectId(userID))).first();
						
			res.setStatus(200);
			res.getWriter().write(currentUser.toJson().toString()); // set error message as response body
					   	
	}
}
